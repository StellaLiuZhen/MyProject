/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.wxshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.supcan.annotation.treelist.cols.SupCol;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 用户Entity
 * @author ThinkGem
 * @version 2013-12-05
 */
public class Member extends DataEntity<Member> {

	private static final long serialVersionUID = 1L;
	private String mid;
	private String password;
	private String name;
	private String phone;
	private String address;
	private String code;
	private Date regdate;
	private String photo;
	private String delFlag;	// 是否允许登陆

	public Member() {
		super();
		this.delFlag = delFlag;
	}

	public Member(String mid){
		this();
		this.mid = mid ;
	}

	public Member(String mid,String name){
		this(mid);
		this.name = name;
	}

	@NotNull
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@JsonIgnore
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@SupCol(isUnique="true", isHide="true")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Length(min = 1,max = 1)
	@Override
	public String getDelFlag() {
		return delFlag;
	}

	@Override
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}