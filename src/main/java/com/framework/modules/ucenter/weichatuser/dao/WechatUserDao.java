/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weichatuser.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.ucenter.weichatuser.entity.WechatUser;

/**
 * 存储微信用户信息DAO接口
 * @author binshangwen
 * @version 2017-04-24
 */
@MyBatisDao
public interface WechatUserDao extends CrudDao<WechatUser> {

	
}