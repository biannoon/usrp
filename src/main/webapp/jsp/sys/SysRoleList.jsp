<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title></title>
        <script type="text/javascript">
            $(function(){
               <!-- getListShow("dataGrid", "角色信息列表", "sysRole", false);-->
                $('#dg').datagrid({
                    url:'/usrp/sysRole/getByPage',
                    fitColumns:true,
                    singleSelect:true,
                    pagination:true,
                    pageNumber:'1',
                    pageSize:'10',
                    onDblClickRow: function (rowIndex, rowData) {
                        var row = $('#dg').datagrid('getSelected');
                        var id = row.roleId;
                        if(row == null){
                            $.messager.alert("提示","请选择需要操作的用户角色！");
                        }else {
                            $('#sysUserDg').datagrid('load', {
                                roleId: id,
                                userNm: $('#userNm1').val(),
                                stus: $('#stus1').val(),
                            });
                        }
                    },
                    onLoadSuccess: function (data) {
                        //去除表头的复选框（表头复选框有全选的作用）
                        $(".datagrid-header-check").html("");
                        $('.datagrid-cell').css('font-size','12px');
                    },
                });
                $('#sysUserDg').datagrid({
                    url: basePath + 'sysUser/getShowRoleUser',
                    title: '用户列表',
                    fitColumns:true,
                    singleSelect:true,
                    pagination:true,
                    pageNumber:'1',
                    pageSize:'10',
                    onLoadSuccess: function (data) {
                        //去除表头的复选框（表头复选框有全选的作用）
                        $(".datagrid-header-check").html("");
                        $('.datagrid-cell').css('font-size','12px');
                    },
                });
                initComboboxSelf('roleHrchCd_lev','SYS06','<%=basePath%>');
                initComboboxSelf('stus1','SYS05','<%=basePath%>');
            });
            //新增系统角色菜单配置窗口
            function toSysRoleFuncConf(title,url,width,height) {
                var row = $('#dg').datagrid('getSelected');
                if(row == null){
                    $.messager.alert("提示","请选择需要操作的记录！");
                }else{
                    newWindow('角色菜单配置','/usrp/sysRole/toSysRoleFuncConf?roleId='+row.roleId,500,430);
                }
            }
            //新开window窗口
            function newWindow(title,url,width,height) {
                $('#win').window({
                    width:width,
                    height:height,
                    title:title,
                    modal:true
                });
                $('#win').window('refresh', url);
            }
            //新增窗口
            function toInsert(title,url,width,height) {
                newWindow(title, url, width, height);
            }
            //新增窗口
            function toUpdate(title,url,width,height) {
                var row = $('#dg').datagrid('getSelected');
                if(row == null){
                    $.messager.alert("提示","请选择需要操作的记录！");
                }else {
                    url = url + '?roleId=' + row.roleId;
                    newWindow(title, url, width, height);
                }
            }
            function clearForm() {
                $('#query_form').form('reset');
            }
            function clearUserForm() {
                $('#userShow_form').form('reset');
            }
            //对分页查询按钮的用法封装
            function SearchUser(){
                var row = $('#dg').datagrid('getSelected');
                var id = row.roleId;
                if(row == null){
                    $.messager.alert("提示","请选择需要操作的用户角色！");
                }else{
                    $('#sysUserDg').datagrid('load',{
                        roleId: id,
                        userNm: $('#userNm1').val(),
                        stus: $('#stus1').val(),
                    });
                }
            }
            //查询
            function doSearch() {
                $('#dg').datagrid('load',{
                    roleNm: $('#role_nm').val(),
                    roleHrchCd: $('#roleHrchCd_lev').val(),
                });
            }
        </script>
    </head>
    <body >
    <div id="win"></div>
    <div class="easyui-layout" style="width:1200px;height:600px;">
        <div id="p" data-options="region:'west',split:true" title="角色信息列表" style="width:53%;">
            <form id="query_form" method="post" action="sysRole/getByPage" >
                <table width="100%" style="text-align: center">
                    <tr>
                        <td align="right"><span style="font-size:small ">角色名称：</span></td>
                        <td align="left"><input  class="easyui-textbox" id="role_nm" name="roleNm" style="height:25px;width:120px;border:1px solid #ccc"></td>
                        <td align="right"><span style="font-size:small ">角色层级：</span></td>
                        <td align="left"><input  class="easyui-combobox" id="roleHrchCd_lev" name="roleHrchCd" dictno="SYS06"  style="height:25px;width:120px;border:1px solid #ccc"></td>
                        <td align="right">
                            <a href="#" class="easyui-linkbutton" onclick="doSearch()">查询</a>
                            <a href="#" class="easyui-linkbutton" onclick="clearForm()">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="">
                <a href="#" onclick="toInsert('角色信息新增','/usrp/sysRole/input','650','330')"
                   class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"></a>
                <a href="#" onclick="toUpdate('角色信息修改','/usrp/sysRole/updat','650','330');"
                   class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"></a>
                <a href="#" onclick="del('dg','sysRole/delete','roleId')"
                   class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"></a>
                <a href="#" onclick="toSysRoleFuncConf('角色菜单配置','','650','330')"
                   class="easyui-linkbutton" text="菜单配置" iconCls="icon-search" plain="true"></a>
            </div>
            <!-- 数据列表展示区域-->
            <div style="width: 100%">
                <table id="dg" fitColumns="true">
                    <thead>
                    <tr>
                        <th data-options="field:'cb',checkbox:true"></th>
                        <th data-options="field:'roleId',width:80,align:'center'">角色ID</th>
                        <th data-options="field:'roleNm',width:150,align:'center'">角色名称</th>
                        <th data-options="field:'roleHrchCd',width:100,align:'center',dictry:'SYS06',formatter:formatData">角色层级</th>
                        <th data-options="field:'crtDt',width:110,align:'center'">创建日期</th>
                        <th data-options="field:'creatr',width:80,align:'center'">创建人</th>
                        <th data-options="field:'finlModfyDt',width:110,align:'center'">最后修改日期</th>
                        <th data-options="field:'finlModifr',width:80,align:'center'">最后修改人</th>
                        <th data-options="field:'roleComnt',width:160,align:'center'">角色说明</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
        <div data-options="region:'center'"  style=" " title="已分配用户列表">

            <form id="userShow_form" method="post" action="sysRole/getShowRoleUser" >
                <table width="100%" style="text-align: center">
                    <tr>
                        <td align="right"><span style="font-size:small ">用户名称:</span></td>
                        <td align="left" ><input  class="easyui-textbox" id="userNm1" name="userNm" style="height:25px;width:100px;border:1px solid #ccc;"></td>
                        <td align="right" ><span style="font-size:small ">用户状态:</span></td>
                        <td align="left"><input  class="easyui-combobox" id="stus1" name="stus"   style="height:25px;width:100px;border:1px solid #ccc;"></td>
                        <td align="right">
                            <a href="#" class="easyui-linkbutton" onclick="SearchUser()">查询</a>
                            <a href="#" class="easyui-linkbutton" onclick="clearUserForm()">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
            <div style="">
             <table>
                 <tr></tr>
                 <tr></tr>
                 <tr></tr>
                 <tr></tr>
             </table>
            </div>
            <div style="width: 100%">
                    <table id="sysUserDg" class="easyui-datagrid" fitColumns="true">

                        <thead>
                        <tr>
                            <th data-options="field:'userId',width:80,align:'center'">用户编号</th>
                            <th data-options="field:'userNm',width:80,align:'center'">用户名称</th>
                            <th data-options="field:'blngtoOrgNo',width:120,align:'center'">所属机构</th>
                            <th data-options="field:'telNo',width:100,align:'center'">联系电话</th>
                            <th data-options="field:'stus',width:60,align:'center',dictry:'SYS05',formatter:formatData">用户状态</th>
                        </tr>
                        </thead>
                    </table>
            </div>
        </div>
    </div>

        <!-- 弹出窗口-->
        <div id="newWin"></div>
    </body>
</html>