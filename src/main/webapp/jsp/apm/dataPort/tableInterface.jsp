<%--
  User: WY
  Date: 2019/9/17
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
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
    <div class="query-wrap">
        <div class="query-item">
            <span>接口中文名称：</span>
            <input class="easyui-textbox" style="width:200px" id="table-interface-query-cn-name">
        </div>
        <div class="query-item">
            <span>接口英文名称：</span>
            <input class="easyui-textbox" style="width:200px" id="table-interface-query-en-name">
        </div>
        <div class="query-item">
            <span>数据源：</span>
            <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '200px'"
                    id="table-interface-query-dataSource">
            </select>
        </div>
        <div class="query-item">
            <span>接口状态选择：</span>
            <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                    id="table-interface-query-status">
            </select>
        </div>
        <div class="query-item">
            <span>所属业务分类：</span>
            <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                    id="table-interface-query-bizClsf">
            </select>
        </div>
        <div class="query-item">
            <a id="table-interface-query-btn" href="javascript:void(0)" class="easyui-linkbutton" onclick="tableInterface.query()"
               data-options="iconCls:'icon-search'">查询</a>
        </div>
    </div>
    <table id="table-interface-data-grid" ></table>
</div>
<%--历史数据表格窗口--%>
<div id="table-interface-history-win" class="easyui-window" title="数据接口历史版本数据"
     data-options="closed: true, width: 1000, height: 500, collapsible: false, maximizable: false, minimizable: false, resizable: false,iconCls:'icon-tip'"
     style="display: none;">
    <table id="table-interface-history-data-grid" ></table>
</div>
<%--历史数据表格窗口 end --%>
<%--新增数据接口窗口--%>
<div id="table-interface-add-win" class="easyui-window" title="新增数据接口" data-options="closed: true, iconCls:'icon-add'" style="display: none;">
    <form id="table-interface-add-form" method="post">
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-long-label">英文名称：</div>
                <input name="enName" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入英文名称。',
                    validType:['enName', 'length[1,50]']" id="table-interface-add-en-name">
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">中文名称：</div>
                <input name="cnName" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,100]']" id="table-interface-add-cn-name">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">数据源：</div>
                <select class="easyui-combobox" name="dataSorceId" id="table-interface-add-dataSource"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择一个数据源。', valueField: 'value', textField: 'text', panelHeight: '200px'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">接口分类：</div>
                <select class="easyui-combobox" name="tableType"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择具体接口分类。', valueField: 'value', textField: 'text'"
                        id="table-interface-add-type">
                </select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">所属业务分类：</div>
                <select class="easyui-combobox" name="bizClsf"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择数据接口所属业务分类。', valueField: 'value', textField: 'text'"
                        id="table-interface-add-bizClsf">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">校验结果存放表名：</div>
                <input name="ruleRsTablNm" class="easyui-textbox" style="width:200px" data-options="validType:['enName', 'length[1,60]'], prompt:'该表名与当前接口为同一数据源'"
                       id="table-interface-add-ruleRsTablNm">
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">接口描述：</div>
                <textarea name="tableComment" class="easyui-validatebox" id="table-interface-add-table-comment"
                          data-options="required: true, missingMessage: '请填写接口描述。', validType:'length[1,500]'"
                          style="width:553px;height:60px; vertical-align: middle; resize: none;"></textarea>
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" onclick="tableInterface.save()" class="easyui-linkbutton" icon="icon-ok">保存</a>
        <a href="javascript: void(0)" onclick="tableInterface.closeWin('#table-interface-add-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<%-- 新增数据接口窗口 end --%>

