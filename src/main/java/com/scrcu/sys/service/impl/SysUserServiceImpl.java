package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.apm.service.impl.SequenceService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.common.utils.SpringUtil;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.entity.SysUserGroupRel;
import com.scrcu.sys.entity.SysUserRoleRel;
import com.scrcu.sys.mapper.SysUserMapper;
import com.scrcu.sys.service.SysOrganizationService;
import com.scrcu.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 用户信息接口层实现类
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/29 22:24
 */
@Service("sysUserServiceImpl")
public class SysUserServiceImpl
        extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SequenceService sequenceService;

    @Override
    public boolean insert(SysUser sysUser) throws Exception {
        boolean bool = false;
        try {
            if (null != sysUser) {
                sysUser.setPwd(CommonUtil.getMd5Pwd(sysUser.getPwd()));
            }
            int count = this.baseMapper.insert(sysUser);
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean delete(String userId) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.deleteById(userId);
            this.baseMapper.deleteUserGroupRel(userId);  //删除用户用户组关联数据
            this.baseMapper.deleteUserRoleRel(userId);   //删除用户用户角色关联数据
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean update(SysUser sysUser) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.updateById(sysUser);
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return bool;
    }

    @Override
    public SysUser getById(String userId) {
        SysUser sysUser = this.baseMapper.selectById(userId);
        return sysUser;
    }

    @Override
    public DataGrid getByPage(PageParameters pageParameters, SysUser sysUser) {
        Page<SysUser> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());

      /*  if (!CommonUtil.isEmpty(sysUser.getUserId())) {
            queryWrapper.eq("user_id", sysUser.getUserId());
        }
        if (!CommonUtil.isEmpty(sysUser.getUserNm())) {
            queryWrapper.like("user_nm", sysUser.getUserNm());
        }
        if (!CommonUtil.isEmpty(sysUser.getStus())) {
            queryWrapper.eq("stus", sysUser.getStus());
        }
        if (null != orgList && orgList.size() > 0) {
            queryWrapper.in("BLNGTO_ORG_NO", orgList);
        }*/
        //queryWrapper.orderByDesc("user_id");
        try{
            page.setRecords(this.baseMapper.selectPageList(page,sysUser));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("用户组列表：用户对应用户组列表查询失败");
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }

    @Override
    public DataGrid getByPage_rewrite(PageParameters pageParameters,SysUser user, List<SysOrganization> orgList) {
        Page<SysUser> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        try{
            page.setRecords(this.baseMapper.selectPageList_rewrite(page,user,orgList));
            List<SysUser> list = page.getRecords();
            for (SysUser sysUser : list){
                sysUser.setStus(EhcacheUtil.getSingleSysDictryCdByCache(sysUser.getStus(),"SYS05").getDictryNm());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }

    @Override
    public boolean isExists(String userId) {
        boolean bool = true;
        SysUser sysUser = this.baseMapper.selectById(userId);
        if (sysUser == null) {
            bool = false;
        }
        return bool;
    }

    @Override
    public boolean changeStus(String userId) throws Exception {
        SysUser sysUser = this.baseMapper.selectById(userId);
        try {
            if (sysUser != null) {
                if ("SYS0501".equalsIgnoreCase(sysUser.getStus())) {
                    sysUser.setStus("SYS0503");
                } else if ("SYS0503".equalsIgnoreCase(sysUser.getStus())) {
                    sysUser.setStus("SYS0501");
                }
                this.baseMapper.updateById(sysUser);
                return true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean restPassword(String userId) throws Exception {
        SysUser sysUser = this.baseMapper.selectById(userId);
        try {
            if (sysUser != null) {
                String md5Pwd = new Md5Hash("12345678").toHex();
                sysUser.setPwd(md5Pwd);
                this.baseMapper.updateById(sysUser);
                return true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return false;
    }

    @Override
    public IPage<SysUser> getOemEmplyByPage(Page<SysUser> page, String userId, String userNm) throws Exception {
        IPage<SysUser> iPage;
        try {
            iPage = this.baseMapper.getOemEmplyByPage(page, userId, userNm);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return iPage;
    }

    @Override
    public SysUser getOemEmplyById(String userId) {
        SysUser sysUser = null;
        try {
            sysUser = this.baseMapper.getOemEmplyById(userId);
        } catch (Exception e) {
            throw new BaseException(e);
        }
        return sysUser;
    }

    @Override
    public IPage<SysUser> getShowUser(Page<SysUser> page, SysUser sysUser, String roleId, String groupId) throws Exception {
        IPage<SysUser> iPage;
        try {
            QueryWrapper queryWrapper = new QueryWrapper();
            if (!CommonUtil.isEmpty(sysUser.getUserNm())) {
                queryWrapper.like("USER_NM", sysUser.getUserNm());
            }
            if (!CommonUtil.isEmpty(sysUser.getStus())) {
                queryWrapper.eq("STUS", sysUser.getStus());
            }
            if (!CommonUtil.isEmpty(roleId)) {
                queryWrapper.exists("SELECT 1 FROM SYS_USER_ROLE_REL B " +
                        "WHERE SYS_USER.USER_ID=B.USER_ID AND B.ROLE_ID='"+roleId+"'");
            }
            if (!CommonUtil.isEmpty(groupId)) {
                queryWrapper.exists("SELECT 1 FROM SYS_USER_GROUP_REL B " +
                        "WHERE SYS_USER.USER_ID=B.USER_ID AND B.GROUP_ID='"+groupId+"'");

            }
            queryWrapper.orderByDesc("USER_ID");
            iPage = this.baseMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return iPage;
    }

    @Override
    public DataGrid getShowRoleUser(PageParameters pageParameters,String roleId, SysUser sysUser) throws Exception {
        Page<SysUser> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        try{
            sysUser.setTelNo(roleId);
            page.setRecords(this.baseMapper.selectUserList(page,sysUser));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("用户列表：角色对应用户列表查询失败");
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }
    @Override
    public boolean saveUserRoleRel(String userId, List<String> roleIds) throws Exception {
        if (null == roleIds || null == userId) {
            return false;
        }
        boolean bool = false;
        int count = 1;
        try {
            //-删除
            this.baseMapper.delUserRoleByUserId(userId);
            //--新增
            String id = this.baseMapper.selectMaxId("sys_user_role_rel");
            if (id == null || "".equals(id)){
                id = "1000001";
            }
            List<SysUserRoleRel> relList = new ArrayList<>();
            for (String roleId : roleIds) {
                SysUserRoleRel rel = new SysUserRoleRel();
                rel.setId(Integer.parseInt(id) + count);
                rel.setUserId(userId);
                rel.setRoleId(roleId);
                relList.add(rel);
                System.out.println(rel);
                count ++;
            }
            this.baseMapper.insertUserRole_re(relList);
            if (count > 1) {
                bool = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean saveUserGroupRel(String userId, List<String> groupIds) throws Exception {
        /*if (null == groupIds || null == userId) {
            return false;
        }*/
        boolean bool = false;
        int count = 1;
        try {
            //-删除
            this.baseMapper.delUserGroupByUserId(userId);
            //-新增
            if (groupIds != null && groupIds.size() > 0) {
                List<SysUserGroupRel> relList = new ArrayList<>();
                String id = this.baseMapper.selectMaxId("sys_user_group_rel");
                if (id == null || "".equals(id)){
                    id = "1000001";
                }
                for (String groupId : groupIds) {
                    SysUserGroupRel rel = new SysUserGroupRel();
                    rel.setId(Integer.parseInt(id)+count);
                    rel.setUserId(userId);
                    rel.setGroupId(groupId);
                    relList.add(rel);
                    count ++;
                }
                this.baseMapper.insertUserGroup_re(relList);
            }
            if (count >= 1) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    //----------------------------pjt-------------------------------------
    /**
     *
     * @param pageParameters 分页
     * @param user 查询用户信息
     * @param roleLimit 是否有用户角色限制：null表示无角色限制，不为Null且有具体角色编号是，为指定的角色限制
     * @param haveRemoved
     * @param orgController true表示控制机构范围，false表示不控制机构范围
     * @return
     */
    @Override
    public DataGrid getSysUserByCommon(PageParameters pageParameters, SysUser user, String roleLimit, String haveRemoved, String orgController) {
        Page<SysUser> page = null;
        List<SysOrganization> orgList = null;
        //-机构权限控制
        if ("true".equals(orgController)){
            orgList = openOrganizationAuthorityBySysUser(user);
            user.setTelNo("true");//替代属性，标志机构权限控制
        }
        //-角色限制
        user = getUserRoleLimit(user,roleLimit);
        //-是否包括已注销用户
        user = getUserStatus(user,haveRemoved);
        try{
            page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
            page.setRecords(this.baseMapper.getSysUserByCommon(page,user,orgList));
            List<SysUser> list = page.getRecords();
            for (SysUser sysUser : list){
                sysUser.setStus(EhcacheUtil.getSingleSysDictryCdByCache(sysUser.getStus(),"SYS05").getDictryNm());
            }
            page.setRecords(list);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("系统用户查询：系统用户分页查询失败");
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }

    @Override
    public String selectMaxId(String table) {
        return this.baseMapper.selectMaxId(table);
    }

    /**
     * 描述：用户的角色控制
     * @param user
     * @param roleLimit
     * @return
     * @author pengjuntao
     */
    public SysUser getUserRoleLimit(SysUser user,String roleLimit){
        if (roleLimit != null && !"".equals(roleLimit)){
            user.setCreatr(roleLimit);
        }
        return user;
    }

    /**
     * 描述：用户的状态控制（是否需要查询已注销的用户）
     * @param user
     * @param haveRemoved
     * @return
     * @author pengjuntao
     */
    public SysUser getUserStatus(SysUser user,String haveRemoved){
        if (haveRemoved != null && !"".equals(haveRemoved) && "false".equals(haveRemoved)){
            user.setStus("SYS0503");
        }
        return user;
    }
    /**
     * 描述：开启用户的机构权限控制，获取机构集合
     * @return
     * @author pengjuntao
     */
    public List<SysOrganization> openOrganizationAuthorityBySysUser(SysUser user){
        SysOrganizationService sysOrganizationService =
                (SysOrganizationService) SpringUtil.getBean("sysOrganizationServiceImpl");
        //-获取当前用户的机构权限机构
        return CommonUtil.getAllOrgInfoByTop(CommonUtil.getTopOrgInfo(sysOrganizationService.getSysOrgInfoFromUser(user)));
    }




    /**
     * 获取指定机构资源的下级机构资源id
     * @param list
     * @return
     */
    public List<SysOrganization> getSysOrgInfoAll(List<SysOrganization> list){
        List<SysOrganization> resultList = new ArrayList<>();
        for (SysOrganization org : list){
            //获取当前机构的所有下级机构
            if (org != null){
                List<SysOrganization> orgList = this.baseMapper.getNextLvlSysOrgInfoFromUser(org);
                resultList.addAll(orgList);
            }
        }
        return  resultList;
    }


}
