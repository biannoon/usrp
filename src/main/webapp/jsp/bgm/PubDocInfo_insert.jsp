<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
    <form id="form_insert" method="post" enctype="multipart/form-data">
        <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
            <tr>
                <td align="right"><span style="font-size: 12px">选择文件：</span></td>
                <td align="left">
                    <input class="easyui-filebox easyui-validatebox"
                           id="docFile_insert"
                           name="docFile"
                           buttonText='选择文件'
                           data-options="required:false,validType:''"
                           style="height:20px;width: 200px">
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: 12px">所属报送系统：</span></td>
                <td align="left">
                    <input class="easyui-textbox easyui-validatebox"
                           id="subsystemId_insert"
                           buttonIcon="icon-search"
                           data-options="required:false,validType:''"
                           style="height:20px;width: 200px">
                    <input class="easyui-textbox" id="subsystemId_hid" name="subsystemId" type="hidden">
                </td>
            </tr>
            <tr>
                <td align="right" valign="top"><span style="font-size: 12px">文件说明：</span></td>
                <td align="left" colspan="4">
                    <input class="easyui-textbox easyui-validatebox"
                           id="docComnt_insert"
                           name="docComnt"
                           style="width: 200px;height: 150px"
                           data-options="required:false,validType:'',multiline:true"/>
                </td>
            </tr>
        </table>
    </form>
    <div style="text-align:center">
        <a href="javascript:void(0)" class="easyui-linkbutton" plain=true iconCls="icon-save"
           onclick="submitForm('form_insert')">提交</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain=true iconCls="icon-cancel"
           onclick="clearForm()">重置</a>
    </div>
<div id="win_pub_doc_info_insert"></div>
<script type="text/javascript">
    $(function () {
        //-初始化子系统选择组件
        initSubsystemComponent('win_pub_doc_info_insert','subsystemId_insert','subsystemId_hid')
    })

    //重置
    function clearForm() {
        $('#form_insert').form('reset');
    }

    //提交
    function submitForm(formId) {
        var url = 'pubdocinfo/save';
        $('#'+formId).form({
            url:basePath + url,
            onSubmit:function () {
                //验证框校验
                if (!$("#"+formId).form('validate')){
                    return false;
                }
            },
            success:function (data) {
                var data = eval('(' + data + ')');
                var result = data.data;
                if (data.success){
                    //-刷新角色列表
                    refreshDataGridBySelf("dg_pub_doc_info");
                    //-关闭窗口
                    $('#win_pub_doc_info').window('close');
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        })
        $('#form_insert').form('submit');
    }


</script>
</body>
</html>
