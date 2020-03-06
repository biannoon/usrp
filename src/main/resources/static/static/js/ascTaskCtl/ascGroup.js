//任务组js---pengjuntao
/**
 * 描述：初始化任务组列表页面
 */
function initAscGroupMainPage() {
    initAscGroupTreeDataGrid('tree_group','dg_group');
    initUnderAscTaskDataGrid('dg_group')
}

/**
 * 描述：初始化任务组树状列表
 * @param treeId
 * @param dgId
 */
function initAscGroupTreeDataGrid(treeId,dgId) {
    $('#'+treeId).treegrid({
        url:basePath + 'AscTask/ListAllTaskGroupByTreeGrid',
        fitColumns:false,
        idField:'groupId',
        treeField:'groupName',
        frozenColumns:[[
            {field:'groupName',title:'任务组名称',width:200},
            {field:'groupId',title:'任务组ID',width:100,align:'center'}
        ]],
        columns:[[
            {field:'ascGroupState',title:'任务组状态',width:100,align:'center'},
            {field:'nextDate',title:'待处理日期',width:100,align:'center'},
            {field:'parentGroup',title:'上级任务组编号',width:100,align:'center'},
            {field:'groupSeq',title:'任务组序列',width:150,align:'center'}
        ]],
        onClickRow: function (row) {//点击事件
            var id = row.groupId;
            $('#'+dgId).datagrid('load',{
                groupId:id
            });
            getDetailByGroupId(id);
        }
    });
}

/**
 * 描述：初始化任务组下的任务列表
 */
