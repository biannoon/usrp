package com.scrcu.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysSubsystem;

import java.util.List;

/**
 * 描述： 子系统信息service接口
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/24 14:48
 */
public interface SysSubsystemService extends IService<SysSubsystem> {

    /**
     * 描述： 子系统信息新增
     * @param sysSubsystem
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/24 14:48
     */
    boolean insert(SysSubsystem sysSubsystem) throws Exception;

    /**
     * 描述： 子系统信息删除
     * @param subsystemId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/24 14:48
     */
    boolean delete(String subsystemId) throws Exception;

    /**
     * 描述： 子系统信息修改
     * @param sysSubsystem
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/24 14:48
     */
    boolean update(SysSubsystem sysSubsystem) throws Exception;

    /**
     * 描述： 子系统信息查询
     * @param subsystemId
     * @return sysSubsystem
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/24 14:48
     */
    SysSubsystem getById(String subsystemId) throws Exception;

    /**
     * 描述： 子系统信息分页查询
     * @param page
     * @param sysSubsystem
     * @return IPage
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/24 14:48
     */
    IPage getByPage(Page<SysSubsystem> page, SysSubsystem sysSubsystem) throws Exception;

    /**
     * 描述： 判断子系统编号是否已存在
     * @param subsystemId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/24 14:48
     */
    boolean isExists(String subsystemId) throws Exception;

    /**
     * 描述： 子系统信息查询并带回被选中的子系统
     * @param groupId
     * @return List<SysSubsystem>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/24 14:48
     */
    List<SysSubsystem> getSysSubsystemList(String groupId) throws Exception;


    /**
     * 描述：获取所有的子系统记录
     * @param pageParameters
     * @param sysSubsystem
     * @return
     */
    DataGrid getAllSubSystemFromFileManage(PageParameters pageParameters, SysSubsystem sysSubsystem);

    /**
     * 描述：获取所有的子系统信息(不分页)
     * @return
     * @author pengjuntao
     */
    List<SysSubsystem> getAllSubSystemWithoutPage();
}
