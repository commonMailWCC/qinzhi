package com.qinzhi.bean;

import com.qinzhi.domain.Goods;

public class QueryResult extends ResultMessage {
	private Goods data;

	public Goods getData() {
		return data;
	}

	public void setData(Goods data) {
		this.data = data;
	}

}
