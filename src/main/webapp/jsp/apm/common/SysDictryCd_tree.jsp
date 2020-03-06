<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/9
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp" %>
<%
    String checkBox = (String) request.getAttribute("checkBox");
    String selectLeafOnly = (String) request.getAttribute("selectLeafOnly");
    String dictryId = (String) request.getAttribute("dictryId");
    String winId = (String) request.getAttribute("winId");
    String fieldId = (String) request.getAttribute("fieldId");
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
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="assignVal('<%=winId%>','<%=fieldId%>')">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow('<%=winId%>')">关闭</a>
        </td>
    </tr>
    <tr>
        <td align="left" width="100%">
            <div>
                <ul id="tree_sysDictryCd"></ul><!--菜单树-->
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function () {
        //获取文本框的值
        var fieldId = '<%=fieldId%>';
        var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
        var value = $('#'+_fieldId).val();
        $('#tree_sysDictryCd').tree({
            url:'<%=basePath%>common/getSysDictryCdListByTree?dictryId=<%=dictryId%>',
            checkbox:true,
            cascadeCheck: false,//取消（点击父级菜单，同时选中子级菜单）
            <%if("1".equals(selectLeafOnly)){%>
                onlyLeafCheck:true,
            <%}%>
            //加载后端返回的json字符串
            loadFilter: function(data){
                return data;
            },
            onSelect:function (node) {
                <% if("0".equals(checkBox)){%>
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
                <%}%>
            },
            onLoadSuccess: function (node, data) {
                <% if("0".equals(checkBox)){%>
                    $(this).find('span.tree-checkbox').unbind().click(function () {
                        $('#tree_sysDictryCd').tree('select', $(this).parent());
                        return false;
                    });
                <%}%>
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
    function assignVal(winId,fieldId){//目前用于单选，多选暂不支持
        var nodes = $('#tree_sysDictryCd').tree('getChecked');//获取选中节点
        if (nodes == null){
            $.messager.alert("提示","请选择字典记录");
        }else{
            var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
            var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
            $.each(nodes,function (index,node) {
                var data = $('#tree_sysDictryCd').tree('getData',node.target);
                if (fieldId_ != '' && fieldId_ != null){
                    $('#'+fieldId_).textbox('setValue',data.text);
                }
                if (_fieldId != '' && _fieldId != null){
                    $('#'+_fieldId).textbox('setValue',data.id);
                }
            })
            /*for(var i = 0; i < nodes.length; i ++){
                var dictryId = nodes[i].id;
                var dictryName = nodes[i].text;
                initTextboxSelf("dictryNm",dictryName,false);
                initTextboxSelf("dictryId",dictryId,false);
            }*/
            $('#'+winId).window('close');
        }
    }

    function closeWindow(winId){
        $('#'+winId).window('close');
    }
</script>
</body>
</html>
