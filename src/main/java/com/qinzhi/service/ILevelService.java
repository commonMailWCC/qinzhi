package com.qinzhi.service;

import java.util.List;

import com.qinzhi.domain.Level;

public interface ILevelService {

	List<Level> findLevelList(Level level);

	void saveLevel(Level level);
    
	void updateLevel(Level level);

	Level getLevelById(Long id);

	Boolean deleteLevel(String ids);
}
