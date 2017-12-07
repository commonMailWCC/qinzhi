package com.qinzhi.bean;

public class TreeNode extends Tree {
	/**
	 * 父节点ID
	 */
	private Long pid;
	
	/**
	 * @fields catgTypeId: 栏目的等级
	 */
	private String catgLevelCode;
	
	private String catgStatus;//栏目状态
	
	private Boolean isShowCheckBox;
	
	private String alias;

	public TreeNode(Long id, Long pid, String text) {
		super(id, text);
		this.pid = pid;
	}

	public TreeNode(Long id, Long pid, String text, Boolean expanded) {
		super(id, text, expanded);
		this.pid = pid;
	}
	
	public TreeNode(Long id, Long pid, String text, Boolean expanded, String catgTypeId) {
		super(id, text, expanded);
		this.pid = pid;
		this.catgLevelCode=catgTypeId;
	}
	
	public TreeNode(Long id, Long pid, String text, Boolean expanded, String catgTypeId, String catgStatus) {
		super(id, text, expanded);
		this.pid = pid;
		this.catgLevelCode=catgTypeId;
		this.catgStatus=catgStatus;
	}
	
	public TreeNode(Long id, Long pid, String text, Boolean expanded, String catgTypeId, String catgStatus, Boolean isShowCheck) {
		super(id, text, expanded,isShowCheck);
		this.pid = pid;
		this.catgLevelCode=catgTypeId;
		this.catgStatus=catgStatus;
	}
	
	public TreeNode(Long id, Long pid, String text, Boolean expanded, String catgTypeId, String catgStatus, String alias) {
		super(id, text, expanded);
		this.pid = pid;
		this.catgLevelCode=catgTypeId;
		this.catgStatus=catgStatus;
		this.alias = alias;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getCatgLevelCode() {
		return catgLevelCode;
	}

	public void setCatgLevelCode(String catgLevelCode) {
		this.catgLevelCode = catgLevelCode;
	}

	public String getCatgStatus() {
		return catgStatus;
	}

	public void setCatgStatus(String catgStatus) {
		this.catgStatus = catgStatus;
	}

	public Boolean getIsShowCheckBox() {
		return isShowCheckBox;
	}

	public void setIsShowCheckBox(Boolean isShowCheckBox) {
		this.isShowCheckBox = isShowCheckBox;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	
	
}
