/**
 * 描述：初始化系统字典选择组件（用于文本框的搜索图标的点击事件）
 * @param id            文本框ID
 * @param id_hid        隐藏文本框ID
 * @param dictryId      字典代码类型
 * @param winId         窗口ID
 * @param isLevel
 * @param selectLeafOnly
 * @param checkbox
 * @param width
 * @param height
 */
function initSysDictryComponent(id,
                                id_hid,
                                dictryId,
                                winId,
                                isLevel,
                                selectLeafOnly,
                                checkbox,
                                width,
                                height){
    $('#'+id).textbox({
        onClickButton:function (index) {
            getSysDictryCdList(winId,
                id + '$' + id_hid,
                isLevel,
                selectLeafOnly,
                checkbox,
                dictryId,
                width,
                height);
        }
    })
}

/**
 * 描述：系统共用组件：系统标准字典代码公共组件跳转
 * @param winId             窗口ID
 * @param fieldId           需要回显的字段id 如果存在多个文本框需要回显，id之间用$拼接
 * @param isLevel           是否有层级关系， 0为无层级关系，1为具有层级关系
 * @param selectLeafOnly    如果有层级关系，是否只能选择叶子节点 0为否，1为是
 * @param checkbox          单选/多选，0为单选，1为多选
 * @param dictryId          指定字典类型代码，如果查询全部，则不填写
 * @param width             窗口宽度
 * @param height            窗口高度
 * @author pengjuntao
 */
function getSysDictryCdList(winId,
                            fieldId,
                            isLevel,
                            selectLeafOnly,
                            checkbox,
                            dictryId,
                            width,
                            height) {
    var url = basePath +"common/toSysDictryCd_show?isLevel=" + isLevel +
        "&selectLeafOnly=" + selectLeafOnly +
        "&checkbox=" + checkbox +
        "&dictryId=" + dictryId +
        "&winId=" + winId +
        "&fieldId=" + fieldId;
    openComponentWindow(winId,"标准字典代码",width,height,url);
}

/**
 * 描述：初始化系统机构选择组件（用于文本框的搜索图标的点击事件）
 * @param id
 * @param id_hid
 * @param winId
 * @param treeType
 * @param selfTreeType
 * @param orgLimits
 * @param selectType
 * @param treeNodeSelectType
 * @param expOrgNo
 * @param width
 * @param height
 */
function initSysOrgInfoComponent(id,
                                 id_hid,
                                 winId,
                                 treeType,
                                 selfTreeType,
                                 orgLimits,
                                 selectType,
                                 treeNodeSelectType,
                                 expOrgNo,
                                 width,
                                 height){

    $('#'+id).textbox({
        onClickButton:function (index) {
            getSysOrgInfoList(winId,
                id + '$' + id_hid,
                basePath + 'common/toSysOrgInfoPage',
                treeType,
                selfTreeType,
                orgLimits,
                selectType,
                treeNodeSelectType,
                expOrgNo,
                width,
                height);
        }
    })

}

/**
 * 描述：系统共用组件：机构选择公共组件跳转
 * @param winId                     弹出窗口id
 * @param fieldId                   回显文本框id
 * @param url                       请求url
 * @param treeType                  机构树分类：0为标准机构树,1为自定义机构树
 * @param selfTreeType              自定义机构树类型：机构树分类为‘自定义机构树’时有效；
 *        如机构树分类为‘自定义机构树’，且自定义机构树类型不为空，则展示指定的树节点数据；
 *        如机构树分类为‘自定义机构树’，且自定义机构树类型为空，则表示展示所有类型的自定义机构树；
 * @param orgLimits                 机构权限控制标志：用于确定选择界面的机构树是否根据当前用户的用户组权限来控制机构树中机构节点的范围 “true”为开启，“false”为关闭
 * @param selectType                单选多选标志：0为单选，1为多选
 * @param treeNodeSelectType        机构节点选择模式：当单选多选标志为多选时有效；
 *       0为多选模式下，选择上级节点后，该节点的所有下级节点不可选择
 *       1为不做控制，多选模式下可同时选择上级和下级机构
 * @param expOrgNo                  排除机构编号：填写不需要展示的机构节点以及其所有的下级节点
 *      如果存在多个机构编号，以下划线_分割
 * @return
 * @author pengjuntao
 */
