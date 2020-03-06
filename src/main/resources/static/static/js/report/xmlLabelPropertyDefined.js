function xmlLabelPropertyDefined(baseUrl) {
    var initData = {};

    var _dataGrid = $('#xml-label-property-data-grid');
    var _dataGridWin = $('#xml-label-property-data-grid-win');
    var _editWin = $('#label-property-edit-win');
    var _editForm = $('#label-property-edit-form');
    var _propertyValueType = $('#label-property-propertyValueType');
    var _propertyValueExps = $('#label-property-propertyValueExps');
    var _nullReplaceChar = $('#label-property-nullReplaceChar');

    this.init = function (cacheData) {
        initData = cacheData;
        initDataGrid();
        _dataGridWin.window('open');
        initBtn();
        initCombobox();
    }

    function initBtn() {
        bindClickEvent('label-property-close-btn', function () {
            _editWin.window('close');
        });
    }

    function initCombobox() {
        initDictionaryCode('#label-property-propertyValueType', 'RPT09', false);
        // 属性值类型下拉框
        _propertyValueType.combobox({
           onChange: function (newValue, oldValue) {
               if (newValue != oldValue && newValue.length > 0) {
                   if (newValue === 'RPT0911' || newValue === 'RPT0912' || newValue === 'RPT0910') {
                       showWarningMessage('属性值类型不能选择[下级标签][国密5加密字段][循环填报内容]');
                       _propertyValueType.combobox('setValue', '');
                       return;
                   }
                   // 标签值表达式
                   var _propertyValueExpsRequired = true;
                   var _propertyValueExpsReadonly = false;
                   if (newValue != 'RPT0901' && newValue != 'RPT0903' && newValue != 'RPT0913') {
                       _propertyValueExpsRequired = false;
                       _propertyValueExpsReadonly = true;
                       _propertyValueExps.textbox('clear');
                   }
                   _propertyValueExps.textbox({
                       readonly: _propertyValueExpsReadonly,
                       required: _propertyValueExpsRequired
                   });
                   // 空值替代内容
                   var _nullReplaceCharReadonly = false;
                   if (newValue != 'RPT0901' && newValue != 'RPT0913') {
                       _nullReplaceCharReadonly = true;
                       _nullReplaceChar.textbox('clear');
                   }
                   _nullReplaceChar.textbox({
                       readonly: _nullReplaceCharReadonly
                   });
               }
           }
        });
    }

    function initSave() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(initData.subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        _editForm.form('clear');
       _editWin.window('open');
        bindSaveBtn('save');
    }

    function initUpdate() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(initData.subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var row = rowData();
        if (isNullObject(row)) {
            showWarningMessage('请选择一行需要修改的数据。');
            return;
        }
        var _ajaxObj = {
            url: baseUrl + '/reportXmlLabelProperty/getOne',
            data: {
                subjectId: initData.subjectId,
                fileId: initData.fileId,
                labelId: initData.labelId,
                propertyId: row.propertyId
            }
        }
        customAjaxSubmit(_ajaxObj, function (result) {
            _editForm.form('load', result);
            _editWin.window('open');
            bindSaveBtn('update');
        });
    }

    function bindSaveBtn(url) {
        bindClickEvent('label-property-save-btn', function () {
            saveOrUpdate(url);
        });
    }

    function saveOrUpdate(url) {
        if (_editForm.form('validate')) {
            $.messager.confirm('提示信息', '确认保存当前标xml签属性配置信息？', function(r){
                if (r) {
                    var _ajaxObj = {
                        url: baseUrl + '/reportXmlLabelProperty/' + url,
                        data: {
                            subjectId: initData.subjectId,
                            fileId: initData.fileId,
                            labelId: initData.labelId,
                            propertyId: $('#label-property-edit-id').val(),
                            sequenceNo: $('#label-property-sequence-no').numberbox('getValue'),
                            propertyNameCn: $('#label-property-name-cn').textbox('getValue'),
                            propertyNameEn: $('#label-property-name-en').textbox('getValue'),
                            propertyValueType: _propertyValueType.combobox('getValue'),
                            propertyValueExps: _propertyValueExps.textbox('getValue'),
                            nullReplaceChar: _nullReplaceChar.textbox('getValue'),
                            comnt: $('#label-property-comment').textbox('getValue')
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

    function deleteProperty() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(initData.subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var row = rowData();
        if (isNullObject(row)) {
            showWarningMessage('请选择一行需要删除的数据。');
            return;
        }
        $.messager.confirm('提示信息', '确认删除勾选的xml标签属性配置信息？', function(r) {
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportXmlLabelProperty/delete',
                    data: {
                        subjectId: initData.subjectId,
                        fileId: initData.fileId,
                        labelId: initData.labelId,
                        propertyId: row.propertyId
                    }
                }
                customAjaxSubmit(_ajaxObj, successCallback);
            }
        });
    }

    function successCallback(result) {
        selectivePrompt(result.success, result.message);
        if (result.success) {
            _dataGrid.datagrid('reload');
        }
    }

    function rowData() {
       return _dataGrid.datagrid('getSelected');
    }

    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportXmlLabelProperty/query',
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
                subjectId: initData.subjectId,
                fileId: initData.fileId,
                labelId: initData.labelId
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "propertyId", title: "标签属性ID",  align: 'center', width: 170
            }, {
                field: "propertyNameCn", title: "中文名称",  align: 'center', width: 200
            }, {
                field: "propertyNameEn", title: "英文名称",  align: 'center', width: 100
            }
            ]],
            columns: [[
                {
                    field: "subjectId", title: "所属报送主体ID", hidden: true
                }, {
                    field: "fileId", title: "所属报文ID", hidden: true
                }, {
                    field: "labelId", title: "所属标签ID", hidden: true
                }, {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "propertyValueType", title: "属性值类型", align: 'center', width: 150
                }, {
                    field: "propertyValueExps", title: "属性值表达式", align: 'center', width: 150
                }, {
                    field: "nullReplaceChar", title: "空值替代内容",  align: 'center', width: 150
                }, {
                    field: "comnt", title: "属性说明",  align: 'center', width: 300, formatter: function (value, row, index) {
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
                    deleteProperty();
                }
            }]
        };
        _dataGrid.datagrid(initDataGrid);
    }
}