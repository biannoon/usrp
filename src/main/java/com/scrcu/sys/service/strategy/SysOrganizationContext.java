package com.scrcu.sys.service.strategy;

import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.common.exception.BaseException;
import com.scrcu.sys.entity.SysOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 更新机构单个字段信息环境角色类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/12 16:52
 **/
@Service
public class SysOrganizationContext {

	private final Map<String, SysOrganizationStrategy> strategyMap = new ConcurrentHashMap<>();

	/**
	 * <p>
	 * 注入所有实现了SysOrganizationStrategy接口的Bean
	 * </p>
	 * @param strategyMap
	 * @author wuyu
	 * @date 2019/9/12 17:13
	 **/
	@Autowired
	public SysOrganizationContext(Map<String, SysOrganizationStrategy> strategyMap) {
		this.strategyMap.clear();
		strategyMap.forEach((k, v)-> this.strategyMap.put(k, v));
	}

	/**
	 * <p>
	 * 获取字段中文名
	 * </p>
	 * @param key 机构字段业务逻辑类对应的key值
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/16 9:14
	 **/
	public String getFieldCnName(String key) {
		SysOrganizationStrategy strategy = this.strategyMap.get(key);
		return strategy.getFieldCnName();
	}

	/**
	 * <p>
	 * 获取字段英文名
	 * </p>
	 * @param key 机构字段业务逻辑类对应的key值
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/16 9:15
	 **/
	public String getFieldEnName(String key) {
		SysOrganizationStrategy strategy = this.strategyMap.get(key);
		return strategy.getFieldEnName();
	}

	/**
	 * <p>
	 * 获取机构字段值
	 * </p>
	 * @param key 机构字段业务逻辑类对应的key值
	 * @param organization 机构信息对象
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/14 21:35
	 **/
	public String getFieldValue(String key, SysOrganization organization) {
		SysOrganizationStrategy strategy = this.strategyMap.get(key);
		return strategy.getFieldValue(organization);
	}

	/**
	 * <p>
	 * 获取更新字段值时存储操作日志所需的操作类型代码
	 * </p>
	 * @param key 机构字段业务逻辑类对应的key值
	 * @return java.lang.String
	 * @author wuyu
	 * @date 2019/9/18 9:11
	 **/
	public String getUpdateOperationCode(String key) {
		SysOrganizationStrategy strategy = this.strategyMap.get(key);
		return strategy.getUpdateOperationCode();
	}

	/**
	 * <p>
	 * 校验字段值长度
	 * </p>
	 * @param key 机构字段业务逻辑类对应的key值
	 * @param value 机构字段值
	 * @return com.scrcu.common.base.bo.VerifyResult
	 * @author wuyu
	 * @date 2019/9/16 10:05
	 **/
	public void verifyFieldValueLength(String key, String value) {
		SysOrganizationStrategy strategy = this.strategyMap.get(key);
		VerifyResult result = strategy.verifyFieldValueLength(value);
		if (!result.isVerifyQualified()) {
			throw new BaseException(result.getInformation());
		}
	}
}
