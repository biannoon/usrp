package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scrcu.sys.entity.SysSubsystem;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述： 子系统信息mapper接口
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/24 14:49
 */
public interface SysSubsystemMapper extends BaseMapper<SysSubsystem> {

    @Select("SELECT * FROM SYS_SUBSYSTEM A WHERE EXISTS (SELECT 1 FROM SYS_GROUP_RECOURS_REL B " +
            "WHERE A.SUBSYSTEM_ID=B.RECOURS_ID AND GROUP_ID=#{groupId} AND RECOURS_TYP='SYS1102')")
    List<SysSubsystem> getCheckedSubSysByGroupId(String groupId) throws Exception;
}
