<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
    <script type="text/javascript">
        $(function(){
            getListShow("dataGrid", "数据源管理列表", "sysDatabase", true);
        });
        //配套getListShow使用，当oper为true时增加操作列
        function formatOper(value, row, index){
            var str= "<a href=\"#\" class=\"easyui-linkbutton l-btn l-btn-small\" " +
                "onclick=\"getDetail('newWin','数据源管理','sysDatabase/getById?id=" + row.id + "')\">详情</a>";
            return str;
        }
    </script>
</head>
<body class="easyui_layout">
<form id="query_form" action="sysDatabase/getByPage" method="post">
    <div style="padding:2px 5px;"><!-- 查询条件容器 -->
        <table width="100%">
            <tr>
                <td align="right" width="12%"><span style="font-size: 12px">数据源名称：</span></td>
                <td align="left" width="12%"><input class="easyui-textbox" name="dsNm" style="width:150px"></td>
                <td align="right" width="12%"><span style="font-size: 12px">数据源类型：</span></td>
                <td align="left" width="12%"><dh:select dictno="SYS15" name="dbTyp" value=""></dh:select></td>
                <td width="40%">
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search"
                       onclick="Search('dataGrid','query_form','sysDatabase/getByPage')" plain="true">查询</a>
                    <a href="#" onclick="add('newWin','数据源管理','sysDatabase/input')"
                       class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"></a>
                    <a href="#" onclick="upd('dataGrid','newWin','数据源管理','sysDatabase/getById','id')"
                       class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"></a>
                    <a href="#" onclick="del('dataGrid','sysDatabase/delete','id')"
                       class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"></a>
                </td>
            </tr>
        </table>
    </div>
</form>
<%--<div style="padding:2px 5px;">
    <a href="#" onclick="add('newWin','数据源管理','sysDatabase/input')"
       class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"></a>
    <a href="#" onclick="upd('dataGrid','newWin','数据源管理','sysDatabase/getById','id')"
       class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"></a>
    <a href="#" onclick="del('dataGrid','sysDatabase/delete','id')"
       class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"></a>
</div>--%>
<!-- 数据列表展示区域-->
<div style="width: 100%">
    <table id="dataGrid"></table>
</div>
<!-- 弹出窗口-->
<div id="newWin"></div>
</body>
</html>