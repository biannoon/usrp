package com.scrcu.common.taglib.bean;

public class BaseRadioBean {

    private String topic;//该单选框的主题：例如：性别
    private String textKey;
    private String textValue;
    private Object checkedObj;

    public BaseRadioBean(String topic, String textKey, String textValue) {
        this.topic = topic;
        this.textKey = textKey;
        this.textValue = textValue;
    }

    public Object getCheckedObj() {
        return checkedObj;
    }

    public void setCheckedObj(Object checkedObj) {
        this.checkedObj = checkedObj;
    }



    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
