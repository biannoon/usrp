package com.scrcu.common.utils;

import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.service.impl.SysDictryCdServiceImpl;

/**
 * @Author pengjuntao
 * @Date 2019/11/19 8:21
 * @Version 1.0
 * 使用组件的文本框回显数据时，进行的码值转换
 **/
public class ExchangeUtil {

    /**
     * 描述：通过字典代码，获取字典中文名称
     * @param dicCode 字典代码
     * @param dicType 字典类型(即所属字典)
     * @return
     */
    public static String exchangeDic(String dicCode,String dicType){
        return EhcacheUtil.getSingleSysDictryCdByCache(dicCode,dicType).getDictryNm();
    }
    /**
     * 描述：通过字典代码，获取字典中文名称(用于父级字典为root的情况)
     * @param dicCode 字典代码
     * @return
     */
    public static String exchangeDic(String dicCode){
        String result = "";
        if (!CommonUtil.isEmpty(dicCode)){
            SysDictryCdServiceImpl sysDictryCdService =
                    (SysDictryCdServiceImpl) SpringUtil.getBean("sysDictryCdServiceImpl");
            SysDictryCd sysDictryCd = new SysDictryCd();
            sysDictryCd.setDictryId(dicCode);
            sysDictryCd.setPareDictryId("root");
            result = sysDictryCdService.getByKeyword(sysDictryCd).getDictryNm();
        }
        return result;
    }

}
