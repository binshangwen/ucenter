/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.sys.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @author framework
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

	public void empty();
}
