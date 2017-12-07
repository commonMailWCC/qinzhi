package com.qinzhi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qinzhi.domain.Goods;
import com.qinzhi.repository.IGoodsRepository;
import com.qinzhi.service.IGoodsService;
import com.qinzhi.utils.Pageable;

@Service
public class GoodsServiceImpl implements IGoodsService {
	@Autowired
	private IGoodsRepository repository;

	@Override
	public Pageable<Goods> findGoodsList(Goods goods) {
		return this.repository.findGoodsList(goods);
	}

	@Override
	public void saveGoods(Goods goods) {
		this.repository.save(goods);
	}

	@Override
	public void updateGoods(Goods goods) {
		this.repository.update(goods);
	}

	@Override
	public Goods getGoodsById(Long id) {
		return this.repository.getGoodsById(id);
	}

	@Override
	public Boolean deleteGoods(String ids) {
		List<String> idList = CollectionUtils.arrayToList(ids.split(","));
		for (String id : idList) {
			this.repository.deleteGood(Long.valueOf(id));
		}
		return true;
	}

	@Override
	public Integer getCount(Goods goods) {
		return this.repository.getCount(goods);
	}

}
