package com.scrcu.common.taglib;

import com.scrcu.common.taglib.bean.BaseRadioBean;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BaseRadioTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        JspWriter out = this.pageContext.getOut();
        BaseRadioBean radio = (BaseRadioBean) request.getAttribute("radio");
        List<Object> datas = (List<Object>) request.getAttribute("datas");
        try {
            String html = createRadioHtml(datas,radio);
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


    public String createRadioHtml(List<Object> datas, BaseRadioBean radio) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuffer sb = new StringBuffer();
        String value = "";
        String html = "";
        sb.append("<div class=\"radio\">");
        sb.append("<span class=\"\">" + radio.getTopic() + "</span>");
        for (Object obj01:datas) {
            value = (String) PropertyUtils.getProperty(obj01,radio.getTextKey());
            html = (String) PropertyUtils.getProperty(obj01,radio.getTextValue());
            sb.append("<input type=\"radio\" ");
            sb.append(" name=\"" + radio.getTextKey() + "\" ");
            sb.append(" value=\"" + value + "\" ");
            String value01 = (String) PropertyUtils.getProperty(radio.getCheckedObj(),radio.getTextKey());
            if (value.equals(value01)){
                sb.append("checked ");
            }
            sb.append("/>");
            sb.append(html);
        }
        sb.append("</div");
        return sb.toString();
    }


    //--------------------set/get-------------------------

}
