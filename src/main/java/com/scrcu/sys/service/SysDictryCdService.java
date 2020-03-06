package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysUser;
import org.aspectj.weaver.loadtime.Aj;

import java.util.List;
import java.util.Map;

public interface SysDictryCdService extends IService<SysDictryCd> {

     List<SysDictryCd> listForComboBox(String parentId);

    /**
     * 描述：查询系统字典代码列表
     * @param dictionary        查询条件实体类
     * @param pageParameters    分页
     * @return
     */
     DataGrid listDictionaryCode(SysDictryCd dictionary, PageParameters pageParameters);

    /**
     * 描述：查询指定字典代码类型下的代码值
     * @param pareDictryId      代码类型（父ID）
     * @param pageParameters    分页
     * @param user              当前用户
     * @return
     */
     DataGrid listDictionaryCodeByPid(String blngtoDictryId, PageParameters pageParameters, SysUser user);

    /**
     * 描述：查询字典代码列表（不分页）
     * @param sysDictryCd
     * @return
     */
     List<SysDictryCd> listDictionaryCodeByPidWithoutPage(SysDictryCd sysDictryCd);

    /**
     * 描述：新增代码值到缓存中，待字典类型提交时，一并提交
     * @param sysDictryCd
     * @param user
     * @author pengjuntao
     */
     AjaxResult insertValueToCache(SysDictryCd sysDictryCd, SysUser user);

    /**
     * 描述：从缓存中修改代码值
     * @param sysDictryCd
     * @param dictryId_hid
     * @param user
     * @return
     */
     AjaxResult updateValueFromCache(SysDictryCd sysDictryCd, String dictryId_hid, SysUser user);

    /**
     * 描述：从缓存中删除代码值
     * @param dictryId              代码ID
     * @param user
     * @return
     * @author pengjuntao
     */
     AjaxResult deleteValueFromCache(String dictryId,SysUser user);

    /**
     * 描述：新增代码值到数据库
     * @param sysDictryCd
     * @param
     * @return
     * @author pengjuntao
     */
     AjaxResult insertValueToDB(SysDictryCd sysDictryCd, String dictryCategory);

    /**
     * 描述：
     * @param sysDictryCd
     * @param dictryId_hid
     * @return
     */
     AjaxResult updateValueFromDB(SysDictryCd sysDictryCd,String dictryId_hid);

    /**
     * 描述：从数据库中删除代码值
     * @param dictryId
     * @param blngtoDictryId
     * @return
     * @author pengjuntao
     */
     AjaxResult deleteValueFromDB(String dictryId,String blngtoDictryId);

    /**
     * 描述：新增系统字典代码
     * @param sysDictryCd
     * @param user
     * @return
     */
     AjaxResult insertSysDictryCd(SysDictryCd sysDictryCd,SysUser user);

    /**
     * 描述：修改系统字典代码
     * @param sysDictryCd
     * @param user
     * @return
     */
     AjaxResult updateSysDictryCd(SysDictryCd sysDictryCd, SysUser user);

    /**
     * 描述：删除系统字典代码
     * @param sysDictryCd
     * @return
     */
     AjaxResult deleteSysDictryCd(SysDictryCd sysDictryCd);


     AjaxResult saveDictionCode(SysDictryCd sysDictryCd, String ip, String userId);
     AjaxResult updateDictionCode(SysDictryCd sysDictryCd, String ip, String userId);
     AjaxResult deleteDictionCode(String dictionCode, String blngtoDictryId, String ip, String userId);
     AjaxResult deleteDictionCodeAndVal(String dictionCode,String blngtoDictryId, String ip, String userId);
    /**
     * 通过字典代码类型查询字典代码集合
     * @param dictryId
     * @author pengjuntao
     * @return
     */
     DataGrid getDrawListById(String dictryId, PageParameters pageParameters);

    /**
     * 描述：系统共用组件-系统字典选择（树状展示）数据查询
     * @param id 节点ID
     * @author pengjuntao
     * @return
     */
    String getSysDictryCdByTree(String id, String dictryId);
    /**
     * 描述：系统共用组件-系统字典选择（网格展示）数据查询
     * @param pageParameters 分页组件
     * @param dictryNm  前端查询条件（字典名称）
     * @param dictryId  字典代码类型
     * @author pengjuntao
     * @return
     */
    DataGrid getSysDictryCdByDatagrid(PageParameters pageParameters, String dictryNm, String dictryId);


    /**
     * 描述：查询字典代码详情
     * @param dictryId              字典代码ID
     * @param blngtoDictryId        所属字典代码
     * @return
     */
    SysDictryCd getSysDictryCdByDictryId(String dictryId, String blngtoDictryId);

    /**
     * 描述：查询字典代码详情（从缓存中）
     * @param dictryId
     * @param user
     * @return
     */
    SysDictryCd getSysDictryCdByDictryIdFromCache(String dictryId, SysUser user);




    /**
     * 描述： 查询全部字典表并以所属字典代码ID分组返回map集合
     * @return map<String, List<SysDictryCd>>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/21 21:39
     */
    Map<String, List<SysDictryCd>> getAllSysDictryCd() throws Exception;
    /**
     * 通过字典代码类型查询字典代码集合
     * @param dictryId
     * @author pengjuntao
     * @return
     */
    List<SysDictryCd> getSysDictryCdListById(String dictryId);


}
