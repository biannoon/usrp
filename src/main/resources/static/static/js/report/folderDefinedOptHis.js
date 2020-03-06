var folderDefinedOptHis = folderDefinedOptHis || {};

folderDefinedOptHis.subjectId = '';
folderDefinedOptHis.subjectStatus = '';
folderDefinedOptHis.subjectReportType = '';
folderDefinedOptHis.subjectZipManner = '';
folderDefinedOptHis.versionNo = '';

folderDefinedOptHis.init = function() {
    folderDefinedOptHis.initDefaultValue(row);
    var _win = $('#report-folderH-data-grid-win');
    _win.window({
        title: row.subjectName + ' 文件夹配置历史信息',
        onBeforeClose: function() {
            folderDefinedOptHis.initDefaultValue({});
        }
    });
    folderDefinedOptHis.initDataGrid();
    _win.window('open');
}

folderDefinedOptHis.initDefaultValue = function(subjectData) {
    folderDefinedOptHis.subjectId = subjectData['subjectId'];
    folderDefinedOptHis.subjectStatus = subjectData['status'];
    folderDefinedOptHis.subjectReportType = subjectData['reportType'];
    folderDefinedOptHis.subjectZipManner = subjectData['zipManner'];
    folderDefinedOptHis.versionNo = subjectData['versionNo'];
}

folderDefinedOptHis.fileNameDefined = function(fileType) {
    var row = folderDefinedOptHis.getSelected();
    if (row === null) {
        showWarningMessage('请选择一行文件名配置信息数据。');
        return;
    }
    var tempTitle = ' 压缩包文件名配置';
    if (fileType == 'RPT1401' && row.zipManner != 'RPT0202' && row.zipManner != 'RPT0203'
        && row.zipManner != 'RPT0204') {
        showWarningMessage('未配置压缩类型。');
        return;
    }
    if (fileType == 'RPT1403') {
        tempTitle = ' 文件夹名称配置历史';
    }
    if (fileType == 'RPT1401') {
        tempTitle = ' 压缩包文件名配置历史';
    }
    var data = {
        subjectId: folderDefinedOptHis.subjectId,
        objectId: row.folderId,
        objectType: 'RPT1302',
        fileType: fileType,
        title: row.folderName + tempTitle,
        status: folderDefinedOptHis.subjectStatus,
        versionNo:folderDefinedOptHis.versionNo
    };
    fileNameOptHis.init(data);
}

folderDefinedOptHis.getSelected = function() {
    var row = $('#report-folderHis-data-grid').datagrid('getSelected');
    if (row === undefined || row === null) {
        showWarningMessage('请选择一行文件夹信息数据。');
        return null;
    }
    return row;
}
folderDefinedOptHis.initDataGrid = function () {
    var initDataGrid = {
        url: baseUrl + '/reportFolderHis/queryHis',
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
            subjectId: folderDefinedOptHis.subjectId,
            versionNo:folderDefinedOptHis.versionNo
        },
        frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "folderId", title: "编号",  align: 'center', width: 170
            }, {
                field: "folderName", title: "中文名称",  align: 'center', width: 120
            }
        ]],
        columns: [[{
            field: "comnt", title: "说明",  align: 'center', width: 300, formatter: function (value, row, index) {
                return reportSubjectOpt.filterData(value);
            }
        }, {
            field: "singleTypeFileFlag", title: "是否单一类型报文", align: 'center', width: 110
        }, {
            field: "zipManner", title: "压缩方式", hidden: true
        }, {
            field: "zipMannerText", title: "压缩方式",  align: 'center', width: 120
        }, {
            field: "zipMaxSize", title: "压缩包大小限制（M）",  align: 'center', width: 125
        }, {
            field: "orgFieldType", title: "机构字段映射类型",  align: 'center', width: 125
        }, {
            field: "orgFieldExps", title: "机构字段映射表达式",  align: 'center', width: 125
        }, {
            field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
        }, {
            field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
        }, {
            field: "sequenceNo", title: "排序号", align: 'center', width: 50
        }, {
            field: "versionNo", title: "版本号", align: 'center', width: 50
        }]],
        toolbar: [ '-', {
            text: '文件夹名称配置历史',
            iconCls: 'pic_225',
            handler: function () {
                folderDefinedOptHis.fileNameDefined('RPT1403');
            }
        }, '-', {
            text: '压缩包文件名配置历史',
            iconCls: 'icon-zip',
            handler: function () {
                folderDefinedOptHis.fileNameDefined('RPT1401');
            }
        }]
    };

    $('#report-folderHis-data-grid').datagrid(initDataGrid);
}