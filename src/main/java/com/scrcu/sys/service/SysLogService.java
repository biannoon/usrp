package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysLog;

/**
 * 描述： 系统日志接口层
 * @创建人： 贺鹏飞
 * @创建时间： 2019/9/16 10:05
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 描述：获取日志服务器参数
     * @param pageParameters
     * @param paramType     参数类型
     * @return
     * @author pengjuntao 重写
     */
    DataGrid listAllSysLogParam(PageParameters pageParameters, String paramType);

    /**
     * 描述：连接服务器，获取日志文件
     * @param sysLog
     * @return
     */
    String listAllSysLog(SysLog sysLog,String id);

}
