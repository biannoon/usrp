<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/24
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String tablId = (String) request.getAttribute("tablId");
    String enNm = (String) request.getAttribute("enNm");
    String id = (String) request.getAttribute("id");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysTabConfig.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<form id="form_insert" method="post">
    <table width="100%" style="text-align: center" cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: 12px">所属数据接口：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="tablId_insert"
                       name="tablId"
                       value="">
            </td>
            <td align="right"><span style="font-size: 12px">表名：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="tabNm_insert"
                       name="tabNm"
                       value="">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">序号：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="seqn_insert"
                       name="seqn"
                       value=""
                       data-options="required:true,validType:['uniqueSeqn','checkNum']">
            </td>
            <td align="right"><span style="font-size: 12px">标签名称：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="lNm_insert"
                       name="lNm"
                       value=""
                       data-options="required:true,validType:'',editable:false" buttonIcon="icon-search" >
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">标签对齐方式：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="lAlign_insert"
                       name="lAlign"
                       data-options="required:true,editable:false,panelHeight:'auto'">
            </td>
            <td align="right"><span style="font-size: 12px">标签合并列数：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="lColspan_insert"
                       name="lColspan"
                       data-options="required:true,validType:'checkNum'">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">标签合并行数：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="lRowspan_insert"
                       name="lRowspan"
                       data-options="required:true,validType:'checkNum'">
            </td>
            <td align="right"><span style="font-size: 12px">字段名称：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="fNm_insert"
                       name="fNm"
                       buttonIcon="icon-search"
                       data-options="required:true,validType:'',editable:false">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字段对齐方式：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="fAlign_insert"
                       name="fAlign"
                       data-options="required:true,editable:false,panelHeight:'auto'">
            </td>
            <td align="right"><span style="font-size: 12px">字段合并列数：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="fColspan_insert"
                       name="fColspan"
                       data-options="required:true,validType:'checkNum'">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字段合并行数：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="fRowspan_insert"
                       name="fRowspan"
                       data-options="required:true,validType:'checkNum'">
            </td>
            <td align="right"><span style="font-size: 12px">字段类型：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="fTyp_insert"
                       data-options="required:true,validType:'',editable:false"
                       buttonIcon="icon-search">
                <input class="easyui-textbox" id="fTyp_hidden" name="fTyp" type="hidden">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字典代码：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="dictryId_insert"
                       data-options="required:true,validType:'checkDictryId',editable:false"
                       buttonIcon="icon-search">
                <input class="easyui-textbox" id="dictryId_hidden" name="dictryId" type="hidden">
            </td>
            <td align="right"><span style="font-size: 12px">是否只读：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="readOnly_insert"
                       name="readOnly"
                       data-options="editable:false,panelHeight:'auto'">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">是否必输：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="mustInput_insert"
                       name="mustInput"
                       data-options="required:false,editable:false,panelHeight:'auto'">
            </td>
            <td align="right"><span style="font-size: 12px">是否列表展示：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="listShowFlg_insert"
                       name="listShowFlg"
                       data-options="required:true,editable:false,panelHeight:'auto'">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">是否详情展示：</span></td>
            <td align="left">
                <input class="easyui-validatebox"
                       id="detailShowFlg_insert"
                       name="detailShowFlg"
                       data-options="required:true,editable:false,panelHeight:'auto'">
            </td>
        </tr>
        <input type="hidden" id="id" value="">
        <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
    </table>
</form>
<div id="win_sysTabConfig_insert01"></div>
<div id="win_sysTabConfig_insert02"></div>
<div style="text-align:center">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true
       onclick="submitForm()">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true
       onclick="resetForm('form_insert')">重置</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true
       onclick="closeTabConfigWindow('win_sysTabConfig')">关闭</a>
</div>
<script type="text/javascript">
    $(function () {
        initSysTabConfigInsertPage('<%=tablId%>','<%=enNm%>');
    })

    //表单异步提交（非easyUI表单提交）
    function submitForm() {
        //验证框校验
        if (!$("#form_insert").form('validate')){
            return false;
        }
        //提交非必输校验
        var fieldType = $('#fTyp_hidden').val();
        var dictryId = $('#dictryId_hidden').val();
        if (fieldType == 'combobox' && dictryId == ''){
            $.messager.alert("提示","当字段类型为下拉框或者组合框时，字典代码必须选择");
            return false;
        }
        //取消禁用
        $('#tablId_insert').textbox('enable');
        $('#tabNm_insert').textbox('enable');
        $('#mustInput_insert').textbox('enable');
        //取消禁用之后，表单提交获取不到下拉框的value值
        var mustInput = $('#mustInput_insert').combobox('getValue');
        if (mustInput == '是'){
            mustInput = 'SYS0201';
        }else{
            mustInput = 'SYS0202';
        }
        $('#mustInput_insert').combobox('setValue',mustInput);
        $.ajax({
            url:'<%=basePath%>SysTabConfig/insertSysTabConfig',
            type:'post',
            dataType:'text',
            data:$('#form_insert').serialize(),
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                var d = data.data;
                if (data.success){
                    //新增成功后，禁用提交/重置按钮
                    $('#but_insert').linkbutton('disable');
                    $('#but_reset').linkbutton('disable');
                    //标记关闭按钮
                    document.getElementById('close_flag').value = 'true';//标记关闭按钮
                    $.messager.alert("提示","新增成功");
                }else{
                    $.messager.alert("提示","新增失败");
                }
            }
        })
    }
</script>
</body>
</html>
