package com.scrcu.common.utils;

import com.scrcu.apm.entity.SysTabConfig;
import com.scrcu.apm.service.SysTabConfigService;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysFunc;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.service.SysDictryCdService;
import com.scrcu.sys.service.SysFuncService;
import com.scrcu.sys.service.SysOrganizationService;
import com.scrcu.sys.service.impl.SysFuncServiceImpl;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 描述： ehcache缓存工具类
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/10 10:07
 */
@Component
public class EhcacheUtil {

    /**
     * 缓存区
     */
    public static final String CACHE_DICTRY = "dictryCache";
    public static final String CACHE_PARM = "parmCache";
    public static final String CACHE_OTHER = "otherCache";
    public static final String CACHE_TEMP = "tempCache";

    /**
     * 缓存元素前缀
     */
    public static final String SYS_ORG_INFO = "org_";
    public static final String SYS_DICTRY_CD = "dictry_";
    public static final String SYS_FUNC = "func_";
    public static final String SYS_TAB_CONFIG = "tabconfig_";
    public static final String SYS_ROLE_FUNC_REL = "authoritoryRel_";
    public static final String SYS_USER_ROLE = "role_";
    public static final String TEMP_GROUP_RES = "temp_group_res_";//临时存储用户组资源
    public static final String TEMP_ORG_INFO = "temp_org_info_";//临时存储系统机构组件的机构集合

    public static CacheManager  cacheManager = CacheManager.create();

    protected static final Logger logger = LoggerFactory.getLogger(EhcacheUtil.class);

    private EhcacheUtil() {
    }

    /**
     * 描述： 初始化缓存需要缓存的信息
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static void init() throws Exception {
        initSysOrgInfo();
        initSysOrgInfoByOrgCdMap();
        initSysOrgInfoByGrdtnCdMap();
        initDictryCd();
        initSysFunc();
        initSysTabConfig();
        initAuthoritoryManageFunction();
    }

    /**
     * 描述： ehcache缓存机构信息
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static void initSysOrgInfo() throws Exception {
        SysOrganizationService sysOrganizationService =
                (SysOrganizationService) SpringUtil.getBean("sysOrganizationServiceImpl");
        Map<String, List<SysOrganization>> map = sysOrganizationService.getAllSysOrg();
        for (Map.Entry<String, List<SysOrganization>> entry : map.entrySet()) {
            cacheAble(CACHE_DICTRY, SYS_ORG_INFO + entry.getKey(), entry.getValue());
        }
    }

    /**
     * 描述：ehcache缓存机构信息（<机构号，机构对象>的map）
     */
    public static void initSysOrgInfoByOrgCdMap(){
        List<SysOrganization> all_org_cache = getSysOrgInfoByCache("ALL_ORG_CACHE");
        Map<String,SysOrganization> map = new HashMap<>();
        for (SysOrganization organization : all_org_cache){
            map.put(organization.getCode(),organization);
        }
        cacheAble(CACHE_DICTRY,SYS_ORG_INFO + "ALL_ORG_MAP_CACHE",map);
    }

    /**
     * 描述：ehcache缓存机构信息（<机构层级代码，机构对象>的map）
     */
    public static void initSysOrgInfoByGrdtnCdMap(){
        List<SysOrganization> all_org_cache = getSysOrgInfoByCache("ALL_ORG_CACHE");
        Map<String,SysOrganization> map = new HashMap<>();
        for (SysOrganization organization : all_org_cache){
            map.put(organization.getGradationCode(),organization);
        }
        cacheAble(CACHE_DICTRY,SYS_ORG_INFO + "ALL_ORG_GRDTN_MAP_CACHE",map);
    }

