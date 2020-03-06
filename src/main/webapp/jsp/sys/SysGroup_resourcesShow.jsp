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
    String groupId = (String) request.getAttribute("groupId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="padding:5px;background:#eee;">
<div title="用户组资源" data-options="iconCls:'icon-more',closable:false" style="overflow:auto;">

    <table width="100%">
    <tr>
        <td align="center">
            <div id="dg_group_resources_tool" style="">
                <table width="100%" style="text-align: center">
                    <tr>
                        <td align="right" style="font-size: small;font-family: Microsoft Yahei">资源类型：</td>
                        <td align="left"><input class="easyui-combobox" id="recoursTyp_se" name="recoursTyp" data-options="panelHeight:'auto'" style="height:20px;width:100px;border:1px solid #ccc;editable:false;panelHeight:'auto'"></td>
                        <td align="right" style="font-size: small;font-family: Microsoft Yahei">资源名称：</td>
                        <td align="left"><input class="easyui-textbox" id="recoursNm_se" name="recoursNm" style="height:20px;width:100px;border:1px solid #ccc"></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" plain="true" size="small" onclick="doSearchSysGroupResource()">查询</a>
                            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-set'" plain="true" size="small" onclick="clearSearchSysGroupResource()">重置</a>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <!--数据网格-->
            <table id="dg_group_resources" class="easyui-datagrid" style="width:570px;height:380px;">
                <thead>
                <tr>
                    <th data-options="field:'recoursId',width:150,align:'center'">资源ID</th>
                    <th data-options="field:'recoursNm',width:280,align:'center'">资源名称</th>
                    <th data-options="field:'recoursTyp',width:150,align:'center',dictry:'SYS11',formatter:formatData">资源类型</th>
                </tr>
                </thead>
            </table>
</td>
</tr>
    </table>
</div>
<script type="text/javascript">
    groupId= "<%=groupId%>";
    //初始化页面
    $(function () {
        $('#dg_group_resources').datagrid({
            url:'/usrp/sysGroup/listRecoursByGroupId?groupId='+groupId,
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
            }
        })
        initComboboxSelf('recoursTyp_se','SYS11','<%=basePath%>');
    })
    function doSearchSysGroupResource(){
        $('#dg_group_resources').datagrid('load',{
            recoursTyp: $('#recoursTyp_se').val(),
            recoursNm: $('#recoursNm_se').val(),
        });
    }
    function clearSearchSysGroupResource(){
        $('#recoursTyp_se').combobox('reset');
        $('#recoursNm_se').textbox('reset');
    }

</script>
</body>
</html>
