package com.scrcu.common.taglib;

import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysDictryCd;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.List;

/**
 * 下拉框标签类
 */
public class BaseSelectTag extends BaseTag {

    private String value; // 下拉框选中的值 必填
    private String name; // 下拉框名称，选填
    private String dictno; // 下拉框对应的字典名，必填
    private String disabled; // 只读，选填
    private String id; // 下拉框名称，选填

    @Override
    public int doStartTag() {
        StringBuffer sb = new StringBuffer();
        sb.append("<select class=\"easyui-combobox\" panelHeight=\"auto\" style=\"width:150px\"");
        sb.append("name=\"" + name + "\" id=\"" + id + "\" ");
        if (StringUtils.isNotBlank(disabled)){
            if ("true".equalsIgnoreCase(disabled)){
                sb.append("disabled=true ");
            }
        }
        sb.append(">");
        sb.append("<option value=\"\">");
        List<SysDictryCd> sysDictryCdList = EhcacheUtil.getSysDictryCdByCache(dictno);
        for (SysDictryCd sysDictryCd : sysDictryCdList) {
            sb.append("<option value=" + sysDictryCd.getDictryId() + ">");
            sb.append(sysDictryCd.getDictryNm() + "</option>");
        }
        sb.append("</select>");
        JspWriter out = this.pageContext.getOut();
        try {
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BodyTagSupport.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDictno() {
        return dictno;
    }

    public void setDictno(String dictno) {
        this.dictno = dictno;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
