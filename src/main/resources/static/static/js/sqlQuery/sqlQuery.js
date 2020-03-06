/**
 * 初始化SQL查询数据网格
 * @author wuyu
 */
function initDataGrid() {
    var ajaxObj = {
        url: baseUrl + '/sqlQuery/getColumns',
        data: {
            databaseId: $('#sql-query-dataSource').combobox('getValue'),
            sql: $('#sql-text').val()
        }
    };
    customAjaxSubmit(ajaxObj, function (result) {
        if (result.success) {
            dataGrid(createColumn(result.data));
        } else {
            showErrorMessage(result.message);
        }
    }, function () {
        showErrorMessage('初始化数据表格失败。');
    })
}

/**
 * 数据网格
 * @param columns 列对象数组
 * @author wuyu
 */
function dataGrid(columns) {
    var initDataGrid = {
        url : baseUrl + '/sqlQuery/query',
        fit: true,
        fitColumns : true,
        border : true,
        pagination : true,
        pageSize : 10,
        pageList : [ 10, 20],
        singleSelect : true,
        checkOnSelect : true,
        selectOnCheck : true,
        remoteSort : false,
        striped : true,
        nowrap : false,
        queryParams: {
            databaseId: $('#sql-query-dataSource').combobox('getValue'),
            sql: $('#sql-text').val()
        },
        onLoadError: function() {
            showErrorMessage('数据加载失败。');
        },
        columns : [columns]
    };

    $('#sql-query-data-grid').datagrid(initDataGrid);
}

/**
 * 创建列对象数组
 * @param data 列集合
 * @returns {Array}
 * @author wuyu
 */
function createColumn(data) {
    var columns = [];
    for (var i = 0; i < data.length; i++) {
        var column = {};
        column['field'] = data[i];
        column['title'] = data[i];
        column['align'] = 'center';
        column['width'] = '100px';
        columns.push(column);
    }
    return columns;
}