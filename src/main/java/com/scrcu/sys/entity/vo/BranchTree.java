package com.scrcu.sys.entity.vo;

import com.scrcu.apm.entity.ReportSqlDefined;
import com.scrcu.apm.entity.ReportSqlDefinedHis;
import com.scrcu.apm.entity.ReportXmlLabelDefined;
import com.scrcu.apm.entity.ReportXmlLabelDefinedHis;
import com.scrcu.sys.entity.SysDictryCd;
import com.scrcu.sys.entity.SysOrgSbmtdTree;
import com.scrcu.sys.entity.SysOrganization;

import java.util.List;

/**
 * <p>
 * 前端树形控件封装<br/>
 * jquery easyui tree
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:29
 **/
public class BranchTree {
	/**
	 * id
	 **/
	private String id;
	/**
	 * 显示文本
	 **/
	private String text;
	/**
	 * 开闭状态（closed/opened）
	 **/
	private String state;
	/**
	 * 父级id
	 **/
	private String parentId;
	/**
	 * css样式
	 **/
	private String iconCls;
	/**
	 * 是否选中（true/false）
	 **/
	private boolean checked;
	/**
	 * 子节点集合
	 **/
	private List<BranchTree> children;
	/**
	 * 值
	 **/
	private String value;
	/**
	 * 类型
	 **/
	private String type;
	/**
	 * 标记
	 **/
	private String flag;

	public static final String CLOSE_STATE = "closed";
	public static final String OPENED_STATE = "opened";

	public static final String ICON_CLS_TREE_FILE =  "tree-file";
	private static final String ICON_CLS_TREE_FOLDER =  "tree-folder";
	private static final String ICON_CLS_ICON_PIC_76 =  "pic_76";
	public static final String ICON_CLS_ICON_PIC_411 =  "pic_411";
	public static final String ICON_CLS_ICON_XML =  "icon-xml";

	private static final String FLAG_DICTIONARY_TREE =  "DICTIONARY_TREE";
	public static final String FLAG_VIRTUAL_ROOT_TREE =  "VIRTUAL_ROOT_TREE";

	public BranchTree() {
		this.iconCls = ICON_CLS_ICON_PIC_76;
		this.state = CLOSE_STATE;
	}

	public BranchTree(SysOrganization org) {
		this.id = org.getId();
		this.text = org.getName();
		this.state = CLOSE_STATE;
		this.value = org.getCode();
		this.parentId = org.getSuperiorId();
		this.iconCls = ICON_CLS_ICON_PIC_76;
	}

	public BranchTree(SysDictryCd dictryCd) {
		this.id = dictryCd.getDictryId();
		this.text = dictryCd.getDictryNm();
		this.state = CLOSE_STATE;
		this.parentId = dictryCd.getPareDictryId();
		this.value = dictryCd.getDictryComnt();
		this.iconCls = ICON_CLS_TREE_FOLDER;
		this.flag = FLAG_DICTIONARY_TREE;
	}

	public BranchTree(SysOrgSbmtdTree sysOrgSbmtdTree) {
		this.id = sysOrgSbmtdTree.getOrgId();
		this.text = sysOrgSbmtdTree.getOrgNm();
		this.state = CLOSE_STATE;
		this.parentId = sysOrgSbmtdTree.getSprOrgId();
		this.value = sysOrgSbmtdTree.getOrgId();
		this.iconCls = ICON_CLS_ICON_PIC_76;
		this.type = sysOrgSbmtdTree.getTreeTyp();
	}

	public BranchTree(ReportSqlDefined sqlDefined) {
		this.id = sqlDefined.getSqlId();
		this.text = sqlDefined.getSqlName();
		this.state = CLOSE_STATE;
		this.parentId = sqlDefined.getPareSqlId();
		this.value = sqlDefined.getSqlId();
		this.iconCls = ICON_CLS_ICON_PIC_411;
		this.type = sqlDefined.getSubjectId();
	}
	public BranchTree(ReportSqlDefinedHis sqlDefined) {
		this.id = sqlDefined.getSqlId();
		this.text = sqlDefined.getSqlName();
		this.state = CLOSE_STATE;
		this.parentId = sqlDefined.getPareSqlId();
		this.value = sqlDefined.getSqlId();
		this.iconCls = ICON_CLS_ICON_PIC_411;
		this.type = sqlDefined.getSubjectId();

	}

	public BranchTree(ReportXmlLabelDefined xmlLabelDefined) {
		this.id = xmlLabelDefined.getLabelId();
		this.text = xmlLabelDefined.getLabelNameCn() + "&lt;" + xmlLabelDefined.getLabelNameEn() + "&gt;";
		this.state = CLOSE_STATE;
		this.parentId = xmlLabelDefined.getParentLabelId();
		this.value = xmlLabelDefined.getLabelId();
		this.iconCls = ICON_CLS_ICON_XML;
		this.type = xmlLabelDefined.getTextSegment();
		this.flag = xmlLabelDefined.getLabelCloseType();
	}
	public BranchTree(ReportXmlLabelDefinedHis xmlLabelDefined) {
		this.id = xmlLabelDefined.getLabelId();
		this.text = xmlLabelDefined.getLabelNameCn() + "&lt;" + xmlLabelDefined.getLabelNameEn() + "&gt;";
		this.state = CLOSE_STATE;
		this.parentId = xmlLabelDefined.getParentLabelId();
		this.value = xmlLabelDefined.getLabelId();
		this.iconCls = ICON_CLS_ICON_XML;
		this.type = xmlLabelDefined.getTextSegment();
		this.flag = xmlLabelDefined.getLabelCloseType();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<BranchTree> getChildren() {
		return children;
	}

	public void setChildren(List<BranchTree> children) {
		this.children = children;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
