var reportSubjectOpt = reportSubjectOpt || {};

reportSubjectOpt.init = function() {
    reportSubjectOpt.dataGrid();
    reportSubjectOpt.initWin();
    reportSubjectOpt.initBtn();
    reportSubjectOpt.initCombobox();
    reportSubjectOpt.initSubsystem('query-subsystem-id', 'combo-grid-query-subsystem-name', '');
}

reportSubjectOpt.initBtn = function() {
    // 初始化报送送主体配置信息编辑窗口关闭按钮
    $('#edit-close-btn').unbind().bind('click', function () {
        $('#report-subject-edit-win').window('close');
    });
}

reportSubjectOpt.initCombobox = function() {
    initDictionaryCode('#query-subject-status', 'DFI02', true);
    initDictionaryCode('#edit-report-type', 'RPT01', false);
    initDictionaryCode('#edit-zip-manner', 'RPT02', false);
    initDictionaryCode('#edit-report-submit-type', 'RPT17', false);
    initDictionaryCode('#edit-report-interface-type', 'RPT18', false);

    var attributes = [
        getComboboxAttributes('#edit-single-type-file-flag', false),
        getComboboxAttributes('#edit-folder-flag', false),
        getComboboxAttributes('#edit-report-download-flag', false)
    ];
    initRepeatDictionaryCode(attributes, 'SYS02');
    // 初始化 分文件夹存放 下拉框 onChange事件
    $('#edit-folder-flag').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                var _zipManner = $('#edit-zip-manner');
                var _singleTypeFile = $('#edit-single-type-file-flag');
                if (newValue === 'SYS0201') {
                    /*
                     * 分文件夹存放时 动态设置 压缩方式下拉框 单一类型文件下拉框
                     * 设置combobox为readonly后会清空combobox选择的值 所以现设置 readonly属性再赋值
                     * 设置压缩方式只能选 不压缩或整体压缩
                     */
                    var zipMannerValue = _zipManner.combobox('getValue');
                    if (zipMannerValue.length > 0 && zipMannerValue != 'RPT0201' && zipMannerValue != 'RPT0204') {
                        showWarningMessage('分文件夹存放时只能选择不压缩或整体压缩。');
                        _zipManner.combobox('setValue', '');
                        return;
                    }
                    // 清空是否单一类型文件下拉框并设置其为只读
                    _singleTypeFile.combobox({
                        readonly: true,
                        required: false
                    });
                    _singleTypeFile.combobox('clear');
                    return;
                }
                _singleTypeFile.combobox({
                    readonly: false,
                    required: true
                });
            }
        }
    });
    // 是否单一类型报文文件
    $('#edit-single-type-file-flag').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue != oldValue && newValue.length > 0) {
                var _zipManner = $('#edit-zip-manner');
                if (newValue === 'SYS0202' && _zipManner.combobox('getValue') === 'RPT0203') {
                    showWarningMessage('非单一类型报文时压缩方式不能选择[报文文件合并压缩]');
                    _zipManner.combobox('setValue', '');
                    return;
                }
            }
        }
    });
    // 初始化 压缩方式 下拉框 onChange事件
    setZipMannerCombobox();
    // 初始化 上报类型下拉框 onChange事件
    setRptSubmitTypeCombobox();

    function setZipMannerCombobox() {
        var _zipManner = $('#edit-zip-manner');
        _zipManner.combobox({
            onChange: function (newValue, oldValue) {
                if (newValue.length > 0 && newValue != oldValue) {
                    var _singleTypeFileFlag = $('#edit-single-type-file-flag');
                    if (newValue === 'RPT0203' && _singleTypeFileFlag.combobox('getValue') === 'SYS0202') {
                        showWarningMessage('非单一类型报文时压缩方式不能选择[报文文件合并压缩]');
                        _zipManner.combobox('setValue', '');
                        return;
                    }
                    var _folderFLag = $('#edit-folder-flag');
                    if (_folderFLag.combobox('getValue') === 'SYS0201' && newValue != 'RPT0201' && newValue != 'RPT0204') {
                        showWarningMessage('分文件夹存放时只能选择不压缩或整体压缩。');
                        _zipManner.combobox('setValue', '');
                    }
                    var readonlyFlag = (newValue != 'RPT0203' && newValue != 'RPT0204');
                    setZipMaxSizeComboboxReadonly(readonlyFlag);

                }
            }
        });
    }
    // 设置 压缩包大小限制 数字输入框为只读并设置默认值 0
    function setZipMaxSizeComboboxReadonly(readonlyFlag) {
        var _zipMaxSize = $('#edit-zip-max-size');
        if (readonlyFlag) {
            _zipMaxSize.numberbox('setValue', 0);
        }
        _zipMaxSize.numberbox({
            readonly: readonlyFlag
        });
    }

    function setRptSubmitTypeCombobox() {
        var _rptSubmitType = $('#edit-report-submit-type');
        var _downloadFlag = $('#edit-report-download-flag');
        var _rptInterfaceType = $('#edit-report-interface-type');
        _rptSubmitType.combobox({
            onChange: function (newValue, oldValue) {
                if (newValue.length > 0 && newValue != oldValue) {
                    /*
                     * 1、当选择为“下载后手工上报”时，是否允许下载默认为“是”且必须选择为“是”
                     * 2、当上报方式选择为“下载后手工上报”时，上报接口类型不允许填写，否则必须填写
                     */
                    var readonly = false;
                    var required = true;
                    var downloadFlagValue = _downloadFlag.combobox('getValue');
                    var rtpInterfaceTypeValue = _rptInterfaceType.combobox('getValue');
                    if (newValue === 'RPT1703') {
                        readonly = true;
                        required = false;
                        downloadFlagValue = 'SYS0201';
                        rtpInterfaceTypeValue = '';
                    }
                    _downloadFlag.combobox({
                        readonly: readonly
                    });
                    _downloadFlag.combobox('setValue', downloadFlagValue);
                    _rptInterfaceType.combobox({
                        readonly: readonly,
                        required: required
                    });
                    _rptInterfaceType.combobox('setValue', rtpInterfaceTypeValue);
                }
            }
        });
    }
}

