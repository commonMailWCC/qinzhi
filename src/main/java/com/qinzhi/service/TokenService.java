package com.qinzhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qinzhi.entity.TokenTable;
import com.qinzhi.repository.ITokenDao;

/**
 * <功能详细描述>
 * 
 * @author 李伟
 * @version [1.0, 2017年5月9日]
 */
@Component
@Transactional(value = "transactionManager")
public class TokenService {
	@Autowired
	private ITokenDao smsDao;

	public void save(TokenTable sms) {
		smsDao.save(sms);
	}

	public TokenTable getTokenById(Long id) {
		return smsDao.findOne(id);
	}

	public TokenTable getTokenByToken(String token) {
		return smsDao.findByUserToken(token);
	}
	
	public void deleteById(Long id) {
		smsDao.delete(id);
	}

}