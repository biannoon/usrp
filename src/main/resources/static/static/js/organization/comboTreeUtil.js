/**
 * 获取选中的id值
 * @param id select dom对象id
 * @returns {string} id值字符串，多个节点时各个id之间由英文逗号分隔。例：id1,id2,id3 。<br/>
 *      未选择机构信息时返回空字符串。
 * @author wuyu
 */
var getSelectedComboTreeIds = function(id) {
    var trees = getSelectedComboTrees(id);
    var ids = [];
    $.each(trees, function(index, tree) {
        ids.push(tree['id']);
    });
    return ids.join(',');
}

/**
 *获取选中的树节点
 * @param id select dom对象id
 * @returns [] tree对象数组
 * @author wuyu
 */
var getSelectedComboTrees = function(id) {
    var trees = [];
    var _o = $(id).combotree('tree');
    // 多选时通过getChecked获取值。getSelected只能获取最后一次选中的值
    var checkedTrees = _o.tree('getChecked');
    if (checkedTrees.length > 0) {
        return checkedTrees;
    }
    // 单选时通过getSelected 获取值。getChecked获取的值为空
    var tree = _o.tree('getSelected');
    if (tree != null) {
        trees.push(tree);
        return trees;
    }
    return trees;
}

/**
 * 初始化机构树（easy ui combotree）
 *
 * @param 需要初始化成机构数的dom对象id值数组。<br/>
 *      例：['#userBranchTree1', '#userBranchTree2', '#userBranchTree3'] 。
 * @param multiple combotree是否启用复选框，value: true/false 。
 * @param cascadeCheck 是否禁用combotree点击父节点时同时全选子节点，value: true/false 。
 * @author wuyu
 */
var initOrganizationComboTree = function(ids, multiple, cascadeCheck) {
    $.ajax({
        //TODO baseUrl
        url: baseUrl + '/organization/findFirstTreeNode',
        type: 'post',
        success: function (data) {
            for (var i = 0; i < ids.length; i++) {
                createOrganizationComboTree(ids[i], multiple, cascadeCheck, data);
            }
        }
    });
}

/**
 * 创建机构树（easy ui combotree）
 *
 * @param id 需要初始化成机构数的dom对象id值。<br/>
 *      例：'#userBranchTree1' 。
 * @param multiple combotree是否启用复选框，value: true/false 。
 * @param cascadeCheck 是否禁用combotree点击父节点时同时全选子节点，value: true/false 。
 * @param data 机构树数据对象集合
 * @author wuyu
 */
var createOrganizationComboTree = function(id, multiple, cascadeCheck, data) {
    $(id).combotree({
        // 是否启用复选框
        multiple: multiple,
        // 是否禁用全选
        cascadeCheck: cascadeCheck,
        data: data,
        onBeforeExpand: function (node) {
            //TODO baseUrl
            $(id).combotree('tree').tree('options').url = baseUrl
                + '/organization/findChildrenTreeNode?parentId=' + node.id;
        }
    });
}