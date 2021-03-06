/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.sys.dao;

import com.framework.common.persistence.CrudDao;
import com.framework.common.persistence.annotation.MyBatisDao;
import com.framework.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * @author framework
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);

}
