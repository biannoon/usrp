
$(function(){
    $('#bussType').tree({
        url:'/usrp/difConf/getSysDictryCdListByLevel1'
    });
    $('#bussType').tree({onClick:function(node){
        $("#bizClsf").val(node.id);
        $('#tablChkConf').datagrid('load', {
            bizClsf: node.id
        });

    }});
    initDatabase('#dataSourceTabl',true);
    initDictionaryCode('#chkModlQry', 'DFI19', true);
    initDictionaryCode('#chkModl', 'DFI19', false);
    initTablChkConf();
    chkModlSelect();
    initTabl();
    $("#chkModl").combobox({
        onChange:function (newValue, oldValue) {
            var chkModelTmp=$("#chkModlTmp").val();
            var row=$("#tablChkConf").datagrid('getSelected');
            $.ajax(baseUrl+"/difTabChkConf/getChkRuleConfByConfId",{
                type:'post',
                async:true,
                data:{confId:row.confId },
                success:function(result){
                    if(result.length>0&&chkModelTmp=='DFI1902'&&newValue!='DFI1902'){
                        $("#chkModl").combobox('setValue','DFI1902');
                        $.messager.alert("修改校验","该配置下已经配置了校验规则，不能修改校验模式！");
                        return false;
                    }
                }
            })
        }
    })
})
var initTablChkConf=function(){
    $('#tablChkConf').datagrid({
        url:baseUrl+'/difTabChkConf/queryDifTabChkConf',
        fitColumns: true,
        border: true,
        pagination: true,
        idField: "confId",
        pageSize: 10,
        pageList: [10, 20, 30],
        singleSelect: true,
        checkOnSelect: true,
        selectOnCheck: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        queryParams:{
            //初始化查询接口列表为空
            bizClsf: '   _'
        },
        //固定列字段 不受横向滚动条拉动影响
        //frozenColumns : [ [ ] ],
        columns: [[{
                field:'ck',checkbox:true
        },{
            field: "tablId", title: "接口ID", width: 50
        }, {
            field: "cnName", title: "中文名称", width: 100, align: 'center'
        }, {
            field: "enName", title: "英文名称", width: 100, align: 'center'
        },  {
            field: "dataSource", title: "所属数据源", width: 50, align: 'center'
        }, {
            field: "chkConfNm", title: "配置名称", width: 135, align: 'center'
        }, {
            field: "chkModl", title: "校验模式", width: 60, align: 'center',dictry:'DFI19',formatter: formatData
        }, {
            field: "creatr", title: "创建人", width: 60, align: 'center'
        }, {
            field: "crtDt", title: "创建日期", hidden: true
        }, {
            field: "finlModifr", title: "最后修改人", width: 60, align: 'center'
        },{
            field: "finlModfyDt", title: "最后修改日期", width: 60, align: 'center'
        }]]
    });
};
var doChkConfSearch=function () {
    $('#tablChkConf').datagrid('load', {
        bizClsf:$("#bizClsf").val(),
        enName:$("#enNm").textbox('getValue'),
        cnName:$("#cnNm").textbox('getValue'),
        dataSorceId:$("#dataSourceTabl").combobox('getValue'),
        chkModl:$("#chkModlQry").combobox('getValue'),
        chkConfNm:$("#chkConfNmQry").textbox('getValue')
    });
}
// 弹出数据接口校验配置
var openTablChkConf=function(){
    var bizClsf=$("#bizClsf").val();
    if(bizClsf==''){
        $.messager.alert("树节点选择","请选择一个业务类型");
        return false;
    }
    var _win = $('#tablChkConfWin');
    _win.window({
        width: '100%',
        height: '450px',
        top:0,
        left:0,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    $("#tablNm").textbox('setValue','');
    $("#chkConfNm").textbox('setValue','');
    $("#chkModl").combobox("setValue",'');
    $("#relSqlExps").textbox("setValue",'');
    $("#chkScpSql").textbox("setValue",'');
    $("#chkConfComnt").textbox("setValue",'');
    $("#confAdd").show();
    $("#confUpd").hide();
    _win.window('open');
}
var chkModlSelect=function(){
    $("#chkModl").combobox({
        onSelect:function (record) {
            if(record.value=='DFI1901'){
                $("#relSqlExps").textbox({
                    required:false,
                    readonly:true
                })
            }else{
                $("#relSqlExps").textbox({
                    required:true,
                    readonly:false
                })
            }
        }
    })
}
// 数据接口=====================================start
/**
 * 初始化目标接口列表
 * @param node
 */
var initTabl=function(){
    $('#difTablList').datagrid({
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
        queryParams:{
            //初始化查询接口列表为空
            bizClsf: 'ttttttt___'
        },
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
        }]]
    });
};
/**
 * 条件查询
 */
