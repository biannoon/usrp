package com.scrcu.common.base.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 前端tree的节点实体类
 * @Author hepengfei
 * @date 2019/10/31 16:43
 */
public class TreeNodeSysLog implements Serializable {

    private String sysLogId; //日志文件编号
    private String fileNm;
    private String ip;//服务器
    private String fileUpdDate; //文件更新时间
    private String parentId ; //父级id
    private String isDic;    //是否为目录
    private String state; //节点状态
    private List<TreeNodeSysLog> children; //子节点集合
    private String port;
    private String user;
    private String pwd;
    private String url;

    public TreeNodeSysLog() {
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }




    public void addChildren(TreeNodeSysLog node){
        if (this.children == null){
            children = new ArrayList<>();
            children.add(node);
        }else{
            children.add(node);
        }
    }

    public String getIsDic() {
        return isDic;
    }

    public void setIsDic(String isDic) {
        this.isDic = isDic;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSysLogId() {
        return sysLogId;
    }

    public void setSysLogId(String sysLogId) {
        this.sysLogId = sysLogId;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileUpdDate() {
        return fileUpdDate;
    }

    public void setFileUpdDate(String fileUpdDate) {
        this.fileUpdDate = fileUpdDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TreeNodeSysLog> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeSysLog> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNodeSysLog{" + "sysLogId='" + sysLogId + '\'' + ", fileNm='" + fileNm + '\'' + ", ip='" + ip + '\'' + ", fileUpdDate='" + fileUpdDate + '\'' + ", state='" + state + '\'' + ", children=" + children + '}';
    }
}
