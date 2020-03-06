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
    <div data-options="region:'north', border: false" style="height:37px;">
        <div class="query-wrap" style="height: 37px;">
            <div class="query-item">
                <span>公告名称：</span>
                <input class="easyui-textbox" style="width:200px" id="pub-notice-query-name">
            </div>
            <div class="query-item" style="width: 400px;">
                <span>发布日期：</span>
                <input class="easyui-datebox" id="pub-notice-query-start-time"
                       data-options="showSeconds:false" style="width:150px"> &nbsp;-&nbsp;
                <input class="easyui-datebox" id="pub-notice-query-end-time"
                       data-options="showSeconds:false" style="width:150px">
            </div>
            <div class="query-item" style="width: 100px;">
                <a id="pub-notice-query-btn" href="javascript:void(0)" class="easyui-linkbutton"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center', border: false">
        <table id="pub-notice-data-grid" ></table>
    </div>
</div>


<%-- jQeury --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>

<%-- Easy UI --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>

<%-- 自定义js --%>
<script src="<%=path%>/static/js/pubNotice/pubNotice.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/ajax.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/combobox.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/message.js" type="text/javascript"></script>
<script>
    var baseUrl = '<%=path%>';
    $(function () {
        new pubNotice(baseUrl).init();
    })
</script>
</body>
</html>
