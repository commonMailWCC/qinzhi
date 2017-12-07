package com.qinzhi.bean;

import java.util.List;

public class HistoryResult extends ResultMessage {

	private List<QueryHistory> data;

	private Integer count;

	public List<QueryHistory> getData() {
		return data;
	}

	public void setData(List<QueryHistory> data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
