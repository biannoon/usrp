
$(function(){
    //初始化查询条件下拉列表
    initDictionaryCode('#tableStatus', 'DFI02', true);
    initDictionaryCode('#isStatsRule', 'SYS02', true);
    initDictionaryCode('#isDtlRule', 'SYS02', true);
    initDictionaryCode('#ruleCfgTyp', 'DFI15', true);
    initDifChkRuleTabl();
    //初始化规则配置方式选择根据不同值显示相应的元素
    ruleCfgTypConfSelected();
    initDictryCdValScope();
    chkTypSelected();
    fieldEnNmConfSelected();
    cmprTrgtTypConfSelected();
    fieldEnNmConfCmprSelect();
    initRuleCombobox();
    isDtlRuleConfSelect();

});
/**
 * 初始化规则列表
 * @param node
 */
var initDifChkRuleTabl=function(){
    $('#difChkRuleConfList').datagrid({
        url:baseUrl+'/chkRuleConf/findChkRuleConfList',
        fitColumns: true,
        border: true,
        pagination: true,
        idField: "ruleId",
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
            confId:'ttttt____'
        },
        //固定列字段 不受横向滚动条拉动影响
        //frozenColumns : [ [ ] ],
        columns: [[{
            field: "checkbox", checkbox: true
        }, {
                field: "ruleId", title: "规则编号", width: 50
            },{
                field: "ruleNm", title: "规则名称", width: 50
            },
            {
                field: "ruleComnt", title: "规则描述", width: 50
            },
            {
                field: "chkConfNm", title: "规则分类", width: 50
            },
            {
            field: "stus", title: "状态", width: 60, align: 'center',dictry:'DFI02',formatter: formatData
        },{
                field: "creatr", title: "创建人", width: 50
         },{
                field: "crtDt", title: "创建日期", width: 50
          },{
                field: "finlModifr", title: "最后修改人", width: 50
            },{
                field: "finlModfyDt", title: "最后修改日期", width: 50
            },
        ]]
    });
};
/**
 * 条件查询
 */
var doChkRuleSearch=function () {
    var rowT=$("#tablChkConf").datagrid('getSelected');
    $('#difChkRuleConfList').datagrid('load', {
        confId:rowT.confId,
        ruleNm:$("#ruleNm").textbox('getValue'),
        ruleId:$("#ruleId").textbox('getValue'),
        stus:$("#tableStatus").combobox('getValue'),
        isStatsRule:$("#isStatsRule").combobox('getValue'),
        isDtlRule:$("#isDtlRule").combobox('getValue'),
        ruleCfgTyp:$("#ruleCfgTyp").combobox("getValue")
    });
}
/**
 * 弹出规则配置窗口
 */
var openChkRuleConfWin = function() {
    $('#chkRulUpd').hide();
    $('#chkRulSave').show();
    this.clearAllElement();
    var _win = $('#difChkRuleConf');
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
            $('#difChkRuleForm').form('clear');
        }
    });
    // initRuleCombobox();
    $(".funway").hide();
    $(".sqlway").hide();
    _win.window('open');
};
/**
 * 查询校验字段下拉列表
 */
var initRuleFieldSelect=function(id){
    var row=$("#tablChkConf").datagrid('getSelected');
    $.ajax(baseUrl+"/chkRuleConf/getFieldByTabl",{
        type:'post',
        async:false,
        data:{tablId:row.tablId},
        success:function(result){
            if(result!=null){
                $('#'+id).combobox({
                    data:result,
                    valueField:'fieldEnNm',
                    textField:'fieldCnNm'
                });
            }
        }
    })
}
/**
 * 选择规则配置方式时，根据不同的配置方式显示不同的配置元素
 */
var ruleCfgTypConfSelected=function(){
    $("#ruleCfgTypConf").combobox({
        onSelect:function(item){
            clearElement(this);
            if(item.value=='DFI1501'){
                $(".funway").show();
                $(".sqlway").hide();
            }else{
                $(".funway").hide();
                $(".sqlway").show();
            }
        }
    });
};
/**
 * 选择校验类型对其他选项的约束
 */
