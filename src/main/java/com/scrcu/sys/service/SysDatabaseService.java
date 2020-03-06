package com.scrcu.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.sys.entity.SysDatabase;

import java.util.List;

/**
 * 描述： 数据源信息接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/18 17:20
 */
public interface SysDatabaseService extends IService<SysDatabase> {

    /**
     * 描述： 数据源信息新增
     * @param sysDatabase
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 17:22
     */
    boolean insert(SysDatabase sysDatabase) throws Exception;

    /**
     * 描述： 数据源信息删除
     * @param id
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 17:22
     */
    boolean delete(String id) throws Exception;

    /**
     * 描述： 数据源信息修改
     * @param sysDatabase
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 17:22
     */
    boolean update(SysDatabase sysDatabase) throws Exception;

    /**
     * 描述： 数据源信息查询
     * @param id
     * @return SysDatabase
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 17:22
     */
    SysDatabase getById(String id) throws Exception;

    /**
     * 描述： 数据源信息分页查询
     * @param page
     * @param sysDatabase
     * @return IPage
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 17:22
     */
    IPage<SysDatabase> getByPage(Page page, SysDatabase sysDatabase) throws Exception;

    /**
     * 描述： 数据源信息查询集合（无条件）
     * @return List
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/18 17:22
     */
    List<SysDatabase> findList() throws Exception;
}
