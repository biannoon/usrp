/*
 * easy ui combobox 控件初始化
 *
 * 以下 baseUrl = '<%=path%>' 由jsp页面定义
 *
 * 使用之前务必在页面引用当前js之前引入 static/static/js/ajax.js
 * customAjaxSubmit() 为ajax.js 中定义的方法
 *
 * @author wuyu
 */

// combobox 空行。如combobox控件作为查询条件时可以增加一行空行
var _emptyOption = {
    value: '',
    text: '<空>'
};

/**
 * 初始化数据字典下拉框数据
 * @param id easy UI combobox主键id值。
 *      例：'#testId'
 * @param data 需要加载的下拉框值。数据类型为对象数组。
 *      例：[{dictryId: 'id1', dictryNm: 'value1'}, {dictryId: 'id2', dictryNm: 'value2'}]
 * @param needEmptyOption 是否需要加载空行
 * @author wuyu
 */
var initComboboxData = function (id, data, needEmptyOption) {
    var newData = [];
    if (needEmptyOption) {
        var emptyOption = [_emptyOption];
        newData = emptyOption.concat(data);
    } else {
        newData = data;
    }
    // 未定义 combobox 的多选属性，即未设置 multiple: true
    $(id).combobox({
        valueField: 'value',
        textField: 'text',
        panelHeight: '200px',
        data: newData
    });
}

/**
 * 初始化combobox
 * @param ajaxObj ajax 请求信息对象
 * @param domId 需要被初始化的combobox控件的id
 * @param needEmptyOption 是否需要加载空行
 * @author wuyu
 */
var initCombobox = function (ajaxObj, domId, needEmptyOption) {
    customAjaxSubmit(ajaxObj, success, function () {});
    function success(result) {
        initComboboxData(domId, result, needEmptyOption);
    }
}

/**
 * 加载相同数据的combobox控件
 * @param ajaxObj ajax 请求信息对象
 * @param comboBoxAttributes 需要初始化的下拉框属性集合。
 *      例：[{'id': '#testId1', 'needEmptyOption': true},
 *          { 'id': '#testId2', 'needEmptyOption': false}]
 * @author  wuyu
 */
var initRepeatCombobox = function(ajaxObj, comboboxAttributes) {
    customAjaxSubmit(ajaxObj, success, function () {});
    function success(result) {
        for (var i = 0; i < comboboxAttributes.length; i++) {
            var comboboxAttribute = comboboxAttributes[i];
            initComboboxData(comboboxAttribute['id'], result, comboboxAttribute['needEmptyOption'])
        }
    }
}

/**
 * 初始化标准字典代码combobox 控件
 * @param domId 需要被初始化的combobox控件的id
 * @param parentId 标准字典代码所属上级字典代码id
 * @param needEmptyOption 是否需要加载空行
 * @author  wuyu
 */
var initDictionaryCode = function (domId, parentId, needEmptyOption) {
    initCombobox(dictionaryCodeAjaxObj(parentId), domId, needEmptyOption);
}

/**
 * 加载同一标准字典代码数据的多个combobox 控件
 * @param comboboxAttributes 需要初始化的下拉框属性集合。
 *      例：[{'id': '#testId1', 'needEmptyOption': true},
 *          { 'id': '#testId2', 'needEmptyOption': false}]
 * @param parentId 标准字典代码所属上级字典代码id
 * @author  wuyu
 */
var initRepeatDictionaryCode = function (comboboxAttributes, parentId) {
    initRepeatCombobox(dictionaryCodeAjaxObj(parentId), comboboxAttributes);
}

var getComboboxAttributes = function (id, needEmptyOption) {
    return { 'id': id, 'needEmptyOption': needEmptyOption};
}

/**
 * 获取查询标准字典代码的ajax基本信息
 * @param parentId 标准字典代码所属上级字典代码id
 * @returns {{url: string, data: {parentId: *}}}
 * @author wuyu
 */
var dictionaryCodeAjaxObj = function (parentId) {
    return {
        url: baseUrl + '/combobox/initDictionaryCode',
        data: {
            parentId: parentId
        }
    };
}

/**
 * 初始化数据源下拉框
 * @param domId
 * @param needEmptyOption
 */
var initDatabase = function (domId, needEmptyOption) {
    var ajaxObj = {
        url: baseUrl + '/combobox/initDatabase'
    }
    initCombobox(ajaxObj, domId, needEmptyOption);
}

var initRepeatDatabase = function(comboboxAttributes) {
    var ajaxObj = {
        url: baseUrl + '/combobox/initDatabase'
    }
    initRepeatCombobox(ajaxObj, comboboxAttributes);
}

