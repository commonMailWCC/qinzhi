package com.qinzhi.domain;

import java.io.Serializable;

public class Level implements Serializable {
	private static final long serialVersionUID = -7049997464L;
	
	
	private Long id;

	private String levelName;

	private String levelImage;

	private String levelWelfare;
	
	private String levelLimit;

	private String levelDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName == null ? null : levelName.trim();
	}

	public String getLevelImage() {
		return levelImage;
	}

	public void setLevelImage(String levelImage) {
		this.levelImage = levelImage == null ? null : levelImage.trim();
	}

	public String getLevelWelfare() {
		return levelWelfare;
	}

	public void setLevelWelfare(String levelWelfare) {
		this.levelWelfare = levelWelfare == null ? null : levelWelfare.trim();
	}

	public String getLevelDesc() {
		return levelDesc;
	}

	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc == null ? null : levelDesc.trim();
	}

	public String getLevelLimit() {
		return levelLimit;
	}

	public void setLevelLimit(String levelLimit) {
		this.levelLimit = levelLimit;
	}
	
	
}