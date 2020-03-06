<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/18
  Time: 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>映射接口配置</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/common/custom_win.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/chkruleconf.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ajax.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/combobox.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/bloodrela/bloodRelaTabl.js"></script>
    <style>
        .combobox-item-selected{
            background-color: rgb(87, 99, 255);
            color: #000000;
        }
    </style>
    <script type="text/javascript">
        var baseUrl = '<%=path%>';
        var basePath = '<%= basePath%>';
        var treeNode=null;
    </script>
</head>
<body class="easyui-layout">
<div  class="easyui-layout" style="width:98%;height:650px;margin-left: 1%;margin-right: 1%">
    <%--接口列表--%>
        <div  data-options="region:'center',title:'接口血缘分析'" style="padding:5px;">
            <div id="btnDiv" style="padding:5px;height:auto">
                <div>
                    接口中文名: <input id="cnNm" class="easyui-textbox"  style="width:200px;">
                    接口英文名: <input id="enNm" class="easyui-textbox"  style="width:200px">
                    所属数据源:
                    <select class="easyui-combobox" style="width:100px;" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '100px'"
                            id="dataSourceTabl">
                    </select>
                    接口状态:
                    <select class="easyui-combobox" style="width:100px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                            id="tableStatus">
                    </select>
                </div>
                <div style="margin-bottom:5px;text-align: right;padding-top: 20px;">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="showFieldWin()">字段血缘分析</a>
                    <a href="#" class="easyui-linkbutton" onclick="doSearch()" iconCls="icon-search">查询</a>
                </div>
            </div>
            <table id="bloodRelaTabl"></table>
        </div>
    <%--字段列表--%>
    <div id="difFieldWin" class="easyui-window" title="字段血缘关系" data-options="closed: true">
        <div id="fieldQuryDiv" style="padding:5px;height:auto">
            <div>
                字段中文名: <input id="fieldCnNm" class="easyui-textbox"  style="width:200px;">
                字段英文名: <input id="fieldEnNm" class="easyui-textbox"  style="width:200px">
            </div>
            <div style="margin-bottom:5px;text-align: right;padding-top: 20px;">
                <a href="#" class="easyui-linkbutton" onclick="showFieldRelaWin()" iconCls="icon-edit">数据补录</a>
                <a href="#" class="easyui-linkbutton" onclick="doSearchField()" iconCls="icon-search">查询</a>
            </div>
        </div>
        <table id="tablFielList"></table>
    </div>
        <div id="difFieldRelaWin" class="easyui-window" title="字段血缘关系" data-options="closed: true">
            <div id="fieldRelaDiv" style="padding:5px;height:auto">
                <div>
                    当前接口中文名: <input id="currCnNm" class="easyui-textbox"  style="width:200px;">
                    当前接口英文名: <input id="currEnNm" class="easyui-textbox"  style="width:200px;">
                    当前字段中文名: <input id="currFieldCnNm" class="easyui-textbox"  style="width:200px">
                    当前字段英文名: <input id="currFieldEnNm" class="easyui-textbox"  style="width:200px">
                </div>
                <div style="margin-bottom:5px;text-align: right;padding-top: 20px;">
                    <a href="#" class="easyui-linkbutton" onclick="showAddWin()" iconCls="icon-save">新增</a>
                    <a href="#" class="easyui-linkbutton" onclick="showEditWin()" iconCls="icon-edit">修改</a>
                    <a href="#" class="easyui-linkbutton" onclick="deleteReal()" iconCls="icon-remove">删除</a>
                    <a href="#" class="easyui-linkbutton" onclick="doSearchFieldRela()" iconCls="icon-search">查询</a>
                </div>
            </div>
            <table id="tablFielRelaList"></table>
        </div>
    <div id="difFieldRelaAddWIn" class="easyui-window" title="新增补录" data-options="closed: true">
            <table style="width: 80%;margin-top: 20px;margin-left: 20px;margin-right: 100px;">
                <tr>
                    <td>当前数据接口</td>
                    <td><input id="currTablNm" class="easyui-textbox ruleText"   data-options="required:true"></td>
                    <td>当前字段</td>
                    <td><input id="currFieldNm" class="easyui-textbox ruleText"   data-options="required:true"></td>
                </tr>
                <tr>
                    <td>下级数据接口</td>
                    <td><select class="easyui-combobox ruleText" prompt="请选中下级数据接口" data-options="editable: false, valueField: 'value',required:true,textField: 'text'"
                                id="relaTablId">
                    </select>
                    </td>
                    <td>下级字段</td>
                    <td><input id="relaFieldId" class="easyui-combobox ruleText" prompt="请选择下级字段" data-options="valueField:'fieldId',textField:'fieldCnNm'">
                    </td>
                </tr>
            </table>
        <div style="margin-top: 10px; padding-right: 20px; text-align: center;">
            <a href="javascript: void(0)" onclick="saveTabl()" class="easyui-linkbutton" icon="icon-ok">保存</a>
            <a href="javascript: void(0)" onclick="closeWin('difFieldRelaAddWIn')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
        </div>
    </div>
        <div id="difFieldRelaEditWIn" class="easyui-window" title="修改补录" data-options="closed: true">
            <table style="width: 80%;margin-top: 20px;margin-left: 20px;margin-right: 100px;">
                <tr>
                    <td>当前数据接口</td>
                    <td><input id="currTablNmUpd" class="easyui-textbox ruleText"   data-options="required:true"></td>
                    <td>当前字段</td>
                    <td><input id="currFieldNmUpd" class="easyui-textbox ruleText"   data-options="required:true"></td>
                </tr>
                <tr>
                    <td>下级数据接口</td>
                    <td><select class="easyui-combobox ruleText" prompt="请选中下级数据接口" data-options="editable: false, valueField: 'value',required:true,textField: 'text'"
                                id="relaTablIdUpd">
                    </select>
                    </td>
                    <td>下级字段</td>
                    <td><input id="relaFieldIdUpd" class="easyui-combobox ruleText" prompt="请选择下级字段" data-options="valueField:'fieldId',textField:'fieldCnNm'">
                    </td>
                </tr>
            </table>
            <div style="margin-top: 10px; padding-right: 20px; text-align: center;">
                <a href="javascript: void(0)" onclick="saveTabl()" class="easyui-linkbutton" icon="icon-ok">保存</a>
                <a href="javascript: void(0)" onclick="closeWin('difFieldRelaEditWIn')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
            </div>
        </div>
</div>
</body>
</html>
