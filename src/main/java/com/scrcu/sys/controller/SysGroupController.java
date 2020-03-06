package com.scrcu.sys.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.scrcu.common.annotation.LogAnnotation;
import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.DataList;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.AjaxResult;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.*;
import com.scrcu.sys.entity.*;
import com.scrcu.sys.service.SysGroupService;
import com.scrcu.sys.service.SysRoleService;
import com.scrcu.sys.service.SysSubsystemService;
import com.scrcu.sys.service.SysUserService;
import com.scrcu.sys.service.impl.SysFuncServiceImpl;
import com.scrcu.sys.service.impl.SysOrganizationServiceImpl;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述： 用户组控制器
 * @创建人： jiyuanbo
 * @创建时间： 2019/9/19 10:32
 */
@Controller
@RequestMapping(value = "/sysGroup")
public class SysGroupController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private SysGroupService sysGroupService;
    @Resource
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysFuncServiceImpl sysFuncService;
    @Autowired
    private SysOrganizationServiceImpl sysOrganizationService;
    @Autowired
    private SysSubsystemService sysSubsystemService;

    //-----------------------------pengjuntao重写用户组模块功能---------------------

    /**
     * 描述：跳转至系统用户组页面
     * @param request
     * @param funcId
     * @return
     */
    @RequestMapping("/toSysGroupList")
    public String toSysGroupList(HttpServletRequest request, String funcId){
        //-获取有操作权限的功能按钮
        AuthorityManageUtil.sendAuthorityManageSysFuncListToJsp(request,getUser(),funcId);
        return "sys/SysGroup_list";
    }

    /**
     * 描述：用户组页面--初始化用户组列表数据
     * @param request
     * @param sysGroup
     * @return
     */
    @RequestMapping("/listSysGroup")
    @ResponseBody
    public DataGrid listSysGroup(HttpServletRequest request, SysGroup sysGroup){
        PageParameters pageParameters = this.getPage(request);
        return sysGroupService.listSysGroup(sysGroup,pageParameters);
    }

    /**
     * 描述：用户组页面--根据用户组ID，初始化用户组已分配用户列表数据
     * @param request
     * @param groupId
     * @return
     */
    @RequestMapping("/listSysUserByGroupId")
    @ResponseBody
    public DataGrid listSysUserByGroupId(HttpServletRequest request, String groupId){
        PageParameters pageParameters = this.getPage(request);
        return sysGroupService.listSysUserByGroupId(groupId,pageParameters);
    }

    /**
     * 描述：用户组页面--根据用户组ID，初始化用户组分配资源列表数据
     * @param request
     * @param sysGroupRecoursRel
     * @return
     */
    @RequestMapping("/listRecoursByGroupId")
    @ResponseBody
    public DataGrid listRecoursByGroupId(HttpServletRequest request,SysGroupRecoursRel sysGroupRecoursRel){
        PageParameters pageParameters = this.getPage(request);
        return sysGroupService.listRecoursByGroupId(sysGroupRecoursRel,pageParameters);
    }



    /**
     * 描述：用户组首页打开用户组新增页面
     * @return
     */
    @RequestMapping("/toInsertSysGroup")
    public String toInsertSysGroup(HttpServletRequest request,String winId){
        request.setAttribute("groupId",CommonUtil.getUUID(10));
        request.setAttribute("winId",winId);
        return "sys/SysGroup_insert";
    }

    /**
     * 描述：新增用户组
     * @param request
     * @param sysGroup
     * @return
     */
    @RequestMapping("/insertSysGroup")
    @ResponseBody
    @LogAnnotation(id={"groupId"},operType = "SYS09SYSGROUP01",operation = "系统用户组：新增系统用户组")
    public AjaxResult insertSysGroup(HttpServletRequest request, SysGroup sysGroup){
        SysUser user = getUser();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        sysGroup.setCreatr(user.getUserId());
        sysGroup.setCrtDt(now);
        sysGroup.setFinlModifr(user.getUserId());
        sysGroup.setFinlModfyDt(now);
        return sysGroupService.insertSysGroup(sysGroup);
    }

    /**
     * 描述：用户组首页打开用户组更新页面
     * @param request
     * @param groupId
     * @param winId
     * @return
     */
    @RequestMapping("/toUpdateSysGroup")
    public String toUpdateSysGroup(HttpServletRequest request, String groupId,String winId){
        //查询详情
        SysGroup sysGroup = sysGroupService.getSysGroupById(groupId);
        request.setAttribute("sysGroup",sysGroup);
        request.setAttribute("winId",winId);
        //获取用户组资源，并存入内存中
        List<SysGroupRecoursRel> list = sysGroupService.listRecoursByGroupIdWithoutPage(sysGroup);
        EhcacheUtil.initTempCache(EhcacheUtil.TEMP_GROUP_RES + sysGroup.getGroupId(), list);
        return "sys/SysGroup_update";
    }

    /**
     * 描述：从用户组首页发出查询详情请求
     * @param request
     * @param groupId
     * @return
     */
    @RequestMapping("/toUpdateSysGroup01")
    @ResponseBody
    public AjaxResult toUpdateSysGroup01(HttpServletRequest request, String groupId){
        //查询详情
        SysGroup sysGroup = sysGroupService.getSysGroupById(groupId);
        //清空资源缓存
        if (TempCodeUtils.getInstance().getSysGroupRecoursMap() != null){
            TempCodeUtils.getInstance().getSysGroupRecoursMap().clear();
        }
        //获取用户组资源，并存入内存中
       /* List<SysGroupRecoursRel> list = sysGroupService.listRecoursByGroupIdWithoutPage(sysGroup);
        Map<String,List> map = new HashMap<>();
        map.put(groupId,list);
        TempCodeUtils.getInstance().setSysGroupRecoursMap(map);*/
        return new AjaxResult(true,"",sysGroup,"");
    }



    /**
     * 描述：更新用户组
     * @param request
     * @param sysGroup
     * @return
     */
    @RequestMapping("/updateSysGroup")
    @ResponseBody
    @LogAnnotation(id={"groupId"},operType = "SYS09SYSGROUP02",operation = "系统用户组：更新系统用户组")
    public AjaxResult updateSysGroup(HttpServletRequest request, SysGroup sysGroup){
        SysUser user = getUser();
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        sysGroup.setFinlModifr(user.getUserId());
        sysGroup.setFinlModfyDt(now);
        return sysGroupService.updateSysGroup(sysGroup);
    }


    /**
     * 描述：删除用户组
     * @param request
     * @param groupId
     * @return
     */
    @RequestMapping("/deleteSysGroup")
    @ResponseBody
    @LogAnnotation(id={"groupId"},operType = "SYS09SYSGROUP03",operation = "系统用户组：删除系统用户组")
    public AjaxResult deleteSysGroup(HttpServletRequest request, String groupId){
        return sysGroupService.deleteSysGroup(groupId);
    }

    /**
     * 描述：检查是否有用户已经分配了该用户组
     * @param request
     * @param groupId 用户组ID
     * @return
     */
    @RequestMapping("/isExistUserUnderGroup")
    @ResponseBody
    public String isExistUserUnderGroup(HttpServletRequest request, String groupId){
        return sysGroupService.isExistUserUnderGroup(groupId);
    }

    /**
     * 描述：跳转用户组资源列表页面
     * @param request
     * @param winId
     * @return
     */
    @RequestMapping("/toSysGroupResourcesList")
    public String toSysGroupResourcesList(HttpServletRequest request,String winId){
        request.setAttribute("winId",winId);
        return "sys/SysGroup_resources_list";
    }

    /**
     * 描述：新增时，资源选择窗口
     * @param request
     * @param treeType
     * @param selfTreeType
     * @param orgLimits
     * @param selectType
     * @param treeNodeSelectType
     * @param expOrgNo
     * @param winId
     * @param fieldId
     * @param flag
     * @return
     */
    @RequestMapping("/toResourcesPage")
    public String toResourcesPage(HttpServletRequest request,
                                  String treeType,
                                  String selfTreeType,
                                  String orgLimits,
                                  String selectType,
                                  String treeNodeSelectType,
                                  String expOrgNo,
                                  String winId,
                                  String fieldId,
                                  String flag,
                                  String whereFlag){
        request.setAttribute("flag",flag);
        request.setAttribute("treeType",treeType);
        request.setAttribute("selfTreeType",selfTreeType);
        request.setAttribute("orgLimits",orgLimits);
        request.setAttribute("selectType",selectType);
        request.setAttribute("expOrgNo",expOrgNo);
        request.setAttribute("treeNodeSelectType",treeNodeSelectType);
        request.setAttribute("winId",winId);
        request.setAttribute("fieldId",fieldId);
        request.setAttribute("whereFlag",whereFlag);
        return "sys/SysGroup_resources";
    }

    /**
     * 描述：获取子系统资源，用于用户组新增时，分配资源
     * @return
     */
    @RequestMapping("/getChildSysFromSysFuncBySysGroup")
    @ResponseBody
    public DataGrid getChildSysFromSysFuncBySysGroup(HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage<SysSubsystem> iPage = sysSubsystemService.getByPage(page,new SysSubsystem());
        return new DataGrid(iPage.getTotal(),iPage.getRecords());
    }


    /**
     * 描述：新增用户组时，分配资源
     * 说明：此时选中的资源先存到内存中，并不直接存入数据库，等待具体新增的提交动作完成后再进行将该资源提交到数据库中
     * @param resources 资源ID（已_连接的资源集合）
     * @param resourceType 资源类型
     * @param groupId 用户组ID
     * @return
     */
    @RequestMapping("/addResources")
    @ResponseBody
    public AjaxResult addSysGroupResources(HttpServletRequest request, String resources, String resourceType, String groupId){
        System.out.println("type:"+resourceType);
        System.out.println("groupId:"+groupId);
        System.out.println("res:"+resources);
        return sysGroupService.addSysGroupResources(resources,resourceType,groupId);
    }

    /**
     * 描述：从内存中删除已选定的用户组资源，同上方法的处理方式
     * @param request
     * @param resources 资源ID的集合
     * @param groupId 用户组ID
     * @return
     */
    @RequestMapping("/deleteSysGroupResources")
    @ResponseBody
    public AjaxResult deleteSysGroupResources(HttpServletRequest request, String resources, String groupId){
        return sysGroupService.deleteSysGroupResources(resources,groupId);
    }

    /**
     * 描述：从数据库中删除选定的用户组资源
     * @param request
     * @param resources
     * @param groupId
     * @return
     */
    @RequestMapping("/deleteSysGroupResourcesFromDB")
    @ResponseBody
    public AjaxResult deleteSysGroupResourcesFromDB(HttpServletRequest request, String resources, String groupId){
        return sysGroupService.deleteSysGroupResourcesFromDB(resources,groupId);
    }

    /**
     * 描述：直接添加用户组资源到数据库
     * @param request
     * @param resources
     * @param resourceType
     * @param groupId
     * @return
     */
    @RequestMapping("/addSysGroupResourcesToDB")
    @ResponseBody
    public AjaxResult addSysGroupResourcesToDB(HttpServletRequest request,String resources, String resourceType, String groupId){
        return sysGroupService.addSysGroupResourcesToDB(resources,resourceType,groupId);
    }

    @RequestMapping("/listOrgInfoFromSysGroup")
    @ResponseBody
    public String listOrgInfoFromSysGroup(String id){
        return sysGroupService.listOrgInfoFromSysGroup(id);
    }

    /**
     * 描述：将机构编码转换为机构id
     */
    @RequestMapping("/exchangeOrgCd")
    @ResponseBody
    public String exchangeOrgCd(String orgCd){
        return sysOrganizationService.getSysOrgInfoByOrgCd(orgCd).getId();
    }

    /**
     * 描述：通过机构编码查询机构名称
     */
    @RequestMapping("/getOrgNmByOrgCd")
    @ResponseBody
    public AjaxResult getOrgNmByOrgCd(String orgCd){
        /*return EhcacheUtil.getSysOrgInfoByOrgCd(orgCd).getName();*/
        return new AjaxResult(true,"", EhcacheUtil.getSysOrgInfoByOrgCd(orgCd),"");
    }

    /**
     * 清除缓存
     * @return
     */
    @RequestMapping("/clearResourcesCache")
    @ResponseBody
    public AjaxResult clearResourcesCache(String groupId){
        //清空资源缓存
        if (!"".equals(groupId) && groupId != null && EhcacheUtil.getTempCache(EhcacheUtil.TEMP_GROUP_RES, groupId) != null){
            EhcacheUtil.cacheRemoveByKey(EhcacheUtil.CACHE_TEMP,EhcacheUtil.TEMP_GROUP_RES + groupId);
        }
        return new AjaxResult(true,"","","");
    }



















    //-----------------------------jiyuanbo------------------------------------------
    @RequestMapping("/input")
    public ModelAndView input(HttpServletRequest request) throws Exception {
        SysGroup sysGroup = new SysGroup();
        sysGroup.setCreatr(this.getUser().getUserId());
        sysGroup.setCrtDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysGroupDetail");
        mv.getModel().put("resultMap", sysGroup);
        mv.getModel().put("action", "insert");
        return mv;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public AjaxResult insert(SysGroup sysGroup, HttpServletRequest request) throws Exception {
        boolean bool = sysGroupService.isExists(sysGroup.getGroupId());
        if (bool) {
            return new AjaxResult(false, "该用户组已存在");
        } else {
            bool = sysGroupService.insert(sysGroup);
            if (bool) {
                return new AjaxResult(true, "新增成功", sysGroup.getGroupId(), "String");
            } else {
                return new AjaxResult(false, "新增失败");
            }
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(String ids, HttpServletRequest request) throws Exception {
        String groupIdArry[] = ids.split(",");
        boolean bool = false;
        for (String groupId : groupIdArry) {
            bool = sysGroupService.delete(groupId);
        }
        if (bool) {
            return new AjaxResult(true, "删除成功");
        } else {
            return new AjaxResult(false, "删除失败");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(SysGroup sysGroup, HttpServletRequest request) throws Exception {
        sysGroup.setFinlModifr(this.getUser().getUserId());
        sysGroup.setFinlModfyDt(DateUtil.getCurrentDate("yyyy-MM-dd"));
        boolean bool = sysGroupService.update(sysGroup);
        if (bool) {
            return new AjaxResult(true, "修改成功");
        } else {
            return new AjaxResult(false, "修改失败");
        }
    }

    @RequestMapping("/getById")
    public ModelAndView getById(String groupId, String readonly, HttpServletRequest request) throws Exception {
        SysGroup sysGroup = sysGroupService.getById(groupId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("sys/SysGroupDetail");
        mv.getModel().put("resultMap", sysGroup);
        mv.getModel().put("readonly", readonly);
        mv.getModel().put("action", "update");
        return mv;
    }

    @RequestMapping("/getByPage")
    @ResponseBody
    public DataGrid getByPage(SysGroup sysGroup, HttpServletRequest request) throws Exception {
        Page page = new Page(this.getPage(request).getPage(), this.getPage(request).getRows());
        IPage<SysGroup> iPage = sysGroupService.getByPage(page, sysGroup);
        return new DataGrid(iPage.getTotal(), iPage.getRecords());
    }

    @RequestMapping("/distributeResouse")
    @ResponseBody
    public AjaxResult distributeResouse(String groupId, String orgs, String systems) throws Exception{
        boolean bool = false;
        List<String> resouseList = null;
        if (null != orgs) {
            String [] orgArry = orgs.substring(0,orgs.lastIndexOf("@")).split("@");
            resouseList = CommonUtil.arrayToList(orgArry);
            bool = this.sysGroupService.saveGroupResouseRel(groupId, resouseList, "SYS1101");
            if (bool) {
                return new AjaxResult(true, "用户组机构资源分配成功");
            } else {
                return new AjaxResult(false, "用户组机构资源分配失败");
            }
        }
        if (null != systems) {
            String [] sysArry = systems.substring(0,systems.lastIndexOf("@")).split("@");
            resouseList = CommonUtil.arrayToList(sysArry);
            bool = this.sysGroupService.saveGroupResouseRel(groupId, resouseList, "SYS1102");
            if (bool) {
                return new AjaxResult(true, "用户组子系统资源分配成功");
            } else {
                return new AjaxResult(false, "用户组子系统资源分配失败");
            }
        }
        return new AjaxResult();
    }

    @RequestMapping("/getGroupListByUserId")
    @ResponseBody
    public String getGroupListByUserId(String userId, HttpServletRequest request) throws Exception {
        List<SysGroup> groupList = sysGroupService.getGroupListByUserId(userId);
        List<DataList> dataListList = new ArrayList<>();
        for (SysGroup sysGroup : groupList) {
            DataList dataList = new DataList();
            dataList.setValue(sysGroup.getGroupId());
            dataList.setText(sysGroup.getGroupNm());
            dataListList.add(dataList);
        }
        return JSON.toJSONString(dataListList);
    }

    @RequestMapping("/getGroupListByLoginUser")
    @ResponseBody
    public String getGroupListByLoginUser(HttpServletRequest request,String userId) throws Exception {
        SysUser sysUser = this.getUser();
        //List<SysRole> roleList = sysRoleService.getRoleListByUserId(sysUser.getUserId());
        //List<SysGroup> groupList = sysGroupService.getGroupListByLoginUser(userId, roleList);
        List<SysGroup> allGroup = sysGroupService.getGroupListByLoginUser_re(sysUser);//获取当前登陆用户所具备的分配权限的用户组集合
        List<SysGroup> idGroup = sysGroupService.getGroupListByUserId(userId);//获取指定用户所拥有的用户组集合
        List<String> strList = new ArrayList<>();
        List<DataList> dataListList = new ArrayList<>();
        for (SysGroup group : idGroup){
            strList.add(group.getGroupId());
        }
        for (SysGroup group : allGroup){
            if (!strList.contains(group.getGroupId())){
                DataList dataList = new DataList();
                dataList.setValue(group.getGroupId());
                dataList.setText(group.getGroupNm());
                dataListList.add(dataList);
            }
        }
        return JSON.toJSONString(dataListList);
    }

    /**
     * <p>
     * 获取在公告发布对象中的用户组信息
     * </p>
     * @param pubNoticeId 公告id
     * @return java.util.List<com.scrcu.common.base.DataList>
     * @author wuyu
     * @date 2019/11/4 11:10
     **/
    @PostMapping("/getGroupListWithNotice")
    public @ResponseBody List<DataList> getGroupListWithNotice(String pubNoticeId) {
        return sysGroupService.getGroupListWithNotice(pubNoticeId);
    }

    /**
     * <p>
     * 获取不存在公告发布对象信息中的用户组信息
     * </p>
     * @param pubNoticeId 公告id
     * @return java.util.List<com.scrcu.common.base.DataList>
     * @author wuyu
     * @date 2019/11/4 11:11
     **/
    @PostMapping("/getGroupNotInPubNotice")
    public @ResponseBody List<DataList> getGroupNotInPubNotice(String pubNoticeId) {
        return sysGroupService.getGroupNotInPubNotice(pubNoticeId);
    }

    @RequestMapping("/getShowGroupUser")
    @ResponseBody
    public DataGrid getShowGroupUser(SysGroup sysGroup, String userId, HttpServletRequest request) throws Exception {
        PageParameters page = this.getPage(request);
        return sysGroupService.getShowGroupUser(page,userId,sysGroup);
    }
}
