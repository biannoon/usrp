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
    String flag = (String) request.getAttribute("flag");
    String whereFlag = (String) request.getAttribute("whereFlag");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
<table width = "100%">
    <% if ("0".equals(flag)){%>
        <tr>
            <div id="tree_tool_01" style="background:#eee;" align="left">
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
    <%}else if("1".equals(flag)){%>
        <tr>
            <td>
                <div id="tree_tool_02" align="right">
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="assignVal('<%=winId%>','<%=fieldId%>')">确定</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeWindow('<%=winId%>')">关闭</a>
                </div>
                <table id="dg_resources_sys_list" class="easyui-datagrid" style="height: 450px">
                    <thead>
                    <tr>
                        <th data-options="field:'cd',checkbox:true"></th>
                        <th data-options="field:'subsystemId',width:100,align:'center'">子系统ID</th>
                        <th data-options="field:'subsystemNm',width:100,align:'center'">子系统名称</th>
                    </tr>
                    </thead>
                </table>
            </td>
        </tr>
    <%}%>
</table>
<script type="text/javascript">
    $(function () {
        <% if ("0".equals(flag)){%>
            $('#tree_sysOrgInfo').tree({
                <%if ("0".equals(treeType)){%>
                url:'<%=basePath%>common/getSysOrgInfoByStandard?orgLimits=<%=orgLimits%>&expOrgNo=<%=expOrgNo%>',
                <%}else{%>
                url:'<%=basePath%>common/getSysOrgInfoBySelf?selfTreeType=<%=selfTreeType%>&orgLimits=<%=orgLimits%>&expOrgNo=<%=expOrgNo%>',
                <%}%>
                checkbox:true,
                cascadeCheck: false,//取消（点击父级菜单，同时选中子级菜单）
                toolbar:'#tree_tool_01',
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
                    //如果选择单选模式：
                    <%if ("0".equals(selectType)){%>
                    $(this).find('span.tree-checkbox').unbind().click(function () {
                        $('#tree_sysOrgInfo').tree('select', $(this).parent());
                        return false;
                    });
                    <%}%>
                    //回显勾选指定的值
                    <%if ("0".equals(whereFlag)){%>
                    var rows = $('#dg_group_resources').datagrid('getRows');
                    <%}else if ("1".equals(whereFlag)){%>
                    var rows = $('#dg_group_resources_list').datagrid('getRows');
                    <%}%>
                    if (rows != null && rows != ''){
                       $.each(rows,function (index,row) {
                           if (row.recoursTyp == 'SYS1101' || row.recoursTyp == '机构'){
                               var id = row.recoursId;
                               var ck = $('#tree_sysOrgInfo').tree('find',id);
                               $('#tree_sysOrgInfo').tree('check',ck.target);
                           }
                        })
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
                        //$.messager.alert("提示","本节点为虚拟节点，不可选择");
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
                            //$.messager.alert("提示","选择上级节点后，该节点的所有下级节点不可选择");
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
        <%}else if("1".equals(flag)){%>
            $('#dg_resources_sys_list').datagrid({
                url: '<%=basePath%>sysGroup/getChildSysFromSysFuncBySysGroup',
                fitColumns: true,
                singleSelect: false,
                pagination: true,
                pageNumber: '1',
                pageSize: '10',
                pagePosition:'top',
                toolbar:'#tree_tool_02',
                onLoadSuccess: function (data) {
                    //去除表头的复选框（表头复选框有全选的作用）
                    $(".datagrid-header-check").html("");
                    //更改的是datagrid中的数据字体大小
                    $('.datagrid-cell').css('font-size', '12px');
                    //datagrid中的列名称
                    $('.datagrid-header .datagrid-cell span ').css('font-size','12px');

                    //回显勾选指定的值
                    var targetRows = $('#dg_resources_sys_list').datagrid('getRows');
                    <%if ("0".equals(whereFlag)){%>
                        var rows = $('#dg_group_resources').datagrid('getRows');
                    <%}else if ("1".equals(whereFlag)){%>
                        var rows = $('#dg_group_resources_list').datagrid('getRows');
                    <%}%>
                    if (rows != null && rows != ''){
                        $.each(rows,function (index01,row) {
                            if (row.recoursTyp == 'SYS1102'  || row.recoursTyp == '子系统'){
                                var id = row.recoursId;
                                $.each(targetRows,function (index02,targetRow) {
                                    var targetId = targetRow.subsystemId;
                                    if (id == targetId){
                                        $('#dg_resources_sys_list').datagrid('checkRow',index02);
                                    }
                                })
                            }
                        })
                    }
                }
            })
        <%}%>
    })

    /**
     * 描述：多选后，回显到数据列表中
     */
    function assignVal(winId,fieldId){
        <%if ("0".equals(whereFlag)){%>
            var groupId = $('#groupId_hidden').textbox('getValue');
        <%}else if("1".equals(whereFlag)){%>
            var groupId = $('#groupId_insert').textbox('getValue');
        <%}%>
        var resources = "";
        var resourceType = "";
        <% if ("0".equals(flag)){%>
            resourceType = 'SYS1101';
            var nodes = $('#tree_sysOrgInfo').tree('getChecked');//获取选中节点
            if (nodes == null){
                $.messager.alert("提示","请选择机构");
            }else{
                $.each(nodes,function (index,node) {
                    var data = $('#tree_sysOrgInfo').tree('getData',node.target);
                    if (index == nodes.length-1){
                        resources = resources + data.id + '$' + data.text;
                    }else{
                        resources = resources + data.id + '$' + data.text + ',';
                    }
                });
            }
        <%}else if("1".equals(flag)){%>
            resourceType = 'SYS1102';
            var rows = $('#dg_resources_sys_list').datagrid('getChecked');
            if (rows == null || rows == ''){
                $.messager.alert('提示','请选择子系统');
            }else{
                $.each(rows,function (index,row) {
                    if (index == rows.length-1){
                        resources = resources + row.subsystemId + '$' + row.subsystemNm;
                    }else{
                        resources = resources + row.subsystemId + '$' + row.subsystemNm + ',';
                    }
                });
            }
        <%}%>
        sumbitResources(resources,groupId,resourceType);
        $('#'+winId).window('close');
    }

    function sumbitResources(resources,groupId,resourceType){
        $.ajax({   //将选中的数据发送到后端，存入内存之中
            <%if ("0".equals(whereFlag)){%>
                url:'<%=basePath%>sysGroup/addSysGroupResourcesToDB',
            <%}else if("1".equals(whereFlag)){%>
                url:'<%=basePath%>sysGroup/addResources',
            <%}%>
            type:'post',
            dataType:'json',
            data:{'resources':resources,'resourceType':resourceType,'groupId':groupId},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    <%if ("0".equals(whereFlag)){%>
                    $('#dg_group_resources').datagrid('reload');
                    <%}else if("1".equals(whereFlag)){%>
                    $('#dg_group_resources_list').datagrid('reload');
                    <%}%>
                    $.messager.alert("提示",data.message);
                }else{
                    $.messager.alert("提示",data.message);
                }
            }
        });
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
