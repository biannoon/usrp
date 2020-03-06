var fileNameOpt = fileNameOpt || {};

fileNameOpt.subjectId = '';
fileNameOpt.objectId = '';
fileNameOpt.objectType = '';
fileNameOpt.fileType = '';
fileNameOpt.subjectStatus = '';

fileNameOpt.initDefaultValue = function(subjectId, objectId, objectType, fileType, subjectStatus) {
    fileNameOpt.subjectId = subjectId;
    fileNameOpt.objectId = objectId;
    fileNameOpt.objectType = objectType;
    fileNameOpt.fileType = fileType;
    fileNameOpt.subjectStatus = subjectStatus
}

fileNameOpt.init = function(data) {
    fileNameOpt.initDefaultValue(data.subjectId, data.objectId, data.objectType, data.fileType, data.status);
    var _win = $('#report-file-name-data-grid-win');
    _win.window({
        title: data.title,
        onBeforeClose: function() {
            fileNameOpt.initDefaultValue('', '', '', '', '');
        }
    });
    fileNameOpt.query();
    fileNameOpt.initEditWin();
    _win.window('open');
    fileNameOpt.initCombobox();
    $('#file-name-edit-close-btn').unbind().bind('click', function () {
        $('#report-file-name-edit-win').window('close');
    });
}

fileNameOpt.initCombobox = function() {
    initDictionaryCode('#file-name-edit-dataItemType', 'RPT15', false);
    $('#file-name-edit-dataItemType').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue != oldValue && newValue.length > 0) {
                var _sequenceNoLen = $('#file-name-edit-sequenceNoLength');
                var readonly = true;
                var required = false;
                var minNum = 0;
                if (newValue === 'RPT1508' || newValue === 'RPT1509' || newValue === 'RPT1511' || newValue === 'RPT1512') {
                    readonly = false;
                    required = true;
                    _sequenceNoLen.numberbox('setValue', 1);
                    minNum = 1;
                } else {
                    _sequenceNoLen.numberbox('setValue', 0);
                }
                _sequenceNoLen.numberbox({
                    readonly: readonly,
                    required: required,
                    min: minNum
                });
                //
                var _dataItemExps =  $('#file-name-edit-dataItemExps');
                var _dataItemExpxReadonly = true;
                var _dataItemExpxRequired = false;
                // 避免新增下拉选项时出现错误
                //if (newValue === 'RPT1501' || newValue === 'RPT1502' || newValue === 'RPT1510') {
                if (newValue != 'RPT1503' && newValue != 'RPT1504' && newValue != 'RPT1505'
                    && newValue != 'RPT1506' && newValue != 'RPT1507' && newValue != 'RPT1508'
                    && newValue != 'RPT1509' && newValue != 'RPT1511' && newValue != 'RPT1512') {
                    _dataItemExpxReadonly = false;
                    _dataItemExpxRequired = true;
                }
                _dataItemExps.textbox({
                    required: _dataItemExpxRequired,
                    readonly: _dataItemExpxReadonly
                });
            }
        }
    })
}

fileNameOpt.initEditWin = function() {
    var _win = $('#report-file-name-edit-win');
    _win.window({
        width: 640,
        height: 255,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            $('#report-file-name-edit-form').form('clear');
        }
    });
}

fileNameOpt.initAddWin = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileNameOpt.subjectStatus)) {
        showWarningMessage('当前报送主体已发布或已删除，禁止修改。');
        return;
    }
    var _win = $('#report-file-name-edit-win');
    _win.window({
        iconCls:'icon-add',
        title: '新增文件名配置'
    });
    $('#file-name-edit-save-btn').unbind().bind('click', function () {
        fileNameOpt.saveOrUpdate('save');
    });
    _win.window('open');
}

fileNameOpt.initUpdateWin = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileNameOpt.subjectStatus)) {
        showWarningMessage('当前报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = fileNameOpt.getSelectedData();
    if (row === null) {
        return;
    }
    getOne();
    function getOne() {
        var _ajaxObj = {
            url: baseUrl + '/reportFileName/getOne',
            data: fileNameOpt.uniqueIdentifyData(row.dataItemId)
        };
        customAjaxSubmit(_ajaxObj, success);
    }
    function success(data) {
        var _win = $('#report-file-name-edit-win');
        _win.window({
            iconCls:'icon-edit',
            title: '修改文件名配置'
        });
        $('#file-name-edit-save-btn').unbind().bind('click', function () {
            fileNameOpt.saveOrUpdate('update');
        });
        $('#report-file-name-edit-form').form('load', data);
        _win.window('open');
    }
}

fileNameOpt.saveOrUpdate = function(url) {

    var _form = $('#report-file-name-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认保存当前文件名配置信息？', function(r){
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportFileName/' + url,
                    data: fileNameOpt.getFileNameDefinedValue()
                };
                customAjaxSubmit(_ajaxObj, successCallback);
            }
        });
    };
    function successCallback(result) {
        if (result.success) {
            $('#report-file-name-edit-win').window('close');
        }
        fileNameOpt.successCallback(result);
    }
}

