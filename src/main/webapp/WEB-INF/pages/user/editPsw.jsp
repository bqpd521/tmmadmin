<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="editPswForm">
	<form method="post" action="user/changeUserPsw.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxChangePsw)">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>登录密码：</label>
				<input id="userPsw" name="userPsw" minlength="6" maxlength="10" class="required" type="password" size="30" value="" alt="请输入登录密码"/>
			</p>
			<p>
				<label>确认密码：</label>
				<input id="userPsw1" name="userPsw1" minlength="6"  maxlength="10" class="required" type="password" size="30" value="" alt="请输入确认密码"/>
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