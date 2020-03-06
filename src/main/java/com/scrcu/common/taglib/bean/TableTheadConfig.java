package com.scrcu.common.taglib.bean;

/**
 * table标签中的Thead标签类
 */
public class TableTheadConfig {

    private String name; //表头字段名
    private String chname; //表头字段中文名
    private String scope; //表头字段的scope属性
    private String align; //表头字段的align属性
    private String style; //表头字段的style css样式属性
    private String hasOp; //是否有操作按钮
    private String fieldType;//表头字段所属列的类型，例如：超链接，纯文本，字符串截断，日期，按钮等
    private String textLength; //表头字段所属列的字段长度

    public TableTheadConfig() {
    }

    public TableTheadConfig(String name, String chname, String scope, String align, String style, String hasOp, String fieldType, String textLength) {
        this.name = name;
        this.chname = chname;
        this.scope = scope;
        this.align = align;
        this.style = style;
        this.hasOp = hasOp;
        this.fieldType = fieldType;
        this.textLength = textLength;
    }

    public String getHasOp() {
        return hasOp;
    }

    public void setHasOp(String hasOp) {
        this.hasOp = hasOp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        this.chname = chname;
    }
    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getTextLength() {
        return textLength;
    }

    public void setTextLength(String textLength) {
        this.textLength = textLength;
    }



}
