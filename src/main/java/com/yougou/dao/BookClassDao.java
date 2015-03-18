package com.yougou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.BookClassModel;

/**
 * 功能说明：按期班使用情况DAO
 * 创建时间：2015-01-09 yinyunfei
 */
public class BookClassDao extends  HibernateBaseDao<BookClassModel>{

	/**
	 * 方法说明：添加或者修
	 * 创建时间：2015年01月08日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BookClassModel addBookClass(BookClassModel bl) throws Exception{
		
		BookClassModel bf2 = this.save(bl);
		return bf2;
	}
	
	/**
	 * 方法说明：更改上传的状态
	 * 创建时间：2014年12月31 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean updateClassState(String uploadId) throws Exception{
		
		String sql=" update tbl_books_class set sendState='1' WHERE uploadId=:uploadId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploadId", uploadId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：删除通过上传ID
	 * 创建时间：2015年2月2 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean deleteByUploadId(String uploadId) throws Exception{
		
		String sql=" delete from tbl_books_class WHERE uploadId=:uploadId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploadId", uploadId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：获取当期期班 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public List<BookClassModel> getUploadBooksListByUploadId(String uploadId)  throws Exception{
		
		//String hql = " from UploadbooksModel t where 1=1 ";
		String sql = "select distinct t.`className` from tbl_books_class t where 1=1 ";
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(uploadId!=null&&!"".equals(uploadId)){
			
//			hql += " and t.uploadId=:uploadId ";
			sql += " and t.uploadId=:uploadId ";
			map.put("uploadId", uploadId);
		}
		List<BookClassModel> tepmList2 = this.queryBySql(sql, map, "");
		//List<UploadbooksModel> tempList=this.queryByHql(hql, map, "");
		
		return tepmList2;
	}
	
	/**
	 * 方法说明：获取当期期班 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public List<BookClassModel> getUploadBooksListByUploadId2(String uploadId)  throws Exception{
		
		//String hql = " from UploadbooksModel t where 1=1 ";
		String sql = "select distinct t.`className` from tbl_books_class t where 1=1 ";
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(uploadId!=null&&!"".equals(uploadId)){
			
//			hql += " and t.uploadId=:uploadId ";
			sql += " and t.uploadId!=:uploadId ";
			map.put("uploadId", uploadId);
		}
		List<BookClassModel> tepmList2 = this.queryBySql(sql, map, "");
		//List<UploadbooksModel> tempList=this.queryByHql(hql, map, "");
		
		return tepmList2;
	}
	
	/**
	 * 方法说明：分页获取期班信息
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookClassModel> querySendInfoPageBean(PageBean<BookClassModel> pageBean,String bookName,String uploadId,String bookState,String className) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from BookClassModel T where 1=1 ";
			
			if(bookName!=null&&!"".equals(bookName)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			if(uploadId!=null&&!"".equals(uploadId)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.uploadId=:uploadId";
				map.put("uploadId", uploadId);
			}
			
			if(className!=null&&!"".equals(className)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.className=:className";
				map.put("className", className);
			}
			
			if(bookState!=null&&!"".equals(bookState)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.sendState=:bookState";
				map.put("bookState", bookState);
			}
			
			hql +=" order by T.id desc";
			
			PageBean<BookClassModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：历史发放单
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookClassModel> queryHiSendInfoPageBean(PageBean<BookClassModel> pageBean,String bookName,String uploadId,String bookState,String className) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from BookClassModel T where 1=1 ";
			
			if(bookName!=null&&!"".equals(bookName)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			if(uploadId!=null&&!"".equals(uploadId)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.uploadId!=:uploadId";
				map.put("uploadId", uploadId);
			}
			
			if(className!=null&&!"".equals(className)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.className=:className";
				map.put("className", className);
			}
			
			if(bookState!=null&&!"".equals(bookState)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.sendState=:bookState";
				map.put("bookState", bookState);
			}
			
			hql +=" order by T.id desc";
			
			PageBean<BookClassModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：获取一个对象 通过ID
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BookClassModel getBookClassModelById(int id)  throws Exception{
		
		String hql = " from BookClassModel t where t.id=:id";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		
		List<BookClassModel> tempList=this.queryByHql(hql, map, "");
		
		if(tempList!=null&&tempList.size()>0){
			
			return tempList.get(0);
		}
		
		return null;
	}
	
	/**
	 * 方法说明：获取教材信息 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public List<BookClassModel> getClassList(String className,String sendState)  throws Exception{
		
		String hql = " from BookClassModel T where 1=1 ";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		if(className!=null&&!"".equals(className)){
			//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
			hql += " and T.className=:className";
			map.put("className", className);
		}
		
		if(sendState!=null&&!"".equals(sendState)){
			//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
			hql += " and T.sendState=:sendState";
			map.put("sendState", sendState);
		}
		
		hql +=" order by T.id desc";
		
		List<BookClassModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
	
	/**
	 * 方法说明：获取教材信息 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public List<BookClassModel> getClassListFfd(String className,String sendState,String who,String uploadId)  throws Exception{
		
		String hql = " from BookClassModel T where 1=1 ";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		if(className!=null&&!"".equals(className)){
			//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
			hql += " and T.className=:className";
			map.put("className", className);
		}
		
		if(sendState!=null&&!"".equals(sendState)){
			//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
			hql += " and T.sendState=:sendState";
			map.put("sendState", sendState);
		}
		
		if("now".equals(who)){
			//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
			hql += " and T.uploadId=:uploadId";
			map.put("uploadId", uploadId);
		}else if("hi".equals(who)){
			
			hql += " and T.uploadId!=:uploadId";
			map.put("uploadId", uploadId);
		}
		
		hql +=" order by T.id desc";
		
		List<BookClassModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
	
	/**
	 * 方法说明：获取一个对象
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BookClassModel getBookClassModelById(String uploadId,String bookId,String useClass)  throws Exception{
		
		String hql = " from BookClassModel t where t.uploadId=:uploadId and className=:useClass and bookId=:bookId ";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("uploadId",uploadId);
		map.put("bookId",bookId);
		map.put("useClass",useClass);
		
		List<BookClassModel> tempList=this.queryByHql(hql, map, "");
		
		if(tempList!=null&&tempList.size()>0){
			
			return tempList.get(0);
		}
		
		return null;
	}
}
