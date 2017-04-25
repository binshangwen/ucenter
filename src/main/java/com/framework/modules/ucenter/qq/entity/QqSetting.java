/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.qq.entity;

import org.hibernate.validator.constraints.Length;

import com.framework.common.persistence.DataEntity;
import com.framework.common.utils.excel.annotation.ExcelField;

/**
 * QQ注册配置信息Entity
 * @author binshangwen
 * @version 2017-04-24
 */
public class QqSetting extends DataEntity<QqSetting> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String androidAppid;		// android平台授权APPID
	private String androidSecret;		// android平台密钥
	private String iosAppid;		// ios平台授权APPID
	private String iosSecret;		// ios平台密钥
	private String getTokenUrl;		// 获取token的url
	private String getInfoUrl;		// 获取用户信息的url
	
	public QqSetting() {
		super();
	}

	public QqSetting(String id){
		super(id);
	}

	@Length(min=1, max=45, message="名称长度必须介于 1 和 45 之间")
	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="android平台授权APPID长度必须介于 0 和 200 之间")
	@ExcelField(title="android平台授权APPID", align=2, sort=2)
	public String getAndroidAppid() {
		return androidAppid;
	}

	public void setAndroidAppid(String androidAppid) {
		this.androidAppid = androidAppid;
	}
	
	@Length(min=0, max=200, message="android平台密钥长度必须介于 0 和 200 之间")
	@ExcelField(title="android平台密钥", align=2, sort=3)
	public String getAndroidSecret() {
		return androidSecret;
	}

	public void setAndroidSecret(String androidSecret) {
		this.androidSecret = androidSecret;
	}
	
	@Length(min=0, max=200, message="ios平台授权APPID长度必须介于 0 和 200 之间")
	@ExcelField(title="ios平台授权APPID", align=2, sort=4)
	public String getIosAppid() {
		return iosAppid;
	}

	public void setIosAppid(String iosAppid) {
		this.iosAppid = iosAppid;
	}
	
	@Length(min=0, max=200, message="ios平台密钥长度必须介于 0 和 200 之间")
	@ExcelField(title="ios平台密钥", align=2, sort=5)
	public String getIosSecret() {
		return iosSecret;
	}

	public void setIosSecret(String iosSecret) {
		this.iosSecret = iosSecret;
	}
	
	@Length(min=1, max=200, message="获取token的url长度必须介于 1 和 200 之间")
	@ExcelField(title="获取token的url", align=2, sort=6)
	public String getGetTokenUrl() {
		return getTokenUrl;
	}

	public void setGetTokenUrl(String getTokenUrl) {
		this.getTokenUrl = getTokenUrl;
	}
	
	@Length(min=1, max=200, message="获取用户信息的url长度必须介于 1 和 200 之间")
	@ExcelField(title="获取用户信息的url", align=2, sort=7)
	public String getGetInfoUrl() {
		return getInfoUrl;
	}

	public void setGetInfoUrl(String getInfoUrl) {
		this.getInfoUrl = getInfoUrl;
	}
	
}