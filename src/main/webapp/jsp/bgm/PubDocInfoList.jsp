<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/19
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
</head>
<body style="padding:5px;background:#eee;">
<div id="dg_pub_doc_info_tool">
    <table width="100%">
        <tr>
            <td align="right"><span style="font-size: 12px">文档名称：</span></td>
            <td align="left">
                <input class="easyui-textbox"
                       id="doc_nm_se"
                       style="height:20px;width:150px;border:1px solid #ccc">
            </td>
            <td align="right"><span style="font-size: 12px">子系统：</span></td>
            <td align="left">
                <input class="easyui-textbox"
                       id="subsystemId_se"
                       style="height:20px;width:150px;border:1px solid #ccc"
                       buttonIcon="icon-search">
                <input class="easyui-textbox" type="hidden" id="subsystemId_se_hid">
            </td>
            <td align="left">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain=true
                   onclick="doSearch()">查询</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain=true
                   onclick="clearForm()">重置</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true
                   onclick="uploadDoc('文档上传','pubdocinfo/toPubDocInfoInsert','400','400')">上传文档</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-doc'" plain=true
                   onclick="downloadDoc();">文件下载</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain=true
                   onclick="toDelete('pubdocinfo/deletedoc')">文件删除</a>
            </td>
        </tr>
    </table>
</div>
<div>
    <table id="dg_pub_doc_info" style="width: 100%"></table>
</div>
<!--动态生成模态框-->
<div id="win_pub_doc_info"></div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        initPubDocInfo_DG('dg_pub_doc_info');
        initSubsystemComponent('win_pub_doc_info','subsystemId_se','subsystemId_se_hid');
    })

    /**
     * 描述：初始化文档列表
     * @param id
     */
    function initPubDocInfo_DG(id){
        $('#'+id).datagrid({
            url:basePath + 'pubdocinfo/getList',
            fitColumns:true,
            singleSelect:true,
            pagination:true,
            pageNumber:'1',
            pageSize:'10',
            pagePosition:'top',
            toolbar:'#dg_pub_doc_info_tool',
            frozenColumns:[[
                {field:'ck',title:'',checkbox:true},
                {field:'docId',title:'文档ID',width:100,align:'center',hidden:true},
                {field:'docNm',title:'文档名称',width:200,align:'center'},
                {field:'subsystemId',title:'所属系统',width:200,align:'center'},
                {field:'uploader',title:'上传人',width:100,align:'center'},
                {field:'uploadDt',title:'上传时间',width:100,align:'center'}
            ]],
            columns:[[
                {field:'docComnt',title:'文档说明',width:300,align:'center'},
                {field:'docPath',title:'文档路径',width:300,align:'center'}
            ]],
            onLoadSuccess: function (data) {
                $(".datagrid-header-check").html("");//去除表头的复选框（表头复选框有全选的作用）
                $('.datagrid-cell').css('font-size', '12px');//更改的是datagrid中的数据字体大小
                $('.datagrid-header .datagrid-cell span ').css('font-size','12px');//datagrid中的列名称
            }
        });
    }

    /**
     * 描述：查询
     */
    function doSearch() {
        $('#dg_pub_doc_info').datagrid('load',{
            docNm: $('#doc_nm_se').val(),
            subsystemId:$('#subsystemId_se_hid').val()
        });
    }

    /**
     * 描述：重置查询条件
     */
    function clearForm() {
        $('#doc_nm_se').textbox('reset');
        $('#subsystemId_se_hid').textbox('reset');
        $('#subsystemId_se').textbox('reset');
    }

    /**
     * 描述：打开上传文件窗口
     * @param title
     * @param url
     * @param width
     * @param height
     */
    function uploadDoc(title,url,width,height){
        url = basePath + url;
        openWindowSelf("win_pub_doc_info",title,width,height,url);
    }

    /**
     * 描述：下载文档
     */
    function downloadDoc() {
        var row = $('#dg_pub_doc_info').datagrid('getSelected');//获取选中行
        if (!row) {
            $.messager.alert("提示", "请选择您想要下载的文件！");
        }else{
            $.messager.confirm("提示","确认下载",function (r) {
                if (r){
                    $.ajax({
                        url:basePath + 'pubdocinfo/downloadoc',
                        type:'POST',
                        dataType:'JSON',
                        data:{'docNm':row.docNm,'docId':row.docId},
                        success:function (data) {
                            var data = JSON.stringify(data);
                            var data = eval('(' + data + ')');
                            if (data.success){
                                $.messager.alert('提示',data.message);
                            }else{
                                $.messager.alert('提示',data.message);
                            }
                        },
                        error:function () {
                            $.messager.alert('警告','请求失败');
                        }
                    })
                }
            })
        }
    }

    /**
     * 描述：删除文档
     * @param url
     */
    function toDelete(url){
        var row = $('#dg_pub_doc_info').datagrid('getSelected');
        if (row == null){
            $.messager.alert("提示","请选择需要删除的文档");
        }else{
            $.messager.confirm("提示","确认删除",function (r) {
                if (r){
                    var docId = row.docId;
                    var docNm = row.docNm;
                    $.ajax({
                        url:basePath + url,
                        type:'post',
                        dataType:'json',
                        data:{'docId':docId,'docNm':docNm},
                        success:function(data){
                            var data = JSON.stringify(data);
                            var data = eval('(' + data + ')');
                            if (data.success){
                                //-刷新角色列表
                                refreshDataGridBySelf("dg_pub_doc_info");
                                $.messager.alert("提示",data.message);
                            }else{
                                $.messager.alert("提示",data.message)
                            }
                        },
                        error:function(){
                            $.messager.alert("警告","请求失败");
                        }
                    })
                }
            })
        }
    }
</script>
</body>
</html>
