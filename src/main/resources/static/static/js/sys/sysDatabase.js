/**
 * 描述：初始化系统数据源管理页面
 */
function initSysDatabasePage(){
    initSysDatabase_DG('dg_sys_database');
    initTextboxByDatabase('dsNm_se','',20,false,'100','20');
    initComboboxByDatabase('dbTyp_se','SYS15','',false,'100','20');
}

/**
 * 描述：初始化数据源数据列表
 * @param id
 */
function initSysDatabase_DG(id){
    $('#'+id).datagrid({
        url: basePath + 'sysDatabase/getByPage',
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sys_database_tool',
        frozenColumns:[[]],
        columns:[[
            {field:'ck',title:'',width:100,checkbox:true},
            {field:'id',title:'数据源ID',width:100,align:'center'},
            {field:'dsNm',title:'数据源名称',width:100,align:'center'},
            {field:'dbTyp',title:'数据源类型',width:150,align:'center'},
            {field:'dbNm',title:'数据库名称',width:150,align:'center'},
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
function searchSysDatabase(){
    $('#dg_sys_database').datagrid('load',{
        dsNm:$('#dsNm_se').val(),
        dbTyp:$('#dbTyp_se').val()
    })
}

/**
 * 描述：重置查询条件
 */
function resetsearchSysDatabase(){
    $('#dsNm_se').textbox('reset');
    $('#dbTyp_se').combobox('reset');
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
        var row = $('#dg_sys_database').datagrid('getSelected');
        if (row == null || row == ''){
            $.messager.alert("提示","请选择数据源");
        }else{
            url = basePath + url + '?type=' + type + '&id=' + row.id;
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
function submitFormBySysDatabase(formId, url) {
    //验证框校验
    if (!$("#" + formId).form('validate')){
        return false;
    }
    $('#id').textbox('enable');
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
                refreshDataGridBySelf("dg_sys_database");
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
function toDeleteSysDatabase(url){
    url = basePath + url;
    var ids = "";
    var selRows = $('#dg_sys_database').datagrid('getChecked');
    if (selRows == null || selRows == ''){
        $.messager.alert("提示","请选择数据源");
    }else{
        $.each(selRows,function (index,row) {
            if (index == selRows.length){
                ids = ids + row.id;
            } else {
                ids = ids + row.id + ",";
            }
        })
        $.messager.confirm('提示','您确定要删除选中的数据源吗?',function(r){
            if (r){
                $.ajax({
                    url:url,
                    type:'POST',
                    dataType:'JSON',
                    data:{'ids':ids},
                    success:function (data) {
                        if (data.success){
                            refreshDataGridBySelf('dg_sys_database');
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


function closeWindowBySysDatabase(id) {
    $('#'+id).window('close');
}


function initTextboxByDatabase(id,echoValue,maxlength,disabled,width,height) {
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

function initComboboxByDatabase(id,dicType,echoValue,disabled,width,height){
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

function initDateBoxByDatabase(id,echoValue,width,height){
    $('#'+id).datebox({
        editable:false,
        required:true,
        width:width,
        height:height
    });
    $('#'+id).datebox('setValue',echoValue);

}