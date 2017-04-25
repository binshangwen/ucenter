/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.user.entity.UserData;
import com.framework.modules.ucenter.user.dao.UserDataDao;

/**
 * 保持用户注册登录信息Service
 * @author admin
 * @version 2017-04-24
 */
@Service
@Transactional(readOnly = true)
public class UserDataService extends CrudService<UserDataDao, UserData> {

	public UserData get(String id) {
		return super.get(id);
	}
	
	public List<UserData> findList(UserData userData) {
		return super.findList(userData);
	}
	
	public Page<UserData> findPage(Page<UserData> page, UserData userData) {
		return super.findPage(page, userData);
	}
	
	@Transactional(readOnly = false)
	public void save(UserData userData) {
		super.save(userData);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserData userData) {
		super.delete(userData);
	}
	
	
	
	
}