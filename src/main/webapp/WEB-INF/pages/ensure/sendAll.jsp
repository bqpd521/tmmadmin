<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="sendAllForm">
	<form method="post" action="ensure/updateAllSendBook.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDoneSendOne)">
		<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="ids" id="ids" value="${ids}" />
		<input type="hidden" name="sType" id="sType" value="${sType}" />
			<p>
				<label>领取人：</label>
				<input id="lqUser" maxlength="80"  class="required" name="lqUser" type="text" size="30" value="" />
			</p>	
			<table class="table" width="99%">
			   <thead>
			      <tr style="width:100%"><td style="width:30%"><b>教材名称</b></td><td style="width:30%"><b>领取数量</b></td><td style="width:50%"><b>是否减库存</b></td></tr>
			   </thead>
			   <tbody>
			   <c:forEach items="${list3}" var="c">
			      <tr style="width:100%">
			        <td style="width:30%">
			        <input type="hidden" name="abookId${c.id}" id="abookId${c.id}" value="${c.bookId}" />
			        <input id="bookName${c.id}" maxlength="80" readonly="readonly"  name="bookName${c.id}" type="text" size="30" value="[${c.className}]${c.bookName}" /></td>
			        <td style="width:20%"><input id="sendCount${c.id}" maxlength="3"  class="required number" name="sendCount${c.id}" type="text" size="30" value="" /></td>
			        <td style="width:50%">
			           	<select id="sendState${c.id}" name="sendState${c.id}" class="required combox">
						   <option value="1">是</option>
						   <option value="0">否</option>
						</select><b style="color:red">注意：如果选择是会减库存。</b>
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