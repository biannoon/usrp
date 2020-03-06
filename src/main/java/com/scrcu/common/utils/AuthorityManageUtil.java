package com.scrcu.common.utils;

import com.scrcu.sys.entity.SysFunc;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysFuncService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AuthorityManageUtil
 * @Description 权限控制工具类
 * @Author pengjuntao
 * @Date 2019/12/13 15:28
 * @Version 1.0
 */
public class AuthorityManageUtil {

    /**
     * 描述：通过用户获取操作页面及按钮的id集合，放入缓Ehcache缓存中
     * @param user 用户
     * @return
     */
    public static void getAllAuthoritySysFuncByUser(SysUser user){
        SysFuncService sysFuncService =
                (SysFuncService) SpringUtil.getBean("sysFuncServiceImpl");
        List<String> authSysFuncList = null;
        if (EhcacheUtil.getCache(EhcacheUtil.CACHE_OTHER,EhcacheUtil.SYS_FUNC + "auth") != null &&
                ((List<String>)EhcacheUtil.getCache(EhcacheUtil.CACHE_OTHER,EhcacheUtil.SYS_FUNC + "auth")).size() > 0){
           EhcacheUtil.cacheRemove(EhcacheUtil.CACHE_OTHER,EhcacheUtil.SYS_FUNC + "auth");
        }
        //-获取页面按钮集合
        authSysFuncList = sysFuncService.getSysFuncByUserId(user.getUserId());
        //-将权限功能菜单添加到Ehcache缓存中，以便后续使用
        EhcacheUtil.cacheAble(EhcacheUtil.CACHE_OTHER,EhcacheUtil.SYS_FUNC + "auth",authSysFuncList);
    }

    /**
     * 获取符合执行权限的功能集合
     * @param funcId
     * @return
     */
    public static List<SysFunc> getAuthorityManageSysFuncByFuncId(SysUser user, String funcId){
        List<SysFunc> returnList = new ArrayList<>();
        List<String> tempReturnList = new ArrayList<>();
        //-从Ehcache缓存中获取功能菜单
        List<SysFunc> sysFuncList = EhcacheUtil.getSysFuncByCache(funcId);
        //-从Ehcache缓存中获取用户角色
        List<SysRole> roleList = (List<SysRole>) EhcacheUtil.getCache(EhcacheUtil.CACHE_PARM,EhcacheUtil.SYS_USER_ROLE + user.getUserId());
        if (sysFuncList != null && sysFuncList.size() > 0) {
            for (SysFunc func : sysFuncList) {
                for (SysRole role : roleList) {
                    List<String> authList = (List<String>) EhcacheUtil.getCache(EhcacheUtil.CACHE_PARM, EhcacheUtil.SYS_ROLE_FUNC_REL + role.getRoleId());
                    if (authList.contains(func.getFuncId()) && !tempReturnList.contains(func.getFuncId())) {
                        returnList.add(func);
                        tempReturnList.add(func.getFuncId());
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * 描述：将符合执行权限的功能集合发送到前端页面
     */
    public static void sendAuthorityManageSysFuncListToJsp(HttpServletRequest request,SysUser user, String funcId){
        request.setAttribute("buttonList",getAuthorityManageSysFuncByFuncId(user, funcId));
    }
}
