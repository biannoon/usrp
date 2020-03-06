package com.scrcu.common.taglib;

import com.scrcu.common.taglib.bean.BaseTableBean;
import com.scrcu.common.taglib.bean.TableTheadConfig;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class BaseTableTag extends BodyTagSupport {

    private static final long SerialVersionUID = 1L;

//    private String chkState;//表内校验字段名
//    private String relChkState;//表间校验字段名
//    private String columns;//列数*/

    @Override
    public int doStartTag() throws JspException {
        String html = null;
        try {
            html = createTableHtml();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        JspWriter out = this.pageContext.getOut();
        try {
            out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.doStartTag();
    }

    public String createTableHtml() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        BaseTableBean table = (BaseTableBean) request.getAttribute("table");
        StringBuffer sb = new StringBuffer();
        //----拼接table开始标签----
        sb.append(createTableTagStartHtml(table));
        //---------拼接thead标签------------
        sb.append(createHeaderContent(table.getThreadConfigs()));
        //---------拼接tbody标签------------
        List<Object> dataList = (List<Object>) request.getAttribute("resultList");
        if (dataList.size() > 0)
            sb.append(createRowContent(table.getThreadConfigs(),dataList));
        else
            sb.append("<tr><td colspan=\"\" align=\"center\">没有符合条件记录！</td></tr>");
        sb.append("</tbody>");
        //----拼接table结束标签-------
        sb.append(createTableTagEndHtml());
        return sb.toString();
    }


    /**
     * 拼接table标签头
     * @param table
     * @return
     */
    public String createTableTagStartHtml(BaseTableBean table){
        StringBuffer sb = new StringBuffer();
        sb.append("<table ");
        sb.append("id=\"" + table.getId() + "\" ");
        sb.append("name=\"" + table.getName() + "\" ");
        sb.append("class=\"" + table.getCls() + "\" ");
        sb.append("width=\"" + table.getWidth() + "\" ");
        sb.append("border=\"" + table.getBorder() + "\" ");
        sb.append("align=\"" + table.getAlign() + "\" ");
        sb.append("cellspacing=\"" + table.getCellspacing() + "\" ");
        sb.append("title=\"" + table.getTitle() + "\" ");
        sb.append(">");

        return sb.toString();
    }

    public String createTableTagEndHtml(){
        StringBuffer sb = new StringBuffer();
        sb.append("</table>");
        return sb.toString();
    }

    /**
     * 创建table表的展示字段头
     * 暂时不完善，未添加权限控制
     * @Param showFieldsName 需要展示的字段的集合
     * @return
     */
    public String createHeaderContent(List<TableTheadConfig> tableTheadConfigs){
        StringBuffer sb = new StringBuffer();
        sb.append("<thead>");
        sb.append("<tr>");
        TableTheadConfig config = null;
        for (int i = 0; i < tableTheadConfigs.size(); i++) {
            config = tableTheadConfigs.get(i);
            if (!"op".equals(config.getName()) && !"操作".equals(config.getChname())){
                sb.append("<th ");
                if (StringUtils.isNotBlank(config.getScope()))
                    sb.append("scope=\"" + config.getScope() + "\" ");
                if (StringUtils.isNotBlank(config.getAlign()))
                    sb.append("align=\"" + config.getAlign() + "\" ");
                if (StringUtils.isBlank(config.getStyle()))
                    sb.append("style=\"text-align:center\" ");
                sb.append(">");
                sb.append("<font class=\"\">");
                sb.append(config.getChname());
                sb.append("</font");
                sb.append("</th>");
            }
        }
        //--拼接操作字段
        for (TableTheadConfig config01 :tableTheadConfigs) {
            if ("op".equals(config01.getName()) || "操作".equals(config01.getChname())){
                sb.append("<th scope=\"col\" align=\"center\" class=\"tail\" data-sorter=\"false\" colspan=\"\" >");
                sb.append("<font class=\"button-color\">操作</font>");
                sb.append("</th>");
            }
        }
        sb.append("</tr>");
        sb.append("</thead>");

        return sb.toString();
    }

    /**
     *
     * @param tableTheadConfigs 需要展示的字段的集合
     * @param dataList 展示的数据集合
     * @return
     */
    public String createRowContent(List<TableTheadConfig> tableTheadConfigs, List<Object> dataList) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        StringBuffer sb = new StringBuffer();
        sb.append("<tbody>");
        for (Object obj : dataList) {
            sb.append("<tr>");
            for (int i = 0; i < tableTheadConfigs.size(); i++) {
                if (!"op".equals(tableTheadConfigs.get(i).getName()) && !"操作".equals(tableTheadConfigs.get(i).getChname())) {
                    sb.append("<td align=\"center\">");
                    String value = PropertyUtils.getProperty(obj, tableTheadConfigs.get(i).getName()).toString();
                   /* Object value = PropertyUtils.getProperty(obj, tableTheadConfigs.get(i).getName());
                    //--判断数据的格式，并转化为指定的格式的数据
                    if ("text".equals(tableTheadConfigs.get(i).getFieldType())) {
                        sb.append(getTextTypeContent(value));
                    } else if ("truncate".equals(tableTheadConfigs.get(i).getFieldType())) {
                        sb.append(getTruncateTypeContent(value, tableTheadConfigs.get(i)));
                    } else {
                        sb.append(getTextTypeContent(value));
                    }*/
                   sb.append(value);
                    sb.append("</td>");
                }
            }
            //--拼接操作按钮
            for (TableTheadConfig config :tableTheadConfigs) {
                if ("op".equals(config.getName()) || "操作".equals(config.getChname())){
                    sb.append("<td style=\"white-space: nowrap\" align=\"center\">");
                    sb.append("button");//暂未开发添加button功能
                    sb.append("</td>");
                }
            }
            sb.append("</tr>");
        }
        return sb.toString();
    }

    /**
     * 转换文本类型
     * @param obj
     * @return
     */
    public String getTextTypeContent(Object obj){
        if (obj == null)
            return "";
        return obj.toString();
    }

    /**
     * 字段长度截取到指定长度
     * @param obj
     * @param config
     * @return
     */
    public String getTruncateTypeContent(Object obj,TableTheadConfig config){
        if (obj == null)
            return "";
        int length = Integer.parseInt(config.getTextLength());
        String value = obj.toString();
        if (value.length() > length)
            return value.substring(0,length) + "...";
        else
            return value;
    }

    /**
     * 转化日期格式
     */
   /* public String getDataTypeContent(Object obj){
        if (obj == null)
            return "";


    }*/

   /*public String getButtonTypeContent(){

   }*/


}
