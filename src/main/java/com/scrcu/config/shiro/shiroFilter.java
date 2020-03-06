package com.scrcu.config.shiro;


import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述： 自定义的filter过滤器,重写了onAccessDenied方法，对ajax增加了判断
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/16 15:38
 */
public class shiroFilter extends FormAuthenticationFilter {

    //保存user和session对象的映射
//    public static Map<String, Session> userSessionMap = new ConcurrentHashMap<>();

//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
//                                      Object mappedValue) {
//        System.out.println("isLoginRequest"+isLoginRequest(request, response));
//        System.out.println("isLoginSubmission"+isLoginSubmission(request, response));
//        if (!isLoginRequest(request, response)) {
//            if (isLoginSubmission(request, response)) {
//                Subject subject = this.getSubject(request, response);
//                String username = this.getUsername(request);
//                SysUser sysUser = (SysUser) subject.getPrincipal();
//                System.out.println("username:"+username);
//                System.out.println("sysUser:"+sysUser.getUserId());
////                if(sysUser != null) {
////                    Session session = subject.getSession();
////                    System.out.println("sessionid：" + session.getId());
////                    userSessionMap.put(sysUser.getUserId(), session);
////                    boolean isExist = false;
////                    for (String before : userSessionMap.keySet()) {
////                        Session beforeSession = userSessionMap.get(before);
////                        if (before.equals(sysUser.getUserId()) && session != beforeSession) {
////                            return false;
////                        }
////                    }
////                }
//            }
//        }
//        return super.isAccessAllowed(request, response, mappedValue);
//    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            if (this.isAjax(httpRequest)) {
                HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                httpServletResponse.setHeader("sessionSts", "timeout");
                httpServletResponse.sendError(401);
                return false;
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    boolean isAjax(HttpServletRequest request){
        return (request.getHeader("X-Requested-With") != null
                && "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) ;
    }

}
