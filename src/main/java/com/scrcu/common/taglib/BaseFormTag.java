package com.scrcu.common.taglib;

import com.scrcu.apm.entity.SysTabConfig;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysDictryCd;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BaseFormTag extends BaseTag {

    private String action;//可选值:update/insert，用来区别是修改表单还是新增表单
    private String tablename;
    private String mode;//query查询模式，detail详情模式
    private String readonly;//整体控制是否只读模式

    @Override
    public int doStartTag() throws JspException {
        this.readonly = (String) this.getRequest().getAttribute("readonly");
        try {
            String tt = this.getDetail();
            JspWriter out = this.pageContext.getOut();
            out.println(tt);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    private String getDetail() throws Exception {
        List<SysTabConfig> sysTabConfigList = EhcacheUtil.getSysTabConfigByCache(tablename);
        List<SysTabConfig> detailShowList = new ArrayList<>();
        for (SysTabConfig sysTabConfig : sysTabConfigList) {
            if (sysTabConfig.getDetailShowFlg().equalsIgnoreCase("SYS0201")) {
                detailShowList.add(sysTabConfig);
            }
        }
        if (sysTabConfigList.size() < 1) {
            return "";
        }
        Object obj = this.getRequest().getAttribute("resultMap");
        if(null == obj) {
            obj = new LinkedCaseInsensitiveMap<>();
        }
        //-拼接table表格
        int fieldPer = 2;//-标记每行的属性的个数
        StringBuffer sb = new StringBuffer();
        sb.append("<table ");
        sb.append(" name=\"detail_table\"");
        sb.append(" mode=\"detail\"");
        sb.append(" align=\"center\"");
        sb.append(" width=\"100%\"");
        sb.append(" border=\"0\"");
        sb.append(" cellpadding=\"1\"");
        sb.append(" cellspacing=\"5\"");
        sb.append(">");
        for (int i = 0; i <detailShowList .size() ; i++) {
            if (i % fieldPer == 0) {
                sb.append("<tr>");
            }
            SysTabConfig sysTabConfig = detailShowList.get(i);
            //标签
            sb.append("<td class=\"td-label\"");
            //标签对齐方式
            if(StringUtils.isNotBlank(sysTabConfig.getlAlign())) {
                sb.append(" align=\"" + sysTabConfig.getlAlign() + "\"");
            } else {
                sb.append(" align=\"right\"");
            }
            sb.append(" valign=\"top\"");
            sb.append(">");
//            sb.append("<div style=\"margin-bottom:20px;\">");
            sb.append("<span style=\"font-size: 12px;\">" + sysTabConfig.getlNm() +"：</span>");
            //判断是否必输
            if(StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
                sb.append("<font color=\"#ff0000\">*</font>");
            }else{
                sb.append("&nbsp");
            }
            sb.append("</td>");
            //字段
            sb.append("<td class=\"td-value\"");
            //标签对齐方式
            if(StringUtils.isNotBlank(sysTabConfig.getfAlign())) {
                sb.append(" align=\"" + sysTabConfig.getfAlign() + "\"");
            } else {
                sb.append(" align=\"left\"");
            }
            sb.append(">");
//            sb.append("<div style=\"margin-bottom:20px;\">");
            sb.append(getTabElementByStyle(sysTabConfig, obj));
            sb.append("</td>");
            if ((i + 1) % fieldPer == 0) {
                sb.append("</tr>");
            }
        }
        return sb.toString();
    }

    private String getTabElementByStyle(SysTabConfig sysTabConfig, Object obj) {
        if (sysTabConfig.getfTyp().equalsIgnoreCase("text")) {
            //-单行文本框
            return getText(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("checkbox")) {
            //-复选框
            return getCheckBox(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("combobox")) {
            //-下拉框
            return getComboBox(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("passwordbox")) {
            //-密码输入框
            return getPwdBox(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("combotree")) {
            return getComboTree(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("datebox")) {
            //-日期
            return getDateBox(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("datetimebox")) {
            //-日期时间
            return getDateTimeBox(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("filebox")) {
            //-文件
            return getText(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("searchbox")) {
            return getSearchBox(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("messager")) {
            return getText(sysTabConfig, obj);
        } else if (sysTabConfig.getfTyp().equalsIgnoreCase("textarea")) {
            //-多行文本框
            return getTextarea(sysTabConfig, obj);
        } else {
            return "";
        }
    }

    /**
     * 描述：单行文本框
     * @param sysTabConfig
     * @param obj
     * @return
     */
    private String getText(SysTabConfig sysTabConfig, Object obj) {
        String fieldValue = getFieldValue(sysTabConfig.getfNm(), obj);
        StringBuffer sb = new StringBuffer();
        //-是否为详情显示
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("<span>" + fieldValue + "</span>");
            return sb.toString();
        }
        sb.append("<input");
        sb.append(" class=\"easyui-textbox\"");
        sb.append(" name=\"" + sysTabConfig.getfNm() + "\"");
        sb.append(" style=\"width:200px\" data-options=\"height:'20px'");
        //-是否必输
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
            sb.append(",required:'true' ");
        }
        //-是否只读
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getReadOnly(),"SYS0201")) {
            sb.append(",readonly:'true' ");
        }
        sb.append("\"");
        sb.append(" value=\"" + fieldValue + "\"");
        sb.append("/>");
        return sb.toString();
    }

    private String getTextarea(SysTabConfig sysTabConfig, Object obj){
        String fieldValue = getFieldValue(sysTabConfig.getfNm(), obj);
        StringBuffer sb = new StringBuffer();
        //-是否为详情页面
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("<span>" + fieldValue + "</span>");
            return sb.toString();
        }
        sb.append("<input");
        sb.append(" class=\"easyui-textbox\"");
        sb.append(" name=\""+sysTabConfig.getfNm()+"\"");
        sb.append(" style=\"width:200px\" data-options=\"height:'100px',multiline:true");
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
            sb.append(",required:'true' ");
        }
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getReadOnly(),"SYS0201")) {
            sb.append(",readonly:'true' ");
        }
        sb.append("\"");
        sb.append("value=\"" + fieldValue + "\"");
        sb.append("/>");
        return sb.toString();
    }

    private String getPwdBox(SysTabConfig sysTabConfig, Object obj) {
        String fieldValue = getFieldValue(sysTabConfig.getfNm(), obj);
        StringBuffer sb = new StringBuffer();
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("<span>" + CommonUtil.changePwd(fieldValue) + "</span>");
            return sb.toString();
        }
        sb.append("<input class=\"easyui-passwordbox\" name=\"" + sysTabConfig.getfNm() + "\" ");
        sb.append("style=\"width:100%\" data-options=\"height:'32px'");
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
            sb.append(",required:'true' ");
        }
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getReadOnly(),"SYS0201")) {
            sb.append(",readonly:'true' ");
        }
        sb.append("\"");
        sb.append("value=\"" + fieldValue + "\"");
        sb.append("/>");
        return sb.toString();
    }

    private String getCheckBox(SysTabConfig sysTabConfig, Object obj) {
        StringBuffer sb = new StringBuffer();
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("");
        }
        return sb.toString();
    }

    private String getComboBox(SysTabConfig sysTabConfig, Object obj) {
        String fieldValue = getFieldValue(sysTabConfig.getfNm(), obj);
        StringBuffer sb = new StringBuffer();
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("<span>" + fieldValue + "</span>");
            return sb.toString();
        }
        sb.append("<select class=\"easyui-combobox\" panelHeight=\"auto\" editable=\"false\" name=\"" + sysTabConfig.getfNm() + "\" ");
        sb.append("style=\"width:200px\" data-options=\"height:'20px'");
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
            sb.append(",required:'true'");
        }
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getReadOnly(),"SYS0201")) {
            sb.append(",readonly:'true'");
        }
        sb.append("\">");
        List<SysDictryCd> sysDictryCdList = EhcacheUtil.getSysDictryCdByCache(sysTabConfig.getDictryId());
        for (SysDictryCd sysDictryCd : sysDictryCdList) {
            if (fieldValue.equals(sysDictryCd.getDictryId())) {
                sb.append("<option value=" + sysDictryCd.getDictryId() + " selected>");
            } else {
                sb.append("<option value=" + sysDictryCd.getDictryId() + ">");
            }
            sb.append(sysDictryCd.getDictryNm() + "</option>");
        }
        sb.append("</select>");
        return sb.toString();
    }

    private String getComboTree(SysTabConfig sysTabConfig, Object obj) {
        StringBuffer sb = new StringBuffer();
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("");
        }
        return sb.toString();
    }

    private String getDateBox(SysTabConfig sysTabConfig, Object obj) {
        String fieldValue = getFieldValue(sysTabConfig.getfNm(), obj);
        StringBuffer sb = new StringBuffer();
        //-是否为详情展示
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("<span>" + fieldValue + "</span>");
            return sb.toString();
        }
        sb.append("<input");
        sb.append(" class=\"easyui-datebox\"");
        sb.append(" name=\""+sysTabConfig.getfNm()+"\"");
        sb.append(" style=\"width:200px\" data-options=\"height:'20px'");
        //-是否必输
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
            sb.append(",required:'true' ");
        }
        //-是否只读
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getReadOnly(),"SYS0201")) {
            sb.append(",readonly:'true' ");
        }
        sb.append("\"");
        sb.append(" value=\"" + fieldValue + "\"");
        sb.append("/>");
        return sb.toString();
    }

    private String getDateTimeBox(SysTabConfig sysTabConfig, Object obj) {
        String fieldValue = getFieldValue(sysTabConfig.getfNm(), obj);
        StringBuffer sb = new StringBuffer();
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("<span>" + fieldValue + "</span>");
            return sb.toString();
        }
        sb.append("<input class=\"easyui-datetimebox\" name=\"" + sysTabConfig.getfNm() + "\" ");
        sb.append("style=\"width:200px\" data-options=\"height:'20px'");
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
            sb.append(",required:'true' ");
        }
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getReadOnly(),"SYS0201")) {
            sb.append(",readonly:'true' ");
        }
        sb.append("\"");
        sb.append("value=\"" + fieldValue + "\"");
        sb.append("/>");
        return sb.toString();
    }

    private String getSearchBox(SysTabConfig sysTabConfig, Object obj) {
        String fieldValue = getFieldValue(sysTabConfig.getfNm(), obj);
        StringBuffer sb = new StringBuffer();
        if ("true".equalsIgnoreCase(this.readonly)) {
            sb.append("<span>" + fieldValue + "</span>");
            return sb.toString();
        }
        sb.append("<input class=\"easyui-searchbox\" name=\"" + sysTabConfig.getfNm() + "\" ");
        sb.append("id=\"" + sysTabConfig.getfNm() + "\" ");
        sb.append("style=\"150px\" data-options=\"height:'24px'");
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getMustInput(),"SYS0201")) {
            sb.append(",required:'true'");
        }
        if (StringUtils.equalsIgnoreCase(sysTabConfig.getReadOnly(),"SYS0201")) {
            sb.append(",readonly:'true'");
        }
        sb.append("\"");
        sb.append("value=\"" + fieldValue + "\"");
        sb.append("/>");
        return sb.toString();
    }

    private String getFieldValue(String fNm, Object obj) {
        Object object = getPropertyValue(fNm, obj);
        if (null == object) {
            return "";
        }
        return object.toString();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }
}
