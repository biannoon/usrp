package com.scrcu.common.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * 描述： 错误信息的构建工具
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/17 9:18
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class ErrorInfoBuilder implements HandlerExceptionResolver, Ordered {

    // 错误KEY
    private final static String ERROR_NAME = "com.scrcu";
    // 错误配置
    private ErrorProperties errorProperties;

    public ErrorProperties getErrorProperties() {
        return errorProperties;
    }
    public void setErrorProperties(ErrorProperties errorProperties) {
        this.errorProperties = errorProperties;
    }

    /**
     * 描述： 异常信息构造器，传递配置属性：server.xx -> server.error.xx
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    public ErrorInfoBuilder(ServerProperties serverProperties) {
        this.errorProperties = serverProperties.getError();
    }

    /**
     * 描述： 构建异常信息
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    public ErrorInfo getErrorInfo(HttpServletRequest request) {
        return getErrorInfo(request, getError(request));
    }

    /**
     * 描述： 构建异常信息
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    public ErrorInfo getErrorInfo(HttpServletRequest request, Throwable error) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setTime(LocalDateTime.now().toString());
        errorInfo.setUrl(request.getRequestURL().toString());
        errorInfo.setError(error.toString());
        errorInfo.setStatusCode(getHttpStatus(request).value());
        errorInfo.setReasonPhrase(getHttpStatus(request).getReasonPhrase());
        errorInfo.setStackTrace(getStackTraceInfo(error, isIncludeStackTrace(request)));
        return errorInfo;
    }

    /**
     * 描述： 获取异常信息
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    public Throwable getError(HttpServletRequest request) {
        //根据HandlerExceptionResolver接口方法来获取错误.
        Throwable error = (Throwable) request.getAttribute(ERROR_NAME);
        //根据Request对象获取错误.
        if (error == null) {
            error = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
        }
        //当获取错误非空,取出RootCause.
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = error.getCause();
            }
        }//当获取错误为null,此时我们设置错误信息即可.
        else {
            String message = (String) request.getAttribute(WebUtils.ERROR_MESSAGE_ATTRIBUTE);
            if (StringUtils.isEmpty(message)) {
                HttpStatus status = getHttpStatus(request);
                message = "Unknown Exception But " + status.value() + " " + status.getReasonPhrase();
            }
            error = new Exception(message);
        }
        return error;
    }

    /**
     * 描述： 获取通信状态
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    public HttpStatus getHttpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
        try {
            return statusCode != null ? HttpStatus.valueOf(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    /**
     * 描述： 获取堆栈信息
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    public String getStackTraceInfo(Throwable error, boolean flag) {
        if (!flag) {
            return "omitted";
        }
        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        return stackTrace.toString();
    }

    /**
     * 描述： 判断是否包含堆栈轨迹
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    public boolean isIncludeStackTrace(HttpServletRequest request) {
        //读取错误配置(server.error.include-stacktrace=NEVER)
        ErrorProperties.IncludeStacktrace includeStacktrace = errorProperties.getIncludeStacktrace();
        //情况1：若IncludeStacktrace为ALWAYS
        if (includeStacktrace == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        //情况2：若请求参数含有trace
        if (includeStacktrace == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            String parameter = request.getParameter("trace");
            return parameter != null && !"false".equals(parameter.toLowerCase());
        }
        //情况3：其它情况
        return false;
    }

    /**
     * 描述： 保存异常信息
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, @Nullable Object handler, Exception ex) {
        request.setAttribute(ERROR_NAME, ex);
        return null;
    }

    /**
     * 描述： 提供优先级或用于排序
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/17 9:20
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
