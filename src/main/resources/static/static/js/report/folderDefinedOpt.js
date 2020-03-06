var folderDefinedOpt = folderDefinedOpt || {};

folderDefinedOpt.subjectId = '';
folderDefinedOpt.subjectStatus = '';
folderDefinedOpt.subjectReportType = '';
folderDefinedOpt.subjectZipManner = '';

folderDefinedOpt.init = function() {
    var row = reportSubjectOpt.getOneRowData();
    if (row === null) {
        return;
    }
    if (row.folderFlag === 'SYS0202') {
        showWarningMessage('所属报送主体未分文件夹。');
        return;
    }
    folderDefinedOpt.initDefaultValue(row);
    var _win = $('#report-folder-data-grid-win');
    _win.window({
        title: row.subjectName + ' 文件夹配置信息',
        onBeforeClose: function() {
            folderDefinedOpt.initDefaultValue({});
        }
    });
    folderDefinedOpt.initDataGrid();
    folderDefinedOpt.initWin();
    folderDefinedOpt.initCombobox();
    sqlDefinedOpt.initComboTree('folder-edit-relation-sql', folderDefinedOpt.subjectId);
    _win.window('open');
    $('#folder-edit-close-btn').unbind().bind('click', function () {
        $('#report-folder-edit-win').window('close');
    });
}

folderDefinedOpt.initDefaultValue = function(subjectData) {
    folderDefinedOpt.subjectId = subjectData['subjectId'];
    folderDefinedOpt.subjectStatus = subjectData['status'];
    folderDefinedOpt.subjectReportType = subjectData['reportType'];
    folderDefinedOpt.subjectZipManner = subjectData['zipManner'];
}

folderDefinedOpt.initCombobox = function() {
    initDictionaryCode('#folder-edit-orgFieldType', 'RPT16', false);
    initDictionaryCode('#folder-edit-zip-manner', 'RPT02', false);
    initDictionaryCode('#folder-edit-single-type-file-flag', 'SYS02', false);
    $('#folder-edit-orgFieldType').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                var _orgFieldExps = $('#folder-edit-orgFieldExps');
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
                    });
                }
            }
        }
    });

    // 初始化 压缩方式 下拉框 onChange事件
    $('#folder-edit-zip-manner').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                var _singleTypeFileFlag = $('#folder-edit-single-type-file-flag');
                if (newValue === 'RPT0203' && _singleTypeFileFlag.combobox('getValue') === 'SYS0202') {
                    showWarningMessage('非单一类型报文时压缩方式不能选择[报文文件合并压缩]');
                    $('#folder-edit-zip-manner').combobox('setValue', '');
                    return;
                }
                var readonlyFlag = (newValue != 'RPT0203' && newValue != 'RPT0204');
                setZipMaxSizeComboboxReadonly(readonlyFlag);
            }
        }
    });
    // 初始化 单一类型报文 下拉框 onChange事件
    $('#folder-edit-single-type-file-flag').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                var _zipManner = $('#folder-edit-zip-manner');
                if (newValue === 'SYS0202' && _zipManner.combobox('getValue') === 'RPT0203') {
                    showWarningMessage('非单一类型报文时压缩方式不能选择[报文文件合并压缩]');
                    _zipManner.combobox('setValue', '');
                    return;
                }
            }
        }
    });
    // 设置 压缩包大小限制 数字输入框为只读并设置默认值 0
    function setZipMaxSizeComboboxReadonly(readonlyFlag) {
        var _zipMaxSize = $('#folder-edit-zip-max-size');
        if (readonlyFlag) {
            _zipMaxSize.numberbox('setValue', 0);
        }
        _zipMaxSize.numberbox({
            readonly: readonlyFlag
        });
    }
}

folderDefinedOpt.dynamicSetCombobox = function() {
    folderDefinedOpt.initZipMannerCombobox();
    folderDefinedOpt.initOrgFieldTypeCombobox();
}

