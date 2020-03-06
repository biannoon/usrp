var showMessage = function (message) {
    $.messager.alert('提示信息', message, 'info');
}

var showErrorMessage = function (message) {
    $.messager.alert('错误提示', message, 'error');
}

var showWarningMessage = function (message) {
    $.messager.alert('提示信息', message, 'warning');
}

var selectivePrompt = function (isOk, message) {
    if (isOk) {
        showMessage(message);
    } else {
        showErrorMessage(message);
    }
}

var showSubmitProgress = function() {
    showProgress('提示', '正在校验数据，请稍候...', '');
}

var showLoadingProgress = function () {
    showProgress('提示', '正在加载，请稍候...', '');
}

var showProgress = function (title, msg, text) {
    $.messager.progress({
        title: title,
        msg: msg,
        text: text
    });
}

var closeProgress = function() {
    $.messager.progress('close');
}