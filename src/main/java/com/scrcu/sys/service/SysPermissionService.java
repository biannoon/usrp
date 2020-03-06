package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.sys.entity.SysPermission;

/**
 * 描述： 权限信息接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/1 21:57
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 描述： 权限信息新增
     * @param SysPermission
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/9 15:50
     */
    int insert(SysPermission sysPermission) throws Exception;

    /**
     * 描述： 权限信息删除
     * @param SysPermission
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/9 15:50
     */
    int delete(SysPermission sysPermission) throws Exception;

    /**
     * 描述： 权限信息修改
     * @param SysPermission
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/9 15:50
     */
    int update(SysPermission sysPermission) throws Exception;

    /**
     * 描述： 根据主键查询权限信息
     * @param id
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/9 15:50
     */
    SysPermission findById(String id) throws Exception;

    /**
     * 描述： 权限信息分页查询
     * @param page
     * @param queryWrapper
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/9 15:50
     */
    Object findByPage(Page page, SysPermission sysPermission) throws Exception;
}
