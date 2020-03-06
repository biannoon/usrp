package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.sys.entity.SysOperlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述： 操作日志mapper接口
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/16 10:10
 */

public interface SysOperLogMapper extends BaseMapper<SysOperlog> {

      /**
       * 描述：查询系统历史日志
       * @param page
       * @param sysOperlog
       * @return
       */
      List<SysOperlog> getSysOperLog(@Param("page") Page page, @Param("sysOperLog") SysOperlog sysOperlog);

}
