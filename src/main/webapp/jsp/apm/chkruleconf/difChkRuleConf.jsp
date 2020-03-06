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
    <title>校验规则配置</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/chkruleconf/difChkRuleConf.js"></script>
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
<div id="btnDivConf" style="padding:5px;height:auto">
    <table style="width: 100%;">
        <tr>
            <td>规则名称:</td>
            <td><input id="ruleNm" class="easyui-textbox" style="width:200px" prompt="模糊查询"  ></td>
            <td>规则编号:</td>
            <td><input id="ruleId" class="easyui-textbox"  style="width:200px"></td>
            <td>是否质量统计校验规则:</td>
            <td><select class="easyui-combobox" style="width:200px" data-options="editable: false, valueField: 'value', textField: 'text'"
                        id="isStatsRule">
            </select></td>
        </tr>
        <tr>
            <td>是否质量明细校验规则:</td>
            <td> <select class="easyui-combobox" style="width:200px" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '100px'"
                         id="isDtlRule">
            </select></td>
            <td>规则配置方式:</td>
            <td> <select class="easyui-combobox" style="width:200px" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '100px'"
                         id="ruleCfgTyp">
            </select></td>
            <td>状态:</td>
            <td> <select class="easyui-combobox" style="width:200px" data-options="editable: false, valueField: 'value', textField: 'text', panelHeight: '100px'"
                         id="tableStatus">
            </select></td>
        </tr>
    </table>
    <div>
    </div>
    <div style="margin-bottom:5px;text-align: right">
        <a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="openChkRuleConfWin()">新增规则</a>
        <a href="#" id="ruleConfUpd" class="easyui-linkbutton" onclick="chkRuleConfUpd()" data-options="iconCls:'icon-edit'" >修改规则</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'"  onclick="dopush()">发布规则</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="doNoPush()">撤销发布</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="deleteChkRule()" >删除</a>
        <a href="#" class="easyui-linkbutton" onclick="doChkRuleSearch()" iconCls="icon-search">查询</a>
    </div>
