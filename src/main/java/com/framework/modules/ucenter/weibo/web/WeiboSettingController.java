/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weibo.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.weibo.entity.WeiboSetting;
import com.framework.modules.ucenter.weibo.service.WeiboSettingService;
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
 * 存储微博配置信息Controller
 * @author binshangwen
 * @version 2017-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/weibo")
public class WeiboSettingController extends BaseController {

	@Autowired
	private WeiboSettingService weiboSettingService;
	
	@ModelAttribute
	public WeiboSetting get(@RequestParam(required=false) String id) {
		WeiboSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiboSettingService.get(id);
		}
		if (entity == null){
			entity = new WeiboSetting();
		}
		return entity;
	}
	
	/**
	 * 微博配置信息列表页面
	 */
	@RequiresPermissions("weibo:weiboSetting:list")
	@RequestMapping(value = {"list", ""})
	public String list(WeiboSetting weiboSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiboSetting> page = weiboSettingService.findPage(new Page<WeiboSetting>(request, response), weiboSetting); 
		model.addAttribute("page", page);
		return "modules/ucenter/weibo/weiboSettingList";
	}

	/**
	 * 查看，增加，编辑微博配置信息表单页面
	 */
	@RequiresPermissions(value={"weibo:weiboSetting:view","weibo:weiboSetting:add","weibo:weiboSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WeiboSetting weiboSetting, Model model) {
		model.addAttribute("weiboSetting", weiboSetting);
		return "modules/ucenter/weibo/weiboSettingForm";
	}

	/**
	 * 保存微博配置信息
	 */
	@RequiresPermissions(value={"weibo:weiboSetting:add","weibo:weiboSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WeiboSetting weiboSetting, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, weiboSetting)){
			return form(weiboSetting, model);
		}
		if(!weiboSetting.getIsNewRecord()){//编辑表单保存
			WeiboSetting t = weiboSettingService.get(weiboSetting.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(weiboSetting, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			weiboSettingService.save(t);//保存
		}else{//新增表单保存
			weiboSettingService.save(weiboSetting);//保存
		}
		addMessage(redirectAttributes, "保存微博配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibo/?repage";
	}
	
	/**
	 * 删除微博配置信息
	 */
	@RequiresPermissions("weibo:weiboSetting:del")
	@RequestMapping(value = "delete")
	public String delete(WeiboSetting weiboSetting, RedirectAttributes redirectAttributes) {
		weiboSettingService.delete(weiboSetting);
		addMessage(redirectAttributes, "删除微博配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibo/?repage";
	}
	
	/**
	 * 批量删除微博配置信息
	 */
	@RequiresPermissions("weibo:weiboSetting:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			weiboSettingService.delete(weiboSettingService.get(id));
		}
		addMessage(redirectAttributes, "删除微博配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibo/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("weibo:weiboSetting:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(WeiboSetting weiboSetting, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微博配置信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WeiboSetting> page = weiboSettingService.findPage(new Page<WeiboSetting>(request, response, -1), weiboSetting);
    		new ExportExcel("微博配置信息", WeiboSetting.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出微博配置信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibo/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("weibo:weiboSetting:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WeiboSetting> list = ei.getDataList(WeiboSetting.class);
			for (WeiboSetting weiboSetting : list){
				try{
					weiboSettingService.save(weiboSetting);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微博配置信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条微博配置信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入微博配置信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibo/?repage";
    }
	
	/**
	 * 下载导入微博配置信息数据模板
	 */
	@RequiresPermissions("weibo:weiboSetting:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微博配置信息数据导入模板.xlsx";
    		List<WeiboSetting> list = Lists.newArrayList(); 
    		new ExportExcel("微博配置信息数据", WeiboSetting.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibo/?repage";
    }
	
	
	

}