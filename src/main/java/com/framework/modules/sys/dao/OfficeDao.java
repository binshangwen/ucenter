/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.sys.dao;

import com.framework.common.persistence.TreeDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author framework
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
}
