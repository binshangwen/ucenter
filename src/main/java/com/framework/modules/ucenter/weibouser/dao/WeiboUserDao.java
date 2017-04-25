/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weibouser.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.ucenter.weibouser.entity.WeiboUser;

/**
 * 存储微博用户信息DAO接口
 * @author binshangwen
 * @version 2017-04-24
 */
@MyBatisDao
public interface WeiboUserDao extends CrudDao<WeiboUser> {

	
}