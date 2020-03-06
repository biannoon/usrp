<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/17
  Time: 8:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String winId = (String)request.getAttribute("winId");
    String fieldId = (String)request.getAttribute("fieldId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
<table>
    <tr>
        <td valign="top" align="left">
            <a href="javascript:void(0)" class="easyui-linkbutton" plain=true iconCls="icon-add" onclick="assignVal('<%=winId%>','<%=fieldId%>')">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain=true iconCls="icon-cancel" onclick="closeWindow('<%=winId%>')">关闭</a>
        </td>
    </tr>
    <tr>
        <td align="left" width="100%">
            <div>
                <ul id="tree_search_sysFunc"></ul><!--菜单树-->
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function () {
        //获取回显文本框ID/取值
        var fieldId = '<%=fieldId%>';
        var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
        var value = $('#'+fieldId_).val();
        $('#tree_search_sysFunc').tree({
            url:'<%=basePath%>common/getSysFuncTree',
            checkbox:true,
            cascadeCheck: false,//取消（点击父级菜单，同时选中子级菜单）
            //加载后端返回的json字符串
            loadFilter: function(data){
                return data;
            },
            //--tree实现单选框
            onSelect: function (node) {
                var cknodes = $('#tree_search_sysFunc').tree("getChecked");
                for (var i = 0; i < cknodes.length; i++) {
                    if (cknodes[i].id != node.id) {
                        $('#tree_search_sysFunc').tree("uncheck", cknodes[i].target);
                    }
                }
                if (node.checked) {
                    $('#tree_search_sysFunc').tree('uncheck', node.target);
                } else {
                    $('#tree_search_sysFunc').tree('check', node.target);
                }
            },
            onLoadSuccess: function (node, data) {
                $(this).find('span.tree-checkbox').unbind().click(function () {
                    $('#tree_search_sysFunc').tree('select', $(this).parent());
                    return false;
                });
                //回显勾选指定的值（目前用于单选）
                if (value != '' && value != null){
                    var node = $(this).tree('find', value);
                    //$(this).tree('expandAll');
                    $(this).tree('check',node.target);
                }
            }
        })
    })

    //子页面给父页面赋值操作
    function assignVal(winId,fieldId){
        var node = $('#tree_search_sysFunc').tree('getSelected');//获取选中节点
        if (node == null){
            $.messager.alert("提示","请选择父级功能菜单");
        }else{
            var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
            var data = $('#tree_search_sysFunc').tree('getData',node.target);
            $('#' + fieldId_).textbox('setValue', data.id);
            $('#'+winId).window('close');
        }
    }

    function closeWindow(winId) {
        $('#'+winId).window('close');
    }
</script>
</body>
</html>
