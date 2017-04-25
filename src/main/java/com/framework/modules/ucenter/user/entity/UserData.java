/**
 * Copyright &copy; 2015-2020 JEE-Framework All rights reserved.
 */
package com.framework.modules.ucenter.user.entity;

import org.hibernate.validator.constraints.Length;


import com.framework.common.persistence.DataEntity;
import com.framework.common.utils.excel.annotation.ExcelField;

/**
 * 保持用户注册登录信息Entity
 * @author admin
 * @version 2017-04-24
 */
public class UserData extends DataEntity<UserData> {
	
	private static final long serialVersionUID = 1L;
	private String userName;		// 登录名
	private String passwd;		// 登录密码
	private String nickName;		// 用户昵称
	private String avatarLarge;		// 用户大头像
	private String avatarMid;		// 用户中头像
	private String avatarSmall;		// 用户小头像
	private String registerBy;		// 注册类型(微博,微信,phone,email,QQ,Facebook,Twitter)
	private String registerPlatform;		// 注册平台(web,android,ios)
	private String registerIp;		// 注册IP
	private String firstName;		// 姓
	private String lastName;		// 名
	private Integer gender;		// 性别(1-男,2-女,3-其他)
	private Integer age;		// 年龄
	private String signature;		// 个性签名
	private String blog;		// 博客
	private String locale;		// 所在地
	private Integer verified;		// 用户认证状态(1-已认证,2-未认证)
	private Integer linkedWechat;		// 微信关联(1-是,2-否)
	private String wechatOpenid;		// 微信授权OPENID
	private Integer linkedWeibo;		// 微博关联(1-是,2-否)
	private String weiboOpenid;		// 微博授权OPENID
	private Integer linkedEmail;		// 邮箱关联(1-是,2-否)
	private Integer verifiedEmail;		// 邮箱是否认证(1-是,2-否)
	private Integer linkedQq;		// QQ关联(1-是,2-否)
	private String linkedQqPlatform;		// QQ认证平台
	private String qqOpenid;		// QQ关联(1-是,2-否)
	
	public UserData() {
		super();
	}

	public UserData(String id){
		super(id);
	}