function getSysOrgInfoList(winId,
                           fieldId,
                           url,
                           treeType,
                           selfTreeType,
                           orgLimits,
                           selectType,
                           treeNodeSelectType,
                           expOrgNo,
                           width,
                           height){
    url = url +
        "?treeType=" + treeType +
        "&selfTreeType=" + selfTreeType +
        "&orgLimits=" + orgLimits +
        "&selectType=" + selectType +
        "&treeNodeSelectType=" + treeNodeSelectType +
        "&expOrgNo=" + expOrgNo +
        "&winId=" + winId +
        "&fieldId=" +fieldId;
    openComponentWindow(winId,"机构列表",width,height,url);
}

/**
 * 描述：初始化系统用户选择组件（用于文本框的搜索图标的点击事件）
 * @param id
 * @param id_hid
 * @param winId
 * @param selectType
 * @param roleLimit
 * @param haveRemoved
 * @param orgController
 * @param width
 * @param height
 */
function initSysUserInfoComponent(id,
                                  id_hid,
                                  winId,
                                  selectType,
                                  roleLimit,
                                  haveRemoved,
                                  orgController,
                                  width,
                                  height){
    $('#'+id).textbox({
        onClickButton:function (index) {
            getSysUserInfolist(winId,
                id + '$' + id_hid,
                basePath + 'common/toSysUserInfo',
                selectType,
                roleLimit,
                haveRemoved,
                orgController,
                width,
                height);
        }
    })

}

/**
 * 描述：系统共用组件：系统用户选择（跳转）
 * @param windId            窗口ID
 * @param fieldId           填充文本框ID
 * @param url               跳转url
 * @param selectType        单选/多选   0为单选，1为多选
 * @param roleLimit         角色范围 ,这里为具体角色代码，如果存在多个角色，用“_”分隔
 * @param haveRemoved       是否包含注销状态的用户     true为包含，false为不包含
 * @param orgController     机构范围控制标志    true为开启机构范围控制，false为关闭机构范围控制
 * @author pengjuntao
 */
function getSysUserInfolist(winId,
                            fieldId,
                            url,
                            selectType,
                            roleLimit,
                            haveRemoved,
                            orgController,
                            width,
                            height){
    url = url +
        "?selectType=" + selectType +
        "&roleLimit=" + roleLimit +
        "&haveRemoved=" + haveRemoved +
        "&orgController=" + orgController +
        "&winId=" + winId +
        "&fieldId=" +fieldId;
    openWindowSelf(winId,"用户选择列表",width,height,url);
}


/**
 * 描述：系统共用组件-系统功能菜单资源树
 * @param winId 窗口ID
 * @param url 调整URL
 * @param width 窗口宽度
 * @param height 窗口高度
 * @author pengjuntao
 */
function getSysFuncTree(winId,fieldId,url,width,height){
    url = url + "?winId=" + winId  + "&fieldId=" + fieldId;
    openWindowSelf(winId,"系统功能菜单资源选择",width,height,url);
}

/**
 * 描述：系统共用组件-任务组树
 * @param winId 窗口ID
 * @param url 调整URL
 * @param width 窗口宽度
 * @param height 窗口高度
 * @author pengjuntao
 */
function getAscGroupTree(winId,fieldId,url,width,height){
    url = url + "?winId=" + winId + "&fieldId=" + fieldId;
    openWindowSelf(winId,"任务组选择",width,height,url);
}




function openComponentWindow(winId, tit, width, height, url){
    $("#"+winId).window({
        title: tit,
        href: url,
        width: width,
        height: height,
        left: '100px',
        closed: true,
        closable:false,
        modal: false,
        cache: false,
        minimizable: false,
        maximizable: false,
        collapsible: false,
        shadow: false
    });
    $("#"+winId).window('open');
    $('#'+winId).window('center');
}

/**
 * 描述：子系统选择组件
 * @param winId
 * @param id
 * @param id_hid
 */
function initSubsystemComponent(winId,id,id_hid){
    $('#'+id).textbox({
        onClickButton:function (index) {
            var url = basePath + "jsp/sys/SysRole_subsystem_component.jsp";
            url = url + '?winId=' + winId + '&id=' + id + '&id_hid=' + id_hid;
            openWindowSelf(winId,"子系统列表",'500','500',url);
        }
    })
}
