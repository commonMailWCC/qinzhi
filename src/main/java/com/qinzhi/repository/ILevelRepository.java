package com.qinzhi.repository;

import java.util.List;

import com.qinzhi.domain.Level;

public interface ILevelRepository {

	List<Level> findLevelList(Level Level);

	void save(Level Level);

	void update(Level Level);

	Level getGoodsById(Long id);

	void deleteGood(Long id);

}
