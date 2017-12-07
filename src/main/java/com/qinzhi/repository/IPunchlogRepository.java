package com.qinzhi.repository;

import java.util.List;

import com.qinzhi.domain.Goods;
import com.qinzhi.domain.Punchlog;
import com.qinzhi.utils.Pageable;

public interface IPunchlogRepository {

	List<Punchlog> findPunchlogList(Punchlog Punchlog);

	void save(Punchlog Punchlog);
    
	Pageable<Punchlog> findList(Punchlog log);
}
