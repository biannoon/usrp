package com.scrcu.common.base.bo;

/**
 * <p>
 * 简单数据校验结果信息对象
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/17 10:31
 **/
public class VerifyResult {

	/**
	 * 校验是否合格
	 **/
	private boolean verifyQualified;
	/**
	 * 校验结果信息
	 **/
	private String information;

	public VerifyResult() {}

	public VerifyResult(boolean verifyQualified) {
		this.verifyQualified = verifyQualified;
	}

	public VerifyResult(boolean verifyQualified, String information) {
		this.verifyQualified = verifyQualified;
		this.information = information;
	}

	public boolean isVerifyQualified() {
		return verifyQualified;
	}

	public void setVerifyQualified(boolean verifyQualified) {
		this.verifyQualified = verifyQualified;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}
}
