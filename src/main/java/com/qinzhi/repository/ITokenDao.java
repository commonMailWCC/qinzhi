package com.qinzhi.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.qinzhi.entity.TokenTable;

public interface ITokenDao extends PagingAndSortingRepository<TokenTable, Long>, JpaSpecificationExecutor<TokenTable> {

	TokenTable findByUserToken(String token);
	
}
