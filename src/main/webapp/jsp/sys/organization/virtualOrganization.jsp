<%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2019/10/10
  Time: 17:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>虚拟机构</title>
    <base href="<%=basePath%>">
    <title>Title</title>
    <style>
        html, body{margin: 0; padding: 0;}
    </style>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/common/custom_win.css"/>
</head>
<body>
<div>
    <div class="query-low-wrap">
        <div class="query-item">
            <span>机构名称：</span>
            <input class="easyui-textbox" style="width:200px" id="virtual-organization-query-name">
        </div>
        <div class="query-item">
            <span>机构编码：</span>
            <input class="easyui-textbox" style="width:200px" id="virtual-organization-query-code">
        </div>
        <div class="query-item-button">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="virtualOrganization.queryByKeyword()"
               data-options="iconCls:'icon-search'">查询</a>
        </div>
    </div>
    <table id="virtual-organization-data-grid" ></table>
</div>
<%--虚拟机构编辑窗口--%>
<div id="virtual-organization-edit-win" class="easyui-window" title="虚拟机构" data-options="closed: true,width: 640, height: 160, collapsible: false,
        maximizable: false, minimizable: false, resizable: false, modal: true" style="display: none;">
    <form id="virtual-organization-edit-form" method="post">
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">机构ID：</div>
                <input name="id" class="easyui-textbox" style="width:200px" data-options="required: true,prompt: '由大写英文字母V开头',
                missingMessage: '请输入机构ID。英文字符或数字组成。',
                    validType:'length[1,20]'" id="virtual-organization-edit-id">
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">机构名称：</div>
                <input name="name" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入机构名称。',
                    validType:'length[1,150]'" id="virtual-organization-edit-name">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">机构简称：</div>
                <input name="abbreviation" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入机构简称。',
                    validType:'length[1,50]'" id="virtual-organization-edit-abbreviation">
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">机构编码：</div>
                <input name="code" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入机构编码。',
                    validType:'length[1,4]'" id="virtual-organization-edit-code">
            </div>
        </div>
    </form>
    <div class="edit-win-div-row-button">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="virtual-organization-edit-btn">保存</a>
        <a href="javascript: void(0)" onclick="virtualOrganization.closeWin('#virtual-organization-edit-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<%--虚拟机构编辑窗口 end--%>

<%-- jQeury --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>

<%-- Easy UI --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
<%-- 自定义js --%>
<script>
    var baseUrl = '<%=path%>';
</script>
<script src="<%=path%>/static/js/ajax.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/organization/virtualOrganization.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/message.js" type="text/javascript"></script>
<script>
    $(function () {
        virtualOrganization.organizationDataGrid();
    })
</script>
</body>
</html>
