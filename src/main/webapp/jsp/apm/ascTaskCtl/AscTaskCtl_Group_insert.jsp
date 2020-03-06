<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/20
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String groupId = (String) request.getAttribute("groupId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ascTaskCtl/ascGroup.js"></script>
</head>
<body style="padding:5px;background:#eee;">
    <form id="form_group_insert" method="post">
        <table width="100%" style="text-align: center" cellpadding="5">
            <tr>
                <td align="right"><span style="font-size: small">任务组编号：</span></td>
                <td align="left"><input class="easyui-validatebox" id="groupId" name="groupId" value="" data-options="required:true,validType:'uniqueGroupId'"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">任务组名称：</span></td>
                <td align="left"><input class="easyui-validatebox" id="groupName" name="groupName" value="" data-options="required:true"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">上级任务组：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" type="text" id="parentGroup" value="" data-options="required:true" buttonIcon="icon-search">
                    <input class="easyui-textbox" type="hidden" id="parentGroup_hidden" name="parentGroup"  value=""/>
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">待处理时间：</span></td>
                <td align="left"><input class="easyui-validatebox" id="nextDate" name="nextDate" value="" data-options="required:true"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">任务组状态：</span></td>
                <td align="left"><input class="easyui-validatebox" id="state" name="state" value="" data-options="required:true"></td>
            </tr>
            <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
        </table>
    </form>
    <div style="text-align:center">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true onclick="submitForm('form_group_insert','<%=basePath%>','AscTask/insertAscGroup')">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true onclick="resetForm('form_group_insert')">重置</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindowBySelf('win_ascGroup','<%=basePath%>AscTask/ListAllTaskGroupByTreeGrid')">关闭</a>
    </div>
    <div id="win_ascGroup_insert"></div>
<%--</div>--%>
<script type="text/javascript">

    $(function () {
        initAscGroupInsertPage('<%=groupId%>');
    })
</script>
</body>
</html>