function pubNotice(baseUrl) {

    this.init = function() {
        initDataGrid();
        initBtn();
    }

    function initBtn() {
        $('#pub-notice-query-btn').unbind().bind('click', function () {
            query();
        });
    }

    function query() {
        var queryParams = {
            noticeTitle: $('#pub-notice-query-name').textbox('getValue'),
            startDt: $('#pub-notice-query-start-time').datebox('getValue'),
            endDt: $('#pub-notice-query-end-time').datebox('getValue')
        }
        $('#pub-notice-data-grid').datagrid('load', queryParams);
    }

    function initDataGrid() {
        var initDataGrid = {
            url: baseUrl + '/pubNotice/listReleasedPubNotice',
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
                field: "noticeTitle", title: "公告名称", width: 100, align: 'center'
            }, {
                field: "relsOper", title: "公告发布人", width: 50, align: 'center'
            }, {
                field: "relsDt", title: "公告发布日期", width: 50, align: 'center'
            }, {
                field: "matrDt", title: "公告到期日期", width: 50, align: 'center'
            }, {
                field: "noticeContent", title: "公告类容", width: 300, align: 'center'
            }]]
        };
        $('#pub-notice-data-grid').datagrid(initDataGrid);
    }
}