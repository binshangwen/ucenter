/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weichat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.weichat.entity.WechatSetting;
import com.framework.modules.ucenter.weichat.dao.WechatSettingDao;

/**
 * 微信注册配置信息Service
 * @author binshangwen
 * @version 2017-04-24
 */
@Service
@Transactional(readOnly = true)
public class WechatSettingService extends CrudService<WechatSettingDao, WechatSetting> {

	public WechatSetting get(String id) {
		return super.get(id);
	}
	
	public List<WechatSetting> findList(WechatSetting wechatSetting) {
		return super.findList(wechatSetting);
	}
	
	public Page<WechatSetting> findPage(Page<WechatSetting> page, WechatSetting wechatSetting) {
		return super.findPage(page, wechatSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatSetting wechatSetting) {
		super.save(wechatSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatSetting wechatSetting) {
		super.delete(wechatSetting);
	}
	
	
	
	
}