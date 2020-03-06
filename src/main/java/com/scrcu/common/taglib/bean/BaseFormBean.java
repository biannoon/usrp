package com.scrcu.common.taglib.bean;

import java.util.List;

/**
 * 表单标签实体类
 */
public class BaseFormBean {

    //基本属性
    private String name; //表单的name属性
    private String method; //表单的method
    private String action; //表单的action属性

    private BaseTableBean table; //form标签内table的样式
    private List<FormShowDatasConfig> showDatasConfigs;//表达需要展示的字段集合

    //新增属性
    private String formKind;//表单的形式：列表表单/展示表单
    private String formType; //表单的类型：新增表单/修改表单/详情表单

    public BaseFormBean() {
    }

    public BaseFormBean(String name, String method, String action) {
        this.name = name;
        this.method = method;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public BaseTableBean getTable() {
        return table;
    }

    public void setTable(BaseTableBean table) {
        this.table = table;
    }

    public List<FormShowDatasConfig> getShowDatasConfigs() {
        return showDatasConfigs;
    }

    public void setShowDatasConfigs(List<FormShowDatasConfig> showDatasConfigs) {
        this.showDatasConfigs = showDatasConfigs;
    }

    public String getFormKind() {
        return formKind;
    }

    public void setFormKind(String formKind) {
        this.formKind = formKind;
    }


}
