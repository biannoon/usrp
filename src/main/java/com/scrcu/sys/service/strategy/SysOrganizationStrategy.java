package com.scrcu.sys.service.strategy;

import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.sys.entity.SysOrganization;

/**
 * <p>
 * 更新机构单个字段信息策略模式类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/12 16:34
 **/
public interface SysOrganizationStrategy {

	/**
	 * <p>
	 * 获取字段中文名
	 * </p>
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/16 8:43
	 **/
	String getFieldCnName();

	/**
	 * <p>
	 * 获取字段英文名
	 * </p>
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/16 8:43
	 **/
	String getFieldEnName();

	/**
	 * <p>
	 * 获取存储更新操作日志时所需要的操作类型代码
	 * </p>
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/18 8:56
	 **/
	String getUpdateOperationCode();

	/**
	 * <p>
	 * 获取字段值
	 * </p>
	 * @param organization 机构信息对象
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/16 8:43
	 **/
	String getFieldValue(SysOrganization organization);

	/**
	 * <p>
	 * 校验字段值的字符长度
	 * </p>
	 * @param value 字段值
	 * @return com.scrcu.common.base.bo.VerifyResult
	 * @author wuyu
	 * @date 2019/9/16 8:44
	 **/
	VerifyResult verifyFieldValueLength(String value);

	/**
	 * <p>
	 * 校验字段值的字符长度
	 * </p>
	 * @param value 字段值
	 * @param length 字段值规定长度
	 * @return com.scrcu.common.base.bo.VerifyResult
	 * @author wuyu
	 * @date 2019/9/17 19:32
	 **/
	default VerifyResult verifyFieldValueLength(String value, int length) {
		VerifyResult result = new VerifyResult();
		if (value == null || value.length() <= length) {
			result.setVerifyQualified(true);
		} else {
			result.setVerifyQualified(false);
			result.setInformation(getFieldCnName() + "字符长度不可超过" + length + "。");
		}
		return result;
	}
}
