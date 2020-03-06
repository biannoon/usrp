//--用户组js--pengjunyao
//----------------用户组主页页面js-----------------
/**
 * 初始化用户组展示页面
 * @param url
 */
function initSysGroupListPage(url) {
    //初始化用户组数据列表
    $('#dg_sysGroup').datagrid({
        url: url + 'sysGroup/listSysGroup',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_tool',
        frozenColumns:[[
            {field:'ck_group',title:'',width:100,checkbox:true},
            {field:'groupId',title:'用户组ID',width:100,align:'center'},
            {field:'groupNm',title:'用户组名称',width:100,align:'center'}
        ]],
        columns:[[
            {field:'blngtoOrgNo',title:'所属机构',width:200,align:'center'},
            {field:'groupComnt',title:'用户组说明',width:250,align:'center'},
            {field:'finlModifr',title:'最后修改人',width:100,align:'center'},
            {field:'finlModfyDt',title:'最后修改日期',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称

            initSysUserByGropuPage(url);
            initGroupResourcesPage(url);
            initSysGroupDetailPage();
        },
        onSelect:function (rowIndex, rowData) {
            //点击任意一行，回显已分配该用户组的用户
            var groupId = rowData.groupId;
            //加载用户组详情
            toDetailSysGorup(url,groupId);
            //加载用户组资源
            $('#dg_group_resources').datagrid('load',{
                groupId:groupId
            });
            //加载已分配用户
            $('#dg_sysUser').datagrid('load',{
                groupId:groupId
            });
            //加载隐藏用户组ID
            $('#groupId_hidden').textbox('setValue',groupId);
        }
    });
    //限制查询条件的文本长度
    $('#groupNm_se').textbox('textbox').attr('maxlength', 20);
    $('#blngtoOrgNo_se').textbox('textbox').attr('maxlength', 100);
}

/**
 * 描述：初始化详情标签页
 */
function initSysGroupDetailPage(){
    initTextboxBySysGroup('groupId_detail','',10,false,'200','20');
    initTextboxBySysGroup('groupNm_detail','',20,false,'200','20');
    initTextboxBySysGroup('blngtoOrgNo_detail','',0,false,'200','20');
    initTextboxBySysGroup('groupComnt_detail','',200,false,'200','100');
    initComboboxBySysGroup('isGlobal_detail','SYS02',false,'200','20');
    initSysOrgInfoComponent('blngtoOrgNo_detail', 'blngtoOrgNo_hid', 'win_sysGroup', '0', '', 'false', '0', '0', '', '600', '400');
}

/**
 * 初始化已分配用户组的用户列表
 * @param url
 */
function initSysUserByGropuPage(url){
    $('#dg_sysUser').datagrid({
        url: url + 'sysGroup/listSysUserByGroupId',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        frozenColumns:[[
            {field:'ck_user',title:'',width:50,checkbox:true},
            {field:'userId',title:'用户编号',width:100,align:'center'},
            {field:'userNm',title:'用户名称',width:100,align:'center'},
        ]],
        columns:[[
            {field:'stus',title:'状态',width:100,align:'center'},
            {field:'blngtoOrgNo',title:'所属机构名称',width:100,align:'center'},
            {field:'telNo',title:'联系电话',width:100,align:'center'}
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
 * 初始化用户组对应的资源列表
 * @param url
 */
function initGroupResourcesPage(url) {
    $('#dg_group_resources').datagrid({
        url: url + 'sysGroup/listRecoursByGroupId',
        fitColumns: true,
        singleSelect: false,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar: '#dg_resources_tool',
        columns:[[
            {field:'ck_res',title:'',width:50,checkbox:true},
            {field:'groupId',title:'',width:50,hidden:true},
            {field:'recoursId',title:'资源ID',width:80,align:'center'},
            {field:'recoursTyp',title:'资源类型',width:50,align:'center'},
            {field:'recoursNm',title:'资源名称',width:170,align:'center'}

        ]],
        onLoadSuccess: function (data) {
            //去除表头的复选框（表头复选框有全选的作用）
            $(".datagrid-header-check").html("");
            //更改的是datagrid中的数据字体大小
            $('.datagrid-cell').css('font-size', '12px');
            //datagrid中的列名称
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');
        }
    })
    //加载资源类型下拉框
    initComboboxSelf('recoursTyp_se','SYS11',url);
    //限制查询条件文本长度
    $('#recoursNm_se').textbox('textbox').attr('maxlength', 100);
}
/**
 * 描述：查询条件
 */
function doSearchSysGroup(flag){
    if (flag == '1'){//用户组查询条件
        $('#dg_sysGroup').datagrid('load',{
            groupNm: $('#groupNm_se').val(),
            blngtoOrgNo: $('#blngtoOrgNo_se').val()
        });
    }else if(flag == '2'){//用户组资源查询条件
        var groupId = $('#groupId_hidden').val();
        $('#dg_group_resources').datagrid('load',{
            recoursTyp: $('#recoursTyp_se').val(),
            recoursNm: $('#recoursNm_se').val(),
            groupId:groupId
        });
    }
}


/**
 * 重置查询条件
 */
function clearSearchSysGroup(flag) {
    if (flag == '1'){
        $('#groupNm_se').textbox('reset');
        $('#blngtoOrgNo_se').textbox('reset');
    }else if(flag == '2'){
        $('#recoursTyp_se').textbox('reset');
        $('#recoursNm_se').textbox('reset');
    }

}

/**
 * 打开窗口：这里的窗口附加了事件，所以与共用方法脱离
 * @param winId
 * @param tit
 * @param width
 * @param height
 * @param url
 */
function openSysGroupWindow(winId, tit, width, height, url){
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
        shadow: false,
        onClose:function () {
            var groupId = $('#groupId_hidden').val();
            clearResourcesCache(groupId);
        }
    });
    $("#"+winId).window('open');
}


/**
 * 描述：打开用户组新增窗口
 * @param winId
 * @param url
 */
function toInsertSysGroup(winId,url) {
    var title = '用户组新增';
    var width = '900';
    var height = '400';
    var url = url + 'sysGroup/toInsertSysGroup?winId='+winId;
    openSysGroupWindow(winId,title,width,height,url);
}

/**
 * 描述：打开用户组修改窗口
 * @param winId
 * @param url
 */
function toUpdateSysGroup(winId,url) {
    var row = $('#dg_sysGroup').datagrid("getSelected");
    if (row == '' || row == null){
        $.messager.alert("提示","请选择用户组");
    }else{
        var groupId = row.groupId;
        var title = '用户组修改';
        var width = '900';
        var height = '400';
        var url = url + 'sysGroup/toUpdateSysGroup?winId=' + winId + '&groupId='+groupId;
        openSysGroupWindow(winId,title,width,height,url);
    }
}

/**
 * 描述：查询用户组详情
 */
function toDetailSysGorup(url,groupId){
    $.ajax({
        url:url+'sysGroup/toUpdateSysGroup01',
        type:'post',
        dataType:'json',
        data:{'groupId':groupId},
        success:function (data) {
            //var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
            var d = data.data;
            if (data.success){
                initTextboxSelf('groupId_detail',d.groupId,true);
                initTextboxSelf('groupNm_detail',d.groupNm,false);
                initTextboxSelf('blngtoOrgNo_hidden',d.blngtoOrgNo,false);
                assignComboboxSelf('isGlobal_detail',d.isGlobal,false);
                initTextboxSelf('groupComnt_detail',d.groupComnt,false);
                //通过机构编码获取机构名称
                var orgNm = getOrgNmByOrgCd(url,d.blngtoOrgNo);
                initTextboxSelf('blngtoOrgNo_detail',orgNm,false);
            }
        }
    })
}



/**
 * 描述：删除用户组
 * @param url
 */
function toDeleteSysGroup(url) {
    var row = $('#dg_sysGroup').datagrid("getSelected");
    if (row == '' || row == null){
        $.messager.alert("提示","请选择用户组");
    }else{
        $.messager.confirm('提示','您确定要删除选中的用户组吗?',function(r){
            if(r){
                var groupId = row.groupId;
                if(!isExistUserUnderGroup(url,groupId)){
                    $.messager.alert("提示","该用户组下有已分配的用户，不能删除");
                }else{
                    $.ajax({
                        url:url + 'sysGroup/deleteSysGroup',
                        type:'post',
                        dataType:'json',
                        data:{'groupId':groupId},
                        success:function (data) {
                            //下面这俩个步骤用于处理后端传递的object对象
                            var data = JSON.stringify(data);
                            var data = eval('(' + data + ')');
                            if (data.success){
                                //刷新页面数据
                                refreshDataGridBySelf('dg_sysGroup');
                                refreshDataGridBySelf('dg_sysUser');
                                refreshDataGridBySelf('dg_group_resources');
                                $('#groupId_detail').textbox({
                                    disabled:false
                                });
                                $('#form_sysGroup_detail').form('clear');
                                $.messager.alert("提示",data.message);
                            }else{
                                $.messager.alert("提示",data.message);
                            }
                        },
                        error:function () {
                            $.messager.alert("提示","删除请求失败！");
                        }
                    })
                }
            }
        })
    }
}

/**
 * 描述：用户组主页删除用户组资源
 * @param url
 */
function toDeleteSysGroupRecources(url) {
    var groupId = $('#groupId_hidden').textbox('getValue');
    var rows = $('#dg_group_resources').datagrid('getChecked');
    if (rows == '' || rows == null){
        $.messager.alert('提示','请选择需要删除的资源!');
    }else{
        var resources = '';
        $.each(rows,function (index,row) {
            if (index == rows.length - 1){
                resources = resources + row.recoursId;
            }else{
                resources = resources + row.recoursId + '_';
            }
        })
        $.ajax({
            url:url+'sysGroup/deleteSysGroupResourcesFromDB',
            type:'post',
            dataType:'json',
            data:{'resources':resources,'groupId':groupId},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    $('#dg_group_resources').datagrid('reload');
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        });
    }
}

/**
 * 描述：检查是否有用户分配了指定的用户组
 * @param groupId
 */
function isExistUserUnderGroup(url,groupId){
    var flag = false;
    $.ajax({
        async:false,
        url:url + 'sysGroup/isExistUserUnderGroup',
        type:'post',
        dataType:'json',
        data:{'groupId':groupId},
        success:function (data) {
            if (data == '0'){
               flag = true;
            }
        }
    })
    return flag;
}

//--------------------用户组新增/修改页面js-----------------------
/**
 * 描述：初始化用户组新增页面
 * @param url
 * @param groupId 用户组ID，后台通过UUID生成
 */
function initSysGroupInsertPage(){
    initTextboxBySysGroup('groupNm_insert','',20,false,'200','20');
    initTextboxBySysGroup('blngtoOrgNo_insert','',0,false,'200','20');
    initComboboxBySysGroup('isGlobal_insert','SYS02','',false,'200','20');
    initTextboxBySysGroup('groupComnt_insert','',200,false,'200','100');
    //-初始化机构选择组件
    initSysOrgInfoComponent('blngtoOrgNo_insert', 'blngtoOrgNo_hid', 'win_sysGroup_insert', '0', '', 'false', '0', '0', '', '600', '400');
}

/**
 * 描述：初始化用户组资源列表
 * @param url
 */
function initSysGroupResourcesList(groupId){
    $('#dg_group_resources_list').datagrid({
        url: basePath + 'sysGroup/listRecoursByGroupId?groupId='+groupId,
        fitColumns: true,
        singleSelect: false,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar:'#dg_resource_tool',
        onLoadSuccess: function (data) {
            //去除表头的复选框（表头复选框有全选的作用）
            $(".datagrid-header-check").html("");
            //更改的是datagrid中的数据字体大小
            $('.datagrid-cell').css('font-size', '12px');
            //datagrid中的列名称
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');
        }
    })
}

/**
 * 描述：打开用户组资源列表
 * @param winId
 * @param url
 */
function listSysGroupResources(winId,url){
    var title = '任务组资源列表';
    var width = '600';
    var height = '500';
    var url = url + 'sysGroup/toSysGroupResourcesList?winId='+winId;
    openWindowSelf(winId,title,width,height,url);
}



/**
 * 描述：打开用户资源选择页面
 * @param winId
 * @param url
 * @param flag 标志资源类型
 * @param whereFlag 标志赋值操作的页面 0表示主页，1表示新增/修改页面
 * @param orgFlag 机构选择组件页面跳转控制，本业务中机构字段的值为机构编号，并非机构ID，故需要重新写一个机构组件
 */
function toResourcesPage(winId,url,flag,whereFlag){
    if (whereFlag == '0'){
        var groupId = $('#groupId_hidden').val();
        if (groupId == null || groupId ==''){
            $.messager.alert("提示","请选择用户组");
        }else{
            var fieldId = '';
            var url = url + 'sysGroup/toResourcesPage?flag='+flag+'&whereFlag='+whereFlag;
            var treeType = '0';
            var selfTreeType = '';
            var orgLimits = 'false';
            var selectType = '1';
            var treeNodeSelectType = '0';
            var expOrgNo = '';
            url = url +
                "&treeType=" + treeType +
                "&selfTreeType=" + selfTreeType +
                "&orgLimits=" + orgLimits +
                "&selectType=" + selectType +
                "&treeNodeSelectType=" + treeNodeSelectType +
                "&expOrgNo=" + expOrgNo +
                "&winId=" + winId +
                "&fieldId=" +fieldId;
            openWindowSelf(winId,"资源选择",'600','400',url);
        }
    }else{
        var fieldId = '';
        var url = url + 'sysGroup/toResourcesPage?flag='+flag+'&whereFlag='+whereFlag;
        var treeType = '0';
        var selfTreeType = '';
        var orgLimits = 'false';
        var selectType = '1';
        var treeNodeSelectType = '0';
        var expOrgNo = '';
        url = url +
            "&treeType=" + treeType +
            "&selfTreeType=" + selfTreeType +
            "&orgLimits=" + orgLimits +
            "&selectType=" + selectType +
            "&treeNodeSelectType=" + treeNodeSelectType +
            "&expOrgNo=" + expOrgNo +
            "&winId=" + winId +
            "&fieldId=" +fieldId;
        openWindowSelf(winId,"资源选择",'600','400',url);
    }
}

/**
 * 描述：用户组资源列表--删除资源
 */
function reduceSysGroupReosurces(url){
    var groupId = $('#groupId_insert').textbox('getValue');
    var rows = $('#dg_group_resources_list').datagrid('getChecked');
    if (rows == '' || rows == null){
        $.messager.alert('提示','请选择需要删除的资源!');
    }else{
        var resources = '';
        $.each(rows,function (index,row) {
            if (index == rows.length - 1){
                resources = resources + row.recoursId;
            }else{
                resources = resources + row.recoursId + '_';
            }
        })
        $.ajax({
            url:url+'sysGroup/deleteSysGroupResources',
            type:'post',
            dataType:'json',
            data:{'resources':resources,'groupId':groupId},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    $('#dg_group_resources_list').datagrid('reload');
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        });
    }
}

/**
 * 描述：关闭窗口
 * @param winId
 */
function closeSysGroupWindow(winId){
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        //刷新列表数据
        refreshDataGridBySelf('dg_sysGroup');
    }
    $("#"+winId).window('close');
}

/**
 *描述：通过机构编码查询机构名称
 * */
function getOrgNmByOrgCd(url,orgCd){
    var result = '';
    $.ajax({
        async:false,
        url:url+'sysGroup/getOrgNmByOrgCd',
        type:'post',
        dataType:'json',
        data:{'orgCd':orgCd},
        success:function(data){
            var d = data.data;
            result = d.name;
        }
    });
    return result;
}

/**
 * 描述：系统用户表单提交方法
 * @param formId
 * @param url
 * @param baseUrl
 * @param flag 标志发起提交请求的位置 0：首页，1：窗口
 * @returns {boolean}
 */
function submitSysGroup(formId,baseUrl,url,flag){
    //验证框校验
    if (!$("#"+formId).form('validate')){
        return false;
    }
    if (flag == '0'){
        $('#groupId_detail').textbox({
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
                //提交成功后，数据回显
                if(flag == '0'){
                    initTextboxSelf('groupId_detail',d.groupId,true);
                    initTextboxSelf('groupNm_detail',d.groupNm,false);
                    assignComboboxSelf('isGlobal_detail',d.isGlobal,false);
                    initTextboxSelf('groupComnt_detail',d.groupComnt,false);
                    initTextboxSelf('blngtoOrgNo_hidden',d.blngtoOrgNo,true);
                    //通过机构编码获取机构名称
                    var orgNm = getOrgNmByOrgCd(baseUrl,d.blngtoOrgNo);
                    initTextboxSelf('blngtoOrgNo_detail',orgNm,false);
                    refreshDataGridBySelf('dg_sysGroup');
                }else if(flag == '1'){
                    initTextboxSelf('groupNm_insert',d.groupNm,true);
                    assignComboboxSelf('isGlobal_insert',d.isGlobal,true);
                    initTextboxSelf('groupComnt_insert',d.groupComnt,true);
                    //通过机构编码获取机构名称
                    var orgNm = getOrgNmByOrgCd(baseUrl,d.blngtoOrgNo);
                    initTextboxSelf('blngtoOrgNo_insert',orgNm,true);
                    //提交成功后，禁用提交/重置按钮
                    $('#but_insert').linkbutton('disable');
                    $('#but_reset').linkbutton('disable');
                    $('#but_resource').menubutton('disable');
                    $('#but_remove_cancel').linkbutton('disable');
                    //标记关闭按钮
                    document.getElementById('close_flag').value = 'true';//标记关闭按钮
                }
                $.messager.alert("提示",data.message);
            }else{
                $.messager.alert("提示",data.message);
            }
        }
    })
}

/**
 * 清除资源缓存
 */
function clearResourcesCache(groupId) {
    var result = false;
    $.ajax({
        async:false,
        url:'clearResourcesCache?groupId='+groupId,
        type:'post',
        dataType:'json',
        data:{},
        success:function(data){
           if(data.success){
                result = true;
           }
        }
    });
    return result;
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
function initTextboxBySysGroup(id,echoValue,maxlength,disabled,width,height) {
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
function initComboboxBySysGroup(id,dicType,echoValue,disabled,width,height){
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

