package com.scrcu.common.utils;

import com.alibaba.fastjson.JSON;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysOrgSbmtdTree;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysOrganizationService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ComponentUtil
 * @Description TODO 共用组件工具类
 * @Author pengjuntao
 * @Date 2020/1/21 16:32
 * @Version 1.0
 */
public class ComponentUtil {

    /**
     * 描述：初始化机构树数据(标准机构树)
     *
     * @param id        父节点Id
     * @param orgLimits 机构权限控制
     * @param user      当前用户
     * @param expOrgNo  排除机构
     * @return json字符串
     * @autor pengjuntao
     */
    public static String getSysOrgInfoByStandard(String id,
                                                 String orgLimits,
                                                 SysUser user,
                                                 String expOrgNo) {
        List<SysOrganization> returnList = null;
        String orgId = (id != null && !"".equals(id)) ? id : "-1";
        List<SysOrganization> cache_org_list = EhcacheUtil.getSysOrgInfoByCache(orgId);
        if (cache_org_list != null && cache_org_list.size() > 0) {
            //机构权限控制
            if ("true".equals(orgLimits)) {
                returnList = openOrganizationAuthorityByStandard(user, cache_org_list);
            } else {
                returnList = cache_org_list;
            }
            //机构排除
            returnList = expOrgFromOrgListByStandard(returnList, expOrgNo);
        }
        return JSON.toJSONString(TreeNodeUtil.fillSysOrgInfoTree(returnList));
    }

    /**
     * 描述：初始化机构树数据（自定义机构树）
     *
     * @param id           父节点ID
     * @param selfTreeType 自定义机构树代码
     * @param orgLimits    是否机构权限控制
     * @param user         当前用户
     * @param expOrgNo     排除机构
     * @return
     */
    public static String getSysOrgInfoBySelf(String id,
                                             String selfTreeType,
                                             String orgLimits,
                                             SysUser user,
                                             String expOrgNo) {
        if (id != null && !"".equals(id)) {
            //获取机构集合
            List<SysOrgSbmtdTree> treeList = getOrgSbmtdTreeBySprOrgId(id);
            List<SysOrganization> resultList = null;
            //机构权限控制
            if ("true".equals(orgLimits)) {
                resultList = openOrganizationAuthorityBySelf(user, treeList);
            } else {
                resultList = closeOrganizationAuthorityBySelf(treeList);
            }
            //排除机构
            resultList = expOrgFromOrgListByStandard(resultList, expOrgNo);
            return JSON.toJSONString(TreeNodeUtil.fillSysOrgInfoTree(resultList));
        } else {
            List<SysDictryCd> resultList = getSelfOrgTypeObjFromDB(selfTreeType);
            return JSON.toJSONString(TreeNodeUtil.fillSysDictryCdBySelfTreeType(resultList));
        }
    }

    /**
     * 描述：从数据库获取自定义机构树代码集合
     * @param selfTreetype 自定义机构树类型
     * @return
     */
    public static List<SysDictryCd> getSelfOrgTypeObjFromDB(String selfTreetype) {
        SysOrganizationService sysOrganizationService =
                (SysOrganizationService) SpringUtil.getBean("sysOrganizationServiceImpl");
        //--获取所有自定义机构树代码
        SysDictryCd dic = new SysDictryCd();
        if (selfTreetype != null && !"".equals(selfTreetype)) {
            dic.setBlngtoDictryId("SYS10");
            dic.setDictryId(selfTreetype);
        } else {
            //-如果不指定自定义机构树，便查询全部自定义机构树
            dic.setPareDictryId("SYS10");
        }
        return sysOrganizationService.getTreeTypeFromDictryCd(dic);
    }

    /**
     * 描述：获取自定义机构树节点的下级机构
     *
     * @param sprOrgId
     * @return
     */
    public static List<SysOrgSbmtdTree> getOrgSbmtdTreeBySprOrgId(String sprOrgId) {
        SysOrganizationService sysOrganizationService =
                (SysOrganizationService) SpringUtil.getBean("sysOrganizationServiceImpl");
        List<SysOrgSbmtdTree> resultList = null;
        SysOrgSbmtdTree sbmtdTree = new SysOrgSbmtdTree();
        if (sprOrgId != null && !"".equals(sprOrgId)) {
            if (sprOrgId.indexOf("_") != -1) {
                sbmtdTree.setTreeTyp(sprOrgId.substring(0, sprOrgId.indexOf("_")));
                sbmtdTree.setSprOrgId(sprOrgId.substring(sprOrgId.indexOf("_") + 1));
            } else {
                sbmtdTree.setSprOrgId(sprOrgId);
            }
            resultList = sysOrganizationService.getSysOrgSbmtdTree(sbmtdTree);
        }
        return resultList;
    }

