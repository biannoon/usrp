<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
模块行数目录：
    1、报文文件综合配置窗口：start: 18
    2、文件名综合配置窗口：start: 115
    3、报文文件结构段综合配置窗口：start: 171
    4、报文文件结构段数据项综合配置窗口：start: 257
    5、报文SQL综合配置窗口：start: 364
    6、报文SQL参数综合配置窗口：start: 425
    7、报文SQL元数据综合配置窗口：start: 482
    8、报文xml标签综合配置窗口：start: 511
    9、报文xml标签属性综合配置窗口：start: 664
    10、报文文件夹综合配置窗口：start: 725

--%>

<%--        报文文件综合配置窗口 start: 18           --%>
<div id="report-file-data-grid-win" class="easyui-window" title="文件配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_338'"
     style="display: none;">
    <table id="report-file-data-grid"></table>
</div>
<div id="report-file-edit-win" class="easyui-window" title="报文文件配置"
     data-options="closed: true, width: 740, height: 365, modal: true, collapsible: false, maximizable: false, minimizable: false, resizable: false" style="display: none;">
    <form id="report-file-edit-form" method="post">
        <input id="file-edit-subject-id" name="subjectId" type="hidden"/>
        <input id="file-edit-file-id" name="fileId" type="hidden"/>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">中文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,50]']" id="file-edit-file-name" name="fileName" >
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">所属文件夹：</div>
                <select class="easyui-combogrid" style="width:200px;" data-options="editable: false, missingMessage: '请选择所属文件夹。'"
                        id="file-edit-folder-name" name="folderName">
                </select>
                <input id="file-edit-folder-id" name="folderId" type="hidden"/>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">文件类型：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择文件类型。'"
                        id="file-edit-file-type" name="fileType">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">报送体类型：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择报送体类型。'"
                        id="file-edit-body-type" name="bodyType">
                </select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">报送记录限制条数：</div>
                <input class="easyui-numberbox" style="width:200px" data-options="min:0, value:0,required: true, prompt: '默认0表示无限制'"
                       id="file-edit-recordLimit" name="recordLimit">
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">是否生成空数据报文文件：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否生成空数据报文文件。'"
                        id="file-edit-emptyFileFlag" name="emptyFileFlag"></select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">文件编码格式：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择文件编码格式。'"
                        id="file-edit-codeType" name="codeType"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">关联SQL：</div>
                <select class="easyui-combotree" style="width:200px;"  id="file-edit-relation-sql" name="relationSqlId"></select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">机构字段映射类型：</div>
                <select class="easyui-combobox"  style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择机构字段映射类型。'"
                        id="file-edit-orgFieldType" name="orgFieldType">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">机构字段映射表达式：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入机构字段映射表达式。',
                 validType:'length[0,100]'" id="file-edit-orgFieldExps" name="orgFieldExps">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">排序号：</div>
                <input  class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true, missingMessage: '请输入排序号。'"
                        id="file-edit-sequence-no" name="sequenceNo">
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">报文说明：</div>
                <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'" style="width:553px;height:60px"
                       id="file-edit-comment" name="comnt">
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="file-edit-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="file-edit-close-btn">取消</a>
    </div>
</div>
<%--        报文文件综合配置窗口 end         --%>

<%--        文件名综合配置窗口 start: 115            --%>
<div id="report-file-name-data-grid-win" class="easyui-window" title="文件名配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'icon-tip'"
     style="display: none;">
    <table id="report-file-name-data-grid"></table>
</div>
<div id="report-file-name-edit-win" class="easyui-window" title="报送文件夹" data-options="closed: true, iconCls:'icon-add'" style="display: none;">
    <form id="report-file-name-edit-form" method="post">
        <input id="file-name-edit-dataItemId" name="dataItemId" type="hidden"/>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">数据项名称：</div>
                <input name="dataItemNm" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入数据项名称。',
                    validType:'length[1,50]'" id="file-name-edit-dataItemNm">
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">排序号：</div>
                <input name="sequenceNo" class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true, missingMessage: '请输入排序号。'"
                       id="file-name-edit-sequence-no">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">数据项类型：</div>
                <select class="easyui-num" name="dataItemType" id="file-name-edit-dataItemType"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择数据项类型。'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">数据项表达式：</div>
                <input name="dataItemExps" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入数据项表达式。',
                    validType:'length[1,100]'" id="file-name-edit-dataItemExps">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">顺序号长度：</div>
                <input name="sequenceNoLength" class="easyui-numberbox" style="width:200px" data-options="min:0,max:10, value:0, missingMessage: '请输顺序号长度。',prompt:'取值范围[1, 10]'"
                       id="file-name-edit-sequenceNoLength">
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-label">数据项说明：</div>
                <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'"  name="comnt"
                       style="width:503px;height:60px" id="file-name-edit-comment">
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="file-name-edit-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="file-name-edit-close-btn">取消</a>
    </div>
