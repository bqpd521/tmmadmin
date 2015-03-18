<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<form id="pagerForm" method="post" action="ensure/ensureList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" name="pageNum" value="1" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			    <td style=" width:40%">
			    </td>
				<td> 
				  <input type="file" name="file" id="file_upload" />
				  <div id="fileQueue" class="fileQueue"></div>
				  <input type="image" src="uploadify/img/upload.jpg" onclick="$('#file_upload').uploadify('upload', '*');"/>
				  <input type="image" src="uploadify/img/cancel.jpg" onclick="$('#file_upload').uploadify('cancel', '*');"/>
				  <input id="files" type="hidden" value ="" name="files" />
				  <div class="divider"></div>
					<div id="fileQueue"></div>   
				</td>
				<td></td>
			</tr>
		</table>
		<br/>
		<!-- <div class="subBar"> -->
		<div class="">
		<table style=" width: 100%">
			<Tr style=" width: 100%">
			   <td style=" width: 30%"></td>
			   <td>
			  
			   <!--
			   <div class="buttonActive"><div class="buttonContent"><button type="submit">上 &nbsp; &nbsp;传</button></div></div>
			     -->
			   </td>
			   <td></td>
			</Tr>
		</table>
		</div>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="book/addBookView.htm" target="dialog" rel="addBookForm" height="420"><span>添加教材</span></a></li>
			<li><a class="delete" href="book/deleteBook.htm?bookId={sid_book}" target="ajaxTodo" callback="navTabAjaxDoneBookDelete" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="book/editBookView.htm?bookId={sid_book}" target="dialog" rel="editBookForm" height="420"><span>修改教材</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="templet/templet.xls" target="dwzExport" targetType="navTab" title="确定要下载模板吗?"><span>下载EXCEL模板</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="bookList">
		<thead>
			<tr>
			    <th width="22"><input type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="150">教材名称</th>
				<th width="130">所属课程</th>
				<th width="100">出版社</th>
				<th width="80">作者</th>
				<th width="80">出版时间</th>
				<th width="60">大类</th>
				<th width="60">小类</th>		
				<th width="80">教材种类</th>
				<th width="40">状态</th>		
				<th width="120">添加时间</th>								
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list2.result}" var="c">
			<tr target="sid_book" rel="${c.id}">
			    <td><input id="${c.id}" name="ids3" value="${c.id}" type="checkbox"></td>
				<td>${c.bookName}</td>
				<td>${c.courseId}</td>
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
	    <script type="text/javascript">  
	    $(document).ready(function() {  
            $("#file_upload").uploadify({  
                    'buttonText' : '请选择Excel文件',  
                    'height' : 25,  
                    'swf' : 'uploadify/scripts/uploadify.swf',  
                    'uploader' : 'book/uploadExcel.htm',  
                    'width' : 120,  
                    'auto':false,  
                    'fileObjName'   : 'file', 
                    'fileTypeExts'  : '*.xls',
                    'fileQueue':"fileQueue",
                    'onComplete':'uploadSuccess',                    
                });  
        });  
	    
	    function uploadSuccess(){
	    	
	    	alert("dddddddd");
	    }
    </script> 
</div>