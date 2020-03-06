<%--
  Created by IntelliJ IDEA.
  User: brucepeng
  Date: 2019/10/9
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta charset="UTF-8">
</head>
<body>
    <table width="100%" style="text-align: center" cellpadding="5">
        <tr>
            <td align="right">字典名称：</td>
            <td align="left"><input class="easyui-textbox" id="dictryNm"  value="" style=""></td>
            <td align="right">字典代码：</td>
            <td align="left"><input class="easyui-textbox" id="dictryId"  value="" style="" buttonIcon="icon-search"></td>
        </tr>
        <tr>
            <td align="right">机构名称：</td>
            <td align="left"><input class="easyui-textbox" id="orgNm"  value="" style=""></td>
            <td align="right">机构编码：</td>
            <td align="left"><input class="easyui-textbox" id="orgId"  value="" style="" buttonIcon="icon-search"></td>
        </tr>
        <tr>
            <td align="right">用户名称：</td>
            <td align="left"><input class="easyui-textbox" id="sysUserNm"  value="" style=""></td>
            <td align="right">用户编码：</td>
            <td align="left"><input class="easyui-textbox" id="sysUserId"  value="" style="" buttonIcon="icon-search"></td>
        </tr>
        <tr>
            <td align="right">测试easyUI文本框：</td>
            <td align="left">
                <input class="easyui-textbox" id="textbox_test"  value="" style="">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="set()">测试赋值操作</a>
            </td>
        </tr>
    </table>
    <div id="win_sysDictryCd"></div>
    <div id="win_sysOrgInfo"></div>
    <div id="win_sysUser"></div>
</body>
<script type="text/javascript">

    $(function () {
        initSysDictryComponent('dictryId', 'dictryNm', 'ASC001', 'win_sysDictryCd', '0', '', '0', '650', '500');
        initSysOrgInfoComponent('orgId', 'orgNm', 'win_sysOrgInfo', '0', '', 'false', '0', '0', '', '600', '400');
        initSysUserInfoComponent('sysUserId', 'sysUserNm', 'win_sysUser', '0', '', 'true', 'true', '600', '500');
    })

    function set(){
        var msg = '测试easyUI文本框赋值';
        $('#textbox_test').textbox("setText",msg);
    }
</script>
</html>
