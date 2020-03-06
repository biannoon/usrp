//系统表单配置js--pengjuntao

var tempTablId;//临时变量

function initSysTabConfigDfPage(url) {
    $('#dg_table_dif').datagrid({
        url:url+'SysTabConfig/listDifTablDf',
        fitColumns:false,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#dg_search',
        frozenColumns:[[
            {field:'id',title:'',width:100,checkbox:true},
            {field:'tablId',title:'',width:150,align:'center',hidden:true},
            {field:'cnNm',title:'数据接口中文名',width:150,align:'center'},
            {field:'enNm',title:'数据接口英文名',width:150,align:'center'}
        ]],
        columns:[[
            {field:'dataSorcId',title:'所属数据源',width:100,align:'center'},
            {field:'tablTyp',title:'数据接口分类',width:100,align:'center'},
            {field:'bizClsf',title:'数据接口所属业务分类',width:100,align:'center'},
            {field:'tablStus',title:'数据接口状态',width:100,align:'center'},
            {field:'creatr',title:'创建人',width:100,align:'center'},
            {field:'crtDt',title:'创建时间',width:100,align:'center'},
            {field:'finlModifr',title:'最后修改人',width:100,align:'center'},
            {field:'finlModfyDt',title:'最后修改时间',width:100,align:'center'},
        ]],
        onLoadSuccess: function (data) {
            //去除表头的复选框（表头复选框有全选的作用）
            $(".datagrid-header-check").html("");
            //更改的是datagrid中的数据字体大小
            $('.datagrid-cell').css('font-size', '12px');
            //datagrid中的列名称
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');

            initSysTabConfigPage(url);
        },
        onSelect:function (rowIndex, rowData) {
            var id = rowData.tablId;
            $('#tablId_hidden').textbox('setValue',id);
            //接口，初始化该接口表单配置列表
            $('#dg_sysTabConfig').datagrid('load',{
                tablId:id
            });
        }
    });
    //初始化组合框
    initComboboxSelf('tablStus_se','DFI02',url);
    initComboboxSelf('bizClsf_se','DFI03',url);

    //限制文本框输入长度
    $('#cnNm_se').textbox('textbox').attr('maxlength',100);
    $('#enNm_se').textbox('textbox').attr('maxlength',50);
    $('#dataSorcId_se').textbox('textbox').attr('maxlength',32);
}

/**
 * 描述：主页面条件数据接口查询
 */
function doTableDfSearch() {
    $('#dg_table_dif').datagrid('load',{
        cnNm: $('#cnNm_se').val(),
        enNm: $('#enNm_se').val(),
        dataSorcId: $('#dataSorcId_se').val(),
        tablStus: $('#tablStus_se').val(),
        bizClsf: $('#bizClsf_se').val()
    });
}

/**
 * 描述：重置接口查询条件
 */
function resetTableDfSearch(){
    $('#cnNm_se').textbox("reset");
    $('#enNm_se').textbox("reset");
    $('#dataSorcId_se').textbox("reset");
    $('#tablStus_se').combobox("clear");
    $('#bizClsf_se').combobox("clear");
}

/**
 * 跳转系统菜单配置标签页
 * @param title 标签页名称
 * @param url 标签页url
 */
function toSysTabConfigList(title,url,funcId){
    var row = $('#dg_table_dif').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择数据接口");
    }else{
        url = url + '?tableId=' + row.tablId + '&enNm=' + row.enNm + '&funcId=' + funcId;
        newTab(title,url);
    }
}

//---------------------------系统表单配置列表页面
/**
 * 描述：初始化系统表单配置主页：接口列表
 * @param url
 */
