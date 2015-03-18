package com.yougou.dao;

import java.util.HashMap;
import java.util.Map;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.BookUploadModel;

/**
 * 功能说明：上传信息DAO
 * 创建时间：2015-01-09 yinyunfei
 */
public class BookUploadDao extends  HibernateBaseDao<BookUploadModel>{

	/**
	 * 方法说明：添加或者修
	 * 创建时间：2015年01月08日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public BookUploadModel addBookUpload(BookUploadModel bm) throws Exception{
		
		BookUploadModel bf2 = this.save(bm);
		return bf2;
	}
	
	
	/**
	 * 方法说明：分页获上记录信息
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<BookUploadModel> queryUploadInfoPageBean(PageBean<BookUploadModel> pageBean,String uploadDate) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from BookUploadModel T where 1=1 ";
			
			if(uploadDate!=null&&!"".equals(uploadDate)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.uploadDate=:uploadDate ";
				map.put("uploadDate", uploadDate);
			}
			
			hql +=" order by T.id desc";
			
			PageBean<BookUploadModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：更改上传的状态
	 * 创建时间：2014年12月31 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean uploadState(String uploadId) throws Exception{
		
		String sql=" update tbl_books_uploads set uploadState='1' WHERE id=:uploadId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploadId", uploadId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明： 查找当前最大的ID
	 * 创建时间： 2015-01-10 YYF
	 * @return
	 */
	public String getUploadTableMaxId(){

		String sql="select id from tbl_books_uploads order by id desc limit 1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		
		String id = this.uniqueQueryBySql(sql, map, null)+"";
		return id;
	}
	
	/**
	 * 方法说明：删除通过上传ID
	 * 创建时间：2015年2月2 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean deleteByUploadId(String uploadId) throws Exception{
		
		String sql=" delete from tbl_books_uploads WHERE id=:uploadId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploadId", uploadId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
}
