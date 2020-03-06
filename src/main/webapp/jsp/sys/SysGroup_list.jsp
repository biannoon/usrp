<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/11/12
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.scrcu.sys.entity.SysFunc" %>
<%@ page import="java.util.List" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    List<SysFunc> buttonList = (List<SysFunc>) request.getAttribute("buttonList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysGroup.js"></script>
</head>
<body class="easyui-layout" style="background:#eee;">
<div data-options="region:'center',title:'用户组列表'" style="width:60%;">
    <table width="100%">
        <tr>
            <td>
                <div>
                    <div id="dg_tool">
                        <table width="100%" style="text-align: center">
                            <tr>
                                <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">用户组名称：</td>
                                <td align="left"><input class="easyui-textbox" id="groupNm_se"  style="height:20px;width:150px;border:1px solid #ccc;"></td>
                                <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">所属机构：</td>
                                <td align="left"><input class="easyui-textbox" id="blngtoOrgNo_se" style="height:20px;width:150px;border:1px solid #ccc;"></td>
                            </tr>
                            <tr>
                                <td align="right" colspan="4">
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" size="small" onclick="doSearchSysGroup('1')">查询</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-set'" plain="true" size="small" onclick="clearSearchSysGroup('1')">重置</a>
                                    <a id="btn_add" href="#" ></a>
                                    <a id="btn_update" href="#" ></a>
                                    <a id="btn_remove" href="#" ></a>
                                    <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" size="small" onclick="toInsertSysGroup('win_sysGroup','<%=basePath%>')">新增</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" size="small" onclick="toUpdateSysGroup('win_sysGroup','<%=basePath%>')">修改</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" size="small" onclick="toDeleteSysGroup('<%=basePath%>')">删除</a>--%>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <table id="dg_sysGroup" class="easyui-datagrid" style="width:100%;height: auto"></table>
                </div>
            </td>
        </tr>
        <input class="easyui-textbox" id="groupId_hidden" type="hidden" value=""><!--隐藏框用于标记用户组-->
    </table>
</div>
//列表展示区
<div data-options="region:'east',title:'用户组管理',collapsible:false" style="background:#eee;width: 40%">
    <div id="tbs_sysGroup" class="easyui-tabs" data-options="fit:true">
        <div title="用户组资源" data-options="closable:false" style="overflow:auto;">
            <table width="100%">
                <tr>
                    <td>
                        <div id="dg_resources_tool" style="">
                            <table width="100%" style="text-align: center">
                                <tr>
                                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">资源类型：</td>
                                    <td align="left"><input class="easyui-combobox" id="recoursTyp_se" style="height:20px;width:100px;border:1px solid #ccc;editable:false;panelHeight:'auto'"></td>
                                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">资源名称：</td>
                                    <td align="left"><input class="easyui-textbox" id="recoursNm_se" style="height:20px;width:100px;border:1px solid #ccc"></td>
                                </tr>
                                <tr>
                                    <td align="right" colspan="4">
                                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" size="small" onclick="doSearchSysGroup('2')">查询</a>
                                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-set'" plain="true" size="small" onclick="clearSearchSysGroup('2')">重置</a>
                                        <a id="btn_resource_add" href="#" ></a>
                                        <a id="btn_resource_remove" href="#" ></a>
                                        <%--<a href="#" class="easyui-menubutton" data-options="iconCls:'icon-add',menu:'#menu_add'" plain="true">资源添加</a>
                                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" size="small" onclick="toDeleteSysGroupRecources('<%=basePath%>')">资源删除</a>--%>
                                    </td>
                                    <%for (SysFunc button : buttonList){%>
                                    <%if ("S01020304".equals(button.getFuncId())){%>
                                    <div id="menu_add">
                                        <div onclick="toResourcesPage('win_sysGroup','<%=basePath%>','0','0')">机构</div>
                                        <div onclick="toResourcesPage('win_sysGroup','<%=basePath%>','1','0')">子系统</div>
                                    </div>
                                    <%}}%>
                                </tr>
                            </table>
                        </div>
                        <table id="dg_group_resources" class="easyui-datagrid" style="width: 100%;">
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
        <div title="已分配用户" data-options="closable:false">
            <table width="100%">
                <tr>
                    <td>
                        <table id="dg_sysUser" class="easyui-datagrid" style="width:100%;height: auto"></table>
                    </td>
                </tr>
            </table>
        </div>
        <div title="用户组详情" data-options="closable:false" style="">
            <div>
                <form id="form_sysGroup_detail" method="post" style="padding:3px 2px">
                    <table width="100%" height="auto">
                        <tr>
                            <td align="right"><span style="font-size: small;width: 100px">用户组ID：</span></td>
                            <td align="left"><input class="easyui-validatebox" id="groupId_detail" name="groupId" data-options="" /></td>
                        </tr>
                        <tr>
                            <td align="right"><span style="font-size: small;width: 100px">用户组名称：</span></td>
                            <td align="left"><input class="easyui-validatebox" id="groupNm_detail" name="groupNm" data-options="required:true" /></td>
                        </tr>
                        <tr>
                            <td align="right"><span style="font-size: small;width: 100px">用户组所属机构：</span></td>
                            <td align="left">
                                <%--<input class="easyui-validatebox" id="blngtoOrgNo_detail" name="blngtoOrgNo" data-options="required:true"/>--%>
                                <input class="easyui-validatebox" id="blngtoOrgNo_detail" data-options="required:true"/>
                                <input class="easyui-textbox" id="blngtoOrgNo_hidden" name="blngtoOrgNo" value="" type="hidden"/>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><span style="font-size: small;width: 100px">全局用户组标志：</span></td>
                            <td align="left"><input class="easyui-validatebox" id="isGlobal_detail" name="isGlobal" data-options="required:true" /></td>
                        </tr>
                        <tr>
                            <td align="right" valign="top"><span style="font-size: small;width: 100px">用户组说明：</span></td>
                            <td align="left"><input class="easyui-validatebox" id="groupComnt_detail" name="groupComnt" data-options="required:true" /></td>
                        </tr>
                    </table>
                </form>
                <div style="text-align:center">
                    <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-man" plain=true onclick="listSysGroupResources('win_sysGroup','<%=basePath%>')">分配资源</a>--%>
                    <a href="javascript:void(0)" class="easyui-linkbutton" id="but_save" plain=true iconCls="icon-save" onclick="submitSysGroup('form_sysGroup_detail','<%=basePath%>','sysGroup/updateSysGroup','0')">更新</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!--动态生成模态框-->
<div id="win_sysGroup"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {

        initSysGroupListPage('<%=basePath%>');

        //-初始化有权限的按钮
        <%for (SysFunc button : buttonList){%>
        <%if ("S01020301".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_add','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_add').bind('click',function () {
            toInsertSysGroup('win_sysGroup','<%=basePath%>');
        });
        <%}else if ("S01020302".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_update','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_update').bind('click',function () {
            toUpdateSysGroup('win_sysGroup','<%=basePath%>');
        });
        <%}else if ("S01020303".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_remove','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_remove').bind('click',function () {
            toDeleteSysGroup('<%=basePath%>');
        });
        <%}else if ("S01020304".equals(button.getFuncId())){%>
        $('#btn_resource_add').menubutton({
            text:'<%=button.getFuncNm()%>',
            iconCls:'<%=button.getIconUrl()%>',
            plain:true,
            menu:'#menu_add'
        });
        <%}else if ("S01020305".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_resource_remove','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_resource_remove').bind('click',function () {
            toDeleteSysGroupRecources('<%=basePath%>')
        });
        <%}}%>
    })
</script>
</body>
</html>
