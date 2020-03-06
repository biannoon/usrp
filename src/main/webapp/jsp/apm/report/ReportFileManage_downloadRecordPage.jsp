<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/19
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/tld.jsp"%>
<%
    String id = (String)request.getAttribute("id");
%>
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
<div>
    <table id="download_record_table" width="100%"></table>
</div>
<script type="text/javascript">
    $(function () {
        initDownloadRecordTable('download_record_table','<%=id%>');
    })
</script>
</body>
</html>
