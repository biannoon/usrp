function sqlParamDefinedHis(baseUrl) {
    var _subjectData = {};

    var _dataGridWin = $('#sql-param-dataHis-grid-win');
    var _dataGrid = $('#sql-param-dataHis-grid');


    this.init = function (subjectData) {
        _subjectData = subjectData;
        initDataGrid();
        _dataGridWin.window('open');
    }

    function getChecked() {
        return _dataGrid.datagrid('getChecked');
    }

    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportSqlParamHis/queryHis',
            fit : true,
            fitColumns: false,
            border: true,
            pagination: true,
            pageSize: 10,
            pageList: [10, 20],
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            remoteSort: false,
            striped: true,
            nowrap: false,
            queryParams: {
                subjectId: _subjectData.subjectId,
                versionNo:_subjectData.versionNo
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "paramEnName", title: "英文名称",  align: 'center', width: 100
            }, {
                field: "paramCnName", title: "中文名称",  align: 'center', width: 200
            }
            ]],
            columns: [[
                {
                    field: "subjectId", title: "所属报送主体ID", hidden: true
                }, {
                    field: "paramType", title: "参数类型", align: 'center', width: 180
                }, {
                    field: "replaceSqlId", title: "参数占位字段所属SQL ID", align: 'center', width: 170
                }, {
                    field: "replaceField", title: "参数占位字段名称", align: 'center', width: 150
                }, {
                    field: "comnt", title: "参数说明",  align: 'center', width: 300, formatter: function (value, row, index) {
                        return reportSubjectOpt.filterData(value);
                    }
                }, {
                    field: "finallyModifier", title: "最后修改人",  align: 'center', width: 90
                }, {
                    field: "finallyModifyDt", title: "最后修改时间",  align: 'center', width: 90
                }, {
                    field: "versionNo", title: "版本号",  align: 'center', width: 90
                }]],
        };
        _dataGrid.datagrid(initDataGrid);
    }
}