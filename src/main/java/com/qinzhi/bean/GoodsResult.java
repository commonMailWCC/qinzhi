package com.qinzhi.bean;

import com.qinzhi.domain.Goods;

import java.util.List;

public class GoodsResult extends ResultMessage {

	private List<Goods> data;

	private Integer count;

	public List<Goods> getData() {
		return data;
	}

	public void setData(List<Goods> data) {
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
