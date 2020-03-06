package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述： 角色信息mapper接口
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/1 21:59
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT * FROM SYS_ROLE A WHERE EXISTS (SELECT 1 FROM SYS_USER_ROLE_REL B " +
            "WHERE A.ROLE_ID=B.ROLE_ID AND B.USER_ID=#{userId})")
    List<SysRole> getRoleListByUserId(String userId) throws Exception;

    @Select("SELECT A.* FROM SYS_ROLE A " +
            "INNER JOIN (SELECT SYS,MAX(ROLE_HRCH_CD) ROLE_HRCH_CD FROM SYS_ROLE " +
            "WHERE ROLE_ID IN (${roles}) AND SYS='SYS' GROUP BY SYS,ROLE_HRCH_CD) B " +
            "WHERE A.ROLE_HRCH_CD<=B.ROLE_HRCH_CD AND A.ROLE_ID NOT IN(${roles}) " +
            "UNION " +
            "SELECT A.* FROM SYS_ROLE A " +
            "INNER JOIN (SELECT SYS,MAX(ROLE_HRCH_CD) ROLE_HRCH_CD FROM SYS_ROLE " +
            "WHERE ROLE_ID IN (${roles}) AND SYS<>'SYS' GROUP BY SYS,ROLE_HRCH_CD) B " +
            "WHERE A.SYS=B.SYS AND A.ROLE_HRCH_CD<=B.ROLE_HRCH_CD  AND A.ROLE_ID NOT IN(${roles})")
    List<SysRole> getRoleListByLoginUser(@Param("roles") String roles) throws Exception;

    @Delete("DELETE FROM SYS_ROLE_FUNC_REL WHERE ROLE_ID=#{roleId}")
    int delRoleFuncByRoleId(String roleId) throws Exception;

    @Insert("INSERT INTO SYS_ROLE_FUNC_REL VALUES(NEXTVAL('RELSEQ'),#{roleId},#{funcId})")
    int insertRoleFunc(String roleId, String funcId) throws Exception;

    @Select("select r.ROLE_ID,r.ROLE_NM from sys_role r inner join pub_notice_trgt t on r.ROLE_ID=t.trgt_id " +
            "where t.trgt_typ='PUB0202' and t.notice_id=#{pubNoticeId} ")
    List<SysRole> getRoleListWithPubNotice(String pubNoticeId);

    @Select("select r.ROLE_ID,r.ROLE_NM from sys_role r where not exists " +
            "(select t.trgt_id from pub_notice_trgt t where t.notice_id=#{pubNoticeId} " +
            "and t.trgt_typ='PUB0202' and r.ROLE_ID=t.trgt_id)")
    List<SysRole> getRoleListNotInPubNotice(String pubNoticeId);

    /**
     * 描述：根据userid查询角色列表
     * @param idList
     * @param groupId
     */
    List<SysRole> selectRoleList(@Param("page") Page page, @Param("sysRole") SysRole sysRole);

    /**
     * 描述：通过角色信息获取已分配用户的数据
     * @param page
     * @param sysUser
     * @param sysRole
     * @return
     * @author pengjuntao
     */
    List<SysUser> getSysUserByRole(@Param("page") Page page,@Param("user") SysUser sysUser,@Param("role") SysRole sysRole);

    /**
     * 描述：获取指定角色的用户ID
     * @param roleId
     * @return
     */
    List<SysUser> getUserUnderRole(@Param("roleId") String roleId);

}
