
$(function(){
    findDifConfList();
})
var getRelaCombiCondTyp=function(obj,id){
    $("#"+id).text($(obj).text());
}
/**
 * 来源接口列表初始化
 */
var findDifConfList=function(){
    $('#difConf').datagrid({
        url:baseUrl+'/difConf/difConfList',
        toolbar:"#btnDiv",
        pagination:true,
        singleSelect:true,
        queryParams:{
            //初始化查询接口列表为空
            mapgId:$("#paramMapgId").val()
        },
        columns:[[
            {field:'ck',checkbox:'true'},
            {field:'sortNo',title:'序号',width:'5%'},
            {field:'trgtEnNm',title:'目标接口英文名',width:'15%',formatter:function (value,row,index) {
                return $("#trgtEnNm").val();
            }},
            {field:'enNm',title:'来源接口英文名',width:'15%',align:'right'},
            {field:'cnNm',title:'来源接口中文名',width:'15%',align:'right'},
            {field:'relaCondId',title:'关联条件配置状态',width:'15%',align:'right',formatter:function (value,row,index) {
                if(value==''||value==null){
                    return '未配置';
                }else{
                    return '已配置';
                }
            }},
            {field:'quryCondId',title:'筛选条件配置状态',width:'15%',align:'right',formatter:function(value,row,index){
                if(value==''||value==null){
                    return '未配置';
                }else{
                    return '已配置';
                }
            }},
            {field:'relaTypCd',title:'关联类型代码',width:'15%',align:'right',dictry:'DFI09',formatter: formatData},
        ]]
    });
}
/**
 * 条件查询
 */
var doSearch=function () {
    $('#difConf').datagrid('load', {
        cnNm:$("#cnNm").val(),
        enNm:$("#enNm").val(),
        mapgId:$("#paramMapgId").val()
    });
}
/**
 * 弹出新增来源接口窗口
 * @returns {boolean}
 */
var openAddWin = function() {
    var trgtEnNm= $("#trgtEnNm").val();
    var sorcTablId=$("#sorcTabl").combobox("getValue");
    var rows=$("#difConf").datagrid("getRows");
    var datas=[];
    if(rows.length==0){
        $('#difTablSorcForm').form('clear');
    }
    for(var i=0;i<rows.length;i++){
        datas.push(rows[i].enNm);
    }
    if(rows.length>0){
       $("#sorcTabls").textbox('setValue',datas);
        $("#sorcTablsDiv").attr("style","display:block");
        $("#relaTypdDiv").attr("style","display:block");
    }else{
        $("#sorcTablsDiv").attr("style","display:none");
        $("#relaTypdDiv").attr("style","display:none");
    }
    crTrgtTablIdComBo();
    $("#trgtTablId").textbox("setValue",trgtEnNm);
    var _win = $('#difTablSorcAdd');
    _win.window({
        top:50,
        left:200,
        width: 640,
        height: 400,
        collapsible: false,
        maximizable: false,
        minimizable: false,
        resizable: false,
        modal: true
    });
    _win.window('open');
}
/**
 * 查询来源接口下拉
 */
