package com.scrcu.common.utils;

import com.scrcu.sys.entity.SysOrganization;

import java.util.List;
import java.util.Map;

/**
 * @Author pengjuntao
 * @Date 2019/10/17 17:43
 * @Version 1.0
 * @function 临时存储数据工具类
 */
public class TempCodeUtils {

    private static TempCodeUtils tempCodeUtils;
    private Map<String,SysOrganization> orgMap;
    private List<SysOrganization> orgList;
    private List<SysOrganization> orgList01;
    private Map<String,List> sysGroupRecoursMap;//用于临时存储用户组新增时用户组的分配资源
    //私有构造
    private TempCodeUtils(){}

    public static TempCodeUtils getInstance(){
        if (tempCodeUtils == null){
            tempCodeUtils = new TempCodeUtils();
        }
        return tempCodeUtils;
    }

    public List<SysOrganization> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<SysOrganization> orgList) {
        this.orgList = orgList;
    }

    public Map<String, SysOrganization> getOrgMap() {
        return orgMap;
    }

    public void setOrgMap(Map<String, SysOrganization> orgMap) {
        this.orgMap = orgMap;
    }

    public List<SysOrganization> getOrgList01() {
        return orgList01;
    }

    public void setOrgList01(List<SysOrganization> orgList01) {
        this.orgList01 = orgList01;
    }

    public Map<String, List> getSysGroupRecoursMap() {
        return sysGroupRecoursMap;
    }

    public void setSysGroupRecoursMap(Map<String, List> sysGroupRecoursMap) {
        this.sysGroupRecoursMap = sysGroupRecoursMap;
    }

}
