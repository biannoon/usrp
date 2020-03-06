<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title></title>
    </head>
    <body>
        <table style="width: 100%;">
            <tr>
                <td valign="top" align="left" width="40%">
                    <div >
                        <a href="#" class="easyui-linkbutton"
                           data-options="iconCls:'icon-add'" onclick="toExpandAll()">展开</a>&nbsp;&nbsp;
                        <a href="#" class="easyui-linkbutton"
                           data-options="iconCls:'icon-remove'" onclick="toCollapseAll()">折叠</a>&nbsp;&nbsp;
                        <a href="#" class="easyui-linkbutton"
                           data-options="iconCls:'icon-save'" onclick="saveRoleFunc()">保存</a>
                    </div>
                </td>
            </tr>
            <tr>
                <!--菜单树-->
                <td valign="top" align="left" width="60%">
                    <div>
                        <ul id="funcTree"></ul>
                    </div>
                </td>
            </tr>
            <input type="hidden" name="roleId" value="<%=request.getParameter("roleId")%>">
        </table>
        <script type="text/javascript">
            $(function () {
                //获取第一层目录
                $.post(basePath + "SysFunc/getTreeMenu?treeId=root", function(data){
                    $("#funcTree").tree({
                        checkbox : true,
                        data : data,
                        onBeforeExpand:function(node){
                            $("#funcTree").tree('options').url= basePath + "SysFunc/getTreeMenu?treeId="+node.id;
                        },
                        onClick : function(node){
                            if (node.state == 'closed'){
                                $(this).tree('expand', node.target);
                            }else if (node.state == 'open'){
                                $(this).tree('collapse', node.target);
                            }
                        }
                    });
                }, 'json');
            })
            //树展开
            function toExpandAll() {
                $('#funcTree').tree('expandAll');
            }
            //树折叠
            function toCollapseAll(){
                $('#funcTree').tree('collapseAll');
            }
            function saveRoleFunc(){
                var url = basePath + 'sysRole/distributeFunc';
                var roleId = $("input[name='roleId']").val();
                var nodes = $('#funcTree').tree('getChecked');
                var funcs = "";
                for(var i=0; i<nodes.length; i++){
                    funcs += nodes[i].id + ",";
                }
                ajaxSubmit("post",url,"roleId="+roleId+"&funcs="+funcs,"json");
            }
        </script>
    </body>
</html>