</div>
<%--        文件名综合配置窗口 end            --%>

<%--        报文文件结构段综合配置窗口 start: 171            --%>
<div id="segment-data-grid-win" class="easyui-window" title="结构段配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_336'"
     style="display: none;">
    <table id="segment-data-grid"></table>
</div>
<div id="segment-edit-win" class="easyui-window" title="结构段配置"
     data-options="closed: true, width: 740, height: 335, modal: true, collapsible: false, maximizable: false, minimizable: false, resizable: false" style="display: none;">
    <form id="segment-edit-form" method="post">
        <input id="segment-edit-id" name="segmentId" type="hidden"/>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">结构段名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入结构段名称。',
                    validType:'length[1,50]'" id="segment-edit-file-name" name="segmentName" >
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">是否循环结构段：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否循环结构段。'"
                        id="segment-edit-loop-segment" name="loopSegment">
                </select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">是否换行：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否换行。'"
                        id="segment-edit-lineBreak" name="lineBreak">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">换行符表达式：</div>
                <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入换行符表达式。',
                    validType:'length[1,5]'" id="segment-edit-lineBreakChar" name="lineBreakChar" >
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">分隔类型：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择分隔类型。'"
                        id="segment-edit-splitType" name="splitType"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">分隔符类型：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, missingMessage: '请选择分隔符类型。'"
                        id="segment-edit-splitCharType" name="splitCharType"></select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">分隔符表达式：</div>
                <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入分隔符表达式。',
                    validType:'length[1,12]'" id="segment-edit-splitChar" name="splitChar" >
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">是否主要报送内容段：</div>
                <select class="easyui-combotree" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否主要报送内容段。'"
                        id="segment-edit-mainSegment" name="mainSegment"></select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">关联SQL：</div>
                <select class="easyui-combotree" style="width:200px;"  id="segment-edit-relation-sql" name="relationSqlId"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">排序号：</div>
                <input  class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true, missingMessage: '请输入排序号。'"
                        id="segment-edit-sequence-no" name="sequenceNo">
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">结构段说明：</div>
                <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'" style="width:553px;height:60px"
                       id="segment-edit-comment" name="comnt">
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="segment-edit-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="segment-edit-close-btn">取消</a>
    </div>
</div>
<%--        报文文件结构段综合配置窗口 end            --%>

<%--        报文文件结构段数据项综合配置窗口 start: 257             --%>
<div id="data-item-data-grid-win" class="easyui-window" title="数据项配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false"
     style="display: none;">
    <table id="data-item-data-grid"></table>
