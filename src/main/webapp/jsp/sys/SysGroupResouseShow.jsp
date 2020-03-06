<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
</head>
    <body>
    <div class="easyui-layout" style="width:100%; height:100%;">
        <div data-options="region:'west',title:'资源类型',collapsible: false" style="width:150px;">
            <input type="radio" name="rad" value="orgRad" checked="true"><label>机构</label><br>
            <input type="radio" name="rad" value="sysRad"><label>子系统</label>
        </div>
        <div data-options="region:'center',border: false">
            <div id="orgSouse">
                <div style="padding:2px 5px;">
                    <a href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-add'" onclick="toExpandAll()">展开</a>&nbsp;&nbsp;
                    <a href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-remove'" onclick="toCollapseAll()">折叠</a>&nbsp;&nbsp;
                    <a href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-save'" onclick="savegroupOrg()">保存</a>
                </div>
                <div style="padding:2px 5px;">
                    <ul id="orgTree"></ul>
                </div>
            </div>
            <div id="sysSouse" style="display: none">
                <div style="padding:2px 5px;">
                    <a href="#" class="easyui-linkbutton"
                       data-options="iconCls:'icon-save'" onclick="savegroupSubsys()">保存</a>
                </div>
                <table name="showInfo" align="center" width="100%"
                       cellpadding="0" cellspacing="0" border="1">
                    <tr style="background-color: #95B8E7">
                        <th align="center">选择</th>
                        <th align="center">子系统编号</th>
                        <th align="center">子系统名称</th>
                        <th align="center">子系统说明</th>
                    </tr>
                    <tbody id="subSysTab"></tbody>
                </table>
            </div>
        </div>
    </div>
    <input type="hidden" name="groupId" value="<%=request.getParameter("groupId")%>">
        <script type="text/javascript">
            $(function () {
                $("input[name='rad']").click(function(){
                    var type = $(this).val();
                    $(this).prop("checked", true);
                    if (type == 'orgRad') {
                        $('#orgSouse').prop("style","display:block");
                        $('#sysSouse').prop("style","display:none");
                    } else if (type == 'sysRad') {
                        $('#orgSouse').prop("style","display:none");
                        $('#sysSouse').prop("style","display:block");
                    }
                });
                //获取第一层目录
                $.post(basePath + "organization/findFirstTreeNode", function(data){
                    $("#orgTree").tree({
                        checkbox : true,
                        data : data,
                        onBeforeExpand:function(node){
                            $("#orgTree").tree('options').url= basePath +
                                "organization/findChildrenTreeNode?parentId="+node.id;
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
                getSubSysList();
            })
            function getSubSysList(){
                var groupId = $("input[name='groupId']").val();
                var ajaxObj = {
                    url : basePath + 'sysSubsystem/getSysSubsystemList?groupId='+ groupId
                }
                customAjaxSubmit(ajaxObj, function(data){
                    var dataObj = eval("(" + data.data + ")");
                    var str = "";
                    for(var i=0; i<dataObj.length; i++){
                        str += '<tr><td align=\"center\"><input class=\"easyui-checkbox\" ';
                        str += 'name=\"schk\" value='+dataObj[i].subsystemId+','+dataObj[i].subsystemNm;
                        if(dataObj[i].check){
                            str += ' checked=\"checked\" /></td>'
                        }else{
                            str += ' /></td>'
                        }
                        str += '<td align=\"center\">'+dataObj[i].subsystemId+'</td>';
                        str += '<td align=\"center\">'+dataObj[i].subsystemNm+'</td>';
                        str += '<td align=\"center\">'+dataObj[i].subsystemComnt+'</td></tr>';
                    }
                    $('#subSysTab').html(str);
                    $.parser.parse();
                }, function(){});
            }
            //树展开
            function toExpandAll() {
                $('#orgTree').tree('expandAll');
            }
            //树折叠
            function toCollapseAll(){
                $('#orgTree').tree('collapseAll');
            }
            function savegroupOrg(){
                var groupId = $("input[name='groupId']").val();
                var nodes = $('#orgTree').tree('getChecked');
                var orgs = "";
                for(var i=0; i<nodes.length; i++){
                    orgs += nodes[i].id + "," + nodes[i].text + "@";
                }
                var ajaxObj = {
                    url : basePath + 'sysGroup/distributeResouse',
                    data : {groupId:groupId,orgs:orgs}
                }
                customAjaxSubmit(ajaxObj,function(data){
                    $.messager.alert('提示信息',data.message,'info');
                },function(){});
            }
            function savegroupSubsys(){
                var groupId = $("input[name='groupId']").val();
                var systems = "";
                $("input[name='schk']:checked").each(function(){
                    systems += $(this).val() + "@";
                });
                var ajaxObj = {
                    url : basePath + 'sysGroup/distributeResouse',
                    data : {groupId:groupId,systems:systems}
                }
                customAjaxSubmit(ajaxObj,function(data){
                    $.messager.alert('提示信息',data.message,'info');
                },function(){});
            }
        </script>
    </body>
</html>
