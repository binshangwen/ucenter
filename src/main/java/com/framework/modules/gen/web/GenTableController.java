/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.framework.modules.gen.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.StringUtils;
import com.framework.common.web.BaseController;
import com.framework.modules.gen.entity.GenScheme;
import com.framework.modules.gen.entity.GenTable;
import com.framework.modules.gen.entity.GenTableColumn;
import com.framework.modules.gen.service.GenSchemeService;
import com.framework.modules.gen.service.GenTableService;
import com.framework.modules.gen.util.GenUtils;
import com.framework.modules.sys.entity.User;
import com.framework.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * 业务表Controller
 * @author ThinkGem
 * @version 2013-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genTable")
public class GenTableController extends BaseController {

	@Autowired
	private GenTableService genTableService;
	@Autowired
	private GenSchemeService genSchemeService;

	@ModelAttribute
	public GenTable get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return genTableService.get(id);
		}else{
			return new GenTable();
		}
	}

	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			genTable.setCreateBy(user);
		}
        Page<GenTable> page = genTableService.find(new Page<GenTable>(request, response), genTable);
        model.addAttribute("page", page);
		return "modules/gen/genTableList";
	}

	@RequiresPermissions("gen:genTable:view")
	@RequestMapping(value = "form")
	public String form(GenTable genTable, Model model) {
		// 获取物理表列表
		List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
		model.addAttribute("tableList", tableList);
		// 验证表是否存在
		if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())){
			addMessage(model, "下一步失败！" + genTable.getName() + " 表已经添加！");
			genTable.setName("");
		}
		// 获取物理表字段
		else{
			genTable = genTableService.getTableFormDb(genTable);
		}
		model.addAttribute("genTable", genTable);
		model.addAttribute("config", GenUtils.getConfig());
		return "modules/gen/genTableForm";
	}

	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "save")
	public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
		genTableService.save(genTable);
		addMessage(redirectAttributes, "保存业务表'" + genTable.getName() + "'成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}

	@RequiresPermissions("gen:genTable:edit")
	@RequestMapping(value = "delete")
	public String delete(GenTable genTable, RedirectAttributes redirectAttributes) {
		genTableService.delete(genTable);
		addMessage(redirectAttributes, "删除业务表成功");
		return "redirect:" + adminPath + "/gen/genTable/?repage";
	}

	@RequiresPermissions({ "gen:genTable:genCode" })
	@RequestMapping({ "genCodeForm" })
	public String genCodeForm(GenScheme genScheme, final Model model) {
		if (StringUtils.isBlank(genScheme.getPackageName())) {
			genScheme.setPackageName("com.framework.modules");
		}
		final GenScheme oldGenScheme;
		if ((oldGenScheme = this.genSchemeService.findUniqueByProperty("gen_table_id", genScheme.getGenTable().getId())) != null) {
			genScheme = oldGenScheme;
		}
		model.addAttribute("genScheme", genScheme);
		model.addAttribute("config", GenUtils.getConfig());
		model.addAttribute("tableList", genTableService.findAll());
		return "modules/gen/genCodeForm";
	}

	@RequiresPermissions({ "gen:genTable:importDb" })
	@RequestMapping({ "importTableFromDB" })
	public String importTableFromDB(GenTable genTable, final Model model, final RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(genTable.getName())) {
			final List<GenTable> tableList = this.genTableService.findTableListFormDb(new GenTable());
			model.addAttribute("tableList", (Object)tableList);
			model.addAttribute("config", GenUtils.getConfig());
			return "modules/gen/importTableFromDB";
		}
		if (!this.genTableService.checkTableName(genTable.getName())) {
			this.addMessage(redirectAttributes, new String[] { "下一步失败！" + genTable.getName() + " 表已经添加！" });
			return "redirect:" + this.adminPath + "/gen/genTable/?repage";
		}
		(genTable = this.genTableService.getTableFormDb(genTable)).setTableType("0");
		this.genTableService.saveFromDB(genTable);
		this.addMessage(redirectAttributes, new String[] { "数据库导入表单'" + genTable.getName() + "'成功" });
		return "redirect:" + this.adminPath + "/gen/genTable/?repage";
	}

	@RequiresPermissions({ "gen:genTable:del" })
	@RequestMapping({ "deleteDb" })
	public String deleteDb(GenTable genTable, final RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			this.addMessage(redirectAttributes, new String[] { "演示模式，不允许操作！" });
			return "redirect:" + this.adminPath + "/gen/genTable/?repage";
		}
		genTable = get(genTable.getId());
		genTableService.delete(genTable);
		genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
		final StringBuffer sql = new StringBuffer();
		final String dbType = Global.getConfig("jdbc.type");
		if ("mysql".equals(dbType)) {
			sql.append("drop table if exists " + genTable.getName() + " ;");
		}
		else if ("oracle".equals(dbType)) {
			try {
				sql.append("DROP TABLE " + genTable.getName());
			}
			catch (Exception ex) {}
		}
		else if ("mssql".equals(dbType) || "sqlserver".equals(dbType)) {
			sql.append("if exists (select * from sysobjects where id = object_id(N'[" + genTable.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" + genTable.getName() + "]");
		}
		genTableService.buildTable(sql.toString());
		addMessage(redirectAttributes, new String[] { "删除业务表记录和数据库表成功" });
		return "redirect:" + this.adminPath + "/gen/genTable/?repage";
	}

	@RequiresPermissions({ "gen:genTable:synchDb" })
	@RequestMapping({ "synchDb" })
	public String synchDb(GenTable genTable, final RedirectAttributes redirectAttributes) {
		final String config = Global.getConfig("jdbc.type");
		final List<GenTableColumn> getTableColumnList =get(genTable.getId()).getColumnList();
		if ("mysql".equals(config)) {
			StringBuffer sql;
			(sql = new StringBuffer()).append("drop table if exists " + genTable.getName() + " ;");
			this.genTableService.buildTable(sql.toString());
			(sql = new StringBuffer()).append("create table " + genTable.getName() + " (");
			String pk = "";
			final Iterator<GenTableColumn> iterator = getTableColumnList.iterator();
			while (iterator.hasNext()) {
				final GenTableColumn column;
				if ((column = iterator.next()).getIsPk().equals("1")) {
					sql.append("  " + column.getName() + " " + column.getJdbcType() + " comment '" + column.getComments() + "',");
					pk = String.valueOf(pk) + column.getName() + ",";
				}
				else {
					sql.append("  " + column.getName() + " " + column.getJdbcType() + " comment '" + column.getComments() + "',");
				}
			}
			sql.append("primary key (" + pk.substring(0, pk.length() - 1) + ") ");
			sql.append(") comment '" + genTable.getComments() + "'");
			this.genTableService.buildTable(sql.toString());
		}
		else if ("oracle".equals(config)) {
			StringBuffer sql = new StringBuffer();
			try {
				sql.append("DROP TABLE " + genTable.getName());
				this.genTableService.buildTable(sql.toString());
			}
			catch (Exception ex) {}
			(sql = new StringBuffer()).append("create table " + genTable.getName() + " (");
			String pk = "";
			final Iterator<GenTableColumn> iterator2 = getTableColumnList.iterator();
			while (iterator2.hasNext()) {
				final GenTableColumn column;
				String jdbctype;
				if ((jdbctype = (column = iterator2.next()).getJdbcType()).equalsIgnoreCase("integer")) {
					jdbctype = "number(10,0)";
				}
				else if (jdbctype.equalsIgnoreCase("datetime")) {
					jdbctype = "date";
				}
				else if (jdbctype.contains("nvarchar(")) {
					jdbctype = jdbctype.replace("nvarchar", "nvarchar2");
				}
				else if (jdbctype.contains("varchar(")) {
					jdbctype = jdbctype.replace("varchar", "varchar2");
				}
				else if (jdbctype.equalsIgnoreCase("double")) {
					jdbctype = "float(24)";
				}
				else if (jdbctype.equalsIgnoreCase("longblob")) {
					jdbctype = "blob raw";
				}
				else if (jdbctype.equalsIgnoreCase("longtext")) {
					jdbctype = "clob raw";
				}
				if (column.getIsPk().equals("1")) {
					sql.append("  " + column.getName() + " " + jdbctype + ",");
					pk = String.valueOf(pk) + column.getName();
				}
				else {
					sql.append("  " + column.getName() + " " + jdbctype + ",");
				}
			}
			sql = new StringBuffer(String.valueOf(sql.substring(0, sql.length() - 1)) + ")");
			this.genTableService.buildTable(sql.toString());
			this.genTableService.buildTable("comment on table " + genTable.getName() + " is  '" + genTable.getComments() + "'");
			final Iterator<GenTableColumn> iterator3 = getTableColumnList.iterator();
			while (iterator3.hasNext()) {
				final GenTableColumn column = iterator3.next();
				this.genTableService.buildTable("comment on column " + genTable.getName() + "." + column.getName() + " is  '" + column.getComments() + "'");
			}
			this.genTableService.buildTable("alter table " + genTable.getName() + " add constraint PK_" + genTable.getName() + "_" + pk + " primary key (" + pk + ") ");
		}
		else if ("mssql".equals(config) || "sqlserver".equals(config)) {
			StringBuffer sql;
			(sql = new StringBuffer()).append("if exists (select * from sysobjects where id = object_id(N'[" + genTable.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" + genTable.getName() + "]");
			this.genTableService.buildTable(sql.toString());
			(sql = new StringBuffer()).append("create table " + genTable.getName() + " (");
			String pk = "";
			final Iterator<GenTableColumn> iterator4 = getTableColumnList.iterator();
			while (iterator4.hasNext()) {
				final GenTableColumn column;
				if ((column = iterator4.next()).getIsPk().equals("1")) {
					sql.append("  " + column.getName() + " " + column.getJdbcType() + ",");
					pk = String.valueOf(pk) + column.getName() + ",";
				}
				else {
					sql.append("  " + column.getName() + " " + column.getJdbcType() + ",");
				}
			}
			sql.append("primary key (" + pk.substring(0, pk.length() - 1) + ") ");
			sql.append(")");
			this.genTableService.buildTable(sql.toString());
		}
		this.genTableService.syncSave(genTable);
		this.addMessage(redirectAttributes, new String[] { "强制同步数据库表成功" });
		return "redirect:" + this.adminPath + "/gen/genTable/?repage";
	}
}
