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
<body style="padding:5px;background:#eee;">
<table width="100%">
    <tr>
        <td>
            <div>
                <div id="dg_search" style="padding:3px">
                    <table width="100%" style="text-align: center">
                        <tr>
                            <td align="right"><span style="font-size: 12px">数据接口中文名：</span></td>
                            <td align="left"><input class="easyui-textbox" id="cnNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            <td align="right"><span style="font-size: 12px">数据接口英文名：</span></td>
                            <td align="left"><input class="easyui-textbox" id="enNm_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                            <td align="right"><span style="font-size: 12px">所属数据源：</span></td>
                            <td align="left"><input class="easyui-textbox" id="dataSorcId_se" style="width: 150px;height: 20px;border:1px solid #ccc"></td>
                        </tr>
                        <tr>
                            <td align="right"><span style="font-size: 12px">数据接口状态：</span></td>
                            <td align="left"><input class="easyui-combobox" id="tablStus_se" style="width: 150px;height: 20px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                            <td align="right"><span style="font-size: 12px">所属业务分类：</span></td>
                            <td align="left"><input class="easyui-combobox" id="bizClsf_se" style="width: 150px;height: 20px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="6">
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true onclick="doTableDfSearch()">查询</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" plain=true onclick="resetTableDfSearch()">条件重置</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" plain=true onclick="toSysTabConfigList('系统表单配置','<%=basePath%>SysTabConfig/toTabConfigList','<%=funcId%>')">表单配置</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <table id="dg_sysTabConfig" class="easyui-datagrid">
                    <thead>
                    <tr>
                        <th data-options="field:'id',checkbox:true"></th>
                        <th data-options="field:'tablId',width:200,align:'center'">数据接口ID</th>
                        <th data-options="field:'cnNm',width:150,align:'center'">数据接口中文名</th>
                        <th data-options="field:'enNm',width:150,align:'center'">数据接口英文名</th>
                        <th data-options="field:'dataSorcId',width:50,align:'center'">所属数据源</th>
                        <th data-options="field:'tablTyp',width:100,align:'center',dictry:'DFI01',formatter:formatData">数据接口分类</th>
                        <th data-options="field:'bizClsf',width:100,align:'center',dictry:'DFI03',formatter:formatData">数据接口所属业务分类</th>
                        <th data-options="field:'tablStus',width:100,align:'center',dictry:'DFI02',formatter:formatData">数据接口状态</th>
                        <%--<th data-options="field:'creatr',width:120,align:'center'">创建人</th>
                        <th data-options="field:'crtDt',width:120,align:'center'">创建时间</th>
                        <th data-options="field:'finlModifr',width:120,align:'center'">最后修改人</th>
                        <th data-options="field:'finlModfyDt',width:120,align:'center'">最后修改时间</th>--%>
                    </tr>
                    </thead>
                </table>
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function(){
        //初始化列表
        initSysTabConfigDfPage('<%=basePath%>');
    })
</script>
</body>
</html>
