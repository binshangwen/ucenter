/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.qq.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.qq.entity.QqSetting;
import com.framework.modules.ucenter.qq.dao.QqSettingDao;

/**
 * QQ注册配置信息Service
 * @author binshangwen
 * @version 2017-04-24
 */
@Service
@Transactional(readOnly = true)
public class QqSettingService extends CrudService<QqSettingDao, QqSetting> {

	public QqSetting get(String id) {
		return super.get(id);
	}
	
	public List<QqSetting> findList(QqSetting qqSetting) {
		return super.findList(qqSetting);
	}
	
	public Page<QqSetting> findPage(Page<QqSetting> page, QqSetting qqSetting) {
		return super.findPage(page, qqSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(QqSetting qqSetting) {
		super.save(qqSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(QqSetting qqSetting) {
		super.delete(qqSetting);
	}
	
	
	
	
}