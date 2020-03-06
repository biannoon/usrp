function segmentDefined(baseUrl) {
    var _subjectId = '';
    var _fileId = '';
    var _subjectStatus = '';

    var _editWin = $('#segment-edit-win');
    var _editForm = $('#segment-edit-form');
    var _dataGridWin = $('#segment-data-grid-win');
    var _dataGrid = $('#segment-data-grid');
    var _splitCharType = $('#segment-edit-splitCharType');
    var _splitChar = $('#segment-edit-splitChar');

    this.init = function(fileData) {
        initDataGrid(fileData.subjectId, fileData.fileId);
        _dataGridWin.window({
            title: fileData.fileName + ' 文件结构段配置信息'
        });
        _dataGridWin.window('open');
        initEditWin();
        initCombobox();
        sqlDefinedOpt.initComboTree('segment-edit-relation-sql', fileData.subjectId);
        _subjectId = fileData.subjectId;
        _fileId = fileData.fileId;
        var subjectData = reportSubjectOpt.getOneRowData();
        _subjectStatus = subjectData.status;
        $('#segment-edit-close-btn').unbind().bind('click', function () {
            $('#segment-edit-win').window('close');
        });
    }

    function initCombobox() {
        var _attributes = [getComboboxAttributes('#segment-edit-loop-segment', false),
            getComboboxAttributes('#segment-edit-lineBreak', false),
            getComboboxAttributes('#segment-edit-mainSegment', false)];
        initRepeatDictionaryCode(_attributes, 'SYS02');
        initDictionaryCode('#segment-edit-splitType', 'RPT07', false);
        initDictionaryCode('#segment-edit-splitCharType', 'RPT08', false);
        // 分隔类型下拉框变更事件
        $('#segment-edit-splitType').combobox({
           onChange: function (newValue, oldValue) {
               if (newValue != oldValue && !isEmptyChar(newValue)) {
                   var readonly = false;
                   var required = true;
                   if (newValue === 'RPT0701') {
                       readonly = true;
                       required = false;
                       _splitCharType.combobox('clear');
                       _splitChar.textbox('clear');
                   }
                    _splitCharType.combobox({
                        readonly: readonly,
                        required: required
                    });
                   _splitChar.textbox({
                       readonly: readonly,
                       required: required
                   });
               }
           }
        });
        // 是否换行下拉框
        $('#segment-edit-lineBreak').combobox({
           onChange: function (newValue, oldValue) {
               if (newValue != oldValue && !isEmptyChar(newValue)) {
                   var readonly = false;
                   var required = true;
                   if (newValue === 'SYS0202') {
                       readonly = true;
                       required = false;
                       $('#segment-edit-lineBreakChar').textbox('clear');
                   }
                   $('#segment-edit-lineBreakChar').textbox({
                       readonly: readonly,
                       required: required
                   });
               }
           }
        });
        // 是否循环结构段
        $('#segment-edit-loop-segment').combobox({
            onChange: function (newValue, oldValue) {
                if (newValue != oldValue && newValue.length > 0) {
                    var _relationalSqlId = $('#segment-edit-relation-sql');
                    var required = false;
                    if (newValue === 'SYS0201') {
                        required = true;
                    }
                    _relationalSqlId.combotree({
                        required: required
                    });
                }
            }
        });
    }

    function initEditWin() {
        _editWin.window({
            onBeforeClose: function() {
                _editForm.form('clear');
            }
        });
    }

    function initSaveWin() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        _editWin.window({
            iconCls:'icon-add',
            title: '新增报文文件结构段配置信息'
        });
        _editWin.window('open');
        bindClickEvent('segment-edit-save-btn', function () {
            saveOrUpdate('save');
        })
    }

    function initUpdateWin() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var segmentData = getSelected();
        if (!isNullObject(segmentData)) {
            getOne(segmentData.segmentId);
        }
    }

    function getOne(segmentId) {
        var _ajaxObj = {
            url: baseUrl + '/reportSegment/getOne',
            data: {
                subjectId: _subjectId,
                fileId: _fileId,
                segmentId: segmentId
            }
        };
        customAjaxSubmit(_ajaxObj, function (result) {
            _editForm.form('load', result);
            _editWin.window({
                iconCls:'icon-edit',
                title: '更新报文文件结构段配置信息'
            });
            _editWin.window('open');
            bindClickEvent('segment-edit-save-btn', function () {
                saveOrUpdate('update');
            })
        })
    }

    function saveOrUpdate(url) {
        if (_editForm.form('validate')) {
            $.messager.confirm('提示信息', '确认保存当前结构段配置信息？', function(r){
                if (r) {
                    var _ajaxObj = {
                        url: baseUrl + '/reportSegment/' + url,
                        data: {
                            subjectId: _subjectId,
                            fileId: _fileId,
                            segmentId: $('#segment-edit-id').val(),
                            sequenceNo: $('#segment-edit-sequence-no').numberbox('getValue'),
                            segmentName: $('#segment-edit-file-name').textbox('getValue'),
                            loopSegment: $('#segment-edit-loop-segment').combobox('getValue'),
                            lineBreak: $('#segment-edit-lineBreak').combobox('getValue'),
                            lineBreakChar: $('#segment-edit-lineBreakChar').textbox('getValue'),
                            splitType: $('#segment-edit-splitType').combobox('getValue'),
                            splitCharType: $('#segment-edit-splitCharType').combobox('getValue'),
                            splitChar: $('#segment-edit-splitChar').textbox('getValue'),
                            mainSegment: $('#segment-edit-mainSegment').combobox('getValue'),
                            relationSqlId: $('#segment-edit-relation-sql').combotree('getValue'),
                            comnt: $('#segment-edit-comment').textbox('getValue')
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

    function deleteSegmentData() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var segmentData = getSelected();
        if (!isNullObject(segmentData)) {
            $.messager.confirm('提示信息', '确认删除勾选的结构段配置信息？', function(r){
                if (r) {
                    var _ajaxObj = {
                        url: baseUrl + '/reportSegment/delete',
                        data: {
                            subjectId: _subjectId,
                            fileId: _fileId,
                            segmentId: segmentData.segmentId
                        }
                    };
                    customAjaxSubmit(_ajaxObj, successCallback);
                }
            });
        }
    }

    function successCallback(result) {
        if (result.success) {
            _dataGrid.datagrid('reload');
        }
        selectivePrompt(result.success, result.message);
    }

    function setDataItem() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var segmentData = getSelected();
        if (!isNullObject(segmentData)) {
            var cacheData = {
                subjectId: _subjectId,
                fileId: _fileId,
                segmentId: segmentData.segmentId,
                subjectStatus: _subjectStatus,
                splitType: segmentData.splitType
            };
            new dataItemDefined(baseUrl).init(cacheData);
        }
    }

    function getSelected() {
        var row = _dataGrid.datagrid('getSelected');
        if (isNullObject(row)) {
            showWarningMessage('请选择一行报文文件信息数据。');
        }
        return row;
    }

    function initDataGrid(subjectId, fileId) {
        var initDataGrid = {
            url: baseUrl + '/reportSegment/query',
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
                subjectId: subjectId,
                fileId: fileId
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "segmentId", title: "报文文件ID", hidden: true
            }, {
                field: "segmentName", title: "结构段名称",  align: 'center', width: 200
            }
            ]],
            columns: [[
                {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "loopSegment", title: "是否循环结构段", hidden: true
                }, {
                    field: "loopSegmentText", title: "是否循环结构段", align: 'center', width: 100
                }, {
                    field: "mainSegment", title: "是否主要报送内容段", hidden: true
                }, {
                    field: "mainSegmentText", title: "是否主要报送内容段", align: 'center', width: 120
                }, {
                    field: "lineBreak", title: "是否换行", hidden: true
                }, {
                    field: "lineBreakText", title: "是否换行", align: 'center', width: 100
                }, {
                    field: "splitType", title: "分隔类型", hidden: true
                }, {
                    field: "splitTypeText", title: "分隔类型", align: 'center', width: 100
                }, {
                    field: "splitCharType", title: "分隔符类型", align: 'center', width: 100
                }, {
                    field: "relationSqlId", title: "关联SQL ID",  align: 'center', width: 160
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                }]],
            toolbar: [{
                text: '新增',
                iconCls: 'icon-add',
                handler: function () {
                    initSaveWin();
                }
            }, '-', {
                text: '修改',
                iconCls: 'icon-edit',
                handler: function () {
                    initUpdateWin();
                }
            }, '-', {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    deleteSegmentData();
                }
            }, '-', {
                text: '数据项配置',
                iconCls: 'pic_336',
                handler: function () {
                    setDataItem();
                }
            }]
        };
        _dataGrid.datagrid(initDataGrid);
    }
}