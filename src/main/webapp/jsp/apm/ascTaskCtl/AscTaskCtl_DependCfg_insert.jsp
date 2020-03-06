<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/22
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String taskId = (String) request.getAttribute("taskId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body>
    <form id="form_insert" method="post">
        <table width="100%" style="text-align: center" cellpadding="5">
            <tr>
                <td align="right"><span style="font-size: small">任务编号：</span></td>
                <td align="left"><input class="easyui-validatebox" id="taskId_insert" name="taskId" value="" data-options="required:true"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">依赖任务编号：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="dependId_insert" name="dependId" value="" data-options="required:true,validType:['checkDependId','uniqueDependId']" buttonIcon="icon-search">
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">依赖对象类型：</span></td>
                <td align="left"><input class="easyui-validatebox" id="flag_insert" name="flag" value="" data-options="required:true,editable:false,panelHeight:'auto'"></td>
            </tr>
            <tr>
                <td align="right" valign="top"><span style="font-size: small">任务描述：</span></td>
                <td align="left">
                    <textarea class="easyui-validatebox" id="describ_insert" name="describ" data-options="required:false" multiline=true></textarea>
                </td>
            </tr>
            <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
        </table>
    </form>
    <div id="win_task_search"></div>
    <div style="text-align:center">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true onclick="submitDependCfgForm('<%=basePath%>','AscTask/insertDependCfg','form_insert')">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true onclick="resetForm('form_insert')">重置</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindowByAscTask('win_ascTask','dg_dependCfg')">关闭</a>
    </div>
<script type="text/javascript">
    $(function () {
        initAscTaskDependCfgInsertPage('<%=taskId%>');
    })
</script>
</body>
</html>
