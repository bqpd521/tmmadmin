<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<form id="pagerForm" method="post" action="user/userList2.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum" name="pageNum" value="${list.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="user/userList2.htm" method="post">
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
				<td>
					帐号：<input type="text" name="keyword" />
				</td>
				<td>
					创建日期：<input type="text" class="date" readonly="true" />
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
			<li><a class="add" href="user/addUserView.htm" target="dialog" rel="addUserForm" height="400"><span>添加用户</span></a></li>
			<li><a class="delete" href="user/deleteUser.htm?userId={sid_user}" target="ajaxTodo" callback="navTabAjaxDone3" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="user/editUserView.htm?userId={sid_user}" target="dialog" rel="editUserForm" height="400"><span>修改用户</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="userList2">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th width="80">帐号</th>
				<th width="120">姓名</th>
				<th width="120">邮箱</th>
				<th width="100">手机号码</th>
				<th width="80">用户部门</th>
				<th width="80" align="center">状态</th>
				<th width="80">角色类型</th>
				<th width="120">添加时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list.result}" var="user">
			<tr target="sid_user" rel="${user.id}">
			    <td><input id="${user.id}" name="userIds" value="${user.id}" type="checkbox"></td>
				<td>${user.userAccount}</td>
				<td>${user.userName}</td>
				<td>${user.userMail}</td>
				<td>${user.userPhone}</td>
				<td>${user.userDept}</td>
				<td>
				<c:if test='${user.userState == "1" }'>
				可用
				</c:if>
				<c:if test='${user.userState == "0" }'>
				<b style="color:red">停用</b>
				</c:if>
				</td>
				<td>
				<c:if test='${user.userType == "1" }'>超级管理员</c:if>
				<c:if test='${user.userType == "2" }'>教材处教材管理员</c:if>
				<c:if test='${user.userType == "3" }'>教保处教材参谋</c:if>
				<c:if test='${user.userType == "4" }'>其它</c:if>
				</td>
				<td>${user.insertTime}</td>
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