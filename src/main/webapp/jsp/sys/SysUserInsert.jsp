<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysUser.js"></script>
</head>
<body>
<form id="detail_form"  method="post">
    <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: 12px">用户编号：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="userId_insert" name="userId" data-options="required:true,validType:'uniqueUserId'">
            </td>
            <td align="right"><span style="font-size: 12px">用户名称：</span></td>
            <td align="left">
                <input class="easyui-validatebox"  id="userNm_insert" name="userNm" value="" data-options="required:true">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">密码：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="pwd_insert" name="pwd" data-options="required:true">
            </td>
            <td align="right"><span style="font-size: 12px">性别：</span></td>
            <td align="left">
                <input class="easyui-validatebox"  id="genderCd_insert" name="genderCd" data-options="required:true,panelHeight:'auto'">
            </td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">证件号码：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="crtfNo_insert" name="crtfNo" data-options="required:true">
            </td>
            <td align="right"><span style="font-size: 12px">出生日期：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="brthdy_insert" name="brthdy" data-options="required:true">
            </td>
        </tr>
       <tr>
        <td align="right"><span style="font-size: 12px">联系电话：</span></td>
        <td align="left">
            <input class="easyui-validatebox" id="telNo_insert" name="telNo" data-options="required:true">
        </td>
           <td align="right"><span style="font-size: 12px">通讯地址：</span></td>
           <td align="left">
               <input class="easyui-validatebox" id="addr_insert" name="addr" data-options="required:true">
           </td>
       </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">所属机构号：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="blngtoOrgNo_insert" data-options="required:true" buttonIcon="icon-search"/>
                <input class="easyui-textbox" id="blngtoOrgNo_hid" name="blngtoOrgNo" type="hidden"/>
            </td>
            <td align="right"><span style="font-size: 12px">状态：</span></td>
            <td align="left">
                <input class="easyui-validatebox" id="stus_insert" name="stus" data-options="required:true,panelHeight:'auto'">
            </td>
        </tr>
    </table>
</form>
<input type="hidden" id="close_flag" value=""><!--标记判断关闭window后是否需要刷新页面-->
<div class="btn-area">
    <a href="javascript:void(0)" class="easyui-linkbutton" id="btn_add" plain=true onclick="save()" text="保存" icon="icon-save"></a>
    <a href="javascript:void(0)" class="easyui-linkbutton" id="btn_reset "plain=true icon="icon-cancel" text="重置" onclick="resetTargetForm('detail_form')"></a>
    <a href="javascript:void(0)" class="easyui-linkbutton" id="btn_close "plain=true icon="icon-cancel" text="关闭" onclick="closeSysUserWindow('win_sys_user','dg_sysUser')"></a>
</div>
<!-- 弹出窗口-->
<div id="win_sys_user_insert_emp"></div>
<div id="win_sys_user_insert_org"></div>
<script type="text/javascript">
    $(function(){
        initSysUserInsertPage();
    })


    function save() {
        //规则验证
        if (!$('#detail_form').form('validate')){
            $.messager.alert('提示信息','请补全信息输入','info');
            return false;
        }
        ajaxSubmit("post",basePath+"sysUser/insert",$("#detail_form").serialize(),"json");
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
                    //新增成功后，禁用提交/重置按钮
                    $('#btn_add').linkbutton('disable');
                    $('#btn_reset').linkbutton('disable');
                    //标记关闭按钮
                    document.getElementById('close_flag').value = 'true';//标记关闭按钮
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
</script>
</body>
</html>