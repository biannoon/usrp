<%--
报送主体信息配置页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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
                <span>报送主体ID：</span>
                <input class="easyui-textbox" style="width:200px" id="query-subject-id">
            </div>
            <div class="query-item">
                <span>报送主体名称：</span>
                <input class="easyui-textbox" style="width:200px" id="query-subject-name">
            </div>
            <div class="query-item">
                <span>发布状态：</span>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '200px'"
                        id="query-subject-status">
                </select>
            </div>
            <div class="query-item">
                <span>所属报送子系统：</span>
                <select class="easyui-combogrid" style="width:200px;" id="query-subsystem-id">
                </select>
            </div>
            <div class="query-item">
                <a id="query-btn" href="javascript:void(0)" class="easyui-linkbutton" onclick="reportSubjectOpt.query()"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
        <table id="report-subject-data-grid"></table>
    </div>
    <%-- 报送主体信息编辑窗 start --%>
    <div id="report-subject-edit-win" class="easyui-window" title="报送主体" data-options="closed: true, iconCls:'icon-add'" style="display: none;">
        <form id="report-subject-edit-form" method="post">
            <input id="edit-subject-id" name="subjectId" type="hidden"/>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">中文名称：</div>
                    <input name="subjectName" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,50]']" id="edit-subject-name">
                </div>
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">所属子系统：</div>
                    <select class="easyui-combogrid" name="subsystemName" style="width:200px;" id="edit-subsystem-name-id"
                            data-options="editable: false, required: true, missingMessage: '请选择所属子系统。'">
                    </select>
                    <input id="edit-subsystem-id" name="subsystemId" type="hidden"/>
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">分文件夹存放：</div>
                    <select class="easyui-combobox" name="folderFlag"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否分文件夹存放。'"
                            id="edit-folder-flag">
                    </select>
                </div>
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">单一类型报文：</div>
                    <select class="easyui-combobox" name="singleTypeFileFlag"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选是否单一类型报文。'"
                            id="edit-single-type-file-flag">
                    </select>
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">压缩方式：</div>
                    <select class="easyui-num" name="zipManner" id="edit-zip-manner"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择报送类型。'">
                    </select>
                </div>
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">压缩包大小限制：</div>
                    <input name="zipMaxSize" class="easyui-numberbox" style="width:200px" data-options="min:0, prompt: '单位：M', value:0, readonly:true"
                           id="edit-zip-max-size">
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">报送方类型：</div>
                    <select class="easyui-combobox" name="reportType" id="edit-report-type"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择报送方类型。'">
                    </select>
                </div>
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">关联SQL：</div>
                    <select id="edit-report-relation-sql" name="relationSqlId" class="easyui-combotree" style="width:200px;"></select>
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">上报方式：</div>
                    <select class="easyui-combobox" name="rptSubmitType" id="edit-report-submit-type"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择上报方式。'">
                    </select>
                </div>
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">是否允许下载</div>
                    <select class="easyui-combobox" name="downloadFlag" id="edit-report-download-flag"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否允许下载。'">
                    </select>
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">上报接口类型：</div>
                    <select class="easyui-combobox" name="rptInterfaceType" id="edit-report-interface-type"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择上报接口类型。'">
                    </select>
                </div>
            </div>
            <div style="margin-top: 5px; height: 40px;">
                <div>
                    <div class="edit-win-div-row-column-label">描述：</div>
                    <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'"  name="comnt"
                           style="width:503px;height:40px" id="edit-comment">
                </div>
            </div>
        </form>
        <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
            <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="edit-save-btn">保存</a>
            <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="edit-close-btn">取消</a>
        </div>
    </div>
    <%-- 报送主体信息编辑窗 end   --%>
    <%--历史数据表格窗口--%>
    <div id="report-subject-history-win" class="easyui-window" title="报送主体历史版本列表"
         data-options="closed: true, width: 1100, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'icon-tip'"
         style="display: none;">

        <table id="report-subject-history-data-grid" >
            <toolbar>
                <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-zip" onclick="selFileDefinedHis()" >压缩包文件名配置历史</a>
                <a href="javascript: void(0)" class="easyui-linkbutton" icon="pic_394" onclick="selFolderDefinedHis()">文件夹配置历史</a>
                <a href="javascript: void(0)" class="easyui-linkbutton" icon="pic_411" onclick="selSqlDefinedHis()">SQL关联SQL配置历史</a>
                <a href="javascript: void(0)" class="easyui-linkbutton" icon="pic_338" onclick="selFileDefinedOptHis()">报文文件配置历史</a>
            </toolbar>
        </table>
    </div>
    <%--历史数据表格窗口 end --%>

    <%-- 页面引入 start --%>
    <%@ include file="synthesizedPages.jsp"%>
    <%@ include file="reportSubjectHis.jsp"%>
    <%-- 页面引入 end   --%>

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
    <script src="<%=path%>/static/js/report/reportSubject.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/folderDefinedOptHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/folderDefinedOpt.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/fileNameOpt.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/fileNameOptHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/sqlDefinedOpt.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/sqlDefinedOptHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/fileDefinedOpt.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/fileDefinedOptHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/segmentDefined.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/segmentDefinedHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/xmlLabelDefined.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/xmlLabelDefinedHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/xmlLabelPropertyDefined.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/xmlLabelPropertyDefinedHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/dataItemDefined.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/dataItemDefinedHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/sqlParamDefined.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/sqlParamDefinedHis.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/sqlMetadata.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/report/sqlMetadataHis.js" type="text/javascript"></script>

    <script>
        $(function () {
            reportSubjectOpt.init();
        });
        function selFileDefinedHis(){
            var row = $('#report-subject-history-data-grid').datagrid('getSelected');
            if (row==null) {
                showWarningMessage('请选择一行报送主体信息数据。');
                return ;
            }
            if (row.zipManner != '报文文件合并压缩' && row.zipManner != '整体压缩(报送主体或文件夹)') {
                showWarningMessage('报文文件合并压缩或整体压缩时才需配置压缩包文件名。');
                return;
            }
            var data = {
                subjectId: row.subjectId,
                objectId: row.subjectId,
                objectType: 'RPT1301',
                fileType: 'RPT1401',
                title: row.subjectName + ' 压缩包文件名配置历史版本',
                status: row.status,
                versionNo:row.versionNo
            };
            fileNameOptHis.init(data);
        }
        function selFolderDefinedHis(){
            var row = $('#report-subject-history-data-grid').datagrid('getSelected');
            if (row==null) {
                showWarningMessage('请选择一行报送主体信息数据。');
                return ;
            }
            if (row.folderFlag == 'SYS0202') {
                showWarningMessage('所属报送主体未分文件夹。');
                return;
            }
            folderDefinedOptHis.initDefaultValue(row);
            var _win = $('#report-folderHis-data-grid-win');
            _win.window({
                title: row.subjectName + ' 文件夹配置历史信息',
                onBeforeClose: function() {
                    folderDefinedOptHis.initDefaultValue({});
                }
            });
            folderDefinedOptHis.initDataGrid();
            _win.window('open');
        }
    function selSqlDefinedHis() {
        var row = $('#report-subject-history-data-grid').datagrid('getSelected');
        if (row==null) {
            showWarningMessage('请选择一行报送主体信息数据。');
            return ;
        }
        sqlDefinedOptHis.initDefaultValue(row.subjectId, row.status,row.versionNo);
        var _win = $('#report-sqlHis-win');
        _win.window({
            title: row.subjectName + ' SQL配置历史信息',
            onBeforeClose: function() {
                sqlDefinedOptHis.initDefaultValue('', '','');
                $('.sql-edit-detailAndBtn').hide();
            }
        });
        sqlDefinedOptHis.initTree();
        _win.window('open');
        sqlDefinedOptHis.initBtn();
    }
    function selFileDefinedOptHis(){
        fileDefinedOptHis.init();
    }
    </script>
</body>
</html>
