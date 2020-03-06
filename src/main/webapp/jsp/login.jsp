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
	<link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
	<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/login.css"/>
	<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
</head>
<body>
	<div class="login-div">
		<form action="userLogin" method="post" name="LoginForm">
			<div><font>欢迎登陆</font></div>
			<div class="text-div">
				<div><img src="<%=path%>/static/image/man.png"/></div>
				<input type="text" id="usernameInput" name="username" value="" placeholder="请输入用户名/邮箱"/>
			</div>
			<div class="text-div">
				<div><img src="<%=path%>/static/image/password.png"/></div>
				<input type="password" id="passwordInput" name="password" value="" placeholder="请输入密码"/>
			</div>
			<div>
				<h4 style="color: red">${message}</h4>
			</div>
			<div>
				<a href="#"><font>忘记密码？</font></a>
			</div>
			<div>
				<input type="button" value="登陆" onclick="beforeSubmit();"/>
			</div>
		</form>
	</div>
	<div class="logo-div">
		<div>
			<img src="<%=path%>/static/image/logo.png" />
		</div>
	</div>
	<div class="bg-div">
		<div>
			<img src="<%=path%>/static/image/bg.png" />
		</div>
	</div>
	<div class="copyright-div">
		<p><font>2019&copy版权所有 | 四川省农村信用社联合社</font></p>
	</div>
</body>
<script type="text/javascript">
	function beforeSubmit(){
		$("#promptInfo").html("");
		var username = $("#usernameInput").val();
		var password = $("#passwordInput").val();
		if(!username || username===""){
			layer.tips("<span style='color:red'>请输入用户名！</span>", '#usernameInput',{tips:[2,'#fff']});
			return false;
		}
		if(!password || password===""){
			layer.tips("<span style='color:red'>请输入密码！</span>", '#passwordInput',{tips:[2,'#fff']});
			return false;
		}
		document.LoginForm.submit();
	}
	$(document).ready(function () {
		$("input[name='password']").keydown(function (event) {
			var x = event.which || event.keyCode;
			if (x === 13) {
				beforeSubmit();
			}
		});
	})
</script>
</html>