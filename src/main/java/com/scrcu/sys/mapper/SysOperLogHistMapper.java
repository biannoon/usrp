package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.entity.SysOperlogHist;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： 操作日志历史mapper接口
 * @创建人： hepengfei
 * @创建时间： 2019/11/04 10:10
 */

public interface SysOperLogHistMapper extends BaseMapper<SysOperlogHist> {

    /**
     * 描述：查询系统历史日志
     * @param page
     * @param sysOperlog
     * @return
     */
    List<SysOperlogHist> getSysOperLog(@Param("page") Page page, @Param("sysOperLog") SysOperlog sysOperlog);


}
