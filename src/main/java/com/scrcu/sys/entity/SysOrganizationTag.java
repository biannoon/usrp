package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 机构标签实体类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/10/11 9:48
 **/
@TableName(value = "sys_org_tag")
public class SysOrganizationTag {

	/**
	 * 机构ID
	 **/
	@TableField(value = "org_id")
	private String orgId;
	/**
	 * 标签类型
	 **/
	@TableField(value = "tag_typ")
	private String tagTyp;
	/**
	 * 标签值
	 **/
	@TableField(value = "tag_val")
	private String tagVal;

	@TableField(exist = false)
	private String tagName;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getTagTyp() {
		return tagTyp;
	}

	public void setTagTyp(String tagTyp) {
		this.tagTyp = tagTyp;
	}

	public String getTagVal() {
		return tagVal;
	}

	public void setTagVal(String tagVal) {
		this.tagVal = tagVal;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