folderDefinedOpt.initZipMannerCombobox = function() {
    var _zipManner = $('#folder-edit-zip-manner');
    var readonly = false;
    // 报送主体设置压缩方式为 整体压缩
    if (folderDefinedOpt.subjectZipManner === 'RPT0204') {
        readonly = true;
    }
    _zipManner.combobox({
        readonly: readonly
    });
    // 压缩方式默认选择 不压缩
    _zipManner.combobox('setValue', 'RPT0201');
}

folderDefinedOpt.initOrgFieldTypeCombobox = function() {
    var _orgFileType = $('#folder-edit-orgFieldType');
    var readonly = false;
    // 报送主体设置报送方式为 全国统一报送
    if (folderDefinedOpt.subjectReportType === 'RPT0101') {
        readonly = true;
    }
    _orgFileType.combobox({
        readonly: readonly
    });
    // 默认选择 省联社机构号
    _orgFileType.combobox('setValue', 'RPT1601');
}

folderDefinedOpt.initWin = function() {
    var _win = $('#report-folder-edit-win');
    _win.window({
        width: 740,
        height: 295,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            $('#report-folder-edit-form').form('clear');
        }
    });
}

folderDefinedOpt.initAddWin = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(folderDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var _win = $('#report-folder-edit-win');
    _win.window({
        iconCls:'icon-add',
        title: '新增文件夹信息'
    });
    $('#folder-edit-save-btn').unbind().bind('click', function () {
        folderDefinedOpt.saveOrUpdate('save');
    });
    $('#folder-edit-subject-id').val(folderDefinedOpt.subjectId);
    folderDefinedOpt.dynamicSetCombobox();
    _win.window('open');
}

folderDefinedOpt.initUpdateWin = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(folderDefinedOpt.subjectStatus)) {
        showWarningMessage('当前报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = folderDefinedOpt.getSelected();
    if (row === null) {
        return;
    }
    getById();
    function getById() {
        var _ajaxObj = {
            url: baseUrl + '/reportFolder/getById',
            data: {
                subjectId: folderDefinedOpt.subjectId,
                folderId: row.folderId
            }
        }
        customAjaxSubmit(_ajaxObj, function (result) {
            $('#report-folder-edit-form').form('load', result);
            var _win = $('#report-folder-edit-win');
            _win.window({
                iconCls:'icon-edit',
                title: '更新文件夹信息'
            });
            $('#folder-edit-save-btn').unbind().bind('click', function () {
                folderDefinedOpt.saveOrUpdate('update');
            });
            folderDefinedOpt.dynamicSetCombobox();
            _win.window('open');
        })
    }
}

folderDefinedOpt.saveOrUpdate = function(url) {
    var _form = $('#report-folder-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认保存当前文件夹信息？', function(r){
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportFolder/' + url,
                    data: folderDefinedOpt.getFolderEditData()
                };
                customAjaxSubmit(_ajaxObj, successCallback);
            }
        });
    };
    function successCallback(result) {
        if (result.success) {
            $('#report-folder-edit-win').window('close');
        }
        folderDefinedOpt.successCallback(result);
    }
}

folderDefinedOpt.delete = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(folderDefinedOpt.subjectStatus)) {
        showWarningMessage('当前报送主体已发布或已删除，禁止修改。');
        return;
    }
    var row = folderDefinedOpt.getSelected();
    if (row === null) {
        return;
    }
    $.messager.confirm('提示信息', '确认删除勾选的文件夹信息？', function(r) {
        if (r) {
            var _ajaxObj = {
                url: baseUrl + '/reportFolder/delete',
                data: {
                    subjectId: folderDefinedOpt.subjectId,
                    folderId: row.folderId
                }
            };
            customAjaxSubmit(_ajaxObj, folderDefinedOpt.successCallback);
        }
    });
}

