//系统功能菜单共用js

/**
 * 描述：展开树
 * @param treeId 树ID
 * @author pengjuntao
 */
function toExpandTree(treeId) {
    $('#'+treeId).tree('expandAll');
}
/**
 * 描述：折叠树
 * @param treeId 树ID
 * @author pengjuntao
 */
function toCollapseTree(treeId){
    $('#'+treeId).tree('collapseAll');
}

//判断是否由下级菜单
/**
 * 描述：判断指定节点是否有下一级节点
 * @param Id 指定节点ID
 * @param url 访问的url
 * @param flag 标志：1代表判断指定节点是否存在下一级菜单，2代表判断指定节点是否存在下一级资源
 * @returns {boolean}
 */
function hasNextLvl(id,url,flag){
    var result = false;
    $.ajax({
        async:false,
        url:url,
        type:'post',
        dataType:'json',
        data:{"id":id,"flag":flag},
        success: function (data) {
            var data = JSON.stringify(data);
            var data = eval('(' + data + ')');
            if (data.success){
                result = true;
            }
        },
        error: function () {
            $.messager.alert("提示","请求失败");
        }
    })
    return result;
}

/**
 * 描述：远程校验功能ID的唯一性
 * @param funcId 功能ID
 * @param url 远程url
 * @returns {boolean}
 * @author pengjuntao
 */
function checkId(funcId,url) {
    var flag = false;
    $.ajax({
        async:false,
        url:url,
        type:'post',
        dataType:'json',
        data:{"FUNC_ID":funcId},
        success:function(data){
            if (data == "1"){
                flag = false
            }else{
                flag = true;
            }
        },
        error:function () {
            $.messager.alert("警告","请求失败");
        }
    })
    return flag;
}

/**
 * 关闭窗口
 * @param winId 窗口ID
 * @param treeId 树ID
 * @param dgId 列表ID
 */
function closeWindowFromSysFunc(winId,treeId,dgId) {
    var flag = document.getElementById('close_flag').value;
    if (flag == 'true'){
        if (treeId != null && treeId != ''){
            refreshTreeBySelf(treeId);//刷新树
        }
        if (dgId != null && dgId != ''){
            refreshDataGridBySelf('dg_sysFunc');//刷新数据列表
        }
    }
    $("#"+winId).window('close');
}

/**
 * 自定义校验规则
 * @author pengjuntao
 */
$.extend($.fn.validatebox.defaults.rules,{
    uniqueFuncId:{  //功能ID唯一性校验
        validator:function (value,param) {
            return checkId(value,'sysFunc/CheckSysFuncId');
        },
        message:'功能ID已存在，请重新输入;'
    },
    selectValueRequired:{ //组合框选择项必输校验
        validator:function (value,param) {
            return value != '请选择';
        },
        message:'该选择项为必选项;'
    },
    checkIsLeaf:{ //叶子节点‘是否’校验
        validator:function (value,param) {
            var funcType = $('#funcType_insert').combobox('getValue');
            if (funcType=="SYS0702" && value == ""){
                return false;
            }
            if(funcType!="SYS0702" && value != ''){
                return false;
            }
            return true;
        },
        message:'当功能类型为‘功能菜单’时，‘是否为叶子节点’必须选择;当功能类型不为‘功能菜单’时，‘是否为叶子节点’不能选择;'
    },
    checkUrl:{ //功能url校验
        validator:function (value,param) {
            var funcType = $('#funcType_insert').combobox('getValue');
            var isLeaf = $('#isLeaf_insert').combobox('getValue');
            if (funcType == "SYS0702" && isLeaf=="SYS0201" && value == ""){
                return false;
            }
            if (funcType != 'SYS0702' && value != ""){
                return false;
            }
            return true;
        },
        message:'当功能类型为‘菜单’，且为叶子菜单时，URL不能为空;如功能类型选择为‘功能按钮’，该项不可填写;'
    }
})


/**
 * 描述：初始化文本框组件
 * @param id
 * @param echoValue
 * @param maxlength
 * @param disabled
 * @param width
 * @param height
 */
function initTextboxByFunc(id,echoValue,maxlength,disabled,width,height) {
    $('#'+id).textbox({
        type:'text',
        width:width,
        height:height,
        value:echoValue,
        disabled:disabled
    });
    if (maxlength != 0){
        $('#'+id).textbox('textbox').attr('maxlength',maxlength);
    }
}

/**
 * 描述：初始化组合框组件
 * @param id
 * @param dicType
 * @param echoValue
 * @param disabled
 * @param width
 * @param height
 */
function initComboboxByFunc(id,dicType,echoValue,disabled,width,height){
    $('#'+id).combobox({
        url:basePath + 'dictionary/getSysDictryCdListById?dictryId='+dicType,
        valueField:'dictryId',
        textField:'dictryNm',
        width:width,
        height:height,
        value:echoValue,
        disabled:disabled
    });
}

