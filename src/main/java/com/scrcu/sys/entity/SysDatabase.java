package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.scrcu.common.utils.CommonUtil;

import java.io.Serializable;

/**
 * 描述： 数据源信息
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/18 15:26
 */
public class SysDatabase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    private String dsNm;
    private String dbTyp;
    private String dbIp;
    private String dbPort;
    private String dbNm;
    private String dbUserNm;
    private String dbUserPwd;
    private String crtDt;
    private String creatr;
    private String finlModfyDt;
    private String finlModifr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDsNm() {
        return dsNm;
    }

    public void setDsNm(String dsNm) {
        this.dsNm = dsNm;
    }

    public String getDbTyp() {
        return dbTyp;
    }

    public void setDbTyp(String dbTyp) {
        this.dbTyp = dbTyp;
    }

    public String getDbIp() {
        return dbIp;
    }

    public void setDbIp(String dbIp) {
        this.dbIp = dbIp;
    }

    public String getDbPort() {
        return dbPort;
    }

    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbNm() {
        return dbNm;
    }

    public void setDbNm(String dbNm) {
        this.dbNm = dbNm;
    }

    public String getDbUserNm() {
        return dbUserNm;
    }

    public void setDbUserNm(String dbUserNm) {
        this.dbUserNm = dbUserNm;
    }

    public String getDbUserPwd() {
        return dbUserPwd;
    }

    public void setDbUserPwd(String dbUserPwd) {
        this.dbUserPwd = dbUserPwd;
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
}