</div>
<%--数据接口ID--%>
<table id="difChkRuleConfList"></table>
<div id="difChkRuleConf" class="easyui-window" title="规则校验配置" data-options="closed: true">
    <form id="difChkRuleForm" method="post">
        <table style="width: 100%;margin-top: 20px;margin-left: 20px;margin-right: 100px;">

            <tr>
                <td>规则编号:</td>
                <td ><input id="ruleIdConf" class="easyui-textbox ruleText" prompt="自定义编号，且必须唯一,长度32字符或16个汉字"  data-options="required:true,validType:'length[1,32]'">
                    <input id="stus" type="hidden">
                    <input id="creatr" type="hidden">
                    <input id="crtDt" type="hidden">
                </td>
                <td>规则名称:</td>
                <td><input id="ruleNmConf" class="easyui-textbox ruleText" prompt="长度限制32字符约16个汉字"  data-options="required:true,validType:'length[1,32]'"></td>
            </tr>
            <tr>
                <td>是否质量统计校验规则:</td>
                <td><select class="easyui-combobox ruleText" prompt="汇总统计" data-options="editable: false, valueField: 'value',required:true, textField: 'text'"
                            id="isStatsRuleConf">
                </select>
                </td>
                <td>是否质量明细校验规则:</td>
                <td><select class="easyui-combobox ruleText" prompt="明细查询" data-options="editable: false, valueField: 'value',required:true,textField: 'text'"
                            id="isDtlRuleConf">
                </select>
                </td>
            </tr>
            <tr>
                <td>校验字段:</td>
                <td colspan="3"><select class="easyui-combobox ruleText"  data-options="editable: false,valueField: 'value',required:true, textField: 'text'"
                            id="fieldEnNmConf">
                </select>
                </td>
            </tr>
            <tr>
                <td>规则配置方式:</td>
                <td>
                    <select class="easyui-combobox ruleText"   data-options="editable: false, valueField: 'value',required:true, textField: 'text'"
                            id="ruleCfgTypConf">
                    </select>
                </td>
                <td class="funway">校验类型:</td>
                <td class="funway">
                    <select class="easyui-combobox ruleText"  data-options="editable: false, valueField: 'value',required:true, textField: 'text'"
                            id="chkTypConf">
                    </select>
                </td>
            </tr>
            <tr  class="funway"><td>比较方式:</td>
                <td>
                    <select class="easyui-combobox ruleText"  data-options="editable: false, valueField: 'value',required:true, textField: 'text'"
                            id="cmprManrConf">
                    </select>
                </td>
                <td>比较对象类型:</td>
                <td>
                    <select class="easyui-combobox ruleText"  data-options="editable: false, valueField: 'value',required:true, textField: 'text'"
                            id="cmprTrgtTypConf">
                    </select>
                </td>
            </tr>
            <tr class="fieldEnNmConfCmpr" style="display: none">
                <td>选择被比较字段:</td>
                <td colspan="3">
                    <select class="easyui-combobox ruleText"  data-options="editable: false, valueField: 'value',required:true, textField: 'text'"
                            id="fieldEnNmConfCmpr">
                    </select>
                </td>
            </tr>
            <tr  class="funway">
                <td>被比较内容</td>
                <td colspan="3"><input id="cmprTrgtContentConf" style="width: 87%;height: 80px" prompt="被比较的字段英文名、常量、长度校验常量值、四则运算公式、值域校验对应的字典类型代码或正则表达式" class="easyui-textbox" data-options="multiline:true" value="">
                    <a id="valScopeBtn" style="display: none" href="javascript: void(0)" onclick="openDictryCdValScope()" class="easyui-linkbutton">选择值域</a>
                </td>
            </tr>
            <tr>
                <td>规则描述:</td>
                <td colspan="3"><input id="ruleComntConf" style="width: 86%;height: 80px" prompt="针对本条规则的文字说明,长度100字符约50汉字" class="easyui-textbox" data-options="multiline:true,validType:'length[1,100]'" value="">
            </tr>
            <tr class="sqlway">
                <td>校验规则SQL表达式:</td>
                <td colspan="3"><input id="chkRuleSql" style="width: 87%;height: 80px" class="easyui-textbox" prompt="校验规则的SQL语句段，如t1.amt>t2.amt" data-options="multiline:true,required:true" value="">
            </tr>
        </table>
    </form>
    <div style="position: absolute;top: 0px;right:5px;">
        <span>所属配置：</span>
        <input  id="currChkConfNm" class="easyui-textbox" style="width:200px" readonly="readonly" value="">
        <a href="javascript: void(0)" onclick="openTablDetail()" class="easyui-linkbutton" icon="icon-search">接口详细信息</a>
        <a href="javascript: void(0)" id="chkRulSave" onclick="saveChkRule()" class="easyui-linkbutton" icon="icon-ok">新增规则</a>
            <a href="javascript: void(0)" id="chkRulUpd" onclick="updChkRule()" class="easyui-linkbutton"  icon="icon-ok">修改规则</a>
        <a href="javascript: void(0)" onclick="closeWin('difChkRuleConf')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<div id="dictryCdValScope" class="easyui-window" title="规则校验配置" data-options="closed: true">
    <div id="ValScopeDiv" style="padding:5px;height:auto">
        <div>
            字典代码名称: <input id="dictryNm" class="easyui-textbox"  style="width:150px">
            所属字典代码: <input id="pareDictryId" class="easyui-textbox"  style="width:150px">
        </div>
        <div style="margin-bottom:5px;text-align: right">
            <a href="#" class="easyui-linkbutton" onclick="doSearchValScope()" iconCls="icon-search">查询</a>
        </div>
    </div>
    <table id="dictryCdValScopeList"></table>
    <div style="position: absolute;top: 0px;right:5px;">
        <a href="javascript: void(0)" onclick="cofirmValScope('dictryCdValScope')" class="easyui-linkbutton" icon="icon-ok">确定</a>
        <a href="javascript: void(0)" onclick="closeWin('dictryCdValScope')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
    </div>
</div>
<div id="table-field-data-grid-win" class="easyui-window" title="数据接口字段信息"
     data-options="closed: true, width: 1000, height: 500, collapsible: false, maximizable: false, minimizable: false, resizable: false,iconCls:'icon-tip'"
     style="display: none;">
    <table id="table-field-data-grid" ></table>
</div>
<div id="dicTablDatil" class="easyui-window" title="接口详细信息" data-options="closed: true">
        <div class="query-wrap">
            <div class="query-item">
                <span>接口中文名称：</span>
                <input class="easyui-textbox" style="width:200px" id="table-interface-query-cn-name">
            </div>
            <div class="query-item">
                <span>接口英文名称：</span>
                <input class="easyui-textbox" style="width:200px" id="table-interface-query-en-name">
            </div>
            <div class="query-item">
                <a id="table-interface-query-btn" href="javascript:void(0)" class="easyui-linkbutton" onclick="queryTablDetail()"
                   data-options="iconCls:'icon-search'">查询</a>
            </div>
        </div>
        <table id="table-interface-data-grid" ></table>
</div>
</body>
</html>
