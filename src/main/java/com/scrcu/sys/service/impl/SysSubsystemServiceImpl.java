package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.exception.BaseException;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.sys.entity.SysSubsystem;
import com.scrcu.sys.mapper.SysSubsystemMapper;
import com.scrcu.sys.service.SysSubsystemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述： 子系统信息service接口实现类
 * @创建人： jiyuanbo
 * @创建时间： 2019/10/24 14:49
 */
@Service("sysSubsystemServiceImpl")
public class SysSubsystemServiceImpl
        extends ServiceImpl<SysSubsystemMapper, SysSubsystem> implements SysSubsystemService {

    @Override
    public boolean insert(SysSubsystem sysSubsystem) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.insert(sysSubsystem);
            if(count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean delete(String subsystemId) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.deleteById(subsystemId);
            if(count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean update(SysSubsystem sysSubsystem) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.updateById(sysSubsystem);
            if(count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
        return bool;
    }

    @Override
    public SysSubsystem getById(String subsystemId) throws Exception {
        SysSubsystem sysSubsystem;
        try {
            sysSubsystem = this.baseMapper.selectById(subsystemId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sysSubsystem;
    }

    @Override
    public IPage getByPage(Page<SysSubsystem> page, SysSubsystem sysSubsystem) throws Exception {
        IPage iPage;
        try {
            QueryWrapper<SysSubsystem> queryWrapper = new QueryWrapper<>();
            if (!CommonUtil.isEmpty(sysSubsystem.getSubsystemNm())) {
                queryWrapper.like("subsystem_nm", sysSubsystem.getSubsystemNm());
            }
            iPage = this.baseMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return iPage;
    }

    @Override
    public boolean isExists(String subsystemId) throws Exception {
        try {
            SysSubsystem sysSubsystem = this.baseMapper.selectById(subsystemId);
            if (null != sysSubsystem) {
                return true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return false;
    }

    public List<SysSubsystem> getSysSubsystemList(String groupId) throws Exception {
        List<SysSubsystem> sysSubsystemList;
        try {
            List<SysSubsystem> checkList = this.baseMapper.getCheckedSubSysByGroupId(groupId);
            sysSubsystemList = this.baseMapper.selectList(new QueryWrapper<>());
            for (SysSubsystem sysSubsystem : sysSubsystemList) {
                for (SysSubsystem ckd : checkList) {
                    if (sysSubsystem.getSubsystemId().equals(ckd.getSubsystemId())) {
                        sysSubsystem.setCheck(true);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sysSubsystemList;
    }

    @Override
    public DataGrid getAllSubSystemFromFileManage(PageParameters pageParameters , SysSubsystem sysSubsystem) {
        QueryWrapper<SysSubsystem> queryWrapper = new QueryWrapper<>();
        Page<SysSubsystem> page = null;
        IPage<SysSubsystem> ipage = null;
        try{
            if (sysSubsystem.getSubsystemId() != null && !"".equals(sysSubsystem.getSubsystemId())){
                queryWrapper.eq("SUBSYSTEM_ID",sysSubsystem.getSubsystemId());
            }else if (sysSubsystem.getSubsystemNm() != null && !"".equals(sysSubsystem.getSubsystemNm())){
                queryWrapper.like("SUBSYSTEM_NM",sysSubsystem.getSubsystemNm());

            }
            queryWrapper.orderByAsc("SUBSYSTEM_ID");
            page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
            ipage = this.page(page,queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return new DataGrid(ipage.getTotal(),ipage.getRecords());
    }

    @Override
    public List<SysSubsystem> getAllSubSystemWithoutPage() {
        List<SysSubsystem> list = null;
        QueryWrapper<SysSubsystem> queryWrapper = new QueryWrapper<>();
        try{
            list = this.baseMapper.selectList(queryWrapper);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }
}
