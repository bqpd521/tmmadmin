package com.yougou.dao;

import java.util.HashMap;
import java.util.List;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.model.BookDestroyModel;

/**
 * 功能说明：销毁DAO
 * 创建时间：2015-01-05 yinyunfei
 */
public class BookDestroyDao extends  HibernateBaseDao<BookDestroyModel>{

	/**
	 * 方法说明：添加或者修
	 * 创建时间：2015年01月01日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BookDestroyModel addBookDestroyInfo(BookDestroyModel bm) throws Exception{
		
		BookDestroyModel bf2 = this.save(bm);
		return bf2;
	}
	
	/**
	 * 方法说明：获取销毁信息
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public List<BookDestroyModel> getBookDestroyModelByBookId(String bookId)  throws Exception{
		
		String hql = " from BookDestroyModel t where t.bookId=:bookId";
		hql +=" order by t.id desc  ";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("bookId",bookId);
		
		List<BookDestroyModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
}
