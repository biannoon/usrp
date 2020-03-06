<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title></title>
        <script type="text/javascript">
            $(function(){
                getListShow("dataGrid", "用户组信息列表", "sysGroup", true);
            });
            //配套getListShow使用，当oper为true时增加操作列
            function formatOper(value, row, index){
                var str= "<a href=\"#\" class=\"easyui-linkbutton l-btn l-btn-small\" " +
                    "onclick=\"getDetail('newWin','用户组','sysGroup/getById?groupId=" + row.groupId + "')\">查看</a>&nbsp;&nbsp;";
                str+="<a href=\"#\" class=\"easyui-linkbutton l-btn l-btn-small\" " +
                    "onclick=\"getShowUser(\'" + row.groupId + "\')\">已分配用户</a>";
                return str;
            };
            function getShowUser(groupId){
                // var url = basePath + 'sysUser/getShowUser?id=' + groupId + "&flag=group";
                var url = basePath + 'jsp/sys/SysUserShowInfo.jsp?id=' + groupId + "&showFlag=group";
                openWindow("newWin", "已分配用户信息", url);
            }
        </script>
    </head>
    <body class="easyui_layout">
        <form id="query_form" method="post">
            <div style="padding:2px 5px;"><!-- 查询条件容器 -->
                所属机构&nbsp;&nbsp;<input class="easyui-textbox" name="blngtoOrgNo" style="width:150px">
                用户组名称&nbsp;&nbsp;<input class="easyui-textbox" name="groupNm" style="width:150px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search"
                   onclick="Search('dataGrid','query_form','sysGroup/getByPage')">查询</a>
            </div>
        </form>
        <div style="padding:2px 5px;">
            <a href="#" onclick="add('newWin','用户组','sysGroup/input')"
               class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"></a>
            <a href="#" onclick="upd('dataGrid','newWin','用户组','sysGroup/getById','groupId')"
               class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"></a>
            <a href="#" onclick="del('dataGrid','sysGroup/delete','groupId')"
               class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"></a>
        </div>
        <!-- 数据列表展示区域-->
        <table id="dataGrid"></table>
        <!-- 弹出窗口-->
        <div id="newWin"></div>
    </body>
</html>