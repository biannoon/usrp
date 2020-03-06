/**
 * 数据接口字段信息
 * @author wuyu
 */

var tableFieldOpt = tableFieldOpt || {};

tableFieldOpt.interfaceName = '';
tableFieldOpt.interfaceId = '';

/**
 * 打开新增字段窗口
 * @author wuyu
 */
tableFieldOpt.openAddWin = function () {
    var verifyResult = tableFieldOpt.verifyEditOperation(tableFieldOpt.interfaceId);
    if (!verifyResult.success) {
        showWarningMessage(verifyResult.message);
        return;
    }
    var _win = $('#table-field-add-win');
    _win.window({
        width: 640,
        height: 230,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        title: tableFieldOpt.interfaceName + ' 新增字段',
        onBeforeClose: function() {
            tableFieldOpt.clearData();
        }
    });
    $('#table-field-add-interfaceId').val(tableFieldOpt.interfaceId);
    _win.window('open');
}

/**
 * 清楚缓存数据
 * @author wuyu
 */
tableFieldOpt.clearData = function () {
    //清空新增字段信息窗口数据
    $('#table-field-add-form').form('clear');
    //清空更新字段信息窗口数据
    $('#table-field-update-form').form('clear');
    //清空导入字段信息窗口数据
    $('#table-field-import-file').filebox('clear');
    $('#table-field-import-interfaceId').val('');
}

/**
 * 关闭窗口
 * @param id 窗口dom对象id
 * @author wuyu
 */
tableFieldOpt.closeWin = function (id) {
    $(id).window('close');
}

/**
 * 存储字段信息
 * @author wuyu
 */
tableFieldOpt.save = function () {
    var _form = $('#table-field-add-form');
    if (_form.form('validate')) {
        var ajaxObj = {
            url: baseUrl + '/tableField/save',
            data: tableFieldOpt.fieldAddData()
        }
        customAjaxSubmit(ajaxObj, tableFieldOpt.successCallback, tableFieldOpt.error);
    }
}

/**
 * 打开字段信息数据网格窗口
 * @param interfaceId 数据接口id
 * @param interfaceName 数据接口名
 * @author wuyu
 */
tableFieldOpt.openDataGridWin = function(interfaceId, interfaceName) {
    tableFieldOpt.interfaceId = interfaceId;
    tableFieldOpt.interfaceName = interfaceName;
    var _win = $('#table-field-data-grid-win');
    _win.window({
        title: tableFieldOpt.interfaceName + ' 数据接口字段信息',
        modal: true,
        onBeforeClose: function() {
            tableFieldOpt.interfaceName = '';
            tableFieldOpt.interfaceId = '';
            tableFieldOpt.closeWin('#table-field-add-win');
            tableFieldOpt.closeWin('#table-field-update-win');
        }
    });
    _win.window('open');
    tableFieldOpt.tableFieldDataGrid(interfaceId);
    tableFieldOpt.initCombobox();
}

/**
 * 打开更新字段信息窗口
 * @author wuyu
 */
tableFieldOpt.openUpdateWin = function() {
    var verifyResult = tableFieldOpt.verifyEditOperation(tableFieldOpt.interfaceId);
    if (!verifyResult.success) {
        showWarningMessage(verifyResult.message);
        return;
    }
    var checkedItems = $('#table-field-data-grid').datagrid('getChecked');
    if (checkedItems == undefined || checkedItems == null || checkedItems.length == 0) {
        showWarningMessage('请选择一行字段数据。');
        return;
    }
    if (checkedItems.length > 1) {
        showWarningMessage('修改字段信息时只能选择一行字段数据。');
        return;
    }
    var row = checkedItems[0];
    show(row['fieldId']);
    function show(id) {
        $.ajax({
            url: baseUrl + '/tableField/getById',
            type: 'post',
            dataType: 'json',
            data: {
                id: id
            },
            success: function (result) {
                if (!result.success) {
                    showErrorMessage(result.message);
                    return;
                }
                var _win = $('#table-field-update-win');
                _win.window({
                    width: 640,
                    height: 230,
                    collapsible: false,
                    maximizable: false,
                    minimizable: false,
                    resizable: false,
                    modal: true,
                    title: tableFieldOpt.interfaceName + ' 更新字段',
                    onBeforeClose: function() {
                        tableFieldOpt.clearData();
                    }
                });
                _win.window('open');
                $('#table-field-update-form').form('load', result.data);
            }
        })
    }
}

tableFieldOpt.update = function () {
    var _form = $('#table-field-update-form');
    if (_form.form('validate')) {
        var ajaxObj = {
            url: baseUrl + '/tableField/update',
            data: tableFieldOpt.fieldUpdateData()
        }
        customAjaxSubmit(ajaxObj, tableFieldOpt.successCallback, tableFieldOpt.error);
    }
}

