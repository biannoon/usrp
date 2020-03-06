package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 描述： 系统日志参数实体对象
 * @创建人： hepengfei
 * @创建时间： 2019/10/16 10:02
 */
public class SysLogParam {
    private String ip;       //ip
    private String port;    //地址
    private String username;        // 服务器ip
    private String passworrd;    //是否为父级id
    private String url; //父级id


    public SysLogParam(String ip, String port, String username, String passworrd, String url) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.passworrd = passworrd;
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassworrd() {
        return passworrd;
    }

    public void setPassworrd(String passworrd) {
        this.passworrd = passworrd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
