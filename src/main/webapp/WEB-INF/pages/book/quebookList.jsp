<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<form id="pagerForm" method="post" action="book/quebookList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum"  name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" id="pageNum3"  name="pageNum3" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="book/quebookList.htm" method="post">
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
			    <td style=" width:10%">
			    </td>
				<td>
					教材名称：<input type="text" name="bookName" maxlength="30"  value="${bookName}" />
				</td>
				<td>
					作者：<input type="text" name="bookAuthor" maxlength="30" value="${bookAuthor}" />
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
				<td align="right">库存数量：</td>
				<td align="left">
					<select id="kcCount" name="kcCount" class="required combox">
					    <option value="">请选择 </option>
						<option value="5">库存小于等于5 </option>
						<option value="10">库存小于等于10 </option>
						<option value="15">库存小于等于15 </option>
						<option value="20">库存小于等于20 </option>
						<option value="25">库存小于等于25 </option>
						<option value="30">库存小于等于30 </option>
						<option value="35">库存小于等于35 </option>
						<option value="40">库存小于等于40 </option>
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
			<li><a class="edit" href="book/ruBookView.htm?cfrom=2&bookId={sid_book}" target="dialog" rel="ruBookForm" height="250"><span>入库</span></a></li>
			<li><a class="edit" href="book/tiaoBookView.htm?cfrom=2&bookId={sid_book}" target="dialog" rel="tiaoBookForm" height="250"><span>调库</span></a></li>
			<li><a class="edit" href="book/chuBookView.htm?cfrom=2&bookId={sid_book}" target="dialog" rel="chuBookForm" height="250"><span>出库</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="quebookList">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="120">教材名称</th>
				<th width="80">出版社</th>
				<th width="70">作者</th>
				<th width="70">出版时间</th>
				<th width="40">大类</th>
				<th width="80">库号</th>	
				<th width="80">架号</th>	
				<th width="80">层号</th>	
				<th width="40">剩余库存</th>		
				<th width="40"><b>缺库数</b></th>											
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
				<td>${c.kuHao}</td>
				<td>${c.jiaHao}</td>
				<td>${c.chengHao}</td>		
				<td>${c.kcCount}</td>
				<td <c:if test='${c.queCount == "-1" }'>style="color:blue"</c:if><c:if test='${c.queCount != "-1" }'>style="color:red"</c:if> >
				<c:if test='${c.kcCount <= 5 }'>
					<c:if test='${c.queCount == "-1" }'><span style="color:red">预警</span></c:if>
					<c:if test='${c.queCount != "-1" }'>${c.queCount}</c:if>				
				</c:if>
				<c:if test='${c.kcCount > 5 }'>
					<c:if test='${c.queCount == "-1" }'>不缺</c:if>
					<c:if test='${c.queCount != "-1" }'>${c.queCount}</c:if>				
				</c:if>
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