<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title></title>
    </head>
    <body class="easyui_layout">
        <form id="oa_form" method="post">
            <div style="padding:2px 5px;"><!-- 查询条件容器 -->
                OA编号：&nbsp;&nbsp;<input class="easyui-textbox" name="userId" style="width:150px">
                用户姓名：&nbsp;&nbsp;<input class="easyui-textbox" name="userNm" style="width:150px">
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain=true
                   onclick="Search('oadataGrid','oa_form','sysUser/getOemEmplyByPage')">查询</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain=true
                   onclick="choseOem()">选定</a>
            </div>
        </form>
        <!-- 数据列表展示区域-->
        <table id="oadataGrid"></table>
        <!-- 弹出窗口-->
        <div id="newWin"></div>
        <script type="text/javascript">
            $(function(){
                var colemsAll = [
                    {field:"userId",title:"OA编号",width:30,align:'center'},
                    {field:"userNm",title:"用户名称",width:30,align:'center'},
                    {field:"genderCd",title:"性别",width:30,align:'center',dictry:'CD0006',formatter:formatData},
                    {field:"brthdy",title:"生日",width:50,align:'center'},
                    {field:"telNo",title:"联系电话",width:50,align:'center'},
                    {field:"addr",title:"地址",width:100,align:'center'}
                ];
                createDataGrid('oadataGrid','员工池信息列表', colemsAll);
                $('#oadataGrid').datagrid({
                    url : basePath + 'sysUser/getOemEmplyByPage'
                });
            });


            function choseOem(){
                var selRows = $('#oadataGrid').datagrid("getChecked");
                if(selRows.length != 1){
                    $.messager.alert('提示信息', "只能选择一条个员工进行添加！");
                }else{
                    var row = $('#oadataGrid').datagrid("getSelected");
                  //  var url = basePath + 'sysUser/input?';
                   // var param = 'userId='+row.userId+'&userNm='+row.userNm+'&genderCd='+row.genderCd
                   //     +'&brthdy='+row.brthdy+'&telNo='+row.telNo+'&addr='+row.addr;
                   // openWindow('newWin', "用户信息新增", url + param);
                    $('#userId_insert').textbox('setValue', row.userId);
                    $('#userNm_insert').textbox('setValue', row.userNm);
                    $('#genderCd_insert').combobox('setValue', row.genderCd);
                    $('#brthdy_insert').datebox('setValue', row.brthdy);
                    $('#telNo_insert').textbox('setValue', row.telNo);
                    $('#addr_insert').textbox('setValue', row.addr);
                    $('#win_sys_user_insert_emp').window('close');
                }
            }
        </script>
    </body>
</html>