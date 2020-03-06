package com.scrcu.common.utils;

import com.scrcu.apm.ascTaskCtl.entity.AscGroup;
import com.scrcu.apm.ascTaskCtl.entity.AscGroup_treeGrid;
import com.scrcu.common.base.TreeNode;
import com.scrcu.common.base.TreeNodeAttribute;
import com.scrcu.common.base.bo.TreeNodeSysLog;
import com.scrcu.sys.entity.*;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tree的工具类
 * 将需要树状展示的业务数据，转化为node节点对象
 * @Author pengjuntao
 * @date 2019/09/10 16:58
 */
public class TreeNodeUtil {

    /**
     * 查询数据转化为node节点：菜单功能数据 ----> 树状节点
     * @param sysFuncList
     * @author pengjuntao
     * @return
     */
    public static List fillFuncTree(List<SysFunc> sysFuncList){
        List<TreeNode> list = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for (SysFunc sysFunc : sysFuncList) {
            TreeNode node = new TreeNode();
            node.setId(sysFunc.getFuncId());
            node.setText(sysFunc.getFuncNm());
            node.setState("SYS0202".equals(sysFunc.getIsLeaf())?"closed":"open");
            TreeNodeAttribute attribute = new TreeNodeAttribute();
            attribute.setSubsystemId(sysFunc.getSubsystemId());
            node.setAttributes(attribute);
            map.put(sysFunc.getFuncId(),node);
        }
        for (SysFunc sysFunc : sysFuncList) {
            if (map.get(sysFunc.getFuncId()) != null)
                list.add(map.get(sysFunc.getFuncId()));
        }
        return list;
    }

    /**
     * 描述：功能菜单转化为树状节点（角色管理时，角色分配功能菜单使用）
     * @param List
     * @return
     */
    public static List fillFuncTreeByRole(List<SysFunc> List){
        List<TreeNode> list = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for (SysFunc sysFunc : List) {
            TreeNode node = new TreeNode();
            node.setId(sysFunc.getFuncId());
            node.setText(sysFunc.getFuncNm());
            node.setState("SYS0202".equals(sysFunc.getIsLeaf())?"closed":"open");
            if (sysFunc.getIconUrl()!= null && sysFunc.getIconUrl().equals("checked")){
                node.setChecked(sysFunc.getIconUrl());//借用属性
            }
            map.put(sysFunc.getFuncId(),node);
        }
        for (SysFunc sysFunc : List) {
            if (sysFunc.getPareFuncId() != null && !"".equals(sysFunc.getPareFuncId())){
                String pid = sysFunc.getPareFuncId();
                TreeNode pNode = map.get(pid);
                TreeNode cNode = map.get(sysFunc.getFuncId());
                pNode.addChildren(cNode);
            }else{
                list.add(map.get(sysFunc.getFuncId()));
            }
        }
        return list;
    }

    /**
     * 描述：子系统信息转化为node节点
     * @param subsystemList
     * @return
     */
    public static List fillFuncTreeBySubSystem(List<SysSubsystem> subsystemList){
        List<TreeNode> nodeList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for (SysSubsystem subsystem : subsystemList){
            TreeNode node = new TreeNode();
            node.setId(subsystem.getSubsystemId());
            node.setText(subsystem.getSubsystemNm());
            node.setState("closed");
            TreeNodeAttribute attribute = new TreeNodeAttribute();
            attribute.setSubsystemId(subsystem.getSubsystemId());
            node.setAttributes(attribute);
            map.put(subsystem.getSubsystemId(),node);
        }
        for (SysSubsystem subsystem : subsystemList) {
           nodeList.add(map.get(subsystem.getSubsystemId()));
        }
        return nodeList;
    }


    /**
     * 查询数据转化为node节点：菜单功能数据 ----> 树状节点
     * @param sysFuncList
     * @param isPareNodeflag 是否只查询所有非叶子节点
     * @author pengjuntao
     * @return
     */
    public static List fillCheckFuncTree(List<SysFunc> sysFuncList,List<SysFunc> menuList,boolean isPareNodeflag){
        List<TreeNode> list = new ArrayList<>();
        Map map = new HashMap<String,TreeNode>();
        TreeNodeAttribute attr = null;
        for (SysFunc sysFunc : sysFuncList) {
            TreeNode node = new TreeNode();
            node.setId(sysFunc.getFuncId());
            node.setText(sysFunc.getFuncNm());
            for(SysFunc s:menuList ){
                if(s.getFuncId().equals(sysFunc.getFuncId())){
                    node.setChecked("true");
                }
            }
            node.setState("SYS0202".equals(sysFunc.getIsLeaf())?"closed":"open");
            //封装自定义属性
            attr = new TreeNodeAttribute();
            if (StringUtils.isNotBlank(sysFunc.getFuncType()))
                attr.setFuncType(sysFunc.getFuncType());
            if (StringUtils.isNotBlank(sysFunc.getPareFuncId()))
                attr.setPareFuncId(sysFunc.getPareFuncId());
            if (StringUtils.isNotBlank(sysFunc.getUrl()))
                attr.setUrl(sysFunc.getUrl());
            if (StringUtils.isNotBlank(sysFunc.getFinlModifr()))
                attr.setFinlModifr(sysFunc.getFinlModifr());
            if (sysFunc.getFinlModfyDt() != null)
                attr.setFinlModfyDt(new SimpleDateFormat("yyyy-MM-dd").format(sysFunc.getFinlModfyDt()));
            node.setAttributes(attr);
            if (isPareNodeflag){
                if (!"open".equals(node.getState()))
                    map.put(sysFunc.getFuncId(),node);
            }else{
                map.put(sysFunc.getFuncId(),node);
            }
        }
        for (SysFunc sysFunc : sysFuncList) {
            if (map.get(sysFunc.getFuncId()) != null)
                list.add((TreeNode) map.get(sysFunc.getFuncId()));
        }
        return list;
    }


