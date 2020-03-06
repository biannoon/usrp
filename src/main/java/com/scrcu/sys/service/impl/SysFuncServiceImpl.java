package com.scrcu.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.TreeNodeUtil;
import com.scrcu.sys.entity.SysFunc;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysRoleFuncRel;
import com.scrcu.sys.entity.SysSubsystem;
import com.scrcu.sys.mapper.SysFuncMapper;
import com.scrcu.sys.mapper.SysRoleFuncRelMapper;
import com.scrcu.sys.service.SysFuncService;
import com.scrcu.sys.service.SysRoleService;
import com.scrcu.sys.service.SysSubsystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("sysFuncServiceImpl")
@Transactional
public class SysFuncServiceImpl
        extends ServiceImpl<SysFuncMapper,SysFunc> implements SysFuncService {

    @Autowired(required = false)
    private SysRoleFuncRelMapper relMapper;
    @Autowired
    private SysSubsystemService sysSubsystemService;
    @Autowired
    private SysRoleService sysRoleService;
    /**
     * @param id
     * @param isPareNodeflag 判断是否只获取非叶子节点
     * @return
     */
    @Override
    public String listAllSysMenu(String id,boolean isPareNodeflag) {
        QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
        try{
            if (id != null && !"".equals(id)){
                if (id.equals("root")){
                    List<SysSubsystem> list = sysSubsystemService.getAllSubSystemWithoutPage();
                    return JSON.toJSONString(TreeNodeUtil.fillFuncTreeBySubSystem(list));
                }else{
                    queryWrapper.eq("pare_func_id",id).orderByAsc("SEQ_NO");
                    List<SysFunc> list = this.baseMapper.selectList(queryWrapper);
                    return JSON.toJSONString(TreeNodeUtil.fillFuncTree(list));
                }
            }else{
                List<SysFunc> list = new ArrayList<>();
                SysFunc func = new SysFunc();//虚拟节点
                func.setFuncId("root");
                func.setFuncNm("功能资源");
                func.setIsLeaf("SYS0202");
                func.setFuncComnt("功能资源");
                list.add(func);
                return JSON.toJSONString(TreeNodeUtil.fillFuncTree(list));
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("系统功能菜单资源管理：查询失败");
        }
    }

    @Override
    public String listAllSysMenuBySubsystemId(String roleId,String id,String sysId){
        List<SysFunc> returnList = null;
        try{
            if (roleId != null && !"".equals(roleId)){
                SysRole role = sysRoleService.getById(roleId);
                sysId = role.getSys();
            }
            //-获取指定子系统下的所有功能菜单
            QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("subsystem_id",sysId).orderByAsc("SEQ_NO");
            returnList = this.baseMapper.selectList(queryWrapper);
            //-添加一个虚拟的子系统节点
            SysSubsystem system = sysSubsystemService.getById(sysId);
            SysFunc func = new SysFunc();
            func.setFuncId(system.getSubsystemId());
            func.setFuncNm(system.getSubsystemNm());
            func.setIsLeaf("SYS0202");
            func.setFuncComnt(system.getSubsystemComnt());
            returnList.add(func);

            if (roleId != null && !"".equals(roleId)){
                List<SysFunc> existList = this.baseMapper.getSysFuncByRoleId(roleId);
                List<String> tempList = new ArrayList<>();
                for (SysFunc sysfunc : existList){
                    if (sysfunc != null){
                        tempList.add(sysfunc.getFuncId());
                    }
                }
                for (SysFunc sysfunc : returnList){
                    if (tempList.contains(sysfunc.getFuncId())){
                        sysfunc.setIconUrl("checked");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return JSON.toJSONString(TreeNodeUtil.fillFuncTreeByRole(returnList));
    }

    @Override
    public DataGrid listSysFuncByDataGrid(PageParameters pageParameters,String id) {
        QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
        Page<SysFunc> page = null;
        IPage<SysFunc> ipage = null;
        try{
            queryWrapper.eq("pare_func_id",id).orderByAsc("SEQ_NO");
            page = new Page<>(pageParameters.getPage(),pageParameters.getRows());
            ipage = this.page(page,queryWrapper);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("系统菜单资源管理：查询系统资源失败");
        }
        return new DataGrid(ipage.getTotal(),ipage.getRecords());
    }

    @Override
    public AjaxResult insert(SysFunc sysFunc) {
        AjaxResult ajaxResult = null;
        try{
            sysFunc.setCrtDt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            sysFunc.setFinlModfyDt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            int affectRow = this.baseMapper.insert(sysFunc);
            if (affectRow > 0){
                sysFunc = getSysMenuByNo(sysFunc.getFuncId());
                ajaxResult = new AjaxResult(true,"新增成功",sysFunc,"");
            }else{
                ajaxResult = new AjaxResult(false,"新增失败","","");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("系统功能菜单资源管理：新增资源失败");
        }
        return ajaxResult;
    }

    @Override
    public AjaxResult update(SysFunc sysFunc) {
        AjaxResult ajaxResult = null;
        try{
            int affectRow = this.baseMapper.updateById(sysFunc);
            if (affectRow > 0){
                sysFunc = getSysMenuByNo(sysFunc.getFuncId());
                ajaxResult = new AjaxResult(true,"修改成功",sysFunc,"");
            }else{
                ajaxResult = new AjaxResult(false,"修改失败","","");
            }
        }catch (Exception e){
            throw new RuntimeException("系统功能菜单资源管理：修改资源失败");
        }
        return ajaxResult;
    }

    @Override
    public AjaxResult delete(String funcId) {
        AjaxResult result = null;
        try{
            int affectRow = this.baseMapper.deleteById(funcId);
            if (affectRow > 0){
                result = new AjaxResult(true,"删除成功","","");
            }else{
                result = new AjaxResult(false,"删除失败","","");
            }
        }catch (Exception e){
            throw new RuntimeException("系统功能菜单资源管理：删除资源失败");
        }
        return result;
    }

    @Override
    public SysFunc getSysMenuByNo(String funcId) {
        SysFunc func = null;
        try{
            func = (SysFunc) this.baseMapper.selectById(funcId);
        }catch (Exception e){
            throw new RuntimeException("系统功能菜单资源管理：查询菜单资源详情失败");
        }
        return func;
    }

    @Override
    public List<SysFunc> getListByParent(String pareFuncId) {
        QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pare_func_id", pareFuncId);
        List<SysFunc> sysFuncList = this.baseMapper.selectList(queryWrapper);
        return sysFuncList;
    }

    @Override
    public AjaxResult hasNextLvl(String funcId,String flag) {
        List<SysFunc> list = null;
        AjaxResult ajaxResult = null;
        QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
        try{
            queryWrapper.eq("pare_func_id",funcId);
            list = this.baseMapper.selectList(queryWrapper);
            if ("1".equals(flag)){
                if (list.size() > 0 && "SYS0702".equals(list.get(0).getFuncType())){
                    ajaxResult = new AjaxResult(true,"所选菜单资源存在下一级菜单","","");
                }else{
                    ajaxResult = new AjaxResult(false,"所选菜单资源不存在下一级菜单","","");
                }
            }else {
                if (list.size() > 0){
                    ajaxResult = new AjaxResult(true,"所选菜单资源存在下一级资源","","");
                }else{
                    ajaxResult = new AjaxResult(false,"所选菜单资源不存在下一级资源","","");
                }
            }
        }catch (Exception e){
            throw new RuntimeException("系统功能菜单资源管理：查询是否存在下一级资源失败");
        }
        return ajaxResult;
    }

    /**
     * 描述： 功能资源表根据父节点进行分组封装进map集合
     * @param sysFuncList
     * @return map<String, List<SysFunc>>
     * @创建人： jiyuanbo
     * @创建时间： 2019/10/21 21:39
     */
    private static Map<String, List<SysFunc>> groupByObjectKey(List<SysFunc> sysFuncList) {
        Map<String, List<SysFunc>> map = new HashMap<>();
        for (SysFunc sysFunc : sysFuncList) {
            if(!map.containsKey(sysFunc.getPareFuncId())) {
                map.put(sysFunc.getPareFuncId(), new ArrayList<>());
            }
            List<SysFunc> tempList = map.get(sysFunc.getPareFuncId());
            tempList.add(sysFunc);
        }
        return map;
    }

    @Override
    public Map<String, List<SysFunc>> getAllSysFunc() throws Exception {
        Map<String, List<SysFunc>> map;
        QueryWrapper<SysFunc> queryWrapper = null;
        try {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("SEQ_NO");
            //queryWrapper.eq("FUNC_TYP","SYS0702");
            List<SysFunc> sysFuncList = this.baseMapper.selectList(queryWrapper);
            map = groupByObjectKey(sysFuncList);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return map;

    }

   /* @Override
    public List<SysFunc> getButtonsFromMenu(String menuno) {
        List<SysFunc> buttonList = new ArrayList<>();
        QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
        try{
            queryWrapper.eq("pare_func_id",menuno);
            queryWrapper.eq("func_typ","SYS0703");
            buttonList = this.baseMapper.selectList(queryWrapper);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return buttonList;
    }*/

    /*@Override
    public AjaxResult saveAllRoleSysFunc(String str) {
        AjaxResult ajaxResult = null;
        try{
            if(str.contains(";")){
                String[] sysFuncs = str.split(";");
                List<SysRoleFuncRel> list = new ArrayList<SysRoleFuncRel>();
                SysRoleFuncRel sysRoleFuncRel = null;
                String[] roleIds = sysFuncs[0].split(",");
                String roleId = roleIds[1];
                //先删除角色原有的系统菜单
                relMapper.delRoleFuncsByRoleId(roleId);
                int id = Integer.valueOf(relMapper.selectMaxId());
                QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
                List<SysFunc> menuList = null;
                if(str.contains("root")){
                    menuList = this.baseMapper.selectList(queryWrapper);
                    for(SysFunc sysFunc:menuList){
                        sysRoleFuncRel = new SysRoleFuncRel();
                        id+=1;
                        sysRoleFuncRel.setFuncId(sysFunc.getFuncId());
                        sysRoleFuncRel.setRoleId(roleId);
                        sysRoleFuncRel.setId(id);
                        list.add(sysRoleFuncRel);
                    }
                    relMapper.insertRoleFuncs(list);
                    return  new AjaxResult(true,"保存成功","","");
                }else if(str.contains("A01,")){

                }
                for(String s:sysFuncs){
                    sysRoleFuncRel = new SysRoleFuncRel();
                    id+=1;
                    String[] funcs = s.split(",");
                    String funcId = funcs[0];
                    sysRoleFuncRel.setFuncId(funcId);
                    sysRoleFuncRel.setRoleId(funcs[1]);
                    sysRoleFuncRel.setId(id);
                    list.add(sysRoleFuncRel);
                }
                relMapper.insertRoleFuncs(list);
            }else{
                relMapper.delRoleFuncsByRoleId(str);
            }
            ajaxResult = new AjaxResult(true,"保存成功","","");
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult = new AjaxResult(false,"保存失败","","");
            throw new RuntimeException("系统功能菜单资源管理：新增配置菜单失败");
        }
        return ajaxResult;
    }*/

    @Override
    public Map<String,List<String>> getAllSysRoleFuncRel() {
        Map<String, List<String>> map = null;
        List<SysRoleFuncRel> sysRoleFuncRelList = null;
        try{
            sysRoleFuncRelList = this.baseMapper.getAllSysRoleFuncRel();
            map = groupAuthoritoryByRole(sysRoleFuncRelList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return  map;
    }

    @Override
    public List<SysFunc> getFuncBySubsystemId(String subsystemId) {
        QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pare_func_id",subsystemId);
        List<SysFunc> list = this.baseMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public SysFunc getSysFuncByFuncId(String funcId) {
        SysFunc func = null;
        try{
            func = this.baseMapper.selectById(funcId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return func;
    }

    /*@Override
    public List<SysFunc> getAllFunc() {
        QueryWrapper<SysFunc> queryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectList(queryWrapper);
    }*/

    /**
     * 描述：按角色分组存储权限资源
     * @param list
     * @return
     */
    private static Map<String,List<String>> groupAuthoritoryByRole(List<SysRoleFuncRel> list){
        Map<String,List<String>> map = new HashMap<>();
        for (SysRoleFuncRel rel : list){
            if (!map.containsKey(rel.getRoleId())){
                map.put(rel.getRoleId(),new ArrayList<>());
            }
            map.get(rel.getRoleId()).add(rel.getFuncId());
        }
        return map;
    }

    @Override
    public List<String> getSysFuncByUserId(String userId) {
        List<String> list = null;
        try{
            list = this.baseMapper.getSysFuncByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

}
