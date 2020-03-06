<%@ page import="com.scrcu.sys.entity.SysGroup" %>
<%@ page import="com.scrcu.common.utils.EhcacheUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/12
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    SysGroup sysGroup = (SysGroup) request.getAttribute("sysGroup");
    String winId = (String) request.getAttribute("winId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body>
<div class="easyui-layout" style="background:#eee;" fit="true">
    <div data-options="region:'center',title:'基本信息'" style="width:40%;">
        <form id="form_sysGroup_update" method="post">
            <table width="100%">
                <input class="easyui-textbox" type="hidden" id="groupId_insert" name="groupId" value="<%=sysGroup.getGroupId()%>"/></td>
                <tr>
                    <td align="right"><span style="font-size: small;width: 100px">用户组名称：</span></td>
                    <td align="left"><input class="easyui-validatebox" id="groupNm_insert" name="groupNm" data-options="required:true" /></td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: small;width: 100px">用户组所属机构：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="blngtoOrgNo_insert" data-options="required:true,editable:false" buttonIcon="icon-search"/>
                        <input class="easyui-textbox" id="blngtoOrgNo_hid" name="blngtoOrgNo" value="<%=sysGroup.getBlngtoOrgNo()%>" type="hidden"/>
                    </td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: small;width: 100px">全局用户组标志：</span></td>
                    <td align="left"><input class="easyui-validatebox" id="isGlobal_insert" name="isGlobal" data-options="required:true,panelHeight:'auto'" /></td>
                </tr>
                <tr>
                    <td align="right" valign="top"><span style="font-size: small;width: 100px">用户组说明：</span></td>
                    <td align="left" colspan="4"><input class="easyui-validatebox" id="groupComnt_insert" name="groupComnt" data-options="required:false,multiline:true" /></td>
                </tr>
                <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
            </table>
        </form>
        <div style="text-align:center">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="but_insert" iconCls="icon-save" plain=true onclick="submitSysGroup('form_sysGroup_update','<%=basePath%>','sysGroup/updateSysGroup','1')">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="but_reset" iconCls="icon-set" plain=true onclick="resetForm('form_sysGroup_update')">重置</a>
           <%-- <a href="javascript:void(0)" class="easyui-linkbutton" id="but_resource" iconCls="icon-save" plain=true onclick="listSysGroupResources('win_sysGroup_insert','<%=basePath%>')">分配资源</a>--%>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="but_close" iconCls="icon-cancel" plain=true onclick="closeSysGroupWindow('<%=winId%>')">关闭</a>
        </div>
    </div>
    <div data-options="region:'east',title:'资源维护',collapsible:false" style="background:#eee;width: 60%">
        <table width="97%">
            <tr>
                <td>
                    <div id="dg_resource_tool" align="right">
                        <a href="#" id="but_resource" class="easyui-menubutton" data-options="iconCls:'icon-add',menu:'#menu_add_01'" plain="true">添加</a>
                        <a href="#" id="but_remove_cancel" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" size="small" onclick="reduceSysGroupReosurces('<%=basePath%>')">删除</a>
                        <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain="true" size="small" onclick="closeWindow('<%=winId%>')">关闭</a>--%>
                    </div>
                    <div id="menu_add_01">
                        <div onclick="toResourcesPage('win_sysGroup_insert','<%=basePath%>','0','1')">机构</div>
                        <div onclick="toResourcesPage('win_sysGroup_insert','<%=basePath%>','1','1')">子系统</div>
                    </div>
                    <table id="dg_group_resources_list" class="easyui-datagrid" style="width: 100%">
                        <thead>
                        <tr>
                            <th data-options="field:'cd',checkbox:true"></th>
                            <th data-options="field:'recoursId',width:80,align:'center'">资源ID</th>
                            <th data-options="field:'recoursTyp',width:50,align:'center',dictry:'SYS11',formatter:formatData">资源类型</th>
                            <th data-options="field:'recoursNm',width:150,align:'center'">资源名称</th>
                        </tr>
                        </thead>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div id="win_sysGroup_insert"></div>
<script type="text/javascript">
    $(function () {
        //初始化文本框值
        initTextboxBySysGroup('groupNm_insert','<%=sysGroup.getGroupNm()%>',20,false,'200','20');
        initTextboxBySysGroup('blngtoOrgNo_insert','<%=EhcacheUtil.getSysOrgInfoByOrgCd(sysGroup.getBlngtoOrgNo()).getName()%>',0,false,'200','20');
        initComboboxBySysGroup('isGlobal_insert','SYS02','<%=sysGroup.getIsGlobal()%>',false,'200','20');
        initTextboxBySysGroup('groupComnt_insert','<%=sysGroup.getGroupComnt()%>',200,false,'200','100');
        //-初始化机构
        initSysOrgInfoComponent('blngtoOrgNo_insert', 'blngtoOrgNo_hid', 'win_sysGroup_insert', '0', '', 'false', '0', '0', '', '600', '400');

        //-初始化用户组资源维护的页面
        var groupId = $('#groupId_insert').val();
        initSysGroupResourcesList(groupId);
    })
</script>
</body>
</html>
