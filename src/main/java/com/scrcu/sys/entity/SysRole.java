package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.scrcu.common.utils.CommonUtil;

import java.io.Serializable;

/**
 * 描述： 角色信息实体对象
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 19:50
 */
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String roleId;
    private String roleNm;
    private String roleComnt;
    private String roleHrchCd;
    private String sys;
    private String crtDt;
    private String creatr;
    private String finlModfyDt;
    private String finlModifr;
    @TableField(exist = false)
    private boolean check;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleNm() {
        return roleNm;
    }

    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    public String getRoleComnt() {
        return roleComnt;
    }

    public void setRoleComnt(String roleComnt) {
        this.roleComnt = roleComnt;
    }

    public String getRoleHrchCd() {
        return roleHrchCd;
    }

    public void setRoleHrchCd(String roleHrchCd) {
        this.roleHrchCd = roleHrchCd;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = (CommonUtil.isEmpty(crtDt) ? null : crtDt);
    }

    public String getCreatr() {
        return creatr;
    }

    public void setCreatr(String creatr) {
        this.creatr = creatr;
    }

    public String getFinlModfyDt() {
        return finlModfyDt;
    }

    public void setFinlModfyDt(String finlModfyDt) {
        this.finlModfyDt = (CommonUtil.isEmpty(finlModfyDt) ? null : finlModfyDt);
    }

    public String getFinlModifr() {
        return finlModifr;
    }

    public void setFinlModifr(String finlModifr) {
        this.finlModifr = finlModifr;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "SysRole{" + "roleId='" + roleId + '\'' + ", roleNm='" + roleNm + '\'' + ", roleComnt='" + roleComnt + '\'' + ", roleHrchCd='" + roleHrchCd + '\'' + ", sys='" + sys + '\'' + ", crtDt='" + crtDt + '\'' + ", creatr='" + creatr + '\'' + ", finlModfyDt='" + finlModfyDt + '\'' + ", finlModifr='" + finlModifr + '\'' + ", check=" + check + '}';
    }
}
