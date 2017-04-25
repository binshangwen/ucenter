/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.qquser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.qquser.entity.QqUser;
import com.framework.modules.ucenter.qquser.dao.QqUserDao;

/**
 * 存储QQ用户信息Service
 * @author binshangwen
 * @version 2017-04-24
 */
@Service
@Transactional(readOnly = true)
public class QqUserService extends CrudService<QqUserDao, QqUser> {

	public QqUser get(String id) {
		return super.get(id);
	}
	
	public List<QqUser> findList(QqUser qqUser) {
		return super.findList(qqUser);
	}
	
	public Page<QqUser> findPage(Page<QqUser> page, QqUser qqUser) {
		return super.findPage(page, qqUser);
	}
	
	@Transactional(readOnly = false)
	public void save(QqUser qqUser) {
		super.save(qqUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(QqUser qqUser) {
		super.delete(qqUser);
	}
	
	
	
	
}