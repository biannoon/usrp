package com.scrcu.common.base;

import java.io.Serializable;

/**
 * 描述： easyui datalist数据列表对象
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/30 19:29
 */
public class DataList implements Serializable {
    private String value;
    private String text;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
