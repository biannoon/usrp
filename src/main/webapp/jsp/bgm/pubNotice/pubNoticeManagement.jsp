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
        .pub-object-btn-wrap{height: 30px; line-height: 30px; text-align: center;}
        .pub-object-btn-wrap a{padding: 7px;}
    </style>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/common/custom_win.css"/>
</head>
<body>
<div class="easyui-layout" style="width:100%; height:100%;">
    <div data-options="region:'north', border: false" style="height:73px;">
        <div class="query-wrap">
            <div class="query-item">
                <span>公告名称：</span>
                <input class="easyui-textbox" style="width:200px" id="pub-notice-management-query-name">
            </div>
            <div class="query-item" style="width: 400px;">
                <span>发布日期：</span>
                <input class="easyui-datebox" id="pub-notice-management-query-start-time"
                       data-options="showSeconds:false" style="width:150px"> &nbsp;-&nbsp;
                <input class="easyui-datebox" id="pub-notice-management-query-end-time"
                       data-options="showSeconds:false" style="width:150px">
            </div>
            <div class="query-item">
                <span>公告状态：</span>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                        id="pub-notice-management-query-status">
                </select>
            </div>
            <div class="query-item">
                <span>发布对象：</span>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                        id="pub-notice-management-query-type">
                </select>
            </div>
            <div class="query-item">
                <a id="pub-notice-management-query-btn" href="javascript:void(0)" class="easyui-linkbutton"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
    </div>
    <div data-options="region:'center', border: false">
        <table id="pub-notice-management-data-grid" ></table>
    </div>
</div>
<%-- 系统公告编辑窗口 --%>
<div id="pub-notice-management-edit-win" class="easyui-window" title="系统公告" data-options="closed: true, width: 640, height: 290, collapsible: false,
        maximizable: false, minimizable: false, resizable: false, modal: true,iconCls:'icon-edit'" style="display: none;">
    <div>
        <form id="pub-notice-management-edit-form">
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">公告名称：</div>
                    <input name="noticeTitle" class="easyui-textbox" style="width:200px" id="pub-notice-management-edit-title"
                           data-options="required: true, missingMessage: '请输入公告名称。', validType:'length[1,100]'">
                </div>
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">到期日期：</div>
                    <input class="easyui-datebox" style="width:200px" name="matrDt" id="pub-notice-management-edit-matrDt"
                           data-options="editable: false, showSeconds:false,required: true, missingMessage: '请选择到期日期。'">
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">发布对象：</div>
                    <select class="easyui-combobox" style="width:200px;" name="trgtTyp"
                            data-options="editable: false, required: true, missingMessage: '请选择发布对象。', valueField: 'value', textField: 'text'"
                            id="pub-notice-management-edit-type"></select>
                </div>
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">具体对象：</div>
                    <a href="javascript:void(0)" id="pub-notice-management-target-btn">点击选择公告发布具体对象</a>
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">全部人可见：</div>
                    <select class="easyui-combobox" style="width:200px;" name="pub"
                            data-options="editable: false, required: true, missingMessage: '请选择。', valueField: 'value', textField: 'text'"
                            id="pub-notice-management-edit-pub"></select>
                </div>
            </div>
            <div style="margin-top: 5px; height: 90px;">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">公告类容：</div>
                    <textarea name="noticeContent" class="easyui-validatebox" id="pub-notice-management-edit-content"
                              data-options="required: true, missingMessage: '请填公告类容。', validType:'length[1,2000]'"
                              style="width:503px;height:90px; vertical-align: middle; resize: none;"></textarea>
                </div>
            </div>
            <input name="noticeId" type="hidden" id="pub-notice-management-edit-id"/>
        </form>
        <div style="margin-top: 10px; padding-right: 19px; text-align: right;">
            <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-redo" id="pub-notice-management-release-btn">发布</a>
            <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-save" id="pub-notice-management-save-btn">暂存</a>
            <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="pub-notice-management-cancel-btn">取消</a>
        </div>
    </div>
</div>
<%-- 系统公告编辑窗口 end --%>
<%-- 发布具体对象窗口 --%>
<div id="pub-object-win" class="easyui-window" title="发布具体对象" data-options="closed: true, width: 650, height: 400, collapsible: false,
        maximizable: false, minimizable: false, resizable: false, modal: true,iconCls:'icon-edit'" style="display: none;">
    <div class="easyui-layout" style="width:635px;height:360px;">
        <div data-options="region:'east',title:'待选区',collapsible:false" style="width:300px;">
            <div id="right-object-id-data-list" style="width:280px;height:318px;">
            </div>
        </div>
        <div data-options="region:'west',title:'已选取',collapsible:false" style="width:300px;">
            <div id="left-object-id-data-list" style="width:280px;height:318px;">
            </div>
        </div>
        <div data-options="region:'center'">
            <div style="display: inline-block; width: 30px;height:120px; padding: 120px 0;">
                <div class="pub-object-btn-wrap">
                    <a id="pub-object-id-to-right-all" href="javascript:void(0)" title="删除全部">&gt;&gt;</a>
                </div>
                <div class="pub-object-btn-wrap">
                    <a id="pub-object-id-to-right" href="javascript:void(0)" title="删除">&gt;</a>
                </div>
                <div class="pub-object-btn-wrap">
                    <a id="pub-object-id-to-left" href="javascript:void(0)" title="新增">&lt;</a>
                </div>
                <div class="pub-object-btn-wrap">
                    <a id="pub-object-id-to-left-all" href="javascript:void(0)" title="新增全部">&lt;&lt;</a>
                </div>
            </div>
        </div>
    </div>
<%-- 发布具体对象窗口 end --%>

<%-- jQeury --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>

<%-- Easy UI --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>

<%-- 自定义js --%>
<script src="<%=path%>/static/js/pubNotice/pubNoticeManagement.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/ajax.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/combobox.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/message.js" type="text/javascript"></script>
<script>
    var baseUrl = '<%=path%>';
    $(function () {
        new pubNoticeManagement(baseUrl).init();
    })
</script>
</body>
</html>
