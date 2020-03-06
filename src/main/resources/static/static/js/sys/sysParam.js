/**
 * 描述：初始化系统参数页面
 */
function initSysParamPage(){
    initSysParam_DG('dg_sys_param');
    initTextboxByParam('paramNm_se','',40,false,'150','20');
    initComboboxByParam('paramTyp_se','SYS08','',false,'150','20');
}

/**
 * 描述：初始化系统参数列表
 * @param id
 */
function initSysParam_DG(id){
    $('#'+id).datagrid({
        url: basePath + 'sysParam/getByPage',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sys_param_tool',
        frozenColumns:[[
            {field:'ck',title:'',width:50,checkbox:true},
            {field:'paramId',title:'参数编号',width:150,align:'center'},
            {field:'paramNm',title:'参数名称',width:150,align:'center'},
            {field:'paramTyp',title:'参数类型',width:100,align:'center'}
        ]],
        columns:[[
            {field:'paramValue',title:'参数值',width:300,align:'center'},
            {field:'paramComnt',title:'参数说明',width:300,align:'center'},
            {field:'crtDt',title:'创建日期',width:150,align:'center'},
            {field:'creatr',title:'创建人',width:150,align:'center'},
            {field:'finlModfyDt',title:'最后修改日期',width:150,align:'center'},
            {field:'finlModifr',title:'最后修改人',width:150,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    })
}

function searchSysParam(){
    $('#dg_sys_param').datagrid('load',{
        paramNm:$('#paramNm_se').val(),
        paramTyp:$('#paramTyp_se').val()
    })
}

function resetSearchSysParam() {
    $('#paramNm_se').textbox('reset');
    $('#paramTyp_se').textbox('reset');
}

//新增窗口
function toInsertSysParam(winId,title,url,width,height) {
    url = basePath + url;
    openWindowSelf(winId,title,width,height,url);
}
//新增窗口
function toUpdateSysParam(winId,title,url,width,height) {
    var row = $('#dg_sys_param').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        url = basePath + url + '?paramId=' + row.paramId;
        openWindowSelf(winId,title, width, height, url);
    }
}
function toDeleteSysParam(url) {
    var rows = $('#dg_sys_param').datagrid('getChecked');
    if(rows == null || rows == ''){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        $.messager.confirm('提示','您确定要删除选中的参数吗?',function(r){
            if (r){
                var ids = '';
                $.each(rows,function (index,row) {
                    if (index == rows.length){
                        ids += row.paramId;
                    }else{
                        ids += row.paramId + ',';
                    }
                })
                url = basePath + url;
                $.ajax({
                    url:url,
                    type:'post',
                    dataType:'json',
                    data:{'ids':ids},
                    success:function (data) {
                        var data = JSON.stringify(data);
                        var data = eval('(' + data + ')');
                        if (data.success){
                            $.messager.alert("提示",data.message,'info');
                            $('#dg_sys_param').datagrid('reload');
                        }else{
                            $.messager.alert("提示",data.message,'error');
                        }
                    }
                })
            }
        })
    }
}

function initTextboxByParam(id,echoValue,maxlength,disabled,width,height) {
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

function initComboboxByParam(id,dicType,echoValue,disabled,width,height){
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

$.extend($.fn.validatebox.defaults.rules,{
    uniqueParamId:{  //功能ID唯一性校验
        validator:function (value,param) {
            return uniqueParamId(value);
        },
        message:'参数编号已存在，请重新输入'
    }
})

function uniqueParamId(id) {
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'sysParam/uniqueParamId',
        type:'POST',
        dataType:'JSON',
        data:{'paramId':id},
        success:function (data) {
            if(data == '0'){
                result = true;
            }
        }
    })
    return result;
}