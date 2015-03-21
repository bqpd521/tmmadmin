package com.yougou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.BooksInfoModel;

/**
 * 功能说明：教材信息DAO
 * 创建时间：2015-01-01 yinyunfei
 */
public class BooksInfoDao extends  HibernateBaseDao<BooksInfoModel>{

	
	/**
	 * 方法说明：添加或者修改教材 
	 * 创建时间：2015年01月01日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BooksInfoModel addBooksInfo(BooksInfoModel bf) throws Exception{
		
		BooksInfoModel bf2 = this.save(bf);
		return bf2;
	}
	
	/**
	 * 方法说明：分页获教材信息
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean  orderType:1 库存明细；2：缺库
	 * @return
	 * @throws Exception
	 */
	public PageBean<BooksInfoModel> queryBooksInfoPageBean(PageBean<BooksInfoModel> pageBean,String bookName,String bookAuthor,String bigType,String bookState,String orderType,String kcCount) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from BooksInfoModel T where 1=1 ";
			
			if(bookName!=null&&!"".equals(bookName)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			if(bookAuthor!=null&&!"".equals(bookAuthor)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookAuthor like '%" + bookAuthor + "%'";
			}
			
			if(bigType!=null&&!"".equals(bigType)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bigType=:bigType";
				map.put("bigType", bigType);
			}
			
			if(bookState!=null&&!"".equals(bookState)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookState=:bookState";
				map.put("bookState", bookState);
			}
			
			if(kcCount!=null&&!"".equals(kcCount)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.kcCount<=:kcCount";
				map.put("kcCount", kcCount);
			}
			
			if("1".equals(orderType)){
				
				hql +=" order by T.id desc";
			}else if("2".equals(orderType)){
				
				hql +=" order by T.queCount desc";
			}
			
			
			PageBean<BooksInfoModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：分页获教材信息
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BooksInfoModel> queryBooksInfoPageBean(PageBean<BooksInfoModel> pageBean,String bookName,String bookAuthor) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from BooksInfoModel T where 1=1 and T.huiShou='1' ";
			
			if(bookName!=null&&!"".equals(bookName)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			if(bookAuthor!=null&&!"".equals(bookAuthor)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookAuthor like '%" + bookAuthor + "%'";
			}
			
			hql +=" order by T.id desc";
			
			PageBean<BooksInfoModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：分页获教材信息
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public List<BooksInfoModel> getBooksInfoList6(String bookName,String bookAuthor,String bigType) throws Exception{
		
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from BooksInfoModel T where 1=1  ";
			
			if(bookName!=null&&!"".equals(bookName)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			if(bookAuthor!=null&&!"".equals(bookAuthor)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookAuthor like '%" + bookAuthor + "%'";
			}
			
			if(bigType!=null&&!"".equals(bigType)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bigType=:bigType";
				map.put("bigType", bigType);
			}
			
			hql +=" order by T.id desc";
			
			List<BooksInfoModel> tempList=this.queryByHql(hql, map, null);
			
			return tempList;
	}
	
	/**
	 * 方法说明：删除教材
	 * 创建时间：2014年12月31 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean deleteBook(String bookId) throws Exception{
		
		String sql=" DELETE FROM tbl_books_info WHERE id=:bookId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bookId", bookId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：获取教材信息
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BooksInfoModel getBooksInfoModelById(int id)  throws Exception{
		
		String hql = " from BooksInfoModel t where t.id=:id";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		
		List<BooksInfoModel> tempList=this.queryByHql(hql, map, "");
		
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
	public List<BooksInfoModel> getBooInfoList()  throws Exception{
		
		String hql = " from BooksInfoModel t where 1=1 ";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		List<BooksInfoModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
}
