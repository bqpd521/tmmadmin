package com.yougou.dao;

import java.util.HashMap;
import java.util.Map;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.BookSendModel;

//领取记录 DAO  
//2015-01-04  yinyunfei
public class BookSendDao extends  HibernateBaseDao<BookSendModel>{

	/**
	 * 方法说明：添加或者修
	 * 创建时间：2015年01月01日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BookSendModel addBookSendInfo(BookSendModel bf) throws Exception{
		
		BookSendModel bf2 = this.save(bf);
		return bf2;
	}
	
	/**
	 * 方法说明：分页获教材信息
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookSendModel> queryBookSendInfoPageBean(PageBean<BookSendModel> pageBean,String bookName) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from BookSendModel T where 1=1 ";
			
			if(bookName!=null&&!"".equals(bookName)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			
			hql +=" order by T.id desc";
			
			PageBean<BookSendModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材领用信息");
			
			return pbBean;
		}

		return null;
	}
}
