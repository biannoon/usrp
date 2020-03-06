/**
 * 虚拟机构
 * @author wuyu
 */
var virtualOrganization = virtualOrganization || {};

/**
 * 打开新增虚拟机构窗口
 * @author wuyu
 */
virtualOrganization.openAddWin = function () {
    var _win = $('#virtual-organization-edit-win');
    _win.window({
        iconCls:'icon-add',
        title: '新增虚拟机构',
        onBeforeClose: function() {
            $('#virtual-organization-edit-form').form('clear');
        }
    });
    $('#virtual-organization-edit-id').textbox('textbox').attr('readonly',false);
    var _a = $('#virtual-organization-edit-btn');
    _a.removeAttr('onclick').attr('onclick', 'virtualOrganization.save()');
    _win.window('open');
}

/**
 * 打开更新虚拟机构窗口
 * @author wuyu
 */
virtualOrganization.openUpdateWin = function () {
    var rows = $('#virtual-organization-data-grid').datagrid('getChecked');
    if (rows == undefined || rows == null || rows.length < 1) {
        showWarningMessage('请勾选一条虚拟机构信息。');
        return;
    }
    if (rows.length > 1) {
        showWarningMessage('修改时只能勾选一条虚拟机构信息。');
        return;
    }
    var data = rows[0];
    var _win = $('#virtual-organization-edit-win');
    _win.window({
        iconCls:'icon-edit',
        title: '更新虚拟机构',
        onBeforeClose: function() {
            $('#virtual-organization-edit-form').form('clear');
        }
    });
    var result = virtualOrganization.query(data.id);
    if (result.success) {
        $('#virtual-organization-edit-id').textbox('textbox').attr('readonly',true);
        var _a = $('#virtual-organization-edit-btn');
        _a.removeAttr('onclick').attr('onclick', 'virtualOrganization.update()');
        $('#virtual-organization-edit-form').form('load', result.data);
        _win.window('open');
    } else {
        showErrorMessage(result.message);
    }
}

virtualOrganization.query = function(id) {
    var data = {};
    $.ajax({
        url: baseUrl + '/organization/getVirtualOrganization',
        type: 'post',
        dataType: 'json',
        data: {
            id: id
        },
        async: false,
        success: function (result) {
            data = result;
        },
        error: function () {
            showErrorMessage('查询虚拟机构时发生异常。');
        }
    });
    return data;
}


/**
 * 打开新增虚拟机构窗口
 * @author wuyu
 */
virtualOrganization.save = function() {
    var _form = $('#virtual-organization-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认新增当前虚拟机构？', function(r){
            if (r) {
                var ajaxObj = {
                    url: baseUrl + '/organization/saveVirtualOrganization',
                    data: virtualOrganization.virtualOrganizationData()
                };
                customAjaxSubmit(ajaxObj, virtualOrganization.success, function () {
                    showErrorMessage('新增失败，程序异常。');
                });
            }
        });
    }
}

/**
 * 打开新增虚拟机构窗口
 * @author wuyu
 */
virtualOrganization.update = function() {
    var _form = $('#virtual-organization-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认更新当前虚拟机构？', function(r){
            if (r) {
                var ajaxObj = {
                    url: baseUrl + '/organization/updateVirtualOrganization',
                    data: virtualOrganization.virtualOrganizationData()
                };
                customAjaxSubmit(ajaxObj, virtualOrganization.success, function () {
                    showErrorMessage('更新失败，程序异常。');
                });
            }
        });
    }
}

/**
 * 删除虚拟机构
 * @author wuyu
 */