	@Length(min=0, max=200, message="登录名长度必须介于 0 和 200 之间")
	@ExcelField(title="登录名", align=2, sort=1)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=200, message="登录密码长度必须介于 0 和 200 之间")
	@ExcelField(title="登录密码", align=2, sort=2)
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@Length(min=0, max=200, message="用户昵称长度必须介于 0 和 200 之间")
	@ExcelField(title="用户昵称", align=2, sort=3)
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Length(min=0, max=200, message="用户大头像长度必须介于 0 和 200 之间")
	@ExcelField(title="用户大头像", align=2, sort=4)
	public String getAvatarLarge() {
		return avatarLarge;
	}

	public void setAvatarLarge(String avatarLarge) {
		this.avatarLarge = avatarLarge;
	}
	
	@Length(min=0, max=200, message="用户中头像长度必须介于 0 和 200 之间")
	@ExcelField(title="用户中头像", align=2, sort=5)
	public String getAvatarMid() {
		return avatarMid;
	}

	public void setAvatarMid(String avatarMid) {
		this.avatarMid = avatarMid;
	}
	
	@Length(min=0, max=200, message="用户小头像长度必须介于 0 和 200 之间")
	@ExcelField(title="用户小头像", align=2, sort=6)
	public String getAvatarSmall() {
		return avatarSmall;
	}

	public void setAvatarSmall(String avatarSmall) {
		this.avatarSmall = avatarSmall;
	}
	
	@Length(min=0, max=45, message="注册类型(微博,微信,phone,email,QQ,Facebook,Twitter)长度必须介于 0 和 45 之间")
	@ExcelField(title="注册类型(微博,微信,phone,email,QQ,Facebook,Twitter)", align=2, sort=7)
	public String getRegisterBy() {
		return registerBy;
	}

	public void setRegisterBy(String registerBy) {
		this.registerBy = registerBy;
	}
	
	@Length(min=0, max=100, message="注册平台(web,android,ios)长度必须介于 0 和 100 之间")
	@ExcelField(title="注册平台(web,android,ios)", align=2, sort=8)
	public String getRegisterPlatform() {
		return registerPlatform;
	}

	public void setRegisterPlatform(String registerPlatform) {
		this.registerPlatform = registerPlatform;
	}
	
	@Length(min=0, max=200, message="注册IP长度必须介于 0 和 200 之间")
	@ExcelField(title="注册IP", align=2, sort=9)
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	
	@Length(min=0, max=200, message="姓长度必须介于 0 和 200 之间")
	@ExcelField(title="姓", align=2, sort=10)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Length(min=0, max=200, message="名长度必须介于 0 和 200 之间")
	@ExcelField(title="名", align=2, sort=11)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@ExcelField(title="性别(1-男,2-女,3-其他)", align=2, sort=12)
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	@ExcelField(title="年龄", align=2, sort=13)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	@Length(min=0, max=500, message="个性签名长度必须介于 0 和 500 之间")
	@ExcelField(title="个性签名", align=2, sort=14)
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	@Length(min=0, max=200, message="博客长度必须介于 0 和 200 之间")
	@ExcelField(title="博客", align=2, sort=15)
	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}
	
	@Length(min=0, max=200, message="所在地长度必须介于 0 和 200 之间")
	@ExcelField(title="所在地", align=2, sort=16)
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	@ExcelField(title="用户认证状态(1-已认证,2-未认证)", align=2, sort=17)
	public Integer getVerified() {
		return verified;
	}

	public void setVerified(Integer verified) {
		this.verified = verified;
	}
	
	@ExcelField(title="微信关联(1-是,2-否)", align=2, sort=18)
	public Integer getLinkedWechat() {
		return linkedWechat;
	}

	public void setLinkedWechat(Integer linkedWechat) {
		this.linkedWechat = linkedWechat;
	}
	
	@Length(min=0, max=200, message="微信授权OPENID长度必须介于 0 和 200 之间")
	@ExcelField(title="微信授权OPENID", align=2, sort=19)
	public String getWechatOpenid() {
		return wechatOpenid;
	}

	public void setWechatOpenid(String wechatOpenid) {
		this.wechatOpenid = wechatOpenid;
	}
	
	@ExcelField(title="微博关联(1-是,2-否)", align=2, sort=20)
	public Integer getLinkedWeibo() {
		return linkedWeibo;
	}

	public void setLinkedWeibo(Integer linkedWeibo) {
		this.linkedWeibo = linkedWeibo;
	}
	
	@Length(min=0, max=200, message="微博授权OPENID长度必须介于 0 和 200 之间")
	@ExcelField(title="微博授权OPENID", align=2, sort=21)
	public String getWeiboOpenid() {
		return weiboOpenid;
	}

	public void setWeiboOpenid(String weiboOpenid) {
		this.weiboOpenid = weiboOpenid;
	}
	
	@ExcelField(title="邮箱关联(1-是,2-否)", align=2, sort=22)
	public Integer getLinkedEmail() {
		return linkedEmail;
	}

	public void setLinkedEmail(Integer linkedEmail) {
		this.linkedEmail = linkedEmail;
	}
	
	@ExcelField(title="邮箱是否认证(1-是,2-否)", align=2, sort=23)
	public Integer getVerifiedEmail() {
		return verifiedEmail;
	}

	public void setVerifiedEmail(Integer verifiedEmail) {
		this.verifiedEmail = verifiedEmail;
	}
	
	@ExcelField(title="QQ关联(1-是,2-否)", align=2, sort=24)
	public Integer getLinkedQq() {
		return linkedQq;
	}

	public void setLinkedQq(Integer linkedQq) {
		this.linkedQq = linkedQq;
	}
	
	@Length(min=0, max=200, message="QQ认证平台长度必须介于 0 和 200 之间")
	@ExcelField(title="QQ认证平台", align=2, sort=25)
	public String getLinkedQqPlatform() {
		return linkedQqPlatform;
	}

	public void setLinkedQqPlatform(String linkedQqPlatform) {
		this.linkedQqPlatform = linkedQqPlatform;
	}
	
	@Length(min=0, max=200, message="QQ关联(1-是,2-否)长度必须介于 0 和 200 之间")
	@ExcelField(title="QQ关联(1-是,2-否)", align=2, sort=26)
	public String getQqOpenid() {
		return qqOpenid;
	}

	public void setQqOpenid(String qqOpenid) {
		this.qqOpenid = qqOpenid;
	}
	
}