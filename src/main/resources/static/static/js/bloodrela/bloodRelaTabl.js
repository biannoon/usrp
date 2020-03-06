
$(function(){
    initDatabase('#dataSourceTabl',true);
    initDictionaryCode('#tableStatus', 'DFI02', true);
    initBloodRelaTabl();
    initFieldList();
    initFieldMidList();
})
// 数据接口=====================================start
/**
 * 初始化目标接口列表
 * @param node
 */
var initBloodRelaTabl=function(){
    $('#bloodRelaTabl').datagrid({
        url:baseUrl+'/tableInterface/getList',
        fitColumns: true,
        border: true,
        pagination: true,
        idField: "tableId",
        pageSize: 10,
        pageList: [10, 20, 30],
        singleSelect: true,
        checkOnSelect: true,
        selectOnCheck: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        //固定列字段 不受横向滚动条拉动影响
        //frozenColumns : [ [ ] ],
        columns: [[{
            field: "tableId", title: "编号", width: 50, checkbox: true
        }, {
            field: "enName", title: "英文名称", width: 100, align: 'center'
        }, {
            field: "cnName", title: "中文名称", width: 100, align: 'center'
        }, {
            field: "dataSorceId", title: "数据源ID", width: 50, align: 'center'
        }, {
            field: "tableComment", title: "接口描述", width: 135, align: 'center'
        }, {
            field: "tableType", title: "接口分类", width: 60, align: 'center'
        }, {
            field: "tableStatus", title: "接口状态", width: 60, align: 'center'
        }, {
            field: "bizClsf", title: "所属业务分类", hidden: true
        }, {
            field: "bizClsfText", title: "所属业务分类", width: 60, align: 'center'
        }, {
            field: "showDiagram", title: "血缘关系图", width: 60, align: 'center',
            formatter: function (value, row, index) {
                var queryData = {
                    tableId: row.tableId,
                    tableName: row.enName
                };
                return '<a href="javascript: void(0)"' + 'onclick=\'showTableRelationDiagram( ' + JSON.stringify(queryData) + ')\'>查看血缘关系图</a>';
            }
        }]]
    });
};
/**
 * 条件查询
 */
