<%@ page import="com.scrcu.sys.entity.SysFunc" %><%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/10
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String subsystemId = (String) request.getAttribute("subsystemId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>系统功能菜单新增</title>
    <meta charset="UTF-8">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysfunc.js"></script>
</head>
<body>
<div style="padding:5px;">
    <div style="padding:10px 60px 20px 60px">
        <form id="form_sysfunc_insert" method="post">
            <table cellpadding="5" width="100%" style="text-align: center">
                <tr>
                    <td align="right"><span style="font-size: small">功能编号：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="funcId_insert" name="funcId" data-options="required:true,validType:'uniqueFuncId'"/>
                    </td>
                    <td align="right"><span style="font-size: small">功能名称：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="funcNm_insert" name="funcNm" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: small">功能类型：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="funcType_insert" name="funcType" data-options="required:true,editable:false,panelHeight:'auto'"/>
                    </td>
                    <td align="right"><span style="font-size: small">是否为叶子菜单：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="isLeaf_insert" name="isLeaf" data-options="required:false,editable:false,panelHeight:'auto'"/>
                    </td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: small">菜单路径：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="url_insert" name="url" data-options="required:false,validType:'checkUrl'"/>
                    </td>
                    <td align="right"><span style="font-size: small">上级功能编号：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="pareFuncId_insert" name="pareFuncId"/>
                    </td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: small">所属系统：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="subsystemId_insert" name="subsystemId"/>
                    </td>
                    <td align="right"><span style="font-size: small">顺序号：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="seqNo_insert" name="seqNo" data-options="required:true,validType:''"/>
                    </td>
                </tr>
                <tr>
                    <td align="right"><span style="font-size: small">图标路径：</span></td>
                    <td align="left">
                        <input class="easyui-validatebox" id="iconUrl_insert" name="iconUrl" />
                    </td>
                </tr>
                <tr>
                    <td align="right" valign="top"><span style="font-size: small">功能说明：</span></td>
                    <td align="left" colspan="4">
                        <input class="easyui-validatebox" id="funcComnt_insert" name="funcComnt"  data-options="required:false,validType:'',multiline:true" />
                    </td>
                </tr>
                <input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
            </table>
        </form>
        <div style="text-align:center">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="but_insert" iconCls="icon-save" plain=true onclick="submitForm('<%=basePath%>sysFunc/insert')">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="but_reset" iconCls="icon-set" plain=true onclick="resetForm('form_sysfunc_insert')">重置</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="but_close" iconCls="icon-cancel" plain=true onclick="closeWindowFromSysFunc('win_sysFunc','tree_sysFunc','dg_sysFunc')">关闭</a>
        </div>
    </div>
</div>
<script type="text/javascript">

    $(function () {
        var pid = $('#pid').val();//上级节点ID
        var subsystemId = $('#subsystemId_hid').val();//所属系统
        initTextboxByFunc('funcId_insert','',10,false,'200','20');
        initTextboxByFunc('funcNm_insert','',40,false,'200','20');
        initComboboxByFunc('funcType_insert','SYS07','',false,'200','20');
        initComboboxByFunc('isLeaf_insert','SYS02','',false,'200','20');
        initTextboxByFunc('url_insert','',200,false,'200','20');
        initTextboxByFunc('pareFuncId_insert',pid,10,true,'200','20');
        initTextboxByFunc('subsystemId_insert',subsystemId,32,true,'200','20');
        initTextboxByFunc('seqNo_insert','',0,false,'200','20');
        initTextboxByFunc('iconUrl_insert','icon-set',200,false,'200','20');
        initTextboxByFunc('funcComnt_insert','',200,false,'200','100');
    })

    //表单异步提交（非easyui表单提交）
    function submitForm(url) {
        //规则验证
        if (!$('#form_sysfunc_insert').form('validate')){
            return false;
        }
        //解除禁用
        $('#pareFuncId_insert').textbox('enable');
        $('#subsystemId_insert').textbox('enable');
        //提交校验
        if (!validateSubmitForm()){
            return false;
        }
        //提交
        $.ajax({
            url:url,
            type:'post',
            dataType:'text',
            data:$('#form_sysfunc_insert').serialize(),
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                var d = data.data;
                if (data.success){
                    //新增成功后，禁用新增/重置按钮
                    $('#but_insert').linkbutton('disable');
                    $('#but_reset').linkbutton('disable');
                    //标记关闭按钮
                    document.getElementById('close_flag').value = 'true';
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            },
            error:function () {
                $.messager.alert("提示","请求失败")
            }
        })
    }

    function validateSubmitForm() {
        var funcType = $('#funcType_insert').val();
        var isLeaf = $('#isLeaf_insert').val();
        var url = $('#url_insert').val();
        if (funcType == 'SYS0702' && isLeaf == ''){
            $.messager.alert("提示","当功能类型选择为“菜单”时，是否为叶子菜单必填！");
            return false;
        }
        if (funcType == 'SYS0702' && isLeaf == 'SYS0201' && url == ''){
            $.messager.alert("提示","当功能类型选择为“菜单”，并且为叶子节点时，菜单路径必填！");
            return false;
        }
        return true;
    }
</script>
</body>
</html>
