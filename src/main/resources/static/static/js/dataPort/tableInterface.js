/*
 * 数据接口
 * @author wuyu
 */

var tableInterface = tableInterface || {};

tableInterface.init = function() {
    tableInterface.tableInterfaceDataGrid();
    tableInterface.initDataBase();
    // 初始化接口所属业务分类下拉框
    var comboBoxAttributes = [
        getComboboxAttributes('#table-interface-query-bizClsf', true),
        getComboboxAttributes('#table-interface-add-bizClsf', false),
        getComboboxAttributes('#table-interface-update-bizClsf', false)
    ];
    initRepeatDictionaryCode(comboBoxAttributes,'DFI03');
    tableInterface.initCombobox();
}

tableInterface.initCombobox = function() {
    // 初始化接口状态下拉框
    initDictionaryCode('#table-interface-query-status', 'DFI02', true);
    // 初始化接口类型下拉框
    initDictionaryCode('#table-interface-add-type', 'DFI01', false);
}

/**
 * 存储数据接口信息
 * @author wuyu
 */
tableInterface.save = function() {
    var _form = $('#table-interface-add-form');
    if (_form.form('validate')) {
        var ajaxObj = {
            url: baseUrl + '/tableInterface/save',
            data: tableInterface.interfaceAddData()
        }
        customAjaxSubmit(ajaxObj, success, function () {showErrorMessage('新增失败，程序异常。');});
        function success(data) {
            if (data.success) {
                tableInterface.closeWin('#table-interface-add-win');
                $('#table-interface-data-grid').datagrid('reload');
            }
            selectivePrompt(data.success, data.message);
        }
    }
}

/**
 * 打开新增数据接口窗口
 * @author wuyu
 */
tableInterface.openAddWin = function() {
    var _win = $('#table-interface-add-win');
    _win.window({
        width: 740,
        height: 260,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            $('#table-interface-add-form').form('clear');
        }
    });
    _win.window('open');
}

/**
 * 打开更新数据接口窗口
 * @author wuyu
 */
tableInterface.openUpdateWin = function() {
    var _win = $('#table-interface-update-win');
    _win.window({
        width: 740,
        height: 255,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            $('#table-interface-update-form').form('clear');
        }
    });
    _win.window('open');
}

/**
 * 关闭窗口
 * @param id 窗口dom对象ID</br>
 *  例：'#table-interface-add-win'
 * @author wuyu
 */
tableInterface.closeWin = function(id) {
    $(id).window('close');
}

/**
 * 查询单个数据接口对象信息
 * @param id 数据接口对象ID
 * @author wuyu
 */
tableInterface.getById = function(id) {
    var result = {};
    $.ajax({
        url: baseUrl + '/tableInterface/getById',
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
            id: id
        },
        success: function (data) {
            result = data;
        },
        error: function () {
            result['message'] = '查询数据接口信息时发生异常。';
        }
    });
    return result;
}

/**
 * 更新数据接口信息
 * @author wuyu
 */
tableInterface.update = function() {
    var _form = $('#table-interface-update-form');
    if (_form.form('validate')) {
        var ajaxObj = {
            url: baseUrl + '/tableInterface/update',
            data: tableInterface.interfaceUpdateData()
        }
        customAjaxSubmit(ajaxObj, success, function () {showErrorMessage('更新失败，程序异常。');});
        function success(data) {
            if (data.success) {
                tableInterface.closeWin('#table-interface-update-win');
                $('#table-interface-data-grid').datagrid('reload');
            }
            selectivePrompt(data.success, data.message)
        }
    }
}

/**
 * 发布数据接口
 * @param rowData DataGrid中选择的一行数据接口信息对象
 * @author wuyu
 */
tableInterface.releaseOne = function(rowData) {
    var tableId = rowData.tableId;
    var data = tableInterface.getById(tableId);
    var tempMessage = '是否确认发布英文名称为' + rowData.enName + ' 的数据接口?';
    if (data.success) {
        $.messager.confirm('提示信息', tempMessage, function(r){
            if (r) {
                showSubmitProgress();
                $.ajax({
                    url: baseUrl + '/tableInterface/releaseById',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        id: tableId
                    },
                    success: function (result) {
                        if (result.success) {
                            $('#table-interface-data-grid').datagrid('reload');
                        }
                        selectivePrompt(result.success, result.message);
                        closeProgress();
                    },
                    error: function () {
                        showErrorMessage('发布数据接口时发生异常。');
                        closeProgress();
                    }
                })
            }
        });
    } else {
        showWarningMessage(data.message);
    }
}

/**
 * 撤销发布数据接口
 * @param rowData DataGrid中选择的一行数据接口信息对象
 * @author wuyu
 */