</div>
<div id="data-item-edit-win" class="easyui-window" title="数据项配置"
     data-options="closed: true, width: 740, height: 400, modal: true, collapsible: false, maximizable: false, minimizable: false, resizable: false" style="display: none;">
    <form id="data-item-edit-form" method="post">
        <input id="data-item-edit-id" name="dataItemId" type="hidden"/>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">中文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,50]']" id="data-item-name" name="dataItemName" >
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">排序号：</div>
                <input  class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true, missingMessage: '请输入排序号。'"
                        id="data-item-sequence-no" name="sequenceNo">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">数据项类型：</div>
                <select class="easyui-combotree" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择数据项类型。'"
                        id="data-item-dataItemType" name="dataItemType"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">数据项表达式：</div>
                <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入数据项表达式。', validType:'length[1,100]'"
                       id="data-item-dataItemExps" name="dataItemExps">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">空值替代内容：</div>
                <input class="easyui-textbox" style="width:200px" data-options="validType:'length[1,10]'"
                       id="data-item-nullReplaceChar" name="nullReplaceChar">
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">是否循环填报数据项：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否循环填报数据项。'"
                        id="data-item-loop-segment" name="loopItem">
                </select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">循环填报内容分隔类型：</div>
                <select class="easyui-combotree" style="width:200px;" data-options="editable: false, missingMessage: '请选择循环填报内容分隔类型。'"
                        id="data-item-splitCharType" name="splitCharType"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">循环填报内容分隔表达式：</div>
                <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入循环填报内容分隔表达式。', validType:'length[1,12]'"
                       id="data-item-splitChar" name="splitChar">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">数据项长度：</div>
                <input  class="easyui-numberbox" style="width:200px" data-options="min:1, value:0, missingMessage: '请输入数据项长度。', prompt: '默认0表示无限制'"
                        id="data-item-data-length" name="dataLength">
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">循环填报映射SQL：</div>
                <select class="easyui-combotree" style="width:200px;"  id="data-item-relation-sql" name="relationSqlId"></select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">定长补位位置：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, missingMessage: '请选择定长补位位置。'"
                        id="data-item-fixLocation" name="fixLocation"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">定长补位字符类型：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, missingMessage: '请选择定长补位字符类型。'"
                        id="data-item-fixCharType" name="fixCharType"></select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">定长补位字符表达式：</div>
                <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入定长补位字符表达式。', validType:'length[1,2]'"
                       id="data-item-fixChar" name="fixChar">
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">数据项说明：</div>
                <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'" style="width:553px;height:60px"
                       id="data-item-comment" name="comnt">
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="data-item-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="data-item-close-btn">取消</a>
    </div>
</div>
<%--        报文文件结构段数据项综合配置窗口 end           --%>

<%--        报文SQL综合配置窗口 start: 361            --%>
<div id="report-sql-win" class="easyui-window" title="SQL信息配置"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_411'"
     style="display: none;">
    <div class="easyui-layout" data-options="fit: true">
        <div data-options="region:'north', border: false" style="height:30px;">
            <a id="add-report-sql" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain: true">新增SQL</a>
            <a id="edit-report-sql" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain: true">更新SQL</a>
            <a id="remove-report-sql" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain: true">删除SQL</a>
            <a id="edit-report-sql-param" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'pic_411', plain: true">SQL参数配置</a>
            <a id="edit-report-sql-metadata" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'pic_411', plain: true">元数据配置</a>
        </div>
        <div data-options="region:'west',title:'SQL树', collapsible: false" style="width:300px;">
            <ul id="branch-tree"></ul>
        </div>
        <div data-options="region:'center',title:'SQL详情'">
            <div class="easyui-layout" data-options="border:false, fit: true">
                <div data-options="region:'center',border:false">
                    <div style="display: none" class="sql-edit-detail">
                        <form id="report-sql-edit-form" method="post">
                            <input type="hidden" id="sql-edit-parentSqlId" name="pareSqlId"/>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-label">中文名称：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。', validType:['cnName', 'length[1,50]']"
                                           id="sql-edit-name" name="sqlName">
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-label">所属数据源：</div>
                                    <select class="easyui-num" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择所属数据源。'"
                                            id="sql-edit-dataSourceId" name="dataSourceId">
                                    </select>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div>
                                    <div class="edit-win-div-row-column-label">SQL：</div>
                                    <input class="easyui-textbox" data-options="required: true, missingMessage: '请输入SQL定义语句。',multiline:true,validType:'length[1,20000]',
                                      prompt: '注：校验SQL语句时会使用的别名有 temp_table_name_1、temp_table_name_2、temp_row_number_1。'" style="width:553px;height:240px" id="sql-edit-sqlExps" name="sqlExps">
                                </div>
                                <div style="margin-top: 5px;">
                                    <div class="edit-win-div-row-column-label">SQL说明：</div>
                                    <input class="easyui-textbox" data-options="multiline:true,validType:'length[1,200]'" style="width:553px;height:30px"
                                           id="sql-edit-comment" name="comnt">
                                </div>
                                <div style="margin-top: 5px;">
                                    <div class="edit-win-div-row-column-label">SQL_ID：</div>
                                    <input class="easyui-textbox" data-options="readonly:true" style="width:553px;height:30px" id="sql-edit-id" name="sqlId" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div data-options="region:'south', border:false" style="height: 40px;">
                    <div style="height: 30px;margin-bottom: 10px; padding-right: 20px; text-align: right; display: none;" class="sql-edit-Btn">
                        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="sql-edit-save-btn">保存</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--        报文SQL综合配置窗口 end            --%>

