package com.qinzhi.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qinzhi.domain.Goods;
import com.qinzhi.domain.Punchlog;
import com.qinzhi.repository.IPunchlogRepository;
import com.qinzhi.repository.mapper.PunchlogMapper;
import com.qinzhi.utils.Pageable;

@Repository
public class PunchlogRepositoryImpl implements IPunchlogRepository {
	@Autowired
	private PunchlogMapper mapper;

	@Override
	public List<Punchlog> findPunchlogList(Punchlog Punchlog) {
		return mapper.findPunchlogList(Punchlog);
	}

	@Override
	public void save(Punchlog Punchlog) {
		mapper.save(Punchlog);
	}

	@Override
	public Pageable<Punchlog> findList(Punchlog log) {
		List<Punchlog> operatorList = this.mapper.findList(log);
		int total = this.mapper.getCount(log);
		return new Pageable<Punchlog>().instanceByPageNo(operatorList, total, log.getPage(), log.getRows());
	}


}
