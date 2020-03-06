/**
 * 描述：初始化系统服务器列表
 * @param id
 */
function initServerList_DG(id){
    $('#'+id).datagrid({
        url: basePath + 'syslog/getSysParamList?paramType=SYS0803',
        fitColumns: false,
        singleSelect: true,
        pagination: true,
        pageNumber: '1',
        pageSize: '10',
        pagePosition:'top',
        toolbar:'#dg_sys_log_tool',
        frozenColumns:[[
            {field:'ck',title:'',checkbox:true},
            {field:'ip',title:'服务器',width:100,align:'center'},
            {field:'port',title:'端口',width:100,align:'center'},
            {field:'user',title:'用户',width:100,align:'center'},
            {field:'pwd',title:'密码',width:100,align:'center'}
        ]],
        columns:[[
            {field:'url',title:'存放路径',width:500,align:'center'}
        ]],
        onLoadSuccess: function (data) {
            $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
            $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
            $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
        }
    });
}

/**
 * 描述：查询服务器日志文件
 */
function toSearchLog(){
    var row = $('#dg_sys_log').datagrid("getSelected");
    if (row == null || row == ''){
        $.messager.alert("提示","请选择需要查询日志的服务器");
    }else{
        var ip = row.ip;
        var port = row.port;
        var user = row.user;
        var pwd = row.pwd;
        var url = row.url;
        var url_ = basePath + 'syslog/toSearchSysLog?ip=' + ip + '&port=' + port + '&user=' + user + '&pwd=' + pwd + '&url=' + url;
        openWindowSelf('win_sys_log_server','系统日志列表','800','400',url_);
    }

}

/**
 * 描述：初始化任务组树状列表
 * @param treeId
 * @param dgId
 */
function initSysLog_DG_TREE(treeId,url) {
    $('#'+treeId).treegrid({
        url:url,
        fitColumns:false,
        idField:'sysLogId',
        treeField:'fileNm',
        frozenColumns:[[
            {field:'fileNm',title:'文件名',width:200,align:'left'},
            {field:'sysLogId',title:'日志id',width:150,align:'center',hidden:true}

        ]],
        columns:[[
            {field:'ip',title:'服务器',width:100,align:'center'},
            {field:'fileUpdDate',title:'文件更新时间',width:200,align:'center'},
            {field:'parentId',title:'父级id',width:100,align:'center',hidden:true},
            {field:'state',title:'是否展开',width:100,align:'center',hidden:true},
            {field:'isDic',title:'是否为目录',width:100,align:'center',hidden:true},
            {field:'port',title:'端口',width:100,align:'center',hidden:true},
            {field:'user',title:'用户',width:100,align:'center',hidden:true},
            {field:'pwd',title:'密码',width:100,align:'center',hidden:true},
            {field:'url',title:'存放路径',width:300,align:'center',hidden:true},
            {field:'op',title:'操作',width:100,align:'center',formatter:function (value,row,index) {
                if (row.isDic != 'SYS0201'){
                    return '<a href="javascript:void(0)" onclick="downloadFile(\''+row.url+','+row.ip +','+row.port+','+row.user+','+row.pwd+'\')">下载</a>';
                }
            }}
        ]],
        onClickRow: function (row) {//点击事件
        }
    });
}

/**
 * 描述：下载文件
 * @param url
 */
function downloadFile(url){
    $.messager.confirm("提示","您确定要下载日志文件吗？",function (r) {
        if (r){
            $.ajax({
                url:basePath + 'syslog/downloadSysLogFile',
                type:'post',
                dataType:'json',
                data:{'url':url},
                success:function (data) {
                    var data = JSON.stringify(data);
                    var data = eval('(' + data + ')');
                    if (data.success){
                        $.messager.alert("提示",data.message);
                    }else{
                        $.messager.alert("提示",data.message);
                    }
                }
            })
        }
    })
}