var chkTypSelected=function(){
    $("#chkTypConf").combobox({
        onChange:function(item,oitem){
            if(item==''){
                return false;
            }
            clearElement(this);
            var chkField=$("#fieldEnNmConf").combobox("getValue");
            if(chkField==''){
                $("#chkTypConf").combobox('clear');
                $.messager.alert("空值校验","请先选择校验字段");
                return  false;
            }
            //空值校验
            if(item=="DFI0401"){
                $("#valScopeBtn").attr("style","display:none");
                $(".fieldEnNmConfCmpr").attr("style","display:none");
                $("#batchDate").hide();
                // 设置比较方式不可编辑，不进行空值校验
                setCmprManrDisable(false,true);
                // 设置比较对象类型不可编辑，不进行空值校验
                setCmprTrgtTypDisable(false,true);
                // 设置比较内容不可编辑，不进行空值校验
                setCmprTrgtContentDisable(true);


            }
            //长度校验
            else if(item=="DFI0402"){
                if(!chkDataType('VARCHAR',chkField)){
                    $("#chkTypConf").combobox('clear');
                    $.messager.alert("数据类型校验","校验字段和校验类型不匹配,字段类型必须为字符类型字段");
                    return false;
                }
                $("#valScopeBtn").attr("style","display:none");
                $(".fieldEnNmConfCmpr").attr("style","display:none");
                // 设置比较方式不可编辑，不进行空值校验
                setCmprManrDisable(true,false);
                // 设置比较对象类型不可编辑，不进行空值校验
                setCmprTrgtTypDisable(true,false);
                $("#cmprTrgtTypConf").combobox({
                    loadFilter: function(data){
                        var resData=[];
                      for(var i in data){
                        if(data[i].value=='DFI0601'){
                            resData.push(data[i]);
                        }
                      }
                      return resData;
                    }
                });
                // 设置比较内容不可编辑，不进行空值校验
                setCmprTrgtContentDisable(false);
            }
            //值域校验
            else if(item=="DFI0403"){
                if(!chkDataType('VARCHAR',chkField)){
                    $("#chkTypConf").combobox('clear');
                    $.messager.alert("数据类型校验","校验字段和校验类型不匹配,字段类型必须为字符类型字段");
                    return false;
                }
                $("#valScopeBtn").attr("style","display:black");
                $(".fieldEnNmConfCmpr").attr("style","display:none");
                // 设置比较方式不可编辑，不进行空值校验
                setCmprManrDisable(false,true);
                // 设置比较对象类型不可编辑，不进行空值校验
                setCmprTrgtTypDisable(false,true);
                // 设置比较内容不可编辑，不进行空值校验
                setCmprTrgtContentDisable(true);
             //四则运算校验
            }else if(item=="DFI0404"){
                var  dataTypes='BIGINT,DECIMAL,INT,NUMBER';
                if(!chkDataType(dataTypes,chkField)){
                    $("#chkTypConf").combobox('clear');
                    $.messager.alert("数据类型校验","校验字段和校验类型不匹配");
                    return false;
                }
                $("#valScopeBtn").attr("style","display:none");
                $(".fieldEnNmConfCmpr").attr("style","display:none");
                // 设置比较方式不可编辑，不进行空值校验
                setCmprManrDisable(true,false);
                // 设置比较对象类型不可编辑，不进行空值校验
                setCmprTrgtTypDisable(false,true);
                // 设置比较内容不可编辑，不进行空值校验
                setCmprTrgtContentDisable(false);
                //日期比较校验
            }else if(item=="DFI0405"){
                var  dataTypes='DATE,VARCHAR';
                if(!chkDataType(dataTypes,chkField)){
                    $("#chkTypConf").combobox('clear');
                    $.messager.alert("数据类型校验","校验字段和校验类型不匹配");
                    return false;
                }
                $("#cmprTrgtTypConf").combobox({
                    loadFilter: function(data){
                        var resData=[];
                        for(var i in data){
                            if(data[i].value!='DFI0601'){
                                resData.push(data[i]);
                            }
                        }
                        return resData;
                    }
                });
                initRuleFieldSelect("fieldEnNmConfCmpr");
                $("#valScopeBtn").attr("style","display:none");
                $(".fieldEnNmConfCmpr").attr("style","display:black");
                // 设置比较方式不可编辑，不进行空值校验
                setCmprManrDisable(true,false);
                // 设置比较对象类型不可编辑，不进行空值校验
                setCmprTrgtTypDisable(true,false);
                // 设置比较内容不可编辑，不进行空值校验
                setCmprTrgtContentDisable(false);
                //数值比较校验
            }else if(item=="DFI0406"){
                var  dataTypes='BIGINT,DECIMAL,INT,NUMBER';
                if(!chkDataType(dataTypes,chkField)){
                    $("#chkTypConf").combobox('clear');
                    $.messager.alert("数据类型校验","校验字段和校验类型不匹配");
                    return false;
                }
                initRuleFieldSelect("fieldEnNmConfCmpr");
                $("#cmprTrgtTypConf").combobox({
                    loadFilter: function(data){
                        var resData=[];
                        for(var i in data){
                            if(data[i].value=='DFI0601'||data[i].value=='DFI0602'){
                                resData.push(data[i]);
                            }
                        }
                        return resData;
                    }
                });
                $("#valScopeBtn").attr("style","display:none");
                $(".fieldEnNmConfCmpr").attr("style","display:black");
                // 设置比较方式不可编辑，不进行空值校验
                setCmprManrDisable(true,false);
                // 设置比较对象类型不可编辑，不进行空值校验
                setCmprTrgtTypDisable(true,false);
                // 设置比较内容不可编辑，不进行空值校验
                setCmprTrgtContentDisable(false);
                //身份证校验
            }
            // else if(item=="DFI0407"){
            //     $("#valScopeBtn").attr("style","display:none");
            //     $(".fieldEnNmConfCmpr").attr("style","display:none");
            //     // 设置比较方式不可编辑，不进行空值校验
            //     setCmprManrDisable(false,true);
            //     // 设置比较对象类型不可编辑，不进行空值校验
            //     setCmprTrgtTypDisable(false,true);
            //     // 设置比较内容不可编辑，不进行空值校验
            //     setCmprTrgtContentDisable(true);
            //     //正则表达式校验
            // }
            // else if(item=="DFI0408"){
            //     $("#valScopeBtn").attr("style","display:none");
            //     $(".fieldEnNmConfCmpr").attr("style","display:none");
            //     // 设置比较方式不可编辑，不进行空值校验
            //     setCmprManrDisable(false,true);
            //     // 设置比较对象类型不可编辑，不进行空值校验
            //     setCmprTrgtTypDisable(false,true);
            //     // 设置比较内容不可编辑，不进行空值校验
            //     setCmprTrgtContentDisable(false);
            // }
        }
    });
};
var fieldEnNmConfSelected=function(){
    $("#fieldEnNmConf").combobox({
        onSelect:function(){
            clearElement(this);
        }
    })
}
/**
 * 清空所有组件的值
 */
