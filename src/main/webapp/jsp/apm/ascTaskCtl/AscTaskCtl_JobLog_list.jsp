<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/24
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<%
    String taskId = (String) request.getAttribute("taskId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ascTaskCtl/ascTaskCtl.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<table width="100%">
    <tr>
        <td>
            <div><!--数据网格-->
                <div id="dg_tool" style="padding:3px">
                    <table width="100%" style="text-align: center">
                        <tr>
                            <td align="right" style="font-size: small;font-family: Microsoft Yahei;">处理编号：</td>
                            <td align="left"><input class="easyui-textbox" id="jobId_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                            <td align="right" style="font-size: small;font-family: Microsoft Yahei;">批处理日期（起）：</td>
                            <td align="left"><input class="easyui-datebox" id="nextDate_start" style="height:20px;width:150px;border:1px solid #ccc"></td>
                            <td align="right" style="font-size: small;font-family: Microsoft Yahei;">批处理日期（止）：</td>
                            <td align="left"><input class="easyui-datebox" id="nextDate_end" style="height:20px;width:150px;border:1px solid #ccc"></td>
                            <td align="right" style="font-size: small;font-family: Microsoft Yahei;">运行结果：</td>
                            <td align="left"><input class="easyui-combobox" id="ret_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="8">
                                <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain=true onclick="doSearch()">查询</a>
                                <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="resetForm('form_search')">重置</a>
                                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain=true onclick="toDelete('<%=basePath%>AscTask/deleteAscJobLog')">删除</a>
                            </td>
                        </tr>
                        </table>
                </div>
                <table id="dg_jobLog" class="easyui-datagrid" style="width:1050px;height: 400px">
                    <thead>
                    <tr>
                        <th data-options="field:'cb',checkbox:true"></th>
                        <th data-options="field:'id',width:100,align:'center',hidden:true"></th>
                        <th data-options="field:'taskId',width:100,align:'center'">任务编号</th>
                        <th data-options="field:'jobId',width:100,align:'center'">处理编号</th>
                        <th data-options="field:'ret',width:100,align:'center',dictry:'ASC006',formatter:formatData">运行结果</th>
                        <th data-options="field:'nextDate',width:100,align:'center'">批处理日期</th>
                        <th data-options="field:'startTime',width:100,align:'center'">处理开始时间</th>
                        <th data-options="field:'endTime',width:100,align:'center'">处理结束时间</th>
                        <th data-options="field:'execTime',width:100,align:'center'">处理时长</th>
                        <th data-options="field:'jobName',width:100,align:'center'">处理名称</th>
                        <th data-options="field:'jobPara',width:100,align:'center'">处理参数</th>
                        <th data-options="field:'msg',width:100,align:'center'">运行信息</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    //初始化表格
    $(function(){
        $('#dg_jobLog').datagrid({
            url:'<%=basePath%>AscTask/listAllAscJobLog?taskId=<%=taskId%>',
            fitColumns:true,
            singleSelect:true,
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pagePosition:'top',
            toolbar:'#dg_tool',
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
            },
        });
        //初始化组合框
        initComboboxSelf('ret_se','ASC006','<%=basePath%>');
        /*//数据网格自适应网页大小
        $('#dg_jobLog').resizeDataGrid(20, 20, 350, 900);
        // 当窗口大小发生变化时，调整DataGrid的大小
        $(window).resize(function() {
            $('#dg_jobLog').resizeDataGrid(20, 20, 350, 900);
        });*/
    })


    //查询
    function doSearch() {
        $('#dg_jobLog').datagrid('load',{
            jobId: $('#jobId_se').val(),
            nextDate_start: $('#nextDate_start').val(),
            nextDate_end: $('#nextDate_end').val(),
            ret: $('#ret_se').val(),
        })
    }

    //删除
    function toDelete(url) {
        var row = $('#dg_dependCfg').datagrid('getSelections');
        if (row.length == 0){
            $.messager.alert("提示","请选择需要删除的记录");
        }else{
            var str = "";
            for(var i = 0;i < row.length; i++){
                if (i != row.length - 1){
                    str = str + row[i];
                }else{
                    str = str + row[i].id + "&";
                }
            }
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
    }
</script>
</body>
</html>
