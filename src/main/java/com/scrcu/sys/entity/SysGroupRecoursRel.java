package com.scrcu.sys.entity;

/**
 * @Author pengjuntao
 * @Date 2019/11/12 17:12
 * @Version 1.0
 * 描述：用户组资源关联表
 **/
public class SysGroupRecoursRel {

    private int id;//关联ID
    private String groupId;//用户组ID
    private String recoursId;//资源ID
    private String recoursNm;//资源名称
    private String recoursTyp;//资源类型

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRecoursId() {
        return recoursId;
    }

    public void setRecoursId(String recoursId) {
        this.recoursId = recoursId;
    }

    public String getRecoursNm() {
        return recoursNm;
    }

    public void setRecoursNm(String recoursNm) {
        this.recoursNm = recoursNm;
    }

    public String getRecoursTyp() {
        return recoursTyp;
    }

    public void setRecoursTyp(String recoursTyp) {
        this.recoursTyp = recoursTyp;
    }

    @Override
    public String toString() {
        return "SysGroupRecoursRel{" + "id=" + id + ", groupId='" + groupId + '\'' + ", recoursId='" + recoursId + '\'' + ", recoursNm='" + recoursNm + '\'' + ", recoursTyp='" + recoursTyp + '\'' + '}';
    }
}
