/**
 * 描述：初始化系统数据源管理页面
 */
function initSysSubsystemPage(){
    initSysSubsystem_DG('dg_sys_subsystem');
    initTextboxBySubsystem('subsystemNm_se','',50,false,'100','20');
}

/**
 * 描述：初始化数据源数据列表
 * @param id
 */
function initSysSubsystem_DG(id){
    $('#'+id).datagrid({
        url: basePath + 'sysSubsystem/getByPage',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sys_subsystem_tool',
        frozenColumns:[[
            {field:'ck',title:'',width:100,checkbox:true},
            {field:'subsystemId',title:'子系统编号',width:150,align:'center'},
            {field:'subsystemNm',title:'子系统名称',width:150,align:'center'}
        ]],
        columns:[[
            {field:'iconUrl',title:'子系统图标',width:100,align:'center'},
            {field:'url',title:'子系统服务访问路径',width:200,align:'center'},
            {field:'subsystemComnt',title:'子系统说明',width:200,align:'center'},
            {field:'creatr',title:'创建人',width:150,align:'center'},
            {field:'crtDt',title:'创建日期',width:150,align:'center'},
            {field:'finlModifr',title:'最后修改人',width:150,align:'center'},
            {field:'finlModfyDt',title:'最后修改日期',width:150,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    });
}

/**
 * 描述：查询系统数据源
 */
function searchSysSubsystem(){
    $('#dg_sys_subsystem').datagrid('load',{
        subsystemNm:$('#subsystemNm_se').val()
    })
}

/**
 * 描述：重置查询条件
 */
function resetsearchSysSubsystem(){
    $('#subsystemNm_se').textbox('reset');
}


/**
 * 描述：打开功能窗口
 * @param type  类型：insert-新增    update-修改   detail-详情
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toDeailPage(type,winId,title,url,width,height){
    if (type == 'update' || type == 'detail'){
        var row = $('#dg_sys_subsystem').datagrid('getSelected');
        if (row == null || row == ''){
            $.messager.alert("提示","请选择子系统");
        }else{
            url = basePath + url + '?type=' + type + '&id=' + row.subsystemId;
            openWindowSelf(winId,title,width,height,url);
        }
    }else{
        url = basePath + url + '?type=' + type;
        openWindowSelf(winId,title,width,height,url);
    }
}


/**
 * 描述：提交操作
 * @param type
 * @param url
 * @param data
 * @param datatype
 */
function submitFormBySysSubsystem(formId, url) {
    //验证框校验
    if (!$("#" + formId).form('validate')){
        return false;
    }
    $('#subsystemId').textbox('enable');
    //-提交ajax
    $.ajax({
        url:basePath + url,
        type:'post',
        dataType:'text',
        data:$('#' + formId).serialize(),
        success:function (data) {
            var data = eval('(' + data + ')');
            var result = data.data;
            if (data.success){
                //-刷新字典代码列表
                refreshDataGridBySelf("dg_sys_subsystem");
                //-禁用提交按钮
                $('#btn_add').linkbutton('disable');
                $.messager.alert("提示",data.message);
            }else{
                $.messager.alert("提示",data.message);
            }
        }
    })
}

/**
 * 描述：删除数据源
 */
function toDeleteSysSubsystem(url){
    url = basePath + url;
    var ids = "";
    var selRows = $('#dg_sys_subsystem').datagrid('getChecked');
    if (selRows == null || selRows == ''){
        $.messager.alert("提示","请选择子系统");
    }else{
        $.each(selRows,function (index,row) {
            if (index == selRows.length){
                ids = ids + row.subsystemId;
            } else {
                ids = ids + row.subsystemId + ",";
            }
        })
        if (!hasFuncUnderSystem(ids)){
            $.messager.alert("提示","子系统下存在功能菜单，不能删除");
        }else {
            $.messager.confirm('提示','您确定要删除选中的子系统吗?',function(r){
                if (r){
                    $.ajax({
                        url:url,
                        type:'POST',
                        dataType:'JSON',
                        data:{'ids':ids},
                        success:function (data) {
                            if (data.success){
                                refreshDataGridBySelf('dg_sys_subsystem');
                                $.messager.alert('提示','删除成功');
                            }else{
                                $.messager.alert('提示','删除失败');
                            }
                        },
                        error:function () {
                            $.messager.alert('警告','请求失败');
                        }
                    })
                }
            })
        }
    }
}


function closeWindowBySysSubsystem(id) {
    $('#'+id).window('close');
}


function initTextboxBySubsystem(id,echoValue,maxlength,disabled,width,height) {
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

function initDateBoxBySubsystem(id,echoValue,width,height){
    $('#'+id).datebox({
        editable:false,
        required:true,
        width:width,
        height:height
    });
    $('#'+id).datebox('setValue',echoValue);
}

$.extend($.fn.validatebox.defaults.rules,{
    uniqueSubsystemId:{  //功能ID唯一性校验
        validator:function (value,param) {
            return uniqueSubsystemId(value);
        },
        message:'子系统编号已存在，请重新输入'
    }
})

function uniqueSubsystemId(id) {
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'sysSubsystem/uniqueSubsystemId',
        type:'POST',
        dataType:'JSON',
        data:{'subsystemId':id},
        success:function (data) {
            if(data == '0'){
                result = true;
            }
        }
    })
    return result;
}

/**
 * 描述：判断子系统下是否存在功能菜单
 * @param id
 */
function hasFuncUnderSystem(id){
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'sysSubsystem/hasFuncUnderSystem',
        type:'POST',
        dataType:'JSON',
        data:{'id':id},
        success:function (data) {
            if(data == '0'){
                result = true;
            }
        }
    })
    return result;
}
