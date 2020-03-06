
$(function(){
    $('#bussType').tree({
        url:'/usrp/difConf/getSysDictryCdListByLevel1'
    });
    $('#bussType').tree({onClick:function(node){
        $("#bizClsf").val(node.id);
        initDifTablMapgList(node);
        initDifTablTrgtList();
    }});
    initDifTablMapgList("");
    difSorcGrpgOnSelect();
    fieldListOnSelect();
    trgtablField();
    $('#rpgCond').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 8||e.keyCode == 46) {
           return false;
        }
    });
})
/**
 * 初始化目标接口列表
 * @param node
 */
var initDifTablMapgList=function(node){
    $('#difTablMapgList').datagrid({
        url:baseUrl+'/mapgInfo/difTablMapgList?bizClsf='+node.id,
        toolbar:"#btnDiv",
        pagination:true,
        singleSelect:true,
        // checkOnSelect:false,
        columns:[[
            {field:'ck',checkbox:'true'},
            {field:'enNm',title:'接口英文名',width:100},
            {field:'cnNm',title:'接口中文名',width:100},
            {field:'stus',title:'映射配置状态',width:100,align:'right',dictry:'DFI02',formatter: formatData},
            {field:'grpgCond',title:'分组汇总条件',width:100,align:'right'},
            {field:'mapgComnt',title:'映射说明',width:100,align:'right'},
            {field:'mapgTyp',title:'映射类型',width:100,align:'right',dictry:'DFI07',formatter: formatData},
            {field:'creatr',title:'创建人',width:100,align:'right'},
            {field:'crtDt',title:'创建日期',width:100,align:'right'},
            {field:'finlModifr',title:'最后修改人',width:100,align:'right'},
            {field:'finlModfyDt',title:'最后修改日期',width:100,align:'right'}
        ]]
    });
};
var initDifTablTrgtList=function(){
    var bizClsf= $("#bizClsf").val();
    $('#trgtablList').datagrid({
        url:baseUrl+'/mapgInfo/queryTrgtList',
        toolbar:"#trgtSelectDiv",
        pagination:true,
        singleSelect:true,
        queryParams:{
            //初始化查询接口列表为空
            bizClsf:bizClsf,
            tableStatus:'DFI0202',
            tableType:'DFI0102'
        },
        // checkOnSelect:false,
        columns:[[
            {field:'ck',checkbox:'true'},
            {field:'cnName',title:'接口中文名',width:200,align:'center'},
            {field:'enName',title:'接口英文名',width:200,align:'center'},
            {field:'tableType',title:'接口分类',width:180,align:'center'},
            {field:'tableStatus',title:'接口状态',width:180,align:'center'},
            {
                field: "bizClsfText", title: "所属业务分类", width: 200, align: 'center'
            },
            {
                field: "dataSorceId", title: "数据源ID", width: 200, align: 'center'
            }
        ]]
    });
};
var doSearchTargt=function(){
    var bizClsf= $("#bizClsf").val();
    $('#trgtablList').datagrid('load', {
        cnName:$("#cnName").textbox('getValue'),
        enName:$("#enName").textbox('getValue'),
        bizClsf:bizClsf,
        tableStatus:'DFI0202',
        tableType:'DFI0102'

    });
}
/**
 * 条件查询
 */
var doSearch=function () {
    $('#difTablMapgList').datagrid('load', {
        cnNm:$("#cnNm").val(),
        enNm:$("#enNm").val(),
        stus:$("#stus").combobox('getValue'),
        mapgTyp:$("#mapgTyp").combobox('getValue')

    });
}
/**
 * 新增目标接口弹出窗
 */
