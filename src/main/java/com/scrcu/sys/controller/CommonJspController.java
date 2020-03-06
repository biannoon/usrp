package com.scrcu.sys.controller;

import com.scrcu.common.base.BaseController;
import com.scrcu.common.base.bo.PageParameters;
import com.scrcu.common.base.vo.DataGrid;
import com.scrcu.common.utils.ComponentUtil;
import com.scrcu.sys.entity.SysUser;
import com.scrcu.sys.service.SysDictryCdService;
import com.scrcu.sys.service.SysFuncService;
import com.scrcu.sys.service.SysOrganizationService;
import com.scrcu.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author pengjuntao
 * @Date 2019/10/9 10:47
 * @Version 1.0
 * @function 共用组件
 */
@Controller
public class CommonJspController extends BaseController {

    @Autowired
    private SysDictryCdService sysDictryCdService;
    @Autowired
    private SysOrganizationService sysOrganizationService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysFuncService sysFuncService;

    /**
     * 测试
     * @return
     */
    @RequestMapping("common/toIndex")
    public String toCommonJsp(){
        return "apm/common/SysCommonTest";
    }

    /**
     * 描述：系统共用组件：系统标准字典代码公共组件跳转
     * @param winId 窗口ID
     * @param fieldId 需要回显的字段id 如果存在多个文本框需要回显，id之间用$拼接
     * @param isLevel 是否有层级关系， 0为无层级关系，1为具有层级关系
     * @param selectLeafOnly 如果有层级关系，是否只能选择叶子节点 0为否，1为是
     * @param checkbox 单选/多选，0为单选，1为多选
     * @param dictryId 指定字典类型代码，如果查询全部，则不填写
     * @author pengjuntao
     */
    @RequestMapping("common/toSysDictryCd_show")
    public String toSysDictryCdPage(HttpServletRequest request,
                                    String winId,
                                    String fieldId,
                                    String isLevel,
                                    String selectLeafOnly,
                                    String checkbox,
                                    String dictryId){
        request.setAttribute("checkBox",checkbox);
        request.setAttribute("selectLeafOnly",selectLeafOnly);
        request.setAttribute("dictryId",dictryId);
        request.setAttribute("winId",winId);
        request.setAttribute("fieldId",fieldId);
        request.setAttribute("isLevel",isLevel);
        return "apm/common/SysDictryCd_component";
    }

    /**
     * 描述：系统共用组件-系统字典查询选择组件-查询字典数据（树形展示）
     * @param id 父节点ID
     * @param dictryId 字典代码类型
     * @return
     * @author pengjuntao
     */
    @RequestMapping("common/getSysDictryCdListByTree")
    @ResponseBody
    public String getSysDictryCdListByTree(String id,
                                           String dictryId){
        return sysDictryCdService.getSysDictryCdByTree(id,dictryId);
    }

    /**
     * 描述：系统共用组件-系统字典查询选择组件-查询字典数据（列表展示）
     * @param pageParameters 分页
     * @param dictryNm 查询条件 字典代码名称
     * @param dictryId 字典代码类型
     * @return
     * @author pengjuntao
     */
    @RequestMapping("common/getSysDictryCdListByDatagrid")
    @ResponseBody
    public DataGrid getSysDictryCdListByDatagrid(PageParameters pageParameters,
                                                 String dictryNm,
                                                 String dictryId){
        return sysDictryCdService.getSysDictryCdByDatagrid(pageParameters,dictryNm,dictryId);
    }

    /**
     *  跳转到机构选择公共组件
     * @param treeType
     *      机构树分类：0为标准机构树,1为自定义机构树
     * @param selfTreeType
     *      自定义机构树类型：机构树分类为自定义机构树时有效，如机构树分类为自定义机构树，且自定义机构树类型为空，则表示展示所有类型的自定义机构树
     * @param orgLimits
     *      机构权限控制标志：用于确定选择界面的机构树是否根据当前用户的用户组权限来控制机构树中机构节点的范围
     *      false为否，true为是
     * @param selectType
     *      单选多选标志：0为单选，1为多选
     * @param treeNodeSelectType
     *      机构节点选择模式：当单选多选标志为多选时有效
     *      1、多选模式下，选择上级节点后，该节点的所有下级节点不可选择=0
     *      2、不做控制，多选模式下可同时选择上级和下级机构=1
     * @param expOrgNo
     *      排除机构编号：
     *      当不为空时，展现的机构树中不包括该编号对应的机构节点及其所有下级节点，用于机构归属关系调整的场景
     * @param winId
     * @return
     */
    @RequestMapping("common/toSysOrgInfoPage")
    public String toSysOrgInfoPage(HttpServletRequest request,
                                   String treeType,
                                   String selfTreeType,
                                   String orgLimits,
                                   String selectType,
                                   String treeNodeSelectType,
                                   String expOrgNo,
                                   String winId,
                                   String fieldId){
        request.setAttribute("treeType",treeType);
        request.setAttribute("selfTreeType",selfTreeType);
        request.setAttribute("orgLimits",orgLimits);
        request.setAttribute("selectType",selectType);
        request.setAttribute("expOrgNo",expOrgNo);
        request.setAttribute("treeNodeSelectType",treeNodeSelectType);
        request.setAttribute("winId",winId);
        request.setAttribute("fieldId",fieldId);
        return "apm/common/SysOrgInfo_component";
    }