tableInterface.repealOne = function(rowData) {
    var tableId = rowData.tableId;
    var data = query(tableId);
    var tempMessage = '是否确认撤销发布英文名称为' + rowData.enName + ' 的数据接口?';
    if (data.success) {
        $.messager.confirm('提示信息', tempMessage, function(r){
            if (r) {
                $.ajax({
                    url: baseUrl + '/tableInterface/repealById',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        id: tableId
                    },
                    success: function (result) {
                        if (result.success) {
                            $('#table-interface-data-grid').datagrid('reload');
                        }
                        selectivePrompt(result.success, result.message);
                    },
                    error: function () {
                        showErrorMessage('撤销发布数据接口时发生异常。');
                    }
                })
            }
        });
    } else {
        showWarningMessage(data.message);
    }

    function query(id) {
        var result = {};
        $.ajax({
            url: baseUrl + '/tableInterface/allowUndoRelease',
            type: 'post',
            dataType: 'json',
            async: false,
            data: {
                id: id
            },
            success: function (data) {
                result = data;
            },
            error: function () {
                result['message'] = '查询数据接口信息时发生异常。';
            }
        });
        return result;
    }
}

/**
 * 删除数据接口
 * @param rowData DataGrid中选择的一行数据接口信息对象
 * @author wuyu
 */
tableInterface.delete = function(rowData) {
    var tableId = rowData.tableId;
    var data = tableInterface.getById(tableId);
    var tempMessage = '是否确认删除英文名称为' + rowData.enName + ' 的数据接口?';
    if (data.success) {
        $.messager.confirm('提示信息', tempMessage, function(r){
            if (r) {
                $.ajax({
                    url: baseUrl + '/tableInterface/deleteById',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        id: tableId
                    },
                    success: function (result) {
                        if (result.success) {
                            $('#table-interface-data-grid').datagrid('reload');
                        }
                        selectivePrompt(result.success, result.message);
                    },
                    error: function () {
                        showErrorMessage('删除数据接口时发生异常。');
                    }
                })
            }
        });
    } else {
        showWarningMessage(data.message);
    }
}

/**
 * 查询接口数据历史版本信息
 * @param id 接口id
 * @param enName 接口名称
 * @author wuyu
 */
tableInterface.queryHistoryVersion = function(id, enName) {
    var _win = $('#table-interface-history-win');
    _win.window({
        title: enName + ' 数据接口历史版本数据'
    })
    _win.window('open');
    tableInterface.historyDataGrid(id);
}

/**
 * 查询数据接口信息
 * @author wuyu
 */
tableInterface.query = function() {
    var queryParams = {
        cnName: $('#table-interface-query-cn-name').textbox('getValue'),
        enName: $('#table-interface-query-en-name').textbox('getValue'),
        dataSorceId: $('#table-interface-query-dataSource').combobox('getValue'),
        tableStatus: $('#table-interface-query-status').combobox('getValue'),
        bizClsf: $('#table-interface-query-bizClsf').combobox('getValue')
    }
    $('#table-interface-data-grid').datagrid('load', queryParams);
}

/**
 * 初始化数据源下拉框
 */
tableInterface.initDataBase = function() {
    var _combobox = [
        getComboboxAttributes('#table-interface-query-dataSource', true),
        getComboboxAttributes('#table-interface-add-dataSource', false),
        getComboboxAttributes('#table-interface-update-dataSource', false)
    ];
    initRepeatDatabase(_combobox);
}

/**
 * 加载数据接口信息数据网格
 * @author wuyu
 */
