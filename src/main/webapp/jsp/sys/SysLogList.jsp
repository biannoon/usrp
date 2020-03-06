<%@ page import="com.scrcu.sys.entity.SysLog" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/9/20
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/jsp/tld.jsp" %>
<%
    SysLog log = (SysLog) request.getAttribute("sysLog");
%>
<!DOCTYPE html>
<html>
<head>
    <title>统一监管报送系统</title>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/sys/sysLog.js"></script>
</head>
<body>
<%--<table id="tg" title="系统日志查看" class="easyui-treegrid" style="width:1000px;height:400px"
       data-options="
				iconCls: 'icon-search',
				rownumbers: true,
				animate: false,
				collapsible: true,
				fitColumns: true,
				url: '/usrp/syslog/ListAllSysLog?ip=<%=ip%>',
				method: 'ListAllSysLog',
				idField: 'sysLogId',
				treeField: 'fileNm',
				pagination: true,
				pageSize: 2,
				pageList: [2,5,10]
			">
    <thead>
    <tr>
        <th data-options="field:'cb',checkbox:true"></th>
        <th data-options="field:'fileNm',width:80">文件名</th>
        <th data-options="field:'ip',width:60,align:'right'">服务器</th>
        <th data-options="field:'fileUpdDate',width:60,align:'right'">文件更新时间</th>
        <th data-options="field:'sysLogId',width:60,align:'right'"  hidden="true">日志id</th>
        <th data-options="field:'parentId',width:60,align:'right'" hidden="true">父级id</th>
        <th data-options="field:'state',width:60,align:'right'"  hidden="true">是否展开</th>
        <th data-options="field:'isDic',width:60,align:'right'"  hidden="true">是否为目录</th>
    </tr>
    </thead>
    <tfoot>
   <a href="#" class="easyui-linkbutton" align="right" onclick="doSearch()">下载</a>
    </tfoot>
</table>--%>
<div><table id="tg_sys_log"></table></div>
<script type="text/javascript">
    $(function () {
        var url = basePath + "syslog/ListAllSysLog?ip=<%=log.getIp()%>&port=<%=log.getPort()%>&user=<%=log.getUser()%>&pwd=<%=log.getPwd()%>&url=<%=log.getUrl()%>";
        initSysLog_DG_TREE('tg_sys_log',url);
    })

    /*(function($){
        function pagerFilter(data){
            if ($.isArray(data)){    // is array
                data = {
                    total: data.length,
                    rows: data
                }
            }
            var dg = $(this);
            var state = dg.data('treegrid');
            var opts = dg.treegrid('options');
            var pager = dg.treegrid('getPager');
            pager.pagination({
                onSelectPage:function(pageNum, pageSize){
                    opts.pageNumber = pageNum;
                    opts.pageSize = pageSize;
                    pager.pagination('refresh',{
                        pageNumber:pageNum,
                        pageSize:pageSize
                    });
                    dg.treegrid('loadData',state.originalRows);
                }
            });
           if (!state.originalRows){
                state.originalRows = data.rows;
            }
            var topRows = [];
            var childRows = [];
            $.map(state.originalRows, function(row){
                row._parentId ? childRows.push(row) : topRows.push(row);
            });
            data.total = topRows.length;
            var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
            var end = start + parseInt(opts.pageSize);
           <!-- data.rows = $.extend(true,[],topRows.slice(start, end).concat(childRows))-->;
            return data;
        }

        var appendMethod = $.fn.treegrid.methods.append;
        var loadDataMethod = $.fn.treegrid.methods.loadData;
        $.extend($.fn.treegrid.methods, {
            clientPaging: function(jq){
                return jq.each(function(){
                    var state = $(this).data('treegrid');
                    var opts = state.options;
                    opts.loadFilter = pagerFilter;
                    var onBeforeLoad = opts.onBeforeLoad;
                    opts.onBeforeLoad = function(row,param){
                        state.originalRows = null;
                        onBeforeLoad.call(this, row, param);
                    }
                    $(this).treegrid('loadData', state.data);
                    $(this).treegrid('reload');
                });
            },
            loadData: function(jq, data){
                jq.each(function(){
                    $(this).data('treegrid').originalRows = null;
                });
                return loadDataMethod.call($.fn.treegrid.methods, jq, data);
            },
            append: function(jq, param){
                return jq.each(function(){
                    var state = $(this).data('treegrid');
                    if (state.options.loadFilter == pagerFilter){
                        $.map(param.data, function(row){
                            row._parentId = row._parentId || param.parent;
                            state.originalRows.push(row);
                        });
                        $(this).treegrid('loadData', state.originalRows);
                    } else {
                        appendMethod.call($.fn.treegrid.methods, jq, param);
                    }
                })
            }
        });
    })(jQuery);

    function formatProgress(value){
        if (value){
            var s = '<div style="width:100%;border:1px solid #ccc">' +
                '<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
            '</div>';
            return s;
        } else {
            return '';
        }
    }

    $(function(){
        $('#tg').treegrid('collapseAll');
    })
    function doSearch(){
        var row = $('#tg').treegrid('getSelected');
        <!--var data =  $('#tg').treegrid('getData');-->
        var parent = $('#tg').treegrid('find',row.parentId);
        if(row==null){
            alert("请选择你要下载的文件");
        }else{
            if(row.isDic=='1'){
                alert("请选择目录下的文件");
            }else{
                if(parent==null ||parent==''){
                    if(confirm("确认下载"+row.fileNm)){
                        window.location.href = "/usrp/syslog/downloadSysLogFile?fileNm="+row.fileNm+"&ip="+row.ip;
                    }
                }else{
                    if(confirm("确认下载"+row.fileNm)){
                        window.location.href = "/usrp/syslog/downloadSysLogFile?fileNm="+parent.fileNm+'/'+row.fileNm+"&ip="+row.ip;
                    }

                }

            }

        }
    }
    function toindex() {
       $('h2').style.display = "none";
        $('div1').style.display= "block";
    }*/
</script>
</body>
</html>
