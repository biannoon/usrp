<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import=" com.scrcu.sys.entity.SysDictryCd,java.util.*" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String roleId = (String) request.getAttribute("roleId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/demo/demo.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.easyui.min.js"></script>
</head>
<body style="padding:5px;background:#eee;">
<%--<div data-options="region:'center'" style="padding:5px;background:#eee;">--%>
    <form id="form_rolefunc" method="post">
        <table style="width: 100%;">
            <tr>
                <td valign="top" align="left" width="30%">
                    <!--菜单管理操作按钮-->
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="toExpandTree('tree_sysFunc')">展开</a>
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="toCollapseTree('tree_sysFunc')">折叠</a>
                </td>
            </tr>
            <tr>
                <td valign="top" align="left" width="30%">
                    <div>
                        <ul id="tree_sysFunc"></ul><!--菜单树-->
                    </div>
                </td>

            </tr>
            <tr>
                <td><input type="hidden" id="pid" name="pid" value=""></td><!--埋点一个所选节点ID-->
            </tr>
        </table>
    </form>
    <div style="text-align:center">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
    </div>
<%--</div>--%>
<script type="text/javascript">
    var roleids =  "<%=roleId%>";
    //初始化页面
    $(function () {
        //初始化菜单树
        $('#tree_sysFunc').tree({
            url:'<%=basePath%>sysFunc/listAllSysRoleFunc?roleId='+roleids,
            checkbox:true,
            //cascadeCheck: false,取消（点击父级菜单，同时选中子级菜单）
            loadFilter: function(data){
                //加载后端返回的json字符串
                return data;
            },
            onClick:function(node){//点击事件
                var id = node.id;
                //加载刷新数据网格数据
                $('#dg_sysFunc').datagrid('load',{
                    id:id,
                });

            }
    });
        //加载刷新数据网格数据
        $('#dg_sysFunc').datagrid('load',{
            id:'root',
        });

    })
    //提交
    function submitForm() {
        $('#form_rolefunc').form({
            url:'<%=basePath%>sysFunc/saveAllRoleSysFunc',
            onSubmit:function (param) {
                //输入验证
                var nodes = $('#tree_sysFunc').tree('getChecked');
                var str = '';
                for(var i=0;i<nodes.length;i++){
                    str+=nodes[i].id+','+roleids+";"
                }
                if(str=='' || str==null){
                    str+=roleids
                }
                param.str =str;
            },
            success:function (data) {
                var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                if (data.success){
                   <!-- refreshTab("字典代码管理","toSysDictryCdList")-->
                 window.location.href="<%=basePath%>sysRole/index";
                }else{
                    alert(data.message);
                }
            }
        })
        $('#form_rolefunc').form('submit');

    }

    //重置
    function clearForm() {
        $('#form_insert').form('reset');
    }

    //检查任务组Id的唯一性
    function checkGroupId(){
        var dictryId = document.getElementById("dictryId_insert").value;
        if (ajaxCheckId(dictryId)==false){
            remind("dictryId_back","任务编号已存在")
        }
    }

    function ajaxCheckId(taskId){
        var result = false;
        $.ajax({
            async:false,
            url:'AscTask/checkAscTaskId',
            type:'post',
            dataType:'json',
            data:{"taskId":taskId},
            success:function (data) {
                var data = JSON.stringify(data);
                var data = eval('(' + data + ')');
                if (data.success){
                    result = true
                }else{
                    result = false;
                }
            },
            error:function () {
                $.messager.alert("警告","请求失败");
            }
        })
        return result;
    }

    function remind(field,msg){
        document.getElementById(field).style.display = 'block';
        document.getElementById(field).value = msg;
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
