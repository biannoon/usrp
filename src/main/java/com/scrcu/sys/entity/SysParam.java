package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.scrcu.common.utils.CommonUtil;

import java.io.Serializable;

/**
 * 描述： 系统参数实体对象
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/17 16:57
 */
public class SysParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String paramId;
    private String paramNm;
    private String paramValue;
    private String paramComnt;
    private String paramTyp;
    private String crtDt;
    private String creatr;
    private String finlModfyDt;
    private String finlModifr;

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getParamNm() {
        return paramNm;
    }

    public void setParamNm(String paramNm) {
        this.paramNm = paramNm;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamComnt() {
        return paramComnt;
    }

    public void setParamComnt(String paramComnt) {
        this.paramComnt = paramComnt;
    }

    public String getParamTyp() {
        return paramTyp;
    }

    public void setParamTyp(String paramTyp) {
        this.paramTyp = paramTyp;
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
