/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.framework.modules.gen.web;

import com.framework.common.persistence.Page;
import com.framework.common.utils.StringUtils;
import com.framework.common.web.BaseController;
import com.framework.modules.gen.entity.GenScheme;
import com.framework.modules.gen.service.GenSchemeService;
import com.framework.modules.gen.service.GenTableService;
import com.framework.modules.gen.util.GenUtils;
import com.framework.modules.sys.entity.Menu;
import com.framework.modules.sys.entity.User;
import com.framework.modules.sys.service.SystemService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 生成方案Controller
 * @author ThinkGem
 * @version 2013-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genScheme")
public class GenSchemeController extends BaseController {

	@Autowired
	private GenSchemeService genSchemeService;
	
	@Autowired
	private GenTableService genTableService;

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public GenScheme get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return genSchemeService.get(id);
		}else{
			return new GenScheme();
		}
	}
	
	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = {"list", ""})
	public String list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			genScheme.setCreateBy(user);
		}
        Page<GenScheme> page = genSchemeService.find(new Page<GenScheme>(request, response), genScheme); 
        model.addAttribute("page", page);
		
		return "modules/gen/genSchemeList";
	}

	@RequiresPermissions("gen:genScheme:view")
	@RequestMapping(value = "form")
	public String form(GenScheme genScheme, Model model) {
		if (StringUtils.isBlank(genScheme.getPackageName())){
			genScheme.setPackageName("com.framework.modules");
		}
//		if (StringUtils.isBlank(genScheme.getFunctionAuthor())){
//			genScheme.setFunctionAuthor(UserUtils.getUser().getName());
//		}
		model.addAttribute("genScheme", genScheme);
		model.addAttribute("config", GenUtils.getConfig());
		model.addAttribute("tableList", genTableService.findAll());
		return "modules/gen/genSchemeForm";
	}

	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "save")
	public String save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, genScheme)){
			return form(genScheme, model);
		}
		
		String result = genSchemeService.save(genScheme);
		addMessage(redirectAttributes, "操作生成方案'" + genScheme.getName() + "'成功<br/>"+result);
		return "redirect:" + adminPath + "/gen/genScheme/?repage";
	}
	
	@RequiresPermissions("gen:genScheme:edit")
	@RequestMapping(value = "delete")
	public String delete(GenScheme genScheme, RedirectAttributes redirectAttributes) {
		genSchemeService.delete(genScheme);
		addMessage(redirectAttributes, "删除生成方案成功");
		return "redirect:" + adminPath + "/gen/genScheme/?repage";
	}

	@RequiresPermissions({ "gen:genTable:genCode" })
	@RequestMapping({ "genCode" })
	public String genCode(final GenScheme genScheme, final RedirectAttributes redirectAttributes) {
		final String result = genSchemeService.save(genScheme);
		this.addMessage(redirectAttributes, new String[] { String.valueOf(genScheme.getGenTable().getName()) + "代码生成成功<br/>" + result });
		return "redirect:" + this.adminPath + "/gen/genTable/?repage";
	}

	@RequestMapping({ "menuForm" })
	public String menuForm(final String gen_table_id, final Menu menu, final Model model) {
		if (menu.getParent() == null || menu.getParent().getId() == null) {
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		if (StringUtils.isBlank(menu.getId())) {
			 List<Menu> list = new ArrayList<>();
			 List<Menu> sourcelist = systemService.findAllMenu();
			Menu.sortList((List)list, (List)sourcelist, menu.getParentId(), false);
			if (list.size() > 0) {
				menu.setSort(list.get(list.size() - 1).getSort() + 30);
			}
		}
		GenScheme genScheme = null;
		if ((genScheme = genSchemeService.findUniqueByProperty("gen_table_id", gen_table_id)) != null) {
			menu.setName(genScheme.getFunctionName());
		}
		model.addAttribute("menu", menu);
		model.addAttribute("gen_table_id", gen_table_id);
		return "modules/gen/genMenuForm";
	}

	@RequestMapping({ "createMenu" })
	public String createMenu(final String gen_table_id, final Menu menu, final RedirectAttributes redirectAttributes) {
		final GenScheme genScheme;
		if ((genScheme = genSchemeService.findUniqueByProperty("gen_table_id", gen_table_id)) == null) {
			this.addMessage(redirectAttributes, new String[] { "创建菜单失败,请先生成代码!" });
			return "redirect:" + this.adminPath + "/gen/genTable/?repage";
		}
		genScheme.setGenTable(this.genTableService.get(gen_table_id));
		genSchemeService.createMenu(genScheme, menu);
		this.addMessage(redirectAttributes, new String[] { "创建菜单'" + genScheme.getFunctionName() + "'成功<br/>" });
		return "redirect:" + this.adminPath + "/gen/genTable/?repage";
	}
}
