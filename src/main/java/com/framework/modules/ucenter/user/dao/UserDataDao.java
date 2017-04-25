/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.user.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.ucenter.user.entity.UserData;

/**
 * 保持用户注册登录信息DAO接口
 * @author admin
 * @version 2017-04-24
 */
@MyBatisDao
public interface UserDataDao extends CrudDao<UserData> {

	
}