var openAddWin = function() {
    var bizClsf= $("#bizClsf").val();
    if(bizClsf==''||bizClsf==undefined){
        $.messager.alert("选择业务分类","请选择业务分类！");
        return false;
    }
    // crTrgtTablIdComBo();
        var _win = $('#difTablMapgAdd');
    _win.window({
        width: 800,
        height: 250,
        left:200,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true,
        onBeforeClose: function() {
            $('#difTablMapgForm').form('clear');
        }
    });
    _win.window('open');
}
var openQueryEditWin=function(){
    var rows= $('#difTablMapgList').datagrid('getChecked');
    if(rows.length<1){
        $.messager.alert("选中校验","请选中一行！");
        return false;
    }
    $("#enNmqury").textbox('setValue',rows[0].enNm);
    $("#cnNmqury").textbox('setValue',rows[0].cnNm);
    $("#mapgDlwthManrQury").combobox('setValue',rows[0].mapgDlwthManr);
    $("#mapgTypQury").combobox('setValue',rows[0].mapgTyp);
    $("#rpgCondQury").textbox('setValue',rows[0].grpgCond);
    $("#mapgComntQury").textbox('setValue',rows[0].mapgComnt);
    $("#enNmqury").textbox('readonly',true);
    $("#cnNmqury").textbox('readonly',true);
    $("#rpgCondQury").textbox('readonly',true);
    if(rows[0].mapgTyp=='DFI0701'){
        // $(".rpgCondQury").attr("style","visibility:hidden");
        $(".rpgCondQury").attr("style","visibility:hidden");
    }else{
        $(".rpgCondQury").attr("style","visibility:visible");
    }
    $('#enNmqury').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 8||e.keyCode == 46) {
            return false;
        }
    });
    $('#rpgCondQury').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 8||e.keyCode == 46) {
            return false;
        }
    });
    $('#cnNmqury').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 8||e.keyCode == 46) {
            return false;
        }
    });
    var _win = $('#difTablMapgQury');
    _win.window({
        width: 800,
        height: 350,
        left:200,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    _win.window('open');
};
var updDifTablMapg=function(){
    var rows= $('#difTablMapgList').datagrid('getChecked');
    var mapgTyp=$("#mapgTypQury").combobox("getValue");
    if(mapgTyp==''){
        $.messager.alert("空值校验","映射类型不能为空");
        return false;
    }
    var mapgDlwthManr=$("#mapgDlwthManrQury").combobox("getValue");
    if(mapgDlwthManr==''){
        $.messager.alert("空值校验","映射处理方式不能为空");
        return false;
    }
    var mapgComnt=$("#mapgComntQury").textbox('getValue');
    if(mapgComnt==''){
        $.messager.alert("空值校验","映射说明不能为空！");
        return false;
    }
    if(mapgTyp==rows[0].mapgTyp&&rows[0].mapgDlwthManr==mapgDlwthManr&&rows[0].mapgComnt==mapgComnt){
        $.messager.alert("修改校验","没有需要修改数据项！");
        return false;
    }
    $.messager.confirm("操作提示","确认修改？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/mapgInfo/updDifTablMapg",{
                type:'post',
                async:true,
                data:{mapgId:rows[0].mapgId,mapgTyp:mapgTyp,mapgDlwthManr:mapgDlwthManr,mapgComnt:mapgComnt},
                success:function(result){
                    if(result!=null){
                        $.messager.alert("操作提示",result.message);
                        $('#difTablMapgList').datagrid('reload');
                        $("#difTablMapgQury").window("close");
                    }
                }
            })
        }
    })
}
var disableKey=function(id){
    $(id).textbox('textbox').keydown(function (e) {
        if (e.keyCode == 8||e.keyCode == 46) {
            return false;
        }
    });
}
/**
 * 目标接口选择区域
 * @returns {boolean}
 */