var doSearchTabl=function () {
    $('#difTablList').datagrid('load', {
        bizClsf:$("#bizClsf").val(),
        enName:$("#enName").textbox('getValue'),
        cnName:$("#cnName").textbox('getValue'),
        dataSorceId:$("#dataSource").combobox('getValue')
    });
}
//弹出数据接口选择列列表
var openTablWin=function(){
    var _win = $('#tablWin');
    _win.window({
        width: '100%',
        height: '100%',
        top:0,
        left:0,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    initDatabase('#dataSource',true);
    $('#difTablList').datagrid('load', {
        bizClsf:$("#bizClsf").val()
    });
    _win.window('open');
}
//确认选择数据接口
var confirmTabl=function () {
    var row=$("#difTablList").datagrid('getSelected');
    if(row==null){
        $.messager.alert("是否选择接口验证","请选择一个接口");
        return false;
    }
    $("#tablNm").textbox('setValue',row.cnName+'_'+row.enName);
    $("#tablId").val(row.tablId);
    closeWin('tablWin');
}
//新增数据接口校验配置
var saveTablChkConf=function () {
    var row=$("#difTablList").datagrid('getSelected');
        $.messager.confirm("新增确认","确认保存？",function(flag){
            if(flag){
                $.ajax(baseUrl+"/difTabChkConf/saveTablChkConf",{
                    type:'post',
                    async:true,
                    data:{tablId:row.tableId,chkConfNm:$("#chkConfNm").textbox('getValue'),chkConfComnt:$("#chkConfComnt").textbox('getValue'),
                        chkModl:$("#chkModl").combobox('getValue'),relSqlExps:$("#relSqlExps").textbox('getValue'),chkScpSql:$("#chkScpSql").textbox('getValue')},
                    success:function(result){
                        if(result!=null){
                            $.messager.alert("新增结果提示",result.message);
                            closeWin('tablChkConfWin');
                            $('#tablChkConf').datagrid('reload');
                        }else{
                            $.messager.alert("新增结果提示",result.message);
                            closeWin('tablChkConfWin');
                        }
                    }
                })
            }
        })
}
// 弹出数据接口校验配置修改
var openTablChkConfUpd=function(){
    var row=$("#tablChkConf").datagrid('getSelected');
    if(row==null||row==''){
        $.messager.alert("修改数据选择验证","请选择一行数据进行修改！")
        return false;
    }
    var _win = $('#tablChkConfWin');
    _win.window({
        width: '100%',
        height: '450px',
        top:0,
        left:0,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    $("#tablNm").textbox('setValue',row.cnName+'_'+row.enName);
    $("#chkConfNm").textbox('setValue',row.chkConfNm);
    $("#chkModl").combobox("setValue",row.chkModl);
    $("#relSqlExps").textbox("setValue",row.relSqlExps);
    $("#chkScpSql").textbox("setValue",row.chkScpSql);
    $("#chkConfComnt").textbox("setValue",row.chkConfComnt);
    $("#chkModlTmp").val(row.chkModl);
    if(row.chkModl=='DFI1901'){
        $("#relSqlExps").textbox({
            required:false,
            readonly:true
        })
    }else{
        $("#relSqlExps").textbox({
            required:true,
            readonly:false
        })
    }
    $("#confAdd").hide();
    $("#confUpd").show();
    _win.window('open');
}
//修改数据接口校验配置
var updTablChkConf=function () {
    var row=$("#tablChkConf").datagrid('getSelected');
    $.messager.confirm("修改确认","确认保存？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/difTabChkConf/updTablChkConf",{
                type:'post',
                async:true,
                data:{confId:row.confId,tablId:row.tableId,chkConfNm:$("#chkConfNm").textbox('getValue'),chkConfComnt:$("#chkConfComnt").textbox('getValue'),
                    chkModl:$("#chkModl").combobox('getValue'),relSqlExps:$("#relSqlExps").textbox('getValue'),chkScpSql:$("#chkScpSql").textbox('getValue'),
                    creatr:row.creatr,crtDt:row.crtDt
                },
                success:function(result){
                    if(result!=null){
                        $.messager.alert("修改结果提示",result.message);
                        closeWin('tablChkConfWin');
                        $('#tablChkConf').datagrid('reload');
                    }else{
                        $.messager.alert("修改结果提示",result.message);
                        closeWin('tablChkConfWin');
                    }
                }
            })
        }
    })
}
//删除数据接口校验配置
var delTablChkConf=function () {
    var row=$("#tablChkConf").datagrid('getSelected');
    if(row==''||row==null){
        $.messager.alert("选择提示","请选择一条数据进行删除");
        return false;
    }
    $.ajax(baseUrl+"/difTabChkConf/getChkRuleConfByConfId",{
        type:'post',
        async:true,
        data:{confId:row.confId },
        success:function(result){
            if(result.length>0){
                $.messager.alert("删除校验","该配置下已经配置了校验规则，不能删除！");
                return false;
            }else{
                $.messager.confirm("删除确认","确认删除？",function(flag){
                    if(flag){
                        $.ajax(baseUrl+"/difTabChkConf/delTablChkConf",{
                            type:'post',
                            async:true,
                            data:{confId:row.confId },
                            success:function(result){
                                if(result!=null){
                                    $.messager.alert("删除结果提示",result.message);
                                    $('#tablChkConf').datagrid('reload');
                                }else{
                                    $.messager.alert("删除结果提示",result.message);
                                }
                            }
                        })
                    }
                })
            }
        }
    })

}
var openChkRuleWin=function () {
    var row=$("#tablChkConf").datagrid('getSelected');
    if(row==''||row==null){
        $.messager.alert("选择提示","请选择一个配置！");
        return false;
    }
    var _win = $('#difChkRuleWin');
    _win.window({
        width: '100%',
        height: '99%',
        top:0,
        left:0,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    $('#difChkRuleConfList').datagrid('load', {
        confId:row.confId
    });
    //初始化校验字段
    initRuleFieldSelect("fieldEnNmConf");
    $("#currChkConfNm").textbox('setValue',row.chkConfNm);
    _win.window('open');
}


