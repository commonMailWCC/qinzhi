package com.qinzhi.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qinzhi.domain.Level;
import com.qinzhi.repository.ILevelRepository;
import com.qinzhi.repository.mapper.LevelMapper;

@Repository
public class LevelRepositoryImpl implements ILevelRepository {
	@Autowired
	private LevelMapper mapper;

	@Override
	public List<Level> findLevelList(Level Level) {
		return mapper.findAll();
	}

	@Override
	public void save(Level Level) {
		mapper.save(Level);
	}

	@Override
	public void update(Level Level) {
		mapper.update(Level);
	}

	@Override
	public Level getGoodsById(Long id) {
		return mapper.get(id);
	}

	@Override
	public void deleteGood(Long id) {
		// TODO Auto-generated method stub

	}

}
