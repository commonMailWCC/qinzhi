package com.qinzhi.repository;

import com.qinzhi.domain.Push;
import com.qinzhi.utils.Pageable;

public interface IPushRepository {

	Pageable<Push> findPushList(Push Push);

	void save(Push Push);
}
