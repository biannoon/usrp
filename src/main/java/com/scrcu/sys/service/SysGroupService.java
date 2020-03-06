package com.scrcu.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.DataList;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysGroup;
import com.scrcu.sys.entity.SysGroupRecoursRel;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysUser;

import java.util.List;

/**
 * 描述： 用户组接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/19 10:21
 */
public interface SysGroupService extends IService<SysGroup> {
    //-----------------------------pengjuntao重写用户组模块功能---------------------

    /**
     * 描述：初始化用户组列表数据
     * @param sysGroup
     * @param pageParameters
     * @return
     */
    DataGrid listSysGroup(SysGroup sysGroup, PageParameters pageParameters);

    /**
     * 描述：初始化用户组对应的资源列表
     * @param sysGroup
     * @param pageParameters
     * @return
     */
    DataGrid listRecoursByGroupId(SysGroupRecoursRel sysGroupRecoursRel, PageParameters pageParameters);

    /**
     * 描述：获取指定用户组的所有资源，不分页查询
     * @return
     */
    List<SysGroupRecoursRel> listRecoursByGroupIdWithoutPage(SysGroup sysGroup);

    /**
     * 描述：根据用户组ID，初始化用户列表数据
     * @param groupId
     * @param pageParameters
     * @author pengjuntao
     * @return
     */
    DataGrid listSysUserByGroupId(String groupId, PageParameters pageParameters);

    /**
     * 描述：新增用户组
     * @param sysGroup
     * @return
     */
    AjaxResult insertSysGroup(SysGroup sysGroup);

    /**
     * 描述：修改用户组
     * @param sysGroup
     * @return
     */
    AjaxResult updateSysGroup(SysGroup sysGroup);

    /**
     * 描述：删除用户组
     * @param groupId
     * @return
     */
    AjaxResult deleteSysGroup(String groupId);

    /**
     * 描述：获取指定用户组详情
     * @param groupId
     * @return
     */
    SysGroup getSysGroupById(String groupId);

    /**
     * 描述：新增用户组时，分配资源
     * 说明：此时选中的资源先存到内存中，并不直接存入数据库，等待具体新增的提交动作完成后再进行将该资源提交到数据库中
     * @param resources 资源ID（已_连接的资源集合）
     * @param resourceType 资源类型
     * @param groupId 用户组ID
     * @return
     */
    AjaxResult addSysGroupResources(String resources, String resourceType, String groupId);
    /**
     * 描述：从内存中删除已选定的用户组资源，同上方法的处理方式
     * @param resources 资源ID的集合
     * @param groupId 用户组ID
     * @return
     */
    AjaxResult deleteSysGroupResources(String resources, String groupId);
    /**
     * 描述：从数据库中删除选定的用户组资源
     * @param request
     * @param resources
     * @param groupId
     * @return
     */
    AjaxResult deleteSysGroupResourcesFromDB(String resources, String groupId);

    /**
     * 描述：直接添加用户组资源到数据库
     * @param resources
     * @param resourceType
     * @param groupId
     * @return
     */
    AjaxResult addSysGroupResourcesToDB(String resources, String resourceType, String groupId);

    /**
     * 描述：获取所有系统机构数据，不分级
     * @param id
     * @return
     */
    String listOrgInfoFromSysGroup(String id);

    /**
     * 描述：检查是否有用户已经分配了该用户组
     * @param request
     * @param groupId 用户组ID
     * @return
     */
    String isExistUserUnderGroup(String groupId);



    //----------------------------------jiyuanbo--------------------------------------------
    /**
     * 描述： 用户组新增
     * @param sysGroup
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    boolean insert(SysGroup sysGroup) throws Exception;

    /**
     * 描述： 用户组删除
     * @param sysGroup
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    boolean delete(String groupId) throws Exception;

    /**
     * 描述： 用户组修改
     * @param sysGroup
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    boolean update(SysGroup sysGroup) throws Exception;

    /**
     * 描述： 用户组主键查询
     * @param groupId
     * @return SysGroup
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    SysGroup getById(String groupId) throws Exception;

    /**
     * 描述： 用户组分页查询
     * @param page
     * @param sysGroup
     * @return IPage
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    IPage<SysGroup> getByPage(Page page, SysGroup sysGroup) throws Exception;

    /**
     * 描述： 判断用户组id是否已经存在
     * @param groupId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    boolean isExists(String groupId) throws Exception;

    /**
     * 描述： 用户组分配机构资源、子系统资源,需要按类型来进行添加
     * @param groupId
     * @param resouseList
     * @param resouseType
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    boolean saveGroupResouseRel(String groupId, List<String> resouseList, String resouseType) throws Exception;

    /**
     * 描述： 根据用户编号查询出该用户已拥有的用户组信息集合
     * @param userId
     * @return list
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    List<SysGroup> getGroupListByUserId(String userId) throws Exception;

    /**
     * 描述： 根据当前登录用户编号以及登录用户的角色集合查询出能够授权的用户组集合
     * @param groupList
     * @return list
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    List<SysGroup> getGroupListByLoginUser(String userId, List<SysRole> roleList) throws Exception;

    /**
     * 描述：根据当前登录用户编号以及登录用户能够授权的用户组集合
     * @param  sysUser
     * @return
     */
    List<SysGroup> getGroupListByLoginUser_re(SysUser sysUser);

    /**
     * 描述： 通过用户编号查询出该用户所属用户组下所有的机构资源
     * @param userId
     * @return list
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/19 10:21
     */
    List<String> getOrgResouseByUserId(String userId) throws Exception;

    /**
     * <p>
     * 获取与公告发布对象相关的用户组信息
     * </p>
     * @param pubNoticeId 公告id
     * @return java.util.List<com.scrcu.common.base.DataList>
     * @author wuyu
     * @date 2019/11/3 11:42
     **/
    List<DataList> getGroupListWithNotice(String pubNoticeId);

    /**
     * <p>
     * 获取不在公告发布对象中的用户组信息
     * </p>
     * @param pubNoticeId 公告id
     * @return java.util.List<com.scrcu.common.base.DataList>
     * @author wuyu
     * @date 2019/11/4 11:10
     **/
    List<DataList> getGroupNotInPubNotice(String pubNoticeId);
    /**
     * 描述： 根据用户编号查询用户组集合
     * @param id
     * @return Ipage
     * @创建人： hepengfei
     * @创建时间： 2019/11/20 19:50
     * */
    public DataGrid  getShowGroupUser(PageParameters page,String userId, SysGroup sysGroup)throws Exception  ;
}