var clearAllElement=function(){
    $("#ruleIdConf").textbox('setValue','')
    $("#ruleIdConf").textbox("readonly",false);
    $("#ruleNmConf").textbox('setValue','')
    $("#isStatsRuleConf").combobox('setValue','')
    $("#isDtlRuleConf").combobox('setValue','')
    $("#ruleCfgTypConf").combobox('setValue','')
    $("#chkTypConf").combobox('setValue','')
    $("#cmprManrConf").combobox('setValue','')
    $("#cmprTrgtTypConf").combobox('setValue','')
    $("#fieldEnNmConfCmpr").combobox('setValue','')
    $("#cmprTrgtContentConf").textbox('setValue','')
    $("#ruleComntConf").textbox('setValue','')
    $("#chkRuleSql").textbox('setValue','')
}
/**
 * 设置检验类型的值为空
 */
var clearElement=function(obj){
    if($(obj).attr("id")=="ruleCfgTypConf"){
        $("#chkTypConf").combobox('setValue','');
        $("#cmprManrConf").combobox('setValue','');
        $("#cmprTrgtTypConf").combobox('setValue','');
        $("#fieldEnNmConfCmpr").combobox('setValue','');
        $("#cmprTrgtContentConf").textbox('setValue','');
        $("#chkRuleSql").textbox('setValue','');
    }else if($(obj).attr("id")=="fieldEnNmConf"){
        $("#chkTypConf").combobox('setValue','');
        $("#cmprManrConf").combobox('setValue','');
        $("#cmprTrgtTypConf").combobox('setValue','');
        $("#fieldEnNmConfCmpr").combobox('setValue','');
        $("#cmprTrgtContentConf").textbox('setValue','');
    }else if($(obj).attr("id")=="chkTypConf"){
        $("#cmprManrConf").combobox('setValue','');
        $("#cmprTrgtTypConf").combobox('setValue','');
        $("#fieldEnNmConfCmpr").combobox('setValue','');
        $("#cmprTrgtContentConf").textbox('setValue','');
    }else if($(obj).attr("id")=="cmprTrgtTypConf"){
        $("#fieldEnNmConfCmpr").combobox('setValue','');
        $("#cmprTrgtContentConf").textbox('setValue','');
    }
}
/**
 * 数据类型校验
 * @param fieldNm
 */
