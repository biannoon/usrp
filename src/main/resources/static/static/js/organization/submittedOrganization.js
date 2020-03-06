/*
 * 报送机构
 * @author wu'yu
 */

var submittedOrganizationOpt = submittedOrganizationOpt || {};

/*
 * 机构树初始数据
 */
submittedOrganizationOpt.organizationData = [];

submittedOrganizationOpt.sourceOrgId = '';

submittedOrganizationOpt.init = function() {
    submittedOrganizationOpt.getOrgComboTreeData();
    submittedOrganizationOpt.initOnChange();
    initDictionaryCode('#submitted-organization-type', 'SYS10', false);
}

submittedOrganizationOpt.initOnChange = function() {
    $('#submitted-organization-type').combobox({
        onChange: function (newValue, oldValue) {
            if (newValue.length > 0 && newValue != oldValue) {
                submittedOrganizationOpt.initTree(newValue);
            }
        }
    });
}

/**
 * 初始化报送机构树
 * @author wuyu
 */
submittedOrganizationOpt.initTree = function (treeType) {
    $('#submitted-organization-tree').tree({
        url: baseUrl + '/submittedOrganization/initTree?treeType=' + treeType,
        //tree 启用复选框
        checkbox: true,
        //禁用全选
        cascadeCheck: false,
        //显示树线条
        lines: true,
        onBeforeExpand: function(node) {
            $('#submitted-organization-tree').tree('options').url =
                baseUrl + '/submittedOrganization/listTreeNode?orgId=' + node.id + '&treeType=' + node.type;
        },
        onDblClick: function(node){
            if (node['flag'] != 'VIRTUAL_ROOT_TREE' && node['flag'] != 'DICTIONARY_TREE') {
                submittedOrganizationOpt.queryDetail(node.id, node.parentId);
            } else {
                $('#organization-details-form').form('clear');
            }
        },
        onLoadSuccess: function () {
            $('#organization-details-win').show();
        }
    });
}

/**
 * 获取机构树数据
 * @author wuyu
 */
submittedOrganizationOpt.getOrgComboTreeData = function() {
    $.ajax({
        url: baseUrl + '/organization/findFirstTreeNode',
        type: 'post',
        success: function (data) {
            submittedOrganizationOpt.organizationData = data;
        }
    });
}

/**
 * 打开新增报送机构窗口
 * @author wuyu
 */
submittedOrganizationOpt.openAddWin = function () {
    if (!submittedOrganizationOpt.selectedOrgType()) {
        showWarningMessage('请选择一种报送机构树类型。');
        return;
    }
    // 数据校验
    var trees = $('#submitted-organization-tree').tree('getChecked');
    if (!submittedOrganizationOpt.isSelectedOne(trees, '新增时')) {
        return;
    }
    // 窗口初始化
    var tree = trees[0];

    var _labelName = $('#submitted-organization-label-name');
    var _selectLabelName = $('#submitted-organization-select-label-name');
    if (tree['flag'] == 'VIRTUAL_ROOT_TREE') {
        return;
    } else if (tree['flag'] == 'DICTIONARY_TREE') {
        var hasRootOrganization = submittedOrganizationOpt.hasRootOrganization(tree.id);
        if (hasRootOrganization) {
            showWarningMessage('当前报送机构类型下已存在机构根节点。');
            return;
        }
        createOrganizationComboTree('#submitted-organization-add-select-tree', false, true,
            submittedOrganizationOpt.organizationData);
        var _a = $('#submitted-organization-add-btn');
        _a.removeAttr('onclick').attr('onclick', 'submittedOrganizationOpt.saveRootOrganization()');
        _labelName.html('报送机构类型：');
        _selectLabelName.html('根节点机构：');
        $('#submitted-organization-add-type').val(tree.id);
        $('#submitted-organization-add-sprOrgId').val(tree.id);
    } else {
        createOrganizationComboTree('#submitted-organization-add-select-tree', true, false,
            submittedOrganizationOpt.organizationData);
        var _a = $('#submitted-organization-add-btn');
        _a.removeAttr('onclick').attr('onclick', 'submittedOrganizationOpt.saveOrganizations()');
        _labelName.html('当前机构名称：');
        _selectLabelName.html('子节点机构：');
        $('#submitted-organization-add-type').val(tree['type']);
        $('#submitted-organization-add-sprOrgId').val(tree.id);
    }
    var _name = $('#submitted-organization-add-name');
    _name.text(tree.text);
    _name.attr('title', tree.text);
    var _win = $('#submitted-organization-add-win');
    _win.window({
        onBeforeClose: function() {
            $('#submitted-organization-add-form').form('clear');
        }
    })
    _win.window('open');
}

/**
 * 存储报送机构根节点
 * @author wuyu
 */