fileNameOpt.delete = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(fileNameOpt.subjectStatus)) {
        showWarningMessage('当前报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = fileNameOpt.getSelectedData();
    if (row === null) {
        return;
    }
    $.messager.confirm('提示信息', '确认删除勾选的文件名配置信息？', function(r){
        if (r) {
            var _ajaxObj = {
                url: baseUrl + '/reportFileName/delete',
                data: fileNameOpt.uniqueIdentifyData(row.dataItemId)
            };
            customAjaxSubmit(_ajaxObj, fileNameOpt.successCallback);
        }
    });
}

fileNameOpt.successCallback = function(result) {
    $('#report-file-name-data-grid').datagrid('reload');
    selectivePrompt(result.success, result.message);
}

fileNameOpt.getSelectedData = function() {
    var row = $('#report-file-name-data-grid').datagrid('getSelected');
    if (row === undefined || row === null || row.length < 1) {
        showWarningMessage('请选择一行文件名配置信息数据。');
        return null;
    }
    return row;
}

fileNameOpt.uniqueIdentifyData = function(dataItemId) {
    return {
        subjectId: fileNameOpt.subjectId,
        objectId: fileNameOpt.objectId,
        objectType: fileNameOpt.objectType,
        fileType: fileNameOpt.fileType,
        dataItemId: dataItemId
    };
}

fileNameOpt.getFileNameDefinedValue = function() {
    return {
        dataItemId: $('#file-name-edit-dataItemId').val(),
        subjectId: fileNameOpt.subjectId,
        objectId: fileNameOpt.objectId,
        objectType: fileNameOpt.objectType,
        fileType: fileNameOpt.fileType,
        sequenceNo: $('#file-name-edit-sequence-no').numberbox('getValue'),
        dataItemNm: $('#file-name-edit-dataItemNm').textbox('getValue'),
        comnt: $('#file-name-edit-comment').textbox('getValue'),
        dataItemType: $('#file-name-edit-dataItemType').combobox('getValue'),
        dataItemExps: $('#file-name-edit-dataItemExps').textbox('getValue'),
        sequenceNoLength: $('#file-name-edit-sequenceNoLength').numberbox('getValue')
    };
}

fileNameOpt.query = function () {
    var initDataGrid = {
        url: baseUrl + '/reportFileName/query',
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
            subjectId: fileNameOpt.subjectId,
            objectId: fileNameOpt.objectId,
            objectType: fileNameOpt.objectType,
            fileType: fileNameOpt.fileType
        },
        frozenColumns:[[{
            field: "checkbox", title: "编号",  checkbox: true
        }, {
            field: "dataItemId", title: "数据项ID",  align: 'center', width: 200
        }, {
            field: "dataItemNm", title: "数据项名称",  align: 'center', width: 100
        }
        ]],
        columns: [[{
            field: "subjectId", title: "报送主体ID",  align: 'center', width: 200
        }, {
            field: "objectId", title: "所属对象ID",  align: 'center', width: 200
        }, {
            field: "objectType", title: "所属对象类型",  align: 'center', width: 80
        }, {
            field: "fileType", title: "文件类型", align: 'center', width: 100
        }, {
            field: "sequenceNo", title: "排序序号",  align: 'center', width: 70
        }, {
            field: "comnt", title: "数据项说明",  align: 'center', width: 200
        }, {
            field: "dataItemType", title: "数据项类型",  align: 'center', width: 80
        }, {
            field: "dataItemExps", title: "数据项内容表达式",  align: 'center', width: 150
        }, {
            field: "sequenceNoLength", title: "顺序号长度",  align: 'center', width: 80
        }, {
            field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
        }, {
            field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
        }]],
        toolbar: [{
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                fileNameOpt.initAddWin();
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                fileNameOpt.initUpdateWin();
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                fileNameOpt.delete();
            }
        }]
    };
    $('#report-file-name-data-grid').datagrid(initDataGrid);
}