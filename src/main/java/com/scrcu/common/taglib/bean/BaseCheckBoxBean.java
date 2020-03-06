package com.scrcu.common.taglib.bean;

public class BaseCheckBoxBean {

    private String textKey; //传入值
    private String textValue; //显示值

    public BaseCheckBoxBean(String textKey, String textValue) {
        this.textKey = textKey;
        this.textValue = textValue;
    }

    public String getTextKey() {
        return textKey;
    }

    public void setTextKey(String textKey) {
        this.textKey = textKey;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }


}
