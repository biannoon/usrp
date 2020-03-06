<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/17
  Time: 15:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysSubsystem.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<div id="dg_sys_subsystem_tool" style="padding:3px">
    <table width="100%" style="text-align: center">
        <tr>
            <td align="right" width="10%"><span style="font-size: 12px">子系统名称：</span></td>
            <td align="left" width="15%"><input id="subsystemNm_se"></td>
            <td align="left" width="50">
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true
                   onclick="searchSysSubsystem()" >查询</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  plain=true
                   onclick="resetsearchSysSubsystem()" >重置</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true
                   onclick="toDeailPage('insert','win_sys_subsystem','子系统新增','sysSubsystem/toDetailPage','400','350')">新增</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true
                   onclick="toDeailPage('update','win_sys_subsystem','子系统修改','sysSubsystem/toDetailPage','400','350')">修改</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true
                   onclick="toDeailPage('detail','win_sys_subsystem','子系统详情','sysSubsystem/toDetailPage','400','350')">详情</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true
                   onclick="toDeleteSysSubsystem('sysSubsystem/delete')">删除</a>
            </td>
        </tr>
    </table>
</div>
<div>
    <table id="dg_sys_subsystem" width="100%"></table>
</div>
<!--动态生成模态框-->
<div id="win_sys_subsystem"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initSysSubsystemPage();
    })
</script>
</body>
</html>
