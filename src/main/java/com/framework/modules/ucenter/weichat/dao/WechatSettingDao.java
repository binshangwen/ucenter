/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weichat.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.ucenter.weichat.entity.WechatSetting;

/**
 * 微信注册配置信息DAO接口
 * @author binshangwen
 * @version 2017-04-24
 */
@MyBatisDao
public interface WechatSettingDao extends CrudDao<WechatSetting> {

	
}