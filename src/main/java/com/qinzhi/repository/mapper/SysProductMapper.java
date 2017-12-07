package com.qinzhi.repository.mapper;

import com.qinzhi.domain.SysProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysProductMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysProduct record);

    SysProduct selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SysProduct record);

	List<SysProduct> findProductList(@Param("start")int start, @Param("limit")int limit);

	int getProductCount();
	List<String> findAllAppId();
}
