package com.qinzhi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qinzhi.domain.Punchlog;
import com.qinzhi.repository.IPunchlogRepository;
import com.qinzhi.service.IPunchlogService;
import com.qinzhi.utils.Pageable;

@Service
public class PunchlogServiceImpl implements IPunchlogService {
	@Autowired
	private IPunchlogRepository repository;

	@Override
	public List<Punchlog> findPunchlogList(Punchlog log) {
		return repository.findPunchlogList(log);
	}

	@Override
	public void savePunchlog(Punchlog log) {
		repository.save(log);
	}

	@Override
	public Pageable<Punchlog> findLogList(Punchlog log) {
		return repository.findList(log);
	}

}
