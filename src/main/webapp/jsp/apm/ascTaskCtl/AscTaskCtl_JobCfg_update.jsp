<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/22
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.scrcu.apm.ascTaskCtl.entity.*" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    AscJobCfg job = (AscJobCfg) request.getAttribute("ascJobCfg");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ascTaskCtl/ascTaskCtl.js"></script>
</head>
<body>
<form id="form_update" method="post">
    <table width="100%" style="text-align: center" cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: small">处理名称：</span></td>
            <td align="left"><input class="easyui-validatebox" id="jobName_insert" name="jobName" value=""  data-options="required:true"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: small">处理序列：</span></td>
            <td align="left"><input class="easyui-validatebox" id="jobSeq_insert" name="jobSeq" value=""  data-options="required:true"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: small">处理类型：</span></td>
            <td align="left"><input class="easyui-validatebox" id="jobType_insert" name="jobType" value="" data-options="required:true,validType:'checkJobType',editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right" valign="top"><span style="font-size: small">处理参数：</span></td>
            <td align="left"><input class="easyui-validatebox" id="jobPara_insert" name="jobPara" value="" data-options="required:true,multiline:true"></td>
        </tr>
        <input id="jobId" name="jobId" type="hidden" value="<%=job.getJobId()%>">
        <input id="taskId_jobcfg" name="taskId" value="<%=job.getTaskId()%>" type="hidden">
        <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
    </table>
</form>
<div style="text-align:center">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true onclick="submitJobCfgForm('<%=basePath%>','AscTask/updateJobCfg','form_update')">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true onclick="resetForm('form_insert')">重置</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindowByAscTask('win_ascTask','dg_jobCfg')">关闭</a>
</div>
<script type="text/javascript">
    $(function () {
        //回显数据
        initTextboxByAscTask('jobName_insert','<%=job.getJobName()%>',10,false,'252','20');
        initTextboxByAscTask('jobSeq_insert','<%=job.getJobSeq()%>',40,false,'252','20');
        initTextboxByAscTask('jobPara_insert','<%=job.getJobPara()%>',2000,false,'252','100');
        initComboboxByAscTask('jobType_insert','ASC012','<%=job.getJobType()%>',false,'252','20');
    })
</script>
</body>
</html>

