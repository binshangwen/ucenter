/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.framework.modules.gen.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.gen.entity.GenTable;
import com.framework.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {

	void deleteByGenTable(final GenTable genTable);
}
