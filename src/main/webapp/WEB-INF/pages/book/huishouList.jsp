<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<form id="pagerForm" method="post" action="book/huishouList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum"  name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="book/huishouList.htm" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td style=" width:10%">
			    </td>
				<td>
					教材名称：<input type="text" name="bookName" maxlength="30"  />
				</td>
				<td>
					作者：<input type="text" name="bookAuthor" maxlength="30"  />
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
			<li><a class="edit" href="book/huishouBookView.htm?bookId={sid_book}" target="dialog" rel="huishouBookForm2" height="250"><span>回收</span></a></li>
			<li><a class="edit" href="book/huishouSearchView.htm?bookId={sid_book}" target="dialog" rel="huishouSearchForm" height="350"><span>回收查询</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="huishouList">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="150">教材名称</th>
				<th width="100">出版社</th>
				<th width="70">作者</th>
				<th width="70">出版时间</th>
				<th width="40">大类</th>
				<th width="40">小类</th>		
				<th width="40">种类</th>
				<th width="40">状态</th>	
				<th width="60">是否保密</th>
				<th width="60">销毁数量</th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list2.result}" var="c">
			<tr target="sid_book" rel="${c.id}">
			    <td><input id="${c.id}" name="ids3" value="${c.id}" type="checkbox"></td>
				<td>${c.bookName}</td>
				<td>${c.bookPress}</td>
				<td>${c.bookAuthor}</td>
				<td>${c.pressTime}</td>
				<td>
					<c:if test='${c.bigType == "1" }'>
					外购
					</c:if>
					<c:if test='${c.bigType == "2" }'>
					印刷
					</c:if>
					<c:if test='${c.bigType == "3" }'>
					配发
					</c:if>
					<c:if test='${c.bigType == "4" }'>
					调拨
					</c:if>				
				</td>
				<td>
					<c:if test='${c.smallType == "1" }'>
					地方
					</c:if>
					<c:if test='${c.smallType == "2" }'>
					军内
					</c:if>
					<c:if test='${c.smallType == "0" }'>
					无
					</c:if>
				</td>		
				<td>
					<c:if test='${c.bookType == "1" }'>
					教材
					</c:if>
					<c:if test='${c.bookType == "2" }'>
					用图
					</c:if>
				</td>
				<td>
					<c:if test='${c.bookState == "1" }'>
					可用
					</c:if>
					<c:if test='${c.bookState == "0" }'>
					<b style="color:red">停用</b>
					</c:if>
				</td>
				<td>
					<c:if test='${c.baoMi == "0" }'>
					不保密
					</c:if>
					<c:if test='${c.baoMi == "1" }'>
					<b style="color:blue">保密</b>
					</c:if>
				</td>
				<td>
				  ${c.destroyCount}
				</td>		
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