/**
 * 描述：初始化系统字典代码管理页面
 */
function initSysDictryPage(){
    initTextboxByDictry('dictryId_se','',20,false,'150','20');
    initTextboxByDictry('dictryNm_se','',50,false,'150','20');
    initTextboxByDictry('dictry_id_se','',20,false,'100','20');
    initTextboxByDictry('dictry_nm_se','',50,false,'100','20');
    initSysDictryCategoryDG('dg_dictry');
    initSysDictryValueDG('dg_dictry_value');
}

/**
 * 描述：初始化字典代码种类
 * @param id
 */
function initSysDictryCategoryDG(id){
    $('#'+id).datagrid({
        url: basePath + 'dictionary/getList?cdTyp=SYS0101',
        fitColumns: true,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_dictry_tool',
        frozenColumns:[[
            {field:'ck_group',title:'',width:100,checkbox:true},
            {field:'dictryId',title:'字典类型ID',width:100,align:'center'},
            {field:'dictryNm',title:'字典类型名称',width:100,align:'center'}
        ]],
        columns:[[
            {field:'pareDictryId',title:'上级字典类型ID',width:150,align:'center'},
            {field:'blngtoDictryId',title:'所属字典类型ID',width:150,align:'center'},
            {field:'cdTyp',title:'代码类型',width:150,align:'center'},
            {field:'dictryComnt',title:'字典说明',width:200,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        },
        onSelect:function (rowIndex, rowData) {
            var dicId = rowData.dictryId;
            $('#dictryId_hid').textbox('setValue',dicId);
            $('#dg_dictry_value').datagrid('load',{
                pareDictryId:dicId
            })
        }
    });
}

/**
 * 描述：初始化字典代码值
 * @param id
 */
function initSysDictryValueDG(id){
    $('#'+id).datagrid({
        url: basePath + 'dictionary/getList?cdTyp=SYS0102',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_dictry_value_tool',
        frozenColumns:[[
            {field:'ck',title:'',width:100,checkbox:true},
            {field:'dictryId',title:'代码值ID',width:100,align:'center'},
            {field:'dictryNm',title:'代码中文名',width:100,align:'center'},
            {field:'pareDictryId',title:'上级字典代码ID',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    });
}

/**
 * 描述：查询字典类型
 */
function searchDicType(){
    $('#dg_dictry').datagrid('load',{
        dictryId:$('#dictryId_se').val(),
        dictryNm:$('#dictryNm_se').val()
    })
}

/**
 * 描述：重置字典类型查询条件
 */
function resetDicType(){
    $('#dictryId_se').textbox('reset');
    $('#dictryNm_se').textbox('reset');
}

/**
 * 描述：查询字典值
 */
function searchDicValue(){
    $('#dg_dictry_value').datagrid('load',{
        dictryId:$('#dictry_id_se').val(),
        dictryNm:$('#dictry_nm_se').val(),
        pareDictryId:$('#dictryId_hid').val()
    })
}
/**
 * 描述：重置字典代码值查询条件
 */
function resetDicValue(){
    $('#dictry_id_se').textbox('reset');
    $('#dictry_nm_se').textbox('reset');
}


/**
 * 描述：打开新增窗口
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toSysDictryInsertPage(winId,title,url,width,height) {
    var url = basePath + url;
    openWindowSelf(winId,title,width,height,url);
}

/**
 * 描述：初始化字典代码新增页面
 */
function initSysDictryInsertPage(){
    initTextboxByDictry('dictryId_insert','',20,false,'150','20');
    initTextboxByDictry('dictryNm_insert','',50,false,'150','20');
    initTextboxByDictry('pareDictryId_insert','root',20,true,'150','20');
    initComboboxByDictry('cdTyp_insert','SYS01','SYS0101',true,'150','20');
    initTextboxByDictry('dictryComnt_insert','',200,false,'150','100');
    initSysDictryInsertPage_DG('dg_sys_dictry_insert','');
}

/**
 * 描述：打开修改页面
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toSysDictryUpdatePage(winId,title,url,width,height){
    var row = $('#dg_dictry').datagrid('getSelected');
    if (row == '' || row == null){
        $.messager.alert("提示","请选择需要修改的字典类型");
    }else{
        var url = basePath + url + '?dictryId=' + row.dictryId + '&blngtoDictryId=' + row.blngtoDictryId;
        openWindowSelf(winId,title,width,height,url);
    }
}

/**
 * 描述：删除系统字典代码
 */
function toDeleteSysDictry(url){
    var row = $('#dg_dictry').datagrid('getSelected');
    if (row == '' || row == null){
        $.messager.alert('提示','请选择需要删除的系统字典!');
    }else{
        var dictryId = row.dictryId;
        var blngtoDictryId = row.blngtoDictryId;
        $.ajax({
            url:basePath + url,
            type:'post',
            dataType:'json',
            data:{'dictryId':dictryId,'blngtoDictryId':blngtoDictryId},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    $('#dg_dictry').datagrid('reload');
                    $('#dg_dictry_value').datagrid('reload');
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        });
    }
}

/**
 * 描述：初始化字典代码新增页面的数据列表
 */
function initSysDictryInsertPage_DG(id,blngtoDictryId){
    $('#'+id).datagrid({
        url: basePath + 'dictionary/getChildList?blngtoDictryId='+blngtoDictryId,
        fitColumns: false,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sys_dictry_insert_tool',
        frozenColumns:[[
            {field:'ck',title:'',checkbox:true},
            {field:'dictryId',title:'代码值ID',width:100,align:'center'},
            {field:'dictryNm',title:'代码中文名',width:100,align:'center'},
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    });
}

/**
 * 描述：打开字典码值新增窗口
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 * @param flag          标记打开新增字典代码值窗口的位置    cache:新增时  db:列表页面
 */
function toSysDictryValueInsertPage(winId,title,url,width,height,flag){
    url = basePath + url + '?flag=' + flag  + '&winId=' + winId;
    openWindowSelf(winId,title,width,height,url);
}

/**
 * 描述：初始化字典码值新增窗口
 */
function initSysDictryValueInsertPage(){
    initTextboxByDictry('dictryId_value_insert','',20,false,'150','20');
    initTextboxByDictry('dictryNm_value_insert','',50,false,'150','20');
    //initTextboxByDictry('pareDictryId_value_insert',blngtoDictryId,20,true,'150','20');
}

/**
 * 描述：打开字典码值更新窗口
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 * @param flag          标记打开新增字典代码值窗口的位置    cache:新增时  db:列表页面
 */
function toSysDictryValueUpdatePage(winId,title,url,width,height,flag){
    if (flag == 'cache'){
        var rows = $('#dg_sys_dictry_insert').datagrid('getChecked');
        if (rows == ''){
            $.messager.alert("提示","请选择一条需要修改的代码值");
        }else if(rows.length > 1){
            $.messager.alert("提示","请选择一条需要修改的代码值")
        }else{
            $.each(rows,function (index,row) {
                var dictryId = row.dictryId;
                url = basePath + url + '?dictryId=' + dictryId + '&flag=' + flag + '&winId=' + winId;
                openWindowSelf(winId,title,width,height,url);
            })
        }
    } else if (flag == 'db'){
        var rows = $('#dg_dictry_value').datagrid('getChecked');
        if (rows == ''){
            $.messager.alert("提示","请选择一条需要修改的代码值");
        }else if(rows.length > 1){
            $.messager.alert("提示","请选择一条需要修改的代码值")
        }else{
            $.each(rows,function (index,row) {
                var dictryId = row.dictryId;
                var blngtoDictryId = $('#dictryId_hid').val();
                url = basePath + url + '?dictryId=' + dictryId + '&blngtoDictryId=' + blngtoDictryId + '&flag=' + flag + '&winId=' + winId;
                openWindowSelf(winId,title,width,height,url);
            })
        }
    }

}

/**
 * 描述：删除字典代码值
 * @param flag  标记打开新增字典代码值窗口的位置    insert:新增时  list:列表页面
 */
function deleteSysDictryValue(flag){
    var rows;
    var blngtoDictryId;
    var url = basePath;
    if (flag == 'cache'){
        rows = $('#dg_sys_dictry_insert').datagrid('getChecked');
        url = url + "dictionary/deleteValueFromCache";
    } else if (flag == 'db'){
        rows = $('#dg_dictry_value').datagrid('getChecked');
        blngtoDictryId = $('#dictryId_hid').val();
        url = url + "dictionary/deleteValueFromDB";
    }
    if (rows == ''){
        $.messager.alert("提示","请选择需要删除的记录");
    } else {
        var values = '';
        $.each(rows,function (index,row) {
            if (index == rows.length - 1){
                values = values + row.dictryId;
            }else{
                values = values + row.dictryId + '_';
            }
        })
        $.ajax({
            url:url,
            type:'post',
            dataType:'json',
            data:{'dictryId':values,'blngtoDictryId':blngtoDictryId},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    if (flag == 'cache'){
                        //-刷新数据列表
                        initSysDictryInsertPage_DG('dg_sys_dictry_insert');
                    } else if (flag == 'db'){
                        //-刷新数据列表
                        refreshDataGridBySelf('dg_dictry_value')
                    }
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        });
    }
}

/**
 * 描述：查看引用
 * @param
 */
function toSysDictryCdShowDraw(winId,title,url,width,height){
    var row = $('#dg_dictry').datagrid('getChecked');
    if (row == null || row == ''){
        $.messager.alert("提示","请选择查看引用的记录");
    }else{
        url = basePath + url;
        openWindowSelf(winId,title,width,height,url);
    }
}

/**
 * 描述：初始化文本框
 * @param id            文本框ID
 * @param echoValue     文本框回显值
 * @param maxlength     文本框最大输入长度
 * @param disabled      是否禁用
 * @param width
 * @param height
 */
function initTextboxByDictry(id,echoValue,maxlength,disabled,width,height) {
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
 * 描述：初始化组合框
 * @param id        组合框ID
 * @param dicType   下拉框字典类型代码
 * @param echoValue 回显数据
 * @param disabled  是否禁用
 * @param width
 * @param height
 *
 */
function initComboboxByDictry(id,dicType,echoValue,disabled,width,height){
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
 * 描述：关闭窗口
 * @param id
 * @param dgId
 */
function closeWindowBySysDictry(id,dgId) {
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        refreshDataGridBySelf(dgId);
    }
    clearCache();
    $('#'+id).window('close');
}
function closeWindowBySysDictryValue(id) {
    $('#'+id).window('close');
}

$.extend($.fn.validatebox.defaults.rules,{
    uniqueDictry:{  //字典种类唯一性校验
        validator:function (value,param) {
            return checkDictry(value,value,'db');
        },
        message:'字典代码已存在，请重新输入'
    }
})

/**
 * 描述：判断代码ID的唯一性
 * @param dictryId
 * @param blngtoDictryId
 * @returns {boolean}
 */
function checkDictry(dictryId,blngtoDictryId,flag){
    var result = false;
    var url;
    $.ajax({
        async:false,
        url:basePath + 'dictionary/checkDictryId',
        type:'post',
        dataType:'json',
        data:{"dictryId":dictryId,"blngtoDictryId":blngtoDictryId,"flag":flag},
        success:function (data) {
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')');
            if (data.success){
                result = true
            }
        },
        error:function () {
            $.messager.alert("警告","请求失败");
        }
    })
    return result;
}

/**
 * 描述：清除缓存
 */
function clearCache(){
    $.ajax({
        url:basePath + 'dictionary/clearCache'
    })
}

