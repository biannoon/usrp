package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysOrgSbmtdTree;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 机构信息mapper映射对象
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:35
 **/
public interface SysOrganizationMapper extends BaseMapper<SysOrganization> {

    //查询数据的系统字典代码，获取自定义机构树类型
    List<SysDictryCd> getTreeTypeFromDictryCd(SysDictryCd dictryCd);

    //查询上报机构树信息
    List<SysOrgSbmtdTree> getSysOrgSbmtdTree(SysOrgSbmtdTree orgSbmtdTre);

    //系统机构信息表和系统上报机构树表联合查询
    List<SysOrganization> getSysOrgInfoFromSbmtedTree(SysOrgSbmtdTree orgSbmtdTree);

    //获取当前用户所属用户组的机构资源
    List<SysOrganization> getSysOrgInfoFromUser(SysUser user);

    List<SysOrganization> getSysOrgInfoByList(List<String> list);

    SysOrganization getSingleSysOrgInfoByOrgGrdtnCd(SysOrganization sysOrganization);

    SysOrganization getSysOrgInfoByOrgCd(@Param("org_cd") String org_cd);

}
