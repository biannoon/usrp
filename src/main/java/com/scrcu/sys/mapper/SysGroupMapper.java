package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.sys.entity.SysGroup;
import com.scrcu.sys.entity.SysGroupRecoursRel;
import com.scrcu.sys.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 描述： 用户组Mapper接口
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/19 10:23
 */
public interface SysGroupMapper extends BaseMapper<SysGroup> {

    @Delete("DELETE FROM sys_group_recours_rel WHERE GROUP_ID=#{groupId} AND RECOURS_TYP=#{recoursTyp}")
    int delGroupResouseByGroupId(@Param("groupId") String groupId, @Param("recoursTyp") String recoursTyp) throws Exception;

    @Insert("INSERT INTO sys_group_recours_rel VALUES(NEXTVAL('RELSEQ')," +
            "#{groupId},#{recoursId},#{recoursNm},#{recoursTyp})")
    int insertGroupResouse(@Param("groupId") String groupId, @Param("recoursId") String recoursId, @Param("recoursNm") String recoursNm,
                           @Param("recoursTyp") String recoursTyp) throws Exception;

    @Select("SELECT * FROM sys_group A WHERE EXISTS (SELECT 1 FROM sys_user_group_rel B " +
            "WHERE A.GROUP_ID=B.GROUP_ID AND B.USER_ID=#{userId})")
    List<SysGroup> getGroupListByUserId(@Param("userId") String userId);

    //@Select("SELECT * FROM sys_group A WHERE EXISTS (SELECT 1 FROM sys_user_group_rel B " +
     //       "WHERE A.GROUP_ID=B.GROUP_ID AND B.USER_ID=#{userId}) OR IS_GLOBAL='SYS0201'")
    @Select("SELECT  * FROM SYS_GROUP A "+
   "LEFT JOIN SYS_USER_GROUP_REL R ON  A.GROUP_ID=R.GROUP_ID WHERE A.GROUP_ID NOT IN (SELECT GROUP_ID FROM SYS_USER_GROUP_REL WHERE USER_ID = #{userId})")
    List<SysGroup> getGroupListByLoginUser(@Param("userId") String userId);

    @Select("SELECT RECOURS_ID FROM sys_group_recours_rel WHERE GROUP_ID=#{groupId} AND RECOURS_TYP=#{recoursTyp}")
    List<String> getGroupResouseRel(@Param("groupId") String groupId, @Param("recoursTyp") String recoursTyp) throws Exception;

    @Select("SELECT ORG_CD FROM sys_org_info WHERE org_grdtn_cd LIKE " +
            "CONCAT((SELECT org_grdtn_cd FROM sys_org_info WHERE ORG_ID=#{orgId}),'%')")
    List<String> getOrgCdListByParent(@Param("orgId") String orgId) throws Exception;

    @Select("select g.GROUP_ID, g.GROUP_NM from sys_group g inner join pub_notice_trgt t on g.GROUP_ID = t.trgt_id " +
            "where t.trgt_typ='PUB0201' and t.notice_id=#{pubNoticeId}")
    List<SysGroup> getGroupListWithNotice(@Param("pubNoticeId") String pubNoticeId);

    @Select("select g.GROUP_ID,g.GROUP_NM from sys_group g where not exists " +
            "(select t.trgt_id from pub_notice_trgt t where t.notice_id=#{pubNoticeId} " +
            "and t.trgt_typ='PUB0201' and g.GROUP_ID=t.trgt_id)")
    List<SysGroup> getGroupNotInPubNotice(@Param("pubNoticeId") String pubNoticeId);


    //------------------pengjuntao--------------------------

    /**
     * 查询用户组数据集合
     * @param sysGroup
     * @return
     */
    List<SysGroup> listSysGroup(@Param("page") Page page, @Param("sysGroup") SysGroup sysGroup);

    /**
     * 描述：通过用户组ID获取用户组资源
     * @param sysGroupRecoursRel
     * @return
     */
    List<SysGroupRecoursRel> listRecoursByGroupId(@Param("page") Page page, @Param("sysGroupRecoursRel") SysGroupRecoursRel sysGroupRecoursRel);
    /**
     * 描述：查询已分配指定用户组的用户
     * @param group
     * @return
     */
    List<SysUser> listSysUserByGroupId(@Param("page") Page page, @Param("sysGroup") SysGroup sysGroup);

    /**
     * 描述：批量新增用户组资源
     * @param list
     */
    void insertSysGroupRecoursRel(@Param("list") List<SysGroupRecoursRel> list);

    /**
     * 描述：批量删除用户组资源
     * @param list
     */
    void deleteSysGroupRecoursRelByGroupId(@Param("groupId") String groupId);

    /**
     * 描述：批量删除用户组资源：条件为资源类型和用户组ID
     * @param rel
     */
    void deleteSysGroupResoursBatch(SysGroupRecoursRel rel);

    /**
     * 描述：判断用户组是否存在已分配的用户
     * @param groupId
     * @return
     */
    @Select("select count(id) from sys_user_group_rel where group_id=#{groupId}")
    int isExistUserUnderGroup(@Param("groupId") String groupId);

    /**
     * 描述：批量删除指定ID的用户组资源集合
     * @param idList
     * @param groupId
     */
    void deleteSysGroupResoursById(@Param("idList") List<String> idList, @Param("groupId") String groupId);
    /**
     * 描述：根据userid查询用户组列表
     * @param idList
     * @param groupId
     */
    List<SysGroup> selectSysGroupList(@Param("page") Page page, @Param("sysGroup") SysGroup sysGroup);
}
