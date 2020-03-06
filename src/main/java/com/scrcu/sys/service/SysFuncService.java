package com.scrcu.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.sys.entity.SysFunc;

import java.util.List;
import java.util.Map;

/**
 * 描述：系统菜单功能
 * @Author pengjuntao
 * @Date 2019/09/09 16:35
 */
public interface SysFuncService extends IService<SysFunc> {

    /**
     * 描述： 功能资源信息新增
     * @param sysFunc
     * @return AjaxResult
     * @Author pengjuntao
     * @Date 2019/09/09 16:35
     */
    AjaxResult insert(SysFunc sysFunc);

    /**
     * 描述： 功能资源信息删除
     * @param funcId
     * @return AjaxResult
     * @Author pengjuntao
     * @Date 2019/09/09 16:35
     */
    AjaxResult delete(String funcId);

    /**
     * 描述： 功能资源信息修改
     * @param sysFunc
     * @return AjaxResult
     * @Author pengjuntao
     * @Date 2019/09/09 16:35
     */
    AjaxResult update(SysFunc sysFunc);

    /**
     * 描述： 功能资源信息查询
     * @param funcId
     * @return SysFunc
     * @Author pengjuntao
     * @Date 2019/09/09 16:35
     */
    SysFunc getSysMenuByNo(String funcId);

    /**
     * 描述： 根据主键判断当前菜单是否有下一级
     * @param funcId
     * @return AjaxResult
     * @Author pengjuntao
     * @Date 2019/09/09 16:35
     */
    AjaxResult hasNextLvl(String funcId, String flag);

    /**
     * 描述： 根据主键查询出页面集合
     * @param id
     * @return String
     * @Author pengjuntao
     * @Date 2019/09/09 16:35
     */
    String listAllSysMenu(String id, boolean isPareNodeflag);
    /**
     * 描述： 根据主键以及分页参数查询功能资源集合
     * @param pageParameters
     * @param id
     * @return DataGrid
     * @Author pengjuntao
     * @Date 2019/09/09 16:35
     */
    DataGrid listSysFuncByDataGrid(PageParameters pageParameters, String id);

    /**
     * 描述： 查询全部资源功能菜单返回map集合
     * @return Map<String, List<SysFunc>>
     * @Author jiyuanbo
     * @Date 2019/10/21 16:35
     */
    Map<String, List<SysFunc>> getAllSysFunc() throws Exception;

    /**
     * 描述： 根据主键以及分页参数查询功能资源集合
     * @param pareFuncId 上级功能ID
     * @return List
     * @Author pengjuntao
     * @Date 2019/09/18 16:35
     */
    List<SysFunc> getListByParent(String pareFuncId) throws Exception;

    /**
     * 描述：通过菜单编号，获取菜单下的功能按钮
     * @param funcId 菜单ID
     * @param user
     * @return
     */
    //List<SysFunc> getButtonsFromMenu(String funcId, SysUser user);

    /**
     * 描述：保存勾选的角色菜单
     * @param str 菜单角色集合
     * @return
     */
    //AjaxResult saveAllRoleSysFunc(String str);

    /**
     * 描述：查询指定子系统下的功能菜单（角色管理中，角色分配功能菜单时使用）
     * @param roleId    角色ID
     * @param id        节点ID
     * @param sysId     子系统ID
     * @return
     */
    String listAllSysMenuBySubsystemId(String roleId, String id, String sysId) throws Exception;

    /**
     * 描述：通过用户ID查询用户角色所能访问的菜单
     * @param userId
     * @return 功能菜单的集合
     * @author pengjuntao
     */
    List<String> getSysFuncByUserId(String userId);


    /**
     * 描述：获取所有的角色资源
     * @return
     */
    Map<String,List<String>> getAllSysRoleFuncRel();

    /**
     * 描述：获取指定子系统下的功能菜单
     * @param subsystemId
     * @return
     */
    List<SysFunc> getFuncBySubsystemId(String subsystemId);

    //List<SysFunc> getAllFunc();

    SysFunc getSysFuncByFuncId(String funcId);

}
