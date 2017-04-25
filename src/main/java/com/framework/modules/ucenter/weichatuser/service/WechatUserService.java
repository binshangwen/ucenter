/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weichatuser.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.weichatuser.entity.WechatUser;
import com.framework.modules.ucenter.weichatuser.dao.WechatUserDao;

/**
 * 存储微信用户信息Service
 * @author binshangwen
 * @version 2017-04-24
 */
@Service
@Transactional(readOnly = true)
public class WechatUserService extends CrudService<WechatUserDao, WechatUser> {

	public WechatUser get(String id) {
		return super.get(id);
	}
	
	public List<WechatUser> findList(WechatUser wechatUser) {
		return super.findList(wechatUser);
	}
	
	public Page<WechatUser> findPage(Page<WechatUser> page, WechatUser wechatUser) {
		return super.findPage(page, wechatUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatUser wechatUser) {
		super.save(wechatUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatUser wechatUser) {
		super.delete(wechatUser);
	}
	
	
	
	
}