<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="editUserForm">
	<form method="post" action="user/addUser.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone2)">
		<div class="pageFormContent" layoutH="56">
			<p>
			<input type="hidden" name="userId" id="userId" value="${user.id}" />
				<label>登录帐号：</label>
				<input id="userAccount" maxlength="20" readonly="readonly" name="userAccount" type="text" size="30" value="${user.userAccount}" />
			</p>
			<!-- 
			<p>
				<label>登录密码：</label>
				<input id="userPsw" name="userPsw" minlength="6" maxlength="10" class="required" type="password" size="30" value="123456" alt="请输入登录密码"/>
			</p>
			<p>
				<label>确认密码：</label>
				<input id="userPsw1" name="userPsw1" minlength="6"  maxlength="10" class="required" type="password" size="30" value="123456" alt="请输入确认密码"/>
			</p> -->
			<p>
				<label>用户姓名：</label>
				<input id="userName" name="userName" class="required" type="text" size="30" value="${user.userName}" alt=""/>
			</p>						
			<p>
				<label>用户部门：</label>
				<input id="userDept" name="userDept" type="text" size="30" value="${user.userDept}" alt=""/>
			</p>
			<p>
				<label>用户邮箱：</label>
				<input id="userMail" name="userMail" maxlength="50" type="text" size="30" value="${user.userMail}" alt=""/>
			</p>
			<p>
				<label>手机号码：</label>
				<input id="userPhone" name="userPhone" maxlength="11" type="text" size="30" value="${user.userPhone}" alt="请输入手机号码"/>
			</p>
			<p>
				<label>用户类型：</label>
				<select id="userType" name="userType" class="required combox">
					<option value="4" <c:if test='${user.userType == "4" }'>selected="selected"</c:if>>其它</option>
					<option value="1" <c:if test='${user.userType == "1" }'>selected="selected"</c:if>>超级管理员</option>
					<option value="2" <c:if test='${user.userType == "2" }'>selected="selected"</c:if>>教材处教材管理员</option>
					<option value="3" <c:if test='${user.userType == "3" }'>selected="selected"</c:if>>教保处教材参谋</option>
				</select>
			</p>
			<p>
				<label>用户状态：</label>
				<select id="userState" name="userState" class="required combox">
					<option value="1">可用 </option>
					<option value="0">停用</option>
				</select>
			</p>			
		</div>
		<div class="formBar">
			<table style=" width: 100%">
			  <tr style=" width: 100%">
			     <td style=" width: 30%"></td>
			     <td>
			     <div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
			     </td>
			     <td align="left" style=" width: 50%">
			     <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			     </td>
			  </tr>
			</table>
		</div>
	</form>
</div>