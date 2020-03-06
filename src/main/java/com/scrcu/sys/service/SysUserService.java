package com.scrcu.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;

import java.util.List;

/**
 * 描述： 用户信息接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 19:49
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 描述： 用户信息新增
     * @param sysUser
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    boolean insert(SysUser sysUser) throws Exception;

    /**
     * 描述： 用户信息删除
     * @param userId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    boolean delete(String userId) throws Exception;

    /**
     * 描述： 用户信息修改
     * @param sysUser
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    boolean update(SysUser sysUser) throws Exception;

    /**
     * 描述： 根据主键查询用户信息
     * @param userId
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    SysUser getById(String userId) throws Exception;

    /**
     * 描述： 用户信息分页查询
     * @param page
     * @param queryWrapper
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    DataGrid getByPage(PageParameters page, SysUser sysUser) throws Exception;


    /**
     * 描述：重写用户信息分页查询
     * @param page
     * @param user      用户查询条件实体
     * @param orgList   权限机构集合
     * @return
     * @author pengjuntao
     */
    DataGrid getByPage_rewrite(PageParameters page,SysUser user, List<SysOrganization> orgList);

    /**
     * 描述： 判断用户是否存在
     * @param userId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    boolean isExists(String userId) throws Exception;

    /**
     * 描述： 用户状态修改
     * @param userId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    boolean changeStus(String userId) throws Exception;

    /**
     * 描述： 用户密码重置（默认12345678）
     * @param userId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    boolean restPassword(String userId) throws Exception;

    /**
     * 描述： 获取员工池分页列表
     * @param page
     * @param userId
     * @param userNm
     * @return IPage
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    IPage<SysUser> getOemEmplyByPage(Page<SysUser> page, String userId, String userNm) throws Exception;

    /**
     * 描述： 从员工池中选中员工信息
     * @param userId
     * @return sysUser
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:49
     */
    SysUser getOemEmplyById(String userId) throws Exception;

    /**
     * 描述： 根据角色编号或用户组编号查询赋权的用户集合
     * @param id
     * @return Ipage
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    IPage<SysUser> getShowUser(Page<SysUser> page, SysUser sysUser, String roleId, String groupId) throws Exception;
    /**
     * 描述： 根据角色编号查询用户集合
     * @param id
     * @return Ipage
     * @创建人： hepengfei
     * @创建时间： 2019/11/20 19:50
     * */
    public DataGrid  getShowRoleUser(PageParameters page, String roleId, SysUser sysUser)throws Exception  ;

    /**
     * 描述： 保存用户和角色之间的关系
     * @param id
     * @param list
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    boolean saveUserRoleRel(String userId, List<String> roleIds) throws Exception;

    /**
     * 描述： 保存用户和用户组直接的关系
     * @param userId
     * @param list
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    boolean saveUserGroupRel(String userId, List<String> groupIds) throws Exception;

    /**
     * 系统共用组件：初始化系统用户
     * @param user 查询用户信息
     * @param pageParameters 分页
     * @param roleLimit 是否有用户角色限制：null表示无角色限制，不为Null且有具体角色编号是，为指定的角色限制
     * @author pengjuntao
     * @return
     */
    DataGrid getSysUserByCommon(PageParameters pageParameters, SysUser user, String roleLimit, String haveRemoved, String orgController);


    /**
     * 描述：获取表中Id的最大值
     * @param table
     * @return
     */
    String selectMaxId(String table);
}