var chkDataType=function(dataType,fieldNm){
    var row=$("#tablChkConf").datagrid('getSelected');
    var flag=false;
    $.ajax(baseUrl+"/chkRuleConf/getFieldDataType",{
        type:'post',
        async:false,
        data:{tablId:row.tablId,fieldEnNm:fieldNm},
        success:function(result){
            var strs=dataType.split(",");
            if(strs.length>1){
                for(var j in strs){
                    if(result.toLowerCase().indexOf(strs[j].toLowerCase())!=-1){
                        flag= true;
                    }
                }
            }else{
                if(result.toLowerCase().indexOf(dataType.toLowerCase())<0){
                    flag= false;
                }else{
                    flag= true;
                }
            }

        }
    });
    return flag;
}
/**
 * 设置比较方式编辑状态
 * @param required
 * @param disabled
 */
var setCmprManrDisable=function(required,disabled){
    $("#cmprManrConf").combobox({
        required:required,
        disabled:disabled
    });
}
/**
 * 设置比较对象类型编辑状态
 * @param required
 * @param disabled
 */
var setCmprTrgtTypDisable=function(required,disabled){
    $("#cmprTrgtTypConf").combobox({
        required:required,
        disabled:disabled
    });
}
/**
 * 设置比较内容编辑状态
 * @param required
 * @param disabled
 */
var setCmprTrgtContentDisable=function(disabled){
    $("#cmprTrgtContentConf").textbox("readonly",disabled);
}
/**
 * 选择比较对象类型时控制比较内容的选择范围
 */
