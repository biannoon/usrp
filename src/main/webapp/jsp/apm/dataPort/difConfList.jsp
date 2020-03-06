<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title>映射接口配置列表</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/demo/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/common/custom_win.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/difconf.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ajax.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/dataPort/difConf.js"></script>
    <script type="text/javascript">
        var baseUrl = '<%=path%>';
        var basePath = '<%= basePath%>';
    </script>
</head>
<body class="easyui-layout">
    <div id="btnDiv" style="padding:5px;height:auto">
    <div>
        来源接口中文名: <input id="cnNm" class="easyui-textbox"  style="width:200px">
        来源接口英文名: <input id="enNm" class="easyui-textbox"  style="width:200px">
    </div>
        <div style="position: absolute;top: 40px;text-align: left;">
            <a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-back'" style="text-align: left" onclick="backTablQuery()">返回</a>
        </div>
        <div style="margin-bottom:5px;text-align: right">
            <a href="#" id="confAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="openAddWin()">新增来源接口</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="openRelaAddWin()">配置关联条件</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="openQuryAddWin()">配置筛选条件</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="openFieldAddWin()">配置字段映射</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="deleteDifConf()">删除</a>
            <a href="#" class="easyui-linkbutton" onclick="doSearch()" iconCls="icon-search">查询</a>
        </div>
    </div>
    <%--映射ID--%>
    <input id="paramMapgId" type="hidden" value="${mapgId}">
    <%--目标表英文名--%>
    <input id="trgtEnNm" type="hidden" value="${trgtEnNm}">
    <%--所属行业分类--%>
    <input id="bizClsf" type="hidden" value="${bizClsf}">
    <%--目标表ID--%>
    <input id="paramTrgtTablId" type="hidden" value="${trgtTablId}">
    <input id="mapgTyp" type="hidden" value="${mapgTyp}">
    <table id="difConf"></table>
    <%--新增来源数据接口--%>
    <div id="difTablSorcAdd" class="easyui-window"  title="新增来源接口" data-options="closed: true, iconCls:'icon-add'">
        <form id="difTablSorcForm" method="post">
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">目标数据接口：</div>
                    <input id="trgtTablId" disabled="disabled" class="easyui-textbox" style="width:200px">
                </div>
            </div>
            <div id="sorcTablsDiv" style="margin-top: 5px; height: 60px;text-align: center;display: none;">
                <div>
                    <div class="edit-win-div-row-column-label">已选来源数据接口：</div>
                    <textarea name="tableComment" class="easyui-textbox" id="sorcTabls" disabled="disabled"
                              style="width:503px;height:60px; vertical-align: middle; resize: none;"></textarea>
                </div>
            </div>
            <div id="relaTypdDiv" class="edit-win-div-row" style="display: none">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">关联类型：</div>
                    <input id="relaTypCd" class="easyui-combobox" style="width:200px"
                           url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI09"
                           valueField="dictryId" panelHeight="120px" textField="dictryNm">
                </div>
            </div>
            <div  class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div class="edit-win-div-row-column-label">来源数据接口：</div>
                    <input id="sorcTabl" class="easyui-combobox" style="width:200px">
                </div>
            </div>
        </form>
        <div style="margin-top: 10px; padding-right: 20px; text-align: center;">
            <a href="javascript: void(0)" onclick="saveSorcTabl()" class="easyui-linkbutton" icon="icon-ok">保存</a>
            <a href="javascript: void(0)" onclick="closeWin('difTablSorcAdd')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
        </div>
    </div>
    <%--配置关联条件--%>
    <div id="difRelaCondConf" class="easyui-window" title="关联条件配置" data-options="closed: true">
        <form id="difRelaCondForm" method="post">
            <div style="position: absolute;top: 0px;left:95px;">
                <a >目标数据接口:</a>
                <input id="trgtTablIdRela" disabled="disabled" class="easyui-textbox" style="width:150px">
                <a >已配置来源数据接口:</a>
                <input id="sorcTablsRela" disabled="disabled" class="easyui-textbox" style="width:200px">
                <a >待配置来源数据接口:</a>
                <input id="sorcTablRela" disabled="disabled" class="easyui-textbox" style="width:150px">
                <a id="relaConfBtn" href="javascript: void(0)" onclick="saveRelaCond()" class="easyui-linkbutton" icon="icon-ok"></a>
                <a href="javascript: void(0)" onclick="deleteRelaConf()" class="easyui-linkbutton" icon="icon-remove"></a>
                <a href="javascript: void(0)" onclick="closeWin('difRelaCondConf')" class="easyui-linkbutton" icon="icon-cancel"></a>
            </div>
                <div id="relComb0"  class="wrapper" style="top:20px;" ><span class="label_root"><a href="javascript: void(0)" onclick="showRelaMenu('relaMenu',event,this)" class="easyui-linkbutton" icon="icon-ok">AND</a></span>
                    <div class="branch root">

                    </div>
                </div>
        </form>
    </div>
    <div id="relaMenu" class="easyui-menu" style="width:90px;">
        <div data-options="iconCls:'icon-information'" id="and">AND</div>
        <div id="or">OR</div>
        <div class="menu-sep"></div>
        <div id="exp">表达式</div>
        <div id="combo">组合条件</div>
        <div class="menu-sep"></div>
        <div><a href="#" id="menuRemove"  class="easyui-linkbutton" style="position: absolute;left: 30px"  data-options="iconCls:'icon-remove'">删除</a></div>
    </div>
    <%--配置筛选条件--%>
    <div id="difQuryCondConf" class="easyui-window" title="筛选条件配置" data-options="closed: true, iconCls:'icon-add'">
        <form id="difQuryCondForm" method="post">
            <div style="position: absolute;top: 0px;left:150px;">
                <a >目标数据接口:</a>
                <input id="trgtTablIdQury" disabled="disabled" class="easyui-textbox" style="width:150px">
                <a >待配置来源数据接口:</a>
                <input id="sorcTablQury" disabled="disabled" class="easyui-textbox" style="width:150px">
                <a id="quryConfBtn" href="javascript: void(0)" onclick="saveQuryCond()" class="easyui-linkbutton" icon="icon-ok"></a>
                <a href="javascript: void(0)" onclick="deleteQuryConf()" class="easyui-linkbutton" icon="icon-remove">删除</a>
                <a href="javascript: void(0)" onclick="closeWin('difQuryCondConf')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
            </div>
                <div id="quryComb0"  class="wrapper" style="top:20px;" ><span class="label_root"><a href="javascript: void(0)" onclick="showQuryMenu('relaMenu',event,this)" class="easyui-linkbutton" icon="icon-ok">AND</a></span>
                    <div class="branch rootQury">

                    </div>
                </div>
        </form>
    </div>
    <div id="difFieldMapgConf" class="easyui-window" title="字段映射" data-options="closed: true, iconCls:'icon-add'" style="overflow: auto">
        <div style="position: absolute;top: 0px;left:95px;">
            <a >目标数据接口:</a>
            <input id="trgtTablIdField" disabled="disabled" class="easyui-textbox" style="width:150px">
            <a >配置来源数据接口:</a>
            <input id="sorcTablField" disabled="disabled" class="easyui-textbox" style="width:150px">
            <a href="javascript: void(0)" id="fieldConfBtn"  class="easyui-linkbutton" onclick="saveFieldMapgConf()" icon="icon-save"></a>
            <a href="javascript: void(0)" onclick="deleteFieldMapgConf()" class="easyui-linkbutton" icon="icon-remove">删除</a>
            <a href="javascript: void(0)" onclick="closeWin('difFieldMapgConf')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
        </div>
            <div id="fieldComb0"  class="wrapper" style="top:20px;" ><span class="label_root"><a href="javascript: void(0)" onclick="addFieldExp()" class="easyui-linkbutton" icon="icon-ok">添加映射</a></span>
                <div class="branch rootField">

                </div>
            </div>

    </div>

</body>
</html>