<%--        报文SQL参数综合配置窗口 start: 425           --%>
<div id="sql-param-data-grid-win" class="easyui-window" title="SQL参数配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_411'"
     style="display: none;">
    <table id="sql-param-data-grid"></table>
</div>
<div id="sql-param-edit-win" class="easyui-window" title="SQL参数配置"
     data-options="closed: true, width: 740, height: 265, modal: true, collapsible: false, maximizable: false, minimizable: false, resizable: false" style="display: none;">
    <form id="sql-param-edit-form" method="post">
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">参数英文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入参数英文名称。',
                    validType:['enName', 'length[1,20]']" id="sql-param-edit-paramEnName" name="paramEnName" >
            </div>
            <input type="hidden" id="sql-param-edit-oldParamEnName" name="oldParamEnName"/>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">参数中文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入参数中文名称。',
                    validType:['cnName', 'length[1,50]']" id="sql-param-edit-paramCnName" name="paramCnName" >
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">参数类型：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择参数类型。'"
                        id="sql-param-edit-paramType" name="paramType">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">参数占位字段所属SQL ID：</div>
                <select class="easyui-combotree" style="width:200px;"  id="sql-param-edit-replaceSqlId" name="replaceSqlId"></select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">参数占位字段名称：</div>
                <select class="easyui-combobox" style="width:200px;" data-options="editable: false, missingMessage: '请选择占位字段名称。'"
                        id="sql-param-edit-replaceField" name="replaceField">
                </select>
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">参数说明：</div>
                <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'" style="width:553px;height:60px"
                       id="sql-param-edit-comment" name="comnt">
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="sql-param-edit-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="sql-param-edit-close-btn">取消</a>
    </div>
</div>
<%--        报文SQL参数综合配置窗口 end            --%>

<%--        报文SQL元数据综合配置窗口 start: 482            --%>
<div id="sql-metadata-data-grid-win" class="easyui-window" title="SQL元数据配置信息"
     data-options="closed: true, width: 700, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_411'"
     style="display: none;">
    <table id="sql-metadata-data-grid"></table>
</div>
<div id="sql-metadata-edit-win" class="easyui-window" title="SQL元数据配置"
     data-options="closed: true, width: 640, height: 130, modal: true, collapsible: false, maximizable: false, minimizable: false, resizable: false" style="display: none;">
    <form id="sql-metadata-edit-form" method="post">
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">英文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="readonly:true"
                       id="sql-metadata-edit-fieldEnName" name="fieldEnName" >
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-label">中文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。', validType:['cnName', 'length[1,200]']"
                       id="sql-metadata-edit-fieldCnName" name="fieldCnName" >
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="sql-metadata-edit-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="sql-metadata-edit-close-btn">取消</a>
    </div>
</div>
<%--        报文SQL元数据综合配置窗口 end            --%>

