var fileDefinedOptHis = fileDefinedOptHis || {};

fileDefinedOptHis.subjectId = '';
fileDefinedOptHis.subjectStatus = '';
fileDefinedOptHis.subjectReportType = '';
fileDefinedOptHis.versionNo = '';



fileDefinedOptHis.initDefaultValue = function(subjectId, status, subjectReportType,versionNo) {
    fileDefinedOptHis.subjectId = subjectId;
    fileDefinedOptHis.subjectStatus = status;
    fileDefinedOptHis.subjectReportType = subjectReportType;
    fileDefinedOptHis.versionNo = versionNo;
}

/**
 * 初始化
 */
fileDefinedOptHis.init = function () {

    var row = $('#report-subject-history-data-grid').datagrid('getSelected');
    if (row==null||row==undefined) {
        showWarningMessage('请选择一行报送主体信息数据。');
        return null;
    }
    fileDefinedOptHis.initDefaultValue(row.subjectId, row.status, row.reportType,row.versionNo);
    var _win = $('#report-file-dataHis-grid-win');
    _win.window({
        title: row.subjectName + ' 报文文件配置历史信息',
        onBeforeClose: function() {
            fileDefinedOptHis.initDefaultValue('', '', '');
        }
    });
    fileDefinedOptHis.initDataGrid();
    _win.window('open');
    sqlDefinedOpt.initComboTree('file-edit-relation-sql', fileDefinedOptHis.subjectId);
}
fileDefinedOptHis.getSelected = function() {
    var row = $('#report-file-dataHis-grid').datagrid('getSelected');
    if (isEmptyArray(row)) {
        showWarningMessage('请选择一行文件夹信息数据。');
        return null;
    }
    return row;
}
fileDefinedOptHis.fileNameDefined = function() {
    var row = fileDefinedOptHis.getSelected();
    if (row === null) {
        return;
    }
    var data = {
        subjectId: fileDefinedOptHis.subjectId,
        objectId: row.fileId,
        objectType: 'RPT1303',
        fileType: 'RPT1402',
        title: row.fileName + ' 文件名配置历史',
        status: fileDefinedOptHis.subjectStatus,
        versionNo:fileDefinedOptHis.versionNo
    };
    fileNameOptHis.init(data);
}

fileDefinedOptHis.initDataGrid = function () {
    var initDataGrid = {
        url: baseUrl + '/reportFileHis/query',
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
            subjectId: fileDefinedOptHis.subjectId,
            versionNo:fileDefinedOptHis.versionNo
        },
        frozenColumns:[[{
            field: "checkbox", title: "编号",  checkbox: true
        },{
            field: "fileId", title: "报文文件ID",  align: 'center', width: 170
        }, {
            field: "fileName", title: "中文名称",  align: 'center', width: 200
        }, {
            field: "folderId", title: "所属文件夹ID",  align: 'center', width: 170
        }
        ]],
        columns: [[
            {
                field: "sequenceNo", title: "排序号", align: 'center', width: 50
            }, {
                field: "fileType", title: "文件类型", hidden: true
            }, {
                field: "fileTypeText", title: "文件类型", align: 'center', width: 100
            }, {
                field: "bodyType", title: "报送体类型", hidden: true
            }, {
                field: "bodyTypeText", title: "报送体类型", align: 'center', width: 100
            }, {
                field: "recordLimit", title: "报送记录限制条数",  align: 'center', width: 120
            }, {
                field: "codeType", title: "文件编码格式",  align: 'center', width: 100
            }, {
                field: "comnt", title: "说明",  align: 'center', width: 300, formatter: function (value, row, index) {
                    return reportSubjectOpt.filterData(value);
                }
            },{
                field: "versionNo", title: "版本号",  align: 'center', width: 100
            }]],
        toolbar: [ {
            text: '文件名配置',
            iconCls: 'pic_336',
            handler: function () {
                fileDefinedOptHis.fileNameDefined();
            }
        }, '-', {
            text: '结构段或标签配置',
            iconCls: 'pic_220',
            handler: function () {
                var fileData = fileDefinedOptHis.getSelected();
                if (isNullObject(fileData)) {
                    return;
                }
                if (fileData.fileType === 'RPT0403') {
                    // xml 报文
                    new xmlLabelDefinedHis(baseUrl).init(fileData);
                    // 文本型报文
                } else if (fileData.fileType === 'RPT0401' || fileData.fileType === 'RPT0402') {
                    new segmentDefinedHis(baseUrl).init(fileData);
                }
            }
        }]
    };
    $('#report-file-dataHis-grid').datagrid(initDataGrid);
}
