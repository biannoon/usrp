package com.scrcu.common.taglib;

import com.scrcu.common.taglib.bean.BaseButtonBean;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * 按钮标签类
 */
public class BaseButtonTag extends BodyTagSupport {

    private static final long serialVersionUID = 1L;
    private String menuno; //该按钮所属的菜单编号
    private String buttonno;//按钮编号

    public int doStartTag(){
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        BaseButtonBean button = new BaseButtonBean();//引入button数据
        button.setButtonName("查询");
        String html = createButtonHtml(button);
        JspWriter out = this.pageContext.getOut();
        try {
            out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BodyTagSupport.SKIP_BODY;
    }

    /**
     * 创建button按钮
     * @param button
     * @return
     */
    public String createButtonHtml(BaseButtonBean button){
        StringBuffer sb = new StringBuffer();
        sb.append("<button ");
        sb.append("type=\"" + button.getType() + "\" ");
        sb.append("name=\"" + button.getName() + "\" ");
        sb.append("value=\"" + button.getValue() + "\" ");
        if (StringUtils.isNotBlank(button.getId()))
            sb.append("id=\"" + button.getId() + "\" ");
        if (StringUtils.isNotBlank(button.getCls()))
            sb.append("class=\"" + button.getCls() + "\" ");
        if (StringUtils.isNotBlank(button.getDisabled()))
            sb.append("disabled=\"" + button.getDisabled() + "\" ");
        if (StringUtils.isNotBlank(button.getForm()))
            sb.append("form=\"" + button.getForm() + "\" ");
        if (StringUtils.isNotBlank(button.getOnclick()))
            sb.append("onclick=\"" + button.getOnclick() + "\" ");
        sb.append(">");
        sb.append(button.getButtonName());
        sb.append("</button>");
        return sb.toString();
    }
    //-------------------------set/get-------------------------------------

    public String getMenuno() {
        return menuno;
    }

    public void setMenuno(String menuno) {
        this.menuno = menuno;
    }

    public String getButtonno() {
        return buttonno;
    }

    public void setButtonno(String buttonno) {
        this.buttonno = buttonno;
    }



}
