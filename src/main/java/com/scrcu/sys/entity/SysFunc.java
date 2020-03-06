package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 描述：功能菜单资源表
 * @author pengjuntao
 * @date 2019/09/12/17:33
 */
@TableName(value="sys_func")
public class SysFunc {

    @TableId(type = IdType.INPUT,value="FUNC_ID")
    private String funcId;//功能编号
    @TableField(value="FUNC_NM")
    private String funcNm;//功能名称
    @TableField(value="FUNC_TYP")
    private String funcType;//功能类型
    @TableField(value="PARE_FUNC_ID")
    private String pareFuncId;//上级功能编号
    @TableField(value="URL")
    private String url;//菜单路径
    @TableField(value="FUNC_COMNT")
    private String funcComnt;//功能描述
    @TableField(value="IS_LEF")
    private String isLeaf;//是否为叶子菜单
    @TableField(value="SEQ_NO")
    private Integer seqNo;//菜单顺序号
    @TableField(value="ICON_URL")
    private String iconUrl;//图标路径
    @TableField(value="CRT_DT")
    private String crtDt;//创建日期
    @TableField(value="CREATR")
    private String creatr;//创建人
    @TableField(value="FINL_MODFY_DT")
    private String finlModfyDt;//最后修改日期
    @TableField(value="FINL_MODIFR")
    private String finlModifr;//最后修改人
    @TableField(value="SUBSYSTEM_ID")
    private String subsystemId;//所属子系统ID

    public String getSubsystemId() {
        return subsystemId;
    }

    public void setSubsystemId(String subsystemId) {
        this.subsystemId = subsystemId;
    }
    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getFuncNm() {
        return funcNm;
    }

    public void setFuncNm(String funcNm) {
        this.funcNm = funcNm;
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

    public String getFuncComnt() {
        return funcComnt;
    }

    public void setFuncComnt(String funcComnt) {
        this.funcComnt = funcComnt;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCreatr() {
        return creatr;
    }

    public void setCreatr(String creatr) {
        this.creatr = creatr;
    }

    public String getFinlModifr() {
        return finlModifr;
    }

    public void setFinlModifr(String finlModifr) {
        this.finlModifr = finlModifr;
    }

    public String getCrtDt() {
        return crtDt;
    }

    public void setCrtDt(String crtDt) {
        this.crtDt = crtDt;
    }

    public String getFinlModfyDt() {
        return finlModfyDt;
    }

    public void setFinlModfyDt(String finlModfyDt) {
        this.finlModfyDt = finlModfyDt;
    }

    @Override
    public String toString() {
        return "SysFunc{" +
                "funcId='" + funcId + '\'' +
                ", funcNm='" + funcNm + '\'' +
                ", funcType='" + funcType + '\'' +
                ", pareFuncId='" + pareFuncId + '\'' +
                ", url='" + url + '\'' +
                ", funcComnt='" + funcComnt + '\'' +
                ", isLeaf='" + isLeaf + '\'' +
                ", seqNo=" + seqNo +
                ", iconUrl='" + iconUrl + '\'' +
                ", crtDt=" + crtDt +
                ", creatr='" + creatr + '\'' +
                ", finlModfyDt=" + finlModfyDt +
                ", finlModifr='" + finlModifr + '\'' +
                '}';
    }
}
