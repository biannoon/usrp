<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <base href="<%=basePath%>">
        <title>统一监管报送平台</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <script type='text/javascript' src="<%=path%>/static/js/index.js"></script>
        <script type="text/javascript">
            function updUserInfo(winId, userId){
                var url = basePath + 'sysUser/getById?userId=' + userId;
                openWindow(winId, "个人信息维护", url);
            }
            function changePwd(winId, userId){
                var url = basePath + 'sysUser/getById?userId=' + userId;
                openWindow(winId, "密码修改", url);
            }
            function logOut(){
                window.location.href = basePath + "logout";
            }
        </script>
    </head>
    <body class="easyui-layout">
        <input type="hidden" id="bsPath" value="<%=basePath%>">
        <div data-options="region:'north',split:false,collapsible:false" style="height: 50px;">
            <div style="position:absolute;left:1px;bottom:1px;width:330px;border:0px;">
                <div style="width: 330px;height: 48px;">
                    <img style="width: 100%;height: 100%;" src="<%=path%>/static/image/logo.png"/>
                </div>
            </div>
            <div id="crtTime" style="position:absolute;right:200px;bottom:1px;width:200px;border:0px;padding:5px;"></div>
            <div class="easyui-panel" style="position:absolute;right:1px;bottom:1px;width:200px;border:0px;padding:5px;">
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'pic_5'">${sysUser.userNm}</a>
                <a href="#" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'pic_339'">帮助</a>
            </div>
            <div id="mm1" style="width:150px;">
                <div data-options="iconCls:'pic_2'" onclick="updUserInfo('newWin','${sysUser.userId}')">信息维护</div>
                <div data-options="iconCls:'pic_71'" onclick="changePwd('newWin','${sysUser.userId}')">修改密码</div>
                <div class="menu-sep"></div>
                <div data-options="iconCls:'icon-undo'" onclick="logOut()">安全退出</div>
            </div>
            <div id="mm2" style="width:100px;">
                <div data-options="iconCls:'pic_339'">Help</div>
                <div >About</div>
            </div>
        </div>
        <div data-options="region:'west',title:'菜单导航',split:false,collapsible:true"
             style="width: 200px;">
            <div id="westAccordion" class="easyui-accordion"
                 data-options="fit:true,border:false,nimate:true,lines:true"></div>
        </div>
        <div data-options="region:'center'">
            <div class="easyui-tabs" fit="true" border="false" id="tabs">
                <div title="首页">
                    <div style="height: 30px; line-height: 30px;">
                        <p style="width: 50px; float: left; float: left; margin: 0 0 0 5px;">公告:</p>
                        <p id="pub-notice-1" style="width: 300px; float: left; margin: 0;"></p>
                        <p id="pub-notice-2" style="width: 300px; float: left; margin: 0;"></p>
                        <p id="pub-notice-3" style="width: 300px; float: left; margin: 0;"></p>
                        <a id="more-pub-notice-btn" href="javascript:void(0)" data-options="plain: true" title="更多">&gt;&gt;</a>
                    </div>
                </div>
            </div>
        </div>
        <div id="menu" class="easyui-menu" style="width:150px;">
            <div id="m-refresh" data-options="iconCls:'icon-reload'">刷新</div>
            <div class="menu-sep"></div>
            <div id="m-close">关闭</div>
            <div class="menu-sep"></div>
            <div id="m-closeall">全部关闭</div>
            <div id="m-closeother">关闭其他标签</div>
            <div class="menu-sep"></div>
            <div id="m-closeright">关闭右侧标签</div>
            <div id="m-closeleft">关闭左侧标签</div>
        </div>
        <!-- 弹出窗口-->
        <div id="newWin"></div>
    </body>
</html>