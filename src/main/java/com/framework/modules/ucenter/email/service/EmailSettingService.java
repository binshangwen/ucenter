/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.email.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.common.persistence.Page;
import com.framework.common.service.CrudService;
import com.framework.modules.ucenter.email.entity.EmailSetting;
import com.framework.modules.ucenter.email.dao.EmailSettingDao;

/**
 * 存储邮箱注册配置信息Service
 * @author binshangwen
 * @version 2017-04-25
 */
@Service
@Transactional(readOnly = true)
public class EmailSettingService extends CrudService<EmailSettingDao, EmailSetting> {

	public EmailSetting get(String id) {
		return super.get(id);
	}
	
	public List<EmailSetting> findList(EmailSetting emailSetting) {
		return super.findList(emailSetting);
	}
	
	public Page<EmailSetting> findPage(Page<EmailSetting> page, EmailSetting emailSetting) {
		return super.findPage(page, emailSetting);
	}
	
	@Transactional(readOnly = false)
	public void save(EmailSetting emailSetting) {
		super.save(emailSetting);
	}
	
	@Transactional(readOnly = false)
	public void delete(EmailSetting emailSetting) {
		super.delete(emailSetting);
	}
	
	
	
	
}