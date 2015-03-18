package com.yougou.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.common.CommonParms;
import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.AdminUserModel;
import com.yougou.service.UserInfoServer;

/**
 * 功能说明：后台登录用户操作相关Controller
 * 创建时间：2014-09-24 Yinyunfei
 */
@Controller
public class LoginController {

	/**
	 * 方法说明：后台登录用户验证
	 * 创建时间：2014-09-24 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="login",method=RequestMethod.POST)
	private Map<String, Object> userLogin(HttpServletRequest request,HttpServletResponse response) {
		//mobileApiServer.updateToken("aaaaaaaaaaaaaaabb", "aaaaaaaaaaaaaaa");
		userInfoServer.userLogin(request,response);
		return null;
	}
	
	/**
	 * 方法说明：添加用户
	 * 创建时间：2014-12-15 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="user/addUser")
	private Map<String, Object> addUser(HttpServletRequest request,HttpServletResponse response) {
	
		Map<String, Object> map=userInfoServer.addUser(request, response);
		return null;
	}
	
	/**
	 * 方法说明：后台登录用户验证
	 * 创建时间：2014-09-24 Yinyunfei
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="login/userLogin2",method=RequestMethod.POST)
	private Map<String, Object> userLogin2(HttpServletRequest request,HttpServletResponse response) {
		//mobileApiServer.updateToken("aaaaaaaaaaaaaaabb", "aaaaaaaaaaaaaaa");
		userInfoServer.addUser(request, response);
		return null;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="login/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request,HttpServletResponse response){
		
		//System.out.println("=====Login==========gon!~~~~~~~~~");
		return "login";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response){
		
		//System.out.println("=====index==========gon!~~~~~~~~~");
		HttpSession session = request.getSession(false);
		if (session == null) {
			
			session = request.getSession();
		}
		
		AdminUserModel userModel=(AdminUserModel)session.getAttribute(CommonParms.USER_SESSION_KEY);
		
		if(userModel!=null){
			
			String userName = userModel.getUserName();
			//System.out.println(userName+"============userName===============");
			request.setAttribute("userName", userName);
		}
		
		return "index";
	}
	
	/**
	 * 方法说明：分页查询用户信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/userList",method=RequestMethod.POST)  
	@ResponseBody 
	private Map<String, Object> queryAdminUserBean(HttpServletRequest request,HttpServletResponse response) {
		
		userInfoServer.queryAdminUserPageBean(request, response);
		
		return null;
	}
	
	/**
	 * 方法说明：删除用户
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/deleteUser")  
	@ResponseBody 
	private Map<String, Object> deleteUser(HttpServletRequest request,HttpServletResponse response) {
		
		userInfoServer.deleteUser(request, response);
		
		return null;
	}
	
	/**
	 * 方法说明：修改密码
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/changeUserPsw")  
	@ResponseBody 
	private Map<String, Object> changeUserPsw(HttpServletRequest request,HttpServletResponse response) {
		
		userInfoServer.changePsw(request, response);
		
		return null;
	}
	
	/**
	 * 方法说明：分页查询用户信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/userList2")  
	@ResponseBody 
	private ModelAndView queryAdminUserBean2(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PageBean<AdminUserModel> pb=userInfoServer.queryAdminUserPageBean2(request, response);
		
		request.setAttribute("list", pb);
		return new ModelAndView("user/userList2");// "user/userList2";
	}
	
	/**
	 * 方法说明：添加用户VIEW
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/addUserView")  
	@ResponseBody 
	private ModelAndView addUserView(HttpServletRequest request,HttpServletResponse response) {
		
		return new ModelAndView("user/addUser");// "user/userList2";
	}
	
	/**
	 * 方法说明：编辑用户VIEW
	 * 创建时间：2015年01月01日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/editUserView")  
	@ResponseBody 
	private ModelAndView editUserView(HttpServletRequest request,HttpServletResponse response) {
		
		AdminUserModel al=userInfoServer.getAdminUserModelById(request, response);
		request.setAttribute("user", al);
		return new ModelAndView("user/editUser");// "user/userList2";
	}
	
	/**
	 * 方法说明：编辑用户VIEW
	 * 创建时间：2015年01月01日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/editPswView")  
	@ResponseBody 
	private ModelAndView editPswView(HttpServletRequest request,HttpServletResponse response) {
		
		return new ModelAndView("user/editPsw");// "user/userList2";
	}
	
	/**
	 * 方法说明：分页查询用户信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/1",method=RequestMethod.GET)  
	@ResponseBody 
	private String test1(HttpServletRequest request,HttpServletResponse response,Model model) {
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<AdminUserModel>tempList=new ArrayList<AdminUserModel>();
		
		AdminUserModel al=new AdminUserModel();
		al.setUserAccount("a");
		tempList.add(al);
		
		AdminUserModel al2=new AdminUserModel();
		al.setUserAccount("b");
		tempList.add(al2);
		
		request.setAttribute("userList", tempList);
		//modelMap.put("result", pb);
		return "user/1";// "user/userList2";
	}
	
	/**
	 * 方法说明：分页查询用户信息
	 * 创建时间：2014年12月10日 下午2:43:22  Yin yunfei
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/a",method=RequestMethod.GET)  
	@ResponseBody 
	private ModelAndView test2(HttpServletRequest request,HttpServletResponse response,Model model) {
		
		PageBean<AdminUserModel> pb=userInfoServer.queryAdminUserPageBean2(request, response);
		
		request.setAttribute("userList", pb);
		//modelMap.put("result", pb);
		return new ModelAndView("user/1");// "user/userList2";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "user/nginx")  
	@ResponseBody 
	private Map<String, Object> nginx(HttpServletRequest request,HttpServletResponse response) {
		
		//userInfoServer.deleteUser(request, response);
		String url = request.getParameter("url");
		//System.out.println("=========================="+url);
		
		return null;
	}
	
	@Autowired
	private UserInfoServer userInfoServer;
}