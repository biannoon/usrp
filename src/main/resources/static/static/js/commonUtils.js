function bindClickEvent(domId, callback) {
    $('#' + domId).unbind().bind('click', callback);
}

function isUndefined(obj) {
    return obj === undefined;
}

function isNull(obj) {
    return obj === null;
}

function isNullObject(obj) {
    return isUndefined(obj) || isNull(obj);
}

function isEmptyChar(char) {
    return isNullObject(char) || char.length == 0;
}

function isEmptyArray(array) {
    return isNullObject(array) || array.length == 0;
}

$.extend($.fn.validatebox.defaults.rules, {
    enName: {
        validator: function(value, param){
            return /^[A-Za-z0-9_]+$/.test(value);
        },
        message: '可输入英文、数字、下划线'
    },
    cnName: {
        validator: function(value, param){
            return /^[A-Za-z0-9_\u4e00-\u9fa5]+$/.test(value);
        },
        message: '可输入中文、英文、数字、下划线'
    }
});