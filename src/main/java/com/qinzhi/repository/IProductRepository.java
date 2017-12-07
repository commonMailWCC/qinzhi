package com.qinzhi.repository;

import com.qinzhi.domain.SysProduct;

import java.util.List;

public interface IProductRepository {

	List<SysProduct> findProductList(int start, int limit);

	int getProductCount();

	void save(SysProduct product);
	List<String> findAllAppId();

}