<%--        报文xml标签综合配置窗口 start: 511           --%>
<div id="xml-label-edit-win" class="easyui-window" title="xml标签配置"
     data-options="closed: true, width: 1050, height: 505, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'icon-xml'"
     style="display: none;">
    <div class="easyui-layout" data-options="fit: true">
        <div data-options="region:'north', border: false" style="height:30px;">
            <a id="add-xml-label" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain: true">新增标签</a>
            <a id="edit-xml-label" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit', plain: true">更新标签</a>
            <a id="remove-xml-label" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain: true">删除标签</a>
            <a id="remove-xml-label-attribute" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xml', plain: true">标签属性配置</a>
            <a id="remove-xml-label-dataItem" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xml', plain: true">数据项配置</a>
        </div>
        <div data-options="region:'west',title:'xml标签树', collapsible: false" style="width:300px;">
            <ul id="xml-label-branch-tree"></ul>
        </div>
        <div data-options="region:'center',title:'xml标签详情'">
            <div class="easyui-layout" data-options="border:false, fit: true">
                <div data-options="region:'center',border:false">
                    <div style="display: none" class="xml-label-edit-detailAndBtn">
                        <form id="xml-label-edit-form" method="post">
                            <input type="hidden" id="xml-label-edit-id" name="labelId"/>
                            <input type="hidden" id="xml-label-parentLabelId" name="parentLabelId"/>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否文本结构段：</div>
                                    <select class="easyui-combobox"  style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否文本结构段。'"
                                            id="xml-label-textSegment" name="textSegment">
                                    </select>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">排序号：</div>
                                    <input  class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true, missingMessage: '请输入排序号。'"
                                            id="xml-label-sequence-no" name="sequenceNo">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">中文名称：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。', validType:['cnName', 'length[1,50]']"
                                           id="xml-label-name-cn" name="labelNameCn">
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">英文名称：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入英文名称。', validType:['enName', 'length[1,20]']"
                                           id="xml-label-name-en" name="labelNameEn">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">标签值类型：</div>
                                    <select class="easyui-combotree" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择标签值类型。'"
                                            id="xml-label-labelValueType" name="labelValueType"></select>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">标签值表达式：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入标签值表达式。', validType:'length[1,100]'"
                                           id="xml-label-labelValueExps" name="labelValueExps">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否换行：</div>
                                    <select class="easyui-combobox" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否换行。'"
                                            id="xml-label-lineBreak" name="lineBreak">
                                    </select>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">换行符表达式：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入换行符表达式。',
                                    validType:'length[1,5]'" id="xml-label-lineBreakChar" name="lineBreakChar" >
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">分隔类型：</div>
                                    <select class="easyui-combobox" style="width:200px;" data-options="editable: false, missingMessage: '请选择分隔类型。'"
                                            id="xml-label-splitType" name="splitType"></select>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">分隔符类型：</div>
                                    <select class="easyui-combobox" style="width:200px;" data-options="editable: false,readonly: true, missingMessage: '请选择分隔符类型。'"
                                            id="xml-label-splitCharType" name="splitCharType"></select>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">分隔符表达式：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="readonly: true, missingMessage: '请输入分隔符表达式。',
                                    validType:'length[1,12]'" id="xml-label-splitChar" name="splitChar" >
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否主要填报内容标签：</div>
                                    <select class="easyui-combotree" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否主要填报内容标签。'"
                                            id="xml-label-mainLabel" name="mainLabel"></select>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否循环标签：</div>
                                    <select class="easyui-combobox"  style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否循环标签。'"
                                            id="xml-label-loopLabel" name="loopLabel">
                                    </select>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">关联SQL：</div>
                                    <select class="easyui-combotree" style="width:200px;"  id="xml-label-relation-sql" name="relationSqlId"></select>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">循环填报内容分隔符类型：</div>
                                    <select class="easyui-combotree" style="width:200px;" data-options="editable: false, missingMessage: '请选择是否循环填报内容分隔符类型。'"
                                            id="xml-label-loopSplitCharType" name="loopSplitCharType"></select>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">循环填报内容分隔符：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入循环填报内容分隔符。', validType:'length[1,12]'"
                                           id="xml-label-loopSplitChar" name="loopSplitChar">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">空值替代内容：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="validType:'length[1,10]'"
                                           id="xml-label-nullReplaceChar" name="nullReplaceChar">
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">标签体闭合方式：</div>
                                    <select class="easyui-combotree" style="width:200px;" data-options="editable: false, missingMessage: '请选择标签体闭合方式。'"
                                            id="xml-label-labelCloseType" name="labelCloseType"></select>
                                </div>
                            </div>
                            <div style="margin-top: 5px; height: 30px;">
                                <div>
                                    <div class="edit-win-div-row-column-long-label">报文说明：</div>
                                    <input class="easyui-textbox" data-options="multiline:true,validType:'length[1,200]'" style="width:553px;height:30px"
                                           id="xml-label-comment" name="comnt">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div data-options="region:'south', border:false" style="height: 40px;">
                    <div style="height: 30px;margin-bottom: 10px; padding-right: 20px; text-align: right; display: none;" class="xml-label-edit-detailAndBtn">
                        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="xml-label-save-btn">保存</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%--        报文xml标签综合配置窗口 end           --%>

<%--        报文xml标签属性综合配置窗口 start: 664             --%>
<div id="xml-label-property-data-grid-win" class="easyui-window" title="xml标签配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false"
     style="display: none;">
    <table id="xml-label-property-data-grid"></table>
