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
    String checkBox = (String) request.getAttribute("checkBox");
    String dictryId = (String) request.getAttribute("dictryId");
    String winId = (String) request.getAttribute("winId");
    String fieldId = (String) request.getAttribute("fieldId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
<table width="100%">
    <tr>
        <td>
            <div id="tb" style="padding:3px">
                <table width="100%" style="text-align: center">
                    <tr>
                        <td align="right"><span style="font-size: small">字典代码名称：</span></td>
                        <td align="left"><input class="easyui-textbox" id="dictryNm_search" style="height:20px;width:200px;border:1px solid #ccc" ></td>
                        <td  align="right">
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain=true onclick="doSearch()">查询</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="assignVal('<%=fieldId%>','<%=winId%>')">确定</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindow('<%=winId%>')">关闭</a>
                        </td>
                    </tr>
                </table>
            </div>
            <table id="dg_sysDictryCd" class="easyui-datagrid" style=""><!--数据网格-->
                <thead>
                <tr>
                    <th data-options="field:'cb',checkbox:true"></th>
                    <th data-options="field:'dictryId',width:100,align:'center'">字典代码ID</th>
                    <th data-options="field:'dictryNm',width:100,align:'center'">字典代码名称</th>
                    <th data-options="field:'cdTyp',width:100,align:'center'">字典代码类型</th>
                    <th data-options="field:'dictryComnt',width:100,align:'center'">字典代码说明</th>
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

        $('#dg_sysDictryCd').datagrid({
            idField:'dictryId',
            url:'<%=basePath%>common/getSysDictryCdListByDatagrid?dictryId=<%=dictryId%>',
            fitColumns:true,
            <%if ("0".equals(checkBox)){%>
            singleSelect:true,
            <%}%>
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pagePosition:'top',
            toolbar:'#tb',
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
                //回显勾选指定的值（目前用于单选）
                var rows = $(this).datagrid('getRows');
                $.each(rows,function (index,row) {
                    if (value == row.dictryId){
                        $('#dg_sysDictryCd').datagrid('checkRow',index);
                    }
                })
            }
        });

    })

    //子页面给父页面赋值操作
    function assignVal(fieldId,winId){
        var rows = $('#dg_sysDictryCd').datagrid('getChecked');
        if (rows == null){
            $.messager.alert("提示","请选择记录");
        }else{
            var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
            var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
            $.each(rows,function(index,row) {//存在一个缺陷，只能用于单选
                if (fieldId_ != '' && fieldId_ != null){
                    $('#' + fieldId_).textbox('setValue', row.dictryNm);
                }
                if (_fieldId != '' && _fieldId != null){
                    $('#' + _fieldId).textbox('setValue', row.dictryId);
                }
            })
            $('#'+winId).window('close');
        }
    }

    //条件查询
    function doSearch() {
        $('#dg_sysDictryCd').datagrid('load',{
            dictryNm: $('#dictryNm_search').val()
        })
    }

    function closeWindow(winId){
        $('#'+winId).window('close');
    }
</script>
</body>
</html>
