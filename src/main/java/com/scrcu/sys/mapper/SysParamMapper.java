package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scrcu.sys.entity.SysParam;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 描述： 系统参数mapper
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/17 17:07
 */
public interface SysParamMapper extends BaseMapper<SysParam> {

    @Insert("INSERT INTO ASC_PARAM VALUES(#{paramId},'','',#{paramValue},#{paramComnt})")
    int insertAscParam(String paramId, String paramValue, String paramComnt) throws Exception;

    @Delete("DELETE FROM ASC_PARAM WHERE ID=#{paramId}")
    int deleteAscParam(String paramId) throws Exception;

    @Update("UPDATE ASC_PARAM SET PARAM_VALUE=#{paramValue},PARAM_DESC=#{paramComnt} WHERE ID=#{paramId}")
    int updateAscParam(String paramValue, String paramComnt, String paramId) throws Exception;
    List<SysParam> listSysParamByTyp(String paramTyp);

}
