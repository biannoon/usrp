<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/9/18
  Time: 9:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>映射接口配置</title>
    <style>
       .combobox-item-selected{
           background-color: rgb(87, 99, 255);
           color: #000000;
       }
       #tablSl input{
           border-radius: 100px;
       }
    </style>
    <script type="text/javascript">
        var baseUrl = '<%=path%>';
        var treeNode=null;
    </script>
</head>
<body class="easyui-layout">
<div data-options="region:'west',title:'业务类型',split:true" style="width:16%;">
    <ul id="bussType"></ul>
</div>
</body>
</html>