submittedOrganizationOpt.saveRootOrganization = function () {
    var trees = getSelectedComboTrees('#submitted-organization-add-select-tree');
    if (submittedOrganizationOpt.isEmptyTree(trees)) {
        return;
    }
    var tree = trees[0];
    if (tree.id == 'VROOT') {
        showWarningMessage('[' + tree.text + '] 为虚拟节点，不可选择。');
        return;
    }
    var ajaxObj = {
        url: baseUrl + '/submittedOrganization/saveRootOrganization',
        data: {
            orgId: tree.id,
            treeTyp: $('#submitted-organization-add-type').val(),
            sprOrgId: $('#submitted-organization-add-sprOrgId').val()
        }
    };
    customAjaxSubmit(ajaxObj, submittedOrganizationOpt.successCallback);
}

/**
 * 存储报送机构子节点
 * @author wuyu
 */
submittedOrganizationOpt.saveOrganizations = function () {
    var trees = getSelectedComboTrees('#submitted-organization-add-select-tree');
    if (submittedOrganizationOpt.isEmptyTree(trees)) {
        return;
    }
    var ids = [];
    for (var i = 0; i < trees.length; i++) {
        var item = trees[i];
        if (item['flag'] == 'VIRTUAL_ROOT_TREE') {
            showWarningMessage('[' + item.text + '] 为虚拟节点，不可选择。');
            return;
        }
        ids.push(item['id']);
    }
    var data = {
        orgId: $('#submitted-organization-add-sprOrgId').val(),
        treeType: $('#submitted-organization-add-type').val(),
        children: ids
    };
    var ajaxObj = {
        url: baseUrl + '/submittedOrganization/saveOrganizations',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    };
    customAjaxSubmit(ajaxObj, submittedOrganizationOpt.successCallback);
}

/**
 * 维护报送机构树Ajax函数的回调方法
 * @param result Ajax 函数执行结果
 * @author wuyu
 */
submittedOrganizationOpt.successCallback = function(result) {
    if (result.success) {
        var _tree = $('#submitted-organization-tree');
        _tree.tree('options').url =
            baseUrl + '/submittedOrganization/initTree?treeType=' + submittedOrganizationOpt.getOrgType();
        _tree.tree('reload');
        closeWin('submitted-organization-add-win');
    }
    selectivePrompt(result.success, result.message);
}

/**
 * 检验某一报送机构类型是否已存在报送机构根节点
 * @param treeType 报送机构类型值
 * @returns {boolean}
 * @author wuyu
 */
submittedOrganizationOpt.hasRootOrganization = function (treeType) {
    var hasRootOrganization = false;
    $.ajax({
        url: baseUrl + '/submittedOrganization/hasRootOrganization',
        dataType: 'json',
        type: 'post',
        data: {
            treeType: treeType
        },
        async: false,
        success: function (result) {
            hasRootOrganization = result;
        }
    })
    return hasRootOrganization;
}

/**
 * 查询机构详情
 * @param id 机构id
 * @param sprOrgId 上级机构id
 * @author wuyu
 */
submittedOrganizationOpt.queryDetail = function(id, sprOrgId) {
    var ajaxObj = {
        url: baseUrl + '/organization/getById',
        data: {
            id: id
        }
    };
    customAjaxSubmit(ajaxObj, reloadForm);

    function reloadForm(result) {
        var data = result.data;
        // 设置报送机构的上级机构ID
        data['sprOrgId'] = sprOrgId;
        // 加载机构信息数据
        $('#organization-details-form').form('load', data);
        $('#organization-update-win').hide();
        $('#organization-update-form').form('clear');
        $('#organization-details-win').show();
    }
}

/**
 * 批量删除报送机构信息
 * @author wuyu
 */
submittedOrganizationOpt.delete = function () {
    if (!submittedOrganizationOpt.selectedOrgType()) {
        showWarningMessage('请选择一种报送机构树类型。');
        return;
    }
    var trees = $('#submitted-organization-tree').tree('getChecked');
    if (trees.length < 1) {
        showWarningMessage('请选择一条报送机构数据。');
        return;
    }
    var typeNode = [];
    var data = [];
    for (var i = 0; i < trees.length; i++) {
        var tree = trees[i];
        if (tree['flag'] == 'VIRTUAL_ROOT_TREE' || tree['flag'] == 'DICTIONARY_TREE') {
            typeNode.push(tree.text);
        }
        var node = {
            orgId: tree.id,
            treeTyp: tree.type,
            orgNm: tree.text
        }
        data.push(node);
    }
    if (typeNode.length != 0) {
        var typeNodeName = typeNode.join(', ');
        showWarningMessage('[' + typeNodeName + ']不可变更。');
        return;
    }
    $.messager.confirm('提示信息', '确认删除勾选的报送机构信息？', function(r){
        if (r) {
            deleteBatch(data);
        }
    });
    function deleteBatch(data) {
        $.ajax({
            url: baseUrl + '/submittedOrganization/deleteBatch',
            dataType: 'json',
            type: 'post',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (result) {
                submittedOrganizationOpt.successCallback(result);
            },
            error: function () {
                showErrorMessage('删除失败，程序异常。');
            }
        })
    }
}

/**
 * 显示更新报送机构所需信息
 * @author wuyu
 */