function initUnderAscTaskDataGrid(dgId) {
    $('#'+dgId).datagrid({
        url:basePath + 'AscTask/listAscTaskFromGroup',
        fitColumns:false,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar: '#dg-tools',
        frozenColumns:[[
            {field:'sb',title:'',width:50,checkbox:true},
            {field:'taskId',title:'任务编号',width:80,align:'center'}
        ]],
        columns:[[
            {field:'taskName',title:'任务名称',width:80,align:'center'},
            {field:'taskType',title:'任务类型',width:80,align:'center'},
            {field:'taskCategory',title:'任务种类',width:80,align:'center'},
            {field:'nextDate',title:'待处理日期',width:80,align:'center'},
            {field:'frequency',title:'执行频率',width:80,align:'center'},
            {field:'priority',title:'优先级',width:80,align:'center'},
            {field:'state',title:'任务状态',width:80,align:'center'},
            {field:'crtDate',title:'创建日期',width:80,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            //去除表头的复选框（表头复选框有全选的作用）
            $(".datagrid-header-check").html("");
            //更改的是datagrid中的数据字体大小
            $('.datagrid-cell').css('font-size', '12px');
            //datagrid中的列名称
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');
        }
    });

}


/**
 * 描述：跳转新增
 * @param winId
 * @param url
 */
function toInsertAscGroup(winId,url) {
    var node = $('#tree_group').treegrid('getSelected');
    var groupId;
    if (node != null){
        groupId = node.groupId;
        var title = '任务组新增';
        var width = '500';
        var height = '300';
        url = url + 'AscTask/toAscGroupInsert?groupId=' + groupId;
        openWindowSelf(winId, title, width, height, url);
    }else{
        $.messager.alert("提示","请选择上级任务组");
    }
}

/**
 * 描述：跳转修改
 * @param winId
 * @param url
 */
function toUpdateAscGroup(winId,url) {
    var node = $('#tree_group').treegrid('getSelected');
    if (node == null){
        $.messager.alert("提示","请选择需要修改的任务组");
    }else{
        var title = '任务组修改';
        var width = '500';
        var height = '300';
        var groupId = node.groupId;
        url = url + 'AscTask/toAscGroupUpdate?groupId=' + groupId;
        openWindowSelf(winId, title, width, height, url);
    }
}

/**
 * 描述：删除任务组
 * @param url
 */
function toDeleteAscGroup(url){
    var node = $('#tree_group').treegrid('getSelected');
    if (node == null || node == ''){
        $.messager.alert("提示","请选择需要删除的任务组");
    }else{
        var groupId = node.groupId;
        var rows = $('#dg_group').datagrid('getRows');
        if(hasChildrenGroup(groupId)){
            $.messager.alert("提示","所选任务组拥有子级任务组，不能删除");
        }else if(rows.length > 0){
            $.messager.alert("提示","所选任务组下已经定义了具体任务，不能删除");
        }else if(groupId == 'root'){
            $.messager.alert("提示","所选任务组为根节点任务组，不能删除");
        }else{
            $.ajax({
                url:url+'AscTask/deleteAscGroup',
                type:'post',
                dataType:'json',
                data:{groupId:groupId},
                success:function(data){
                    var data = JSON.stringify(data);
                    var data = eval('(' + data + ')');
                    if (data.success){
                        //刷新页面
                        refreshTreeGridBySelf('tree_group',url+'AscTask/ListAllTaskGroupByTreeGrid');
                        refreshDataGridBySelf('dg_group');
                        $.messager.alert("提示",data.message);
                    }else{
                        $.messager.alert("提示",data.message);
                    }
                },
                error:function(){
                    $.messager.alert("警告","请求失败");
                }
            })
        }
    }
}

/**
 * 判断是否有子节点
 * @param groudId
 * @returns {boolean}
 */
function hasChildrenGroup(groupId) {
    var result = false;
    $.ajax({
        async:false,
        url:'hasChildrenGroup',
        type:'post',
        dataType:'json',
        data:{'groupId':groupId},
        success:function (data) {
            if (data == '1'){
                result = true;
            }
        }
    })
    return result;
}

/**
 * 折叠树
 */
function collapseAscGroupTree(id) {
    $('#'+id).treegrid('collapseAll');
}

/**
 * 展开树
 */
function expandAscGroupTree(id) {
    $('#'+id).treegrid('expandAll');
}

/**
 * 初始化任务组新增页面
 * @param url 基本url
 * @param groupId 上级任务组ID
 */
function initAscGroupInsertPage(groupId){

    initTextboxByAscGroup('groupId','',40,false,'200','20');
    initTextboxByAscGroup('groupName','',40,false,'200','20');
    initDateBoxByAscGroup('nextDate','','200','20');
    initComboboxByAscGroup('state','ASC001','ASC001002',true,'200','20');
    initAscGroupComponent('parentGroup','win_ascGroup_insert','parentGroup_hidden');

    if (groupId != null && groupId != ''){
        initTextboxByAscGroup('parentGroup',exchangeCodeToNameFromAscGroup(groupId),40,false,'200','20');
        $('#parentGroup_hidden').textbox({
            value:groupId
        })
    }else{
        initTextboxByAscGroup('parentGroup','',40,false,'200','20');
    }
}


/**
 * 描述：初始化任务组选择组件
 * @param id            文本框ID
 * @param winId         弹出窗口ID
 * @param hid_field     隐藏文本框ID
 */
function initAscGroupComponent(id,winId,hid_field){
    $('#'+id).textbox({
        onClickButton:function (index) {
            getAscGroupTree(winId,
                id+'$'+hid_field,
                basePath + 'common/toAscGroupList',
                '500',
                '350');
        }
    })
}


/**
 * 描述：通过任务组ID，获取任务组详情
 * @param groupId
 */
function getDetailByGroupId(groupId){
    $.ajax({
        url:basePath + 'AscTask/getAscGroupByGroupId',
        type:'post',
        dataType:'json',
        data:{'groupId':groupId},
        success:function (data) {
            var result = data.data;
            if (data.success){
                if (result.groupId != '' && result.groupId != null){
                    $('#groupId_detail').html(result.groupId);
                } else {
                    $('#groupId_detail').html('');
                }
                if (result.groupName != '' && result.groupName != null){
                    $('#groupName_detail').html(result.groupName);
                } else {
                    $('#groupName_detail').html('');
                }
                if (result.parentGroup !='' && result.parentGroup != null && result.parentGroup != '-1'){
                    var parentGroup = exchangeCodeToNameFromAscGroup(basePath,result.parentGroup);
                    $('#parentGroup_detail').html(parentGroup);
                }else {
                    $('#parentGroup_detail').html('');
                }
                if (result.nextDate != '' && result.nextDate != null) {
                    $('#nextDate_detail').html(result.nextDate);
                }else {
                    $('#nextDate_detail').html('');
                }
                if (result.state != '' && result.state != null){
                    var state = exchangeAscDictry(basePath,result.state,'ASC001');
                    $('#state_detail').html(state);
                }else {
                    $('#state_detail').html('');
                }
            }
        }
    })
}

/**
 * 描述：码值中文转换
 */
function exchangeAscDictry(url,dic,type){
    var result = '';
    $.ajax({
        async:false,
        url:url + 'AscTask/exchangeDic',
        type:'post',
        dataType:'json',
        data:{'dic':dic,'type':type},
        success:function (data) {
            var d = data.data;
            result = d.dictryNm;
        }
    })
    return result;
}

//自定义校验规则
$.extend($.fn.validatebox.defaults.rules,{
    uniqueGroupId:{
        validator:function (value,param) {
            return checkGroupId(value);
        },
        message:'任务组编号已存在，请重新输入'
    }
})

//验证任务组ID的唯一性
function checkGroupId(groupId){
    var result = false;
    $.ajax({
        async:false,
        url:'checkGroupId',
        type:'post',
        dataType:'json',
        data:{"groupId":groupId},
        success:function (data) {
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')');
            if (data.success){
                result = true
            }else{
                result = false;
            }
        },
        error:function () {
            $.messager.alert("警告","请求失败");
        }
    })
    return result;
}

//关闭窗口
function closeWindowBySelf(winId,url) {
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        //刷新页面
        refreshTreeGridBySelf('tree_group',url);
        refreshDataGridBySelf('dg_group');
    }
    $("#"+winId).window('close');
}

