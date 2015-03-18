package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用途说明：按期班使用信息
 * @author yinyunfei 2015-01-09
 */
@Entity
@Table(name = "tbl_books_class")
public class BookClassModel implements Serializable{

	private static final long serialVersionUID = 9206133699646892845L;
	
	private int id;
	//适用期班名称
    private String className;
    //教材ID
    private String bookId;
    //课程名称
    private String courseName;
    //教材名称
    private String bookName;
    //出版社
    private String bookPress;
    //作者
    private String bookAuthor;
    //出版时间
    private String pressTime;
    //使用数量
    private String userCount;
    //发放数量
    private String sendCount;
    //插入时间
    private String insertTime;
    //当关状态 1已发放；0未发放。
    private String sendState;
    //上传ID
    private String uploadId;
	//领取人姓名
    private String lqUser;
    //领取时间
    private String lqTime;
    
    @Column(name="lqUser")
    public String getLqUser() {
		return lqUser;
	}
	public void setLqUser(String lqUser) {
		this.lqUser = lqUser;
	}
	
	@Column(name="lqTime")
	public String getLqTime() {
		return lqTime;
	}
	public void setLqTime(String lqTime) {
		this.lqTime = lqTime;
	}
    
    @Column(name="uploadId")
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
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
	
	@Column(name="className")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Column(name="bookId")
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@Column(name="courseName")
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	@Column(name="bookName")
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Column(name="bookPress")
	public String getBookPress() {
		return bookPress;
	}
	public void setBookPress(String bookPress) {
		this.bookPress = bookPress;
	}
	
	@Column(name="bookAuthor")
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
	@Column(name="pressTime")
	public String getPressTime() {
		return pressTime;
	}
	public void setPressTime(String pressTime) {
		this.pressTime = pressTime;
	}
	
	@Column(name="userCount")
	public String getUserCount() {
		return userCount;
	}
	public void setUserCount(String userCount) {
		this.userCount = userCount;
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
	
	@Column(name="sendState")
	public String getSendState() {
		return sendState;
	}
	public void setSendState(String sendState) {
		this.sendState = sendState;
	}
}
