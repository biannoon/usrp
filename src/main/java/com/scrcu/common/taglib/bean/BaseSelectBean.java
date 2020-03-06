package com.scrcu.common.taglib.bean;

/**
 * 下拉框标签bean
 */
public class BaseSelectBean {

    private String id; //下拉框id
    private String name; //下拉框name
    private String cls; //下拉框class
    private String value; //下拉框value
    private String disabled; //下拉框的只读属性
    private String dictionaryName; //下拉框对应的字典名

    public BaseSelectBean(String id, String name, String cls, String value, String disabled, String dictionaryName) {
        this.id = id;
        this.name = name;
        this.cls = cls;
        this.value = value;
        this.disabled = disabled;
        this.dictionaryName = dictionaryName;
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

    public String getCls() {
        return cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
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

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }



}
