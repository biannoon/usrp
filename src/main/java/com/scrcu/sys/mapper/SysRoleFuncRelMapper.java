package com.scrcu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scrcu.sys.entity.SysRoleFuncRel;

import java.util.List;

/**
 * 描述： 角色信息mapper接口
 * @创建人： hepengfei
 * @创建时间： 2019/11/9 21:59
 */
public interface SysRoleFuncRelMapper extends BaseMapper<SysRoleFuncRel> {

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


    void delRoleFuncsByRoleId(String roleId);
}
