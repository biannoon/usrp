package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 描述： 操作日志历史实体对象
 * @创建人： hepengfei
 * @创建时间： 2019/11/04 10:02
 */
@TableName(value="SYS_OPERLOG_HIST")
public class SysOperlogHist {
    @TableId(value="id")
    private String id;    //日志编号
    @TableField(value = "oper_tm")
    private String operTm;  //操作时间
    @TableField(value = "operator")
    private String operator;  //操作人
    @TableField(value = "oper_ip")
    private String operIp;   //操作人ip
    @TableField(value = "oper_typ")
    private String operTyp;  //操作类型
    @TableField(value = "obj_id")
    private String objId;  //操作对象id
    @TableField(value = "query_type")
    private String queryTyp; //查询类型  查询
    @TableField(value = "operation")
    private String operation;  //操作描述

    public SysOperlogHist() {}

    public SysOperlogHist(String id, String operTm, String operator, String operIp, String operTyp, String objId, String operation) {
        this.id = id;
        this.operTm = operTm;
        this.operator = operator;
        this.operIp = operIp;
        this.operTyp = operTyp;
        this.objId = objId;
        this.operation = operation;
    }


    public String getQueryTyp() {
        return queryTyp;
    }

    public void setQueryTyp(String queryTyp) {
        this.queryTyp = queryTyp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperTm() {
        return operTm;
    }

    public void setOperTm(String operTm) {
        this.operTm = operTm;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    public String getOperTyp() {
        return operTyp;
    }

    public void setOperTyp(String operTyp) {
        this.operTyp = operTyp;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "SysOperlog{" + "id='" + id + '\'' + ", operTm='" + operTm + '\'' + ", operator='" + operator + '\'' + ", operIp='" + operIp + '\'' + ", operTyp='" + operTyp + '\'' + ", objId='" + objId + '\'' + ", queryTyp='" + queryTyp + '\'' + ", operation='" + operation + '\'' + '}';
    }
}
