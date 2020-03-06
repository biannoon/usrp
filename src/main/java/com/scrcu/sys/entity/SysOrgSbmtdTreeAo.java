package com.scrcu.sys.entity;

import java.util.List;

/**
 * <p>
 * 报送机构树数据传输对象
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/10/12 15:18
 **/
public class SysOrgSbmtdTreeAo {
	/**
	 * 报送机构id
	 **/
	private String orgId;
	/**
	 * 报送机构树类型
	 **/
	private String treeType;
	/**
	 * 报送机构子节点id集合
	 **/
	private List<String> children;
	/**
	 * 源报送机构id
	 * 报送机构维护在查询上级机构中用于存储需维护的报送机构id
	 **/
	private String sourceOrgId;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getTreeType() {
		return treeType;
	}

	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
	}

	public String getSourceOrgId() {
		return sourceOrgId;
	}

	public void setSourceOrgId(String sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}
}
