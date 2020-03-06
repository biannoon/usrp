package com.scrcu.sys.entity.dto;

/**
 * <p>
 * 机构字段更新参数传输对象
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/13 1:23
 **/
public class SysOrganizationFieldDto {

	/**
	 * 机构ID
	 **/
	private String id;
	/**
	 * 机构更新字段的值
	 **/
	private String value;
	/**
	 * 调用具体策略对象所对应的key值
	 **/
	private String key;
	/**
	 * URL请求IP
	 **/
	private String ip;
	/**
	 * 操作人userId
	 **/
	private String userId;
	/**
	 * 字段值的字符长度校验结果
	 **/
	private boolean verifyQualified;
	/**
	 * 字段值的字符长度校验结果信息
	 **/
	private String verifyInformation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isVerifyQualified() {
		return verifyQualified;
	}

	public void setVerifyQualified(boolean verifyQualified) {
		this.verifyQualified = verifyQualified;
	}

	public String getVerifyInformation() {
		return verifyInformation;
	}

	public void setVerifyInformation(String verifyInformation) {
		this.verifyInformation = verifyInformation;
	}
}
