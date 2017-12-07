package com.qinzhi.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qinzhi.domain.Push;
import com.qinzhi.repository.IPushRepository;
import com.qinzhi.repository.mapper.PushMapper;
import com.qinzhi.utils.Pageable;

@Repository
public class PushRepositoryImpl implements IPushRepository {
	@Autowired
	private PushMapper mapper;

	@Override
	public Pageable<Push> findPushList(Push push) {
		List<Push> operatorList = this.mapper.findPushList(push);
		int total = this.mapper.getPushCount(push);
		return new Pageable<Push>().instanceByPageNo(operatorList, total, push.getPage(), push.getRows());
	}

	@Override
	public void save(Push Push) {
		mapper.save(Push);
	}

}
