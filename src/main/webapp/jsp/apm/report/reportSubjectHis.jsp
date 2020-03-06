<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 数据项配置历史信息窗口 start --%>
<div id="data-item-dataHis-grid-win" class="easyui-window" title="数据项配置历史信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false"
     style="display: none;">
    <table id="data-item-dataHis-grid"></table>
</div>
<%-- 文件名配置历史窗口 end   --%>
<div id="report-file-nameHis-data-grid-win" class="easyui-window" title="文件名配置历史版本信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'icon-tip'"
     style="display: none;">
    <table id="report-file-nameHis-data-grid"></table>
</div>
<%-- 文件配置历史信息窗口 start --%>
<div id="report-file-dataHis-grid-win" class="easyui-window" title="文件配置历史信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_338'"
     style="display: none;">
    <table id="report-file-dataHis-grid"></table>
</div>
<%-- 文件夹配置历史信息窗口 start --%>
<div id="report-folderHis-data-grid-win" class="easyui-window" title="文件夹配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_394'"
     style="display: none;">
    <table id="report-folderHis-data-grid"></table>
</div>
<div id="segment-dataHis-grid-win" class="easyui-window" title="结构段配置历史信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_336'"
     style="display: none;">
    <table id="segment-dataHis-grid"></table>
</div>

<%--数据项配置信息内容 start--%>
<div id="data-item-data-grid-win" class="easyui-window" title="数据项配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false"
     style="display: none;">
    <table id="data-item-data-grid"></table>
</div>

<div id="report-sqlHis-win" class="easyui-window" title="SQL信息配置历史"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_411'"
     style="display: none;">
    <div class="easyui-layout" data-options="fit: true">
        <div data-options="region:'north', border: false" style="height:30px;">
            <a id="edit-report-sql-paramHis" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'pic_411', plain: true">SQL参数配置</a>
            <a id="edit-report-sql-metadataHis" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'pic_411', plain: true">元数据配置</a>
        </div>
        <div data-options="region:'west',title:'SQL树', collapsible: false" style="width:300px;">
            <ul id="branchHis-tree"></ul>
        </div>
        <div data-options="region:'center',title:'SQL详情'">
            <div class="easyui-layout" data-options="border:false, fit: true">
                <div data-options="region:'center',border:false">
                    <div style="display: none" class="sql-edit-detailAndBtnHis">
                        <form id="report-sqlHis-edit-form" method="post">
                           <!--<input type="hidden" id="sql-edit-id" name="sqlId"/>
                            <input type="hidden" id="sql-edit-parentSqlId" name="pareSqlId"/>-->
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-label">中文名称：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="required: true,readonly:true, validType:'length[1,50]'"
                                            name="sqlName">
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-label">所属数据源：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false, required: true,readonly:true, validType:'length[1,50]'"
                                           name="dataSourceId">
                                    </input>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div>
                                    <div class="edit-win-div-row-column-label">SQL：</div>
                                    <input class="easyui-textbox" data-options="required: true,readonly:true,multiline:true,validType:'length[1,2000]'"
                                           style="width:553px;height:240px"  name="sqlExps">
                                </div>
                                <div style="margin-top: 5px;">
                                    <div class="edit-win-div-row-column-label">SQL说明：</div>
                                    <input class="easyui-textbox" data-options="multiline:true,readonly:true,validType:'length[1,200]'" style="width:553px;height:60px"
                                            name="comnt">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<%-- sql 参数配置 start --%>
<div id="sql-param-dataHis-grid-win" class="easyui-window" title="SQL参数配置历史"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_411'"
     style="display: none;">
    <table id="sql-param-dataHis-grid"></table>
</div>
<%-- sql 参数配置 end --%>
<div id="sql-metadataHis-data-grid-win" class="easyui-window" title="SQL元数据配置信息"
     data-options="closed: true, width: 700, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'pic_411'"
     style="display: none;">
    <table id="sql-metadataHis-data-grid"></table>
</div>

