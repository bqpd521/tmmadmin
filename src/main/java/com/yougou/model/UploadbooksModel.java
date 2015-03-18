package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.xml.internal.bind.v2.model.core.ID;

/**
 * 用途说明：按期班使用图书信息（使用计划）
 * @author yinyunfei 2015-01-09
 */
@Entity
@Table(name = "tbl_books_uploadbooks")
public class UploadbooksModel  implements Serializable{

	private static final long serialVersionUID = -5634002623782724647L;
	//
	private int id;
	//课程名称
	private String courseName;
	//教材ID
	private String bookId;
	//教材名称
	private String bookName;
	//出版社
	private String bookPress;
	//作者
	private String bookAuthor;
	//出版时间
	private String pressTime;
	//使用班级
	private String userClass;
	//上传表中的ID
	private String uploadId;
	//使用数量
	private String userCount;
	//插入时间
	private String insertTime;
	//状态     1：已处理       0：未处理
	private String sendState;
	//库存数量
	private String kcCount;
	//大类： 1： 外购、2：印刷、3：配发、4：调拨 ，5其它
	private String bigType;
	//是否要进行打印设置，0：需要打印，1：不需要
    private String isPrint;
    
    @Column(name="isPrint")
	public String getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}
	//缺货数量
	private String queCount;
	@Column(name="queCount")
	public String getQueCount() {
		return queCount;
	}
	public void setQueCount(String queCount) {
		this.queCount = queCount;
	}
	
	@Transient
	public String getKcCount() {
		return kcCount;
	}
	public void setKcCount(String kcCount) {
		this.kcCount = kcCount;
	}
	
	@Column(name="bigType")
	public String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType) {
		this.bigType = bigType;
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
	
	@Column(name="courseName")
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	
	@Column(name="userClass")
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	
	@Column(name="uploadId")
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}
	
	@Column(name="userCount")
	public String getUserCount() {
		return userCount;
	}
	public void setUserCount(String userCount) {
		this.userCount = userCount;
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