function initSysTabConfigPage(url) {
    $('#dg_sysTabConfig').datagrid({
        url:url + 'SysTabConfig/listSysTabConfig',
        fitColumns:false,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#dg_config_search',
        frozenColumns:[[
            {field:'ck',title:'',width:100,checkbox:true},
            {field:'tabNm',title:'表名',width:100,align:'center'},
            {field:'lNm',title:'标签名称',width:100,align:'center'},
            {field:'fNm',title:'字段名称',width:100,align:'center'}
        ]],
        columns:[[
            {field:'fTyp',title:'字段类型',width:100,align:'center'},
            {field:'dictryId',title:'字典代码',width:100,align:'center'},
            {field:'readOnly',title:'是否只读',width:100,align:'center'},
            {field:'mustInput',title:'是否必输',width:100,align:'center'},
            {field:'listShowFlg',title:'是否列表展示',width:100,align:'center'},
            {field:'detailShowFlg',title:'是否详情展示',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");
            //更改的是datagrid中的数据字体大小
            $('.datagrid-cell').css('font-size', '12px');
            //datagrid中的列名称
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');
        }
    });
    initComboboxSelf('fTyp_se','SYS14',url);

    //限制文本框输入长度
    $('#tabNm_se').textbox('textbox').attr('maxlength',50);
    $('#lNm_se').textbox('textbox').attr('maxlength',50);
    $('#fNm_se').textbox('textbox').attr('maxlength',50);
}

/*function dataFormat(value,row,index){
    var result = '';
    $.ajax({
        async:false,
        url: url+'SysTabConfig/getSysDictryCdByDicId',
        type: 'post',
        dataType: 'json',
        data: {'dicId':value},
        success: function (data) {
            alert("返回数据=="+data);
            result = data;
        }
    })
    return result;
}*/
/**
 * 描述：系统菜单配置列表页面，配置条件查询
 */
function doConfigSearch() {
    var tablId = $('#tablId_hidden').val();
    $('#dg_sysTabConfig').datagrid('load',{
        tabNm: $('#tabNm_se').val(),
        lNm:$('#lNm_se').val(),
        fNm:$('#fNm_se').val(),
        fTyp:$('#fTyp_se').combobox('getValue'),
        tablId:tablId
    });
}
/**
 * 描述：表单配置查询条件重置
 */
function resetConfigSearch(){
    $('#tabNm_se').textbox("reset");
    $('#lNm_se').textbox("reset");
    $('#fNm_se').textbox("reset");
    $('#fTyp_se').combobox("reset");
}

/**
 * 描述：表单配置新增
 * @param winId 弹出窗口ID
 * @param title 弹出窗口标题
 * @param url 请求url
 * @param width 弹出窗口宽度
 * @param height 弹出窗口高度
 */
function toInsertTabConfigPage(winId,title,url,width,height) {
    var row = $('#dg_table_dif').datagrid('getSelected');
    if (row == null || row == ''){
        $.messager.alert("提示","请选择接口");
    }else{
        var tablId = row.tablId;
        var enNm = row.enNm;
        url = url + '?tablId='+tablId+'&enNm='+enNm;
        openWindowSelf(winId,title,width,height,url);
    }
}
/**
 * 描述：表单配置修改
 * @param winId 弹出窗口ID
 * @param title 弹出窗口标题
 * @param url 请求url
 * @param width 弹出窗口宽度
 * @param height 弹出窗口高度
 */
function toModifyTabConfigPage(winId,title,url,width,height) {
    var row = $('#dg_sysTabConfig').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要修改的记录");
    }else{
        var id = row.id;
        var tablId = $('#tablId_hidden').val();
        url = url + '?id='+ id + '&tablId=' + tablId;
        openWindowSelf(winId,title,width,height,url);
    }
}
/**
 * 描述：表单配置详情
 * @param winId 弹出窗口ID
 * @param title 弹出窗口标题
 * @param url 请求url
 * @param width 弹出窗口宽度
 * @param height 弹出窗口高度
 */
function toDetailTabConfigPage(winId,title,url,width,height){
    var row = $('#dg_sysTabConfig').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要查看的记录");
    }else{
        var id = row.id;
        url = url + '?id='+ id;
        openWindowSelf(winId,title,width,height,url);
    }
}
/**
 * 描述：表单配置批量删除
 * @param url 请求url
 */
function toDeleteTabConfig(url) {
    var rows = $('#dg_sysTabConfig').datagrid('getChecked');
    if (rows == '' || rows == null){
        $.messager.alert("提示","请选择需要删除的记录");
    }else{
        var idList = '';
        $.each(rows,function(index,row){
            var id = row.id;
            if (index == rows.length - 1){
                idList = idList + id;
            } else{
                idList = idList + id + "_";
            }
        })
        $.ajax({
            url:url,
            type:'post',
            dataType:'json',
            data:{"id":idList},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    refreshDataGridBySelf('dg_sysTabConfig');
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            },
            error:function () {
                $.messager.alert("警告","请求失败");
            }
        })
    }
}
//------------------表单配置新增页面---------------
/**
 * 描述：初始化表单配置新增页面
 * @param url 绝对路径
 * @param enNm 表名
 */
