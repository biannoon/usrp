<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/11/13
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String winId = (String) request.getAttribute("winId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body>
<table width="100%">
    <tr>
        <td>
            <div id="dg_resource_tool" align="right">
                <a href="#" class="easyui-menubutton" data-options="iconCls:'icon-add',menu:'#menu_add_01'" plain="true">添加</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" size="small" onclick="reduceSysGroupReosurces('<%=basePath%>')">删除</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain="true" size="small" onclick="closeWindow('<%=winId%>')">关闭</a>
            </div>
            <div id="menu_add_01">
                <div onclick="toResourcesPage('win_sysGroup_insert_resources','<%=basePath%>','0','1')">机构</div>
                <div onclick="toResourcesPage('win_sysGroup_insert_resources','<%=basePath%>','1','1')">子系统</div>
            </div>
            <table id="dg_group_resources_list" class="easyui-datagrid" style="height: 450px">
                <thead>
                <tr>
                    <th data-options="field:'cd',checkbox:true"></th>
                    <th data-options="field:'recoursId',width:100,align:'center'">资源ID</th>
                    <th data-options="field:'recoursNm',width:100,align:'center'">资源名称</th>
                    <th data-options="field:'recoursTyp',width:100,align:'center',dictry:'SYS11',formatter:formatData">资源类型</th>
                </tr>
                </thead>
            </table>
        </td>
    </tr>
</table>
<div id="win_sysGroup_insert_resources"></div>
<script type="text/javascript">
    $(function () {
        var groupId = $('#groupId_insert').textbox('getValue');
        initSysGroupResourcesList('<%=basePath%>',groupId);
    })

    function closeWindow(winId) {
        $('#'+winId).window('close');
    }
</script>
</body>
</html>
