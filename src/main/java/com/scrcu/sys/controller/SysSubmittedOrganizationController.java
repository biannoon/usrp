package com.scrcu.sys.controller;

import com.scrcu.common.annotation.LogAnnotation;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.Combobox;
import com.scrcu.common.exception.BaseException;
import com.scrcu.sys.entity.SysOrgSbmtdTree;
import com.scrcu.sys.entity.SysOrgSbmtdTreeAo;
import com.scrcu.sys.entity.vo.BranchTree;
import com.scrcu.sys.service.impl.SysSubmittedOrganizationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 报送机构前端控制器类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/10/10 14:56
 **/
@Controller
@RequestMapping("/submittedOrganization")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class SysSubmittedOrganizationController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(SysSubmittedOrganizationController.class);

	@Autowired
	private SysSubmittedOrganizationServiceImpl service;

	/**
	 * <p>
	 * 获取报送机构树首页
	 * </p>
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/10/12 13:42
	 **/
	@RequestMapping("/index")
	public String index() {
		return "sys/organization/submittedOrganization";
	}

	/**
	 * <p>
	 * 初始化报送机构树
	 * </p>
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/10/12 13:42
	 **/
	@PostMapping("/initTree")
	public @ResponseBody List<BranchTree> initTree(String treeType) {
		return this.service.listFirstTreeNode(treeType);
	}

	/**
	 * <p>
	 * 获取报送机构树子节点
	 * </p>
	 * @param orgSbmtdTreeAo 报送机构树数据传输对象
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/10/12 13:42
	 **/
	@PostMapping("/listTreeNode")
	public @ResponseBody List<BranchTree> listTreeNode(SysOrgSbmtdTreeAo orgSbmtdTreeAo) {
		return this.service.listTreeNode(orgSbmtdTreeAo);
	}

	/**
	 * <p>
	 * 存储报送机构根节点
	 * </p>
	 * @param request HttpServletRequest
	 * @param orgSbmtdTree 报送机构信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 15:55
	 **/
	@PostMapping("/saveRootOrganization")
	public @ResponseBody AjaxResult saveRootOrganization(HttpServletRequest request, SysOrgSbmtdTree orgSbmtdTree) {
		try {
			this.service.saveRootOrganization(orgSbmtdTree);
			((SysSubmittedOrganizationController) AopContext.currentProxy())
					.saveInsertOperationLog(request, orgSbmtdTree);
			return new AjaxResult(true, "新增报送机构树根节点成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		}catch (Exception e) {
			return new AjaxResult(false, "新增报送机构树根节点失败，程序异常。");
		}
	}

	@LogAnnotation(id={"orgId", "treeTyp"}, operType = "SYS09INST04", operation = "新增报送机构树根节点。")
	protected void saveInsertOperationLog(HttpServletRequest request, SysOrgSbmtdTree orgSbmtdTree) {
		this.logger.info("OA号：" + getUser().getUserId() + "，新增报送机构树根节点，机构号：" + orgSbmtdTree.getOrgId()
				+ "机构树类型：" + orgSbmtdTree.getTreeTyp());
	}

	/**
	 * <p>
	 * 存储报送机构子节点
	 * </p>
	 * @param request HttpServletRequest
	 * @param orgSbmtdTreeDto 报送机构信息传输对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 15:56
	 **/
	@PostMapping("/saveOrganizations")
	public @ResponseBody AjaxResult saveOrganizations(HttpServletRequest request,
													  @RequestBody SysOrgSbmtdTreeAo orgSbmtdTreeAo) {
		try {
			this.service.saveOrganizations(orgSbmtdTreeAo);
			((SysSubmittedOrganizationController) AopContext.currentProxy())
					.saveInsertOperationLog(request, orgSbmtdTreeAo);
			return new AjaxResult(true, "新增报送机构树子节点信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "新增报送机构树子节点失败，程序异常。");
		}
	}

	@LogAnnotation(id={"orgId", "treeType"}, operType = "SYS09INST04", operation = "新增子报送机构。")
	protected void saveInsertOperationLog(HttpServletRequest request, SysOrgSbmtdTreeAo orgSbmtdTreeAo) {
		this.logger.info("OA号：" + getUser().getUserId() + "，新增报送机构树根节点，机构号："
				+ orgSbmtdTreeAo.getOrgId() + "机构树类型：" + orgSbmtdTreeAo.getTreeType());
	}

	/**
	 * <p>
	 * 查询报送机构树是否已存在机构根节点
	 * </p>
	 * @param treeType 报送机构树类型
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/14 12:35
	 **/
	@PostMapping("/hasRootOrganization")
	public @ResponseBody boolean hasRootOrganization(String treeType) {
		return this.service.hasRootOrganization(treeType);
	}

	/**
	 * <p>
	 * 批量删除报送机构信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param orgSbmtdTrees 报送机构信息对象集合
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/14 17:04
	 **/
	@PostMapping("/deleteBatch")
	public @ResponseBody AjaxResult deleteBatch(HttpServletRequest request,
												@RequestBody List<SysOrgSbmtdTree> orgSbmtdTrees) {
		try {
			this.service.deleteSubmittedTreeBatch(orgSbmtdTrees);
			((SysSubmittedOrganizationController) AopContext.currentProxy())
					.saveDeleteOperationLog(request, "");
			return new AjaxResult(true, "删除报送机构节点信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "删除报送机构节点信息失败，程序异常。");
		}
	}

	@LogAnnotation(id={"orgId"}, operType = "SYS09INST05", operation = "删除报送机构信息。")
	protected void saveDeleteOperationLog(HttpServletRequest request, String orgId) {
		this.logger.info("OA号：" + getUser().getUserId() + "，删除报送机构信息");
	}

	/**
	 * <p>
	 * 更新报送机构信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param orgSbmtdTree 报送机构树信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/15 14:05
	 **/
	@PostMapping("/update")
	@LogAnnotation(id={"orgId", "treeTyp"}, operType = "SYS09INST06", operation = "更新报送机构信息。")
	public @ResponseBody AjaxResult update(HttpServletRequest request, SysOrgSbmtdTree orgSbmtdTree) {
		try {
			this.service.updateSubmittedOrganization(orgSbmtdTree);
			return new AjaxResult(true, "更新报送机构信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		}
	}

	/**
	 * <p>
	 * 获取复制报送机构树窗口中的报送机构树类型
	 * </p>
	 * @return java.util.Map<java.lang.Boolean,java.util.List<com.scrcu.common.base.vo.Combobox>>
	 * @author wuyu
	 * @date 2019/10/29 21:40
	 **/
	@PostMapping("/getOrgTypeData")
	public @ResponseBody Map<Boolean, List<Combobox>> getOrgTypeData() {
		return this.service.groupByOrgType();
	}

	/**
	 * <p>
	 * 复制报送机构树信息
	 * </p>
	 * @param request HttpServletRequest
	 * @param sourceTreeType 源报送机构树类型
	 * @param targetTreeType 目标报送机构树类型
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/29 21:41
	 **/
	@PostMapping("/copy")
	@LogAnnotation(id={"sourceTreeType", "targetTreeType"}, operType = "SYS09INST04", operation = "复制报送机构。")
	public @ResponseBody AjaxResult copy(HttpServletRequest request, String sourceTreeType, String targetTreeType) {
		try {
			this.service.copySubmittedTree(sourceTreeType, targetTreeType);
			return new AjaxResult(true, "复制报送机构信息成功。");
		} catch (BaseException e) {
			return new AjaxResult(false, e.getMessage());
		} catch (Exception e) {
			return new AjaxResult(false, "程序异常。");
		}
	}
}
