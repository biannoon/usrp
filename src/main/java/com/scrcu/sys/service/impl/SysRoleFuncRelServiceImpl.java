package com.scrcu.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.sys.entity.SysRoleFuncRel;
import com.scrcu.sys.mapper.SysRoleFuncRelMapper;
import com.scrcu.sys.service.SysRoleFuncRelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述： 角色信息接口实现层
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/1 21:58
 */
@Service("sysRoleFuncRelServiceImpl")
public class SysRoleFuncRelServiceImpl
        extends ServiceImpl<SysRoleFuncRelMapper, SysRoleFuncRel> implements SysRoleFuncRelService {

    @Override
    public String selectMaxId() {
        return this.baseMapper.selectMaxId();
    }

    @Override
    public void insertRoleFuncs(List<SysRoleFuncRel> list) {
        this.baseMapper.insertRoleFuncs(list);
    }



}
