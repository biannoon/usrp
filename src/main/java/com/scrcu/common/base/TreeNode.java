package com.scrcu.common.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 前端tree的节点实体类
 * @Author pengjuntao
 * @date 2019/09/10 16:43
 */
public class TreeNode implements Serializable {

    private String id; //节点编号
    private String text; //节点名称
    private String iconCls;
    private String state; //节点状态
    private String checked; //节点是否被选中
    private TreeNodeAttribute attributes; //属性
    private List<TreeNode> children; //子节点集合

    public TreeNode() {
    }

    public TreeNode(String id, String text, String iconCls, String state, String checked,
                    TreeNodeAttribute attributes, List<TreeNode> children) {
        this.id = id;
        this.text = text;
        this.iconCls = iconCls;
        this.state = state;
        this.checked = checked;
        this.attributes = attributes;
        this.children = children;
    }

    public void addChildren(TreeNode node){
        if (this.children == null){
            children = new ArrayList<>();
            children.add(node);
        }else{
            children.add(node);
        }
    }

    //--------------------set/get-------------------------------
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public TreeNodeAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(TreeNodeAttribute attributes) {
        this.attributes = attributes;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }


}
