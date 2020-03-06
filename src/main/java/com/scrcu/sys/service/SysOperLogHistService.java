package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.entity.SysOperlogHist;

/**
 * 描述： 操作日志历史接口层
 * @创建人： hepengfei
 * @创建时间： 2019/11/04 10:05
 */
public interface SysOperLogHistService extends IService<SysOperlogHist> {

    /**
     * 描述： 操作日志分页查询
     * @param page
     * @param SysOperLog
     * @return
     * @创建人： hepengfei
     * @创建时间： 2019/10/16 10:05
     */
    DataGrid listSysOperlog(SysOperlog sysOperlog, PageParameters pageParameters);
}
