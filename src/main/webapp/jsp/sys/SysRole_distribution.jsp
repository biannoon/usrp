<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/18
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp" %>
<%
    String sysId = request.getParameter("sysId");
    String roleId = request.getParameter("roleId");
    String winId = request.getParameter("winId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysRole.js"></script>
</head>
<body>
<table style="width: 100%;">
    <tr>
        <td valign="top" align="left" width="40%">
            <div >
                <a href="#" class="easyui-linkbutton" plain=true
                   data-options="iconCls:'icon-add'"
                   onclick="toExpandAll('tree_sys_role_func')">展开</a>&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" plain=true
                   data-options="iconCls:'icon-remove'"
                   onclick="toCollapseAll('tree_sys_role_func')">折叠</a>&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton" plain=true
                   data-options="iconCls:'icon-save'"
                   onclick="saveRoleFunc()">保存</a>
            </div>
        </td>
    </tr>
    <tr>
        <td valign="top" align="left" width="60%">
            <div><ul id="tree_sys_role_func"></ul></div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function () {
        initSysRoleFuncTree('tree_sys_role_func','<%=sysId%>','<%=roleId%>');
    })

    /**
     * 描述：初始化角色权限功能树
     * @param treeId
     * @param sysId 所属系统ID
     * @param roleId 角色ID
     */
    function initSysRoleFuncTree(id,sysId,roleId){
        $('#'+id).tree({
            url:basePath + 'sysRole/listAllSysRoleMenu?sysId=' + sysId + '&roleId=' + roleId,
            checkbox:true,
            cascadeCheck: true,//取消（点击父级菜单，同时选中子级菜单）
            loadFilter: function(data){
                return data;//加载后端返回的json字符串
            }
        });
    }


    //树展开
    function toExpandAll(id) {
        $('#'+id).tree('expandAll');
    }
    //树折叠
    function toCollapseAll(id){
        $('#'+id).tree('collapseAll');
    }
    /**
     * 描述：角色菜单功能分配提交
     */
    function saveRoleFunc(){
        var url = basePath + 'sysRole/distributeFunc';
        var roleId = '<%=roleId%>';
        var nodes = $('#tree_sys_role_func').tree('getChecked');
        var funcs = "";
        for(var i=0; i<nodes.length; i++){
            funcs += nodes[i].id + ",";
        }
        //-提交
        $.ajax({
            type : 'post',
            url : url,
            data : {'roleId':roleId,'funcs':funcs},
            dataType : 'json',
            success : function(data){
                if(data.success){
                    $('#<%=winId%>').window('close');
                    $.messager.alert('提示信息',data.message,'info');
                }else{
                    $.messager.alert("警告",data.message,'error');
                }
            },
            error : function(){
                $.messager.alert("警告","ajax提交报错",'error');
            }
        });
    }
</script>
</body>
</html>
