package com.scrcu.common.taglib;

import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * 基础标签类
 */
public class BaseTagAttr extends TagSupport {

    private static final long serialVersionUID = 1L;


    //-------------------------------------基本标签属性生成方法区-----------------------------------------------------
    /**
     * 生成标签的class属性
     * @param style class属性的值
     * @return
     */
    public String getClassHtml(String value){
        if (StringUtils.isNotBlank(value))
            return " class=\"" + value.trim() + "\" ";
        return "";
    }

    /**
     * 生成标签的id属性
     * @param id id属性的值
     * @return
     */
    public String getIdHtml(String id){
        if (StringUtils.isNotBlank(id))
            return " id=\"" + id.trim() + "\" ";
        return "";
    }

    /**
     * 生成标签的name属性
     * @param name name属性的值
     * @return
     */
    public String getNameHtml(String name){
        if (StringUtils.isNotBlank(name))
            return " name=\"" + name.trim() + "\" ";
        return "";
    }

    /**
     * 生成标签的value属性
     * @param value
     * @return
     */
    public String getValueHtml(String value){
        if (StringUtils.isNotBlank(value))
            return " value=\"" + value.trim() + "\" ";
        return "";
    }

    /**
     * 生成只读标签属性
     * @param readonly true或者1
     * @return
     */
    public String getReadonlyHtml(String readonly){
        if (StringUtils.isNotBlank(readonly)) {
            if ("1".equals(readonly.trim()) || "true".equalsIgnoreCase(readonly.trim())) {
                return " readonly=true ";
            }
        }
        return "";
    }

    /**
     * 生成必输属性
     * @param mustInput 1/true
     * @return
     */
    public String getMustInputHtml(String mustInput){
        if (StringUtils.isNotBlank(mustInput)){
            if ("1".equals(mustInput.trim()) || "true".equalsIgnoreCase(mustInput.trim())){
                return " mustinput=true ";
            }
        }
        return "";
    }

    /**
     *
     * @param lable
     * @return
     */
    public String getLableHtml(String lable){
        if (StringUtils.isNotBlank(lable))
            return " lable=\"" + lable.trim() + "\" ";
        return "";
    }

    /**
     * 文本位置属性
     * @param align
     * @return
     */
    public String getAlignHtml(String align){
        if (StringUtils.isNotBlank(align))
            return " align=\"" + align.trim() + "\" ";
        return "";
    }

    /**
     * 宽度属性
     * @param width
     * @return
     */
    public String getWidthHtml(String width){
        if (StringUtils.isNotBlank(width))
            return " width=\"" + width.trim() + "\" ";
        return  "";
    }

    /**
     * 高度属性
     * @param height
     * @return
     */
    public String getHeightHtml(String height){
        if (StringUtils.isNotBlank(height))
            return " height=\"" + height.trim() + "\" ";
        return "";
    }

    //------------------------form表单属性-----------------------------------

    /**
     * action属性
     * @param action
     * @return
     */
    public String getActionHtml(String action){
        if (StringUtils.isNotBlank(action))
            return " action=\"" + action.trim() + "\" ";
        return "";
    }

    /**
     * method属性
     * @param method
     * @return
     */
    public String getMethodHtml(String method){
        if (StringUtils.isNotBlank(method))
            return " method=\"" + method.trim() + "\" ";
        return " method=\"post\" ";
    }

    /**
     * 获取对象属性值--未完成
     * @param obj
     * @param property
     * @return
     */
    public Object getPropertyValue(Object obj, String property){
        return "";
    }



}
