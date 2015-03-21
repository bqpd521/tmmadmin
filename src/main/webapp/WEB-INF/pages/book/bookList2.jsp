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
<form id="pagerForm" method="post" action="book/bookList2.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum" name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" id="pageNum2" name="pageNum2" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="book/bookList2.htm" method="post">
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
					教材名称：<input type="text" name="bookName" maxlength="30" value="${bookName}" />
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
				<td align="right">状态：</td>
				<td align="left">
					<select id="bookState" name="bookState" class="required combox">
					    <option value="">请选择 </option>
						<option value="1">可用 </option>
						<option value="0">停用</option>
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
			<li>
			<!-- 
			<a class="add" href="book/addBookView.htm" target="dialog" rel="addBookForm" height="580"><span>添加教材</span></a></li> -->
			<!--  
			<li><a class="delete" href="book/deleteBook.htm?bookId={sid_book}" target="ajaxTodo" callback="navTabAjaxDoneBookDelete" title="确定要删除吗?"><span>删除</span></a></li>
			-->
			<!--  
			<li><a id="aDellBookAll" class="delete" onclick="getCheckBoxValuesBook();"  title="确定要删除吗?"><span>删除bat</span></a></li>
			<li><a class="edit" href="book/editBookView.htm?bookId={sid_book}" target="dialog" rel="editBookForm" height="580"><span>修改教材</span></a></li>
			--><li><a class="edit" href="book/ruBookView.htm?cfrom=1&bookId={sid_book}" target="dialog" rel="ruBookForm" height="250"><span>入库</span></a></li>
			<li><a class="edit" href="book/tiaoBookView.htm?cfrom=1&bookId={sid_book}" target="dialog" rel="tiaoBookForm" height="250"><span>调库</span></a></li>
			<li><a class="edit" href="book/chuBookView.htm?cfrom=1&bookId={sid_book}" target="dialog" rel="chuBookForm" height="250"><span>出库</span></a></li>
			<li><a id="aExprot2" class="icon" onclick="exportExcelBooList('${bookName}','${bookAuthor}','${bigType}','<%=basePath%>');" href="###" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="bookList">
		<thead>
			<tr>
			    <th width="22"><input onclick="setCheckBoxValuesBook();" id="checkBoxBook" type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="150">教材名称</th>
				<th width="130">所属课程</th>
				<th width="100">出版社</th>
				<th width="70">作者</th>
				<th width="70">出版时间</th>
				<th width="40">大类</th>
				<th width="40">小类</th>		
				<th width="40">种类</th>
				<th width="40">状态</th>	
				<th width="40">保密</th>
				<th width="40">回收</th>	
				<th width="60">库号</th>	
				<th width="60">架号</th>	
				<th width="60">层号</th>	
				<th width="60">库存数</th>												
				<th width="120">添加时间</th>								
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list2.result}" var="c">
			<tr target="sid_book" rel="${c.id}">
			    <td><input id="${c.id}" name="ids_book" value="${c.id}" type="checkbox"></td>
				<td>${c.bookName}</td>
				<td>${c.courseName}</td>
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
					否
					</c:if>
					<c:if test='${c.baoMi == "1" }'>
					<b style="color:blue">是</b>
					</c:if>
				</td>
				<td>
					<c:if test='${c.huiShou == "0" }'>
					否
					</c:if>
					<c:if test='${c.huiShou == "1" }'>
					<b style="color:blue">是</b>
					</c:if>
				</td>		
				<td>${c.kuHao}</td>
				<td>${c.jiaHao}</td>
				<td>${c.chengHao}</td>		
				<td>${c.kcCount}</td>														
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

function setCheckBoxValuesBook(){
	
	 if($("#checkBoxbook").attr("checked")){
		 $("[name = ids_book]:checkbox").attr("checked", true);
	 }else{
    	$("[name= ids_book]:checkbox").attr("checked", false);
	 }
}

function getCheckBoxValuesBook(){
	
    var result = "";
    $("[name = ids_book]:checkbox").each(function () {
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
        	 var url="book/deleteBook.htm?bookIds="+result;
             ajaxTodo(url,navTabAjaxDoneBookDelete);
             //alert(result);
        }
   });
}

</script>