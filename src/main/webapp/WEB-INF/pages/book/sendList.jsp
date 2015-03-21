<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<form id="pagerForm" method="post" action="book/sendList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum" name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="book/sendList.htm" method="post">
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
			    <td style=" width:30%">
			    </td>
				<td>
					教材名称：<input type="text" name="bookName" maxlength="30"  />
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
		<!--  
			<li><a class="add" href="book/addBookView.htm" target="dialog" rel="addBookForm" height="550"><span>添加教材</span></a></li>
			<li><a class="delete" href="book/deleteBook.htm?bookId={sid_book}" target="ajaxTodo" callback="navTabAjaxDoneBookDelete" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="book/editBookView.htm?bookId={sid_book}" target="dialog" rel="editBookForm" height="550"><span>修改教材</span></a></li>
			<li><a class="edit" href="book/ruBookView.htm?bookId={sid_book}" target="dialog" rel="ruBookForm" height="250"><span>入库</span></a></li>
			<li><a class="edit" href="book/tiaoBookView.htm?bookId={sid_book}" target="dialog" rel="tiaoBookForm" height="250"><span>调库</span></a></li>
			<li><a class="edit" href="book/chuBookView.htm?bookId={sid_book}" target="dialog" rel="chuBookForm" height="250"><span>出库</span></a></li>
		--></ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="sendList">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="150">教材名称</th>
				<th width="130">发放人</th>
				<th width="100">领取人</th>
				<th width="70">领取数量</th>
				<th width="60">领取人类别</th>												
				<th width="120">领取时间</th>								
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list2.result}" var="c">
			<tr target="sid_book" rel="${c.id}">
			    <td><input id="${c.id}" name="ids3" value="${c.id}" type="checkbox"></td>
				<td>${c.bookName}</td>
				<td>${c.userId}</td>
				<td>${c.sendUser}</td>
				<td>${c.sendCount}</td>
				<td>
					<c:if test='${c.sendType == "1" }'>
					教材参谋
					</c:if>
					<c:if test='${c.sendType == "2" }'>
					其它领用
					</c:if>
					<c:if test='${c.sendType == "3" }'>
					学员发放
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
			<span>条，共${list2.totalCount}条</span>
		</div>
		
		<div class="pagination" targetType="navTab" totalCount="${list2.totalCount}" numPerPage="${list2.pageSize}" pageNumShown="${list2.totalPages}" currentPage="${list2.pageNow}"></div>

	</div>
</div>