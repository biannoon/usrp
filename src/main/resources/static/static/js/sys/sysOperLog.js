/**
 * 描述：初始化系统操作日志页面
 */
function initSysOperLogPage() {
    initSysOperLog_DG('dg_sys_operLog');
    initComboboxByOperLog('queryTyp_se','SYS02','',false,'150','20');
    initTextboxByOperLog('operTyp_se','',100,false,'150','20');
    initSysDictryComponent('operTyp_se', 'operTyp_hid_se', 'SYS09', 'win_sys_operLog', '0', '', '0', '650', '500');
    initDateTimeByOperLog('startTm_se','','150','20');
    initDateTimeByOperLog('endTm_se','','150','20');
    initTextboxByOperLog('org_se','',100,false,'150','20');
    //-初始化机构选择组件
    initSysOrgInfoComponent('org_se', 'org_hid_se', 'win_sys_operLog', '0', '', 'true', '0', '0', '', '600', '400');
}

/**
 * 描述：初始化系统操作日志列表
 */
function initSysOperLog_DG(id){
    $('#'+id).datagrid({
        url: basePath + 'sysOperlog/getList',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sys_operLog_tool',
        frozenColumns:[[
            {field:'ck',title:'',width:100,checkbox:true},
            {field:'operator',title:'操作人员',width:150,align:'center'},
            {field:'operTyp',title:'操作类型',width:150,align:'center'},
            {field:'objId',title:'操作机构',width:150,align:'center'}
        ]],
        columns:[[
            {field:'operTm',title:'操作时间',width:150,align:'center'},
            {field:'operIp',title:'ip地址',width:150,align:'center'},
            {field:'operation',title:'操作内容',width:300,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    });
}


/**
 * 描述：条件查询
 */
function searchOperlog() {
    $('#dg_sys_operLog').datagrid('load',{
        queryTyp: $('#queryTyp_se').val(),
        operTyp: $('#operTyp_hid_se').val(),
        operTm: $('#startTm_se').val(),
        operIp:$('#endTm_se').val(),
        objId:$('#org_hid_se').val()
    });
}

/**
 * 描述：重置查询条件
 */
function resetSearchOperlog(){
    $('#queryTyp_se').combobox('reset');
    $('#operTyp_se').textbox('reset');
    $('#operTyp_hid_se').textbox('reset');
    $('#startTm_se').datetimebox('reset');
    $('#endTm_se').datetimebox('reset');
    $('#org_hid_se').textbox('reset');
    $('#org_se').textbox('reset');
}

/**
 * 描述：初始化文本框组件
 * @param id
 * @param echoValue
 * @param maxlength
 * @param disabled
 * @param width
 * @param height
 */
function initTextboxByOperLog(id,echoValue,maxlength,disabled,width,height) {
    $('#'+id).textbox({
        type:'text',
        width:width,
        height:height,
        value:echoValue,
        disabled:disabled
    });
    if (maxlength != 0){
        $('#'+id).textbox('textbox').attr('maxlength',maxlength);
    }
}

/**
 * 描述：初始化组合框组件
 * @param id
 * @param dicType
 * @param echoValue
 * @param disabled
 * @param width
 * @param height
 */
function initComboboxByOperLog(id,dicType,echoValue,disabled,width,height){
    $('#'+id).combobox({
        url:basePath + 'dictionary/getSysDictryCdListById?dictryId='+dicType,
        valueField:'dictryId',
        textField:'dictryNm',
        width:width,
        height:height,
        value:echoValue,
        disabled:disabled
    });
}

/**
 * 描述：初始化日期框组件
 * @param id
 * @param echoValue
 * @param width
 * @param height
 */
function initDateBoxByOperLog(id,echoValue,width,height){
    $('#'+id).datebox({
        editable:false,
        required:true,
        width:width,
        height:height
    });
    $('#'+id).datebox('setValue',echoValue);
}

/**
 * 描述：初始化时间框组件
 * @param id
 * @param echoValue
 * @param width
 * @param height
 */
function initDateTimeByOperLog(id,echoValue,width,height){
    $('#'+id).datetimebox({
        editable:false,
        showSeconds:true,
        width:width,
        height:height
    });
    $('#'+id).datetimebox('setValue',echoValue);

}