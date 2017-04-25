/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weichat.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.weichat.entity.WechatSetting;
import com.framework.modules.ucenter.weichat.service.WechatSettingService;
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
 * 微信注册配置信息Controller
 * @author binshangwen
 * @version 2017-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/weichat")
public class WechatSettingController extends BaseController {

	@Autowired
	private WechatSettingService wechatSettingService;
	
	@ModelAttribute
	public WechatSetting get(@RequestParam(required=false) String id) {
		WechatSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wechatSettingService.get(id);
		}
		if (entity == null){
			entity = new WechatSetting();
		}
		return entity;
	}
	
	/**
	 * 微信注册配置列表页面
	 */
	@RequiresPermissions("weichat:wechatSetting:list")
	@RequestMapping(value = {"list", ""})
	public String list(WechatSetting wechatSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WechatSetting> page = wechatSettingService.findPage(new Page<WechatSetting>(request, response), wechatSetting); 
		model.addAttribute("page", page);
		return "modules/ucenter/weichat/wechatSettingList";
	}

	/**
	 * 查看，增加，编辑微信注册配置表单页面
	 */
	@RequiresPermissions(value={"weichat:wechatSetting:view","weichat:wechatSetting:add","weichat:wechatSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WechatSetting wechatSetting, Model model) {
		model.addAttribute("wechatSetting", wechatSetting);
		return "modules/ucenter/weichat/wechatSettingForm";
	}

	/**
	 * 保存微信注册配置
	 */
	@RequiresPermissions(value={"weichat:wechatSetting:add","weichat:wechatSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WechatSetting wechatSetting, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wechatSetting)){
			return form(wechatSetting, model);
		}
		if(!wechatSetting.getIsNewRecord()){//编辑表单保存
			WechatSetting t = wechatSettingService.get(wechatSetting.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(wechatSetting, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			wechatSettingService.save(t);//保存
		}else{//新增表单保存
			wechatSettingService.save(wechatSetting);//保存
		}
		addMessage(redirectAttributes, "保存微信注册配置成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichat/?repage";
	}
	
	/**
	 * 删除微信注册配置
	 */
	@RequiresPermissions("weichat:wechatSetting:del")
	@RequestMapping(value = "delete")
	public String delete(WechatSetting wechatSetting, RedirectAttributes redirectAttributes) {
		wechatSettingService.delete(wechatSetting);
		addMessage(redirectAttributes, "删除微信注册配置成功");
		return "redirect:"+Global.getAdminPath()+"/weichat/?repage";
	}
	
	/**
	 * 批量删除微信注册配置
	 */
	@RequiresPermissions("weichat:wechatSetting:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wechatSettingService.delete(wechatSettingService.get(id));
		}
		addMessage(redirectAttributes, "删除微信注册配置成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichat/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("weichat:wechatSetting:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(WechatSetting wechatSetting, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微信注册配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WechatSetting> page = wechatSettingService.findPage(new Page<WechatSetting>(request, response, -1), wechatSetting);
    		new ExportExcel("微信注册配置", WechatSetting.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出微信注册配置记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichat/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("weichat:wechatSetting:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WechatSetting> list = ei.getDataList(WechatSetting.class);
			for (WechatSetting wechatSetting : list){
				try{
					wechatSettingService.save(wechatSetting);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微信注册配置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条微信注册配置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入微信注册配置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichat/?repage";
    }
	
	/**
	 * 下载导入微信注册配置数据模板
	 */
	@RequiresPermissions("weichat:wechatSetting:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微信注册配置数据导入模板.xlsx";
    		List<WechatSetting> list = Lists.newArrayList(); 
    		new ExportExcel("微信注册配置数据", WechatSetting.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichat/?repage";
    }
	
	
	

}