function initSysTabConfigInsertPage(tablId,enNm){

    initTextboxByTabConfig('tablId_insert',tablId,32,true,'200','20');
    initTextboxByTabConfig('tabNm_insert',enNm,50,true,'200','20');
    initTextboxByTabConfig('seqn_insert','',0,false,'200','20');
    initTextboxByTabConfig('lNm_insert','',50,false,'200','20');
    initComboboxByTabConfig('lAlign_insert','SYS13','',false,'200','20');
    initTextboxByTabConfig('lColspan_insert','',0,false,'200','20');
    initTextboxByTabConfig('lRowspan_insert','',0,false,'200','20');
    initTextboxByTabConfig('fNm_insert','',50,false,'200','20');
    initComboboxByTabConfig('fAlign_insert','SYS13','',false,'200','20');
    initTextboxByTabConfig('fColspan_insert','',0,false,'200','20');
    initTextboxByTabConfig('fRowspan_insert','',0,false,'200','20');
    initTextboxByTabConfig('fTyp_insert','',0,false,'200','20');
    initTextboxByTabConfig('dictryId_insert','',20,false,'200','20');
    initComboboxByTabConfig('readOnly_insert','SYS02','',false,'200','20');
    initComboboxByTabConfig('mustInput_insert','SYS02','',false,'200','20');
    initComboboxByTabConfig('listShowFlg_insert','SYS02','',false,'200','20');
    initComboboxByTabConfig('detailShowFlg_insert','SYS02','',false,'200','20');

    $('#fTyp_insert').textbox({
        onChange:function (newValue,oldValue) {
            if (newValue == '下拉框'){
                $("#dictryId_insert").validatebox('options').required = true;
                //$("#dictryId_insert").validatebox('validate');
            }
        }
    })

    $('#readOnly_insert').combobox({
        onSelect:function (record) {
            alert('val:'+record.dictryId);
            alert('id:'+record.dictryNm);
        }
    })

    //字典代码选择组件
    initSysDictryComponent('dictryId_insert', 'dictryId_hidden', 'root', 'win_sysTabConfig_insert01', '0', '', '0', '650', '500');
    initSysDictryComponent('fTyp_insert', 'fTyp_hidden', 'SYS14', 'win_sysTabConfig_insert01', '0', '', '0', '650', '500');
    //字段选择组件
    initFieldComponent('fNm_insert',tablId,'win_sysTabConfig_insert02');
    initFieldComponent('lNm_insert',tablId,'win_sysTabConfig_insert02');
    tempTablId = tablId;//临时变量赋值

    //当是否只读为‘是’时，是否必输不能选择
    /*$('#readOnly_insert').combobox({
        onSelect:function (record) {
            if (record.dictryId == 'SYS0201'){
                $('#mustInput_insert').combobox({
                    value:'SYS0202',
                    disabled:true
                })
            } else if (record.dictryId == 'SYS0202'){
                $('#mustInput_insert').combobox({
                    disabled:false
                })
            }
        }
    });

    $('#mustInput_insert').combobox({
        onSelect:function (record) {
            if (record.dictryId == 'SYS0201'){
                $('#readOnly_insert').combobox({
                    value:'SYS0202',
                    disabled:true
                })
            } else if (record.dictryId == 'SYS0202'){
                $('#readOnly_insert').combobox({
                    disabled:false
                })
            }
        }
    })*/
}

/**
 * 初始化字段选择组件
 * @param id        文本框字段
 * @param tablId    接口ID
 * @param winId     弹窗窗ID
 */
function initFieldComponent(id,tablId,winId){
    $('#'+id).textbox({
        onClickButton:function (index) {
            openWindowSelf(winId,
                "表字段选择",
                '500',
                '500',
                basePath + 'SysTabConfig/toFieldsList?tablId='+tablId+'&winId='+winId+'&fieldId=lNm_insert$fNm_insert');
        }
    });
}

/**
 * 描述：检查序号的唯一性
 * @param seqn 序号值
 * @param url 请求url
 */
function uniqueSeqn(seqn,url,tablId){
    var flag = false;
    $.ajax({
        async:false,
        url:url,
        type:'post',
        dataType:'json',
        data:{'seqn':seqn,'tablId':tablId},
        success:function(data){
            if (data == 0){
                flag = true;
            }
        },
        error:function () {
            $.messager.alert("警告","请求失败");
        }
    });
    return flag;
}

/**
 * 描述：检查字段的唯一性
 * @param field
 * @param url
 */
function uniqueField(field,url,tablId){
    var flag = false;
    $.ajax({
        async:false,
        url:url,
        type:'post',
        dataType:'json',
        data:{'field':field,'tablId':tablId},
        success:function(data){
            if (data == 0){
                flag = true;
            }
        },
        error:function () {
            $.messager.alert("警告","请求失败");
        }
    });
    return flag;
}

//关闭窗口
function closeTabConfigWindow(winId) {
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        //刷新列表数据
        refreshDataGridBySelf('dg_sysTabConfig');
    }
    $("#"+winId).window('close');
}

