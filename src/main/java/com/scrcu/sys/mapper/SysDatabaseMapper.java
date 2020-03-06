package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scrcu.sys.entity.SysDatabase;
import org.apache.ibatis.annotations.Select;

/**
 * 描述： 数据源信息Mapper接口
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/18 17:25
 */
public interface SysDatabaseMapper extends BaseMapper<SysDatabase> {

    @Select("SELECT NEXTVAL('ID_SEQ')")
    int getId();
}
