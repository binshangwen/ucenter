/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.framework.modules.gen.service;

import com.framework.common.persistence.Page;
import com.framework.common.service.BaseService;
import com.framework.common.utils.StringUtils;
import com.framework.modules.gen.dao.GenSchemeDao;
import com.framework.modules.gen.dao.GenTableColumnDao;
import com.framework.modules.gen.dao.GenTableDao;
import com.framework.modules.gen.entity.*;
import com.framework.modules.gen.util.GenUtils;
import com.framework.modules.sys.entity.Menu;
import com.framework.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 生成方案Service
 * @author ThinkGem
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenSchemeService extends BaseService {

	@Autowired
	private GenSchemeDao genSchemeDao;
//	@Autowired
//	private GenTemplateDao genTemplateDao;
	@Autowired
	private GenTableDao genTableDao;
	@Autowired
	private SystemService systemService;
	@Autowired
	private GenTableColumnDao genTableColumnDao;
	
	public GenScheme get(String id) {
		return genSchemeDao.get(id);
	}
	
	public Page<GenScheme> find(Page<GenScheme> page, GenScheme genScheme) {
		GenUtils.getTemplatePath();
		genScheme.setPage(page);
		page.setList(genSchemeDao.findList(genScheme));
		return page;
	}
	
	@Transactional(readOnly = false)
	public String save(GenScheme genScheme) {
		if (StringUtils.isBlank(genScheme.getId())){
			genScheme.preInsert();
			genSchemeDao.insert(genScheme);
		}else{
			genScheme.preUpdate();
			genSchemeDao.update(genScheme);
		}
		// 生成代码
//		if ("1".equals(genScheme.getFlag())){
			return generateCode(genScheme);
//		}
//		return "";
	}
	
	@Transactional(readOnly = false)
	public void delete(GenScheme genScheme) {
		genSchemeDao.delete(genScheme);
	}
	
	private String generateCode(GenScheme genScheme){

		StringBuilder result = new StringBuilder();
		
		// 查询主表及字段列
		GenTable genTable = genTableDao.get(genScheme.getGenTable().getId());
		genTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
		
		// 获取所有代码模板
		GenConfig config = GenUtils.getConfig();
		
		// 获取模板列表
		List<GenTemplate> templateList = GenUtils.getTemplateList(config, genScheme.getCategory(), false);
		List<GenTemplate> childTableTemplateList = GenUtils.getTemplateList(config, genScheme.getCategory(), true);
		
		// 如果有子表模板，则需要获取子表列表
		if (childTableTemplateList.size() > 0){
			GenTable parentTable = new GenTable();
			parentTable.setParentTable(genTable.getName());
			genTable.setChildList(genTableDao.findList(parentTable));
		}
		
		// 生成子表模板代码
		for (GenTable childTable : genTable.getChildList()){
			childTable.setParent(genTable);
			childTable.setColumnList(genTableColumnDao.findList(new GenTableColumn(new GenTable(childTable.getId()))));
			genScheme.setGenTable(childTable);
			Map<String, Object> childTableModel = GenUtils.getDataModel(genScheme);
			for (GenTemplate tpl : childTableTemplateList){
				result.append(GenUtils.generateToFile(tpl, childTableModel, true));
			}
		}
		
		// 生成主表模板代码
		genScheme.setGenTable(genTable);
		Map<String, Object> model = GenUtils.getDataModel(genScheme);
		for (GenTemplate tpl : templateList){
			result.append(GenUtils.generateToFile(tpl, model, true));
		}
		return result.toString();
	}

	public GenScheme findUniqueByProperty(final String propertyName, final String value) {
		return (GenScheme)this.genSchemeDao.findUniqueByProperty(propertyName, (Object)value);
	}

	@Transactional(readOnly = false)
	public void createMenu( GenScheme genScheme,  Menu topMenu) {
		String permissionPrefix = String.valueOf(StringUtils.lowerCase(genScheme.getModuleName())) + (StringUtils.isNotBlank((CharSequence)genScheme.getSubModuleName()) ? (":" + StringUtils.lowerCase(genScheme.getSubModuleName())) : "") + ":" + StringUtils.uncapitalize(genScheme.getGenTable().getClassName());
		String url = "/" + StringUtils.lowerCase(genScheme.getModuleName()) + (StringUtils.isNotBlank((CharSequence)genScheme.getSubModuleName()) ? ("/" + StringUtils.lowerCase(genScheme.getSubModuleName())) : "") + "/" + StringUtils.uncapitalize(genScheme.getGenTable().getClassName());
		topMenu.setName(genScheme.getFunctionName());
		topMenu.setHref(url);
		topMenu.setIsShow("1");
		topMenu.setPermission(String.valueOf(permissionPrefix) + ":list");
		systemService.saveMenu(topMenu);
		Menu addMenu = null;
		(addMenu = new Menu()).setName("增加");
		addMenu.setIsShow("0");
		addMenu.setSort(30);
		addMenu.setPermission(String.valueOf(permissionPrefix) + ":add");
		addMenu.setParent(topMenu);
		this.systemService.saveMenu(addMenu);
		Menu delMenu = null;
		(delMenu = new Menu()).setName("删除");
		delMenu.setIsShow("0");
		delMenu.setSort(60);
		delMenu.setPermission(String.valueOf(permissionPrefix) + ":del");
		delMenu.setParent(topMenu);
		systemService.saveMenu(delMenu);
		Menu editMenu = null;
		(editMenu = new Menu()).setName("编辑");
		editMenu.setIsShow("0");
		editMenu.setSort(90);
		editMenu.setPermission(String.valueOf(permissionPrefix) + ":edit");
		editMenu.setParent(topMenu);
		systemService.saveMenu(editMenu);
		Menu viewMenu = null;
		(viewMenu = new Menu()).setName("查看");
		viewMenu.setIsShow("0");
		viewMenu.setSort(120);
		viewMenu.setPermission(String.valueOf(permissionPrefix) + ":view");
		viewMenu.setParent(topMenu);
		systemService.saveMenu(viewMenu);
		Menu importMenu = null;
		(importMenu = new Menu()).setName("导入");
		importMenu.setIsShow("0");
		importMenu.setSort(150);
		importMenu.setPermission(String.valueOf(permissionPrefix) + ":import");
		importMenu.setParent(topMenu);
		systemService.saveMenu(importMenu);
		Menu exportMenu = null;
		(exportMenu = new Menu()).setName("导出");
		exportMenu.setIsShow("0");
		exportMenu.setSort(180);
		exportMenu.setPermission(String.valueOf(permissionPrefix) + ":export");
		exportMenu.setParent(topMenu);
		systemService.saveMenu(exportMenu);
	}

}
