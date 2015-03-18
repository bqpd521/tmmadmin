package com.yougou.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.Position.Bias;

import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import redis.clients.jedis.Jedis;

import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sun.xml.internal.ws.message.stream.PayloadStreamReaderMessage;
import com.yougou.common.CommonParms;
import com.yougou.common.DateUtils;
import com.yougou.common.Logs;
import com.yougou.common.RedisClient2;
import com.yougou.common.StrUtils;
import com.yougou.common.WebUtils;
import com.yougou.dao.BookClassDao;
import com.yougou.dao.BookDestroyDao;
import com.yougou.dao.BookSendDao;
import com.yougou.dao.BookUploadDao;
import com.yougou.dao.BooksInfoDao;
import com.yougou.dao.CourseInfoDao;
import com.yougou.dao.UploadbooksDao;
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
 * 用途说明：教材与课程操作 server
 * @author yinyunfei 2015-01-01
 */
public class CourserAndBookServerImpl implements CourserAndBookServer {
	
	/**
	 * 方法说明：添加或编辑课程
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 */
	public Map<String, Object> addOrEditCourse(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("result", "-1");
			
			String insertTime=DateUtils.getSystemDateAndTime();
			String courseCode=request.getParameter("courseCode");
			String courseName=request.getParameter("courseName");
			String courseState=request.getParameter("courseState");
			String id=request.getParameter("courseId");
			String addOrEdit="1"; //1:add; 0：edit;
			courseName =courseName.replace(" ", "");
			
			CourseInfoModel cl =new CourseInfoModel();
			cl.setCourseCode(courseCode);
			cl.setCourseName(courseName);
			cl.setCourseState(courseState);
            cl.setInsertTime(insertTime);
			
			if(id!=null&&!"".equals(id)){
				
				cl.setId(Integer.parseInt(id));
				addOrEdit="0";
			}else{
				
				//判断课程名称是否存在：
				List<CourseInfoModel> tempList = courseInfoDao.getCourseInfo(courseName);
				
				if(tempList!=null&&tempList.size()>0){
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"400\",\"message\":\"课程名称重复，请确认。\",\"navTabId\":\"courseList\",\"rel\":\"courseList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\"}");
					return modelMap;
				}
			}
			
			CourseInfoModel re2=courseInfoDao.addCourseInfo(cl);
			
