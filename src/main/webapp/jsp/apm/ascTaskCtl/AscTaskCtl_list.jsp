<%@ page import="com.scrcu.sys.entity.SysFunc" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/18
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/ascTaskCtl/ascTaskCtl.js"></script>
</head>
<body id="ascTaskPage" class="easyui-layout" style="background:#eee;">
    <div data-options="region:'north',title:'任务查询条件',hideCollapsedContent:false,collapsed:true" style="width:25%;height:130px;background:#eee;">
        <div style="text-align: center">
            <table width="100%" style="text-align: center">
                <tr>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei;">任务编号：</td>
                    <td align="left"><input class="easyui-textbox" id="taskId_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">任务名称：</td>
                    <td align="left"><input class="easyui-textbox" id="taskName_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">任务种类：</td>
                    <td align="left"><input class="easyui-combobox" id="taskCategory_se" style="height:20px;width:150px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">执行频率：</td>
                    <td align="left"><input class="easyui-combobox" id="frequency_se" style="height:20px;width:150px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                </tr>
                <tr>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">执行优先级：</td>
                    <td align="left"><input class="easyui-combobox" id="priority_se" style="height:20px;width:150px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">待处理日期：</td>
                    <td align="left"><input class="easyui-datebox" id="nextDate_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">待处理日期起：</td>
                    <td align="left"><input class="easyui-datebox" id="nextDate_start_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">待处理日期止：</td>
                    <td align="left"><input class="easyui-datebox" id="nextDate_end_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                </tr>
                <tr>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">创建日期起：</td>
                    <td align="left"><input class="easyui-datebox" id="crtDate_start_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">创建日期止：</td>
                    <td align="left"><input class="easyui-datebox" id="crtDate_end_se" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    <td align="right" style="font-size: 12px;font-family: Microsoft Yahei">任务状态：</td>
                    <td align="left"><input class="easyui-combobox" id="state_se" style="height:20px;width:150px;border:1px solid #ccc" data-options="editable:false,panelHeight:'auto'"></td>
                    <td colspan="2" align="right">
                        <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain=true onclick="doAscTaskSearch()" >查询</a>
                        <a href="#" class="easyui-linkbutton" iconCls="icon-set" plain=true onclick="resetAscTaskSearch()">重置</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div data-options="region:'center',title:'任务列表'" style="width:50%;background:#eee;">
        <table width="100%">
            <tr>
                <td>
                    <div>
                        <div id="dg_tools" align="right">
                            <a id="btn_add" href="#" ></a>
                            <a id="btn_update" href="#" ></a>
                            <a id="btn_remove" href="#" ></a>
                            <%--<a id="btn_taskconfig" href="#"></a>--%>
                            <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsertAscTaskPage('win_ascTask','任务新增','<%=basePath%>AscTask/toAscTaskInsert','800','430')">任务新增</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true onclick="toModifyAscTaskPage('win_ascTask','任务更新','<%=basePath%>AscTask/toAscTaskUpdate','800','430')">任务修改</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDeleteTask('<%=basePath%>')">任务删除</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" plain=true onclick="openTabs()">任务配置管理</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" plain=true onclick="toAscJobCfg('任务处理配置','<%=basePath%>AscTask/toAscJobCfg')">处理配置</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" plain=true onclick="toAscDependCfg('依赖配置','<%=basePath%>AscTask/toAscDependCfg')">依赖配置</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" plain=true onclick="toAscTriggerCfg('触发配置','<%=basePath%>AscTask/toAscTriggerCfg')">触发配置</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-redo'" plain=true onclick="toAscTriggerCfg('日志查询','<%=basePath%>AscTask/toAscJobLog')">处理日志查询</a>--%>
                        </div>
                        <table id="dg_ascTask" width="100%" hight="100%">
                            <%--<thead>
                            <tr>
                                <th data-options="field:'cb',checkbox:true"></th>
                                <th data-options="field:'taskId',width:100,align:'center'">任务编号</th>
                                <th data-options="field:'taskName',width:100,align:'center'">任务名称</th>
                                <th data-options="field:'taskType',width:100,align:'center',dictry:'ASC010',formatter:formatData">任务类型</th>
                                <th data-options="field:'taskCategory',width:100,align:'center',dictry:'ASC003',formatter:formatData">任务种类</th>
                                <th data-options="field:'nextDate',width:120,align:'center'">待处理日期</th>
                                <th data-options="field:'frequency',width:80,align:'center',dictry:'ASC002',formatter:formatData">执行频率</th>
                                <th data-options="field:'priority',width:80,align:'center',dictry:'ASC011',formatter:formatData">优先级</th>
                                <th data-options="field:'state',width:100,align:'center',dictry:'ASC001',formatter:formatData">任务状态</th>
                                <th data-options="field:'crtDate',width:100,align:'center'">创建日期</th>
                            </tr>
                            </thead>--%>
                        </table>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div id="layout_task_config" data-options="region:'east',title:'任务配置管理',hideCollapsedContent:false,collapsed:false" style="width:50%;">
        <div id="tbs_ascTaskConfig" class="easyui-tabs" data-options="fit:true">
            <div title="任务详情" data-options="closable:false" style="">
                <table width="100%">
                    <tr>
                        <td align="right" width="30%"><span style="font-size: 12px">任务编号：</span></td>
                        <td align="left" width="70%">
                            <span id="taskId_detail" style="font-size: 12px"></span>
                            <%--<input type="text" class="easyui-textbox easyui-validatebox" id="taskId_detail" name="taskId" value="" style="width: 200px;height: 20px" maxlength="32" data-options="required:true,validType:'uniqueTaskId'">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">任务名称：</span></td>
                        <td align="left">
                            <span id="taskName_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="taskName_detail" name="taskName" value="" style="width: 200px;height: 20px" data-options="required:true,validType:''">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">任务类型：</span></td>
                        <td align="left">
                            <span id="taskType_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="taskType_detail" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                            <input class="easyui-textbox" type="hidden" id="taskType_hidden" name="taskType" value=""/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">任务种类：</span></td>
                        <td align="left">
                            <span id="taskCategory_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="taskCategory_detail" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                            <input class="easyui-textbox" type="hidden" id="taskCategory_hidden" name="taskCategory"  value=""/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">执行频率：</span></td>
                        <td align="left">
                            <span id="frequency_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="frequency_detail" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                            <input class="easyui-textbox" type="hidden" id="frequency_hidden" name="frequency"  value=""/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">任务状态：</span></td>
                        <td align="left">
                            <span id="state_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="state_detail" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                            <input class="easyui-textbox" type="hidden" id="state_hidden" name="state"  value=""/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">待处理日期：</span></td>
                        <td align="left">
                            <span id="nextDate_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-datebox easyui-validatebox" id="nextDate_detail" name="nextDate" value="" style="width: 200px;height: 20px;" data-options="required:true,validType:'checkDate'">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">开始时间：</span></td>
                        <td align="left">
                            <span id="startTime_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="startTime_detail" name="startTime" value="" style="width: 200px;height: 20px;" data-options="required:true,validType:'limitTime'">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">结束时间：</span></td>
                        <td align="left">
                            <span id="endTime_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="endTime_detail" name="endTime" value="" style="width: 200px;height: 20px;" data-options="required:true,validType:['limitTime','checkTime']">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">时间段内是否允许执行标志：</span></td>
                        <td align="left">
                            <span id="timeFlag_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-combobox easyui-validatebox" id="timeFlag_detail" name="timeFlag" value="" style="width: 200px;height: 20px" data-options="required:true,editable:false,panelHeight:'auto'">--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">优先级：</span></td>
                        <td align="left">
                            <span id="priority_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="priority_detail" value="" style="width: 200px;height: 20px" data-options="required:true" buttonIcon="icon-search">
                            <input class="easyui-textbox" type="hidden" id="priority_hidden" name="priority"  value=""/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">所属任务组：</span></td>
                        <td align="left">
                            <span id="groupId_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="groupId_detail" name="groupId" value="" style="width: 200px;height: 20px;" data-options="required:true" buttonIcon="icon-search">--%>
                            <%--<input class="easyui-textbox" type="hidden" id="groupId_hidden" name="groupId"  value=""/>--%>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="top"><span style="font-size: 12px">任务描述：</span></td>
                        <td align="left">
                            <span id="describ_detail" style="font-size: 12px"></span>
                            <%--<input class="easyui-textbox easyui-validatebox" id="describ_insert" name="describ" style="width:400px;height: 100px;" multiline=true data-options="required:true" >--%>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="任务处理配置" data-options="closable:false" style="">
                <table width="100%">
                    <tr>
                        <td>
                            <div><!--数据网格-->
                                <div id="dg_jobcfg_tools" align="right">
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsertAscTaskJobCfgPage('win_ascTask','处理配置新增','<%=basePath%>AscTask/toJobCfgInsert','450','350')">新增</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" plain=true onclick="toModifyAscTaskJobCfgPage('win_ascTask','处理配置更新','<%=basePath%>AscTask/toJobCfgUpdate','450','350')">修改</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDeleteAscTaskJobCfg('<%=basePath%>AscTask/deleteJobCfg')">删除</a>
                                </div>
                                <table id="dg_jobCfg" class="easyui-datagrid" style="width:100%;"></table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="任务依赖配置" data-options="closable:false" style="">
                <table width="100%">
                    <tr>
                        <td>
                            <div><!--数据网格-->
                                <div id="dg_dependcfg_tools" align="right">
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsertAscTaskDependCfgPage('win_ascTask','依赖配置新增','<%=basePath%>AscTask/toDependCfgInsert','400','350')">新增</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDeleteAscTaskDependCfg('<%=basePath%>AscTask/deleteDependCfg')">删除</a>
                                </div>
                                <table id="dg_dependCfg" class="easyui-datagrid" style="width:100%;"></table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="任务触发配置" data-options="closable:false" style="">
                <table width="100%">
                    <tr>
                        <td>
                            <div><!--数据网格-->
                                <div id="dg_trigger_tool" align="right">
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true onclick="toInsertAscTaskTriggerCfgPage('win_ascTask','触发配置新增','<%=basePath%>AscTask/toTriggerCfgInsert','400','350')">新增</a>
                                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true onclick="toDeleteAscTaskTriggerCfg('<%=basePath%>AscTask/deleteAscTriggerCfg' )">删除</a>
                                </div>
                                <table id="dg_triggerCfg" class="easyui-datagrid" style="width:100%;"></table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div title="任务处理日志" data-options="closable:false" style="">
                <table width="100%">
                    <tr>
                        <td>
                            <div><!--数据网格-->
                                <div id="dg_joblog_tool" style="padding:3px">
                                    <table width="100%" style="text-align: center">
                                        <tr>
                                            <td align="right"><span style="font-size: 12px;">处理编号：</span></td>
                                            <td align="left"><input class="easyui-textbox" id="jobId_se" style="height:20px;width:100px;"></td>
                                            <td align="right"><span style="font-size: 12px;">运行结果：</span></td>
                                            <td align="left"><input class="easyui-combobox" id="ret_se" style="height:20px;width:100px;"></td>
                                        </tr>
                                        <tr>
                                            <td align="right"><span style="font-size: 12px;">批处理日期（起）：</span></td>
                                            <td align="left"><input class="easyui-datebox" id="nextDate_start" style="height:20px;width:100px;"></td>
                                            <td align="right"><span style="font-size: 12px;">批处理日期（止）：</span></td>
                                            <td align="left"><input class="easyui-datebox" id="nextDate_end" style="height:20px;width:100px;"></td>
                                        </tr>
                                        <tr>
                                            <td align="right" colspan="8">
                                                <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain=true onclick="doJobLogSearch()">查询</a>
                                                <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="resetJobLogSearch()">重置</a>
                                                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain=true onclick="toDeleteAscTaskJobLog('<%=basePath%>AscTask/deleteAscJobLog')">删除</a>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <table id="dg_jobLog" class="easyui-datagrid" style="width:100%;"></table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <!--用于标记选择的任务id-->
    <input class="easyui-textbox" type="hidden" id="taskId_hidden" value="">
<!--动态生成模态框-->
<div id="win_ascTask"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initAscTaskCtlListPage('<%=basePath%>');
        //-功能按钮权限配置
        <%for (SysFunc button : buttonList){%>
        <%if ("T010101".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_add','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_add').bind('click',function () {
            toInsertAscTaskPage('win_ascTask','任务新增','<%=basePath%>AscTask/toAscTaskInsert','800','430');
        });
        <%}else if ("T010102".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_update','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_update').bind('click',function () {
            toModifyAscTaskPage('win_ascTask','任务更新','<%=basePath%>AscTask/toAscTaskUpdate','800','430');
        });
        <%}else if ("T010103".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_remove','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_remove').bind('click',function () {
            toDeleteTask('<%=basePath%>');
        });
        <%--<%}else if ("T010104".equals(button.getFuncId())){%>
        createLinkedButtonBySelf('btn_taskconfig','<%=button.getFuncNm()%>','<%=button.getIconUrl()%>');
        $('#btn_taskconfig').bind('click',function () {
            openTabs();
        });--%>
        <%}}%>
    })
</script>
</body>
</html>
