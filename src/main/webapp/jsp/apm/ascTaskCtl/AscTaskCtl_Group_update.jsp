<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/20
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.scrcu.apm.ascTaskCtl.entity.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    AscGroup ascGroup = (AscGroup) request.getAttribute("ascGroup");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ascTaskCtl/ascGroup.js"></script>
</head>
<body>
<div data-options="region:'center'" style="padding:5px;background:#eee;">
    <form id="form_group_update" method="post">
        <table width="100%" style="text-align: center" cellpadding="5">
            <tr>
                <td align="right"><span style="font-size: small">任务组编号：</span></td>
                <td align="left"><input class="easyui-validatebox" id="groupId" name="groupId" value="" data-options="required:true,validType:'uniqueGroupId'"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">任务组名称：</span></td>
                <td align="left"><input class="easyui-validatebox" id="groupName" name="groupName" value="" data-options="required:true"></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">上级任务组：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="parentGroup" value="" data-options="required:true">
                    <input class="easyui-textbox" type="hidden" id="parentGroup_hidden" name="parentGroup"  value=""/>
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">待处理时间：</span></td>
                <td align="left"><input class="easyui-validatebox" id="nextDate" name="nextDate" value=""></td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: small">任务组状态：</span></td>
                <td align="left"><input class="easyui-validatebox" id="state" name="state" value="" data-options="required:true"></td>
            </tr>
            <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
        </table>
    </form>
    <div style="text-align:center">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true onclick="submitForm('form_group_update','<%=basePath%>','AscTask/updateAscGroup')">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true onclick="resetForm('form_group_update')">重置</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindowBySelf('win_ascGroup','<%=basePath%>AscTask/ListAllTaskGroupByTreeGrid')">关闭</a>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        initTextboxByAscGroup('groupId','<%=ascGroup.getGroupId()%>',40,true,'200','20');
        initTextboxByAscGroup('groupName','<%=ascGroup.getGroupName()%>',40,false,'200','20');
        initDateBoxByAscGroup('nextDate','<%=ascGroup.getNextDate()%>','200','20');
        initComboboxByAscGroup('state','ASC001','<%=ascGroup.getState()%>',true,'200','20');
        var parentGroup = '<%=ascGroup.getParentGroup()%>';
        initTextboxByAscGroup('parentGroup',exchangeCodeToNameFromAscGroup(parentGroup),40,false,'200','20');
        $('#parentGroup_hidden').textbox({
            value:parentGroup
        })
        initAscGroupComponent('parentGroup','win_ascGroup_insert','parentGroup_hidden');
    })


    //表单提交
    /*function submitForm() {
        $('#form_group_update').form({
            url:'<%=basePath%>AscTask/updateAscGroup',
            onSubmit:function () {
                //输入验证
                $('#state').combobox({
                    disabled:false
                })
                $('#groupId').textbox({
                    disabled:false
                })
                $('#parentGroup').textbox({
                    disabled:false
                })
            },
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                var d = data.data;
                if (data.success){
                    //回显
                    initTextboxSelf('groupId',d.groupId,true);
                    initTextboxSelf('groupName',d.groupName,true);
                    initTextboxSelf('parentGroup',d.parentGroup,true);
                    initTextboxSelf('nextDate',d.nextDate,true);
                    assignComboboxSelf('state',d.state,true);
                    //修改成功后，禁用提交/重置按钮
                    $('#but_insert').linkbutton('disable');
                    $('#but_reset').linkbutton('disable');
                    //标记关闭按钮
                    document.getElementById('close_flag').value = 'true';
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        })
        $('#form_group_insert').form('submit');
    }*/

</script>
</body>
</html>
