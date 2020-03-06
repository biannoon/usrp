var sqlDefinedOpt = sqlDefinedOpt|| {};

sqlDefinedOpt.subjectId = '';
sqlDefinedOpt.subjectStatus = '';

sqlDefinedOpt.init = function () {
    var subjectData = reportSubjectOpt.getOneRowData();
    if (subjectData === null) {
        return;
    }
    sqlDefinedOpt.initDefaultValue(subjectData.subjectId, subjectData.status);
    var _win = $('#report-sql-win');
    _win.window({
        title: subjectData.subjectName + ' SQL配置信息',
        onBeforeClose: function() {
            sqlDefinedOpt.initDefaultValue('', '');
            sqlDefinedOpt.clearData();
        }
    });
    sqlDefinedOpt.initTree();
    _win.window('open');
    sqlDefinedOpt.initCombobox();
    sqlDefinedOpt.initBtn();
}

sqlDefinedOpt.initBtn = function() {
    bindClickEvent('add-report-sql', sqlDefinedOpt.initAddOperation);
    bindClickEvent('edit-report-sql', function () {
        sqlDefinedOpt.initUpdateOperation(null);
    });
    bindClickEvent('remove-report-sql', sqlDefinedOpt.initDeleteOperation);
    bindClickEvent('edit-report-sql-param', sqlDefinedOpt.editSqlParam);
    bindClickEvent('edit-report-sql-metadata', sqlDefinedOpt.editMetadata);
}

sqlDefinedOpt.initDefaultValue = function(subjectId, subjectStatus) {
    sqlDefinedOpt.subjectId = subjectId;
    sqlDefinedOpt.subjectStatus = subjectStatus;
}

sqlDefinedOpt.initCombobox = function() {
    initDatabase('#sql-edit-dataSourceId', false);
}

sqlDefinedOpt.initAddOperation = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(sqlDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var trees = $('#branch-tree').tree('getChecked');
    if (isEmptyArray(trees) && trees.length != 1) {
        showWarningMessage('请在SQL树中勾选新增SQL的所属节点。');
        return;
    }
    $('#report-sql-edit-form').form('clear');
    $('.sql-edit-detail, .sql-edit-Btn').show();
    var node = trees[0];
    $('#sql-edit-parentSqlId').val(node.id);
    bindClickEvent('sql-edit-save-btn', function () {
        sqlDefinedOpt.saveOrUpdate('save');
    });
}

sqlDefinedOpt.initUpdateOperation = function(node) {
    if (!reportSubjectOpt.isInsertOrRepealStatus(sqlDefinedOpt.subjectStatus)) {
        $('.sql-edit-detail').show();
        $('.sql-edit-Btn').hide();
        bindClickEvent('sql-edit-save-btn', function () {});
    } else {
        $('.sql-edit-detail, .sql-edit-Btn').show();
        bindClickEvent('sql-edit-save-btn', function () {
            sqlDefinedOpt.saveOrUpdate('update');
        });
    }
    if (isNullObject(node)) {
        var trees = $('#branch-tree').tree('getChecked');
        if (isEmptyArray(trees) && trees.length != 1) {
            showWarningMessage('请在SQL树中勾选或双击需要修改的SQL。');
            return;
        }
        node = trees[0];
        if (node.id === 'ROOT') {
            sqlDefinedOpt.clearData();
            return;
        }
        node = trees[0];
    }
    getOne();
    function getOne() {
        var _ajaxObj = {
            url: baseUrl + '/reportSql/getOne',
            data: {
                subjectId: sqlDefinedOpt.subjectId,
                sqlId: node.id
            }
        };
        customAjaxSubmit(_ajaxObj, function (result) {
            $('#report-sql-edit-form').form('load', result);
        });
    }
}

