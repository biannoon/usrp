package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.entity.SysUserGroupRel;
import com.scrcu.sys.entity.SysUserRoleRel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述： 用户信息mapper接口
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/30 15:29
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("<script>SELECT OA_NO USER_ID,EMPLY_NM USER_NM,GENDER_CD,BRTHDY,ADDR,CASE WHEN " +
            "MOBILE_NO IS NOT NULL THEN MOBILE_NO ELSE (CASE WHEN OFFICE_TEL_NO IS NOT NULL " +
            "THEN OFFICE_TEL_NO ELSE HOME_TEL_NO END) END TEL_NO FROM SYS_OEM_EMPLY WHERE 1=1 " +
//            "<if test=\"OA_NO !=null\">AND OA_NO=#{userId}</if><if test=\"EMPLY_NM !=null\">AND EMPLY_NM LIKE '%#{userNm}%'</if>" +
            "</script>")
    IPage<SysUser> getOemEmplyByPage(Page<SysUser> page, @Param("OA_NO") String userId, @Param("EMPLY_NM") String userNm) throws Exception;

    @Select("SELECT OA_NO USER_ID,EMPLY_NM USER_NM,GENDER_CD,BRTHDY,ADDR,CASE WHEN MOBILE_NO IS NOT NULL " +
            "THEN MOBILE_NO ELSE (CASE WHEN OFFICE_TEL_NO IS NOT NULL THEN OFFICE_TEL_NO " +
            "ELSE HOME_TEL_NO END) END TEL_NO FROM SYS_OEM_EMPLY WHERE OA_NO=#{userId}")
    SysUser getOemEmplyById(String userId) throws Exception;

    @Delete("DELETE FROM SYS_USER_ROLE_REL WHERE USER_ID=#{userId}")
    int delUserRoleByUserId(String userId) throws Exception;

    @Insert("INSERT INTO SYS_USER_ROLE_REL VALUES(#{id},#{userId},#{roleId})")
    int insertUserRole(@Param("id")String id, @Param("userId") String userId, @Param("roleId") String roleId) throws Exception;

    /**
     * 描述：批量插入用户-角色关系
     * @param list
     */
    void insertUserRole_re(@Param("relList") List<SysUserRoleRel> list);

    @Delete("DELETE FROM SYS_USER_GROUP_REL WHERE USER_ID=#{userId}")
    int delUserGroupByUserId(String userId) throws Exception;

    /**
     * 描述：批量插入用户-角色关系
     * @param list
     */
    void insertUserGroup_re(@Param("relList") List<SysUserGroupRel> list);

    @Insert("INSERT INTO SYS_USER_GROUP_REL VALUES(#{id},#{userId},#{groupId})")
    int insertUserGroup(@Param("id") String id, @Param("userId") String userId, @Param("groupId") String groupId) throws Exception;

    List<SysUser> selectUserList(@Param("page") Page page, @Param("sysUser") SysUser sysUser);

    List<SysUser> selectPageList(@Param("page") Page page, @Param("sysUser") SysUser sysUser);
    //-重写selectPageList方法：pengjuntao
    List<SysUser> selectPageList_rewrite(@Param("page") Page page, @Param("sysUser") SysUser sysUser,@Param("orgList")List<SysOrganization> orgList);

    void deleteUserRoleRel(@Param("userId") String userId);

    void deleteUserGroupRel(@Param("userId") String userId);


    //-----------------pjt共用组件---------------------

    /**
     * 描述：按照条件查询用户
     * @param page
     * @param user
     * @param orgList
     * @return
     */
    List<SysUser> getSysUserByCommon(@Param("page") Page page,@Param("user") SysUser user, @Param("orgList") List<SysOrganization> orgList);

    /**
     * 描述：查询当前机构的下级所有机构
     * @param organization
     * @return
     */
    List<SysOrganization> getNextLvlSysOrgInfoFromUser(SysOrganization organization);

    /**
     * 描述：获取表中Id的最大值
     * @param table
     * @return
     */
    String selectMaxId(@Param("table") String table);

}
