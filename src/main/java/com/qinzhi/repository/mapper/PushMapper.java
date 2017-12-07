package com.qinzhi.repository.mapper;

import java.util.List;

import com.qinzhi.domain.Push;

public interface PushMapper extends BaseMapper<Push, Long> {
	List<Push> findPushList(Push push);

	int getPushCount(Push push);
}