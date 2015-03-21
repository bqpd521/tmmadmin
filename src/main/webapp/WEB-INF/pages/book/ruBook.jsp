<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="ruBookForm">
	<form method="post" action="book/addBook2.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, ${jsMenth})">
		<div class="pageFormContent" layoutH="56">
				<input type="hidden" name="bookId" id="bookId" value="${bModel.id}" />
		        <input type="hidden" name="sendFlag" id="sendFlag" value="ru" />
		        <input type="hidden" name="cfrom" id="cfrom" value="${cfrom}" />
			<p>
				<label>教材名称：</label>
				<input id="bookName" maxlength="80"  readonly="readonly" name="bookName" type="text" size="30" value="${bModel.bookName}" />
			</p>
			<p>
				<label>入库数：</label>
				<input id="kcCount" maxlength="80"  class="required number" name="kcCount" type="text" size="30" value="" />
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