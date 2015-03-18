<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="huishouSearchForm">
	<form method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDoneBookXiaoHui)">
		<div class="pageFormContent" layoutH="56">
			<table class="table" width="99%">
				<thead>
					<tr>
						<th width="60">操作人</th>
						<th width="40">课程ID</th>
						<th width="100">课程名称</th>
						<th width="60">销毁数量</th>
						<th width="110">销毁时间</th>								
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list3}" var="c">
					<tr style="height:28px">
						<td>${c.userAccount}</td>
						<td>${c.bookId}</td>
						<td>${c.bookName}</td>
						<td>${c.destroyCount}</td>
						<td>${c.insertTime}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>	 
		</div>
		<div class="formBar">
			<table style=" width: 100%">
			  <tr style=" width: 100%">
			     <td style=" width: 50%"></td>
			     <td align="left" style=" width: 50%">
			     <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			     </td>
			  </tr>
			</table>
		</div>
	</form>
</div>