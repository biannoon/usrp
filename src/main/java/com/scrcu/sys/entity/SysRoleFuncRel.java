package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * 描述： 角色菜单关联信息实体对象
 * @创建人： hepengfei
 * @创建时间： 2019/11/15 19:50
 */
public class SysRoleFuncRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private int id;
    private String funcId;
    private String roleId;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFuncId() {
        return funcId;
    }

    public void setFuncId(String funcId) {
        this.funcId = funcId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysRoleFuncRel{" + "id=" + id + ", funcId='" + funcId + '\'' + ", roleId='" + roleId + '\'' + '}';
    }
}
