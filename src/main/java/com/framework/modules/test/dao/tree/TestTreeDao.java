/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.test.dao.tree;

import com.framework.common.persistence.TreeDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.test.entity.tree.TestTree;

/**
 * 组织机构DAO接口
 * @author liugf
 * @version 2016-05-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}