<%--更新数据接口窗口--%>
<div id="table-interface-update-win" class="easyui-window" title="更新数据接口" data-options="closed: true, iconCls:'icon-edit'" style="display: none;">
    <form id="table-interface-update-form" method="post">
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-long-label">英文名称：</div>
                <input name="enName" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入英文名称。',
                    validType:['enName', 'length[1,50]']" id="table-interface-update-en-name">
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-long-label">中文名称：</div>
                <input name="cnName" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,100]']" id="table-interface-update-cn-name">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">数据源：</div>
                <select class="easyui-combobox" name="dataSorceId" id="table-interface-update-dataSource"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择一个数据源。', valueField: 'value', textField: 'text', panelHeight: '200px'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">所属业务分类：</div>
                <select class="easyui-combobox" name="bizClsf"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择数据接口所属业务分类。', valueField: 'value', textField: 'text'"
                        id="table-interface-update-bizClsf">
                </select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-long-label">校验结果存放表名：</div>
                    <input name="ruleRsTablNm" class="easyui-textbox" style="width:200px" data-options="validType:['enName', 'length[1,60]'], prompt:'该表名与当前接口为同一数据源'"
                           id="table-interface-edit-ruleRsTablNm">
                </div>
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">接口描述：</div>
                <textarea name="tableComment" class="easyui-validatebox" id="table-interface-update-table-comment"
                          data-options="required: true, missingMessage: '请填写接口描述。', validType:'length[1,500]'"
                          style="width:553px;height:60px; vertical-align: middle; resize: none;"></textarea>
            </div>
        </div>
        <input id="table-interface-update-id" name="tableId" type="hidden"/>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" onclick="tableInterface.update()" class="easyui-linkbutton" icon="icon-ok">保存</a>
        <a href="javascript: void(0)" onclick="tableInterface.closeWin('#table-interface-update-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<%-- 更新数据接口窗口 end --%>
<%-- 新增数据接口字段信息窗口--%>
<div id="table-field-add-win" class="easyui-window" title="新增字段" data-options="closed: true, iconCls:'icon-add'" style="display: none;">
    <form id="table-field-add-form" method="post">
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">英文名称：</div>
                <input name="fieldEnNm" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入英文名称。',
                    validType:['enName', 'length[1,50]']" id="table-field-add-en-name">
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">中文名称：</div>
                <input name="fieldCnNm" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,100]']" id="table-field-add-cn-name">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">数据类型：</div>
                <select class="easyui-combobox" name="dataTyp" id="table-field-add-type"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择。'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">数据长度：</div>
                <input name="dataLength" class="easyui-numberbox" style="width:200px" data-options="min:0"
                       id="table-field-add-length">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">引用代码值：</div>
                <input name="dictryCd" class="easyui-textbox" style="width:200px" data-options="validType:'length[1,20]'" id="table-field-add-dictryCd">
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">排序号：</div>
                <input name="sortNo" class="easyui-numberbox" style="width:200px" data-options="required: true, missingMessage: '请输入排序号。', min:0"
                       id="table-field-add-sort-number">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">是否唯一索引：</div>
                <select class="easyui-combobox" name="uniqIndxFlg" id="table-field-add-uniqIndxFlg"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择。'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">是否机构权限控制字段：</div>
                <select class="easyui-combobox" name="prvlgCtrlFlag" id="table-field-add-prvlgCtrlFlag"
                        style="width:150px;" data-options="editable: false, required: true, missingMessage: '请选择。'">
                </select>
            </div>
        </div>
        <input name="tablId" id="table-field-add-interfaceId" type="hidden"/>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" onclick="tableFieldOpt.save()" class="easyui-linkbutton" icon="icon-ok">保存</a>
        <a href="javascript: void(0)" onclick="tableFieldOpt.closeWin('#table-field-add-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<%-- 新增数据接口字段信息窗口 end--%>
