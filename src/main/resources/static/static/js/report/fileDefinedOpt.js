var fileDefinedOpt = fileDefinedOpt || {};

fileDefinedOpt.subjectId = '';
fileDefinedOpt.subjectStatus = '';
fileDefinedOpt.subjectReportType = '';

fileDefinedOpt.initDefaultValue = function(subjectId, status, subjectReportType) {
    fileDefinedOpt.subjectId = subjectId;
    fileDefinedOpt.subjectStatus = status;
    fileDefinedOpt.subjectReportType = subjectReportType;
}

/**
 * 初始化
 */
fileDefinedOpt.init = function () {
    var subjectData = reportSubjectOpt.getOneRowData();
    if (isNullObject(subjectData)) {
        return;
    }
    fileDefinedOpt.initDefaultValue(subjectData.subjectId, subjectData.status, subjectData.reportType);
    var _win = $('#report-file-data-grid-win');
    _win.window({
        title: subjectData.subjectName + ' 报文文件配置信息',
        onBeforeClose: function() {
            fileDefinedOpt.initDefaultValue('', '', '');
        }
    });
    fileDefinedOpt.initDataGrid();
    _win.window('open');
    fileDefinedOpt.initCombobox();
    sqlDefinedOpt.initComboTree('file-edit-relation-sql', fileDefinedOpt.subjectId);
    fileDefinedOpt.initEditWin();
    fileDefinedOpt.initFolderComboGrid(subjectData.folderFlag);
    $('#file-edit-close-btn').unbind().bind('click', function () {
        $('#report-file-edit-win').window('close');
    });
}

fileDefinedOpt.initCombobox = function() {
    initDictionaryCode('#file-edit-file-type', 'RPT04', false);
    initDictionaryCode('#file-edit-body-type', 'RPT05', false);
    initDictionaryCode('#file-edit-emptyFileFlag', 'SYS02', false);
    initDictionaryCode('#file-edit-codeType', 'RPT06', false);
    initDictionaryCode('#file-edit-orgFieldType', 'RPT16', false);

    $('#file-edit-orgFieldType').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                var _orgFieldExps = $('#file-edit-orgFieldExps');
                if (newValue === 'RPT1601') {
                    _orgFieldExps.textbox({
                        readonly: true,
                        required: false
                    });
                    _orgFieldExps.textbox('setValue', '');
                } else {
                    _orgFieldExps.textbox({
                        readonly: false,
                        required: true
                    })
                }
            }
        }
    });
    $('#file-edit-body-type').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue != oldValue && newValue.length > 0) {
                var _recordLimit = $('#file-edit-recordLimit');
                var _emptyFileFlag = $('#file-edit-emptyFileFlag');
                var readonly = false;
                var required = true;
                if (newValue === 'RPT0502') {
                    readonly = true;
                    required = false;
                    _recordLimit.numberbox('setValue', 0);
                }
                _recordLimit.numberbox({
                    readonly: readonly
                });
                _emptyFileFlag.combobox({
                    readonly: readonly,
                    required: required
                });
            }
        }
    });
}

fileDefinedOpt.initOrgFieldTypeCombobox = function(subjectReportType) {
    var _orgFileType = $('#file-edit-orgFieldType');
    var readonly = false;
    // 报送主体设置报送方式为 全国统一报送
    if (subjectReportType === 'RPT0101') {
        readonly = true;
    }
    _orgFileType.combobox({
        readonly: readonly
    });
    // 默认选择 省联社机构号
    _orgFileType.combobox('setValue', 'RPT1601');
}

fileDefinedOpt.initEditWin = function() {
    var _win = $('#report-file-edit-win');
    _win.window({
        onBeforeClose: function() {
            $('#report-file-edit-form').form('clear');
        }
    });
}

fileDefinedOpt.initAddWin = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var _win = $('#report-file-edit-win');
    _win.window({
        iconCls:'icon-add',
        title: '新增报文文件配置信息'
    });
    bindClickEvent('file-edit-save-btn', function () {
        fileDefinedOpt.saveOrUpdate('save');
    });
    $('#file-edit-subject-id').val(fileDefinedOpt.subjectId);
    fileDefinedOpt.initOrgFieldTypeCombobox(fileDefinedOpt.subjectReportType);
    _win.window('open');
}

