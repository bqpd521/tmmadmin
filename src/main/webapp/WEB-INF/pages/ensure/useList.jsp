<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<form id="pagerForm" method="post" action="ensure/useList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum" name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="ensure/useList.htm" method="post">
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
					教材名称：<input type="text" name="bookName" maxlength="30" value="${bookName}" />
				</td>
				<td align="right">大类：</td>
				<td align="left">
					<select id="bigType" name="bigType" class="required combox">
					<option value="">请选择</option>
					<option <c:if test='${bigType=="1"}'>selected="selected"</c:if> value="1">外购</option>
					<option <c:if test='${bigType=="2"}'>selected="selected"</c:if> value="2">印刷</option>
					<option <c:if test='${bigType=="3"}'>selected="selected"</c:if> value="3">配发</option>
					<option <c:if test='${bigType=="4"}'>selected="selected"</c:if> value="4">调拨</option>
				</select>
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
			<li><a id="aExprotUse" class="icon" onclick="exportExcelUseList('<%=basePath%>');" href="###" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			<li><a class="edit" href="ensure/addUseClassView.htm?id={sid_book}" target="dialog" rel="addUseClassForm" height="250"><span>使用班级</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="useList">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="150">教材名称</th>
				<th width="100">出版社</th>
				<th width="70">作者</th>
				<th width="50">出版时间</th>
				<th width="40">来源</th>
				<th width="40">库存数</th>	
				<th width="40">使用数</th>	
				<th width="40">缺货数</th>	
				<th width="40">状态</th>	
				<th width="140">使用班级</th>					
				<th width="120">添加时间</th>							
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list2.result}" var="c">
			<tr target="sid_book" rel="${c.id}">
			    <td><input id="cBox${c.id}" name="ids3" value="${c.id}" type="checkbox"></td>
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
				<td>${c.kcCount}
					<c:if test='${c.kcCount == "" }'>
					0
					</c:if>
				</td>
				<td>${c.userCount}</td>
				<td>
				<c:if test='${c.queCount == "0" }'>
				 有货
				</c:if>
				<c:if test='${c.queCount != "0" }'>
				 <b style="color:red">${c.queCount}</b>
				</c:if>				
				</td>
				<td>
					<c:if test='${c.sendState == "0" }'>
					<div style="color:blue">未处理</div>
					</c:if>
					<c:if test='${c.sendState == "1" }'>
					已处理
					</c:if>					
				</td>
				<td title="${c.userClass}">${c.userClass}</td>
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