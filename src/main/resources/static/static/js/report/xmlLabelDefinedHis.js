function xmlLabelDefinedHis(baseUrl) {
    var _subjectId = '';
    var _fileId = '';
    var _subjectStatus = '';
    var _versionNo = '';

    var _tree = $('#xml-label-branchHis-tree');
    var _xmlLabelEditWin = $('#xml-label-editHis-win');
    var _xmlLabelEditForm = $('#xml-label-editHis-form');
    var _show = $('.xml-label-edit-detailAndBtnHis');


    this.init = function (fileData) {
        _show.hide();
        _xmlLabelEditForm.form('clear');
        _subjectId = fileData.subjectId;
        _fileId = fileData.fileId;
        _versionNo=fileData.versionNo;
        // 初始化标签树
        var _o = this;
        _o.initBranchTree();
        // 初始xml标签配置窗口
        _xmlLabelEditWin.window({
            title: fileData.fileName + ' xml标签配置'
        });
        _xmlLabelEditWin.window('open');
        initBtn();
        // 初始缓存数据
        var subjectData = $('#report-subject-history-data-grid').datagrid('getSelected');
        _subjectStatus = subjectData.status;
        sqlDefinedOptHis.initComboTree('xml-label-relation-sql', fileData.subjectId);
    }

    function initBtn() {
       bindClickEvent('remove-xml-labelHis-attribute', function () {
           setLabelProperty();
       });
       bindClickEvent('remove-xml-labelHis-dataItem', function () {
           setDataItem();
       });
    }

    function initUpdateOperation(node) {
        if (isNullObject(node)) {
            var trees = _tree.tree('getChecked');
            if (isEmptyArray(trees) && trees.length != 1) {
                showWarningMessage('请在xml标签树中勾选或双击需要查看的xml标签。');
                _show.hide();
                return;
            }
            node = trees[0];
            if (node.id === 'ROOT') {
                return;
            }
        }
        var data = getOne(node.id);
        if (!isNullObject(data)) {
            _xmlLabelEditForm.form('load', data);
            _show.show();
        }
    }

    function getOne(labelId) {
        var _ajaxObj = {
            url: baseUrl + '/reportXmlLabelHis/getOne',
            async: false,
            data: {
                subjectId: _subjectId,
                fileId: _fileId,
                labelId: labelId,
                versionNo:_versionNo
            }
        };
        var data = {};
        customAjaxSubmit(_ajaxObj, function (result) {
            data = result;
        });
        return data;
    }

    function setLabelProperty() {
        var trees = _tree.tree('getChecked');
        if (isEmptyArray(trees) && trees.length != 1) {
            showWarningMessage('请在xml标签树中选择查看标签的所属标签。');
            _show.hide();
            return;
        }
        var tree = trees[0];
        if (tree.type === 'SYS0201') {
            showWarningMessage('当前xml标签为文本结构段，不能查看标签属性。');
            return;
        }
        if (tree.id === 'ROOT') {
            return;
        }
        var cacheData = {
            subjectId: _subjectId,
            fileId: _fileId,
            labelId: tree.id,
            subjectStatus: _subjectStatus,
            versionNo:_versionNo
        };
        new xmlLabelPropertyDefinedHis(baseUrl).init(cacheData);
    }

    function setDataItem() {
        var trees = _tree.tree('getChecked');
        if (isEmptyArray(trees) && trees.length != 1) {
            showWarningMessage('请在xml标签树中选择查看标签的所属标签。');
            _show.hide();
            return;
        }
        var tree = trees[0];
        if (tree.type != 'SYS0201') {
            showWarningMessage('非文本结构段没有数据项信息。');
            return;
        }
        if (tree.id === 'ROOT') {
            return;
        }
        var data = getOne(tree.id);
        var cacheData = {
            subjectId: _subjectId,
            fileId: _fileId,
            segmentId: tree.id,
            subjectStatus: _subjectStatus,
            splitType: data.splitType,
            versionNo:_versionNo
        };
        new dataItemDefinedHis(baseUrl).init(cacheData);
    }

    function successCallback(result) {
        if (result.success) {
            _tree.tree('options').url =
                baseUrl + '/reportXmlLabel/initXmlLabelTree?subjectId=' + _subjectId + '&fileId=' + _fileId,
            _tree.tree('reload');
        }
        selectivePrompt(result.success, result.message);
    }

    this.initBranchTree = function () {
        _tree.tree({
            url: baseUrl + '/reportXmlLabelHis/initXmlLabelTree?subjectId=' + _subjectId + '&fileId=' + _fileId+"&versionNo="+_versionNo,
            //tree 启用复选框
            checkbox: true,
            //禁用全选
            cascadeCheck: false,
            //显示树线条
            lines: true,
            onBeforeExpand: function(node) {
                _tree.tree('options').url = baseUrl + '/reportXmlLabelHis/listTreeNode?subjectId='
                    + _subjectId + '&fileId=' + _fileId + '&parentLabelId=' + node.id+"&versionNo="+_versionNo;
            },
            onDblClick: function(node){
                if (node != undefined && node != null && node.id != 'ROOT') {
                    initUpdateOperation(node);
                }
            },
            onCheck: treeSingleCheckCtrl
        });
        function treeSingleCheckCtrl(node, checked) {
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
}