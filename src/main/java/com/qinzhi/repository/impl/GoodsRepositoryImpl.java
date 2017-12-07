package com.qinzhi.repository.impl;

import com.qinzhi.domain.Goods;
import com.qinzhi.domain.Goods;
import com.qinzhi.repository.IGoodsRepository;
import com.qinzhi.repository.mapper.GoodsMapper;
import com.qinzhi.utils.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoodsRepositoryImpl implements IGoodsRepository {
	@Autowired
	private GoodsMapper mapper;

	@Override
	public Pageable<Goods> findGoodsList(Goods goods) {
		List<Goods> operatorList = this.mapper.findGoodsList(goods);
		int total = this.mapper.getGoodsCount(goods);
		return new Pageable<Goods>().instanceByPageNo(operatorList, total, goods.getPage(), goods.getRows());
	}
 

	@Override
	public void save(Goods Goods) {
		mapper.save(Goods);
	}
	@Override
	public void update(Goods Goods) {
		mapper.update(Goods);
	}


	@Override
	public Goods getGoodsById(Long id) {
		return mapper.get(id);
	}


	@Override
	public void deleteGood(Long id) {
		mapper.delete(id);
	}


	@Override
	public Integer getCount(Goods goods) {
	 return this.mapper.getGoodsCount(goods);
	}
}
