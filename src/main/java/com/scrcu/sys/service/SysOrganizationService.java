package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysOrgSbmtdTree;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机构信息业务操作类
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:37
 **/
public interface SysOrganizationService extends IService<SysOrganization> {

    /**
     * 描述：通过机构编码查询机构信息
     * @param org_cd
     * @return
     * @author pengjuntao
     */
    SysOrganization getSysOrgInfoByOrgCd(String org_cd);

    /**
     * 描述： 查询全部基础机构信息返回map集合
     * @return Map<String, List<SysOrganization>>
     * @Author jiyuanbo
     * @Date 2019/10/21 16:35
     */
    Map<String, List<SysOrganization>> getAllSysOrg() throws Exception;

    /**
     * 描述：通过用户查询到查询机构资源的权限
     * @param user
     * @return
     */
    List<SysOrganization> getSysOrgInfoFromUser(SysUser user);

    /**
     * 描述：获取指定的字典代码集合
     * @param dic
     * @return
     */
    List<SysDictryCd> getTreeTypeFromDictryCd(SysDictryCd dic);

    /**
     * 描述：系统机构信息表和系统上报机构树表联合查询
     * @param orgSbmtdTree
     * @return
     */
    List<SysOrganization> getSysOrgInfoFromSbmtedTree(SysOrgSbmtdTree orgSbmtdTree);

    /**
     * 描述：获取自定义机构树的机构集合
     * @param sbmtdTree
     * @return
     */
    List<SysOrgSbmtdTree> getSysOrgSbmtdTree(SysOrgSbmtdTree sbmtdTree);

}
