<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body class="easyui_layout">
    <div id="panel" class="easyui-panel" style="padding:1px;background:#eee9dc;">
        <table style="width: 100%;height: 97% ; border: #00ee00" >
            <tr align="center">
                <td align="right" colspan="3">
                    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true icon="icon-save"
                       onclick="save()" text="保存" ></a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true icon="icon-cancel"
                       onclick="closeWin('win_sys_user')" text="取消" ></a>
                </td>
            </tr>
            <tr>
                <td style="width: 45%" align="center">
                    <ul id="select1" class="easyui-datalist" title="待选择区域" style="width:200px;height:380px"></ul>
                </td>
                <td style="width: 10%" align="center">
                    <a href="#" class="easyui-linkbutton" id="right" style="width: 50px">></a><br><br><br>
                    <a href="#" class="easyui-linkbutton" id="left" style="width: 50px"><</a><br><br><br>
                    <a href="#" class="easyui-linkbutton" id="allRight" style="width: 50px">>></a><br><br><br>
                    <a href="#" class="easyui-linkbutton" id="allLeft" style="width: 50px"><<</a>
                </td>
                <td style="width: 45%" align="center">
                    <ul id="select2" class="easyui-datalist" title="已选择区域" style="width:200px;height:380px"></ul>
                </td>
            </tr>
            <input type="hidden" id="uid" name="uid" value="<%=request.getParameter("userId")%>">
            <input type="hidden" id="flag" name="flag" value="<%=request.getParameter("flag")%>">
        </table>
    </div>
    <script type="text/javascript">
        $(function(){
            $('#select1').datalist({
                lines : true,
                singleSelect : false
            });
            $('#select2').datalist({
                lines : true,
                singleSelect : false
            });
            $("#right").click(function (){
                var rows = $('#select1').datalist("getSelections");
                var rowArray = new Array();
                $(rows).each(function(i){
                    var value = rows[i].value;
                    var text = rows[i].text;
                    var row = {
                        value:value,
                        text:text
                    };
                    rowArray.push(row);
                    var rowIndex = $("#select1").datalist("getRowIndex", rows[i]);
                    $("#select1").datalist("deleteRow",rowIndex);
                });
                rowArray = rowArray.concat($("#select2").datalist("getRows"));//合并

                var total = { "total":rowArray.length,rows:rowArray };
                $("#select2").datalist("loadData",rowArray);
            });
            $("#left").click(function (){
                var rows = $("#select2").datalist("getSelections");
                var rowArray = new Array();
                $(rows).each(function(i){
                    var value = rows[i].value;
                    var text = rows[i].text;
                    var row = {
                        value:value,
                        text:text
                    };
                    rowArray.push(row);
                    var rowIndex = $("#select2").datalist("getRowIndex", rows[i]);
                    $("#select2").datalist("deleteRow",rowIndex);
                });
                rowArray = rowArray.concat($("#select1").datalist("getRows"));//合并
                var total = { "total":rowArray.length,rows:rowArray };
                $("#select1").datalist("loadData",rowArray);
            });
            $("#allRight").click(function (){
                var data = $("#select1").datalist("getData");
                var rows = data.rows;
                var rowsLength = rows.length;
                for (var i = 0; i < rowsLength; i++){
                    var value = rows[i].value;
                    var text = rows[i].text;
                    var row = {
                        value:value,
                        text:text
                    };
                    //添加
                    $("#select2").datalist("appendRow",row);
                }
                //删除
                var rows = $("#select1").datalist('getRows');
                for(var i=rows.length-1;i>=0;i--){
                    $("#select1").datalist("deleteRow",i);
                }
                //移动完后重新加载dl2,否则显示不正常

                $("#select2").datalist("resize");

            });
            $("#allLeft").click(function (){
                var data = $("#select2").datalist("getData");
                var rows = data.rows;
                var rowsLength = rows.length;
                for (var i = 0; i < rowsLength; i++){
                    var value = rows[i].value;
                    var text = rows[i].text;
                    var row = {
                        value:value,
                        text:text
                    };
                    $("#select1").datalist("appendRow",row);
                }
                var rows = $("#select2").datalist('getRows');
                for(var i=rows.length-1;i>=0;i--){
                    $("#select2").datalist("deleteRow",i);
                }
                var rows1 = $("#select2").datalist('getRows');
                $("#select1").datalist("resize");
            });
            var flag = $('#flag').val();
            var uid = $('#uid').val();
            if(flag == 'role'){
                $('#select1').datalist({
                    url : basePath + 'sysRole/getRoleListByLoginUser?userId=' + uid
                });
                $('#select2').datalist({
                    url : basePath + 'sysRole/getRoleListByUserId?userId=' + uid
                });
            }else if(flag == 'group'){
                $('#select1').datalist({
                    url : basePath + 'sysGroup/getGroupListByLoginUser?userId='+uid
                });
                $('#select2').datalist({
                    url :  basePath + 'sysGroup/getGroupListByUserId?userId=' + uid
                });
            }
        });

        function save(){
            var flag = $('#flag').val();
            var uid = $('#uid').val();
            var data = $("#select2").datalist("getData");
            var rows = data.rows;
            var param = '';
            for (var i = 0; i < rows.length; i++){
                param += rows[i].value + ",";
            }
            if(flag == 'role'){
                ajaxSubmit("post",basePath + 'sysUser/distributeRole',"userId="+uid+"&roles="+param,"json");
            }else if(flag == 'group'){
                ajaxSubmit("post",basePath + 'sysUser/distributeGroup',"userId="+uid+"&groups="+param,"json");
            }
        }

        //关闭弹出窗口
        function closeWin(winId) {
            $("#"+winId).window('close');
        }
    </script>
</body>
</html>