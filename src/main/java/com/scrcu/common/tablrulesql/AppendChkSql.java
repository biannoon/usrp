package com.scrcu.common.tablrulesql;

import com.scrcu.apm.entity.DifChkRuleConf;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: TODO
 * @Author: xuyongjiang
 * @Date: 2019/12/3 16:38
 **/
public class AppendChkSql {
    public static String getDb2ChkSql(DifChkRuleConf difChkRuleConf) {
        StringBuffer sql = new StringBuffer();
        if (difChkRuleConf.getRuleCfgTyp().equals("DFI1501")) {
            sql.append(" select ");

        } else if (difChkRuleConf.getRuleCfgTyp().equals("DFI1502")) {
        }
        return sql.toString();
    }
}
