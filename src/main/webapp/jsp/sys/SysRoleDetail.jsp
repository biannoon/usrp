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
          <form id="detail_form"  method="post">
                    <table width="100%" style="text-align: center" id = "tab1" cellpadding="5">
                        <tr>
                            <td align="right">角色编号：</td>
                            <td align="left">
                                <input class="easyui-textbox"  id="roleId_insert" name="roleId" value="" onblur="checkGroupId()" required="true" style="width: 150px" maxlength="22" >
                                <input id="roleId_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
                                <!-- onblur="checkGroupId()"-->
                            </td>
                            <td align="right">角色名称：</td>
                            <td align="left">
                                <input class="easyui-textbox"  id="roleNm_insert" name="roleNm"  required="true" value="" style="width: 150px">
                                <input id="roleNm_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
                            </td>
                        </tr>
                        <tr>
                            <td align="right">所属系统：</td>
                            <td align="left">
                                <input class="easyui-textbox"  id="sys_insert" name="sys" value="" required="true" style="width: 150px" maxlength="32" >
                                <input id="sys_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
                            </td>
                            <td align="right">角色层级关系：</td>
                            <td align="left">
                                <input class="easyui-combobox easyui-validatebox" type="text" id="roleHrchCd_insert" name="roleHrchCd" value="" style="width: 150px" data-options="required:true">

                            </td>
                        </tr>
                        <tr>
                            <td align="right">角色说明：</td>
                            <td align="left">
                                <input class="easyui-textbox" data-options="multiline:true" style="height:60px;width:200px;" id="roleComnt_insert" name="roleComnt" style="width: 200px" ></input>
                                <input id="roleComnt_back" value="" style="width: 200px;display: none;color: red;border-left: 0px;border-right: 0px;border-top: 0px;border-bottom: 0px;overflow: hidden;background:lightgoldenrodyellow">
                            </td>
                        </tr>
                    </table>
                </form>
            <div class="btn-area">
                    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()" text="保存" icon="icon-save"></a>
                   <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-cancel" text="重置" onclick="clearForm()"></a>
            </div>
        <script type="text/javascript">
            $(function () {
                //初始化组合框
                initComboboxSelf('roleHrchCd_insert','SYS06','<%=basePath%>');

            })

            function save() {
                var roleId_insert = null;
                $('#detail_form').form({
                    url:'/usrp/sysRole/insert',
                    onSubmit:function () {
                        //输入验证
                        roleId_insert = document.getElementById("roleId_insert").value;
                        var roleNm_insert = document.getElementById("roleNm_insert").value;
                        var roleHrchCd_insert = document.getElementById("roleHrchCd_insert").value;

                        if (roleId_insert == null || roleId_insert == ""){
                            remind("roleId_back","角色编号不能为空");
                            return false;
                        }
                        if (roleNm_insert == null || roleNm_insert == ""){
                            remind("roleNm_back","角色名称不能为空");
                            return false;
                        }
                        if (roleHrchCd_insert == null || roleHrchCd_insert == ""){
                            remind("roleHrchCd_back","角色层级代码不能为空");
                            return false;
                        }

                    },
                    success:function (data) {
                        var data = eval('(' + data + ')'); // 将json字符串转化为JavaScript对象
                        if (data.success){
                            <!-- refreshTab("字典代码管理","toSysDictryCdList")-->
                            newWindow('角色菜单配置','/usrp/sysRole/toSysRoleFuncConf?roleId='+roleId_insert,500,430);
                        }else{
                            alert(data.message);
                        }
                    }
                })
                $('#detail_form').form('submit');
            }
            //检查任务组Id的唯一性
            function checkGroupId(){
                var roleId = document.getElementById("roleId_insert").value;
                if (ajaxCheckId(roleId)==false){
                    remind("roleId_back","角色编号已存在");
                }else{
                    document.getElementById("roleId_back").style.display = 'none';
                }

            }
            function ajaxCheckId(roleId){
                var result = false;
                $.ajax({
                    async:false,
                    url:'/usrp/sysRole/checkRoleId',
                    type:'post',
                    dataType:'json',
                    data:{"roleId":roleId},
                    success:function (data) {
                        var data = JSON.stringify(data);
                        var data = eval('(' + data + ')');
                        if (data.success){
                            result = true
                        }else{
                            result = false;
                        }
                    }
                })
                return result;
            }
            function remind(field,msg){
                document.getElementById(field).style.display = 'block';
                document.getElementById(field).value = msg;
            }
            //重置
            function clearForm() {
                $('#detail_form').form('reset');
            }
        </script>
    </body>
</html>