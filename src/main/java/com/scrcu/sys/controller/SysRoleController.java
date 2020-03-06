package com.scrcu.sys.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.DataList;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.DateUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysRole;
import com.scrcu.sys.entity.SysSubsystem;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysFuncService;
import com.scrcu.sys.service.SysRoleService;
import com.scrcu.sys.service.SysSubsystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 角色信息控制器
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/10 16:27
 */
@Controller
@RequestMapping(value = "/sysRole")
public class SysRoleController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    public SysRoleService sysRoleService;
    @Autowired
    private SysFuncService sysFuncService;
    @Autowired
    private SysSubsystemService sysSubsystemService;

    @RequestMapping("/index")
    public String toIndex(HttpServletRequest request) throws Exception {
        return "sys/SysRole_list";
    }

    /**
     * 描述：打开功能窗口
     * @param request
     * @param type      类型：insert-新增    update-修改   detail-详情
     * @param id
     * @return
     * @author pengjuntao
     */
    @RequestMapping("/toDetailPage")
    public String toDetailPage(HttpServletRequest request, String type, String id) throws Exception {
        if (id != null && !"".equals(id)){
            request.setAttribute("sysRole",sysRoleService.getById(id));
        }
        request.setAttribute("type",type);
        return "sys/SysRole_detail";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public AjaxResult insert(SysRole sysRole, HttpServletRequest request) throws Exception {
        boolean bool = sysRoleService.isExists(sysRole.getRoleId());
        if (bool) {
            return new AjaxResult(false, "该角色已存在");
        } else {
            sysRole.setCreatr(this.getUser().getUserId());
            sysRole.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
            sysRole.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
            sysRole.setFinlModifr(this.getUser().getUserId());
            bool = sysRoleService.insert(sysRole);
            if (bool) {
                return new AjaxResult(true, "新增成功", sysRole.getRoleId(), "String");
            } else {
                return new AjaxResult(false, "新增失败");
            }
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String ids, HttpServletRequest request) throws Exception {
        String roleIdArry[] = ids.split(",");
        boolean bool = false;
        for (String roleId : roleIdArry) {
            bool = sysRoleService.delete(roleId);
        }

        if (bool) {
            return new AjaxResult(true, "删除成功");
        } else {
            return new AjaxResult(false, "删除失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(SysRole sysRole, HttpServletRequest request) throws Exception {
        sysRole.setFinlModifr(this.getUser().getUserId());
        sysRole.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        boolean bool = sysRoleService.update(sysRole);
        if (bool) {
            return new AjaxResult(true, "修改成功",sysRole.getRoleId(),"");
        } else {
            return new AjaxResult(false, "修改失败");
        }
    }

    /**
     * 新增任务时：检验新增任务ID的唯一性
     * @param request
     * @param response
     * @param roleId
     * @return
     */
    @RequestMapping("/checkRoleId")
    @ResponseBody
    public AjaxResult checkRoleId(HttpServletRequest request, HttpServletResponse response, String roleId) throws Exception {
        SysRole sysRole = sysRoleService.getById(roleId);
        if (sysRole != null) {
            return new AjaxResult(false, "角色编号已存在");
        }else{
            return new AjaxResult(true, "角色编号不存在");
        }
    }
    @RequestMapping("/getById")
    public ModelAndView getById(String roleId, String readonly, HttpServletRequest request) throws Exception {
        SysRole sysRole = sysRoleService.getById(roleId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysRoleDetail");
        mv.getModel().put("resultMap", sysRole);
        if ("ADMIN".equals(roleId)) {
            readonly = "true";
        }
        mv.getModel().put("readonly", readonly);
        mv.getModel().put("action", "update");
        return mv;
    }

    @RequestMapping("/getByPage")
    @ResponseBody
    public DataGrid getByPage(SysRole sysRole, HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage<SysRole> iPage = sysRoleService.getByPage(page, sysRole);
        List<SysRole> list = iPage.getRecords();
        for (SysRole role : list){
            role.setRoleHrchCd(EhcacheUtil.getSingleSysDictryCdByCache(role.getRoleHrchCd(),"SYS06").getDictryNm());
        }
        return new DataGrid(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * 初始化系统资源页面的功能菜单树
     * @param id
     * @param sysId
     * @param roleId
     * @return
     */
    @RequestMapping("/listAllSysRoleMenu")
    @ResponseBody
    public String listAllSysRoleMenu(String id, String sysId,String roleId) throws Exception {
        return sysFuncService.listAllSysMenuBySubsystemId(roleId,id,sysId);
    }


    @RequestMapping("/distributeFunc")
    @ResponseBody
    public AjaxResult distributeFunc(String roleId, String funcs) throws Exception {
        List<String> fundIds = new ArrayList<>();
        if (!CommonUtil.isEmpty(funcs)) {
            String[] funcArray = funcs.substring(0,funcs.lastIndexOf(",")).split(",");
            for (String str : funcArray) {
                fundIds.add(str);
            }
        }
        boolean bool = sysRoleService.saveRoleFuncRel(roleId, fundIds);
        if (bool) {
            return new AjaxResult(true, "角色分配资源成功");
        } else {
            return new AjaxResult(false, "角色分配资源失败");
        }
    }

    @RequestMapping("/exchangeSubsystemNm")
    @ResponseBody
    public AjaxResult exchangeSubsystemNm(String subsystemId) throws Exception {
        SysSubsystem system = sysSubsystemService.getById(subsystemId);
        if (system != null){
            return new AjaxResult(true,"",system,"");
        }else{
            return new AjaxResult(false,"","","");
        }
    }

    @RequestMapping("/getRoleListByUserId")
    @ResponseBody
    public String getRoleListByUserId(String userId, HttpServletRequest request) throws Exception {
        List<SysRole> roleList = sysRoleService.getRoleListByUserId(userId);
        List<DataList> dataListList = new ArrayList<>();
        for (SysRole sysRole : roleList) {
            DataList dataList = new DataList();
            dataList.setValue(sysRole.getRoleId());
            dataList.setText(sysRole.getRoleNm());
            dataListList.add(dataList);
        }
        return JSON.toJSONString(dataListList);
    }

    @RequestMapping("/getRoleListByLoginUser")
    @ResponseBody
    public String getRoleListByLoginUser(HttpServletRequest request,String userId) throws Exception {
        SysUser sysUser = this.getUser();
        List<SysRole> roleList = sysRoleService.getRoleListByUserId(sysUser.getUserId());
        List<SysRole> sysRoleList = sysRoleService.getRoleListByLoginUser(roleList);
        List<SysRole> idList = sysRoleService.getRoleListByUserId(userId);
        List<String> strList = new ArrayList<>();
        List<DataList> dataListList = new ArrayList<>();
        for (SysRole role : idList){
            strList.add(role.getRoleId());
        }
        for (SysRole role : sysRoleList){
            if (!strList.contains(role.getRoleId())){
                DataList dataList = new DataList();
                dataList.setValue(role.getRoleId());
                dataList.setText(role.getRoleNm());
                dataListList.add(dataList);
            }
        }
        return JSON.toJSONString(dataListList);
    }

    /**
     * <p>
     * 查询公告发布发布的角色对象信息
     * </p>
     * @param pubNoticeId 公告id
     * @return java.util.List<com.scrcu.common.base.DataList>
     * @author wuyu
     * @date 2019/11/1 15:35
     **/
    @PostMapping("/getRoleListWithPubNotice")
    public @ResponseBody List<DataList> getRoleListWithPubNotice(String pubNoticeId) {
        return sysRoleService.getRoleListWithPubNotice(pubNoticeId);
    }

    /**
     * <p>
     * 获取不在公告发布对象中的角色信息
     * </p>
     * @param pubNoticeId 公告id
     * @return java.util.List<com.scrcu.common.base.DataList>
     * @author wuyu
     * @date 2019/11/1 16:20
     **/
    @PostMapping("/getRoleListNotInPubNotice")
    public @ResponseBody List<DataList> getRoleListNotInPubNotice(String pubNoticeId) {
        return sysRoleService.getRoleListNotInPubNotice(pubNoticeId);
    }
    /**
     * 跳转角色菜单配置页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/toSysRoleFuncConf")
    public String toInsert(HttpServletRequest request, HttpServletResponse response) {
        String roleId = request.getParameter("roleId");
        request.setAttribute("roleId",roleId);
        return "sys/SysRoleFuncConf";
    }

    /**
     * 描述：获取已分配角色的用户
     * @param sysrole
     * @param sysUser
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getShowRoleUser")
    @ResponseBody
    public DataGrid getShowRoleUser(SysRole sysrole, SysUser sysUser, HttpServletRequest request){
        PageParameters page = this.getPage(request);
        return sysRoleService.getShowRoleUser(page,sysUser,sysrole);
    }

    /**
     * 描述：判断该角色是否已分配给用户
     * @param roleId
     * @return
     */
    @RequestMapping("/isExistUserUnderRole")
    @ResponseBody
    public AjaxResult isExistUserUnderRole(String roleId){
        return sysRoleService.isExistUserUnderRole(roleId);
    }

}
