package com.qinzhi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.qinzhi.domain.Level;
import com.qinzhi.repository.ILevelRepository;
import com.qinzhi.service.ILevelService;

@Service
public class LevelServiceImpl implements ILevelService {
	@Autowired
	private ILevelRepository repository;

	@Override
	public List<Level> findLevelList(Level level) {
		return repository.findLevelList(level);
	}

	@Override
	public void saveLevel(Level level) {
		repository.save(level);
	}

	@Override
	public void updateLevel(Level level) {
		repository.update(level);
	}

	@Override
	public Level getLevelById(Long id) {
		return repository.getGoodsById(id);
	}

	@Override
	public Boolean deleteLevel(String ids) {
		List<String> idList = CollectionUtils.arrayToList(ids.split(","));
		for (String id : idList) {
			this.repository.deleteGood(Long.valueOf(id));
		}
		return true;
	}

}
