<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/30
  Time: 21:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String winId = (String)request.getAttribute("winId");
    String fieldId = (String) request.getAttribute("fieldId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background-color: #a4e9c1">
<table width="100%">
    <tr>
        <td valign="top" width="100%" align="left">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="assignVal('<%=winId%>','<%=fieldId%>')">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindow('<%=winId%>')">关闭</a>
        </td>
    </tr>
    <tr>
        <td align="left" width="100%">
            <div><ul id="tree_search_ascGroup"></ul></div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function () {
        //获取回显文本框ID/取值
        var fieldId = '<%=fieldId%>';
        var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
        var value = $('#'+_fieldId).val();
        $('#tree_search_ascGroup').tree({
            url:'<%=basePath%>AscTask/ListAllTaskGroup',
            checkbox:true,
            cascadeCheck: false,//取消（点击父级菜单，同时选中子级菜单）
            //加载后端返回的json字符串
            loadFilter: function(data){
                return data;
            },
            //--tree实现单选框
            onSelect: function (node) {
                var cknodes = $('#tree_search_ascGroup').tree("getChecked");
                for (var i = 0; i < cknodes.length; i++) {
                    if (cknodes[i].id != node.id) {
                        $('#tree_search_ascGroup').tree("uncheck", cknodes[i].target);
                    }
                }
                if (node.checked) {
                    $('#tree_search_ascGroup').tree('uncheck', node.target);
                } else {
                    $('#tree_search_ascGroup').tree('check', node.target);
                }
            },
            onLoadSuccess: function (node, data) {
                $(this).find('span.tree-checkbox').unbind().click(function () {
                    $('#tree_search_ascGroup').tree('select', $(this).parent());
                    return false;
                });
                //回显勾选指定的值（目前用于单选）
                if (value != '' && value != null){
                    var node = $(this).tree('find', value);
                    $(this).tree('expandAll');
                    $(this).tree('check',node.target);
                }
            }
        })
    })

    //子页面给父页面赋值操作
    function assignVal(winId,fieldId){
        var nodes = $('#tree_search_ascGroup').tree('getChecked');
        if (nodes == null || nodes == ''){
            $.messager.alert("提示","请选择记录");
        }else{
            var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
            var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
            $.each(nodes,function(index,node) {//存在一个缺陷，只能用于单选
                var data = $('#tree_search_ascGroup').tree('getData',node.target);
                $('#' + fieldId_).textbox('setValue', data.text);
                $('#' + _fieldId).textbox('setValue', data.id);
            })
            $('#'+winId).window('close');
        }
    }

    function closeWindow(winId) {
        $('#'+winId).window('close');
    }
</script>
</body>
</html>

