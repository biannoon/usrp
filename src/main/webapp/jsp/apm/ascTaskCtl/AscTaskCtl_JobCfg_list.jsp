<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/22
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String taskId = (String) request.getAttribute("taskId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ascTaskCtl/ascTaskCtl.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<table width="100%">
    <tr>
        <td>
            <div><!--数据网格-->
                <div id="dg_tool" align="right">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsert('win_ascJobCfg','处理配置新增','<%=basePath%>AscTask/toJobCfgInsert','900','200')">新增</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true onclick="toModify('win_ascJobCfg','处理配置更新','<%=basePath%>AscTask/toJobCfgUpdate','900','200')">修改</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDelete('<%=basePath%>AscTask/deleteJobCfg')">删除</a>
                </div>
                <table id="dg_jobCfg" class="easyui-datagrid" style="width:1050px;height: 400px">
                    <thead>
                    <tr>
                        <th data-options="field:'cb',checkbox:true"></th>
                        <th data-options="field:'jobId',width:100,align:'center'">处理编号</th>
                        <th data-options="field:'jobName',width:100,align:'center'">处理名称</th>
                        <th data-options="field:'jobPara',width:100,align:'center'">处理参数</th>
                        <th data-options="field:'jobSeq',width:100,align:'center'">处理序列</th>
                        <th data-options="field:'jobType',width:120,align:'center',dictry:'ASC012',formatter:formatData">处理类型</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </td>
    </tr>
</table>
<!--动态生成模态框-->
<div id="win_ascJobCfg"></div>
<script type="text/javascript">
    $(function(){
        $('#dg_jobCfg').datagrid({
            url:'<%=basePath%>AscTask/listAllAscJobCfg?taskId=<%=taskId%>',
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
        //数据网格自适应网页大小
        /*$('#dg_jobCfg').resizeDataGrid(20, 20, 350, 900);
        // 当窗口大小发生变化时，调整DataGrid的大小
        $(window).resize(function() {
            $('#dg_jobCfg').resizeDataGrid(20, 20, 350, 900);
        });*/
    })

    //新增窗口
    function toInsert(winId,title,url,width,height) {
        url = url + '?taskId=<%=taskId%>';
        openWindowSelf(winId,title,width,height,url);
    }
    //修改窗口
    function toModify(winId,title,url,width,height) {
        var row = $('#dg_jobCfg').datagrid('getSelected');
        if (row == null){
            $.messager.alert("提示","请选择需要修改的记录");
        }else{
            var jobId = row.jobId;
            url = url + '?jobId='+ jobId;
            openWindowSelf(winId,title,width,height,url);
        }
    }
    //删除
    function toDelete(url) {
        var row = $('#dg_jobCfg').datagrid('getSelected');
        if (row == null){
            $.messager.alert("提示","请选择需要删除的任务处理配置");
        }else{
            var jobId = row.jobId;
            $.ajax({
                url:url,
                type:'post',
                dataType:'json',
                data:{"jobId":jobId},
                success:function (data) {
                    var data = JSON.stringify(data);
                    var data = eval('(' + data + ')');
                    if (data.success){
                        refreshDataGridBySelf('dg_jobCfg');
                        $.messager.alert("提示",data.message);
                    }else{
                        $.messager.alert("提示",data.message);
                    }
                },
                error:function () {
                    $.messager.alert("警告","请求失败");
                }
            })
        }
    }

</script>
</body>
</html>
