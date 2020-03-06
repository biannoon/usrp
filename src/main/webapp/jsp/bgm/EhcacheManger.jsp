<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>缓存管理</title>
        <script type="text/javascript">
            $(function(){
                $("input[name='checkAll']").on('click',function(){
                    if(this.checked){
                        $("input[name='initFlag']").prop("checked", true);
                    }else{
                        $("input[name='initFlag']").prop("checked", false);
                    }
                });
            });
            function isChecked(){
                var flag = false;
                var initflag = "";
                $("input[name='initFlag']").each(function(){
                    if(this.checked){
                        flag=true;
                        initflag += $(this).val() + ",";
                    }
                });
                if(!flag){
                    $.messager.alert('提示信息',"请选择需要刷新的选项！",'error');
                }else{
                    var ajaxObj = {
                        url : basePath+"/initSysServlet",
                        data: "initflag=" + initflag
                    }
                    customAjaxSubmit(ajaxObj, function(data){
                        if(data.result == "success"){
                            $.messager.alert('提示信息',"缓存刷新成功！",'info');
                            $("input[type='checkbox']").prop("checked", false);
                        }
                    }, function(){});
                }
            }
        </script>
    </head>
    <body class="easyui_layout">
        <div style="float:right;right:5px;padding:2px 5px;">
            <a class="easyui-linkbutton" onclick="isChecked()" text="刷新" icon="icon-reload"></a>
        </div>
        <div style="padding:2px 5px;">
            <table width="70%" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="center">操作<input type="checkbox" name="checkAll"/></td>
                    <td align="center">缓存名称</td>
                </tr>
                <tr>
                    <td align="center"><input type="checkbox" name="initFlag" value="baseOrg"/></td>
                    <td align="left">初始化机构信息</td>
                </tr>
                <tr>
                    <td align="center"><input type="checkbox" name="initFlag" value="dictry"/></td>
                    <td align="left">初始化字典</td>
                </tr>
                <tr>
                    <td align="center"><input type="checkbox" name="initFlag" value="func"/></td>
                    <td align="left">初始化功能菜单资源</td>
                </tr>
                <tr>
                    <td align="center"><input type="checkbox" name="initFlag" value="tabconfig"/></td>
                    <td align="left">初始化表单配置</td>
                </tr>
                <tr>
                    <td align="center"><input type="checkbox" name="initFlag" value="authoritory"/></td>
                    <td align="left">初始化角色权限配置</td>
                </tr>
            </table>
        </div>
    </body>
</html>