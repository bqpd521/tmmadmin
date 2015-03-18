package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能说明：销毁记录model
 * 创建时间：2015-01-05 yinyunfei
 */
@Entity
@Table(name = "tbl_books_destroy")
public class BookDestroyModel implements Serializable{

	private static final long serialVersionUID = -3004526444791924567L;

	private int id;
	//登录帐号
	private String userAccount;
	//教材ID
	private String bookId;
	//教材名称
	private String bookName;
	//销毁数量
	private String destroyCount;
	//插入时间
	private String insertTime;
	
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
	
	@Column(name="bookId")
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@Column(name="bookName")
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Column(name="destroyCount")
	public String getDestroyCount() {
		return destroyCount;
	}
	public void setDestroyCount(String destroyCount) {
		this.destroyCount = destroyCount;
	}
	
	@Column(name="insertTime")
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}
