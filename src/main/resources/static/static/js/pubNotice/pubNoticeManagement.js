/**
 * 系统公告管理
 * @param baseUrl 项目上下文:usrp
 * @author wuyu
 */
function pubNoticeManagement(baseUrl) {
    this.init = function() {
        initDataGrid();
        initBtn();
        initCombobox();
        initEditWin();
        initDataList();
    }

    /**
     * 初始化系统公告数据列表
     * @author wuyu
     */
    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/pubNotice/listPubNotice',
            fit: true,
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
            columns: [[{
                field: "noticeId", title: "编号", width: 50, checkbox: true
            }, {
                field: "noticeTitle", title: "公告名称", width: 100, align: 'center'
            }, {
                field: "stus", title: "公告状态", width: 50, align: 'center', hidden: true
            }, {
                field: "stusNm", title: "公告状态", width: 50, align: 'center'
            }, {
                field: "relsOper", title: "公告发布人", width: 50, align: 'center'
            }, {
                field: "relsDt", title: "公告发布日期", width: 50, align: 'center'
            }, {
                field: "matrDt", title: "公告到期日期", width: 60, align: 'center'
            }, {
                field: "trgtTyp", title: "发布对象", width: 50, align: 'center',
                formatter: function (value, row, index) {
                    return isEmpty(value) ? '<spam>所有人</spam>' : value;
                }
            }, {
                field: "noticeContent", title: "公告类容", width: 200, align: 'center'
            }]],
            toolbar: [{
                text: '新增',
                iconCls: 'icon-add',
                handler: function () {
                    initAdd();
                }
            }, '-', {
                text: '更新',
                iconCls: 'icon-edit',
                handler: function () {
                    initUpdate();
                }
            }, '-', {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    deletePubNotice();
                }
            }, '-', {
                text: '发布',
                iconCls: 'icon-redo',
                handler: function () {
                    release();
                }
            }]
        };
        $('#pub-notice-management-data-grid').datagrid(initDataGrid);
    }

    /**
     * 初始化新增系统公告
     * @author wuyu
     */
    function initAdd() {
        initSaveBtn();
        $('#pub-notice-management-edit-win').window('open');
    }

    /**
     * 初始化更新系统公告
     * @author wuyu
     */
    function initUpdate() {
        var rows = getCheckedData();
        if (isSelectOne(rows)) {
            var row = rows[0];
            if (notAddStatus(row)) {
                showWarningMessage('[' + row.noticeTitle + '] 处于已发布或已删除状态。');
                return;
            }
            queryDetail(row.noticeId, function (data) {
                $('#pub-notice-management-edit-form').form('load', data);
                $('#pub-notice-management-edit-win').window('open');
                initUpdateBtn();
            });
        } else {
            showWarningMessage('请选择一行系统公告数据。');
        }
    }

    /**
     * 初始化系统公告编辑窗口
     * @author wuyu
     */
    function initEditWin() {
        var _win = $('#pub-notice-management-edit-win');
        _win.window({
            onBeforeClose: function() {
                $('#pub-notice-management-edit-form').form('clear');
                $('#pub-notice-management-edit-type').combobox({
                    required: true,
                    readonly: false,
                    missingMessage: '请选择发布对象。'
                });
            }
        });
    }

    /**
     * 关闭系统公告编辑窗口
     * @author wuyu
     */
    function closeWin() {
        $('#pub-notice-management-edit-win').window('close');
    }

    /**
     * 存储系统公告信息
     * @param url
     * @param operationText
     * @author wuyu
     */
    function save(url, operationText) {
        var _form = $('#pub-notice-management-edit-form');
        if (_form.form('validate')) {
            var data = getPubNoticeAoObj();
            if (!checkPubNoticeValues(data)) {
                return;
            }
            $.messager.confirm('提示信息', '确认' + operationText + '当前系统公告信息？', function(r){
                if (r) {
                    var ajaxObj = {
                        url: baseUrl + url,
                        contentType: 'application/json; charset=utf-8',
                        data: data
                    }
                    customAjaxSubmit(ajaxObj, editSuccessCallback, function () {})
                }
            })
        }
    }

    /**
     * 检核公告数据是否合格
     * @param data 公告数据
     * @returns {boolean}
     * @author wuyu
     */
    function checkPubNoticeValues(data) {
        var newData = JSON.parse(data);
        var now = new Date();
        var newDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        if (newData.matrDt <= newDate) {
            showWarningMessage('发布日期只能选择大于今天的日期。');
            return false;
        }
        if (newData.pub === 'SYS0202') {
            if (isEmpty(newData.trgtId)) {
                showWarningMessage('请选择公告发布具体对象。');
                return false;
            }
            if (isEmpty(newData.trgtTyp)) {
                showWarningMessage('请选择公告发布对象。');
                return false;
            }
        }
        return true;
    }

    /**
     * 删除系统公告
     * @author wuyu
     */
    function deletePubNotice() {
        var request = {url: '/pubNotice/delete', text: '删除', errorStatus: '已处于删除状态'};
        batchSubmit(request, isDeletedStatus);
    }

    /**
     * 批量发布系统公告
     * @author wuyu
     */
    function release() {
        var request = {url: '/pubNotice/releaseBatch', text: '发布', errorStatus: '已发布或已删除'};
        batchSubmit(request, notAddStatus);
    }

    /**
     * 发布系统公告
     * @author wuyu
     */
    function releaseOne(){
        save('/pubNotice/release', '发布');
    }

    /**
     * 批量提交
     * @param request 自定义json请求对象
     *  例：{url: '/pubNotice/releaseBatch', text: '发布', errorStatus: '已发布或已删除'}
     * @param checkStatusFun 检核系统公告信息状态方法
     * @author wuyu
     */
    function batchSubmit(request, checkStatusFun) {
        var rows = getCheckedData();
        if (!isEmpty(rows)) {
            var checkResult = checkStatus(rows,checkStatusFun);
            if (!checkResult.success) {
                showWarningMessage(request.text + '失败，以下系统公告[' + checkResult.unqualifiedNames + ']'
                    + request['errorStatus'] + '。');
                return;
            }
            $.messager.confirm('提示信息', '确认' + request.text + '已勾选的系统公告信息？', function(r){
                if (r) {
                    var ajaxObj = {
                        url: baseUrl + request.url,
                        data: {
                            ids: checkResult.qualifiedIds
                        }
                    }
                    customAjaxSubmit(ajaxObj, editSuccessCallback, function () {});
                }
            })
        } else {
            showWarningMessage('请勾选需要' + request.text + '的系统公告数据。');
        }
    }

    /**
     * 查询系统公告信息
     * @author wuyu
     */
    function query() {
        var queryParams = {
            noticeTitle: $('#pub-notice-management-query-name').textbox('getValue'),
            startDt: $('#pub-notice-management-query-start-time').datebox('getValue'),
            endDt: $('#pub-notice-management-query-end-time').datebox('getValue'),
            status: $('#pub-notice-management-query-status').combobox('getValue'),
            targetType: $('#pub-notice-management-query-type').combobox('getValue')
        }
        $('#pub-notice-management-data-grid').datagrid('load', queryParams);
    }

    /**
     * 初始化按钮单击事件
     * @author wuyu
     */
    function initBtn() {
        $('#pub-notice-management-query-btn').unbind().bind('click', function () {
            query();
        });
        $('#pub-notice-management-cancel-btn').unbind().bind('click', function () {
            closeWin();
        });
        $('#pub-notice-management-release-btn').unbind().bind('click', function () {
            releaseOne();
        });
        $('#pub-object-id-to-right-all').unbind().bind('click', function () {
            mergingAllPubObject('left-object-id-data-list', 'right-object-id-data-list')
        });
        $('#pub-object-id-to-right').unbind().bind('click', function () {
            mergingPubObject('left-object-id-data-list', 'right-object-id-data-list')
        });
        $('#pub-object-id-to-left-all').unbind().bind('click', function () {
            mergingAllPubObject('right-object-id-data-list', 'left-object-id-data-list')
        });
        $('#pub-object-id-to-left').unbind().bind('click', function () {
            mergingPubObject('right-object-id-data-list', 'left-object-id-data-list')
        });
        $('#pub-notice-management-target-btn').unbind().bind('click', function () {
            $('#pub-object-win').window('open');
            $('#right-object-id-data-list').datalist('resize');
            $('#left-object-id-data-list').datalist('resize');
        });
    }

    /**
     * 初始化新增公告时暂存按钮单击事件
     * @author wuyu
     */
    function initSaveBtn() {
        $('#pub-notice-management-save-btn').unbind().bind('click', function () {
            save('/pubNotice/save', '暂存');
        });
    }

    /**
     * 初始化更新系统公告时暂存按钮单击事件
     * @author wuyu
     */
    function initUpdateBtn() {
        $('#pub-notice-management-save-btn').unbind().bind('click', function () {
            save('/pubNotice/update', '更新');
        });
    }

    /**
     * 初始化页面下拉框数据
     * @author wuyu
     */
    function initCombobox() {
        initDictionaryCode('#pub-notice-management-query-status', 'PUB01', true);
        initDictionaryCode('#pub-notice-management-edit-pub', 'SYS02', false);

        var _comboboxAttributes = [{
            id: '#pub-notice-management-query-type',
            needEmptyOption: true
        },{
            id: '#pub-notice-management-edit-type',
            needEmptyOption: false
        }];
        initRepeatDictionaryCode(_comboboxAttributes, 'PUB02');

        $('#pub-notice-management-edit-type').combobox({
            onChange: function (newValue, oldValue) {
                if (newValue.length > 0 && newValue != oldValue) {
                    if (newValue === 'PUB0201') {
                        reloadDataList('/sysGroup/getGroupNotInPubNotice', '/sysGroup/getGroupListWithNotice');
                    } else if (newValue === 'PUB0202') {
                        reloadDataList('/sysRole/getRoleListNotInPubNotice', '/sysRole/getRoleListWithPubNotice');
                    }
                }
            }
        });
        $('#pub-notice-management-edit-pub').combobox({
            onChange: function (newValue, oldValue) {
                if (newValue.length > 0 && newValue != oldValue) {
                    var _combobox = $('#pub-notice-management-edit-type');
                    if (newValue === 'SYS0201') {
                        _combobox.combobox({
                            required: false,
                            readonly: true,
                            missingMessage: ''
                        });
                        $('#left-object-id-data-list').datalist('loadData', []);
                        $('#right-object-id-data-list').datalist('loadData', []);
                    } else if (newValue === 'SYS0202') {
                        _combobox.combobox({
                            required: true,
                            readonly: false,
                            missingMessage: '请选择发布对象。'
                        });
                    }
                }
            }
        })
    }

    /**
     * 获取系统公告编辑窗口中的公告数据
     * @author wuyu
     */
    function getPubNoticeAoObj() {
        var targetObj = getDataListData('left-object-id-data-list');
        var targetIds = [];
        $(targetObj).each(function (i) {
            targetIds.push(targetObj[i].value);
        });
        var data = {
            noticeId: $('#pub-notice-management-edit-id').val(),
            noticeTitle: $('#pub-notice-management-edit-title').textbox('getValue'),
            noticeContent: $('#pub-notice-management-edit-content').val(),
            matrDt: $('#pub-notice-management-edit-matrDt').datebox('getValue'),
            pub: $('#pub-notice-management-edit-pub').combobox('getValue'),
            trgtTyp: $('#pub-notice-management-edit-type').combobox('getValue'),
            trgtId: targetIds
        };
        return JSON.stringify(data);
    }

    /**
     * 保存系统公告信息后的success返回事件
     * @author wuyu
     */
    function editSuccessCallback(result) {
        if (result.success) {
            $('#pub-notice-management-data-grid').datagrid('reload');
            $('#pub-notice-management-edit-win').window('close');
            showMessage(result.message);
        } else {
            showErrorMessage(result.message);
        }
    }

    /**
     * 是否空数据
     * @author wuyu
     */
    function isEmpty(data) {
        return data === undefined || data === null || data.length < 1;
    }

    /**
     * 是否只选有一行数据
     * @param data
     * @returns {boolean}
     */
    function isSelectOne(data) {
        return !isEmpty(data) && data.length == 1;
    }

    /**
     * 获取datagrid中选择的行对象数据
     * @author wuyu
     */
    function getCheckedData() {
        return $('#pub-notice-management-data-grid').datagrid('getChecked');
    }

    /**
     * 编辑系统公告时校验当前系统公告的状态
     * @param rows
     * @param check
     * @returns {{success: boolean, unqualifiedNames: string, qualifiedIds: Array}}
     * @author wuyu
     */
    function checkStatus(rows, check) {
        var unqualifiedNames = [];
        var qualifiedIds = [];
        for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            if (check(row)) {
                unqualifiedNames.push(row.noticeTitle);
            } else {
                qualifiedIds.push(row.noticeId);
            }
        }
        return {success: unqualifiedNames.length < 1, unqualifiedNames: unqualifiedNames.join(','),
            qualifiedIds: qualifiedIds};
    }

    /**
     * 是否删除状态
     * @param row
     * @returns {boolean}
     * @author wuyu
     */
    function isDeletedStatus(row) {
        return row.stus == 'PUB0103';
    }

    /**
     * 是否新增状态
     * @param row
     * @returns {boolean}
     * @author wuyu
     */
    function notAddStatus(row) {
        return row.stus != 'PUB0101';
    }

    /*
     * 初始化datebox选择框只能选择大于当前日期的日期
     */
    function initDateBox(id) {
        $(id).datebox('calendar').calendar({
            validator: function (date) {
                var now = new Date();
                var newDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                return date > newDate;
            }
        });
    }

    /**
     * 查询单条公告数据详情
     * @param noticeId
     * @param successCallback
     * @author wuyu
     */
    function queryDetail(noticeId, successCallback) {
        var ajaxObj = {
            url: baseUrl + '/pubNotice/queryDetail',
            async: false,
            data: {
                noticeId: noticeId
            }
        }
        customAjaxSubmit(ajaxObj, successCallback, function () {})
    }

    /**
     * 合并公告发布具体对象数据
     * @param sourceDataListId 数据来源datalist控件id值
     * @param targetDataListId 目标datalist控件id值
     * @author wuyu
     */
    function mergingPubObject(sourceDataListId, targetDataListId) {
        refreshDataList(sourceDataListId, targetDataListId, $('#' + sourceDataListId).datalist('getSelections'));
    }

    /**
     * 合并所有公告发布具体对象数据
     * @param sourceDataListId 数据来源datalist控件id值
     * @param targetDataListId 目标datalist控件id值
     * @author wuyu
     */
    function mergingAllPubObject(sourceDataListId, targetDataListId) {
        refreshDataList(sourceDataListId, targetDataListId, getDataListData(sourceDataListId).concat());
    }

    /**
     * 刷新datalist控件
     * @param sourceDataListId
     * @param targetDataListId
     * @param rows
     */
    function refreshDataList(sourceDataListId, targetDataListId, rows) {
        var _source = $('#' + sourceDataListId);
        var _target = $('#' + targetDataListId);
        $(rows).each(function (i) {
            var rowIndex = _source.datalist('getRowIndex', rows[i]);
            _source.datalist('deleteRow', rowIndex);
        });
        rows = rows.concat(getDataListData(targetDataListId));
        _target.datalist('loadData', rows);
    }

    function getAjaxObj(url) {
        return {
            url: baseUrl + url,
            data: {
                pubNoticeId : $('#pub-notice-management-edit-id').val()
            }
        }
    }

    /**
     * 重新加载datalist控件数据
     * @param sourceUrl
     * @param targetUrl
     * @author wuyu
     */
    function reloadDataList(sourceUrl, targetUrl) {
        customAjaxSubmit(getAjaxObj(targetUrl), function (result) {
            $('#left-object-id-data-list').datalist('loadData', result);
        });
        customAjaxSubmit(getAjaxObj(sourceUrl), function (result) {
            $('#right-object-id-data-list').datalist('loadData', result);
        });
    }

    /**
     * 初始化datalist控件
     * @author  wuyu
     */
    function initDataList() {
        $('#left-object-id-data-list').datalist({
            fit:true,
            plain:true,
            valueField:'value',
            textField:'text',
            lines : true,
            singleSelect:false
        });
        $('#right-object-id-data-list').datalist({
            fit:true,
            plain:true,
            valueField:'value',
            textField:'text',
            lines : true,
            singleSelect:false
        })
    }

    /**
     * 获取datalist控件中的所有值
     * @param domId
     * @returns {jQuery}
     * @author wuyu
     */
    function getDataListData(domId) {
        return $('#' + domId).datalist('getRows')
    }
}