tableFieldOpt.successCallback = function(data) {
    if (data.success) {
        tableFieldOpt.closeWin('#table-field-update-win');
        tableFieldOpt.closeWin('#table-field-add-win');
        tableFieldOpt.closeWin('#table-field-import-win');
        $('#table-field-data-grid').datagrid('reload');
    }
    selectivePrompt(data.success, data.message);
}

tableFieldOpt.error = function() {
    showErrorMessage('执行失败，程序异常。');
}

/**
 * 删除字段信息数据
 * @author wuyu
 */
tableFieldOpt.delete = function() {
    var verifyResult = tableFieldOpt.verifyEditOperation(tableFieldOpt.interfaceId);
    if (!verifyResult.success) {
        showWarningMessage(verifyResult.message);
        return;
    }
    var checkedItems = $('#table-field-data-grid').datagrid('getChecked');
    if (checkedItems == undefined || checkedItems == null || checkedItems.length == 0) {
        showWarningMessage('请选择一行字段数据。');
        return;
    }
    var ids = [];
    $.each(checkedItems, function(index, item){
        ids.push(item['fieldId']);
    });
    var allId = ids.join(',');
    var tempMessage = '确认删除已勾选的字段信息数据？';
    $.messager.confirm('提示信息', tempMessage, function(r){
        if (r) {
            $.ajax({
                url: baseUrl + '/tableField/deleteBatch',
                type: 'post',
                dataType: 'json',
                data: {
                    ids: allId
                },
                success: function (result) {
                    if (result.success) {
                        $('#table-field-data-grid').datagrid('reload');
                    }
                    selectivePrompt(result.success, result.message);
                },
                error: function () {
                    showErrorMessage('删除数据接口字段时发生异常。');
                }
            })
        }
    });
}

/**
 * 校验当前数据接口是否允许变更
 * @param interfaceId 数据接口id
 * @author wuyu
 */
tableFieldOpt.verifyEditOperation = function(interfaceId) {
    var result = {};
    $.ajax({
        url: baseUrl + '/tableField/ensureEditStatus',
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
            interfaceId: interfaceId
        },
        success: function (data) {
            result = data;
        },
        error: function () {
            result['message'] = '校验当前字段所属数据接口是否可编辑时发生异常。';
            result['success'] = false;
        }
    });
    return result;
}

/**
 * 打开新增字段窗口
 * @author wuyu
 */
tableFieldOpt.openImportWin = function () {
    var _win = $('#table-field-import-win');
    _win.window({
        width: 325,
        height: 125,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            tableFieldOpt.clearData();
        }
    });
    $('#table-field-import-interfaceId').val(tableFieldOpt.interfaceId);
    _win.window('open');
}

tableFieldOpt.importTableField = function() {
    var _form = $('#table-field-import-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认导入字段数据？', function(r){
            if (r) {
                showSubmitProgress();
                var formData = new FormData();
                formData.append('excelFile', document.getElementsByName('excelFile')[0].files[0]);
                formData.append('interfaceId', $('#table-field-import-interfaceId').val());
                $.ajax({
                    url: baseUrl + '/tableField/importTableField',
                    type : 'post',
                    dataType: 'json',
                    data : formData,
                    processData : false,
                    contentType : false,
                    success : function(data){
                        tableFieldOpt.successCallback(data);
                        closeProgress();
                    },
                    error: function () {
                        showErrorMessage('导入失败，程序异常。');
                        closeProgress();
                    }
                });
            }
        });
    }
}

/**
 * 加载化字段信息数据网格
 * @param id 数据接口id
 * @author wuyu
 */
tableFieldOpt.tableFieldDataGrid = function (id) {
    var initDataGrid = {
        url: baseUrl + '/tableField/queryList',
        fit : true,
        fitColumns: true,
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
        queryParams: {
            interfaceId: id
        },
        columns: [[{
            field: "fieldId", title: "编号", width: 50, checkbox: true
        }, {
            field: "tablId", title: "接口id", hidden: true
        }, {
            field: "fieldEnNm", title: "英文名称", width: 100, align: 'center'
        }, {
            field: "fieldCnNm", title: "中文名称", width: 100, align: 'center'
        }, {
            field: "dataTyp", title: "字段类型", width: 60, align: 'center'
        }, {
            field: "dataLength", title: "字段长度", width: 40, align: 'center'
        }, {
            field: "dictryCd", title: "引用代码值", width: 60, align: 'center'
        }, {
            field: "sortNo", title: "排序号", width: 35, align: 'center'
        }, {
            field: "uniqIndxFlg", title: "是否唯一索引", width: 45, align: 'center'
        }, {
            field: "prvlgCtrlFlag", title: "是否机构权限控制字段", width: 70, align: 'center'
        }]],
        toolbar: [{
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                tableFieldOpt.openAddWin();
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                tableFieldOpt.openUpdateWin();
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                tableFieldOpt.delete();
            }
        }, '-', {
            text: '导入Excel',
            iconCls: 'icon-redo',
            handler: function () {
                tableFieldOpt.openImportWin();
            }
        }, '-', {
            text: 'Excel模板下载',
            iconCls: 'icon-undo',
            handler: function () {
                window.location.href = baseUrl + '/tableField/downloadTemplate';
            }
        }]
    };
    $('#table-field-data-grid').datagrid(initDataGrid);
    $('#table-field-data-grid').datagrid('clearSelections');
}

