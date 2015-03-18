package com.yougou.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.yougou.dao.hibernate.PageBean;
import com.yougou.model.AdminUserModel;

@Transactional
public interface UserInfoServer {

	public Map<String, Object> userLogin(HttpServletRequest request,HttpServletResponse response);
	
	public Map<String, Object> addUser(HttpServletRequest request,HttpServletResponse response);
	
	public boolean  queryAdminUserPageBean(HttpServletRequest request,HttpServletResponse response);

	public PageBean<AdminUserModel>  queryAdminUserPageBean2(HttpServletRequest request,HttpServletResponse response);
	
	public boolean  deleteUser(HttpServletRequest request,HttpServletResponse response);
	
	public AdminUserModel getAdminUserModelById(HttpServletRequest request,HttpServletResponse response) ;
	
	public boolean  changePsw(HttpServletRequest request,HttpServletResponse response);
}
