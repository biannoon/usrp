package com.scrcu.sys.service.strategy.impl;

import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.service.strategy.SysOrganizationStrategy;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 更新 人行支付行号 字段业务实现类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/12 16:40
 **/
@Component("paymtRwBnkNoField")
public class PaymtRwBnkNoField implements SysOrganizationStrategy {

	private static final String FIELD_CN_NAME = "人行支付行号";
	private static final String FIELD_EN_NAME = "paymt_rw_bnk_no";
	private static final String UPDATE_OPERATION_CODE = "SYS09ORG01";

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
		return organization == null ? "" : organization.getPaymtRwBnkNo();
	}

	@Override
	public String getUpdateOperationCode() {
		return this.UPDATE_OPERATION_CODE;
	}

	@Override
	public VerifyResult verifyFieldValueLength(String value) {
		return  this.verifyFieldValueLength(value, 14);
	}
}
