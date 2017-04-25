/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.email.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.framework.common.persistence.DataEntity;
import com.framework.common.utils.excel.annotation.ExcelField;

/**
 * 存储邮箱注册配置信息Entity
 * @author binshangwen
 * @version 2017-04-25
 */
public class EmailSetting extends DataEntity<EmailSetting> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String emailName;		// 邮件服务的邮箱名称
	private String emailAccount;		// 邮件服务帐号
	private String emailSecret;		// 邮件服务帐号密码
	private String stmpHost;		// 发邮件服务的host
	private Integer stmpPort;		// 发邮件服务的端口
	private String registerModel;		// 注册发送邮件模版
	private String resetModel;		// 重置密码发送邮件模版
	
	public EmailSetting() {
		super();
	}

	public EmailSetting(String id){
		super(id);
	}

	@Length(min=1, max=200, message="名称长度必须介于 1 和 200 之间")
	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=200, message="邮件服务的邮箱名称长度必须介于 1 和 200 之间")
	@ExcelField(title="邮件服务的邮箱名称", align=2, sort=2)
	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}
	
	@Length(min=1, max=200, message="邮件服务帐号长度必须介于 1 和 200 之间")
	@ExcelField(title="邮件服务帐号", align=2, sort=3)
	public String getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}
	
	@Length(min=1, max=200, message="邮件服务帐号密码长度必须介于 1 和 200 之间")
	@ExcelField(title="邮件服务帐号密码", align=2, sort=4)
	public String getEmailSecret() {
		return emailSecret;
	}

	public void setEmailSecret(String emailSecret) {
		this.emailSecret = emailSecret;
	}
	
	@Length(min=1, max=100, message="发邮件服务的host长度必须介于 1 和 100 之间")
	@ExcelField(title="发邮件服务的host", align=2, sort=5)
	public String getStmpHost() {
		return stmpHost;
	}

	public void setStmpHost(String stmpHost) {
		this.stmpHost = stmpHost;
	}
	
	@NotNull(message="发邮件服务的端口不能为空")
	@ExcelField(title="发邮件服务的端口", align=2, sort=6)
	public Integer getStmpPort() {
		return stmpPort;
	}

	public void setStmpPort(Integer stmpPort) {
		this.stmpPort = stmpPort;
	}
	
	@Length(min=1, max=300, message="注册发送邮件模版长度必须介于 1 和 300 之间")
	@ExcelField(title="注册发送邮件模版", align=2, sort=7)
	public String getRegisterModel() {
		return registerModel;
	}

	public void setRegisterModel(String registerModel) {
		this.registerModel = registerModel;
	}
	
	@Length(min=1, max=300, message="重置密码发送邮件模版长度必须介于 1 和 300 之间")
	@ExcelField(title="重置密码发送邮件模版", align=2, sort=8)
	public String getResetModel() {
		return resetModel;
	}

	public void setResetModel(String resetModel) {
		this.resetModel = resetModel;
	}
	
}