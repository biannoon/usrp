package com.scrcu.sys.controller;

import com.alibaba.fastjson.JSON;
import com.scrcu.common.annotation.LogAnnotation;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.TreeNode;
import com.scrcu.common.base.TreeNodeAttribute;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.AuthorityManageUtil;
import com.scrcu.sys.entity.SysFunc;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysDictryCdService;
import com.scrcu.sys.service.SysFuncService;
import com.scrcu.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：系统菜单功能
 * @Author pengjuntao
 * @Date 2019/09/09 16:35
 */
@Controller
public class SysFuncController extends BaseController{

    @Autowired
    private SysFuncService sysFuncService;
    @Autowired
    private SysDictryCdService sysDictryCdService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询菜单列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("listSysFunc")
    public String toSysFuncList(HttpServletRequest request, HttpServletResponse response){
        return "sys/SysFunc_list";
    }

    /**
     * 初始化系统资源页面的功能菜单树
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping("sysFunc/listAllSysFunc")
    @ResponseBody
    //@LogAnnotation(id={"funcId"},operType = "SYS09FUNC01",operation = "系统菜单资源：查询系统菜单资源数据集合")
    public String listAllSysFunc(HttpServletRequest request, HttpServletResponse response, String id){
        return sysFuncService.listAllSysMenu(id,false);

       /* //-返回ajax请求第二种方式：
        String result = sysFuncService.listAllSysMenu();
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    /**
     * 初始化系统资源页面的功能菜单树
     * @param request
     * @param response
     * @param id
     * @return
     */
   /* @RequestMapping("sysFunc/listAllSysRoleFunc")
    @ResponseBody
    //@LogAnnotation(id={"funcId"},operType = "SYS09FUNC01",operation = "系统菜单资源：查询系统菜单资源数据集合")
    public String listAllSysRoleFunc(HttpServletRequest request, HttpServletResponse response,String roleId,String id){
        String result =  sysFuncService.listAllSysRoleMenu(roleId,id,false);
        return result;
    }*/
    /**
     *初始化系统资源页面的数据网格
     * @param request
     * @param response
     * @param id 树节点Id
     * @return
     */
    @RequestMapping("sysFunc/listSysFuncByDataGrid")
    @ResponseBody
    //@LogAnnotation(id={"funcId"},operType = "SYS09FUNC02",operation = "系统菜单资源：查询对应节点下一级系统菜单资源集合")
    public DataGrid listSysFuncByDataGrid(HttpServletRequest request,HttpServletResponse response,String id){
        PageParameters pageParameters = this.getPage(request);
        return sysFuncService.listSysFuncByDataGrid(pageParameters,id);
    }

    /**
     * 新增菜单--跳转到新增页面
     * @param request
     * @return
     */
    @RequestMapping("sysFunc/toInsert")
    public String toInsert(){
        return "sys/SysFunc_insert";
    }

    /**
     * 新增菜单--新增
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("sysFunc/insert")
    @ResponseBody
    @LogAnnotation(id={"funcId"},operType = "SYS09FUNC03",operation = "系统菜单资源：新增系统功能菜单资源")
    public AjaxResult insert(HttpServletRequest request, HttpServletResponse response, SysFunc sysFunc){
        sysFunc.setCreatr(this.getUser().getUserId());
        sysFunc.setFinlModifr(this.getUser().getUserId());
        //返回结果对象
        return sysFuncService.insert(sysFunc);

    }

    /**
     * 修改菜单--跳转修改页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("sysFunc/toUpdate")
    public String toUpdate(HttpServletRequest request, HttpServletResponse response,String funcId){
        SysFunc sysFunc = sysFuncService.getSysMenuByNo(funcId);
        request.setAttribute("SysFunc",sysFunc);
        return "sys/SysFunc_update";
    }

    /**
     * 修改菜单--修改
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("sysFunc/update")
    @ResponseBody
    @LogAnnotation(id={"funcId"},operType = "SYS09FUNC04",operation = "系统菜单资源：更新系统功能菜单资源")
    public AjaxResult update(HttpServletRequest request, HttpServletResponse response, SysFunc sysFunc){
        sysFunc.setFinlModifr(this.getUser().getUserId());//待定
        sysFunc.setFinlModfyDt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return sysFuncService.update(sysFunc);
    }

    /**
     * 删除菜单
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("sysFunc/delete")
    @ResponseBody
    @LogAnnotation(id={"funcId"},operType = "SYS09FUNC05",operation = "系统菜单资源：删除系统功能菜单资源")
    public AjaxResult delete(HttpServletRequest request, HttpServletResponse response, String FUNC_ID){
        return sysFuncService.delete(FUNC_ID);
    }

    /**
     * ajax请求：
     * 判断新增的menuno是否已经存在
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("sysFunc/CheckSysFuncId")
    @ResponseBody
    @LogAnnotation(id={"funcId"},operType = "SYS09FUNC06",operation = "系统菜单资源：验证系统功能菜单资源ID的唯一性")
    public String checkSysFuncId(HttpServletRequest request, HttpServletResponse response, String FUNC_ID){
        if (sysFuncService.getSysMenuByNo(FUNC_ID) != null){
            return "true";
        }
        return "false";
    }

    /**
     * 查询单个菜单详情
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("sysFunc/detail")
    @LogAnnotation(id={"funcId"},operType = "SYS09FUNC07",operation = "系统菜单资源：查询指定系统菜单资源详情")
    public String getSysMenuByNo(HttpServletRequest request, HttpServletResponse response, String FUNC_ID){
        SysFunc sysFunc = sysFuncService.getSysMenuByNo(FUNC_ID);
        request.setAttribute("SysFunc",sysFunc);
        return "sys/SysFunc_detail";
    }

    /**
     * 检查是否存在下一级资源
     * @param id 功能资源Id
     *@param flag 查询是否存在下一级的条件
     * @return
     */
    @RequestMapping("sysFunc/hasNextLvl")
    @ResponseBody
    @LogAnnotation(id={"funcId"},operType = "SYS09FUNC08",operation = "系统菜单资源：检查指定功能菜单资源是否存在下一级资源")
    public AjaxResult hasNextLvl(String id,String flag) {
        return sysFuncService.hasNextLvl(id,flag);
    }


