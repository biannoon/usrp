package com.scrcu.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.TreeNode;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.common.utils.TreeNodeUtil;
import com.scrcu.sys.entity.*;
import com.scrcu.sys.entity.dto.SysOrganizationFieldDto;
import com.scrcu.sys.entity.vo.BranchTree;
import com.scrcu.sys.mapper.SysOrganizationMapper;
import com.scrcu.sys.service.SysOrganizationService;
import com.scrcu.sys.service.strategy.SysOrganizationContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 机构信息业务逻辑处理实现类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:38
 **/
@Service
public class SysOrganizationServiceImpl extends ServiceImpl<SysOrganizationMapper, SysOrganization>
		implements SysOrganizationService {

	private static Logger logger = LoggerFactory.getLogger(SysOrganizationServiceImpl.class);

	@Autowired
	private SysOrganizationContext sysOrganizationContext;
	@Autowired
	private SysOperLogServiceImpl sysOperLogService;
	@Autowired
	private SysSubmittedOrganizationServiceImpl sysSubmittedOrganizationService;

	/**
	 * <p>
	 * 获取机构树第一个子节点
	 * </p>
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/9/6 16:55
	 **/
	public List<BranchTree> listFirstTreeNode() {
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<SysOrganization>();
		queryWrapper.isNull("spr_org_id");
		queryWrapper.orderByAsc("org_cd");
		List<BranchTree> trees = getBranchTree(list(queryWrapper));
		// 增加虚拟结构根节点
		BranchTree virtualRoot = new BranchTree();
		virtualRoot.setId("VROOT");
		virtualRoot.setText("虚拟机构根节点");
		virtualRoot.setValue("VROOT");
		virtualRoot.setFlag(BranchTree.FLAG_VIRTUAL_ROOT_TREE);
		trees.add(virtualRoot);
		return trees;
	}

	/**
	 * <p>
	 * 根据当前节点id查询其所有子节点信息
	 * </p>
	 * @param parentId 节点ID
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/9/6 16:54
	 **/
	public List<BranchTree> listTreeNodeByParentId(String parentId) {
		return getBranchTree(EhcacheUtil.getSysOrgInfoByCache(parentId));
	}

	/**
	 * <p>
	 * 将机构数据对象转换成树形结构对象
	 * </p>
	 * @param organizations 机构对象信息数据集合
	 * @return java.util.List<com.scrcu.sys.entity.vo.BranchTree>
	 * @author wuyu
	 * @date 2019/9/6 16:54
	 **/
	private List<BranchTree> getBranchTree(List<SysOrganization> organizations) {
		List<BranchTree> branchTrees = new ArrayList<>();
		if (!CommonUtil.isEmptyList(organizations)) {
			organizations.stream().forEach(organization -> branchTrees.add(new BranchTree(organization)));
		}
		return branchTrees;
	}

	/**
	 * <p>
	 * 分页查询机构信息
	 * </p>
	 * @param organization 机构信息对象
	 * @param pageParameters 分页信息对象
	 * @return com.scrcu.common.base.vo.DataGrid
	 * @author wuyu
	 * @date 2019/9/6 17:01
	 **/
	public DataGrid listOrganization(SysOrganization organization, PageParameters pageParameters) {
		Page<SysOrganization> page = new Page<>(pageParameters.getPage(), pageParameters.getRows());
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(!StringUtils.isEmpty(organization.getName()), "org_nm", organization.getName())
				.eq(!StringUtils.isEmpty(organization.getCode()), "org_cd", organization.getCode())
				.eq(!StringUtils.isEmpty(organization.getSuperiorId()), "spr_org_id",
						organization.getSuperiorId())
				.orderByDesc("org_cd");
		IPage<SysOrganization> result = page(page, queryWrapper);
		return new DataGrid(result.getTotal(), result.getRecords());
	}

	/**
	 * <p>
	 * 获取机构信息列表
	 * </p>
	 * @param ids 机构id值集合
	 * @return java.util.List<com.scrcu.sys.entity.SysOrganization>
	 * @author wuyu
	 * @date 2019/10/30 14:08
	 **/
	public List<SysOrganization> listOrganization(List<String> ids) {
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("org_id", ids).orderByAsc("org_grdtn_cd");
		return list(queryWrapper).stream()
				.sorted(
						Comparator.comparing(SysOrganization :: getGradationCode, (x, y) -> {
							if (x.length() > y.length())
								return 1;
							else if (x.length() == y.length())
								return 0;
							else
								return -1;
						})
				)
				.collect(Collectors.toList());
	}

	/**
	 * <p>
	 * 根据机构ID查询机构详情
	 * </p>
	 * @param id 机构ID
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/9/6 17:01
	 **/
	public AjaxResult getOrganization(String id) {
		AjaxResult result = new AjaxResult();
		SysOrganization organization = getById(id);
		if (organization != null) {
			result.setSuccess(true);
			result.setData(organization);
			setDictionaryCodeName(organization);
		} else {
			result.setSuccess(false);
			result.setMessage("查询结果为空。");
		}
		return result;
	}

	/**
	 * <p>
	 * 查询虚拟机构信息
	 * </p>
	 * @param id 机构id
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/9 15:48
	 **/
	public AjaxResult getVirtualOrganization(String id) {
		AjaxResult result = new AjaxResult();
		SysOrganization organization = getById(id);
		if (organization == null || !Objects.equals(organization.getSuperiorId(), "VROOT")) {
			result.setSuccess(false);
			result.setMessage("请选择虚拟机构后继续当前操作。");
		} else {
			result.setSuccess(true);
			result.setData(organization);
		}
		return result;
	}

	/**
	 * <p>
	 * 更新人行支付行号
	 * </p>
	 * @param dto 机构字段更新参数传输对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/9/12 15:45
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void updateField(SysOrganizationFieldDto dto, SysOperlog log) {
		if (StringUtils.isEmpty(dto.getId())) {
			throw new BaseException("更新失败，数据异常。");
		}
		String value = dto.getValue();
		if (value == null || StringUtils.isEmpty(value.trim())) {
			value = "";
		}
		value = value.replace(" ", "");
		String key = dto.getKey();
		String fieldCnName = this.sysOrganizationContext.getFieldCnName(key);

		this.sysOrganizationContext.verifyFieldValueLength(key, value);
		UpdateWrapper<SysOrganization> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set(this.sysOrganizationContext.getFieldEnName(key), value);
		updateWrapper.eq("org_id", dto.getId());
		if (!update(updateWrapper)) {
			throw new RuntimeException("更新" + fieldCnName + "失败。");
		}
		//设置日志详情
		setLog(log, "更新报送机构字段：" + fieldCnName, this.sysOrganizationContext.getUpdateOperationCode(key),
				dto.getId());
		if (this.sysOperLogService.insert(log) < 1) {
			throw new RuntimeException("存储更新" + fieldCnName + "字段值操作日志失败。");
		}
	}

	/**
	 * <p>
	 * 获取机构字段值
	 * </p>
	 * @param dto 机构字段更新参数传输对象
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/14 21:36
	 **/
	public AjaxResult getFieldValue(SysOrganizationFieldDto dto) {
		AjaxResult result = new AjaxResult();
		SysOrganization organization = getById(dto.getId());
		result.setData(sysOrganizationContext.getFieldValue(dto.getKey(), organization));
		return result;
	}

	/**
	 * <p>
	 * 删除虚拟机构
	 * </p>
	 * @param ids 机构id值集合
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/10 15:44
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void deleteVirtualOrganization(List<String> ids) {
		verifyOrganizationStatus(ids);
		if (!removeByIds(ids)) {
			throw new BaseException("删除虚拟机构信息时发生异常。");
		}
	}

	/**
	 * <p>
	 * 存储虚拟机构信息
	 * </p>
	 * @param organization 机构信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/10 9:48
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void saveVirtualOrganization(SysOrganization organization) {
		replaceBlank(organization);
		verifyVirtualOrganization(organization, false);
		organization.setSuperiorId("VROOT");
		organization.setGradationCode("VROOT." + organization.getId() + ".");
		if (!save(organization)) {
			throw new BaseException("新增虚拟机构时发生异常。");
		}
	}

	/**
	 * <p>
	 * 更新虚拟机构信息
	 * </p>
	 * @param organization 机构信息对象
	 * @param
	 * @param
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/10 9:59
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void updateVirtualOrganization(SysOrganization organization) {
		replaceBlank(organization);
		verifyVirtualOrganization(organization, true);
		SysOrganization tempOrganization = getById(organization.getId());
		tempOrganization.setAbbreviation(organization.getAbbreviation());
		tempOrganization.setCode(organization.getCode());
		tempOrganization.setName(organization.getName());
		if (!saveOrUpdate(tempOrganization)) {
			throw new BaseException("更新虚拟机构：" + organization.getName() + " 时发生异常。");
		}
	}

	/**
	 * <p>
	 * 删除虚拟机构时校验机构信息状态
	 * </p>
	 * @param ids 机构id值集合
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/10 14:53
	 **/
	public void verifyOrganizationStatus(List<String> ids) {
		if (CommonUtil.isEmptyList(ids)) {
			throw new BaseException("数据为空。");
		}
		// 校验选中的是否都为虚拟机构
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("org_nm as name").in("org_id", ids)
				.and(i -> i.ne("spr_org_id", "VROOT").or().isNull("spr_org_id"));
		List<SysOrganization> organizations = list(queryWrapper);
		List<String> names;
		if (!CommonUtil.isEmptyList(organizations)) {
			names = organizations.stream().map(organization -> organization.getName()).distinct()
					.collect(Collectors.toList());
			throw new BaseException(names.toString() + "不是虚拟机构。");
		}
		// 校验机构是否已被报送机构树引用
		QueryWrapper<SysOrgSbmtdTree> submittedQueryWrapper = new QueryWrapper<>();
		submittedQueryWrapper.select("org_nm as orgNm").in("org_id", ids);
		List<SysOrgSbmtdTree> submittedOrganizations =
				this.sysSubmittedOrganizationService.list(submittedQueryWrapper);
		if (!CommonUtil.isEmptyList(submittedOrganizations)) {
			names = submittedOrganizations.stream().map(orgSbmtdTree -> orgSbmtdTree.getOrgNm()).distinct()
					.collect(Collectors.toList());
			throw new BaseException(names.toString() + "已被报送机构树引用。");
		}
	}

	/**
	 * <p>
	 * 校验虚拟机构信息值
	 * </p>
	 * @param organization 虚拟机构信息对象
	 * @param isUpdate 是否更新操作。更新时排除当前机构信息。
	 * @return com.scrcu.common.base.bo.VerifyResult
	 * @author wuyu
	 * @date 2019/10/10 8:55
	 **/
	private void verifyVirtualOrganization(SysOrganization organization, boolean isUpdate) {
		if (organization == null) {
			throw new BaseException("虚拟机构数据为空。");
		}
		if (!isQualifiedVirtualId(organization.getId())) {
			throw new BaseException("虚拟机构ID由英文字符加数字组成。");
		}
		if (!isQualifiedCode(organization.getCode())) {
			throw new BaseException("虚拟机构编码由英文字符或数字组成。");
		}
		if (!isQualifiedAbbreviation(organization.getAbbreviation())) {
			throw new BaseException("虚拟机构简称含有非法字符。");
		}
		if (!isQualifiedName(organization.getName())) {
			throw new BaseException("虚拟机构名称含有非法字符。");
		}
		if (!isUpdate && hasOrganizationId(organization.getId())) {
			throw new BaseException("机构ID已存在。");
		}
		if (hasOrganizationCode(organization, isUpdate)) {
			throw new BaseException("机构编码已存在。");
		}
		if (hasOrganizationAbbreviation(organization, isUpdate)) {
			throw new BaseException("机构简称已存在。");
		}
		if (hasOrganizationName(organization, isUpdate)) {
			throw new BaseException("机构名称已存在。");
		}
	}

	/**
	 * <p>
	 * 机构id值是否重复
	 * </p>
	 * @param id 机构ID值
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/10 9:28
	 **/
	private boolean hasOrganizationId(String id) {
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("org_id as id").eq("org_id", id);
		List<SysOrganization> organizations = list(queryWrapper);
		return !CommonUtil.isEmptyList(organizations);
	}

	/**
	 * <p>
	 * 机构编码值是否重复
	 * </p>
	 * @param organization 机构信息对象
	 * @param isUpdate 是否更新操作。更新时排除当前机构信息。
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/10 9:29
	 **/
	private boolean hasOrganizationCode(SysOrganization organization, boolean isUpdate) {
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("org_id as id").eq("org_cd", organization.getCode())
				.ne(isUpdate, "org_id", organization.getId());
		List<SysOrganization> organizations = list(queryWrapper);
		return !CommonUtil.isEmptyList(organizations);
	}

	/**
	 * <p>
	 * 机构名称是否重复
	 * </p>
	 * @param organization 机构信息对象
	 * @param isUpdate 是否更新操作。更新时排除当前机构信息。
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/10 9:30
	 **/
	private boolean hasOrganizationName(SysOrganization organization, boolean isUpdate) {
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("org_id as id").eq("org_nm", organization.getName())
				.ne(isUpdate, "org_id", organization.getId());
		List<SysOrganization> organizations = list(queryWrapper);
		return !CommonUtil.isEmptyList(organizations);
	}

	/**
	 * <p>
	 * 机构简称是否重复
	 * </p>
	 * @param organization 机构信息对象
	 * @param isUpdate 是否更新操作。更新时排除当前机构信息。
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/10 9:30
	 **/
	private boolean hasOrganizationAbbreviation(SysOrganization organization, boolean isUpdate) {
		QueryWrapper<SysOrganization> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("org_id as id").eq("org_abbr", organization.getAbbreviation())
				.ne(isUpdate, "org_id", organization.getId());
		List<SysOrganization> organizations = list(queryWrapper);
		return !CommonUtil.isEmptyList(organizations);
	}

	/**
	 * <p>
	 * 虚拟机构id值是否合格
	 * </p>
	 * @param id 机构id
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/9 17:39
	 **/
	private boolean isQualifiedVirtualId(String id) {
		String regex = "^[V]([a-zA-Z0-9]{1,19})$";
		return Pattern.matches(regex, id);
	}

	/**
	 * <p>
	 * 机构编码是否合格
	 * </p>
	 * @param code 机构编码
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/9 17:44
	 **/
	private boolean isQualifiedCode(String code) {
		String regex = "^[a-zA-Z0-9]{1,4}$";
		return Pattern.matches(regex, code);
	}

	/**
	 * <p>
	 * 是否合格机构名称
	 * </p>
	 * @param name 机构名称值
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/10 8:44
	 **/
	private boolean isQualifiedName(String name) {
		String regex = "^[\u4E00-\u9FA5A-Za-z0-9]{1,150}$";
		return Pattern.matches(regex, name);
	}

	/**
	 * <p>
	 * 是否合格机构简称
	 * </p>
	 * @param abbreviation 机构简称值
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/10 8:45
	 **/
	private boolean isQualifiedAbbreviation(String abbreviation) {
		String regex = "^[\u4E00-\u9FA5A-Za-z0-9]{1,50}$";
		return Pattern.matches(regex, abbreviation);
	}

	/**
	 * <p>
	 * 去除空格
	 * </p>
	 * @param organization 机构信息对象
	 * @return void
	 * @author wuyu
	 * @date 2019/10/9 17:39
	 **/
	private void replaceBlank(SysOrganization organization) {
		if (organization == null) {
			return;
		}
		organization.setId(organization.getId().replace(" ", ""));
		organization.setName(organization.getName().replace(" ", ""));
		organization.setCode(organization.getCode().replace(" ", ""));
		organization.setAbbreviation(organization.getAbbreviation().replace(" ", ""));
	}

	/**
	 * 描述： 功能资源表根据父节点进行分组封装进map集合
	 * @param
	 * @return
	 * @创建人： jiyuanbo
	 * @创建时间： 2019/10/21 21:39
	 */
	private static Map<String, List<SysOrganization>> groupByObjectKey(List<SysOrganization> sysOrgList) {
		Map<String, List<SysOrganization>> map = new HashMap<>();
		//机构缓存方式一：分级存储
		for (SysOrganization sysOrganization : sysOrgList) {
			if(!map.containsKey(sysOrganization.getSuperiorId())) {
				map.put(sysOrganization.getSuperiorId(), new ArrayList<>());
			}
			List<SysOrganization> tempList = map.get(sysOrganization.getSuperiorId());
			tempList.add(sysOrganization);
		}
		//机构缓存方式二：不分级存储，存储一个完整的机构集合
		map.put("ALL_ORG_CACHE",sysOrgList);
		return map;
	}

	@Override
	public Map<String, List<SysOrganization>> getAllSysOrg() throws Exception {
		Map<String, List<SysOrganization>> map;
		try {
			List<SysOrganization> list = this.baseMapper.selectList(new QueryWrapper<>());
			map = groupByObjectKey(list);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return map;
	}

	@Override
	public List<SysOrganization> getSysOrgInfoFromUser(SysUser user) {
		List<SysOrganization> list = this.baseMapper.getSysOrgInfoFromUser(user);
		return list;
	}

	/**
	 * <p>
	 * 设置日志详情
	 * </p>
	 * @param log 日志信息对象
	 * @param text 日志描述
	 * @param value 日志操作类型代码值
	 * @param objId 操作对象id
	 * @return void
	 * @author wuyu
	 * @date 2019/10/28 8:30
	 **/
	private void setLog(SysOperlog log, String text, String value, String objId) {
		this.logger.info("操作人OA号： " + log.getOperator() + "; " + text + "，机构ID：" + objId);
		log.setObjId(objId);
		log.setOperTyp(value);
		log.setOperation(text);
	}

	/**
	 * <p>
	 * 将机构信息表中的字典代码id转换为字典代码名称
	 * </p>
	 * @param organization 机构信息对象
	 * @return void
	 * @author wuyu
	 * @date 2019/10/31 16:25
	 **/
	private void setDictionaryCodeName(SysOrganization organization) {
		organization.setStatus(CommonUtil.dictionaryCodeName("SYS03", organization.getStatus()));
		organization.setChanceFlag(CommonUtil.dictionaryCodeName("SYS04", organization.getChanceFlag()));
		organization.setOrgSumVrtlFlg(CommonUtil.dictionaryCodeName("SYS02", organization.getOrgSumVrtlFlg()));
		organization.setDept(CommonUtil.dictionaryCodeName("SYS02", organization.getDept()));
	}

	/**
	 * 描述：查询所有的机构
	 * @return
	 * @author pengjuntao
	 * @date 2019/11/15
	 */
	public String listAllSysOrgInfoWithoutLvl(String id){
		List<SysOrganization> list = EhcacheUtil.getSysOrgInfoByCache("ALL_ORG_CACHE");
		List<TreeNode> nodeList = TreeNodeUtil.fillSysOrgInfoTree(list);
		return JSON.toJSONString(nodeList);
	}


	@Override
	public SysOrganization getSysOrgInfoByOrgCd(String org_cd) {
		SysOrganization sysOrganization = null;
		try{
			sysOrganization = this.baseMapper.getSysOrgInfoByOrgCd(org_cd);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return sysOrganization;
	}


	@Override
	public List<SysDictryCd> getTreeTypeFromDictryCd(SysDictryCd dic){
		List<SysDictryCd> list = null;
		try{
			list = this.baseMapper.getTreeTypeFromDictryCd(dic);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return list;
	}

	@Override
	public List<SysOrganization> getSysOrgInfoFromSbmtedTree(SysOrgSbmtdTree orgSbmtdTree) {
		List<SysOrganization> list = null;
		try{
			list = this.baseMapper.getSysOrgInfoFromSbmtedTree(orgSbmtdTree);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return list;
	}

	@Override
	public List<SysOrgSbmtdTree> getSysOrgSbmtdTree(SysOrgSbmtdTree sbmtdTree) {
		List<SysOrgSbmtdTree> list = null;
		try{
			list = this.baseMapper.getSysOrgSbmtdTree(sbmtdTree);
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return list;
	}


}
