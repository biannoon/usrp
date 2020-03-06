<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/tld.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title></title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
    </head>
    <body>
        <div class="container-area">
            <div class="detail-area"><!-- 详情容器 -->
                <form id="detail_form" action="<%= basePath%>sysGroup/update" method="post">
                    <dh:form tablename="sysGroup" mode="detail" action="update"/>
                </form>
            </div>
            <div class="btn-area">
                <jstl:if test="${requestScope.readonly!='true'}">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()" text="保存" icon="icon-save"></a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </jstl:if>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin('newWin')" text="取消" icon="icon-cancel"></a>
            </div>
        </div>
        <script type="text/javascript">
            function save() {
                var action = '${action}';
                if (action === "insert") {
                    var ajaxObj = {
                        url : basePath+"sysGroup/insert",
                        data: $("#detail_form").serialize()
                    }
                    customAjaxSubmit(ajaxObj, function(data){
                        if(data.success){
                            $("#dataGrid").datagrid("reload");
                            var url = basePath + 'jsp/sys/SysGroupResouseShow.jsp?groupId=' + data.data;
                            openWindow("newWin", "用户组资源分配", url);
                        }else{
                            $.message.alert("警告",data.message,'error');
                        }
                    }, function(){});
                } else if (action === "update") {
                    ajaxSubmit("post",basePath+"sysGroup/update",$("#detail_form").serialize(),"json");
                }
            }
        </script>
    </body>
</html>