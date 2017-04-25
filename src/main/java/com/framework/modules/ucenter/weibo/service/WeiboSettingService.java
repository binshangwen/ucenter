/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weibo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.weibo.entity.WeiboSetting;
import com.framework.modules.ucenter.weibo.dao.WeiboSettingDao;

/**
 * 存储微博配置信息Service
 * @author binshangwen
 * @version 2017-04-24
 */
@Service
@Transactional(readOnly = true)
public class WeiboSettingService extends CrudService<WeiboSettingDao, WeiboSetting> {

	public WeiboSetting get(String id) {
		return super.get(id);
	}
	
	public List<WeiboSetting> findList(WeiboSetting weiboSetting) {
		return super.findList(weiboSetting);
	}
	
	public Page<WeiboSetting> findPage(Page<WeiboSetting> page, WeiboSetting weiboSetting) {
		return super.findPage(page, weiboSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(WeiboSetting weiboSetting) {
		super.save(weiboSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(WeiboSetting weiboSetting) {
		super.delete(weiboSetting);
	}
	
	
	
	
}