reportSubjectOpt.initWin = function() {
    var _win = $('#report-subject-edit-win');
    _win.window({
        width: 640,
        height: 340,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            $('#report-subject-edit-form').form('clear');
            $('#edit-zip-manner').combobox({
                readonly: false
            });
            $('#edit-zip-max-size').numberbox({
                readonly: false
            });
            $('#edit-single-type-file-flag').combobox({
                readonly: false
            })
        }
    });
}

/**
 * 初始化所属子系统下拉列表
 * @param combogridId combogrid控件id
 * @param keywordDomId 自定义combogrid控件搜索框id
 * @param defaultValue combogrid控件显示的默认值
 * @author wuyu
 */
reportSubjectOpt.initSubsystem = function(combogridId, keywordDomId, defaultValue) {
    var _combogrid = reportSubjectOpt.subsystemComboGrid();
    _combogrid['toolbar'] = reportSubjectOpt.subsystemComboGridToolbar(combogridId, keywordDomId);
    _combogrid['onLoadSuccess'] = function() {
        $('#' + combogridId).combogrid('setValue', defaultValue);
    }
    $('#' + combogridId).combogrid(_combogrid);
}

reportSubjectOpt.initAddWin = function() {
    var _win = $('#report-subject-edit-win');
    _win.window({
        iconCls:'icon-add',
        title: '新增报送主体信息'
    });
    reportSubjectOpt.initSubsystem('edit-subsystem-name-id', 'combo-grid-edit-subsystem-name', '');
    $('#edit-save-btn').unbind().bind('click', function () {
        reportSubjectOpt.saveOrUpdate('save');
    });
    // 初始化关联SQL下拉树为空
    sqlDefinedOpt.initComboTree('edit-report-relation-sql', '-1');
    _win.window('open');
}

