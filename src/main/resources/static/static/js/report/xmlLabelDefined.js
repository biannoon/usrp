function xmlLabelDefined(baseUrl) {
    var _subjectId = '';
    var _fileId = '';
    var _subjectStatus = '';

    var _tree = $('#xml-label-branch-tree');
    var _xmlLabelEditWin = $('#xml-label-edit-win');
    var _xmlLabelEditForm = $('#xml-label-edit-form');
    var _show = $('.xml-label-edit-detailAndBtn');
    var _enName = $('#xml-label-name-en');
    var _lineBreak = $('#xml-label-lineBreak');
    var _lineBreakChar = $('#xml-label-lineBreakChar');
    var _textSegment = $('#xml-label-textSegment');
    var _splitType = $('#xml-label-splitType');
    var _splitCharType = $('#xml-label-splitCharType');
    var _splitChar = $('#xml-label-splitChar');
    var _labelValueExps = $('#xml-label-labelValueExps');
    var _labelValueType = $('#xml-label-labelValueType');
    var _parentLabelId = $('#xml-label-parentLabelId');
    var _nullReplaceChar = $('#xml-label-nullReplaceChar');
    var _labelCloseType = $('#xml-label-labelCloseType');
    var _loopSplitCharType = $('#xml-label-loopSplitCharType');
    var _loopSplitChar = $('#xml-label-loopSplitChar');
    var _relationSqlId = $('#xml-label-relation-sql');
    var _loopLabel = $('#xml-label-loopLabel');

    this.init = function (fileData) {
        _show.hide();
        _xmlLabelEditForm.form('clear');
        _subjectId = fileData.subjectId;
        _fileId = fileData.fileId;
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
        var subjectData = reportSubjectOpt.getOneRowData();
        _subjectStatus = subjectData.status;
        initCombobox();
        sqlDefinedOpt.initComboTree('xml-label-relation-sql', fileData.subjectId);
    }

    function initCombobox() {
        var _attributes = [getComboboxAttributes('#xml-label-textSegment', false),
            getComboboxAttributes('#xml-label-lineBreak', false),
            getComboboxAttributes('#xml-label-loopLabel', false),
            getComboboxAttributes('#xml-label-mainLabel', false)];
        initRepeatDictionaryCode(_attributes, 'SYS02');

        var _splitTypeAttributes = [getComboboxAttributes('#xml-label-splitCharType', false),
            getComboboxAttributes('#xml-label-loopSplitCharType', false)];
        initRepeatDictionaryCode(_splitTypeAttributes, 'RPT08');

        initDictionaryCode('#xml-label-splitType', 'RPT07', false);
        initDictionaryCode('#xml-label-labelValueType', 'RPT09', false);
        initDictionaryCode('#xml-label-labelCloseType', 'RPT12', false);
        // 是否文本结构段下拉框
        _textSegment.combobox({
            onChange: function (newValue, oldValue) {
                if (!equals(newValue, oldValue)) {
                    var readonly = false;
                    var required = true;
                    if (newValue === 'SYS0201') {
                        // 是文本结构段
                        readonly = true;
                        required = false;
                        _enName.textbox('clear');
                        _labelCloseType.combobox('setValue', '');
                    } else {
                        // 非文本结构段设置分隔类型为空
                        _splitType.combobox('setValue', '');
                    }
                    _labelCloseType.combobox({
                        readonly: readonly,
                        required: required
                    });
                    _labelValueType.combobox({
                        readonly: readonly,
                        required: required
                    });
                    _labelValueExps.textbox({
                        readonly: readonly
                    });
                    _nullReplaceChar.textbox({
                        readonly: readonly
                    });
                    _loopSplitChar.textbox({
                        readonly: readonly
                    });
                    _splitType.combobox({
                        readonly: !readonly,
                        required: !required
                    });
                    _enName.textbox({
                        readonly: readonly,
                        required: required
                    });
                }
            }
        });
        // 是否换行下拉框
        _lineBreak.combobox({
            onChange: function (newValue, oldValue) {
                if (!equals(newValue, oldValue)) {
                    var readonly = false;
                    var required = true;
                    if (newValue === 'SYS0202') {
                        readonly = true;
                        required = false;
                        _lineBreakChar.textbox('clear');
                    }
                    _lineBreakChar.textbox({
                        readonly: readonly,
                        required: required
                    });
                }
            }
        });

        // 分隔类型下拉框变更事件
        _splitType.combobox({
            onChange: function (newValue, oldValue) {
                var readonly = false;
                var required = true;
                if (newValue != 'RPT0702') {
                    readonly = true;
                    required = false;
                    _splitCharType.combobox('clear');
                    _splitChar.textbox('clear');
                }
                _splitCharType.combobox({
                    readonly: readonly,
                    required: required
                });
                _splitChar.textbox({
                    readonly: readonly,
                    required: required
                });
            }
        });
        // 标签值类型下拉框 此下拉框业务变可能交频繁，因此未提出公共逻辑代码部分
        _labelValueType.combobox({
            onChange: function (newValue, oldValue) {
                if (!equals(newValue, oldValue)) {
                    // SQL结果映射值、循环填报类容、调用参数时 标签值表达式 必输
                    var _labelValueExpsRequired = true;
                    var _labelValueExpsReadonly = false;
                    if (newValue != 'RPT0901' && newValue != 'RPT0910' && newValue != 'RPT0913') {
                        _labelValueExpsRequired = false;
                        _labelValueExpsReadonly = true;
                        _labelValueExps.textbox('clear');
                    }
                    _labelValueExps.textbox({
                        readonly: _labelValueExpsReadonly,
                        required: _labelValueExpsRequired
                    });
                    // 空值替代内容可编辑
                    var _nullReplaceCharReadonly = false;
                    // 标签闭合方式可选择
                    var _labelCloseTypeReadOnly = false;
                    var _labelCloseTypeValue = _labelCloseType.combobox('getValue');
                    // 循环填报内容分隔符可编辑
                    var _loopSplitCharReadonly = false;
                    var _loopSplitCharRequired = true;
                    if (newValue === 'RPT0901') {// sql结果映射值
                        // 空值代替类容可编辑
                        _nullReplaceCharReadonly = false;
                        // 标签闭合方式设置为双闭合标签 且 不可选择
                        _labelCloseTypeValue = 'RPT1202';
                        _labelCloseTypeReadOnly = true;
                        // 循环填报内容分隔符不可编辑
                        _loopSplitCharReadonly = true;
                        _loopSplitCharRequired = false;
                    }
                    if (newValue === 'RPT0902' || newValue === 'RPT0903') {// 空值 或 常量值
                        // 空值代替类容不可编辑
                        _nullReplaceCharReadonly = true;
                        // 循环填报内容分隔符不可编辑
                        _loopSplitCharReadonly = true;
                        _loopSplitCharRequired = false;
                    }
                    if (newValue === 'RPT0904' || newValue === 'RPT0905' || newValue === 'RPT0906'
                        || newValue === 'RPT0907' || newValue === 'RPT0908' || newValue === 'RPT0909'
                        || newValue === 'RPT0911') {// “系统日期1”“系统日期2”“系统时间”“批量日期1”“批量日期2”、“顺序号”、“下级标签”
                        // 空值代替类容不可编辑
                        _nullReplaceCharReadonly = true;
                        // 循环填报内容分隔符不可编辑
                        _loopSplitCharReadonly = true;
                        _loopSplitCharRequired = false;
                        // 标签闭合方式设置为双闭合标签 且 不可选择
                        _labelCloseTypeValue = 'RPT1202';
                        _labelCloseTypeReadOnly = true;
                    }
                    if (newValue === 'RPT0910') {// 循环填报内容
                        // 空值代替类容可编辑
                        _nullReplaceCharReadonly = false;
                        // 标签闭合方式设置为双闭合标签 且 不可选择
                        _labelCloseTypeValue = 'RPT1202';
                        _labelCloseTypeReadOnly = true;
                    }
                    if (newValue === 'RPT0913') {// 调用参数
                        // 空值代替类容可编辑
                        _nullReplaceCharReadonly = false;
                        // 标签闭合方式设置为双闭合标签 且 不可选择
                        _labelCloseTypeValue = 'RPT1202';
                        _labelCloseTypeReadOnly = true;
                        // 循环填报内容分隔符不可编辑
                        _loopSplitCharReadonly = true;
                        _loopSplitCharRequired = false;
                    }
                    if (_nullReplaceCharReadonly) {
                        _nullReplaceChar.textbox('clear');
                    }
                    if (_loopSplitCharRequired) {
                        _loopSplitChar.textbox('clear');
                    }
                    _nullReplaceChar.textbox({
                        readonly: _nullReplaceCharReadonly
                    });
                    _labelCloseType.combobox({
                        readonly: _labelCloseTypeReadOnly
                    });
                    _labelCloseType.combobox('setValue', _labelCloseTypeValue);
                    _loopSplitChar.textbox({
                        required: _loopSplitCharRequired,
                        readonly: _loopSplitCharReadonly
                    });
                    _loopSplitCharType.combobox({
                        required: _loopSplitCharRequired,
                        readonly: _loopSplitCharReadonly
                    });
                }
            }
        });
        // 是否循环标签
        _loopLabel.combobox({
            onChange: function (newValue, oldValue) {
                if (newValue != oldValue && newValue.length > 0) {
                    var _required = true;
                    var _readonly = false;
                    if (newValue != 'SYS0201') {
                        _required = false;
                        _readonly = true;
                        _relationSqlId.combotree('setValue', '');
                    }
                    _relationSqlId.combotree({
                        readonly: _readonly,
                        required: _required
                    });
                }
            }
        });
    }

    function equals(newValue, oldValue) {
        return newValue === oldValue || newValue.length == 0;
    }

    function initBtn() {
       bindClickEvent('add-xml-label', function () {
           initAddOperation();
       });
       bindClickEvent('edit-xml-label', function () {
           initUpdateOperation(null);
       });
       bindClickEvent('remove-xml-label', function () {
           deleteXmlLabelDefinedData();
       });
       bindClickEvent('remove-xml-label-attribute', function () {
           setLabelProperty();
       });
       bindClickEvent('remove-xml-label-dataItem', function () {
           setDataItem();
       });
    }

    function initAddOperation() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var trees = _tree.tree('getChecked');
        if (isEmptyArray(trees) && trees.length != 1) {
            showWarningMessage('请在xml标签树中选择新增标签的所属标签。');
            _show.hide();
            return;
        }
        var tree = trees[0];
        if (tree.type === 'SYS0201' || tree.flag === 'RPT1201') {
            showWarningMessage('当前xml标签为文本结构段或单闭合标签，不允许新增子标签。');
            return;
        }
        _xmlLabelEditForm.form('clear');
        _parentLabelId.val(tree.id);
        bindClickEvent('xml-label-save-btn', function () {
            saveOrUpdate('save');
        });
        _show.show();
    }

    function initUpdateOperation(node) {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        if (isNullObject(node)) {
            var trees = _tree.tree('getChecked');
            if (isEmptyArray(trees) && trees.length != 1) {
                showWarningMessage('请在xml标签树中勾选或双击需要修改的xml标签。');
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
            bindClickEvent('xml-label-save-btn', function () {
                saveOrUpdate('update');
            });
        }
    }

    function getOne(labelId) {
        var _ajaxObj = {
            url: baseUrl + '/reportXmlLabel/getOne',
            async: false,
            data: {
                subjectId: _subjectId,
                fileId: _fileId,
                labelId: labelId
            }
        };
        var data = {};
        customAjaxSubmit(_ajaxObj, function (result) {
            data = result;
        });
        return data;
    }

    function saveOrUpdate(url) {
        if (_xmlLabelEditForm.form('validate')) {
            $.messager.confirm('提示信息', '确认保存当前xml标签配置信息？', function(r){
                if (r) {
                    var _ajaxObj = {
                        url: baseUrl + '/reportXmlLabel/' + url,
                        data: {
                            subjectId: _subjectId,
                            fileId: _fileId,
                            labelId: $('#xml-label-edit-id').val(),
                            parentLabelId: _parentLabelId.val(),
                            textSegment: $('#xml-label-textSegment').combobox('getValue'),
                            loopLabel: _loopLabel.combobox('getValue'),
                            labelNameCn: $('#xml-label-name-cn').textbox('getValue'),
                            labelNameEn: _enName.textbox('getValue'),
                            lineBreak: _lineBreak.combobox('getValue'),
                            lineBreakChar: _lineBreakChar.textbox('getValue'),
                            splitType: _splitType.combobox('getValue'),
                            splitCharType: _splitCharType.combobox('getValue'),
                            splitChar: _splitChar.textbox('getValue'),
                            mainLabel: $('#xml-label-mainLabel').combobox('getValue'),
                            labelValueType: _labelValueType.combotree('getValue'),
                            labelValueExps: _labelValueExps.textbox('getValue'),
                            relationSqlId: _relationSqlId.combotree('getValue'),
                            sequenceNo: $('#xml-label-sequence-no').numberbox('getValue'),
                            nullReplaceChar: _nullReplaceChar.textbox('getValue'),
                            labelCloseType: _labelCloseType.combobox('getValue'),
                            loopSplitChar: _loopSplitChar.textbox('getValue'),
                            loopSplitCharType: _loopSplitCharType.combobox('getValue'),
                            comnt: $('#xml-label-comment').textbox('getValue')
                        }
                    };
                    customAjaxSubmit(_ajaxObj, successCallback);
                }
            });
        };
    }

    function deleteXmlLabelDefinedData() {
        if (!reportSubjectOpt.isInsertOrRepealStatus(_subjectStatus)) {
            showWarningMessage('所属报送主体已发布或已删除，禁止修改。');
            return;
        }
        var trees = _tree.tree('getChecked');
        if (isEmptyArray(trees) || trees.length != 1) {
            showWarningMessage('请在xml标签树中勾选一条需要删除的SQL。');
            return;
        }
        var node = trees[0];
        if (node.id === 'ROOT') {
            return;
        }
        $.messager.confirm('提示信息', '确认删除['+ node.text +'] xml标签配置信息？', function(r) {
            if (r) {
                var _ajaxObj = {
                    url: baseUrl + '/reportXmlLabel/delete',
                    data: {
                        subjectId: _subjectId,
                        fileId: _fileId,
                        labelId: node.id,
                        parentLabelId: node.parentId
                    }
                };
                customAjaxSubmit(_ajaxObj, successCallback);
            }
        });
    }

    function setLabelProperty() {
        var trees = _tree.tree('getChecked');
        if (isEmptyArray(trees) && trees.length != 1) {
            showWarningMessage('请在xml标签树中选择新增标签的所属标签。');
            _show.hide();
            return;
        }
        var tree = trees[0];
        if (tree.type === 'SYS0201') {
            showWarningMessage('当前xml标签为文本结构段，不能配置标签属性。');
            return;
        }
        if (tree.id === 'ROOT') {
            return;
        }
        var cacheData = {
            subjectId: _subjectId,
            fileId: _fileId,
            labelId: tree.id,
            subjectStatus: _subjectStatus
        };
        new xmlLabelPropertyDefined(baseUrl).init(cacheData);
    }

    function setDataItem() {
        var trees = _tree.tree('getChecked');
        if (isEmptyArray(trees) && trees.length != 1) {
            showWarningMessage('请在xml标签树中选择新增标签的所属标签。');
            _show.hide();
            return;
        }
        var tree = trees[0];
        if (tree.type != 'SYS0201') {
            showWarningMessage('非文本结构段不能配置数据项信息。');
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
            splitType: data.splitType
        };
        new dataItemDefined(baseUrl).init(cacheData);
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
            url: baseUrl + '/reportXmlLabel/initXmlLabelTree?subjectId=' + _subjectId + '&fileId=' + _fileId,
            //tree 启用复选框
            checkbox: true,
            //禁用全选
            cascadeCheck: false,
            //显示树线条
            lines: true,
            onBeforeExpand: function(node) {
                _tree.tree('options').url = baseUrl + '/reportXmlLabel/listTreeNode?subjectId='
                    + _subjectId + '&fileId=' + _fileId + '&parentLabelId=' + node.id;
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