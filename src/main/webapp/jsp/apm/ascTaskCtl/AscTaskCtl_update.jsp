<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.scrcu.apm.ascTaskCtl.entity.*" %>
<%@ page import="com.scrcu.common.utils.EhcacheUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    AscTaskCtl task = (AscTaskCtl) request.getAttribute("ascTask");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body class="easyui-layout">
<div data-options="region:'center'" style="padding:5px;background:#eee;">
    <form id="form_update" method="post">
        <table width="100%" style="text-align: center" cellpadding="5">
            <tr>
                <td align="right"><span style="font-size: small">任务编号：</span></td>
                <td align="left"><input type="text" class="easyui-validatebox" id="taskId_insert" name="taskId" data-options=""></td>
                <td align="right"><span style="font-size: small">任务名称：</span></td>
                <td align="left"><input class="easyui-validatebox" id="taskName_insert" name="taskName" data-options="required:true,validType:''"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">任务类型：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="taskType_insert" name="taskType" value="" data-options="required:true,editable:false,panelHeight:'auto'">
                    <%--<input class="easyui-textbox easyui-validatebox" id="taskType_insert" value="<%=EhcacheUtil.getSingleSysDictryCdByCache(task.getTaskType(),"ASC010").getDictryNm()%>" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                    <input class="easyui-textbox" type="hidden" id="taskType_hidden" name="taskType" value="<%=task.getTaskType()%>"/>--%>
                </td>
                <td align="right"><span style="font-size: small">任务种类：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="taskCategory_insert" name="taskCategory" value="" data-options="required:true,editable:false,panelHeight:'auto'">
                    <%--<input class="easyui-textbox easyui-validatebox" id="taskCategory_insert" value="<%=EhcacheUtil.getSingleSysDictryCdByCache(task.getTaskCategory(),"ASC003").getDictryNm()%>" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                    <input class="easyui-textbox" type="hidden" id="taskCategory_hidden" name="taskCategory"  value="<%=task.getTaskCategory()%>"/>--%>
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">执行频率：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="frequency_insert" value="" data-options="required:true,editable:false" buttonIcon="icon-search">
                    <input class="easyui-textbox" type="hidden" id="frequency_hidden" name="frequency"  value="<%=task.getFrequency()%>"/>
                </td>
                <td align="right"><span style="font-size: small">任务状态：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="state_insert" data-options="required:true,editable:false" buttonIcon="icon-search">
                    <input class="easyui-textbox" type="hidden" id="state_hidden" name="state"  value="<%=task.getState()%>"/>
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">待处理日期：</span></td>
                <td align="left"><input class="easyui-validatebox" id="nextDate_insert" name="nextDate" data-options="required:true,validType:''"></td>
                <td align="right"><span style="font-size: small">开始时间：</span></td>
                <td align="left"><input class="easyui-validatebox" id="startTime_insert" name="startTime" data-options="required:true,validType:'limitTime'"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">结束时间：</span></td>
                <td align="left"><input class="easyui-validatebox" id="endTime_insert" name="endTime" data-options="required:true,validType:['limitTime','checkTime']"></td>
                <td align="right"><span style="font-size: small">时间段内是否允许执行标志：</span></td>
                <td align="left"><input class="easyui-validatebox" id="timeFlag_insert" name="timeFlag" data-options="required:true,editable:false,panelHeight:'auto'"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">优先级：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="priority_insert" value="" name="priority" data-options="required:true,editable:false,panelHeight:'auto'">
                    <%--<input class="easyui-textbox easyui-validatebox" id="priority_insert" value="<%=EhcacheUtil.getSingleSysDictryCdByCache(task.getPriority(),"ASC011").getDictryNm()%>" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                    <input class="easyui-textbox" type="hidden" id="priority_hidden" name="priority"  value="<%=task.getPriority()%>"/>--%>
                </td>
                <td align="right"><span style="font-size: small">所属任务组编号：</span></td>
                <td align="left">
                    <input class="easyui-textbox easyui-validatebox" id="groupId_insert" value="" style="width: 200px;height: 20px;" data-options="required:true,editable:false" buttonIcon="icon-search">
                    <input class="easyui-textbox" type="hidden" id="groupId_hidden" name="groupId"  value="<%=task.getGroupId()%>"/>
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
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true onclick="submitAscTaskForm('<%=basePath%>','AscTask/updateAscTask','form_update','update')">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true onclick="resetForm('form_update')">重置</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeAscTaskWindow('win_ascTask')">返回</a>
    </div>
    <div id="win_ascTask_insert"></div>
</div>
<script type="text/javascript">
    $(function () {

        initAscTaskUpdatePage();
        //页面赋值
        initTextboxByAscTask('taskId_insert','<%=task.getTaskId()%>',32,true,'200','20');
        initTextboxByAscTask('taskName_insert','<%=task.getTaskName()%>',100,false,'200','20');
        initTextboxByAscTask('startTime_insert','<%=task.getStartTime()%>',0,false,'200','20');
        initTextboxByAscTask('endTime_insert','<%=task.getEndTime()%>',0,false,'200','20');
        initTextboxByAscTask('describ_insert','<%=task.getDescrib()%>',255,false,'600','100');
        initTextboxByAscTask('frequency_insert','<%=EhcacheUtil.getSingleSysDictryCdByCache(task.getFrequency(),"ASC002").getDictryNm()%>',0,false,'200','20');
        initTextboxByAscTask('state_insert','<%=EhcacheUtil.getSingleSysDictryCdByCache(task.getState(),"ASC001").getDictryNm()%>',0,false,'200','20');

        initComboboxByAscTask('taskType_insert','ASC010','<%=task.getTaskType()%>',false,'200','20');
        initComboboxByAscTask('taskCategory_insert','ASC003','<%=task.getTaskCategory()%>',false,'200','20');
        initComboboxByAscTask('priority_insert','ASC011','<%=task.getPriority()%>',false,'200','20');
        initComboboxByAscTask('timeFlag_insert','SYS02','<%=task.getTimeFlag()%>',false,'200','20');
        initDateBoxByAscTask('nextDate_insert','<%=new SimpleDateFormat("yyyy-MM-dd").format(task.getNextDate())%>','200','20');

        //任务组ID与任务组名称转换
        var groupName = exchangeAscGroupName('<%=task.getGroupId()%>');
        $('#groupId_insert').textbox({
            value:groupName
        })
    })
</script>
</body>
</html>
