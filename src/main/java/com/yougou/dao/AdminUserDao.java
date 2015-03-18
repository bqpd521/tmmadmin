package com.yougou.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.yougou.dao.hibernate.HibernateBaseDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.AdminUserModel;

/**
 * 功能说明：后台管理员操作数据库相关
 * 创建时间：2014-09-25 yinyunfei
 */
public class AdminUserDao  extends  HibernateBaseDao<AdminUserModel>{

	/**
	 * 方法说明：通过帐号和密码进行验证登录
	 * 创建时间：2014年9月25日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public AdminUserModel userLogin(String userAccount,String userPsw)  throws Exception{
		
		String hql = " from AdminUserModel t where t.userAccount=:userAccount and t.userPsw=:userPsw";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("userAccount",userAccount);
		map.put("userPsw",userPsw);
		
		List<AdminUserModel> tempList=this.queryByHql(hql, map, "");
		
		if(tempList!=null&&tempList.size()>0){
			
			return tempList.get(0);
		}
		
		return null;
	}
	
	/**
	 * 方法说明：通过帐号和密码进行验证登录
	 * 创建时间：2014年9月25日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 * @return
	 * @throws Exception
	 */
	public AdminUserModel getAdminUserModelById(int id)  throws Exception{
		
		String hql = " from AdminUserModel t where t.id=:id";
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		
		List<AdminUserModel> tempList=this.queryByHql(hql, map, "");
		
		if(tempList!=null&&tempList.size()>0){
			
			return tempList.get(0);
		}
		
		return null;
	}
	
	/**
	 * 方法说明：添加用户
	 * 创建时间：2014年12月8日 下午5:27:02  Yin yunfei
	 * @return
	 * @throws Exception
	 */
	public AdminUserModel addUserInfo(AdminUserModel re) throws Exception{
		
		AdminUserModel re2 = this.save(re);
		return re2;
	}
	
	/**
	 * 方法说明：修改密码
	 * 创建时间：2014年10月23日 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean changePsw(String oldPsw,String newPsw) throws Exception{
		
		String sql=" update tbl_user_user set userPsw=:newPsw  WHERE userPsw=:oldPsw ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("newPsw", newPsw);
		map.put("oldPsw", oldPsw);
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：添加管理员用户访问日志
	 * 创建时间：2014年10月23日 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean insertLoginLog(String userId,String loginIp,String loginTime) throws Exception{
		
		String sql=" INSERT INTO tbl_admin_user2log(userId,loginIp,loginTime)VALUES('"+userId+"','"+loginIp+"','"+loginTime+"') ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("loginIp", loginIp);
		map.put("loginTime", loginTime);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：删除用户
	 * 创建时间：2014年12月31 下午5:45:12  Yin yunfei
	 * @return
	 */
	public boolean deleteUser(String userId) throws Exception{
		
		String sql=" DELETE FROM tbl_user_user WHERE id=:userId ";
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		
		boolean bRet=this.excuteBySql(sql, map)>0?true:false;
		
		return bRet;
	}
	
	/**
	 * 方法说明：分页获取用户信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<AdminUserModel> queryAdminUserPageBean(PageBean<AdminUserModel> pageBean) throws Exception{
		
			
		if(pageBean!=null){
			
			Map<String, Object> map = new HashMap<String, Object>();
			String hql = " from AdminUserModel T where 1=1 ";
			
//			if(recDate!=null&&!"".equals(recDate)){
//				
//				hql += " and T.recDate=:recDate ";
//				map.put("recDate", recDate);
//			}
			
			hql +=" order by T.id desc";
			
			PageBean<AdminUserModel> pbBean=this.queryPageByHql(hql, pageBean, map, "分页查询用户信息");
			
			return pbBean;
		}

		return null;
	}
}
