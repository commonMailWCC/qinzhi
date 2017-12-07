package com.qinzhi.repository.mapper;

import java.util.List;

import com.qinzhi.domain.Punchlog;

public interface PunchlogMapper extends BaseMapper<Punchlog, Long> {
	List<Punchlog> findPunchlogList(Punchlog log);

	List<Punchlog> findList(Punchlog log);

	int getCount(Punchlog log);
}