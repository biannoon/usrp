function sqlMetadata(baseUrl) {
    var _subjectStatus = {};
    var _sqlId = '';

    var _editWin = $('#sql-metadata-edit-win');
    var _editForm = $('#sql-metadata-edit-form');
    var _dataGrid = $('#sql-metadata-data-grid');
    var _dataGridWin = $('#sql-metadata-data-grid-win');

    this.init = function (cacheData) {
        _subjectStatus = cacheData.subjectStatus;
        _sqlId = cacheData.sqlId;
        initDataGrid();
        _dataGridWin.window('open');
        initBtn();
    }

    function initBtn() {
        bindClickEvent('sql-metadata-edit-close-btn', function () {
            _editWin.window('close');
        });
        bindClickEvent('sql-metadata-edit-save-btn', function () {
            update();
        });
    }

    function initUpdate() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var rows = _dataGrid.datagrid('getChecked');
        if (isEmptyArray(rows)) {
            showWarningMessage('请选择一行元数据。');
            return;
        }
        _editForm.form('clear');
        _editForm.form('load', rows[0]);
        _editWin.window('open');
    }

    function update() {
        $.messager.confirm('提示信息', '确认更新当前元数据配置信息？', function(r) {
            if (r) {
                var data = {
                    sqlId: _sqlId,
                    fieldCnName: $('#sql-metadata-edit-fieldCnName').textbox('getValue'),
                    fieldEnName: $('#sql-metadata-edit-fieldEnName').textbox('getValue')
                };
                var _ajaxObj = {
                    url: baseUrl + '/reportSqlMetaData/update',
                    contentType: 'application/json; charset=utf-8',
                    data: JSON.stringify(data)
                };
                customAjaxSubmit(_ajaxObj, function (result) {
                    if (result.success) {
                        _editWin.window('close');
                        _dataGrid.datagrid('reload');
                    }
                    selectivePrompt(result.success, result.message);
                });
            }
        });
    }

    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportSqlMetaData/query',
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
                sqlId: _sqlId
            },
            frozenColumns:[[
            ]],
            columns: [[{
                    field: "checkbox", title: "编号",  checkbox: true
                },{
                    field: "sqlId", title: "sqlId", hidden: true
                },{
                    field: "fieldEnName", title: "英文名称",  align: 'center', width: 200
                }, {
                    field: "fieldCnName", title: "中文名称",  align: 'center', width: 200
                }, {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                }]],
            toolbar: [{
                text: '修改',
                iconCls: 'icon-edit',
                handler: function () {
                    initUpdate();
                }
            }]
        };
        _dataGrid.datagrid(initDataGrid);
    }
}