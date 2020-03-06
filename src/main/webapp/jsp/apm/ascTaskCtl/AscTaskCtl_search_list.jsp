<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/18
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String taskId = (String) request.getAttribute("taskId");
    String flag = (String) request.getAttribute("flag");
    String winId = (String)request.getAttribute("winId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body style="background:#eee;">
    <table width="97%">
        <tr>
            <td valign="top">
                <div>
                    <div id="dg-search-tool" align="right">
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="assignVal()">确定</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="closeAscWindow('<%=winId%>')">关闭</a>
                    </div>
                    <!--数据网格-->
                    <table id="dg_depend_search" class="easyui-datagrid" style="">
                        <thead>
                        <tr>
                            <th data-options="field:'cb',checkbox:true"></th>
                            <th data-options="field:'taskId',width:100,align:'center'">任务编号</th>
                            <th data-options="field:'taskName',width:100,align:'center'">任务名称</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </td>
        </tr>
    </table>
<script type="text/javascript">
    //初始化页面
    $(function () {
        $('#dg_depend_search').datagrid({
            url:'<%=basePath%>AscTask/listAscTaskById?taskId=<%=taskId%>',
            fitColumns:true,
            singleSelect:true,
            pagePosition:'top',
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            toolbar: '#dg-search-tool',
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
            },
            onClickRow:function(rowIndex,rowData){
                var row = $('#dg_depend_search').datagrid('getSelected');
                if (row == null){
                    $.messager.alert("提示","请选择记录");
                }else{
                    var taskId = row.taskId;
                    //var taskName = row.taskName;
                    if ('<%=flag%>' == 'dependCfg'){
                        $('#dependId_insert').textbox('setValue',taskId);
                    }else if ('<%=flag%>' == 'triggerCfg'){
                        $('#trigId_insert').textbox('setValue',taskId);
                    }
                    $('#<%=winId%>').window('close');
                }
            }
        })
    })

    function assignVal(){
        var rows = $('#dg_depend_search').datagrid('getChecked');
        if (rows == null || rows == ''){
            $.messager.alert("提示","请选择任务");
        }else{
            var task = ';'
            $.each(rows,function (index,row) {
                var taskId = row.taskId;
                //var taskName = row.taskName;
                if ('<%=flag%>' == 'dependCfg'){
                    task = taskId;
                    $('#dependId_insert').textbox('setValue',task);
                }else if ('<%=flag%>' == 'triggerCfg'){
                    task = taskId;
                    $('#trigId_insert').textbox('setValue',task);
                }
                $('#<%=winId%>').window('close');
            })
        }
    }

    //关闭窗口
    function closeAscWindow(winId) {
        $("#"+winId).window('close');
    }

</script>
</body>
</html>
