package com.scrcu.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @ClassName SysUserGroupRel
 * @Description TODO
 * @Author pengjuntao
 * @Date 2020/2/21 18:42
 * @Version 1.0
 */
public class SysUserGroupRel {
    @TableId
    private int id;
    private String userId;
    private String groupId;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "SysUserGroupRel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
