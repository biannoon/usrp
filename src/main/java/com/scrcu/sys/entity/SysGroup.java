package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.scrcu.common.utils.CommonUtil;

import java.io.Serializable;

/**
 * 描述： 用户组实体对象
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/19 10:13
 */
public class SysGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String groupId;
    private String groupNm;
    private String groupComnt;
    private String blngtoOrgNo;
    private String isGlobal;
    private String crtDt;
    private String creatr;
    private String finlModfyDt;
    private String finlModifr;
    @TableField(exist = false)
    private boolean check;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupNm() {
        return groupNm;
    }

    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    public String getGroupComnt() {
        return groupComnt;
    }

    public void setGroupComnt(String groupComnt) {
        this.groupComnt = groupComnt;
    }

    public String getBlngtoOrgNo() {
        return blngtoOrgNo;
    }

    public void setBlngtoOrgNo(String blngtoOrgNo) {
        this.blngtoOrgNo = blngtoOrgNo;
    }

    public String getIsGlobal() {
        return isGlobal;
    }

    public void setIsGlobal(String isGlobal) {
        this.isGlobal = isGlobal;
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
        return "SysGroup{" + "groupId='" + groupId + '\'' + ", groupNm='" + groupNm + '\'' + ", groupComnt='" + groupComnt + '\'' + ", blngtoOrgNo='" + blngtoOrgNo + '\'' + ", isGlobal='" + isGlobal + '\'' + ", crtDt='" + crtDt + '\'' + ", creatr='" + creatr + '\'' + ", finlModfyDt='" + finlModfyDt + '\'' + ", finlModifr='" + finlModifr + '\'' + ", check=" + check + '}';
    }
}
