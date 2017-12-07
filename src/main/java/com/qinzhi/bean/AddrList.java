package com.qinzhi.bean;

import java.util.List;

public class AddrList {
	private String type;

	private int status;

	private String name;

	private String id;

	private String admCode;

	private String admName;

	private String addr;

	private String[] nearestPoint;

	private String distance;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdmCode() {
		return admCode;
	}

	public void setAdmCode(String admCode) {
		this.admCode = admCode;
	}

	public String getAdmName() {
		return admName;
	}

	public void setAdmName(String admName) {
		this.admName = admName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String[] getNearestPoint() {
		return nearestPoint;
	}

	public void setNearestPoint(String[] nearestPoint) {
		this.nearestPoint = nearestPoint;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}