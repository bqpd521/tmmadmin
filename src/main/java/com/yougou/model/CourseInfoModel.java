package com.yougou.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用途说明：课程信息实体
 * @author yinyunfei
 *
 */
@Entity
@Table(name = "tbl_course_info")
public class CourseInfoModel  implements Serializable{

	private static final long serialVersionUID = 3080390769232014058L;
	//自增主键
	private int id;
	//课程名称
	private String courseName;
	//课程代码
	private String courseCode;
	//课程状态  1：可用；0：不可用
	private String courseState;
	//添加时间
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
	
	@Column(name = "courseName")
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	@Column(name = "courseCode")
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	@Column(name = "courseState")
	public String getCourseState() {
		return courseState;
	}
	public void setCourseState(String courseState) {
		this.courseState = courseState;
	}
	
	@Column(name = "insertTime")
	public String getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(String insertTime) {
		this.insertTime = insertTime;
	}
}
