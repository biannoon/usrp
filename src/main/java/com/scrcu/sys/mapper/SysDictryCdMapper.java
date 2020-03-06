package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysDictryCdResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：  字典代码管理
 *
 * @创建人： hepengfei
 * @创建时间： 2019/9/11 21:59
 */
public interface SysDictryCdMapper extends BaseMapper<SysDictryCd> {

     /**
      * 描述：查询系统字典详情
      * @param dictryId
      * @param blngtoDictryId
      * @return
      */
     SysDictryCd getSysDictryCdByDicId(@Param("dictryId") String dictryId, @Param("blngtoDictryId") String blngtoDictryId);

     /**
      * 描述：批量新增系统字典
      * @param list
      */
     void insertSysDictryCdBatch(@Param("dicList") List<SysDictryCd> list);

     /**
      * 描述：更新系统字典代码
      * @param sysDictryCd
      */
     void updateSysDictryCd(@Param("sysDictryCd") SysDictryCd sysDictryCd);

     /**
      * 描述：更新系统字典代码值
      * @param sysDictryCd
      * @param dictryId_old
      */
     void updateSysDictryCdValue(@Param("sysDictryCd") SysDictryCd sysDictryCd, @Param("old_id") String dictryId_old);

     /**
      * 描述：删除系统字典
      * @param sysDictryCd
      */
     void deleteSysDictryCd(SysDictryCd sysDictryCd);

     /**
      * 描述：删除系统字典代码值
      * @param sysDictryCd
      */
     void deleteSysDictryCdValue(SysDictryCd sysDictryCd);

     /**
      * 描述：删除系统字典代码（通过ID）
      * @param dictryId
      * @param blngtoDictryId
      */
     void deleteSysDictryCdById(@Param("dictryId") String dictryId,@Param("blngtoDictryId") String blngtoDictryId);

     /**
      * 描述：获取字典引用
      * @param blngtoDictryId
      * @return
      */
     List<SysDictryCdResult> listDrawById(@Param("page") Page page, @Param("blngtoDictryId")String blngtoDictryId);



     void updSysDictryCdByDicId(SysDictryCd sysDictryCd);
     void delSysDictryCdByDicId(@Param("dictryId")String dictryId,@Param("blngtoDictryId")String blngtoDictryId);
     int countFieldByDicId(@Param("blngtoDictryId")String blngtoDictryId);
     void delSysDicsByDicId(@Param("dictryId")String dictryId);




}