var openTrgtWin = function() {
    var _win = $('#targtWin');
    _win.window({
        width: '100%',
        height: '600',
        top:0,
        left:0,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    _win.window('open');
};
/**
 * 分组汇总条件区域
 */
var openGrpgCondWin = function() {
    var rows=$("#difTablMapgList").datagrid("getChecked");
    if(rows.length<1){
        $.messager.alert("选择提示","请选中一行");
        return false;
    }
    $('#trgtablFielList').datagrid('reload', {
        tablId:'-----TTT'
    });
    $('#trgtablFielList').datagrid('clearChecked');
    $("#rpgCond").textbox('setValue',rows[0].grpgCond);
    $("#trgtTablIdQury").textbox('setValue',rows[0].enNm+'@'+rows[0].cnNm);
    difSorcGrpgInit();
    var _win = $('#grpgCondWin');
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
    $.ajax(baseUrl+"/mapgInfo/befoRpgCondChk",{
        type:'post',
        async:false,
        data:{mapgId:rows[0].mapgId},
        success:function(result){
            if(result.success){
                _win.window('open');
            }else{
                $.messager.alert("配置校验",result.message);
                return false;
            }

        }
    })

};
var trgtablField=function(){
    $('#trgtablFielList').datagrid({
        url:baseUrl+'/mapgInfo/getFieldByTablId',
        toolbar:"#fieldSelectDiv",
        fitColumns: true,
        border: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        checkOnSelect: false,
        remoteSort: false,
        striped: true,
        nowrap: false,
        queryParams:{tablId:'__rttt'},
        columns:[[
            {field:'ck',checkbox:'true'},
            {field:'fieldEnNm',title:'字段英文名',width:100},
            {field:'fieldCnNm',title:'字段中文名',width:100},
            {field:'dataTyp',title:'数据类型',width:100,align:'right'},
            {field:'dataLength',title:'数据长度',width:100,align:'right'}
        ]],
    });
};

/**
 * 查询来源接口列表，条件：当前勾选行对应的映射ID
 */
var getDifSorcSelect=function () {
    var rows=$("#difTablMapgList").datagrid("getChecked");
    var data=null;
    $.ajax(baseUrl+"/mapgInfo/getDifSorcSelect",{
        type:'post',
        async:false,
        data:{mapgId:rows[0].mapgId},
        success:function(result){
            if(result!=null){
                data=result;
            }
        }
    })
    return data;
}
/**
 * 初始化来源
 */
var difSorcGrpgInit=function(){
    var sorcSelect=getDifSorcSelect();
    $('#difSorcGrpg').combobox({
        data:sorcSelect,
        valueField:'tablId',
        textField:'tablText',
        width:"400px;"
    });
}
/**
 * 选中来源接口下拉，刷新分组汇总字段列表
 */
var difSorcGrpgOnSelect=function(){
    $('#difSorcGrpg').combobox({
        onSelect:function(item){
            $('#trgtablFielList').datagrid('reload', {
                tablId:item.tablId.split("_")[0]
            });
            $('#trgtablFielList').datagrid({
                onLoadSuccess:function(data){
                    var rpgConds=$("#rpgCond").textbox('getValue');
                    var sortNo=item.tablId.split("_")[1];
                    var tablAlis='t'+sortNo+".";
                    var rpgCondArr=rpgConds.split(',');
                    for(var i in rpgCondArr){
                        var rows=data.rows;
                        for(var j in rows){
                            if((tablAlis+rows[j].fieldEnNm)==rpgCondArr[i]){
                                $('#trgtablFielList').datagrid('checkRow', $('#trgtablFielList').datagrid('getRowIndex',rows[j]));
                            }
                        }
                    }
                    //已配置字段映射的分组汇总条件
                    var res=getFieldMapgConf(item.tablId.split("_")[2]);
                    if(res.length>0&&rpgConds==''){
                        for(var k in res){
                            var rows=data.rows;
                            for(var n in rows){
                                if((rows[n].fieldEnNm)==res[k].sorcFieldEnNm){
                                    $('#trgtablFielList').datagrid('checkRow', $('#trgtablFielList').datagrid('getRowIndex',rows[n]));
                                }
                            }
                        }
                    }
                    //已配置的分组汇总条件
                    var rpgConded=getRpgConded();
                    if(rpgConded!=null&&rpgConded.grpgCond!=null){
                        var fields=rpgConded.grpgCond.split(",");
                        for(var m in fields){
                            var rows=data.rows;
                            for(var q in rows){
                                if((tablAlis+rows[q].fieldEnNm)==fields[m]){
                                    $('#trgtablFielList').datagrid('checkRow', $('#trgtablFielList').datagrid('getRowIndex',rows[q]));
                                }
                            }
                        }
                    }
                }
            })
        }
    })
};
/**
 * 选中和取消选中字段列表同步添加和删除汇总条件
 */
var fieldListOnSelect=function(){
    $("#trgtablFielList").datagrid({
        onCheck:function(rowIndex, rowData){
            var rpgConds=$("#rpgCond").textbox('getValue');
            var sortNo=$("#difSorcGrpg").combobox('getValue').split("_")[1];
            var tablAlis='t'+sortNo+".";
            var rpgCondArr=new Array();
            //勾选对选框时给分组汇总条件赋值
            if(rpgConds!=''){
                rpgCondArr=rpgConds.split(',');
            }
            if(rpgConds.indexOf(tablAlis+rowData.fieldEnNm)<0){
                rpgCondArr.push(tablAlis+rowData.fieldEnNm);
            }
            $("#rpgCond").textbox('setValue',rpgCondArr);
        },
        onUncheck:function(rowIndex, rowData){
            var rpgConds=$("#rpgCond").textbox('getValue');
            var res=getMapgConfFields();
            var rpgCondArr=rpgConds.split(',');
            var sortNo=$("#difSorcGrpg").combobox('getValue').split("_")[1];
            var tablAlis='t'+sortNo+".";
            for(var i in rpgCondArr){
                if(rpgCondArr[i]==(tablAlis+rowData.fieldEnNm)){
                    if(res.indexOf(rpgCondArr[i])!=-1){
                        $('#trgtablFielList').datagrid('checkRow', rowIndex);
                        $.messager.alert("删除提示","已经映射的字段必须作为分组汇总条件！");
                        return false;
                    }else{
                        rpgCondArr.splice($.inArray(tablAlis+rowData.fieldEnNm,rpgCondArr),1);
                    }

                }
            }
            $("#rpgCond").textbox('setValue',rpgCondArr);
        },
        onCheckAll:function(rows){
         var rpgConds=$("#rpgCond").textbox('getValue');
            var sortNo=$("#difSorcGrpg").combobox('getValue').split("_")[1];
            var tablAlis='t'+sortNo+".";
            var rpgCondArr=new Array();
            if(rpgConds!=''){
                rpgCondArr=rpgConds.split(',');
            }
        if(rows.length>0){
            for(var i in rows){
                if(rpgConds.indexOf(tablAlis+rows[i].fieldEnNm)<0){
                    rpgCondArr.push(tablAlis+rows[i].fieldEnNm);
                }
            }
            $("#rpgCond").textbox('setValue',rpgCondArr);
        }
        },
        onUncheckAll:function(rows){
         var rpgConds=$("#rpgCond").textbox('getValue');
            var rpgCondArr=rpgConds.split(',');
            var res=getMapgConfFields();
            var sortNo=$("#difSorcGrpg").combobox('getValue').split("_")[1];
            var tablAlis='t'+sortNo+".";
            for(var j in rows){
                if(rpgConds.indexOf(tablAlis+rows[j].fieldEnNm)!=-1){
                    if(res.indexOf(rows[j].fieldEnNm)!=-1){
                        return false;
                    }else{
                        rpgCondArr.splice($.inArray(tablAlis+rows[j].fieldEnNm,rpgCondArr),1);
                    }

                }
            }
            //取消勾选时取消来源表展示
            $("#rpgCond").textbox('setValue',rpgCondArr);
        }
    })
}
/**
 * 查询已配置的字段映射，并拼接出对应的别名
 * @returns {Array}
 */
var getMapgConfFields=function(){
    var sorcId=$("#difSorcGrpg").combobox('getValue').split("_")[2];
    var sortNo=$("#difSorcGrpg").combobox('getValue').split("_")[1];
    var tablAlis='t'+sortNo+".";
    var res=getFieldMapgConf(sorcId);
    var resArr=new Array();
    for(var k in res){
        resArr.push(tablAlis+res[k].sorcFieldEnNm)
    }
    return resArr;
}
/**
 * 字段条件查询，使用场景：分组汇总条件选择字段列表
 */
var doSearchField=function(){
    var tablId=$("#difSorcGrpg").combobox('getValue').split("_")[0];

    $('#trgtablFielList').datagrid('load', {
        fieldCnNm:$("#fieldCnNm").textbox('getValue'),
        fieldEnNm:$("#fieldEnNm").textbox('getValue'),
        tablId:tablId
    });
};
/**
 * 配置分组汇总条件
 */
var saveRpgCond=function(){
    var rows=$("#difTablMapgList").datagrid("getChecked");
    var grpgCond=$("#rpgCond").textbox("getValue");
    if(grpgCond==''){
        $.messager.alert("空值校验","请配置分组汇总条件");
        return false;
    }
    var difSorcGrpg=$("#difSorcGrpg").combobox('getValue');
    if(difSorcGrpg==''){
        $.messager.alert("","没有可以保存的数据或者数据没有被修改");
        return false;
    }
    $.messager.confirm("操作确认","您确认配置？",function (r) {
        if(r){
            $.ajax(baseUrl+"/mapgInfo/saveRpgCond",{
                type:'post',
                async:true,
                data:{mapgId:rows[0].mapgId,grpgCond:grpgCond},
                success:function(result){
                    $.messager.alert("操作提示",result.message);
                    if(result.success){
                        closeWin('grpgCondWin');
                        $('#difTablMapgList').datagrid('load');
                    }
                }
            })
        }
    })
}
/**
 * 查询已配置的字段映射
 * @param tablId
 */
var getFieldMapgConf=function (sorcId) {
    var resData;
    var rows=$("#difTablMapgList").datagrid("getChecked");
    $.ajax(baseUrl+"/mapgInfo/getFieldMapgConf",{
        type:'post',
        async:false,
        data:{mapgId:rows[0].mapgId,sorcTablConfId:sorcId},
        success:function(result){
            resData=result;
        }
    })
    return resData;
}
/**
 * 查询已配置的分组汇总条件
 * @param tablId
 */
var getRpgConded=function () {
    var resData;
    var rows=$("#difTablMapgList").datagrid("getChecked");
    $.ajax(baseUrl+"/mapgInfo/getRpgConded",{
        type:'post',
        async:false,
        data:{mapgId:rows[0].mapgId},
        success:function(result){
            resData=result;
        }
    })
    return resData;
}
/**
 * 确定选择
 */
var confirmSelect=function(){
    var objCheck=$("#trgtablList").datagrid("getChecked");
    if(objCheck.length<1){
        $.messager.alert("选择校验","请选择目标数据接口！");
        return false;
    }
    $("#trgtTablText").textbox('setValue',objCheck[0].enName+'@'+objCheck[0].cnName);
    $("#trgtTablId").val(objCheck[0].tableId);
    $("#targtWin").window("close");
}
var closeWindow=function(id){
$("#"+id).window("close");
};
/**
 * 创建目标接口下拉
 */
var crTrgtTablIdComBo=function(){
    var bizClsf=$("#bizClsf").val();
    $.ajax(baseUrl+"/mapgInfo/getTrgtTablIdComBo",{
        type:'post',
        async:true,
        data:{bizClsf:bizClsf,tableType:'DFI0101'},
        success:function(result){
            if(result!=null){
                $('#trgtTablId').combobox({
                    data:result,
                    valueField:'tableId',
                    textField:'enName',
                    width:"200px;"
                });
            }
        }
    })
};
/**
 * 新增目标接口
 */
var saveDifTablMapg=function(){
    var trgtTablId=$("#trgtTablId").val();
    if(trgtTablId==''){
        $.messager.alert("空值校验","目标接口不能为空");
        return false;
    }
    var mapgTyp=$("#mapgTyp").combobox('getValue');
    if(mapgTyp==''){
        $.messager.alert("空值校验","映射类型不能为空");
        return false;
    }
    var mapgDlwthManr=$("#mapgDlwthManr").combobox("getValue");
    if(mapgDlwthManr==''){
        $.messager.alert("空值校验","映射处理方式不能为空");
        return false;
    }
    var mapgComnt=$("#mapgComnt").textbox('getValue');
    if(mapgComnt==''){
        $.messager.alert("空值校验","映射说明");
        return false;
    }
    $.messager.confirm("操作提示","确认新增目标接口？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/mapgInfo/saveDifTablMapg",{
                type:'post',
                async:true,
                data:{trgtTablId:trgtTablId.split('@')[0],mapgTyp:mapgTyp,grpgCond:'',mapgDlwthManr:mapgDlwthManr,mapgComnt:mapgComnt},
                success:function(result){
                    if(result!=null){
                        $.messager.alert("新增结果提示",result.message);
                        $("#difTablMapgAdd").window("close");
                        $('#difTablMapgList').datagrid('reload');
                    }
                }
            })
        }
    })
};
/**
 * 数据接口发布
 * @returns {boolean}
 */
