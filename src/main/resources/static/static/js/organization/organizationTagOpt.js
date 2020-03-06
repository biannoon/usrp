/**
 * 机构标签
 * @author wuyu
 */
var organizationTagOpt = organizationTagOpt || {};

organizationTagOpt.orgId = '';
organizationTagOpt.orgName = '';

/**
 * 初始化机构标签数据
 * @author wuyu
 */
organizationTagOpt.openDataGridWin = function() {
    var trees = $('#branch-tree').tree('getChecked');
    if (trees == undefined || trees == null || trees.length < 1) {
        showWarningMessage('请勾选一条机构信息。');
        return;
    }
    if (trees.length > 1) {
        showWarningMessage('维护机构标签时只能勾选一条机构信息。');
        return;
    }
    var tree = trees[0];
    if (tree.id == 'VROOT') {
        showWarningMessage('虚拟机构根节点不允许修改。');
        return;
    }
    organizationTagOpt.orgId = tree.id;
    organizationTagOpt.orgName = tree.text;
    var _win = $('#organization-tag-win');
    _win.window({
        onBeforeClose: function() {
            organizationTagOpt.orgId = '';
            organizationTagOpt.orgName = '';
        }
    });
    organizationTagOpt.organizationTagDataGrid(tree.id);
    initDictionaryCode('#organization-tag-type-select', 'SYS12', false);
    _win.window('open');
}

/**
 * 打开新增机构标签窗口
 * @author wuyu
 */
organizationTagOpt.openAddWin = function() {
    // 初始化窗口信息
    var _win = $('#organization-tag-edit-win');
    _win.window({
        title: '新增机构标签',
        iconCls:'icon-add',
        onBeforeClose: function() {
            $('#organization-tag-edit-form').form('clear');
        }
    });
    organizationTagOpt.setEditWinName();
    // 初始化机构标签类型下拉框属性
    var _box = $('#organization-tag-type-select');
    _box.combobox({readonly: false});
    _box.combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                organizationTagOpt.getOne(organizationTagOpt.orgId, newValue, false)
            }
        }
    });
    // 初始化'保存'按钮事件
    var _a = $('#organization-tag-edit-btn');
    _a.removeAttr('onclick').attr('onclick', 'organizationTagOpt.save()');
    _win.window('open');
}

/**
 * 存储机构标签信息
 * @author wuyu
 */
organizationTagOpt.save = function() {
    var _form = $('#organization-tag-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认新增当前机构标签？', function(r){
            if (r) {
                var ajaxObj = {
                    url: baseUrl + '/organizationTag/save',
                    data: organizationTagOpt.tagData()
                };
                customAjaxSubmit(ajaxObj, organizationTagOpt.success, organizationTagOpt.error);
            }
        });
    }
}

/**
 * 打开更新机构标签窗口
 * @author wuyu
 */
organizationTagOpt.openUpdateWin = function() {
    // 获取选中的机构标签数据
    var rows = $('#organization-tag-data-grid').datagrid('getChecked');
    if (rows == undefined || rows == null || rows.length < 1) {
        showWarningMessage('请勾选一条机构标签信息。');
        return;
    }
    if (rows.length > 1) {
        showWarningMessage('修改时只能勾选一条机构标签信息。');
        return;
    }
    var data = rows[0];
    // 初始化窗口信息
    var _win = $('#organization-tag-edit-win');
    _win.window({
        title: '更新机构标签',
        iconCls:'icon-edit',
        onBeforeClose: function() {
            $('#organization-tag-edit-form').form('clear');
        }
    });
    organizationTagOpt.setEditWinName();
    // 初始化机构标签类型下拉框属性
    var _box = $('#organization-tag-type-select');
    _box.combobox({readonly: true});
    _box.combobox({
        onChange: function (newValue, oldValue) {
            // 置空
        }
    });
    // 初始化'保存'按钮事件
    var _a = $('#organization-tag-edit-btn');
    _a.removeAttr('onclick').attr('onclick', 'organizationTagOpt.update()');
    // 加载选中的机构标签数据
    organizationTagOpt.getOne(data.orgId, data.tagTyp, true);
    _win.window('open');
}

/**
 * 更新机构标签信息
 * @author wuyu
 */
organizationTagOpt.update = function() {
    var _form = $('#organization-tag-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认更新当前机构标签信息？', function(r){
            if (r) {
                var ajaxObj = {
                    url: baseUrl + '/organizationTag/update',
                    data: organizationTagOpt.tagData()
                };
                customAjaxSubmit(ajaxObj, organizationTagOpt.success, organizationTagOpt.error);
            }
        });
    }
}

