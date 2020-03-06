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
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/js/vis/vis.min.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ajax.js"></script>
    <script src="<%=request.getContextPath()%>/static/js/combobox.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/bloodrela/bloodRelaTabl.js"></script>
    <script src="<%=path%>/static/js/vis/vis.min.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/bloodrela/relationDiagram.js" type="text/javascript"></script>
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
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="toDifChkRuleConf()">字段血缘分析</a>
                    <a href="#" class="easyui-linkbutton" onclick="doSearch()" iconCls="icon-search">查询</a>
                </div>
            </div>
            <table id="bloodRelaTabl"></table>
        </div>
    <div id="difFieldRela" class="easyui-window" title="字段血缘关系" data-options="closed: true, iconCls:'icon-add'">

    </div>
    <div id="table-relation-diagram-win" class="easyui-window" title="接口血缘关系图" style="display: none;"
         data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false" >
        <div id="table-relation-diagram" style="width: 100%; height: 100%"></div>
    </div>
    <div id="field-relation-diagram-win" class="easyui-window" title="接口字段血缘关系图" style="display: none;"
         data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false" >
        <div id="field-relation-diagram" style="width: 100%; height: 100%"></div>
    </div>
</div>
</body>
</html>
