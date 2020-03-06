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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/chkruleconf/difChkRuleTabl.js"></script>
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
                    <jsp:include page="/jsp/apm/dataPort/mapgTypTree.jsp"></jsp:include>
                    <div  data-options="region:'center',title:'数据接口校验配置'" style="padding:5px;">
                        <div id="btnDiv" style="padding:5px;height:auto">
                            <table style="width: 100%;">
                                <tr>
                                    <td>接口中文名:</td>
                                    <td><input id="cnNm" class="easyui-textbox ruleText" prompt="模糊查询"  style="width:200px;"></td>
                                    <td>接口英文名:</td>
                                    <td><input id="enNm" class="easyui-textbox"  style="width:200px"></td>
                                    <td>配置名称:</td>
                                    <td><input id="chkConfNmQry" prompt="模糊查询" class="easyui-textbox"  style="width:200px"></td>
                                </tr>
                                <tr>
                                    <td>校验模式:</td>
                                    <td><select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                                                id="chkModlQry">
                                    </select></td>
                                    <td>所属数据源:</td>
                                    <td> <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '100px'"
                                                 id="dataSourceTabl">
                                    </select></td>
                                </tr>
                            </table>
                            <div style="text-align: right;">
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'"  onclick="openTablChkConf()">新增校验配置</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="openTablChkConfUpd()">修改校验配置</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  onclick="delTablChkConf()">删除校验配置</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="openChkRuleWin()">校验规则配置</a>
                                <a href="#" class="easyui-linkbutton" onclick="doChkConfSearch()" iconCls="icon-search">查询</a>
                            </div>
                        </div>
                        <table id="tablChkConf"></table>
                        <input id="bizClsf" type="hidden" value="">
                    </div>
                </div>
                <%--校验配置新增--%>
                <div id="tablChkConfWin" class="easyui-window" title="数据接口校验配置" data-options="closed: true">
                    <table style="width: 100%;margin-top: 20px;">
                        <tr>
                            <td>数据接口:</td>
                            <td><input id="tablNm" class="easyui-textbox ruleText"  prompt="点击后边的按钮选择数据接口，不可编辑" data-options="required:true,readonly:true">
                                <a href="javascript: void(0)" style="height: 28px;width: 150px;" onclick="openTablWin()" class="easyui-linkbutton" icon="icon-ok" >选择数据接口</a>
                                <input type="hidden" id="tablId">
                            </td>
                        </tr>
                        <tr>
                            <td>配置名称:</td>
                            <td><input id="chkConfNm" class="easyui-textbox ruleText" prompt="配置名称，最大输入长度32个字符长度，不超过16个汉字"  data-options="required:true,validType:'length[1,32]'"></td>
                            <td>校验模式:</td>
                            <td><select class="easyui-combobox ruleText" prompt="请选择校验模式" data-options="editable: false, valueField: 'value',required:true,textField: 'text'"
                                        id="chkModl">
                            </select>
                                <input type="hidden" id="chkModlTmp">
                            </td>
                        </tr>
                        <tr>
                            <td>表间关联SQL表达式:</td>
                            <td colspan="3"><input id="relSqlExps" style="width: 98%;height: 80px" class="easyui-textbox" prompt="
                            当校验模式选择为表间校验时此项必填，否则不能填写
                            记录校验数据主体的SQL语句段，如 from TAB1 t1 JOIN TAB2 t2 ON t1.id=t2.id" data-options="multiline:true,required:true">
                            </td>
                        </tr>
                        <tr>
                            <td>校验数据范围SQL表达式:</td>
                            <td colspan="3"><input id="chkScpSql" style="width: 98%;height: 80px" class="easyui-textbox" prompt="记录明确数据范围的SQL语句段，可为空，如t1.batch_date=$SYSDATE
                            " data-options="multiline:true">
                            </td>
                        </tr>
                        <tr>
                            <td>配置说明:</td>
                            <td colspan="3"><input id="chkConfComnt" style="width: 98%;height: 80px" class="easyui-textbox" prompt="规则的业务描述" data-options="multiline:true">
                            </td>
                        </tr>
                    </table>
                    <div style="margin-top: 10px; padding-right: 20px; text-align: center;">
                        <a id="confAdd" href="javascript: void(0)" onclick="saveTablChkConf()" class="easyui-linkbutton" icon="icon-ok">保存</a>
                        <a id="confUpd" href="javascript: void(0)" onclick="updTablChkConf()" class="easyui-linkbutton" icon="icon-ok">保存</a>
                        <a href="javascript: void(0)" onclick="closeWin('tablChkConfWin')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
                    </div>
                </div>
                <div id="tablWin" class="easyui-window" title="数据接口列表" data-options="closed: true">
                    <div id="tablDiv" style="padding:5px;height:auto">
                        <table style="width: 80%;margin-left: 20px;margin-right: 100px;">
                            <tr>
                                <td>接口中文名:</td>
                                <td><input id="cnName" class="easyui-textbox"  style="width:200px;"></td>
                                <td>接口英文名:</td>
                                <td><input id="enName" class="easyui-textbox"  style="width:200px"></td>
                            </tr>
                            <tr>
                                <td>所属数据源:</td>
                                <td> <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '100px'"
                                             id="dataSource">
                                </select></td>
                            </tr>

                        </table>
                        <div style="text-align: right;">
                            <a href="#" class="easyui-linkbutton" onclick="confirmTabl()" iconCls="icon-ok">确定</a>
                            <a href="#" class="easyui-linkbutton" onclick="doSearchTabl()" iconCls="icon-search">查询</a>
                        </div>
                    </div>
                    <table id="difTablList"></table>
                </div>
                <div id="difChkRuleWin" class="easyui-window" title="校验规则配置" data-options="closed: true">
                    <jsp:include page="/jsp/apm/chkruleconf/difChkRuleConf.jsp"></jsp:include>
                </div>
        </div>
</body>
</html>
