package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @Author pengjuntao
 * @Date 2019/10/11 17:27
 * @Version 1.0
 * @function 报送机构树实体类
 */
@TableName(value="sys_org_sbmtd_tree")
public class SysOrgSbmtdTree {
    private String orgId;
    private String treeTyp;
    private String orgNm;
    private String sprOrgId;
    private String orgSeq;

    public SysOrgSbmtdTree() {}

    public SysOrgSbmtdTree(SysOrganization organization) {
        this.orgId = organization.getId();
        this.orgNm = organization.getName();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTreeTyp() {
        return treeTyp;
    }

    public void setTreeTyp(String treeTyp) {
        this.treeTyp = treeTyp;
    }

    public String getOrgNm() {
        return orgNm;
    }

    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    public String getSprOrgId() {
        return sprOrgId;
    }

    public void setSprOrgId(String sprOrgId) {
        this.sprOrgId = sprOrgId;
    }

    public String getOrgSeq() {
        return orgSeq;
    }

    public void setOrgSeq(String orgSeq) {
        this.orgSeq = orgSeq;
    }




}
