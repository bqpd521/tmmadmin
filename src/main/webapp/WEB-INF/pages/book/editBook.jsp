<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%

%>
<div class="pageContent" id="editBookForm">
	<form method="post" action="book/addBook.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDoneBook)">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="bookId" id="bookId" value="${bModel.id}" />
			<input type="hidden" name="destroyCount" id="destroyCount" value="${bModel.destroyCount}" />
			<p>
				<label>教材名称：</label>
				<input id="bookName" maxlength="80"  class="required" name="bookName" type="text" size="30" value="${bModel.bookName}" />
			</p>
			<p>
				<label>教材编码：</label>
				<input id="bookCode" maxlength="80"  class="required" name="bookCode" type="text" size="10" value="${bModel.bookCode}" />
			</p>				
			<p>
				<label>所属课程：</label>
				<c:set var="courseId2" scope="session" value="${bModel.courseId}" />
				<select id="courseId" name="courseId" class="required combox">
				<option value="-">-===无===-</option>
				<c:forEach items="${cList}" var="c">
					<option <c:if test='${c.id==courseId2 }'>selected="selected"</c:if> value="${c.id}">
					     <c:choose> 
						     <c:when test="${fn:length(c.courseName) > 15}">
						      <c:out value="${fn:substring(c.courseName, 0, 15)}......" />
						     </c:when>
						     <c:otherwise>
						      <c:out value="${c.courseName}" />
						     </c:otherwise>
						 </c:choose>
						 
					</option>
				</c:forEach>
				</select>
			</p>
			<p>
				<label>出版社：</label>
				<input id="bookPress" maxlength="80"  class="required" name="bookPress" type="text" size="30" value="${bModel.bookPress}" />
			</p>
			<p>
				<label>出版时间：</label>
				<input id="pressTime" maxlength="80"  class="required" name="pressTime" type="text" size="30" value="${bModel.pressTime}" />
			</p>			
			<p>
				<label>作者：</label>
				<input id="bookAuthor" name="bookAuthor"maxlength="10" class="required" type="text" size="30" value="${bModel.bookAuthor}" />
			</p>
			<p>
				<label>大类：</label>
				<select id="bigType" name="bigType" class="required combox">
					<option <c:if test='${bModel.bigType == "1" }'>selected="selected"</c:if> value="1">外购</option>
					<option <c:if test='${bModel.bigType == "2" }'>selected="selected"</c:if> value="2">印刷</option>
					<option <c:if test='${bModel.bigType == "3" }'>selected="selected"</c:if> value="3">配发</option>
					<option <c:if test='${bModel.bigType == "4" }'>selected="selected"</c:if> value="4">调拨</option>
					<option <c:if test='${bModel.bigType == "5" }'>selected="selected"</c:if> value="5">其它</option>					
				</select>
			</p>
			<p>
				<label>小类：</label>
				<select id="smallType" name="smallType" class="required combox">
					<option <c:if test='${bModel.smallType == "0" }'>selected="selected"</c:if> value="0">无类别 </option>
					<option <c:if test='${bModel.smallType == "1" }'>selected="selected"</c:if> value="1">地方采购</option>
					<option <c:if test='${bModel.smallType == "2" }'>selected="selected"</c:if> value="2">军内采购</option>
				</select>
			</p>
			<p>
				<label>教材状态：</label>
				<select id="bookState" name="bookState" class="required combox">
					<option <c:if test='${bModel.bookState == "1" }'>selected="selected"</c:if> value="1">可用 </option>
					<option <c:if test='${bModel.bookState == "0" }'>selected="selected"</c:if> value="0">停用</option>
				</select>
			</p>	
			<p>
				<label>教材种类：</label>
				<select id="bookType" name="bookType" class="required combox">
					<option <c:if test='${bModel.bookType == "1" }'>selected="selected"</c:if> value="1">教材</option>
					<option <c:if test='${bModel.bookType == "2" }'>selected="selected"</c:if>  value="2">教学用图</option>
				</select>
			</p>
			<p>
				<label>是否保密：</label>
				<select id="baoMi" name="baoMi" class="required combox">
					<option value="0">不保密</option>
					<option <c:if test='${bModel.baoMi == "1" }'>selected="selected"</c:if> value="1">保密</option>
				</select>
			</p>
			<p>
				<label>是否回收：</label>
				<select id="huiShou" name="huiShou" class="required combox">
					<option value="0">不回收</option>
					<option <c:if test='${bModel.huiShou == "1" }'>selected="selected"</c:if> value="1">回收</option>
				</select>
			</p>	
			<p>
				<label>库号：</label>
				<input id="kuHao" maxlength="80"  name="kuHao" type="text" size="30" value="${bModel.kuHao}" />
			</p>
			<p>
				<label>架号：</label>
				<input id="jiaHao" maxlength="80"  name="jiaHao" type="text" size="30" value="${bModel.jiaHao}" />
			</p>
			<p>
				<label>层号：</label>
				<input id="chengHao" maxlength="80" name="chengHao" type="text" size="30" value="${bModel.chengHao}" />
			</p>	
			<p>
				<label>库存：</label>
				<input id="kcCount" maxlength="80" class="required number" name="kcCount" type="text" size="30" value="${bModel.kcCount}" />
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