var doChkSql=function(){
    var row = $('#difTablMapgList').datagrid('getSelected');
    if(row==null){
        $.messager.alert("","请选中一行");
        return false;
    }
    if(row.stus=='DFI0202'){
        $.messager.alert("发布校验","接口配置已发布");
        return false;
    }

    if(row.stus!='DFI0201'&&row.stus!='DFI0203'){
        $.messager.alert("发布校验","只能对处于编制中和撤销发布发布状态的配置进行发布操作！");
        return false;
    }
    $.ajax(baseUrl+"/mapgInfo/checkDifDoPublish",{
        type:'post',
        async:true,
        data:{mapgId:row.mapgId,grpgCond:row.grpgCond,mapgTyp:row.mapgTyp},
        success:function(result){
            if(result.success){
                getDifConfSql();
            }else{
                $.messager.alert("发布校验",result.message);
                return false;
            }
        }
    })

};
var getDifConfSql=function(){
    var row = $('#difTablMapgList').datagrid('getSelected');
    $("#pushBtnDiv").hide();
    $.ajax(baseUrl+"/mapgInfo/getDifConfSql",{
        type:'post',
        async:true,
        data:{mapgId:row.mapgId,mapgTyp:row.mapgTyp},
        success:function(result){
            if(result!=null){
                $("#chkSqlTablTrgt").textbox('setValue',row.enNm);
                $("#chkSqlTablTrgt").textbox("readonly",true);
                $("#fieldCondStrs").textbox('setValue',result.fieldCondStrs);
                $("#fieldCondStrs").textbox("readonly",true);
                $("#relaCondStrs").textbox('setValue',result.relaCondStrs);
                $("#relaCondStrs").textbox("readonly",true);
                $("#sorcTablStrs").textbox('setValue',result.sorcTablStrs);
                $("#sorcTablStrs").textbox("readonly",true);
                $("#quryCondStrs").textbox('setValue',result.quryCondStrs);
                $("#quryCondStrs").textbox("readonly",true);
                $("#chkSql").textbox('setValue',result.chkSql);
                $("#chkSql").textbox("readonly",true);
                var _win = $('#chkSqlWin');
                _win.window({
                    width: '99%',
                    height: '80%',
                    left:'10px',
                    top:0,
                    collapsible: false,
                    maximizable: false,
                    minimizable: false,
                    resizable: false,
                    modal: true
                });
                _win.window('open');
            }
        }
    });
}
/**
 * 发布
 */