<div id="xml-label-editHis-win" class="easyui-window" title="xml标签配置"
     data-options="closed: true, width: 1050, height: 505, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false,iconCls:'icon-xml'"
     style="display: none;">
    <div class="easyui-layout" data-options="fit: true">
        <div data-options="region:'north', border: false" style="height:30px;">
            <a id="remove-xml-labelHis-attribute" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xml', plain: true">标签属性配置</a>
            <a id="remove-xml-labelHis-dataItem" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xml', plain: true">数据项配置</a>
        </div>
        <div data-options="region:'west',title:'xml标签树', collapsible: false" style="width:300px;">
            <ul id="xml-label-branchHis-tree"></ul>
        </div>
        <div data-options="region:'center',title:'xml标签详情'">
            <div class="easyui-layout" data-options="border:false, fit: true">
                <div data-options="region:'center',border:false">
                    <div style="display: none" class="xml-label-edit-detailAndBtnHis">
                        <form id="xml-label-editHis-form" method="post">
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否文本结构段：</div>
                                    <input class="easyui-textbox"  style="width:200px;" data-options="editable: false, required: true,readonly:true"
                                             name="textSegment">
                                    </input>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">排序号：</div>
                                    <input  class="easyui-numberbox" style="width:200px" data-options="min:0, value:0, required: true,readonly:true"
                                             name="sequenceNo">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">中文名称：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="required: true,readonly:true,validType:'length[1,50]'"
                                            name="labelNameCn">
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">英文名称：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="readonly:true, validType:'length[1,20]'"
                                           name="labelNameEn">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">标签值类型：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false, required: true,readonly:true"
                                             name="labelValueType"></input>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">标签值表达式：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="readonly:true, validType:'length[1,100]'"
                                           name="labelValueExps">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否换行：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false, required: true,readonly:true"
                                             name="lineBreak">
                                    </input>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">换行符表达式：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="readonly:true,
                                    validType:'length[1,5]'"  name="lineBreakChar" >
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">分隔类型：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false,readonly:true"
                                            name="splitType"></input>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">分隔符类型：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false,readonly: true,readonly:true"
                                             name="splitCharType"></input>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">分隔符表达式：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="readonly: true,readonly:true,
                                    validType:'length[1,12]'"  name="splitChar" >
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否主要填报内容标签：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false, required: true,readonly:true"
                                            name="mainLabel"></input>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">是否循环标签：</div>
                                    <input class="easyui-textbox"  style="width:200px;" data-options="required: true,readonly:true"
                                            name="loopLabel">
                                    </input>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">关联SQL：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="required:true,readonly:true"  name="relationSqlId"></input>
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">循环填报内容分隔符类型：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false,readonly:true"
                                             name="loopSplitCharType"></input>
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">循环填报内容分隔符：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="readonly:true, validType:'length[1,12]'"
                                            name="loopSplitChar">
                                </div>
                            </div>
                            <div class="edit-win-div-row">
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">空值替代内容：</div>
                                    <input class="easyui-textbox" style="width:200px" data-options="readonly:true,validType:'length[1,10]'"
                                           name="nullReplaceChar">
                                </div>
                                <div class="edit-win-div-row-column">
                                    <div class="edit-win-div-row-column-long-label">标签体闭合方式：</div>
                                    <input class="easyui-textbox" style="width:200px;" data-options="editable: false,readonly:true"
                                             name="labelCloseType"></input>
                                </div>
                            </div>
                            <div style="margin-top: 5px; height: 30px;">
                                <div>
                                    <div class="edit-win-div-row-column-long-label">报文说明：</div>
                                    <input class="easyui-textbox" data-options="readonly:true,multiline:true,validType:'length[1,200]'" style="width:553px;height:30px"
                                            name="comnt">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--标签属性配置类容 start--%>
<div id="xml-label-property-dataHis-grid-win" class="easyui-window" title="xml标签配置信息"
     data-options="closed: true, width: 1000, height: 500, modal: true, collapsible: false, maximizable: true, minimizable: false, resizable: false"
     style="display: none;">
    <table id="xml-label-property-dataHis-grid"></table>
</div>