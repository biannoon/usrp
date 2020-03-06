package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 描述： 查看引用返回实体对象
 *
 * @创建人： hepengfei
 * @创建时间： 2019/9/5 21:45
 */
public class SysDictryCdResult {

	private String dictryId;             //字典代码id
	private String tableEnNm;            //接口英文名
	private String tableCnNm;         //接口中文名
	private String dataSorcId;      //所属数据源ID
	private String fieldEnNm;      //字段英文名
	private String fieldCnNm;             //字段中文名


	public String getDictryId() {
		return dictryId;
	}

	public void setDictryId(String dictryId) {
		this.dictryId = dictryId;
	}

	public String getTableEnNm() {
		return tableEnNm;
	}

	public void setTableEnNm(String tableEnNm) {
		this.tableEnNm = tableEnNm;
	}

	public String getTableCnNm() {
		return tableCnNm;
	}

	public void setTableCnNm(String tableCnNm) {
		this.tableCnNm = tableCnNm;
	}

	public String getDataSorcId() {
		return dataSorcId;
	}

	public void setDataSorcId(String dataSorcId) {
		this.dataSorcId = dataSorcId;
	}

	public String getFieldEnNm() {
		return fieldEnNm;
	}

	public void setFieldEnNm(String fieldEnNm) {
		this.fieldEnNm = fieldEnNm;
	}

	public String getFieldCnNm() {
		return fieldCnNm;
	}

	public void setFieldCnNm(String fieldCnNm) {
		this.fieldCnNm = fieldCnNm;
	}
}
