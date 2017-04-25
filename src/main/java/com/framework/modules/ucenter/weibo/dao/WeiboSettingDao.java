/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weibo.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.ucenter.weibo.entity.WeiboSetting;

/**
 * 存储微博配置信息DAO接口
 * @author binshangwen
 * @version 2017-04-24
 */
@MyBatisDao
public interface WeiboSettingDao extends CrudDao<WeiboSetting> {

	
}