/**
 * 描述：初始化系统角色页面
 */
function initSysRolePage(){
    initTextboxByRole('role_nm_se','',60,false,'150','20');
    initComboboxByRole('roleHrchCd_lev_se','SYS06','',false,'150','20');
    initSysRole_DG('dg_sys_role');
    initTextboxByRole('userNm_se','',60,false,'150','20');
    initTextboxByRole('blngtoOrgNo_se','',100,false,'150','20');
    initSysUser_DG('dg_sys_user');
}

/**
 * 描述：初始化系统角色列表数据
 * @param id
 */
function initSysRole_DG(id){
    $('#'+id).datagrid({
        url: basePath + 'sysRole/getByPage',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sys_role_tool',
        frozenColumns:[[
            {field:'ck',title:'',width:100,checkbox:true},
            {field:'roleId',title:'角色ID',width:100,align:'center'},
            {field:'roleNm',title:'角色名称',width:150,align:'center'}
        ]],
        columns:[[
            {field:'roleHrchCd',title:'角色层级',width:100,align:'center'},
            {field:'sys',title:'所属系统',width:100,align:'center'},
            {field:'roleComnt',title:'角色说明',width:300,align:'center'},
            {field:'crtDt',title:'创建日期',width:150,align:'center'},
            {field:'creatr',title:'创建人',width:100,align:'center'},
            {field:'finlModfyDt',title:'最后修改日期',width:150,align:'center'},
            {field:'finlModifr',title:'最后修改人',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        },
        onSelect:function (rowIndex, rowData) {
            var roleId = rowData.roleId;
            $('#dg_sys_user').datagrid('load', {
                roleId: roleId
            });
        }
    });
}

/**
 * 描述：初始化用户列表数据
 */
function initSysUser_DG(id){
    $('#'+id).datagrid({
        url: basePath + 'sysRole/getShowRoleUser',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sys_user_tool',
        frozenColumns:[[
            {field:'ck',title:'',width:100,checkbox:true},
            {field:'userId',title:'用户编号',width:100,align:'center'},
            {field:'userNm',title:'用户名称',width:150,align:'center'}
        ]],
        columns:[[
            {field:'blngtoOrgNo',title:'所属机构',width:200,align:'center'},
            {field:'stus',title:'用户状态',width:100,align:'center'},
            {field:'telNo',title:'联系电话',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    });
}

/**
 * 描述：系统角色查询
 */
function searchSysRole(){
    $('#dg_sys_role').datagrid('load',{
        roleNm:$('#role_nm_se').val(),
        roleHrchCd:$('#roleHrchCd_lev_se').val()
    })
}

/**
 * 描述：重置查询条件
 */
function resetSearchSysRole(){
    $('#role_nm_se').textbox('reset');
    $('#roleHrchCd_lev_se').combobox('reset');
}

/**
 * 描述：系统用户查询
 */
function searchSysUser(){
    var row = $('#dg_sys_role').datagrid('getSelected');
    var roleId = row.roleId;
    $('#dg_sys_user').datagrid('load',{
        roleId:roleId,
        userNm:$('#userNm_se').val(),
        blngtoOrgNo:$('#blngtoOrgNo_se').val()
    })
}

/**
 * 描述：重置查询条件
 */
function resetSearchSysUser(){
    $('#userNm_se').textbox('reset');
    $('#blngtoOrgNo_se').textbox('reset');
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
        var row = $('#dg_sys_role').datagrid('getSelected');
        if (row == null || row == ''){
            $.messager.alert("提示","请选择角色");
        }else{
            url = basePath + url + '?type=' + type + '&id=' + row.roleId;
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
function submitFormBySysRole(formId, url) {
    //验证框校验
    if (!$("#" + formId).form('validate')){
        return false;
    }
    $('#roleId_insert').textbox('enable');
    var sysId = $('#sys_insert_hid').val();
    var roleId = $('#roleId_insert').val();
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
                //-刷新角色列表
                refreshDataGridBySelf("dg_sys_role");
                //-禁用提交按钮
                $('#btn_add').linkbutton('disable');
                //-打开功能权限分配窗口
                var url = basePath + 'jsp/sys/SysRole_distribution.jsp?sysId='+sysId + '&roleId='+ roleId + '&winId=win_sys_role_detail';
                openWindowSelfByRole('win_sys_role_detail','功能权限分配','400','400',url);
                //$.messager.alert("提示",data.message);
            }else{
                $.messager.alert("提示",data.message);
            }
        }
    })
}

/**
 * 描述：删除角色
 */
function toDeleteSysRole(url){
    url = basePath + url;
    var ids = "";
    var selRows = $('#dg_sys_role').datagrid('getChecked');
    if (selRows == null || selRows == ''){
        $.messager.alert("提示","请选择角色");
    }else{
        $.each(selRows,function (index,row) {
            if (index == selRows.length){
                ids = ids + row.roleId;
            } else {
                ids = ids + row.roleId + ",";
            }
        })
        if (!isExistUserUnderRole(ids)){
            $.messager.alert("提示","已经被分配用户的角色不允许删除");
        }else{
            $.messager.confirm('提示','您确定要删除选中的角色吗?',function(r){
                if (r){
                    $.ajax({
                        url:url,
                        type:'POST',
                        dataType:'JSON',
                        data:{'ids':ids},
                        success:function (data) {
                            if (data.success){
                                refreshDataGridBySelf('dg_sys_role');
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

/**
 * 描述：配置角色功能权限
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toSysRoleFuncConf() {
    var row = $('#dg_sys_role').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择角色！");
    }else{
        var roleId = row.roleId;
        var url = basePath + 'jsp/sys/SysRole_distribution.jsp?roleId=' + roleId + '&winId=win_sys_role';
        openWindowSelfByRole('win_sys_role','功能权限分配','400','400',url);
    }
}


$.extend($.fn.validatebox.defaults.rules,{
    uniqueRoleId:{  //功能ID唯一性校验
        validator:function (value,param) {
            return uniqueRoleId(value);
        },
        message:'角色ID已存在，请重新输入'
    }
})

function uniqueRoleId(id) {
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'sysRole/checkRoleId',
        type:'POST',
        dataType:'JSON',
        data:{'roleId':id},
        success:function (data) {
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')');
            if (data.success){
                result = true
            }else{
                result = false;
            }
        }
    })
    return result;
}

/**
 * 描述：判断该角色是否已分配给用户
 * @param roleId
 */
function isExistUserUnderRole(roleId){
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'sysRole/isExistUserUnderRole',
        type:'POST',
        dataType:'JSON',
        data:{'roleId':roleId},
        success:function (data) {
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')');
            if (data.success){
                result = true
            }else{
                result = false;
            }
        }
    })
    return result;
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
function initTextboxByRole(id,echoValue,maxlength,disabled,width,height) {
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
function initComboboxByRole(id,dicType,echoValue,disabled,width,height){
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
 * 描述：初始化子系统选择组件
 * @param id
 */
function initSubsystemComponent(winId,id,id_hid){
    $('#'+id).textbox({
        onClickButton:function (index) {
            var url = basePath + "jsp/sys/SysRole_subsystem_component.jsp";
            url = url + '?winId=' + winId + '&id=' + id + '&id_hid=' + id_hid;
            openWindowSelf(winId,"子系统列表",'500','500',url);
        }
    })
}

/**
 * 描述：子系统id 和 子系统名称转换
 * @param subsystemId
 */
function exchangeSubsystemNm(subsystemId){
    var result = "";
    $.ajax({
        async:false,
        url:basePath + 'sysRole/exchangeSubsystemNm',
        dataType:'json',
        type:'post',
        data:{'subsystemId':subsystemId},
        success:function (data) {
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')');
            if (data.success){
                result = data.data.subsystemNm;
            }
        },
        error:function () {
            $.messager.alert("提示","请求失败");
        }
    })
    return result;
}


//在div中以HTML片段形式打开自定义大小弹出窗口
function openWindowSelfByRole(winId, tit, width, height, url){
    $("#"+winId).window({
        title: tit,
        href: url,
        width: width,
        height: height,
        left: '100px',
        closed: true,
        modal: false,
        cache: false,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        shadow: false
    });
    $("#"+winId).window('open');
    $('#'+winId).window('center');
}