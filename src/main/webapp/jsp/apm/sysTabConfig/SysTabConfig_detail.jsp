<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/30
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.scrcu.apm.entity.SysTabConfig" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    SysTabConfig tabConfig = (SysTabConfig)request.getAttribute("sysTabConfig.js");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
<form id="form_insert" method="post">
    <table width="100%" style="text-align: center" cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: 12px">所属数据接口：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="tablId_insert" name="tablId" value="" style="width: 200px;height: 20px"></td>
            <td align="right"><span style="font-size: 12px">表名：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="tabNm_insert" name="tabNm" value="" style="width: 200px;height: 20px"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">序号：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="seqn_insert" name="seqn" value="" style="width: 200px;height: 20px"></td>
            <td align="right"><span style="font-size: 12px">标签名称：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="lNm_insert" name="lNm" value="" style="width: 200px;height: 20px"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">标签对齐方式：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="lAlign_insert" name="lAlign" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
            <td align="right"><span style="font-size: 12px">标签合并列数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="lColspan_insert" name="lColspan" value="" style="width: 200px;height: 20px" ></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">标签合并行数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="lRowspan_insert" name="lRowspan" value="" style="width: 200px;height: 20px"></td>
            <td align="right"><span style="font-size: 12px">字段名称：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="fNm_insert" name="fNm" value="" style="width: 200px;height: 20px"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字段对齐方式：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="fAlign_insert" name="fAlign" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
            <td align="right"><span style="font-size: 12px">字段合并列数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="fColspan_insert" name="fColspan" value="" style="width: 200px;height: 20px"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字段合并行数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="fRowspan_insert" name="fRowspan" value="" style="width: 200px;height: 20px"></td>
            <td align="right"><span style="font-size: 12px">字段类型：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="fTyp_insert" name="fTyp" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字典代码：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="dictryId_insert" name="dictryId" value="" style="width: 200px;height: 20px"></td>
            <td align="right"><span style="font-size: 12px">是否只读：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="readOnly_insert" name="readOnly" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">是否必输：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="mustInput_insert" name="mustInput" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
            <td align="right"><span style="font-size: 12px">是否列表展示：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="listShowFlg_insert" name="listShowFlg" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">是否详情展示：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="detailShowFlg_insert" name="detailShowFlg" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
        </tr>
        <input type="hidden" id="id" value="">
    </table>
</form>
<div style="text-align:center">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindow('win_sysTabConfig')">关闭</a>
</div>
<script type="text/javascript">
    $(function () {
        //初始化组合框
        initComboboxSelf('lAlign_insert','SYS13','<%=basePath%>');
        initComboboxSelf('fAlign_insert','SYS13','<%=basePath%>');
        initComboboxSelf('fTyp_insert','SYS14','<%=basePath%>');
        initComboboxSelf('readOnly_insert','SYS02','<%=basePath%>');
        initComboboxSelf('mustInput_insert','SYS02','<%=basePath%>');
        initComboboxSelf('listShowFlg_insert','SYS02','<%=basePath%>');
        initComboboxSelf('detailShowFlg_insert','SYS02','<%=basePath%>');
        //回显数据
        initTextboxSelf('id_insert','<%=tabConfig.getId()%>',false);
        initTextboxSelf('tablId_insert','<%=tabConfig.getTablId()%>',false);
        initTextboxSelf('tabNm_insert','<%=tabConfig.getTabNm()%>',false);
        initTextboxSelf('seqn_insert','<%=tabConfig.getSeqn()%>',false);
        initTextboxSelf('lNm_insert','<%=tabConfig.getlNm()%>',false);
        assignComboboxSelf('lAlign_insert','<%=tabConfig.getlAlign()%>',false);
        initTextboxSelf('lColspan_insert','<%=tabConfig.getlColspan()%>',false);
        initTextboxSelf('lRowspan_insert','<%=tabConfig.getlRowspan()%>',false);
        initTextboxSelf('fNm_insert','<%=tabConfig.getfNm()%>',false);
        assignComboboxSelf('fAlign_insert','<%=tabConfig.getfAlign()%>',false);
        initTextboxSelf('fColspan_insert','<%=tabConfig.getfColspan()%>',false);
        initTextboxSelf('fRowspan_insert','<%=tabConfig.getfRowspan()%>',false);
        assignComboboxSelf('fTyp_insert','<%=tabConfig.getfTyp()%>',false);
        initTextboxSelf('dictryId_insert','<%=tabConfig.getDictryId()%>',false);
        assignComboboxSelf('readOnly_insert','<%=tabConfig.getReadOnly()%>',false);
        assignComboboxSelf('mustInput_insert','<%=tabConfig.getMustInput()%>',false);
        assignComboboxSelf('listShowFlg_insert','<%=tabConfig.getListShowFlg()%>',false);
        assignComboboxSelf('detailShowFlg_insert','<%=tabConfig.getDetailShowFlg()%>',false);
    })

    //关闭窗口
    function closeWindow(winId) {
        $("#"+winId).window('close');
    }
</script>
</body>
</html>

