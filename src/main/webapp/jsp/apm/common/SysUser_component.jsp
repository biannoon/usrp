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
    String selectType = ((String) request.getAttribute("selectType")).equals("0")?"true":"false";
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
                        <td align="right"><span style="font-size: 12px">用户所属机构：</span></td>
                        <td align="left"><input class="easyui-textbox" id="blngtoOrgNo_search" style="height:20px;width:150px;border:1px solid #ccc"></td>
                        <td align="right"><span style="font-size: 12px">OA编号：</span></td>
                        <td align="left"><input class="easyui-textbox" id="userId_search" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    </tr>
                    <tr>
                        <td align="right"><span style="font-size: 12px">用户名称：</span></td>
                        <td align="left"><input class="easyui-textbox" id="userNm_search" style="height:20px;width:150px;border:1px solid #ccc"></td>
                    </tr>
                    <tr>
                        <td align="right" colspan="4">
                            <div>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain=true onclick="doComponentSearch_user('dg_sysUser')">查询</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-set" plain=true onclick="resetComponentValues_user()">重置</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true onclick="assignVal_user('dg_sysUser','<%=winId%>','<%=fieldId%>')">确定</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain=true onclick="closeComponentWindow('<%=winId%>')">关闭</a>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div><table id="dg_sysUser" style=""></table></div>
        </td>
    </tr>
</table>
<script type="text/javascript">

    $(function () {
        //获取文本框的值
        var fieldId = '<%=fieldId%>';
        var _fieldId = fieldId.substring(fieldId.indexOf("$")+1);
        var value = $('#'+_fieldId).val();
        var url = '<%=basePath%>common/getSysUserByCommon?roleLimit=<%=roleLimit%>&haveRemoved=<%=haveRemoved%>&orgController=<%=orgController%>';
        initSysUserInfoPageFromComponent('dg_sysUser',url,'<%=selectType%>',value);
    })

    /**
     * 描述：初始化系统用户选择组件页面
     * @param id
     * @param url
     * @param selectType    单选/多选
     * @param echoValue     回显值
     */
    function initSysUserInfoPageFromComponent(id,url,selectType,echoValue) {
        $('#'+id).datagrid({
            url:url,
            singleSelect:selectType,
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pagePosition: 'top',
            toolbar:'#tb_search',
            columns:[[
                {field:'cb',title:'',width:100,checkbox:true},
                {field:'userId',title:'OA编号',width:100,align:'center'},
                {field:'userNm',title:'用户名称',width:100,align:'center'},
                {field:'blngtoOrgNo',title:'用户所属机构',width:200,align:'center'},
                {field:'stus',title:'用户状态',width:100,align:'center'}
            ]],
            onLoadSuccess: function (data) {
                //去除表头的复选框（表头复选框有全选的作用）
                $(".datagrid-header-check").html("");
                //回显勾选指定的值（目前用于单选）
                var rows = $(this).datagrid('getRows');
                $.each(rows,function (index,row) {
                    if (echoValue == row.userId){
                        $('#dg_sysUser').datagrid('checkRow',index);
                    }
                })
            }
        })
    }

    /**
     * 描述：系统用户组件窗口赋值父页面
     * @param id        用户数据列表ID
     * @param winId     窗口ID
     * @param fieldId   属性ID
     */
    function assignVal_user(id,winId,fieldId){
        var rows = $('#'+id).datagrid('getChecked');
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
    function doComponentSearch_user(tabId) {
        $('#'+tabId).datagrid('load',{
            userId: $('#userId_search').val(),
            userNm: $('#userNm_search').val(),
            blngtoOrgNo: $('#blngtoOrgNo_search').val()
        })
    }

    //条件重置
    function resetComponentValues_user() {
        $('#blngtoOrgNo_search').textbox('reset');
        $('#userId_search').textbox('reset');
        $('#userNm_search').textbox('reset');
    }

    function closeComponentWindow(winId){
        $('#'+winId).window('close');
    }


</script>
</body>
</html>
