package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 描述： 系统日志实体对象
 * @创建人： hepengfei
 * @创建时间： 2019/10/16 10:02
 */
public class SysLog {
    private String sysLogId;  //系统日志id
    private String fileNm;    //文件名
    private String ip;        // 服务器ip
    private String state;    //是否为父级id
    private String parentId; //父级id
    private String fileUpdDate; //文件更新时间
    private String isDic;      //是否为目录
    @TableField(exist = false)
    private String port;    //端口
    @TableField(exist = false)
    private String user;    //用户
    @TableField(exist = false)
    private String pwd;     //密码
    @TableField(exist = false)
    private String url;     //日志路径

    public SysLog() {}

    public SysLog(String sysLogId, String fileNm, String ip, String state, String parentId, String fileUpdDate, String isDic, String port, String user, String pwd, String url) {
        this.sysLogId = sysLogId;
        this.fileNm = fileNm;
        this.ip = ip;
        this.state = state;
        this.parentId = parentId;
        this.fileUpdDate = fileUpdDate;
        this.isDic = isDic;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
        this.url = url;
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




    public String getIsDic() {
        return isDic;
    }

    public void setIsDic(String isDic) {
        this.isDic = isDic;
    }

    public String getSysLogId() {
        return sysLogId;
    }

    public void setSysLogId(String sysLodId) {
        this.sysLogId = sysLodId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "SysLog{" +
                "sysLogId='" + sysLogId + '\'' +
                ", fileNm='" + fileNm + '\'' +
                ", ip='" + ip + '\'' +
                ", state='" + state + '\'' +
                ", parentId='" + parentId + '\'' +
                ", fileUpdDate='" + fileUpdDate + '\'' +
                ", isDic='" + isDic + '\'' +
                ", port='" + port + '\'' +
                ", user='" + user + '\'' +
                ", pwd='" + pwd + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
