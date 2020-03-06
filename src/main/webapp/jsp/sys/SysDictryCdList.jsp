<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysDictryCd.js"></script>
</head>
<body class="easyui-layout" style="padding:5px;background:#eee;">
<div data-options="region:'center',title:'字典类型代码列表',split:true,border:false" style="width:60%;">
    <div id="dg_dictry_tool" style="padding:3px">
        <table width="100%" style="text-align: center">
            <tr>
                <td align="right"><span style="font-size: 12px">字典类型代码ID：</span></td>
                <td align="left"><input id="dictryId_se"></td>
                <td align="right"><span style="font-size: 12px">字典类型名称：</span></td>
                <td align="left"><input id="dictryNm_se"></td>
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true
                       onclick="searchDicType()" >查询</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  plain=true
                       onclick="resetDicType()" >重置</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true
                       onclick="toSysDictryInsertPage('win_sys_dictry','字典代码新增','dictionary/toSysDictryCdInsert','800','400')">新增</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true
                       onclick="toSysDictryUpdatePage('win_sys_dictry','字典代码修改','dictionary/toSysDictryCdUpdate','800','400')">修改</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true
                       onclick="toDeleteSysDictry('dictionary/deleteSysDictryCd')">删除</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain=true
                       onclick="toSysDictryCdShowDraw('win_sys_dictry','字典引用列表','dictionary/toSysDictryCdShowDraw','700','400')">查看引用</a>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <table id="dg_dictry" width="100%"></table>
    </div>
    <input class="easyui-textbox" id="dictryId_hid" type="hidden">
</div>
<div data-options="region:'east',title:'代码值列表',split:true,border:true" style="width:40%;">
    <div id="dg_dictry_value_tool" style="padding:3px">
        <table width="100%" style="text-align: center">
            <tr>
                <td align="right"><span style="font-size: 12px">代码值ID：</span></td>
                <td align="left"><input id="dictry_id_se"></td>
                <td align="right"><span style="font-size: 12px">代码值名称：</span></td>
                <td align="left"><input id="dictry_nm_se"></td>
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
                       onclick="searchDicValue()" plain=true >查询</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
                       onclick="resetDicValue()" plain=true >重置</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true
                       onclick="toSysDictryValueInsertPage('win_sys_dictry','代码值新增','dictionary/toSysDictryCdInsertValue','350','200','db')">新增</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true
                       onclick="toSysDictryValueUpdatePage('win_sys_dictry','代码值修改','dictionary/toSysDictryCdUpdateValue','350','200','db')">修改</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true
                       onclick="deleteSysDictryValue('db')" >删除</a>
                </td>
            </tr>
        </table>
    </div>
    <div>
        <table id="dg_dictry_value" width="100%"></table>
    </div>
</div>
<!--动态生成模态框-->
<div id="win_sys_dictry"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initSysDictryPage();
    })

    /*function searchdraw(){
        var row = $('#dg').datagrid('getSelected');
        if(row == null){
            $.messager.alert("提示","请选择需要操作的记录！");
        }else{
            var dictryId = row.dictryId;
            var url = '/usrp/dictionary/toSysDictryCdShowDraw?dictryId='+dictryId;
            newWindow('查看引用',url,'600','430');
        }
    }*/
</script>
</body>
</html>
