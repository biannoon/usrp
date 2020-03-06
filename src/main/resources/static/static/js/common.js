/**
 * 设置未来(全局)的AJAX请求默认选项
 * 主要设置了AJAX请求遇到Session过期的情况
 */
$.ajaxSetup({
    type: 'POST',
    complete: function(xhr,status) {
        var sessionSts = xhr.getResponseHeader('sessionSts');
        if(sessionSts == 'timeout') {
            var top = getTopWinow();
            var yes = $.messager.confirm("提示",'由于您长时间没有操作, session已过期, 请重新登录.','info');
            if (yes) {
                top.location.href = "http://"+window.location.host+"/usrp/login";
            }else{
                window.opener=null;
                window.open('','_self');
                window.close();
            }
        }
    }
});

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}

/**
 * 描述：动态查询需要展示到页面的列表列信息
 * @param gridId 数据列表ID
 * @param title 标题
 * @param tabnm 查询表名
 * @param oper 标志是否需要操作列
 */
function getListShow(gridId, title, tabnm, oper){
    $.ajax({
        url : basePath + "sysTabConfig/findByListShow",
        type : 'post',
        dataType : 'json',
        data : {
            sysFlg : "USRP",
            tabNm : tabnm
        },
        success: function(data){
            var colemsAll = new Array();
            $.each(data,function(i, o){
                var col = {};
                col['field'] = o.fNm;
                col['title'] = o.lNm;
                col['align'] = "center";
                col['width'] = "100px";
                if(o.dictryId != null && o.dictryId != ''){
                    col['dictry'] = o.dictryId;
                    col['formatter'] = formatData;
                }
                colemsAll.push(col);
            });
            if(oper){
                colemsAll = addOper(colemsAll);
            }
            createDataGrid(gridId, title, colemsAll);
        },
        error: function() {
            $.messager.alert('提示信息', "查询列表展示信息异常");
        }
    });
}

//增加操作列
function addOper(colemsAll){
    var col = {};
    col['field'] = 'operate';
    col['title'] = '操作';
    col['align'] = "center";
    col['formatter'] = formatOper;
    colemsAll.push(col);
    return colemsAll;
}

//每行数据是否被选中的标志位
var IsCheckFlag = true;
//创建列表展示页的列表
function createDataGrid(gridId, tit, colemsAll){
    $('#'+gridId).datagrid({
        border : false,
        //标题
        title : tit,
        //加载数据时显示
        loadMsg : '数据加载中....',
        //图标
        iconCls : 'icon-large-smartart',
        width : '99%',
        height : 'auto',
        //显示数据在同一行上
        nowrap : true,
        //单元格显示条纹
        striped : true,
        //是否显示行号
        rownumbers : true,
        autoRowHeight : false,
        //自动大小
        fit : false,
        fitColumns : true,
        //是否可折叠的
        collapsible : false,
        //只选择一行
        singleSelect : false,
        //只有当用户点击了复选框时，才会选中/取消选中复选框
        checkOnSelect: false,
        //分页控件
        pagination : true,
        frozenColumns : [[
            {field:'ck',checkbox:true}
        ]],
        columns : [ colemsAll ],
        onClickCell: function (rowIndex, field, value) {
            IsCheckFlag = false;
        },
        onSelect: function (rowIndex, rowData) {
            if (!IsCheckFlag) {
                IsCheckFlag = true;
                $('#'+gridId).datagrid("unselectRow", rowIndex);
            }
        },
        onUnselect: function (rowIndex, rowData) {
            if (!IsCheckFlag) {
                IsCheckFlag = true;
                $('#'+gridId).datagrid("selectRow", rowIndex);
            }
        }
    });
    $('#'+gridId).datagrid('getPager').pagination({
        pageNumber : 1,
        pageSize : 10,//每页显示的记录条数，默认为10
        pageList: [10,20,50,100]//可以设置每页记录条数的列表
    });
}

function formatData(value, row, index){
    var dictry = this.dictry;
    var ajaxObj = {
        url : basePath + 'dictionary/getSysDictryCdListById?dictryId=' + dictry,
        async : false
    }
    var showInfo = '字典项不存在';
    customAjaxSubmit(ajaxObj,function(data){
        $.each(data,function(i,item){
            if(value == item.dictryId){
                showInfo = item.dictryNm;
            }
        })
        return showInfo;
    },function(){});
    if(showInfo != null){
        return showInfo;
    }else{
        return value;
    }
}

//在div中以HTML片段形式打开固定大小弹出窗口
function openWindow(winId, tit, url){
    $("#"+winId).window({
        title: tit,
        href: url,
        width: 750,
        height: 470,
        left: '15%',
        closed: true,
        modal: false,
        cache: false,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        shadow: false,
        inline: false
    });
    $("#"+winId).window('open');
}