    /**
     * 描述：获取系统主页面导航菜单（+角色权限控制）
     * @param treeId 菜单id
     * @return
     */
    @RequestMapping("SysFunc/getTreeMenu")
    @ResponseBody
    public String getTreeMenu(String treeId){
        String jsonString = null;
        //-获取用户角色
        SysUser user = getUser();
        try {
            //-通过权限控制工具类获取返回的功能菜单
            List<SysFunc> returnList = AuthorityManageUtil.getAuthorityManageSysFuncByFuncId(user,treeId);
            //-转化为node节点
            List<TreeNode> treeNodeList = new ArrayList<>();
            for (SysFunc sysFunc : returnList) {
                TreeNode t = new TreeNode();
                TreeNodeAttribute treeNodeAttribute = null;
                if(null != sysFunc.getUrl() && !"".equals(sysFunc.getUrl())) {
                    treeNodeAttribute = new TreeNodeAttribute();
                    treeNodeAttribute.setUrl(sysFunc.getUrl());
                }
                t.setId(sysFunc.getFuncId());
                t.setText(sysFunc.getFuncNm());
                t.setIconCls(sysFunc.getIconUrl());
                t.setAttributes(treeNodeAttribute);
                t.setState(sysFunc.getIsLeaf().equals("SYS0201") ? "open" : "closed");
                treeNodeList.add(t);
            }
            jsonString = JSON.toJSONString(treeNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }


    /**
     * 获取列表菜单（主页菜单使用）
     * @param treeId 功能资源Id
     * @author jiyuanbo
     * @return
     */
    /*@RequestMapping("SysFunc/getTreeMenu")
    public @ResponseBody String getTreeMenu(String treeId) {
        String jsonString = null;
        try {
            List<SysFunc> sysFuncList = EhcacheUtil.getSysFuncByCache(treeId);
//                    sysFuncService.getListByParent(treeId);
            List<TreeNode> treeNodeList = new ArrayList<>();
            for (SysFunc sysFunc : sysFuncList) {
                TreeNode t = new TreeNode();
                TreeNodeAttribute treeNodeAttribute = null;
                if(null != sysFunc.getUrl() && !"".equals(sysFunc.getUrl())) {
                    treeNodeAttribute = new TreeNodeAttribute();
                    treeNodeAttribute.setUrl(sysFunc.getUrl());
                }
                t.setId(sysFunc.getFuncId());
                t.setText(sysFunc.getFuncNm());
                t.setIconCls(sysFunc.getIconUrl());
                t.setAttributes(treeNodeAttribute);
                t.setState(sysFunc.getIsLeaf().equals("SYS0201") ? "open" : "closed");
                treeNodeList.add(t);
            }
            jsonString = JSON.toJSONString(treeNodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }*/


    /**
     * 初始化系统资源页面的功能菜单树
     * @param request
     * @param response
     * @param str
     * @return
     */
    /*@RequestMapping("sysFunc/saveAllRoleSysFunc")
    @ResponseBody
    @LogAnnotation(id={"funcId"},operType = "SYS09FUNC09",operation = "系统菜单资源：保存系统菜单资源数据集合")
    public AjaxResult saveAllRoleSysFunc(HttpServletRequest request, HttpServletResponse response,String str){
        return sysFuncService.saveAllRoleSysFunc(str);

    }*/
}
