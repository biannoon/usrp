<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/14
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>同一监管报送</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/report/reportFileManage.js"></script>
    <style>
        .grid-panel .datagrid-btable tr{height: 20px;}
    </style>
</head>
<body id="reportFileManagePage" class="easyui-layout" style="background:#eee;">
<div data-options="region:'center',title:'子系统列表'" style="width:50%;background:#eee;">
    <div id="manage_sys_table_tool">
        <table width="100%">
            <tr>
                <td align="right"><span style="font-size: 12px">子系统名称</span></td>
                <td align="left"><input id="subsystemNm_se"/></td>
                <td align="right"><span style="font-size: 12px">子系统编号</span></td>
                <td align="left"><input id="subsystemId_se"/></td>
            </tr>
            <tr>
                <td colspan="4" align="right">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="searchSubsystem()">查询</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="resetSearchSubsystem()">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <div class="grid-panel" style="width: 100%;height: 100%">
        <table id="manage_sys_table"></table>
    </div>
</div>
<!--隐藏属性-->
<input class="easyui-textbox" type="hidden" id="subsystemId_hidden" value="">
<input class="easyui-textbox" type="hidden" id="flag_hidden" value="">
<input class="easyui-textbox" type="hidden" id="ids_hidden" value="">

<div data-options="region:'east',title:'报送接口列表'" style="width:50%;background:#eee;">
    <div id="manage_mansbj_table_tool">
        <table width="100%">
            <tr>
                <td align="right"><span style="font-size: 12px">报送接口名称：</span></td>
                <td align="left"><input id="mansbjNm_se" /></td>
                <td align="right"><span style="font-size: 12px">报送接口状态：</span></td>
                <td align="left"><input id="stats_se" /></td>
            </tr>
            <tr>
                <td colspan="4" align="right">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="searchMansbj()">查询</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="resetSearchMansbj()">重置</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="toReportFileRecordsPage('reportFileManage_win','now')">查看报文记录</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="toReportFileRecordsPage('reportFileManage_win','hist')">查看历史报文记录</a>
                </td>
            </tr>
        </table>
    </div>
    <div class="grid-panel" style="width: 100%;height: 100%">
        <table id="manage_mansbj_table"></table>
    </div>
</div>
<div id="reportFileManage_win"></div>
<script type="text/javascript">
    $(function () {
        initReportFileManagePage();
    })
</script>
</body>
</html>
