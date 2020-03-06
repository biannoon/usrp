var sqlDefinedOptHis = sqlDefinedOptHis|| {};

sqlDefinedOptHis.subjectId = '';
sqlDefinedOptHis.subjectStatus = '';
sqlDefinedOptHis.versionNo='';

sqlDefinedOptHis.init = function () {
    sqlDefinedOptHis.initDefaultValue(subjectData.subjectId, subjectData.status,subjectData.versionNo);
    var _win = $('#report-sqlHis-win');
    _win.window({
        title: subjectData.subjectName + ' SQL配置历史信息',
        onBeforeClose: function() {
            sqlDefinedOptHis.initDefaultValue('', '','');
            $('.sql-edit-detailAndBtnHis').hide();
        }
    });
}

sqlDefinedOptHis.initBtn = function() {
    bindClickEvent('edit-report-sql-paramHis', sqlDefinedOptHis.editSqlParam);
    bindClickEvent('edit-report-sql-metadataHis', sqlDefinedOptHis.editMetadata);
}

sqlDefinedOptHis.initDefaultValue = function(subjectId, subjectStatus,versionNo) {
    sqlDefinedOptHis.subjectId = subjectId;
    sqlDefinedOptHis.subjectStatus = subjectStatus;
    sqlDefinedOptHis.versionNo= versionNo;

}
sqlDefinedOptHis.initTree = function () {
    $('#branchHis-tree').tree({
        url: baseUrl + '/reportSqlHis/initSqlHisTree?subjectId=' + sqlDefinedOptHis.subjectId+"&versionNo="+sqlDefinedOptHis.versionNo,
        //tree 启用复选框
        checkbox: true,
        //禁用全选
        cascadeCheck: false,
        //显示树线条
        lines: true,
        onBeforeExpand: function(node) {
            $('#branchHis-tree').tree('options').url = baseUrl
                + '/reportSqlHis/findChildrenHisTreeNode?subjectId=' + node.type + '&parentSqlId=' + node.id+"&versionNo="+sqlDefinedOptHis.versionNo;
        },
        onDblClick: function(node){
            if (node != undefined && node != null && node.id != 'ROOT') {
                sqlDefinedOptHis.initUpdateOperationa(node);
            }
        },
        onLoadSuccess: function () {
            //$('#organization-details-win').show();
        },
        onCheck: treeSingleCheckCtrl
    });
    sqlDefinedOptHis.initUpdateOperationa= function(node) {
        if (isNullObject(node)) {
            var trees = $('#branchHis-tree').tree('getChecked');
            if (isEmptyArray(trees) && trees.length != 1) {
                showWarningMessage('请在SQL树中勾选或双击需要查看的SQL。');
                return;
            }
            if (node.id === 'ROOT') {
                return;
            }
            node = trees[0];
        }
        $('.sql-edit-detailAndBtnHis').show();
        getOne();
        function getOne() {
            var _ajaxObj = {
                url: baseUrl + '/reportSqlHis/getOne',
                data: {
                    subjectId: sqlDefinedOptHis.subjectId,
                    sqlId: node.id
                }
            };
            customAjaxSubmit(_ajaxObj, callback);
        }
        function callback(result) {
            $('#report-sqlHis-edit-form').form('load', result);

        }
    }
    function treeSingleCheckCtrl(node, checked) {
        var _tree = $('#branchHis-tree');
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

sqlDefinedOptHis.editSqlParam = function () {
    var row = $('#report-subject-history-data-grid').datagrid('getSelected');
    if (row==null||row==undefined) {
        showWarningMessage('请选择一行报送主体信息数据。');
        return null;
    }
    new sqlParamDefinedHis(baseUrl).init(row);
}

sqlDefinedOptHis.editMetadata = function () {
    var trees = $('#branchHis-tree').tree('getChecked');
    if (isEmptyArray(trees) || trees.length != 1) {
        showWarningMessage('请在SQL树中勾选需要查看的SQL。');
        return;
    }
    var node = trees[0];
    if (node.id === 'ROOT') {
        return;
    }
    var cacheData = {
        subjectStatus: sqlDefinedOptHis.subjectStatus,
        sqlId: node.id,
        versionNo:sqlDefinedOptHis.versionNo
    };
    new sqlMetadataHis(baseUrl).init(cacheData);
}