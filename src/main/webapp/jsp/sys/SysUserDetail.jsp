<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/10
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.scrcu.sys.entity.SysUser" %>
<%@ include file="/jsp/tld.jsp" %>
<%
    SysUser user = (SysUser) request.getAttribute("sysUser");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysUser.js"></script>
</head>
<body>
<form id="detail_form"  method="post">
    <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
        <tr>
            <td align="right" width="20%"><span style="font-size: 12px">用户编号：</span></td>
            <td align="left" width="25%"><span id="userId_d" style="font-size: 12px"></span></td>
            <td align="right" width="20%"><span style="font-size: 12px">用户名称：</span></td>
            <td align="left" width="25%"><span id="userNm_d" style="font-size: 12px"></span></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">密码：</span></td>
            <td align="left"><span id="pwd_d" style="font-size: 12px"></span></td>
            <td align="right"><span style="font-size: 12px">性别：</span></td>
            <td align="left"><span id="genderCd_d" style="font-size: 12px"></span></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">证件号码：</span></td>
            <td align="left"><span id="crtfNo_d" style="font-size: 12px"></span></td>
            <td align="right"><span style="font-size: 12px">出生日期：</span></td>
            <td align="left"><span id="brthdy_d" style="font-size: 12px"></span></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">联系电话：</span></td>
            <td align="left"><span id="telNo_d" style="font-size: 12px"></span></td>
            <td align="right"><span style="font-size: 12px">通讯地址：</span></td>
            <td align="left"><span id="addr_d" style="font-size: 12px"></span></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">所属机构：</span></td>
            <td align="left"><span id="blngtoOrgNo_d" style="font-size: 12px"></span></td>
            <td align="right"><span style="font-size: 12px">状态：</span></td>
            <td align="left"><span id="stus_d" style="font-size: 12px"></span></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">上次登陆时间：</span></td>
            <td align="left"><span id="recntLgnTm_d" style="font-size: 12px"></span></td>
            <td align="right"><span style="font-size: 12px">创建人：</span></td>
            <td align="left"><span id="creatr_d" style="font-size: 12px"></span></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">创建时间：</span></td>
            <td align="left"><span id="crtDt_d" style="font-size: 12px"></span></td>
            <td align="right"><span style="font-size: 12px">最后修改人：</span></td>
            <td align="left"><span id="finlModifr_d" style="font-size: 12px"></span></td>
        </tr>
        <tr>
            <td align="right"><span style="font-size: 12px">最后修改时间：</span></td>
            <td align="left"><span id="finlModfyDt_d" style="font-size: 12px"></span></td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    $(function () {
        $('#userId_d').html('<%=user.getUserId()==null?"":user.getUserId()%>');
        $('#userNm_d').html('<%=user.getUserNm()==null?"":user.getUserNm()%>');
        $('#pwd_d').html('<%=user.getPwd()==null?"******":user.getPwd()%>');
        $('#genderCd_d').html('<%=user.getGenderCd()==null?"":user.getGenderCd()%>');
        $('#crtfNo_d').html('<%=user.getCrtfNo()==null?"":user.getCrtfNo()%>');
        $('#brthdy_d').html('<%=user.getBrthdy()==null?"":user.getBrthdy()%>');
        $('#telNo_d').html('<%=user.getTelNo()==null?"":user.getTelNo()%>');
        $('#addr_d').html('<%=user.getAddr()==null?"":user.getAddr()%>');
        $('#blngtoOrgNo_d').html('<%=user.getBlngtoOrgNo()==null?"":user.getBlngtoOrgNo()%>');
        $('#stus_d').html('<%=user.getStus()==null?"":user.getStus()%>');
        $('#recntLgnTm_d').html('<%=user.getRecntLgnTm()==null?"":user.getRecntLgnTm()%>');
        $('#crtDt_d').html('<%=user.getCrtDt()==null?"":user.getCrtDt()%>');
        $('#creatr_d').html('<%=user.getCreatr()==null?"":user.getCreatr()%>');
        $('#finlModifr_d').html('<%=user.getFinlModifr()==null?"":user.getFinlModifr()%>');
        $('#finlModfyDt_d').html('<%=user.getFinlModfyDt()==null?"":user.getFinlModfyDt()%>');
    })
</script>
</body>
</html>
