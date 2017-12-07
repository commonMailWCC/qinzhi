package com.qinzhi.repository.mapper;

import java.util.List;

import com.qinzhi.domain.Goods;

public interface GoodsMapper extends BaseMapper<Goods, Long> {

	List<Goods> findGoodsList(Goods goods);

	int getGoodsCount(Goods goods);
}