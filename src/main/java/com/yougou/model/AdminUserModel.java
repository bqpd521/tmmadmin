package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能说明：后台管理员相关model
 * 创建时间：2014-09-25 yinyunfei
 */
@Entity
@Table(name = "tbl_user_user")
public class AdminUserModel  implements Serializable{

	private static final long serialVersionUID = 2113066490439846407L;
	//自增主键
	private int id;
	//用户登录帐号
	private String userAccount;
	//密码
	private String userPsw;
	//用户名称
	private String userName;
	//用户邮箱
	private String userMail;
	//用户手机
	private String userPhone;
	//用户所属部门
	private String userDept;
	//标识字估1 超级管理员；2：普通管理员；3：4
	private String userType;
	//状态 1：可用，0不可用
	private String userState;
	//用户最后一次登录时间
	private String loginTime;
	//记录插入时间
	private String insertTime;

	@Column(name = "userMail")
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
	@Column(name = "userPhone")
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@Column(name = "userDept")
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	
	@Column(name = "userType")
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Column(name = "userState")
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	
	@Column(name = "loginTime")
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	
	@Column(name = "userAccount", length = 50)
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	@Column(name = "userPsw", length = 50)
	public String getUserPsw() {
		return userPsw;
	}
	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}
	
	@Column(name = "userName", length = 20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "insertTime", length = 30)
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