var crTrgtTablIdComBo=function(){
    var rows=$("#difConf").datagrid("getRows");
    var dataSorcId="";
    if(rows.length>0){
        dataSorcId=rows[0].dataSorcId;
    }
    var bizClsf=$("#bizClsf").val();
    $.ajax(baseUrl+"/mapgInfo/getTrgtTablIdComBo",{
        type:'post',
        async:false,
        panelHeight:'120px',
        data:{bizClsf:bizClsf,dataSorcId:dataSorcId},
        success:function(result){
            if(result!=null){
                $('#sorcTabl').combobox({
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
 * 保存来源数据接口
 */
var saveSorcTabl=function () {
    var sorcTablId=$("#sorcTabl").combobox("getValue");
    var rows=$("#difConf").datagrid("getRows");
    var mapgId=$("#paramMapgId").val();
    var trgtTablId=$("#paramTrgtTablId").val();

    var sortNo=rows.length+1;
    var relaTypCd=$("#relaTypCd").combobox("getValue");
    $.messager.confirm("新增提示","您确认新增？",function(r){
     if(r){
         $.ajax(baseUrl+"/difConf/saveSorcTabl",{
             type:'post',
             async:false,
             data:{sortNo:sortNo,mapgId:mapgId,trgtTablId:trgtTablId,sorcTablId:sorcTablId,relaTypCd:relaTypCd},
             success:function(result){
                 if(result!=null){
                     $.messager.alert("新增结果提示",result.message);
                     $("#difTablSorcAdd").window("close");
                     $('#difConf').datagrid('reload');
                 }
             }
         })
     }
    })
};
/**
 * 删除配置
 * @returns {boolean}
 */
var deleteDifConf=function(){
    var row = $('#difConf').datagrid('getSelected');
    var mapgId=$("#paramMapgId").val();
    if(row==null){
        $.messager.alert("选中校验","请选中一行");
        return false;
    }
    $.ajax(baseUrl+"/difConf/sorcChkConf",{
        type:'post',
        data:{id:row.id,sortNo:row.sortNo,mapgId:mapgId},
        success:function(res){
            if(res.success){
                $.messager.confirm('删除确认',"您确认删除？",function(r){
                    if (r){
                        $.ajax(baseUrl+"/difConf/deleteSorcConf",{
                            type:'post',
                            data:{mapgId:mapgId,id:row.id},
                            success:function(res){
                                $.messager.alert("删除提示",res.message);
                                if(res.success){
                                    $('#difConf').datagrid('reload');
                                }
                            }
                        })
                    }
                });
            }else{
                $.messager.alert("删除提示",res.message);
                return false;
            }
        }
    })
}
/**
 * 关联条件配置弹出窗口
 */
var openRelaAddWin = function() {
    $(".branch").empty();
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    if(sorcTablRela==null){
        $.messager.alert("操作提示","请选择一行");
        return false;
    }
    var trgtEnNm= $("#trgtEnNm").val();
    var rows=$("#difConf").datagrid("getRows");
    for(var i=1;i<rows.length;i++){
        if(sorcTablRela.sortNo==rows[i].sortNo){
            if((rows[i-1].relaCondId==''||rows[i-1].relaCondId==null)&&rows[i-1].relaTypCd!='DFI0901'&&rows[i-1].sortNo!='1'){
                $.messager.alert("操作提示","有关联条件未配置！请按照顺序配置关联条件");
               return false;
            }
        }
    }
    if(sorcTablRela.relaTypCd=='DFI0901'){
        $.messager.alert("操作提示","关联类型为笛卡尔积时：不需要配置关联条件和筛选条件");
        return false;
    }
    if(sorcTablRela==null){
        $.messager.alert("操作提示","请选择一个来源接口");
        return false;
    }
    if(sorcTablRela.sortNo==1){
        $.messager.alert("","主表不需要配置关联条件");
        return false;
    }
    var rows=$("#difConf").datagrid("getRows");

    var datas=[];
    for(var i=0;i<rows.length;i++){
        if(sorcTablRela.id==rows[i].id){
            break;
        }else{
            datas.push(rows[i].enNm);
        }

    }
    $("#trgtTablIdRela").textbox("setValue",trgtEnNm);
    $("#sorcTablsRela").textbox("setValue",datas);
    $("#relaTypCdRela").textbox("setValue",sorcTablRela.relaTypCd);
    $("#sorcTablRela").textbox("setValue",sorcTablRela.enNm);
    difRelaCondDetail(sorcTablRela);
    var _win = $('#difRelaCondConf');
    _win.window({
        width: '100%',
        height: '95%',
        left:0,
        top:0,
        collapsible: false,
        maximizable: true,
        minimizable: false,
        resizable: false,
        modal: true,
        inline:false,
        onBeforeClose: function() {
            $('#difTablSorcForm').form('clear');
        }
    });
    _win.window('open');
};
/**
 * 关联条件详细配置查询
 */
var difRelaCondDetail=function(rows){
    $.ajax(baseUrl+"/difConf/getRelaCondDetail",{
        type:'post',
        async:false,
        data:{sorcTablConfId:rows.id},
        success:function(result){
            if(result.length>0){
                $("#relaConfBtn").linkbutton({
                    text:'修改配置'
                });
                var comObj= $("#relComb0").find('a').first();
                $(comObj).linkbutton({
                    text:result[0].combiCondTyp=='DFI1101'?'AND':result[0].combiCondTyp=='DFI1102'?'OR':''
                })
                for(var i=1;i<result.length;i++){
                    var relaObj=result[i];
                    var nodeId=relaObj.nodeId;
                    var pareNodeId=relaObj.pareNodeId;
                    var combiCondTyp=relaObj.combiCondTyp;
                    var relaCondTyp=relaObj.relaCondTyp;
                    var relaFieldEnNm=$.trim(relaObj.relaFieldEnNm);
                    var currFieldEnNm=relaObj.currFieldEnNm;
                    var nodeTyp=relaObj.nodeTyp;
                    var relaSorcTableConfId=relaObj.relaSorcTableConfId;
                    var combiText=combiCondTyp=='DFI1101'?'AND':combiCondTyp=='DFI1102'?'OR':''
                    if(nodeId.length==1&&nodeTyp=='DFI1002'){
                        var html="";
                        html+="<div id=relaComb"+nodeId+" class=entry>";
                        html+= '<span class="label_type">\n' +
                            '<a href="javascript: void(0)" onclick="showRelaMenu(\'relaMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">'+combiText+'</a>\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div>';
                        $(".root").append(html);
                        var expCombo=$("#relaComb"+nodeId)
                        $.parser.parse(expCombo);
                    }else if(nodeId.length==1&&nodeTyp=='DFI1001'){
                        var html="";
                        html+="<div id=relaExp"+nodeId+" class=entry>";
                        html+= '<span class="label_exp">\n' +
                            '<input id=currField'+nodeId+' class="easyui-combobox" value="本表字段" style="width:200px;">\n' +
                            '<input id=relaCondTyp'+nodeId+' class="easyui-combobox" value="关联条件" style="width:200px;"\n' +
                            'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI12"\n' +
                            'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
                            '<input id=relaField'+nodeId+' class="easyui-combobox" value="关联字段" style="width:200px;">\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div>';
                        $(".root").append(html);
                        var expObj=$("#relaExp"+nodeId);
                        $.parser.parse(expObj);
                        crRelaField("relaField"+nodeId);
                        crCurrField("currField"+nodeId);
                        $("#currField"+nodeId).combobox('setValue',currFieldEnNm);
                        $("#relaField"+nodeId).combobox('setValue',relaFieldEnNm+'@'+relaSorcTableConfId);
                        $("#relaCondTyp"+nodeId).combobox('setValue',relaCondTyp);
                    }else if(nodeId.length>1&&nodeTyp=='DFI1002'){
                        var html="";
                        html+="<div class=branch>";
                        html+="<div id=relaComb"+nodeId+" class=entry>";
                        html+= '<span class="label_type">\n' +
                            '<a href="javascript: void(0)" onclick="showRelaMenu(\'relaMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">'+combiText+'</a>\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div></div>';
                        $("#relaComb"+pareNodeId).append(html);
                        var expCombo=$("#relaComb"+nodeId);
                        $.parser.parse(expCombo);
                    }else if(nodeId.length>1&&nodeTyp=='DFI1001'){
                        var html="";
                        html+="<div class=branch>";
                        html+="<div id=relaExp"+nodeId+" class=entry>";
                        html+= '<span class="label_exp">\n' +
                            '<input id=currField'+nodeId+' class="easyui-combobox" value="本表字段" style="width:200px;">\n' +
                            '<input id=relaCondTyp'+nodeId+' class="easyui-combobox" value="关联条件" style="width:200px;"\n' +
                            'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI12"\n' +
                            'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
                            '<input id=relaField'+nodeId+' class="easyui-combobox" value="关联字段" style="width:200px;">\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div></div>';
                        $("#relaComb"+pareNodeId).append(html);
                        var expObj=$("#relaExp"+nodeId);
                        $.parser.parse(expObj);
                        crRelaField("relaField"+nodeId);
                        crCurrField("currField"+nodeId);
                        $("#relaField"+nodeId).combobox('setValue',relaFieldEnNm+'@'+relaSorcTableConfId);
                        $("#currField"+nodeId).combobox('setValue',currFieldEnNm);
                        $("#relaCondTyp"+nodeId).combobox('setValue',relaCondTyp);
                    }
                }
            }else{
                $("#relaConfBtn").linkbutton({
                    text:'新增配置'
                });
            }
        }
    })
}
/**
 * 创建关联表下拉
 * @param id
 */
var crRelaField=function(id){
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    var sorcTablId =sorcTablRela.sorcTablId;
    var rows=$("#difConf").datagrid("getRows");
    var tabls=new Array();
    for(var i=0;i<rows.length;i++){
        if(sorcTablRela.id==rows[i].id){
            break;
        }else{
            tabls.push(rows[i].id);
        }

    }
    var params = {
        "tabls":tabls,
        "currTabl":sorcTablId
    }
    $.ajax(baseUrl+"/difConf/getFieldByTabls",{
        type:'post',
        async:false,
        data:params,
        success:function(result){
            if(result!=null){
                $('#'+id).combobox({
                    data:result,
                    valueField:'fieldEnNm',
                    textField:'fieldCnNm',
                    width:"200px;"
                });
            }
        }
    })
};
/**
 * 创建本表字段选择下拉
 * @param id
 */
var crCurrField=function(id){
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    var sorcTablId =sorcTablRela.sorcTablId;
    $.ajax(baseUrl+"/difConf/getFieldByTabl",{
        type:'post',
        async:false,
        data:{tablId:sorcTablId},
        success:function(result){
            if(result!=null){
                $('#'+id).combobox({
                    data:result,
                    valueField:'fieldEnNm',
                    textField:'fieldCnNm',
                    width:"200px",
                    top:'50px'
                });
            }
        }
    })
};
/**
 * 节点类型和组合条件类型选择菜单
 */
var showRelaMenu=function (id,e,obj) {
    var pbject=obj.parentNode.parentNode;
    var _obj=null;
    var x=e.pageX;
    var y=e.pageY;
    $('#'+id).menu('show', {
        left: x,
        top: y
    });
        $('#relaMenu').menu({
            onClick:function(item){
                var num=pbject.id.split("relComb")[1];
                if(item.id=='and'||item.id=='or'){
                    $(obj).linkbutton({
                        text:item.text
                    })
                }else if(item.id=='exp'){
                    if(num=='0'){
                        addRelaFirstExp();
                    }else{
                        addRelaMoreExp(pbject);
                    }
                }else if(item.id=='combo'){
                    if(num=='0'){
                        addRelaFirstComboExp();
                    }else{
                        addRelamoveComboExp(pbject);
                    }
                }
            }
        });
    $('#menuRemove').bind('click', function(){
       $(pbject).remove();
    });
}

/**
 * 添加第一层表达式
 */
var addRelaFirstExp=function(){
    var num=$(".root").children("div").length+1;
    var html="";
    html+="<div id=relaExp"+num+" class=entry>";
    html+= '<span class="label_exp">\n' +
        '<input id=currField'+num+' class="easyui-combobox" value="本表字段" style="width:200px;">\n' +
        '<input id=relaCondTyp'+num+' class="easyui-combobox" value="关联条件" style="width:200px;"\n' +
        'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI12"\n' +
        'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
        '<input id=relaField'+num+' class="easyui-combobox" value="关联字段" style="width:200px;">\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div>';
   $(".root").append(html);
    var expObj=$("#relaExp"+num);
    $.parser.parse(expObj);
    crRelaField("relaField"+num);
    crCurrField("currField"+num);
}
/**
 * 条件第一层组合条件
 */
var addRelaFirstComboExp=function(){
    var num=$(".root").children("div").length+1;
    var html="";
    html+="<div id=relaComb"+num+" class=entry>";
    html+= '<span class="label_type">\n' +
        '<a href="javascript: void(0)" onclick="showRelaMenu(\'relaMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">AND</a>\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div>';
    $(".root").append(html);
    var expCombo=$("#relaComb"+num)
    $.parser.parse(expCombo);
}
var appendNodId=function (num) {
    // if(){
    //
    // }
}
/**
 * 删除一个节点
 * @param obj
 */
var removeCurrNone=function(obj){
    var tem=obj.parentNode.parentNode;
    $(tem).remove();
}
/**
 * 添加更多表达式
 */
var addRelaMoreExp=function(obj){
    var comboNum=obj.id.toString().replace("relaComb","");
    var queNum=0;
    var num="";
    var len=$("#"+obj.id).find("div").length;
    for(var i=1;i<10;i++){
        if(len>0){
            queNum=parseInt($(obj).children("div").length)+parseInt(i);
            var relaExpO=$("#relaExp"+comboNum+queNum).length;
            var relaComb=$("#relaComb"+comboNum+queNum).length;
            if(relaExpO==0&&relaComb==0){
                num=comboNum+queNum;
                break;
            }
        }else{
            var relaExpO=$("#relaExp"+comboNum+i).length;
            var relaComb=$("#relaExp"+comboNum+i).length;
            if(relaExpO==0&&relaComb==0){
                num=comboNum+i;
                break;
            }

        }
    }
    var html="";
    html+="<div class=branch>";
    html+="<div id=relaExp"+num+" class=entry>";
    html+= '<span class="label_exp">\n' +
        '<input id=currField'+num+' class="easyui-combobox" value="本表字段" style="width:200px;">\n' +
        '<input id=relaCondTyp'+num+' class="easyui-combobox" value="关联条件" style="width:200px;"\n' +
        'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI12"\n' +
        'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
        '<input id=relaField'+num+' class="easyui-combobox" value="关联字段" style="width:200px;">\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div></div>';
    $(obj).append(html);
    var expObj=$("#relaExp"+num);
    $.parser.parse(expObj);
    crRelaField("relaField"+num);
    crCurrField("currField"+num);
};
//添加更多组合条件元素
var addRelamoveComboExp=function(obj){
    var comboNum=obj.id.toString().replace("relaComb","");
    var queNum=0;
    var num="";
    var len=$("#"+obj.id).find("div").length;
    for(var i=1;i<100;i++){
        if(len>0){
            queNum=parseInt($(obj).children("div").length)+parseInt(i);
            var relaExpO=$("#relaExp"+comboNum+queNum).length;
            var relaComb=$("#relaComb"+comboNum+queNum).length;
            if(relaExpO==0&&relaComb==0){
                num=comboNum+queNum;
                break;
            }
        }else{
            var relaExpO=$("#relaExp"+comboNum+i).length;
            var relaComb=$("#relaExp"+comboNum+i).length;
            if(relaExpO==0&&relaComb==0){
                num=comboNum+i;
                break;
            }

        }
    }
    var html="";
    html+="<div class=branch>";
    html+="<div id=relaComb"+num+" class=entry>";
    html+= '<span class="label_type">\n' +
        '<a href="javascript: void(0)" onclick="showRelaMenu(\'relaMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">AND</a>\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div></div>';
   $(obj).append(html);
    var expCombo=$("#relaComb"+num);
    $.parser.parse(expCombo);
};
/**
 * 新增关联条件配置
 */
var saveRelaCond=function(){
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    var datas=[];
    var sorcTablConfId=sorcTablRela.id;
    var nodeId="";
    var pareNodeId="";
    var combiCondTyp="";
    var relaCondTyp="";
    var relaSorcTableConfId="";
    var relaFieldEnNm="";
    var currFieldEnNm="";
    var trgtTablId=$("#paramTrgtTablId").val();
    if($("#relComb0").find("div").length==1){
        $.messager.alert("新增校验","没有配置关联条件，无法新增");
        return false;
    }
    $("#relComb0").find("div").each(function(i){
        if($(this).attr('id')!=undefined){
            var currId=$(this).attr("id").toString();
            if(currId.indexOf("relaExp")!=-1){
                nodeId=currId.replace("relaExp","");
                if(nodeId.length==1){
                    pareNodeId='0';
                }else{
                    pareNodeId=nodeId.substr(0,nodeId.length-1);
                }
                currFieldEnNm=$("#currField"+nodeId).combobox("getValue");
                if(currFieldEnNm=="本表字段"){
                    $.messager.alert("空值校验","请选择本表字段");
                    return false;
                }
                relaCondTyp=$("#relaCondTyp"+nodeId).combobox("getValue");
                if(relaCondTyp=="关联条件"){
                    $.messager.alert("空值校验","请选择关联条件");
                    return false;
                }
                var tempStrs=$("#relaField"+nodeId).combobox("getValue").toString().split("@");
                if(tempStrs=="关联字段"){
                    $.messager.alert("空值校验","请选择关联字段");
                    return false;
                }
                relaFieldEnNm=tempStrs[0];
                relaSorcTableConfId=tempStrs[1];
                var difTablRelaCond={
                    sorcTablConfId:sorcTablConfId,
                    nodeId:nodeId,
                    pareNodeId:pareNodeId,
                    nodeTyp:'DFI1001',
                    combiCondTyp:'',
                    relaCondTyp:relaCondTyp,
                    relaSorcTableConfId:relaSorcTableConfId,
                    relaFieldEnNm:relaFieldEnNm,
                    currFieldEnNm:currFieldEnNm,
                    trgtTablId:trgtTablId
                }
                datas.push(difTablRelaCond);
            }else if(currId.indexOf("relaComb")!=-1){
                nodeId=currId.replace("relaComb","");
                if(nodeId.length==1){
                    pareNodeId='0';
                }else{
                    pareNodeId=nodeId.substr(0,nodeId.length-1);
                }
                var spanText=$(this).find("span").first().text();
                if(spanText.indexOf('AND')!=-1){
                    combiCondTyp='DFI1101';
                }else{
                    combiCondTyp='DFI1102';
                }
                var difTablRelaCond={
                    sorcTablConfId:sorcTablConfId,
                    nodeId:nodeId,
                    pareNodeId:pareNodeId,
                    nodeTyp:'DFI1002',
                    combiCondTyp:combiCondTyp,
                    relaCondTyp:'',
                    relaSorcTableConfId:'',
                    relaFieldEnNm:'',
                    currFieldEnNm:'',
                    trgtTablId:trgtTablId
                }
                datas.push(difTablRelaCond);
            }
        }
        var len= $("#relComb0").find("div").length-1;
        var rootCombi=$(".label_root").text().indexOf("AND")!=-1?"DFI1101":$(".label_root").text().indexOf('OR')!=-1?'DFI1102':'';
        if(len==i){
            var difTablRelaCond={
                sorcTablConfId:sorcTablConfId,
                nodeId:'root',
                pareNodeId:'0',
                nodeTyp:'DFI1002',
                combiCondTyp:rootCombi,
                relaCondTyp:'',
                relaSorcTableConfId:'',
                relaFieldEnNm:'',
                currFieldEnNm:'',
                trgtTablId:trgtTablId
            }
            datas.push(difTablRelaCond);
            $.messager.confirm('操作确认',"您确认提交配置信息?",function(r){
                if (r){
                    //修改配置进行先删除后新增
                    var btnTex=$.trim($("#relaConfBtn").text().toString());
                    if(btnTex=="修改配置"){
                        $.ajax(baseUrl+"/difConf/deleteConfBySorcId",{
                            type:'post',
                            data:{sorcTablConfId:sorcTablConfId},
                            success:function(result){
                                if(result){
                                    $.ajax(baseUrl+"/difConf/saveRelaCondConf",{
                                        type:'post',
                                        data:JSON.stringify(datas),
                                        contentType:"application/json",
                                        success:function(result){
                                            $.messager.alert("操作提示",result.message);
                                            closeWin('difRelaCondConf');
                                            $('#difConf').datagrid('reload');
                                        }
                                    });
                                }
                            }
                        });
                        //新增配置
                    }else  if(btnTex=='新增配置'){
                        $.ajax(baseUrl+"/difConf/saveRelaCondConf",{
                            type:'post',
                            data:JSON.stringify(datas),
                            contentType:"application/json",
                            success:function(result){
                                $.messager.alert("操作提示",result.message);
                                closeWin('difRelaCondConf');
                                $('#difConf').datagrid('reload');
                            }
                        });
                    }

                }
            });
        }
    })
};
/**
 * 删除关联条件
 */
var deleteRelaConf=function(){
    if($("#relComb0").find("div").length==1){
        $.messager.alert("删除校验","没有配置关联条件，无法删除");
        return false;
    }
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    $.messager.confirm("删除确认","您确认删除？",function(r){
        if(r){
            $.ajax(baseUrl+"/difConf/deleteConfBySorcId",{
                type:'post',
                data:{sorcTablConfId:sorcTablRela.id},
                success:function(result){
                    if(result){
                        $.messager.alert("删除提示","删除成功");
                        $('#difConf').datagrid('reload');
                        closeWin('difRelaCondConf');
                    }else{
                        $.messager.alert("删除提示","删除失败");
                    }
                }
            });
        }
    })
};
// =============================================配置筛选条件==========================================================
/**
 * 筛选条件配置弹出窗
 * @returns {boolean}
 */
var openQuryAddWin = function() {
    $(".branch").empty();
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    if(sorcTablRela==null){
        $.messager.alert("操作提示","请选择一行");
        return false;
    }
    var trgtEnNm= $("#trgtEnNm").val();
    if(sorcTablRela.relaTypCd=='DFI0901'){
        $.messager.alert("操作提示","关联类型为笛卡尔积时：不需要配置关联条件和筛选条件");
        return false;
    }
    if(sorcTablRela==null){
        $.messager.alert("操作提示","请选择一个来源接口");
        return false;
    }
    $("#trgtTablIdQury").textbox("setValue",trgtEnNm);
    $("#sorcTablQury").textbox("setValue",sorcTablRela.enNm);
    difQuryCondDetail(sorcTablRela);
    var _win = $('#difQuryCondConf');
    _win.window({
        width: '100%',
        height: '95%',
        left:0,
        top:0,
        collapsible: false,
        maximizable: true,
        minimizable: false,
        resizable: false,
        modal: true,
        inline:false,
        onBeforeClose: function() {
            $('#difQuryCondForm').form('clear');
        }
    });
    _win.window('open');
};
var showQuryMenu=function (id,e,obj) {
    var pbject=obj.parentNode.parentNode;
    var _obj=null;
    var x=e.pageX;
    var y=e.pageY;
    $('#'+id).menu('show', {
        left: x,
        top: y
    });
    $('#relaMenu').menu({
        onClick:function(item){
            var num=pbject.id.split("quryComb")[1];
            if(item.id=='and'||item.id=='or'){
                $(obj).linkbutton({
                    text:item.text
                })
            }else if(item.id=='exp'){
                if(num=='0'){
                    addQuryFirstExp();
                }else{
                    addQuryMoreExp(pbject);
                }
            }else if(item.id=='combo'){
                if(num=='0'){
                    addQuryFirstComboExp();
                }else{
                    addQurymoveComboExp(pbject);
                }
            }
        }
    });
    $('#menuRemove').bind('click', function(){
        $(pbject).remove();
    });
};
/**
 * 添加第一层表达式
 */
var addQuryFirstExp=function(){
    var num=$(".rootQury").children("div").length+1;
    var html="";
    html+="<div id=quryExp"+num+" class=entry>";
    html+= '<span class="label_exp">\n' +
        '<input id=quryField'+num+' class="easyui-combobox" value="筛选字段" style="width:200px;">\n' +
        '<input id=quryTyp'+num+' class="easyui-combobox" value="筛选类型" style="width:200px;"\n' +
        'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI13"\n' +
        'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
        '<input id=condExps'+num+' class="easyui-textbox" value="" style="width:200px;">\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div>';
    $(".rootQury").append(html);
    var expObj=$("#quryExp"+num);
    $.parser.parse(expObj);
    crCurrField("quryField"+num);
};
/**
 * 条件第一层组合条件
 */
var addQuryFirstComboExp=function(){
    var num=$(".rootQury").children("div").length+1;
    var html="";
    html+="<div id=quryComb"+num+" class=entry>";
    html+= '<span class="label_type">\n' +
        '<a href="javascript: void(0)" onclick="showQuryMenu(\'relaMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">AND</a>\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div>';
    $(".rootQury").append(html);
    var expCombo=$("#quryComb"+num)
    $.parser.parse(expCombo);
};
/**
 * 添加更多表达式
 */
var addQuryMoreExp=function(obj){
    var comboNum=obj.id.toString().replace("quryComb","");
    var queNum=0;
    var num="";
    var len=$("#"+obj.id).find("div").length;
    for(var i=1;i<100;i++){
        if(len>0){
            queNum=parseInt($(obj).children("div").length)+parseInt(i);
            var quryExpO=$("#quryExp"+comboNum+queNum).length;
            var quryComb=$("#quryComb"+comboNum+queNum).length;
            if(quryExpO==0&&quryComb==0){
                num=comboNum+queNum;
                break;
            }
        }else{
            var quryExpO=$("#quryExp"+comboNum+i).length;
            var quryComb=$("#quryExp"+comboNum+i).length;
            if(quryExpO==0&&quryComb==0){
                num=comboNum+i;
                break;
            }

        }
    }
    var html="";
    html+="<div class=branch>";
    html+="<div id=quryExp"+num+" class=entry>";
    html+= '<span class="label_exp">\n' +
        '<input id=quryField'+num+' class="easyui-combobox" value="筛选字段" style="width:200px;">\n' +
        '<input id=quryTyp'+num+' class="easyui-combobox" value="筛选类型" style="width:200px;"\n' +
        'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI13"\n' +
        'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
        '<input id=condExps'+num+' class="easyui-textbox" value="" style="width:200px;">\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div></div>';
    $(obj).append(html);
    var expObj=$("#quryExp"+num);
    $.parser.parse(expObj);
    crCurrField("quryField"+num);
};
//添加更多组合条件元素
var addQurymoveComboExp=function(obj){
    var comboNum=obj.id.toString().replace("quryComb","");
    var queNum=0;
    var num="";
    var len=$("#"+obj.id).find("div").length;
    for(var i=1;i<10;i++){
        if(len>0){
            queNum=parseInt($(obj).children("div").length)+parseInt(i);
            var quryExpO=$("#quryExp"+comboNum+queNum).length;
            var quryComb=$("#quryComb"+comboNum+queNum).length;
            if(quryExpO==0&&quryComb==0){
                num=comboNum+queNum;
                break;
            }
        }else{
            var quryExpO=$("#quryExp"+comboNum+i).length;
            var quryComb=$("#quryExp"+comboNum+i).length;
            if(quryExpO==0&&quryComb==0){
                num=comboNum+i;
                break;
            }

        }
    }
    var html="";
    html+="<div class=branch>";
    html+="<div id=quryComb"+num+" class=entry>";
    html+= '<span class="label_type">\n' +
        '<a href="javascript: void(0)" onclick="showQuryMenu(\'relaMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">AND</a>\n' +
        '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div></div>';
    $(obj).append(html);
    var expCombo=$("#quryComb"+num);
    $.parser.parse(expCombo);
};
/**
 * 筛选条件详细配置查询
 */
var difQuryCondDetail=function(rows){
    $.ajax(baseUrl+"/difConf/getQuryCondDetail",{
        type:'post',
        async:false,
        data:{sorcTablConfId:rows.id},
        success:function(result){
            if(result.length>0){
                $("#quryConfBtn").linkbutton({
                    text:'修改配置'
                });
                var comObj= $("#quryComb0").find('a').first();
                $(comObj).linkbutton({
                    text:result[0].combiCondTyp=='DFI1101'?'AND':result[0].combiCondTyp=='DFI1102'?'OR':''
                })
                for(var i=1;i<result.length;i++){
                    var quryTypObj=result[i];
                    var nodeId=quryTypObj.nodeId;
                    var pareNodeId=quryTypObj.pareNodeId;
                    var combiCondTyp=quryTypObj.combiCondTyp;
                    var quryTyp=quryTypObj.quryTyp;
                    var condExps=quryTypObj.condExps;
                    var quryFieldEnNm=$.trim(quryTypObj.quryFieldEnNm);
                    var nodeTyp=quryTypObj.nodeTyp;
                    var combiText=combiCondTyp=='DFI1101'?'AND':combiCondTyp=='DFI1102'?'OR':''
                    if(nodeId.length==1&&nodeTyp=='DFI1002'){
                        var html="";
                        html+="<div id=quryComb"+nodeId+" class=entry>";
                        html+= '<span class="label_type">\n' +
                            '<a href="javascript: void(0)" onclick="showQuryMenu(\'relaMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">'+combiText+'</a>\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div>';
                        $(".rootQury").append(html);
                        var expCombo=$("#quryComb"+nodeId)
                        $.parser.parse(expCombo);
                    }else if(nodeId.length==1&&nodeTyp=='DFI1001'){
                        var html="";
                        html+="<div id=quryExp"+nodeId+" class=entry>";
                        html+= '<span class="label_exp">\n' +
                            '<input id=quryField'+nodeId+' class="easyui-combobox" value="筛选字段" style="width:200px;">\n' +
                            '<input id=quryTyp'+nodeId+' class="easyui-combobox" value="筛选类型" style="width:200px;"\n' +
                            'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI13"\n' +
                            'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
                            '<input id=condExps'+nodeId+' class="easyui-textbox" value="" style="width:200px;">\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div>';
                        $(".rootQury").append(html);
                        var expObj=$("#quryExp"+nodeId);
                        $.parser.parse(expObj);
                        crCurrField("quryField"+nodeId);
                        $("#quryField"+nodeId).combobox('setValue',quryFieldEnNm);
                        $("#quryTyp"+nodeId).combobox('setValue',quryTyp);
                        $("#condExps"+nodeId).textbox('setValue',condExps)
                    }else if(nodeId.length>1&&nodeTyp=='DFI1002'){
                        var html="";
                        html+="<div class=branch>";
                        html+="<div id=quryComb"+nodeId+" class=entry>";
                        html+= '<span class="label_type">\n' +
                            '<a href="javascript: void(0)" onclick="showQuryMenu(\'quryMenu\',event,this)" class="easyui-linkbutton" icon="icon-ok">'+combiText+'</a>\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div></div>';
                        $("#quryComb"+pareNodeId).append(html);
                        var expCombo=$("#quryComb"+nodeId);
                        $.parser.parse(expCombo);
                    }else if(nodeId.length>1&&nodeTyp=='DFI1001'){
                        var html="";
                        html+="<div class=branch>";
                        html+="<div id=quryExp"+nodeId+" class=entry>";
                        html+= '<span class="label_exp">\n' +
                            '<input id=quryField'+nodeId+' class="easyui-combobox" value="筛选字段" style="width:200px;">\n' +
                            '<input id=quryTyp'+nodeId+' class="easyui-combobox" value="筛选类型" style="width:200px;"\n' +
                            'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI13"\n' +
                            'valueField="dictryId" panelHeight="120px" textField="dictryNm">\n' +
                            '<input id=condExps'+nodeId+' class="easyui-textbox" value="" style="width:200px;">\n' +
                            '<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                            '</div></div>';
                        $("#quryComb"+pareNodeId).append(html);
                        var expObj=$("#quryExp"+nodeId);
                        $.parser.parse(expObj);
                        crCurrField("quryField"+nodeId);
                        $("#quryField"+nodeId).combobox('setValue',quryFieldEnNm);
                        $("#quryTyp"+nodeId).combobox('setValue',quryTyp);
                        $("#condExps"+nodeId).textbox('setValue',condExps)
                    }
                }
            }else{
                $("#quryConfBtn").linkbutton({
                    text:'新增配置'
                });
            }
        }
    })
};
/**
 * 新增筛选条件配置
 */
var saveQuryCond=function(){
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    var datas=[];
    var sorcTablConfId=sorcTablRela.id;
    var nodeId="";
    var pareNodeId="";
    var combiCondTyp="";
    var quryTyp="";
    var quryField="";
    var condExps="";
    var trgtTablId=$("#paramTrgtTablId").val();
    if($("#quryComb0").find("div").length==1){
        $.messager.alert("新增校验","没有配置筛选条件，无法新增");
        return false;
    }
    $("#quryComb0").find("div").each(function(i){
        if($(this).attr('id')!=undefined){
            var currId=$(this).attr("id").toString();
            if(currId.indexOf("quryExp")!=-1){
                nodeId=currId.replace("quryExp","");
                if(nodeId.length==1){
                    pareNodeId='0';
                }else{
                    pareNodeId=nodeId.substr(0,nodeId.length-1);
                }
                quryField=$("#quryField"+nodeId).combobox("getValue");
                if(quryField=="筛选字段"){
                    $.messager.alert("空值校验","请选择筛选字段");
                    return false;
                }
                quryTyp=$("#quryTyp"+nodeId).combobox("getValue");
                if(quryTyp=="筛选类型"){
                    $.messager.alert("空值校验","请选择筛选类型");
                    return false;
                }
                condExps=$("#condExps"+nodeId).textbox('getValue');
                if(condExps==''){
                    $.messager.alert("空值校验","表达式不能为空");
                    return false;
                }
                var difTablQuryCond={
                    sorcTablConfId:sorcTablConfId,
                    nodeId:nodeId,
                    pareNodeId:pareNodeId,
                    nodeTyp:'DFI1001',
                    combiCondTyp:'',
                    quryFieldEnNm:quryField,
                    quryTyp:quryTyp,
                    condExps:condExps,
                    trgtTablId:trgtTablId
                }
                datas.push(difTablQuryCond);
            }else if(currId.indexOf("quryComb")!=-1){
                nodeId=currId.replace("quryComb","");
                if(nodeId.length==1){
                    pareNodeId='0';
                }else{
                    pareNodeId=nodeId.substr(0,nodeId.length-1);
                }
                var spanText=$(this).find("span").first().text();
                if(spanText.indexOf('AND')!=-1){
                    combiCondTyp='DFI1101';
                }else{
                    combiCondTyp='DFI1102';
                }
                var difTablQuryCond={
                    sorcTablConfId:sorcTablConfId,
                    nodeId:nodeId,
                    pareNodeId:pareNodeId,
                    nodeTyp:'DFI1002',
                    combiCondTyp:combiCondTyp,
                    quryFieldEnNm:'',
                    quryTyp:'',
                    condExps:'',
                    trgtTablId:trgtTablId
                }
                datas.push(difTablQuryCond);
            }
        }
        var len= $("#quryComb0").find("div").length-1;
        var rootCombi=$(".label_root").text().indexOf("AND")!=-1?"DFI1101":$(".label_root").text().indexOf('OR')!=-1?'DFI1102':'';
        if(len==i){
            var difTablQuryCond={
                sorcTablConfId:sorcTablConfId,
                nodeId:'root',
                pareNodeId:'0',
                nodeTyp:'DFI1002',
                combiCondTyp:rootCombi,
                quryFieldEnNm:'',
                quryTyp:'',
                condExps:'',
                trgtTablId:trgtTablId
            }
            datas.push(difTablQuryCond);
            $.messager.confirm('操作确认',"您确认提交配置信息?",function(r){
                if (r){
                    //修改配置进行先删除后新增
                    var btnTex=$.trim($("#quryConfBtn").text().toString());
                    if(btnTex=="修改配置"){
                        $.ajax(baseUrl+"/difConf/deleteQuryByConfId",{
                            type:'post',
                            data:{sorcTablConfId:sorcTablConfId},
                            success:function(result){
                                if(result){
                                    $.ajax(baseUrl+"/difConf/saveQuryCondConf",{
                                        type:'post',
                                        data:JSON.stringify(datas),
                                        contentType:"application/json",
                                        success:function(result){
                                            $.messager.alert("操作提示",result.message);
                                            closeWin('difQuryCondConf');
                                            $('#difConf').datagrid('reload');
                                        }
                                    });
                                }
                            }
                        });
                        //新增配置
                    }else  if(btnTex=='新增配置'){
                        $.ajax(baseUrl+"/difConf/saveQuryCondConf",{
                            type:'post',
                            data:JSON.stringify(datas),
                            contentType:"application/json",
                            success:function(result){
                                $.messager.alert("操作提示",result.message);
                                closeWin('difQuryCondConf');
                                $('#difConf').datagrid('reload');
                            }
                        });
                    }

                }
            });
        }
    })
};
/**
 * 删除筛选条件
 */
var deleteQuryConf=function(){
    if($("#quryComb0").find("div").length==1){
        $.messager.alert("删除校验","没有配置筛选条件，无法删除");
        return false;
    }
    var sorcTablRela=$("#difConf").datagrid("getSelected");
    $.messager.confirm("删除确认","您确认删除？",function(r){
        if(r){
            $.ajax(baseUrl+"/difConf/deleteQuryByConfId",{
                type:'post',
                data:{sorcTablConfId:sorcTablRela.id},
                success:function(result){
                    if(result){
                        $.messager.alert("删除提示","删除成功");
                        $('#difConf').datagrid('reload');
                        closeWin('difQuryCondConf');
                    }else{
                        $.messager.alert("删除提示","删除失败");
                    }
                }
            });
        }
    })
}
// =======================字段映射配置=====
var openFieldAddWin = function() {
    $(".rootField").empty();
    var datas=[];
    var rows=$("#difConf").datagrid("getRows");
    for(var i=0;i<rows.length;i++){
        datas.push(rows[i].enNm);
    }
    var trgtEnNm= $("#trgtEnNm").val();
    if(rows.length<1){
        $.messager.alert("操作提示","请先配置来源接口");
        return false;
    }
    $("#trgtTablIdField").textbox("setValue",trgtEnNm);
    $("#sorcTablField").textbox("setValue",datas);
    difFieldCondDetail();
    var _win = $('#difFieldMapgConf');
    _win.window({
        width: '100%',
        height: '95%',
        left:0,
        top:0,
        collapsible: false,
        maximizable: true,
        minimizable: false,
        resizable: false,
        modal: true,
        inline:false,
        onBeforeClose: function() {
            $('#difFieldCondForm').form('clear');
        }
    });
    _win.window('open');
};

/**
 * 映射来源字段下拉
 * @param id
 */
var crMapgSorcField=function(id){
    var rows=$("#difConf").datagrid("getRows");
    var tabls=new Array();
    for(var i=0;i<rows.length;i++){
        if(rows[i].sortNo=='1'){
            tabls.push(rows[i].id);
        }else{
            if(rows[i].relaCondId!=''&&rows[i].relaCondId!=null){
                tabls.push(rows[i].id);
            }
        }
    }
    var params = {
        "tabls":tabls,
        "currTabl":""
    }
    $.ajax(baseUrl+"/difConf/getFieldByTabls",{
        type:'post',
        async:false,
        data:params,
        success:function(result){
            if(result!=null){
                $('#'+id).combobox({
                    data:result,
                    valueField:'fieldEnNm',
                    textField:'fieldCnNm',
                    width:"200px;"
                });
            }
        }
    })
};
var crMapgTrgtField=function(id){
    var trgtTablId=$("#paramTrgtTablId").val();
    var mapgTyp=$("#mapgTyp").val();
    var mapgId=$("#paramMapgId").val();
    $.ajax(baseUrl+"/difConf/getFieldByTabl",{
        type:'post',
        async:false,
        data:{tablId:trgtTablId,mapgTyp:mapgTyp,mapgId:mapgId},
        success:function(result){
            if(result!=null){
                $('#'+id).combobox({
                    data:result,
                    valueField:'fieldEnNm',
                    textField:'fieldCnNm',
                    width:"200px",
                    top:'50px'
                });
            }
        }
    })
};
var difFieldCondDetail=function(){
    var mapgId=$("#paramMapgId").val();
    $.ajax(baseUrl+"/difConf/getFieldMapgDetail",{
        type:'post',
        async:false,
        data:{mapgId:mapgId},
        success:function(result){
            if(result.length>0){
                $("#fieldConfBtn").linkbutton({
                    text:'修改配置'
                });
                for(var i=0;i<result.length;i++){
                    var num=i+1;
                    var fieldObj=result[i];
                    var trgtFieldEnNm=fieldObj.trgtFieldEnNm;
                    var fieldMapgWay=fieldObj.fieldMapgWay;
                    var sorcTablConfId=$.trim(fieldObj.sorcTablConfId);
                    var sorcFieldEnNm=fieldObj.sorcFieldEnNm;
                    var udfExps=fieldObj.udfExps;
                    var html="";
                    html+="<div id=fieldExp"+num+" class=entry>";
                    html+= '<span class="label_exp">\n' +
                        '<input id=trgtFieldEnNm'+num+' class="easyui-combobox" value="目标接口字段" style="width:200px;">\n' +
                        '<input id=fieldMapgWay'+num+' class="easyui-combobox" value="字段映射方式" style="width:200px;"\n' +
                        'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI14"\n' +
                        'valueField="dictryId"  panelHeight="120px" textField="dictryNm">';

                    html+="<input id=sorcFieldEnNm"+num+" class='easyui-combobox' value='来源接口字段' style='width:200px;'>";
                    html+="<input id=udfExps"+num+" class='easyui-textbox'  value='表达式' style='width:200px;'>";
                    html+='<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
                        '</div>';
                    $(".rootField").append(html);
                    var expObj=$("#fieldExp"+num);
                    $.parser.parse(expObj);
                    if( fieldMapgWay=='DFI1402'||fieldMapgWay==='DFI1499'){
                        $('#sorcFieldEnNm'+num).next(".combo").hide();
                        $('#udfExps'+num).next().show();
                    }
                    else if(fieldMapgWay=='DFI1401'||fieldMapgWay=='DFI1403'||fieldMapgWay=='DFI1404'){
                        $('#sorcFieldEnNm'+num).next(".combo").hide();
                        $('#udfExps'+num).next().hide();
                    }else if(fieldMapgWay=='DFI1405'||fieldMapgWay=='DFI1406'||fieldMapgWay=='DFI1407'||fieldMapgWay=='DFI1408'||fieldMapgWay=='DFI1409'||fieldMapgWay=='DFI1410'){
                        $('#sorcFieldEnNm'+num).next(".combo").show();
                        $('#udfExps'+num).next().hide();
                    }
                    $("#fieldMapgWay"+num).combobox({
                        loadFilter: function(data){
                            var  mapgTyp=$("#mapgTyp").val();
                            var resData=[];
                            for(var i in data){
                                if(mapgTyp=='DFI0701'){
                                    if(data[i].dictryId!='DFI1406'&&data[i].dictryId!='DFI1407'&&data[i].dictryId!='DFI1408'&&data[i].dictryId!='DFI1409'&&data[i].dictryId!='DFI1410'){
                                        resData.push(data[i]);
                                    }
                                }else{
                                    resData.push(data[i]);
                                }
                            }
                            return resData;
                        }
                    });
                    crMapgSorcField("sorcFieldEnNm"+num);
                    crMapgTrgtField("trgtFieldEnNm"+num);
                    fieldMapgWaySelect(num);
                    if(sorcFieldEnNm!=''&&sorcTablConfId!=''){
                        $("#sorcFieldEnNm"+num).combobox("setValue",sorcFieldEnNm+'@'+sorcTablConfId);
                    }
                    $("#trgtFieldEnNm"+num).combobox("setValue",trgtFieldEnNm);
                    $("#fieldMapgWay"+num).combobox('setValue',fieldMapgWay);
                    $("#udfExps"+num).textbox("setValue",udfExps);
                }
            }else{
                $("#fieldConfBtn").linkbutton({
                    text:'新增配置'
                });
            }
        }
    })
}
/**
 * 配置字段映射
 */
var addFieldExp=function(){
    var num=parseInt($(".rootField").children("div").length)+1;
    var html="";
    html+="<div id=fieldExp"+num+" class=entry>";
    html+= '<span class="label_exp">\n' +
        '<input id=trgtFieldEnNm'+num+' class="easyui-combobox" value="目标接口字段" style="width:200px;">\n' +
        '<input id=fieldMapgWay'+num+' class="easyui-combobox" value="字段映射方式" style="width:200px;"\n' +
        'url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI14"\n' +
        'valueField="dictryId" panelHeight="120px" textField="dictryNm">';

    html+="<input id=sorcFieldEnNm"+num+" class='easyui-combobox' value='来源接口字段' style='width:200px;'>";
    html+="<input id=udfExps"+num+" class='easyui-textbox'  value='表达式' style='width:200px;'>";
    html+='<a href="#" class="easyui-linkbutton" data-options="iconCls:\'icon-remove\'" onclick="removeCurrNone(this)"></a></span>\n' +
        '</div>';
    $(".rootField").append(html);
    var expObj=$("#fieldExp"+num);
    $.parser.parse(expObj);
    $('#sorcFieldEnNm'+num).next(".combo").hide();
    $('#udfExps'+num).next().hide();
    $("#fieldMapgWay"+num).combobox({
        loadFilter: function(data){
            var  mapgTyp=$("#mapgTyp").val();
            var resData=[];
            for(var i in data){
                if(mapgTyp=='DFI0701'){
                    if(data[i].dictryId!='DFI1406'&&data[i].dictryId!='DFI1407'&&data[i].dictryId!='DFI1408'&&data[i].dictryId!='DFI1409'&&data[i].dictryId!='DFI1410'){
                        resData.push(data[i]);
                    }
                }else{
                    resData.push(data[i]);
                }
            }
            return resData;
        }
    });
    crMapgSorcField("sorcFieldEnNm"+num);
    crMapgTrgtField("trgtFieldEnNm"+num);
    fieldMapgWaySelect(num);
    // crCurrField("quryField"+num);
};
//当映射方式不为空值、常量值、系统日期、批量日期、自定义表达式时显示来源表和来源字段选择，当字段映射方式为自定义表达式时，显示自定义输入框
var fieldMapgWaySelect=function (num) {
$("#fieldMapgWay"+num).combobox({
    onSelect:function(item){
        var val=item.dictryId;
        if( val==='DFI1499'||val=='DFI1402'){
            $('#sorcFieldEnNm'+num).next(".combo").hide();
            $('#udfExps'+num).next().show();
        }
        else if(val=='DFI1401'||val=='DFI1403'||val=='DFI1404'){
            $('#sorcFieldEnNm'+num).next(".combo").hide();
            $('#udfExps'+num).next().hide();
        }else if(val=='DFI1405'||val=='DFI1406'||val=='DFI1407'||val=='DFI1408'||val=='DFI1409'||val=='DFI1410'){
            $('#sorcFieldEnNm'+num).next(".combo").show();
            $('#udfExps'+num).next().hide();
        }
    }
});
}
//保存字段配置信息
var saveFieldMapgConf=function(){
    var mapgId=$("#paramMapgId").val();
    var trgtTablId=$("#paramTrgtTablId").val();
    var trgtFieldEnNm='';
    var fieldMapgWay='';
    var sorcTablConfId='';
    var sorcFieldEnNm='';
    var udfExps='';
    var datas=[];
    if($(".rootField").find("div").length==0){
        $.messager.alert("新增校验","没有配置字段映射，无法新增");
        return false;
    }
    $(".rootField").find("div").each(function(i){
        if($(this).attr('id')!=undefined){
            if($(this).attr('id').indexOf("fieldExp")!=-1){
                var num=  $(this).attr('id').replace("fieldExp","");
                trgtFieldEnNm=$("#trgtFieldEnNm"+num).combobox("getValue");
                if(trgtFieldEnNm.indexOf("目标接口字段")!=-1){
                    $.messager.alert("空值校验","请选择目标接口字段");
                    return false;
                }
                fieldMapgWay=$("#fieldMapgWay"+num).combobox("getValue");
                if(fieldMapgWay.indexOf("字段映射方式")!=-1){
                    $.messager.alert("空值校验","请选择字段映射方式");
                    return false;
                }

                if(fieldMapgWay=='DFI1405'||fieldMapgWay=='DFI1406'||fieldMapgWay=='DFI1407'||fieldMapgWay=='DFI1408'||fieldMapgWay=='DFI1409'||fieldMapgWay=='DFI1410'){
                    var sorcStrs=$("#sorcFieldEnNm"+num).combobox("getValue").split('@');
                    if(sorcStrs.indexOf("来源接口字段")!=-1){
                        $.messager.alert("空值校验","请选择来源接口字段");
                        return false;
                    }
                    sorcTablConfId=sorcStrs[1];
                    sorcFieldEnNm=sorcStrs[0];
                }
                if(fieldMapgWay=='DFI1499'||fieldMapgWay=='DFI1402'){
                    udfExps=$("#udfExps"+num).textbox('getValue');
                    if(udfExps=="表达式"||udfExps==''){
                        $.messager.alert("空值校验","表达式不能为空！");
                        return false;
                    }
                }
                var data={
                    mapgId:mapgId,
                    trgtTablId:trgtTablId,
                    trgtFieldEnNm:trgtFieldEnNm,
                    fieldMapgWay:fieldMapgWay,
                    sorcTablConfId:sorcTablConfId,
                    sorcFieldEnNm:sorcFieldEnNm,
                    udfExps:udfExps
                }
                datas.push(data);
            }
        }
        var len=$(".rootField").find("div").length-1;
        if(i==len){
            var btnTex=$("#fieldConfBtn").text();
            if($.trim(btnTex)=='修改配置'){
                $.messager.confirm('操作确认',"您确认提交配置信息?",function(r){
               if(r){
                   $.ajax(baseUrl+"/difConf/deleteDifFieldMapgConf",{
                       type:'post',
                       data:{mapgId:mapgId},
                       success:function(result){
                           if(result){
                               $.ajax(baseUrl+"/difConf/saveDifFieldMapgConf",{
                                   type:'post',
                                   data:JSON.stringify(datas),
                                   contentType:"application/json",
                                   success:function(result){
                                       $.messager.alert("操作提示",result.message);
                                       closeWin('difFieldMapgConf');
                                       $('#difConf').datagrid('reload');
                                   }
                               });
                           }
                       }
                   });
               }
                })

            }else{
                $.messager.confirm('操作确认',"您确认提交配置信息?",function(r){
                    if(r){
                        $.ajax(baseUrl+"/difConf/saveDifFieldMapgConf",{
                            type:'post',
                            data:JSON.stringify(datas),
                            contentType:"application/json",
                            success:function(result){
                                $.messager.alert("操作提示",result.message);
                                $('#difConf').datagrid('reload');
                                closeWin('difFieldMapgConf');
                            }
                        });
                    }
                })
            }

        }
    });
};
/**
 * 删除筛选条件
 */
var deleteFieldMapgConf=function(){
    if($(".rootField").find("div").length==0){
        $.messager.alert("删除校验","没有配置字段映射，无法删除");
        return false;
    }
    var mapgId=$("#paramMapgId").val();
    $.messager.confirm("删除确认","您确认删除？",function(r){
        if(r){
            $.ajax(baseUrl+"/difConf/deleteDifFieldMapgConf",{
                type:'post',
                data:{mapgId:mapgId},
                success:function(result){
                    if(result){
                        $.messager.alert("删除提示","删除成功");
                        $('#difConf').datagrid('reload');
                        closeWin('difFieldMapgConf');
                    }else{
                        $.messager.alert("删除提示","删除失败");
                    }
                }
            });
        }
    })
}
var backTablQuery=function(){
    location.href =baseUrl+"/jsp/apm/dataPort/difTablMapgInfo.jsp";
}

















