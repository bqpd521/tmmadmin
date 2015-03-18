<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="editCourseForm">
	<form method="post" action="course/addCourse.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDoneCourse)">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="courseId" id="courseId" value="${cr.id}" />
			<p>
				<label>课程名称：</label>
				<input id="courseName" maxlength="80"  class="required" name="courseName" type="text" size="30" value="${cr.courseName}" />
			</p>
			<p>
				<label>课程编码：</label>
				<input id="courseCode" name="courseCode"maxlength="10" class="required" type="text" size="30" value="${cr.courseCode}" />
			</p>
			<p>
				<label>课程状态：</label>
				<select id="courseState" name="courseState" class="required combox">
					<option value="1">可用 </option>
					<option
					<c:if test='${cr.courseState == "0" }'>
					selected="selected"
					</c:if>
					 value="0">停用</option>
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