package com.qinzhi.service;

import com.qinzhi.domain.SysProduct;
import com.qinzhi.utils.Pageable;

public interface IProductService {

	Pageable<SysProduct> findProductList(int offset, int limit);

	void saveProduct(SysProduct product);

}
