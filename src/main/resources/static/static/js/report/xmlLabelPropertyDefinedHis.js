function xmlLabelPropertyDefinedHis(baseUrl) {
    var initData = {};
    var _dataGrid = $('#xml-label-property-dataHis-grid');
    var _dataGridWin = $('#xml-label-property-dataHis-grid-win');

    this.init = function (cacheData) {
        initData = cacheData;
        initDataGrid();
        _dataGridWin.window('open');
    }
    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/reportXmlLabelPropertyHis/query',
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
                subjectId: initData.subjectId,
                fileId: initData.fileId,
                labelId: initData.labelId,
                versionNo:initData.versionNo
            },
            frozenColumns:[[{
                field: "checkbox", title: "编号",  checkbox: true
            },{
                field: "propertyId", title: "标签属性ID",  align: 'center', width: 170
            }, {
                field: "propertyNameCn", title: "中文名称",  align: 'center', width: 200
            }, {
                field: "propertyNameEn", title: "英文名称",  align: 'center', width: 100
            }
            ]],
            columns: [[
                {
                    field: "subjectId", title: "所属报送主体ID", hidden: true
                }, {
                    field: "fileId", title: "所属报文ID", hidden: true
                }, {
                    field: "labelId", title: "所属标签ID", hidden: true
                }, {
                    field: "sequenceNo", title: "排序号", align: 'center', width: 50
                }, {
                    field: "propertyValueType", title: "属性值类型", align: 'center', width: 150
                }, {
                    field: "propertyValueExps", title: "属性值表达式", align: 'center', width: 150
                }, {
                    field: "nullReplaceChar", title: "空值替代内容",  align: 'center', width: 150
                }, {
                    field: "comnt", title: "属性说明",  align: 'center', width: 300, formatter: function (value, row, index) {
                        return reportSubjectOpt.filterData(value);
                    }
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