package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 机构信息DO
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:27
 **/
@TableName("sys_org_info")
public class SysOrganization {

	/**
	 * 机构id，主键。
	 **/
	@TableId(value = "org_id")
	private String id;
	/**
	 * 机构名称
	 **/
	@TableField(value = "org_nm")
	private String name;
	/**
	 * 简称（缩写）
	 **/
	@TableField(value = "org_abbr")
	private String abbreviation;
	/**
	 * 机构编码
	 **/
	@TableField(value = "org_cd")
	private String code;
	/**
	 * 上级机构id
	 **/
	@TableField(value = "spr_org_id")
	private String superiorId;
	/**
	 * 机构层级编码
	 **/
	@TableField(value = "org_grdtn_cd")
	private String gradationCode;
	/**
	 * <p>
	 * 是否汇总虚拟机构
	 * </p>
	 * SYS0201：是；<br/>
	 * SYS0202：否。
	 **/
	@TableField(value = "org_sum_vrtl_flg")
	private String orgSumVrtlFlg;
	/**
	 * 排序号。默认值：999
	 **/
	@TableField(value = "sort_num")
	private int sortNum;
	/**
	 * <p>
	 * 机构类型编码
	 * </p>
	 * 1：内部机构的一级部门 <br/>
	 * 2：内部机构的非一级部门 <br/>
	 * 3：内部机构分类 <br/>
	 * 4：独立机构 <br/>
	 * 5：外部机构分类
	 **/
	@TableField(value = "org_typ_cd")
	private String typeCode;
	/**
	 * <p>
	 * 是否上级机构的内部部门
	 * </p>
	 * SYS0201：是；<br/>
	 * SYS0202：否。
	 **/
	@TableField(value = "is_dept")
	private String dept;
	/**
	 * 电子邮箱
	 **/
	@TableField(value = "e_mail")
	private String eMail;
	/**
	 * 负责人名称
	 **/
	@TableField(value = "org_princ_nm")
	private String principalName;
	/**
	 * 地址
	 **/
	@TableField(value = "addr")
	private String address;
	/**
	 * 邮政编码
	 **/
	@TableField(value = "post_cd")
	private String postalCode;
	/**
	 * 办公电话
	 **/
	@TableField(value = "offi_tel_no")
	private String officeTelNo;
	/**
	 * 传真
	 **/
	@TableField(value = "fax")
	private String fax;
	/**
	 * <p>
	 * 状态
	 * </p>
	 * SYS0301：正常；<br/>
	 * SYS0302：注销。
	 **/
	@TableField(value = "org_stus")
	private String status;
	/**
	 * 最近修改时间(YYYY-MM-DD HH:MM:SS) TODO
	 **/
	@TableField(value = "recnt_modfy_tm")
	private String recntModfyTm;
	/**
	 * <p>变动标识</p>
	 * SYS0400：新增 <br/>
	 * SYS0401：机构信息变动 <br/>
	 * SYS0402：被注销 <br/>
	 * SYS0403：机构归属关系变动
	 **/
	@TableField(value = "chg_flg")
	private String chanceFlag;
	/**
	 * 人行支付行号
	 **/
	@TableField(value = "paymt_rw_bnk_no")
	private String paymtRwBnkNo;
	/**
	 * 金融许可证编码
	 **/
	@TableField(value = "fin_lics_no")
	private String finLicsNo;
	/**
	 * 个人征信机构代码
	 **/
	@TableField(value = "ntrlpsn_crdt_org_no")
	private String ntrlpsnCrdtOrgNo;
	/**
	 * 企业征信机构代码
	 **/
	@TableField(value = "gnrl_crdt_org_no")
	private String gnrlCrdtOrgNo;
	/**
	 * 金融机构代码
	 **/
	@TableField(value = "fin_org_cd")
	private String finOrgCd;
	/**
	 * 金融机构标识码
	 **/
	@TableField("fin_org_ind_cd")
	private String finOrgIndCd;
	/**
	 * 登记银行代码
	 **/
	@TableField(value = "rgst_bnk_no")
	private String rgstBnkNo;
	/**
	 * 报送机构类型代码
	 **/
	@TableField(value = "bnk_typ_cd")
	private String bnkTypCd;
	/**
	 * 地区代码
	 **/
	@TableField(value = "rgn_cd")
	private String rgnCd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSuperiorId() {
		return superiorId;
	}

	public void setSuperiorId(String superiorId) {
		this.superiorId = superiorId;
	}

