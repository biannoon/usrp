var fileNameOptHis = fileNameOptHis || {};

fileNameOptHis.subjectId = '';
fileNameOptHis.objectId = '';
fileNameOptHis.objectType = '';
fileNameOptHis.fileType = '';
fileNameOptHis.subjectStatus = '';
fileNameOptHis.versionNo = '';
fileNameOptHis.initDefaultValue = function(subjectId, objectId, objectType, fileType, subjectStatus,versionNo) {
    fileNameOptHis.subjectId = subjectId;
    fileNameOptHis.objectId = objectId;
    fileNameOptHis.objectType = objectType;
    fileNameOptHis.fileType = fileType;
    fileNameOptHis.subjectStatus = subjectStatus;
    fileNameOptHis.versionNo = versionNo;
}
fileNameOptHis.init = function(data) {
    fileNameOptHis.initDefaultValue(data.subjectId, data.objectId, data.objectType, data.fileType, data.status,data.versionNo);
    var _win = $('#report-file-nameHis-data-grid-win');
    _win.window({
        title: data.title,
        onBeforeClose: function() {
            fileNameOptHis.initDefaultValue('', '', '', '', '','');
        }
    });
    fileNameOptHis.query();
    _win.window('open');
}

fileNameOptHis.getSelectedData = function() {
    var row = $('#report-file-nameHis-data-grid').datagrid('getSelected');
    if (row === undefined || row === null || row.length < 1) {
        showWarningMessage('请选择一行文件名配置信息数据。');
        return null;
    }
    return row;
}

fileNameOptHis.query = function () {
    var initDataGrid = {
        url: baseUrl + '/reportFileNameHis/queryHis',
        fit : true,
        fitColumns: false,
        border: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        singleSelect: true,
        checkOnSelect: true,
        selectOnCheck: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        queryParams: {
            subjectId: fileNameOptHis.subjectId,
            objectId: fileNameOptHis.objectId,
            objectType: fileNameOptHis.objectType,
            fileType: fileNameOptHis.fileType,
            versionNo:fileNameOptHis.versionNo
        },
        frozenColumns:[[{
            field: "dataItemId", title: "数据项ID",  align: 'center', width: 160
        }
        ]],
        columns: [[{
            field: "subjectId", title: "报送主体ID",  align: 'center', width: 180
        }, {
            field: "objectId", title: "所属对象ID",  align: 'center', width: 180
        }, {
            field: "objectType", title: "所属对象类型",  align: 'center',width: 80
        }, {
            field: "fileType", title: "文件类型", align: 'center', width: 80
        }, {
            field: "sequenceNo", title: "排序序号",  align: 'center', width: 60
        }, {
            field: "comnt", title: "数据项说明",  align: 'center', width: 200
        }, {
            field: "dataItemType", title: "数据项类型",  align: 'center', width: 100
        }, {
            field: "dataItemExps", title: "数据项内容表达式",  align: 'center', width: 120
        }, {
            field: "sequenceNoLength", title: "顺序号长度",  align: 'center', width: 80
        }, {
            field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
        }, {
            field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
        }, {
            field: "versionNo", title: "版本号",  align: 'center', width: 60
            }]],
    };
    $('#report-file-nameHis-data-grid').datagrid(initDataGrid);
        //去除表头的复选框（表头复选框有全选的作用）

}