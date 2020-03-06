package com.scrcu.common.taglib;

import com.scrcu.common.utils.ReflectionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 描述： 自定义标签基础类
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/29 10:08
 */
public class BaseTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    protected PageContext pageContext = null;

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) this.pageContext.getRequest();
    }

    /**
     * 描述： 获取对象的属性值。目前标签类中只接受map类型和实体bean
     * @param property
     * @param obj
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/29 10:11
     */
    public Object getPropertyValue(String property, Object obj) {
        return ReflectionUtil.returnFieldValue(property, obj);
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;
    }
}