	public String getGradationCode() {
		return gradationCode;
	}

	public void setGradationCode(String gradationCode) {
		this.gradationCode = gradationCode;
	}

	public String getOrgSumVrtlFlg() {
		return orgSumVrtlFlg;
	}

	public void setOrgSumVrtlFlg(String orgSumVrtlFlg) {
		this.orgSumVrtlFlg = orgSumVrtlFlg;
	}

	public int getSortNum() {
		return sortNum;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getOfficeTelNo() {
		return officeTelNo;
	}

	public void setOfficeTelNo(String officeTelNo) {
		this.officeTelNo = officeTelNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecntModfyTm() {
		return recntModfyTm;
	}

	public void setRecntModfyTm(String recntModfyTm) {
		this.recntModfyTm = recntModfyTm;
	}

	public String getChanceFlag() {
		return chanceFlag;
	}

	public void setChanceFlag(String chanceFlag) {
		this.chanceFlag = chanceFlag;
	}

	public String getPaymtRwBnkNo() {
		return paymtRwBnkNo;
	}

	public void setPaymtRwBnkNo(String paymtRwBnkNo) {
		this.paymtRwBnkNo = paymtRwBnkNo;
	}

	public String getFinLicsNo() {
		return finLicsNo;
	}

	public void setFinLicsNo(String finLicsNo) {
		this.finLicsNo = finLicsNo;
	}

	public String getNtrlpsnCrdtOrgNo() {
		return ntrlpsnCrdtOrgNo;
	}

	public void setNtrlpsnCrdtOrgNo(String ntrlpsnCrdtOrgNo) {
		this.ntrlpsnCrdtOrgNo = ntrlpsnCrdtOrgNo;
	}

	public String getGnrlCrdtOrgNo() {
		return gnrlCrdtOrgNo;
	}

	public void setGnrlCrdtOrgNo(String gnrlCrdtOrgNo) {
		this.gnrlCrdtOrgNo = gnrlCrdtOrgNo;
	}

	public String getFinOrgCd() {
		return finOrgCd;
	}

	public void setFinOrgCd(String finOrgCd) {
		this.finOrgCd = finOrgCd;
	}

	public String getFinOrgIndCd() {
		return finOrgIndCd;
	}

	public void setFinOrgIndCd(String finOrgIndCd) {
		this.finOrgIndCd = finOrgIndCd;
	}

	public String getRgstBnkNo() {
		return rgstBnkNo;
	}

	public void setRgstBnkNo(String rgstBnkNo) {
		this.rgstBnkNo = rgstBnkNo;
	}

	public String getBnkTypCd() {
		return bnkTypCd;
	}

	public void setBnkTypCd(String bnkTypCd) {
		this.bnkTypCd = bnkTypCd;
	}

	public String getRgnCd() {
		return rgnCd;
	}

	public void setRgnCd(String rgnCd) {
		this.rgnCd = rgnCd;
	}

	@Override
	public String toString() {
		return "SysOrganization{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", abbreviation='" + abbreviation + '\'' +
				", code='" + code + '\'' +
				", superiorId='" + superiorId + '\'' +
				", gradationCode='" + gradationCode + '\'' +
				", orgSumVrtlFlg='" + orgSumVrtlFlg + '\'' +
				", sortNum=" + sortNum +
				", typeCode='" + typeCode + '\'' +
				", dept='" + dept + '\'' +
				", eMail='" + eMail + '\'' +
				", principalName='" + principalName + '\'' +
				", address='" + address + '\'' +
				", postalCode='" + postalCode + '\'' +
				", officeTelNo='" + officeTelNo + '\'' +
				", fax='" + fax + '\'' +
				", status='" + status + '\'' +
				", recntModfyTm='" + recntModfyTm + '\'' +
				", chanceFlag='" + chanceFlag + '\'' +
				", paymtRwBnkNo='" + paymtRwBnkNo + '\'' +
				", finLicsNo='" + finLicsNo + '\'' +
				", ntrlpsnCrdtOrgNo='" + ntrlpsnCrdtOrgNo + '\'' +
				", gnrlCrdtOrgNo='" + gnrlCrdtOrgNo + '\'' +
				", finOrgCd='" + finOrgCd + '\'' +
				", finOrgIndCd='" + finOrgIndCd + '\'' +
				", rgstBnkNo='" + rgstBnkNo + '\'' +
				", bnkTypCd='" + bnkTypCd + '\'' +
				", rgnCd='" + rgnCd + '\'' +
				'}';
	}
}
