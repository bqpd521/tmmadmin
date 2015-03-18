package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用途说明：上传EXCEL信处 
 * @author yinyunfei 2015-01-09
 */
@Entity
@Table(name = "tbl_books_uploads")
public class BookUploadModel  implements Serializable{

	private static final long serialVersionUID = 1605530293572186305L;
	private int id;
	private String userAccount;
	private String userName;
	private String uploadDate;
	private String insertTime;
	private String uploadState;
	private String excelUrl;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="userAccount")
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	@Column(name="userName")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="uploadDate")
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	@Column(name="insertTime")
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	@Column(name="uploadState")
	public String getUploadState() {
		return uploadState;
	}
	public void setUploadState(String uploadState) {
		this.uploadState = uploadState;
	}
	
	@Column(name="excelUrl")
	public String getExcelUrl() {
		return excelUrl;
	}
	public void setExcelUrl(String excelUrl) {
		this.excelUrl = excelUrl;
	}
}
