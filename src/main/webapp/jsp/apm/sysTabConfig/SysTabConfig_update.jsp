<%@ page import="com.scrcu.apm.entity.SysTabConfig" %><%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/24
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.scrcu.apm.entity.SysTabConfig" %>
<%@ page import="com.scrcu.common.utils.ExchangeUtil" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    SysTabConfig tabConfig = (SysTabConfig)request.getAttribute("sysTabConfig");
    String tablId = (String) request.getAttribute("tablId");
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
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="tablId_insert" name="tablId" value="" style="width: 200px;height: 20px"></td>
            <td align="right"><span style="font-size: 12px">表名：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="tabNm_insert" name="tabNm" value="" style="width: 200px;height: 20px"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">序号：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="seqn_insert" name="seqn" value="" style="width: 200px;height: 20px" data-options="required:true,validType:'uniqueSeqn'"></td>
            <td align="right"><span style="font-size: 12px">标签名称：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="lNm_insert" name="lNm" value="" style="width: 200px;height: 20px" buttonIcon="icon-search" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">标签对齐方式：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="lAlign_insert" name="lAlign" value="" style="width: 200px;height: 20px" data-options="required:true,editable:false,panelHeight:'auto'"></td>
            <td align="right"><span style="font-size: 12px">标签合并列数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="lColspan_insert" name="lColspan" value="" style="width: 200px;height: 20px" data-options="required:true,validType:'checkNum'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">标签合并行数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="lRowspan_insert" name="lRowspan" value="" style="width: 200px;height: 20px" data-options="required:true,validType:'checkNum'"></td>
            <td align="right"><span style="font-size: 12px">字段名称：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="fNm_insert" name="fNm" value="" style="width: 200px;height: 20px" buttonIcon="icon-search" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字段对齐方式：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="fAlign_insert" name="fAlign" value="" style="width: 200px;height: 20px" data-options="required:true,editable:false,panelHeight:'auto'"></td>
            <td align="right"><span style="font-size: 12px">字段合并列数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="fColspan_insert" name="fColspan" value="" style="width: 200px;height: 20px" data-options="required:true,validType:'checkNum'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字段合并行数：</span></td>
            <td align="left"><input class="easyui-textbox easyui-validatebox" id="fRowspan_insert" name="fRowspan" value="" style="width: 200px;height: 20px" data-options="required:true,validType:'checkNum'"></td>
            <td align="right"><span style="font-size: 12px">字段类型：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="fTyp_insert" name="fTyp" value="" style="width: 200px;height: 20px" data-options="required:true,editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">字典代码：</span></td>
            <td align="left">
                <input class="easyui-textbox" id="dictryId_insert" value="" style="width: 200px;height: 20px" data-options="" buttonIcon="icon-search">
                <input class="easyui-textbox" id="dictryId_hidden" name="dictryId" value="" type="hidden">
            </td>
            <td align="right"><span style="font-size: 12px">是否只读：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="readOnly_insert" name="readOnly" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">是否必输：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="mustInput_insert" name="mustInput" value="" style="width: 200px;height: 20px" data-options="editable:false,panelHeight:'auto'"></td>
            <td align="right"><span style="font-size: 12px">是否列表展示：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="listShowFlg_insert" name="listShowFlg" value="" style="width: 200px;height: 20px" data-options="required:false,editable:false,panelHeight:'auto'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">是否详情展示：</span></td>
            <td align="left"><input class="easyui-combobox easyui-validatebox" id="detailShowFlg_insert" name="detailShowFlg" value="" style="width: 200px;height: 20px" data-options="required:false,editable:false,panelHeight:'auto'"></td>
        </tr>
        <input class="easyui-textbox" type="hidden" id="id" name="id" value="">
        <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
    </table>
    <div id="win_sysTabConfig_insert"></div>