var doPublish=function(){
    var row = $('#difTablMapgList').datagrid('getSelected');
        $.messager.confirm("操作提示","确认发布？",function(flag){
            if(flag){
                $.ajax(baseUrl+"/mapgInfo/updateStatus",{
                    type:'post',
                    async:true,
                    data:{mapgId:row.mapgId,stus:'DFI0202'},
                    success:function(result){
                        if(result){
                            $('#difTablMapgList').datagrid('reload');
                            $.messager.alert("发布提示","发布成功");
                            closeWin('chkSqlWin')
                        }else{
                            $.messager.alert("发布提示","发布失败");
                        }
                    }
                })
            }
        })

}
/**
 * 撤销发布
 * @returns {boolean}
 */
var unPublish=function(){
    var row = $('#difTablMapgList').datagrid('getSelected');
    if(row==null){
        $.messager.alert("","请选中一行");
        return false;
    }
    if(row.stus!='DFI0202'){
        $.messager.alert("撤销发布校验","该接口不处于发布状态，不能撤销发布");
        return false;
    }
   $.messager.confirm("操作提示","您确认撤销发布？",function(flag){
    if(flag){
        $.ajax(baseUrl+"/mapgInfo/updateStatus",{
            type:'post',
            async:true,
            data:{mapgId:row.mapgId,stus:'DFI0203'},
            success:function(result){
                if(result){
                    $('#difTablMapgList').datagrid('reload');
                    $.messager.alert("撤销发布提示","撤销发布成功");
                }else{
                    $.messager.alert("撤销发布提示","撤销发布失败");
                }
            }
        })
    }
   })
};
/**
 * 删除
 * @returns {boolean}
 */
