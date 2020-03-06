package com.scrcu.common.taglib;

import com.scrcu.common.taglib.bean.BaseInputBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

public class BaseInputTag extends BodyTagSupport{

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        BaseInputBean input = (BaseInputBean) request.getAttribute("input");
        StringBuffer sb = new StringBuffer();
//        sb.append(BaseTag.createInputHtml(input));
        JspWriter out = this.pageContext.getOut();
        try {
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }
}
