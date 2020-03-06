<%@ page import="com.scrcu.sys.entity.SysDictryCd" %><%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/15
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String flag = (String) request.getAttribute("flag");
    String winId = (String) request.getAttribute("winId");
    SysDictryCd sysDictryCd = (SysDictryCd) request.getAttribute("sysDictryCd");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="background:#eee;">
<form id="form_sys_dictry_value_insert" method="post">
    <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: 12px">代码值ID：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dictryId_value_insert" name="dictryId" data-options="required:true,validTyp:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">代码中文名：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dictryNm_value_insert" name="dictryNm" data-options="required:true,validTyp:''"></td>
        </tr>
        <%--<tr>
            <td align="right"><span style="font-size: 12px">上级字典代码ID：</span></td>
            <td align="left"><input class="easyui-validatebox" id="pareDictryId_value_insert" name="pareDictryId" data-options="required:true,validTyp:''"></td>
        </tr>--%>
        <tr>
            <td><input class="easyui-textbox" id="dictryId_value_hid" name="dictryId_hid" type="hidden" value="<%=sysDictryCd.getDictryId()%>"></td>
            <td><input class="easyui-textbox" id="blngtoDictryId_value_hid" name="blngtoDictryId" type="hidden" value="<%=sysDictryCd.getBlngtoDictryId()%>"></td>
        </tr>
    </table>
</form>
<div style="text-align:center">
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true iconCls="icon-save" onclick="submitSysDictryValue('form_sys_dictry_value_insert')">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true iconCls="icon-remove" onclick="closeWindowBySysDictryValue('<%=winId%>')">关闭</a>
</div>
<script type="text/javascript">
    $(function () {
        //-赋值
        initTextboxByDictry('dictryId_value_insert','<%=sysDictryCd.getDictryId()%>',20,false,'150','20');
        initTextboxByDictry('dictryNm_value_insert','<%=sysDictryCd.getDictryNm()%>',50,false,'150','20');
        //initTextboxByDictry('pareDictryId_value_insert','<%=sysDictryCd.getPareDictryId()%>',20,true,'150','20');
    })

    //提交表单
    function submitSysDictryValue(formId){
        //验证框校验
        if (!$("#"+formId).form('validate')){
            return false;
        }
        //-取消禁用(上级字典代码ID)
        //$('#pareDictryId_value_insert').textbox('enable');
        //-判断提交url
        var flag = '<%=flag%>';
        var url = basePath;
        var dictryId = $('#dictryId_value_insert').val();
        if (flag == 'cache'){
            url = url + 'dictionary/updateValueFromCache';
            if (!checkDictry(dictryId, '', 'cache')){
                $.messager.alert("提示","代码值已存在，请重新输入");
                return false;
            }
        } else if (flag == 'db'){
            var dictryCategory = $('#dictryId_hid').val();
            url = url + 'dictionary/updateValueFromDB';
            if (!checkDictry(dictryId, dictryCategory, 'db')){
                $.messager.alert("提示","代码值已存在，请重新输入");
                return false;
            }
        }
        //-提交ajax
        $.ajax({
            url:url,
            type:'post',
            dataType:'text',
            data:$('#'+formId).serialize(),
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                var result = data.data;
                if (data.success){
                    $.messager.alert("提示",data.message);
                    if (flag == 'cache'){
                        //-刷新数据列表
                        initSysDictryInsertPage_DG('dg_sys_dictry_insert');
                        //-关闭窗口
                        $('#<%=winId%>').window('close');
                    } else if (flag == 'db'){
                        //-刷新数据列表
                        refreshDataGridBySelf('dg_dictry_value');
                        //-关闭窗口
                        $('#<%=winId%>').window('close');
                    }
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        })
    }
</script>
</body>
</html>
