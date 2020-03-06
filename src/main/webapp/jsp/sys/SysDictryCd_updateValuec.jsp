<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import=" com.scrcu.sys.entity.SysDictryCd,java.util.*" %>
<%
    SysDictryCd sysDictryCd = (SysDictryCd) request.getAttribute("sysDictryCd");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/demo/demo.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.easyui.min.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<%--<div data-options="region:'center'" style="padding:5px;background:#eee;">--%>
<form id="form_insert" method="post">
    <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
        <tr>
            <td align="right">字典代码Id：</td>
            <td align="left">
                <input type="text" id="dictryId_insert" name="dictryId" value="<%=sysDictryCd.getDictryId()%>" required="true" style="width: 200px" maxlength="32" >
                <input id="dictryId_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
                <!-- onblur="checkGroupId()"-->
            </td>
            <td align="right">代码名称：</td>
            <td align="left">
                <input id="dictryNm_insert" name="dictryNm"  required="true" value="<%=sysDictryCd.getDictryNm()%>" style="width: 200px">
                <input id="dictryNm_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
            </td>
        </tr>
        <tr>
            <td align="right">上级代码Id：</td>
            <td align="left">
                <input type="text" id="pareDictryId_insert" name="pareDictryId" readonly="true"  value="<%=sysDictryCd.getPareDictryId()%>" style="width: 200px" maxlength="32" >
                <input id="pareDictryId_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
                <!-- onblur="checkGroupId()"-->
            </td>
            <td align="right">所属字典代码id：</td>
            <td align="left">
                <input id="blngtoDictryId_insert" name="blngtoDictryId" required="true" readonly="true" value="<%=sysDictryCd.getBlngtoDictryId()%>" style="width: 200px">
                <input id="blngtoDictryId_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
            </td>
        </tr>
        <tr>
            <td align="right">代码类型：</td>
            <td align="left">
                <input type="text" id="cdTyp_insert" name="cdTyp" value="<%=sysDictryCd.getCdTyp()%>" readonly="true"  style="width: 200px" maxlength="32" >
                <input id="cdTyp_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
                <!-- onblur="checkGroupId()"-->
            </td>
            <td align="right">字典说明：</td>
            <td align="left" colspan="10">
                <textarea id="dictryComnt_insert" name="dictryComnt" value="" cols="30" rows="3"><%=sysDictryCd.getDictryComnt()%></textarea>
                <input id="dictryComnt_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
            </td>
        </tr>
    </table>


</form>
<div style="text-align:center">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
</div>
<%--</div>--%>
<script type="text/javascript">
    //初始化页面
    $(function () {
    })
    //提交
    var pareDictryId_insert = null;
    function submitForm() {
        $('#form_insert').form({
            url:'/usrp/dictionary/updateDic',
            onSubmit:function () {
                //输入验证
                dictryId_insert  = document.getElementById("dictryId_insert").value;
                var dictryNm_insert = document.getElementById("dictryNm_insert").value;
                var dictryComnt_insert = document.getElementById("dictryComnt_insert").value;
                var blngtoDictryId_insert = document.getElementById("blngtoDictryId_insert").value;
                pareDictryId_insert = document.getElementById("pareDictryId_insert").value;
                if (dictryId_insert == null || dictryId_insert == ""){
                    remind("dictryId_back","字典代码Id不能为空");
                    return false;
                }
                if (dictryNm_insert == null || dictryNm_insert == ""){
                    remind("dictryNm_back","字典代码名称不能为空");
                    return false;
                }
                if (dictryComnt_insert == null || dictryComnt_insert == ""){
                    remind("dictryComnt_back","字典说明不能为空");
                    return false;
                }
                if (blngtoDictryId_insert == null || blngtoDictryId_insert == ""){
                    remind("blngtoDictryId_back","字典代码所属id不能为空");
                    return false;
                }
            },
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                if (data.success){
                    <!-- refreshTab("字典代码管理","toSysDictryCdList")-->
                    <!--window.location.href =  "/usrp/dictionary/toSysDictryCdInsertVal?dictryId="+dictryId_insert;-->
                    newWindow('新增码值','/usrp/dictionary/toSysDictryCdInsertVal?dictryId='+pareDictryId_insert,'600','430');

                }else{
                    alert(data.message);
                }
            }
        })
        $('#form_insert').form('submit');

    }

    //重置
    function clearForm() {
        $('#form_insert').form('reset');
    }

    //检查任务组Id的唯一性
    function checkGroupId(){
        var dictryId = document.getElementById("dictryId_insert").value;
        if (ajaxCheckId(dictryId)==false){
            remind("dictryId_back","任务编号已存在")
        }
    }

    function ajaxCheckId(taskId){
        var result = false;
        $.ajax({
            async:false,
            url:'AscTask/checkAscTaskId',
            type:'post',
            dataType:'json',
            data:{"taskId":taskId},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    result = true
                }else{
                    result = false;
                }
            },
            error:function () {
                $.messager.alert("警告","请求失败");
            }
        })
        return result;
    }

    function remind(field,msg){
        document.getElementById(field).style.display = 'block';
        document.getElementById(field).value = msg;
    }

</script>
</body>
</html>
