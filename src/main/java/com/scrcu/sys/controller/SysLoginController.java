package com.scrcu.sys.controller;

import com.scrcu.common.utils.CZenithDecrypt;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysRoleService;
import com.scrcu.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 描述： 用户登录控制器，没有创建相应的接口以及实现类，所以登录逻辑写在这里
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/1 10:14
 */
@Controller
@RequestMapping("/")
public class SysLoginController {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 描述： 用户登录进入默认登录页面
     * @param
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/1 10:14
     */
    @RequestMapping("login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
    /**
     * 描述： 用户登录进入默认登录页面
     * @param request
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/1 10:14
     */
    @RequestMapping("userLogin")
    public ModelAndView userLogin(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String msg = "";
        Subject subject = SecurityUtils.getSubject();
        //-获取每个用户实例的session
        Session session = subject.getSession();
        ModelAndView mv = new ModelAndView();
        try {
            //-创建用户信息凭证，进行用户认证
            UsernamePasswordToken userToken = new UsernamePasswordToken(username,password);
            //-用户认证
            subject.login(userToken);
            //-认证成功后，将用户信息添加到session中
            SysUser sysUser = subject.getPrincipals().oneByType(SysUser.class);
            session.setAttribute("sysUser", sysUser);
            //-查询登陆用户的角色信息，并存入Ehcache中
            List<SysRole> roleList = sysRoleService.getRoleListByUserId(sysUser.getUserId());
            EhcacheUtil.cacheAble(EhcacheUtil.CACHE_PARM, EhcacheUtil.SYS_USER_ROLE + sysUser.getUserId(), roleList);
            mv.setViewName("index");
        } catch (UnknownAccountException e) {
            msg = "账号:" + username + "不存在";
            logger.error(this.getClass() + msg);
            mv.setViewName("login");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            msg = "账号:" + username + "密码不正确";
            logger.error(this.getClass() + msg);
            mv.setViewName("login");
        } catch (DisabledAccountException e) {
            msg = "账号:" + username + "已被禁用";
            logger.error(this.getClass() + msg);
            mv.setViewName("login");
        } catch (Exception e) {
            msg = "账号:" + username + "异常，请联系管理员";
            logger.error(this.getClass() + msg);
            mv.setViewName("login");
        }
        mv.getModel().put("message", msg);
        return mv;
    }

    /**
     * 描述： 用户登录进入默认登录页面
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/1 10:14
     */
    @RequestMapping("logout")
    public ModelAndView userLogOut(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:jsp/login.jsp");
        return mv;
    }

    /**
     * 描述：门户系统跳转登录
     * @return
     */
    @RequestMapping("protal")
    public ModelAndView userLoginByProtal(HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        String msg = "";
        //-判断请求是否是从门户系统过来的
        String referer = request.getParameter("Referer");
        if (referer != null && referer.indexOf("http://10.0.193.91:8001/protal/server.pt") != -1){
            //-获取用户的信息
            String sid = request.getParameter("sid") == null?"":request.getParameter("sid");
            if (!"".equals(sid)){
                try {
                    //-解密
                    String userId = CZenithDecrypt.decrypt(sid);
                    SysUser user = sysUserService.getById(userId);
                    if (user == null){
                        msg = "账号：" + user.getUserId() + " 不存在，请联系系统管理员";
                        mv.setViewName("");
                    } else if("SYS0502".equals(user.getStus())){
                        msg = "账号：" + user.getUserId() + " 已冻结，无法访问系统，请联系系统管理员";
                        mv.setViewName("");
                    } else if ("SYS0503".equals(user.getStus())){
                        msg = "账号：" + user.getUserId() + " 已注销，无法访问系统，请联系系统管理员";
                        mv.setViewName("");
                    } else {
                        //-将用户账号信息存入session中
                        Session session = SecurityUtils.getSubject().getSession();
                        session.setAttribute("sysUser",user);
                        //-查询登陆用户的角色信息，并存入Ehcache中
                        List<SysRole> roleList = sysRoleService.getRoleListByUserId(user.getUserId());
                        EhcacheUtil.cacheAble(EhcacheUtil.CACHE_PARM, EhcacheUtil.SYS_USER_ROLE + user.getUserId(), roleList);
                        mv.setViewName("index");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = " 访问出现异常，请联系系统管理员";
                }
            }
        }
        mv.getModel().put("message",msg);
        return mv;
    }
}
