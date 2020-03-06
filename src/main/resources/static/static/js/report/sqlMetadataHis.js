function sqlMetadataHis(baseUrl) {
    var _subjectStatus = {};
    var _sqlId = '';
    var _dataGrid = $('#sql-metadataHis-data-grid');
    var _dataGridWin = $('#sql-metadataHis-data-grid-win');
    var _versionNo = '';
    this.init = function (cacheData) {
        _subjectStatus = cacheData.subjectStatus;
        _sqlId = cacheData.sqlId;
        _versionNo=cacheData.versionNo;
        initDataGrid();
        _dataGridWin.window('open');
    }
    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportSqlMetadataHis/query',
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
                sqlId: _sqlId,
                versionNo:_versionNo
            },
            frozenColumns:[[
            ]],
            columns: [[{
                    field: "checkbox", title: "编号",  checkbox: true
                },{
                    field: "sqlId", title: "sqlId", hidden: true
                },{
                    field: "fieldEnName", title: "英文名称",  align: 'center', width: 200
                }, {
                    field: "fieldCnName", title: "中文名称",  align: 'center', width: 200
                }, {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                },
                {
                    field: "versionNo", title: "版本号",  align: 'center', width: 90
                }]],

        };
        _dataGrid.datagrid(initDataGrid);
    }
}