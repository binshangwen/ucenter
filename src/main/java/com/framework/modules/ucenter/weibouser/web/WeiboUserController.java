/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weibouser.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.weibouser.entity.WeiboUser;
import com.framework.modules.ucenter.weibouser.service.WeiboUserService;
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
 * 存储微博用户信息Controller
 * @author binshangwen
 * @version 2017-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/weibouser")
public class WeiboUserController extends BaseController {

	@Autowired
	private WeiboUserService weiboUserService;
	
	@ModelAttribute
	public WeiboUser get(@RequestParam(required=false) String id) {
		WeiboUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = weiboUserService.get(id);
		}
		if (entity == null){
			entity = new WeiboUser();
		}
		return entity;
	}
	
	/**
	 * 微博用户信息列表页面
	 */
	@RequiresPermissions("weibouser:weiboUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(WeiboUser weiboUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WeiboUser> page = weiboUserService.findPage(new Page<WeiboUser>(request, response), weiboUser); 
		model.addAttribute("page", page);
		return "modules/ucenter/weibouser/weiboUserList";
	}

	/**
	 * 查看，增加，编辑微博用户信息表单页面
	 */
	@RequiresPermissions(value={"weibouser:weiboUser:view","weibouser:weiboUser:add","weibouser:weiboUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WeiboUser weiboUser, Model model) {
		model.addAttribute("weiboUser", weiboUser);
		return "modules/ucenter/weibouser/weiboUserForm";
	}

	/**
	 * 保存微博用户信息
	 */
	@RequiresPermissions(value={"weibouser:weiboUser:add","weibouser:weiboUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WeiboUser weiboUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, weiboUser)){
			return form(weiboUser, model);
		}
		if(!weiboUser.getIsNewRecord()){//编辑表单保存
			WeiboUser t = weiboUserService.get(weiboUser.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(weiboUser, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			weiboUserService.save(t);//保存
		}else{//新增表单保存
			weiboUserService.save(weiboUser);//保存
		}
		addMessage(redirectAttributes, "保存微博用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibouser/?repage";
	}
	
	/**
	 * 删除微博用户信息
	 */
	@RequiresPermissions("weibouser:weiboUser:del")
	@RequestMapping(value = "delete")
	public String delete(WeiboUser weiboUser, RedirectAttributes redirectAttributes) {
		weiboUserService.delete(weiboUser);
		addMessage(redirectAttributes, "删除微博用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibouser/?repage";
	}
	
	/**
	 * 批量删除微博用户信息
	 */
	@RequiresPermissions("weibouser:weiboUser:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			weiboUserService.delete(weiboUserService.get(id));
		}
		addMessage(redirectAttributes, "删除微博用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibouser/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("weibouser:weiboUser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(WeiboUser weiboUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微博用户信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WeiboUser> page = weiboUserService.findPage(new Page<WeiboUser>(request, response, -1), weiboUser);
    		new ExportExcel("微博用户信息", WeiboUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出微博用户信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibouser/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("weibouser:weiboUser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WeiboUser> list = ei.getDataList(WeiboUser.class);
			for (WeiboUser weiboUser : list){
				try{
					weiboUserService.save(weiboUser);
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
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibouser/?repage";
    }
	
	/**
	 * 下载导入微博用户信息数据模板
	 */
	@RequiresPermissions("weibouser:weiboUser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微博用户信息数据导入模板.xlsx";
    		List<WeiboUser> list = Lists.newArrayList(); 
    		new ExportExcel("微博用户信息数据", WeiboUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weibouser/?repage";
    }
	
	
	

}