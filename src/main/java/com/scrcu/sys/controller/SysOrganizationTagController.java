package com.scrcu.sys.controller;

import com.scrcu.common.annotation.LogAnnotation;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysOrganizationTag;
import com.scrcu.sys.service.impl.SysOrganizationTagServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 机构标签前端控制类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/10/11 9:54
 **/
@RestController
@RequestMapping("/organizationTag")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class SysOrganizationTagController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SysOrganizationTagController.class);

	@Autowired
	private SysOrganizationTagServiceImpl service;

	/**
	 * <p>
	 * 分页查询机构标签信息对象
	 * </p>
	 * @param request HttpServletRequest
	 * @param organizationTag 机构信息对象
	 * @return com.scrcu.common.base.vo.DataGrid
	 * @author wuyu
	 * @date 2019/10/11 10:47
	 **/
	@PostMapping("/queryOrganizationTag")
	public DataGrid queryOrganizationTag(HttpServletRequest request, SysOrganizationTag organizationTag) {
		return this.service.listOrganizationTag(organizationTag, getPage(request));
	}

	/**
	 * <p>
	 * 获取单个机构标签信息
	 * </p>
	 * @param organizationTag 机构标签信息对象封装的查询条件数据
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/11 15:42
	 **/
	@PostMapping("/getOne")
	public AjaxResult getOne(SysOrganizationTag organizationTag) {
		return this.service.getOrganizationTag(organizationTag);
	}

	/**
	 * <p>
	 * 存储机构标签信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param organizationTag 机构标签信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/11 15:44
	 **/
	@PostMapping("/save")
	public AjaxResult save(HttpServletRequest request, SysOrganizationTag organizationTag) {
		try {
			VerifyResult verifyResult = this.service.verifyTagValueForSave(organizationTag);
			if (!verifyResult.isVerifyQualified()) {
				return new AjaxResult(false, verifyResult.getInformation());
			}
			this.service.saveOrganizationTag(organizationTag);
			((SysOrganizationTagController) AopContext.currentProxy()).saveInsertOperationLog(request, organizationTag);
			return new AjaxResult(true, "新增机构标签信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		}
	}

	/**
	 * <p>
	 * 存储机构标签信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param organizationTag 机构标签信息对象
	 * @return void
	 * @author wuyu
	 * @date 2019/11/20 16:15
	 **/
	@LogAnnotation(id={"orgId", "tagTyp"}, operType = "SYS09INST07", operation = "新增机构标签信息。")
	protected void saveInsertOperationLog(HttpServletRequest request, SysOrganizationTag organizationTag) {
		this.logger.info("OA号：" + getUser().getUserId() + "，新增机构标签信息，机构id：" + organizationTag.getOrgId()
				+ "，标签类型：" + organizationTag.getTagTyp());
	}

	/**
	 * <p>
	 * 更新机构标签信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param organizationTag 机构标签信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 10:33
	 **/
	@PostMapping("/update")
	public AjaxResult update(HttpServletRequest request, SysOrganizationTag organizationTag) {
		try {
			VerifyResult verifyResult = this.service.verifyTagValue(organizationTag);
			if (!verifyResult.isVerifyQualified()) {
				return new AjaxResult(false, verifyResult.getInformation());
			}
			this.service.updateOrganizationTag(organizationTag);
			((SysOrganizationTagController) AopContext.currentProxy()).saveUpdateOperationLog(request, organizationTag);
			return new AjaxResult(true, "更新机构标签信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		}
	}

	/**
	 * <p>
	 * 更新机构标签信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param organizationTag 机构标签信息对象
	 * @return void
	 * @author wuyu
	 * @date 2019/10/12 10:33
	 **/
	@LogAnnotation(id={"orgId", "tagTyp"}, operType = "SYS09INST08", operation = "更新机构标签信息。")
	protected void saveUpdateOperationLog(HttpServletRequest request, SysOrganizationTag organizationTag) {
		this.logger.info("OA号：" + getUser().getUserId() + "，更新机构标签信息，机构id：" + organizationTag.getOrgId()
				+ "，标签类型：" + organizationTag.getTagTyp());
	}

	/**
	 * <p>
	 * 更新机构标签信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param organizationTag 机构标签信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 10:34
	 **/
	@PostMapping("/delete")
	public AjaxResult delete(HttpServletRequest request, SysOrganizationTag organizationTag) {
		try {
			this.service.deleteOrganizationTag(organizationTag);
			((SysOrganizationTagController) AopContext.currentProxy()).saveDeleteOperationLog(request, organizationTag);
			return new AjaxResult(true, "删除机构标签信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		}
	}

	@LogAnnotation(id={"orgId", "tagTyp"}, operType = "SYS09INST09", operation = "删除机构标签信息。")
	protected void saveDeleteOperationLog(HttpServletRequest request, SysOrganizationTag organizationTag) {
		this.logger.info("OA号：" + getUser().getUserId() + "，删除机构标签信息，机构id：" + organizationTag.getOrgId()
				+ "，标签类型：" + organizationTag.getTagTyp());
	}
}
