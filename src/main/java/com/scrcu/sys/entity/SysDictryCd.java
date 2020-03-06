package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 描述： 字典代码实体类
 *
 * @创建人： hepengfei
 * @创建时间： 2019/9/5 21:45
 */
@TableName(value="sys_dictry_cd")
public class SysDictryCd {

	@TableField(value = "dictry_id")
	private String dictryId;            //字典代码ID  主键
	@TableField(value = "dictry_nm")
	private String dictryNm;         //代码名称
	@TableField(value = "pare_dictry_id")
	private String pareDictryId;     //上级代码ID
	@TableField(value = "blngto_dictry_id")
	private String blngtoDictryId;  //所属字典代码ID
	@TableField(value = "cd_typ")
	private String cdTyp;             //代码类型
	@TableField(value = "dictry_comnt")
	private String dictryComnt;      //字典说明

	public String getDictryId() {
		return dictryId;
	}

	public void setDictryId(String dictryId) {
		this.dictryId = dictryId;
	}

	public String getDictryNm() {
		return dictryNm;
	}

	public void setDictryNm(String dictryNm) {
		this.dictryNm = dictryNm;
	}

	public String getPareDictryId() {
		return pareDictryId;
	}

	public void setPareDictryId(String pareDictryId) {
		this.pareDictryId = pareDictryId;
	}

	public String getBlngtoDictryId() {
		return blngtoDictryId;
	}

	public void setBlngtoDictryId(String blngtoDictryId) {
		this.blngtoDictryId = blngtoDictryId;
	}

	public String getCdTyp() {
		return cdTyp;
	}

	public void setCdTyp(String cdTyp) {
		this.cdTyp = cdTyp;
	}

	public String getDictryComnt() {
		return dictryComnt;
	}

	public void setDictryComnt(String dictryComnt) {
		this.dictryComnt = dictryComnt;
	}

	@Override
	public String toString() {
		return "SysDictryCd{" + "dictryId='" + dictryId + '\'' + ", dictryNm='" + dictryNm + '\'' + ", pareDictryId='" + pareDictryId + '\'' + ", blngtoDictryId='" + blngtoDictryId + '\'' + ", cdTyp='" + cdTyp + '\'' + ", dictryComnt='" + dictryComnt + '\'' + '}';
	}
}
