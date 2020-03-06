<%@ page import="com.scrcu.sys.entity.SysRole" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/18
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>
<%
    String type = (String) request.getAttribute("type");
    SysRole role = null;
    if (!"insert".equals(type)){
        role = (SysRole) request.getAttribute("sysRole");
    }else{
        role = new SysRole();
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysRole.js"></script>
</head>
<body>
<div>
    <form id="detail_form"  method="post">
        <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
            <tr>
                <td align="right"><span style="font-size: 12px">角色编号：</span></td>
                <td align="left">
                    <%if ("insert".equals(type)){%>
                    <input class="easyui-validatebox" id="roleId_insert" name="roleId" data-options="required:true,validType:'uniqueRoleId'">
                    <%}else{%>
                    <input class="easyui-validatebox" id="roleId_insert" name="roleId" data-options="required:true,validType:''">
                    <%}%>
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: 12px">角色名称：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="roleNm_insert" name="roleNm" data-options="required:true,validType:''">
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: 12px">所属系统：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="sys_insert" data-options="required:true,validType:''" buttonIcon="icon-search">
                    <input class="easyui-textbox" id="sys_insert_hid" name="sys" type="hidden" value="<%=role.getSys()==null?"":role.getSys()%>">
                </td>
            </tr>
            <tr>
                <td align="right"><span style="font-size: 12px">角色层级关系：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="roleHrchCd_insert" name="roleHrchCd" data-options="required:true,validType:'',editable:false,panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td align="right" valign="top"><span style="font-size: 12px">角色说明：</span></td>
                <td align="left">
                    <input class="easyui-validatebox" id="roleComnt_insert" name="roleComnt" data-options="required:false,validType:'',multiline:true"></input>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="win_sys_role_detail"></div>
<div id="win_sys_subsystem"></div>
<div class="btn-area">
    <%if ("insert".equals(type)){%>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add"
       onclick="submitFormBySysRole('detail_form','sysRole/insert')" text="保存" icon="icon-save"></a>
    <%}else if("update".equals(type)){%>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add"
       onclick="submitFormBySysRole('detail_form','sysRole/update')" text="保存" icon="icon-save"></a>
    <%}%>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true
       onclick="clearForm()" text="重置" icon="icon-cancel"></a>
</div>
<script type="text/javascript">
    $(function () {
        <%if ("insert".equals(type)){%>
        initTextboxByRole('roleId_insert','',10,false,'200','20');
        <%}else{%>
        initTextboxByRole('roleId_insert','<%=role.getRoleId()==null?"":role.getRoleId()%>',10,true,'200','20');
        <%}%>
        initTextboxByRole('roleNm_insert','<%=role.getRoleNm()==null?"":role.getRoleNm()%>',60,false,'200','20');
        initTextboxByRole('sys_insert',exchangeSubsystemNm('<%=role.getSys()==null?"":role.getSys()%>'),30,false,'200','20');
        initSubsystemComponent('win_sys_subsystem','sys_insert','sys_insert_hid')
        initComboboxByRole('roleHrchCd_insert','SYS06','<%=role.getRoleHrchCd()==null?"":role.getRoleHrchCd()%>',false,'200','20');
        initTextboxByRole('roleComnt_insert','<%=role.getRoleComnt()==null?"":role.getRoleComnt()%>',200,false,'200','100');
    })

    //重置
    function clearForm() {
        $('#detail_form').form('reset');
    }
</script>
</body>
</html>
