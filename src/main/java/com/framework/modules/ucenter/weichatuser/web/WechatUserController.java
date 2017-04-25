/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weichatuser.web;

import com.framework.common.config.Global;
import com.framework.common.persistence.Page;
import com.framework.common.utils.DateUtils;
import com.framework.common.utils.MyBeanUtils;
import com.framework.common.utils.StringUtils;
import com.framework.common.utils.excel.ExportExcel;
import com.framework.common.utils.excel.ImportExcel;
import com.framework.common.web.BaseController;
import com.framework.modules.ucenter.weichatuser.entity.WechatUser;
import com.framework.modules.ucenter.weichatuser.service.WechatUserService;
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
 * 存储微信用户信息Controller
 * @author binshangwen
 * @version 2017-04-24
 */
@Controller
@RequestMapping(value = "${adminPath}/ucenter/weichatuser")
public class WechatUserController extends BaseController {

	@Autowired
	private WechatUserService wechatUserService;
	
	@ModelAttribute
	public WechatUser get(@RequestParam(required=false) String id) {
		WechatUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wechatUserService.get(id);
		}
		if (entity == null){
			entity = new WechatUser();
		}
		return entity;
	}
	
	/**
	 * 微信用户信息列表页面
	 */
	@RequiresPermissions("weichatuser:wechatUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(WechatUser wechatUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WechatUser> page = wechatUserService.findPage(new Page<WechatUser>(request, response), wechatUser); 
		model.addAttribute("page", page);
		return "modules/ucenter/weichatuser/wechatUserList";
	}

	/**
	 * 查看，增加，编辑微信用户信息表单页面
	 */
	@RequiresPermissions(value={"weichatuser:wechatUser:view","weichatuser:wechatUser:add","weichatuser:wechatUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WechatUser wechatUser, Model model) {
		model.addAttribute("wechatUser", wechatUser);
		return "modules/ucenter/weichatuser/wechatUserForm";
	}

	/**
	 * 保存微信用户信息
	 */
	@RequiresPermissions(value={"weichatuser:wechatUser:add","weichatuser:wechatUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WechatUser wechatUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wechatUser)){
			return form(wechatUser, model);
		}
		if(!wechatUser.getIsNewRecord()){//编辑表单保存
			WechatUser t = wechatUserService.get(wechatUser.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(wechatUser, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			wechatUserService.save(t);//保存
		}else{//新增表单保存
			wechatUserService.save(wechatUser);//保存
		}
		addMessage(redirectAttributes, "保存微信用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichatuser/?repage";
	}
	
	/**
	 * 删除微信用户信息
	 */
	@RequiresPermissions("weichatuser:wechatUser:del")
	@RequestMapping(value = "delete")
	public String delete(WechatUser wechatUser, RedirectAttributes redirectAttributes) {
		wechatUserService.delete(wechatUser);
		addMessage(redirectAttributes, "删除微信用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichatuser/?repage";
	}
	
	/**
	 * 批量删除微信用户信息
	 */
	@RequiresPermissions("weichatuser:wechatUser:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wechatUserService.delete(wechatUserService.get(id));
		}
		addMessage(redirectAttributes, "删除微信用户信息成功");
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichatuser/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("weichatuser:wechatUser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(WechatUser wechatUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微信用户信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WechatUser> page = wechatUserService.findPage(new Page<WechatUser>(request, response, -1), wechatUser);
    		new ExportExcel("微信用户信息", WechatUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出微信用户信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichatuser/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("weichatuser:wechatUser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WechatUser> list = ei.getDataList(WechatUser.class);
			for (WechatUser wechatUser : list){
				try{
					wechatUserService.save(wechatUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微信用户信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条微信用户信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入微信用户信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichatuser/?repage";
    }
	
	/**
	 * 下载导入微信用户信息数据模板
	 */
	@RequiresPermissions("weichatuser:wechatUser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "微信用户信息数据导入模板.xlsx";
    		List<WechatUser> list = Lists.newArrayList(); 
    		new ExportExcel("微信用户信息数据", WechatUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ucenter/weichatuser/?repage";
    }
	
	
	

}