package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.scrcu.common.utils.CommonUtil;

import java.io.Serializable;

/**
 * 描述： 用户信息实体对象
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 10:34
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String userId;
    private String userNm;
    private String pwd;
    private String genderCd;
    private String crtfNo;
    private String brthdy;
    private String telNo;
    private String addr;
    private String blngtoOrgNo;
    private String stus;
    private String recntLgnTm;
    private String crtDt;
    private String creatr;
    private String finlModfyDt;
    private String finlModifr;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getGenderCd() {
        return genderCd;
    }

    public void setGenderCd(String genderCd) {
        this.genderCd = genderCd;
    }

    public String getCrtfNo() {
        return crtfNo;
    }

    public void setCrtfNo(String crtfNo) {
        this.crtfNo = crtfNo;
    }

    public String getBrthdy() {
        return brthdy;
    }

    public void setBrthdy(String brthdy) {
        this.brthdy = (CommonUtil.isEmpty(brthdy) ? null : brthdy);
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getBlngtoOrgNo() {
        return blngtoOrgNo;
    }

    public void setBlngtoOrgNo(String blngtoOrgNo) {
        this.blngtoOrgNo = blngtoOrgNo;
    }

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public String getRecntLgnTm() {
        return recntLgnTm;
    }

    public void setRecntLgnTm(String recntLgnTm) {
        this.recntLgnTm = (CommonUtil.isEmpty(recntLgnTm) ? null : recntLgnTm);
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

    @Override
    public String toString() {
        return "SysUser{" + "userId='" + userId + '\'' + ", userNm='" + userNm + '\'' + ", pwd='" + pwd + '\'' + ", genderCd='" + genderCd + '\'' + ", crtfNo='" + crtfNo + '\'' + ", brthdy='" + brthdy + '\'' + ", telNo='" + telNo + '\'' + ", addr='" + addr + '\'' + ", blngtoOrgNo='" + blngtoOrgNo + '\'' + ", stus='" + stus + '\'' + ", recntLgnTm='" + recntLgnTm + '\'' + ", crtDt='" + crtDt + '\'' + ", creatr='" + creatr + '\'' + ", finlModfyDt='" + finlModfyDt + '\'' + ", finlModifr='" + finlModifr + '\'' + '}';
    }
}