tableFieldOpt.fieldAddData = function () {
    var dataLength = $('#table-field-add-length').numberbox('getValue');
    return {
        fieldEnNm: $('#table-field-add-en-name').textbox('getValue'),
        fieldCnNm: $('#table-field-add-cn-name').textbox('getValue'),
        dataTyp: $('#table-field-add-type').combobox('getValue'),
        dataLength: dataLength == '' ? 0 : dataLength,
        dictryCd: $('#table-field-add-dictryCd').textbox('getValue'),
        sortNo: $('#table-field-add-sort-number').numberbox('getValue'),
        uniqIndxFlg: $('#table-field-add-uniqIndxFlg').combobox('getValue'),
        prvlgCtrlFlag: $('#table-field-add-prvlgCtrlFlag').combobox('getValue'),
        tablId: $('#table-field-add-interfaceId').val()
    }
}

tableFieldOpt.fieldUpdateData = function () {
    return {
        fieldEnNm: $('#table-field-update-en-name').textbox('getValue'),
        fieldCnNm: $('#table-field-update-cn-name').textbox('getValue'),
        dataTyp: $('#table-field-update-type').combobox('getValue'),
        dataLength: $('#table-field-update-length').numberbox('getValue'),
        dictryCd: $('#table-field-update-dictryCd').textbox('getValue'),
        sortNo: $('#table-field-update-sort-number').numberbox('getValue'),
        uniqIndxFlg: $('#table-field-update-uniqIndxFlg').combobox('getValue'),
        prvlgCtrlFlag: $('#table-field-update-prvlgCtrlFlag').combobox('getValue'),
        tablId: $('#table-field-update-interfaceId').val(),
        fieldId: $('#table-field-update-fieldId').val()
    }
}

tableFieldOpt.initCombobox = function () {
    // 是否唯一索引、是否机构权限控制字段 下拉框
    var _combobox = [
        getComboboxAttributes('#table-field-add-uniqIndxFlg', false),
        getComboboxAttributes('#table-field-add-prvlgCtrlFlag', false),
        getComboboxAttributes('#table-field-update-uniqIndxFlg', false),
        getComboboxAttributes('#table-field-update-prvlgCtrlFlag', false)
    ];
    initRepeatDictionaryCode(_combobox, 'SYS02');
    // 数据类型下拉框
    var fileTypeComboboxAttributes = [getComboboxAttributes('#table-field-add-type', false),
        getComboboxAttributes('#table-field-update-type', false)];
    initRepeatDictionaryCode(fileTypeComboboxAttributes, 'DFI16');
}

tableFieldOpt.queryHistoryVersion = function(id, versionNo) {
    var _win = $('#table-his-field-data-grid-win');
    _win.window('open');
    tableFieldOpt.tableFieldHisDataGrid(id, versionNo);
}

tableFieldOpt.tableFieldHisDataGrid = function (id, versionNo) {
    var initDataGrid = {
        url: baseUrl + '/tableField/queryHisList',
        fit : true,
        fitColumns: true,
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
        queryParams: {
            tableId: id,
            versionNo: versionNo
        },
        columns: [[{
            field: "fieldId", title: "编号", width: 50, checkbox: true
        }, {
            field: "tablId", title: "接口id", hidden: true
        }, {
            field: "fieldEnNm", title: "英文名称", width: 100, align: 'center'
        }, {
            field: "fieldCnNm", title: "中文名称", width: 100, align: 'center'
        }, {
            field: "dataTyp", title: "字段类型", width: 60, align: 'center'
        }, {
            field: "dataLength", title: "字段长度", width: 40, align: 'center'
        }, {
            field: "dictryCd", title: "引用代码值", width: 60, align: 'center'
        }, {
            field: "sortNo", title: "排序号", width: 35, align: 'center'
        }, {
            field: "uniqIndxFlg", title: "是否唯一索引", width: 45, align: 'center'
        }, {
            field: "prvlgCtrlFlag", title: "是否机构权限控制字段", width: 70, align: 'center'
        }, {
            field: "versNo", title: "版本号", width: 70, align: 'center'
        }]]
    };
    $('#table-his-field-data-grid').datagrid(initDataGrid);
}