    /**
     * 任务组转换为树结构节点
     * @param list
     * @author pengjuntao
     * @return
     */
    public static List fillAscGroupTree(List<AscGroup> list){
        List<TreeNode> ascGroupList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for (AscGroup group : list) {
            TreeNode node = new TreeNode();
            node.setId(group.getGroupId());
            node.setText(group.getGroupName());
            node.setState("closed");
            map.put(group.getGroupId(),node);
        }
        for (AscGroup group : list) {
            /*if (group.getParentGroup() != null && !"".equals(group.getParentGroup()) && !"-1".equals(group.getParentGroup())){
                String pid = group.getParentGroup();
                TreeNode pNode = map.get(pid);
                TreeNode cNode = map.get(group.getGroupId());
                pNode.addChildren(cNode);
            }else{
                ascGroupList.add(map.get(group.getGroupId()));
            }*/
            ascGroupList.add(map.get(group.getGroupId()));
        }
        return ascGroupList;
    }

    /**
     * 描述：任务组转化为treeGrid形式展示
     * @return
     */
    public static List fillAscGroupTreeGrid(List<AscGroup> list){
        List<AscGroup_treeGrid> ascGroupList = new ArrayList<>();
        Map<String,AscGroup_treeGrid> map = new HashMap<>();
        for (AscGroup group : list){
            AscGroup_treeGrid node = new AscGroup_treeGrid();
            node.setGroupId(group.getGroupId());
            node.setGroupName(group.getGroupName());
            node.setGroupSeq(group.getGroupSeq());
            node.setNextDate(group.getNextDate());
            node.setParentGroup(group.getParentGroup());
            node.setState("closed");
            node.setAscGroupState(group.getState());
            map.put(group.getGroupId(),node);
        }
        for (AscGroup group : list){
            ascGroupList.add(map.get(group.getGroupId()));
            /*if (group.getParentGroup() != null && !"-1".equals(group.getParentGroup())){
                AscGroup_treeGrid pNode = map.get(group.getParentGroup());
                AscGroup_treeGrid cNode = map.get(group.getGroupId());
                pNode.addChildren(cNode);
            }else{

            }*/
        }
        return ascGroupList;
    }

    /**
     * 系统标准字典信息转换为树状节点：一级节点
     * @param list 系统字典集合
     * @author pengjuntao
     * @return
     */
    public static List fillSysDictryCdLevel1(List<SysDictryCd> list){
        List<TreeNode> dicList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for(SysDictryCd code : list){
            TreeNode node = new TreeNode();
            node.setId(code.getDictryId());
            node.setText(code.getDictryNm());
            node.setState("SYS0101".equals(code.getCdTyp())?"closed":"open");
            map.put(code.getDictryId(),node);
        }
        for(SysDictryCd code : list){
            dicList.add(map.get(code.getDictryId()));
        }
        return dicList;
    }

    /**
     * 系统标准字典信息转换为树状节点：二级节点
     * @param list 系统字典集合
     * @author pengjuntao
     * @return
     */
    /*public static List fillSysDictryCdLevel2(List<SysDictryCd> list,String id){
        List<TreeNode> dicList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for(SysDictryCd code : list){
            TreeNode node = new TreeNode();
            node.setId(code.getDictryId());
            node.setText(code.getDictryNm());
            map.put(code.getDictryId(),node);
        }
        for(SysDictryCd code : list){
            if(!id.equals(code.getDictryId())){
                if(code.getPareDictryId() != null && !"".equals(code.getPareDictryId()) && !id.equals(code.getPareDictryId())){
                    String pid = code.getPareDictryId();
                    TreeNode pNode = map.get(pid);
                    TreeNode cNode = map.get(code.getDictryId());
                    pNode.addChildren(cNode);
                }else{
                    dicList.add(map.get(code.getDictryId()));
                }
            }
        }
        return dicList;
    }*/

