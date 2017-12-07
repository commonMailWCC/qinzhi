package com.qinzhi.bean;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI ComboTree Created by frank on 16-1-15.
 */
public class ComboTree {

	public static final String CLOSED = "closed";
	public static final String OPEN = "open";
	private String id;
	private String text;
	private String state;
	private String pid;
	private boolean checked;
	private String attributes;
	// private String clickFlag;
	private List<ComboTree> children;

	public ComboTree() {
		super();
		this.setId("0");
		this.setText(null);
		// this.setState(OPEN);
		this.setChildren(new ArrayList<ComboTree>());
	}

	public void clear() {
		this.setId(null);
		this.setText(null);
		this.setChildren(null);
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void addChildren(ComboTree ct) {
		this.children.add(ct);
		// System.out.println("add");
	}

	// 添加一个节点
	public boolean addNode(ComboTree ct) {

		// 如果需要添加的节点的PID 为当前节点的ID, 则直接
		// 添加, 返回true
		if (StringUtils.equals(this.id, ct.pid)) {
			this.children.add(ct);
			this.state = CLOSED;
			return true;
		}
		// 委托给子节点
		for (ComboTree cct : this.children) {
			if (cct.addNode(ct))
				return true;
		}
		// 无法添加成功
		return false;
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

	public List<ComboTree> getChildren() {
		return children;
	}

	public void setChildren(List<ComboTree> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

}
