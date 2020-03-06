//------任务调度js-pengjuntao
var TempTaskId;
/**
 * 描述：初始化任务调度首页
 * @param url
 */
function initAscTaskCtlListPage(url){

    $('#taskId_hidden').textbox('setValue',"");
    //初始化组合框
    initComboboxSelf('taskCategory_se','ASC003',url);
    initComboboxSelf('frequency_se','ASC002',url);
    initComboboxSelf('priority_se','ASC011',url);
    initComboboxSelf('state_se','ASC001',url);
    initComboboxSelf('ret_se','ASC006',url);

    initAscTaskDataGrid('dg_ascTask');
    initTabClick('tbs_ascTaskConfig');
}

/**
 * 描述：初始化任务列表
 * @param id
 */
function initAscTaskDataGrid(id){
    $('#'+id).datagrid({
        url:basePath + 'AscTask/listAllAscTask',
        fitColumns:false,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#dg_tools',
        frozenColumns:[[
            {field:'ck_task',title:'',width:100,checkbox:true},
            {field:'taskId',title:'任务编号',width:100,align:'center'},
            {field:'taskName',title:'任务名称',width:100,align:'center'}
        ]],
        columns:[[
            {field:'taskType',title:'任务类型',width:100,align:'center'},
            {field:'taskCategory',title:'任务种类',width:100,align:'center'},
            {field:'nextDate',title:'待处理日期',width:100,align:'center'},
            {field:'frequency',title:'执行频率',width:100,align:'center'},
            {field:'priority',title:'优先级',width:100,align:'center'},
            {field:'state',title:'任务状态',width:100,align:'center'},
            {field:'crtDate',title:'创建日期',width:100,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px'); //更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px'); //datagrid中的列名称

            initTaskJobCfgTabPage();
            initTaskDependCfgTabPage();
            initTaskTriggerCfgTabPage();
            initTaskJobLogTabPage();
        },
        onSelect:function (rowIndex, rowData) {
            var taskId = rowData.taskId;
            $('#taskId_hidden').textbox('setValue',taskId);
            //获取任务组详情
            getAscTaskByTaskId(taskId);
            //选择任务，初始化任务配置页面，以查询该任务的任务配置
            $('#dg_jobCfg').datagrid('load',{
                taskId:taskId
            });
            $('#dg_dependCfg').datagrid('load',{
                taskId:taskId
            });
            $('#dg_triggerCfg').datagrid('load',{
                taskId:taskId
            });
            $('#dg_jobLog').datagrid('load',{
                taskId:taskId
            });
        }
    });
}

/**
 * 描述：初始化标签页点击事件
 */
function initTabClick(id){
    $('#'+id).tabs({
        onSelect:function (title,index) {
            var taskId = $('#taskId_hidden').val();
            if (title == '任务依赖配置'){
                $('#dg_dependCfg').datagrid('load',{
                    taskId:taskId
                })
            } else if (title == '任务触发配置'){
                $('#dg_triggerCfg').datagrid('load',{
                    taskId:taskId
                })
            } else if (title == '任务处理日志'){
                $('#dg_jobLog').datagrid('load',{
                    taskId:taskId
                })
            } else if (title == '任务处理配置'){
                $('#dg_jobCfg').datagrid('load',{
                    taskId:taskId
                })
            }
        }
    });
}

/**
 * 描述：初始化任务处理配置数据表格
 * @param url
 */
function initTaskJobCfgTabPage(){
    //初始化任务配置数据网格
    $('#dg_jobCfg').datagrid({
        url:basePath + 'AscTask/listAllAscJobCfg',
        fitColumns:true,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#dg_jobcfg_tools',
        columns:[[
            {field:'ck_jobcfg',title:'',checkbox:true},
            {field:'jobId',title:'处理编号',width:100,align:'center'},
            {field:'jobName',title:'处理名称',width:100,align:'center'},
            {field:'jobSeq',title:'处理序列',width:100,align:'center'},
            {field:'jobType',title:'处理类型',width:100,align:'center'},
            {field:'jobPara',title:'处理参数',width:100,align:'center'}
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
 * 描述：初始化任务依赖配置数据表格
 * @param url
 */
function initTaskDependCfgTabPage(){
    //初始化任务依赖配置数据网格
    $('#dg_dependCfg').datagrid({
        url:basePath + 'AscTask/listAllAscDependCfg',
        fitColumns:true,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#dg_dependcfg_tools',
        columns:[[
            {field:'ck_depend',title:'',checkbox:true},
            {field:'dependId',title:'依赖任务编号',width:100,align:'center'},
            {field:'taskName',title:'依赖任务名称',width:100,align:'center'},
            {field:'flag',title:'依赖对象类型',width:100,align:'center'},
            {field:'frequency',title:'执行频率',width:100,align:'center'}
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
 * 描述：初始化任务触发配置数据表格
 * @param url
 */
function initTaskTriggerCfgTabPage() {
    //初始化任务触发配置
    $('#dg_triggerCfg').datagrid({
        url:basePath + 'AscTask/listAllAscTriggerCfg',
        fitColumns:true,
        singleSelect:true,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#dg_trigger_tool',
        columns:[[
            {field:'ck_trig',title:'',checkbox:true},
            {field:'trigId',title:'触发任务编号',width:100,align:'center'},
            {field:'taskName',title:'触发任务名称',width:100,align:'center'},
            {field:'flag',title:'触发对象类型',width:100,align:'center'},
            {field:'frequency',title:'执行频率',width:100,align:'center'}
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
 * 描述：初始化任务处理日志数据网格
 * @param url
 */
function initTaskJobLogTabPage() {
    $('#dg_jobLog').datagrid({
        url:basePath + 'AscTask/listAllAscJobLog',
        fitColumns:false,
        singleSelect:false,
        pagination:true,
        pageNumber:'1',
        pageSize:'10',
        pagePosition:'top',
        toolbar:'#dg_joblog_tool',
        frozenColumns:[[
            {field:'ck_log',title:'',width:100,checkbox:true},
            {field:'id',title:'',width:100,hidden:true},
            {field:'taskId',title:'任务编号',width:100,align:'center'},
            {field:'jobId',title:'处理编号',width:100,align:'center'},
            {field:'ret',title:'运行结果',width:100,align:'center'}
        ]],
        columns:[[
            {field:'nextDate',title:'批处理日期',width:100,align:'center'},
            {field:'startTime',title:'处理开始时间',width:100,align:'center'},
            {field:'endTime',title:'处理结束时间',width:100,align:'center'},
            {field:'execTime',title:'处理时长',width:100,align:'center'},
            {field:'jobName',title:'处理名称',width:100,align:'center'},
            {field:'jobPara',title:'处理参数',width:100,align:'center'},
            {field:'msg',title:'运行信息',width:100,align:'center'}
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
 * 描述：查询条件查询
 */
function doAscTaskSearch() {
    $('#dg_ascTask').datagrid('load',{
        taskId: $('#taskId_se').val(),
        taskName: $('#taskName_se').val(),
        taskCategory: $('#taskCategory_se').val(),
        frequency: $('#frequency_se').val(),
        priority: $('#priority_se').val(),
        nextDate: $('#nextDate_se').datebox('getValue'),
        nextDate_start: $('#nextDate_start_se').datebox('getValue'),
        nextDate_end: $('#nextDate_end_se').datebox('getValue'),
        crtDate_start: $('#crtDate_start_se').datebox('getValue'),
        crtDate_end: $('#crtDate_end_se').datebox('getValue'),
        state: $('#state_se').val()
    });
}

/**
 * 描述：重置查询条件
 * @param formId
 * @author pengjuntao
 */
function resetAscTaskSearch() {
    $('#taskId_se').textbox('reset');
    $('#taskName_se').textbox('reset');
    $('#taskCategory_se').combobox('clear');
    $('#frequency_se').combobox('clear');
    $('#priority_se').combobox('clear');
    $('#nextDate_se').datebox('clear');
    $('#nextDate_start_se').datebox('clear');
    $('#nextDate_end_se').datebox('clear');
    $('#crtDate_start_se').datebox('clear');
    $('#crtDate_end_se').datebox('clear');
    $('#state_se').combobox('clear');
}

/**
 * 描述：查询任务详情
 * @param url
 * @param taskId
 */
function getAscTaskByTaskId(taskId){
    //-清除之前的详情数据
    clearAscTaskDetail();
    $.ajax({
        url:basePath + 'AscTask/getAscTaskByTaskIdByShow',
        type:'post',
        dataType:'json',
        data:{'taskId':taskId},
        success:function (data) {
            var d = data.data;
            if (data.success){
                /*var taskType = exchangeAscDictry(url,d.taskType,'ASC010');
                var priority = exchangeAscDictry(url,d.priority,'ASC011');
                var taskCategory = exchangeAscDictry(url,d.taskCategory,'ASC003');
                var frequency = exchangeAscDictry(url,d.frequency,'ASC002');
                var state = exchangeAscDictry(url,d.state,'ASC001');
                var timeFlag = exchangeAscDictry(url,d.timeFlag,'SYS02');*/
                var groupName = exchangeAscGroupName(d.groupId);

                $('#taskId_detail').html(d.taskId);
                $('#taskName_detail').html(d.taskName);
                $('#taskType_detail').html(d.taskType);
                $('#taskCategory_detail').html(d.taskCategory);
                $('#frequency_detail').html(d.frequency);
                $('#state_detail').html(d.state);
                $('#nextDate_detail').html(d.nextDate);
                $('#startTime_detail').html(d.startTime);
                $('#endTime_detail').html(d.endTime);
                $('#timeFlag_detail').html(d.timeFlag);
                $('#priority_detail').html(d.priority);
                $('#groupId_detail').html(groupName);
                $('#describ_detail').html(d.describ);
            }
        }
    })
}

/**
 * 清除任务详情
 */
function clearAscTaskDetail(){
    $('#taskId_detail').html("");
    $('#taskName_detail').html("");
    $('#taskType_detail').html("");
    $('#taskCategory_detail').html("");
    $('#frequency_detail').html("");
    $('#state_detail').html("");
    $('#nextDate_detail').html("");
    $('#startTime_detail').html("");
    $('#endTime_detail').html("");
    $('#timeFlag_detail').html("");
    $('#priority_detail').html("");
    $('#groupId_detail').html("");
    $('#describ_detail').html("");
}

/**
 * 任务组编号与任务组名称转换
 * @param url
 * @param groupId
 * @returns {string}
 */
function exchangeAscGroupName(groupId){
    var result = '';
    $.ajax({
        async:false,
        url:basePath + 'AscTask/getAscGroupByGroupId',
        type:'post',
        dataType:'json',
        data:{'groupId':groupId},
        success:function (data) {
            var d = data.data;
            result = d.groupName;
        }
    })
    return result;
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

/**
 * 描述：打开新增页面
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toInsertAscTaskPage(winId,title,url,width,height) {
    openWindowSelf(winId, title, width, height, url);
}

/**
 * 打开更新页面
 * @param winId
 * @param title
 * @param url
 * @param width
 * @param height
 */
function toModifyAscTaskPage(winId,title,url,width,height) {
    var row = $('#dg_ascTask').datagrid('getSelected');
    if(row == null){
        $.messager.alert("提示","请选择需要操作的记录！");
    }else{
        var taskId = row.taskId;
        url = url+'?taskId='+taskId;
        openWindowSelf(winId, title, width, height, url);
    }
}

/**
 * 描述：删除任务
 * @param url
 */
function toDeleteTask(url) {
    var row = $('#dg_ascTask').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要删除的任务！");
    }else{
        var taskId = row.taskId;
        var state = row.state;
        if(state == 'ASC001004' || state == '正在处理'){
            $.messager.alert("提示","所选择的任务正在处理中，无法删除！");
        }else if(checkDependRel(url,taskId)==true){
            $.messager.alert("提示","所选择的任务存在被依赖关系，无法删除！");
        }else{
            $.messager.confirm('提示', '您确定要删除选中的任务吗？', function(r){
                if (r){
                    $.ajax({
                        url:url + 'AscTask/toDeleteAscTask',
                        type:'post',
                        dataType:'json',
                        data:{"taskId":taskId},
                        success:function (data) {
                            //下面这俩个步骤用于处理后端传递的object对象
                            var data = JSON.stringify(data);
                            var data = eval('(' + data + ')'); // change the JSON string to javascript object
                            if(data.success){
                                refreshDataGridBySelf('dg_ascTask');
                                $.messager.alert("提示","删除成功");
                            }else{
                                $.messager.alert("提示","删除失败");
                            }
                        },
                        error:function () {
                            $.messager.alert("警告","请求失败");
                        }
                    })
                }
            });
        }
    }
}
/**
 * 描述：查询选中的任务是否存在被依赖关系
 * @param taskId
 * @returns {boolean}
 */
function checkDependRel(url,taskId){
    var result = false;
    $.ajax({
        async:false,
        url:'AscTask/checkDependRel',
        type:'post',
        dataType:'json',
        data:{"taskId":taskId},
        success:function (data) {
            //下面这俩个步骤用于处理后端传递的object对象
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')'); // change the JSON string to javascript object
            if(data.success){
                result = true;
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

//打开任务处理配置标签页
function toAscJobCfg(title,url) {
    var row = $('#dg_ascTask').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要处理配置的记录");
    }else{
        var taskId = row.taskId;
        url = url + "?taskId=" + taskId;
        newTab(title,url);
    }
}
//打开任务依赖配置标签页
function toAscDependCfg(title,url) {
    var row = $('#dg_ascTask').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要依赖配置的记录");
    }else{
        var taskId = row.taskId;
        url = url + "?taskId=" + taskId;
        newTab(title,url);
    }
}
//打开任务日志标签页
function toAscTriggerCfg(title,url) {
    var row = $('#dg_ascTask').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要查看日志的记录");
    }else{
        var taskId = row.taskId;
        url = url + "?taskId=" + taskId;
        newTab(title,url);
    }
}

//打开任务日志查询标签页
function toAscTriggerCfg(title,url) {
    var row = $('#dg_ascTask').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要触发配置的记录");
    }else{
        var taskId = row.taskId;
        url = url + "?taskId=" + taskId;
        newTab(title,url);
    }
}

/**
 * 描述：初始化新增任务页面
 * @param url
 */
function initAscTaskInsertPage(){

    initTextboxByAscTask('taskId_insert','',32,false,'200','20');
    initTextboxByAscTask('taskName_insert','',100,false,'200','20');
    initTextboxByAscTask('startTime_insert','',0,false,'200','20');
    initTextboxByAscTask('endTime_insert','',0,false,'200','20');
    initTextboxByAscTask('describ_insert','',255,false,'600','100');
    initTextboxByAscTask('frequency_insert','',0,false,'200','20');
    //initTextboxByAscTask('state_insert','',0,false,'200','20');

    initComboboxByAscTask('taskType_insert','ASC010','',false,'200','20');
    initComboboxByAscTask('taskCategory_insert','ASC003','',false,'200','20');
    initComboboxByAscTask('priority_insert','ASC011','',false,'200','20');
    initComboboxByAscTask('timeFlag_insert','SYS02','',false,'200','20');
    initDateBoxByAscTask('nextDate_insert','','200','20');

    //初始化文本框的按钮点击事件(查询码值)
    //initCommonFuncModule('taskType_insert','win_ascTask_insert','taskType_insert$taskType_hidden','ASC010');
    //initCommonFuncModule('taskCategory_insert','win_ascTask_insert','taskCategory_insert$taskCategory_hidden','ASC003');
    //initCommonFuncModule('priority_insert','win_ascTask_insert','priority_insert$priority_hidden','ASC011');
    //initCommonFuncModule('frequency_insert','win_ascTask_insert','frequency_insert$frequency_hidden','ASC002');
    //initCommonFuncModule('state_insert','win_ascTask_insert','state_insert$state_hidden','ASC001');

    initAscGroupComponent('groupId_insert','win_ascTask_insert','groupId_hidden');
    initSysDictryComponent('frequency_insert', 'frequency_hidden', 'ASC002', 'win_ascTask_insert', '0', '', '0', '650', '500');
    initSysDictryComponent('state_insert', 'state_hidden', 'ASC001', 'win_ascTask_insert', '0', '', '0', '650', '500');
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
 * 描述：初始化任务更新页面
 * @param url
 */
function initAscTaskUpdatePage() {
    initAscGroupComponent('groupId_insert','win_ascTask_insert','groupId_hidden');
    initSysDictryComponent('frequency_insert', 'frequency_hidden', 'ASC002', 'win_ascTask_insert', '0', '', '0', '650', '500');
    initSysDictryComponent('state_insert', 'state_hidden', 'ASC001', 'win_ascTask_insert', '0', '', '0', '650', '500');
}

/**
 * 描述：表单提交
 * @param baseUrl
 * @param url
 * @param formId
 * @param type 新增/修改
 */
function submitAscTaskForm(baseUrl,url,formId,type) {
    //-提交校验
    if (!$("#"+formId).form('validate')){
        return false;
    }
    if (type == 'update'){
        //取消禁用
        $('#taskId_insert').textbox({
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
                //数据回显
                initTextboxSelf('taskId_insert',d.taskId,true);
                initTextboxSelf('taskName_insert',d.taskName,true);
                initTextboxSelf('taskType_insert',d.taskType,true);
                initTextboxSelf('taskCategory_insert',d.taskCategory,true);
                initTextboxSelf('frequency_insert',d.frequency,true);
                initTextboxSelf('state_insert',d.state,true);
                initTextboxSelf('nextDate_insert',d.nextDate,true);
                initTextboxSelf('startTime_insert',d.startTime,true);
                initTextboxSelf('endTime_insert',d.endTime,true);
                assignComboboxSelf('timeFlag_insert',d.timeFlag,true);
                initTextboxSelf('priority_insert',d.priority,true);
                initTextboxSelf('groupId_insert',d.groupId,true);
                initTextboxSelf('describ_insert',d.describ,true);
                //禁用新增/重置按钮
                $('#but_insert').linkbutton('disable');
                $('#but_reset').linkbutton('disable');
                document.getElementById('close_flag').value = 'true';//标记关闭按钮
                if (type == 'update'){
                    $.messager.alert('提示','更新成功');
                }else{
                    $.messager.alert('提示','新增成功');
                }
            }else{
                if (type == 'update'){
                    $.messager.alert('提示','更新失败');
                }else{
                    $.messager.alert('提示','新增失败');
                }
            }
        }
    })
}



/**
 * 描述：检查任务ID的唯一性
 * @param taskId
 * @returns {boolean}
 */
function checkTaskId(taskId){
    var result = false;
    $.ajax({
        async:false,
        url:'AscTask/checkAscTaskId',
        type:'post',
        dataType:'json',
        data:{"taskId":taskId},
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
function closeAscTaskWindow(winId) {
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        //刷新列表数据
        refreshDataGridBySelf('dg_ascTask');
    }
    $("#"+winId).window('close');
}

//-------------------任务处理配置----------------------------
/**
 * 描述：初始化任务处理配置新增页面
 * @param jobSeq
 */
function initAscTaskJobCfgInsertPage(jobSeq){
    //初始化文本框/组合框
    initComboboxByAscTask('jobType_insert','ASC012','',false,'252','20');
    initTextboxByAscTask('jobName_insert','',10,false,'252','20');
    initTextboxByAscTask('jobSeq_insert',jobSeq,40,true,'252','20');
    initTextboxByAscTask('jobPara_insert','',2000,false,'252','100');
}


//新增窗口
function toInsertAscTaskJobCfgPage(winId,title,url,width,height) {
    var taskId = $('#taskId_hidden').val();
    if (taskId == ''){
        $.messager.alert('提示','请选择任务');
    }else{
        url = url + '?taskId='+taskId;
        openWindowSelf(winId,title,width,height,url);
    }
}
//修改窗口
function toModifyAscTaskJobCfgPage(winId,title,url,width,height) {
    var row = $('#dg_jobCfg').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要修改的记录");
    }else{
        var jobId = row.jobId;
        url = url + '?jobId='+ jobId;
        openWindowSelf(winId,title,width,height,url);
    }
}
//删除
function toDeleteAscTaskJobCfg(url) {
    var row = $('#dg_jobCfg').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要删除的任务处理配置");
    }else{
        var jobId = row.jobId;
        $.messager.confirm('提示','您确定要删除选中的配置吗？',function(r) {
            if (r){
                $.ajax({
                    url:url,
                    type:'post',
                    dataType:'json',
                    data:{"jobId":jobId},
                    success:function (data) {
                        var data = JSON.stringify(data);
                        var data = eval('(' + data + ')');
                        if (data.success){
                            refreshDataGridBySelf('dg_jobCfg');
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
        })
    }
}

/**
 * 检查新增job的类型是否符合任务的类型要求:起点任务/终点任务/系统任务均为shell任务
 * @param taskId        任务ID
 * @param jobType       处理类型
 */
function checkJobType(taskId,jobType){
    var flag = false;
    $.ajax({
        async:false,
        url:'AscTask/checkJobType',
        type:'post',
        dataType:'json',
        data:{'taskId':taskId,'jobType':jobType},
        success:function (data) {
            if (data == '1'){
                flag = true;
            }
        }
    });
    return flag;
}

//表单异步提交（非easyUI表单提交）
function submitJobCfgForm(baseUrl,url,formId) {
    if(!$('#'+formId).form('validate')){
        return false;
    }
    //取消禁用
    $('#jobSeq_insert').textbox({
        disabled:false
    })
    $.ajax({
        url:baseUrl + url,
        type:'post',
        dataType:'text',
        data:$('#'+formId).serialize(),
        success:function (data) {
            var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
            var d = data.data;
            if (data.success){
                //新增成功后，数据回显
                initTextboxSelf('jobName_insert',d.jobName,true);
                initTextboxSelf('jobSeq_insert',d.jobSeq,true);
                initTextboxSelf('jobPara_insert',d.jobPara,true);
                assignComboboxSelf('jobType_insert',d.jobType,true);
                $('#but_insert').linkbutton('disable');
                $('#but_reset').linkbutton('disable');
                document.getElementById('close_flag').value = 'true';//标记关闭按钮
                $.messager.alert("提示",data.message);
            }else{
                $.messager.alert("提示",data.message);
            }
        }
    })
}

//------------------任务依赖配置-------------------------------
/**
 * 描述：初始化任务依赖配置新增页面
 * @param taskId        任务ID
 */
function initAscTaskDependCfgInsertPage(taskId){
    initComboboxByAscTask('flag_insert','ASC014','',false,'252','20');
    initTextboxByAscTask('taskId_insert',taskId,32,true,'252','20');
    initTextboxByAscTask('dependId_insert','',32,false,'252','20');
    initTextboxByAscTask('describ_insert','',255,false,'252','100');
    initTaskComponent('dependId_insert','win_task_search','dependCfg');
    TempTaskId = taskId;
}

/**
 * 描述：初始化任务选择组件
 * @param id        文本框ID
 * @param winId     打开窗口
 * @param target    目标窗口
 */
function initTaskComponent(id,winId,target){
    $('#'+id).textbox({
        onClickButton:function (index) {
            searchTaskInfo(winId,
                '任务查询列表',
                basePath + 'AscTask/toListAscTaskById',
                '600',
                '400',
                target)
        }
    })
}

//查询任务编号
function searchTaskInfo(winId,title,url,width,height,flag) {
    var id = '';
    if (flag == 'dependCfg'){
        id = document.getElementById("dependId_insert").value;
    }else if (flag == 'triggerCfg'){
        id = document.getElementById("trigId_insert").value;
    }
    url = url + '?taskId=' + id + '&flag='+flag + '&winId=' + winId;
    openWindowSelf(winId,title,width,height,url);
}

/**
 * 描述：依赖任务ID的唯一性
 */
function uniqueDependId(taskId,dependId){
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'AscTask/uniqueDependId',
        dataType:'JSON',
        type:'POST',
        data:{'taskId':taskId,'dependId':dependId},
        success:function (data) {
            //下面这俩个步骤用于处理后端传递的object对象
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')'); // change the JSON string to javascript object
            if(data.success){
                result = false;
            }else{
                result = true;
            }
        }
    })
    return result;
}

//新增窗口
function toInsertAscTaskDependCfgPage(winId,title,url,width,height) {
    var taskId = $('#taskId_hidden').val();
    if (taskId == ''){
        $.messager.alert('提示','请选择任务');
    }else{
        url = url + '?taskId='+taskId;
        openWindowSelf(winId,title,width,height,url);
    }
}

function toDeleteAscTaskDependCfg(url) {
    var taskId = $('#taskId_hidden').val();
    var row = $('#dg_dependCfg').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要删除的记录");
    }else{
        var dependId = row.dependId;
        $.messager.confirm('提示','您确定要删除选中的配置吗？',function(r) {
            if (r){
                $.ajax({
                    url:url,
                    type:'post',
                    dataType:'json',
                    data:{"dependId":dependId,"taskId":taskId},
                    success:function (data) {
                        var data = JSON.stringify(data);
                        var data = eval('(' + data + ')');
                        if (data.success){
                            refreshDataGridBySelf('dg_dependCfg');
                            $.messager.alert("提示","删除成功");
                        }else{
                            $.messager.alert("提示","删除失败");
                        }
                    },
                    error:function () {
                        $.messager.alert("警告","请求失败")
                    }
                })
            }
        })
    }
}

/**
 * 描述：依赖配置表单提交
 */
function submitDependCfgForm(baseUrl,url,formId){
    //提交校验
    if (!$('#'+formId).form('validate')){
        return false;
    }
    //取消禁用
    $('#taskId_insert').textbox({
        disabled:false
    })
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
                initTextboxSelf('taskId_insert',d.taskId,true);
                initTextboxSelf('dependId_insert',d.dependId,true);
                assignComboboxSelf('flag_insert',d.flag,true);
                initTextboxSelf('describ_insert',d.describ,true);
                //新增成功后，禁用提交/重置按钮
                $('#but_insert').linkbutton('disable');
                $('#but_reset').linkbutton('disable');
                document.getElementById('close_flag').value = 'true';
                $.messager.alert("提示",data.message);
            }else{
                $.messager.alert("提示",data.message);
            }
        },
        error:function () {

        }
    })
}


//-----------------触发配置页面----------------------
function toInsertAscTaskTriggerCfgPage(winId,title,url,width,height) {
    var taskId = $('#taskId_hidden').val();
    if (taskId == ''){
        $.messager.alert('提示','请选择任务');
    }else{
        url = url + '?taskId='+taskId;
        openWindowSelf(winId,title,width,height,url);
    }
}
/**
 * 描述：初始化任务依赖配置新增页面
 * @param taskId        任务ID
 */
function initAscTaskTrigCfgInsertPage(taskId){
    initComboboxByAscTask('flag_insert','ASC014','',false,'252','20');
    initTextboxByAscTask('taskId_insert',taskId,32,true,'252','20');
    initTextboxByAscTask('trigId_insert','',32,false,'252','20');
    initTextboxByAscTask('describ_insert','',255,false,'252','100');
    initTaskComponent('trigId_insert','win_task_search','triggerCfg');
    TempTaskId = taskId;
}

/**
 * 描述：依赖任务ID的唯一性
 */
function uniqueTrigId(taskId,trigId){
    var result = false;
    $.ajax({
        async:false,
        url:basePath + 'AscTask/uniqueTrigId',
        dataType:'JSON',
        type:'POST',
        data:{'taskId':taskId,'trigId':trigId},
        success:function (data) {
            //下面这俩个步骤用于处理后端传递的object对象
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')'); // change the JSON string to javascript object
            if(data.success){
                result = false;
            }else{
                result = true;
            }
        }
    })
    return result;
}

function toDeleteAscTaskTriggerCfg(url) {
    var taskId = $('#taskId_hidden').val();
    var row = $('#dg_triggerCfg').datagrid('getSelected');
    if (row == null){
        $.messager.alert("提示","请选择需要删除的记录");
    }else{
        var trigId = row.trigId;
        $.messager.confirm('提示','您确定要删除选中的配置吗？',function(r) {
            if (r) {
                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    data: {"trigId": trigId, "taskId": taskId},
                    success: function (data) {
                        var data = JSON.stringify(data);
                        var data = eval('(' + data + ')');
                        if (data.success) {
                            refreshDataGridBySelf('dg_triggerCfg');
                            $.messager.alert("提示", "删除成功");
                        } else {
                            $.messager.alert("提示", "删除失败")
                        }
                    },
                    error: function () {
                        $.messager.alert("警告", "请求失败")
                    }
                })
            }
        })
    }
}

//表单异步提交（非easyUI表单提交）
function submitTriggerCfgForm(baseUrl,url,formId) {
    //提交校验
    if (!$('#'+formId).form('validate')){
        return false;
    }
    //取消禁用
    $('#taskId_insert').textbox({
        disabled:false
    })
    $.ajax({
        url:baseUrl + url,
        type:'post',
        dataType:'text',
        data:$('#'+formId).serialize(),
        success:function (data) {
            var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
            var d = data.data;
            if (data.success){
                //新增成功后数据回显
                initTextboxSelf('taskId_insert',d.taskId,true);
                initTextboxSelf('trigId_insert',d.trigId,true);
                assignComboboxSelf('flag_insert',d.flag,true);
                initTextboxSelf('describ_insert',d.describ,true);
                $('#but_insert').linkbutton('disable');
                $('#but_reset').linkbutton('disable');
                document.getElementById('close_flag').value = 'true';
                $.messager.alert("提示",data.message);
            }else{
                $.messager.alert("提示",data.message);
            }
        }
    })
}
//----------------------处理日志页面------------------------------
//查询
function doJobLogSearch() {
    $('#dg_jobLog').datagrid('load',{
        taskId: $('#taskId_hidden').val(),
        jobId: $('#jobId_se').val(),
        nextDate_start: $('#nextDate_start').val(),
        nextDate_end: $('#nextDate_end').val(),
        ret: $('#ret_se').val(),
    })
}
function resetJobLogSearch() {
    $('#jobId_se').textbox('reset');
    $('#nextDate_start').textbox('reset');
    $('#nextDate_end').textbox('reset');
    $('#ret_se').combobox('clear');
}

//删除
function toDeleteAscTaskJobLog(url) {
    var rows = $('#dg_jobLog').datagrid('getChecked');
    if (rows.length == 0){
        $.messager.alert("提示","请选择需要删除的记录");
    }else{
        $.messager.confirm('提示','您确定要删除选中的日志吗？',function(r) {
            if (r){
                var str = "";
                $.each(rows,function (index,row) {
                    if (index != row.length - 1){
                        str = str + row.id + "_";
                    }else{
                        str = str + row.id;
                    }
                });
                $.ajax({
                    url:url,
                    type:'post',
                    dataType:'json',
                    data:{"jobLogId":str},
                    success:function (data) {
                        var data = JSON.stringify(data);
                        var data = eval('(' + data + ')');
                        if (data.success){
                            refreshDataGridBySelf('dg_jobLog');
                            $.messager.alert("提示","删除成功");
                        }else{
                            $.messager.alert("提示","删除失败");
                        }
                    },
                    error:function () {
                        $.messager.alert("警告","请求失败")
                    }
                })
            }
        })
    }
}

/**
 * 打开折叠
 */
function openTabs() {
    $('#ascTaskPage').layout('expand','east');
}

//表单提交
/*function submitForm() {
    $('#form_insert').form({
        url:'<%=basePath%>AscTask/insertAscTask',
        onSubmit:function () {
            //输入验证
        },
        success:function (data) {
            var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
            var d = data.data;
            if (data.success){
                //数据回显
                initTextboxSelf('taskId_insert',d.taskId,true);
                initTextboxSelf('taskName_insert',d.taskName,true);
                initTextboxSelf('taskType_insert',d.taskType,true);
                initTextboxSelf('taskCategory_insert',d.taskCategory,true);
                initTextboxSelf('frequency_insert',d.frequency,true);
                initTextboxSelf('state_insert',d.state,true);
                initTextboxSelf('nextDate_insert',d.nextDate,true);
                initTextboxSelf('startTime_insert',d.startTime,true);
                initTextboxSelf('endTime_insert',d.endTime,true);
                assignComboboxSelf('timeFlag_insert',d.timeFlag,true);
                initTextboxSelf('priority_insert',d.priority,true);
                initTextboxSelf('groupId_insert',d.groupId,true);
                initTextboxSelf('describ_insert',d.describ,true);
                //禁用新增/重置按钮
                $('#but_insert').linkbutton('disable');
                $('#but_reset').linkbutton('disable');
                document.getElementById('close_flag').value = 'true';//标记关闭按钮
                $.messager.alert('提示','新增成功');
            }else{
                $.messager.alert('提示','新增失败');
            }
        }
    })
    $('#form_insert').form('submit');
}*/

/**
 * 描述：初始化任务管理的文本框
 * @param id            文本框ID
 * @param echoValue     文本框回显值
 * @param maxlength     文本框最大输入长度
 * @param disabled      是否禁用
 * @param width
 * @param height
 */
function initTextboxByAscTask(id,echoValue,maxlength,disabled,width,height) {
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
function initComboboxByAscTask(id,dicType,echoValue,disabled,width,height){
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
function initDateBoxByAscTask(id,echoValue,width,height){
    $('#'+id).datebox({
        editable:false,
        required:true,
        width:width,
        height:height
    });
    $('#'+id).datebox('setValue',echoValue);

}

/**
 * 描述：关闭弹出窗口
 * @param winId     窗口ID
 * @param targetDg  刷新的目标datagrid
 */
function closeWindowByAscTask(winId,targetDg){
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        //刷新页面
        refreshDataGridBySelf(targetDg);
    }
    $("#"+winId).window('close');

}

//自定义校验规则
$.extend($.fn.validatebox.defaults.rules,{
    uniqueTaskId:{
        validator:function (value,param) {
            return checkTaskId(value);
        },
        message:'任务组编号已存在，请重新输入'
    },
    limitTime:{
        validator:function (value,param) {
            return value > 0 && value < 23;
        },
        message:'时间为0到23之间的整数'
    },
    checkTime:{
        validator:function (value,param) {
            var startTime = $('#startTime_insert').textbox('getValue');
            return parseInt(startTime) < parseInt(value);
        },
        message:'结束时间必须大于开始时间'
    },
    checkDate:{
        validator:function (value,param) {
            var date01 = new Date();
            var date02 = new Date(value);
            if (date01.getTime() < date02.getTime()) {
                return true;
            } else {
                return false;
            }
        },
        message:'待处理日期必须大于今天的日期'
    },
    checkJobType:{
        validator:function (value,param) {
            var taskId = document.getElementById('taskId_jobcfg').value;
            return checkJobType(taskId,value);
        },
        message:'当选中任务的类型为起点任务/终点任务/系统任务时，为该任务新增的job配置处理类型必须为shell脚本'
    },
    checkDependId: {  //功能ID唯一性校验
        validator: function (value, param) {
            return value != TempTaskId;
        },
        message: '依赖任务编号与任务编号不能相等，请重新选择！'
    },
    uniqueDependId:{  //依赖编号的唯一性校验
        validator: function (value,param) {
            return uniqueDependId(TempTaskId,value);
        },
        message: '依赖任务编号已经在列表中存在，请重新选择！'
    },
    checkTrigId: {  //功能ID唯一性校验
        validator: function (value, param) {
            return value != TempTaskId;
        },
        message: '触发任务编号与任务编号不能相等，请重新选择！'
    },
    uniqueTrigId:{  //依赖编号的唯一性校验
        validator: function (value,param) {
            return uniqueTrigId(TempTaskId,value);
        },
        message: '触发任务编号已经在列表中存在，请重新选择！'
    }
})

