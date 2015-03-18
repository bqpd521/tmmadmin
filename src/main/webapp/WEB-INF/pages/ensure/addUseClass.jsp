<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="addUseClassForm">
	<form method="post" action="ensure/addUseClass.htm?id=${id}&uploadId=${uploadId}&bookId=${bookId}" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDoneBookAddUseClass)">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>使用班级：</label>
				<input id="useClass" maxlength="80"  name="useClass" type="text" size="30" value="${useClass}" />
			</p>
			<p style="color:red">
			<b>注意：多个班级请用中文顿号分隔<span style="color:blue">“、”</span></b>
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