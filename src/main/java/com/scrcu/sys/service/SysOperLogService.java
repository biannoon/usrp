package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.entity.SysLog;

/**
 * 描述： 操作日志接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/16 10:05
 */
public interface SysOperLogService extends IService<SysOperlog> {

    /**
     * 描述： 操作日志新增
     * @param SysOperLog
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/16 10:05
     */
    int insert(SysOperlog sysOperLog) throws Exception;

    /**
     * 描述： 操作日志删除
     * @param SysOperLog
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/16 10:05
     */
    int delete(SysOperlog sysOperLog) throws Exception;

    /**
     * 描述： 操作日志修改
     * @param SysOperLog
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/16 10:05
     */
    int update(SysOperlog sysOperLog) throws Exception;

    /**
     * 描述： 根据主键查询操作日志
     * @param id
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/9/16 10:05
     */
    SysOperlog findById(String id) throws Exception;

    /**
     * 描述： 操作日志分页查询
     * @param page
     * @param SysOperLog
     * @return
     * @创建人： hepengfei
     * @创建时间： 2019/10/16 10:05
     */
    DataGrid listSysOperlog(SysOperlog sysOperLog, PageParameters pageParameters);
}
