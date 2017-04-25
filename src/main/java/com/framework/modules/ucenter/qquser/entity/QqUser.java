/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.qquser.entity;

import org.hibernate.validator.constraints.Length;


import com.framework.common.persistence.DataEntity;
import com.framework.common.utils.excel.annotation.ExcelField;

/**
 * 存储QQ用户信息Entity
 * @author binshangwen
 * @version 2017-04-24
 */
public class QqUser extends DataEntity<QqUser> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 客户端提供code值
	private String signPlatform;		// 注册平台(web,android,ios)
	private String openId;		// 通过code获得的授权信息
	private String accessToken;		// 通过code获得AccessToken
	private String refreshToken;		// 通过code获得refreshToken
	private String avatarLarge;		// 用户大头像
	private String avatarMid;		// 用户中头像
	private String avatarSmall;		// 用户小头像
	private String nickName;		// 用户昵称
	private Integer gender;		// 性别(1-男,2-女,3-其他)
	private Integer age;		// 年龄
	private String signature;		// 个性签名
	
	public QqUser() {
		super();
	}

	public QqUser(String id){
		super(id);
	}

	@Length(min=0, max=200, message="客户端提供code值长度必须介于 0 和 200 之间")
	@ExcelField(title="客户端提供code值", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=200, message="注册平台(web,android,ios)长度必须介于 0 和 200 之间")
	@ExcelField(title="注册平台(web,android,ios)", align=2, sort=2)
	public String getSignPlatform() {
		return signPlatform;
	}

	public void setSignPlatform(String signPlatform) {
		this.signPlatform = signPlatform;
	}
	
	@Length(min=0, max=300, message="通过code获得的授权信息长度必须介于 0 和 300 之间")
	@ExcelField(title="通过code获得的授权信息", align=2, sort=3)
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=300, message="通过code获得AccessToken长度必须介于 0 和 300 之间")
	@ExcelField(title="通过code获得AccessToken", align=2, sort=4)
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	@Length(min=0, max=300, message="通过code获得refreshToken长度必须介于 0 和 300 之间")
	@ExcelField(title="通过code获得refreshToken", align=2, sort=5)
	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	@Length(min=0, max=200, message="用户大头像长度必须介于 0 和 200 之间")
	@ExcelField(title="用户大头像", align=2, sort=6)
	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}
	
	@Length(min=0, max=200, message="用户中头像长度必须介于 0 和 200 之间")
	@ExcelField(title="用户中头像", align=2, sort=7)
	public String getAvatarMid() {
		return avatarMid;
	}

	public void setAvatarMid(String avatarMid) {
		this.avatarMid = avatarMid;
	}
	
	@Length(min=0, max=200, message="用户小头像长度必须介于 0 和 200 之间")
	@ExcelField(title="用户小头像", align=2, sort=8)
	public String getAvatarSmall() {
		return avatarSmall;
	}

	public void setAvatarSmall(String avatarSmall) {
		this.avatarSmall = avatarSmall;
	}
	
	@Length(min=0, max=200, message="用户昵称长度必须介于 0 和 200 之间")
	@ExcelField(title="用户昵称", align=2, sort=9)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@ExcelField(title="性别(1-男,2-女,3-其他)", align=2, sort=10)
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	@ExcelField(title="年龄", align=2, sort=11)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Length(min=0, max=500, message="个性签名长度必须介于 0 和 500 之间")
	@ExcelField(title="个性签名", align=2, sort=12)
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}