reportSubjectOpt.initUpdateWin = function() {
    var _win = $('#report-subject-edit-win');
    _win.window({
        iconCls:'icon-edit',
        title: '更新报送主体信息'
    });
    $('#edit-save-btn').unbind().bind('click', function () {
        reportSubjectOpt.saveOrUpdate('update');
    });
    _win.window('open');
}

/**
 * 查询报送主体信息列表
 * @author wuyu
 */
reportSubjectOpt.query = function () {
    var queryParams = {
        subsystemId: $('#query-subsystem-id').combogrid('getValue'),
        status: $('#query-subject-status').combobox('getValue'),
        subjectName: $('#query-subject-name').textbox('getValue'),
        subjectId: $('#query-subject-id').textbox('getValue')
    };
    $('#report-subject-data-grid').datagrid('load', queryParams);
}

reportSubjectOpt.saveOrUpdate = function(url) {
    var _form = $('#table-interface-update-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认保存当前报送主体信息？', function(r){
            if (r) {
                showSubmitProgress();
                var _ajaxObj = {
                    url: baseUrl + '/reportSubject/' + url,
                    data: reportSubjectOpt.getSubjectEditData()
                };
                customAjaxSubmit(_ajaxObj, successCallback);
            }
        });
    };
    function successCallback(result) {
        if (result.success) {
            $('#report-subject-edit-win').window('close');
        }
        reportSubjectOpt.successCallback(result);
        closeProgress();
    }
}

reportSubjectOpt.initUpdate = function() {
    var row = reportSubjectOpt.getReleaseOrUpdateData();
    if (row == null) {
        return;
    }
    sqlDefinedOpt.initComboTree('edit-report-relation-sql', row.subjectId);
    loadData();
    function loadData() {
        var _ajaxObj = {
            url: baseUrl + '/reportSubject/getOne',
            data: {
                subjectId: row.subjectId
            }
        };
        customAjaxSubmit(_ajaxObj, function (result) {
            $('#report-subject-edit-form').form('load', result);
            reportSubjectOpt.initSubsystem('edit-subsystem-name-id', 'combo-grid-edit-subsystem-name', result.subsystemName);
            reportSubjectOpt.initUpdateWin();
        }, function () {
            showErrorMessage('初始化更新操作失败。');
        });
    }
}

reportSubjectOpt.delete = function() {
    var checkedItems = $('#report-subject-data-grid').datagrid('getChecked');
    if (!reportSubjectOpt.hasSelected(checkedItems)) {
        showWarningMessage('请选择需要删除的报送主体信息。')
        return;
    }
    var subjectIds = [];
    for (var i = 0, length = checkedItems.length; i < length; i++) {
        var row = checkedItems[i];
        if (row['status'] === 'DFI0202' || row['status'] === 'DFI0204') {
            showWarningMessage('报送主体[' + row['subjectName'] + '] 已发布或已删除，禁止修改。');
            return;
        }
        subjectIds.push(row['subjectId']);
    }
    $.messager.confirm('提示信息', '确认删除勾选的报送主体信息？', function(r){
        if (r) {
            var _ajaxObj = {
                url: baseUrl + '/reportSubject/delete',
                data: {
                    subjectIds: subjectIds
                }
            };
            customAjaxSubmit(_ajaxObj, reportSubjectOpt.successCallback);
        }
    });
}

reportSubjectOpt.release = function() {
    var row = reportSubjectOpt.getReleaseOrUpdateData();
    if (row == null) {
        return;
    }
    $.messager.confirm('提示信息', '确认发布[' + row.subjectName + ']？', function(r){
        if (r) {
            showSubmitProgress();
            reportSubjectOpt.releaseOrRepeal('release', row.subjectId);
        }
    });
}

reportSubjectOpt.repeal = function() {
    var row = reportSubjectOpt.getOneRowData();
    if (row.status != 'DFI0202') {
        showWarningMessage('当前报送主体信息未发布。');
        return;
    }
    $.messager.confirm('提示信息', '确认撤销发布[' + row.subjectName + ']？', function(r){
        if (r) {
            reportSubjectOpt.releaseOrRepeal('repeal', row.subjectId);
        }
    });
}

