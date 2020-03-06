<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/12
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysfunc.js"></script>
</head>
<body class="easyui-layout" style="background:#eee;">
    //树状结构区
    <div data-options="region:'west',title:'系统功能菜单',split:true,border:false,toolbar:'#tree_tool'" style="width:300px;">
        <div id="tree_tool" style="background:#eee;">
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="toExpandTree('tree_sysFunc')">展开</a>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" onclick="toCollapseTree('tree_sysFunc')">折叠</a>
        </div>
        <table>
            <tr>
                <td valign="top" align="left">
                    <div>
                        <ul id="tree_sysFunc"></ul><!--菜单树-->
                    </div>
                </td>
            </tr>
        </table>
    </div>
    //列表展示区
    <div data-options="region:'center',title:'下级菜单列表'" style="background:#eee;">
        <table style="width: 100%;">
            <tr>
                <td valign="top" align="right">
                    <div style="width: 100%; height: 100%">
                        <div id="dg_tool" align="right">
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="toInsert('win_sysFunc','功能菜单新增','900','400','<%=basePath%>sysFunc/toInsert')">功能菜单新增</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain="true" onclick="toModify('win_sysFunc','功能菜单修改','900','400','<%=basePath%>sysFunc/toUpdate')">功能菜单修改</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true" onclick="toDelete()">功能菜单删除</a>
                        </div>
                        <table id="dg_sysFunc" class="easyui-datagrid" style="height:450px;">
                            <thead>
                            <tr>
                                <th data-options="field:'cb',checkbox:true,align:'center'"></th>
                                <th data-options="field:'funcId',width:80,align:'center'">功能编号</th>
                                <th data-options="field:'funcNm',width:100,align:'center'">功能名称</th>
                                <th data-options="field:'funcType',width:80,align:'center',dictry:'SYS07',formatter:formatData">功能类型</th>
                                <th data-options="field:'pareFuncId',width:80,align:'center'">上级功能编号</th>
                                <th data-options="field:'url',width:200,align:'center'">菜单路径</th>
                                <th data-options="field:'finlModifr',width:80,align:'center'">最后修改人</th>
                                <th data-options="field:'finlModfyDt',width:100,align:'center'">最后修改日期</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td><input class="easyui-textbox" type="hidden" id="pid" name="pid" value=""></td><!--隐藏所选节点ID-->
                <td><input class="easyui-textbox" type="hidden" id="subsystemId_hid" value=""></td><!--隐藏所选择树节点的所属系统ID-->
            </tr>
        </table>
    </div>
    <!--动态生成模态框-->
    <div id="win_sysFunc"></div>
<script type="text/javascript">
    //页面初始化
    $(function () {
        //初始化菜单树
        $('#tree_sysFunc').tree({
            url:'<%=basePath%>sysFunc/listAllSysFunc',
            //checkbox:true,
            //cascadeCheck: false,取消（点击父级菜单，同时选中子级菜单）
            loadFilter: function(data){
                //加载后端返回的json字符串
                return data;
            },
            onClick:function(node){//点击事件
                var id = node.id;
                var subsystemId = node.attributes.subsystemId;
                //加载刷新数据网格数据
                $('#dg_sysFunc').datagrid('load',{
                    id:id
                });
                $('#pid').textbox('setValue',id);
                $('#subsystemId_hid').textbox('setValue',subsystemId);
            }
        });
        //----初始化datagrid
        $('#dg_sysFunc').datagrid({
            url:'<%=basePath%>sysFunc/listSysFuncByDataGrid',
            fitColumns:true,
            singleSelect:true,
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pagePosition:'top',
            toolbar:'#dg_tool',
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
            }
        });
    })

    /**
     * 描述：跳转新增功能菜单窗口
     * @param winId 打开的window窗口ID
     * @param title 窗口的标题
     * @param width 窗口的宽度
     * @param height 窗口的高度
     * @param url 窗口内容的url链接
     * @author pengjuntao
     */
    function toInsert(winId,title,width,height,url){
        var node = $('#tree_sysFunc').tree('getSelected');//获取选中节点
        if (node == null){
            $.messager.alert("提示","请选择需要操作的菜单");
        }else{
            var data = $('#tree_sysFunc').tree('getData',node.target);//获取选中节点的数据
            var id = data.id;//获取选中节点的id
            if (id == 'root'){
                $.messager.alert("提示","功能资源为虚拟节点，不可在该节点下添加功能菜单！")
            }else{
                openWindowSelf(winId,title,width,height,url);
            }
        }
    }

    /**
     * 描述：跳转修改功能菜单窗口
     * @param winId 打开的window窗口ID
     * @param title 窗口的标题
     * @param width 窗口的宽度
     * @param height 窗口的高度
     * @param url 窗口内容的url链接
     * @author pengjuntao
     */
    function toModify(winId,title,width,height,url){
        var row = $('#dg_sysFunc').datagrid('getSelected');//获取选择行
        if (row == null){
            $.messager.alert("提示","请在列表中选择需要操作的功能资源");
        }else{
            var funcId =  row.funcId;
            url = url + '?funcId=' + funcId;
            openWindowSelf(winId,title,width,height,url);
        }
    }

    /**
     * 描述删除指定功能菜单
     * @author pengjuntao
     */
    function toDelete(){
        var row = $('#dg_sysFunc').datagrid('getSelected');
        if (row == null){
            $.messager.alert("提示","请在列表中选择需要操作的菜单");
        }else{
            var funcId = row.funcId;
            if(hasNextLvl(funcId,'<%=basePath%>sysFunc/hasNextLvl','2')){
                $.messager.alert("提示","所选中菜单包含下一级菜单，不能删除！");
            }else{
                $.messager.confirm('Confirm','您确定要删除选中的菜单吗?',function(r){
                    if (r){
                        //异步删除菜单
                        $.ajax({
                            url:'<%=basePath%>sysFunc/delete',
                            type:'post',
                            dataType:'json',
                            data:{"FUNC_ID":funcId},
                            success:function (data) {
                                //下面这俩个步骤用于处理后端传递的object对象
                                var data = JSON.stringify(data);
                                var data = eval('(' + data + ')'); // change the JSON string to javascript object
                                if (data.success){
                                    //刷新页面数据
                                    refreshTreeBySelf('tree_sysFunc','<%=basePath%>sysFunc/listAllSysFunc');
                                    refreshDataGridBySelf('dg_sysFunc');
                                    $.messager.alert("提示","删除成功");
                                }else{
                                    $.messager.alert("提示",data.message);
                                }
                            },
                            error:function(){
                                $.messager.alert("警告","删除请求失败");
                            }
                        });
                    }
                });
            }
        }
    }

</script>
</body>
</html>
