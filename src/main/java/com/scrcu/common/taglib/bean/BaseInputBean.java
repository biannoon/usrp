package com.scrcu.common.taglib.bean;

public class BaseInputBean {

    //input框属性
    private String id;
    private String name;
    private String value;//input 元素的值
    private String type = "text";//input 元素的类型
    private String width = "252px"; //input 字段的宽度
    private String height;
    private String max;
    private String maxlength;
    private String placeholder;
    private String readonly;//输入字段为只读
    private String disabled;

    //input框的事件
    private String onclick;//点击事件


    public BaseInputBean() {
    }

    public BaseInputBean(String id,
                         String name,
                         String value,
                         String type,
                         String width,
                         String height,
                         String max,
                         String maxlength,
                         String placeholder,
                         String readonly,
                         String disabled) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.type = type;
        this.width = width;
        this.height = height;
        this.max = max;
        this.maxlength = maxlength;
        this.placeholder = placeholder;
        this.readonly = readonly;
        this.disabled = disabled;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

}
