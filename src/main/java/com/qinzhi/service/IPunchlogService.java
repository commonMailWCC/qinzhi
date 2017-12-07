package com.qinzhi.service;

import java.util.List;

import com.qinzhi.domain.Punchlog;
import com.qinzhi.utils.Pageable;

public interface IPunchlogService {

	List<Punchlog> findPunchlogList(Punchlog Punchlog);

	void savePunchlog(Punchlog Punchlog);
	
	
	Pageable<Punchlog> findLogList(Punchlog log);

}
