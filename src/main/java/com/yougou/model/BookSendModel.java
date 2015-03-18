package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 功能说明：发放记录model
 * 创建时间：2014-09-25 yinyunfei
 */
@Entity
@Table(name = "tbl_books_send")
public class BookSendModel implements Serializable{

	private static final long serialVersionUID = 6596240657736112068L;
    
	//自增主键
	private int id;
	//教材名称
	private String bookName;
	//教材ID
	private String bookId;
	//管理员ID
	private String userId;
	//发放人姓名
	private String sendUser;
	//领取数量
	private String sendCount;
	//插入时间
	private String insertTime;
	//邻用类型  1： 教材参谋领取；2：其它人领取。     3:学员发放  （2015-01-24 新增需求  长春）
	private String sendType;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="bookName")
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Column(name="bookId")
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@Column(name="userId")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="sendUser")
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	
	@Column(name="sendCount")
	public String getSendCount() {
		return sendCount;
	}
	
	public void setSendCount(String sendCount) {
		this.sendCount = sendCount;
	}
	
	@Column(name="insertTime")
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	@Column(name="sendType")
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
}
