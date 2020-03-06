<%@ page import="com.scrcu.sys.entity.SysSubsystem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/jsp/tld.jsp"%>
<%
    String type = (String) request.getAttribute("type");
    SysSubsystem system = null;
    if (!"insert".equals(type)){
        system = (SysSubsystem) request.getAttribute("sysSubsystem");
    }else{
        system = new SysSubsystem();
    }
%>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysSubsystem.js"></script>
</head>
<body>
<form id="form_sys_subsystem" method="post">
    <table width="100%" style="text-align: center"  cellpadding="5">
        <tr>
            <td align="right"><span style="font-size: 12px">子系统编号：</span></td>
            <td align="left"><input class="easyui-validatebox" id="subsystemId" name="subsystemId" data-options="required:true,validType:'uniqueSubsystemId'"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">子系统名称：</span></td>
            <td align="left"><input class="easyui-validatebox" id="subsystemNm" name="subsystemNm" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">子系统图标：</span></td>
            <td align="left"><input class="easyui-validatebox" id="iconUrl" name="iconUrl" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">子系统服务访问路径：</span></td>
            <td align="left"><input class="easyui-validatebox" id="url" name="url" data-options="required:true,validType:''"></td>
        </tr>
        <tr>
            <td align="right" valign="top"><span style="font-size: 12px">子系统说明：</span></td>
            <td align="left"><input class="easyui-validatebox" id="subsystemComnt" name="subsystemComnt" data-options="required:false,validType:'',multiline:true"></td>
        </tr>
        <%if ("detail".equals(type)){%>
        <tr>
            <td align="right"><span style="font-size: 12px">创建人：</span></td>
            <td align="left"><input class="easyui-validatebox" id="creatr" name="creatr" data-options="required:false,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">创建日期：</span></td>
            <td align="left"><input class="easyui-validatebox" id="crtDt" name="crtDt" data-options="required:false,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">最后修改人：</span></td>
            <td align="left"><input class="easyui-validatebox" id="finlModifr" name="finlModifr" data-options="required:false,validType:''"></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">最后修改日期：</span></td>
            <td align="left"><input class="easyui-validatebox" id="finlModfyDt" name="finlModfyDt" data-options="required:false,validType:''"></td>
        </tr>
        <%}%>
    </table>
</form>
<div style="text-align:center">
    <%if ("insert".equals(type)){%>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add"
       iconCls="icon-save" onclick="submitFormBySysSubsystem('form_sys_subsystem','sysSubsystem/insert')">提交</a>
    <%} else if("update".equals(type)){%>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true id="btn_add"
       iconCls="icon-save" onclick="submitFormBySysSubsystem('form_sys_subsystem','sysSubsystem/update')">提交</a>
    <%}%>
    <a href="javascript:void(0)" class="easyui-linkbutton" plain=true
       iconCls="icon-remove" onclick="closeWindowBySysSubsystem('win_sys_subsystem')">关闭</a>
</div>
<script type="text/javascript">
    //初始化页面
    $(function () {
        <%if (!"insert".equals(type)){%>
        initTextboxBySubsystem('subsystemId','<%=system.getSubsystemId()==null?"":system.getSubsystemId()%>',32,true,'200','20');
        <%}else{%>
        initTextboxBySubsystem('subsystemId','',32,false,'200','20');
        <%}%>
        initTextboxBySubsystem('subsystemNm','<%=system.getSubsystemNm()==null?"":system.getSubsystemNm()%>',50,false,'200','20');
        initTextboxBySubsystem('iconUrl','<%=system.getIconUrl()==null?"icon-set":system.getIconUrl()%>',200,false,'200','20');
        initTextboxBySubsystem('url','<%=system.getUrl()==null?"":system.getUrl()%>',200,false,'200','20');
        initTextboxBySubsystem('subsystemComnt','<%=system.getSubsystemComnt()==null?"":system.getSubsystemComnt()%>',200,false,'200','100');
        <%if ("detail".equals(type)){%>
        initTextboxBySubsystem('creatr','<%=system.getCreatr()==null?"":system.getCreatr()%>',6,false,'200','20');
        initTextboxBySubsystem('finlModifr','<%=system.getFinlModifr()==null?"":system.getFinlModifr()%>',6,false,'200','20');
        initDateBoxBySubsystem('crtDt','<%=system.getCrtDt()==null?"":system.getCrtDt()%>','200','20');
        initDateBoxBySubsystem('finlModfyDt','<%=system.getFinlModfyDt()==null?"":system.getFinlModfyDt()%>','200','20');
        <%}%>
    })
</script>
</body>
</html>