var cmprTrgtTypConfSelected=function(){
    $("#cmprTrgtTypConf").combobox({
        onSelect:function(item){
            clearElement(this);
            var chkTypConf=$("#chkTypConf").combobox("getValue");
            var fieldEnNmTrgt=$("#fieldEnNmConf").combobox("getValue");
            //校验类型为日期比较
            if(chkTypConf=='DFI0405'){
                //比较对象类型为系统日期
                if(item.value=='DFI0603'){
                    setCmprTrgtContentDisable(true);
                    $(".fieldEnNmConfCmpr").attr("style","display:none");
                 //比较对象类型为批量日期
                }else if(item.value=='DFI0604'){
                    setCmprTrgtContentDisable(true);
                    $(".fieldEnNmConfCmpr").attr("style","display:none");
                 //比较对象类型为目标字段
                }else if(item.value=='DFI0602'){
                    setCmprTrgtContentDisable(true);
                    $(".fieldEnNmConfCmpr").attr("style","display:block");
                    $("#fieldEnNmConfCmpr").combobox({
                        loadFilter: function(data){
                            var resData=[];
                            for(var i in data){
                                if(data[i].fieldEnNm!=fieldEnNmTrgt){
                                    resData.push(data[i]);
                                }
                            }
                            return resData;
                        }
                    });
                }
            }else if(chkTypConf=='DFI0406'){
                if(item.value=='DFI0601'){
                    setCmprTrgtContentDisable(false);
                    $(".fieldEnNmConfCmpr").attr("style","display:none");
                }else{
                    setCmprTrgtContentDisable(true);
                    $(".fieldEnNmConfCmpr").attr("style","display:block");
                    $("#fieldEnNmConfCmpr").combobox({
                        loadFilter: function(data){
                            var  dataTypes='BIGINT,DECIMAL,INT,NUMBER';
                            var resData=[];
                            for(var i in data){
                                if(data[i].fieldEnNm!=fieldEnNmTrgt){
                                    resData.push(data[i]);
                                }
                            }
                            return resData;
                        }
                    });
                }
            }
        }
    })
};
//被选择字段赋值被比较内容
var fieldEnNmConfCmprSelect=function(){
    $("#fieldEnNmConfCmpr").combobox({
        onSelect:function(item){
            $("#cmprTrgtContentConf").textbox("setValue",item.fieldEnNm);
        }
    })
}
//统计查询校验SQL
var saveChkRule=function(){
    var rowT=$("#tablChkConf").datagrid('getSelected');
    var fieldEnNm ='';
    var chkTyp ='';
    var cmprManr ='';
    var cmprTrgtTyp ='';
    var cmprTrgtContent ='';
    var chkRuleSql  ='';
    var ruleCfgTyp=$("#ruleCfgTypConf").combobox("getValue");
    var key=$("#ruleIdConf").textbox('getValue');
    fieldEnNm=$("#fieldEnNmConf").combobox("getValue");
    if(ruleCfgTyp=='DFI1501'){
        chkTyp=$("#chkTypConf").combobox("getValue");
        cmprManr=$("#cmprManrConf").combobox("getValue");
        cmprTrgtTyp=$("#cmprTrgtTypConf").combobox("getValue");
        cmprTrgtContent=$("#cmprTrgtContentConf").textbox("getValue");
    }else{
        chkRuleSql=$("#chkRuleSql").textbox("getValue");
    }
    $.ajax(baseUrl+"/chkRuleConf/chkKey",{
        type:'post',
        async:false,
        data:{
            ruleId:key
        },
        success:function(result){
            if(result.success){
                $.messager.confirm("新增确认","您确认新增校验规则？",function(r){
                    if(r){
                        $.ajax(baseUrl+"/chkRuleConf/saveChkRule",{
                            type:'post',
                            async:false,
                            data:{
                                ruleId:$("#ruleIdConf").textbox("getValue"),
                                tablId:rowT.tablId,
                                confId:rowT.confId,
                                ruleNm:$("#ruleNmConf").textbox("getValue"),
                                isStatsRule:$("#isStatsRuleConf").combobox("getValue"),
                                isDtlRule:$("#isDtlRuleConf").combobox("getValue"),
                                ruleCfgTyp:$("#ruleCfgTypConf").combobox("getValue"),
                                fieldEnNm:fieldEnNm,
                                chkTyp:chkTyp,
                                cmprManr:cmprManr,
                                cmprTrgtTyp:cmprTrgtTyp,
                                cmprTrgtContent:cmprTrgtContent,
                                chkRuleSql:chkRuleSql,
                                ruleComnt:$("#ruleComntConf").textbox("getValue"),
                                tablEnNm:rowT.enName,
                                dataSorceId:rowT.dataSorceId

                            },
                            success:function(result){
                                if(result!=null){
                                    $.messager.alert("操作提示",result.message);
                                    if(result.success){
                                        $("#difChkRuleConfList").datagrid('reload');
                                        closeWin('difChkRuleConf');
                                    }
                                }
                            }
                        });
                    }

                })
            }else{
                $.messager.alert("操作提示",result.message);
                return false;
            }
        }
    });

}
//规则配置=====================================end
//值域选择======================================start
/**
 * 值域选择字典列表
 */
var initDictryCdValScope=function(){
    $('#dictryCdValScopeList').datagrid({
        url:baseUrl+'/chkRuleConf/getSysDictryCdList',
        fitColumns: true,
        border: true,
        pagination: true,
        idField: "dictryId",
        pageSize: 10,
        pageList: [10, 20, 30],
        singleSelect: false,
        checkOnSelect: true,
        selectOnCheck: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        columns: [[{
            field: "dictryId", title: "字典代码",checkbox:true, width: 50
        },
            {
                field: "dictryNm", title: "字典名称", width: 100, align: 'center',
            },
            {
                field: "pareDictryId", title: "上级代码", width: 60, align: 'center'
            }, {
                field: "blngtoDictryId", title: "所属字典代码", width: 150, align: 'center'
            },
        ]]
    });
};
/**
 * 值域代码选择条件查询
 */
var doSearchValScope=function () {
    $('#dictryCdValScopeList').datagrid('load', {
        dictryNm:$("#dictryNm").textbox('getValue'),
        pareDictryId:$("#pareDictryId").textbox('getValue'),
    });
};
/**
 * 弹出规则配置窗口
 */
