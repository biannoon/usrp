package com.scrcu.config.shiro;

import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysRoleService;
import com.scrcu.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述： shiro身份验证核心类
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 10:15
 */
public class ShiroRealm extends AuthorizingRealm {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    /**
     * 描述： 用户登录认证,定义如何获取用户信息的业务逻辑,给shiro做登录
     * @param token
     * @return
     * @throws AuthenticationException
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:15
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        logger.info("-----------用户登录认证---------");
        String userId = (String) token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        String md5Pwd = new Md5Hash(password).toHex();
        if (userId == null) {
            throw new AccountException();
        }
        SysUser sysUser = null;
        try {
            sysUser = this.sysUserService.getById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (sysUser == null) {
            throw new UnknownAccountException();
        }
        //-判断用户状态
        if (!sysUser.getStus().equals("SYS0501")) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo saInfo =
                new SimpleAuthenticationInfo(sysUser,md5Pwd, getName());
        return saInfo;
    }

    /**
     * 描述：用户访问控制（授权）
     * 本方法目前只做了角色层的访问控制，并未做资源层的控制，
     * 资源层的控制由角色决定
     * @param principals
     * @return
     * @author pengjuntao
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("---------用户访问控制--------");
        //-用户登陆用户信息
        SysUser user = (SysUser) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<SysRole> roleList = null;
        try {
            //-获取用户所对应的角色集合
            roleList = sysRoleService.getRoleListByUserId(user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> roleSet = new HashSet<>();
        for (SysRole role : roleList){
            roleSet.add(role.getRoleId());
        }
        authorizationInfo.addRoles(roleSet);
        return authorizationInfo;
    }

    /**
     * 描述： 用户授权,定义如何获取用户的角色和权限的逻辑,给shiro做权限判断
     * @param principals
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 10:15
     *//*
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("------------用户授权认证---------");
        SysUser sysUserInfo = (SysUser) getAvailablePrincipal(principals);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<SysRole> sysRoleInfoList = null;
        try{
            sysRoleInfoList = this.sysRoleService.getRoleListByUserId(sysUserInfo.getUserId());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        Set<String> set = new HashSet<>();
        for (SysRole sysRole : sysRoleInfoList) {
            set.add(sysRole.getRoleNm());
        }
        Set<String> perms = new HashSet<String>();
        List<Permission> list1 = this.iPermissionService.findRolePerm(user.getNickname());
        for(Permission p : list1) {
            perms.add(p.getUrl());
        }
        info.setRoles(set);//添加角色集合   @RequireRoles("admin")会到info中寻找 字符串 "admin"
        info.setStringPermissions(perms);// 添加权限集合 @RequiresPermissions("test") 会到info中寻找字符串"test"
        return info;
        return null;
    }*/
}
