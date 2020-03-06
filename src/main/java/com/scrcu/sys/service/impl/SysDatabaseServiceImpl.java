package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.sys.entity.SysDatabase;
import com.scrcu.sys.mapper.SysDatabaseMapper;
import com.scrcu.sys.service.SysDatabaseService;
import com.scrcu.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述： 数据源信息接口实现类
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/18 17:22
 */
@Service("sysDatabaseServiceImpl")
public class SysDatabaseServiceImpl
        extends ServiceImpl<SysDatabaseMapper, SysDatabase> implements SysDatabaseService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean insert(SysDatabase sysDatabase) throws Exception {
        boolean bool = false;
        try {
            String id = sysUserService.selectMaxId("sys_database");
            if (id == null || "".equals(id)){
                id = "1000001";
            }
            sysDatabase.setId(String.valueOf(id)+1);
            int count = this.baseMapper.insert(sysDatabase);
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean delete(String id) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.deleteById(id);
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public boolean update(SysDatabase sysDatabase) throws Exception {
        boolean bool = false;
        try {
            int count = this.baseMapper.updateById(sysDatabase);
            if (count > 0) {
                bool = true;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return bool;
    }

    @Override
    public SysDatabase getById(String id) throws Exception {
        SysDatabase sysDatabase;
        try {
            sysDatabase = this.baseMapper.selectById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return sysDatabase;
    }

    @Override
    public IPage<SysDatabase> getByPage(Page page, SysDatabase sysDatabase) throws Exception {
        QueryWrapper<SysDatabase> queryWrapper = new QueryWrapper<>();
        IPage<SysDatabase> iPage;
        if (!CommonUtil.isEmpty(sysDatabase.getDsNm())) {
            queryWrapper.like("ds_nm", sysDatabase.getDsNm());
        }
        if (!CommonUtil.isEmpty(sysDatabase.getDbTyp())) {
            queryWrapper.eq("db_typ", sysDatabase.getDbTyp());
        }
        try {
            iPage = this.baseMapper.selectPage(page, queryWrapper);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return iPage;
    }

    @Override
    public List<SysDatabase> findList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        return this.baseMapper.selectList(queryWrapper);
    }

    /**
     * <p>
     * 前端选择数据源信息时查询数据源信息
     * </p>
     * @return java.util.List<com.scrcu.sys.entity.SysDatabase>
     * @author wuyu
     * @date 2019/9/27 14:24
     **/
    public List<SysDatabase> listDataBase() {
        QueryWrapper<SysDatabase> queryWrapper = new QueryWrapper<>();
        // 只查询id和name属性
        queryWrapper.select("id", "ds_nm");
        return this.list(queryWrapper);
    }
}