var openDictryCdValScope = function() {
    var _win = $('#dictryCdValScope');
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
        inline:false
    });
    _win.window('open');
};
var cofirmValScope=function(id){
    var row=$("#dictryCdValScopeList").datagrid("getChecked");
    var valScopes=[];
    for(var i in row){
        valScopes.push("'"+row[i].dictryId+"'");
    }
    $("#cmprTrgtContentConf").textbox("setValue",valScopes);
    closeWin(id);
}
var isDtlRuleConfSelect=function(){
    $("#isDtlRuleConf").combobox({
        onChange:function (newValue, oldValue) {
            var row=$("#tablChkConf").datagrid('getSelected');
            if(newValue=='SYS0201'&&(row.ruleRsTablNm==null||row.ruleRsTablNm=='')){
                $("#isDtlRuleConf").combobox('setValue','SYS0202');
                $.messager.alert("","校验结果存放表名为空，不能配置为质量明细校验规则！");
                return false;
            }
        }
    })
}
//值域选择==========================================end
//接口详细信息查询=====================================start
/**
 * 弹出接口详细信息窗口
 */
var openTablDetail = function() {
    var _win = $('#dicTablDatil');
    _win.window({
        width: '80%',
        height: '95%',
        left:'10%',
        right:'10%',
        top:0,
        collapsible: false,
        maximizable: true,
        minimizable: false,
        resizable: false,
        modal: true,
        inline:false
    });
    _win.window('open');
    initTablDetail();
};
//初始化接口列表
var initTablDetail=function(){
    var dataSorceId=$("#dataSorceId").val();
    $('#table-interface-data-grid').datagrid({
        url: baseUrl + '/tableInterface/getList',
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
        queryParams:{dataSorceId:dataSorceId,bizClsf:$("#bizClsf").val()},
        //固定列字段 不受横向滚动条拉动影响
        //frozenColumns : [ [ ] ],
        columns: [[{
            field: "tableId", title: "编号", width: 50, checkbox: true
        }, {
            field: "enName", title: "英文名称", width: 100, align: 'center',
            formatter: function (value, row, index) {
                return '<a href="javascript: void(0)" onclick="openFieldDetail(\'' + row.tableId + '\')" style="text-decoration:none">' + value + '</a>';
            }
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
 * 接口详情条件查询
 */
var queryTablDetail=function () {
    var dataSorceId=$("#dataSorceId").val();
    var queryParams = {
        cnName: $('#table-interface-query-cn-name').textbox('getValue'),
        enName: $('#table-interface-query-en-name').textbox('getValue'),
        bizClsf:$('#bizClsf').val(),
        dataSorceId:$('#dataSorceId').val(),
    }
    $('#table-interface-data-grid').datagrid('load', queryParams);
};
//接口字段信息窗口
var openFieldDetail = function(tablId) {
    var _win = $('#table-field-data-grid-win');
    _win.window({
        width: '70%',
        height: '95%',
        left:'15%',
        right:'15%',
        top:0,
        collapsible: false,
        maximizable: true,
        minimizable: false,
        resizable: false,
        modal: true,
        inline:false
    });
    _win.window('open');
    initFieldDetail(tablId);
};
/**
 * 初始化接口字段信息
 * @param id
 */
var initFieldDetail=function(id){
    $("#table-field-data-grid").datagrid({
        url: baseUrl + '/tableField/queryList',
        fit : true,
        fitColumns: true,
        border: true,
        pagination: true,
        pageSize: 10,
        pageList: [10, 20, 30],
        singleSelect: false,
        checkOnSelect: true,
        selectOnCheck: true,
        remoteSort: false,
        striped: true,
        nowrap: false,
        queryParams: {
            interfaceId: id
        },
        columns: [[{
            field: "tablId", title: "接口id", hidden: true
        }, {
            field: "fieldEnNm", title: "英文名称", width: 100, align: 'center'
        }, {
            field: "fieldCnNm", title: "中文名称", width: 100, align: 'center'
        }, {
            field: "dataTyp", title: "字段类型", width: 60, align: 'center'
        }, {
            field: "dataLength", title: "字段长度", width: 40, align: 'center'
        }, {
            field: "dictryCd", title: "引用代码值", width: 60, align: 'center'
        }, {
            field: "uniqIndxFlg", title: "是否唯一索引", width: 45, align: 'center'
        }]]
    })
};
//接口详细信息查询========================================end
//规则修改==================================================================start
var chkRuleConfUpd = function() {
    // initRuleCombobox();
    var row=$("#difChkRuleConfList").datagrid("getChecked");
    if(row.length<1){
        $.messager.alert("操作提示","请选择一行");
        return false;
    }
    if(row[0].stus!='DFI0201'&&row[0].stus!='DFI0203'){
        $.messager.alert("修改提示","当前状态不能修改！");
        return false;
    }
    $('#chkRulUpd').show();
    $('#chkRulSave').hide();
    var ruleId=row[0].ruleId;
    $.ajax(baseUrl+"/chkRuleConf/findChkRuleOne",{
        type:'post',
        async:false,
        data:{
            ruleId:ruleId
        },
        success:function(result){
            if(result!=null){
                $("#stus").val(result.stus);
                $("#creatr").val(result.creatr);
                $("#crtDt").val(result.crtDt);
                if(result.ruleCfgTyp=="DFI1501"){
                    $(".funway").show();
                    $(".sqlway").hide();
                    chkTypSelected();
                    setValuesFun(result);
                }else{
                    $(".funway").hide();
                    $(".sqlway").show();
                    chkTypSelected();
                    setValuesSql(result);
                }
                openWin();
            }
        }
    });
};
var initRuleCombobox=function(){
    //初始化是否质量统计校验规则
    initDictionaryCode('#isStatsRuleConf', 'SYS02', false);
    //初始化是否质量明细校验规则
    initDictionaryCode('#isDtlRuleConf', 'SYS02', false);
    //初始化规则配置方式
    initDictionaryCode('#ruleCfgTypConf', 'DFI15', false);
    //初始化校验类型
    initDictionaryCode('#chkTypConf', 'DFI04', false);
    //初始化比较方式
    initDictionaryCode('#cmprManrConf', 'DFI05', false);
    //初始化被比较对象
    initDictionaryCode('#cmprTrgtTypConf', 'DFI06', false);

}
var openWin=function(){
    var _win = $('#difChkRuleConf');
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
        inline:false
    });
    _win.window('open');
};
//设置功能配置方式的值
var setValuesFun=function(obj){
    $("#ruleIdConf").textbox("setValue",obj.ruleId);
    $("#ruleIdConf").textbox("readonly",true);
    $("#ruleNmConf").textbox("setValue",obj.ruleNm);
    $("#isStatsRuleConf").combobox("setValue",obj.isStatsRule);
    $("#isDtlRuleConf").combobox("setValue",obj.isDtlRule);
    $("#ruleCfgTypConf").combobox("setValue",obj.ruleCfgTyp);
    $("#fieldEnNmConf").combobox("setValue",obj.fieldEnNm);
    $("#chkTypConf").combobox("setValue",obj.chkTyp);
    $("#cmprManrConf").combobox("setValue",obj.cmprManr);
    $("#cmprTrgtTypConf").combobox("setValue",obj.cmprTrgtTyp);
    $("#ruleComntConf").textbox("setValue",obj.ruleComnt);
    $("#cmprTrgtContentConf").textbox("setValue",obj.cmprTrgtContent);
    $("#fieldEnNmConfCmpr").combobox("setValue",obj.cmprTrgtContent);

}
//设置SQ配置方式的值
var setValuesSql=function(obj){
    $("#ruleIdConf").textbox("setValue",obj.ruleId);
    $("#ruleIdConf").textbox("readonly",true);
    $("#ruleNmConf").textbox("setValue",obj.ruleNm);
    $("#isStatsRuleConf").combobox("setValue",obj.isStatsRule);
    $("#isDtlRuleConf").combobox("setValue",obj.isDtlRule);
    $("#ruleCfgTypConf").combobox("setValue",obj.ruleCfgTyp);
    $("#fieldEnNmConf").combobox("setValue",obj.fieldEnNm);
    $("#chkRuleSql").textbox("setValue",obj.chkRuleSql);
    $("#ruleComntConf").textbox("setValue",obj.ruleComnt);
};
/**
 * 修改校验规则
 */
var updChkRule=function(){
    var row=$("#tablChkConf").datagrid('getSelected');
    var fieldEnNm ='';
    var chkTyp ='';
    var cmprManr ='';
    var cmprTrgtTyp ='';
    var cmprTrgtContent ='';
    var chkRuleSql  ='';
    var ruleCfgTyp=$("#ruleCfgTypConf").combobox("getValue");
    fieldEnNm=$("#fieldEnNmConf").combobox("getValue");
    if(ruleCfgTyp=='DFI1501'){
        chkTyp=$("#chkTypConf").combobox("getValue");
        cmprManr=$("#cmprManrConf").combobox("getValue");
        cmprTrgtTyp=$("#cmprTrgtTypConf").combobox("getValue");
        cmprTrgtContent=$("#cmprTrgtContentConf").textbox("getValue");
    }else{
        chkRuleSql=$("#chkRuleSqlConf").textbox("getValue");
    }
    $.messager.confirm("修改确认确认","确认修改校验规则？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/chkRuleConf/updChkRule",{
                type:'post',
                async:false,
                data:{
                    ruleId:$("#ruleIdConf").textbox("getValue"),
                    confId:row.confId,
                    tablId:row.tablId,
                    ruleNm:$("#ruleNmConf").textbox("getValue"),
                    isStatsRule:$("#isStatsRuleConf").combobox("getValue"),
                    isDtlRule:$("#isDtlRuleConf").combobox("getValue"),
                    ruleCfgTyp:$("#ruleCfgTypConf").combobox("getValue"),
                    fieldEnNm:fieldEnNm,
                    chkTyp:chkTyp,
                    cmprManr:cmprManr,
                    cmprTrgtTyp:cmprTrgtTyp,
                    cmprTrgtContent:cmprTrgtContent,
                    chkRuleSql:chkRuleSql,
                    ruleComnt:$("#ruleComntConf").textbox("getValue"),
                    stus:$("#stus").val(),
                    creatr:$("#creatr").val(),
                    crtDt:$("#crtDt").val(),
                    tablEnNm:row.enName,
                    dataSorceId:row.dataSorceId

                },
                success:function(result){
                    if(result!=null){
                        $.messager.alert("操作提示",result.message);
                        if(result.success){
                            $("#difChkRuleConfList").datagrid('load');
                            closeWin('difChkRuleConf');
                        }
                    }
                }
            });
        }
    });
};
/**
 * 发布
 * @returns {boolean}
 */
var dopush=function(){
    var row=$("#difChkRuleConfList").datagrid("getChecked");
    if(row.length<1){
        $.messager.alert("操作提示","请选择一行");
        return false;
    }
    if(row[0].stus!='DFI0201'&&row[0].stus!='DFI0203'){
        $.messager.alert("发布提示","当前状态不能发布！");
        return false;
    }
    $.messager.confirm("发布确认","确认发布校验规则？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/chkRuleConf/updChkRuleStatus",{
                type:'post',
                async:false,
                data:{
                    ruleId:row[0].ruleId,
                    stus:'DFI0202'
                },
                success:function(result){
                    if(result!=null){
                        $.messager.alert("操作提示",result.message);
                        $("#difChkRuleConfList").datagrid("reload");
                    }
                }
            });
        }
    });

};
/**
 * 撤销发布
 * @returns {boolean}
 */
