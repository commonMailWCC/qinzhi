package com.qinzhi.repository;

import com.qinzhi.domain.Goods;
import com.qinzhi.utils.Pageable;

public interface IGoodsRepository {

	Pageable<Goods> findGoodsList(Goods Goods);

	void save(Goods Goods);

	void update(Goods Goods);

	Goods getGoodsById(Long id);

	void deleteGood(Long valueOf);

	Integer getCount(Goods goods);

}
