package com.scrcu.sys.controller;

import com.scrcu.common.annotation.LogAnnotation;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.dto.SysOrganizationFieldDto;
import com.scrcu.sys.entity.vo.BranchTree;
import com.scrcu.sys.service.impl.SysOrganizationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 机构信息管理前端控制器类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:41
 **/
@Controller
@RequestMapping("/organization")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class SysOrganizationController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SysOrganizationController.class);

	@Autowired
	private SysOrganizationServiceImpl service;

	/**
	 * <p>
	 * 返回机构信息页面
	 * </p>
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/6 16:49
	 **/
	@RequestMapping("/index")
	public String index() {
		return "sys/organization/organization";
	}

	/**
	 * <p>
	 * 获取顶层机构树节点信息
	 * </p>
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/9/6 16:56
	 **/
	@PostMapping("/findFirstTreeNode")
	public @ResponseBody List<BranchTree> findFirstTreeNode() {
		return this.service.listFirstTreeNode();
	}

	/**
	 * <p>
	 * 根据自身机构树节点id查询所有子机构树节点信息
	 * </p>
	 * @param parentId 自身树形节点id
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/9/6 16:57
	 **/
	@PostMapping("/findChildrenTreeNode")
	public @ResponseBody List<BranchTree> findChildrenTreeNode(String parentId) {
		return this.service.listTreeNodeByParentId(parentId);
	}

	/**
	 * <p>
	 * 查询机构信息数据列表
	 * </p>
	 * @param request HttpServletRequest
	 * @param organization 机构信息对象
	 * @return com.scrcu.common.base.vo.DataGrid
	 * @author wuyu
	 * @date 2019/9/6 17:03
	 **/
	@PostMapping("/queryOrganization")
	public @ResponseBody DataGrid queryOrganization(HttpServletRequest request, SysOrganization organization) {
		return this.service.listOrganization(organization, this.getPage(request));
	}

	/**
	 * <p>
	 * 根据机构ID查询机构详情
	 * </p>
	 * @param id 机构ID
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/9/6 17:03
	 **/
	@PostMapping("/getById")
	public @ResponseBody AjaxResult getById(String id) {
		return this.service.getOrganization(id);
	}

	/**
	 * <p>
	 * 策略模型更新机构单个字段信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param dto 机构字段更新参数传输对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/9/12 17:41
	 **/
	@PostMapping("/updateField")
	public @ResponseBody AjaxResult updateField(HttpServletRequest request, SysOrganizationFieldDto dto) {
		try {
			this.service.updateField(dto, getLogObject(request));
			return new AjaxResult(true, "更新成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "更新失败。");
		}
	}

	/**
	 * <p>
	 * 获取机构字段值
	 * </p>
	 * @param dto 机构字段更新参数传输对象
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/14 21:38
	 **/
	@PostMapping("/getFieldValue")
	public @ResponseBody AjaxResult getFieldValue(SysOrganizationFieldDto dto) {
		return this.service.getFieldValue(dto);
	}

	/**
	 * <p>
	 * 查询虚拟机构信息
	 * </p>
	 * @param id 机构id
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/9 15:50
	 **/
	@PostMapping("/getVirtualOrganization")
	public @ResponseBody AjaxResult getVirtualOrganization(String id) {
		return this.service.getVirtualOrganization(id);
	}

	/**
	 * <p>
	 * 删除虚拟机构
	 * </p>
	 * @param id 机构ID
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/9 16:12
	 **/
	@PostMapping("/deleteVirtualOrganization")
	public @ResponseBody AjaxResult deleteVirtualOrganization(HttpServletRequest request,
															  @RequestParam(value = "ids[]") List<String> ids) {
		try {
			this.service.deleteVirtualOrganization(ids);
			((SysOrganizationController) AopContext.currentProxy()).saveDeleteOperationLog(request, "");
			return new AjaxResult(true, "删除虚拟机构信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "删除虚拟机构信息时发生异常。");
		}
	}

	@LogAnnotation(id={"orgId"}, operType = "SYS09INST02", operation = "删除虚拟机构信息。")
	protected void saveDeleteOperationLog(HttpServletRequest request, String orgId) {
		this.logger.info("OA号:" + getUser().getUserId() + "，批量删除虚拟机构信息");
	}

	/**
	 * <p>
	 * 存储虚拟机构
	 * </p>
	 * @param organization 机构信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/9 16:12
	 **/
	@PostMapping("/saveVirtualOrganization")
	public @ResponseBody AjaxResult saveVirtualOrganization(HttpServletRequest request, SysOrganization organization) {
		try {
			this.service.saveVirtualOrganization(organization);
			((SysOrganizationController) AopContext.currentProxy())
					.saveInsertOperationLog(request, organization.getId());
			return new AjaxResult(true, "新增虚拟机构信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "新增虚拟机构信息时发生异常。");
		}
	}

	@LogAnnotation(id={"orgId"}, operType = "SYS09INST01", operation = "新增虚拟机构信息。")
	protected void saveInsertOperationLog(HttpServletRequest request, String orgId) {
		this.logger.info("OA号:" + getUser().getUserId() + "，新增虚拟机构信息，机构id：" + orgId);
	}

	/**
	 * <p>
	 * 更新虚拟机构
	 * </p>
	 * @param organization 机构信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/9 16:12
	 **/
	@PostMapping("/updateVirtualOrganization")
	public @ResponseBody AjaxResult updateVirtualOrganization(HttpServletRequest request,
															  SysOrganization organization) {
		try {
			this.service.updateVirtualOrganization(organization);
			((SysOrganizationController) AopContext.currentProxy())
					.saveUpdateOperationLog(request, organization.getId());
			return new AjaxResult(true, "更新虚拟机构信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "更新虚拟机构信息时发生异常。");
		}
	}

	@LogAnnotation(id={"orgId"}, operType = "SYS09INST03", operation = "更新虚拟机构信息。")
	protected void saveUpdateOperationLog(HttpServletRequest request, String orgId) {
		this.logger.info("OA号:" + getUser().getUserId() + "，更新虚拟机构信息，机构id：" + orgId);
	}

	/**
	 * <p>
	 * 删除虚拟机构时校验机构状态
	 * </p>
	 * @param ids 机构id值集合
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/10 14:52
	 **/
	@PostMapping("/verifyOrganizationStatus")
	public @ResponseBody AjaxResult verifyOrganizationStatus(@RequestParam(value = "ids[]") List<String> ids) {
		try {
			this.service.verifyOrganizationStatus(ids);
			return new AjaxResult(true, "");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "校验虚拟机构状态时发生异常。");
		}
	}

	@RequestMapping("/getSysOrgInfoByOrgCd")
	@ResponseBody
	public AjaxResult getSysOrgInfoByOrgCd(String orgCd){
		System.out.println("orgCd:"+orgCd);
		return new AjaxResult(true,"",EhcacheUtil.getSysOrgInfoByOrgCd(orgCd),"");
	}
}
