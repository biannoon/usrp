<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/15
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysDictryCd.js"></script>
</head>
<body style="background:#eee;">
<div><table id="dg_sys_dictry_draw" style="width:100%"></table></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        var dictryId = $('#dictryId_hid').val();
        $('#dg_sys_dictry_draw').datagrid({
            url:basePath + 'dictionary/getListDraw?dictryId='+dictryId,
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pagePosition:'top',
            frozenColumns:[[
                {field:'tableEnNm',title:'接口英文名',width:100,align:'center'},
                {field:'tableCnNm',title:'接口中文名',width:100,align:'center'},
                {field:'fieldEnNm',title:'字段英文名',width:100,align:'center'},
                {field:'fieldCnNm',title:'字段中文名',width:100,align:'center'},
                {field:'dataSorcId',title:'所属数据源',width:200,align:'center'}
            ]],
            onLoadSuccess: function (data) {
                $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
                $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
                $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
            }
        })
    })
</script>
</body>
</html>
