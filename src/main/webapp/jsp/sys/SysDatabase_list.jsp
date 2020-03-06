<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/17
  Time: 11:14
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysDatabase.js"></script>
</head>
<body style="padding:5px;background:#eee;">
    <div id="dg_sys_database_tool" style="padding:3px">
        <table width="100%" style="text-align: center">
            <tr>
                <td align="right" width="10%"><span style="font-size: 12px">数据源名称：</span></td>
                <td align="left" width="15%"><input id="dsNm_se"></td>
                <td align="right" width="10%"><span style="font-size: 12px">数据源类型：</span></td>
                <td align="left" width="15%"><input id="dbTyp_se" data-options="editable:false,panelHeight:'auto'"></td>
                <td align="left" width="50">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true
                       onclick="searchSysDatabase()" >查询</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  plain=true
                       onclick="resetsearchSysDatabase()" >重置</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true
                       onclick="toDeailPage('insert','win_sys_database','数据源新增','sysDatabase/toDetailPage','400','400')">新增</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true
                       onclick="toDeailPage('update','win_sys_database','数据源修改','sysDatabase/toDetailPage','400','400')">修改</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true
                       onclick="toDeailPage('detail','win_sys_database','数据源详情','sysDatabase/toDetailPage','400','400')">详情</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true
                       onclick="toDeleteSysDatabase('sysDatabase/delete')">删除</a>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <table id="dg_sys_database" width="100%"></table>
    </div>
<!--动态生成模态框-->
<div id="win_sys_database"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initSysDatabasePage();
    })
</script>
</body>
</html>