//在div中以HTML片段形式打开自定义大小弹出窗口
function openWindowSelf(winId, tit, width, height, url){
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

//以ifram的形式打开固定大小弹出窗口
function openWindowContent(winId, tit, url){
    $("#"+winId).window({
        title : tit,
        width : 850,
        height : 500,
        left : '15%',
        closed : true,
        modal : false,
        cache : false,
        minimizable : false,
        maximizable : false,
        collapsible : false,
        shadow : false,
        inline : false,
        content : '<iframe frameborder=\"0\" src=\"' + url + '\" style=\"height: 100%; width: 100%;\" ></iframe>'
    });
    $("#"+winId).window('open');
}

//关闭弹出窗口
function closeWin(winId) {
    $("#"+winId).window('close');
}

//对数据新增按钮的用法封装
function add(winId, title, url){
    openWindow(winId, title + "信息新增", basePath + url);
}

//对数删除增按钮的用法封装
function del(gridId, url, tableId){
    url = basePath + url;
    var selRows = $('#'+gridId).datagrid('getChecked');
    var ids = [];
    var rows = $('#'+gridId).datagrid('getSelections');
    for(var i=0; i<rows.length; i++){
        ids.push(rows[i][tableId]);
    }
    if(selRows.length == 0){
        $.messager.alert('提示信息', "请至少选择一条数据进行删除！");
        return false;
    }else if(selRows.length >= 1){
        $.messager.confirm("操作提示","确定删除所选记录吗？",function(data){
            if(data){
                ajaxUtil("post",url,"ids="+ids,"json");
                $('#'+gridId).datagrid('reload');
            }
        })
    }
}

//对数据修改按钮的用法封装
function upd(gridId, winId, title, url, tableId){
    var selRows = $('#'+gridId).datagrid("getChecked");
    if(selRows.length == 0 || selRows.length > 1){
        $.messager.alert('提示信息', "只能选择一条数据进行修改！");
    }else{
        var row = $('#'+gridId).datagrid("getSelected");
        url = basePath + url + '?' + tableId+ "=" + row[tableId];
        openWindow(winId, title + "信息修改", url);
    }
}

//对查看详情按钮的用法封装
function getDetail(winId, title, url){
    url = basePath + url + "&readonly=true";
    openWindow(winId, title + "详情列表", url);
}

//对分页查询按钮的用法封装
function Search(gridId, formId, url){
    $('#'+gridId).datagrid({
        url : basePath + url + '?' + $('#'+formId).serialize()
    });
}


/**
 * 描述：刷新指定标签页
 * @param title 标签标题
 * @param url 标签页内容加载url
 * @author pengjuntao
 *
 */
function refreshTab(title,url){
    var parent = self.parent.$;//获取父页面对象
    var tab = parent('#tabs').tabs('getSelected');  // get selected panel
    parent('#tabs').tabs('update', {
        tab: tab,
        options: {
            title: title,
            href: url
        }
    });
}

/**
 * 描述：新建同级标签页tab
 * @param title 标签标题
 * @param url 标签页内容加载url
 * @author pengjuntao
 */
function newTab(title,url) {
    //需要在本标签页，添加一个同级别的标签页，
    //需要先获取父级对象，通过父级对象获取同级标签的id
    var parent = self.parent.$;
    if (parent('#tabs').tabs('exists', title)){
        parent('#tabs').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
        parent('#tabs').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}

/**
 * 描述：修改DataGrid对象的默认大小，以适应页面宽度。
 * @param heightMargin 高度对页内边距的距离。
 * @param widthMargin 宽度对页内边距的距离。
 * @param minHeight 最小高度。
 * @param minWidth 最小宽度。
 * @author pengjuntao
 */
$.fn.extend({
    resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {
        var height = $(document.body).height() - heightMargin;
        var width = $(document.body).width() - widthMargin;
        height = height < minHeight ? minHeight : height;
        width = width < minWidth ? minWidth : width;
        $(this).datagrid('resize', {
            height : height,
            width : width
        });
    }
});

/**
 * 描述：重置表单内容
 * @param formId
 * @author pengjuntao
 */
function resetForm(formId) {
    $('#'+formId).form('reset');
}

/**
 * 描述：初始化组合框数据
 * @param id 组合框id
 * @param dicType 码值类型
 * @param url 访问url
 * @author pengjuntao
 */
function initComboboxSelf(id,dicType,url){
    $('#'+id).combobox({
        url:url+'dictionary/getSysDictryCdListById?dictryId='+dicType,
        valueField:'dictryId',
        textField:'dictryNm'
    });
}

/**
 * 描述：组合框赋值
 * @param id 组合框id
 * @param value 组合框value
 * @param disabled 是否禁用 true/false
 * @author pengjuntao
 */
function assignComboboxSelf(id,value,disabled){
    $('#'+id).combobox({
        value:value,
        disabled:disabled
    });
}

/**
 * 描述：初始化文本框数据
 * @param id 文本框id
 * @param value 文本框value
 * @param disabled 是否禁用 true/false
 * @author pengjuntao
 */
function initTextboxSelf(id,value,disabled){
    $('#'+id).textbox({
        value:value,
        disabled:disabled
    });
}

/**
 * 描述：刷新tree的数据
 * @param treeId 树ID
 * @param url 刷新url
 * @author pengjuntao
 */
function refreshTreeBySelf(treeId,url){
    $('#'+treeId).tree({
        url:url,
        loadFilter: function(data){
            return data;
        }
    })
}

/**
 * 描述：刷新tree的数据
 * @param treeId 树ID
 * @param url 刷新url
 * @author pengjuntao
 */
function refreshTreeGridBySelf(treeId,url){
    $('#'+treeId).treegrid({
        url:url,
        loadFilter: function(data){
            return data;
        }
    })
}



/**
 * 描述：刷新datagrid的数据
 * @param dgId datagrid的id
 * @author pengjuntao
 */
function refreshDataGridBySelf(dgId) {
    $('#'+dgId).datagrid('reload');
}

/**
 * 描述：js生成自定义按钮
 * @param btnId
 * @param text
 * @param iconCls
 */
function createLinkedButtonBySelf(btnId,text,iconCls) {
    $('#'+btnId).linkbutton({
        iconCls:iconCls,
        text:text,
        plain:true
    })
}

