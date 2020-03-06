package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.DataList;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.*;
import com.scrcu.sys.mapper.SysGroupMapper;
import com.scrcu.sys.service.SysGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述： 用户组接口层实现类
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/19 10:22
 */
@Service
@Transactional
public class SysGroupServiceImpl
        extends ServiceImpl<SysGroupMapper, SysGroup> implements SysGroupService {
    //-----------------------------pengjuntao重写用户组模块功能---------------------
    @Autowired
    private SysOrganizationServiceImpl sysOrganizationService;

    @Override
    public DataGrid listSysGroup(SysGroup sysGroup, PageParameters pageParameters) {
        Page<SysGroup> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        try {
            page.setRecords(this.baseMapper.listSysGroup(page,sysGroup));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }

    @Override
    public DataGrid listRecoursByGroupId(SysGroupRecoursRel sysGroupRecoursRel, PageParameters pageParameters) {
        Page<SysGroupRecoursRel> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        try {
            if (EhcacheUtil.getCacheKeys(EhcacheUtil.CACHE_TEMP) != null &&
                    EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES,sysGroupRecoursRel.getGroupId()) != null){
                List<SysGroupRecoursRel> list =
                        (List<SysGroupRecoursRel>) EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES,sysGroupRecoursRel.getGroupId());
                List<SysGroupRecoursRel> tempList = new ArrayList<>();
                page.setTotal(list.size());
                //-自定义分页
                for (int i = (int)((page.getCurrent()-1)*page.getSize()); i < page.getCurrent()*page.getSize(); i ++){
                    if (list.size() > i) {
                        tempList.add(list.get(i));
                    }else{
                        break;
                    }
                }
                page.setRecords(tempList);
            }else{
                if (!CommonUtil.isEmpty(sysGroupRecoursRel.getGroupId())){
                    page.setRecords(this.baseMapper.listRecoursByGroupId(page,sysGroupRecoursRel));
                }
            }
            List<SysGroupRecoursRel> relList = page.getRecords();
            /*for (SysGroupRecoursRel rel : relList){
                rel.setRecoursTyp(EhcacheUtil.getSingleSysDictryCdByCache(rel.getRecoursTyp(),"SYS11").getDictryNm());
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }

    @Override
    public List<SysGroupRecoursRel> listRecoursByGroupIdWithoutPage(SysGroup sysGroup) {
        List<SysGroupRecoursRel> list = null;
        try{
            SysGroupRecoursRel rel = new SysGroupRecoursRel();
            rel.setGroupId(sysGroup.getGroupId());
            list = this.baseMapper.listRecoursByGroupId(null,rel);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public DataGrid listSysUserByGroupId(String groupId, PageParameters pageParameters) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        Page<SysUser> page = null;
        SysGroup group = new SysGroup();
        group.setGroupId(groupId);
        try{
            page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
            page.setRecords(this.baseMapper.listSysUserByGroupId(page, group));
            List<SysUser> list = page.getRecords();
            for (SysUser user : list){
                user.setStus(EhcacheUtil.getSingleSysDictryCdByCache(user.getStus(),"SYS05").getDictryNm());
            }
            page.setRecords(list);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }

    @Override
    public SysGroup getSysGroupById(String id){
        SysGroup sysGroup = null;
        try{
            sysGroup = this.baseMapper.selectById(id);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return sysGroup;
    }

    @Override
    public AjaxResult addSysGroupResources(String resources, String resourceType, String groupId) {
        //数组转集合
        String[] arr = resources.split(",");
        List<String> strlist = Arrays.asList(arr);
        List<String> idList = new ArrayList<>();//存储资源ID集合，去重
        //判断临时缓存中资源是否为空
        List<SysGroupRecoursRel> existedResources = null;
        List<String> keys = EhcacheUtil.getCacheKeys(EhcacheUtil.CACHE_TEMP);
        if (keys != null && keys.size() > 0){
            if (keys.contains(EhcacheUtil.TEMP_GROUP_RES + groupId)){
                existedResources = (List<SysGroupRecoursRel>)EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES, groupId);
            }else {
                existedResources = new ArrayList<>();
            }
        }else {
            existedResources = new ArrayList<>();
        }
        //-获取缓存中的resourceId,用于去重
        if (existedResources.size() > 0){
            for (SysGroupRecoursRel recoursRel : existedResources){
                idList.add(recoursRel.getRecoursId());
            }
        }else {
            idList = new ArrayList<>();
        }
        //-添加缓存并去重
        for (String str : strlist){
            if (!idList.contains(str.substring(0,str.indexOf("$")))){
                SysGroupRecoursRel re = new SysGroupRecoursRel();
                re.setRecoursId(str.substring(0,str.indexOf("$")));
                re.setRecoursNm(str.substring(str.indexOf("$")+1));
                re.setGroupId(groupId);
                re.setRecoursTyp(resourceType);
                existedResources.add(re);
            }
        }
        //-清除指定缓存
        EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,EhcacheUtil.TEMP_GROUP_RES + groupId);
        //-添加指定缓存
        EhcacheUtil.initTempCache(EhcacheUtil.TEMP_GROUP_RES + groupId, existedResources);
        return new AjaxResult(true,"资源添加成功","","");
    }

    @Override
    public AjaxResult deleteSysGroupResources(String resources, String groupId) {
        String[] strArr = resources.split("_");
        List<String> strList = Arrays.asList(strArr);
        List<SysGroupRecoursRel> tempList = new ArrayList<>();
        List<String> keys = EhcacheUtil.getCacheKeys(EhcacheUtil.CACHE_TEMP);
        if (keys != null && keys.size() > 0){
            if (keys.contains(EhcacheUtil.TEMP_GROUP_RES + groupId)){
                List<SysGroupRecoursRel> list =
                        (List<SysGroupRecoursRel>) EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES,groupId);
                tempList.addAll(list);
                if (list != null){
                    for (SysGroupRecoursRel recoursRel : list){
                        if (strList.contains(recoursRel.getRecoursId())){
                            tempList.remove(recoursRel);
                        }
                    }
                }
                //-清除指定缓存
                EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,EhcacheUtil.TEMP_GROUP_RES + groupId);
                //-添加指定缓存
                EhcacheUtil.initTempCache(EhcacheUtil.TEMP_GROUP_RES + groupId, tempList);
            }
        }
        return new AjaxResult(true,"资源删除成功","","");
    }

    public AjaxResult deleteSysGroupResourcesFromDB(String resources, String groupId) {
        AjaxResult result = null;
        String[] strArr = resources.split("_");
        List<String> idList = Arrays.asList(strArr);
        try{
            this.baseMapper.deleteSysGroupResoursById(idList,groupId);
            result = new AjaxResult(true,"资源删除成功","","");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public AjaxResult addSysGroupResourcesToDB(String resources, String resourceType, String groupId) {
        AjaxResult result = null;
        String[] strArr = resources.split(",");
        List<String> idList = Arrays.asList(strArr);
        List<SysGroupRecoursRel> recoursRels = new ArrayList<>();
        //-删除之前的机构资源
        SysGroupRecoursRel rel = new SysGroupRecoursRel();
        rel.setGroupId(groupId);
        rel.setRecoursTyp(resourceType);
        this.baseMapper.deleteSysGroupResoursBatch(rel);
        //-新增选中的机构资源
        for (String str : idList){
            SysGroupRecoursRel recoursRel = new SysGroupRecoursRel();
            recoursRel.setGroupId(groupId);
            recoursRel.setRecoursTyp(resourceType);
            recoursRel.setRecoursId(str.substring(0,str.indexOf("$")));
            recoursRel.setRecoursNm(str.substring(str.indexOf("$")+1));
            recoursRels.add(recoursRel);
        }
        try{
            this.baseMapper.insertSysGroupRecoursRel(recoursRels);
            result = new AjaxResult(true,"资源新增成功","","");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public String listOrgInfoFromSysGroup(String id) {
        return sysOrganizationService.listAllSysOrgInfoWithoutLvl(id);
    }

    @Override
    public String isExistUserUnderGroup(String groupId) {
        String result = "";
        try{
            int num = this.baseMapper.isExistUserUnderGroup(groupId);
            if (num > 0){
                result = "1";
            }else{
                result = "0";
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public AjaxResult insertSysGroup(SysGroup sysGroup) {
        AjaxResult result = null;
        try{
            int row = this.baseMapper.insert(sysGroup);
            sysGroup = this.baseMapper.selectById(sysGroup.getGroupId());
            if (row > 0){
                //用户组新增成功之后，添加用户组资源
                if (EhcacheUtil.getCacheKeys(EhcacheUtil.CACHE_TEMP) != null &&
                        EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES,sysGroup.getGroupId()) != null){
                    List<SysGroupRecoursRel> list =
                            (List<SysGroupRecoursRel>) EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES,sysGroup.getGroupId());
                    this.baseMapper.insertSysGroupRecoursRel(list);
                    //-清除缓存
                    EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,EhcacheUtil.TEMP_GROUP_RES + sysGroup.getGroupId());
                }
                result = new AjaxResult(true,"新增用户组成功",sysGroup,"");
            }else{
                result = new AjaxResult(false,"新增用户组失败","","");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public AjaxResult updateSysGroup(SysGroup sysGroup) {
        AjaxResult result = null;
        try{
            int row = this.baseMapper.updateById(sysGroup);
            sysGroup = this.baseMapper.selectById(sysGroup.getGroupId());
            if (row > 0){
                //用户组修改成功之后，添加用户组资源
                if (EhcacheUtil.getCacheKeys(EhcacheUtil.CACHE_TEMP) != null &&
                        EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES,sysGroup.getGroupId()) != null){
                    List<SysGroupRecoursRel> list =
                            (List<SysGroupRecoursRel>) EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES,sysGroup.getGroupId());
                    if (list != null && list.size() > 0){
                        //先删除之前的资源数据
                        this.baseMapper.deleteSysGroupRecoursRelByGroupId(sysGroup.getGroupId());
                        //再新增新的资源数据
                        this.baseMapper.insertSysGroupRecoursRel(list);
                    }
                    //-清除缓存
                    EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,EhcacheUtil.TEMP_GROUP_RES + sysGroup.getGroupId());
                }
                result = new AjaxResult(true,"修改用户组成功",sysGroup,"");
            }else{
                result = new AjaxResult(false,"修改用户组失败","","");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public AjaxResult deleteSysGroup(String groupId) {
        AjaxResult result = null;
        try{
            this.baseMapper.deleteById(groupId);
            this.baseMapper.deleteSysGroupRecoursRelByGroupId(groupId);
            result = new AjaxResult(true,"删除用户组成功","","");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


    //----------------------------------jiyuanbo--------------------------------------------
    @Override
    public boolean insert(SysGroup sysGroup) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.insert(sysGroup);
            if(count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean delete(String groupId) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.deleteById(groupId);
            if(count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean update(SysGroup sysGroup) {
        boolean bool = false;
        try {
            int count = this.baseMapper.updateById(sysGroup);
            if(count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        return bool;
    }

    @Override
    public SysGroup getById(String groupId) throws Exception {
        SysGroup sysGroup;
        try {
            sysGroup = this.baseMapper.selectById(groupId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sysGroup;
    }

    @Override
    public IPage<SysGroup> getByPage(Page page, SysGroup sysGroup) throws Exception {
        IPage iPage;
        try {
            QueryWrapper<SysGroup> queryWrapper = new QueryWrapper<>();
            if (!CommonUtil.isEmpty(sysGroup.getBlngtoOrgNo())) {
                queryWrapper.in("blngto_org_no", sysGroup.getBlngtoOrgNo());
            }
            if (!CommonUtil.isEmpty(sysGroup.getGroupNm())) {
                queryWrapper.like("group_nm", sysGroup.getGroupNm());
            }
            iPage = this.baseMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return iPage;
    }

    @Override
    public boolean isExists(String groupId) throws Exception {
        boolean bool = false;
        try {
            SysGroup sysGroup = this.baseMapper.selectById(groupId);
            if (null != sysGroup) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean saveGroupResouseRel(String groupId, List<String> resouseList, String resouseType) throws Exception {
        if (null == groupId || null == resouseList) {
            return false;
        } else {
            int count = 0;
            this.baseMapper.delGroupResouseByGroupId(groupId, resouseType);
            for (String resouse : resouseList) {
                String[] resouseArry = resouse.split(",");
                    this.baseMapper.insertGroupResouse(groupId,
                            resouseArry[0], resouseArry[1], resouseType);
                    count ++;
            }
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SysGroup> getGroupListByUserId(String userId) throws Exception {
        List<SysGroup> groupList;
        try {
            groupList = this.baseMapper.getGroupListByUserId(userId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return groupList;
    }

    @Override
    public List<SysGroup> getGroupListByLoginUser(String userId, List<SysRole> roleList) throws Exception {
        List<SysGroup> groupList;
        try {
            boolean bool = false;
            for (SysRole sysRole : roleList) {
                if ("ADMIN".equalsIgnoreCase(sysRole.getRoleId())) {
                    bool = true;
                }
            }
            if (bool) {
                groupList = this.baseMapper.selectList(new QueryWrapper<>());
            } else {
                groupList = this.baseMapper.getGroupListByLoginUser(userId);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return groupList;
    }

    @Override
    public List<SysGroup> getGroupListByLoginUser_re(SysUser sysUser) {
        List<SysOrganization> orgList = CommonUtil.getAllOrgInfoByUserId(sysUser);
        List<String> strList = new ArrayList<>();
        List<SysGroup> resultList = null;
        for (SysOrganization org : orgList){
            strList.add(org.getCode());
        }
        QueryWrapper<SysGroup> queryWrapper = new QueryWrapper<>();
        try{
            queryWrapper.in("BLNGTO_ORG_NO",strList).orderByAsc("GROUP_ID");
            resultList = this.baseMapper.selectList(queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return resultList;
    }


    @Override
    public List<String> getOrgResouseByUserId(String userId) throws Exception {
        List<String> orgList = new ArrayList<>();
        try {
            List<SysGroup> groupList = getGroupListByUserId(userId);
            if (null == groupList || groupList.size() == 0) {
                return null;
            } else {
                for (SysGroup sysGroup : groupList) {
                    List<String> tempList = this.baseMapper.
                            getGroupResouseRel(sysGroup.getGroupId(), "SYS1101");
                    for (String str : tempList) {
                        List<String> temp = this.baseMapper.getOrgCdListByParent(str);
                        if (null != temp && temp.size() > 0) {
                            for (String orgId : temp) {
                                orgList.add(orgId);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return orgList;
    }

    @Override
    public List<DataList> getGroupListWithNotice(String pubNoticeId) {
        List<SysGroup> groups = this.baseMapper.getGroupListWithNotice(pubNoticeId);
        return getDataList(groups);
    }

    @Override
    public List<DataList> getGroupNotInPubNotice(String pubNoticeId) {
        List<SysGroup> groups = this.baseMapper.getGroupNotInPubNotice(pubNoticeId);
        return getDataList(groups);
    }

    private List<DataList> getDataList(List<SysGroup> groups) {
        List<DataList> result = new ArrayList<>(groups.size());
        DataList dataList;
        for (SysGroup group : groups) {
            dataList = new DataList();
            dataList.setText(group.getGroupNm());
            dataList.setValue(group.getGroupId());
            result.add(dataList);
        }
        return result;
    }
    @Override
    public DataGrid getShowGroupUser(PageParameters pageParameters,String userId, SysGroup sysGroup) throws Exception {
        Page<SysGroup> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        try{
            sysGroup.setCreatr(userId);

            page.setRecords(this.baseMapper.selectSysGroupList(page,sysGroup));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("用户组列表：用户对应用户组列表查询失败");
        }
        return new DataGrid(page.getTotal(),page.getRecords());
    }
}