reportSubjectOpt.releaseOrRepeal = function(url, subjectId) {
    var _ajaxObj = {
        url: baseUrl + '/reportSubject/' + url,
        data: {
            subjectId: subjectId
        }
    };
    customAjaxSubmit(_ajaxObj, reportSubjectOpt.successCallback);
}

reportSubjectOpt.getReleaseOrUpdateData = function() {
    var row = reportSubjectOpt.getOneRowData();
    if (row == null) {
        return row;
    }
    if (reportSubjectOpt.isReleaseOrUpdateStatus(row)) {
        showWarningMessage('当前报送主体已发布或已删除，禁止修改。');
        return null;
    }
    return row;
}

reportSubjectOpt.isReleaseOrUpdateStatus = function(rowData) {
    return !reportSubjectOpt.isInsertOrRepealStatus(rowData.status);
}

reportSubjectOpt.isInsertOrRepealStatus = function(data) {
    return data === 'DFI0201' || data === 'DFI0203';
}

reportSubjectOpt.getOneRowData = function() {
    var checkedItems = $('#report-subject-data-grid').datagrid('getChecked');
    if (!reportSubjectOpt.isSelectedOne(checkedItems)) {
        showWarningMessage('请选择一行报送主体信息数据。');
        return null;
    }
    return checkedItems[0];
}

reportSubjectOpt.isEmptyObject = function(data) {
    return data === undefined || data === null || typeof data != 'object';
}

/**
 * 检验报送是否只选中了一行报送主体信息数据
 * @param checkedItems 选中的报送主体信息集合
 * @returns {boolean}
 */
reportSubjectOpt.isSelectedOne = function(checkedItems) {
    return !reportSubjectOpt.isEmptyObject(checkedItems) && checkedItems.length == 1;
}

reportSubjectOpt.hasSelected = function(checkedItems) {
    return !reportSubjectOpt.isEmptyObject(checkedItems) && checkedItems.length > 0;
}

/**
 *
 *
 * @param result
 */
reportSubjectOpt.successCallback  = function(result) {
    selectivePrompt(result.success, result.message);
    if (result.success) {
        $('#report-subject-data-grid').datagrid('reload');
    }
    closeProgress();
}

reportSubjectOpt.queryHistoryVersion = function(id, name) {
    var _win = $('#report-subject-history-win');
    _win.window({
        title: name + ' 历史版本数据'
    })
    _win.window('open');
    reportSubjectOpt.historyDataGrid(id);
}

/**
 * 获取报送主体编辑窗口中的报送主体信息数据
 * @author wuyu
 */
reportSubjectOpt.getSubjectEditData = function() {
    var zipMaxSize = $('#edit-zip-max-size').numberbox('getValue');
    return {
        subjectId: $('#edit-subject-id').val(),
        folderId: $('#folder-edit-folder-id').val(),
        subjectName: $('#edit-subject-name').textbox('getValue'),
        subsystemId: $('#edit-subsystem-id').val(),
        reportType: $('#edit-report-type').combobox('getValue'),
        singleTypeFileFlag: $('#edit-single-type-file-flag').combobox('getValue'),
        zipManner: $('#edit-zip-manner').combobox('getValue'),
        zipMaxSize: (zipMaxSize == null || zipMaxSize === '') ? 0 : zipMaxSize,
        folderFlag: $('#edit-folder-flag').combobox('getValue'),
        comnt: $('#edit-comment').textbox('getValue'),
        relationSqlId: $('#edit-report-relation-sql').combotree('getValue'),
        downloadFlag: $('#edit-report-download-flag').combobox('getValue'),
        rptSubmitType: $('#edit-report-submit-type').combobox('getValue'),
        rptInterfaceType: $('#edit-report-interface-type').combobox('getValue')
    };
}

/**
 * 过滤数据
 * @param val
 * @returns {string}
 * @author wuyu
 */
reportSubjectOpt.filterData = function(val) {
    if (isNullObject(val)) {
        return '';
    }
    var newVal = val.replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return newVal;
}

/**
 * 获取所属子系统ComboGrid控件信息
 * @author wuyu
 */
