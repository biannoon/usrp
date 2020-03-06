package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysOrganizationTag;
import com.scrcu.sys.mapper.SysOrganizationTagMapper;
import com.scrcu.sys.service.SysOrganizationTagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 机构标签业务逻辑实现类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/10/11 9:57
 **/
@Service
public class SysOrganizationTagServiceImpl extends ServiceImpl<SysOrganizationTagMapper, SysOrganizationTag>
		implements SysOrganizationTagService {

	@Autowired
	private SysDictryCdServiceImpl sysDictryCdService;

	/**
	 * <p>
	 * 分页查询机构标签信息
	 * </p>
	 * @param organizationTag 机构标签信息对象
	 * @param pageParameters 分页信息对象
	 * @return com.scrcu.common.base.vo.DataGrid
	 * @author wuyu
	 * @date 2019/10/11 10:44
	 **/
	public DataGrid listOrganizationTag(SysOrganizationTag organizationTag, PageParameters pageParameters) {
		Page<SysOrganizationTag> page = new Page<>(pageParameters.getPage(), pageParameters.getRows());
		QueryWrapper<SysOrganizationTag> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(!StringUtils.isEmpty(organizationTag.getOrgId()), "org_id", organizationTag.getOrgId())
				.orderByAsc("org_id");
		IPage<SysOrganizationTag> iPage = page(page, queryWrapper);
		Map<String, String> type = CommonUtil.dictionaryCodes("SYS12");
		iPage.getRecords().stream().forEach(tag -> tag.setTagName(type.get(tag.getTagTyp())));
		return new DataGrid(iPage.getTotal(), iPage.getRecords());
	}

	/**
	 * <p>
	 * 获取单个机构标签信息
	 * </p>
	 * @param organizationTag 机构标签信息对象，传递查询条件信息
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/11 15:16
	 **/
	public AjaxResult getOrganizationTag(SysOrganizationTag organizationTag) {
		if (organizationTag == null) {
			return new AjaxResult(false, "查询条件为空，查询失败。");
		}
		SysOrganizationTag data = getOneByKeyword(organizationTag);
		SysDictryCd queryDictionary = new SysDictryCd();
		queryDictionary.setDictryId(organizationTag.getTagTyp());
		queryDictionary.setPareDictryId("SYS12");
		SysDictryCd dictryCd = this.sysDictryCdService.getByKeyword(queryDictionary);
		return new AjaxResult(true, "", data, dictryCd.getDictryNm());
	}

	/**
	 * <p>
	 * 存储机构标签信息
	 * </p>
	 * @param organizationTag 机构标签信息对象
	 * @return void
	 * @author wuyu
	 * @date 2019/10/11 15:41
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void saveOrganizationTag(SysOrganizationTag organizationTag) {
		if (!save(organizationTag)) {
			throw new BaseException("存储机构标签信息时发生异常。");
		}
	}

	/**
	 * <p>
	 * 更新机构标签信息
	 * </p>
	 * @param organizationTag 机构标签信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 10:33
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void updateOrganizationTag(SysOrganizationTag organizationTag) {
		UpdateWrapper<SysOrganizationTag> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("tag_val", organizationTag.getTagVal()).eq("org_id", organizationTag.getOrgId())
				.eq("tag_typ", organizationTag.getTagTyp());
		if (!update(updateWrapper)) {
			throw new BaseException("存储机构标签信息时发生异常。");
		}
	}

	/**
	 * <p>
	 * 删除机构标签信息
	 * </p>
	 * @param organizationTag 机构标签信息对象
	 * @return com.scrcu.common.base.vo.AjaxResult
	 * @author wuyu
	 * @date 2019/10/12 10:33
	 **/
	@Transactional(rollbackFor = Exception.class)
	public void deleteOrganizationTag(SysOrganizationTag organizationTag) {
		QueryWrapper<SysOrganizationTag> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("org_id", organizationTag.getOrgId())
				.eq("tag_typ", organizationTag.getTagTyp());
		if (!remove(queryWrapper)) {
			throw new BaseException("删除机构标签信息时发生异常。");
		}
	}

	/**
	 * <p>
	 * 新增机构标签时检验机构标签值信息
	 * </p>
	 * @param organizationTag 机构标签值信息对象
	 * @return com.scrcu.common.base.bo.VerifyResult
	 * @author wuyu
	 * @date 2019/11/20 16:01
	 **/
	public VerifyResult verifyTagValueForSave(SysOrganizationTag organizationTag) {
		VerifyResult verifyResult = verifyTagValue(organizationTag);
		if (!verifyResult.isVerifyQualified())
			return verifyResult;
		SysOrganizationTag tempTag = getOneByKeyword(organizationTag);
		if (tempTag != null) {
			verifyResult.setVerifyQualified(false);
			verifyResult.setInformation("存储机构标签信息失败， 当前机构已存在该标签信息。");
		}
		return verifyResult;
	}

	/**
	 * <p>
	 * 更新机构标签时检验机构标签值信息
	 * </p>
	 * @param organizationTag 机构标签值信息对象
	 * @return com.scrcu.common.base.bo.VerifyResult
	 * @author wuyu
	 * @date 2019/11/20 16:01
	 **/
	public VerifyResult verifyTagValue(SysOrganizationTag organizationTag) {
		if (organizationTag == null) {
			return new VerifyResult(false, "存储机构标签信息失败， 机构标签信息为空。");
		}
		if (!isQualifiedTagValue(organizationTag.getTagVal())) {
			return new VerifyResult(false, "存储机构标签信息失败， 标签值含有非法字符或过长。");
		}
		return new VerifyResult(true);
	}

	/**
	 * <p>
	 * 获取单个机构标签信息对象
	 * </p>
	 * @param organizationTag 机构标签信息对象
	 * @return com.scrcu.sys.entity.SysOrganizationTag
	 * @author wuyu
	 * @date 2019/10/11 16:19
	 **/
	private SysOrganizationTag getOneByKeyword(SysOrganizationTag organizationTag) {
		if (organizationTag == null) {
			return null;
		}
		QueryWrapper<SysOrganizationTag> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(!StringUtils.isEmpty(organizationTag.getOrgId()), "org_id", organizationTag.getOrgId())
				.eq(!StringUtils.isEmpty(organizationTag.getTagTyp()), "tag_typ", organizationTag.getTagTyp());
		return getOne(queryWrapper);
	}

	/**
	 * <p>
	 * 校验机构标签值是否合格
	 * </p>
	 * @param value 机构标签值
	 * @return boolean
	 * @author wuyu
	 * @date 2019/10/11 16:20
	 **/
	private boolean isQualifiedTagValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		String regex = "^[\u4E00-\u9FA5A-Za-z0-9]{1,50}$";
		return Pattern.matches(regex, value);
	}
}
