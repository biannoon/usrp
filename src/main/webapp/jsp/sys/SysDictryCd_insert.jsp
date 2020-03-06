<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysDictryCd.js"></script>
</head>
<body>
<div class="easyui-layout" style="padding:5px;background:#eee;" fit="true">
    <div data-options="region:'west',title:'字典种类基本信息',split:true,border:false" style="width: 40%">
        <form id="form_sys_dictry_insert" method="post">
            <table width="100%" style="text-align: center"  cellpadding="5">
                <tr>
                    <td align="right"><span style="font-size: 12px">字典类型代码ID：</span></td>
                    <td align="left"><input class="easyui-validatebox" id="dictryId_insert" name="dictryId" data-options="required:true,validType:'uniqueDictry'"></td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: 12px">字典类型名称：</span></td>
                    <td align="left"><input class="easyui-validatebox" id="dictryNm_insert" name="dictryNm" data-options="required:true,validType:''"></td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: 12px">上级字典代码ID：</span></td>
                    <td align="left"><input class="easyui-validatebox" id="pareDictryId_insert" name="pareDictryId" data-options="required:true,validType:''"></td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: 12px">代码类型：</span></td>
                    <td align="left"><input class="easyui-validatebox" id="cdTyp_insert" name="cdTyp" data-options="required:true,validType:''"></td>
                </tr>
                <tr>
                    <td align="right" valign="top"><span style="font-size: 12px">字典说明：</span></td>
                    <td align="left" colspan="3"><input id="dictryComnt_insert" name="dictryComnt" multiline=true></td>
                </tr>
            </table>
        </form>
        <div style="text-align:center">
            <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add"
               iconCls="icon-save" onclick="submitSysDictry('form_sys_dictry_insert','dictionary/insertSysDictryCd')">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain=true
               iconCls="icon-remove" onclick="closeWindowBySysDictry('win_sys_dictry','dg_dictry')">关闭</a>
        </div>
    </div>
    <div data-options="region:'center',title:'代码值列表',split:true,border:false" style="width: 60%">
        <div id="dg_sys_dictry_insert_tool">
            <table width="100%" style="text-align: center">
                <tr>
                    <td align="right" colspan="4">
                        <a href="#" class="easyui-linkbutton" plain=true
                           onclick="toSysDictryValueInsertPage('win_sys_dictry_insert','代码值新增','dictionary/toSysDictryCdInsertValue','350','200','cache')" >新增</a>
                        <a href="#" class="easyui-linkbutton" plain=true
                           onclick="toSysDictryValueUpdatePage('win_sys_dictry_insert','代码值修改','dictionary/toSysDictryCdUpdateValue','350','200','cache')" >修改</a>
                        <a href="#" class="easyui-linkbutton" plain=true
                           onclick="deleteSysDictryValue('cache')" >删除</a>
                    </td>
                </tr>
            </table>
        </div>
        <div>
            <table id="dg_sys_dictry_insert" width="100%"></table>
        </div>
    </div>
</div>
<input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
<div id="win_sys_dictry_insert"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initSysDictryInsertPage();
    })

    //提交表单
    function submitSysDictry(formId,url) {
        //验证框校验
        if (!$("#" + formId).form('validate')){
            return false;
        }
        //-取消禁用(上级字典代码ID)
        $('#pareDictryId_insert').textbox('enable');
        $('#cdTyp_insert').combobox('enable');
        //-提交ajax
        $.ajax({
            url:basePath + url,
            type:'post',
            dataType:'text',
            data:$('#' + formId).serialize(),
            success:function (data) {
                var data = eval('(' + data + ')');
                var result = data.data;
                if (data.success){
                    //-刷新字典代码列表
                    refreshDataGridBySelf("dg_dictry");
                    //-禁用提交按钮
                    $('#btn_add').linkbutton('disable');
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        })
    }
</script>
</body>
</html>
