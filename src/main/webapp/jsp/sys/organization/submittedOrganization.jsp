<%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2019/10/12
  Time: 11:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <style>
        html, body{margin: 0; padding: 0;}
        .hidden-text{max-width: 200px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    </style>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/bootstrap/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/include/easyui/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/css/common/custom_win.css"/>
</head>
<body>
<div id="submitted-organization-layout" class="easyui-layout" style="width:100%; height:100%;">
    <div data-options="region:'north', border: false" style="height:37px;">
        <div style="height: 30px; margin: 3px 0; line-height: 30px;">
            <div style="display: inline-block;">
                <span>报送机构树类型：</span>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, valueField: 'value', textField: 'text'"
                    id="submitted-organization-type">
                </select>
            </div>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submittedOrganizationOpt.initCopyWin()"
               data-options="iconCls:'icon-add', plain: true">复制报送机构树</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submittedOrganizationOpt.openAddWin()"
               data-options="iconCls:'icon-add', plain: true">新增机构节点</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submittedOrganizationOpt.showUpdateData()"
               data-options="iconCls:'icon-edit', plain: true">维护机构节点</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submittedOrganizationOpt.delete()"
               data-options="iconCls:'icon-remove', plain: true">删除机构节点</a>
        </div>
    </div>
    <div data-options="region:'west',title:'机构树', collapsible: false" style="width:300px;">
        <ul id="submitted-organization-tree"></ul>
    </div>
    <div data-options="region:'center',title:'机构详情'" style="background:#eee;">
        <div id="organization-update-win" style="display: none; height: 100%; width: 100%;">
            <form id="organization-update-form" style="margin: 0;" method="post">
                <div class="win-div-row">
                    <div class="win-div-row-column">
                        <div class="edit-win-div-row-column-label">机构名称：</div>
                        <input name="orgNm" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入机构名称。',
                    validType:'length[1,150]'" id="submitted-organization-update-name">
                    </div>
                    <div class="win-div-row-column">
                        <div class="edit-win-div-row-column-label">上级机构：</div>
                        <select id="submitted-organization-update-select-tree" name="sprOrgId" class="easyui-combotree" style="width:300px;"></select>
                    </div>
                </div>
                <input type="hidden" name="treeTyp" id="submitted-organization-update-treeTyp">
                <input type="hidden" name="orgId" id="submitted-organization-update-orgId">
            </form>
            <a href="javascript: void(0)" onclick="submittedOrganizationOpt.update()" class="easyui-linkbutton" icon="icon-ok">保存</a>
        </div>
        <div id="organization-details-win" style="display: none; height: 100%; width: 100%;">
            <form id="organization-details-form" style="margin: 0;">
                <div class="win-div-row">
                    <div class="win-div-row-column">
                        <div class="edit-win-div-row-column-label">机构ID：</div>
                        <input name="id" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                    <div class="win-div-row-column">
                        <div class="edit-win-div-row-column-label">机构名称：</div>
                        <input name="name" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column">
                        <div class="edit-win-div-row-column-label">机构简称：</div>
                        <input name="abbreviation" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column">
                        <div class="edit-win-div-row-column-label">机构编码：</div>
                        <input name="code" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">机构层级编码：</div>
                        <input name="gradationCode" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">上级机构ID：</div>
                        <input name="sprOrgId" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">排序号：</div>
                        <input name="sortNum" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">机构类型编码：</div>
                        <input name="typeCode" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">电子邮箱：</div>
                        <input name="eMail" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">负责人名称：</div>
                        <input name="principalName" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">地址：</div>
                        <input name="address" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">邮政编码：</div>
                        <input name="postalCode" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">办公电话：</div>
                        <input name="officeTelNo" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">传真：</div>
                        <input name="fax" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">状态：</div>
                        <input name="status" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">最近修改时间：</div>
                        <input name="recntModfyTm" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">变动标识：</div>
                        <input name="chanceFlag" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">人行支付行号：</div>
                        <input name="paymtRwBnkNo" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">金融许可证编码：</div>
                        <input name="finLicsNo" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">金融机构代码：</div>
                        <input name="finOrgCd" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">金融机构标识码：</div>
                        <input name="finOrgIndCd" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">登记银行代码：</div>
                        <input name="rgstBnkNo" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-label">地区代码：</div>
                        <input name="rgnCd" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-long-label">是否汇总虚拟机构：</div>
                        <input name="orgSumVrtlFlg" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-long-label">个人征信机构代码：</div>
                        <input name="ntrlpsnCrdtOrgNo" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-long-label">企业征信机构代码：</div>
                        <input name="gnrlCrdtOrgNo" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                </div>
                <div class="win-div-row">
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-long-label">报送机构类型代码：</div>
                        <input name="bnkTypCd" class="easyui-textbox" style="width:200px"
                               data-options="editable: false">
                    </div>
                    <div class="win-div-row-column column-hidden-flag">
                        <div class="edit-win-div-row-column-long-label">是否上级机构的内部部门：</div>
                        <input name="dept" class="easyui-textbox" style="width:200px" data-options="editable: false">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%-- 新增报送机构窗口 --%>
<div id="submitted-organization-add-win" class="easyui-window" title="新增机构节点" data-options="closed: true, width: 425, height: 160, collapsible: false,
        maximizable: false, minimizable: false, resizable: false, modal: true,iconCls:'icon-add'" style="display: none;">
    <div>
        <div class="edit-win-div-row">
            <div  style="margin-top: 5px; height: 30px; ">
                <div id="submitted-organization-label-name" style="width: 100px; text-align: right; line-height: 30px; float: left;">当前机构名称：</div>
                <div  style="width: 200px; text-align: left; line-height: 30px; float: left;">
                    <p id="submitted-organization-add-name" class="hidden-text" style="margin: 0; padding: 0 0 0 5px;"></p>
                </div>
            </div>
        </div>
        <form id="submitted-organization-add-form">
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div id="submitted-organization-select-label-name" style="width: 100px; text-align: right; display: inline-block;">子机构选择：</div>
                    <select id="submitted-organization-add-select-tree" name="orgId" class="easyui-combotree" style="width:300px;"></select>
                </div>
            </div>
            <input id="submitted-organization-add-sprOrgId" name="sprOrgId" type="hidden" />
            <input id="submitted-organization-add-type" name="treeTyp" type="hidden" />
        </form>
        <div style="margin-top: 10px; padding-right: 5px; text-align: right;">
            <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="submitted-organization-add-btn">保存</a>
            <a href="javascript: void(0)" onclick="closeWin('submitted-organization-add-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
        </div>
    </div>
</div>
<%-- 新增报送机构窗口 end --%>
<%-- 复制报送机构窗口 --%>
<div id="submitted-organization-copy-win" class="easyui-window" title="复制报送机构" data-options="closed: true, width: 325, height: 160, collapsible: false,
        maximizable: false, minimizable: false, resizable: false, modal: true,iconCls:'icon-add'" style="display: none;">
    <div>
        <form id="submitted-organization-copy-form">
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div style="width: 100px; text-align: right; display: inline-block;">来源：</div>
                    <select id="submitted-organization-copy-source" name="sourceOrgType" data-options="editable: false, required: true, missingMessage: '请选择。'"
                            class="easyui-combobox" style="width:200px;"></select>
                </div>
            </div>
            <div class="edit-win-div-row">
                <div class="edit-win-div-row-column">
                    <div style="width: 100px; text-align: right; display: inline-block;">目标：</div>
                    <select id="submitted-organization-copy-target" name="targetOrgType" data-options="editable: false, required: true, missingMessage: '请选择。'"
                            class="easyui-combobox" style="width:200px;"></select>
                </div>
            </div>
        </form>
        <div style="margin-top: 10px; padding-right: 5px; text-align: right;">
            <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" onclick="submittedOrganizationOpt.copy()">复制</a>
            <a href="javascript: void(0)" onclick="closeWin('submitted-organization-copy-win')" class="easyui-linkbutton" icon="icon-cancel">取消</a>
        </div>
    </div>
</div>
<%-- 复制报送机构窗口 end --%>

<%-- jQeury --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.min.js"></script>

<%-- Easy UI --%>
<script type="text/javascript" src="<%=path%>/static/include/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/static/include/easyui/locale/easyui-lang-zh_CN.js"></script>
<%-- 自定义js --%>
<script>
    var baseUrl = '<%=path%>';
</script>
<script src="<%=path%>/static/js/ajax.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/organization/submittedOrganization.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/organization/comboTreeUtil.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/combobox.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/common.js" type="text/javascript"></script>
<script src="<%=path%>/static/js/message.js" type="text/javascript"></script>
<script>
    $(function () {
        submittedOrganizationOpt.init();
    })
</script>
</body>
</html>
