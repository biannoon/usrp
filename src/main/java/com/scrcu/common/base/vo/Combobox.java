package com.scrcu.common.base.vo;

import com.scrcu.apm.entity.ReportSqlMetadataDefined;
import com.scrcu.sys.entity.SysDatabase;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysGroup;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysUser;

import java.util.List;

/**
 * <p>
 * easy ui combobox 控件封装类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/10/18 14:39
 **/
public class Combobox {

	private String value;
	private String text;
	private boolean checked;

	public Combobox() {}

	public Combobox(SysDictryCd dictryCd) {
		this.value = dictryCd.getDictryId();
		this.text = dictryCd.getDictryNm();
	}

	public Combobox(SysDatabase database) {
		this.value = database.getId();
		this.text = database.getDsNm();
	}

	public Combobox(ReportSqlMetadataDefined metadata) {
		this.value = metadata.getFieldEnName();
		this.text = metadata.getFieldCnName();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
