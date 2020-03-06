<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/18
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysOperLog.js"></script>
</head>
<body style="padding:5px;background:#eee;">
    <div id="dg_sys_operLog_tool" style="padding:3px">
        <table width="100%" style="text-align: center">
            <tr>
                <td align="right"><span style="font-size: 12px">查询历史数据：</span></td>
                <td align="left"><input id="queryTyp_se" data-options="editable:false,panelHeight:'auto'"></td>
                <td align="right"><span style="font-size: 12px">操作类型：</span></td>
                <td align="left">
                    <input id="operTyp_se" buttonIcon="icon-search">
                    <input class="easyui-textbox" id="operTyp_hid_se" type="hidden">
                </td>
                <td align="right"><span style="font-size: 12px">开始时间：</span></td>
                <td align="left"> <input id="startTm_se"></td>
                <td align="right"><span style="font-size: 12px">结束时间：</span></td>
                <td align="left"> <input id="endTm_se"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: 12px">机构：</span></td>
                <td align="left">
                    <input id="org_se" buttonIcon="icon-search">
                    <input class="easyui-textbox" id="org_hid_se" type="hidden">
                </td>
                <td colspan="6" align="right">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain=true
                       onclick="searchOperlog()">查询</a>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-set" plain=true
                       onclick="resetSearchOperlog()">重置</a>
                </td>
             </tr>
        </table>
    </div>
    <div>
        <table id="dg_sys_operLog" style="width: 100%"></table>
    </div>
<!--动态生成模态框-->
<div id="win_sys_operLog"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initSysOperLogPage();
    })
</script>
</body>
</html>