virtualOrganization.delete = function() {
    var rows = $('#virtual-organization-data-grid').datagrid('getChecked');
    if (rows == undefined || rows == null || rows.length < 1) {
        showWarningMessage('请勾选一条或多条虚拟机构信息。');
        return;
    }
    var ids = [];
    $.each(rows, function (index, item) {
        ids.push(item['id']);
    });
    var status = virtualOrganization.verifyStatus(ids);
    if (status) {
        var tempMessage = '确认删除勾选的虚拟机构？';
        $.messager.confirm('提示信息', tempMessage, function(r){
            if (r) {
                $.ajax({
                    url: baseUrl + '/organization/deleteVirtualOrganization',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        ids: ids
                    },
                    success: function (result) {
                        var message = '';
                        if (result.success) {
                            $('#virtual-organization-data-grid').datagrid('reload');
                            message = result.message;
                        } else {
                            message = '删除虚拟机构失败，' + result.message;
                        }
                        selectivePrompt(result.success, message);
                    },
                    error: function () {
                        showErrorMessage('删除虚拟机构时发生异常。');
                    }
                })
            }
        });
    }
}

/**
 * 校验虚拟机构状态是否允许删除
 * @param ids 勾选的机构id数组
 * @returns {boolean} true： 允许删除；false： 禁止删除。
 * @author wuyu
 */
virtualOrganization.verifyStatus = function(ids) {
    var status = false;
    $.ajax({
        url: baseUrl + '/organization/verifyOrganizationStatus',
        type: 'post',
        dataType: 'json',
        async: false,
        data: {
            ids: ids
        },
        success: function (result) {
            if (result.success) {
                status = result.success;
                virtualOrganization.reloadDataGrid();
            } else {
                showWarningMessage(result.message);
            }
        },
        error: function () {
            showWarningMessage('校验机构状态时发生异常。');
        }
    });
    return status;
}

/**
 * 刷新数据
 * @author wuyu
 */
virtualOrganization.reloadDataGrid = function() {
    $('#virtual-organization-data-grid').datagrid('reload');
}

/**
 * 关闭虚拟机构窗口
 * @param id
 * @author wuyu
 */
virtualOrganization.closeWin = function(id) {
    $(id).window('close');
}

virtualOrganization.queryByKeyword = function() {
    var queryParams = {
        superiorId: 'VROOT',
        name: $('#virtual-organization-query-name').textbox('getValue'),
        code: $('#virtual-organization-query-code').textbox('getValue')
    }
    $('#virtual-organization-data-grid').datagrid('load', queryParams);
}

virtualOrganization.organizationDataGrid = function() {
    var initDataGrid = {
        url : baseUrl + '/organization/queryOrganization',
        fitColumns : true,
        border : true,
        pagination : true,
        pageSize : 10,
        pageList : [ 10, 20, 30],
        singleSelect : false,
        checkOnSelect : true,
        selectOnCheck : true,
        remoteSort : false,
        striped : true,
        nowrap : false,
        queryParams: {
            superiorId: 'VROOT',
            name: '',
            code: ''
        },
        columns : [ [ {
            field: "checkbox", title: "id", width: 50, checkbox: true
        },{
            field : "id", title : "机构ID", width : 50, align : 'center'
        }, {
            field : "name", title : "机构名称", width : 150, align : 'center'
        }, {
            field : "abbreviation", title : "机构简称", width : 100, align : 'center'
        }, {
            field : "code", title : "机构编码", width : 50, align : 'center'
        } ] ],
        toolbar: [{
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                virtualOrganization.openAddWin();
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                virtualOrganization.openUpdateWin();
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                virtualOrganization.delete();
            }
        }]
    };

    $('#virtual-organization-data-grid').datagrid(initDataGrid);
}

virtualOrganization.virtualOrganizationData = function () {
    return{
        id: $('#virtual-organization-edit-id').textbox('getValue'),
        name: $('#virtual-organization-edit-name').textbox('getValue'),
        abbreviation: $('#virtual-organization-edit-abbreviation').textbox('getValue'),
        code: $('#virtual-organization-edit-code').textbox('getValue')
    }
}

virtualOrganization.success = function (data) {
    if (data.success) {
        virtualOrganization.closeWin('#virtual-organization-edit-win');
        virtualOrganization.reloadDataGrid();
    }
    selectivePrompt(data.success, data.message);
}