package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.scrcu.common.utils.CommonUtil;

import java.io.Serializable;

/**
 * 描述： 子系统信息实体对象
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/24 14:45
 */
public class SysSubsystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String subsystemId;
    private String subsystemNm;
    private String subsystemComnt;
    private String url;
    private String iconUrl;
    private String crtDt;
    private String creatr;
    private String finlModfyDt;
    private String finlModifr;
    @TableField(exist = false)
    private boolean check;

    public String getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }

    public String getSubsystemNm() {
        return subsystemNm;
    }

    public void setSubsystemNm(String subsystemNm) {
        this.subsystemNm = subsystemNm;
    }

    public String getSubsystemComnt() {
        return subsystemComnt;
    }

    public void setSubsystemComnt(String subsystemComnt) {
        this.subsystemComnt = subsystemComnt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
}
