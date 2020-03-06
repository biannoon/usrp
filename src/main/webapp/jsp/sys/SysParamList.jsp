<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysParam.js"></script>
</head>
<body >
    <div id="dg_sys_param_tool"  title="系统参数列表" >
        <table width="100%" style="text-align: right">
            <tr>
                <td align="right" width="10%"><span style="font-size:12px ">参数名称：</span></td>
                <td align="left"  width="10%"><input id="paramNm_se" ></td>
                <td align="right" width="10%"><span style="font-size:12px;">参数类型：</span></td>
                <td align="left"  width="10%"><input id="paramTyp_se" data-options="editable:false,panelHeight:'auto'"></td>
                <td align="left"  width="60%">
                    <div style="">
                        <a href="#" class="easyui-linkbutton"
                           onclick="searchSysParam()" plain="true" iconCls="icon-search">查询</a>
                        <a href="#" class="easyui-linkbutton"
                           onclick="resetSearchSysParam()" plain="true" iconCls="icon-set">重置</a>
                        <a href="#" class="easyui-linkbutton" text="新增" iconCls="icon-add" plain="true"
                           onclick="toInsertSysParam('win_sys_param','系统参数新增','sysParam/toInsert','500','400')"></a>
                        <a href="#" class="easyui-linkbutton" text="修改" iconCls="icon-edit" plain="true"
                           onclick="toUpdateSysParam('win_sys_param','系统参数修改','sysParam/toUpdate','500','400')"></a>
                        <a href="#" class="easyui-linkbutton" text="删除" iconCls="icon-remove" plain="true"
                           onclick="toDeleteSysParam('sysParam/delete')"></a>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <!-- 数据列表展示区域-->
    <div style="width: 100%">
        <table id="dg_sys_param"></table>
    </div>
    <!-- 弹出窗口-->
    <div id="win_sys_param"></div>
    <script type="text/javascript">
        $(function() {
            initSysParamPage();
        })
    </script>
</body>
</html>