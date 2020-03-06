package com.scrcu.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述： 统一异常处理器
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/16 15:44
 */
@Controller
@RequestMapping("${server.error.path:/error}")
public class GlobalExceptionHandler implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String DEFAULT_ERROR_VIEW = "error";

    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;

    /**
     * 描述： 若预期返回类型为text/html,则返回错误信息页(View)
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request) {
        return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request));
    }

    /**
     * 描述： 其它预期类型 则返回详细的错误信息(JSON)
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    @RequestMapping
    @ResponseBody
    public ErrorInfo error(HttpServletRequest request) {
        return errorInfoBuilder.getErrorInfo(request);
    }

    /**
     * 描述： 获取异常路径
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    @Override
    public String getErrorPath() {
        return errorInfoBuilder.getErrorProperties().getPath();
    }
}
