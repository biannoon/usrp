package com.scrcu.sys.service.strategy.impl;

import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.service.strategy.SysOrganizationStrategy;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 更新 金融机构标识码 字段值业务逻辑实现类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/15 20:24
 **/
@Component("finOrgIndCdField")
public class FinOrgIndCdField implements SysOrganizationStrategy {

	private static final String FIELD_CN_NAME = "金融机构标识码";
	private static final String FIELD_EN_NAME = "fin_org_ind_cd";
	private static final String UPDATE_OPERATION_CODE = "SYS09ORG06";

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
		return organization == null ? "" : organization.getFinOrgIndCd();
	}

	@Override
	public String getUpdateOperationCode() {
		return this.UPDATE_OPERATION_CODE;
	}

	@Override
	public VerifyResult verifyFieldValueLength(String value) {
		return  this.verifyFieldValueLength(value, 30);
	}
}
