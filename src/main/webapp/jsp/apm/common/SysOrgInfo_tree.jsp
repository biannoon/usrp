<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/11
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String treeType = (String) request.getAttribute("treeType");
    String selfTreeType = (String) request.getAttribute("selfTreeType");
    String orgLimits = (String) request.getAttribute("orgLimits");
    String selectType = (String) request.getAttribute("selectType");
    String expOrgNo = (String) request.getAttribute("expOrgNo");
    String treeNodeSelectType = (String) request.getAttribute("treeNodeSelectType");
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
<table width = "100%">
    <tr>
        <div id="tree_tool" style="background:#eee;" align="left">
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="toExpandTree('tree_sysOrgInfo')">展开</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="toCollapseTree('tree_sysOrgInfo')">折叠</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-ok" onclick="assignVal('<%=winId%>','<%=fieldId%>')">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" onclick="closeWindow('<%=winId%>')">关闭</a>
        </div>
        <td align="left" width="100%">
            <div>
                <ul id="tree_sysOrgInfo"></ul><!--菜单树-->
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

        $('#tree_sysOrgInfo').tree({
            <%if ("0".equals(treeType)){%>
                url:'<%=basePath%>common/getSysOrgInfoByStandard?orgLimits=<%=orgLimits%>&expOrgNo=<%=expOrgNo%>',
            <%}else{%>
                url:'<%=basePath%>common/getSysOrgInfoBySelf?selfTreeType=<%=selfTreeType%>&orgLimits=<%=orgLimits%>&expOrgNo=<%=expOrgNo%>',
            <%}%>
            checkbox:true,
            cascadeCheck: false,//取消（点击父级菜单，同时选中子级菜单）
            toolbar:'#tree_tool',
            //onlyLeafCheck:true,
            onSelect:function (node) {
                //如果选择单选模式：
                <%if ("0".equals(selectType)){%>
                    var cknodes = $('#tree_sysOrgInfo').tree("getChecked");
                    for (var i = 0; i < cknodes.length; i++) {
                        if (cknodes[i].id != node.id) {
                            $('#tree_sysOrgInfo').tree("uncheck", cknodes[i].target);
                        }
                    }
                    if (node.checked) {
                        $('#tree_sysOrgInfo').tree('uncheck', node.target);
                    } else {
                        $('#tree_sysOrgInfo').tree('check', node.target);
                    }
                <%}%>
            },
            onLoadSuccess:function (node,data) {
                <%if ("0".equals(selectType)){%>
                    $(this).find('span.tree-checkbox').unbind().click(function () {
                        $('#tree_sysOrgInfo').tree('select', $(this).parent());
                        return false;
                    });
                <%}%>
                //回显勾选指定的值（目前用于单选）
                if (value != '' && value != null){
                    var node = $(this).tree('find', value);
                    //$(this).tree('expandAll');
                    $(this).tree('check',node.target);
                }
            },
            onCheck:function (node, checked) {
                var flag = false;
                var nodeData = $('#tree_sysOrgInfo').tree("getData",node.target);
                var tpId = nodeData.attributes.spr_org_id;
                var canCheck = nodeData.attributes.canCheck;
                var tId = nodeData.id;
                if (canCheck == 'false'){
                    $('#tree_sysOrgInfo').tree("uncheck",node.target);
                    $.messager.alert("提示","本节点为虚拟节点，不可选择");
                } else{
                    <%if ("0".equals(treeNodeSelectType)){%>
                        var nodes = $('#tree_sysOrgInfo').tree("getChecked");
                        for (var i = 0; i < nodes.length; i ++){
                            var data = $('#tree_sysOrgInfo').tree("getData",nodes[i].target);
                            var pid = data.attributes.spr_org_id;
                            if (pid == tId || data.id == tpId){
                                flag = true;
                            }
                        }
                        if (flag == true){
                            $.messager.alert("提示","选择上级节点后，该节点的所有下级节点不可选择");
                            $('#tree_sysOrgInfo').tree("uncheck",node.target);
                        }
                    <%}%>
                }
            },
            //加载后端返回的json字符串
            loadFilter: function(data){
                return data;
            }
        })
    })

    //子页面给父页面赋值操作
    function assignVal(winId,fieldId){//支持单选
        var nodes = $('#tree_sysOrgInfo').tree('getChecked');//获取选中节点
        if (nodes == null){
            $.messager.alert("提示","请选择机构");
        }else{
            //文本框赋值
            var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
            var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
            $.each(nodes,function (index,node) {
                var data = $('#tree_sysOrgInfo').tree('getData',node.target);
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

    //关闭窗口
    function closeWindow(winId) {
        $('#'+winId).window('close');
    }

    /**
     * 描述：展开树
     * @param treeId 树ID
     * @author pengjuntao
     */
    function toExpandTree(treeId) {
        $('#'+treeId).tree('expandAll');
    }
    /**
     * 描述：折叠树
     * @param treeId 树ID
     * @author pengjuntao
     */
    function toCollapseTree(treeId){
        $('#'+treeId).tree('collapseAll');
    }
</script>
</body>
</html>
