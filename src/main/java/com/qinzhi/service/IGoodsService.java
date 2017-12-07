package com.qinzhi.service;

import com.qinzhi.domain.Goods;
import com.qinzhi.utils.Pageable;

public interface IGoodsService {

	Pageable<Goods> findGoodsList(Goods goods);
	
	Integer getCount(Goods goods);

	void saveGoods(Goods goods);
    
	void updateGoods(Goods goods);

	Goods getGoodsById(Long id);

	Boolean deleteGoods(String ids);
}
