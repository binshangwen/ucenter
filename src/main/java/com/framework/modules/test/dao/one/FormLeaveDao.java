/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.test.dao.one;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.test.entity.one.FormLeave;

/**
 * 员工请假DAO接口
 * @author liugf
 * @version 2016-06-22
 */
@MyBatisDao
public interface FormLeaveDao extends CrudDao<FormLeave> {

	
}