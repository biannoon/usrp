package com.scrcu.common.taglib.bean;

public class ButtonBean {
    private String menuno; //该按钮所属的菜单编号
    private String buttonno; //按钮编号
    private String id; //按钮标签的id属性
    private String name; //按钮标签的name属性
    private String value; //按钮的value属性
    private String cls; //按钮标签的classs属性
    private String isCommitButton; //按钮是否为提交按钮
    private String onClick; //按钮的onclick事件
    private String function; //按钮所触发的方法
    private String start = "return submitForm(this.form,    ";
    private String end = ")";
    private String type = "button";

    public ButtonBean(String menuno, String buttonno, String id, String name, String value, String cls, String isCommitButton, String onClick, String function) {
        this.menuno = menuno;
        this.buttonno = buttonno;
        this.id = id;
        this.name = name;
        this.value = value;
        this.cls = cls;
        this.isCommitButton = isCommitButton;
        this.onClick = onClick;
        this.function = function;
    }

    public String getMenuno() {
        return menuno;
    }

    public void setMenuno(String menuno) {
        this.menuno = menuno;
    }

    public String getButtonno() {
        return buttonno;
    }

    public void setButtonno(String buttonno) {
        this.buttonno = buttonno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getIsCommitButton() {
        return isCommitButton;
    }

    public void setIsCommitButton(String isCommitButton) {
        this.isCommitButton = isCommitButton;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
