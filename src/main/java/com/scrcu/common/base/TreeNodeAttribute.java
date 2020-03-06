package com.scrcu.common.base;

public class TreeNodeAttribute {

    //菜单功能自定义属性
    private String funcType;
    private String pareFuncId;
    private String url;
    private String finlModifr;
    private String finlModfyDt;
    private String subsystemId;//菜单节点的子系统属性

    //机构信息自定义属性
    private String spr_org_id;//父级节点
    private String org_cd;//机构编码

    private String canCheck;//用来判断该节点是否可以选择

    //任务组自定义属性
    public String getCanCheck() {
        return canCheck;
    }

    public void setCanCheck(String canCheck) {
        this.canCheck = canCheck;
    }
    public String getSpr_org_id() {
        return spr_org_id;
    }

    public void setSpr_org_id(String spr_org_id) {
        this.spr_org_id = spr_org_id;
    }

    public String getFuncType() {
        return funcType;
    }

    public void setFuncType(String funcType) {
        this.funcType = funcType;
    }

    public String getPareFuncId() {
        return pareFuncId;
    }

    public void setPareFuncId(String pareFuncId) {
        this.pareFuncId = pareFuncId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFinlModifr() {
        return finlModifr;
    }

    public void setFinlModifr(String finlModifr) {
        this.finlModifr = finlModifr;
    }

    public String getFinlModfyDt() {
        return finlModfyDt;
    }

    public void setFinlModfyDt(String finlModfyDt) {
        this.finlModfyDt = finlModfyDt;
    }

    public String getOrg_cd() {
        return org_cd;
    }

    public void setOrg_cd(String org_cd) {
        this.org_cd = org_cd;
    }

    public String getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }



}