    /**
     * 描述： ehcache缓存字典表
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static void initDictryCd() throws Exception {
        SysDictryCdService sysDictryCdService =
                (SysDictryCdService) SpringUtil.getBean("sysDictryCdServiceImpl");
        Map<String, List<SysDictryCd>> map = sysDictryCdService.getAllSysDictryCd();
        for (Map.Entry<String, List<SysDictryCd>> entry : map.entrySet()) {
            cacheAble(CACHE_DICTRY, SYS_DICTRY_CD + entry.getKey(), entry.getValue());
        }
    }
    /**
     * 描述： ehcache缓存功能资源表
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static void initSysFunc() throws Exception {
        SysFuncServiceImpl sysFuncServiceImpl =
                (SysFuncServiceImpl) SpringUtil.getBean("sysFuncServiceImpl");
        Map<String, List<SysFunc>> map = sysFuncServiceImpl.getAllSysFunc();
        for (Map.Entry<String, List<SysFunc>> entry : map.entrySet()) {
            cacheAble(CACHE_PARM, SYS_FUNC + entry.getKey(), entry.getValue());
        }
    }

    /**
     * 描述： ehcache缓存表单配置信息表
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static void initSysTabConfig() throws Exception {
        SysTabConfigService sysTabConfigService =
                (SysTabConfigService) SpringUtil.getBean("sysTabConfigServiceImpl");
        Map<String, List<SysTabConfig>> map = sysTabConfigService.getAllSysTab();
        for (Map.Entry<String, List<SysTabConfig>> entry : map.entrySet()) {
            cacheAble(CACHE_PARM, SYS_TAB_CONFIG + entry.getKey(), entry.getValue());
        }
    }

    /**
     * 描述：ehcache缓存具有角色功能权限的功能菜单
     * @author pengjuntao
     * @date 2019/12/17
     */
    public static void initAuthoritoryManageFunction(){
        SysFuncService sysFuncService =
                (SysFuncService) SpringUtil.getBean("sysFuncServiceImpl");
        Map<String,List<String>> map = sysFuncService.getAllSysRoleFuncRel();
        for (Map.Entry<String,List<String>> entry : map.entrySet()){
            cacheAble(CACHE_PARM, SYS_ROLE_FUNC_REL+entry.getKey(),entry.getValue());
        }
    }

    /**
     * 描述：ehcache临时缓存
     * @param key
     * @param object
     */
    public static void initTempCache(String key, Object object){
        cacheAble(CACHE_TEMP, key, object);
    }

    /**
     * 描述：获取临时缓存
     * @return
     */
    public static Object getTempCache(String type, String key){
        return getCache(CACHE_TEMP, type + key);
    }



    /**
     * 描述： 将数据加载进ehcache缓存
     * @param cacheName
     * @param key
     * @param value
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static boolean cacheAble(String cacheName, String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (null != cache) {
            cache.put(new Element(key, value));
            logger.info("缓存cacheName="+cacheName+"，key="+key+"的缓存对象");
            return true;
        } else {
            logger.info("无法获取缓存cacheName="+cacheName+"，key="+key+"的缓存对象");
            return false;
        }
    }

    /**
     * 描述： 通过缓存名称查询出该缓存下所有的element元素的key集合
     * @param cacheName
     * @return List
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static List getCacheKeys(String cacheName) {
        return cacheManager.getCache(cacheName).getKeys();
    }

    /**
     * 描述： 根据缓存名称删除相应的缓存信息
     * @param cacheName
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static boolean cacheRemove(String cacheName, String subKey) {
        Cache cache = cacheManager.getCache(cacheName);
        if (null != cache) {
            Pattern pattern = Pattern.compile(subKey);
            List<Object> cacheKeys = getCacheKeys(cacheName);
            for (Object cacheKey : cacheKeys) {
                if (pattern.matcher(String.valueOf(cacheKey)).find()) {
                    cache.remove(cacheKey);
                }
            }
            logger.info("清除缓存cacheName="+cacheName+",前缀为"+subKey+"的缓存对象");
            return true;
        }
        return false;
    }

    /**
     * 描述：根据缓存key删除缓存信息
     * @param cacheName
     * @param key
     * @return
     */
    public static boolean cacheRemoveByKey(String cacheName, String key){
        Cache cache = cacheManager.getCache(cacheName);
        if (null != cache) {
            List<Object> cacheKeys = getCacheKeys(cacheName);
            for (Object cacheKey : cacheKeys) {
                if (String.valueOf(cacheKey).equals(key)) {
                    cache.remove(cacheKey);
                }
            }
            logger.info("清除缓存cacheName="+cacheName+",key值为"+key+"的缓存对象");
            return true;
        }
        return false;
    }

