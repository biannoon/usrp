package com.scrcu.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.TreeNode;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.bo.VerifyResult;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.DateUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.common.utils.TreeNodeUtil;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysDictryCdResult;
import com.scrcu.sys.entity.SysOperlog;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.mapper.SysDictryCdMapper;
import com.scrcu.sys.service.SysDictryCdService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("sysDictryCdServiceImpl")
@Transactional
public class SysDictryCdServiceImpl extends ServiceImpl<SysDictryCdMapper,SysDictryCd> implements SysDictryCdService{

    private static Logger logger = LoggerFactory.getLogger(SysDictryCdServiceImpl.class);
    @Autowired
    private SysOperLogServiceImpl sysOperLogService;

    /**
     * 描述：查询字典代码详情
     * @param dictryId
     * @param blngtoDictryId
     * @return
     */
    @Override
    public SysDictryCd getSysDictryCdByDictryId(String dictryId,String blngtoDictryId) {
        SysDictryCd sysDictryCd = null;
        try{
            sysDictryCd = this.baseMapper.getSysDictryCdByDicId(dictryId,blngtoDictryId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("字典管理：字典查询失败");
        }
        return sysDictryCd;
    }

    @Override
    public SysDictryCd getSysDictryCdByDictryIdFromCache(String dictryId, SysUser user) {
        List<SysDictryCd> cacheList =
                (List<SysDictryCd>) EhcacheUtil.getTempCache("SYS_DICTRY_VALUE_INSERT_", user.getUserId());
        SysDictryCd result = null;
        if (cacheList != null){
            for (SysDictryCd sysDictryCd : cacheList){
                if (sysDictryCd.getDictryId().equals(dictryId)){
                    result = sysDictryCd;
                }
            }
            return result;
        }else{
            return new SysDictryCd();
        }
    }

    /**
     * <p>
     * 分页查询字典代码信息
     * </p>
     * @param dictionary 字典代码信息对象
     * @param pageParameters 分页信息对象
     * @return com.scrcu.common.base.vo.DataGrid
     * @author wuyu
     * @date 2019/9/26 15:24
     **/
    public DataGrid listDictionaryCode(SysDictryCd dictionary, PageParameters pageParameters) {
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<SysDictryCd>();
        if (dictionary.getDictryNm() != null && !"".equals(dictionary.getDictryNm())){
            queryWrapper.like("dictry_nm",dictionary.getDictryNm());
        }
        if (dictionary.getDictryId() != null && !"".equals(dictionary.getDictryId())){
            queryWrapper.like("dictry_id",dictionary.getDictryId());
        }
        if (dictionary.getCdTyp() != null && !"".equals(dictionary.getCdTyp())){
            queryWrapper.eq("cd_typ",dictionary.getCdTyp());
        }
        if (dictionary.getPareDictryId() != null && !"".equals(dictionary.getPareDictryId())){
            queryWrapper.eq("pare_dictry_id",dictionary.getPareDictryId());
        }else{
            queryWrapper.eq("pare_dictry_id","root");
        }
        queryWrapper.orderByAsc("dictry_id");
        IPage<SysDictryCd> result =
                this.page(new Page<>(pageParameters.getPage(), pageParameters.getRows()), queryWrapper);
        for (SysDictryCd sysDictryCd : result.getRecords()){
            sysDictryCd.setCdTyp(EhcacheUtil.getSingleSysDictryCdByCache(sysDictryCd.getCdTyp(),"SYS01").getDictryNm());
        }
        return new DataGrid(result.getTotal(), result.getRecords());
    }

    /**
     * <p>
     * 分页查询父节点下字典代码信息
     * </p>
     * @param blngtoDictryId 字典代码信息对象
     * @param pageParameters 分页信息对象
     * @return com.scrcu.common.base.vo.DataGrid
     * @author hepengfei
     * @date 2019/9/26 15:24
     **/
    @Override
    public DataGrid listDictionaryCodeByPid(String blngtoDictryId, PageParameters pageParameters, SysUser user) {
        Page<SysDictryCd> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        List<SysDictryCd> cacheList =
                (List<SysDictryCd>) EhcacheUtil.getTempCache("SYS_DICTRY_VALUE_INSERT_",user.getUserId());
        if (cacheList != null){
            List<SysDictryCd> tempList = new ArrayList<>();
            page.setTotal(cacheList.size());
            for (int i = (int)((page.getCurrent()-1)*page.getSize()); i < page.getCurrent()*page.getSize(); i ++){
                if (cacheList.size() > i) {
                    tempList.add(cacheList.get(i));
                }else{
                    break;
                }
            }
            page.setRecords(tempList);
        }else{
            QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("blngto_dictry_id",blngtoDictryId);
            queryWrapper.eq("cd_typ","SYS0102");
            queryWrapper.orderByAsc("dictry_id");
            page.setRecords(this.baseMapper.selectList(queryWrapper));
        }
        return new DataGrid(page.getTotal(), page.getRecords());
    }

    @Override
    public List<SysDictryCd> listDictionaryCodeByPidWithoutPage(SysDictryCd sysDictryCd) {
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
        List<SysDictryCd> list = null;
        try {
            if (sysDictryCd.getDictryId() != null && !"".equals(sysDictryCd.getDictryId())){
                queryWrapper.eq("dictry_id",sysDictryCd.getDictryId());
            }
            if (sysDictryCd.getBlngtoDictryId() != null && !"".equals(sysDictryCd.getBlngtoDictryId())){
                queryWrapper.eq("blngto_dictry_id",sysDictryCd.getBlngtoDictryId());
            }
            if (sysDictryCd.getCdTyp() != null && !"".equals(sysDictryCd.getCdTyp())){
                queryWrapper.eq("cd_typ",sysDictryCd.getCdTyp());
            }
            list = this.baseMapper.selectList(queryWrapper);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public AjaxResult insertValueToCache(SysDictryCd sysDictryCd, SysUser user) {
        List<SysDictryCd> cacheList =
                (List<SysDictryCd>) EhcacheUtil.getTempCache("SYS_DICTRY_VALUE_INSERT_",user.getUserId());
        if (cacheList == null){
            cacheList = new ArrayList<>();
            EhcacheUtil.initTempCache("SYS_DICTRY_VALUE_INSERT_" + user.getUserId(),cacheList);
        }
        cacheList.add(sysDictryCd);
        return new AjaxResult(true,"新增成功","","");
    }

    @Override
    public AjaxResult updateValueFromCache(SysDictryCd sysDictryCd, String dictryId_hid, SysUser user) {
        List<SysDictryCd> cacheList =
                (List<SysDictryCd>) EhcacheUtil.getTempCache("SYS_DICTRY_VALUE_INSERT_",user.getUserId());
        if (cacheList != null){
            for (SysDictryCd sysDictryCd1 : cacheList){
                if (sysDictryCd1.getDictryId().equals(dictryId_hid)){
                    sysDictryCd1.setDictryId(sysDictryCd.getDictryId());
                    sysDictryCd1.setDictryNm(sysDictryCd.getDictryNm());
                }
            }
        }
        return new AjaxResult(true,"修改成功","","");
    }

    @Override
    public AjaxResult deleteValueFromCache(String dictryId, SysUser user) {
        String[] arr = dictryId.split("_");
        List<String> idList = Arrays.asList(arr);
        List<SysDictryCd> cacheList =
                (List<SysDictryCd>) EhcacheUtil.getTempCache("SYS_DICTRY_VALUE_INSERT_",user.getUserId());
        List<SysDictryCd> tempList = new ArrayList<>();
        if (cacheList != null){
            tempList.addAll(cacheList);
            for (SysDictryCd sysDictryCd : tempList){
                if (idList.contains(sysDictryCd.getDictryId())){
                    cacheList.remove(sysDictryCd);
                }
            }
            return new AjaxResult(true,"删除成功","","");
        }
        return new AjaxResult(false,"删除失败","","");
    }


    @Override
    public AjaxResult insertValueToDB(SysDictryCd sysDictryCd, String dictryCategory) {
        AjaxResult result = null;
        try {
            SysDictryCd dic = this.baseMapper.getSysDictryCdByDicId(dictryCategory,dictryCategory);
            sysDictryCd.setBlngtoDictryId(dic.getBlngtoDictryId());
            sysDictryCd.setPareDictryId(dic.getDictryId());
            sysDictryCd.setCdTyp("SYS0102");
            sysDictryCd.setDictryComnt(dic.getDictryComnt());
            int row = this.baseMapper.insert(sysDictryCd);
            if (row > 0){
                result = new AjaxResult(true,"新增成功","","");
            } else {
                result = new AjaxResult(false,"新增失败","","");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public AjaxResult updateValueFromDB(SysDictryCd sysDictryCd, String dictryId_hid) {
        AjaxResult result = null;
        try {
            SysDictryCd dic = this.baseMapper.getSysDictryCdByDicId(dictryId_hid,sysDictryCd.getBlngtoDictryId());
            if (dic != null){
                sysDictryCd.setCdTyp(dic.getCdTyp());
                sysDictryCd.setDictryComnt(dic.getDictryComnt());
                sysDictryCd.setPareDictryId(dic.getPareDictryId());
                sysDictryCd.setBlngtoDictryId(dic.getBlngtoDictryId());
                this.baseMapper.updateSysDictryCdValue(sysDictryCd,dictryId_hid);
                result = new AjaxResult(true,"修改成功","","");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


    @Override
    public AjaxResult deleteValueFromDB(String dictryId, String blngtoDictryId) {
        AjaxResult result = null;
        try {
            this.baseMapper.deleteSysDictryCdById(dictryId,blngtoDictryId);
            result = new AjaxResult(true,"删除成功","","");
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public AjaxResult insertSysDictryCd(SysDictryCd sysDictryCd,SysUser user) {
        AjaxResult result = null;
        try{
            sysDictryCd.setBlngtoDictryId(sysDictryCd.getDictryId());
            List<SysDictryCd> list = new ArrayList<>();
            list.add(sysDictryCd);
            //-从缓存获取代码值
            List<SysDictryCd> cacheList =
                    (List<SysDictryCd>) EhcacheUtil.getTempCache("SYS_DICTRY_VALUE_INSERT_",user.getUserId());
            if (cacheList != null){
                for (SysDictryCd dic : cacheList){
                    dic.setPareDictryId(sysDictryCd.getDictryId());
                    dic.setBlngtoDictryId(sysDictryCd.getDictryId());
                    dic.setCdTyp("SYS0102");
                    dic.setDictryComnt(sysDictryCd.getDictryComnt());
                    list.add(dic);
                }
            }
            for (SysDictryCd cd : list){
                System.out.println("新增：" + cd);
            }
            this.baseMapper.insertSysDictryCdBatch(list);
            result = new AjaxResult(true,"新增成功","","");
            //-清空缓存
            EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,"SYS_DICTRY_VALUE_INSERT_" + user.getUserId());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("新增失败");
        }
        return result;
    }

    @Override
    public AjaxResult updateSysDictryCd(SysDictryCd sysDictryCd, SysUser user) {
        AjaxResult result = null;
        try{
            //-删除之前的该字典种类下的所有代码值
            this.baseMapper.deleteSysDictryCdValue(sysDictryCd);
            //-获取缓存中存放的代码值
            List<SysDictryCd> cacheList =
                    (List<SysDictryCd>) EhcacheUtil.getTempCache("SYS_DICTRY_VALUE_INSERT_",user.getUserId());
            if (cacheList != null){
                for (SysDictryCd dic : cacheList){
                    dic.setPareDictryId(sysDictryCd.getDictryId());
                    dic.setBlngtoDictryId(sysDictryCd.getDictryId());
                    dic.setCdTyp("SYS0102");
                    dic.setDictryComnt(sysDictryCd.getDictryComnt());
                }
                this.baseMapper.insertSysDictryCdBatch(cacheList);
            }
            this.baseMapper.updateSysDictryCd(sysDictryCd);
            result = new AjaxResult(true,"修改成功","","");
            //-清空缓存
            EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,"SYS_DICTRY_VALUE_INSERT_" + user.getUserId());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
        return result;
    }

    @Override
    public AjaxResult deleteSysDictryCd(SysDictryCd sysDictryCd) {
        AjaxResult result = null;
        try{
            this.baseMapper.deleteSysDictryCd(sysDictryCd);
            result = new AjaxResult(true,"删除成功","","");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }


    public DataGrid getDrawListById(String dictryId, PageParameters pageParameters) {
        Page<SysDictryCdResult> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        try {
            page.setRecords(this.baseMapper.listDrawById(page,dictryId));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return new DataGrid(page.getTotal(), page.getRecords());
    }

    /**
     * <p>
     * 查询标准字典代码列表
     * </p>
     * @param parentId 父级字典代码id
     * @return java.util.List<com.scrcu.sys.entity.SysDictryCd>
     * @author wuyu
     * @date 2019/9/27 15:21
     **/
    public List<SysDictryCd> listForComboBox(String parentId) {
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("dictry_id", "dictry_nm").eq("pare_dictry_id", parentId);
        return this.list(queryWrapper);
    }
    /**
     * <p>
     * 新增字典代码接口
     * </p>
     * @param sysDictryCd 字典代码信息对象
     * @param ip 客户端请求IP地址
     * @param userId 用户id（OA号）
     * @return com.scrcu.common.base.vo.AjaxResult
     * @author hepengfei
     * @date 2019/9/24 14:35
     **/
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult saveDictionCode(SysDictryCd sysDictryCd, String ip, String userId) {
                VerifyResult nullVerify = verifyTableInterfaceValue(sysDictryCd, false);
            if (!nullVerify.isVerifyQualified()) {
                return new AjaxResult(false, "新增失败," + nullVerify.getInformation());
            }
            if (!this.save(sysDictryCd)) {
                throw new RuntimeException("新增字典代码信息失败。");
            }
            //存储日志
            this.logger.info("操作人OA号：" + userId + "；操作类型：新增字典代码信息；数据接口ID："
                    + sysDictryCd.getDictryId()+ "。");
            String id = CommonUtil.getUUID(32);
            SysOperlog log = new SysOperlog(id, DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"),
                    userId, ip, "SYS09DIC01", sysDictryCd.getDictryId(),"新增字典代码信息。");
            if (this.sysOperLogService.insert(log ) < 1) {
                throw new RuntimeException("存储新增数据接口操作日志失败。");
            }
        return new AjaxResult(true, "新增字典代码信息成功。");
    }
    /**
     * <p>
     * 更新字典代码信息
     * </p>
     * @param sysDictryCd 字典代码信息对象
     * @param ip 客户端请求IP地址
     * @param userId 用户id（OA号）
     * @return com.scrcu.common.base.vo.AjaxResult
     * @author hepengfei
     * @date 2019/9/19 10:50
     **/
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateDictionCode(SysDictryCd sysDictryCd, String ip, String userId)
    {
        //字典代码信息 数据校验
        VerifyResult nullVerify = verifyTableInterfaceValue(sysDictryCd, true);
        if (!nullVerify.isVerifyQualified()) {
            return new AjaxResult(false, "更新失败," + nullVerify.getInformation());
        }
        SysDictryCd data = this.getSysDictryCdByDictryId(sysDictryCd.getDictryId(),sysDictryCd.getBlngtoDictryId());
        if(data == null){
            return new AjaxResult(false, "更新失败," + "字典代码对象信息不存在");
        }try{
          //更新字典代码信息
          this.baseMapper.updSysDictryCdByDicId(sysDictryCd);
         }catch(Exception e){
            e.printStackTrace();
        }
        //存储日志
        this.logger.info("操作人OA号：" + userId + "；操作类型：更新字典代码信息；数据接口ID："
                + data.getDictryId() + "。");
        String id = CommonUtil.getUUID(32);
        SysOperlog log = new SysOperlog(id, DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"),
                userId, ip, "SYS09DIC02", data.getDictryId(),"更新字典代码信息。");
        if (this.sysOperLogService.insert(log) < 1) {
            throw new RuntimeException("存储“更新数据接口操作日志”时发生异常。");
        }
        return new AjaxResult(true, "更新字典代码信息成功。");
    }
    /**
     * <p>
     * 删除字典代码接口。
     * </p>
     * @param userId 登陆用户OA号
     * @param ip 客户点请求IP
     * @return com.scrcu.common.base.vo.AjaxResult
     * @author hepengfei
     * @date 2019/9/19 10:50
     **/
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteDictionCode(String dictryid,String blngtoDictryId,String userId, String ip){
        SysDictryCd sysDictryCd = this.getSysDictryCdByDictryId(dictryid,blngtoDictryId);
        if (sysDictryCd == null) {
            return new AjaxResult(false, "数据异常，删除字典代码信息接口失败。");
        }
        try{
            this.baseMapper.delSysDictryCdByDicId(dictryid,blngtoDictryId);
        }catch (Exception e){
            log.error("删除失败，错误信息"+e.getMessage());
        }
        //存储日志
        this.logger.info("操作人OA号：" + userId + "；操作类型：" +  "删除字典代码接口；数据接口ID："
                + dictryid + "。");
        String uuid = CommonUtil.getUUID(32);
        SysOperlog log = new SysOperlog(uuid, DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"),
                userId, ip, "SYS09DIC03", dictryid, "删除字典代码信息。");
        if (this.sysOperLogService.insert(log) < 1) {
            throw new RuntimeException("存储“删除数据操作日记”失败。");
        }
        return new AjaxResult(true, "删除字典代码信息成功。");
    }

    /**
     * <p>
     * 删除字典代码接口。
     * </p>
     * @param dictryid 数据接口ID
     * @param userId 登陆用户OA号
     * @param ip 客户点请求IP
     * @return com.scrcu.common.base.vo.AjaxResult
     * @author hepengfei
     * @date 2019/9/19 10:50
     **/
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteDictionCodeAndVal(String dictryid,String blngtoDictryId,String userId, String ip){
        SysDictryCd sysDictryCd = this.getSysDictryCdByDictryId(dictryid,blngtoDictryId);
        if (sysDictryCd == null) {
            return new AjaxResult(false, "数据异常，删除字典代码信息接口失败。");
        }
        int i = this.baseMapper.countFieldByDicId(blngtoDictryId);
        if(i>0){
            return new AjaxResult(false, "字典代码已被引用，不能删除。");
        }
        try{
            this.baseMapper.delSysDicsByDicId(dictryid);
        }catch (Exception e){
            log.error("删除失败，错误信息"+e.getMessage());
        }
        //存储日志
        this.logger.info("操作人OA号：" + userId + "；操作类型：" +  "删除字典代码接口；数据接口ID："
                + dictryid + "。");
        String uuid = CommonUtil.getUUID(32);
        SysOperlog log = new SysOperlog(uuid, DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"),
                userId, ip, "SYS09DIC03", dictryid, "删除字典代码信息。");
        if (this.sysOperLogService.insert(log) < 1) {
            throw new RuntimeException("存储“删除数据操作日记”失败。");
        }
        return new AjaxResult(true, "删除字典代码信息成功。");
    }
    /**
     * <p>
     * 校验字典代码信息数据是否完整 不能为空
     * </p>
     * @param sysDictryCd 字典代码对象
     * @param isUpdate 是否更新字典代码信息操作
     * @return com.scrcu.common.base.bo.VerifyResult
     * @author hepengfei
     * @date 2019/9/19 10:50
     **/
    private VerifyResult verifyTableInterfaceValue(SysDictryCd sysDictryCd, boolean isUpdate) {

        if (sysDictryCd == null) {
            return new VerifyResult(false, "字典代码信息为空。");
        }
        if (isUpdate) {
            if (com.alibaba.druid.util.StringUtils.isEmpty(sysDictryCd.getDictryId())) {
                return new VerifyResult(false, "字典代码Id不能为空。");
            }
        }
        String[] values = {sysDictryCd.getDictryId()};
        for (String value : values) {
            if (com.alibaba.druid.util.StringUtils.isEmpty(value)) {
                return new VerifyResult(false, "字典代码信息字段值不能为空。");
            }
        }
        return new VerifyResult(true, "");
    }

    /**
     * <p>
     * 查询单个标准字典代码
     * </p>
     * @param dictryCd 标准字典代码对象
     * @return com.scrcu.sys.entity.SysDictryCd
     * @author wuyu
     * @date 2019/10/11 14:26
     **/
    public SysDictryCd getByKeyword(SysDictryCd dictryCd) {
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(dictryCd.getDictryId()), "dictry_id", dictryCd.getDictryId())
                .eq(StringUtils.isEmpty(dictryCd.getPareDictryId()),
                        "pare_dictry_id", dictryCd.getPareDictryId());
        return this.getOne(queryWrapper);
    }

    /**
     * 描述：系统公用组件-系统标准字典组件：查询系统字典集合（以树状形式展示）
     * @param id 父节点ID
     * @param dictryId 字典代码类型
     * @author pengjuntao
     * @return 封装成node对象的系统字典信息
     */
    @Override
    public String getSysDictryCdByTree(String id,String dictryId) {
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
        List<TreeNode> nodeList = null;
        List<SysDictryCd> dicList = null;
        try{
            //如果字典代码类型不为空
            if (dictryId != null && !"".equals(dictryId)){
                if (id != null && !"".equals(id)){
                    queryWrapper.eq("pare_dictry_id",id).orderByAsc("dictry_id");
                }else{
                    queryWrapper.eq("blngto_dictry_id",dictryId);
                    queryWrapper.eq("pare_dictry_id","root");
                }
                dicList = this.baseMapper.selectList(queryWrapper);
            }else{
                if (id != null && !"".equals(id)){
                    queryWrapper.eq("pare_dictry_id",id);
                    dicList = this.baseMapper.selectList(queryWrapper);
                }else{
                    dicList = new ArrayList<>();
                    //如果是第一次查询，显示虚拟节点
                    SysDictryCd dic = new SysDictryCd();
                    dic.setDictryId("root");
                    dic.setDictryNm("系统标准字典");
                    dic.setCdTyp("SYS0101");
                    dicList.add(dic);
                }
            }
            nodeList = TreeNodeUtil.fillSysDictryCdLevel1(dicList);
        }catch (Exception e){
            throw new RuntimeException("系统字典查询：查询失败");
        }
        return JSON.toJSONString(nodeList);
    }

    /**
     * 描述：系统共用组件-系统字典选择（网格展示）数据查询
     * @param pageParameters 分页组件
     * @param dictryNm  前端查询条件（字典名称）
     * @param dictryId  字典代码类型
     * @return
     */
    @Override
    public DataGrid getSysDictryCdByDatagrid(PageParameters pageParameters, String dictryNm, String dictryId) {
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
        Page<SysDictryCd> page = null;
        IPage<SysDictryCd> ipage = null;
        try{
            if (dictryNm != null && !"".equals(dictryNm))
                queryWrapper.like("dictry_nm",dictryNm);
            if (dictryId != null && !"".equals(dictryId))
                queryWrapper.eq("pare_dictry_id",dictryId);
            page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
            ipage = this.page(page,queryWrapper);
        }catch (Exception e){
            throw new RuntimeException("系统字典查询：查询失败");
        }
        return new DataGrid(ipage.getTotal(),ipage.getRecords());
    }

    @Override
    public Map<String, List<SysDictryCd>> getAllSysDictryCd() throws Exception {
        Map<String, List<SysDictryCd>> map;
        try {
            QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
            queryWrapper.ne("pare_dictry_id","root");
            List<SysDictryCd> sysDictryCdList = this.baseMapper.selectList(queryWrapper);
            map = groupByObjectKey(sysDictryCdList);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return map;
    }

    /**
     * 描述： 字典表List根据所属字典代码ID分组放入集合
     * @param sysDictryCdList
     * @return map<String, List<SysDictryCd>>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/21 21:39
     */
    private static Map<String, List<SysDictryCd>> groupByObjectKey(List<SysDictryCd> sysDictryCdList) {
        Map<String, List<SysDictryCd>> map = new HashMap<>();
        for (SysDictryCd sysDictryCd : sysDictryCdList) {
            if(!map.containsKey(sysDictryCd.getBlngtoDictryId())) {
                map.put(sysDictryCd.getBlngtoDictryId(), new ArrayList<>());
            }
            List<SysDictryCd> tempList = map.get(sysDictryCd.getBlngtoDictryId());
            tempList.add(sysDictryCd);
        }
        return map;
    }

    @Override
    public List<SysDictryCd> getSysDictryCdListById(String dictryId) {
        List<SysDictryCd> list = null;
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
        try {
            queryWrapper.eq("pare_dictry_id",dictryId);
            list = this.baseMapper.selectList(queryWrapper);
        } catch (Exception e){
            throw new RuntimeException("系统字典查询：查询系统字典失败");
        }
        return list;
    }

    /**
     * <p>
     * 获取单个标准字典代码值
     * </p>
     * @param dictionaryId 字典代码id值
     * @param parentId 上级字典代码id值
     * @return com.scrcu.sys.entity.SysDictryCd
     * @author wuyu
     * @date 2019/10/29 17:38
     **/
    public SysDictryCd getDictionaryCode(String dictionaryId, String parentId) {
        QueryWrapper<SysDictryCd> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dictry_id", dictionaryId).eq("pare_dictry_id", parentId);
        return getOne(queryWrapper);
    }
}
