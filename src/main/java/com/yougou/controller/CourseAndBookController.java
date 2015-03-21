package com.yougou.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.AdminUserModel;
import com.yougou.model.BookClassModel;
import com.yougou.model.BookDestroyModel;
import com.yougou.model.BookSendModel;
import com.yougou.model.BookUploadModel;
import com.yougou.model.BooksInfoModel;
import com.yougou.model.CourseInfoModel;
import com.yougou.model.UploadbooksModel;
import com.yougou.service.CourserAndBookServer;

/**
 * 功能说明：教材和书Controller
 * 创建时间：2014-12-31 Yinyunfei
 */
@Controller
public class CourseAndBookController {

	/**
	 * 方法说明：添加课程
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="course/addCourse")
	private Map<String, Object> addCourse(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.addOrEditCourse(request, response);
		return null;
	}
	
	/**
	 * 方法说明：删除课程
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "course/deleteCourse")  
	@ResponseBody 
	private Map<String, Object> deleteCourse(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.deleteCourse(request, response);
		return null;
	}
	
	/**
	 * 方法说明：分页查询课程信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "course/courseList")  
	@ResponseBody 
	private ModelAndView courseList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<CourseInfoModel> pb=courserAndBookServer.queryCoureInfoPageBean(request, response);
		request.setAttribute("list", pb);
		
		return new ModelAndView("course/courseList");
	}
	
	/**
	 * 方法说明：添加课程VIEW
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "course/addCourseView")  
	@ResponseBody 
	private ModelAndView addCourseView(HttpServletRequest request,HttpServletResponse response) {
		
		return new ModelAndView("course/addCourse");
	}
	
	/**
	 * 方法说明：编辑课程VIEW
	 * 创建时间：2015年01月01日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "course/editCourseView")  
	@ResponseBody 
	private ModelAndView editCourseView(HttpServletRequest request,HttpServletResponse response) {
		
		CourseInfoModel clCourseInfoModel=courserAndBookServer.getCourseInfoModelById(request, response);
		request.setAttribute("cr", clCourseInfoModel);
		return new ModelAndView("course/editCourse");
	}
	
	//============================
	
	/**
	 * 方法说明：添加Book
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="book/addBook")
	private Map<String, Object> addBook(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.addOrEditBook(request, response);
		return null;
	}
	
	/**
	 * 方法说明：添加回收数据
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="book/huishouAdd")
	private Map<String, Object> huishouAdd(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.huishouAdd(request, response);
		return null;
	}
	
	/**
	 * 方法说明：添加Book
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="book/addBook2")
	private Map<String, Object> addBook2(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.addOrEditBook2(request, response);
		return null;
	}
	
	/**
	 * 方法说明：删除Book
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/deleteBook")  
	@ResponseBody 
	private Map<String, Object> deleteBook(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.deleteBook(request, response);
		return null;
	}
	
	/**
	 * 方法说明：删除Book
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure//uploadState")  
	@ResponseBody 
	private Map<String, Object> uploadState(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.uploadState(request, response);
		return null;
	}
	
	/**
	 * 方法说明：分页查询上历史信息
	 * 创建时间：2015年1月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/uploadList")  
	@ResponseBody 
	private ModelAndView uploadList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BookUploadModel> pb=courserAndBookServer.queryUploadInfoPageBean(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("ensure/uploadList");
	}
	
	/**
	 * 方法说明：删除上传记录
	 * 创建时间：2015年2月2日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/uploadListDelete")  
	@ResponseBody 
	private Map<String, Object> uploadListDelete(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.deleteUploadList(request, response);
		return null;
	}
	
	
	
	/**
	 * 方法说明：添加使用班级
	 * 创建时间：2015-03-15  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/addUseClass")  
	@ResponseBody 
	private Map<String, Object> addUseClass(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.addUseClass(request, response);
		return null;
	}
	
	/**
	 * 方法说明：分页查询Book信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/bookList")  
	@ResponseBody 
	private ModelAndView bookList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BooksInfoModel> pb=courserAndBookServer.queryBookInfoPageBean(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("book/bookList");
	}
	
	/**
	 * 方法说明：分页查询Book信息
	 * 创建时间：2015年3月20 长春  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/bookList2")  
	@ResponseBody 
	private ModelAndView bookList2(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BooksInfoModel> pb=courserAndBookServer.queryBookInfoPageBean(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("book/bookList2");
	}
	
	
	/**
	 * 方法说明：保障计划列表
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/bzjhList")  
	@ResponseBody 
	private ModelAndView bzjhList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<UploadbooksModel> pb=courserAndBookServer.queryBzjhInfoPageBean(request, response);
		request.setAttribute("list2", pb);
		String bigType=request.getParameter("bigType3");
		
		//System.out.println("bzjhList:================:"+bigType);
		if(bigType==null||"".equals(bigType)||"null".equals(bigType)){
			
			bigType="";
		}
		//System.out.println("bzjhList:================2:"+bigType);
		request.setAttribute("bigType3", bigType);
		return new ModelAndView("ensure/bzjhList");
	}
	
	/**
	 * 方法说明：保障计划列表
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/useList")  
	@ResponseBody 
	private ModelAndView useList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<UploadbooksModel> pb=courserAndBookServer.queryUseListPageBean(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("ensure/useList");
	}
	
	/**
	 * 方法说明：添加使用班级  VIEW
	 * 创建时间：2015-03-14  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/addUseClassView")  
	@ResponseBody 
	private ModelAndView addUseClassView(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.getUploadbooksModelById(request, response);
		return new ModelAndView("ensure/addUseClass");
	}
	
	/**
	 * 方法说明：当前发放单
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/nowSendList")  
	@ResponseBody 
	private ModelAndView nowSendList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BookClassModel> pb=courserAndBookServer.queryNowSendInfoPageBean(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("ensure/nowSendList");
	}
	
	/**
	 * 方法说明：历史发放单
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/hiSendList")  
	@ResponseBody 
	private ModelAndView hiSendList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BookClassModel> pb=courserAndBookServer.queryHiSendInfoPageBean(request, response);
		request.setAttribute("list2", pb); 
		
		return new ModelAndView("ensure/hiSendList");
	}
	
	/**
	 * 方法说明：分页查询缺库Book信息
	 * 创建时间：2015年1月6日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/quebookList")  
	@ResponseBody 
	private ModelAndView quebookList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BooksInfoModel> pb=courserAndBookServer.queryBookInfoPageBean5(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("book/quebookList");
	}
	
	/**
	 * 方法说明：分页查询去向统计Book信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/sendList")  
	@ResponseBody 
	private ModelAndView sendList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BookSendModel> pb=courserAndBookServer.queryBookSendInfoPageBean(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("book/sendList");
	}
	
	/**
	 * 方法说明：分页查询Book[回收统计]信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/huishouList")  
	@ResponseBody 
	private ModelAndView huishouList(HttpServletRequest request,HttpServletResponse response) {
		
		PageBean<BooksInfoModel> pb=courserAndBookServer.queryBookHuishouInfoPageBean(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("book/huishouList");
	}
	
	/**
	 * 方法说明：添加Book VIEW
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/addBookView")  
	@ResponseBody 
	private ModelAndView addBookView(HttpServletRequest request,HttpServletResponse response) {
		
		List<CourseInfoModel> tempList=courserAndBookServer.getCourseInfoList();  //getCourseInfoList2
		request.setAttribute("cList", tempList);
		return new ModelAndView("book/addBook");
	}
	
	/**
	 * 方法说明：编辑Book VIEW
	 * 创建时间：2015年01月01日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/editBookView")  
	@ResponseBody 
	private ModelAndView editBookView(HttpServletRequest request,HttpServletResponse response) {
		
		List<CourseInfoModel> tempList=courserAndBookServer.getCourseInfoList();
		request.setAttribute("cList", tempList);
		
		BooksInfoModel bl =courserAndBookServer.getBooksInfoModelById(request, response);
		request.setAttribute("pId", bl.getCourseId());
		request.setAttribute("bModel", bl);
		
		return new ModelAndView("book/editBook");
	}
	
	/**
	 * 方法说明：调库Book VIEW
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/tiaoBookView")  
	@ResponseBody 
	private ModelAndView tiaoBookView(HttpServletRequest request,HttpServletResponse response) {
		
		BooksInfoModel bl =courserAndBookServer.getBooksInfoModelById(request, response);
		request.setAttribute("bModel", bl);
		
		String cfrom=request.getParameter("cfrom");
		
		if("1".equals(cfrom)){
			
			request.setAttribute("jsMenth", "navTabAjaxDoneBook2");
		} else if("2".equals(cfrom)){
			
			request.setAttribute("jsMenth", "navTabAjaxDoneBook3");
		}
		
		request.setAttribute("cfrom", cfrom);
		
		return new ModelAndView("book/tiaoBook");
	}
	
	/**
	 * 方法说明：	入库Book VIEW
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/ruBookView")  
	@ResponseBody 
	private ModelAndView ruBookView(HttpServletRequest request,HttpServletResponse response) {
		
		BooksInfoModel bl =courserAndBookServer.getBooksInfoModelById(request, response);
		request.setAttribute("bModel", bl);
		
		String cfrom=request.getParameter("cfrom");
		
		if("1".equals(cfrom)){
			
			request.setAttribute("jsMenth", "navTabAjaxDoneBook2");
		} else if("2".equals(cfrom)){
			
			request.setAttribute("jsMenth", "navTabAjaxDoneBook3");
		}
		
		request.setAttribute("cfrom", cfrom);
		
		return new ModelAndView("book/ruBook");
	}
	
	/**
	 * 方法说明：出库Book VIEW
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/chuBookView")  
	@ResponseBody 
	private ModelAndView chuBookView(HttpServletRequest request,HttpServletResponse response) {
		
		BooksInfoModel bl =courserAndBookServer.getBooksInfoModelById(request, response);
		request.setAttribute("bModel", bl);
		
		String cfrom=request.getParameter("cfrom");
		
		if("1".equals(cfrom)){
			
			request.setAttribute("jsMenth", "navTabAjaxDoneBook2");
		} else if("2".equals(cfrom)){
			
			request.setAttribute("jsMenth", "navTabAjaxDoneBook3");
		}
		
		request.setAttribute("cfrom", cfrom);
		
		return new ModelAndView("book/chuBook");
	}
	
	/**
	 * 方法说明：回收查询 VIEW
	 * 创建时间：2014年12月30日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/huishouBookView")  
	@ResponseBody 
	private ModelAndView huishouBookView(HttpServletRequest request,HttpServletResponse response) {
		
		BooksInfoModel bl =courserAndBookServer.getBooksInfoModelById(request, response);
		request.setAttribute("bModel", bl);
		
		return new ModelAndView("book/huishouBook");
	}
	
	/**
	 * 方法说明：销毁查询 VIEW
	 * 创建时间：2015年1月5日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/huishouSearchView")  
	@ResponseBody 
	private ModelAndView huishouSearchView(HttpServletRequest request,HttpServletResponse response) {
		
		List<BookDestroyModel> tempList = courserAndBookServer.getBookDestroyList(request, response);
		request.setAttribute("list3", tempList);
		
		return new ModelAndView("book/huishouSearch");
	}
	
	/**
	 * 方法说明：添加Book 出库信息
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="book/addBookSend")
	private Map<String, Object> addBookSend(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.addOrEditSend(request, response);
		return null;
	}
	
	/**
	 * 方法说明：导出发放单
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="ensure/exportExcelFfd")
	private Map<String, Object> exportExcelFfd(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.exportExcelFfd(request, response);
		return null;
	}
	
	/**
	 * 方法说明：导出-保障计划
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="ensure/exportExcelBzjh")
	private Map<String, Object> exportExcelBzjh(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.exportExcelBzjh(request, response);
		return null;
	}
	
	/**
	 * 方法说明：导出教材信息 booklist
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="book/exportExcelBooList")
	private Map<String, Object> exportExcelBooList(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.exportExcelBooList(request, response);
		return null;
	}
	
	/**
	 * 方法说明：导出教材信息 booklist
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="ensure/exportExcelUseList")
	private Map<String, Object> exportExcelUseList(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.exportExcelUseList(request, response);
		return null;
	}
	
	//==============================
	/**
	 * 方法说明：上传 excel列表
	 * 创建时间：2014年12月31日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/ensureList")  
	@ResponseBody 
	private ModelAndView ensureList(HttpServletRequest request,HttpServletResponse response) {
		
//		PageBean<BooksInfoModel> pb=courserAndBookServer.queryBookInfoPageBean(request, response);
		PageBean<UploadbooksModel> pb=courserAndBookServer.queryBzjhInfoPageBean2(request, response);
		request.setAttribute("list2", pb);
		
		return new ModelAndView("ensure/ensureList");
	}
	
	/**
	 * 方法说明：上传EXCEL
	 * 创建时间：2015-01-06  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "book/uploadExcel",method=RequestMethod.POST)  
	@ResponseBody 
	private Map<String, Object> uploadExcel(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file", required=false) MultipartFile file) {
		
		//courserAndBookServer.deleteBook(request, response);
		courserAndBookServer.uploadImage(request, response, file);
		return null;
	}
	
	/**
	 * 方法说明：单个发放 
	 * 创建时间：2015年1月15日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/sendOneView")  
	@ResponseBody 
	private ModelAndView sendOneView(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.getBookClass(request, response);
		return new ModelAndView("ensure/sendOne");
	}
	
	/**
	 * 方法说明：单个发放 
	 * 创建时间：2015年1月15日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/sendOneViewHi")  
	@ResponseBody 
	private ModelAndView sendOneViewHi(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.getBookClass(request, response);
		return new ModelAndView("ensure/sendOneHi");
	}
	
	/**
	 * 方法说明：多个发放 
	 * 创建时间：2015年1月15日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/sendAllView")  
	@ResponseBody 
	private ModelAndView sendAllView(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.getBookClassByIds(request, response);
		return new ModelAndView("ensure/sendAll");
	}
	
	/**
	 * 方法说明：减库存
	 * 创建时间：2015年1月15日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/sendAllView2")  
	@ResponseBody 
	private ModelAndView sendAllView2(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.getBookClassByIds(request, response);
		return new ModelAndView("ensure/sendAll2");
	}
	
	/**
	 * 方法说明：多个发放 
	 * 创建时间：2015年1月15日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "ensure/sendAllViewHi")  
	@ResponseBody 
	private ModelAndView sendAllViewHi(HttpServletRequest request,HttpServletResponse response) {
		
		courserAndBookServer.getBookClassByIds(request, response);
		return new ModelAndView("ensure/sendAllHi");
	}
	
	/**
	 * 方法说明：单个更新发放单
	 * 创建时间：2015-01-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="ensure/updateOneSendBook")
	private Map<String, Object> updateOneSendBook(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.updateOneSendBook(request, response);
		return null;
	}
	
	/**
	 * 方法说明：单个更新发放单
	 * 创建时间：2015-01-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="ensure/updateAllSendBook")
	private Map<String, Object> updateAllSendBook(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.updateAllSendBook(request, response);
		return null;
	}
	
	/**
	 * 方法说明：更改状态 并减库存
	 * 创建时间：2015-01-24 Yinyunfei   长春
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="ensure/updateAllSendBook2")
	private Map<String, Object> updateAllSendBook2(HttpServletRequest request,HttpServletResponse response) {
	
		courserAndBookServer.updateAllSendBook2(request, response);
		return null;
	}
	
	@Autowired
	private CourserAndBookServer courserAndBookServer;
}