    /**
     * 描述：开启机构控制权限（自定义机构树）
     * @param user    用户
     * @param orgList
     * @return
     */
    public static List<SysOrganization> openOrganizationAuthorityBySelf(SysUser user,
                                                                        List<SysOrgSbmtdTree> orgList) {
        SysOrganizationService sysOrganizationService =
                (SysOrganizationService) SpringUtil.getBean("sysOrganizationServiceImpl");
        //-获取当前用户机构权限范围内的机构集合
        List<SysOrganization> authOrgList = sysOrganizationService.getSysOrgInfoFromUser(user);
        authOrgList = constructOrganizationTree(CommonUtil.getTopOrgInfo(authOrgList));
        //-取并集
        List<SysOrganization> unionList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        for (SysOrgSbmtdTree sbmtdTree : orgList) {
            idList.add(sbmtdTree.getOrgId());
        }
        for (SysOrganization sysOrganization : authOrgList) {
            if (idList.contains(sysOrganization.getId())) {
                unionList.add(sysOrganization);
            }
        }
        return unionList;
    }

    /**
     * 描述：关闭权限控制（自定义机构树）
     *
     * @param list
     * @return
     */
    public static List<SysOrganization> closeOrganizationAuthorityBySelf(List<SysOrgSbmtdTree> list) {
        List<SysOrganization> resultList = new ArrayList<>();
        for (SysOrgSbmtdTree sbmtdTree : list) {
            SysOrganization organization = new SysOrganization();
            organization.setId(sbmtdTree.getTreeTyp() + "_" + sbmtdTree.getOrgId());
            organization.setName(sbmtdTree.getOrgNm());
            organization.setSuperiorId(sbmtdTree.getSprOrgId());
            organization.seteMail("true");
            organization.setCode("");
            resultList.add(organization);
        }
        return resultList;
    }


    /**
     * 描述：开启机构权限控制(标准机构树)
     *
     * @param user    用户
     * @param orgList 机构集合
     * @return
     */
    public static List<SysOrganization> openOrganizationAuthorityByStandard(SysUser user, List<SysOrganization> orgList) {
        SysOrganizationService sysOrganizationService =
                (SysOrganizationService) SpringUtil.getBean("sysOrganizationServiceImpl");
        List<SysOrganization> resultList = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        //-获取当前用户所分配用户组对应的最顶层机构资源（去重处理加上下级关系处理）
        List<SysOrganization> authOrgList =
                CommonUtil.getTopOrgInfo(sysOrganizationService.getSysOrgInfoFromUser(user));
        //-获取当前用户具有的所有权限机构
        List<SysOrganization> authAllOrgList = constructOrganizationTree(authOrgList);
        for (SysOrganization org : authAllOrgList) {
            idList.add(org.getId());
        }
        for (SysOrganization org : orgList) {
            if (idList.contains(org.getId())) {
                resultList.add(org);
            }
        }
        return resultList;
    }


    /**
     * 描述：组装机构树
     *
     * @param topList
     * @return
     */
    public static List<SysOrganization> constructOrganizationTree(List<SysOrganization> topList) {
        List<SysOrganization> resultList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        List<SysOrganization> down_orgList = CommonUtil.getAllOrgInfoByTop(topList);
        resultList.addAll(down_orgList);
        Map<String, SysOrganization> orgMap =
                (Map<String, SysOrganization>) EhcacheUtil.getSysOrgInfoByCache01("ALL_ORG_GRDTN_MAP_CACHE");
        for (SysOrganization organization : topList) {
            for (int i = organization.getGradationCode().length() - 4; i >= 0; i -= 4) {
                String code = organization.getGradationCode().substring(0, i);
                if (orgMap.containsKey(code)) {
                    SysOrganization org = orgMap.get(code);
                    org.seteMail("false");
                    if (!tempList.contains(org.getId())) {
                        tempList.add(org.getId());
                        resultList.add(org);
                    }
                }
            }
        }
        return resultList;
    }

    /**
     * 描述：排除不需要机构(标准机构树)
     *
     * @param orgList  传入的机构集合
     * @param expOrgNo 不需要的机构
     * @return
     */
    public static List<SysOrganization> expOrgFromOrgListByStandard(List<SysOrganization> orgList,
                                                                    String expOrgNo) {
        List<SysOrganization> resultList = new ArrayList<>();
        resultList.addAll(orgList);
        List<String> expList = new ArrayList<>();//存储排除机构编号
        if (StringUtils.isNotBlank(expOrgNo)) {
            String[] orgArry = expOrgNo.split("_");
            expList = Arrays.asList(orgArry);
        }
        for (SysOrganization organization : orgList) {
            if (expList.contains(organization.getId())) {
                resultList.remove(organization);
            }
        }
        return resultList;
    }

}