			if(re2!=null){
			
				//\"rel\":\"userList2\" \"navTabId\":\"userList2\",
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"courseList\",\"rel\":\"courseList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\"}");
			}
			
			return modelMap;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->addOrEditCourse():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页获取课程信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<CourseInfoModel>  queryCoureInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String courseName= request.getParameter("courseName");
			
            int iPageSize=15;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<CourseInfoModel> pageBean =new PageBean<CourseInfoModel>(); 
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<CourseInfoModel> pb = courseInfoDao.queryCourseInfoPageBean(pageBean,courseName);
			//向Redis缓存中添加数据
			this.addCourseInfoToRedis();
			
			if(pb!=null&&pb.getResult().size()>0){
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryCoureInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：删除课程
	 * 创建时间：2014年12月31日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteCourse(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String courseIds=request.getParameter("courseId");
			
			if(courseIds!=null&&!"".equals(courseIds)){
				
				String str [] =courseIds.split(",");
				int iLength=str.length;
				boolean bRet =false;
				
				for(int i=0;i<iLength;i++){
					
					String courseId=str[i];
					courseId=courseId.replace("=", "");
					courseId=courseId.replace("'", "");
					bRet=courseInfoDao.deleteCourse(courseId);
				}
				
				if(bRet){
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"删除成功\",\"navTabId\":\"courseList\",\"rel\":\"courseList\"}");
				}else{
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"删除失败\",\"navTabId\":\"courseList\",\"rel\":\"courseList\"}");
				}
			}
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->deleteUser():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：删除上传记录
	 * 创建时间：2015年2月2日 下午2:27:03  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteUploadList(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String uploadIds=request.getParameter("uploadIds");
			
			if(uploadIds!=null&&!"".equals(uploadIds)){
				
				String str [] =uploadIds.split(",");
				int iLength=str.length;
				boolean bRet =false;
				
				for(int i=0;i<iLength;i++){
					
					String uploadId=str[i];
					uploadId=uploadId.replace("=", "");
					uploadId=uploadId.replace("'", "");
					boolean bRet2 = this.bookClassDao.deleteByUploadId(uploadId);
					
					if(bRet2){
						
						boolean bRet3 = this.bookUploadDao.deleteByUploadId(uploadId);
						
						if(bRet3){
							
							boolean bRet4 = this.uploadbooksDao.deleteByUploadId(uploadId);
							
							if(bRet4){
								
								bRet=true;
							}
						}
					}
				}
				
				if(bRet){
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"删除成功\",\"navTabId\":\"uploadList\",\"rel\":\"uploadList\"}");
				}else{
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"删除失败\",\"navTabId\":\"uploadList\",\"rel\":\"uploadList\"}");
				}
			}
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->deleteUser():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：通过ID获取对象
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 * @throws Exception
	 */
	public CourseInfoModel getCourseInfoModelById(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			String courseId=request.getParameter("courseId");
            int id= 0;
            if(courseId!=null&&!"".equals(courseId)){
            	
            	id= Integer.parseInt(courseId);
            }
			
            CourseInfoModel al=courseInfoDao.getCourseInfoModelById(id);
			
			return al;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getCourseInfoModelById():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：获取课程信息 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public List<CourseInfoModel> getCourseInfoList(){
		
		try {
			
			Map<String,CourseInfoModel> tempMap=this.getCourseInfoMapFromRedis();
			
			if(tempMap!=null&&tempMap.size()>0){
				
				List<CourseInfoModel> tempList=new ArrayList<CourseInfoModel>();
				for (String mapKey:tempMap.keySet()) {
					
					tempList.add(tempMap.get(mapKey));
				}
				System.out.println("从缓存中获取课程信息============= getCourseInfoList");
				return tempList;
			}
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getCourseInfoList():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：获取课程信息 list   ---按昵称排序
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public List<CourseInfoModel> getCourseInfoList2(){
		
		try {
			
			List<CourseInfoModel> tempList= this.courseInfoDao.getCourseInfoList();
			return tempList;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getCourseInfoList():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
//==========================
	
	/**
	 * 方法说明：添加或编辑课程
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 */
	public Map<String, Object> addOrEditBook(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("result", "-1");
			
			String insertTime=DateUtils.getSystemDateAndTime();
			String bigType=request.getParameter("bigType");
			String bookAuthor=request.getParameter("bookAuthor");
			String bookName=request.getParameter("bookName");
			String bookPress=request.getParameter("bookPress");
			String bookState=request.getParameter("bookState");
			String bookType=request.getParameter("bookType");
			String courseId=request.getParameter("courseId");
			String pressTime=request.getParameter("pressTime");
			String smallType=request.getParameter("smallType");
			String baoMi=request.getParameter("baoMi");
			String huiShou=request.getParameter("huiShou");
			String kuHao=request.getParameter("kuHao");
			String jiaHao=request.getParameter("jiaHao");
			String chengHao=request.getParameter("chengHao");
			String kcCount=request.getParameter("kcCount");
			String destroyCount=request.getParameter("destroyCount");
			String id=request.getParameter("bookId");
			String bookCode =request.getParameter("bookCode");
			String addOrEdit="1"; //1:add; 0：edit;
			bookName =bookName.replace(" ", "");
			
			BooksInfoModel bi =new BooksInfoModel();
			bi.setBigType(bigType);
			bi.setBookAuthor(bookAuthor);
			bi.setBookName(bookName.trim());
			bi.setBookPress(bookPress);
			bi.setBookState(bookState);
			bi.setBookType(bookType);
			bi.setCourseId(courseId);
			bi.setInsertTime(insertTime);
			bi.setPressTime(pressTime);
			bi.setSmallType(smallType);
			bi.setBaoMi(baoMi);
			bi.setHuiShou(huiShou);
			bi.setKuHao(kuHao);
			bi.setJiaHao(jiaHao);
			bi.setChengHao(chengHao);
			bi.setKcCount(kcCount);
			bi.setDestroyCount(destroyCount);
			bi.setBookCode(bookCode);
			
			if(id!=null&&!"".equals(id)){
				
				bi.setId(Integer.parseInt(id));
				
				addOrEdit="0";
			}else{
				
				//判断教材名称是否重复
				Map<String,BooksInfoModel> tempBookMap = this.getBooksInfoMapFromRedis();
				if(tempBookMap!=null&&tempBookMap.size()>0){
					
					BooksInfoModel blRedis = tempBookMap.get(bookName);
					if(blRedis!=null){
						
						WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"400\",\"message\":\"教材名称重复，请确认！\",\"navTabId\":\"bookList\",\"rel\":\"bookList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\"}");
						return modelMap;
					}
				}
			}
			
			BooksInfoModel re2=booksInfoDao.addBooksInfo(bi);
			
			if(re2!=null){
			
				//添加信息到缓存
				addBookInfoToRedis();
				//\"rel\":\"userList2\" \"navTabId\":\"userList2\",
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"bookList\",\"rel\":\"bookList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\"}");
			}
			
			return modelMap;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->addOrEditBook():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：添加或编辑课程
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 */
	public Map<String, Object> huishouAdd(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("result", "-1");
			
			String insertTime=DateUtils.getSystemDateAndTime();
			String bookName=request.getParameter("bookName");
			String destroyCount=request.getParameter("destroyCount");
			String id=request.getParameter("bookId");
			String addOrEdit="xiaohui"; //1:add; 0：edit;
			String cfrom=request.getParameter("cfrom");
			
			BooksInfoModel bi =booksInfoDao.getBooksInfoModelById(Integer.parseInt(id));
			String destroyCount_db=bi.getDestroyCount();
			int iDestroyCount_db=0;
			int iDestroyCount=0;
			
			if(destroyCount_db!=null&&!"".equals(destroyCount_db)){
				
				iDestroyCount_db=Integer.parseInt(destroyCount_db);
			}
			
			if(destroyCount!=null&&!"".equals(destroyCount)){
				
				iDestroyCount=Integer.parseInt(destroyCount);
				
				if(iDestroyCount<=0){
					addOrEdit="-6";
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"对不起，销毁数量请大于0\",\"navTabId\":\"huishouList\",\"rel\":\"huishouList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\",\"cfrom\":\""+cfrom+"\"}");
					return null;
				}
			}
			
			bi.setDestroyCount((iDestroyCount_db+iDestroyCount)+"");
			
			BooksInfoModel re2=booksInfoDao.addBooksInfo(bi);
			
			if(re2!=null){
				
				BookDestroyModel bm=new BookDestroyModel();
				
				bm.setBookId(id);
				bm.setBookName(bookName);
				bm.setDestroyCount(destroyCount);
				bm.setInsertTime(insertTime);
				String userAccount=this.getLoginUserModel(request);
				bm.setUserAccount(userAccount);
				
				//添加销毁记录
				bookDestroyDao.addBookDestroyInfo(bm);
				
				//\"rel\":\"userList2\" \"navTabId\":\"userList2\",
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"huishouList\",\"rel\":\"huishouList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\",\"cfrom\":\""+cfrom+"\"}");
			  //WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"bookList\",\"rel\":\"bookList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\"}");
			}
			
			return modelMap;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->huishouAdd():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	private String getLoginUserModel(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			
			session = request.getSession();
		}
		
		AdminUserModel userModel=(AdminUserModel)session.getAttribute(CommonParms.USER_SESSION_KEY);
		
		if(userModel!=null){//跳转到登录页面
			
        	return userModel.getUserAccount();
		}
		
		return null;
	}
	
	private AdminUserModel getLoginUserModel2(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			
			session = request.getSession();
		}
		
		AdminUserModel userModel=(AdminUserModel)session.getAttribute(CommonParms.USER_SESSION_KEY);
		
		if(userModel!=null){//跳转到登录页面
			
        	return userModel;
		}
		
		return null;
	}
	
	/**
	 * 方法说明：添加或编辑课程
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 */
	public Map<String, Object> addOrEditBook2(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("result", "-1");
			
			String insertTime=DateUtils.getSystemDateAndTime();
			String sendFlag=request.getParameter("sendFlag");
			String id=request.getParameter("bookId");
			String kuHao=request.getParameter("kuHao");
			String jiaHao=request.getParameter("jiaHao");
			String chengHao=request.getParameter("chengHao");
			String kcCount=request.getParameter("kcCount");
			String cfrom=request.getParameter("cfrom");
			String addOrEdit="1"; //1:add; 0：edit; 4:ru; 5:tiao;
			
			BooksInfoModel bi =booksInfoDao.getBooksInfoModelById(Integer.parseInt(id));
			
			if(bi==null){
				
				return null;
			}
			
			if("ru".equals(sendFlag)){
				
				bi.setKcCount(kcCount);
				addOrEdit="4";
				//添加日志
			}else if("tiao".equals(sendFlag)){
				
				bi.setKuHao(kuHao);
				bi.setJiaHao(jiaHao);
				bi.setChengHao(chengHao);
				addOrEdit="5";
				//添加日志
			}
			
			BooksInfoModel re2=booksInfoDao.addBooksInfo(bi);
			
			if(re2!=null){
				//添加信息到缓存
				addBookInfoToRedis();
				//\"rel\":\"userList2\" \"navTabId\":\"userList2\",
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"bookList\",\"rel\":\"bookList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\",\"cfrom\":\""+cfrom+"\"}");
			}
			
			return modelMap;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->addOrEditCourse():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页book信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BooksInfoModel>  queryBookInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String bookAuthor=request.getParameter("bookAuthor");
			String bigType=request.getParameter("bigType");
			String bookState=request.getParameter("bookState");
			
            int iPageSize=15;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<BooksInfoModel> pageBean =new PageBean<BooksInfoModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<BooksInfoModel> pb = booksInfoDao.queryBooksInfoPageBean(pageBean, bookName, bookAuthor, bigType, bookState,"1");
			
			if(pb!=null&&pb.getResult().size()>0){
				
				//把课程ID改为课程名称
				PageBean<BooksInfoModel> newPb=pb;
				newPb=this.getCourseIdChangeName(newPb);
				request.setAttribute("bookName", bookName);
				request.setAttribute("bookAuthor", bookAuthor);
				request.setAttribute("bigType", bigType);
				return newPb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryBookInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	
	/**
	 * 方法说明：分页book信息   --缺库列表
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BooksInfoModel>  queryBookInfoPageBean5(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String bookAuthor=request.getParameter("bookAuthor");
			String bigType=request.getParameter("bigType");
			String bookState=request.getParameter("bookState");
			
            int iPageSize=15;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<BooksInfoModel> pageBean =new PageBean<BooksInfoModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<BooksInfoModel> pb = booksInfoDao.queryBooksInfoPageBean(pageBean, bookName, bookAuthor, bigType, bookState,"2");
			//pb=this.getCourseIdChangeName(pb);
			
			
			if(pb!=null&&pb.getResult().size()>0){
				Map<String,Integer> tempMap = this.userBookCountMap();
				List<BooksInfoModel> tempList =pb.getResult();
				
				if(tempList!=null&&tempList.size()>0){
					
					List<BooksInfoModel> newList =new ArrayList<BooksInfoModel>();
					for(BooksInfoModel bm: tempList){
						
						String kcCount =bm.getKcCount();
						int iKcCount=0;
						
						if(kcCount!=null&&!"".equals(kcCount)){
							
							iKcCount=Integer.parseInt(kcCount);
						}
						
						String bookName2=bm.getBookName();
						if(tempMap!=null&&tempMap.size()>0&&tempMap.get(bookName2)!=null){
							
							int iUserCount= tempMap.get(bookName2);
							if(iUserCount>=iKcCount){
								System.out.println(bookName2+"==========="+(iUserCount-iKcCount));
								bm.setQueCount((iUserCount-iKcCount)+"");
								newList.add(bm);
							}else {
								bm.setQueCount("-1");
								newList.add(bm);
							}
						}else{
							bm.setQueCount("-1");
							newList.add(bm);
						}
					}
					
					if(newList!=null&&newList.size()>0){
						
						pb.setResult(newList);
					}
				}
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryBookInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明： 获取使用计划中的用量
	 * 创建时间： 2015-01-18 yyf
	 * @return
	 */
	private Map<String,Integer> userBookCountMap(){
		
		try {
			List<UploadbooksModel> tempList = this.uploadbooksDao.getUploadBooksListCount("0");
			if(tempList!=null&&tempList.size()>0){
				
				Map<String,Integer> tempMap = new HashMap<String, Integer>();
				for(UploadbooksModel ul:tempList){
					
					String bookName = ul.getBookName();
					if(tempMap.get(bookName)==null){
						
						tempMap.put(bookName, Integer.parseInt(ul.getUserCount()));
					}else{
						
						tempMap.put(bookName, tempMap.get(bookName)+Integer.parseInt(ul.getUserCount()));
					}
				}
				
				return tempMap;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页当前发放单
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookClassModel>  queryNowSendInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String uploadId=request.getParameter("uploadId");
			String className=request.getParameter("className");
			String bookState=request.getParameter("bookState");
			
            int iPageSize=15;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
            if(uploadId==null||"".equals(uploadId)){
            	
            	uploadId=this.bookUploadDao.getUploadTableMaxId();
            }
            request.setAttribute("uploadId", uploadId);
			PageBean<BookClassModel> pageBean =new PageBean<BookClassModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<BookClassModel> pb =this.bookClassDao.querySendInfoPageBean(pageBean, bookName, uploadId, bookState, className);
			//pb=this.getCourseIdChangeName(pb);
			
			if(pb!=null&&pb.getResult().size()>0){
				
				List<BookClassModel> classList=this.bookClassDao.getUploadBooksListByUploadId(uploadId);
				request.setAttribute("classList", classList);
				if(className!=null&&!"".equals(className)){
					
					request.setAttribute("className1", className);
				}
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryBookInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页历史发放单信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookClassModel>  queryHiSendInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String uploadId=request.getParameter("uploadId");
			String className=request.getParameter("className2");
			String bookState=request.getParameter("bookState");
			
            int iPageSize=15;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
            if(uploadId==null||"".equals(uploadId)){
            	
            	uploadId=this.bookUploadDao.getUploadTableMaxId();
            }
            request.setAttribute("uploadId", uploadId);
			PageBean<BookClassModel> pageBean =new PageBean<BookClassModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<BookClassModel> pb =this.bookClassDao.queryHiSendInfoPageBean(pageBean, bookName, uploadId, bookState, className);
			//pb=this.getCourseIdChangeName(pb);
			
			if(pb!=null&&pb.getResult().size()>0){
				
				List<BookClassModel> classList=this.bookClassDao.getUploadBooksListByUploadId2(uploadId);
				request.setAttribute("classList", classList);
				if(className!=null&&!"".equals(className)){
					
					request.setAttribute("className1", className);
				}
				
				request.setAttribute("uploadId",uploadId);
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryHiSendInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页查询保障计划
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<UploadbooksModel>  queryBzjhInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String bigType=request.getParameter("bigType3");
			
			if(bigType==null||"".equals(bigType)||"null".equals(bigType)){
				
				bigType="";
			}
			
            int iPageSize=18;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<UploadbooksModel> pageBean =new PageBean<UploadbooksModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<UploadbooksModel> pb = this.uploadbooksDao.queryBzjhInfoPageBean(pageBean, bookName, bigType,"","0");
			//pb=this.getCourseIdChangeName(pb);
			//request.setAttribute("bigType", bigType);
			if(pb!=null&&pb.getResult().size()>0){
				
				Map<String,BooksInfoModel> tempMap = this.getBooksInfoMapFromRedis2();
				//生成 Excel
				//List<UploadbooksModel>uList= this.uploadbooksDao.getUploadbooksList(bigType,"0","0");
				//ExcelTreat.writeExcelForBzjh(CommonParms.UPLOAD_IMG_PATH+"/Bzjh-"+bigType+".xls", uList,tempMap);
				if(tempMap!=null&&tempMap.size()>0){
					
					List<UploadbooksModel> tempList = pb.getResult();
					List<UploadbooksModel> newList = new ArrayList<UploadbooksModel>();
					for(UploadbooksModel ul:tempList){
						
						String bookNameTemp= ul.getBookName();
						String bookIdTemp = ul.getBookId();
						BooksInfoModel bl=tempMap.get(bookIdTemp);
						String kcCountTemp = "0";
						int iKcCount=0;
						
						if(bl!=null){
							
							kcCountTemp = bl.getKcCount();
							ul.setBookPress(bl.getBookPress());
						}
						
						if(kcCountTemp!=null&&!"".equals(kcCountTemp)){
							
							iKcCount= Integer.parseInt(kcCountTemp);
						}else{
							
							kcCountTemp="0";
						}
						
						ul.setKcCount(kcCountTemp);
						String userCount=ul.getUserCount();
						int iUserCount=0;
						if(userCount!=null&&!"".equals(userCount)){
							
							iUserCount= Integer.parseInt(userCount);
						}
						
						int iQueCount =iUserCount-iKcCount;
						
						if(iQueCount<0){
							
							ul.setQueCount("充足");
						}else{
							
							ul.setQueCount(iQueCount+"");
						}
						
						newList.add(ul);
					}
					
					if(newList!=null&&newList.size()>0){
						
						pb.setResult(newList);
					}
				}
				
				request.setAttribute("bookName", bookName);
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryBzjhInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页查询保障计划   -在上传处用到的
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<UploadbooksModel>  queryBzjhInfoPageBean2(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String bigType=request.getParameter("bigType");
			String uploadId=request.getParameter("uploadId");
			
			if(bigType==null||"".equals(bigType)||"null".equals(bigType)){
				
				bigType="";
			}
			
            int iPageSize=18;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
            uploadId= this.bookUploadDao.getUploadTableMaxId();
            
			PageBean<UploadbooksModel> pageBean =new PageBean<UploadbooksModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<UploadbooksModel> pb = this.uploadbooksDao.queryBzjhInfoPageBean(pageBean, bookName, bigType,uploadId,"");

			if(pb!=null&&pb.getResult().size()>0){
				
				Map<String,BooksInfoModel> tempMap = this.getBooksInfoMapFromRedis2();
				if(tempMap!=null&&tempMap.size()>0){
					
					List<UploadbooksModel> tempList = pb.getResult();
					List<UploadbooksModel> newList = new ArrayList<UploadbooksModel>();
					for(UploadbooksModel ul:tempList){
						
						String bookNameTemp= ul.getBookName();
						String bookIdTemp= ul.getBookId();
						
						BooksInfoModel bl=tempMap.get(bookIdTemp);
						String kcCountTemp ="0";
						
						if(bl!=null){
							
							kcCountTemp = bl.getKcCount();
							ul.setBookPress(bl.getBookPress());
						}
						
						int iKcCount=0;
						
						if(kcCountTemp!=null&&!"".equals(kcCountTemp)){
							
							iKcCount= Integer.parseInt(kcCountTemp);
						}else{
							
							kcCountTemp="0";
						}
						
						ul.setKcCount(kcCountTemp);
						String userCount=ul.getUserCount();
						int iUserCount=0;
						if(userCount!=null&&!"".equals(userCount)){
							
							iUserCount= Integer.parseInt(userCount);
						}
						
						int iQueCount =iUserCount-iKcCount;
						
						if(iQueCount<0){
							
							iQueCount=0;
						}
						
						ul.setQueCount(iQueCount+"");
						
						newList.add(ul);
					}
					
					if(newList!=null&&newList.size()>0){
						
						pb.setResult(newList);
					}
				}
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryBzjhInfoPageBean2():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页查询保障计划   -在上传处用到的
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<UploadbooksModel>  queryUseListPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String bigType=request.getParameter("bigType");
			String uploadId=request.getParameter("uploadId");
			
			if(bigType==null||"".equals(bigType)||"null".equals(bigType)){
				
				bigType="";
			}
			
            int iPageSize=18;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
            uploadId= this.bookUploadDao.getUploadTableMaxId();
            
			PageBean<UploadbooksModel> pageBean =new PageBean<UploadbooksModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<UploadbooksModel> pb = this.uploadbooksDao.queryUseListPageBean(pageBean, bookName, bigType);

			if(pb!=null&&pb.getResult().size()>0){
				
				Map<String,BooksInfoModel> tempMap = this.getBooksInfoMapFromRedis2();
				if(tempMap!=null&&tempMap.size()>0){
					
					List<UploadbooksModel> tempList = pb.getResult();
					List<UploadbooksModel> newList = new ArrayList<UploadbooksModel>();
					for(UploadbooksModel ul:tempList){
						
						String bookNameTemp= ul.getBookName();
						String bookIdTemp= ul.getBookId();
						BooksInfoModel bl=tempMap.get(bookIdTemp);
						String kcCountTemp ="0";
						
						if(bl!=null){
							ul.setBookPress(bl.getBookPress());
							kcCountTemp=bl.getKcCount();
						}else{
							
							System.out.println("BooksInfoModel bl===null--"+bookNameTemp);
						}
						
						int iKcCount=0;
						
						if(kcCountTemp!=null&&!"".equals(kcCountTemp)){
							
							iKcCount= Integer.parseInt(kcCountTemp);
						}else{
							
							kcCountTemp="0";
						}
						
						ul.setKcCount(kcCountTemp);
						String userCount=ul.getUserCount();
						int iUserCount=0;
						if(userCount!=null&&!"".equals(userCount)){
							
							iUserCount= Integer.parseInt(userCount);
						}
						
						int iQueCount =iUserCount-iKcCount;
						
						if(iQueCount<0){
							
							iQueCount=0;
						}
						
						ul.setQueCount(iQueCount+"");
						
						newList.add(ul);
					}
					
					if(newList!=null&&newList.size()>0){
						
						pb.setResult(newList);
					}
				}
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryUseListPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：添加使用班级
	 * 创建时间：2015-3-15  Yin yunfei
	 */
	public boolean  addUseClass(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String sId = request.getParameter("id");
			String bookId = request.getParameter("bookId");
			String uploadId = request.getParameter("uploadId");
			String useClasss = request.getParameter("useClass");
			
			if(useClasss!=null&&!"".equals(useClasss)){
				
				int id=0;
				if(sId!=null&&!"".equals(sId)){
					
					id= Integer.parseInt(sId);
				}
				UploadbooksModel ul =uploadbooksDao.getUploadbooksModelById(id);
				String bookAuthor ="";
				String bookName ="";
				String bookPress ="";
				String courseName ="";
				String insertTime =DateUtils.getSystemDateAndTime();
				String pressTime ="";
				String sendState ="0";
				
				if(ul!=null){
					
					bookAuthor=ul.getBookAuthor();
					bookName=ul.getBookName();
					bookPress=ul.getBookPress();
					courseName=ul.getCourseName();
					pressTime=ul.getPressTime();
				}
				
				String classArgr[] = useClasss.split("、");
				int iLength = classArgr.length;
				boolean bRet =false;
				for(int i=0;i<iLength;i++){
					
					String useClass =classArgr[i];
					
					if(useClass==null || "".equals(useClass)|| "".equals(useClass.trim())){
						
						continue;
					}
					
					//第一步:判断是否存在记录，如果存在不更新；如果不存在添加
					BookClassModel bModel =this.bookClassDao.getBookClassModelById(uploadId, bookId, useClass);
					if(bModel==null){
						
						//添加记录
						BookClassModel bc2 =new BookClassModel();
						bc2.setBookAuthor(bookAuthor);
						bc2.setBookId(bookId);
						bc2.setBookName(bookName);
						bc2.setBookPress(bookPress);
						bc2.setClassName(useClass);
						bc2.setCourseName(courseName);
						bc2.setInsertTime(insertTime);
						bc2.setPressTime(pressTime);
						bc2.setSendState(sendState);
						bc2.setUploadId(uploadId);
						
						BookClassModel bl3=bookClassDao.addBookClass(bc2);
						
						if(bl3!=null){
							
							bRet=true;
						}
					}
				}
				
				//if(bRet){
					
					//更新 使用班级  表tbl_books_uploadbooks
					ul.setUserClass(useClasss);
					uploadbooksDao.addUploadbooks(ul);
				//}
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"编辑使用班级成功\",\"navTabId\":\"useList\",\"rel\":\"useList\"}");
			    return true;
			}
		} catch (Exception e) {
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->addUseClass():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		try {
			
			WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"编辑使用班级失败，请重试！\",\"navTabId\":\"useList\",\"rel\":\"useList\"}");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	/**
	 * 方法说明：通过ID获取UploadbooksModel对角
	 * 创建时间：2015-3-15  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public UploadbooksModel getUploadbooksModelById(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String sId= request.getParameter("id");
			int id=0;
			if(sId!=null&&!"".equals(sId)){
				
				id= Integer.parseInt(sId);
			}
			
			UploadbooksModel ul =uploadbooksDao.getUploadbooksModelById(id);
			String uploadId= "";
			String useClass = "";
			if(ul!=null){
				
				uploadId=ul.getUploadId();
				useClass = ul.getUserClass();
				
				if(useClass==null || "".equals(useClass.trim())){
					
					useClass="";
				}
			}
			
			request.setAttribute("id", sId);
			request.setAttribute("bookId", ul.getBookId());
			request.setAttribute("uploadId", uploadId);
			request.setAttribute("useClass", useClass);
		} catch (Exception e) {
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getUploadbooksModelById():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页book信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookUploadModel>  queryUploadInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String uploadDate=request.getParameter("uploadDate");
			
            int iPageSize=15;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<BookUploadModel> pageBean =new PageBean<BookUploadModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<BookUploadModel> pb = this.bookUploadDao.queryUploadInfoPageBean(pageBean, uploadDate);
			
			if(pb!=null&&pb.getResult().size()>0){
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryUploadInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	//把课程ID改为课程名称
	private PageBean<BooksInfoModel>  getCourseIdChangeName(PageBean<BooksInfoModel> pb) {
		
		if(pb!=null){
			
			List<BooksInfoModel> tempList=pb.getResult();
			if(tempList!=null&&tempList.size()>0){
				List<BooksInfoModel> tempList2=new ArrayList<BooksInfoModel>();
				Map<String,CourseInfoModel> tempMap=this.getCourseInfoMapFromRedis();
				for(BooksInfoModel bl:tempList){
					
					String courseId = bl.getCourseId();
					CourseInfoModel cl=tempMap.get(courseId);
					if(cl!=null){
						
						String courseName = cl.getCourseName();
						
						//bl.setCourseId(courseName);
						bl.setCourseName(courseName);
					}
					
					tempList2.add(bl);
				}
				
				pb.setResult(tempList2);
				
				return pb;
			}
		}
		
		return pb;
	}
	
	/**
	 * 方法说明：分页book【回收统计】信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BooksInfoModel>  queryBookHuishouInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			String bookAuthor=request.getParameter("bookAuthor");
			
            int iPageSize=10;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<BooksInfoModel> pageBean =new PageBean<BooksInfoModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<BooksInfoModel> pb = booksInfoDao.queryBooksInfoPageBean(pageBean, bookName, bookAuthor);
			
			if(pb!=null&&pb.getResult().size()>0){
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryBookInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	
	/**
	 * 方法说明：分页查询领取信息
	 * 创建时间：2014年12月31日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookSendModel>  queryBookSendInfoPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			String bookName=request.getParameter("bookName");
			
            int iPageSize=17;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<BookSendModel> pageBean =new PageBean<BookSendModel>();
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<BookSendModel> pb = bookSendDao.queryBookSendInfoPageBean(pageBean, bookName);
			
			if(pb!=null&&pb.getResult().size()>0){
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->queryBookSendInfoPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：删除课程
	 * 创建时间：2014年12月31日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteBook(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String bookIds=request.getParameter("bookIds");
			
			if(bookIds!=null&&!"".equals(bookIds)){
				
				String str [] =bookIds.split(",");
				int iLength=str.length;
				boolean bRet =false;
				
				for(int i=0;i<iLength;i++){
					
					String bookId = str[i];
					bookId=bookId.replace("=", "");
					bookId=bookId.replace("'", "");
					bRet=booksInfoDao.deleteBook(bookId);
				}
				
				if(bRet){
					//添加信息到缓存
					addBookInfoToRedis();
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"删除成功\",\"navTabId\":\"bookList\",\"rel\":\"bookList\"}");
				}else{
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"删除失败\",\"navTabId\":\"bookList\",\"rel\":\"bookList\"}");
				}
			}
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->deleteBook():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：更改上传记录中的状态
	 * 创建时间：2014年12月31日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public boolean  uploadState(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String id=request.getParameter("uploadId");
			
			if(id!=null&&!"".equals(id)){
				
				id=id.replace("=", "");
				id=id.replace("'", "");
				boolean bRet=this.bookUploadDao.uploadState(id);
				
				if(bRet){
					
					//修改两处：记录
					bookClassDao.updateClassState(id);
					uploadbooksDao.updateUbooksState(id);
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"更改成功\",\"navTabId\":\"uploadList\",\"rel\":\"uploadList\"}");
				}else{
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"更改失败，请重试！\",\"navTabId\":\"uploadList\",\"rel\":\"uploadList\"}");
				}
			}
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->uploadState():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：通过ID获取教材对象
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 * @throws Exception
	 */
	public BooksInfoModel getBooksInfoModelById(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			String bookId=request.getParameter("bookId");
            int id= 0;
            if(bookId!=null&&!"".equals(bookId)){
            	
            	id= Integer.parseInt(bookId);
            }
			
            BooksInfoModel al=booksInfoDao.getBooksInfoModelById(id);
			
			return al;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getBooksInfoModelById():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：获取销毁 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public List<BookDestroyModel> getBookDestroyList(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String bookId=request.getParameter("bookId");
			List<BookDestroyModel> tempList=bookDestroyDao.getBookDestroyModelByBookId(bookId);
			
			return tempList;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getBookDestroyList():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	//START============================BookSendDao===============================
	
	/**
	 * 方法说明：添加或编辑领用信息
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 */
	public Map<String, Object> addOrEditSend(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			String insertTime=DateUtils.getSystemDateAndTime();
			String bookId=request.getParameter("bookId");
			String bookName=request.getParameter("bookName");
			String sendCount=request.getParameter("sendCount");
			String sendType=request.getParameter("sendType");
			String sendUser=request.getParameter("sendUser");
			String id=request.getParameter("id");
			String cfrom=request.getParameter("cfrom");
			String sendFlag=request.getParameter("sendFlag");
			
			String userId=this.getLoginUserModel(request);//从登录sesseion中取;
			
			String addOrEdit="3"; //1:add; 0：edit;3:出库
			
			BookSendModel cl =new BookSendModel();
			
			cl.setBookId(bookId);
			cl.setBookName(bookName);
			cl.setInsertTime(insertTime);
			cl.setSendCount(sendCount);
			cl.setSendType(sendType);
			cl.setSendUser(sendUser);
			cl.setUserId(userId);
			
			BookSendModel re2=bookSendDao.addBookSendInfo(cl);
			
			if(re2!=null){
				
				//成功后减库存
				int iBookId= Integer.parseInt(bookId);
				BooksInfoModel al=booksInfoDao.getBooksInfoModelById(iBookId);
				
				String kcCount =al.getKcCount();
				
				if(kcCount!=null&&!"".equals(kcCount)){
					
					int iKcCount=Integer.parseInt(kcCount);
					int isendCount =Integer.parseInt(sendCount);
					
					int iAllCount=iKcCount-isendCount;
					al.setKcCount(iAllCount+"");
					
					booksInfoDao.addBooksInfo(al);
					//添加信息到缓存
					addBookInfoToRedis();
				}
			
				//\"rel\":\"userList2\" \"navTabId\":\"userList2\",
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"bookList\",\"rel\":\"bookList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\",\"cfrom\":\""+cfrom+"\"}");
			}
			
			return null;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->addOrEditCourse():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	//END =============================BookSendDao=====================
	
	
	//**************缓存数据***************
	
	/**
	 * 方法说明：获取教材信息从缓存中取
	 * 创建时间：2015-01-06 yinyunfei
	 * Map<教材名称,BooksInfoModel>
	 * @return
	 */
	private Map<String,BooksInfoModel> getBooksInfoMapFromRedis(){
		
		try {
			
			String sKey="bookInfoKey";
			Jedis js = this.redisClient.getConnection();
			String jsonString1=js.get(sKey);
			Map<String,BooksInfoModel> tempMap =(Map<String,BooksInfoModel>)JSON.parse(jsonString1);
			
			if(tempMap!=null&&tempMap.size()>0){
				System.out.println("getBooksInfoMapFromRedis===============freom Redis");
				return tempMap;
			}
			
			List<BooksInfoModel> tempList = this.booksInfoDao.getBooInfoList();
			if(tempList!=null&&tempList.size()>0){
				
				tempMap=new HashMap<String, BooksInfoModel>();
				for(BooksInfoModel cl:tempList){
					
					tempMap.put(cl.getBookName().trim(), cl);
				}
				
				SerializerFeature[] featureArr = { SerializerFeature.WriteClassName };  
				String jsonString2 = JSON.toJSONString(tempMap,featureArr);
				System.out.println("getBooksInfoMapFromRedis===============freom Database");
				js.set(sKey, jsonString2);
			}
		} catch (Exception e) {
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getBooksInfoMapFromRedis():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：获取教材信息从缓存中取
	 * 创建时间：2015-02-02 yinyunfei
	 * Map<教材表中的ID,BooksInfoModel>
	 * @return
	 */
	private Map<String,BooksInfoModel> getBooksInfoMapFromRedis2(){
		
		try {
			
			String sKey="bookInfoKey2";
			Jedis js = this.redisClient.getConnection();
			String jsonString1=js.get(sKey);
			Map<String,BooksInfoModel> tempMap =(Map<String,BooksInfoModel>)JSON.parse(jsonString1);
			
			if(tempMap!=null&&tempMap.size()>0){
				System.out.println("getBooksInfoMapFromRedis2===============freom Redis");
				return tempMap;
			}
			
			List<BooksInfoModel> tempList = this.booksInfoDao.getBooInfoList();
			if(tempList!=null&&tempList.size()>0){
				
				tempMap=new HashMap<String, BooksInfoModel>();
				for(BooksInfoModel cl:tempList){
					
					tempMap.put(cl.getId()+"", cl);
				}
				
				SerializerFeature[] featureArr = { SerializerFeature.WriteClassName };  
				String jsonString2 = JSON.toJSONString(tempMap,featureArr);
				System.out.println("getBooksInfoMapFromRedis2===============freom Database");
				js.set(sKey, jsonString2);
			}
		} catch (Exception e) {
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getBooksInfoMapFromRedis2():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：添加课程信息到缓存中取
	 * 创建时间：2015-01-06 yinyunfei
	 * @return
	 */
	private void addBookInfoToRedis(){
		
		try {
			
			List<BooksInfoModel> tempList = this.booksInfoDao.getBooInfoList();
			if(tempList!=null&&tempList.size()>0){
				
				//Map<教材名称,BooksInfoModel>
				Map<String,BooksInfoModel> tempMap=new HashMap<String, BooksInfoModel>();
				//Map<教材表中ID,BooksInfoModel>
				Map<String,BooksInfoModel> tempMap2=new HashMap<String, BooksInfoModel>();
				for(BooksInfoModel cl:tempList){
					
					tempMap.put(cl.getBookName().trim(), cl);
					tempMap2.put(cl.getId()+"", cl);
				}
				
				SerializerFeature[] featureArr = { SerializerFeature.WriteClassName };  
				String jsonString2 = JSON.toJSONString(tempMap,featureArr);
				
				String jsonString3 = JSON.toJSONString(tempMap2,featureArr);
				Jedis js = this.redisClient.getConnection();
				String sKey="bookInfoKey";
				js.set(sKey, jsonString2);
								
				String sKey2="bookInfoKey2";
				js.set(sKey2, jsonString3);
				
				System.out.println("addBookInfoToRedis===============add to Redis");
			}
		} catch (Exception e) {
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->addBookInfoToRedis():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
	}
	
	/**
	 * 方法说明：获取课程信息从缓存中取
	 * 创建时间：2015-01-06 yinyunfei
	 * @return
	 */
	private Map<String,CourseInfoModel> getCourseInfoMapFromRedis(){
		
		try {
			
			String sKey="courseinfoKey";
			Jedis js = this.redisClient.getConnection();
			String jsonString1=js.get(sKey);
			Map<String,CourseInfoModel> tempMap =(Map<String,CourseInfoModel>)JSON.parse(jsonString1);
			
			if(tempMap!=null&&tempMap.size()>0){
				System.out.println("getCourseInfoMapFromRedis===============freom Redis");
				return tempMap;
			}
			
			List<CourseInfoModel> tempList = courseInfoDao.getCourseInfoList("");
			if(tempList!=null&&tempList.size()>0){
				
				tempMap=new HashMap<String, CourseInfoModel>();
				for(CourseInfoModel cl:tempList){
					
					tempMap.put(cl.getId()+"", cl);
				}
				
				SerializerFeature[] featureArr = { SerializerFeature.WriteClassName };  
				String jsonString2 = JSON.toJSONString(tempMap,featureArr);
				System.out.println("getCourseInfoMapFromRedis===============freom Datebase");
				js.set(sKey, jsonString2);
			}
		} catch (Exception e) {
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->getCourseInfoMapFromRedis():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：获取课程信息从缓存中取
	 * 创建时间：2015-01-06 yinyunfei
	 * @return
	 */
	private void addCourseInfoToRedis(){
		
		try {
			
			List<CourseInfoModel> tempList = courseInfoDao.getCourseInfoList("");
			if(tempList!=null&&tempList.size()>0){
				
				Map<String,CourseInfoModel> tempMap=new HashMap<String, CourseInfoModel>();
				for(CourseInfoModel cl:tempList){
					
					tempMap.put(cl.getId()+"", cl);
				}
				
				SerializerFeature[] featureArr = { SerializerFeature.WriteClassName };  
				String jsonString2 = JSON.toJSONString(tempMap,featureArr);
				Jedis js = this.redisClient.getConnection();
				String sKey="courseinfoKey";
				js.set(sKey, jsonString2);
				System.out.println("addCourseInfoToRedis===============add to Redis");
			}
		} catch (Exception e) {
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->addCourseInfoToRedis():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
	}
	
	//**************缓存数据***************
	
	public boolean uploadImage(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="file", required=false) MultipartFile file){

		try {

			String path3 =this.getTomcatPath(request);
             File file3 =new File(path3); 
             if(!file3.exists()){
           	 
           	 	 file3.mkdir();
             }
             
			 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			 String fileName = null;
			 String newFileName="";
			 String allPath ="";
		     Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		     
		     for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
		    	 
		    	 MultipartFile mf = entity.getValue();    
	             fileName = mf.getOriginalFilename(); 
	             String suffix = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;

	             String otherName = DateUtils.getSystemDateAndTime2();
	             newFileName = otherName + (suffix!=null?suffix:"");// 构成新文件名。
	             allPath=path3 +"\\"+ newFileName;
		    	 File uploadFile = new File(allPath); 
		    	 FileCopyUtils.copy(mf.getBytes(), uploadFile);
		     }
		     
		     //第一步：读取Excel
		     List<UploadbooksModel> uploadList = ExcelTreat.bookUploadList(allPath);
		     if(uploadList!=null&&uploadList.size()>0){
		    	 
		    	 //第二步判断 教材是否在否中存在，如果有不存的退出进行提示：
		    	 Map<String,BooksInfoModel> tempMap = this.getBooksInfoMapFromRedis();
		    	 if(tempMap!=null&&tempMap.size()>0){
		    		 String bookNames="";
		    		 for (UploadbooksModel ul : uploadList) {
						
		    			 String bookName = ul.getBookName();
		    			 if(tempMap.get(bookName)==null){
		    				 
		    				 bookNames+=bookName+";";
		    			 }
					 }
		    		 
		    		 if(bookNames!=null&&!"".equals(bookNames)){
		    			 
		    			 //向前台输出提示，有哪些书在库存中没有，请先补充。
		    			 String outInfo="-1,"+bookNames;
		    			 WebUtils.getPrintWriter(response, "UTF-8").print(outInfo);
		    			 return false;
		    		 }
		    		 
		    		 //第三步：向三个表中插入数据
		    		 //3.1  tbl_books_uploads表中插入数据
		    		 BookUploadModel bm= new BookUploadModel();
		    		 
		    		 String insertTime=DateUtils.getSystemDateAndTime();
		    		 String uploadDate=DateUtils.getSystemDate();
		    		 String userAccount="";
		    		 String userName="";
		    		 
		    		 AdminUserModel al=this.getLoginUserModel2(request);
		    		 
		    		 if(al!=null){
		    			 
		    			 userAccount =al.getUserAccount();
		    			 userName=al.getUserName();
		    		 }
		    		 
		    		 bm.setExcelUrl("upload/"+newFileName);
		    		 bm.setInsertTime(insertTime);
		    		 bm.setUploadDate(uploadDate);
		    		 bm.setUploadState("0");
		    		 bm.setUserAccount(userAccount);
		    		 bm.setUserName(userName);
		    		 
		    		 BookUploadModel bm2=this.bookUploadDao.addBookUpload(bm);
		    		 
		    		 if(bm2!=null){
		    			 
		    			 String uploadId = bm2.getId()+"";
		    			 for(UploadbooksModel ul:uploadList){
		    				 
		    				 BooksInfoModel bl=tempMap.get(ul.getBookName());
		    				 ul.setUploadId(uploadId);
		    				 ul.setBigType(bl.getBigType());
		    				 ul.setKcCount(bl.getKcCount());
		    				 ul.setSendState("0");
		    				 ul.setBookId(bl.getId()+"");
		    				 ul.setInsertTime(insertTime);
		    				 ul.setBookPress(bl.getBookPress());
		    				 String isPrint="1";  //1：不需要打印，0：需要打印
		    				 
		    				 //Start--如果库存数小于当前需要的数据 设置 isPrint=0， 在保障计划中需要统计
		    				 int ikcCount=0;
		    				 String kcCount = bl.getKcCount();
		    				 if(kcCount!=null&&!"".equals(kcCount)){
		    					 
		    					 ikcCount=Integer.parseInt(kcCount);
		    				 }
		    				 
		    				 int iUserCount=0;
		    				 String userCount = ul.getUserCount();
		    				 
		    				 if(userCount!=null&&!"".equals(userCount)){
		    					 
		    					 iUserCount= Integer.parseInt(userCount);
		    				 }

		    				 //如果库存数 小于使用数，需要打印
		    				 if(iUserCount>ikcCount){
		    					 
		    					 isPrint="0";
		    				 }
		    				 
		    				 //End----
		    				 ul.setIsPrint(isPrint);
		    				 
		    				 this.uploadbooksDao.addUploadbooks(ul);
		    				 
		    				 String classNames=ul.getUserClass();
		    				 String classNameArray[]=classNames.split("、");
		    				 int iClassNamesLenght=classNameArray.length;
		    				 
		    				 for(int i=0;i<iClassNamesLenght;i++){
		    					 
		    					 try {
									
			    					 String tempClassName = classNameArray[i];
			    					 
			    					 if(tempClassName==null ||"".equals(tempClassName)){
			    						 
			    						 continue;
			    					 }
			    					 
			    					 //再做一次trim
			    					 if(tempClassName.trim()==null ||"".equals(tempClassName.trim())){
			    						 
			    						 continue;
			    					 }
			    					 
				    				 BookClassModel bc=new BookClassModel();
				    				 
				    				 bc.setBookAuthor(bl.getBookAuthor());
				    				 bc.setBookId(bl.getId()+"");
				    				 bc.setBookName(bl.getBookName());
				    				 bc.setBookPress(bl.getBookPress());
				    				 bc.setClassName(tempClassName);
				    				 bc.setCourseName(ul.getCourseName());
				    				 bc.setInsertTime(insertTime);
				    				 bc.setPressTime(bl.getPressTime());
				    				 bc.setSendCount("0");
				    				 bc.setSendState("0");
				    				 bc.setUploadId(uploadId);
				    				 bc.setUserCount(ul.getUserCount());
				    				 
				    				 this.bookClassDao.addBookClass(bc);
								} catch (Exception e) {
									e.printStackTrace();
								}
		    				 }
		    			 }
		    			 
				    	 //输出错误提示 ： Excel格式为空
		    			 String outInfo="1,数据处理成功！";
		    			 WebUtils.getPrintWriter(response, "UTF-8").print(outInfo);
				    	 return true;
		    		 }
		    	 }
		     }else{
		    	 
		    	 //输出错误提示 ： Excel格式为空
    			 String outInfo="0,Excel内容格式不对请确认";
    			 WebUtils.getPrintWriter(response, "UTF-8").print(outInfo);
		    	 return false;
		     }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 方法说明：单个发放  --发放单
	 * 创建时间：2015年1月15日 下午3:07:55  Yin yunfei
	 */
	public Map<String, Object> updateOneSendBook(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");//request.setCharacterEncode("GB2312");
			String id = request.getParameter("classId");
			String sType=request.getParameter("sType");
			
			if(id!=null&&!"".equals(id)){
				
				Map<String, Object> tempMap=new HashMap<String, Object>();
				tempMap.put("result", "1");
				String lqUser = request.getParameter("lqUser");
				String sendCount = request.getParameter("sendCount");
				String sendState = request.getParameter("sendState");
				String booKId = request.getParameter("bookIdone");
				String nowTime = DateUtils.getSystemDateAndTime();
				String bookName = request.getParameter("bookName");
				
				BookClassModel bl = this.bookClassDao.getBookClassModelById(Integer.parseInt(id));
				if(bl!=null){
					
					bl.setLqUser(lqUser);
					bl.setLqTime(nowTime);
					bl.setSendState(sendState);
					bl.setSendCount(sendCount);
					
					BookClassModel bl2=bookClassDao.addBookClass(bl);
					
					if(bl2!=null){
						
						if("1".equals(sendState)){
							
							boolean bJian = this.jianKucCount(booKId, bookName, sendCount, nowTime, request);
							System.out.println(bJian+"：减库存：========updateOneSendBook======="+bookName+",sendcount:"+sendCount);
						}
						
						if("1".equals(sType)){
							
							WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"nowSendList\",\"rel\":\"nowSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"1\",\"forwardurl\":\"ensure/nowSendList.htm\"}");//
							return tempMap;
						}else{
							
							WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"hiSendList\",\"rel\":\"hiSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"1\",\"forwardurl\":\"ensure/hiSendList.htm\"}");//
							return tempMap;
						}
					}
				}
			}
			
			if("1".equals(sType)){
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"操作失败，请重试\",\"navTabId\":\"nowSendList\",\"rel\":\"nowSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"1\",\"forwardurl\":\"ensure/nowSendList.htm\"}");
			}else{
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"操作失败，请重试\",\"navTabId\":\"hiSendList\",\"rel\":\"hiSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"1\",\"forwardurl\":\"ensure/hiSendList.htm\"}");
			}
			
			return null;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->updateOneSendBook():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：多个发放  --发放单
	 * 创建时间：2015年1月15日 下午3:07:55  Yin yunfei
	 */
	public Map<String, Object> updateAllSendBook(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");//request.setCharacterEncode("GB2312");
			String ids = request.getParameter("ids");
			String sType=request.getParameter("sType");
			
			if(ids!=null&&!"".equals(ids)){
				
				String str[] =ids.split(",");
				String lqUser = request.getParameter("lqUser");
				boolean bRet =false;
				for(int i=0;i<str.length;i++){
					
					String id = str[i];
					String sendCount = request.getParameter("sendCount"+id);
					String sendState = request.getParameter("sendState"+id);
					String booKId = request.getParameter("abookId"+id);
					String bookName= request.getParameter("bookName"+id);
					String nowTime = DateUtils.getSystemDateAndTime();
					
					BookClassModel bl = this.bookClassDao.getBookClassModelById(Integer.parseInt(id));
					if(bl!=null){
						
						bl.setLqUser(lqUser);
						bl.setLqTime(DateUtils.getSystemDateAndTime());
						bl.setSendState(sendState);
						bl.setSendCount(sendCount);
						
						BookClassModel bl2=bookClassDao.addBookClass(bl);
						
						if(bl2!=null){
							
							if("1".equals(sendState)){
								
								boolean bJian = this.jianKucCount(booKId, bookName, sendCount, nowTime, request);
								System.out.println(bJian+"：减库存：==============="+bookName+",sendcount:"+sendCount);
							}
						
							bRet=true;
						}
					}
				}
				
				if(bRet){
					
					if("1".equals(sType)){
						
						WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"nowSendList\",\"rel\":\"nowSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"2\",\"forwardurl\":\"ensure/nowSendList.htm\"}");
						return null;
					}else{
						
						WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"hiSendList\",\"rel\":\"hiSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"2\",\"forwardurl\":\"ensure/hiSendList.htm\"}");//
						return null;
					}
					
				}
			}
			
			if("1".equals(sType)){
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"操作失败，请重试\",\"navTabId\":\"nowSendList\",\"rel\":\"nowSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"2\",\"forwardurl\":\"ensure/nowSendList.htm\"}");
			}else{
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"操作失败，请重试\",\"navTabId\":\"hiSendList\",\"rel\":\"hiSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"2\",\"forwardurl\":\"ensure/hiSendList.htm\"}");
			}
			
			return null;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->updateOneSendBook():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 用途说明：减库存操作
	 * 创建时间 ： 2015-02-03 yinyunfei
	 * @param booKId
	 * @param bookName
	 * @param sendCount
	 * @param nowTime
	 * @param request
	 * @return
	 */
	private boolean jianKucCount(String booKId,String bookName,String sendCount,String nowTime,HttpServletRequest request){
		
		try {
			//减库存=========start
			BooksInfoModel bl3 = this.booksInfoDao.getBooksInfoModelById(Integer.parseInt(booKId));
			
			if(bl3!=null){
				
				String kcCount =bl3.getKcCount();
				int iKcCount =0;
				
				if(kcCount!=null&&!"".equals(kcCount)){
					
					iKcCount=Integer.parseInt(kcCount);
				}
				
				int iSendCount =0;
				
				if(sendCount!=null&&!"".equals(sendCount)){
					
					iSendCount=Integer.parseInt(sendCount);
				}
				
				int iCount = iKcCount-iSendCount;
				bl3.setKcCount(iCount+"");
				this.booksInfoDao.addBooksInfo(bl3);
				//添加减库存 日志  
				BookSendModel bf = new BookSendModel();
				bf.setBookId(booKId);
				bf.setBookName(bookName);
				bf.setInsertTime(nowTime);
				bf.setSendCount(sendCount);
				bf.setSendType("3");// 学员发放
				
				String sendUser="";
				String userId="";
				
				HttpSession session = request.getSession(false);
				if (session == null) {
					
					session = request.getSession();
				}
				
				AdminUserModel userModel=(AdminUserModel)session.getAttribute(CommonParms.USER_SESSION_KEY);
				
				if(userModel!=null){//跳转到登录页面
					
					sendUser=userModel.getUserName();
					userId=userModel.getUserAccount();
				}
				
				bf.setSendUser(sendUser);
				bf.setUserId(userId);
				this.bookSendDao.addBookSendInfo(bf);
				
				return true;
			}
			//减库存=========end
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->jianKucCount():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：更改状态    减库存
	 * 创建时间：2015年1月24日 下午3:07:55  Yin yunfei 长春
	 */
	public Map<String, Object> updateAllSendBook2(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");//request.setCharacterEncode("GB2312");
			String ids = request.getParameter("ids");
			String sType=request.getParameter("sType");
			
			if(ids!=null&&!"".equals(ids)){
				
				String str[] =ids.split(",");
				String lqUser = request.getParameter("lqUser");
				boolean bRet =false;
				for(int i=0;i<str.length;i++){
					
					String id = str[i];
					String sendCount = request.getParameter("sendCount"+id);
					String booKId = request.getParameter("abookId"+id);
					String bookName= request.getParameter("bookName"+id);
					String nowTime = DateUtils.getSystemDateAndTime();
					BookClassModel bl = this.bookClassDao.getBookClassModelById(Integer.parseInt(id));
					
					if(bl!=null){
						
						bl.setLqUser(lqUser);
						bl.setLqTime(nowTime);
						bl.setSendState("1");
						bl.setSendCount(sendCount);
						
						BookClassModel bl2=bookClassDao.addBookClass(bl);
						
						if(bl2!=null){
							
							//减库存
							BooksInfoModel bl3 = this.booksInfoDao.getBooksInfoModelById(Integer.parseInt(booKId));
							if(bl3!=null){
								
								String kcCount =bl3.getKcCount();
								int iKcCount =0;
								
								if(kcCount!=null&&!"".equals(kcCount)){
									
									iKcCount=Integer.parseInt(kcCount);
								}
								
								int iSendCount =0;
								
								if(sendCount!=null&&!"".equals(sendCount)){
									
									iSendCount=Integer.parseInt(sendCount);
								}
								
								int iCount = iKcCount-iSendCount;
								bl3.setKcCount(iCount+"");
								this.booksInfoDao.addBooksInfo(bl3);
								//添加减库存 日志  
								BookSendModel bf = new BookSendModel();
								bf.setBookId(booKId);
								bf.setBookName(bookName);
								bf.setInsertTime(nowTime);
								bf.setSendCount(sendCount);
								bf.setSendType("3");// 学员发放
								
								String sendUser="";
								String userId="";
								
								HttpSession session = request.getSession(false);
								if (session == null) {
									
									session = request.getSession();
								}
								
								AdminUserModel userModel=(AdminUserModel)session.getAttribute(CommonParms.USER_SESSION_KEY);
								
								if(userModel!=null){//跳转到登录页面
									
									sendUser=userModel.getUserName();
									userId=userModel.getUserAccount();
								}
								
								bf.setSendUser(sendUser);
								bf.setUserId(userId);
								this.bookSendDao.addBookSendInfo(bf);
							}
							bRet=true;
						}
					}
				}
				
				if(bRet){
					
					//更新缓存  
					this.addBookInfoToRedis();
					
					if("1".equals(sType)){
						
						WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"nowSendList\",\"rel\":\"nowSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"3\",\"forwardurl\":\"ensure/nowSendList.htm\"}");
						return null;
					}else{
						
						WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"hiSendList\",\"rel\":\"hiSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"3\",\"forwardurl\":\"ensure/hiSendList.htm\"}");//
						return null;
					}
				}
			}
			
			if("1".equals(sType)){
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"操作失败，请重试\",\"navTabId\":\"nowSendList\",\"rel\":\"nowSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"3\",\"forwardurl\":\"ensure/nowSendList.htm\"}");
			}else{
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"操作失败，请重试\",\"navTabId\":\"hiSendList\",\"rel\":\"hiSendList\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\"3\",\"forwardurl\":\"ensure/hiSendList.htm\"}");
			}
			
			return null;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("CourserAndBookServerImpl-->updateOneSendBook():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：获取期班对象
	 * 创建时间：2015-01-15 yinyunfei
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean getBookClass(HttpServletRequest request,HttpServletResponse response){
		
		String id=request.getParameter("id");
		
		if(id!=null&&!"".equals(id)){
			
			try {
				
				BookClassModel bl = this.bookClassDao.getBookClassModelById(Integer.parseInt(id));
				request.setAttribute("bl", bl);
				String sType=request.getParameter("sType");
				request.setAttribute("sType", sType);
			} catch (Exception e) {
				
				//此处需要加log4j,
				e.printStackTrace();
				Logs.logger_out.error("CourserAndBookServerImpl-->getBookClass():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
			} 
		}
		
		return true;
	}
	
	/**
	 * 方法说明：获取期班对象
	 * 创建时间：2015-01-15 yinyunfei
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean getBookClassByIds(HttpServletRequest request,HttpServletResponse response){
		
		String ids=request.getParameter("ids");
		
		if(ids!=null&&!"".equals(ids)){
			
			try {
				
				String str[] =ids.split(",");
				List<BookClassModel> tempList=new ArrayList<BookClassModel>();
				
				for(int i=0;i<str.length;i++){
					
					String id =str[i];
					if(id!=null&&!"".equals(str)){
						
						BookClassModel bl = this.bookClassDao.getBookClassModelById(Integer.parseInt(id));
						if(bl!=null){
							
							tempList.add(bl);
						}
					}
				}
				
				request.setAttribute("list3", tempList);
				request.setAttribute("ids", ids);
				String sType=request.getParameter("sType");
				request.setAttribute("sType", sType);
			} catch (Exception e) {
				
				//此处需要加log4j,
				e.printStackTrace();
				Logs.logger_out.error("CourserAndBookServerImpl-->getBookClassByIds():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
			} 
		}
		
		return true;
	}
	
	/**
	 * 方法说明： 导出 放单excel
	 * 创建时间： 2015-01-19
	 * 
	 */
	public boolean  exportExcelFfd(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String className = request.getParameter("className");
			String uploadId=request.getParameter("uploadId");
			String who=request.getParameter("who");
			List<BookClassModel> classList = this.bookClassDao.getClassListFfd(className, "", who, uploadId);
			String excepUrl="Ffd-"+DateUtils.getSystemDate()+".xls";
			if(classList!=null&&classList.size()>0){
				
				//Map<String,CourseInfoModel> tompMap = this.getCourseInfoMapFromRedis();
				//判断文件平是否存在
				String path3 =this.getTomcatPath(request);
//				File file =new File(path3);
//				if(!file.exists()){
//					System.out.println();
//					file.mkdir();
//				}
				
				//生成Excel
				boolean bRet = ExcelTreat.writeExcelForFfd(path3+"/"+excepUrl, classList);
				
				if(bRet){
					
					List<String> tempList=new ArrayList<String>();
					tempList.add("upload/"+excepUrl);
					
					String str [] ={"exprotUrl"};
					net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(tempList, getJsonConfig(str));
					String sRet = StrUtils.convertToJson(jsonArray.toString());
					
					WebUtils.getPrintWriter(response, "UTF-8").print(sRet);
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	private String getTomcatPath(HttpServletRequest request){
		//D:\tomcat-6\webapps\tmmadmin\
		String path3 = request.getSession().getServletContext().getRealPath("/");
		
		if(path3!=null){
			
			path3=path3.replace("tmmadmin\\", "upload");
			//System.out.println(path3);
			return path3;
		}
		
		return null;
	}
	
	/**
	 * 方法说明： 导出 放单excel  保障计划  
	 * 创建时间： 2015-01-24    长春
	 * 
	 */
	public boolean  exportExcelBzjh(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String bigType = request.getParameter("bigType");
			
			List<UploadbooksModel>uList= this.uploadbooksDao.getUploadbooksList(bigType,"0","0");
			
			if(uList!=null&&uList.size()>0){
				
				String excepUrl="Bzjh-"+bigType+"-"+DateUtils.getSystemDate()+".xls";
				Map<String,BooksInfoModel> tempMap = this.getBooksInfoMapFromRedis();
				String path3 =this.getTomcatPath(request);
				boolean bRet = ExcelTreat.writeExcelForBzjh(path3+"/"+excepUrl, uList,tempMap);
				
				if(bRet){
					
					List<String> tempList=new ArrayList<String>();
					tempList.add("upload/"+excepUrl);
					
					String str [] ={"exprotUrl"};
					net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(tempList, getJsonConfig(str));
					String sRet = StrUtils.convertToJson(jsonArray.toString());
					
					WebUtils.getPrintWriter(response, "UTF-8").print(sRet);
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 方法说明： 导出 教材 信息 book list excel
	 * 创建时间： 2015-01-19
	 * 
	 */
	public boolean  exportExcelBooList(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String bookName = request.getParameter("bookName");
			String bookAuthor = request.getParameter("bookAuthor");
			String bigType = request.getParameter("bigType");
			
			List<BooksInfoModel> classList = this.booksInfoDao.getBooksInfoList6(bookName, bookAuthor, bigType);
			
			String excepUrl="BooKlist-"+DateUtils.getSystemDate()+".xls";
			if(classList!=null&&classList.size()>0){
				Map<String,CourseInfoModel> tempMap = this.getCourseInfoMapFromRedis();
				//生成Excel
				String path3 =this.getTomcatPath(request);
				boolean bRet = ExcelTreat.writeExcelForBook(path3+"/"+excepUrl, classList,tempMap);
				
				if(bRet){
					
					List<String> tempList=new ArrayList<String>();
					tempList.add("upload/"+excepUrl);
					
					String str [] ={"exprotUrl2"};
					net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(tempList, getJsonConfig(str));
					String sRet = StrUtils.convertToJson(jsonArray.toString());
					
					WebUtils.getPrintWriter(response, "UTF-8").print(sRet);
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 方法说明： 导出 使用计划 list excel
	 * 创建时间： 2015-01-19
	 * 
	 */
	public boolean  exportExcelUseList(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String bookName = request.getParameter("bookName");
			String bookAuthor = request.getParameter("bookAuthor");
			String bigType = request.getParameter("bigType");

			List<UploadbooksModel> useList = this.uploadbooksDao.getUploadbooksList("", "", "");
			String excepUrl="Uselist-"+DateUtils.getSystemDate()+".xls";
			if(useList!=null&&useList.size()>0){
				//生成Excel
				String path3 =this.getTomcatPath(request);
				boolean bRet = ExcelTreat.writeExcelForUseBook(path3+"/"+excepUrl, useList);
				
				if(bRet){
					
					List<String> tempList=new ArrayList<String>();
					tempList.add("upload/"+excepUrl);
					
					String str [] ={"exprotUrl3"};
					net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(tempList, getJsonConfig(str));
					String sRet = StrUtils.convertToJson(jsonArray.toString());
					
					WebUtils.getPrintWriter(response, "UTF-8").print(sRet);
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	protected JsonConfig getJsonConfig(String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		//jsonConfig = new JsonConfig(); 
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setExcludes(excludes);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return jsonConfig;
	}
	
	private CourseInfoDao courseInfoDao;
	private BooksInfoDao booksInfoDao;
	private BookSendDao bookSendDao;
	private BookDestroyDao bookDestroyDao;
	private BookClassDao bookClassDao;
	private BookUploadDao bookUploadDao;
	private UploadbooksDao uploadbooksDao;
	
	public BookClassDao getBookClassDao() {
		return bookClassDao;
	}

	public void setBookClassDao(BookClassDao bookClassDao) {
		this.bookClassDao = bookClassDao;
	}

	public BookUploadDao getBookUploadDao() {
		return bookUploadDao;
	}

	public void setBookUploadDao(BookUploadDao bookUploadDao) {
		this.bookUploadDao = bookUploadDao;
	}

	public UploadbooksDao getUploadbooksDao() {
		return uploadbooksDao;
	}

	public void setUploadbooksDao(UploadbooksDao uploadbooksDao) {
		this.uploadbooksDao = uploadbooksDao;
	}

	public BookDestroyDao getBookDestroyDao() {
		return bookDestroyDao;
	}

	public void setBookDestroyDao(BookDestroyDao bookDestroyDao) {
		this.bookDestroyDao = bookDestroyDao;
	}

	public BookSendDao getBookSendDao() {
		return bookSendDao;
	}

	public void setBookSendDao(BookSendDao bookSendDao) {
		this.bookSendDao = bookSendDao;
	}

	private RedisClient2 redisClient;
	
	public CourseInfoDao getCourseInfoDao() {
		return courseInfoDao;
	}
	public void setCourseInfoDao(CourseInfoDao courseInfoDao) {
		this.courseInfoDao = courseInfoDao;
	}
	public BooksInfoDao getBooksInfoDao() {
		return booksInfoDao;
	}
	public void setBooksInfoDao(BooksInfoDao booksInfoDao) {
		this.booksInfoDao = booksInfoDao;
	}
	
	public RedisClient2 getRedisClient() {
		
		return redisClient;
	}

	public void setRedisClient(RedisClient2 redisClient) {
		
		this.redisClient = redisClient;
	}
}
