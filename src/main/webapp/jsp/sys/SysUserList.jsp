<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/11/12
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysUser.js"></script>
</head>
<body class="easyui-layout" style="background:#eee;">
    //查询条件区
    <div data-options="region:'west',title:'用户列表',split:true" style="width:60%;">
        <div id="dg_tool" style="">
            <table width="96%" style="text-align: center">
                <tr>
                    <td align="right"><span style="font-size: 12px">用户编号：</span></td>
                    <td align="left"><input id="userId_User"></td>
                    <td align="right"><span style="font-size: 12px">用户姓名：</span></td>
                    <td align="left"><input id="userNm"></td>
                    <td align="right" style="font-size: small;font-family: Microsoft Yahei"><span style="font-size: 12px">所属机构：</span></td>
                    <td align="left"><input id="blngtoOrgNo"></td>
                </tr>
                <tr>
                    <td align="right" style="font-size: small;font-family: Microsoft Yahei"><span style="font-size: 12px;">状态：</span></td>
                    <td align="left"><input id="stus" data-options="editable:false,panelHeight:'auto'"></td>
                    <td colspan="4" align="right">
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" size="small"
                           onclick="doSearchSysUser()">查询</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-set'" plain="true" size="small"
                           onclick="clearSearchSysUser()">重置</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" size="small"
                           onclick="toInsertSysUser('win_sys_user','用户信息新增','sysUser/input','650','250')">新增</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" size="small"
                           onclick="toUpdSysUser('win_sys_user','用户信息修改','sysUser/toUpdate','650','250')">修改</a>
                        <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" size="small" onclick="toDeleteUser()">删除</a>--%>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain="true" size="small"
                           onclick="toCancel()">注销</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" plain="true" size="small"
                           onclick="toRecover()">恢复</a>
                    </td>
                </tr>
                <tr>
                    <td align="right" colspan="6">
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-set'" plain="true" size="small"
                           onclick="resetPwd()">密码重置</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-more'" plain="true" size="small"
                           onclick="distributeGroup()">用户组分配</a>
                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-man'" plain="true" size="small"
                           onclick="distributeRole()">角色分配</a>
                    </td>
                </tr>
            </table>
        </div>
        <div>
            <table id="dg_sysUser"></table>
        </div>
        <input class="easyui-textbox" id="groupId_hidden" type="hidden" value=""><!--隐藏框用于标记用户组-->
    </div>
    //列表展示区
    <div data-options="region:'center',title:''" style="background:#eee;">
        <div id="tbs_sysGroup" class="easyui-tabs">
            <div title="已分配用户组" data-options="iconCls:'icon-more',closable:false" style="overflow:auto;">
                <div id="dg_sysGroup_tool" style="">
                    <table width="96%" style="text-align: center">
                        <tr>
                            <td align="right" ><span style="font-size: 12px">用户组名称：</span></td>
                            <td align="left"><input id="groupNm"></td>
                            <td align="right" ><span style="font-size: 12px">所属机构：</span></td>
                            <td align="left"><input id="blngtoOrgNo_group"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="4">
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" size="small"
                                   onclick="doSearchUserSysGroup()">查询</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-set'" plain="true" size="small"
                                   onclick="clearSearchUserSysGroup()">重置</a>
                                <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" size="small" onclick="SearchSysGroupResource()">资源查看</a>--%>
                            </td>
                        </tr>
                    </table>
                </div>
                <div>
                    <table id="dg_sysGroup" style="width: 100%"></table>
                </div>
            </div>
            <div title="已分配角色" data-options="iconCls:'icon-man',closable:false">
                <div id="dg_sysRole_tool" style="">
                    <table width="100%" style="text-align: center">
                        <tr>
                            <td align="right"><span style="font-size: 12px">角色名称：</span></td>
                            <td align="left"><input id="roleNm" ></td>
                            <td align="right"><span style="font-size: 12px">角色层级：</span></td>
                            <td align="left"><input id="roleHrchCd" data-options="panelHeight:'auto'"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="4">
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true"
                                   onclick="doSearchUserSysRole()">查询</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-set'" plain="true"
                                   onclick="clearSearchUserSysRole()">重置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div>
                    <table id="dg_sysRole" style="width: 100%"></table>
                </div>
            </div>
        </div>
    </div>
    <div id="win_sys_user"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initSysUserListPage();
    })
</script>
<!-- 弹出窗口-->
<div id="newWin"></div>
</body>
</html>
