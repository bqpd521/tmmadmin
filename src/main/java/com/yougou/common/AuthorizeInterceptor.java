package com.yougou.common;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yougou.model.AdminUserModel;

public class AuthorizeInterceptor  extends HandlerInterceptorAdapter{

	@Override   
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
		
		//请求的url路径
		String url = request.getRequestURL().toString();
		//System.out.println("之前触发--AuthorizeInterceptor-->preHandle():"+url);
		//除登录地址外其它所有地址必须要进行登录
		if(url.indexOf("userLogin")!=-1
				||url.indexOf("login")!=-1){
			
			return true;
		}
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			
			session = request.getSession();
		}
		
		AdminUserModel userModel=(AdminUserModel)session.getAttribute(CommonParms.USER_SESSION_KEY);
		
		if(userModel==null){//跳转到登录页面
			
        	String url2=WebUtils.getBasePath(request);
        	response.sendRedirect(url2+"");
        	
        	return true;
		}
		
		//验证是否登录，如果没有登录则跳转到登录页面
		return true;
	}
}
