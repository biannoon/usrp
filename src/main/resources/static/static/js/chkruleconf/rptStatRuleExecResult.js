
$(function(){
    // $('#bussType').tree({
    //     url:'/usrp/difConf/getSysDictryCdListByLevel1'
    // });
    // $('#bussType').tree({onClick:function(node){
    //     $("#bizClsf").val(node.id);
    //     $('#tablChkConf').datagrid('load', {
    //         bizClsf: node.id
    //     });
    //
    // }});
    initDatabase('#dataSorcId',true);
    initDictionaryCode('#execTyp', 'DFI20', true);
    initTablChkConf();
})
var myformatter=function (date) {
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
var myparser=function (s) {
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
var initTablChkConf=function(){
    $('#ruleResultList').datagrid({
        url:baseUrl+'/rptStatRuleExecResult/queryRuleResultList',
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
        queryParams:{
            //初始化查询接口列表为空
            bizClsf: '   _'
        },
        //固定列字段 不受横向滚动条拉动影响
        //frozenColumns : [ [ ] ],
        columns: [[{
                field:'ck',checkbox:true
        },{
            field: "tablNmShow", title: "数据表名称", width: 150
        }, {
            field: "fieldNmShow", title: "检核字段名称", width: 100, align: 'center'
        }, {
            field: "batchDt", title: "批量日期", width: 100, align: 'center'
        },  {
            field: "ruleExecDt", title: "规则执行日期", width: 50, align: 'center'
        }, {
            field: "ruleId", title: "规则编号", width: 135, align: 'center'
        }, {
            field: "ruleNm", title: "规则名称", width: 60, align: 'center'
        }, {
            field: "rcdCnt", title: "检核记录总数", width: 60, align: 'center'
        }, {
            field: "trigRuleCnt", title: "触发规则记录数",width: 60, align: 'center'
        }, {
            field: "rate", title: "规则触发比率", width: 60, align: 'center'
        }]]
    });
};
//条件查询
var doRuleResultSearch=function () {
    $('#ruleResultList').datagrid('load', {
        ruleId:$("#ruleId").textbox('getValue'),
        tablCnNm:$("#cnNm").textbox('getValue'),
        batchDt:$("#batchDt").datebox('getValue'),
        execTyp:$("#execTyp").combobox('getValue'),
        dataSorcId:$("#dataSorcId").combobox('getValue'),
        ruleExecDt:$("#ruleExecDt").datebox('getValue')
    });
}
var downLoadExcel=function(){
    var row=$('#ruleResultList').datagrid("getChecked");
    if(row!=''&&row!=null){
        var resArray=new Array();
        for(var i in row){
            var rptStatRuleExecResult={
                batchDt:row[i].batchDt,
                execTyp:row[i].execTyp,
                ruleId:row[i].ruleId,
                ruleNm:row[i].ruleNm,
                tablId:row[i].tablId,
                tablEnNm:row[i].tablEnNm,
                tablCnNm:row[i].tablCnNm,
                fieldEnNm:row[i].fieldEnNm,
                fieldCnNm:row[i].fieldCnNm,
                rcdCnt:row[i].rcdCnt,
                trigRuleCnt:row[i].trigRuleCnt,
                ruleExecDt:row[i].ruleExecDt,
                rate:row[i].rate
            }
            resArray.push(rptStatRuleExecResult);
        }
        var url=encodeURI(baseUrl+"/rptStatRuleExecResult/downLoadExcel?resultList="+JSON.stringify(resArray))
        window.location.href = url;
    }else{
        window.location.href = baseUrl+"/rptStatRuleExecResult/downLoadExcel";
    }
    // $.post(baseUrl+"/rptStatRuleExecResult/downLoadExcel", {"batchDt":row.batchDt,"execTyp":row.execTyp,"ruleId":row.ruleId}, function(data, status) {
    //     if (status === "success") {
    //         console.log("下载成功");
    //     }
    // })

}