<%-- 更新数据接口字段信息窗口 --%>
<div id="table-field-update-win" class="easyui-window" title="更新字段" data-options="closed: true, iconCls:'icon-edit'" style="display: none;">
    <form id="table-field-update-form" method="post">
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">英文名称：</div>
                <input name="fieldEnNm" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入英文名称。',
                    validType:['enName', 'length[1,50]']" id="table-field-update-en-name">
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">中文名称：</div>
                <input name="fieldCnNm" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,100]']" id="table-field-update-cn-name">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">数据类型：</div>
                <select class="easyui-combobox" name="dataTyp" id="table-field-update-type"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择。'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">数据长度：</div>
                <input name="dataLength" class="easyui-numberbox" style="width:200px" data-options="min:0"
                       id="table-field-update-length">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">引用代码值：</div>
                <input name="dictryCd" class="easyui-textbox" style="width:200px" data-options="validType:'length[1,20]'" id="table-field-update-dictryCd">
            </div>
            <div class="edit-win-div-row-column">
                <div  class="edit-win-div-row-column-label">排序号：</div>
                <input name="sortNo" class="easyui-numberbox" style="width:200px" data-options="required: true, missingMessage: '请输入排序号。', min:0"
                       id="table-field-update-sort-number">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">是否唯一索引：</div>
                <select class="easyui-combobox" name="uniqIndxFlg" id="table-field-update-uniqIndxFlg"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择。'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">是否机构权限控制字段：</div>
                <select class="easyui-combobox" name="prvlgCtrlFlag" id="table-field-update-prvlgCtrlFlag"
                        style="width:150px;" data-options="editable: false, required: true, missingMessage: '请选择。'">
                </select>
            </div>
        </div>
        <input name="tablId" id="table-field-update-interfaceId" type="hidden"/>
        <input name="fieldId" id="table-field-update-fieldId" type="hidden"/>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" onclick="tableFieldOpt.update()" class="easyui-linkbutton" icon="icon-ok">保存</a>
        <a href="javascript: void(0)" onclick="tableFieldOpt.closeWin('#table-field-update-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<%-- 更新数据接口字段信息窗口 end--%>

<%--字段数据网格--%>
<div id="table-field-data-grid-win" class="easyui-window" title="数据接口字段信息"
     data-options="closed: true, width: 1000, height: 500, collapsible: false, maximizable: false, minimizable: false, resizable: false,iconCls:'icon-tip'"
     style="display: none;">
    <table id="table-field-data-grid" ></table>
</div>
<%--字段数据网格--%>
<div id="table-his-field-data-grid-win" class="easyui-window" title="数据接口字段信息"
     data-options="closed: true, width: 1000, height: 500, collapsible: false, maximizable: false, minimizable: false, resizable: false,iconCls:'icon-tip'"
     style="display: none;">
    <table id="table-his-field-data-grid" ></table>
</div>
<%--字段数据网格 end--%>
<%-- 上传字段数据excel文件框 --%>
<div id="table-field-import-win" class="easyui-window" title="导入字段" data-options="closed: true, iconCls:'icon-redo'" style="display: none;">
    <div style="margin: 5px; height: 30px;">
        <form id="table-field-import-form" method="post" enctype="multipart/form-data">
            <input class="easyui-filebox" style="width:300px" data-options=" buttonText: '选择文件', buttonAlign: 'right',
            editable: false, required: true, missingMessage: '请选择一个excel文件。',
             accept:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel' "
                   name="excelFile" id="table-field-import-file">
            <input type="hidden" name="interfaceId" id="table-field-import-interfaceId">
        </form>
    </div>
    <div style="margin-top: 10px; padding-right: 5px; text-align: right;">
        <a href="javascript: void(0)" onclick="tableFieldOpt.importTableField()" class="easyui-linkbutton" icon="icon-ok">上传</a>
        <a href="javascript: void(0)" onclick="tableFieldOpt.closeWin('#table-field-import-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<%-- 上传字段数据excel文件框 end --%>

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
<script src="<%=path%>/static/js/commonUtils.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/combobox.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/message.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/dataPort/tableInterface.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/dataPort/tableFieldOpt.js" type="text/javascript"></script>
<script>
    $(function () {
        tableInterface.init();
    })
</script>
</body>
</html>
