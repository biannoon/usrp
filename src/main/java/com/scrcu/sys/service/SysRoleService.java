package com.scrcu.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.DataList;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysUser;

import java.util.List;

/**
 * 描述： 角色信息接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 19:50
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 描述： 角色信息新增
     * @param SysRole
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    boolean insert(SysRole sysRole) throws Exception;

    /**
     * 描述： 角色信息删除
     * @param SysRole
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    boolean delete(String roleId) throws Exception;

    /**
     * 描述： 角色信息修改
     * @param SysRole
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    boolean update(SysRole sysRole) throws Exception;

    /**
     * 描述： 根据主键查询角色信息
     * @param roleId
     * @return SysRole
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    SysRole getById(String roleId) throws Exception;

    /**
     * 描述： 角色信息分页查询
     * @param page
     * @param sysRole
     * @return Ipage
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    IPage getByPage(Page<SysRole> page, SysRole sysRole) throws Exception;

    /**
     * 描述： 判断角色id是否已经存在
     * @param roleId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    boolean isExists(String roleId) throws Exception;

    /**
     * 描述： 保存角色和资源的对应关系
     * @param roleId
     * @param funcIds
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    boolean saveRoleFuncRel(String roleId, List<String> funcIds) throws Exception;

    /**
     * 描述： 根据用户编号查询角色信息集合
     * @param userId
     * @return list
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    List<SysRole> getRoleListByUserId(String userId) throws Exception;

    /**
     * 描述： 根据当前登录用户所拥有的角色查询出能够授权的角色集合
     * @param roleList
     * @return list
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/28 19:50
     */
    List<SysRole> getRoleListByLoginUser(List<SysRole> roleList) throws Exception;
    

    /**
     * <p>
     * 获取公告发布对象为角色的角色信息
     * </p>
     * @param pubNoticeId 公告id
     * @return java.util.List<com.scrcu.common.base.DataList>
     * @author wuyu
     * @date 2019/11/1 14:37
     **/
    List<DataList> getRoleListWithPubNotice(String pubNoticeId);

    List<DataList> getRoleListNotInPubNotice(String pubNoticeId);
    /**
     * 描述： 根据用户编号查询用户角色集合
     * @param id
     * @return Ipage
     * @创建人： hepengfei
     * @创建时间： 2019/11/27 19:50
     * */
    DataGrid getShowRoleUser(PageParameters page, SysUser sysUser, SysRole sysrole);

    /**
     * 描述：判断该角色是否已分配给用户
     * @param roleId
     * @return
     */
    AjaxResult isExistUserUnderRole(String roleId);
}
