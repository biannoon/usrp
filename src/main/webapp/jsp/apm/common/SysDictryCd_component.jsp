<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/18
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp" %>
<%
    String checkBox = ((String) request.getAttribute("checkBox")).equals("0")?"true":"false";
    String dictryId = (String) request.getAttribute("dictryId");
    String winId = (String) request.getAttribute("winId");
    String fieldId = (String) request.getAttribute("fieldId");
    String isLevel = (String) request.getAttribute("isLevel");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
    <%if ("0".equals(isLevel)){%>
    <div id="tb" style="padding:3px">
        <table width="100%" style="text-align: center">
            <tr>
                <td align="right"><span style="font-size: small">字典代码名称：</span></td>
                <td align="left"><input class="easyui-textbox" id="dictryNm_search" style="height:20px;width:200px;border:1px solid #ccc" ></td>
                <td  align="right">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain=true onclick="doComponentSearch('dg_sys_dictry_component')">查询</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="assignVal_dictry('dg_sys_dictry_component','0','<%=fieldId%>','<%=winId%>')">确定</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeComponentWindow('<%=winId%>')">关闭</a>
                </td>
            </tr>
        </table>
    </div>
    <table id="dg_sys_dictry_component" width="100%"></table>
    <%}else if("1".equals(isLevel)){%>
    <table>
        <tr>
            <td valign="top" align="left">
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="assignVal_dictry('tree_sys_dictry_component','1','<%=winId%>','<%=fieldId%>')">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeComponentWindow('<%=winId%>')">关闭</a>
            </td>
        </tr>
        <tr>
            <td align="left" width="100%">
                <div>
                    <ul id="tree_sys_dictry_component"></ul>
                </div>
            </td>
        </tr>
    </table>
    <%}%>
<script type="text/javascript">
    //初始化页面
    $(function () {
        //获取文本框的值
        var fieldId = '<%=fieldId%>';
        var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
        var echoValue = $('#'+_fieldId).val();
        <%if ("0".equals(isLevel)){%>
        initSysDictryInfoDatagridByComponent('dg_sys_dictry_component','<%=checkBox%>',echoValue,'<%=dictryId%>');
        <%}else if("1".equals(isLevel)){%>
        initSysDictryInfoTreeByComponent('tree_sys_dictry_component','<%=checkBox%>',echoValue,'<%=dictryId%>');
        <%}%>
    })

    /**
     * 描述：初始化字典代码共用组件的datagrid
     * @param id            datagrid ID
     * @param checkbox      是否单选
     * @param echoValue     回显值
     * @param dicId         字典代码
     */
    function initSysDictryInfoDatagridByComponent(id,checkbox,echoValue,dicId){
        $('#'+id).datagrid({
            idField:'dictryId',
            url: basePath + 'common/getSysDictryCdListByDatagrid?dictryId='+dicId,
            fitColumns: true,
            singleSelect: checkbox,
            pagination: true,
            pageNumber: '1',
            pageSize: '10',
            pagePosition: 'top',
            toolbar: '#tb',
            columns:[[
                {field:'cb',title:'',width:100,checkbox:true},
                {field:'dictryId',title:'字典代码ID',width:100,align:'center'},
                {field:'dictryNm',title:'字典代码名称',width:100,align:'center'},
                {field:'cdTyp',title:'字典代码类型',width:100,align:'center'},
                {field:'dictryComnt',title:'字典代码说明',width:100,align:'center'}
            ]],
            onLoadSuccess: function (data) {
                $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
                //回显勾选指定的值（目前用于单选）
                var rows = $(this).datagrid('getRows');
                $.each(rows, function (index, row) {
                    if (echoValue == row.dictryId) {
                        $('#dg_sys_dictry_component').datagrid('checkRow', index);
                    }
                })
            }
        });
    }

    /**
     * 描述：初始化字典代码树tree
     * @param id                tree ID
     * @param dicId             字典代码类型
     * @param onlyLeafCheck     只能选中叶子节点
     * @param echoValue         回显数据
     * @param checkbox          单选/多选
     */
    function initSysDictryInfoTreeByComponent(id,checkbox,onlyLeafCheck,echoValue,dicId) {
        $('#'+id).tree({
            url:basePath + 'common/getSysDictryCdListByTree?dictryId='+dicId,
            checkbox:true,
            cascadeCheck: false,//取消（点击父级菜单，同时选中子级菜单）
            onlyLeafCheck:onlyLeafCheck,
            //加载后端返回的json字符串
            loadFilter: function(data){
                return data;
            },
            onSelect:function (node) {
                if(checkbox){
                    var cknodes = $('#tree_sysDictryCd').tree("getChecked");
                    for (var i = 0; i < cknodes.length; i++) {
                        if (cknodes[i].id != node.id) {
                            $('#tree_sysDictryCd').tree("uncheck", cknodes[i].target);
                        }
                    }
                    if (node.checked) {
                        $('#tree_sysDictryCd').tree('uncheck', node.target);
                    } else {
                        $('#tree_sysDictryCd').tree('check', node.target);
                    }
                }
            },
            onLoadSuccess: function (node, data) {
                if(checkbox){
                    $(this).find('span.tree-checkbox').unbind().click(function () {
                        $('#tree_sysDictryCd').tree('select', $(this).parent());
                        return false;
                    });
                };
                //回显勾选指定的值（目前用于单选）
                if (echoValue != '' && echoValue != null){
                    var node = $(this).tree('find', echoValue);
                    $(this).tree('expandAll');
                    $(this).tree('check',node.target);
                }
            }
        })
    }

    /**
     * 描述：子窗口给父页面赋值
     * @param id
     * @param isLevel
     * @param fieldId
     * @param winId
     */
    function assignVal_dictry(id,isLevel,fieldId,winId){
        if (isLevel == '0'){
            var rows = $('#'+id).datagrid('getChecked');
            if (rows == null){
                $.messager.alert("提示","请选择记录");
            }else{
                var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
                var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
                $.each(rows,function(index,row) {//存在一个缺陷，只能用于单选
                    if (fieldId_ != '' && fieldId_ != null){
                        $('#' + fieldId_).textbox('setValue', row.dictryNm);
                    }
                    if (_fieldId != '' && _fieldId != null){
                        $('#' + _fieldId).textbox('setValue', row.dictryId);
                    }
                })
                $('#'+winId).window('close');
            }
        }else if(isLevel == '1'){
            var nodes = $('#'+id).tree('getChecked');//获取选中节点
            if (nodes == null){
                $.messager.alert("提示","请选择字典记录");
            }else{
                var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
                var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
                $.each(nodes,function (index,node) {
                    var data = $('#'+id).tree('getData',node.target);
                    if (fieldId_ != '' && fieldId_ != null){
                        $('#'+fieldId_).textbox('setValue',data.text);
                    }
                    if (_fieldId != '' && _fieldId != null){
                        $('#'+_fieldId).textbox('setValue',data.id);
                    }
                })
                $('#'+winId).window('close');
            }
        }
    }

    /**
     * 描述：字典查询
     * @param id 操作对象ID
     */
    function doComponentSearch(id) {
        $('#'+id).datagrid('load',{
            dictryNm: $('#dictryNm_search').val()
        })
    }

    function closeComponentWindow(winId){
        $('#'+winId).window('close');
    }

</script>
</body>
</html>