var deleteConf=function(){
    var row = $('#difTablMapgList').datagrid('getSelected');
    if(row==null){
        $.messager.alert("","请选中一行");
        return false;
    }
    if(row.stus!='DFI0201'&&row.stus!='DFI0203'){
        $.messager.alert("删除提示","当前状态不允许删除！")
        return false;
    }
    $.ajax(baseUrl+"/mapgInfo/isChkConfSorc",{
        type:'post',
        async:true,
        data:{mapgId:row.mapgId},
        success:function(result){
                if(result){
                    $.messager.alert("删除校验","该映射配置存在已经配置的来源接口信息，请先删除该配置下的来源接口！");
                    return false;
                }else{
                    if(row.stus=="DFI0201"){
                        $.messager.confirm("删除确认","您确认删除？",function(flag){
                            if(flag){
                                    $.ajax(baseUrl+"/mapgInfo/difConfDelete",{
                                        type:'post',
                                        async:true,
                                        data:{mapgId:row.mapgId},
                                        success:function(result){
                                            $.messager.alert("删除提示",result.message);
                                            if(result.success){
                                                $('#difTablMapgList').datagrid('reload');
                                            }
                                        }
                                    })
                            }
                        })
                    }else{
                        $.ajax(baseUrl+"/mapgInfo/updateStatus",{
                            type:'post',
                            async:true,
                            data:{mapgId:row.mapgId,stus:'DFI0204'},
                            success:function(result){
                                if(result){
                                    $('#difTablMapgList').datagrid('reload');
                                    $.messager.alert("","删除成功");
                                }else{
                                    $.messager.alert("","删除失败");
                                }
                            }
                        })
                    }
                }
        }
    })
};
/**
 * 校验配置SQL
 */
var chkSql=function(){
    var row = $('#difTablMapgList').datagrid('getSelected');
    $.ajax(baseUrl+"/mapgInfo/chkSql",{
        type:'post',
        async:true,
        data:{chkSql:$("#chkSql").textbox('getValue'),mapgId:row.mapgId},
        success:function(result){
                $.messager.alert("校验提示",result.message)
            if(result.success){
                $("#pushBtnDiv").show();
            }

        }
    })
}
/**
 * 跳转配置页面
 * @returns {boolean}
 */
var toDifConf=function(){
    var row = $('#difTablMapgList').datagrid('getSelected');
    if(row==null){
        $.messager.alert("","请选中一行");
        return false;
    }
    if(row.stus!='DFI0201'&&row.stus!='DFI0203'){
        $.messager.alert("配置校验","只能对编制中或者撤销发布状态下的接口进行配置！");
        return false;
    }
    var bizClsf=$("#bizClsf").val();
    var rowParam=row.mapgId+'@'+row.enNm+'@'+bizClsf+'@'+row.trgtTablId+'@'+row.mapgTyp;
    location.href =baseUrl+"/difConf/toDifConf?rowParam="+rowParam;
}
