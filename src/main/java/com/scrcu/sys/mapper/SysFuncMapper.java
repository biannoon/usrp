package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scrcu.sys.entity.SysFunc;
import com.scrcu.sys.entity.SysRoleFuncRel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysFuncMapper extends BaseMapper<SysFunc> {

    @Select("select s.func_id,s.func_nm from sys_func s  left join sys_role_func_rel r " +
            " on s.func_id = r.func_id  left join sys_role e on e.role_id=r.role_id " +
            " where s.pare_func_id = #{pareFuncId} and e.role_id =#{roleId}" )
    List<SysFunc> getSysFuncListById(@Param("pareFuncId") String pareFuncId, @Param("roleId") String roleId);

    @Select("select func_id from sys_user_role_rel a left join sys_role_func_rel b on a.role_id=b.role_id where a.user_id=#{userId}")
    List<String> getSysFuncByUserId(String userId);

    @Select("select func_id,role_id from sys_role_func_rel")
    List<SysRoleFuncRel> getAllSysRoleFuncRel();

    @Select("select b.func_id,b.func_nm,b.is_lef from sys_role_func_rel as a left join sys_func as b on a.func_id=b.func_id " +
            "where a.role_id=#{roleId}")
    List<SysFunc> getSysFuncByRoleId(@Param("roleId") String roleId);

}
