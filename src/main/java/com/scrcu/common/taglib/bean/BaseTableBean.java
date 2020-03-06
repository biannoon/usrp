package com.scrcu.common.taglib.bean;

import java.util.List;

public class BaseTableBean {

    private String id; //table的id属性
    private String name; //table的name属性
    private String cls; //table的class属性
    private String align = "center"; //table的align属性
    private String style; //table的css样式设置
    private String width = "100%";//table的width属性
    private String border = "1";//table的border属性
    private String cellspacing = "1";//table的cellspacing属性
    private String title;//table的title属性

    private List<TableTheadConfig> threadConfigs;

    /**
     * 构造函数
     */
    public BaseTableBean() {
    }

    public BaseTableBean(String id,
                         String name,
                         String cls,
                         String align,
                         String style,
                         String width,
                         String border,
                         String cellspacing,
                         String title) {
        this.id = id;
        this.name = name;
        this.cls = cls;
        this.align = align;
        this.style = style;
        this.width = width;
        this.border = border;
        this.cellspacing = cellspacing;
        this.title = title;
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

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TableTheadConfig> getThreadConfigs() {
        return threadConfigs;
    }

    public void setThreadConfigs(List<TableTheadConfig> threadConfigs) {
        this.threadConfigs = threadConfigs;
    }


}
