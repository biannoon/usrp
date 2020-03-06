package com.scrcu.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.CommonUtil;
import com.scrcu.common.utils.DateUtil;
import com.scrcu.common.utils.EhcacheUtil;
import com.scrcu.sys.entity.SysOrganization;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysGroupService;
import com.scrcu.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 用户信息控制器
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/2 16:35
 */
@Controller
@RequestMapping(value = "/sysUser")
public class SysUserController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysGroupService sysGroupService;

    @RequestMapping("/input")
    public ModelAndView input(SysUser sysUser, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysUserInsert");
        mv.getModel().put("resultMap", sysUser);
        return mv;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public AjaxResult insert(SysUser sysUser, HttpServletRequest request) throws Exception {
        boolean bool = sysUserService.isExists(sysUser.getUserId());
        if (bool) {
            return new AjaxResult(false, "该用户已存在");
        } else {
            sysUser.setCreatr(this.getUser().getUserId());
            sysUser.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
            bool = sysUserService.insert(sysUser);
            if (bool) {
                return new AjaxResult(true, "新增成功");
            } else {
                return new AjaxResult(false, "新增失败");
            }
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String userId, HttpServletRequest request) throws Exception {
        boolean bool = sysUserService.delete(userId);
        if (bool) {
            return new AjaxResult(true, "删除成功");
        } else {
            return new AjaxResult(false, "删除失败");
        }
    }
    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(SysUser sysUser, HttpServletRequest request) throws Exception {
        sysUser.setFinlModifr(this.getUser().getUserId());
        sysUser.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        boolean bool = sysUserService.update(sysUser);
        if (bool) {
            return new AjaxResult(true, "修改成功");
        } else {
            return new AjaxResult(false, "修改失败");
        }
    }
    @RequestMapping("/toUpdate")
    @ResponseBody
    public ModelAndView toUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysUserUpdate");
        SysUser sysUser = sysUserService.getById(userId);
        request.setAttribute("sysUser",sysUser);
        return mv;

    }

    @RequestMapping("/getById")
    public String getById(String userId, String readonly, HttpServletRequest request) throws Exception {
        SysUser sysUser = sysUserService.getById(userId);
        sysUser.setGenderCd(EhcacheUtil.getSingleSysDictryCdByCache(sysUser.getGenderCd(),"CD0006").getDictryNm());
        sysUser.setStus(EhcacheUtil.getSingleSysDictryCdByCache(sysUser.getStus(),"SYS05").getDictryNm());
        sysUser.setBlngtoOrgNo(EhcacheUtil.getSysOrgInfoByOrgCd(sysUser.getBlngtoOrgNo()).getName());
        request.setAttribute("sysUser",sysUser);
        return "sys/SysUserDetail";
    }

    @RequestMapping("/getByPage")
    @ResponseBody
    public DataGrid getByPage(SysUser sysUser, HttpServletRequest request) throws Exception {
        PageParameters page = this.getPage(request);
        SysUser loginUser = getUser();
        List<SysOrganization> authOrgList = CommonUtil.getAllOrgInfoByUserId(loginUser);
        return sysUserService.getByPage_rewrite(page,sysUser,authOrgList);
    }

    @RequestMapping("/changeStus")
    @ResponseBody
    public AjaxResult changeStus(String userId, HttpServletRequest request) throws Exception {
        boolean bool = sysUserService.changeStus(userId);
        if (bool) {
            return new AjaxResult(true, "状态修改成功");
        } else {
            return new AjaxResult(false, "状态修改失败");
        }
    }

    @RequestMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(String userId, HttpServletRequest request) throws Exception {
        boolean bool = sysUserService.restPassword(userId);
        if (bool) {
            return new AjaxResult(true, "密码重置成功");
        } else {
            return new AjaxResult(false, "密码重置失败");
        }
    }

    @RequestMapping("/distributeRole")
    @ResponseBody
    public AjaxResult distributeRole(String userId, String roles, HttpServletRequest request) throws Exception {
        List<String> roleIds = new ArrayList<>();
        if (!CommonUtil.isEmpty(roles)) {
            String[] roleArray = roles.substring(0,roles.lastIndexOf(",")).split(",");
            for (String str : roleArray) {
                roleIds.add(str);
            }
        }
        boolean bool = sysUserService.saveUserRoleRel(userId, roleIds);
        if (bool) {
            return new AjaxResult(true, "用户分配角色成功");
        } else {
            return new AjaxResult(false, "用户分配角色失败");
        }
    }

    @RequestMapping("/distributeGroup")
    @ResponseBody
    public AjaxResult distributeGroup(String userId, String groups, HttpServletRequest request) throws Exception {
        List<String> groupIds = new ArrayList<>();
        if (!CommonUtil.isEmpty(groups)) {
            String[] roleArray = groups.substring(0,groups.lastIndexOf(",")).split(",");
            for (String str : roleArray) {
                groupIds.add(str);
            }
        }
        boolean bool = false;
        try{
            bool = sysUserService.saveUserGroupRel(userId, groupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult(false, "用户分配用户组失败");
        }
        if (bool) {
            return new AjaxResult(true, "用户分配用户组成功");
        } else {
            return new AjaxResult(false, "用户分配用户组失败");
        }
    }

    @RequestMapping("/getOemEmplyById")
    @ResponseBody
    public AjaxResult getOemEmplyById(String userId) throws Exception {
        SysUser sysUser = sysUserService.getOemEmplyById(userId);
        if (null != sysUser) {
            return new AjaxResult(true, "", sysUser, "");
        } else {
            return new AjaxResult(false, "输入的用户编号不存在于员工池中");
        }
    }

    @RequestMapping("/getOemEmplyByPage")
    @ResponseBody
    public DataGrid getOemEmplyByPage(String userId, String userNm, HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage iPage = sysUserService.getOemEmplyByPage(page, userId, userNm);
        return new DataGrid(iPage.getTotal(), iPage.getRecords());
    }

    @RequestMapping("/getShowUser")
    @ResponseBody
    public DataGrid getShowUser(SysUser sysUser, String id, String showFlag, HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage iPage = null;
        if ("role".equalsIgnoreCase(showFlag)) {
            iPage = sysUserService.getShowUser(page, sysUser, id,"");
        }else if ("group".equalsIgnoreCase(showFlag)) {
            iPage = sysUserService.getShowUser(page, sysUser,"",id);
        }else{
            iPage = sysUserService.getShowUser(page, sysUser,"","");
        }
        return new DataGrid(iPage.getTotal(), iPage.getRecords());
    }

    @RequestMapping("/getShowRoleUser")
    @ResponseBody
    public DataGrid getShowRoleUser(SysUser sysUser, String roleId, HttpServletRequest request) throws Exception {
        PageParameters page = this.getPage(request);
        return sysUserService.getShowRoleUser(page,roleId,sysUser);
    }

    @RequestMapping("/uniqueUserId")
    @ResponseBody
    public AjaxResult uniqueUserId(String userId) throws Exception {
        SysUser user = sysUserService.getById(userId);
        if (user != null){
            return new AjaxResult(true,"","","");
        }else{
            return new AjaxResult(false,"","","");
        }

    }
}
