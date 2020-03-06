<%@ page import="com.scrcu.common.utils.EhcacheUtil" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
<form id="form_insert" method="post">
    <table width="100%" style="text-align: center" cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: small">任务编号：</span></td>
            <td align="left"><input type="text" class="easyui-validatebox" id="taskId_insert" name="taskId" value="" data-options="required:true,validType:'uniqueTaskId'"></td>
            <td align="right"><span style="font-size: small">任务名称：</span></td>
            <td align="left"><input class="easyui-validatebox" id="taskName_insert" name="taskName" value="" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: small">任务类型：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="taskType_insert" name="taskType" value="" data-options="required:true,editable:false,panelHeight:'auto'">
                <%--<input class="easyui-validatebox" id="taskType_insert" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                <input class="easyui-textbox" type="hidden" id="taskType_hidden" name="taskType" value=""/>--%>
            </td>
            <td align="right"><span style="font-size: small">任务种类：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="taskCategory_insert" name="taskCategory" value="" data-options="required:true,editable:false,panelHeight:'auto'">
                <%--<input class="easyui-textbox easyui-validatebox" id="taskCategory_insert" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                <input class="easyui-textbox" type="hidden" id="taskCategory_hidden" name="taskCategory"  value=""/>--%>
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: small">执行频率：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="frequency_insert" value="" data-options="required:true,editable:false" buttonIcon="icon-search">
                <input class="easyui-textbox" type="hidden" id="frequency_hidden" name="frequency"  value=""/>
            </td>
            <td align="right"><span style="font-size: small">任务状态：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="state_insert" value="<%=EhcacheUtil.getSingleSysDictryCdByCache("ASC001008","ASC001").getDictryNm()%>" style="width: 200px;height: 20px" disabled="disabled" buttonIcon="icon-search">
                <input class="easyui-textbox" type="hidden" id="state_hidden" name="state"  value="ASC001008"/>
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: small">待处理日期：</span></td>
            <td align="left"><input class="easyui-validatebox" id="nextDate_insert" name="nextDate" value="" data-options="required:true,validType:'checkDate'"></td>
            <td align="right"><span style="font-size: small">开始时间：</span></td>
            <td align="left"><input class="easyui-validatebox" id="startTime_insert" name="startTime" value="" data-options="required:true,validType:'limitTime'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: small">结束时间：</span></td>
            <td align="left"><input class="easyui-validatebox" id="endTime_insert" name="endTime" value="" data-options="required:true,validType:['limitTime','checkTime']"></td>
            <td align="right"><span style="font-size: small">时间段内是否允许执行标志：</span></td>
            <td align="left"><input class="easyui-validatebox" id="timeFlag_insert" name="timeFlag" value="" data-options="required:true,editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: small">优先级：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="priority_insert" value="" name="priority" data-options="required:true,editable:false,panelHeight:'auto'">
                <%--<input class="easyui-textbox easyui-validatebox" id="priority_insert" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                <input class="easyui-textbox" type="hidden" id="priority_hidden" name="priority"  value=""/>--%>
            </td>
            <td align="right"><span style="font-size: small">所属任务组：</span></td>
            <td align="left">
                <input class="easyui-textbox easyui-validatebox" id="groupId_insert" value="" style="width: 200px;height: 20px;" data-options="required:true,editable:false" buttonIcon="icon-search">
                <input class="easyui-textbox" type="hidden" id="groupId_hidden" name="groupId"  value=""/>
            </td>
        </tr>
        <tr>
            <td align="right" valign="top"><span style="font-size: small">任务描述：</span></td>
            <td align="left" colspan="4"><input class="easyui-validatebox" id="describ_insert" name="describ" multiline=true data-options="required:false" ></td>
        </tr>
        <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
    </table>
</form>
<div style="text-align:center">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true onclick="submitAscTaskForm('<%=basePath%>','AscTask/insertAscTask','form_insert','insert')">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true onclick="resetForm('form_insert')">重置</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeAscTaskWindow('win_ascTask')">关闭</a>
</div>
<div id="win_ascTask_insert"></div>
<script type="text/javascript">
    $(function () {
        initAscTaskInsertPage();
    })
</script>
</body>
</html>
