function initReportFileManagePage() {
    initReportFileManagePageTextBox('subsystemNm_se');
    initReportFileManagePageTextBox('subsystemId_se');
    initReportFileManagePageTextBox('mansbjNm_se');
    initReportFileManageCombobox('stats_se','DFI02');
    initManageTableBySys('manage_sys_table','manage_mansbj_table');
    initManageTableByMansbj('manage_mansbj_table');
}

/**
 * 描述：初始化报文管理页面的子系统数据网格
 * @param id 目标数据网格ID
 * @param connectId 关联数据网格ID
 */
function initManageTableBySys(id,connectId) {
    $('#'+id).datagrid({
        url:basePath+'ReportFileManage/getAllSubSystemByFileManage',
        loadMsg:'数据加载中....',
        fitColumns:false,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#manage_sys_table_tool',
        frozenColumns:[[
            {field:'sb',title:'',width:100,checkbox:true},
            {field:'subsystemId',title:'系统编号',width:100,align:'center'},
            {field:'subsystemNm',title:'系统名称',width:160,align:'center'}
        ]],
        columns:[[
            {field:'url',title:'服务访问路径',width:300,align:'left'},
            {field:'subsystemComnt',title:'系统说明',width:300,align:'left'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html(""); //去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        },
        onCheck:function (rowIndex, rowData) {
            var id = rowData.subsystemId;
            $('#subsystemId_hidden').textbox('setValue',id);
            //-选中系统，加载其下的报送接口
            $('#'+connectId).datagrid('load',{
                subsystemId:id
            });
        }
    })
}

/**
 * 描述：初始化子系统对应的报送接口数据列表
 */
function initManageTableByMansbj(id) {
    $('#'+id).datagrid({
        url:basePath+'ReportFileManage/getAllMansbjBySubsystemId',
        loadMsg:'数据加载中....',
        fitColumns:false,
        singleSelect:false,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#manage_mansbj_table_tool',
        frozenColumns:[[
            {field:'subjectId',title:'',width:100,checkbox:true},
            {field:'subjectName',title:'接口名称',width:100,align:'center'},
            {field:'subsystemId',title:'所属子系统',width:100,align:'center'}
        ]],
        columns:[[
            {field:'status',title:'报送接口状态',width:100,align:'center'},
            {field:'comnt',title:'接口说明',width:300,align:'left'},
            {field:'creator',title:'创建人',width:100,align:'center'},
            {field:'createDt',title:'创建日期',width:100,align:'center'},
            {field:'finallyModifier',title:'最后修改人',width:100,align:'center'},
            {field:'finallyModifyDt',title:'最后修改日期',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html(""); //去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    })

}
/**
 * 初始化报文管理页面的文本框样式
 * @param id
 */
function initReportFileManagePageTextBox(id) {
    $('#'+id).textbox({
        type:'text',
        width:'150px',
        height:'20px'
    })
}

/**
 * 初始化报文管理页面的下拉框样式
 * @param id
 * @param dicType
 */
function initReportFileManageCombobox(id,dicType) {
    $('#'+id).combobox({
        url:basePath+'dictionary/getSysDictryCdListById?dictryId='+dicType,
        valueField:'dictryId',
        textField:'dictryNm',
        width:'150px',
        height:'20px',
        editable:false,
        panelHeight:'auto'
    })
}

/**
 * 描述：初始化日期时间框
 * @param id
 */
function initReportFileManageDateTimeBox(id) {
    $('#'+id).datetimebox({
        showSeconds:true,
        timeSeparator:':',
        width:'150px',
        height:'20px',
        editable:false
    })
}

/**
 * 描述：子系统查询
 */
function searchSubsystem(){
    $('#manage_sys_table').datagrid('load',{
        subsystemId:$('#subsystemId_se').val(),
        subsystemNm:$('#subsystemNm_se').val()
    })
}

/**
 * 描述：重置子系统查询条件
 */
function resetSearchSubsystem() {
    $('#subsystemId_se').textbox('clear');
    $('#subsystemNm_se').textbox('clear');
}

/**
 * 报送接口查询
 */
function searchMansbj() {
    $('#manage_mansbj_table').datagrid('load',{
        subsystemId:$('#subsystemId_hidden').val(),
        mansbjNm:$('#mansbjNm_se').val(),
        stats:$('#stats_se').val()
    })
}
/**
 * 描述：重置接口查询条件
 */
function resetSearchMansbj() {
    $('#mansbjNm_se').textbox('clear');
    $('#stats_se').textbox('clear');
}

/**
 * 描述：跳转报文生成及报送记录页面
 * @param flag
 */
function toReportFileRecordsPage(id,flag){
    //-设置隐藏属性
    $('#flag_hidden').textbox('setValue',flag);
    var rows = $('#manage_mansbj_table').datagrid('getChecked');
    if (rows == null || rows == ''){
        $.messager.alert('提示',"请选择报送接口！");
    }else{
        var ids = '';
        $.each(rows,function (index,row) {
            if (index == rows.length){
                ids += row.subjectId;
            } else{
                ids += row.subjectId + '_';
            }
        });
        //-设置隐藏属性
        $('#ids_hidden').textbox('setValue',ids);
        var url = basePath + 'ReportFileManage/toReportFileRecordsPage';
        if (flag == 'now'){
            openWindowSelf(id,'报文生成及报送记录列表','1000px','500px',url);
        }else if(flag == 'hist'){
            openWindowSelf(id,'报文生成及报送历史记录列表','1000x','500px',url);
        }
    }
}

/**
 * 初始化记录页面
 * @param id
 */
function initReportFileRecordsPage(id){
    var mansbjIdList = $('#ids_hidden').val();
    initReportFileRecordTable(id,mansbjIdList);
    initReportFileManagePageTextBox('crtBatchId_se');
    initReportFileManageDateTimeBox('fileCrtTm_start_se');
    initReportFileManageDateTimeBox('fileCrtTm_end_Se');
    initReportFileManageCombobox('rptSubmitTyp_se','RPT17');
    initReportFileManageDateTimeBox('lstSubmitTm_start_se');
    initReportFileManageDateTimeBox('lstSubmitTm_end_se');
}
/**
 * 描述：初始化报文生成记录数据列表
 */
function initReportFileRecordTable(id,mansbjIdList) {
    $('#'+id).datagrid({
        url:basePath+'ReportFileManage/getAllFileRecordsByMansbjId?mansbjIdList='+mansbjIdList,
        loadMsg:'数据加载中....',
        fitColumns:false,
        singleSelect:false,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#manage_records_table_tool',
        frozenColumns:[[
            {field:'mansbjId',title:'接口名称',width:80,align:'center'},
            {field:'id',title:'',hidden:true},
            {field:'fileNm',title:'报文文件名',width:150,align:'center'},
            {field:'fileNmCn',title:'报文文件中文名',width:150,align:'center'},
            {field:'downloadFlag',title:'',hidden:true},
            {field:'op',title:'操作',width:150,align:'center',formatter:function (value,row,index) {
                var btn = '';
                if (row.downloadFlag == 'SYS0201'){
                    btn = btn + '<a href="javascript:void(0)" onclick="toDownloadReportFile(\'' + row.id + '\')">下载</a>&nbsp&nbsp&nbsp' +
                        '<a href="javascript:void(0)" onclick="toDownloadRecordPage(\'' + row.id + '\')">下载记录</a>&nbsp&nbsp&nbsp';
                };
                if (row.rptSubmitTyp == '手动触发上报' || row.rptSubmitTyp == 'RPT1702'){
                    btn = btn + '<a href="javascript:void(0)" onclick="toDownloadRecordPage(\'' + row.id + '\')">报送</a>&nbsp&nbsp&nbsp'
                }
                return btn;
            }}
        ]],
        columns:[[
            {field:'crtBatchId',title:'生成批次号',width:200,align:'center'},
            {field:'batchDt',title:'批次日期',width:100,align:'center'},
            {field:'fileCrtDt',title:'报文生成日期',width:100,align:'center'},
            {field:'fileCrtTm',title:'报文生成时间',width:100,align:'center'},
            {field:'invokor',title:'报文生成操作员',width:120,align:'center'},
            {field:'orgCd',title:'报文文件所属机构',width:200,align:'center'},
            {field:'rptIsDownload',title:'报文是否已下载',width:100,align:'center'},
            {field:'rptIsSubmit',title:'报文是否已上报',width:100,align:'center'},
            {field:'rptSubmitTyp',title:'报文上报方式',width:100,align:'center'},
            {field:'rptInterfaceTyp',title:'报文上报接口类型',width:100,align:'center'},
            {field:'lstSubmitor',title:'最近一次上报操作员',width:120,align:'center'},
            {field:'lstSubmitTm',title:'最近一次上报时间',width:120,align:'center'},
            {field:'lstDownloadTm',title:'最近一次下载时间',width:120,align:'center'},
            {field:'lstDownloader',title:'最近一次下载操作员',width:120,align:'center'},
            {field:'filePath',title:'报文文件路径',width:400,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html(""); //去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    })
}

/**
 * 描述：查询报送生成记录
 */
function searchFileRecord(){
    $('#manage_records_table').datagrid('load',{
        crtBatchId:$('#crtBatchId_se').val(),
        fileCrtTm_start:$('#fileCrtTm_start_se').val(),
        fileCrtTm_end:$('#fileCrtTm_end_Se').val(),
        rptSubmitTyp:$('#rptSubmitTyp_se').val(),
        lstSubmitTm_start:$('#lstSubmitTm_start_se').val(),
        lstSubmitTm_end:$('#lstSubmitTm_end_se').val()
    })
}

/**
 * 描述：重置报送生成记录查询条件
 */
function resetSearchFileRecord() {
    $('#crtBatchId_se').textbox('clear');
    $('#rptSubmitTyp_se').textbox('clear');
    $('#fileCrtTm_start_se').textbox('clear');
    $('#fileCrtTm_end_Se').textbox('clear');
    $('#lstSubmitTm_start_se').textbox('clear');
    $('#lstSubmitTm_end_se').textbox('clear');
}


/**
 * 描述：下载报文文件
 */
function toDownloadReportFile(id){
    $.ajax({
        url:basePath+'ReportFileManage/downLoadReportFile',
        type:'post',
        dataType:'json',
        data:{'id':id},
        success:function (data) {
            if (data.success){
                $.messager.alert("提示","下载成功");
            }else {
                $.messager.alert("提示","下载失败");
            }
        }
    })
}

function toDownloadRecordPage(id){
    var url = basePath + "ReportFileManage/toDownloadRecordPage?id="+id;
    openWindowSelf('win_recordPage','报文下载记录列表','600x','400px',url);
}

/**
 * 描述：初始化下载记录的数据网格
 * @param id
 */
function initDownloadRecordTable(tbId,id) {
    $('#'+tbId).datagrid({
        url:basePath+'ReportFileManage/getAllDowmloadLogById?id='+id,
        loadMsg:'数据加载中....',
        fitColumns:false,
        singleSelect:false,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        frozenColumns:[[
            {field:'operIp',title:'报文名',width:200,align:'center'},
            {field:'operation',title:'报文文件中文名',width:120,align:'center'},
        ]],
        columns:[[
            {field:'operator',title:'下载操作员',width:100,align:'center'},
            {field:'operTyp',title:'下载机构',width:200,align:'center'},
            {field:'operTm',title:'下载时间',width:150,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html(""); //去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    })
}
