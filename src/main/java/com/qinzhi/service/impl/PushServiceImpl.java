package com.qinzhi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qinzhi.domain.Push;
import com.qinzhi.repository.IPushRepository;
import com.qinzhi.service.IPushService;
import com.qinzhi.utils.Pageable;

@Service
public class PushServiceImpl implements IPushService {
	@Autowired
	private IPushRepository repository;

	@Override
	public Pageable<Push> findPushList(Push push) {
		return this.repository.findPushList(push);
	}

	@Override
	public void savePush(Push push) {
		this.repository.save(push);
	}

 

}