    /**
     * 描述： 根据缓存名称获取相应的element元素再读取其中的数据
     * @param cacheName
     * @param key
     * @return object
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static Object getCache(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                return element.getObjectValue();
            } else {
                logger.info("缓存(cacheName=" + cacheName + ",key="+ key + ") 对应的值为空");
            }
        } else {
            logger.error("无法获取cacheName=" + cacheName + "的缓存对象");
        }
        return null;
    }

    /**
     * 描述： 查询基础机构信息缓存信息，由代码在此封装Key
     * @param key
     * @return List<SysOrganization>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static List<SysOrganization> getSysOrgInfoByCache(String key) {
        return (List<SysOrganization>) getCache(CACHE_DICTRY, SYS_ORG_INFO + key);
    }

    /**
     * 描述： 查询基础机构信息缓存信息，由代码在此封装Key
     * @param key
     * @return
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static Object getSysOrgInfoByCache01(String key){
        return getCache(CACHE_DICTRY, SYS_ORG_INFO + key);
    }

    /**
     * 描述： 查询数据字典缓存信息，由代码在此封装Key
     * @param key
     * @return List<SysDictryCd>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static List<SysDictryCd> getSysDictryCdByCache(String key) {
        return (List<SysDictryCd>) getCache(CACHE_DICTRY, SYS_DICTRY_CD + key);
    }

    /**
     * 描述： 查询功能资源缓存信息，由代码在此封装Key
     * @param key
     * @return List<SysFunc>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static List<SysFunc> getSysFuncByCache(String key) {
        return (List<SysFunc>) getCache(CACHE_PARM, SYS_FUNC + key);
    }

    /**
     * 描述： 查询表单配置表缓存信息，由代码在此封装Key
     * @param key
     * @return List<SysTabConfig>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/10 10:07
     */
    public static List<SysTabConfig> getSysTabConfigByCache(String key) {
        return (List<SysTabConfig>) getCache(CACHE_PARM, SYS_TAB_CONFIG + key);
    }


    /**
     * 描述：查询单个字典代码缓存
     * @param key 指定字典代码id
     * @param pareKey 类型节点ID
     * @author pengjuntao
     * @return
     */
    public static SysDictryCd getSingleSysDictryCdByCache(String key,String pareKey){
        List<SysDictryCd> list = getSysDictryCdByCache(pareKey);
        for (SysDictryCd dic : list){
            if (dic.getDictryId().equals(key)){
                return dic;
            }
        }
        return null;
    }

    /**
     * 描述：查询单个机构信息
     * @param key 指定机构代码
     * @param superId 指定机构上级id
     * @author pengjuntao
     * @return
     */
    public static SysOrganization getSingleSysOrgInfoByCache01(String key,String superId){
        List<SysOrganization> list = getSysOrgInfoByCache(superId);
        for (SysOrganization org : list) {
            if (org.getId().equals(key)){
                return org;
            }
        }
        return null;
    }

    /**
     * 描述：查询单个机构信息
     * @param key 指定机构层级代码
     * @param superId 指定机构上级id
     * @author pengjuntao
     * @return
     */
    public static SysOrganization getSingleSysOrgInfoByCache02(String key,String superId){
        List<SysOrganization> list = getSysOrgInfoByCache(superId);
        for (SysOrganization org : list) {
            if (org.getGradationCode().equals(key)){
                return org;
            }
        }
        return null;
    }

    /**
     * 描述：通过机构编码获取机构（此方法不是从缓存中获取数据，而是直接查询数据库）
     * @param orgCd
     * @author pengjuntao
     * @return
     */
    public static SysOrganization getSysOrgInfoByOrgCd(String orgCd){
        SysOrganizationService sysOrganizationService =
                (SysOrganizationService) SpringUtil.getBean("sysOrganizationServiceImpl");
        SysOrganization organization = sysOrganizationService.getSysOrgInfoByOrgCd(orgCd);
        return organization;
    }

    /**
     * 描述：通过funcId获取系统功能
     * @param pareFuncId
     * @param funcId
     * @return
     */
    public static SysFunc getSysFuncInfoByFuncId(String pareFuncId,String funcId){
        List<SysFunc> funcList = getSysFuncByCache(pareFuncId);
        for (SysFunc sysFunc : funcList){
            if (funcId.equals(sysFunc.getFuncId())){
                return sysFunc;
            }
        }
        return null;
    }

}
