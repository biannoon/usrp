package com.scrcu.common.base.vo;

import com.scrcu.common.base.bo.VerifyResult;

/**
 * <p>
 * ajax请求反回信息对象
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:34
 **/
public class AjaxResult {
	/**
	 * 是否成功标志
	 **/
	private boolean success;
	/**
	 * 提示消息
	 **/
	private String message;
	/**
	 * 数据对象
	 **/
	private Object data;
	/**
	 * 操作类型
	 **/
	private String type;

	public AjaxResult() {}

	public AjaxResult(boolean success) {
		this.success = success;
	}

	public AjaxResult(boolean success, String message, Object data, String type) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.type = type;
	}

	public AjaxResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public AjaxResult(VerifyResult verifyResult) {
		this.success = verifyResult.isVerifyQualified();
		this.message = verifyResult.getInformation();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
