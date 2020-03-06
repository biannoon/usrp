package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scrcu.sys.entity.SysLog;
import com.scrcu.sys.entity.SysParam;

import java.util.List;

/**
 * 描述： 系统日志mapper接口
 * @创建人： hepengf
 * @创建时间： 2019/10/24 10:10
 */

public interface SysLogMapper extends BaseMapper<SysLog> {


 List<SysParam> listSysParamByType(String param_typ);


}
