package com.scrcu.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysParam;

import java.util.Map;

/**
 * 描述： 系统参数管理接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/17 17:03
 */
public interface SysParamService extends IService<SysParam> {

    /**
     * 描述： 系统参数新增
     * @param sysParam
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/17 17:03
     */
    boolean insert(SysParam sysParam) throws Exception;

    /**
     * 描述： 系统参数删除
     * @param paramId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/17 17:03
     */
    boolean delete(String paramId) throws Exception;

    /**
     * 描述： 系统参数修改
     * @param sysParam
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/17 17:03
     */
    boolean update(SysParam sysParam) throws Exception;

    /**
     * 描述： 系统参数查询
     * @param paramId
     * @return SysParam
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/17 17:03
     */
    SysParam getById(String paramId) throws Exception;

    /**
     * 描述： 系统参数分页查询
     * @param page
     * @param sysParam
     * @return IPage
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/17 17:03
     */
    IPage<SysParam> getByPage(Page page, SysParam sysParam) throws Exception;

    /**
     * 描述： 判断系统参数是否存在
     * @param paramId
     * @return boolean
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/17 17:03
     */
    boolean isExists(String paramId) throws Exception;

	/**
     * 描述： 根据系统参数类型获取参数值
     * @param paramTyp
     * @return Map
     * @创建人： hepengfei
     * @创建时间： 2019/10/30 17:03
     */
    Map<String,String> GetSysParamMap(String paramTyp);


    /**
     * 描述：通过系统参数类型获取参数值
     * @param paramTyp
     * @return
     */
    DataGrid getSysParamByParamType(PageParameters pageParameters, String paramTyp);
}