    /**
     *系统共用组件--系统机构组件(标准机构树)：初始化机构树数据
     * @param id  父节点ID
     * @param orgLimits  机构权限控制
     * @param expOrgNo 排除机构编号
     * @return
     */
    @RequestMapping("common/getSysOrgInfoByStandard")
    @ResponseBody
    public String getSysOrgInfoByStandard(String id, String orgLimits, String expOrgNo){
        SysUser user = this.getUser();
        return ComponentUtil.getSysOrgInfoByStandard(id, orgLimits, user, expOrgNo);
    }

    /**
     * 系统共用组件--系统机构组件（自定义机构树）：初始化机构树类型
     * @param id 机构树类型节点的ID
     * @param selfTreeType 自定义机构树类型
     * @param orgLimits 机构权限控制
     * @param expOrgNo 排除机构编号
     * @return
     */
    @RequestMapping("common/getSysOrgInfoBySelf")
    @ResponseBody
    public String getSysOrgInfoBySelf(String id, String selfTreeType, String orgLimits, String expOrgNo){
        SysUser user = this.getUser();
        return ComponentUtil.getSysOrgInfoBySelf(id, selfTreeType, orgLimits, user, expOrgNo);
    }

    //-------------------------------------------------------------------

    /**
     * 描述：系统共用组件-系统用户选择
     * @param request
     * @param selectType
     * @param roleLimit
     * @param haveRemoved
     * @param orgController
     * @param winId
     * @param fieldId
     * @return
     */
    @RequestMapping("common/toSysUserInfo")
    public String toSysUserInfo(HttpServletRequest request,
                                String selectType,
                                String roleLimit,
                                String haveRemoved,
                                String orgController,
                                String winId,
                                String fieldId){
        request.setAttribute("selectType",selectType);
        request.setAttribute("roleLimit",roleLimit);
        request.setAttribute("haveRemoved",haveRemoved);
        request.setAttribute("orgController",orgController);
        request.setAttribute("winId",winId);
        request.setAttribute("fieldId",fieldId);
        return "apm/common/SysUser_component";
    }

    /**
     * 系统共用组件：初始化系统用户
     * @param user              用户查询条件
     * @param pageParameters    分页
     * @param roleLimit         是否有用户角色限制：null表示无角色限制，不为Null且有具体角色编号是，为指定的角色限制
     * @param haveRemoved       是否包含注销状态的用户 null表示不限制用户状态，其他为限制
     * @param orgController     机构范围控制标志 true表示控制机构范围，false表示不控制机构范围
     * @author pengjuntao
     * @return
     */
    @RequestMapping("common/getSysUserByCommon")
    @ResponseBody
    public DataGrid getSysUserByCommon(PageParameters pageParameters,
                                       String userId,
                                       String userNm,
                                       String blngtoOrgNo,
                                       String roleLimit,
                                       String haveRemoved,
                                       String orgController){
        SysUser user = new SysUser();
        user.setBlngtoOrgNo(blngtoOrgNo);
        user.setUserNm(userNm);
        if (userId != null && !"".equals(userId)){
            user.setFinlModifr(userId);//替代属性
        }
        if ("true".equals(orgController)){
            user.setUserId(this.getUser().getUserId());
        }
        return sysUserService.getSysUserByCommon(pageParameters,user,roleLimit,haveRemoved,orgController);
    }

    //---------------------------------------------------------

    /**
     * 系统共用组件：系统功能菜单树
     * @return
     */
    @RequestMapping("common/toSysFuncTree")
    public String toSysFuncTree(HttpServletRequest request,String winId,String fieldId){
        request.setAttribute("winId",winId);
        request.setAttribute("fieldId",fieldId);
        return "apm/common/SysFunc_tree";
    }

    /**
     * 描述：初始化系统功能菜单树
     * @param id
     * @return
     */
    @RequestMapping("common/getSysFuncTree")
    @ResponseBody
    public String getSysFuncTree(String id){
        return sysFuncService.listAllSysMenu(id,true);
    }


    /**
     * 描述：跳转任务组树
     * @param request
     * @param winId
     * @param fieldId
     * @return
     */
    @RequestMapping("common/toAscGroupList")
    public String toAscGroupList(HttpServletRequest request, String winId,String fieldId){
        request.setAttribute("winId",winId);
        request.setAttribute("fieldId",fieldId);
        return "apm/common/AscGroup_tree";
    }

}
