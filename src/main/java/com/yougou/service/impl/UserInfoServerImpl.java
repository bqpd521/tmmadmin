package com.yougou.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import com.yougou.common.*;
import com.yougou.dao.AdminUserDao;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.AdminUserModel;
import com.yougou.service.UserInfoServer;

public class UserInfoServerImpl implements UserInfoServer {

	/**
	 * 方法说明：通过帐号和密码进行验证登录,验证成功创建session
	 * 创建时间：2014年9月25日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 */
	public Map<String, Object> userLogin(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			Map<String, Object> modelMap = new HashMap<String, Object>();
			String userAccount=request.getParameter("userAccount");
			String userPsw=request.getParameter("userPsw");
			System.out.println("===============gon!~~~~~~~~~"+userAccount);
			if(userAccount==null||userPsw==null||"".equals(userAccount)||"".equals(userPsw)){
				
				modelMap.put("result", "-1");
				
				return modelMap;
			}
			
			userAccount=userAccount.replace("=", "");
			userAccount=userAccount.replace("'", "");
			userPsw=userPsw.replace("=", "");
			userPsw=userPsw.replace("'", "");
			
			String md5Psw=Md5PwdEncoder.encryptPasswordNoDepend(userPsw, CommonParms.USER_PSW_SATLS+userAccount);
			
			AdminUserModel userModel=adminUserDao.userLogin(userAccount, md5Psw);
			
			if(userModel!=null){
				
				//创建用户session, 由于要用到分布式(多个web服务器)，因此需要用基于缓存文件的session  
				//暂时还没有服务器
				
				HttpSession session = request.getSession(false);
				if (session == null) {
					
					session = request.getSession();
				}
				
				session.setMaxInactiveInterval(60*60*2);//秒
				session.setAttribute(CommonParms.USER_SESSION_KEY, userModel);
				
				//添加访问日志 START
				String userId=userModel.getId()+"";
				String loginIp=WebUtils.getIpAddr(request);
				String loginTime=DateUtils.getSystemDateAndTime();
				//adminUserDao.insertLoginLog(userId, loginIp, loginTime);
				//添加访问日志 END
				
				//测试redis  
//				Jedis js=redisClient.getConnection();
//				js.set("username", userAccount);
//				
//				System.out.println("Redis:======================"+js.get("username"));
				
//	        	String url2=WebUtils.getBasePath(request);
//	        	response.sendRedirect(url2+"/goods/index.htm");
				modelMap.put("result", "1");
				
				String[] excludes = {"recgoodsinfo"};
				JSONArray jsonObject = JSONArray.fromObject(modelMap, getJsonConfig(excludes));
				String sRet=StrUtils.convertToJson(jsonObject.toString());
				WebUtils.getPrintWriter(response, "UTF-8").print(sRet);
			}else{
				
				modelMap.put("result", "-1");
				
				String[] excludes = {"recgoodsinfo"};
				JSONArray jsonObject = JSONArray.fromObject(modelMap, getJsonConfig(excludes));
				String sRet=StrUtils.convertToJson(jsonObject.toString());
				WebUtils.getPrintWriter(response, "UTF-8").print(sRet);
				
				return modelMap;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("UserInfoServerImpl-->userLogin():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：添加用户
	 * 创建时间：2014年12月08日 下午3:07:55  Yin yunfei
	 * @param userAccount
	 * @param userPsw
	 */
	public Map<String, Object> addUser(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("result", "-1");
			
			String insertTime=DateUtils.getSystemDateAndTime();
			String userName=request.getParameter("userName");
			String userPhone=request.getParameter("userPhone");
			String userMail=request.getParameter("userMail");
			String userAccount=request.getParameter("userAccount");
			String userDept=request.getParameter("userDept");
			String userPsw=request.getParameter("userPsw");
			String md5Psw=Md5PwdEncoder.encryptPasswordNoDepend(userPsw, CommonParms.USER_PSW_SATLS+userAccount);
			String userState=request.getParameter("userState");
			String userType=request.getParameter("userType");	
			String id=request.getParameter("userId");
			String addOrEdit="1";//添加；0：编辑
			AdminUserModel re =null;
			
			if(id!=null&&!"".equals(id)){
				
				re=adminUserDao.getAdminUserModelById(Integer.parseInt(id));
			}else{
				
				re =new AdminUserModel();
			}
			
			re.setUserAccount(userAccount);
			re.setUserDept(userDept);
			re.setUserMail(userMail);
			re.setUserName(userName);
			re.setUserPhone(userPhone);
			
			if(userPsw!=null&&!"".equals(userPsw)){
				
				re.setUserPsw(md5Psw);
			}
			
			re.setUserState(userState);
			re.setUserType(userType);
			re.setInsertTime(insertTime);
			
			if(id!=null&&!"".equals(id)){
				
				re.setId(Integer.parseInt(id));
				addOrEdit="0";
			}
			
			AdminUserModel re2=adminUserDao.addUserInfo(re);
			
			if(re2!=null){
				
				modelMap.put("result", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", "ok");
			}
			//\"rel\":\"userList2\" \"navTabId\":\"userList2\",
			WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"操作成功\",\"navTabId\":\"userList2\",\"rel\":\"userList2\",\"callbackType\":\"closeCurrent\",\"addOrEdit\":\""+addOrEdit+"\"}");
			
			return modelMap;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("UserInfoServerImpl-->addUser():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 方法说明：分页获取用户信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public boolean  queryAdminUserPageBean(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("pageSize");
			String pageNow=request.getParameter("pageNow");
			
            int iPageSize=10;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<AdminUserModel> pageBean =new PageBean<AdminUserModel>(); 
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<AdminUserModel> pb = adminUserDao.queryAdminUserPageBean(pageBean);
			
			if(pb!=null&&pb.getResult().size()>0){
				
				String[] excludes = {"usrInfo"};
				JSONArray jsonObject = JSONArray.fromObject(pb, getJsonConfig(excludes));
				String sRet=StrUtils.convertToJson(jsonObject.toString());
				WebUtils.getPrintWriter(response, "UTF-8").print(sRet);
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("UserInfoServerImpl-->queryAdminUserPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：删除用户
	 * 创建时间：2014年12月31日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public boolean  deleteUser(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String userId=request.getParameter("userId");
			
			if(userId!=null&&!"".equals(userId)){
				
				userId=userId.replace("=", "");
				userId=userId.replace("'", "");
				boolean bRet=adminUserDao.deleteUser(userId);
				
				if(bRet){
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"删除成功\",\"navTabId\":\"userList2\",\"rel\":\"userList2\"}");
				}else{
					
					WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"删除失败\",\"navTabId\":\"userList2\",\"rel\":\"userList2\"}");
				}
			}
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("UserInfoServerImpl-->deleteUser():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：删除用户
	 * 创建时间：2014年12月31日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public boolean  changePsw(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String oldPsw=request.getParameter("userPsw");
			String newPsw=request.getParameter("userPsw1");
			
			HttpSession session = request.getSession(false);
			if (session == null) {
				
				session = request.getSession();
			}
			
			AdminUserModel userModel=(AdminUserModel)session.getAttribute(CommonParms.USER_SESSION_KEY);
			String userAccount="";
			
			if(userModel!=null){
				
				userAccount= userModel.getUserAccount();
			}
			
			String oldPswMd5=Md5PwdEncoder.encryptPasswordNoDepend(oldPsw, CommonParms.USER_PSW_SATLS+userAccount);
			String newPswMd5=Md5PwdEncoder.encryptPasswordNoDepend(newPsw, CommonParms.USER_PSW_SATLS+userAccount);
			
			boolean bRet = this.adminUserDao.changePsw(oldPswMd5, newPswMd5);
			
			if(bRet){
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"200\",\"message\":\"修改成功\",\"addOrEdit\":\"1\",\"rel\":\"userList2\"}");
			}else{
				
				WebUtils.getPrintWriter(response, "UTF-8").print("{\"statusCode\":\"300\",\"message\":\"修改失败，请重试\",\"addOrEdit\":\"\",\"rel\":\"userList2\"}");
			}
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("UserInfoServerImpl-->deleteUser():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * 方法说明：分页获取用户信息
	 * 创建时间：2014年12月10日 下午2:27:03  Yin yunfei
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public PageBean<AdminUserModel>  queryAdminUserPageBean2(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String pageSize=request.getParameter("numPerPage");
			String pageNow=request.getParameter("pageNum");
			
            int iPageSize=10;
            
            if(pageSize!=null&&!"".equals(pageSize)){
            	
            	iPageSize = Integer.parseInt(pageSize);
            }
            
            int iPageNow=1;
            
            if(pageNow!=null&&!"".equals(pageNow)){
            	
            	iPageNow = Integer.parseInt(pageNow);
            }
            
			PageBean<AdminUserModel> pageBean =new PageBean<AdminUserModel>(); 
			pageBean.setPageSize(iPageSize);
			pageBean.setPageNow(iPageNow);
			
			PageBean<AdminUserModel> pb = adminUserDao.queryAdminUserPageBean(pageBean);
			
			if(pb!=null&&pb.getResult().size()>0){
				
				return pb;
			}
			
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("UserInfoServerImpl-->queryAdminUserPageBean():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	
	/**
	 * 方法说明：通过ID取用户信息
	 * 创建时间：2014年12月31日 下午3:07:55  Yin yunfei
	 * @throws Exception
	 */
	public AdminUserModel getAdminUserModelById(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			
			String useId=request.getParameter("userId");
            int id= 0;
            if(useId!=null&&!"".equals(useId)){
            	
            	id= Integer.parseInt(useId);
            }
			
			AdminUserModel al=adminUserDao.getAdminUserModelById(id);
			
			return al;
		} catch (Exception e) {
			
			//此处需要加log4j,
			e.printStackTrace();
			Logs.logger_out.error("UserInfoServerImpl-->getAdminUserModelById():"+e.getLocalizedMessage()+"-:-"+e.getStackTrace()+"-:-"+e.getMessage());
		}
		
		return null;
	}
	
	protected JsonConfig getJsonConfig(String[] excludes) {
		
		JsonConfig jsonConfig = new JsonConfig();
		//jsonConfig = new JsonConfig();  
		jsonConfig.setIgnoreDefaultExcludes(false);	
		jsonConfig.setExcludes(excludes);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);		
		
		return jsonConfig;		
	}
	
	@Autowired
	private AdminUserDao adminUserDao;

	public AdminUserDao getAdminUserDao() {
		return adminUserDao;
	}

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	private RedisClient2 redisClient;
	public RedisClient2 getRedisClient() {
		
		return redisClient;
	}

	public void setRedisClient(RedisClient2 redisClient) {
		
		this.redisClient = redisClient;
	}
}
