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
<form id="pagerForm" method="post" action="ensure/bzjhList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum"  name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="ensure/bzjhList.htm" method="post">
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
		</ul><label <c:if test='${cc==className1 }'>style="color:red"</c:if>><input <c:if test='${cc==className1 }'>checked="checked"</c:if> onclick="getRadioValue('radioC${xh.count}');" type="radio" name="className" id="radioC${xh.count}" value="${cc}" />${cc}</label>
		-->
		<table class="searchContent">
			<tr>
			    <td style=" width:10%">
			    </td>
				<td>
					教材名称：<input type="text" name="bookName" maxlength="30" value="${bookName}" />
				</td>
				<td align="right">来源：</td>
				<td align="left">
				<input type="hidden" name="bigType3" id="bigType3" value="" />
				<label><input <c:if test='${bigType3 == "" }'>checked="checked"</c:if> onclick="getRadioValue2('radioL0');" type="radio" name="bigType" id="radioL0" value="" />全部</label>
				<label <c:if test='${bigType3 == "1" }'>style="color:red"</c:if>>
				    <input <c:if test='${bigType3 == "1" }'>checked="checked"</c:if> onclick="getRadioValue2('radioL1');" type="radio" name="bigType" id="radioL1" value="1" />
				            外购
				</label>
				<label <c:if test='${bigType3 == "2" }'>style="color:red"</c:if>>
				    <input <c:if test='${bigType3 == "2" }'>checked="checked"</c:if> onclick="getRadioValue2('radioL2');" type="radio" name="bigType" id="radioL2" value="2" />
				          印刷
				</label>	
				<label <c:if test='${bigType3 == "3" }'>style="color:red"</c:if>>
				    <input <c:if test='${bigType3 == "3" }'>checked="checked"</c:if> onclick="getRadioValue2('radioL3');" type="radio" name="bigType" id="radioL3" value="3" />
				    配发
				</label>
				<label <c:if test='${bigType3 == "4" }'>style="color:red"</c:if>>
				    <input <c:if test='${bigType3 == "4" }'>checked="checked"</c:if> onclick="getRadioValue2('radioL4');" type="radio" name="bigType" id="radioL4" value="4" />
				          调拨
				</label>								
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
		<li><a id="aExprotBzjh" onclick="exportExcelBzjh('${bigType3}','<%=basePath%>');" class="icon" href="###" target="dwzExport" targetType="navTab" title="确实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="bzjhList">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="150">教材名称</th>
				<th width="100">出版社</th>
				<th width="70">作者</th>
				<th width="70">出版时间</th>
				<th width="40">来源</th>
				<th width="60">库存数</th>	
				<th width="60">使用数</th>	
				<th width="60">缺货数</th>						
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
				<td><b style="color:blue">${c.queCount}</b></td>
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
	<script>
	    function getRadioValue2(radId){
	    	
	    	var bigType2=$("input[name='bigType']:checked").val();
	    	//alert(bigType2);
	    	$("#bigType3").val(bigType2);
	    }
	</script>