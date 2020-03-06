<%--
  Created by IntelliJ IDEA.
  User: pengjuntao
  Date: 2019/9/10
  Time: 9:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.scrcu.sys.entity.SysFunc" %>
<%
    SysFunc sysFunc = (SysFunc) request.getAttribute("SysFunc");
%>
<!DOCTYPE html>
<html>
<head>
    <title>系统菜单详情</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/include/easyui/demo/demo.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/include/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'center'" style="padding:5px;background:#eee;">
    <div style="padding:10px 60px 20px 60px">
        <form id="ff" method="post">
            <table cellpadding="5" width="100%" style="text-align: center">
                <tr>
                    <td align="right">功能编号：</td>
                    <td align="left"><input class="easyui-textbox" type="text" name="FUNC_ID" style="width: 200px" value="<%=sysFunc.getFuncId()%>"/></td>
                    <td align="right">功能名称：</td>
                    <td align="left"><input class="easyui-textbox" type="text" name="FUNC_NM" style="width: 200px" value="<%=sysFunc.getFuncNm()%>"/></td>
                </tr>
                <tr>
                    <td align="right">功能类型：</td>
                    <td align="left"><input class="easyui-textbox" type="text" name="FUNC_TYP" style="width: 200px" value="<%=sysFunc.getFuncType()%>"/></td>
                    <td align="right">上级功能编号：</td>
                    <td align="left"><input class="easyui-textbox" type="text" name="PARE_FUNC_ID" style="width: 200px" value="<%=sysFunc.getPareFuncId()%>"/></td>
                </tr>
                <tr>
                    <td align="right">菜单路径：</td>
                    <td align="left"><input class="easyui-textbox" type="text" name="URL" style="width: 200px" value="<%=sysFunc.getUrl()%>"/></td>
                    <td align="right">是否为叶子菜单：</td>
                    <td align="left"><input class="easyui-textbox" type="text" name="IS_LEF" style="width: 200px" value="<%=sysFunc.getIsLeaf()%>"/></td>
                </tr>
                <tr>
                    <td align="right">顺序号：</td>
                    <td align="left"><input class="easyui-textbox" type="text" name="SEQ_NO" style="width: 200px" value="<%=sysFunc.getSeqNo()%>"/></td>
                    <td align="right">图标路径：</td>
                    <td align="left"><input class="easyui-filebox" name="ICON_URL" style="width: 200px" buttonIcon="icon-search" buttonText="" value="<%=sysFunc.getIconUrl()%>"></td>
                </tr>
                <tr>
                    <td align="right">功能说明：</td>
                    <td align="left" colspan="4"><input class="easyui-textbox" type="text" name="FUNC_COMNT" style="width: 400px;height: 100px" multiline="true" value="<%=sysFunc.getFuncComnt()%>"/></td>
                </tr>
            </table>
        </form>
        <div style="text-align:center">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="shutDown()">关闭</a>
        </div>
    </div>
</div>

<script type="text/javascript">
    //关闭窗口
    function shutDown(){
        $('#win').window('close');
    }
</script>
</body>
</html>