reportSubjectOpt.subsystemComboGrid = function() {
    return {
        panelWidth: 405,
        url: baseUrl + '/sysSubsystem/getByPage',
        idField: 'subsystemId',
        textField: 'subsystemNm',
        fitColumns: false,
        method: 'post',
        pagination : true,
        pageSize: 10,
        pageList: [10, 20],
        striped: true,
        columns: [[
            {field: 'subsystemId', title: '子系统ID', width: 200, align: 'center', hidden: true},
            {field: 'subsystemNm', title: '子系统名称', width: 160, align: 'center'},
            {field: 'subsystemComnt', title: '子系统描述', width: 240, align: 'center'}
        ]],
        onSelect: function (rowIndex, rowData){
            $('#edit-subsystem-id').val(rowData.subsystemId);
        }
    };
}

/**
 * 获取所属子系统ComboGrid控件信息
 * @author wuyu
 */
reportSubjectOpt.subsystemComboGridToolbar = function(combogridId, keywordDomId) {
    return [{
        text: '<span>子系统名称：</span><input style="width:200px" id="' + keywordDomId + '"/>'
    }, '-', {
        text: '查询',
        iconCls: 'icon-search',
        handler: function () {
            var subsystem = {
                subsystemNm: $('#' + keywordDomId).val()
            };
            $('#' + combogridId).combogrid('grid').datagrid('load', subsystem);
        }
    }];
}

/**
 * 初始化报送主体数据网格
 * @author wuyu
 */
reportSubjectOpt.dataGrid = function () {
    var initDataGrid = {
        url: baseUrl + '/reportSubject/query',
        fitColumns: false,
        border: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        singleSelect: false,
        checkOnSelect: true,
        selectOnCheck: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        frozenColumns:[[
            {
                field: "checkbox", title: "编号",  checkbox: true
            }, {
                field: "subjectId", title: "报送主体ID", align: 'center', width: 200
            }, {
                field: "subjectName", title: "报送主体名称",  align: 'center', width: 200
            }
        ]],
        columns: [[{
            field: "statusText", title: "发布状态",  align: 'center', width: 90
        }, {
            field: "status", title: "发布状态",  align: 'center', hidden: true
        }, {
            field: "subsystemId", title: "所属子系统",  align: 'center', hidden: true
        }, {
            field: "subsystemName", title: "所属子系统",  align: 'center', width: 100
        }, {
            field: "reportType", title: "报送方类型", hidden: true
        }, {
            field: "reportTypeText", title: "报送方类型", align: 'center', width: 100
        }, {
            field: "folderFlag", title: "是否分文件夹存放",  hidden: true
        }, {
            field: "folderFlagText", title: "是否分文件夹存放",  align: 'center', width: 110
        }, {
            field: "singleTypeFileFlag", title: "是否单一类型报文", align: 'center', width: 110
        }, {
            field: "zipManner", title: "压缩方式",  hidden: true
        }, {
            field: "zipMannerText", title: "压缩方式",  align: 'center', width: 120
        }, {
            field: "zipMaxSize", title: "压缩包大小限制（M）",  align: 'center', width: 125
        }, {
            field: "downloadFlag", title: "是否允许下载",  align: 'center', width: 100
        }, {
            field: "rptSubmitType", title: "上报方式", hidden: true
        }, {
            field: "rptSubmitTypeText", title: "上报方式",  align: 'center', width: 100
        }, {
            field: "rptInterfaceType", title: "上报接口类型",  align: 'center', width: 110
        }, {
            field: "comnt", title: "描述",  align: 'center', width: 300, formatter: function (value, row, index) {
                return reportSubjectOpt.filterData(value);
            }
        }, {
            field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
        }, {
            field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
        }, {
            field: "history", title: "历史版本",  align: 'center', width: 100,
            formatter: function (value, row, index) {
                return '<a href="javascript: void(0)" ' +
                    'onclick="reportSubjectOpt.queryHistoryVersion(\'' + row.subjectId + '\', \'' +row.subjectName+'\')">查看历史版本</a>';
            }
        }]],
        toolbar: reportSubjectOpt.toolbar()
    };

    $('#report-subject-data-grid').datagrid(initDataGrid);
}

