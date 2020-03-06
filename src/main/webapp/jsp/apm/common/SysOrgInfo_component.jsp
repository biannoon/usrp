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
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="toExpandTree('tree_sys_org_info')">展开</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="toCollapseTree('tree_sys_org_info')">折叠</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-ok" onclick="assignVal_org('tree_sys_org_info','<%=winId%>','<%=fieldId%>')">确定</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-cancel" onclick="closeComponentWindow('<%=winId%>')">关闭</a>
        </div>
        <td align="left" width="100%">
            <div>
                <ul id="tree_sys_org_info"></ul><!--菜单树-->
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
        var url = "";
        <%if ("0".equals(treeType)){%>
            url='<%=basePath%>common/getSysOrgInfoByStandard?orgLimits=<%=orgLimits%>&expOrgNo=<%=expOrgNo%>';
        <%}else{%>
            url='<%=basePath%>common/getSysOrgInfoBySelf?selfTreeType=<%=selfTreeType%>&orgLimits=<%=orgLimits%>&expOrgNo=<%=expOrgNo%>';
        <%}%>
        initSysOrgInfoTreeFromComponent('tree_sys_org_info',url,'<%=selectType%>','<%=treeNodeSelectType%>',value);
    })

    /**
     * 描述：初始化机构选择组件的tree
     * @param id
     * @param url
     * @param selectType
     * @param treeNodeSelectType
     * @param echoValue
     */
    function initSysOrgInfoTreeFromComponent(id,url,selectType,treeNodeSelectType,echoValue){
        $('#' + id).tree({
            url:url,
            checkbox:true,
            cascadeCheck:false,//取消（点击父级菜单，同时选中子级菜单）
            toolbar:'#tree_tool',
            loadFilter: function(data){ //加载后端返回的json字符串
                return data;
            },
            onSelect:function (node) {
                if (selectType == '0'){
                    var cknodes = $('#'+id).tree("getChecked");
                    for (var i = 0; i < cknodes.length; i++) {
                        if (cknodes[i].id != node.id) {
                            $('#'+id).tree("uncheck", cknodes[i].target);
                        }
                    }
                    if (node.checked) {
                        $('#'+id).tree('uncheck', node.target);
                    } else {
                        $('#'+id).tree('check', node.target);
                    }
                }
            },
            onLoadSuccess:function (node,data) {
                if (selectType == '0') {
                    $(this).find('span.tree-checkbox').unbind().click(function () {
                        $('#'+id).tree('select', $(this).parent());
                        return false;
                    });
                }
                //回显勾选指定的值（目前用于单选）
                if (echoValue != '' && echoValue != null){
                    //机构号与机构编号转换
                    var orgId = exchangeOrgIdByCd(echoValue);
                    var node = $(this).tree('find', orgId);
                    //$(this).tree('expandAll');
                    $(this).tree('check',node.target);
                }
            },
            onCheck:function (node, checked) {
                var flag = false;
                var nodeData = $('#'+id).tree("getData",node.target);
                var tpId = nodeData.attributes.spr_org_id;
                var canCheck = nodeData.attributes.canCheck;
                var tId = nodeData.id;
                if (canCheck == 'false'){
                    $('#'+id).tree("uncheck",node.target);
                    $.messager.alert("提示","本节点为虚拟节点，不可选择");
                } else{
                    if(treeNodeSelectType == '0'){
                        var nodes = $('#'+id).tree("getChecked");
                        for (var i = 0; i < nodes.length; i ++){
                            var data = $('#'+id).tree("getData",nodes[i].target);
                            var pid = data.attributes.spr_org_id;
                            if (pid == tId || data.id == tpId){
                                flag = true;
                            }
                        }
                        if (flag == true){
                            $.messager.alert("提示","选择上级节点后，该节点的所有下级节点不可选择");
                            $('#'+id).tree("uncheck",node.target);
                        }
                    }
                }
            }
        })
    }

    //子页面给父页面赋值操作
    function assignVal_org(id,winId,fieldId){//支持单选
        var nodes = $('#'+id).tree('getChecked');//获取选中节点
        if (nodes == null){
            $.messager.alert("提示","请选择机构");
        }else{
            //文本框赋值
            var fieldId_ = fieldId.substring(0,fieldId.indexOf("$"));
            var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
            $.each(nodes,function (index,node) {
                var data = $('#'+id).tree('getData',node.target);
                if (fieldId_ != '' && fieldId_ != null){
                    $('#'+fieldId_).textbox('setValue',data.text);
                }
                if (_fieldId != '' && _fieldId != null){
                    var targetId = data.attributes.org_cd;
                    if (targetId.indexOf('_') != -1){
                        targetId = targetId.substring(targetId.indexOf('_')+1);
                    }
                    $('#'+_fieldId).textbox('setValue',targetId);
                }
            })
            $('#'+winId).window('close');
        }
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

    function closeComponentWindow(winId){
        $('#'+winId).window('close');
    }

    /**
     * 描述：机构号与机构编号转换
     */
    function exchangeOrgIdByCd(orgCd){
        var result = "";
        $.ajax({
            async:false,
            url:basePath + 'organization/getSysOrgInfoByOrgCd',
            type:'post',
            dataType:'json',
            data:{'orgCd':orgCd},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    result = data.data.id;
                }
            }
        });
        return result;

    }
</script>
</body>
</html>