//表单异步提交（非easyui表单提交）
function submitForm(formId,baseUrl,url) {
    //-提交校验
    if (!$("#"+formId).form('validate')){
        return false;
    }
    //取消禁用
    if (formId == 'form_group_insert'){
        $('#state').combobox({
            disabled:false
        })
    } else if(formId == 'form_group_update'){
        $('#state').combobox({
            disabled:false
        })
        $('#groupId').textbox({
            disabled:false
        })
        $('#parentGroup').textbox({
            disabled:false
        })
    }
    $.ajax({
        url:baseUrl + url,
        type:'post',
        dataType:'text',
        data:$('#'+formId).serialize(),
        success:function (data) {
            var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
            var d = data.data;
            if (data.success){
                //新增成功后，回显数据
                initTextboxSelf('groupId',d.groupId,true);
                initTextboxSelf('groupName',d.groupName,true);
                initTextboxSelf('parentGroup',d.parentGroup,true);
                initTextboxSelf('nextDate',d.nextDate,true);
                assignComboboxSelf('state',d.state,true);
                //新增成功后，禁用新增/重置按钮
                $('#but_insert').linkbutton('disable');
                $('#but_reset').linkbutton('disable');
                //标记关闭按钮
                document.getElementById('close_flag').value = 'true';
                $.messager.alert("提示",data.message);
            }else{
                $.messager.alert("提示",data.message);
            }
        }
    })
}

/**
 * 描述：将任务组的id
 * @param groupId转化为groupName
 */
function exchangeCodeToNameFromAscGroup(groupId){
    var result = "";
    $.ajax({
        async:false,
        url:basePath + 'AscTask/getAscGroupByGroupId',
        type:'post',
        dataType:'json',
        data:{"groupId":groupId},
        success:function (data) {
            var re = data.data;
            result =re.groupName;
        },
        error:function () {
            $.messager.alert("警告","请求失败");
        }
    })
    return result;
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
function initTextboxByAscGroup(id,echoValue,maxlength,disabled,width,height) {
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
function initComboboxByAscGroup(id,dicType,echoValue,disabled,width,height){
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
function initDateBoxByAscGroup(id,echoValue,width,height){
    $('#'+id).datebox({
        editable:false,
        required:true,
        width:width,
        height:height
    });
    $('#'+id).datebox('setValue',echoValue);

}