var doSearch=function () {
    $('#bloodRelaTabl').datagrid('load', {
        enName:$("#enNm").textbox('getValue'),
        cnName:$("#cnNm").textbox('getValue'),
        dataSorceId:$("#dataSourceTabl").combobox('getValue'),
        tableStatus:$("#tableStatus").combobox('getValue')
    });
}
// 数据接口=====================================end
var showFieldWin=function(){
    var row=$("#bloodRelaTabl").datagrid("getSelected");
    if(row==null||row==''){
        $.messager.alert("操作提示","请选择接口！");
        return false;
    }
    var tablId=row.tableId;
    var _win = $('#difFieldWin');
    _win.window({
        width: '100%',
        height: '95%',
        top:0,
        left:0,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    _win.window('open');
    $('#tablFielList').datagrid('getSelected');
    $('#tablFielList').datagrid('load', {
        tablId:tablId
    });
}
//初始化字段列表
var initFieldList=function () {
    $('#tablFielList').datagrid({
        url: baseUrl + '/mapgInfo/getFieldByTablId',
        toolbar:"#fieldQuryDiv",
        fitColumns: true,
        border: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        checkOnSelect: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        singleSelect: true,
        queryParams:{tablId:'__rttt'},
        columns:[[
            {field:'ck',checkbox:'true'},
            {field:'tablId',title:'接口ID',width:100, hidden: true},
            {field:'fieldId',title:'字段ID',width:100, hidden: true},
            {field:'fieldEnNm',title:'字段英文名',width:100},
            {field:'fieldCnNm',title:'字段中文名',width:100},
            {field:'dataTyp',title:'数据类型',width:100,align:'right'},
            {field:'dataLength',title:'数据长度',width:50,align:'right'},
            {
                field: "showDiagram", title: "血缘关系图", width: 60, align: 'center',
                formatter: function (value, row, index) {
                    var queryData = {
                        tableId: row.tablId,
                        tableName: '',
                        fieldId: row.fieldId,
                        fieldName: row.fieldEnNm
                    };
                    return '<a href="javascript: void(0)"' + 'onclick=\'showFieldRelationDiagram( ' + JSON.stringify(queryData) + ')\'>查看血缘关系图</a>';
                }
            }
        ]],
    });
}

/**
 * 字段条件查询
 */
var doSearchField=function(){
    var row=$("#bloodRelaTabl").datagrid("getSelected");
    var tablId=row.tableId;
    $('#tablFielList').datagrid('load', {
        fieldCnNm:$("#fieldCnNm").textbox('getValue'),
        fieldEnNm:$("#fieldEnNm").textbox('getValue'),
        tablId:tablId
    });
};
//字段查询结束============================
//字段关系中间结果=================start
var initFieldMidList=function () {
    $('#tablFielRelaList').datagrid({
        url: baseUrl + '/difFieldRelaMid/queryFieldRelaList',
        toolbar:"#fieldRelaDiv",
        fitColumns: true,
        border: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        checkOnSelect: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        singleSelect: true,
        queryParams:{currTablId:'__rttt',currFieldId:'__ttt'},
        columns:[[
            {field:'ck',checkbox:'true'},
            {field:'currEnNm',title:'当前接口英文名',width:100},
            {field:'currCnNm',title:'当前接口中文名',width:100},
            {field:'relaEnNm',title:'下级接口英文名',width:100},
            {field:'relaCnNm',title:'下级接口英文名',width:100},
            {field:'currFieldEnNm',title:'当前字段英文名',width:100},
            {field:'currFieldCnNm',title:'当前字段中文名',width:100},
            {field:'relaFieldEnNm',title:'下级字段英文名',width:100},
            {field:'relaFieldCnNm',title:'下级字段英文名',width:100},
            {field:'dataDt',title:'数据日期',width:100},
            {field:'crtDt',title:'创建日期',width:100,align:'right'},
            {field:'creatr',title:'创建人',width:50,align:'right'}
        ]],
    });
}

/**
 * 字段中间结果条件查询
 */
var doSearchFieldRela=function(){
    var row=$("#tablFielList").datagrid("getSelected");
    var tablId=row.tablId;
    $('#tablFielRelaList').datagrid('load', {
        fieldCnNm:$("#fieldCnNm").textbox('getValue'),
        fieldEnNm:$("#fieldEnNm").textbox('getValue'),
        tablId:tablId
    });
};
//弹出字段中间结果关系
var showFieldRelaWin=function(){
    var row=$("#tablFielList").datagrid("getSelected");
    if(row==null||row==''){
        $.messager.alert("操作提示","请选择一个字段！");
        return false;
    }
    var tablId=row.tablId;
    var fieldId=row.fieldId;
    var _win = $('#difFieldRelaWin');
    _win.window({
        width: '100%',
        height: '95%',
        top:0,
        left:0,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    $('#tablFielRelaList').datagrid('load', {
        tablId:tablId,
        fieldId:fieldId
    });
    _win.window('open');

}
//弹出数据补录新增窗口
var showAddWin=function(){
    var rowT=$("#bloodRelaTabl").datagrid("getSelected");
    var rowF=$("#tablFielList").datagrid("getSelected");
    var _win = $('#difFieldRelaAddWIn');
    _win.window({
        width: '80%',
        height: '200px',
        top:0,
        left:'10%',
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    $("#currTablNm").textbox('setValue',rowT.cnName);
    $("#currTablNm").textbox('readonly',true);
    $("#currFieldNm").textbox('setValue',rowF.fieldCnNm);
    $("#currFieldNm").textbox('readonly',true);
    crateRelaTablSelect('relaTablId','relaFieldId');
    _win.window('open');
}
var crateRelaTablSelect=function (tbId,fldId) {
    var row=$("#tablFielList").datagrid("getSelected");
    var tablId=row.tablId;
    $.ajax(baseUrl+"/difFieldRelaMid/getRelaTablList",{
        type:'post',
        async:false,
        data:{currTablId:tablId},
        success:function(result){
            if(result!=null){
                $('#'+tbId).combobox({
                    data:result,
                    valueField:'tablId',
                    textField:'cnNm',
                    onSelect: function(rec){
                        $('#'+fldId).combobox('clear');
                        var url =baseUrl+'/difFieldRelaMid/getRelaFieldList?tablId='+rec.tablId;
                        $('#'+fldId).combobox('reload', url);
                    }
                });
            }
        }
    })
}
//新增字段关系补录
var saveTabl=function () {
    var rowF=$("#tablFielList").datagrid("getSelected");
    var currTablId=rowF.tablId;
    var currFieldId=rowF.fieldId;
    var relaTablId=$("#relaTablId").combobox('getValue');
    if(!chkScpSql()){
        $.messager.alert("日期参数格式校验","校验范围日期参数输入有误！请检查，输入变量只能为：" +
            "$[BATCH_DT1]批量日期YYYYMMDD\n" +
            "$[BATCH_DT2]批量日期YYYY-MM-DD\n" +
            "$[SYS_DT1]系统日期YYYYMMDD\n" +
            "$[SYS_DT2]系统日期YYYY-MM-DD\n" +
            "$[SYS_TM]系统时间");
        return false;
    }
    if(relaTablId==''){
        $.messager.alert("空值校验","请选择下级数据接口！");
        return false;
    }
    var relaFieldId=$("#relaFieldId").combobox('getValue');
    if(relaFieldId==''){
        $.messager.alert("空值校验","请选择下级字段！");
        return false;
    }
    $.messager.confirm("新增确认","确认保存？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/difFieldRelaMid/saveTabl",{
                type:'post',
                async:true,
                data:{currTablId:currTablId,currFieldId:currFieldId,relaTablId:relaTablId,relaFieldId:relaFieldId},
                success:function(result){
                    if(result!=null){
                        $.messager.alert("新增结果提示",result.message);
                        closeWin('difFieldRelaAddWIn');
                        $('#tablFielRelaList').datagrid('reload');
                    }else{
                        $.messager.alert("新增结果提示",result.message);
                        closeWin('difFieldRelaAddWIn');
                    }
                }
            })
        }
    })
}
//删除字段
var deleteReal=function(){
    var row=$("#tablFielRelaList").datagrid("getSelected");
    if(row==''){
        messager.alert("选择提示","请选择一行数据进行删除");
        return false;
    }
    $.messager.confirm("删除确认","确认删除？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/difFieldRelaMid/deleteReal",{
                type:'post',
                async:true,
                data:{currTablId:row.currTablId,currFieldId:row.currFieldId,relaTablId:row.relaTablId,relaFieldId:row.relaFieldId},
                success:function(result){
                    if(result!=null){
                        $.messager.alert("删除结果提示",result.message);
                        $('#tablFielRelaList').datagrid('reload');
                    }else{
                        $.messager.alert("删除结果提示",result.message);
                    }
                }
            })
        }
    })
}
//弹出修改补录窗口
var showEditWin=function(){
    var row=$("#tablFielRelaList").datagrid("getSelected");
    if(row==''){
        messager.alert("选择提示","请选择一行数据进行修改");
        return false;
    }
    var _win = $('#difFieldRelaEditWIn');
    _win.window({
        width: '80%',
        height: '200px',
        top:0,
        left:'10%',
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    $("#currTablNmUpd").textbox('setValue',row.currTablNm);
    $("#currTablNmUpd").textbox('readonly',true);
    $("#currFieldNmUpd").textbox('setValue',row.currFieldNm);
    $("#currFieldNmUpd").textbox('readonly',true);
    $("#relaTablIdUpd").combobox('setValue',row.relaTablId);
    $("#relaFieldIdUpd").combobox('setValue',row.relaFieldId);
    crateRelaTablSelect('relaTablIdUpd','relaFieldIdUpd');
    _win.window('open');
}
//修改字段关系补录
var updTabl=function () {
    var row=$("#tablFielRelaList").datagrid("getSelected");
    var currTablId=row.currTablId;
    var currFieldId=row.currFieldId;
    var relaTablId=$("#relaTablIdUpd").combobox('getValue');
    if(relaTablId==''){
        $.messager.alert("空值校验","请选择下级数据接口！");
        return false;
    }
    var relaFieldId=$("#relaFieldIdUpd").combobox('getValue');
    if(relaFieldId==''){
        $.messager.alert("空值校验","请选择下级字段！");
        return false;
    }
    $.messager.confirm("修改确认","确认保存？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/difFieldRelaMid/updTabl",{
                type:'post',
                async:true,
                data:{currTablId:row.currTablId,currFieldId:row.currFieldId,relaTablId:relaTablId,
                    relaFieldId:relaFieldId,dataSrc:row.dataSrc,dataDt:row.dataDt,crtDt:row.crtDt,creatr:row.creatr},
                success:function(result){
                    if(result!=null){
                        $.messager.alert("修改结果提示",result.message);
                        closeWin('difFieldRelaEditWIn');
                        $('#tablFielRelaList').datagrid('reload');
                    }else{
                        $.messager.alert("修改结果提示",result.message);
                        closeWin('difFieldRelaEditWIn');
                    }
                }
            })
        }
    })
}
var chkScpSql=function () {
    var chkScpSql=$("#chkScpSql").textbox("getValue");
    var dateParm=chkScpSql.split("$")[1];
    var paramT=$.trim(dateParm);
    if(paramT!='[BATCH_DT1]'&&paramT!='[BATCH_DT2]'&&paramT!='[SYS_DT1]'&&paramT!='[SYS_DT2]'&&paramT!='[SYS_TM]'){
            return false;
    }else{
        return true;
    }
}

