$(function(){
    //获取当前系统时间
    setInterval("GetTime()", 1000);
    //初始化accordion
    jQuery("#westAccordion").accordion({
        fillSpace:true,
        fit:true,
        border:false,
        animate:false
    });
    //获取第一层目录
    $.post("SysFunc/getTreeMenu", { "treeId": "SYS01" }, function(data){
        if (data == "0") {
            window.location = "/Account";
        }
        //循环创建手风琴的项
        $.each(data, function (i, e) {
            var id = e.id;
            $('#westAccordion').accordion('add', {
                title: e.text,
                content: "<ul id='tree"+id+"' ></ul>",
                selected: true,
                iconCls: e.iconCls
            });
            $.parser.parse();
            //循环创建树的项
            $.post("SysFunc/getTreeMenu?treeId="+id, function(data){
                $("#tree" + id).tree({
                    data: data,
                    onBeforeExpand:function(node){
                        $("#tree"+id).tree('options').url="SysFunc/getTreeMenu?treeId="+node.id;
                    },
                    onClick : function(node){
                        if (node.state == 'closed'){
                            $(this).tree('expand', node.target);
                        }else if (node.state == 'open'){
                            $(this).tree('collapse', node.target);
                            if(node.attributes != null){
                                addTab(node);
                            }
                        }
                    }
                });
            }, 'json');
        });
    }, "json");

    //为选项卡绑定右键
    $("#tabs").tabs({
        onContextMenu : function(e){
            var subtitle = $(this).text();
            $('#tabs').tabs('select', subtitle);
            e.preventDefault();
            $('#menu').menu('show', {
                left : e.pageX,
                top : e.pageY
            });
            return false;
        }
    });
    //绑定右键菜单事件
    //刷新
    $("#m-refresh").click(function(){
        //获取选中的标签项
        var currTab = $('#tabs').tabs('getSelected');
        //获取该选项卡中内容标签（iframe）的src属性
        var url = $(currTab.panel('options').content).attr('src');
        if (url == null) {
            /* 重新设置该标签 */
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    //如果用herf,容易导致样式与主页面重载,导致页面奔溃.
                    content: '<iframe frameborder="0" src="' + url + '" style="height: 100%; width: 100%;" ></iframe>'
                }
            })
        } else {
            /* 重新设置该标签 */
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: '<iframe frameborder="0" src="' + url + '" style="height: 100%; width: 100%;"></iframe>'
                }
            })
        }
    });
    //关闭当前
    $('#m-close').click(function(){
        var ct = $('#tabs').tabs('getSelected').panel('options').title;
        if (ct!="首页") {
            $('#tabs').tabs('close',ct);
        }
    });
    //全部关闭
    $('#m-closeall').click(function(){
        $('.tabs-inner span').each(function(i,n){
            var t = $(n).text();
            if (t!="首页") {
                $('#tabs').tabs('close',t);
            }
        });
    });
    //关闭除当前之外的TAB
    $('#m-closeother').click(function(){
        var ct = $('#tabs').tabs('getSelected').panel('options').title;
        $('.tabs-inner span').each(function(i,n){
            var t = $(n).text();
            if(t!=ct && t!="首页")
                $('#tabs').tabs('close',t);
        });
    });
    //关闭当前右侧的TAB
    $('#m-closeright').click(function(){
        var nextall = $('.tabs-selected').nextAll();
        if(nextall.length==0){
            return false;
        }
        nextall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
            $('#tabs').tabs('close',t);
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#m-closeleft').click(function(){
        var prevall = $('.tabs-selected').prevAll();
        if(prevall.length==0){
            return false;
        }
        prevall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
            if (t!="首页") {
                $('#tabs').tabs('close',t);
            }
        });
        return false;
    });
    //双击关闭
    $('.tabs-inner span').dblclick(function(){
        var subtitle = $(this).text();
        if(subtitle!="首页"){
            $('#tabs').tabs('close',subtitle);
        }
    });
    initPubNotice();
    initBtn();
});

//获取时间并设置格式
function GetTime() {
    var now, hour, min, sec;
    now = new Date();
    hour = getNow(now.getHours());
    min = getNow(now.getMinutes());
    sec = getNow(now.getSeconds());
    $("#crtTime").html(
        now.getFullYear()+"年"+(now.getMonth()+1)+"月"+now.getDate()+"日"+" "+hour+":"+min+":"+sec
    );
}
// 根据当前时间进行格式化转换
function getNow(s) {
    return s < 10 ? '0' + s : s;
}

//中心区域新增选项卡
function addTab(node) {
    //判断此选项卡是否已存在
    var isExists = $('#tabs').tabs('exists', node.text);
    var tabCount = $('#tabs').tabs('tabs').length;
    var bsPath = document.getElementById("bsPath").value;
    var url = node.attributes.url;
    //-跳转url加上菜单编号参数，以获取菜单下的按钮
    if (url.indexOf('?') > 0){
        url = bsPath + url+ '&funcId=' + node.id;
    }else{
        url = bsPath + url + '?funcId=' + node.id;
    }
    if(isExists == true) {
        $('#tabs').tabs('select', node.text);
        return;
    }
    if(tabCount <= 10) {
        //添加选项卡
        $('#tabs').tabs('add', {
            title: node.text,
            content:'<iframe src="'+url+'" width="100%" height="100%" style="border: 0px;"></iframe>',
            closable: true
        });
    }else{
        $.messager.confirm("系统提示","最多可打开10个页面");
    }
}

/**
 * 初始化首页公告显示
 * @author wuyu
 */
function initPubNotice() {
    var baseUrl = document.getElementById("bsPath").value;
    $.ajax({
        url: baseUrl + '/pubNotice/listReleasedPubNotice',
        data: {
            page: 0,
            rows: 3
        },
        type: 'post',
        dataType: 'json',
        success: function (data) {
            var domId = '#pub-notice-';
            for (var i = 1; i <= 3; i++) {
                var row = data.rows[i-1];
                if (row == undefined || row == null) {
                    return;
                }
                var title = row.noticeTitle;
                if (title.length > 12) {
                    title = title.substring(0, 12) + '...';
                }
                var _p = $(domId + i).html('<span>&bull;</span>' + title + ' -- ' + row.relsDt);
                _p.attr('title', row.noticeContent);
            }
        }
    })
}

function initBtn() {
    $('#more-pub-notice-btn').unbind().bind('click', function () {
        var node = {
            text: '公告详情',
            attributes: {url : '/pubNotice/index'}
        }
        addTab(node);
    });
}