<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysParam.js"></script>
</head>
<body>
<form id="detail_form"  method="post">
    <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: 12px">参数编号：</span></td>
            <td align="left">
                <input class="easyui-validatebox"  id="paramId_insert" name="paramId" data-options="required:true,validType:'uniqueParamId'">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">参数名称：</span></td>
            <td align="left">
                <input class="easyui-validatebox"  id="paramNm_insert" name="paramNm"  data-options="required:true,validType:''">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">参数类型：</span></td>
            <td align="left">
                <input class="easyui-validatebox"  id="paramTyp_insert" name="paramTyp" data-options="required:true,validType:'',panelHeight:'auto',editable:false">
            </td>
        </tr>
        <tr>
            <td align="right" valign="top"><span style="font-size: 12px">参数值：</span></td>
            <td align="left">
                <input class="easyui-validatebox" checkInterval="100" id="paramValue_insert" name="paramValue" data-options="required:true,validType:'',multiline:true">
            </td>
        </tr>
        <tr>
            <td align="right" valign="top"><span style="font-size: 12px;">参数说明：</span></td>
            <td align="left" colspan="3">
                <input class="easyui-validatebox" id="paramComnt_insert" name="paramComnt" data-options="required:true,validType:'',multiline:true">
            </td>
        </tr>
    </table>
</form>
<div class="btn-area">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add" onclick="save()" text="保存" icon="icon-save"></a>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true icon="icon-cancel" text="重置" onclick="clearForm()"></a>
</div>
<!-- 弹出窗口-->
<div id="newWin"></div>
<script type="text/javascript">

    $(function(){
        initTextboxByParam('paramId_insert','',10,false,'200','20');
        initTextboxByParam('paramNm_insert','',40,false,'200','20');
        initComboboxByParam('paramTyp_insert','SYS08','',false,'200','20');
        initTextboxByParam('paramValue_insert','',100,false,'300','100');
        initTextboxByParam('paramComnt_insert','',500,false,'300','100');
    })

    function save() {
        var paramId = $('#paramId_insert').val();
        if (!$('#detail_form').form('validate')){
            $.messager.alert('提示信息','请补全信息','info');
            return false;
        }else{
            ajaxSubmit("post",basePath+"sysParam/insert",$("#detail_form").serialize(),"json");
        }
    }
    //作为window弹出详情列表中form提交的主要方法，会关闭当前window
    function ajaxSubmit(type, url, data, datatype) {
        $.ajax({
            type : type,
            url : url,
            data : data,
            dataType : datatype,
            success : function(data){
                if(data.success){
                    refreshDataGridBySelf('dg_sys_param');
                    $('#btn_add').linkbutton('disable');
                    $.messager.alert('提示信息',data.message,'info');
                }else{
                    $.messager.alert("警告",data.message,'error');
                }
            },
            error : function(){
                $.messager.alert("警告","ajax提交报错",'error');
            }
        });
    }

    function clearForm(){
        $('#detail_form').form('reset');
    }
</script>
</body>
</html>