fileDefinedOpt.initUpdateWin = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = fileDefinedOpt.getSelected();
    if (isNullObject(row)) {
        return;
    }
    getOne();
    function getOne() {
        var _ajaxObj = {
            url: baseUrl + '/reportFile/getOne',
            data: {
                subjectId: fileDefinedOpt.subjectId,
                folderId: row.folderId,
                fileId: row.fileId
            }
        };
        customAjaxSubmit(_ajaxObj, callback);
    }
    function callback(result) {
        $('#report-file-edit-form').form('load', result);
        var _win = $('#report-file-edit-win');
        _win.window({
            iconCls:'icon-edit',
            title: '更新报文文件配置信息'
        });
        bindClickEvent('file-edit-save-btn', function () {
            fileDefinedOpt.saveOrUpdate('update');
        });
        $('#file-edit-subject-id').val(fileDefinedOpt.subjectId);
        $('#file-edit-folder-name').combogrid('setValue', result.folderName);
        fileDefinedOpt.initOrgFieldTypeCombobox(fileDefinedOpt.subjectReportType);
        _win.window('open');
    }
}

fileDefinedOpt.delete = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = fileDefinedOpt.getSelected();
    if (isNullObject(row)) {
        return;
    }
    $.messager.confirm('提示信息', '确认删除勾选的报文文件配置信息？', function(r) {
        if (r) {
            var _ajaxObj = {
                url: baseUrl + '/reportFile/delete',
                data: {
                    subjectId: fileDefinedOpt.subjectId,
                    folderId: row.folderId,
                    fileId: row.fileId
                }
            };
            customAjaxSubmit(_ajaxObj, fileDefinedOpt.successCallback);
        }
    });
}

fileDefinedOpt.saveOrUpdate = function(url) {
    var _form = $('#report-file-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认保存当前报文文件配置信息？', function(r){
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportFile/' + url,
                    data: fileDefinedOpt.getFileDefinedData()
                };
                customAjaxSubmit(_ajaxObj, successCallback);
            }
        });
    };
    function successCallback(result) {
        if (result.success) {
            $('#report-file-edit-win').window('close');
        }
        fileDefinedOpt.successCallback(result);
    }
}

fileDefinedOpt.successCallback = function(result) {
    selectivePrompt(result.success, result.message);
    $('#report-file-data-grid').datagrid('reload');
}

fileDefinedOpt.getSelected = function() {
    var row = $('#report-file-data-grid').datagrid('getSelected');
    if (isEmptyArray(row)) {
        showWarningMessage('请选择一行文件夹信息数据。');
        return null;
    }
    return row;
}

fileDefinedOpt.getFileDefinedData = function() {
    var recordLimit = $('#file-edit-recordLimit').numberbox('getValue');
    return {
        subjectId: $('#file-edit-subject-id').val(),
        fileId: $('#file-edit-file-id').val(),
        fileName: $('#file-edit-file-name').textbox('getValue'),
        folderId: $('#file-edit-folder-id').val(),
        fileType: $('#file-edit-file-type').combobox('getValue'),
        bodyType: $('#file-edit-body-type').combobox('getValue'),
        recordLimit: isEmptyChar(recordLimit) ? 0 : recordLimit,
        emptyFileFlag: $('#file-edit-emptyFileFlag').combobox('getValue'),
        codeType: $('#file-edit-codeType').combobox('getValue'),
        relationSqlId: $('#file-edit-relation-sql').combotree('getValue'),
        orgFieldType: $('#file-edit-orgFieldType').combobox('getValue'),
        orgFieldExps: $('#file-edit-orgFieldExps').textbox('getValue'),
        sequenceNo: $('#file-edit-sequence-no').numberbox('getValue'),
        comnt: $('#file-edit-comment').textbox('getValue')
    };
}

