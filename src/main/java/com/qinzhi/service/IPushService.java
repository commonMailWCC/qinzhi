package com.qinzhi.service;

import com.qinzhi.domain.Push;
import com.qinzhi.utils.Pageable;

public interface IPushService {

	Pageable<Push> findPushList(Push push);

	void savePush(Push push);
    

}