sqlDefinedOpt.initDeleteOperation = function() {
    if (!reportSubjectOpt.isInsertOrRepealStatus(sqlDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var trees = $('#branch-tree').tree('getChecked');
    if (isEmptyArray(trees) || trees.length != 1) {
        showWarningMessage('请在SQL树中勾选需要删除的SQL。');
        return;
    }
    var node = trees[0];
    if (node.id === 'ROOT') {
        return;
    }
    $.messager.confirm('提示信息', '确认删除['+ node.text +'] SQL信息？', function(r) {
        if (r) {
            var _ajaxObj = {
                url: baseUrl + '/reportSql/delete',
                data: {
                    subjectId: sqlDefinedOpt.subjectId,
                    sqlId: node.id
                }
            };
            customAjaxSubmit(_ajaxObj, function (result) {
                $('#report-sql-edit-form').form('clear');
                bindClickEvent('sql-edit-save-btn', function () {});
                $('.sql-edit-detail, .sql-edit-Btn').hide();
                sqlDefinedOpt.successCallback(result);
            });
        }
    });
}

sqlDefinedOpt.saveOrUpdate = function(url) {
    var _form = $('#report-sql-edit-form');
    if (_form.form('validate')) {
        $.messager.confirm('提示信息', '确认保存当前SQL配置信息？', function(r){
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportSql/' + url,
                    data: {
                        subjectId: sqlDefinedOpt.subjectId,
                        sqlId: $('#sql-edit-id').val(),
                        sqlName: $('#sql-edit-name').textbox('getValue'),
                        dataSourceId: $('#sql-edit-dataSourceId').combobox('getValue'),
                        sqlExps: $('#sql-edit-sqlExps').textbox('getValue'),
                        comnt: $('#sql-edit-comment').textbox('getValue'),
                        pareSqlId: $('#sql-edit-parentSqlId').val()
                    }
                };
                customAjaxSubmit(_ajaxObj, sqlDefinedOpt.successCallback);
            }
        });
    }
}

sqlDefinedOpt.successCallback = function(result) {
    if (result.success) {
        var _tree = $('#branch-tree');
        _tree.tree('options').url = baseUrl + '/reportSql/initSqlTree?subjectId=' + sqlDefinedOpt.subjectId;
        _tree.tree('reload');
    }
    selectivePrompt(result.success, result.message);
}

sqlDefinedOpt.initComboTree = function(comboTreeId, subjectId) {
    $('#' + comboTreeId).combotree({
        url: baseUrl + '/reportSql/findChildrenTreeNode?subjectId=' + subjectId + '&parentSqlId=Root',
        // 是否启用复选框
        multiple: false,
        // 是否禁用全选
        cascadeCheck: true,
        panelHeight: '200px',
        onBeforeExpand: function (node) {
            var url = baseUrl + '/reportSql/findChildrenTreeNode?subjectId=' + subjectId + '&parentSqlId=' + node.id;
            $('#' + comboTreeId).combotree('tree').tree('options').url = url;
        }
    });
}

sqlDefinedOpt.initTree = function () {
    $('#branch-tree').tree({
        url: baseUrl + '/reportSql/initSqlTree?subjectId=' + sqlDefinedOpt.subjectId,
        //tree 启用复选框
        checkbox: true,
        //禁用全选
        cascadeCheck: false,
        //显示树线条
        lines: true,
        onBeforeExpand: function(node) {
            $('#branch-tree').tree('options').url = baseUrl
                + '/reportSql/findChildrenTreeNode?subjectId=' + node.type + '&parentSqlId=' + node.id;
        },
        onDblClick: function(node){
            if (node != undefined && node != null) {
                if (node.id == 'ROOT') {
                    sqlDefinedOpt.clearData();
                } else {
                    sqlDefinedOpt.initUpdateOperation(node);
                }
            }
        },
        onLoadSuccess: function () {
            //$('#organization-details-win').show();
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

sqlDefinedOpt.editSqlParam = function () {
    new sqlParamDefined(baseUrl).init(reportSubjectOpt.getOneRowData());
}

sqlDefinedOpt.editMetadata = function () {
    if (!reportSubjectOpt.isInsertOrRepealStatus(sqlDefinedOpt.subjectStatus)) {
        showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
        return;
    }
    var trees = $('#branch-tree').tree('getChecked');
    if (isEmptyArray(trees) || trees.length != 1) {
        showWarningMessage('请在SQL树中勾选需要编辑的SQL。');
        return;
    }
    var node = trees[0];
    if (node.id === 'ROOT') {
        return;
    }
    var cacheData = {
        subjectStatus: sqlDefinedOpt.subjectStatus,
        sqlId: node.id
    };
    new sqlMetadata(baseUrl).init(cacheData);
}

sqlDefinedOpt.clearData = function () {
    $('#report-sql-edit-form').form('clear');
    $('.sql-edit-detail, .sql-edit-Btn').hide();
    bindClickEvent('sql-edit-save-btn', function () {});
}