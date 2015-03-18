<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="sendAllForm2">
	<form method="post" action="ensure/updateAllSendBook2.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDoneSendOne)">
		<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="ids" id="ids" value="${ids}" />
		<input type="hidden" name="sType" id="sType" value="${sType}" />
		<p>
				<b style="color:red">注意：减库存只能操作一次，请一次都处理完。</b>
			</p>	
			<table class="table" width="100%">
			   <thead>
			      <tr style="width:100%"><td style="width:40%">教材名称</td><td style="width:60%">减库数量</td></tr>
			   </thead>
			   <tbody>
			   <c:forEach items="${list3}" var="c">
			      <tr style="width:100%">
			        <td style="width:40%">
			        <input type="hidden" name="abookId${c.id}" id="abookId${c.id}" value="${c.bookId}" />
			           <input id="bookName${c.id}" maxlength="80" readonly="readonly"  name="bookName${c.id}" type="text" size="30" value="[${c.className}]${c.bookName}" />
			        </td>
			        <td style="width:60%">
			           <input id="sendCount${c.id}" maxlength="3"  class="required number" name="sendCount${c.id}" type="text" size="30" value="" />
			        </td>
			      </tr>
			   </c:forEach>
			   </tbody>
			</table>	
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