function initSysTabConfigUpdatePage(url,enNm,tablId) {
    //初始化组合框
    initComboboxSelf('lAlign_insert','SYS13',url);
    initComboboxSelf('fAlign_insert','SYS13',url);
    initComboboxSelf('fTyp_insert','SYS14',url);
    initComboboxSelf('readOnly_insert','SYS02',url);
    initComboboxSelf('mustInput_insert','SYS02',url);
    initComboboxSelf('listShowFlg_insert','SYS02',url);
    initComboboxSelf('detailShowFlg_insert','SYS02',url);

    initSysDictryComponent('dictryId_insert', 'dictryId_hidden', 'root', 'win_sysTabConfig_insert01', '0', '', '0', '650', '500');
    initSysDictryComponent('fTyp_insert', 'fTyp_hidden', 'SYS14', 'win_sysTabConfig_insert01', '0', '', '0', '650', '500');

    //初始化字段查询按钮
    $('#fNm_insert').textbox({
        onClickButton:function (index) {
            openWindowSelf('win_sysTabConfig_insert',
                "表字段选择",
                '650',
                '500',
                url+'SysTabConfig/toFieldsList?winId=win_sysTabConfig_insert&fieldId=lNm_insert$fNm_insert&tablId='+tablId);
        }
    });
    $('#lNm_insert').textbox({
        onClickButton:function (index) {
            openWindowSelf('win_sysTabConfig_insert',
                "表字段选择",
                '650',
                '500',
                url+'SysTabConfig/toFieldsList?winId=win_sysTabConfig_insert&fieldId=lNm_insert$fNm_insert&tablId='+tablId);
        }
    });

    //当是否只读为‘是’时，是否必输不能选择
    $('#readOnly_insert').combobox({
        onSelect:function (record) {
            var mustInput = $('#mustInput_insert').combobox('getValue');
            if (record.dictryId == 'SYS0201'){
                if (mustInput == '' || mustInput == 'SYS0201'){
                    $('#mustInput_insert').combobox({
                        value:'SYS0202',
                        disabled:true
                    })
                }else if(mustInput == 'SYS0202'){
                    $('#mustInput_insert').combobox({
                        value:mustInput,
                        disabled:true
                    })
                }
            }else{
                $('#mustInput_insert').combobox({
                    value:mustInput,
                    disabled:false
                })
            }
        }
    });
}


/**
 * 描述：初始化任务管理的文本框
 * @param id            文本框ID
 * @param echoValue     文本框回显值
 * @param maxlength     文本框最大输入长度
 * @param disabled      是否禁用
 * @param width
 * @param height
 */
function initTextboxByTabConfig(id,echoValue,maxlength,disabled,width,height) {
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
 * 描述：初始化任务管理的组合框
 * @param id        组合框ID
 * @param dicType   下拉框字典类型代码
 * @param echoValue 回显数据
 * @param disabled  是否禁用
 * @param width
 * @param height
 *
 */
function initComboboxByTabConfig(id,dicType,echoValue,disabled,width,height){
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
 * 描述：初始化任务管理的日期框
 * @param id
 * @param echoValue
 * @param width
 * @param height
 */
function initDateBoxByTabConfig(id,echoValue,width,height){
    $('#'+id).datebox({
        editable:false,
        required:true,
        width:width,
        height:height
    });
    $('#'+id).datebox('setValue',echoValue);

}

/**
 * 描述：自定义新增校验规则
 * @author pengjuntao
 */
$.extend($.fn.validatebox.defaults.rules,{
    uniqueSeqn:{  //序号唯一性校验
        validator:function (value,param) {
            return uniqueSeqn(value,basePath + 'SysTabConfig/uniqueSeqn',tempTablId);
        },
        message:'序号已存在，请重新输入'
    },
    uniqueField:{ //字段不可重复
        validator:function (value,param) {
            return uniqueField(value,basePath + 'SysTabConfig/uniqueField',tempTablId);
        },
        message:'字段已存在，请重新选择'
    },
    checkNum:{ //数字校验
        validator:function (value,param) {
            var reg = /^\d+?$/;     //非负整数
            if (!reg.test(value)){
                return false;
            }else if(value == '0'){
                return false;
            }else{
                return true;
            }
        },
        message:'请填写大于或者等于1的整数'
    }
    /*checkDictryId:{ //字典代码必输验证
        validator:function (value) {
            if (value == '下拉框'){

            return true;
        },
        message:'当字段类型为下拉框时，字典代码为必输项'
    }*/
})


