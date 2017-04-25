/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.qquser.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.ucenter.qquser.entity.QqUser;

/**
 * 存储QQ用户信息DAO接口
 * @author binshangwen
 * @version 2017-04-24
 */
@MyBatisDao
public interface QqUserDao extends CrudDao<QqUser> {

	
}