/**
 * 在机构标签编辑窗口中初始化选中的机构名称
 * @author wuyu
 */
organizationTagOpt.setEditWinName = function() {
    $('#organization-tag-orgId').val(organizationTagOpt.orgId);
    var _name = $('#organization-tag-organizationName');
    _name.text(organizationTagOpt.orgName);
    _name.attr('title', organizationTagOpt.orgName);
}

/**
 * 关闭机构标签编辑窗口
 * @author wuyu
 */
organizationTagOpt.closeEditWin = function() {
    $('#organization-tag-edit-form').form('clear');
    $('#organization-tag-edit-win').window('close');
}

/**
 * 获取单条机构标签数据
 * @param orgId 机构id
 * @param tagType 标签
 * @param isUpdate
 */
organizationTagOpt.getOne = function(orgId, tagType, isUpdate) {
    $.ajax({
        url: baseUrl + '/organizationTag/getOne',
        type: 'post',
        dataType: 'json',
        data: {
            orgId: orgId,
            tagTyp: tagType
        },
        async: false,
        success: function (result) {
            var data = result.data;
            if (isUpdate) {
                $('#organization-tag-edit-form').form('load', data);
            } else {
                if (result.success && data != null) {
                    showWarningMessage('机构[' + organizationTagOpt.orgName + ']已存在标签类型[' + result.type + ']。');
                }
            }
        }
    })
}

organizationTagOpt.delete = function() {
// 获取选中的机构标签数据
    var rows = $('#organization-tag-data-grid').datagrid('getChecked');
    if (rows == undefined || rows == null || rows.length < 1) {
        showWarningMessage('请勾选一条机构标签信息。');
        return;
    }
    if (rows.length > 1) {
        showWarningMessage('删除时只能勾选一条机构标签信息。');
        return;
    }
    var data = rows[0];
    $.messager.confirm('提示信息', '确认删除选中的机构标签信息？', function(r){
        if (r) {
            $.ajax({
                url: baseUrl + '/organizationTag/delete',
                dataType: 'json',
                type: 'post',
                data: data,
                success: function (result) {
                    if (result.success) {
                        $('#organization-tag-data-grid').datagrid('reload');
                    }
                    selectivePrompt(result.success, result.message);
                },
                error: function () {
                    showErrorMessage('删除机构标签信息时发生异常。');
                }
            })
        }
    });
}

/**
 * 机构标签数据网格
 * @param id 机构id
 * @author wuyu
 */
organizationTagOpt.organizationTagDataGrid = function(id) {
    var initDataGrid = {
        url : baseUrl + '/organizationTag/queryOrganizationTag',
        fit: true,
        fitColumns : true,
        border : true,
        pagination : true,
        pageSize : 10,
        pageList : [ 10, 20],
        singleSelect : true,
        checkOnSelect : true,
        selectOnCheck : true,
        remoteSort : false,
        striped : true,
        nowrap : false,
        queryParams: {
            orgId: id
        },
        columns : [ [{
            field : "orgId", title : "机构ID", width : 50, checkbox: true
        }, {
            field : "tagTyp", title : "标签类型", width : 50, align : 'center', hidden: true
        }, {
            field : "tagName", title : "标签类型", width : 100, align : 'center'
        }, {
            field : "tagVal", title : "标签值", width : 100, align : 'center'
        } ] ],
        toolbar: [{
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
                organizationTagOpt.openAddWin();
            }
        }, '-', {
            text: '修改',
            iconCls: 'icon-edit',
            handler: function () {
                organizationTagOpt.openUpdateWin();
            }
        }, '-', {
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () {
                organizationTagOpt.delete();
            }
        }]
    };

    $('#organization-tag-data-grid').datagrid(initDataGrid);
}

organizationTagOpt.tagData = function () {
    return {
        tagTyp: $('#organization-tag-type-select').combobox('getValue'),
        tagVal: $('#organization-tag-value').textbox('getValue'),
        orgId: $('#organization-tag-orgId').val()
    }
}

organizationTagOpt.success = function(data) {
    if (data.success) {
        organizationTagOpt.closeEditWin();
        $('#organization-tag-data-grid').datagrid('reload');
    }
    selectivePrompt(data.success, data.message);
}

organizationTagOpt.error = function () {
    showErrorMessage('执行失败，程序异常。');
}