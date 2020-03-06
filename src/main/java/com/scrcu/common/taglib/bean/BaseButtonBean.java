package com.scrcu.common.taglib.bean;

public class BaseButtonBean {

    //按钮的基本属性
    private String name;//按钮的name属性
    private String buttonName; //按钮的名称
    private String type;//按钮的类型 button/reset/submit
    private String value;//按钮的初始值
    private String disabled;//是否禁用该按钮
    private String form;//按钮所属表单

    //按钮的全局属性
    private String id; //按钮的ID
    private String cls; //按钮的class属性

    //按钮事件
    private String onclick; //按钮所触发的事件


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }



}
