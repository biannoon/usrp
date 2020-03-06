<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/22
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<%
    String taskId = (String) request.getAttribute("taskId");
%>
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
                <div id="dg_tool" align="right">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsert('win_ascTriggerCfg','任务新增','<%=basePath%>AscTask/toTriggerCfgInsert','900','350')">新增</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDelete('<%=basePath%>AscTask/deleteAscTriggerCfg' )">删除</a>
                </div>
                <table id="dg_triggerCfg" class="easyui-datagrid" style="width:1050px;height:350px;">
                    <thead>
                    <tr>
                        <th data-options="field:'cb',checkbox:true"></th>
                        <th data-options="field:'trigId',width:100,align:'center'">触发任务编号</th>
                        <th data-options="field:'taskName',width:100,align:'center'">触发任务名称</th>
                        <th data-options="field:'flag',width:100,align:'center',dictry:'ASC014',formatter:formatData">触发对象类型</th>
                        <th data-options="field:'frequency',width:100,align:'center',dictry:'ASC002',formatter:formatData">执行频率</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </td>
    </tr>
</table>
<!--动态生成模态框-->
<div id="win_ascTriggerCfg"></div>
<script type="text/javascript">
    $(function(){
        $('#dg_triggerCfg').datagrid({
            url:'<%=basePath%>AscTask/listAllAscTriggerCfg?taskId=<%=taskId%>',
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
            }
        });
       /* //数据网格自适应网页大小
        $('#dg_triggerCfg').resizeDataGrid(20, 20, 350, 900);
        // 当窗口大小发生变化时，调整DataGrid的大小
        $(window).resize(function() {
            $('#dg_triggerCfg').resizeDataGrid(20, 20, 350, 900);
        });*/
    })


    //新增窗口
    function toInsert(winId,title,url,width,height) {
        url = url + '?taskId=<%=taskId%>';
        openWindowSelf(winId,title,width,height,url);
    }

    function toDelete(url) {
        var row = $('#dg_triggerCfg').datagrid('getSelected');
        if (row == null){
            $.messager.alert("提示","请选择需要删除的记录");
        }else{
            var trigId = row.trigId;
            $.ajax({
                url:url,
                type:'post',
                dataType:'json',
                data:{"trigId":trigId,"taskId":'<%=taskId%>'},
                success:function (data) {
                    var data = JSON.stringify(data);
                    var data = eval('(' + data + ')');
                    if (data.success){
                        refreshDataGridBySelf('dg_triggerCfg');
                        $.messager.alert("提示","删除成功");
                    }else{
                        $.messager.alert("提示","删除失败")
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
