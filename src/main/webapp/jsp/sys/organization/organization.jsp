<%--
  User: WY
  Date: 2019/9/6
  Time: 16:45
  To change this template use File | Settings | File Templates.
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
    <div id="organization-layout" class="easyui-layout" style="width:100%; height:100%;">
        <div data-options="region:'north', border: false" style="height:36px;">
            <div>
                <div style="height: 30px; margin: 3px 0; line-height: 30px;">
                    <a id="virtual-organization-add" href="javascript:void(0)" class="easyui-linkbutton" onclick="organizationTagOpt.openDataGridWin()"
                       data-options="iconCls:'icon-edit', plain: true">机构标签维护</a>
                    <a id="organization-field-update" href="javascript:void(0)" class="easyui-linkbutton" onclick="organization.updateSubmittedField()"
                       data-options="iconCls:'icon-edit', plain: true">报送机构字段更新</a>
                </div>
            </div>
        </div>
        <div data-options="region:'west',title:'机构树', collapsible: false" style="width:300px;">
            <ul id="branch-tree"></ul>
        </div>
        <div data-options="region:'center',title:'机构详情'" style="background:#eee;">
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
                            <input name="superiorId" class="easyui-textbox" style="width:200px"
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

    <%--更新报送机构信息窗--%>
    <div id="organization-update-win" class="easyui-window" title="报送机构更新" data-options="closed: true, iconCls:'icon-edit'" style="display: none;">
        <div>
            <div  style="margin-top: 5px; height: 30px; ">
                <div style="width: 100px; text-align: right; line-height: 30px; float: left;">已选机构名称：</div>
                <div  style="width: 200px; text-align: left; line-height: 30px; float: left;">
                    <p id="organization-name" style="margin: 0; padding: 0 0 0 5px; max-width: 200px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;"></p>
                </div>
            </div>
            <form id="organization-update-form" method="post">
                <div  style="margin-top: 5px;">
                    <div style="width: 100px; text-align: right; display: inline-block;">机构字段选择：</div>
                    <select id="organization-field-select" class="easyui-combobox" name="organization_field"
                            style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择一个机构字段。', panelHeight: '200px'">
                        <option value="paymtRwBnkNoField">人行支付行号</option>
                        <option value="finLicsNoField">金融许可证编码</option>
                        <option value="ntrlpsnCrdtOrgNoField">个人征信机构代码</option>
                        <option value="gnrlCrdtOrgNoField">企业征信机构代码</option>
                        <option value="finOrgCdField">金融机构代码</option>
                        <option value="finOrgIndCdField">金融机构标识码</option>
                        <option value="rgstBnkNoField">登记银行代码</option>
                        <option value="bnkTypCdField">报送机构类型代码</option>
                        <option value="rgnCdField">地区代码</option>
                    </select>
                </div>
                <div style="margin-top: 5px;">
                    <div  style="width: 100px; text-align: right; display: inline-block;">请输入字段值：</div>
                    <input id="organization-field-value" class="easyui-textbox" style="width:200px" />
                </div>
            </form>
            <div style="margin-top: 10px; padding-right: 5px; text-align: right;">
                <a href="javascript: void(0)" onclick="organization.updateField()" class="easyui-linkbutton" icon="icon-ok">保存</a>
                <a href="javascript: void(0)" onclick="organization.closeUpdateWin()" class="easyui-linkbutton" icon="icon-cancel">取消</a>
            </div>
            <input id="organization-id" type="hidden" />
        </div>
    </div>
    <%--更新报送机构信息窗 end--%>
    <%--机构标签数据网格窗口--%>
    <div id="organization-tag-win" class="easyui-window" title="机构标签维护"
         data-options="closed: true, width: 550, height: 370, collapsible: false, maximizable: false, minimizable: false, resizable: false,modal: true,iconCls:'icon-tip'"
         style="display: none;">
        <table id="organization-tag-data-grid" ></table>
    </div>
    <%-- 机构标签数据网格窗口 end--%>
    <%-- 机构标签信息编辑窗口--%>
    <div id="organization-tag-edit-win" class="easyui-window" title="维护机构标签" data-options="closed: true, width: 325, height: 193, collapsible: false,
        maximizable: false, minimizable: false, resizable: false, modal: true," style="display: none;">
        <div>
            <div class="edit-win-div-row">
                <div  style="margin-top: 5px; height: 30px; ">
                    <div style="width: 100px; text-align: right; line-height: 30px; float: left;">已选机构名称：</div>
                    <div  style="width: 200px; text-align: left; line-height: 30px; float: left;">
                        <p id="organization-tag-organizationName" class="hidden-text" style="margin: 0; padding: 0 0 0 5px;"></p>
                    </div>
                </div>
            </div>
            <form id="organization-tag-edit-form" method="post">
                <div class="edit-win-div-row">
                    <div class="edit-win-div-row-column">
                        <div class="edit-win-div-row-column-label">机构标签类型：</div>
                        <select id="organization-tag-type-select" class="easyui-combobox" name="tagTyp"
                                style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择机构标签类型。',valueField: 'value', textField: 'text'">
                        </select>
                    </div>
                </div>
                <div class="edit-win-div-row">
                    <div class="edit-win-div-row-column">
                        <div  style="width: 100px; text-align: right; display: inline-block;">机构标签值：</div>
                        <input id="organization-tag-value" class="easyui-textbox" style="width:200px" name="tagVal"
                               data-options="required: true, missingMessage: '请输入机构标签值。', validType:'length[1,50]'"/>
                    </div>
                </div>
                <input id="organization-tag-orgId" name="orgId" type="hidden" />
            </form>
            <div style="margin-top: 10px; padding-right: 5px; text-align: right;">
                <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="organization-tag-edit-btn">保存</a>
                <a href="javascript: void(0)" onclick="organizationTagOpt.closeEditWin()" class="easyui-linkbutton" icon="icon-cancel">取消</a>
            </div>
        </div>
    </div>
    <%-- 机构标签信息编辑窗口 end --%>
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
    <script src="<%=path%>/static/js/combobox.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/message.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/organization/organization.js" type="text/javascript"></script>
    <script src="<%=path%>/static/js/organization/organizationTagOpt.js" type="text/javascript"></script>
    <script>
        $(function () {
            organization.initTree();
        })
    </script>
</body>
</html>
