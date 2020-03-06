<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <title>统一监管报送平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache must-revalidate">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/login.css"/>
    <script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type='text/javascript' src="<%=path%>/static/include/layer/layer.js"></script>
</head>
<body>
<h2>服务异常，请稍后再试。</h2>
<div >
    <h2>发生时间：</h2>${errorInfo.time}
    <h2>访问地址：</h2>${errorInfo.url}
    <h2>问题类型：</h2>${errorInfo.error}
    <h2>通信状态：</h2>${errorInfo.statusCode}
    <h2>通信状态：</h2>${errorInfo.reasonPhrase}
    <h2>堆栈信息：</h2>${errorInfo.stackTrace}
</div>
</body>
</html>