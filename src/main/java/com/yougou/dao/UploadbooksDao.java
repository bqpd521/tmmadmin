package com.yougou.dao;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.BookUploadModel;
import com.yougou.model.UploadbooksModel;

/**
 * 功能说明：上传信息DAO  使用计划
 * 创建时间：2015-01-09 yinyunfei
 */
public class UploadbooksDao extends  HibernateBaseDao<UploadbooksModel>{

	/**
	 * 方法说明：添加或者修
	 * 创建时间：2015年01月08日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public UploadbooksModel addUploadbooks(UploadbooksModel bk) throws Exception{
		
		UploadbooksModel bf2 = this.save(bk);
		return bf2;
	}
	
	/**
	 * 方法说明：更改上传的状态，标识已处理
	 * 创建时间：2014年12月31 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean updateUbooksState(String uploadId) throws Exception{
		
		String sql=" update tbl_books_uploadbooks set sendState='1' WHERE uploadId=:uploadId ";
		
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
		
		String sql=" delete from tbl_books_uploadbooks WHERE uploadId=:uploadId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uploadId", uploadId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：分页保障计划
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<UploadbooksModel> queryBzjhInfoPageBean(PageBean<UploadbooksModel> pageBean,String bookName,String bigType,String uploadId,String isPrint) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from UploadbooksModel T where 1=1 and T.sendState='0' ";
			
			if(bookName!=null&&!"".equals(bookName)){
				
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			if(bigType!=null&&!"".equals(bigType)){
				
				hql += " and T.bigType=:bigType ";
				map.put("bigType", bigType);
			}
			
			if(uploadId!=null&&!"".equals(uploadId)){
				
				hql += " and T.uploadId=:uploadId ";
				map.put("uploadId", uploadId);
			}
			
			if(isPrint!=null&&!"".equals(isPrint)){
				
				hql += " and T.isPrint=:isPrint ";
				map.put("isPrint", isPrint);
			}
			
			hql +=" order by T.id desc";
			
			PageBean<UploadbooksModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：分页使用计划
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<UploadbooksModel> queryUseListPageBean(PageBean<UploadbooksModel> pageBean,String bookName,String bigType) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from UploadbooksModel T where 1=1  ";
			
			if(bookName!=null&&!"".equals(bookName)){
				
				hql += " and T.bookName like '%" + bookName + "%'";
			}
			
			if(bigType!=null&&!"".equals(bigType)){
				
				hql += " and T.bigType=:bigType ";
				map.put("bigType", bigType);
			}
			
			hql +=" order by T.id desc";
			
			PageBean<UploadbooksModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询教材信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：获取保障计划 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public List<UploadbooksModel> getUploadbooksList(String bigType,String isPrint,String sendState)  throws Exception{
		
		String hql = " from UploadbooksModel t where 1=1 ";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(bigType!=null&&!"".equals(bigType)){
			
			hql += " and t.bigType=:bigType ";
			map.put("bigType", bigType);
		}
		
		if(sendState!=null&&!"".equals(sendState)){
			
			hql += " and t.sendState=:sendState ";
			map.put("sendState", sendState);
		}
		
		if(isPrint!=null&&!"".equals(isPrint)){
			
			hql += " and t.isPrint=:isPrint ";
			map.put("isPrint", isPrint);
		}
		
		List<UploadbooksModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
	
	/**
	 * 方法说明：按group by 汇总使用数量 
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public List<UploadbooksModel> getUploadBooksListCount(String sendState)  throws Exception{
		
		String hql = " from UploadbooksModel t where 1=1 ";
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		if(sendState!=null&&!"".equals(sendState)){
			
			hql += " and t.sendState=:sendState ";
			map.put("sendState", sendState);
		}
		
//		sql += " where t.sendState='0' group by t.bookName ";
		
		
		List<UploadbooksModel> tepmList2 = this.queryByHql(hql, map, null);
		
		return tepmList2;
	}
	
	/**
	 * 方法说明：按group by 汇总使用数量 
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public List<UploadbooksModel> getUploadBooksListCount2()  throws Exception{
		
		//String hql = " from UploadbooksModel t where 1=1 ";
		String sql = " select t.bookName, sum(t.userCount) as userCount from tbl_books_uploadbooks t ";
		sql += " where t.sendState='0' group by t.bookName ";
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		List<UploadbooksModel> tepmList2 = (List<UploadbooksModel>)this.queryBySql(sql, map, "");
		
		if(tepmList2!=null&&tepmList2.size()>0){
			
			for(UploadbooksModel oj:tepmList2){
				
				System.out.println(oj.getBookName());
			}
		}
		
		return tepmList2;
	}
	
	/**
	 * 方法说明：通过ID获取UploadbooksModel对角
	 * 创建时间：2015-3-15  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public UploadbooksModel getUploadbooksModelById(int id)  throws Exception{
		
		String hql = " from UploadbooksModel t where t.id=:id";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		
		List<UploadbooksModel> tempList=this.queryByHql(hql, map, "");
		
		if(tempList!=null&&tempList.size()>0){
			
			return tempList.get(0);
		}
		
		return null;
	}
}
