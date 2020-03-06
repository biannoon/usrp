/**
 * 机构
 * @author wuyu
 */
var organization = organization || {};

/**
 * 初始化机构树（easy ui combotree）
 *
 * @param 需要初始化成机构数的dom对象id值数组。<br/>
 *      例：['#userBranchTree1', '#userBranchTree2', '#userBranchTree3'] 。
 * @param multiple combotree是否启用复选框，value: true/false 。
 * @param cascadeCheck 是否禁用combotree点击父节点时同时全选子节点，value: true/false 。
 * @author wuyu
 */
organization.initCombotree = function(ids, multiple, cascadeCheck) {
    $.ajax({
        //TODO baseUrl
        url: baseUrl + '/organization/findFirstTreeNode',
        type: 'post',
        success: function (data) {
            for (var i = 0; i < ids.length; i++) {
                organization.createCombotree(ids[i], multiple, cascadeCheck, data);
            }
        }
    });
}

/**
 * 创建机构树（easy ui combotree）
 *
 * @param id 需要初始化成机构数的dom对象id值。<br/>
 *      例：'#userBranchTree1' 。
 * @param multiple combotree是否启用复选框，value: true/false 。
 * @param cascadeCheck 是否禁用combotree点击父节点时同时全选子节点，value: true/false 。
 * @param data 机构树数据对象集合
 * @author wuyu
 */
organization.createCombotree = function(id, multiple, cascadeCheck, data) {
    $(id).combotree({
        //panelWidth: 300,
        // combotree 是否启用复选框
        multiple: multiple,
        // 是否禁用全选
        cascadeCheck: cascadeCheck,
        data: data,
        onBeforeExpand: function (node) {
            //TODO baseUrl
            $(id).combotree('tree').tree('options').url = baseUrl
                + '/organization/findChildrenTreeNode?parentId=' + node.id;
        }
    });
}

/**
 * 打开机构字段值更新窗口
 * @param tree 选中的机构树节点数据对象
 * @author wuyu
 */
organization.openUpdateWin = function(tree) {
    $('#organization-update-win').window({
        width: 325,
        height: 190,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            organization.clearUpdateWinData();
        }
    });
    $('#organization-field-select').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                organization.getFieldValue();
            }
        }
    });
    organization.clearUpdateWinData();
    $('#organization-id').val(tree.id);
    var _name = $('#organization-name');
    _name.text(tree.text);
    _name.attr('title', tree.text);
    $('#organization-update-win').window('open');
}

/**
 * 关闭机构字段值更新窗口
 * @author wuyu
 */
organization.closeUpdateWin = function() {
    $('#organization-update-win').window('close');
}

/**
 * 清空更新机构字段窗口中的缓存数据
 * @author wuyu
 */
organization.clearUpdateWinData = function() {
    $('#organization-id').val('');
    $("#organization-field-value").textbox('clear');
    $('#organization-field-select').combobox('clear');
}

/**
 * 获取机构字段值
 * @author wuyu
 */
organization.getFieldValue = function() {
    var key = $('#organization-field-select').combobox('getValue');
    var id = $('#organization-id').val();
    var dto = {
        id: id,
        key: key
    };
    getValue(dto);
    function getValue(data) {
        $.ajax({
            url: baseUrl + '/organization/getFieldValue',
            type: 'post',
            dataType: 'json',
            data: data,
            success: function (result) {
                $("#organization-field-value").textbox('setValue', result.data);
            },
            error: function () {
                showErrorMessage('获取机构字段值时异常。');
            }
        })
    }
}

/**
 * 更新报送机构字段
 * @author wuyu
 */
organization.updateSubmittedField = function() {
    var trees = $('#branch-tree').tree('getChecked');
    if (trees == undefined || trees == null || trees.length < 1) {
        showWarningMessage('请勾选一条机构信息。');
        return;
    }
    if (trees.length > 1) {
        showWarningMessage('修改报送机构字段时只能勾选一条机构信息。');
        return;
    }
    var tree = trees[0];
    if (tree.id == 'VROOT') {
        showWarningMessage('虚拟机构根节点不允许修改。');
        return;
    }
    organization.openUpdateWin(tree);
}

/**
 * 更新机构字段值
 * @author wuyu
 */
organization.updateField = function() {
    var _form = $('#organization-update-form');
    if (_form.form('validate')) {
        var key = $('#organization-field-select').combobox('getValue');
        var tempMessage = '是否确认更新该字段值？';
        var value = $("#organization-field-value").textbox('getValue');
        value = value.replace(/\s/g, '');
        if (value.length == 0) {
            tempMessage = '是否确认更新该字段值为空？';
        }
        $.messager.confirm('提示信息', tempMessage, function(r){
            if (r) {
                var id = $('#organization-id').val();
                var dto = {
                    id: id,
                    value: value,
                    key: key
                };
                update(dto);
            }
        });
    }

    function update(dto) {
        $.ajax({
            url: baseUrl + '/organization/updateField',
            type: 'post',
            dataType: 'json',
            data: dto,
            success: function (result) {
                selectivePrompt(result.success, result.message);
            },
            error: function () {
                showErrorMessage('更新字段值时发生异常。');
            }
        })
    }
}

/**
 * 查询机构详情
 * @param id 机构id
 * @author wuyu
 */
organization.queryDetail = function(id) {
    $.ajax({
        url: baseUrl + '/organization/getById',
        type: 'post',
        dataType: 'json',
        data: {
            id: id
        },
        success: function (result) {
            var data = result.data;
            // TODO 待定
            /*if (data['superiorId'] == 'VROOT') {
                $('.column-hidden-flag').hide();
            } else {
                $('.column-hidden-flag').show();
            }*/
            $('#organization-details-form').form('load', data);
        },
        error: function () {
            showErrorMessage('查询机构详细信息时发生异常。');
        }
    })
}

//TODO 需调整
organization.query = function() {
    var queryParams = {
        name: $('#organization-query-name').textbox('getValue'),
        code: $('#organization-query-code').textbox('getValue')
    }
    $('#organization-data-grid').datagrid('load', queryParams);
}

/**
 * 初始化机构树
 * @author wuyu
 */
organization.initTree = function() {
    $('#branch-tree').tree({
        url: baseUrl + '/organization/findFirstTreeNode',
        //tree 启用复选框
        checkbox: true,
        //禁用全选
        cascadeCheck: false,
        //显示树线条
        lines: true,
        onBeforeExpand: function(node) {
            $('#branch-tree').tree('options').url = baseUrl
                + '/organization/findChildrenTreeNode?parentId=' + node.id;
        },
        onClick: function(node){
            organization.queryDetail(node.id);
        },
        onLoadSuccess: function () {
            $('#organization-details-win').show();
        },
        onCheck: treeSingleCheckCtrl
    });
    function treeSingleCheckCtrl(node, checked) {
        var _tree = $('#branch-tree');
        if (checked) {
            var allCheckedNodes = _tree.tree("getChecked");
            for (var i = 0; i < allCheckedNodes.length; i++) {
                var tempNode = allCheckedNodes[i];
                if (tempNode.id != node.id) {
                    _tree.tree('uncheck', tempNode.target);
                }
            }
        }
    }
}
