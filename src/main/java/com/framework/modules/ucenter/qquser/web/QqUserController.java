/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.qquser.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.qquser.entity.QqUser;
import com.framework.modules.ucenter.qquser.service.QqUserService;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 存储QQ用户信息Controller
 * @author binshangwen
 * @version 2017-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/qquser")
public class QqUserController extends BaseController {

	@Autowired
	private QqUserService qqUserService;
	
	@ModelAttribute
	public QqUser get(@RequestParam(required=false) String id) {
		QqUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qqUserService.get(id);
		}
		if (entity == null){
			entity = new QqUser();
		}
		return entity;
	}
	
	/**
	 * 微博用户信息列表页面
	 */
	@RequiresPermissions("qquser:list")
	@RequestMapping(value = {"list", ""})
	public String list(QqUser qqUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QqUser> page = qqUserService.findPage(new Page<QqUser>(request, response), qqUser); 
		model.addAttribute("page", page);
		return "modules/ucenter/qquser/qqUserList";
	}

	/**
	 * 查看，增加，编辑微博用户信息表单页面
	 */
	@RequiresPermissions(value={"qquser:view","qquser:add","qquser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QqUser qqUser, Model model) {
		model.addAttribute("qqUser", qqUser);
		return "modules/ucenter/qquser/qqUserForm";
	}

	/**
	 * 保存微博用户信息
	 */
	@RequiresPermissions(value={"qquser:add","qquser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QqUser qqUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, qqUser)){
			return form(qqUser, model);
		}
		if(!qqUser.getIsNewRecord()){//编辑表单保存
			QqUser t = qqUserService.get(qqUser.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(qqUser, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			qqUserService.save(t);//保存
		}else{//新增表单保存
			qqUserService.save(qqUser);//保存
		}
		addMessage(redirectAttributes, "保存微博用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/qquser/?repage";
	}
	
	/**
	 * 删除微博用户信息
	 */
	@RequiresPermissions("qquser:del")
	@RequestMapping(value = "delete")
	public String delete(QqUser qqUser, RedirectAttributes redirectAttributes) {
		qqUserService.delete(qqUser);
		addMessage(redirectAttributes, "删除微博用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/qquser/?repage";
	}
	
	/**
	 * 批量删除微博用户信息
	 */
	@RequiresPermissions("qquser:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qqUserService.delete(qqUserService.get(id));
		}
		addMessage(redirectAttributes, "删除微博用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/qquser/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("qquser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(QqUser qqUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微博用户信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QqUser> page = qqUserService.findPage(new Page<QqUser>(request, response, -1), qqUser);
    		new ExportExcel("微博用户信息", QqUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出微博用户信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/qquser/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("qquser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QqUser> list = ei.getDataList(QqUser.class);
			for (QqUser qqUser : list){
				try{
					qqUserService.save(qqUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微博用户信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条微博用户信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入微博用户信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/qquser/?repage";
    }
	
	/**
	 * 下载导入微博用户信息数据模板
	 */
	@RequiresPermissions("qquser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微博用户信息数据导入模板.xlsx";
    		List<QqUser> list = Lists.newArrayList(); 
    		new ExportExcel("微博用户信息数据", QqUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/qquser/?repage";
    }
	
	
	

}