</form>
<div style="text-align:center">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="but_insert" plain=true onclick="submitForm()">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" id="but_reset" plain=true onclick="resetForm('form_insert')">重置</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeTabConfigWindow('win_sysTabConfig')">关闭</a>
</div>
<%--</div>--%>
<script type="text/javascript">

    $(function () {
        initSysTabConfigUpdatePage('<%=basePath%>','<%=tabConfig.getTabNm()%>','<%=tablId%>');
        //回显数据
        initTextboxSelf('id','<%=tabConfig.getId()%>',false);
        initTextboxSelf('tablId_insert','<%=tabConfig.getTablId()%>',true);
        initTextboxSelf('tabNm_insert','<%=tabConfig.getTabNm()%>',true);
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
        initTextboxSelf('dictryId_insert','<%=ExchangeUtil.exchangeDic(tabConfig.getDictryId())%>',false);
        initTextboxSelf('dictryId_hidden','<%=tabConfig.getDictryId()%>',false);
        assignComboboxSelf('readOnly_insert','<%=tabConfig.getReadOnly()%>',false);
        assignComboboxSelf('mustInput_insert','<%=tabConfig.getMustInput()%>',false);
        assignComboboxSelf('listShowFlg_insert','<%=tabConfig.getListShowFlg()%>',false);
        assignComboboxSelf('detailShowFlg_insert','<%=tabConfig.getDetailShowFlg()%>',false);
    })


    //表单异步提交（非easyUI表单提交）
    function submitForm() {
        //验证框校验
        if (!$("#form_insert").form('validate')){
            return false;
        }
        //提交非必输校验
        var fieldType = $('#fTyp_insert').combobox('getValue');
        var dictryId = $('#dictryId_insert').textbox('getValue');
        if (fieldType == 'combobox' && dictryId == ''){
            $.messager.alert("提示","当字段类型为下拉框或者组合框时，字典代码必须选择");
            return false;
        }
        //取消禁用
        $('#tablId_insert').textbox({
            disabled:false
        });
        $('#tabNm_insert').textbox({
            disabled:false
        });
        $('#mustInput_insert').textbox({
            disabled:false
        });
        //取消禁用之后，表单提交获取不到下拉框的value值
        var mustInput = $('#mustInput_insert').combobox('getValue');
        if (mustInput == '是'){
            mustInput = 'SYS0201';
        }else{
            mustInput = 'SYS0202';
        }
        $('#mustInput_insert').combobox('setValue',mustInput);
        $.ajax({
            url:'<%=basePath%>SysTabConfig/updateSysTabConfig',
            type:'post',
            dataType:'text',
            data:$('#form_insert').serialize(),
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                var d = data.data;
                if (data.success){
                    //修改成功后，数据回显
                    initTextboxSelf('tablId_insert',d.tablId,true);
                    initTextboxSelf('tabNm_insert',d.tabNm,true);
                    initTextboxSelf('seqn_insert',d.seqn,true);
                    initTextboxSelf('lNm_insert',d.lNm,true);
                    initTextboxSelf('lAlign_insert',d.lAlign,true);
                    initTextboxSelf('lColspan_insert',d.lColspan,true);
                    initTextboxSelf('lRowspan_insert',d.lRowspan,true);
                    initTextboxSelf('fNm_insert',d.fNm,true);
                    assignComboboxSelf('fAlign_insert',d.fAlign,true);
                    initTextboxSelf('fColspan_insert',d.fColspan,true);
                    initTextboxSelf('fRowspan_insert',d.fRowspan,true);
                    assignComboboxSelf('fTyp_insert',d.fTyp,true);
                    initTextboxSelf('dictryId_insert',d.dictryId,true);
                    assignComboboxSelf('readOnly_insert',d.readOnly,true);
                    assignComboboxSelf('mustInput_insert',d.mustInput,true);
                    assignComboboxSelf('listShowFlg_insert',d.listShowFlg,true);
                    assignComboboxSelf('detailShowFlg_insert',d.detailShowFlg,true);
                    //修改成功后，禁用提交/重置按钮
                    $('#but_insert').linkbutton('disable');
                    $('#but_reset').linkbutton('disable');
                    document.getElementById('close_flag').value = 'true';//标记关闭按钮
                    $.messager.alert("提示","修改成功");
                }else{
                    $.messager.alert("提示","修改失败");
                }
            }
        })
    }


    //easyui表单异步提交方式：弃用
    /*function submitForm() {
        $('#form_insert').form({
            url:'<%=basePath%>SysTabConfig/updateSysTabConfig',
            onSubmit:function () {
                //输入验证
            },
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                var d = data.data;
                if (data.success){
                    //修改成功后，数据回显
                    initTextboxSelf('tablId_insert',d.tablId,true);
                    initTextboxSelf('tabNm_insert',d.tabNm,true);
                    initTextboxSelf('seqn_insert',d.seqn,true);
                    initTextboxSelf('lNm_insert',d.lNm,true);
                    initTextboxSelf('lAlign_insert',d.lAlign,true);
                    initTextboxSelf('lColspan_insert',d.lColspan,true);
                    initTextboxSelf('lRowspan_insert',d.lRowspan,true);
                    initTextboxSelf('fNm_insert',d.fNm,true);
                    assignComboboxSelf('fAlign_insert',d.fAlign,true);
                    initTextboxSelf('fColspan_insert',d.fColspan,true);
                    initTextboxSelf('fRowspan_insert',d.fRowspan,true);
                    assignComboboxSelf('fTyp_insert',d.fTyp,true);
                    initTextboxSelf('dictryId_insert',d.dictryId,true);
                    assignComboboxSelf('readOnly_insert',d.readOnly,true);
                    assignComboboxSelf('mustInput_insert',d.mustInput,true);
                    assignComboboxSelf('listShowFlg_insert',d.listShowFlg,true);
                    assignComboboxSelf('detailShowFlg_insert',d.detailShowFlg,true);
                    //修改成功后，禁用提交/重置按钮
                    $('#but_insert').linkbutton('disable');
                    $('#but_reset').linkbutton('disable');
                    document.getElementById('close_flag').value = 'true';//标记关闭按钮
                    $.messager.alert("提示","修改成功");
                }else{
                    $.messager.alert("提示","修改失败");
                }
            }
        })
        $('#form_insert').form('submit');
    }*/

</script>
</body>
</html>

