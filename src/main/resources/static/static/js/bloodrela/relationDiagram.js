var showTableRelationDiagram = function (queryConditionsObj) {
    var _ajaxObj = {
        url: baseUrl + '/difTableRelation/tableRelationDiagram',
        data: queryConditionsObj
    };
    customAjaxSubmit(_ajaxObj, function (result) {
        $('#table-relation-diagram-win').window('open');
        showDiagram('table-relation-diagram', result);
    });
}

var showFieldRelationDiagram = function (queryConditionsObj) {
    var _ajaxObj = {
        url: baseUrl + '/difTableRelation/fieldRelationDiagram',
        data: queryConditionsObj
    };
    customAjaxSubmit(_ajaxObj, function (result) {
        $('#field-relation-diagram-win').window('open');
        showDiagram('field-relation-diagram', result);
    });
}

var showDiagram = function (domId, data) {
    var network = new vis.Network(document.getElementById(domId), data, getCustomOptions());
}

var getCustomOptions = function () {
    return {
        nodes: { // 节点控制
            borderWidth: 1, // 节点边框宽度设置
            borderWidthSelected: 2, // 点击选中时的宽度设置、
            color: {
                border: '#04aec2' // 边框颜色的设置
            }
        },
        edges: { // 关系线控制
            width: 1, // 关系线宽度
            arrows: { // 箭头
                to: {
                    enabled: true, // 箭头是否显示、开启
                    scaleFactor: 0.3, // 箭头的大小
                    type: 'arrow' // 箭头的类型
                }
            },
            font: {
                size: 1,
                color: 'green',
                align: 'horizontal'
            },
            length: 50, // 关系线线长设置，数字较大最好以100位单位修改可看出差异
            dashes: false, // 关系线虚线
            arrowStrikethrough: true, // 关系线与节点处无缝隙
            color: {
                color: 'red', // 关系线颜色设置
                highlight: 'yellow' // 点击关系线时的颜色
            }
        }
    };
}