package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.sys.entity.SysParam;
import com.scrcu.sys.mapper.SysParamMapper;
import com.scrcu.sys.service.SysParamService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述： 系统参数接口实现类
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/17 17:03
 */
@Service("sysParamServiceImpl")
public class SysParamServiceImpl
        extends ServiceImpl<SysParamMapper, SysParam> implements SysParamService {
    @Override
    public boolean insert(SysParam sysParam) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.insert(sysParam);
            if (count > 0 && "SYS0801".equalsIgnoreCase(sysParam.getParamTyp())) {
                this.baseMapper.insertAscParam(sysParam.getParamId(),
                        sysParam.getParamValue(), sysParam.getParamComnt());
                bool = true;
            }else if(count>0){
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean delete(String paramId) throws Exception {
        boolean bool = false;
        try {
            SysParam sysParam = this.baseMapper.selectById(paramId);
            int count = this.baseMapper.deleteById(paramId);
            if (count > 0){
                if ("SYS0801".equalsIgnoreCase(sysParam.getParamTyp())){
                    this.baseMapper.deleteAscParam(paramId);
                }
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean update(SysParam sysParam) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.updateById(sysParam);
            if (count > 0 && "SYS0801".equalsIgnoreCase(sysParam.getParamTyp())) {
                this.baseMapper.updateAscParam(sysParam.getParamValue(),
                        sysParam.getParamComnt(), sysParam.getParamId());
                bool = true;
            }else if(count>0){
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public SysParam getById(String paramId) throws Exception {
        SysParam sysParam;
        try {
            sysParam = this.baseMapper.selectById(paramId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sysParam;
    }

    @Override
    public IPage<SysParam> getByPage(Page page, SysParam sysParam) throws Exception {
        QueryWrapper<SysParam> queryWrapper = new QueryWrapper<>();
        IPage<SysParam> iPage;
        if (!CommonUtil.isEmpty(sysParam.getParamNm())) {
            queryWrapper.like("param_nm", sysParam.getParamNm());
        }
        if (!CommonUtil.isEmpty(sysParam.getParamTyp())) {
            queryWrapper.eq("param_typ", sysParam.getParamTyp());
        }
        try {
            iPage = this.baseMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return iPage;
    }
  
    @Override
    public boolean isExists(String paramId) throws Exception {
        try {
            SysParam sysParam = this.baseMapper.selectById(paramId);
            if (null != sysParam) {
                return true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return false;
    }
    @Override
    public Map<String,String> GetSysParamMap(String paramTyp){
        Map<String,String> map= new HashMap<String,String>();
        List<SysParam> list = this.baseMapper.listSysParamByTyp(paramTyp);
        for(SysParam s:list){
          map.put(s.getParamNm(),s.getParamValue());
        }
        return map;
    }

    @Override
    public DataGrid getSysParamByParamType(PageParameters pageParameters, String paramTyp) {
        QueryWrapper<SysParam> queryWrapper = new QueryWrapper<>();
        Page<SysParam> page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
        IPage<SysParam> iPage = null;
        try{
            queryWrapper.eq("PARAM_TYP",paramTyp);
            iPage = this.page(page,queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return new DataGrid(iPage.getTotal(),iPage.getRecords());
    }

}
