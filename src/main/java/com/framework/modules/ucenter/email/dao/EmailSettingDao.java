/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.email.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.ucenter.email.entity.EmailSetting;

/**
 * 存储邮箱注册配置信息DAO接口
 * @author binshangwen
 * @version 2017-04-25
 */
@MyBatisDao
public interface EmailSettingDao extends CrudDao<EmailSetting> {

	
}