fileDefinedOpt.fileNameDefined = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileDefinedOpt.subjectStatus)) {
        showWarningMessage('当前报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = fileDefinedOpt.getSelected();
    if (row === null) {
        return;
    }
    var data = {
        subjectId: fileDefinedOpt.subjectId,
        objectId: row.fileId,
        objectType: 'RPT1303',
        fileType: 'RPT1402',
        title: row.fileName + ' 文件名配置',
        status: fileDefinedOpt.subjectStatus
    };
    fileNameOpt.init(data);
}

fileDefinedOpt.initDataGrid = function () {
    var initDataGrid = {
        url: baseUrl + '/reportFile/query',
        fit : true,
        fitColumns: false,
        border: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        singleSelect: true,
        checkOnSelect: true,
        selectOnCheck: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        queryParams: {
            subjectId: fileDefinedOpt.subjectId
        },
        frozenColumns:[[{
            field: "checkbox", title: "编号",  checkbox: true
        },{
            field: "fileId", title: "报文文件ID",  align: 'center', width: 170
        }, {
            field: "fileName", title: "中文名称",  align: 'center', width: 200
        }, {
            field: "folderId", title: "所属文件夹ID",  align: 'center', width: 170
        }
        ]],
        columns: [[
            {
                field: "sequenceNo", title: "排序号", align: 'center', width: 50
            }, {
                field: "fileType", title: "文件类型", hidden: true
            }, {
                field: "fileTypeText", title: "文件类型", align: 'center', width: 100
            }, {
                field: "bodyType", title: "报送体类型", hidden: true
            }, {
                field: "bodyTypeText", title: "报送体类型", align: 'center', width: 100
            }, {
                field: "recordLimit", title: "报送记录限制条数",  align: 'center', width: 120
            }, {
                field: "codeType", title: "文件编码格式",  align: 'center', width: 100
            }, {
                field: "comnt", title: "说明",  align: 'center', width: 300, formatter: function (value, row, index) {
                    return reportSubjectOpt.filterData(value);
                }
            }]],
        toolbar: [{
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                fileDefinedOpt.initAddWin();
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                fileDefinedOpt.initUpdateWin();
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                fileDefinedOpt.delete();
            }
        }, '-', {
            text: '文件名配置',
            iconCls: 'pic_336',
            handler: function () {
                fileDefinedOpt.fileNameDefined();
            }
        }, '-', {
            text: '压缩包文件名配置',
            iconCls: 'icon-zip',
            handler: function () {
                fileDefinedOpt.setZipFileName();
            }
        }, '-', {
            text: '结构段或标签配置',
            iconCls: 'pic_220',
            handler: function () {
                var fileData = fileDefinedOpt.getSelected();
                if (isNullObject(fileData)) {
                    return;
                }
                if (fileData.fileType === 'RPT0403') {
                    // xml 报文
                    new xmlLabelDefined(baseUrl).init(fileData);
                    // 文本型报文
                } else if (fileData.fileType === 'RPT0401' || fileData.fileType === 'RPT0402') {
                    new segmentDefined(baseUrl).init(fileData);
                }
            }
        }]
    };
    $('#report-file-data-grid').datagrid(initDataGrid);
}

fileDefinedOpt.initFolderComboGrid = function(folderFlag) {
    $('#file-edit-folder-id').val('');
    var _comboGrid = $('#file-edit-folder-name');
    if (folderFlag === 'SYS0201') {
        var _combogrid = fileDefinedOpt.folderComboGrid();
        _combogrid['required'] = true;
        _combogrid['readonly'] = false;
        _comboGrid.combogrid(_combogrid);
    } else {
        _comboGrid.combogrid({
            required: false,
            readonly: true
        });
        _comboGrid.combogrid('clear');
    }
}

fileDefinedOpt.folderComboGrid = function() {
    return {
        panelWidth: 405,
        url: baseUrl + '/reportFolder/query',
        idField: 'folderId',
        textField: 'folderName',
        fitColumns: false,
        method: 'post',
        pagination : true,
        pageSize: 10,
        pageList: [10, 20],
        striped: true,
        queryParams: {
            subjectId: fileDefinedOpt.subjectId
        },
        columns: [[
            {field: 'folderId', title: '文件夹ID', width: 200, align: 'center'},
            {field: 'folderName', title: '文件夹名称名称', width: 200, align: 'center'}
        ]],
        onSelect: function (rowIndex, rowData){
            $('#file-edit-folder-id').val(rowData.folderId);
        }
    };
}

fileDefinedOpt.setZipFileName = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = fileDefinedOpt.getSelected();
    if (isNullObject(row)) {
        return;
    }
    if (row.folderId != null) {
        var _ajaxObj = {
            url: baseUrl + '/reportFolder/getById',
            data: {
                folderId: row.folderId
            }
        };
        customAjaxSubmit(_ajaxObj, function (result) {
            if (result.zipManner != 'RPT0202') {
                showWarningMessage('文件夹压缩方式非[报文文件单体压缩]');
                return;
            } else {
                var data = {
                    subjectId: row.subjectId,
                    objectId: row.fileId,
                    objectType: 'RPT1303',
                    fileType: 'RPT1401',
                    title: row.fileName + ' 压缩包文件名配置信息',
                    status: fileDefinedOpt.subjectStatus
                };
                fileNameOpt.init(data);
            }
        });
    } else {
        showWarningMessage('所属报送主体未配置文件夹信息');
    }
}
