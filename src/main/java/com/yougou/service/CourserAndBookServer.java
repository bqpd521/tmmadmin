package com.yougou.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.BookClassModel;
import com.yougou.model.BookDestroyModel;
import com.yougou.model.BookSendModel;
import com.yougou.model.BookUploadModel;
import com.yougou.model.BooksInfoModel;
import com.yougou.model.CourseInfoModel;
import com.yougou.model.UploadbooksModel;

/**
 * 用途说明：教材与课程操作 server
 * @author yinyunfei 2015-01-01
 */
@Transactional
public interface CourserAndBookServer {

	public Map<String, Object> addOrEditCourse(HttpServletRequest request,HttpServletResponse response);
	public PageBean<CourseInfoModel>  queryCoureInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	public boolean  deleteCourse(HttpServletRequest request,HttpServletResponse response);
	public CourseInfoModel getCourseInfoModelById(HttpServletRequest request,HttpServletResponse response);
	public List<CourseInfoModel> getCourseInfoList();
	public List<CourseInfoModel> getCourseInfoList2();
	
	public Map<String, Object> addOrEditBook(HttpServletRequest request,HttpServletResponse response);
	public Map<String, Object> huishouAdd(HttpServletRequest request,HttpServletResponse response);
	public Map<String, Object> addOrEditBook2(HttpServletRequest request,HttpServletResponse response);
	public PageBean<BooksInfoModel>  queryBookInfoPageBean5(HttpServletRequest request,HttpServletResponse response);
	public PageBean<BooksInfoModel>  queryBookInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	public PageBean<BookClassModel>  queryNowSendInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	public PageBean<BookClassModel>  queryHiSendInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	public PageBean<BooksInfoModel>  queryBookHuishouInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	public PageBean<BookSendModel>  queryBookSendInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	public boolean  deleteBook(HttpServletRequest request,HttpServletResponse response);
	public BooksInfoModel getBooksInfoModelById(HttpServletRequest request,HttpServletResponse response);
	
	public Map<String, Object> addOrEditSend(HttpServletRequest request,HttpServletResponse response) ;
	public List<BookDestroyModel> getBookDestroyList(HttpServletRequest request,HttpServletResponse response);
	
	public PageBean<BookUploadModel>  queryUploadInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	
	public boolean uploadImage(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file", required=false) MultipartFile file);
	public boolean  uploadState(HttpServletRequest request,HttpServletResponse response);
	public PageBean<UploadbooksModel>  queryBzjhInfoPageBean(HttpServletRequest request,HttpServletResponse response);
	public PageBean<UploadbooksModel>  queryBzjhInfoPageBean2(HttpServletRequest request,HttpServletResponse response);
	public PageBean<UploadbooksModel>  queryUseListPageBean(HttpServletRequest request,HttpServletResponse response);
	public Map<String, Object> updateOneSendBook(HttpServletRequest request,HttpServletResponse response);
	public Map<String, Object> updateAllSendBook(HttpServletRequest request,HttpServletResponse response);
	public Map<String, Object> updateAllSendBook2(HttpServletRequest request,HttpServletResponse response);
	public boolean getBookClass(HttpServletRequest request,HttpServletResponse response);
	public boolean getBookClassByIds(HttpServletRequest request,HttpServletResponse response);
	
	public boolean  exportExcelFfd(HttpServletRequest request,HttpServletResponse response);
	public boolean  exportExcelBooList(HttpServletRequest request,HttpServletResponse response);
	public boolean  exportExcelUseList(HttpServletRequest request,HttpServletResponse response);
	public boolean  exportExcelBzjh(HttpServletRequest request,HttpServletResponse response);
	
	public boolean  deleteUploadList(HttpServletRequest request,HttpServletResponse response);
	public boolean  addUseClass(HttpServletRequest request,HttpServletResponse response);
	
	public UploadbooksModel getUploadbooksModelById(HttpServletRequest request,HttpServletResponse response);
}