var doNoPush=function(){
    var row=$("#difChkRuleConfList").datagrid("getChecked");
    if(row.length<1){
        $.messager.alert("操作提示","请选择一行");
        return false;
    }
    if(row[0].stus!='DFI0202'){
        $.messager.alert("撤销发布提示","只能撤销已发布的校验规则！");
        return false;
    }
    $.messager.confirm("撤销发布确认","确认撤销发布校验规则？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/chkRuleConf/updChkRuleStatus",{
                type:'post',
                async:false,
                data:{
                    ruleId:row[0].ruleId,
                    stus:'DFI0203'
                },
                success:function(result){
                    if(result!=null){
                        $.messager.alert("操作提示",result.message);
                        $("#difChkRuleConfList").datagrid("reload");
                    }
                }
            });
        }
    });

}
/**
 * 删除规则
 * @returns {boolean}
 */
var deleteChkRule=function(){
    var row=$("#difChkRuleConfList").datagrid("getChecked");
    if(row.length<1){
        $.messager.alert("操作提示","请选择一行");
        return false;
    }
    if(row[0].stus!='DFI0201'&&row[0].stus!='DFI0203'){
        $.messager.alert("删除提示","当前状态不能删除！");
        return false;
    }
    $.messager.confirm("删除确认","确认删除校验规则？",function(flag){
        if(flag){
            $.ajax(baseUrl+"/chkRuleConf/updChkRuleStatus",{
                type:'post',
                async:false,
                data:{
                    ruleId:row[0].ruleId,
                    stus:'DFI0204'
                },
                success:function(result){
                    if(result!=null){
                        $.messager.alert("操作提示",result.message);
                        $("#difChkRuleConfList").datagrid("reload");
                    }
                }
            });
        }
    });

};
var backTablQuery=function(){
    location.href =baseUrl+"/jsp/apm/chkruleconf/difChkRuleTabl.jsp";
}


