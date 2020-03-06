<%@ page import="com.scrcu.sys.entity.SysDatabase" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/17
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/tld.jsp"%>
<%
    String type = (String) request.getAttribute("type");
    SysDatabase database = null;
    if (!"insert".equals(type)){
        database = (SysDatabase) request.getAttribute("sysDatabase");
    }else{
        database = new SysDatabase();
    }
%>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysDatabase.js"></script>
</head>
<body>
<form id="form_sys_database" method="post">
    <table width="100%" style="text-align: center"  cellpadding="5">
        <%if (!"insert".equals(type)){%>
        <tr>
            <td align="right"><span style="font-size: 12px">数据源ID：</span></td>
            <td align="left"><input class="easyui-validatebox" id="id" name="id" data-options="required:true,validType:''"></td>
        </tr>
        <%}%>
        <tr>
            <td align="right"><span style="font-size: 12px">数据源名称：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dsNm" name="dsNm" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">数据源类型ID：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dbTyp" name="dbTyp" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">数据库名称：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dbNm" name="dbNm" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">IP地址：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dbIp" name="dbIp" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">端口：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dbPort" name="dbPort" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">用户名：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dbUserNm" name="dbUserNm" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">密码：</span></td>
            <td align="left"><input class="easyui-validatebox" id="dbUserPwd" name="dbUserPwd" data-options="required:true,validType:''"></td>
        </tr>
        <%if ("detail".equals(type)){%>
        <tr>
            <td align="right"><span style="font-size: 12px">创建人：</span></td>
            <td align="left"><input class="easyui-validatebox" id="creatr" name="creatr" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">创建日期：</span></td>
            <td align="left"><input class="easyui-validatebox" id="crtDt" name="crtDt" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">最后修改人：</span></td>
            <td align="left"><input class="easyui-validatebox" id="finlModifr" name="finlModifr" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">最后修改日期：</span></td>
            <td align="left"><input class="easyui-validatebox" id="finlModfyDt" name="finlModfyDt" data-options="required:true,validType:''"></td>
        </tr>
        <%}%>
        </table>
    </form>
    <div style="text-align:center">
        <%if ("insert".equals(type)){%>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add"
           iconCls="icon-save" onclick="submitFormBySysDatabase('form_sys_database','sysDatabase/insert')">提交</a>
        <%} else if("update".equals(type)){%>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add"
           iconCls="icon-save" onclick="submitFormBySysDatabase('form_sys_database','sysDatabase/update')">提交</a>
        <%}%>
        <a href="javascript:void(0)" class="easyui-linkbutton" plain=true
           iconCls="icon-remove" onclick="closeWindowBySysDatabase('win_sys_database')">关闭</a>
    </div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        <%if (!"insert".equals(type)){%>
        initTextboxByDatabase('id','<%=database.getId()==null?"":database.getId()%>',32,true,'150','20');
        <%}%>
        initTextboxByDatabase('dsNm','<%=database.getDsNm()==null?"":database.getDsNm()%>',20,false,'150','20');
        initComboboxByDatabase('dbTyp','SYS15','<%=database.getDbTyp()==null?"":database.getDbTyp()%>',false,'150','20');
        initTextboxByDatabase('dbNm','<%=database.getDbNm()==null?"":database.getDbNm()%>',20,false,'150','20');
        initTextboxByDatabase('dbIp','<%=database.getDbIp()==null?"":database.getDbIp()%>',15,false,'150','20');
        initTextboxByDatabase('dbPort','<%=database.getDbPort()==null?"":database.getDbPort()%>',10,false,'150','20');
        initTextboxByDatabase('dbUserNm','<%=database.getDbUserNm()==null?"":database.getDbUserNm()%>',20,false,'150','20');
        initTextboxByDatabase('dbUserPwd','<%=database.getDbUserPwd()==null?"":database.getDbUserPwd()%>',32,false,'150','20');
        <%if ("detail".equals(type)){%>
        initTextboxByDatabase('creatr','<%=database.getCreatr()==null?"":database.getCreatr()%>',6,false,'150','20');
        initTextboxByDatabase('finlModifr','<%=database.getFinlModifr()==null?"":database.getFinlModifr()%>',6,false,'150','20');
        initDateBoxByDatabase('crtDt','<%=database.getCrtDt()==null?"":database.getCrtDt()%>','150','20');
        initDateBoxByDatabase('finlModfyDt','<%=database.getFinlModfyDt()==null?"":database.getFinlModfyDt()%>','150','20');
        <%}%>
    })
</script>
</body>
</html>
