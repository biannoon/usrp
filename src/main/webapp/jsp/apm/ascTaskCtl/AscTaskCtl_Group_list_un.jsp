<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/20
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%@ page import="com.scrcu.sys.entity.SysFunc" %>
<%@ page import="java.util.List" %>
<%
    List<SysFunc> buttonList = (List<SysFunc>) request.getAttribute("buttonList");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ascTaskCtl/ascGroup.js"></script>
</head>
<body class="easyui-layout" style="padding:5px;background:#eee;">
    //树形结构面板
    <div data-options="region:'west',title:'任务组',split:true,tools:'tree-tools'" style="width:350px;">
        <div id="tree-tools" style="background:#eee;">
            <a id="btn_expand" href="#"></a>
            <a id="btn_collapse" href="#"></a>
            <a id="btn_add" href="#" ></a>
            <a id="btn_update" href="#" ></a>
            <a id="btn_remove" href="#" ></a>
        </div>
        <table width="100%">
            <tr>
                <td valign="top" align="left">
                    <div>
                        <table width="100%" align="center">
                            <tr><td><ul id="tree_group"></ul></td></tr><!--任务组树-->
                        </table>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    //列表显示面板
    <div data-options="region:'center',title:''" style="background:#eee;">
        <div id="tbs_sysGroup" class="easyui-tabs" data-options="fit:true">
            <div title="任务组下任务列表" data-options="closable:false" style="">
                <table width="100%">
                    <tr>
                        <td valign="top" align="right">
                            <div>
                                <table id="dg_group" class="easyui-datagrid" style="height:450px;">
                                    <thead>
                                    <tr>
                                        <th data-options="field:'sb',width:100,checkbox:true"></th>
                                        <th data-options="field:'taskId',width:100">任务编号</th>
                                        <th data-options="field:'taskName',width:100">任务名称</th>
                                        <th data-options="field:'taskType',width:100,align:'center',dictry:'ASC010',formatter:formatData">任务类型</th>
                                        <th data-options="field:'taskCategory',width:100,align:'center',dictry:'ASC003',formatter:formatData">任务种类</th>
                                        <th data-options="field:'nextDate',width:120,align:'center'">待处理日期</th>
                                        <th data-options="field:'frequency',width:100,align:'center',dictry:'ASC002',formatter:formatData">执行频率</th>
                                        <th data-options="field:'priority',width:100,align:'center',dictry:'ASC011',formatter:formatData">优先级</th>
                                        <th data-options="field:'state',width:100,align:'center',dictry:'ASC001',formatter:formatData">任务状态</th>
                                        <th data-options="field:'crtDate',width:100,align:'center'">创建日期</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="任务组详情" data-options="closable:false" style="">
                <table width="100%">
                    <tr>
                        <td align="right" width="25%"><span style="font-size: smaller" >任务组编号：</span></td>
                        <td align="left" width="25%">
                            <span id="groupId_detail" style="font-size: small"></span>
                            <%--<input class="easyui-validatebox" id="groupId_detail" name="groupId" value="" data-options="required:true,validType:'uniqueGroupId'">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="25%"><span style="font-size: small">任务组名称：</span></td>
                        <td align="left" width="25%">
                            <span id="groupName_detail" style="font-size: small"></span>
                            <%--<input class="easyui-validatebox" id="groupName_detail" name="groupName" value="" data-options="required:true">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="25%"><span style="font-size: small">上级任务组：</span></td>
                        <td align="left" width="25%">
                            <span id="parentGroup_detail" style="font-size: small"></span>
                            <%--<input class="easyui-validatebox" id="parentGroup_detail" name="parentGroup" value="" data-options="required:true">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="25%"><span style="font-size: small">待处理时间：</span></td>
                        <td align="left" width="25%">
                            <span id="nextDate_detail" style="font-size: small"></span>
                            <%--<input class="easyui-validatebox" id="nextDate_detail" name="nextDate" value=""  data-options="required:true">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" width="25%"><span style="font-size: small">任务组状态：</span></td>
                        <td align="left" width="25%">
                            <span id="state_detail" style="font-size: small"></span>
                            <%--<input class="easyui-validatebox" id="state_detail" name="state" value="" data-options="required:true">--%>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <!--动态生成模态框-->
    <div id="win_ascGroup"></div>
<script type="text/javascript">
    $(function () {
        initAscGroupList('<%=basePath%>');
        //-不做权限控制的按钮
        createLinkedButtonBySelf('btn_expand','展开','icon-add');
        $('#btn_expand').bind('click',function () {
            expandAscGroupTree('tree_group');
        });
        createLinkedButtonBySelf('btn_collapse','折叠','icon-remove');
        $('#btn_collapse').bind('click',function () {
            collapseAscGroupTree('tree_group');
        })
        //-权限控制的按钮
        <%for (SysFunc button : buttonList){%>
        <%if ("T010301".equals(button.getFuncId())){%>
            createLinkedButtonBySelf('btn_add','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
            $('#btn_add').bind('click',function () {
                toInsertAscGroup('win_ascGroup','<%=basePath%>');
            });
        <%}else if ("T010302".equals(button.getFuncId())){%>
            createLinkedButtonBySelf('btn_update','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
            $('#btn_update').bind('click',function () {
                toUpdateAscGroup('win_ascGroup','<%=basePath%>');
            });
        <%}else if ("T010303".equals(button.getFuncId())){%>
            createLinkedButtonBySelf('btn_remove','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
            $('#btn_remove').bind('click',function () {
                toDeleteAscGroup('<%=basePath%>');
            });
        <%}}%>
   })
</script>
</body>
</html>
