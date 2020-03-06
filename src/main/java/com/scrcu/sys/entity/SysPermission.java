package com.scrcu.sys.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： 权限信息实体对象
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/5 21:45
 */
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String perId;
    private String perNm;
    private String resTyp;
    private String url;
    private String permission;
    private String parentId;
    private String stus;
    private String crtTm;
    private String uptTm;
    private List<SysRole> roleList;

    public String getPerId() {
        return perId;
    }

    public void setPerId(String perId) {
        this.perId = perId;
    }

    public String getPerNm() {
        return perNm;
    }

    public void setPerNm(String perNm) {
        this.perNm = perNm;
    }

    public String getResTyp() {
        return resTyp;
    }

    public void setResTyp(String resTyp) {
        this.resTyp = resTyp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public String getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(String crtTm) {
        this.crtTm = crtTm;
    }

    public String getUptTm() {
        return uptTm;
    }

    public void setUptTm(String uptTm) {
        this.uptTm = uptTm;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}