tableInterface.tableInterfaceDataGrid = function () {
    var initDataGrid = {
        url: baseUrl + '/tableInterface/getList',
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
        frozenColumns : [ [ {
            field: "tableId", title: "编号", width: 50, checkbox: true
        }, {
            field: "enName", title: "英文名称", width: 200, align: 'center',
            formatter: function (value, row, index) {
                return '<a href="javascript: void(0)" onclick="tableFieldOpt.openDataGridWin(\'' + row.tableId + '\', \''
                    + row.enName+'\')" style="text-decoration:none">' + value + '</a>';
            }
        }, {
            field: "cnName", title: "中文名称", width: 200, align: 'center'
        }
        ] ],
        columns: [[{
            field: "dataSorceId", title: "数据源ID", width: 100, align: 'center'
        }, {
            field: "tableComment", title: "接口描述", width: 200, align: 'center'
        }, {
            field: "tableType", title: "接口分类", width: 100, align: 'center'
        }, {
            field: "tableStatus", title: "接口状态", width: 100, align: 'center'
        }, {
            field: "bizClsf", title: "所属业务分类", hidden: true
        }, {
            field: "bizClsfText", title: "所属业务分类", width: 100, align: 'center'
        }, {
            field: "ruleRsTablNm", title: "校验结果存放表名", width: 200, align: 'center'
        }, {
            field: "creator", title: "创建人", width: 80, align: 'center'
        }, {
            field: "createDt", title: "创建时间", width: 110, align: 'center'
        }, {
            field: "finallyModifier", title: "最后修改人", width: 80, align: 'center'
        }, {
            field: "finallyModifyDt", title: "最后修改时间", width: 110, align: 'center'
        }, {
            field: "history", title: "历史版本", width: 100, align: 'center',
            formatter: function (value, row, index) {
                return '<a href="javascript: void(0)" onclick="tableInterface.queryHistoryVersion(\'' + row.tableId + '\', \'' +row.enName+'\')">查看历史版本</a>';
            }
        }]],
        toolbar: [{
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                tableInterface.openAddWin();
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                var row = $('#table-interface-data-grid').datagrid('getSelected');
                if (row == undefined || row == null) {
                    showWarningMessage('请选择一行数据接口数据。');
                    return;
                }
                var result = tableInterface.getById(row.tableId);
                if (result.success) {
                    tableInterface.openUpdateWin();
                    $('#table-interface-update-form').form('load', result.data);
                } else {
                    showWarningMessage(result.message);
                }
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                var row = $('#table-interface-data-grid').datagrid('getSelected');
                if (row == undefined || row == null) {
                    showWarningMessage('请选择一行数据接口数据。');
                    return;
                }
                tableInterface.delete(row);
            }
        }, '-', {
            text: '发布',
            iconCls: 'icon-redo',
            handler: function () {
                var row = $('#table-interface-data-grid').datagrid('getSelected');
                if (row == undefined || row == null) {
                    showWarningMessage('请选择一行数据接口数据。');
                    return;
                }
                tableInterface.releaseOne(row);
            }
        }, '-', {
            text: '撤销发布',
            iconCls: 'icon-undo',
            handler: function () {
                var row = $('#table-interface-data-grid').datagrid('getSelected');
                if (row == undefined || row == null) {
                    showWarningMessage('请选择一行数据接口数据。');
                    return;
                }
                tableInterface.repealOne(row);
            }
        }]
    };

    $('#table-interface-data-grid').datagrid(initDataGrid);
}

/**
 * 加载数据接口历史版本数据网格
 * @param id 数据接口id
 * @author wuyu
 */
tableInterface.historyDataGrid = function (id) {
    var initDataGrid = {
        url: baseUrl + '/tableInterface/getHistoryList',
        fit : true,
        fitColumns: true,
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
            id: id
        },
        columns: [[{
            field: "enName", title: "英文名称",  align: 'center'
        }, {
            field: "cnName", title: "中文名称", align: 'center'
        }, {
            field: "dataSorceId", title: "数据源ID", align: 'center'
        }, {
            field: "tableComment", title: "接口描述", align: 'center'
        }, {
            field: "tableType", title: "接口分类", align: 'center'
        }, {
            field: "tableStatus", title: "接口状态", align: 'center'
        }, {
            field: "bizClsf", title: "所属业务分类", hidden: true
        }, {
            field: "bizClsfText", title: "所属业务分类", align: 'center'
        }, {
            field: "ruleRsTablNm", title: "校验结果存放表名", width: 200, align: 'center'
        }, {
            field: "creator", title: "创建人", align: 'center'
        }, {
            field: "createDt", title: "创建时间", align: 'center'
        }, {
            field: "finallyModifier", title: "最后修改人", align: 'center'
        }, {
            field: "finallyModifyDt", title: "最后修改时间", align: 'center'
        }, {
            field: "effectDt", title: "生效时间", align: 'center'
        }, {
            field: "invalidDt", title: "失效时间", align: 'center'
        }, {
            field: "versionNo", title: "版本号", align: 'center'
        }, {
            field: "historyField", title: "字段查询", align: 'center',
            formatter: function (value, row, index) {
                return '<a href="javascript: void(0)" onclick="tableFieldOpt.queryHistoryVersion(\'' + row.tableId + '\', \'' +row.versionNo+'\')">字段查询</a>';
            }
        }]]
    };

    $('#table-interface-history-data-grid').datagrid(initDataGrid);
}

tableInterface.interfaceAddData = function () {
    return {
        enName: $('#table-interface-add-en-name').textbox('getValue'),
        cnName: $('#table-interface-add-cn-name').textbox('getValue'),
        dataSorceId: $('#table-interface-add-dataSource').combobox('getValue'),
        tableType: $('#table-interface-add-type').combobox('getValue'),
        bizClsf: $('#table-interface-add-bizClsf').combobox('getValue'),
        tableComment: $('#table-interface-add-table-comment').val(),
        ruleRsTablNm: $('#table-interface-add-ruleRsTablNm').textbox('getValue')
    }
}

tableInterface.interfaceUpdateData = function () {
    return {
        enName: $('#table-interface-update-en-name').textbox('getValue'),
        cnName: $('#table-interface-update-cn-name').textbox('getValue'),
        dataSorceId: $('#table-interface-update-dataSource').combobox('getValue'),
        bizClsf: $('#table-interface-update-bizClsf').combobox('getValue'),
        tableComment: $('#table-interface-update-table-comment').val(),
        tableId: $('#table-interface-update-id').val(),
        ruleRsTablNm: $('#table-interface-edit-ruleRsTablNm').textbox('getValue')
    }
}