folderDefinedOpt.fileNameDefined = function(fileType) {
    var row = folderDefinedOpt.getSelected();
    if (row === null) {
        return;
    }
    var tempTitle = ' 压缩包文件名配置';
    if (fileType === 'RPT1401' && row.zipManner != 'RPT0202' && row.zipManner != 'RPT0203'
        && row.zipManner != 'RPT0204') {
        showWarningMessage('未配置压缩类型。');
        return;
    }
    if (fileType === 'RPT1403') {
        tempTitle = ' 文件夹名称配置';
    }
    var data = {
        subjectId: folderDefinedOpt.subjectId,
        objectId: row.folderId,
        objectType: 'RPT1302',
        fileType: fileType,
        title: row.folderName + tempTitle,
        status: folderDefinedOpt.subjectStatus
    };
    fileNameOpt.init(data);
}

folderDefinedOpt.getSelected = function() {
    var row = $('#report-folder-data-grid').datagrid('getSelected');
    if (row === undefined || row === null) {
        showWarningMessage('请选择一行文件夹信息数据。');
        return null;
    }
    return row;
}

folderDefinedOpt.successCallback = function(result) {
    selectivePrompt(result.success, result.message);
    $('#report-folder-data-grid').datagrid('reload');
}

folderDefinedOpt.getFolderEditData = function() {
    var zipMaxSize = $('#folder-edit-zip-max-size').numberbox('getValue');
    return {
        subjectId: $('#folder-edit-subject-id').val(),
        folderId: $('#folder-edit-folder-id').val(),
        folderName: $('#folder-edit-folder-name').textbox('getValue'),
        singleTypeFileFlag: $('#folder-edit-single-type-file-flag').combobox('getValue'),
        zipManner: $('#folder-edit-zip-manner').combobox('getValue'),
        zipMaxSize: (zipMaxSize == null || zipMaxSize === '') ? 0 : zipMaxSize,
        comnt: $('#folder-edit-comment').textbox('getValue'),
        sequenceNo: $('#folder-edit-sequence-no').numberbox('getValue'),
        orgFieldType: $('#folder-edit-orgFieldType').combobox('getValue'),
        orgFieldExps: $('#folder-edit-orgFieldExps').textbox('getValue'),
        relationSqlId: $('#folder-edit-relation-sql').combotree('getValue')
    };
}

folderDefinedOpt.initDataGrid = function () {
    var initDataGrid = {
        url: baseUrl + '/reportFolder/query',
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
            subjectId: folderDefinedOpt.subjectId
        },
        frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "folderId", title: "文件夹ID",  align: 'center', width: 170
            }, {
                field: "folderName", title: "中文名称",  align: 'center', width: 200
            }
        ]],
        columns: [[{
            field: "comnt", title: "说明",  align: 'center', width: 300, formatter: function (value, row, index) {
                return reportSubjectOpt.filterData(value);
            }
        }, {
            field: "singleTypeFileFlag", title: "是否单一类型报文", align: 'center', width: 110
        }, {
            field: "zipManner", title: "压缩方式", hidden: true
        }, {
            field: "zipMannerText", title: "压缩方式",  align: 'center', width: 120
        }, {
            field: "zipMaxSize", title: "压缩包大小限制（M）",  align: 'center', width: 125
        }, {
            field: "orgFieldType", title: "机构字段映射类型",  align: 'center', width: 125
        }, {
            field: "orgFieldExps", title: "机构字段映射表达式",  align: 'center', width: 150
        }, {
            field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
        }, {
            field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
        }, {
            field: "sequenceNo", title: "排序号", align: 'center', width: 50
        }]],
        toolbar: [{
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                folderDefinedOpt.initAddWin();
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                folderDefinedOpt.initUpdateWin();
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                folderDefinedOpt.delete();
            }
        }, '-', {
            text: '文件夹名称配置',
            iconCls: 'pic_225',
            handler: function () {
                folderDefinedOpt.fileNameDefined('RPT1403');
            }
        }, '-', {
            text: '压缩包文件名配置',
            iconCls: 'icon-zip',
            handler: function () {
                folderDefinedOpt.fileNameDefined('RPT1401');
            }
        }]
    };

    $('#report-folder-data-grid').datagrid(initDataGrid);
}