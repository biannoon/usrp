package com.scrcu.common.taglib;

import com.scrcu.common.taglib.bean.BaseCheckBoxBean;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 复选框标签类
 */
public class BaseCheckBoxTag extends BodyTagSupport{
    private static final long serialVersionUID = 1L;

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();

        BaseCheckBoxBean checkBox = (BaseCheckBoxBean) request.getAttribute("checkbox");
        List<Object> checkBoxVals = (List<Object>) request.getAttribute("datas");
        List<Object> items = (List<Object>) request.getAttribute("shows");

        JspWriter out = this.pageContext.getOut();
        try {
            String html = createCheckBoxHtml(checkBox,checkBoxVals,items);
            out.println(html);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    public String createCheckBoxHtml(BaseCheckBoxBean checkBox, List<Object> checkBoxVals, List<Object> items) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuffer sb = new StringBuffer();
        String value = "";
        String html = "";
        for (int i = 0; i < checkBoxVals.size(); i++) {
            value = (String) PropertyUtils.getProperty(checkBoxVals.get(i),checkBox.getTextKey());
            html = (String) PropertyUtils.getProperty(checkBoxVals.get(i),checkBox.getTextValue());
            sb.append("<input type=\"checkbox\" ");
            sb.append("value=\"" + value + "\" ");
            for (Object obj: items) {
                String value_obj = (String)PropertyUtils.getProperty(obj,checkBox.getTextKey());
                if (value_obj.equals(value)){
                    sb.append("checked ");
                }
            }
            sb.append("/>");
            sb.append(html);
            sb.append("");
        }
        return sb.toString();
    }
    //---------------------set/get-------------------------------------
}
