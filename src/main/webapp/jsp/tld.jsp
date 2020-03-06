<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2020/1/7
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="dh" uri="WEB-INF/tld/usrp.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!-- 第三方控件样式-->
<link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/bootstrap/easyui.css"/>
<link rel='stylesheet' type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
<!-- 第三方js -->
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type='text/javascript' src='<%=path%>/static/include/json2.js'></script><!--pjt-->
<script type="text/javascript" src='<%=path%>/static/include/My97DatePicker/WdatePicker.js'></script>
<!-- 自定义样式 -->
<link rel="stylesheet" type="text/css" href="<%=path%>/static/css/common.css"/>
<!-- 自定义Js -->
<script type='text/javascript' src='<%=path%>/static/js/common.js'></script>
<script type='text/javascript' src='<%=path%>/static/js/ajax.js'></script>
<script type='text/javascript' src='<%=path%>/static/js/sys/component.js'></script>
<script type="text/javascript">
    var path = '<%=path%>';
    var basePath = '<%= basePath%>';
</script>
