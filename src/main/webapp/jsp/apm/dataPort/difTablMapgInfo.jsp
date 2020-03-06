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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/dataPort/fieldMapgConf.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/dataPort/difTablMapgInfo.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/dataPort/uuid.js"></script>


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
<div id="difTablMapg" class="easyui-layout" style="width:98%;height:650px;margin-left: 1%;margin-right: 1%">
                <div id="scrTabl" class="easyui-layout" style="width:100%;height:100%;">
                    <jsp:include page="mapgTypTree.jsp"></jsp:include>
                    <div  data-options="region:'center',title:'数据接口配置'" style="padding:5px;">
                        <div id="btnDiv" style="padding:5px;height:auto">
                            <div>
                                接口中文名: <input id="cnNm" class="easyui-textbox"  style="width:200px;">
                                接口英文名: <input id="enNm" class="easyui-textbox"  style="width:200px">
                                映射配置状态:
                                <input id="stus" class="easyui-combobox" style="width:100px;padding-top: 20px;"
                                       url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI02"
                                       valueField="dictryId" textField="dictryNm">
                                映射类型:
                                <input id="searchmapgTyp" class="easyui-combobox" style="width:100px;display: none"
                                       url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI07"
                                       valueField="dictryId" textField="dictryNm">
                            </div>
                            <div style="margin-bottom:5px;text-align: right;padding-top: 20px;">
                                <a href="#" id="confAdd" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="openAddWin()">新增</a>
                                <a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="openQueryEditWin()">查询修改</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="openGrpgCondWin()">配置分组汇总条件</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="toDifConf()">配置映射</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" onclick="doChkSql()">发布</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="unPublish()">撤销发布</a>
                                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="deleteConf()">删除</a>
                                <a href="#" class="easyui-linkbutton" onclick="doSearch()" iconCls="icon-search">查询</a>
                            </div>
                        </div>
                        <table id="difTablMapgList"></table>
                        <input id="bizClsf" type="hidden" value="">
                        <div id="difTablMapgAdd" class="easyui-window" title="新增目标接口" data-options="closed: true, iconCls:'icon-add'">
                            <table style="width: 100%">
                                <tr>
                                    <td>目标数据接口:</td>
                                    <td colspan="2"><input id="trgtTablText" prompt="点击目标接口选择按钮选择目标接口，不可输入" class="easyui-textbox" data-options="required:true,readonly:true" style="width:300px;">
                                        <input id="trgtTablId" type="hidden">
                                    </td>
                                    <td style="width: 100px;"><a href="javascript: void(0)" onclick="openTrgtWin()" class="easyui-linkbutton" icon="icon-ok">选择目标接口</a></td>
                                </tr>
                                <tr>
                                <td>映射处理方式:</td>
                                <td><input id="mapgDlwthManr" class="easyui-combobox" style="width:200px" data-options="required:true"
                                       url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI08"
                                       valueField="dictryId" textField="dictryNm">
                                </td>
                                <td>映射类型:</td>
                                <td><input id="mapgTyp" class="easyui-combobox" style="width:200px" data-options="required:true"
                                           url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI07"
                                           valueField="dictryId" textField="dictryNm">
                                </td>
                                </tr>
                                <tr>
                                    <td>映射说明</td>
                                    <td colspan="3"><input id="mapgComnt" prompt="映射配置的文字描述" style="width: 90%;height: 80px" class="easyui-textbox" data-options="multiline:true,required:true"></td>
                                </tr>
                            </table>
                            <div style="margin-top: 10px; padding-right: 20px; text-align: center;">
                                <a href="javascript: void(0)" onclick="saveDifTablMapg()" class="easyui-linkbutton" icon="icon-ok">保存</a>
                                <a href="javascript: void(0)" onclick="closeWindow('difTablMapgAdd')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
                            </div>
                        </div>
                        <div id="difTablMapgQury" class="easyui-window" title="目标数据接口修改" data-options="closed: true, iconCls:'icon-edit'">
                            <table style="width: 100%">
                                <tr>
                                    <td>接口英文名:</td>
                                    <td><input id="enNmqury" class="easyui-textbox" data-options="required:true" style="width:200px;">
                                    </td>
                                    <td>中文名:</td>
                                    <td><input id="cnNmqury" class="easyui-textbox" data-options="required:true" style="width:200px;">
                                    </td>
                                </tr>
                                <tr>
                                <td>映射处理方式:</td>
                                <td><input id="mapgDlwthManrQury" class="easyui-combobox" style="width:200px" data-options="required:true"
                                       url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI08"
                                       valueField="dictryId" textField="dictryNm">
                                </td>
                                <td>映射类型:</td>
                                <td><input id="mapgTypQury" class="easyui-combobox" style="width:200px" data-options="required:true"
                                           url="/usrp/difConf/getDifConfDictryCd?blngtoDictryId=DFI07"
                                           valueField="dictryId" textField="dictryNm">
                                </td>
                                </tr>
                                <tr>
                                    <td>映射说明</td>
                                    <td colspan="3"><input id="mapgComntQury" style="width: 90%;height: 80px" class="easyui-textbox" data-options="multiline:true,required:true"></td>
                                </tr>
                                <tr class="rpgCondQury" style="visibility:hidden">
                                    <td>分组汇总条件:</td>
                                    <td colspan="3">
                                        <input id="rpgCondQury" style="width:90%;height:80px" class="easyui-textbox" data-options="multiline:true">
                                    </td>
                                </tr>
                            </table>
                            <div style="margin-top: 10px; padding-right: 20px; text-align: center;">
                                <a href="javascript: void(0)" onclick="updDifTablMapg()" class="easyui-linkbutton" icon="icon-ok">保存</a>
                                <a href="javascript: void(0)" onclick="closeWindow('difTablMapgQury')" class="easyui-linkbutton" icon="icon-cancel">关闭</a>
                            </div>
                        </div>
                        <div id="chkSqlWin" class="easyui-window" title="接口配置校验" data-options="closed: true">
                            <table style="width: 100%;height: 100%">
                                <tr>
                                    <td>目标接口</td>
                                    <td><input id="chkSqlTablTrgt" class="easyui-textbox" style="width: 87%;height: 30px"></td>
                                </tr>
                                <tr>
                                    <td>来源接口</td>
                                    <td colspan="3"><input id="sorcTablStrs" style="width: 87%;height: 30px" class="easyui-textbox"  value="">
                                </tr>
                                <tr>
                                    <td>字段映射配置</td>
                                    <td colspan="3"><input id="fieldCondStrs" style="width: 87%;height: 50px" class="easyui-textbox" data-options="multiline:true" value="">
                                </tr>
                                <tr>
                                    <td>关联条件配置</td>
                                    <td colspan="3"><input id="relaCondStrs" style="width: 87%;height: 50px" class="easyui-textbox" data-options="multiline:true" value="">
                                </tr>
                                <tr>
                                    <td>筛选条件配置</td>
                                    <td colspan="3"><input id="quryCondStrs" style="width: 87%;height: 50px" class="easyui-textbox" data-options="multiline:true" value="">
                                </tr>
                                <tr>
                                    <td>配置SQL</td>
                                    <td colspan="3"><input id="chkSql" style="width: 87%;height: 120px" class="easyui-textbox" data-options="multiline:true" value="">
                                     <a   href="javascript: void(0)" onclick="chkSql()" class="easyui-linkbutton">测试SQL</a>
                                </tr>
                            </table>
                            <div id="pushBtnDiv" style="position: absolute;top: 0px;right:5px;">
                                <a href="javascript: void(0)" onclick="doPublish()" class="easyui-linkbutton" icon="icon-ok">发布</a>
                                <a href="javascript: void(0)" onclick="closeWin('chkSqlWin')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
                            </div>
                        </div>
                        <div id="targtWin" class="easyui-window" title="选择目标数据接口" data-options="closed: true">
                                <div id="trgtSelectDiv" style="padding:5px;height:auto">
                                    <div>
                                        接口中文名: <input id="cnName" class="easyui-textbox"  style="width:200px;">
                                        接口英文名: <input id="enName" class="easyui-textbox"  style="width:200px">
                                    </div>
                                    <div style="margin-bottom:5px;text-align: right;padding-top: 20px;">
                                        <a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="confirmSelect()">确定</a>
                                        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo'" onclick="closeWindow('targtWin')">取消</a>
                                        <a href="#" class="easyui-linkbutton" onclick="doSearchTargt()" iconCls="icon-search">查询</a>
                                    </div>
                                </div>
                                <table id="trgtablList"></table>
                        </div>
                        <div id="grpgCondWin" class="easyui-window" title="配置分组汇总条件" data-options="closed: true">
                            <div style="position: absolute;top: 0px;left:150px;">
                                <a >目标数据接口:</a>
                                <input id="trgtTablIdQury" disabled="disabled" class="easyui-textbox" style="width:300px" data-options="readonly:true">
                                <a >来源接口:</a>
                                <input id="difSorcGrpg"  class="easyui-combobox">
                                <a href="javascript: void(0)" onclick="saveRpgCond()" class="easyui-linkbutton" icon="icon-ok">保存</a>
                                <a href="javascript: void(0)" onclick="closeWin('grpgCondWin')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
                            </div>
                            <div style="width: 20%;height:100%;float: left;">
                                <a >分组汇总条件:</a>
                                <input id="rpgCond" style="width:100%;height:100%" class="easyui-textbox" data-options="multiline:true,readonly:true">
                            </div>
                            <div style="width: 80%;height:100%;float: right;">
                                <div id="fieldSelectDiv" style="padding:5px;height:auto">
                                    <div>
                                        字段中文名: <input id="fieldCnNm" class="easyui-textbox"  style="width:200px;">
                                        字段英文名: <input id="fieldEnNm" class="easyui-textbox"  style="width:200px">
                                    </div>
                                    <div style="margin-bottom:5px;text-align: right;padding-top: 20px;">
                                        <a href="#" class="easyui-linkbutton" onclick="doSearchField()" iconCls="icon-search">查询</a>
                                    </div>
                                </div>
                                <table id="trgtablFielList"></table>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
</body>
</html>