submittedOrganizationOpt.showUpdateData = function () {
    if (!submittedOrganizationOpt.selectedOrgType()) {
        showWarningMessage('请选择一种报送机构树类型。');
        return;
    }
    var trees = $('#submitted-organization-tree').tree('getChecked');
    if (!submittedOrganizationOpt.isSelectedOne(trees, '维护时')) {
        return;
    }
    var tree = trees[0];
    if (tree['flag'] == 'VIRTUAL_ROOT_TREE' || tree['flag'] == 'DICTIONARY_TREE') {
        showWarningMessage('报送机构类型不允许变更。');
        return;
    }
    $('#organization-update-form').form('clear');
    var _selectTree = $('#submitted-organization-update-select-tree');
    var data = {
        orgId: tree.id,
        orgNm: tree.text,
        treeTyp: tree.type
    }
    if (tree.type == tree.parentId) {
        _selectTree.combotree({
            url: null,
            //required: false,
            data: []
        });
        loadData();
    } else {
        initComboTree(tree);
    }
    function initComboTree(tree) {
        _selectTree.combotree({
            url: baseUrl + '/submittedOrganization/listTreeNode?orgId=' + tree.type + '&treeType=' + tree.type,
            // 是否启用复选框
            multiple: false,
            // 是否禁用全选
            cascadeCheck: true,
            //required: true,
            onBeforeExpand: function (node) {
                var url = baseUrl + '/submittedOrganization/listTreeNode?orgId=' + node.id + '&treeType=' + node.type
                    + '&sourceOrgId=' + tree.id;
                _selectTree.combotree('tree').tree('options').url = url;
            },
            onLoadSuccess: function () {
                loadData();
            }
        });
    }
    function loadData() {
        $('#organization-update-form').form('load', data);
        $('#organization-update-win').show();
        $('#organization-details-win').hide();
    }
}

/**
 * 检验是否只选择了一条数据
 * @param trees 选中的tree节点数据数组
 * @param messateText 提示文本
 * @returns {boolean}
 * @author wuyu
 */
submittedOrganizationOpt.isSelectedOne = function (trees, messageText) {
    if (submittedOrganizationOpt.isEmptyTree(trees)) {
        showWarningMessage('请勾选一条数据信息。');
        return false;
    }
    if (trees.length > 1) {
        showWarningMessage(messageText + '只能勾选一条数据信息。');
        return false;
    }
    return true;
}

/**
 * 检验选中的树数据是否为空
 * @param trees
 * @returns {boolean}
 * @author wuyu
 */
submittedOrganizationOpt.isEmptyTree = function(trees) {
    return trees == undefined || trees == null || trees.length < 1;
}

/**
 * 更新报送机构信息
 * @author wuyu
 */
submittedOrganizationOpt.update = function () {
    var _form = $('#organization-update-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认更新当前报送机构信息？', function(r){
            if (r) {
                var ajaxObj = {
                    url: baseUrl + '/submittedOrganization/update',
                    data: {
                        orgNm: $('#submitted-organization-update-name').textbox('getValue'),
                        sprOrgId: $('#submitted-organization-update-select-tree').combobox('getValue'),
                        treeTyp: $('#submitted-organization-update-treeTyp').val(),
                        orgId: $('#submitted-organization-update-orgId').val()
                    }
                };
                customAjaxSubmit(ajaxObj, submittedOrganizationOpt.successCallback, function () {
                    showErrorMessage('更新失败，程序异常。');
                });
            }
        });
    }
}

submittedOrganizationOpt.selectedOrgType = function () {
    var orgType = submittedOrganizationOpt.getOrgType();
    return orgType != undefined && orgType != null && orgType.length > 0;
}

submittedOrganizationOpt.getOrgType = function () {
    return $('#submitted-organization-type').combobox('getValue');
}

/**
 * 初始化机构树复制窗口
 * @author wuyu
 */
submittedOrganizationOpt.initCopyWin = function () {
    var ajaxObj = {
        url: baseUrl + '/submittedOrganization/getOrgTypeData'
    }
    customAjaxSubmit(ajaxObj, success);

    function success(result) {
        initComboboxData('#submitted-organization-copy-source', result[true]);
        initComboboxData('#submitted-organization-copy-target', result[false]);
        $('#submitted-organization-copy-win').window('open');
    }
}

/**
 * 复制报送机构信息
 * @author wuyu
 */
submittedOrganizationOpt.copy = function () {
    if ($('#submitted-organization-copy-form').form('validate')) {
        var sourceText = $('#submitted-organization-copy-source').combobox('getText');
        var targetText = $('#submitted-organization-copy-target').combobox('getText');
        $.messager.confirm('提示信息',
            '确认将[' + sourceText + '] 中的报送机构复制到 [' + targetText + '] 中吗？', function(r){
          if (r) {
              var ajaxObj = {
                  url: baseUrl + '/submittedOrganization/copy',
                  data: {
                      sourceTreeType: $('#submitted-organization-copy-source').combobox('getValue'),
                      targetTreeType: $('#submitted-organization-copy-target').combobox('getValue')
                  }
              };
              customAjaxSubmit(ajaxObj, success);
              function success(result) {
                  selectivePrompt(result.success, result.message);
                  $('#submitted-organization-copy-form').form('clear');
                  $('#submitted-organization-copy-win').window('close');
              }
          }
        })
    }
}