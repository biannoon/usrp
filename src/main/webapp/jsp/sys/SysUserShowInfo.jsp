<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
    </head>
    <body class="easyui_layout">
        <div id="panel" class="easyui-panel" style="padding:1px;">
            <div id="userShow" style="width: 100%; height: 100%"><!--数据网格-->
                <form id="userShow_form" method="post">
                    <input type="hidden" name="id" value="<%=request.getParameter("id")%>">
                    <input type="hidden" name="showFlag" value="<%=request.getParameter("showFlag")%>">
                    <div style="padding:10px 1px;"><!-- 查询条件容器 -->
                        用户名称&nbsp;&nbsp;<input class="easyui-textbox" name="userNm" style="width:150px">
                        用户状态&nbsp;&nbsp;<dh:select dictno="SYS05" name="stus" value=""></dh:select>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-search"
                           onclick="Search('sysUserDg','userShow_form','sysUser/getShowUser')">查询</a>
                    </div>
                </form>
                <div style="padding:10px 1px; width: 100%">
                    <table id="sysUserDg" class="easyui-datagrid">
                        <thead>
                        <tr>
                            <th data-options="field:'userId',width:100,align:'center'">用户编号</th>
                            <th data-options="field:'userNm',width:100,align:'center'">用户名称</th>
                            <th data-options="field:'blngtoOrgNo',width:100,align:'center'">所属机构</th>
                            <th data-options="field:'telNo',width:100,align:'center'">联系电话</th>
                            <th data-options="field:'stus',width:120,align:'center'">用户状态</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $(function(){
                var id = $("input[name='id']").val();
                var showFlag = $("input[name='showFlag']").val();
                $('#sysUserDg').datagrid({
                    url: basePath + 'sysUser/getShowUser?id='+id+'&showFlag='+showFlag,
                    title: '已分配用户列表',
                    fitColumns:true,
                    singleSelect:true,
                    pagination:true,
                    pageNumber:'1',
                    pageSize:'10'
                });
            });
        </script>
    </body>
</html>