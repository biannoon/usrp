<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/15
  Time: 8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<%
    String selectType = (String) request.getAttribute("selectType");
    String roleLimit = (String) request.getAttribute("roleLimit");
    String haveRemoved = (String) request.getAttribute("haveRemoved");
    String orgController = (String) request.getAttribute("orgController");
    String winId = (String) request.getAttribute("winId");
    String fieldId = (String) request.getAttribute("fieldId");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body style="background:#eee;">
<table width="100%">
    <tr>
        <td>
            <div id="tb_search">
                <table width="100%" style="text-align: center">
                    <tr>
                        <td align="right">用户所属机构：</td>
                        <td align="left"><input class="easyui-textbox" id="blngtoOrgNo_search" style="height:20px;width:150px;border:1px solid #ccc"></td>
                        <td align="right">OA编号：</td>
                        <td align="left"><input class="easyui-textbox" id="userId_search" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    </tr>
                    <tr>
                        <td align="right">用户名称：</td>
                        <td align="left"><input class="easyui-textbox" id="userNm_search" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                            <div>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" onclick="resetValues()">重置</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="assignVal('<%=winId%>','<%=fieldId%>')">确定</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow('<%=winId%>')">关闭</a>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <!--数据网格-->
            <table id="dg_sysUser" class="easyui-datagrid" style="">
                <thead>
                <tr>
                    <th data-options="field:'cb',checkbox:true"></th>
                    <th data-options="field:'userId',width:100,align:'center'">OA编号</th>
                    <th data-options="field:'userNm',width:150,align:'center'">用户名称</th>
                    <th data-options="field:'blngtoOrgNo',width:150,align:'center'">用户所属机构</th>
                    <th data-options="field:'stus',width:150,align:'center'">用户状态</th>
                </tr>
                </thead>
            </table>
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
        //初始化列表
        $('#dg_sysUser').datagrid({
            url:'<%=basePath%>common/getSysUserByCommon?roleLimit=<%=roleLimit%>&haveRemoved=<%=haveRemoved%>&orgController=<%=orgController%>',
            /*fitColumns:true,*/
            <%if("0".equals(selectType)){%>
                singleSelect:true,
            <%}%>
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            toolbar:'#tb_search',
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
                //回显勾选指定的值（目前用于单选）
                var rows = $(this).datagrid('getRows');
                $.each(rows,function (index,row) {
                    if (value == row.userId){
                        $('#dg_sysUser').datagrid('checkRow',index);
                    }
                })
            }
        })
    })

    //子页面给父页面赋值操作
    function assignVal(winId,fieldId){
        var rows = $('#dg_sysUser').datagrid('getChecked');
        if (rows == null){
            $.messager.alert("提示","请选择记录");
        }else {
            var fieldId_ = fieldId.substring(0, fieldId.indexOf("$"));
            var _fieldId = fieldId.substring(fieldId.indexOf("$") + 1);
            $.each(rows, function (index, row) {//存在一个缺陷，只能用于单选
                if (fieldId_ != '' && fieldId_ != null) {
                    $('#' + fieldId_).textbox('setValue', row.userNm);
                }
                if (_fieldId != '' && _fieldId != null) {
                    $('#' + _fieldId).textbox('setValue', row.userId);
                }
            })
            $('#' + winId).window('close');
        }
    }

    //条件查询
    function doSearch() {
        $('#dg_sysUser').datagrid('load',{
            userId: $('#userId_search').val(),
            userNm: $('#userNm_search').val(),
            blngtoOrgNo: $('#blngtoOrgNo_search').val()
        })
    }

    //条件重置
    function resetValues() {
        $('#blngtoOrgNo_search').textbox('reset');
        $('#userId_search').textbox('reset');
        $('#userNm_search').textbox('reset');
    }

    //关闭窗口
    function closeWindow(winId) {
        $('#'+winId).window('close');
    }
</script>
</body>
</html>