</div>
<div id="label-property-edit-win" class="easyui-window" title="xml标签属性配置"
     data-options="closed: true, width: 740, height: 265, modal: true, collapsible: false, maximizable: false, minimizable: false, resizable: false" style="display: none;">
    <form id="label-property-edit-form" method="post">
        <input id="label-property-edit-id" name="propertyId" type="hidden"/>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">中文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,50]']" id="label-property-name-cn" name="propertyNameCn" >
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">英文名称：</div>
                <input class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入英文名称。',
                    validType:['enName', 'length[1,20]']" id="label-property-name-en" name="propertyNameEn" >
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">属性值类型：</div>
                <select class="easyui-combotree" style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择属性值类型。'"
                        id="label-property-propertyValueType" name="propertyValueType"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">属性值表达式：</div>
                <input class="easyui-textbox" style="width:200px" data-options="missingMessage: '请输入属性值表达式。', validType:'length[1,100]'"
                       id="label-property-propertyValueExps" name="propertyValueExps">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">排序号：</div>
                <input  class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true, missingMessage: '请输入排序号。'"
                        id="label-property-sequence-no" name="sequenceNo">
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">空值替代内容：</div>
                <input class="easyui-textbox" style="width:200px" data-options="validType:'length[1,10]'"
                       id="label-property-nullReplaceChar" name="nullReplaceChar">
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">属性说明：</div>
                <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'" style="width:553px;height:60px"
                       id="label-property-comment" name="comnt">
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="label-property-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="label-property-close-btn">取消</a>
    </div>
</div>
<%--        报文xml标签属性综合配置窗口 end           --%>

<%--        报文文件夹综合配置窗口 start: 725            --%>
<div id="report-folder-data-grid-win" class="easyui-window" title="文件夹配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_394'"
     style="display: none;">
    <table id="report-folder-data-grid"></table>
</div>
<div id="report-folder-edit-win" class="easyui-window" title="报送文件夹" data-options="closed: true, iconCls:'icon-add'" style="display: none;">
    <form id="report-folder-edit-form" method="post">
        <input id="folder-edit-subject-id" name="subjectId" type="hidden"/>
        <input id="folder-edit-folder-id" name="folderId" type="hidden"/>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">中文名称：</div>
                <input name="folderName" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入中文名称。',
                    validType:['cnName', 'length[1,50]']" id="folder-edit-folder-name">
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">单一类型报文：</div>
                <select class="easyui-combobox" name="singleTypeFileFlag"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择是否单一类型报文。'"
                        id="folder-edit-single-type-file-flag">
                </select>
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">压缩方式：</div>
                <select class="easyui-num" name="zipManner" id="folder-edit-zip-manner"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选择报送类型。'">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">压缩包大小限制：</div>
                <input name="zipMaxSize" class="easyui-numberbox" style="width:200px" data-options="min:0, prompt: '单位：M', value:0"
                       id="folder-edit-zip-max-size">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">关联SQL：</div>
                <select class="easyui-combotree" style="width:200px;"  id="folder-edit-relation-sql" name="relationSqlId"></select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">排序号：</div>
                <input name="sequenceNo" class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true, missingMessage: '请输入排序号。'"
                       id="folder-edit-sequence-no">
            </div>
        </div>
        <div class="edit-win-div-row">
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">机构字段映射类型：</div>
                <select class="easyui-combobox" name="orgFieldType"
                        style="width:200px;" data-options="editable: false, required: true, missingMessage: '请选机构字段映射类型。'"
                        id="folder-edit-orgFieldType">
                </select>
            </div>
            <div class="edit-win-div-row-column">
                <div class="edit-win-div-row-column-long-label">机构字段映射表达式：</div>
                <input name="orgFieldExps" class="easyui-textbox" style="width:200px" data-options="required: true, missingMessage: '请输入机构字段映射表达式。',
                 validType:'length[0,100]'" id="folder-edit-orgFieldExps">
            </div>
        </div>
        <div style="margin-top: 5px; height: 60px;">
            <div>
                <div class="edit-win-div-row-column-long-label">文件夹说明：</div>
                <input class="easyui-textbox" data-options="multiline:true,validType:'length[0,200]'"  name="comnt"
                       style="width:553px;height:60px" id="folder-edit-comment">
            </div>
        </div>
    </form>
    <div style="margin-top: 10px; padding-right: 20px; text-align: right;">
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-ok" id="folder-edit-save-btn">保存</a>
        <a href="javascript: void(0)" class="easyui-linkbutton" icon="icon-cancel" id="folder-edit-close-btn">取消</a>
    </div>
</div>
<%--        报文文件夹综合配置窗口 start: end            --%>