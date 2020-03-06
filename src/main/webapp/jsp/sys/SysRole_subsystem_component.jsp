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
    String winId = request.getParameter("winId");
    String id = request.getParameter("id");
    String id_hid = request.getParameter("id_hid");
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
<body style="padding:5px;background:#eee;">
<div id="dg_subsystem_component_tool">
    <table width="100%" style="text-align: center">
        <tr>
            <td align="right"><span style="font-size: small">子系统名称：</span></td>
            <td align="left"><input id="subsystemNm_se" ></td>
            <td  align="right">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain=true
                   onclick="searchSubsystem()">查询</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true
                   onclick="assignVal()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
                   plain=true onclick="closeComponentWindow('<%=winId%>')">关闭</a>
            </td>
        </tr>
    </table>
</div>
<div>
    <table id="dg_subsystem_component" width="100%"></table>
</div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        var echoValue = $('#<%=id_hid%>').val();
        initSubsystem_DG('dg_subsystem_component',echoValue);
        initTextbox('subsystemNm_se','',50,false,'150','20');
    })

    /**
     * 描述：初始化子系统列表数据
     */
    function initSubsystem_DG(id,echoValue){
        $('#'+id).datagrid({
            url: basePath + 'sysSubsystem/getByPage',
            fitColumns: false,
            singleSelect: true,
            pagination: true,
            pageNumber: '1',
            pageSize: '10',
            pagePosition:'top',
            toolbar: '#dg_subsystem_component_tool',
            frozenColumns:[[
                {field:'ck',title:'',width:100,checkbox:true},
                {field:'subsystemId',title:'子系统编号',width:200,align:'center'},
                {field:'subsystemNm',title:'子系统名称',width:200,align:'center'}
            ]],
            columns:[[]],
            onLoadSuccess: function (data) {
                $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
                $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
                $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称

                //-数据回显
                var rows = $(this).datagrid('getRows');
                $.each(rows, function (index, row) {
                    if (echoValue == row.subsystemId) {
                        $('#dg_subsystem_component').datagrid('checkRow', index);
                    }
                })
            }
        });
    }

    //-子系统查询
    function searchSubsystem(){
        $('#dg_subsystem_component').datagrid('load',{
            subsystemNm:$('#subsystemNm_se').val()
        })
    }

    /**
     * 描述：子窗口给父页面赋值
     * @param id
     * @param isLevel
     * @param fieldId
     * @param winId
     */
    function assignVal(){
        var rows = $('#dg_subsystem_component').datagrid('getChecked');
        if (rows == null){
            $.messager.alert("提示","请选择子系统");
        }else{
            var id = '<%=id%>';
            var id_hid = '<%=id_hid%>';
            var winId = '<%=winId%>';
            $.each(rows,function(index,row) {//存在一个缺陷，只能用于单选
                if (id != '' && id != null){
                    $('#' + id).textbox('setValue', row.subsystemNm);
                }
                if (id_hid != '' && id_hid != null){
                    $('#' + id_hid).textbox('setValue', row.subsystemId);
                }
            })
            $('#'+winId).window('close');
        }
    }

    function initTextbox(id,echoValue,maxlength,disabled,width,height) {
        $('#'+id).textbox({
            type:'text',
            width:width,
            height:height,
            value:echoValue,
            disabled:disabled
        });
        if (maxlength != 0){
            $('#'+id).textbox('textbox').attr('maxlength',maxlength);
        }
    }

    function closeComponentWindow(winId){
        $('#'+winId).window('close');
    }
</script>
</body>
</html>
