package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.sys.entity.SysPermission;
import com.scrcu.sys.mapper.SysPermissionMapper;
import com.scrcu.sys.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 描述： 权限信息接口实现层
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/1 21:58
 */
@Service("sysPermissionServiceImpl")
public class SysPermissionServiceImpl
        extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public int insert(SysPermission sysPermission) {
        return 0;
    }

    @Override
    public int delete(SysPermission sysPermission) {
        return 0;
    }

    @Override
    public int update(SysPermission sysPermission) {
        return 0;
    }

    @Override
    public SysPermission findById(String id) {
        return null;
    }

    @Override
    public Object findByPage(Page page, SysPermission sysPermission) {
        return null;
    }
}
