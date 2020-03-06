<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/24
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.scrcu.sys.entity.SysFunc" %>
<%@ page import="java.util.List" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String tableId = (String) request.getAttribute("tableId");
    String enNm = (String) request.getAttribute("enNm");
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysTabConfig.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<table width="100%">
    <tr>
        <td>
            <div>
                <div id="dg_search" style="padding:3px">
                    <table width="100%" style="text-align: center">
                        <tr>
                            <td align="right"><span style="font-size: 12px;">表名：</span></td>
                            <td align="left"><input class="easyui-textbox" id="tabNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            <td align="right"><span style="font-size: 12px;">标签名称：</span></td>
                            <td align="left"><input class="easyui-textbox" id="lNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            <td align="right"><span style="font-size: 12px;">字段名称：</span></td>
                            <td align="left"><input class="easyui-textbox" id="fNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            <td align="right"><span style="font-size: 12px;">字段类型：</span></td>
                            <td align="left"><input class="easyui-textbox" id="fTyp_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="8">
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="doConfigSearch()" >条件查询</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain=true onclick="resetConfigSearch()">条件重置</a>
                                <a id="btn_add" href="#" ></a>
                                <a id="btn_update" href="#" ></a>
                                <a id="btn_remove" href="#" ></a>
                                <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsertTabConfigPage('win_sysTabConfig','系统菜单配置新增','<%=basePath%>SysTabConfig/toInsertSysTabConfig','900','400','<%=tableId%>','<%=enNm%>')">配置新增</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true onclick="toModifyTabConfigPage('win_sysTabConfig','系统菜单配置更新','<%=basePath%>SysTabConfig/toUpdateSysTabConfig','900','400')">配置更新</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDeleteTabConfig('<%=basePath%>SysTabConfig/deleteSysTabConfig')">删除</a>--%>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true onclick="toDetailTabConfigPage('win_sysTabConfig','系统菜单配置详情','<%=basePath%>SysTabConfig/toSysTabConfigDetail','900','400')">配置详情</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <table id="dg_sysTabConfig" class="easyui-datagrid">
                    <thead>
                    <tr>
                        <th data-options="field:'cd',checkbox:true"></th>
                        <th data-options="field:'tabNm',width:120,align:'center'">表名</th>
                        <th data-options="field:'lNm',width:120,align:'center'">标签名称</th>
                        <th data-options="field:'fNm',width:120,align:'center'">字段名称</th>
                        <th data-options="field:'fTyp',width:120,align:'center',dictry:'SYS14',formatter:formatData">字段类型</th>
                        <th data-options="field:'dictryId',width:120,align:'center',dictry:'root',formatter:formatData">字典代码</th>
                        <th data-options="field:'readOnly',width:120,align:'center',dictry:'SYS02',formatter:formatData">是否只读</th>
                        <th data-options="field:'mustInput',width:120,align:'center',dictry:'SYS02',formatter:formatData">是否必输</th>
                        <th data-options="field:'listShowFlg',width:120,align:'center',dictry:'SYS02',formatter:formatData">是否列表展示</th>
                        <th data-options="field:'detailShowFlg',width:120,align:'center',dictry:'SYS02',formatter:formatData">是否详情展示</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </td>
    </tr>
</table>
<!--动态生成模态框-->
<div id="win_sysTabConfig"></div>
<script type="text/javascript">
    $(function(){
        //初始化列表
        initSysTabConfigPage('<%=basePath%>','<%=tableId%>');

        //-初始化权限按钮
        <%for (SysFunc button : buttonList){%>
        <%if ("A01020101".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_add','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_add').bind('click',function () {
            toInsertTabConfigPage('win_sysTabConfig','系统菜单配置新增','<%=basePath%>SysTabConfig/toInsertSysTabConfig','900','400','<%=tableId%>','<%=enNm%>');
        });
        <%}else if ("A01020102".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_update','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_update').bind('click',function () {
            toModifyTabConfigPage('win_sysTabConfig','系统菜单配置更新','<%=basePath%>SysTabConfig/toUpdateSysTabConfig','900','400');
        });
        <%}else if ("A01020103".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_remove','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_remove').bind('click',function () {
            toDeleteTabConfig('<%=basePath%>SysTabConfig/deleteSysTabConfig');
        });
        <%}}%>
    })
</script>
</body>
</html>
