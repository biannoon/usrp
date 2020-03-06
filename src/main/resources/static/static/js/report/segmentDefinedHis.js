function segmentDefinedHis(baseUrl) {
    var _subjectId = '';
    var _fileId = '';
    var _subjectStatus = '';
    var _versionNo = '';


    var _dataGridWin = $('#segment-dataHis-grid-win');
    var _dataGrid = $('#segment-dataHis-grid');

    this.init = function(fileData) {
        initDataGrid(fileData.subjectId, fileData.fileId,fileData.versionNo);
        _dataGridWin.window({
            title: fileData.fileName + ' 文件结构段配置信息'
        });
        _dataGridWin.window('open');
        _subjectId = fileData.subjectId;
        _fileId = fileData.fileId;
        _versionNo = fileData.versionNo;
        var subjectData = $('#report-subject-history-data-grid').datagrid('getSelected');
        _subjectStatus = subjectData.status;
    }


    function setDataItem() {
        var segmentData = getSelected();
        if (!isNullObject(segmentData)) {
            var cacheData = {
                subjectId: _subjectId,
                fileId: _fileId,
                segmentId: segmentData.segmentId,
                subjectStatus: _subjectStatus,
                splitType: segmentData.splitType,
                versionNo:_versionNo
            };
            new dataItemDefinedHis(baseUrl).init(cacheData);
        }
    }

    function getSelected() {
        var row = _dataGrid.datagrid('getSelected');
        if (isNullObject(row)) {
            showWarningMessage('请选择一行报文文件信息数据。');
        }
        return row;
    }

    function initDataGrid(subjectId, fileId,versionNo) {
        var initDataGrid = {
            url: baseUrl + '/reportSegmentHis/query',
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
                subjectId: subjectId,
                fileId: fileId,
                versionNo:versionNo
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "segmentId", title: "报文文件ID", hidden: true
            }, {
                field: "segmentName", title: "结构段名称",  align: 'center', width: 200
            }
            ]],
            columns: [[
                {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "loopSegment", title: "是否循环结构段", hidden: true
                }, {
                    field: "loopSegmentText", title: "是否循环结构段", align: 'center', width: 100
                }, {
                    field: "mainSegment", title: "是否主要报送内容段", hidden: true
                }, {
                    field: "mainSegmentText", title: "是否主要报送内容段", align: 'center', width: 120
                }, {
                    field: "splitType", title: "分隔类型", hidden: true
                }, {
                    field: "splitTypeText", title: "分隔类型", align: 'center', width: 100
                }, {
                    field: "relationSqlId", title: "关联SQL ID",  align: 'center', width: 160
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                }, {
                    field: "versionNo", title: "版本号",  align: 'center', width: 90
                }]],
            toolbar: [ '-', {
                text: '数据项配置',
                iconCls: 'pic_336',
                handler: function () {
                    setDataItem();
                }
            }]
        };
        _dataGrid.datagrid(initDataGrid);
    }
}