package com.scrcu.sys.service.strategy.impl;

import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.service.strategy.SysOrganizationStrategy;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 更新企业征信机构代码字段业务逻辑实现类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/15 10:59
 **/
@Component("gnrlCrdtOrgNoField")
public class GnrlCrdtOrgNoField implements SysOrganizationStrategy {

	private static final String FIELD_CN_NAME = "企业征信机构代码";
	private static final String FIELD_EN_NAME = "gnrl_crdt_org_no";
	private static final String UPDATE_OPERATION_CODE = "SYS09ORG04";

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
		return organization == null ? "" : organization.getGnrlCrdtOrgNo();
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
