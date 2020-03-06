<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

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
<body class="easyui-layout" style="width: 100%">
    <div data-options="region:'center',title:'角色信息列表',split:true" style="width:50%;">
        <div id="dg_sys_role_tool">
            <table width="100%">
                <tr>
                    <td align="right"><span style="font-size:12px ">角色名称：</span></td>
                    <td align="left"><input id="role_nm_se"></td>
                    <td align="right"><span style="font-size:12px ">角色层级：</span></td>
                    <td align="left"><input id="roleHrchCd_lev_se" data-options="editable:false,panelHeight:'auto'"></td>
                </tr>
                <tr>
                    <td colspan="4" align="right">
                        <a href="#" class="easyui-linkbutton" plain=true iconCls="icon-search"
                           onclick="searchSysRole()">查询</a>
                        <a href="#" class="easyui-linkbutton" plain=true iconCls="icon-cancel"
                           onclick="resetSearchSysRole()">重置</a>
                        <a href="#" onclick="toDeailPage('insert','win_sys_role','角色信息新增','sysRole/toDetailPage','400','350');"
                           class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"></a>
                        <a href="#" onclick="toDeailPage('update','win_sys_role','角色信息修改','sysRole/toDetailPage','400','350');"
                           class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"></a>
                        <a href="#" onclick="toDeleteSysRole('sysRole/delete')"
                           class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"></a>
                        <a href="#" onclick="toSysRoleFuncConf();"
                           class="easyui-linkbutton" text="菜单配置" iconCls="icon-search" plain="true"></a>
                    </td>
                </tr>
            </table>
        </div>
        <div><table id="dg_sys_role"></table></div>
    </div>
    <div data-options="region:'east',title:'已分配用户列表',split:true" style="width: 50%">
        <div id="dg_sys_user_tool">
            <table width="100%" style="text-align: center">
                <tr>
                    <td align="right"><span style="font-size:12px ">用户名称:</span></td>
                    <td align="left"><input id="userNm_se"></td>
                    <td align="right" ><span style="font-size:12px ">所属机构:</span></td>
                    <td align="left"><input id="blngtoOrgNo_se"></td>
                </tr>
                <tr>
                    <td align="right" colspan="4">
                        <a href="#" class="easyui-linkbutton" plain=true iconCls="icon-search"
                           onclick="searchSysUser()">查询</a>
                        <a href="#" class="easyui-linkbutton" plain=true iconCls="icon-cancel"
                           onclick="resetSearchSysUser()">重置</a>
                    </td>
                </tr>
            </table>
        </div>
        <div><table id="dg_sys_user"></table></div>
    </div>
<div id="win_sys_role"></div>
<script type="text/javascript">
    $(function(){
        initSysRolePage();
    });
</script>
</body>
</html>