/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.qq.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.qq.entity.QqSetting;
import com.framework.modules.ucenter.qq.service.QqSettingService;
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
 * QQ注册配置信息Controller
 * @author binshangwen
 * @version 2017-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/qq")
public class QqSettingController extends BaseController {

	@Autowired
	private QqSettingService qqSettingService;
	
	@ModelAttribute
	public QqSetting get(@RequestParam(required=false) String id) {
		QqSetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qqSettingService.get(id);
		}
		if (entity == null){
			entity = new QqSetting();
		}
		return entity;
	}
	
	/**
	 * QQ信息列表页面
	 */
	@RequiresPermissions("qq:qqSetting:list")
	@RequestMapping(value = {"list", ""})
	public String list(QqSetting qqSetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QqSetting> page = qqSettingService.findPage(new Page<QqSetting>(request, response), qqSetting); 
		model.addAttribute("page", page);
		return "modules/ucenter/qq/qqSettingList";
	}

	/**
	 * 查看，增加，编辑QQ信息表单页面
	 */
	@RequiresPermissions(value={"qq:qqSetting:view","qq:qqSetting:add","qq:qqSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(QqSetting qqSetting, Model model) {
		model.addAttribute("qqSetting", qqSetting);
		return "modules/ucenter/qq/qqSettingForm";
	}

	/**
	 * 保存QQ信息
	 */
	@RequiresPermissions(value={"qq:qqSetting:add","qq:qqSetting:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(QqSetting qqSetting, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, qqSetting)){
			return form(qqSetting, model);
		}
		if(!qqSetting.getIsNewRecord()){//编辑表单保存
			QqSetting t = qqSettingService.get(qqSetting.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(qqSetting, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			qqSettingService.save(t);//保存
		}else{//新增表单保存
			qqSettingService.save(qqSetting);//保存
		}
		addMessage(redirectAttributes, "保存QQ信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/qq/?repage";
	}
	
	/**
	 * 删除QQ信息
	 */
	@RequiresPermissions("qq:qqSetting:del")
	@RequestMapping(value = "delete")
	public String delete(QqSetting qqSetting, RedirectAttributes redirectAttributes) {
		qqSettingService.delete(qqSetting);
		addMessage(redirectAttributes, "删除QQ信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/qq/?repage";
	}
	
	/**
	 * 批量删除QQ信息
	 */
	@RequiresPermissions("qq:qqSetting:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qqSettingService.delete(qqSettingService.get(id));
		}
		addMessage(redirectAttributes, "删除QQ信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/qq/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("qq:qqSetting:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(QqSetting qqSetting, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "QQ信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QqSetting> page = qqSettingService.findPage(new Page<QqSetting>(request, response, -1), qqSetting);
    		new ExportExcel("QQ信息", QqSetting.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出QQ信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/qq/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("qq:qqSetting:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QqSetting> list = ei.getDataList(QqSetting.class);
			for (QqSetting qqSetting : list){
				try{
					qqSettingService.save(qqSetting);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条QQ信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条QQ信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入QQ信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/qq/?repage";
    }
	
	/**
	 * 下载导入QQ信息数据模板
	 */
	@RequiresPermissions("qq:qqSetting:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "QQ信息数据导入模板.xlsx";
    		List<QqSetting> list = Lists.newArrayList(); 
    		new ExportExcel("QQ信息数据", QqSetting.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/qq/?repage";
    }
	
	
	

}