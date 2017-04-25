/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weibouser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.weibouser.entity.WeiboUser;
import com.framework.modules.ucenter.weibouser.dao.WeiboUserDao;

/**
 * 存储微博用户信息Service
 * @author binshangwen
 * @version 2017-04-24
 */
@Service
@Transactional(readOnly = true)
public class WeiboUserService extends CrudService<WeiboUserDao, WeiboUser> {

	public WeiboUser get(String id) {
		return super.get(id);
	}
	
	public List<WeiboUser> findList(WeiboUser weiboUser) {
		return super.findList(weiboUser);
	}
	
	public Page<WeiboUser> findPage(Page<WeiboUser> page, WeiboUser weiboUser) {
		return super.findPage(page, weiboUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiboUser weiboUser) {
		super.save(weiboUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeiboUser weiboUser) {
		super.delete(weiboUser);
	}
	
	
	
	
}