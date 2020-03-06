//通用的ajax提交方法，对返回结果进行弹出框提示
function ajaxUtil(type, url, data, datatype) {
    $.ajax({
        type : type,
        url : url,
        data : data,
        dataType : datatype,
        success : function(data){
            if(data.success){
                $("#dataGrid").datagrid("reload");
                $.messager.alert('提示信息', data.message, 'info');
            }else{
                $.messager.alert("警告", data.message,'error');
            }
        },
        error : function(){
            $.messager.alert("警告","ajax提交报错",'error');
        }
    });
}
//作为window弹出详情列表中form提交的主要方法，会关闭当前window
function ajaxSubmit(type, url, data, datatype) {
    $.ajax({
        type : type,
        url : url,
        data : data,
        dataType : datatype,
        success : function(data){
            if(data.success){
                $("#dataGrid").datagrid("reload");
                closeWin('newWin');
                $.messager.alert('提示信息',data.message,'info');
            }else{
                $.messager.alert("警告",data.message,'error');
            }
        },
        error : function(){
            $.messager.alert("警告","ajax提交报错",'error');
        }
    });
}

/**
 * 自定义Ajax提交
 * @param ajaxObj
 * @param successCallback
 * @param errorCallback
 * @author  wuyu
 */
function customAjaxSubmit(ajaxObj, successCallback, errorCallback) {
    $.ajax({
        url: notEmpty(ajaxObj.url) ? ajaxObj.url : '#',
        type: notEmpty(ajaxObj.type) ? ajaxObj.type : 'post',
        data: notNull(ajaxObj.data) ? ajaxObj.data : {},
        dataType: notEmpty(ajaxObj.dataType) ? ajaxObj.dataType : 'json',
        contentType:
            notEmpty(ajaxObj.contentType) ? ajaxObj.contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
        async: !isFalseValue(ajaxObj.async),
        success: successCallback instanceof Function ? successCallback : defaultSuccess,
        error: errorCallback instanceof Function ? errorCallback : defaultError
    });

    function notNull(value) {
        return  value != undefined && value != null;
    }

    function notEmpty(value) {
        return notNull(value) && value.length > 0;
    }

    function isFalseValue(value) {
        return notNull(value) && value === false;
    }

    function defaultSuccess(result) {}

    function defaultError() {
        showErrorMessage('程序异常。')
    }
}