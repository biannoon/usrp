<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/16
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>统一监管报送</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/report/reportFileManage.js"></script>
</head>
<body>
    <div id="manage_records_table_tool">
        <table width="100%">
            <tr>
                <td align="right"><span style="font-size: 12px">批次号：</span></td>
                <td align="left"><input id="crtBatchId_se"></td>
                <td align="right"><span style="font-size: 12px">报文生成日期（起）：</span></td>
                <td align="left"><input id="fileCrtTm_start_se"></td>
                <td align="right"><span style="font-size: 12px">报文生成日期（止）：</span></td>
                <td align="left"><input id="fileCrtTm_end_Se"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: 12px">报文上报方式：</span></td>
                <td align="left"><input id="rptSubmitTyp_se"></td>
                <td align="right"><span style="font-size: 12px">上报日期（起）：</span></td>
                <td align="left"><input id="lstSubmitTm_start_se"></td>
                <td align="right"><span style="font-size: 12px">上报日期（止）：</span></td>
                <td align="left"><input id="lstSubmitTm_end_se"></td>
            </tr>
            <tr>
                <td colspan="6" align="right">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="searchFileRecord()">查询</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="resetSearchFileRecord()">重置</a>
                </td>
            </tr>
        </table>
    </div>
    <table id="manage_records_table" width="98%"></table>
    <div id="win_recordPage"></div>
<script type="text/javascript">
    $(function () {
        var flag = $('#flag_hidden').val();
        if (flag == 'now'){
            initReportFileRecordsPage('manage_records_table');
        } else if (flag == 'hist'){
            initReportFileHistRecordsPage('manage_records_table');
        }
    })
</script>
</body>
</html>
