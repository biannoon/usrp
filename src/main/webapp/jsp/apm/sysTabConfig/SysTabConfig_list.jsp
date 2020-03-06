<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/28
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String funcId = (String)request.getAttribute("funcId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysTabConfig.js"></script>
</head>
<body id="tabConfigPage" class="easyui-layout" style="background:#eee;">
<div data-options="region:'center',title:'数据接口列表'" style="width:50%;background:#eee;">
    <table width="96%">
        <tr>
            <td>
                <div>
                    <div id="dg_search" style="padding:3px">
                        <table width="100%" style="text-align: center">
                            <tr>
                                <td align="right"><span style="font-size: 12px">接口中文名：</span></td>
                                <td align="left"><input class="easyui-textbox" id="cnNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                                <td align="right"><span style="font-size: 12px">接口英文名：</span></td>
                                <td align="left"><input class="easyui-textbox" id="enNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            </tr>
                            <tr>
                                <td align="right"><span style="font-size: 12px">所属数据源：</span></td>
                                <td align="left"><input class="easyui-textbox" id="dataSorcId_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                                <td align="right"><span style="font-size: 12px">接口状态：</span></td>
                                <td align="left"><input class="easyui-combobox" id="tablStus_se" style="width: 150px;height: 20px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                            </tr>
                            <tr>
                                <td align="right"><span style="font-size: 12px">所属业务分类：</span></td>
                                <td align="left"><input class="easyui-combobox" id="bizClsf_se" style="width: 150px;height: 20px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                                <td align="right" colspan="2">
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="doTableDfSearch()">查询</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain=true onclick="resetTableDfSearch()">重置</a>
                                    <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" plain=true onclick="toSysTabConfigList('系统表单配置','<%=basePath%>SysTabConfig/toTabConfigList','<%=funcId%>')">表单配置</a>--%>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <table id="dg_table_dif" class="easyui-datagrid"></table>
                </div>
            </td>
        </tr>
    </table>
</div>
<div data-options="region:'east',title:'表单配置',hideCollapsedContent:false,collapsed:false" style="width:50%;">
    <table width="100%">
        <tr>
            <td>
                <div id="dg_config_search" style="padding:3px">
                    <table width="100%" style="text-align: center">
                        <tr>
                            <td align="right"><span style="font-size: 12px;">表名：</span></td>
                            <td align="left"><input class="easyui-textbox" id="tabNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            <td align="right"><span style="font-size: 12px;">标签名称：</span></td>
                            <td align="left"><input class="easyui-textbox" id="lNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                        </tr>
                        <tr>
                            <td align="right"><span style="font-size: 12px;">字段名称：</span></td>
                            <td align="left"><input class="easyui-textbox" id="fNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            <td align="right"><span style="font-size: 12px;">字段类型：</span></td>
                            <td align="left"><input class="easyui-combobox" id="fTyp_se" style="width: 150px;height: 20px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="8">
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="doConfigSearch()" >查询</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain=true onclick="resetConfigSearch()">重置</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsertTabConfigPage('win_sysTabConfig','表单配置新增','<%=basePath%>SysTabConfig/toInsertSysTabConfig','750','400')">新增</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true onclick="toModifyTabConfigPage('win_sysTabConfig','表单配置更新','<%=basePath%>SysTabConfig/toUpdateSysTabConfig','750','400')">更新</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDeleteTabConfig('<%=basePath%>SysTabConfig/deleteSysTabConfig')">删除</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true onclick="toDetailTabConfigPage('win_sysTabConfig','表单详情','<%=basePath%>SysTabConfig/toSysTabConfigDetail','750','400')">详情</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div><table id="dg_sysTabConfig" width="100%"></table></div>
            </td>
        </tr>
    </table>
</div>
<!--用于标记选择的任务id-->
<input class="easyui-textbox" type="hidden" id="tablId_hidden" value="">
<!--动态生成模态框-->
<div id="win_sysTabConfig"></div>
<script type="text/javascript">
    $(function(){
        //初始化列表
        initSysTabConfigDfPage('<%=basePath%>');
    })
</script>
</body>
</html>
