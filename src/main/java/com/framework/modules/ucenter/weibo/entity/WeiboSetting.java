/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.weibo.entity;

import org.hibernate.validator.constraints.Length;

import com.framework.common.persistence.DataEntity;
import com.framework.common.utils.excel.annotation.ExcelField;

/**
 * 存储微博配置信息Entity
 * @author binshangwen
 * @version 2017-04-24
 */
public class WeiboSetting extends DataEntity<WeiboSetting> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String appid;		// 微博平台的APPID
	private String secret;		// 密钥
	private String getTokenUrl;		// 微信获取acctessToken地址
	private String getInfoUrl;		// 获取用户信息地址
	
	public WeiboSetting() {
		super();
	}

	public WeiboSetting(String id){
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
	
	@Length(min=1, max=200, message="微博平台的APPID长度必须介于 1 和 200 之间")
	@ExcelField(title="微博平台的APPID", align=2, sort=2)
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@Length(min=1, max=200, message="密钥长度必须介于 1 和 200 之间")
	@ExcelField(title="密钥", align=2, sort=3)
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Length(min=1, max=200, message="微信获取acctessToken地址长度必须介于 1 和 200 之间")
	@ExcelField(title="微信获取acctessToken地址", align=2, sort=4)
	public String getGetTokenUrl() {
		return getTokenUrl;
	}

	public void setGetTokenUrl(String getTokenUrl) {
		this.getTokenUrl = getTokenUrl;
	}
	
	@Length(min=1, max=200, message="获取用户信息地址长度必须介于 1 和 200 之间")
	@ExcelField(title="获取用户信息地址", align=2, sort=5)
	public String getGetInfoUrl() {
		return getInfoUrl;
	}

	public void setGetInfoUrl(String getInfoUrl) {
		this.getInfoUrl = getInfoUrl;
	}
	
}