<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/18
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp" %>
<%
    String tablId = (String) request.getAttribute("tablId");
    String winId = (String) request.getAttribute("winId");
    String fieldId = (String) request.getAttribute("fieldId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysTabConfig.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<table width="100%">
    <tr>
        <td>
            <div id="tool_field_" style="padding:3px">
                <table width="100%" style="text-align: center">
                    <tr>
                        <td colspan="2" align="right">
                            <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>--%>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="assignVal('<%=fieldId%>','<%=winId%>')">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindow('<%=winId%>')">关闭</a>
                        </td>
                    </tr>
                </table>
            </div>
            <table id="dg_fields" class="easyui-datagrid" style=""><!--数据网格-->
                <thead>
                <tr>
                    <th data-options="field:'cb',checkbox:true"></th>
                    <th data-options="field:'fieldEnNm',width:100,align:'center'">字段英文名</th>
                    <th data-options="field:'fieldCnNm',width:100,align:'center'">字段中文名</th>
                    <th data-options="field:'dictryCd',width:100,align:'center'">字典代码</th>
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
        //获取文本框的值
        var fieldId = '<%=fieldId%>';
        var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
        var value = $('#'+_fieldId).val();
        $('#dg_fields').datagrid({
            idField:'fieldEnNm',
            url:'<%=basePath%>SysTabConfig/showFieldsByTab?tablId=<%=tablId%>',
            fitColumns:true,
            singleSelect:true,
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pagePosition:'top',
            toolbar:'#tool_field_',
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
                //回显勾选指定的值（目前用于单选）
                var rows = $(this).datagrid('getRows');
                $.each(rows,function (index,row) {
                    if (value == row.fieldEnNm){
                        $('#dg_fields').datagrid('checkRow',index);
                    }
                })
            },
            onCheck:function(rowIndex,rowData) {
                var field = rowData.fieldEnNm;
                if (!uniqueField(field, '<%=basePath%>SysTabConfig/uniqueField', '<%=tablId%>')) {
                    if (value == ''){
                        $(this).datagrid('uncheckRow', rowIndex);
                        $.messager.alert("提示", "该字段已存在，请重新选择字段");
                    }
                }
            }
        });
    })

    //子页面给父页面赋值操作
    function assignVal(fieldId,winId){
        var rows = $('#dg_fields').datagrid('getChecked');
        if (rows == null){
            $.messager.alert("提示","请选择记录");
        }else{
            var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
            var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
            $.each(rows,function(index,row) {//存在一个缺陷，只能用于单选
                if (fieldId_ != '' && fieldId_ != null){
                    $('#' + fieldId_).textbox('setValue', row.fieldCnNm);
                }
                if (_fieldId != '' && _fieldId != null){
                    $('#' + _fieldId).textbox('setValue', row.fieldEnNm);
                }
            })
            $('#'+winId).window('close');
        }
    }


    function closeWindow(winId){
        $('#'+winId).window('close');
    }
</script>
</body>
</html>
