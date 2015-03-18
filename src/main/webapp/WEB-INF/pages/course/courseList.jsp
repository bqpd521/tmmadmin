<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<form id="pagerForm" method="post" action="course/courseList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${list.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="course/courseList.htm" method="post">
	<div class="searchBar">
		<!--<ul class="searchContent">
			<li>
				<label>我的客户：</label>
				<input type="text"/>
			</li>
			<li>
			<select class="combox" name="province">
				<option value="">所有省市</option>
				<option value="北京">北京</option>
				<option value="上海">上海</option>
				<option value="天津">天津</option>
				<option value="重庆">重庆</option>
				<option value="广东">广东</option>
			</select>
			</li>
		</ul>
		-->
		<table class="searchContent">
			<tr>
			<td style=" width:30%"></td>
				<td>
					课程名称：<input type="text" name="courseName" class="required" maxlength="30"  />
				</td>
			</tr>
		</table>
		<br/>
		<!-- <div class="subBar"> -->
		<div class="">
		<table style=" width: 100%">
			<Tr style=" width: 100%">
			   <td style=" width: 30%"></td>
			   <td><div class="buttonActive"><div class="buttonContent"><button type="submit">查 &nbsp; &nbsp;询</button></div></div></td>
			   <td></td>
			</Tr>
		</table>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="course/addCourseView.htm" target="dialog" rel="addCourseForm" height="220"><span>添加课程</span></a></li>
			<!--<li><a class="delete" href="course/deleteCourse.htm?courseId={sid_course}" target="ajaxTodo" callback="navTabAjaxDoneCourseDelete" title="确定要删除吗?"><span>删除</span></a></li>  
			<li><a id="aAllUrl" onclick="getCheckBoxValues();" class="add" rel="sendAllForm" height="550"><span>批量发放</span></a></li>
			-->
			<li><a id="aDellAll" class="delete" onclick="getCheckBoxValuesCourse();"  title="确定要删除吗?"><span>删除bat</span></a></li>
			<li><a class="edit" href="course/editCourseView.htm?courseId={sid_course}" target="dialog" rel="editCourseForm" height="220"><span>修改课程</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="courseList">
		<thead>
			<tr>
			    <th width="22"><input onclick="setCheckBoxValuesC();" id="checkBoxCourse" type="checkbox" group="ids2" class="checkboxCtrl"></th>
				<th width="80">课程名称</th>
				<th width="120">课程编码</th>
				<th width="120">课程状态</th>
				<th width="100">添加时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list.result}" var="c">
			<tr target="sid_course" rel="${c.id}">
			    <td><input id="${c.id}" name="ids_course2" value="${c.id}" type="checkbox"></td>
				<td>${c.courseName}</td>
				<td>${c.courseCode}</td>
				<td>
				<c:if test='${c.courseState == "1" }'>
				可用
				</c:if>
				<c:if test='${c.courseState == "0" }'>
				<b style="color:red">停用</b>
				</c:if>
				</td>
				<td>${c.insertTime}</td>
			</tr>		
			</c:forEach>	
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="30">30</option>
				<option value="40">40</option>
			</select>
			<span>条，共${list.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${list.totalCount}" numPerPage="${list.pageSize}" pageNumShown="${list.totalPages}" currentPage="${list.pageNow}"></div>

	</div>
</div>
<script>

function setCheckBoxValuesC(){
	
	 if($("#checkBoxCourse").attr("checked")){
		 $("[name = ids_course2]:checkbox").attr("checked", true);
	 }else{
    	$("[name= ids_course2]:checkbox").attr("checked", false);
	 }
}

function getCheckBoxValuesCourse(){
	
    var result = "";
    $("[name = ids_course2]:checkbox").each(function () {
        if ($(this).is(":checked")) {
        	
        	if($(this).attr("value")!=""){
        		
        		result+=$(this).attr("value")+",";
        	}
        }
    });
    
    if(result==""){
    	
    	 alertMsg.warn('请勾选要删除的内容！');
    	 return false;
    }
    
    alertMsg.confirm("确定要删除吗", {

        okCall: function(){

        	 // $.post(url, {accountId: accountId}, DWZ.ajaxDone, "json");
        	 var url="course/deleteCourse.htm?courseId="+result;
             ajaxTodo(url,navTabAjaxDoneCourseDelete);
        }
   });
}

</script>