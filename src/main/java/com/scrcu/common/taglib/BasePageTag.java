package com.scrcu.common.taglib;

import com.scrcu.common.taglib.bean.BasePageBean;
import com.scrcu.common.taglib.bean.PageConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


public class BasePageTag extends TagSupport {

    private static final long SerialVersionUID = 1L;

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
        BasePageBean page = (BasePageBean) request.getAttribute("page");
        StringBuffer sb = new StringBuffer("");
        sb.append("共&nbsp;" + (page.getPageCount()) + "&nbsp;页&nbsp; ");
        //--从前端页面获取当前页
        String currentPage = (String) request.getAttribute("current");
        if (currentPage != null && !"".equals(currentPage))
            page.setCurrentPage(Integer.parseInt(currentPage));
        //--如果总记录数>每页记录数，表示有分页，url拼接参数连接符
        if (page.getTotalSize() > page.getPageSize()){
            String pref;
            if (page.getUrl().indexOf("?") != -1)
                pref = "&";
            else
                pref = "?";
            this.doStart(sb, page, pref);
            this.doBody(sb, page, pref);
            this.doEnd(sb, page, pref);
        }else{
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        sb.append("共&nbsp;" + page.getTotalSize() + "&nbsp;条记录");
        JspWriter out = this.pageContext.getOut();
        try {
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;

    }


    @Override
    public int doEndTag() throws JspException {
        return super.doEndTag();
    }


    /**
     * 拼接首页和上一页
     * @param sb
     * @param page
     * @param pref
     */
    public void doStart(StringBuffer sb , BasePageBean page, String pref){
        if (page.getCurrentPage() > 0){
            sb.append("<a href='" + page.getUrl() + pref + "current=0'>首页</a>\n" +
                    "<a href='" + page.getUrl() + pref + "current=" + page.getPreviousPage() + "'>[<上一页]</a>\n");
        }else
            sb.append("<a href='" + page.getUrl() + pref + "current=0'>[首页]</a>\n");
    }

    /**
     * 拼接尾页和下一页
     * @param sb
     * @param page
     * @param pref
     */
    public void doEnd(StringBuffer sb , BasePageBean page, String pref){
        if (page.getCurrentPage() == page.getPageCount())
            sb.append("<a href='" + page.getUrl() + pref + "current=" + page.getPageCount() + "'>[尾页]</a>\n");
        else
            sb.append("<a href='" + page.getUrl() + pref + "current=" + page.getPageCount() + "'>[尾页]</a>\n" +
                    "<a href='" + page.getUrl() + pref + "current=" + page.getNextPage() + "'>[>下一页]</a>\n");
    }

    /**
     * 分页中间数字拼接
     * @param sb
     * @param page
     * @param pref
     */
    public void doBody(StringBuffer sb , BasePageBean page, String pref){
        //--显示半径
        int redius = PageConstants.MAX_PAGE_INDEX / 2;
        //--显示开始数字
        int startNum;
        if (page.getPageCount() <= PageConstants.MAX_PAGE_INDEX ){
            startNum = 0;
        }else{
            if (page.getCurrentPage() > redius) {
                startNum = page.getCurrentPage() - redius;
            }else{
                startNum = 0;
            }
        }

        int i = startNum;
        while(i < page.getPageCount() && i < (startNum + PageConstants.MAX_PAGE_INDEX) ){
            if (i == page.getCurrentPage())
                sb.append("<b>" + i + "</b>\n");
            else
                sb.append("<a href='" + page.getUrl() + pref + "current=" + i + "'>" + i + "</a>\n");
            i += 1;
        }
    }


}
