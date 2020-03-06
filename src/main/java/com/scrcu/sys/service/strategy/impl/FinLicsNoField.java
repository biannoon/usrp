package com.scrcu.sys.service.strategy.impl;

import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.service.strategy.SysOrganizationStrategy;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 更新 金融许可证编码 字段业务实现类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/12 17:16
 **/
@Component("finLicsNoField")
public class FinLicsNoField implements SysOrganizationStrategy {

	private static final String FIELD_CN_NAME = "金融许可证编码";
	private static final String FIELD_EN_NAME = "fin_lics_no";
	private static final String UPDATE_OPERATION_CODE = "SYS09ORG02";

	@Override
	public String getFieldCnName() {
		return this.FIELD_CN_NAME;
	}

	@Override
	public String getFieldEnName() {
		return this.FIELD_EN_NAME;
	}

	@Override
	public String getFieldValue(SysOrganization organization) {
		return organization == null ? "" : organization.getFinLicsNo();
	}

	@Override
	public String getUpdateOperationCode() {
		return this.UPDATE_OPERATION_CODE;
	}

	@Override
	public VerifyResult verifyFieldValueLength(String value) {
		return  this.verifyFieldValueLength(value, 15);
	}
}
