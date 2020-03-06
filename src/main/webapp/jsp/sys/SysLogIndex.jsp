<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/15
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    %>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysLog.js"></script>
</head>
<body style="background:#eee;">
    <div id="dg_sys_log_tool">
        <table width="100%">
            <tr>
                <td align="right">
                    <a href="javascript:void(0)"
                       class="easyui-linkbutton"
                       iconCls="icon-search"
                       plain=true
                       onclick="toSearchLog()">系统日志文件查看</a>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <table id="dg_sys_log" style="width:100%;"></table>
    </div>
    <div id="win_sys_log_server"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initServerList_DG('dg_sys_log');
    })
</script>
</body>
</html>
