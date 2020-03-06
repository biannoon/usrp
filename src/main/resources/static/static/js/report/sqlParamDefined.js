function sqlParamDefined(baseUrl) {
    var _subjectData = {};

    var _dataGridWin = $('#sql-param-data-grid-win');
    var _dataGrid = $('#sql-param-data-grid');
    var _paramType = $('#sql-param-edit-paramType');
    var _replaceSqlId = $('#sql-param-edit-replaceSqlId');
    var _replaceField = $('#sql-param-edit-replaceField');
    var _editWin = $('#sql-param-edit-win');
    var _editForm = $('#sql-param-edit-form');

    this.init = function (subjectData) {
        _subjectData = subjectData;
        initDataGrid();
        _dataGridWin.window('open');
        initCombobox();
        initComboTree();
        initBtn();
    }

    function initComboTree() {
        sqlDefinedOpt.initComboTree('sql-param-edit-replaceSqlId', _subjectData.subjectId);
        $('#sql-param-edit-replaceSqlId').combotree({
            onChange: function (newValue, oldValue) {
                if (newValue != oldValue && newValue.length > 0) {
                    initReplaceFieldCombobox(newValue);
                }
            }
        });
    }

    function initCombobox() {
        initDictionaryCode('#sql-param-edit-paramType', 'RPT03', false);
        _paramType.combobox({
            onChange: function (newValue, oldValue) {
                if (newValue != oldValue && newValue.length > 0) {
                    var _readonly = true;
                    var _required = false;
                    if (newValue === 'RPT0301') {
                        _readonly = false;
                        _required = true;
                    }
                    _replaceSqlId.combotree({
                        required: _required,
                        readonly: _readonly
                    });
                    _replaceField.textbox({
                        required: _required,
                        readonly: _readonly
                    });
                }
            }
        });
    }

    function initReplaceFieldCombobox(sqlId) {
        var _ajaxObj = {
            url: baseUrl + '/combobox/initMetadata',
            data: {
                sqlId: sqlId
            }
        };
        customAjaxSubmit(_ajaxObj, function (result) {
            initComboboxData('#sql-param-edit-replaceField', result, false);
        })
    }

    function initBtn() {
        bindClickEvent('sql-param-edit-close-btn', function () {
            _editWin.window('close');
        });
    }

    function initSave() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectData.status)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        _editForm.form('clear');
        _editWin.window('open');
        bindSaveBtn('save');
    }

    function initUpdate() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectData.status)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var rows = getChecked();
        if (isEmptyArray(rows) || rows.length > 1) {
            showWarningMessage('请选择一行需要修改的SQL参数信息。');
            return;
        }
        loadSqlParamData(rows[0].paramEnName);
    }

    function loadSqlParamData(paramEnName) {
        var _ajaxObj = {
            url: baseUrl + '/reportSqlParam/getOne',
            data: {
                subjectId: _subjectData.subjectId,
                paramEnName: paramEnName
            }
        };
        customAjaxSubmit(_ajaxObj, function (result) {
            _editForm.form('load', result);
            _editWin.window('open');
            bindSaveBtn('update');
        });
    }

    function saveOrUpdate(url) {
        if (_editForm.form('validate')) {
            $.messager.confirm('提示信息', '确认保存当前标SQL参数配置信息？', function(r){
                if (r) {
                    var _ajaxObj = {
                        url: baseUrl + '/reportSqlParam/' + url,
                        data: {
                            subjectId: _subjectData.subjectId,
                            paramEnName: $('#sql-param-edit-paramEnName').textbox('getValue'),
                            oldParamEnName: $('#sql-param-edit-oldParamEnName').val(),
                            paramCnName: $('#sql-param-edit-paramCnName').textbox('getValue'),
                            paramType: _paramType.combobox('getValue'),
                            replaceSqlId: _replaceSqlId.combobox('getValue'),
                            replaceField: _replaceField.combobox('getValue'),
                            comnt: $('#sql-param-edit-comment').textbox('getValue')
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

    function initDelete() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectData.status)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var rows = getChecked();
        if (isEmptyArray(rows)) {
            showWarningMessage('请选择一行需要删除的数据。');
            return;
        }
        var paramEnNameArray = [];
        $(rows).each(function (i) {
            paramEnNameArray.push(rows[i].paramEnName);
        });
        var data = {subjectId: _subjectData.subjectId, paramEnNameList: paramEnNameArray};
        $.messager.confirm('提示信息', '确认删除勾选的SQL参数配置信息？', function(r) {
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportSqlParam/delete',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                }
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
        bindClickEvent('sql-param-edit-save-btn', function () {
            saveOrUpdate(url);
        });
    }

    function getChecked() {
        return _dataGrid.datagrid('getChecked');
    }

    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportSqlParam/query',
            fit : true,
            fitColumns: false,
            border: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 20],
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            remoteSort: false,
            striped: true,
            nowrap: false,
            queryParams: {
                subjectId: _subjectData.subjectId
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "paramEnName", title: "英文名称",  align: 'center', width: 100
            }, {
                field: "paramCnName", title: "中文名称",  align: 'center', width: 200
            }
            ]],
            columns: [[
                {
                    field: "subjectId", title: "所属报送主体ID", hidden: true
                }, {
                    field: "paramType", title: "参数类型", align: 'center', width: 180
                }, {
                    field: "replaceSqlId", title: "参数占位字段所属SQL ID", align: 'center', width: 170
                }, {
                    field: "replaceField", title: "参数占位字段名称", align: 'center', width: 150
                }, {
                    field: "comnt", title: "参数说明",  align: 'center', width: 300, formatter: function (value, row, index) {
                        return reportSubjectOpt.filterData(value);
                    }
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                }]],
            toolbar: [{
                text: '新增',
                iconCls: 'icon-add',
                handler: function () {
                    initSave();
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
                    initDelete();
                }
            }]
        };
        _dataGrid.datagrid(initDataGrid);
    }
}