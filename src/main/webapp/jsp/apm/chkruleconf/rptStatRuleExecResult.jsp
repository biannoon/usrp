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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/chkruleconf/rptStatRuleExecResult.js"></script>
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
                <div  class="easyui-layout" style="width:100%;height:100%;">
                    <div  data-options="region:'center',title:'数据接口校验配置'" style="padding:5px;">
                        <div id="btnDiv" style="padding:5px;height:auto">
                            <table style="width: 100%;">
                                <tr>
                                    <td>规则编号:</td>
                                    <td><input id="ruleId" class="easyui-textbox ruleText"   style="width:200px;"></td>
                                    <td>批量日期:</td>
                                    <td><input id="batchDt" class="easyui-datebox" prompt="日期格式：YYYY-MM-DD" data-options="formatter:myformatter,parser:myparser"  style="width:200px"></td>
                                    <td>校验规则执行方式:</td>
                                    <td><input id="execTyp"  class="easyui-combobox"  style="width:200px"></td>
                                </tr>
                                <tr>
                                    <td>数据源:</td>
                                    <td> <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '100px'"
                                                 id="dataSorcId">
                                    </select></td>
                                    <td>数据接口名称:</td>
                                    <td><input id="cnNm" class="easyui-textbox ruleText" prompt="模糊查询"  style="width:200px;"></td>
                                    <td>规则执行日期:</td>
                                    <td><input id="ruleExecDt" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"   style="width:200px;"></td>
                                </tr>
                            </table>
                            <div style="text-align: right;">
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'"  onclick="downLoadExcel()">下载</a>
                                <a href="#" class="easyui-linkbutton" onclick="doRuleResultSearch()" iconCls="icon-search">查询</a>
                            </div>
                        </div>
                        <table id="ruleResultList"></table>
                        <input id="bizClsf" type="hidden" value="">
                    </div>
                </div>
                <div id="difChkRuleWin" class="easyui-window" title="校验规则配置" data-options="closed: true">
                </div>
        </div>
</body>
</html>
