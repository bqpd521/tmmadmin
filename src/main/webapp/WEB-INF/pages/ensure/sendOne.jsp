<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="sendOneForm">
	<form method="post" action="ensure/updateOneSendBook.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDoneSendOne)">
		<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="classId" id="classId" value="${bl.id}" />
		<input type="hidden" name="sType" id="sType" value="${sType}" />
			<p>
				<label>教材名称：</label>
				<input type="hidden" name="bookIdone" id="bookIdone" value="${bl.bookId}" />
				<input id="bookName" maxlength="80" readonly="readonly"  name="bookName" type="text" size="30" value="[${bl.className}]${bl.bookName}" />
			</p>	
			<p>
				<label>教领数量：</label>
				<input id="sendCount" maxlength="3"  class="required number" name="sendCount" type="text" size="30" value="" /> 
			</p>
			<p>
				<label>领取人：</label>
				<input id="lqUser" maxlength="80"  class="required" name="lqUser" type="text" size="30" value="" />
			</p>	
			<p>
				<label>是否减库存：</label>
				<select id="sendState" name="sendState" class="required combox">
				   <option value="1">是</option>
				   <option value="0">否</option>
				</select><b style="color:red">注意：如果选择是会减库存。</b>
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