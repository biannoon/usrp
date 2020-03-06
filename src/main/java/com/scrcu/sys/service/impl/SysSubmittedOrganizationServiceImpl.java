package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.apm.service.impl.ComboboxService;
import com.scrcu.common.base.vo.Combobox;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysOrgSbmtdTree;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysOrgSbmtdTreeAo;
import com.scrcu.sys.entity.vo.BranchTree;
import com.scrcu.sys.mapper.SysSubmittedOrganizationMapper;
import com.scrcu.sys.service.SysSubmittedOrganizationService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 报送机构业务逻辑实现类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/10/10 14:58
 **/
@Service
public class SysSubmittedOrganizationServiceImpl
		extends ServiceImpl<SysSubmittedOrganizationMapper, SysOrgSbmtdTree>
		implements SysSubmittedOrganizationService {

	private static Logger logger = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

	@Autowired
	private SysDictryCdServiceImpl sysDictryCdService;
	@Autowired
	private SysOrganizationServiceImpl sysOrganizationService;
	@Autowired
	private ComboboxService comboboxService;

	/**
	 * <p>
	 * 获取报送机构树根节点
	 * </p>
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/10/12 13:39
	 **/
	public List<BranchTree> listFirstTreeNode(String treeType) {
		// 初始化虚拟根节点
		SysDictryCd sysDictryCd = this.sysDictryCdService.getDictionaryCode(treeType, "SYS10");
		BranchTree dictionaryTree = new BranchTree(sysDictryCd);
		dictionaryTree.setType(sysDictryCd.getDictryId());
		// 获取报送机构树类型分类树
		SysOrgSbmtdTreeAo orgSbmtdTreeAo = new SysOrgSbmtdTreeAo();
		orgSbmtdTreeAo.setOrgId(treeType);
		orgSbmtdTreeAo.setTreeType(treeType);
		List<BranchTree> trees = listTreeNode(orgSbmtdTreeAo);
		if (!CommonUtil.isEmptyList(trees)) {
			dictionaryTree.setState(BranchTree.OPENED_STATE);
			dictionaryTree.setChildren(trees);
		}
		List<BranchTree> result = new ArrayList<>();
		result.add(dictionaryTree);
		return result;
	}

	/**
	 * <p>
	 * 获取报送机构树子节点
	 * </p>
	 * @param orgSbmtdTreeAo 报送机构树数据传输对象
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/10/12 13:40
	 **/
	public List<BranchTree> listTreeNode(SysOrgSbmtdTreeAo orgSbmtdTreeAo) {
		List<BranchTree> result = new ArrayList<>();
		QueryWrapper<SysOrgSbmtdTree> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(!StringUtils.isEmpty(orgSbmtdTreeAo.getOrgId()),"spr_org_id",
						orgSbmtdTreeAo.getOrgId())
				.eq(!StringUtils.isEmpty(orgSbmtdTreeAo.getTreeType()), "tree_typ",
						orgSbmtdTreeAo.getTreeType())
				.ne(!StringUtils.isEmpty(orgSbmtdTreeAo.getSourceOrgId()), "org_id",
						orgSbmtdTreeAo.getSourceOrgId())
				.orderByAsc("org_nm");
		List<SysOrgSbmtdTree> list = list(queryWrapper);
		list.stream().forEach(sysSubmittedOrganization -> result.add(new BranchTree(sysSubmittedOrganization)));
		return result;
	}

	/**
	 * <p>
	 * 存储报送机构根节点
	 * </p>
	 * @param orgSbmtdTree 报送机构信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 15:57
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void saveRootOrganization(SysOrgSbmtdTree orgSbmtdTree) {
		// 报送机构根节点的上级机构id和报送机构树类型的值相同
		if (isEmptyData(orgSbmtdTree) || !Objects.equals(orgSbmtdTree.getTreeTyp(), orgSbmtdTree.getSprOrgId())) {
			throw new BaseException("新增根节点机构失败，数据异常。");
		}
		if (hasRootOrganization(orgSbmtdTree.getTreeTyp())) {
			throw new BaseException("当前报送机构类型下已存在机构根节点。");
		}
		SysOrganization organization = this.sysOrganizationService.getById(orgSbmtdTree.getOrgId());
		if (organization == null) {
			throw new BaseException("新增根节点机构失败，数据异常。");
		}
		SysOrgSbmtdTree rootOrganization = new SysOrgSbmtdTree(organization);
		rootOrganization.setSprOrgId(orgSbmtdTree.getSprOrgId());
		rootOrganization.setTreeTyp(orgSbmtdTree.getTreeTyp());
		// 机构序列号 由上级机构排序号 + '.' + 当前机构id   机构根节点的上级机构排序号为报送机构类型值
		rootOrganization.setOrgSeq(orgSbmtdTree.getTreeTyp() + "." + orgSbmtdTree.getOrgId());
		if (!save(rootOrganization)) {
			throw new BaseException("新增机构根节点时发生异常。");
		}
	}

	/**
	 * <p>
	 * 存储报送机构子节点
	 * </p>
	 * @param orgSbmtdTreeAo 报送机构信息传输对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 15:57
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void saveOrganizations(SysOrgSbmtdTreeAo orgSbmtdTreeAo) {
		if (isEmptyData(orgSbmtdTreeAo) || Objects.equals(orgSbmtdTreeAo.getOrgId(), orgSbmtdTreeAo.getTreeType())) {
			throw  new BaseException("新增报送机构子节点失败，数据异常。");
		}
		// 校验是否存在重复的机构
		List<String> names = listTreeName(orgSbmtdTreeAo.getTreeType(), orgSbmtdTreeAo.getChildren());
		if (!CommonUtil.isEmptyList(names)) {
			throw new BaseException("新增报送机构子节点失败。当前报送机构类型已存在以下机构：" + names.toString());
		}
		// 获取选中的子机构信息
		List<SysOrganization> organizations =
				this.sysOrganizationService.listOrganization(orgSbmtdTreeAo.getChildren());
		if (CommonUtil.isEmptyList(organizations)) {
			throw new BaseException("新增报送机构子节点失败，数据异常。");
		}
		// 获取当前报送机构信息
		SysOrgSbmtdTree superiorTree = getSubmittedTree(orgSbmtdTreeAo.getOrgId(), orgSbmtdTreeAo.getTreeType());
		List<SysOrgSbmtdTree> newTrees = createSubmittedTrees(organizations, superiorTree);
		// 数据存储
		if (!saveBatch(newTrees)) {
			throw new BaseException("新增报送机构子节点时发生异常");
		}
	}

	/**
	 * <p>
	 * 查询报送机构树类型下是否已存在机构根节点
	 * </p>
	 * @param treeType 报送机构树类型
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/12 14:29
	 **/
	public boolean hasRootOrganization(String treeType) {
		QueryWrapper<SysOrgSbmtdTree> queryWrapper = new QueryWrapper<>();
		// 报送机构树根节点的 上级机构号 和 报送机构树类型 值相同
		queryWrapper.eq("spr_org_id", treeType).eq("tree_typ", treeType);
		return !CommonUtil.isEmptyList(list(queryWrapper));
	}

	/**
	 * <p>
	 * 批量删除报送机构
	 * </p>
	 * @param orgSbmtdTrees 报送机构信息对象集合
	 * @param userId 用户ID
	 * @param ip 客户端请求IP
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/14 17:04
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void deleteSubmittedTreeBatch(List<SysOrgSbmtdTree> orgSbmtdTrees){
		// 校验报送机构值
		if (CommonUtil.isEmptyList(orgSbmtdTrees)) {
			throw  new BaseException("删除报送机构节点失败，数据为空。");
		}
		List<String> ids = getSubmittedTreeId(orgSbmtdTrees);
		verifyHasChildrenOrg(orgSbmtdTrees, orgSbmtdTrees.get(0).getTreeTyp());
		// 删除报送机构节点
		deleteSubmittedTreeBatch(ids, orgSbmtdTrees.get(0).getTreeTyp());
	}

	/**
	 * <p>
	 * 删除报送机构信息
	 * </p>
	 * @param ids 报送机构id集合
	 * @param treeType 报送机构类型
	 * @return void
	 * @author wuyu
	 * @date 2019/10/30 14:36
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void deleteSubmittedTreeBatch(List<String> ids, String treeType) {
		QueryWrapper<SysOrgSbmtdTree> deleteWrapper = new QueryWrapper<>();
		deleteWrapper.in("org_id", ids).eq("tree_typ", treeType);
		if (!remove(deleteWrapper)) {
			throw new BaseException("删除报送机构信息时发生异常。");
		}
	}

	/**
	 * <p>
	 * 更新报送机构树
	 * </p>
	 * @param orgSbmtdTree 报送机构信息对象
	 * @param userId 用户ID
	 * @param ip 客户端请求IP
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/15 14:06
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void updateSubmittedOrganization(SysOrgSbmtdTree orgSbmtdTree) {
		if (orgSbmtdTree == null) {
			throw new BaseException("更新报送机构信息失败，数据为空。");
		}
		if (StringUtils.isEmpty(orgSbmtdTree.getOrgId()) || StringUtils.isEmpty(orgSbmtdTree.getTreeTyp())
				|| StringUtils.isEmpty(orgSbmtdTree.getOrgNm())) {
			throw new BaseException("更新报送机构信息失败，数据异常。");
		}
		if (!isQualifiedName(orgSbmtdTree.getOrgNm())) {
			throw new BaseException("更新报送机构信息失败，机构名称存在非法字符。");
		}
		UpdateWrapper<SysOrgSbmtdTree> updateWrapper = new UpdateWrapper<>();
		// 选择了上级机构后设置上级机构和机构序列号信息
		if (!StringUtils.isEmpty(orgSbmtdTree.getSprOrgId())) {
			// 查询当前报送机构信息
			SysOrgSbmtdTree tempTree = getSubmittedTree(orgSbmtdTree.getOrgId(), orgSbmtdTree.getTreeTyp());
			if (tempTree == null) {
				throw new BaseException("更新报送机构信息失败，数据异常。");
			}
			// 查询选择的上级报送机构信息
			SysOrgSbmtdTree newSuperior = getSubmittedTree(orgSbmtdTree.getSprOrgId(), orgSbmtdTree.getTreeTyp());
			// 设置报送机构序列号
			String newOrgSeq = newSuperior.getOrgSeq() + "." + tempTree.getOrgId();
			updateChildrenOrgTree(tempTree.getOrgSeq(), newOrgSeq);
			updateWrapper.set("spr_org_id", newSuperior.getOrgId()).set("org_seq", newOrgSeq);
		}
		// 更新当前报送机构信息
		updateWrapper.set("org_nm", orgSbmtdTree.getOrgNm()).eq("org_id", orgSbmtdTree.getOrgId())
				.eq("tree_typ", orgSbmtdTree.getTreeTyp());
		if (!update(updateWrapper)) {
			throw new BaseException("更新当前报送机构信息时发生异常。");
		}
	}

	/**
	 * <p>
	 * 更新子机构信息
	 * </p>
	 * @param orgSeq 旧机构序列号
	 * @param newOrgSeq 新机构序列号
	 * @return void
	 * @author wuyu
	 * @date 2019/10/30 15:57
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void updateChildrenOrgTree(String orgSeq, String newOrgSeq) {
		QueryWrapper<SysOrgSbmtdTree> childrenWrapper = new QueryWrapper<>();
		childrenWrapper.likeRight("org_seq", orgSeq);
		List<SysOrgSbmtdTree> childrenList = list(childrenWrapper);
		childrenList.stream().forEach(tree -> tree.setOrgSeq(tree.getOrgSeq().replace(orgSeq, newOrgSeq)));
		UpdateWrapper<SysOrgSbmtdTree> updateChildrenWrapper;
		// 更新所有子报送机构的机构序列号
		for (SysOrgSbmtdTree children : childrenList) {
			updateChildrenWrapper = new UpdateWrapper<>();
			updateChildrenWrapper.set("org_seq", children.getOrgSeq()).eq("org_id", children.getOrgId())
					.eq("tree_typ", children.getTreeTyp());
			if (!update(updateChildrenWrapper)) {
				throw new BaseException("更新当前报送机构的子机构信息时发生异常。");
			}
		}
	}

	/**
	 * <p>
	 * 分组报送机构树类型数据
	 * </p>
	 * @return java.util.Map<java.lang.Boolean,java.util.List<com.scrcu.common.base.vo.Combobox>>
	 * @author wuyu
	 * @date 2019/10/29 21:43
	 **/
	public Map<Boolean, List<Combobox>> groupByOrgType() {
		QueryWrapper<SysOrgSbmtdTree> queryWrapper = new QueryWrapper<>();
		queryWrapper.apply("spr_org_id = tree_typ");
		List<SysOrgSbmtdTree> trees = list(queryWrapper);
		if (CommonUtil.isEmptyList(trees)) {
			return new HashMap<>();
		}
		Set<String> set = new HashSet<>(trees.stream().map(tree -> tree.getTreeTyp()).collect(Collectors.toList()));
		List<Combobox> comboboxList = this.comboboxService.listDictionaryCode("SYS10");
		return comboboxList.stream().collect(Collectors.groupingBy(combobox -> set.contains(combobox.getValue())));
	}

	/**
	 * <p>
	 * 复制报送机构树信息
	 * </p>
	 * @param sourceTreeType 源报送机构树类型
	 * @param targetTreeType 目标报送机构树类型
	 * @param log 机构信息对象
	 * @return void
	 * @author wuyu
	 * @date 2019/10/29 21:42
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void copySubmittedTree(String sourceTreeType, String targetTreeType) {
		QueryWrapper<SysOrgSbmtdTree> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tree_typ", sourceTreeType);
		List<SysOrgSbmtdTree> trees = list(queryWrapper);
		if (CommonUtil.isEmptyList(trees)) {
			throw new BaseException("数据异常。");
		}
		List<SysOrgSbmtdTree> newTrees = new ArrayList<>(trees.size());
		trees.stream().forEach(tree -> {
			SysOrgSbmtdTree newTree = new SysOrgSbmtdTree();
			newTree.setOrgId(tree.getOrgId());
			if (Objects.equals(tree.getTreeTyp(), tree.getSprOrgId())) {
				newTree.setSprOrgId(targetTreeType);
			} else {
				newTree.setSprOrgId(tree.getSprOrgId());
			}
			newTree.setOrgNm(tree.getOrgNm());
			newTree.setTreeTyp(targetTreeType);
			newTree.setOrgSeq(tree.getOrgSeq().replaceFirst(tree.getTreeTyp(), targetTreeType));
			newTrees.add(newTree);
		});
		if (!saveBatch(newTrees)) {
			throw new BaseException("复制报送机构树信息时发生异常。");
		}
	}

	private boolean isQualifiedName(String name) {
		String regex = "^[\u4E00-\u9FA5A-Za-z0-9]{1,150}$";
		return Pattern.matches(regex, name);
	}

	/**
	 * <p> 
	 * 报送机构树信息是否为空
	 * </p>
	 * @param tree 报送机构树信息对象
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/30 14:27
	 **/
	private boolean isEmptyData(SysOrgSbmtdTree tree) {
		return tree == null || StringUtils.isEmpty(tree.getOrgId()) || StringUtils.isEmpty(tree.getTreeTyp())
				|| StringUtils.isEmpty(tree.getSprOrgId());
	}

	/**
	 * <p> 
	 * 报送机构树数据传输对象是否为空
	 * </p>
	 * @param orgSbmtdTreeAo 报送机构树数据传输对象
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/30 14:27
	 **/
	private boolean isEmptyData(SysOrgSbmtdTreeAo orgSbmtdTreeAo) {
		return orgSbmtdTreeAo == null || StringUtils.isEmpty(orgSbmtdTreeAo.getOrgId())
				|| StringUtils.isEmpty(orgSbmtdTreeAo.getTreeType())
				|| CommonUtil.isEmptyList(orgSbmtdTreeAo.getChildren());
	}

	/**
	 * <p> 
	 * 获取报送机构树的名称集合
	 * </p>
	 * @param treeType 报送机构树类型
	 * @param ids 报送机构树id值集合
	 * @return java.util.List<java.lang.String>
	 * @author wuyu
	 * @date 2019/10/30 14:28
	 **/
	private List<String> listTreeName(String treeType, List<String> ids) {
		QueryWrapper<SysOrgSbmtdTree> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("org_nm").eq("tree_typ", treeType).in("org_id", ids);
		List<SysOrgSbmtdTree> trees = list(queryWrapper);
		if (!CommonUtil.isEmptyList(trees)) {
			return trees.stream().map(tree -> tree.getOrgNm()).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	/**
	 * <p> 
	 * 获取单个报送机构树信息
	 * </p>
	 * @param orgId 报送机构id
	 * @param treeType 报送机构树类型
	 * @return com.scrcu.sys.entity.SysOrgSbmtdTree
	 * @author wuyu
	 * @date 2019/10/30 14:29
	 **/
	private SysOrgSbmtdTree getSubmittedTree(String orgId, String treeType) {
		QueryWrapper<SysOrgSbmtdTree> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("tree_typ", treeType).eq("org_id", orgId);
		return getOne(queryWrapper);
	}

	/**
	 * <p> 
	 * 将机构信息转换为报送机构信息 并维持原机构信息的层级关系
	 * </p>
	 * @param organizations 机构信息对象集合
	 * @param superiorTree 报送机构树信息对象
	 * @return java.util.List<com.scrcu.sys.entity.SysOrgSbmtdTree>
	 * @author wuyu
	 * @date 2019/10/30 14:30
	 **/
	private List<SysOrgSbmtdTree> createSubmittedTrees(List<SysOrganization> organizations,
													   SysOrgSbmtdTree superiorTree) {
		int size = organizations.size();
		List<SysOrgSbmtdTree> result = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			SysOrganization organization = organizations.get(i);
			SysOrgSbmtdTree newOrgSbmtdTree = new SysOrgSbmtdTree(organization);
			newOrgSbmtdTree.setSprOrgId(superiorTree.getOrgId());
			newOrgSbmtdTree.setTreeTyp(superiorTree.getTreeTyp());
			StringBuffer orgSeq = new StringBuffer(superiorTree.getOrgSeq());
			for (int j = 0; j < i; j++) {
				SysOrganization parentOrg = organizations.get(j);
				if (organization.getGradationCode().startsWith(parentOrg.getGradationCode())) {
					newOrgSbmtdTree.setSprOrgId(parentOrg.getId());
					orgSeq.append(".").append(parentOrg.getId());
				}
			}
			orgSeq.append(".").append(organization.getId());
			newOrgSbmtdTree.setOrgSeq(orgSeq.toString());
			result.add(newOrgSbmtdTree);
		}
		return result;
	}

	/**
	 * <p> 
	 * 检验报送机构树是否存在子机构信息
	 * </p>
	 * @param trees 报送机构树信息集合
	 * @param treeType 报送机构树类型
	 * @return void
	 * @author wuyu
	 * @date 2019/10/30 14:32
	 **/
	private void verifyHasChildrenOrg(List<SysOrgSbmtdTree> trees, String treeType) {
		List<String> ids = trees.stream().map(tree -> tree.getOrgId()).collect(Collectors.toList());
		QueryWrapper<SysOrgSbmtdTree> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("spr_org_id as sprOrgId");
		queryWrapper.in("spr_org_id", ids).eq("tree_typ", treeType);
		List<SysOrgSbmtdTree> result = list(queryWrapper);
		if (!CommonUtil.isEmptyList(result)) {
			List<String> parentIds = result.stream()
					.map(tree -> tree.getSprOrgId()).distinct().collect(Collectors.toList());
			Set<String> set = new HashSet<>(parentIds);
			List<String> names = new ArrayList<>(set.size());
			for (SysOrgSbmtdTree tree : trees) {
				if (set.contains(tree.getOrgId())) {
					names.add(tree.getOrgNm());
				}
			}
			throw new BaseException(names.toString() + " 存在子机构信息。");
		}
	}

	/**
	 * <p>
	 * 获取报送机构树的id信息
	 * </p>
	 * @param trees 报送机构树信息对象集合
	 * @return java.util.List<java.lang.String>
	 * @author wuyu
	 * @date 2019/10/30 14:33
	 **/
	private List<String> getSubmittedTreeId(List<SysOrgSbmtdTree> trees) {
		List<String> result = new ArrayList<>();
		int size = trees.size();
		for (int i = 0; i < size; i++) {
			SysOrgSbmtdTree orgSbmtdTree = trees.get(i);
			// 数据校验
			if (StringUtils.isEmpty(orgSbmtdTree.getOrgId()) || StringUtils.isEmpty(orgSbmtdTree.getTreeTyp())
				|| Objects.equals(orgSbmtdTree.getOrgId(), orgSbmtdTree.getTreeTyp()))
				throw  new BaseException("数据异常。");
			result.add(orgSbmtdTree.getOrgId());
		}
		return result;
	}
}
