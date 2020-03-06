function dataItemDefinedHis(baseUrl) {
    var segmentData = {};

    var _dataGridWin = $('#data-item-dataHis-grid-win');
    var _dataGrid = $('#data-item-dataHis-grid');


    this.init = function (cacheData) {
        segmentData = cacheData;
        initDataGrid();
        _dataGridWin.window('open');
    }
    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportDataItemHis/query',
            fit : true,
            fitColumns: false,
            border: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 20],
            singleSelect: true,
            checkOnSelect: true,
            selectOnCheck: true,
            remoteSort: false,
            striped: true,
            nowrap: false,
            queryParams: {
                subjectId: segmentData.subjectId,
                fileId: segmentData.fileId,
                segmentId: segmentData.segmentId,
                versionNo:segmentData.versionNo
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "dataItemId", title: "数据项ID",  align: 'center', width: 170
            }, {
                field: "dataItemName", title: "中文名称",  align: 'center', width: 200
            }
            ]],
            columns: [[
                {
                    field: "subjectId", title: "所属报送主体ID", hidden: true
                }, {
                    field: "fileId", title: "所属报文ID", hidden: true
                }, {
                    field: "segmentId", title: "所属结构段ID", hidden: true
                }, {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "dataItemType", title: "数据项类型", align: 'center', width: 150
                }, {
                    field: "loopItem", title: "是否循环填报数据项", align: 'center', width: 150
                }, {
                    field: "dataLength", title: "数据项长度",  align: 'center', width: 150
                }, {
                    field: "fixLocation", title: "定长补位位置",  align: 'center', width: 150
                }, {
                    field: "fixCharType", title: "定长补位字符类型",  align: 'center', width: 150
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                },{
                    field: "versionNo", title: "版本号",  align: 'center', width: 90
                }]],
        };
        _dataGrid.datagrid(initDataGrid);
    }
}