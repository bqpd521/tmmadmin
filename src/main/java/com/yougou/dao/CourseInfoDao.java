package com.yougou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.BooksInfoModel;
import com.yougou.model.CourseInfoModel;

/**
 * 功能说明：课程信息DAO
 * 创建时间：2015-01-01 yinyunfei
 */
public class CourseInfoDao  extends  HibernateBaseDao<CourseInfoModel>{

	/**
	 * 方法说明：添加或者修改 课程 
	 * 创建时间：2015年01月01日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public CourseInfoModel addCourseInfo(CourseInfoModel cf) throws Exception{
		
		CourseInfoModel cf2 = this.save(cf);
		return cf2;
	}
	
	/**
	 * 方法说明：分页获课程信息
	 * 创建时间：2015年01月01日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<CourseInfoModel> queryCourseInfoPageBean(PageBean<CourseInfoModel> pageBean,String courseName) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from CourseInfoModel T where 1=1 ";
			
			if(courseName!=null&&!"".equals(courseName)){
				//String hql2 = "FROM Table AS t WHERE t.field like '%" + str + "%'";
				hql += " and T.courseName like '%" + courseName + "%'";
				map.put("courseName", courseName);
			}
			
			hql +=" order by T.id desc";
			
			PageBean<CourseInfoModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询课程信息");
			
			return pbBean;
		}

		return null;
	}
	
	/**
	 * 方法说明：删除课程
	 * 创建时间：2014年12月31 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean deleteCourse(String courseId) throws Exception{
		
		String sql=" DELETE FROM tbl_course_info WHERE id=:courseId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("courseId", courseId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：获取对角
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public CourseInfoModel getCourseInfoModelById(int id)  throws Exception{
		
		String hql = " from CourseInfoModel t where t.id=:id";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		
		List<CourseInfoModel> tempList=this.queryByHql(hql, map, "");
		
		if(tempList!=null&&tempList.size()>0){
			
			return tempList.get(0);
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
	public List<CourseInfoModel> getCourseInfoList()  throws Exception{
		
		String hql = " from CourseInfoModel t where 1=1 and t.courseState='1' order by t.courseName desc";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		List<CourseInfoModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
	
	/**
	 * 方法说明：获取课程信息 list
	 * 创建时间：2014年12月30日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public List<CourseInfoModel> getCourseInfoList(String courseState)  throws Exception{
		
		String hql = " from CourseInfoModel t where 1=1 ";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("courseState",courseState);
		
		List<CourseInfoModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
	
	/**
	 * 方法说明：通过课程名称获取课程信息
	 * 创建时间：2015年02月02日 下午3:07:55  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public List<CourseInfoModel> getCourseInfo(String courseName)  throws Exception{
		
		String hql = " from CourseInfoModel t where 1=1 ";
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		if(courseName!=null&&!"".equals(courseName)){
			
			hql += " and t.courseName=:courseName";
			map.put("courseName",courseName);
		}
		
		List<CourseInfoModel> tempList=this.queryByHql(hql, map, "");
		
		return tempList;
	}
	
	/**
	 * 方法说明：通过名称查询是否存在   
	 * 创建时间： 2015-02-02 YYF
	 * @return
	 */
	public String getCourseName(String courseName){

		String sql="select courseName from tbl_course_info order by id desc limit 1 ";
		Map<String, Object> map = new HashMap<String, Object>();
		
		String tempName = this.uniqueQueryBySql(sql, map, null)+"";
		return tempName;
	}
}