    /**
     * 系统标准字典信息转换为树状节点：一级节点
     * @param list 系统字典集合
     * @author pengjuntao
     * @return
     */
    public static List fillSysDictryCdBySelfTreeType(List<SysDictryCd> list){
        List<TreeNode> dicList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for(SysDictryCd code : list){
            TreeNode node = new TreeNode();
            node.setId(code.getDictryId());
            node.setText(code.getDictryNm());
            node.setState("closed");
            map.put(code.getDictryId(),node);
        }
        for(SysDictryCd code : list){
            dicList.add(map.get(code.getDictryId()));
        }
        return dicList;
    }

    /**
     * 描述：共用组件--自定义机构树转换为树状节点
     * @param list 系统机构信息集合
     * @author pengjuntao
     * @return
     */
    public static List fillSysOrgIndoByTreeType(List<SysOrgSbmtdTree> list){
        List<TreeNode> nodeList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        for (SysOrgSbmtdTree org : list) {
            TreeNode node = new TreeNode();
            node.setId(org.getTreeTyp()+"_"+org.getOrgId());
            node.setText(org.getOrgNm());
            node.setState("closed");
            map.put(org.getTreeTyp()+"_"+org.getOrgId(),node);
        }
        for (SysOrgSbmtdTree org : list) {
            nodeList.add(map.get(org.getTreeTyp()+"_"+org.getOrgId()));
        }
        return nodeList;
    }

    /**
     * 系统机构信息转换为树状节点
     * @param list 系统机构信息集合
     * @author pengjuntao
     * @return
     */
    public static List fillSysOrgInfoTree(List<SysOrganization> list){
        List<TreeNode> nodeList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        TreeNodeAttribute attr = null;
        if (list != null){
            for (SysOrganization org : list) {
                TreeNode node = new TreeNode();
                node.setId(org.getId());
                node.setText(org.getName());
                node.setState("closed");
                attr = new TreeNodeAttribute();
                attr.setSpr_org_id(org.getSuperiorId());
                attr.setCanCheck(org.geteMail());
                attr.setOrg_cd(org.getCode());
                node.setAttributes(attr);
                map.put(org.getId(),node);
            }
            for (SysOrganization org : list) {
                nodeList.add(map.get(org.getId()));
            }
        }
        return nodeList;
    }

    /*public static List fillSysOrgInfoTreeBySelfTreeType(List<SysOrganization> list){
        List<TreeNode> nodeList = new ArrayList<>();
        Map<String,TreeNode> map = new HashMap<>();
        TreeNodeAttribute attr = null;
        if (list != null){
            for (SysOrganization org : list) {
                TreeNode node = new TreeNode();
                node.setId(org.getId());
                node.setText(org.getName());
                node.setState("closed");
                attr = new TreeNodeAttribute();
                attr.setSpr_org_id(org.getSuperiorId());
                attr.setCanCheck(org.geteMail());
                attr.setOrg_cd(org.getCode());
                node.setAttributes(attr);
                map.put(org.getId(),node);
            }
            for (SysOrganization org : list) {
                if (org.getSuperiorId() != null && !"".equals(org.getSuperiorId()) && !"-1".equals(org.getSuperiorId())){
                    String pid = org.getSuperiorId();
                    TreeNode pNode = map.get(pid);
                    TreeNode cNode = map.get(org.getId());
                    if (pNode != null && cNode != null){
                        pNode.addChildren(cNode);
                    }
                }
                nodeList.add(map.get(org.getId()));
            }
        }
        return nodeList;
    }*/







    /**
     * 系统日志转换为树结构节点
     * @param list
     * @author hepengfei
     * @return
     */
    public static List fillSysLogTree(List<SysLog> list){
        List<TreeNodeSysLog> sysLogList = new ArrayList<TreeNodeSysLog>();
        Map<String,TreeNodeSysLog> map = new HashMap<>();
        if (list != null && list.size() > 0){
            for (SysLog sysLog : list) {
                TreeNodeSysLog node = new TreeNodeSysLog();
                node.setSysLogId(sysLog.getSysLogId());
                node.setFileNm(sysLog.getFileNm());
                node.setFileUpdDate(sysLog.getFileUpdDate());
                node.setState(sysLog.getState());
                node.setIp(sysLog.getIp());
                node.setParentId(sysLog.getParentId());
                node.setIsDic(sysLog.getIsDic());
                node.setPort(sysLog.getPort());
                node.setUser(sysLog.getUser());
                node.setPwd(sysLog.getPwd());
                node.setUrl(sysLog.getUrl());
                map.put(sysLog.getSysLogId(),node);
            }
            for (SysLog sysLog : list) {
                if (sysLog.getParentId() != null && !"".equals(sysLog.getParentId()) && !"-1".equals(sysLog.getParentId())){
                    String pid = sysLog.getParentId();
                    TreeNodeSysLog pNode = map.get(pid);
                    TreeNodeSysLog cNode = map.get(sysLog.getSysLogId());
                    pNode.addChildren(cNode);
                }else{
                    sysLogList.add(map.get(sysLog.getSysLogId()));
                }
            }
        }
        return sysLogList;
    }

}

