package com.scrcu.sys.entity;

/**
 * @ClassName SysUserRoleRel
 * @Description TODO
 * @Author pengjuntao
 * @Date 2020/2/21 18:19
 * @Version 1.0
 */
public class SysUserRoleRel {
    private int id;
    private String userId;
    private String roleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysUserRoleRel{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
