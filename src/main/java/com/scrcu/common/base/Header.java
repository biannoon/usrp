package com.scrcu.common.base;

/**
 * 描述： TODO
 *
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/24 18:57
 */
public class Header {
    private String field;
    private String title;
    private String align;
    private int width = 150;
    private boolean checkbox = false;
    private String rowspan;
    private String colspan;
    private boolean sortable;

    public Header(){

    }

    public Header(String field, String title, String align){
        this.field = field;
        this.title = title;
        this.align = align;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getRowspan() {
        return rowspan;
    }

    public void setRowspan(String rowspan) {
        this.rowspan = rowspan;
    }

    public String getColspan() {
        return colspan;
    }

    public void setColspan(String colspan) {
        this.colspan = colspan;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    @Override
    public String toString(){
        return "";
    }
}
