package com.qinzhi.repository.impl;

import com.qinzhi.domain.SysProduct;
import com.qinzhi.repository.IProductRepository;
import com.qinzhi.repository.mapper.SysProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
	@Autowired
	private SysProductMapper mapper;

	@Override
	public List<SysProduct> findProductList(int start, int limit) {
		return mapper.findProductList(start, limit);
	}

	@Override
	public int getProductCount() {
		return mapper.getProductCount();
	}

	@Override
	public void save(SysProduct product) {
		mapper.insert(product);
	}

	@Override
	public List<String> findAllAppId() {
		return mapper.findAllAppId();
	}

}