reportSubjectOpt.toolbar = function () {
    return [{
        text: '新增',
        iconCls: 'icon-add',
        handler: function () {
            reportSubjectOpt.initAddWin();
        }
    }, '-', {
        text: '修改',
        iconCls: 'icon-edit',
        handler: function () {
            reportSubjectOpt.initUpdate();
        }
    }, '-', {
        text: '删除',
        iconCls: 'icon-remove',
        handler: function () {
            reportSubjectOpt.delete();
        }
    }, '-', {
        text: '发布',
        iconCls: 'icon-redo',
        handler: function () {
            reportSubjectOpt.release();
        }
    }, '-', {
        text: '撤销发布',
        iconCls: 'icon-undo',
        handler: function () {
            reportSubjectOpt.repeal();
        }
    }, '-', {
        text: '压缩包文件名配置',
        //iconCls: 'pic_332',
        iconCls: 'icon-zip',
        handler: function () {
            var row = reportSubjectOpt.getOneRowData();
            if (row === null) {
                return;
            }
            if (row.zipManner != 'RPT0203' && row.zipManner != 'RPT0204') {
                showWarningMessage('报文文件合并压缩或整体压缩时才需配置压缩包文件名。');
                return;
            }
            var data = {
                subjectId: row.subjectId,
                objectId: row.subjectId,
                objectType: 'RPT1301',
                fileType: 'RPT1401',
                title: row.subjectName + ' 压缩包文件名配置信息',
                status: row.status
            };
            fileNameOpt.init(data);
        }
    }, '-', {
        text: '文件夹配置',
        iconCls: 'pic_394',
        handler: function () {
            folderDefinedOpt.init();
        }
    }, '-', {
        text: '关联SQL配置',
        iconCls: 'pic_411',
        handler: function () {
            sqlDefinedOpt.init();
        }
    }, '-', {
        text: '报文文件配置',
        iconCls: 'pic_338',
        handler: function () {
            fileDefinedOpt.init();
        }
    }];
}

reportSubjectOpt.historyDataGrid = function (subjectId) {
    var initDataGrid = {
        url: baseUrl + '/reportSubject/queryHistory',
        fitColumns:false,
        singleSelect:true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        queryParams: {
            subjectId: subjectId
        },
        frozenColumns:[[
            {
                field: "checkbox", title: "编号",  checkbox: true
            }, {
                field: "subjectId", title: "报送主体ID", align: 'center', width: 160
            }
        ]],
        columns: [[
        {
            field: "subsystemId", title: "所属子系统",  align: 'center', hidden: true
        }, {
            field: "subsystemName", title: "所属子系统",  align: 'center', width: 120
        }, {
            field: "reportType", title: "报送方类型", align: 'center', width: 120
        }, {
            field: "folderFlag", title: "是否分文件夹存放",  align: 'center', width: 110
        }, {
            field: "singleTypeFileFlag", title: "是否单一类型报文", align: 'center', width: 110
        }, {
            field: "zipManner", title: "压缩方式",  align: 'center', width: 120
        }, {
            field: "zipMaxSize", title: "压缩包大小限制（M）",  align: 'center', width: 120
        }, {
            field: "downloadFlag", title: "是否允许下载",  align: 'center', width: 100
        }, {
            field: "rptSubmitType", title: "上报方式", align: 'center', width: 100
        }, {
            field: "rptInterfaceType", title: "上报接口类型",  align: 'center', width: 110
        }, {
            field: "statusText", title: "发布状态",  align: 'center', width: 110
        }, {
            field: "status", title: "发布状态",  align: 'center', hidden: true
        }, {
            field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
        }, {
            field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 110
        }, {
            field: "effectDt", title: "生效时间", align: 'center', width: 110
        }, {
            field: "invalidDt", title: "失效时间", align: 'center', width: 110
        }, {
            field: "versionNo", title: "版本号", align: 'center', width: 50
        }]]
    };

    $('#report-subject-history-data-grid').datagrid(initDataGrid);
}