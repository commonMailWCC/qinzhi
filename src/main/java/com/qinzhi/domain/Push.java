package com.qinzhi.domain;

import java.io.Serializable;
import java.util.Date;

public class Push implements Serializable {
	private static final long serialVersionUID = -7024123549997464L;
	private Integer id;

	private String platform;

	private String target;

	private String targetDesc;

	private Integer sendTime;

	private String sendDesc;

	private Integer constantSpeed;

	private Integer constantDesc;

	private String message;

	private Date createTime;

	private Integer rows;
	private Integer page;
	private Integer start;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target == null ? null : target.trim();
	}

	public String getTargetDesc() {
		return targetDesc;
	}

	public void setTargetDesc(String targetDesc) {
		this.targetDesc = targetDesc == null ? null : targetDesc.trim();
	}

	public Integer getSendTime() {
		return sendTime;
	}

	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendDesc() {
		return sendDesc;
	}

	public void setSendDesc(String sendDesc) {
		this.sendDesc = sendDesc == null ? null : sendDesc.trim();
	}

	public Integer getConstantSpeed() {
		return constantSpeed;
	}

	public void setConstantSpeed(Integer constantSpeed) {
		this.constantSpeed = constantSpeed;
	}

	public Integer getConstantDesc() {
		return constantDesc;
	}

	public void setConstantDesc(Integer constantDesc) {
		this.constantDesc = constantDesc;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
		setStart((page - 1) * rows);
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}