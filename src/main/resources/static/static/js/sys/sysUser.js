//--用户组js--pengjunyao
//----------------用户组主页页面js-----------------
/**
 * 初始化用户展示页面
 * @param url
 */
function initSysUserListPage() {
    initComboboxByUser('stus','SYS05','',false,'100','20');
    initTextboxByUser('userId_User','',6,false,'100','20');
    initTextboxByUser('userNm','',60,false,'100','20');
    initTextboxByUser('blngtoOrgNo','',50,false,'100','20');

    initSysUserDataGrid('dg_sysUser');
    initUserToGroupTab('dg_sysGroup');
    initUserToRoleTab('dg_sysRole');
}

/**
 *描述：初始化用户数据列表
 */
function initSysUserDataGrid(id){
    $('#'+id).datagrid({
        url: basePath + 'sysUser/getByPage',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_tool',
        frozenColumns:[[
            {field:'cd',title:'',checkbox:true},
            {field:'userId',title:'用户编号',width:100,align:'center',formatter: function(value,row,index){
                return '<a href="javascript:void(0)" onclick="toSysUserDetail(\''+row.userId+'\');">'+value+'</a>';
            }},
            {field:'userNm',title:'用户姓名',width:100,align:'center'}
        ]],
        columns:[[
            {field:'blngtoOrgNo',title:'所属机构',width:150,align:'center'},
            {field:'stus',title:'状态',width:50,align:'center'},
            {field:'telNo',title:'联系电话',width:150,align:'center'},
            {field:'recntLgnTm',title:'上次登录时间',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            //去除表头的复选框（表头复选框有全选的作用）
            $(".datagrid-header-check").html("");
            //更改的是datagrid中的数据字体大小
            $('.datagrid-cell').css('font-size', '12px');
            //datagrid中的列名称
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');
        },
        onSelect:function (rowIndex, rowData) {
            //点击任意一行，回显已分配该用户组的用户
            var userId = rowData.userId;
            $('#dg_sysGroup').datagrid('load',{
                userId:userId
            });
            $('#dg_sysRole').datagrid('load',{
                userId:userId
            });
        }
    });
}

/**
 * 描述：初始化已分配用户组标签页
 */
function initUserToGroupTab(id) {
    initTextboxByUser('groupNm','',6,false,'100','20');
    initTextboxByUser('blngtoOrgNo_group','',6,false,'100','20');

    $('#'+id).datagrid({
        url: basePath + 'sysGroup/getShowGroupUser',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sysGroup_tool',
        frozenColumns:[[
            {field:'cd',title:'',checkbox:true},
            {field:'groupId',title:'用户组ID',width:100,align:'center'},
            {field:'groupNm',title:'用户组名称',width:100,align:'center'}
        ]],
        columns:[[
            {field:'blngtoOrgNo',title:'所属机构',width:150,align:'center'},
            {field:'groupComnt',title:'用户组说明',width:200,align:'center'}
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
 * 描述：初始化用户角色标签页
 */
function initUserToRoleTab(id){
    initTextboxByUser('roleNm','',40,false,'100','20');
    initComboboxByUser('roleHrchCd','SYS06','',false,'100','20');
    $('#'+id).datagrid({
        url: basePath + 'sysRole/getShowRoleUser',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_sysRole_tool',
        frozenColumns:[[
            {field:'cd',title:'',checkbox:true},
            {field:'roleId',title:'角色ID',width:100,align:'center'},
            {field:'roleNm',title:'角色名称',width:100,align:'center'}
        ]],
        columns:[[
            {field:'roleHrchCd',title:'角色层级',width:100,align:'center'},
            {field:'sys',title:'所属系统',width:100,align:'center'},
            {field:'roleComnt',title:'角色说明',width:200,align:'center'}
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
 * 描述：查看用户详情
 * @param userId
 */
function toSysUserDetail(userId){
    var url = basePath + 'sysUser/getById?userId=' + userId;
    openWindowSelf('win_sys_user','用户详情','600','350',url);
}

/**
 * 描述：跳转用户新增窗口
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toInsertSysUser(winId,title,url,width,height) {
    var url = basePath + url;
    openWindowSelf(winId,title,width,height,url);
}

/**
 * 描述：跳转用户更新窗口
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toUpdSysUser(winId,title,url,width,height) {
    var row = $('#dg_sysUser').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        var url = basePath + url + "?userId=" + row.userId;
        openWindowSelf(winId,title,width,height,url);
    }
}

/**
 * 描述：初始化用户新增页面
 */
function initSysUserInsertPage(){

    initTextboxByUser('userId_insert','',6,false,'200','20');
    initTextboxByUser('userNm_insert','',60,false,'200','20');
    initTextboxByUser('crtfNo_insert','',20,false,'200','20');
    initTextboxByUser('telNo_insert','',20,false,'200','20');
    initTextboxByUser('addr_insert','',200,false,'200','20');
    initTextboxByUser('blngtoOrgNo_insert','',0,false,'200','20');
    initComboboxByUser('genderCd_insert','CD0006','',false,'200','20');
    initComboboxByUser('stus_insert','SYS05','SYS0501',false,'200','20');
    initDateBoxByUser('brthdy_insert','','200','20');
    initPasswordBoxByUser('pwd_insert','','200','20');
    initMainUserComponent('userId_insert','win_sys_user_insert_emp');
    //-初始化机构选择组件
    initSysOrgInfoComponent('blngtoOrgNo_insert', 'blngtoOrgNo_hid', 'win_sys_user_insert_org', '0', '', 'true', '0', '0', '', '600', '400');
}

/**
 * 描述：中心员工选择组件
 */
function initMainUserComponent(id,winId){
    $('#'+id).searchbox({
        width:'200',
        height:'20',
        searcher : function(value){
            if(null == value || '' == value){
                openWindow(winId, "员工池信息列表", basePath + 'jsp/sys/SysOemEmply.jsp');
            }else{
                var ajaxObj = {
                    url : basePath + 'sysUser/getOemEmplyById?userId=' + value
                }
                customAjaxSubmit(ajaxObj, function(data){
                    if(data.success){
                        $('#detail_form').form('load',data.data);
                    }else{
                        $.messager.alert('错误提示', data.message, 'error');
                    }
                },function(){})
            }
        }
    });
}



/**
 * 描述：用户OA号唯一性校验
 * @param userId
 */
function uniqueUserId(userId){
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'sysUser/uniqueUserId',
        dataType:'JSON',
        type:'POST',
        data:{'userId':userId},
        success:function (data) {
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')');
            if (data.success){
                result = false
            }else{
                result = true;
            }
        }
    })
    return result;
}

/**
 * 描述：用户删除
 * */
function toDeleteUser(){
    var row = $('#dg_sysUser').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要删除的用户信息！");
    }else {
        $.messager.confirm("提示","确定删除该用户吗？",function(data) {
            if(data){
                ajaxSubmit("post", basePath + "sysUser/delete?userId=" + row.userId, "", "json");
            }
        })
    }
}

/**
 * 描述：用户注销
 */
function toCancel(){
    var row = $('#dg_sysUser').datagrid('getSelected');
    var url = basePath + 'sysUser/changeStus';
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        if(row.stus == '注销' || row.stus=="SYS0503"){
            $.messager.alert("提示","只能注销非注销状态下的用户！");
        }else{
            $.messager.confirm("提示","确定注销该用户吗？",function(data){
                if(data){
                    ajaxSubmit("post",url,"userId="+row.userId,"json");
                }
            })
        }
    }
}

/**
 * 描述：用户恢复
 */
function toRecover(){
    var row = $('#dg_sysUser').datagrid('getSelected');
    var url = basePath + 'sysUser/changeStus';
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        if(row.stus == "正常" || row.stus == 'SYS0501'){
            $.messager.alert("提示","只能恢复非正常状态下的用户！");
        }else{
            $.messager.confirm("提示","确定恢复该用户吗？",function(data){
                if(data){
                    ajaxSubmit("post",url,"userId="+row.userId,"json");
                }
            })
        }
    }
}

/**
 * 描述：密码重置
 */
function resetPwd(){
    var row = $('#dg_sysUser').datagrid('getSelected');
    var url = basePath + 'sysUser/resetPwd';
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        $.messager.confirm("操作提示","确定重置该用户的密码吗？",function(data){
            if(data){
                ajaxSubmit("post",url,"userId="+row.userId,"json");
            }
        })
    }
}

/**
 * 描述：角色分配
 */
function distributeRole(){
    var row = $('#dg_sysUser').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        var url = basePath + 'jsp/sys/SysUserToRoleGroup.jsp?userId='+row.userId+'&flag=role';
        openWindowSelf('win_sys_user','角色分配','600','500',url)
    }
}

/**
 * 描述：用户组分配
 */
function distributeGroup(){
    var row = $('#dg_sysUser').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else {
        var url = basePath + 'jsp/sys/SysUserToRoleGroup.jsp?userId='+row.userId+'&flag=group';
        openWindowSelf('win_sys_user','用户组分配','600','500',url)
    }
}

/**
 * 描述：提交操作
 * @param type
 * @param url
 * @param data
 * @param datatype
 */
function ajaxSubmit(type, url, data, datatype) {
    $.ajax({
        type : type,
        url : url,
        data : data,
        dataType : datatype,
        success : function(data){
            if(data.success){
                refreshDataGridBySelf('dg_sysUser');
                $.messager.alert('提示信息',data.message,'info');
            }else{
                $.messager.alert("警告",data.message,'error');
            }
        },
        error : function(){
            $.messager.alert("警告","ajax提交报错",'error');
        }
    });
}

/**
 * 描述：查询已分配用户组
 */
function doSearchUserSysGroup(){
     var row = $('#dg_sysUser').datagrid('getSelected');
     var userId = row.userId;
     if(row == null){
         $.messager.alert("提示","请选择需要操作的用户！");
     }else{
         $('#dg_sysGroup').datagrid('load',{
             userId:userId,
             groupNm:$('#groupNm').val(),
             blngtoOrgNo:$('#blngtoOrgNo_group').val(),
         });
     }
}

/**
 * 描述：重置用户组查询条件
 */
function clearSearchUserSysGroup(){
    $('#groupNm').textbox('reset');
    $('#blngtoOrgNo_group').textbox('reset');
}

/**
 * 描述：查看用户组资源
 * @constructor
 */
function SearchSysGroupResource(){
    var row = $('#dg_sysGroup').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else{
        var url = basePath + 'sysGroup/tolistRecoursByGroupId?groupId=' + row.groupId;
        openWindowSelf('win_sys_user','用户组资源查看','600','430',url);
    }
}

/**
 * 描述：查询已分配角色
 */
function doSearchUserSysRole(){
    var row = $('#dg_sysUser').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要操作的用户！");
    }else{
        var userId = row.userId;
        $('#dg_sysRole').datagrid('load',{
            userId:userId,
            roleNm:$('#roleNm').val(),
            roleHrchCd:$('#roleHrchCd').val(),
        });
    }
}

/**
 * 描述：重置角色查询条件
 */
function clearSearchUserSysRole(){
    $('#roleNm').textbox('reset');
    $('#roleHrchCd').textbox('reset');
}

/**
 * 描述：查询用户
 */
function doSearchSysUser(){
        $('#dg_sysUser').datagrid('load',{
            userId: $('#userId_User').val(),
            userNm: $('#userNm').val(),
            blngtoOrgNo: $('#blngtoOrgNo').val(),
            stus: $('#stus').val()
        });
}

/**
 * 描述：重置用户查询条件
 */
function clearSearchSysUser(){
    $('#userId_User').textbox('reset');
    $('#userNm').textbox('reset');
    $('#blngtoOrgNo').textbox('reset');
    $('#stus').textbox('reset');
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
function initTextboxByUser(id,echoValue,maxlength,disabled,width,height) {
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
function initComboboxByUser(id,dicType,echoValue,disabled,width,height){
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
function initDateBoxByUser(id,echoValue,width,height){
    $('#'+id).datebox({
        editable:false,
        required:true,
        width:width,
        height:height
    });
    $('#'+id).datebox('setValue',echoValue);

}

/**
 * 描述：初始化密码框
 * @param id
 * @param echoValue
 * @param width
 * @param height
 */
function initPasswordBoxByUser(id,echoValue,width,height){
    $('#'+id).passwordbox({
        prompt: 'Password',
        showEye: true,
        checkInterval:"100",
        value:echoValue,
        width:width,
        height:height
    });
}

//关闭窗口
function closeSysUserWindow(winId,refreshI) {
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        //刷新列表数据
        refreshDataGridBySelf(refreshI);
    }
    $("#"+winId).window('close');
}

/**
 * 描述：重置表单
 * @param formId
 */
function resetTargetForm(formId){
    $('#'+formId).form('reset');
}

/**
 * 描述：自定义新增校验规则
 * @author pengjuntao
 */
$.extend($.fn.validatebox.defaults.rules,{
    uniqueUserId:{  //OA编号唯一性校验
        validator:function (value,param) {
            return uniqueUserId(value);
        },
        message:'用户编号已存在，请重新选择'
    }
})



