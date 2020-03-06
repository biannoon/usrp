package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.sys.entity.SysRoleFuncRel;

import java.util.List;

/**
 * 描述： 角色信息接口层
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/28 19:50
 */
public interface SysRoleFuncRelService extends IService<SysRoleFuncRel> {

    /**
     * 描述：获取当前表中的ID最大值（用于生成主键ID）
     * @return
     */
    String selectMaxId();

    /**
     * 描述：批量新增角色对应功能菜单记录
     * @param list
     */
    void insertRoleFuncs(List<SysRoleFuncRel> list);


}
