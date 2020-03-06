package com.scrcu.config.shiro;

import com.scrcu.listener.ShiroSessionListener;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * 描述： shrio配置类
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 10:09
 */
@Configuration
public class ShiroConfiguration {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

  /*  //-cas服务器登陆ip地址
    public static final String casServerUrlPrefix = "http://localhost";
    //-cas登陆页面地址
    public static final String casLoginUrl = casServerUrlPrefix + "/login";
    //-cas登出页面地址
    public static final String casLogoutUrl = casServerUrlPrefix + "/logout";
    //-当前项目对外提供的服务地址
    public static final String shiroServerUrlPrefix = "";
    //-casFilter UrlPattern
    public static final String casFilterUrlPattern = "/cas";
    //-登陆地址
    public static final String loginUrl = casLoginUrl + "?service=" + shiroServerUrlPrefix + casFilterUrlPattern;
    //-登出地址
    public static final String logoutUrl = casLogoutUrl + "?service=" + shiroServerUrlPrefix;
    //-登陆成功后跳转地址
    public static final String loginSuccessUrl = "/index";
    //-权限认证失败跳转地址
    public static final String unauthorizedUrl = "/error/403.html";*/


    /**
     * 描述： 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 描述： 凭证匹配器
     *      由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *      所以我们需要修改下doGetAuthenticationInfo中的代码
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5("")) 默认是1
        //hashedCredentialsMatcher.setHashIterations(2);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 描述： 注入自定义的realm，告诉shiro如何获取用户信息来做登录或权限控制
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean
    public ShiroRealm selfShiroRealm() {
        logger.info("shiro自定义realm权限登录器已加载");
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * 描述： 配置ehcache缓存管理器
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean(name = "ehCacheManager")
    public EhCacheManager ehCacheManager() {
       return new EhCacheManager();
    }

    /**
     * 描述： 配置session监听
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean(name = "sessionListener")
    public ShiroSessionListener sessionListener() {
        ShiroSessionListener sessionListener = new ShiroSessionListener();
        return sessionListener;
    }

    /**
     * 描述： 配置session会话ID生成器
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * 描述： SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO ecsDAO = new EnterpriseCacheSessionDAO();
        ecsDAO.setCacheManager(ehCacheManager());
        ecsDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        ecsDAO.setSessionIdGenerator(sessionIdGenerator());
        return ecsDAO;
    }

    /**
     * 描述： 配置保存sessionId的cookie，这里的cookie记住我的cookie，记住我需要一个cookie session管理也需要自己的cookie
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean(name = "simpleCookie")
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * 描述： 配置session会话管理器，设定会话超时及保存
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(simpleCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(ehCacheManager());
        //全局会话超时时间（单位毫秒），默认30分钟
        sessionManager.setGlobalSessionTimeout(3000000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler
        // 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        sessionManager.setSessionValidationInterval(3000000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 描述： 配置核心安全事务管理器
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:12
     */
    @Bean
    public SecurityManager securityManager() {
        logger.info("shiro核心安全事务管理器已经加载");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置SecurityManager并注入ShiroRealm
        securityManager.setRealm(selfShiroRealm());
        // 配置rememberMeCookie
        //securityManager.setRememberMeManager(rememberMeManager());
        // 配置缓存管理类 cacheManager
        securityManager.setCacheManager(ehCacheManager());
        // 配置session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 描述： Shiro的Web过滤器Factory
     * @param securityManager
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 11:41
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        logger.info("进入shiro的web过滤器factory");
        // 定义shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 默认登录的url，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后跳转的url
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 设置未授权url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // 添加自定义拦截器
        LinkedHashMap<String, Filter> selfFilter = new LinkedHashMap();
        selfFilter.put("selfFilter", new shiroFilter());
        shiroFilterFactoryBean.setFilters(selfFilter);
        // 定义拦截器，LinkedHashMap是有序的，进行顺序拦截器配置
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/userLogin", "anon");
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        // 配置门户系统登录url
        //filterChainDefinitionMap.put("/protal","");
        // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/**", "selfFilter,authc");
        // 设置shiroFilterFactoryBean的FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 描述： 开启shiro的aop注解支持；使用代理方式所以需要开启代码支持
     * @param securityManager
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:33
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationASA(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationASA = new AuthorizationAttributeSourceAdvisor();
        authorizationASA.setSecurityManager(securityManager);
        return authorizationASA;
    }


    //--------------------------------------------------------
    /*@Bean
    public MyShiroCasRealm myShiroCasRealm(EhCacheManager ehCacheManager){
        MyShiroCasRealm realm = new MyShiroCasRealm();
        realm.setCacheManager(ehCacheManager);
        realm.setCasServerUrlPrefix(ShiroConfiguration.casServerUrlPrefix);
        realm.setCasService(ShiroConfiguration.shiroServerUrlPrefix + ShiroConfiguration.casServerUrlPrefix);
        return realm;
    }

    *//**
     * 描述：注册单点登出listener
     * @return
     *//*
    @Bean
    public ServletListenerRegistrationBean singleSignOutHttpSessionListener(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SingleSignOutHttpSessionListener());
        bean.setEnabled(true);
        return bean;
    }

    *//**
     * 描述：注册单点登出的filter
     * @return
     *//*
    @Bean
    public FilterRegistrationBean singleSignOutFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        bean.setFilter(new SingleSignOutFilter());
        //bean.setUrlPatterns();
        bean.setEnabled(true);
        return bean;
    }

    *//**
     * 描述：注册DelegatingFilterProxy
     * @return
     *//*
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new DelegatingFilterProxy("shiroFilter"));
        bean.addInitParameter("targetFilterLifecycle","true");
        bean.setEnabled(true);
        //bean.setUrlPatterns("");
        return bean;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager manager){
        AuthorizationAttributeSourceAdvisor advisor =
                new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    *//**
     * 描述：注册CAS过滤器
     * @return
     *//*
    @Bean(name="casFilter")
    public CasFilter getCasFilter(){
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        //-设置登陆失败后的跳转url;这里设置为认证失败后再次打开登陆页面
        casFilter.setFailureUrl(loginUrl);
        return casFilter;
    }


    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager manager,
                                                            CasFilter casFilter,
                                                            SysUserService sysUserService){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //-设置安全管理器
        bean.setSecurityManager(manager);
        //-设置项目登陆的url，如果不设置，将会自动寻找web项目根目录下的‘login.jsp’页面
        bean.setLoginUrl(loginUrl);
        //-设置登陆成功后跳转的url
        bean.setSuccessUrl(loginSuccessUrl);
        //-设置权限授权失败跳转的url
        bean.setUnauthorizedUrl(unauthorizedUrl);
        //-添加casFilter过滤器到安全管理中
        Map<String,Filter> map = new HashMap<>();
        map.put("casFilter",casFilter);
        bean.setFilters(map);

        loadShiroFilterChain(bean,sysUserService);
        return bean;
    }


    public void loadShiroFilterChain(ShiroFilterFactoryBean bean, SysUserService sysUserService){

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //-shiro集成cas后，首先添加该规则
        filterChainDefinitionMap.put(casFilterUrlPattern,"casFilter");
        //-不拦截的请求
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/logout","anon");
        filterChainDefinitionMap.put("/error","anon");
        //-拦截的请求
        filterChainDefinitionMap.put("/user", "authc"); //需要登录
        filterChainDefinitionMap.put("/user/add/**", "authc,roles[admin]"); //需要登录，且用户角色为admin
        filterChainDefinitionMap.put("/user/delete/**", "authc,perms[\"user:delete\"]"); //需要登录，且用户有权限为user:delete
        //-登陆过的不拦截
        filterChainDefinitionMap.put("/**", "user");

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);


    }
*/




    /**
     * 描述： 注册全局异常处理
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:33
     */
//    @Bean(name = "GlobalExceptionHandler")
//    public HandlerExceptionResolver handlerExceptionResolver() {
//        return new GlobalExceptionHandler();
//    }

}
