<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/12
  Time: 8:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/demo/demo.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript">

        /*function openURL(title, url) {
            $("#pageContent").panel({
                title: title,
                content  : '<iframe frameborder="0" style="width:100%;height:100%" src="'+url+'"></iframe>',
                //href: url
            });
        }*/
        
        function newTabs(title,url) {
            if ($('#pageContent').tabs('exists', title)){
                $('#pageContent').tabs('select', title);
            } else {
                var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
                $('#pageContent').tabs('add',{
                    title:title,
                    content:content,
                    closable:true
                });
            }
        }

       //$('#pageContent').panel('refresh',url)

    </script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'North Title',split:true" style="height:100px;">
        <div id="pageNorth" class="easyui-panel" style="width: 100%;background-color:#2d8bef;" border="false">
            <h2>统一监管报送系统</h2>
        </div>
    </div>
    <div data-options="region:'west',title:'West',split:true" style="width:150px;">
        <div id="pageWest" class="easyui-panel" style="width: 100%;" border="false">
            <li><span><a href="javascript:newTabs('系统菜单管理', 'listSysFunc')">系统菜单管理</a></span></li>
            <li><span><a href="javascript:newTabs('任务调度管理','toAscTaskList')">任务调度管理</a></span></li>
        </div>
    </div>
    <div id="pageContent" class="easyui-tabs" data-options="region:'center'" style="padding:5px;background:#eee;" >
    </div>
    <div region="south" style="height: 30px; background-color: #2d8bef; text-align:center; line-height: 30px; color:#fff;" border="false">
        版权所有 © XX XXX有限公司
    </div>
</body>
</html>
