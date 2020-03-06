function dataItemDefined(baseUrl) {
    var segmentData = {};

    var _dataGridWin = $('#data-item-data-grid-win');
    var _dataGrid = $('#data-item-data-grid');
    var _editWin = $('#data-item-edit-win');
    var _editForm = $('#data-item-edit-form');
    var _fixLocation = $('#data-item-fixLocation');
    var _fixCharType = $('#data-item-fixCharType');
    var _fixChar = $('#data-item-fixChar');
    var _dataItemType = $('#data-item-dataItemType');
    var _dataItemExps = $('#data-item-dataItemExps');
    var _nullReplaceChar = $('#data-item-nullReplaceChar');
    var _loopSplitCharType = $('#data-item-splitCharType');
    var _loopSplitChar = $('#data-item-splitChar');
    var _relationSqlId = $('#data-item-relation-sql');
    var _dataLength = $('#data-item-data-length');
    var _loopItem = $('#data-item-loop-segment');

    this.init = function (cacheData) {
        segmentData = cacheData;
        initDataGrid();
        _dataGridWin.window('open');
        initBtn();
        initCombobox();
        sqlDefinedOpt.initComboTree('data-item-relation-sql', segmentData.subjectId);

    }

    function initCombobox() {
        initDictionaryCode('#data-item-dataItemType', 'RPT09', false);
        initDictionaryCode('#data-item-loop-segment', 'SYS02', false);
        initDictionaryCode('#data-item-splitCharType', 'RPT08', false);
        initDictionaryCode('#data-item-fixLocation', 'RPT10', false);
        initDictionaryCode('#data-item-fixCharType', 'RPT11', false);
        initFixLocationCombobox();
        initDataItemTypeCombobox();
        initLoopItemCombobox();
    }

    function initFixLocationCombobox() {
        var required = true;
        var readonly = false;
        if (segmentData.splitType != 'RPT0701') {// 分隔类型为非定长分隔
            required = false;
            readonly = true;
        }
        _fixLocation.combobox({
            required: required,
            readonly: readonly
        });
        _fixCharType.combobox({
            required: required,
            readonly: readonly
        });
        _fixChar.textbox({
            required: required,
            readonly: readonly
        });
        _dataLength.numberbox({
            required: required,
            readonly: readonly
        });
    }

    function initDataItemTypeCombobox() {
        _dataItemType.combobox({
            onChange: function(newValue, oldValue) {
                if (newValue != oldValue && newValue.length > 0) {
                    if (newValue === 'RPT0911') {
                        showWarningMessage('当前不可选择[下级标签]');
                        _dataItemType.combobox('setValue', '');
                        return;
                    }
                    // 数据项内容表达式
                    var _dataItemExpsRequired = true;
                    var _dataItemExpsReadonly = false;
                    if (newValue != 'RPT0901' && newValue != 'RPT0903') {
                        _dataItemExpsRequired = false;
                        _dataItemExpsReadonly = true;
                        _dataItemExps.textbox('clear');
                    }
                    _dataItemExps.textbox({
                        readonly: _dataItemExpsReadonly,
                        required: _dataItemExpsRequired
                    });
                    // 空值替代内容
                    var _nullReplaceCharReadonly = false;
                    if (newValue != 'RPT0901' && newValue != 'RPT0910' && newValue != 'RPT0913') {
                        _nullReplaceCharReadonly = true;
                        _nullReplaceChar.textbox('clear');
                    }
                    _nullReplaceChar.textbox({
                        readonly: _nullReplaceCharReadonly
                    });
                    // 是否循环填报数据项
                    var _loopLabelCharReadonly = true;
                    if (newValue != 'RPT0910') {// 非循环填报内容
                        _loopLabelCharReadonly = false;
                    }
                    _loopItem.combobox({
                        readonly: _loopLabelCharReadonly
                    });
                    _loopItem.combobox('setValue', 'SYS0201');
                }
            }
        });
    }

    function initLoopItemCombobox() {
        _loopItem.combobox({
            onChange: function (newValue, oldValue) {
                if (newValue != oldValue && newValue.length > 0) {
                    var _loopSplitCharRequired = true;
                    var _loopSplitCharReadonly = false;
                    if (newValue != 'SYS0201') {
                        _loopSplitCharRequired = false;
                        _loopSplitCharReadonly = true;
                        _loopSplitCharType.combobox('setValue', '');
                        _loopSplitChar.textbox('clear');
                        _relationSqlId.combotree('setValue', '');
                    }
                    _loopSplitCharType.combobox({
                        readonly: _loopSplitCharReadonly,
                        required: _loopSplitCharRequired
                    });
                    _loopSplitChar.textbox({
                        readonly: _loopSplitCharReadonly,
                        required: _loopSplitCharRequired
                    });
                    _relationSqlId.combotree({
                        readonly: _loopSplitCharReadonly,
                        required: _loopSplitCharRequired
                    });
                }
            }
        });
    }
    
    function initBtn() {
        bindClickEvent('data-item-close-btn', function () {
            _editWin.window('close');
        });
    }

    function initAddOperation() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(segmentData.subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        _editForm.form('clear');
        _editWin.window('open');
        bindSaveBtn('save');
    }

    function initUpdate() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(segmentData.subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var row = rowData();
        if (isNullObject(row)) {
            showWarningMessage('请选择一行需要修改的数据。');
            return;
        }
        var _ajaxObj = {
            url: baseUrl + '/reportDataItem/getOne',
            data: {
                subjectId: segmentData.subjectId,
                fileId: segmentData.fileId,
                segmentId: segmentData.segmentId,
                dataItemId: row.dataItemId
            }
        }
        customAjaxSubmit(_ajaxObj, function (result) {
            _editForm.form('load', result);
            _editWin.window('open');
            bindSaveBtn('update');
        });
    }

    function saveOrUpdate(url) {
        if (_editForm.form('validate')) {
            $.messager.confirm('提示信息', '确认保存当前数据项配置信息？', function(r){
                if (r) {
                    var _ajaxObj = {
                        url: baseUrl + '/reportDataItem/' + url,
                        data: {
                            subjectId: segmentData.subjectId,
                            fileId: segmentData.fileId,
                            segmentId: segmentData.segmentId,
                            dataItemId: $('#data-item-edit-id').val(),
                            dataItemName: $('#data-item-name').textbox('getValue'),
                            sequenceNo: $('#data-item-sequence-no').numberbox('getValue'),
                            dataItemType: _dataItemType.textbox('getValue'),
                            dataItemExps: _dataItemExps.textbox('getValue'),
                            nullReplaceChar: _nullReplaceChar.textbox('getValue'),
                            loopItem: _loopItem.combobox('getValue'),
                            splitCharType: _loopSplitCharType.combobox('getValue'),
                            splitChar: _loopSplitChar.textbox('getValue'),
                            dataLength: isEmptyChar(_dataLength.numberbox('getValue')) ? 0 : _dataLength.numberbox('getValue'),
                            relationSqlId: _relationSqlId.combotree('getValue'),
                            fixLocation: _fixLocation.combobox('getValue'),
                            fixCharType: _fixCharType.combobox('getValue'),
                            fixChar: _fixChar.textbox('getValue'),
                            comnt: $('#data-item-comment').textbox('getValue')
                        }
                    };
                    customAjaxSubmit(_ajaxObj, function (result) {
                        if (result.success) {
                            _editWin.window('close');
                        }
                        successCallback(result);
                    });
                }
            });
        }
    }

    function rowData() {
        return _dataGrid.datagrid('getSelected');
    }

    function deleteDataItem() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(segmentData.subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var row = rowData();
        if (isNullObject(row)) {
            showWarningMessage('请选择一行需要删除的数据。');
            return;
        }
        $.messager.confirm('提示信息', '确认删除勾选的数据项配置信息？', function(r) {
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportDataItem/delete',
                    data: {
                        subjectId: segmentData.subjectId,
                        fileId: segmentData.fileId,
                        segmentId: segmentData.segmentId,
                        dataItemId: row.dataItemId
                    }
                };
                customAjaxSubmit(_ajaxObj, successCallback);
            }
        });
    }

    function successCallback(result) {
        if (result.success) {
            _dataGrid.datagrid('reload');
        }
        selectivePrompt(result.success, result.message);
    }

    function bindSaveBtn(url) {
        bindClickEvent('data-item-save-btn', function () {
            saveOrUpdate(url);
        });
    }

    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportDataItem/query',
            fit : true,
            fitColumns: false,
            border: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 20],
            singleSelect: true,
            checkOnSelect: true,
            selectOnCheck: true,
            remoteSort: false,
            striped: true,
            nowrap: false,
            queryParams: {
                subjectId: segmentData.subjectId,
                fileId: segmentData.fileId,
                segmentId: segmentData.segmentId
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "dataItemId", title: "数据项ID",  align: 'center', width: 170
            }, {
                field: "dataItemName", title: "中文名称",  align: 'center', width: 200
            }
            ]],
            columns: [[
                {
                    field: "subjectId", title: "所属报送主体ID", hidden: true
                }, {
                    field: "fileId", title: "所属报文ID", hidden: true
                }, {
                    field: "segmentId", title: "所属结构段ID", hidden: true
                }, {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "dataItemType", title: "数据项类型", align: 'center', width: 150
                }, {
                    field: "nullReplaceChar", title: "空值替代内容", align: 'center', width: 150
                }, {
                    field: "loopItem", title: "是否循环填报数据项", align: 'center', width: 150
                }, {
                    field: "splitCharType", title: "循环填报内容分隔符类型", align: 'center', width: 150
                }, {
                    field: "splitChar", title: "循环填报内容分隔符表达式", align: 'center', width: 150
                }, {
                    field: "dataLength", title: "数据项长度",  align: 'center', width: 150
                }, {
                    field: "fixLocation", title: "定长补位位置",  align: 'center', width: 150
                }, {
                    field: "fixCharType", title: "定长补位字符类型",  align: 'center', width: 150
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                }]],
            toolbar: [{
                text: '新增',
                iconCls: 'icon-add',
                handler: function () {
                    initAddOperation();
                }
            }, '-', {
                text: '修改',
                iconCls: 'icon-edit',
                handler: function () {
                    initUpdate();
                }
            }, '-', {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    deleteDataItem();
                }
            }]
        };
        _dataGrid.datagrid(initDataGrid);
    }
}