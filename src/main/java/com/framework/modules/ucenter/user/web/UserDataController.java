/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.user.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.user.entity.UserData;
import com.framework.modules.ucenter.user.service.UserDataService;
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
 * 保持用户注册登录信息Controller
 * @author admin
 * @version 2017-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/user")
public class UserDataController extends BaseController {

	@Autowired
	private UserDataService userDataService;
	
	@ModelAttribute
	public UserData get(@RequestParam(required=false) String id) {
		UserData entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = userDataService.get(id);
		}
		if (entity == null){
			entity = new UserData();
		}
		return entity;
	}
	
	/**
	 * 用户注册登录列表页面
	 */
	@RequiresPermissions("user:userData:list")
	@RequestMapping(value = {"list", ""})
	public String list(UserData userData, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserData> page = userDataService.findPage(new Page<UserData>(request, response), userData); 
		model.addAttribute("page", page);
		return "modules/ucenter/user/userDataList";
	}

	/**
	 * 查看，增加，编辑用户注册登录表单页面
	 */
	@RequiresPermissions(value={"user:userData:view","user:userData:add","user:userData:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(UserData userData, Model model) {
		model.addAttribute("userData", userData);
		return "modules/ucenter/user/userDataForm";
	}

	/**
	 * 保存用户注册登录
	 */
	@RequiresPermissions(value={"user:userData:add","user:userData:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(UserData userData, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, userData)){
			return form(userData, model);
		}
		if(!userData.getIsNewRecord()){//编辑表单保存
			UserData t = userDataService.get(userData.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(userData, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			userDataService.save(t);//保存
		}else{//新增表单保存
			userDataService.save(userData);//保存
		}
		addMessage(redirectAttributes, "保存用户注册登录成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/user/?repage";
	}
	
	/**
	 * 删除用户注册登录
	 */
	@RequiresPermissions("user:userData:del")
	@RequestMapping(value = "delete")
	public String delete(UserData userData, RedirectAttributes redirectAttributes) {
		userDataService.delete(userData);
		addMessage(redirectAttributes, "删除用户注册登录成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/user/?repage";
	}
	
	/**
	 * 批量删除用户注册登录
	 */
	@RequiresPermissions("user:userData:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			userDataService.delete(userDataService.get(id));
		}
		addMessage(redirectAttributes, "删除用户注册登录成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/user/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("user:userData:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(UserData userData, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户注册登录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<UserData> page = userDataService.findPage(new Page<UserData>(request, response, -1), userData);
    		new ExportExcel("用户注册登录", UserData.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户注册登录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/user/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user:userData:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<UserData> list = ei.getDataList(UserData.class);
			for (UserData userData : list){
				try{
					userDataService.save(userData);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户注册登录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户注册登录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户注册登录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/user/?repage";
    }
	
	/**
	 * 下载导入用户注册登录数据模板
	 */
	@RequiresPermissions("user:userData:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户注册登录数据导入模板.xlsx";
    		List<UserData> list = Lists.newArrayList(); 
    		new ExportExcel("用户注册登录数据", UserData.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/user/?repage";
    }
	
	
	

}