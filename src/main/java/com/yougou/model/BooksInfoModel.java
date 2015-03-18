package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用途说明：教材信息实体
 * @author yinyunfei 2015-01-01
 */
@Entity
@Table(name = "tbl_books_info")
public class BooksInfoModel implements Serializable{

	private static final long serialVersionUID = -6720864113119754202L;
	//自增主键
	private int id;
	//教材名称
	private String bookName;
	//课程ID
	private String courseId;
	//出版社
	private String bookPress;
	//作者
	private String bookAuthor;
	//出版时间
	private String pressTime;
	//大类 1： 外购、2：印刷、3：配发、4：调拨 ，5其它
	private String bigType;
	//小类： 0：无分类；1：地方采购；2 军内采购
	private String smallType;
	//添加时间
	private String insertTime;	
	//修改时间
	private String updateTime;
	//状态：1可用。0不可用。
	private String bookState;
	//教材分类：1：书；2地图
	private String bookType;
	//是否保密：1保密；0不保密。
	private String baoMi;
	//是否回收：1：回收；0不回收
	private String huiShou;
	
	//库号
	private String kuHao;
	//架号
	private String jiaHao;
	//层号
	private String chengHao;
	//库存数量
	private String kcCount;
	//销毁数量                               默认值0 
	private String destroyCount;
	private String bookCode;
	//缺库数
	private String  queCount;
	
	@Column(name = "queCount")
	public String getQueCount() {
		return queCount;
	}
	public void setQueCount(String queCount) {
		this.queCount = queCount;
	}
	
	@Column(name = "bookCode")
	public String getBookCode() {
		return bookCode;
	}
	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}
	
	@Column(name = "destroyCount")
	public String getDestroyCount() {
		return destroyCount;
	}
	public void setDestroyCount(String destroyCount) {
		this.destroyCount = destroyCount;
	}
	
	@Column(name = "kcCount")
	public String getKcCount() {
		return kcCount;
	}
	public void setKcCount(String kcCount) {
		this.kcCount = kcCount;
	}
	
	@Column(name = "baoMi")
	public String getBaoMi() {
		return baoMi;
	}
	public void setBaoMi(String baoMi) {
		this.baoMi = baoMi;
	}
	
	@Column(name = "huiShou")
	public String getHuiShou() {
		return huiShou;
	}
	public void setHuiShou(String huiShou) {
		this.huiShou = huiShou;
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
	
	@Column(name = "bookName")
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	@Column(name = "courseId")
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	@Column(name = "bookPress")
	public String getBookPress() {
		return bookPress;
	}
	public void setBookPress(String bookPress) {
		this.bookPress = bookPress;
	}
	
	@Column(name = "bookAuthor")
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
	@Column(name = "pressTime")
	public String getPressTime() {
		return pressTime;
	}
	public void setPressTime(String pressTime) {
		this.pressTime = pressTime;
	}
	
	@Column(name = "bigType")
	public String getBigType() {
		return bigType;
	}
	public void setBigType(String bigType) {
		this.bigType = bigType;
	}
	
	@Column(name = "smallType")
	public String getSmallType() {
		return smallType;
	}
	public void setSmallType(String smallType) {
		this.smallType = smallType;
	}
	
	@Column(name = "insertTime")
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
	
	@Column(name = "updateTime")
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name = "bookState")
	public String getBookState() {
		return bookState;
	}
	public void setBookState(String bookState) {
		this.bookState = bookState;
	}
	
	@Column(name = "bookType")
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	
	@Column(name = "kuHao")
	public String getKuHao() {
		return kuHao;
	}
	public void setKuHao(String kuHao) {
		this.kuHao = kuHao;
	}
	
	@Column(name = "jiaHao")
	public String getJiaHao() {
		return jiaHao;
	}
	public void setJiaHao(String jiaHao) {
		this.jiaHao = jiaHao;
	}
	
	@Column(name = "chengHao")
	public String getChengHao() {
		return chengHao;
	}
	public void setChengHao(String chengHao) {
		this.chengHao = chengHao;
	}
	
	//非表中字段  课程名称 2015-02-03
	private String courseName;
	@Transient
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
