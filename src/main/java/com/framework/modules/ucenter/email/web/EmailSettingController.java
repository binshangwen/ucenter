/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.email.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.email.entity.EmailSetting;
import com.framework.modules.ucenter.email.service.EmailSettingService;
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
 * 存储邮箱注册配置信息Controller
 * @author binshangwen
 * @version 2017-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/email")
public class EmailSettingController extends BaseController {

	@Autowired
	private EmailSettingService emailSettingService;
	
	@ModelAttribute
	public EmailSetting get(@RequestParam(required=false) String id) {
		EmailSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = emailSettingService.get(id);
		}
		if (entity == null){
			entity = new EmailSetting();
		}
		return entity;
	}
	
	/**
	 * 邮箱信息列表页面
	 */
	@RequiresPermissions("ucenter:email:list")
	@RequestMapping(value = {"list", ""})
	public String list(EmailSetting emailSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EmailSetting> page = emailSettingService.findPage(new Page<EmailSetting>(request, response), emailSetting); 
		model.addAttribute("page", page);
		return "modules/ucenter/email/emailSettingList";
	}

	/**
	 * 查看，增加，编辑邮箱信息表单页面
	 */
	@RequiresPermissions(value={"ucenter:email:view","ucenter:email:add","ucenter:email:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(EmailSetting emailSetting, Model model) {
		model.addAttribute("emailSetting", emailSetting);
		return "modules/ucenter/email/emailSettingForm";
	}

	/**
	 * 保存邮箱信息
	 */
	@RequiresPermissions(value={"ucenter:email:add","ucenter:email:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(EmailSetting emailSetting, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, emailSetting)){
			return form(emailSetting, model);
		}
		if(!emailSetting.getIsNewRecord()){//编辑表单保存
			EmailSetting t = emailSettingService.get(emailSetting.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(emailSetting, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			emailSettingService.save(t);//保存
		}else{//新增表单保存
			emailSettingService.save(emailSetting);//保存
		}
		addMessage(redirectAttributes, "保存邮箱信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/email/?repage";
	}
	
	/**
	 * 删除邮箱信息
	 */
	@RequiresPermissions("ucenter:email:del")
	@RequestMapping(value = "delete")
	public String delete(EmailSetting emailSetting, RedirectAttributes redirectAttributes) {
		emailSettingService.delete(emailSetting);
		addMessage(redirectAttributes, "删除邮箱信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/email/?repage";
	}
	
	/**
	 * 批量删除邮箱信息
	 */
	@RequiresPermissions("ucenter:email:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			emailSettingService.delete(emailSettingService.get(id));
		}
		addMessage(redirectAttributes, "删除邮箱信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/email/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ucenter:email:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(EmailSetting emailSetting, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮箱信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EmailSetting> page = emailSettingService.findPage(new Page<EmailSetting>(request, response, -1), emailSetting);
    		new ExportExcel("邮箱信息", EmailSetting.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出邮箱信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/email/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ucenter:email:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EmailSetting> list = ei.getDataList(EmailSetting.class);
			for (EmailSetting emailSetting : list){
				try{
					emailSettingService.save(emailSetting);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条邮箱信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮箱信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮箱信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/email/?repage";
    }
	
	/**
	 * 下载导入邮箱信息数据模板
	 */
	@RequiresPermissions("ucenter:email:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮箱信息数据导入模板.xlsx";
    		List<EmailSetting> list = Lists.newArrayList(); 
    		new ExportExcel("邮箱信息数据", EmailSetting.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/email/?repage";
    }
	
	
	

}