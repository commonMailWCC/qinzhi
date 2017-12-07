package com.qinzhi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qinzhi.domain.SysProduct;
import com.qinzhi.repository.IProductRepository;
import com.qinzhi.service.IProductService;
import com.qinzhi.utils.Pageable;
@Service
public class ProductServiceImpl implements IProductService{

	@Autowired
	private IProductRepository productRepository;
	@Override
	public Pageable<SysProduct> findProductList(int offset, int limit) {
		List<SysProduct> list = productRepository.findProductList(offset,limit);
		int total = productRepository.getProductCount();
		return new Pageable<SysProduct>().instanceByPageNo(list, total, offset, limit);
	}
	@Override
	public void saveProduct(SysProduct product) {
		productRepository.save(product);
	}

}
