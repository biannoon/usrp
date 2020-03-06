<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>统一监管报送</title>
        <script type="text/javascript">
            $(function(){
                getListShow("dataGrid", "子系统信息", "sysSubsystem", true);
            });
            //配套getListShow使用，当oper为true时增加操作列
            function formatOper(value, row, index){
                var str= "<a href=\"#\" class=\"easyui-linkbutton l-btn l-btn-small\" " +
                    "onclick=\"getDetail('newWin','子系统信息','sysSubsystem/getById?subsystemId=" + row.subsystemId + "')\">详情</a>";
                return str;
            }
        </script>
    </head>
    <body class="easyui_layout" style="background:#eee9dc;">
        <form id="query_form" action="sysSubsystem/getByPage" method="post">
            <table width="100%">
                <tr>
                    <td align="right" width="10%"><span style="font-size: 12px">子系统名称：</span></td>
                    <td align="left" width="10%"><input class="easyui-textbox" name="subsystemNm" style="width:150px;height: 20px;"></td>
                    <td align="left" width="80%">
                        <a href="#" onclick="Search('dataGrid','query_form','sysSubsystem/getByPage')"
                           class="easyui-linkbutton" text="查询" iconCls="icon-search" plain="true"></a>
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="left">
                        <div style="padding:2px 5px;">
                            <a href="#" onclick="add('newWin','子系统信息','sysSubsystem/input')"
                               class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"></a>
                            <a href="#" onclick="upd('dataGrid','newWin','子系统信息','sysSubsystem/getById','subsystemId')"
                               class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"></a>
                            <a href="#" onclick="del('dataGrid','sysSubsystem/delete','subsystemId')"
                               class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"></a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
        <!--按钮区域-->
        <%--<div style="padding:2px 5px;">
            <a href="#" onclick="add('newWin','子系统信息','sysSubsystem/input')"
               class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"></a>
            <a href="#" onclick="upd('dataGrid','newWin','子系统信息','sysSubsystem/getById','subsystemId')"
               class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"></a>
            <a href="#" onclick="del('dataGrid','sysSubsystem/delete','subsystemId')"
               class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"></a>
        </div>--%>
        <!-- 数据列表展示区域-->
        <div style="width: 100%"><table id="dataGrid"></table></div>
        <!-- 弹出窗口-->
        <div id="newWin"></div>
    </body>
</html>