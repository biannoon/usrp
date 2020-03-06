package com.scrcu.common.servlet;

import com.alibaba.fastjson.JSONObject;
import com.scrcu.common.utils.EhcacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述： 系统初始化servlet
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/10 11:06
 */
@Configuration
public class InitSysServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 描述： springboot注册自定义servlet配置
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 11:06
     */
    @Bean
    public ServletRegistrationBean myServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new InitSysServlet());
        registrationBean.setName("initSysServlet");
        registrationBean.addUrlMappings("/initSysServlet");
        registrationBean.setLoadOnStartup(0);
        return registrationBean;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /**
     * 描述： 刷新缓存时调用的方法
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 11:06
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        try {
            String initFlags = req.getParameter("initflag");
            for (String initflag : initFlags.substring(0,initFlags.lastIndexOf(",")).split(",")) {
                if ("dictry".equalsIgnoreCase(initflag)) {
                    EhcacheUtil.cacheRemove(EhcacheUtil.CACHE_DICTRY, EhcacheUtil.SYS_DICTRY_CD);
                    EhcacheUtil.initDictryCd();
                } else if ("func".equalsIgnoreCase(initflag)) {
                    EhcacheUtil.cacheRemove(EhcacheUtil.CACHE_PARM, EhcacheUtil.SYS_FUNC);
                    EhcacheUtil.initSysFunc();
                } else if ("tabconfig".equalsIgnoreCase(initflag)) {
                    EhcacheUtil.cacheRemove(EhcacheUtil.CACHE_PARM, EhcacheUtil.SYS_TAB_CONFIG);
                    EhcacheUtil.initSysTabConfig();
                } else if ("baseOrg".equalsIgnoreCase(initflag)) {
                    EhcacheUtil.cacheRemove(EhcacheUtil.CACHE_DICTRY, EhcacheUtil.SYS_ORG_INFO);
                    EhcacheUtil.initSysOrgInfo();
                } else if ("authoritory".equalsIgnoreCase(initflag)){
                    EhcacheUtil.cacheRemove(EhcacheUtil.CACHE_PARM, EhcacheUtil.SYS_FUNC + "auth");
                    EhcacheUtil.initAuthoritoryManageFunction();
                }
            }
            json.put("result","success");
        } catch (Exception e) {
            json.put("result","error");
            throw new ServletException(e.getMessage());
        } finally {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.write(json.toString());
            out.flush();
            out.close();
        }
    }

    /**
     * 描述： 初始化系统参数以及缓存信息等需要启动加载的内容
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 11:06
     */
    @Override
    public void init() throws ServletException {
        super.init();
        logger.info("####################初始化系统参数-开始#######################");
        logger.info("####################初始化加载系统缓存开始");
        try {
            EhcacheUtil.init();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("####################初始化加载系统缓存失败");
        }
        logger.info("####################初始化加载系统缓存完成");
        logger.info("####################初始化系统参数-完成#######################");
    }
}
