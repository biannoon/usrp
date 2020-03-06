<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <style>
        html, body{margin: 0; padding: 0;}
    </style>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/common/custom_win.css"/>
</head>
<body>
<div class="easyui-layout" style="width:100%; height:100%;">
    <div data-options="region:'north', border: false" style="height:140px;">
        <div class="query-wrap" style="height: 37px;">
            <div class="query-item">
                <span>数据源：</span>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                        id="sql-query-dataSource">
                </select>
            </div>
            <div class="query-item">
                <a id="table-interface-query-btn" href="javascript:void(0)" class="easyui-linkbutton" onclick="initDataGrid()"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
        <textarea class="easyui-validatebox" id="sql-text" style="width:100%;height:100px; vertical-align: middle; resize: none;"
                  placeholder="请输入SQL语句。注：后台封装SQL语句已使用的别名有 temp_table_name_1、temp_table_name_2、temp_row_number_1、temp_total_1。" ></textarea>
    </div>
    <div data-options="region:'center', border: false">
        <table id="sql-query-data-grid" ></table>
    </div>
</div>


<%-- jQeury --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>

<%-- Easy UI --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>

<%-- 自定义js --%>
<script src="<%=path%>/static/js/ajax.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/combobox.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/message.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/sqlQuery/sqlQuery.js" type="text/javascript"></script>

<script>
    var baseUrl = '<%=path%>';
    $(function () {
        initDatabase('#sql-query-dataSource', false);
    })
</script>
</body>
</html>
