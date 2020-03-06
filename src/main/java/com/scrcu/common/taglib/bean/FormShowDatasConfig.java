package com.scrcu.common.taglib.bean;

/**
 * form表单需要展示的数据的实体类
 * @Desc 该类中定义需要展示字段是所有属性，例如颜色，字段名等
 */
public class FormShowDatasConfig {

    private String fieldName;//表单显示的字段
    private String fieldChname;//表单显示的字段的中文名

    private BaseInputBean input;//input框的样式bean

    public FormShowDatasConfig(String fieldName, String fieldChname, BaseInputBean input) {
        this.fieldName = fieldName;
        this.fieldChname = fieldChname;
        this.input = input;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldChname() {
        return fieldChname;
    }

    public void setFieldChname(String fieldChname) {
        this.fieldChname = fieldChname;
    }

    public BaseInputBean getInput() {
        return input;
    }

    public void setInput(BaseInputBean input) {
        this.input = input;
    }

}
