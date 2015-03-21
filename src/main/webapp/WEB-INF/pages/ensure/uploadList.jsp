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
<form id="pagerForm" method="post" action="ensure/uploadList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum" name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="ensure/uploadList.htm" method="post">
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
			    <td style=" width:40%">
			    </td>
				<td>
					上传日期：<input type="text" name="uploadDate" maxlength="30" class="date" dateFmt="yyyy-MM-dd" readonly="true" />
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
			<li><a class="delete" href="ensure/uploadState.htm?uploadId={sid_book}" target="ajaxTodo" callback="navTabAjaxDoneBookUpload" title="确定要修改处理状态吗?"><span>修改处理状态</span></a></li>
			<li><a id="aDellAll2" class="delete" onclick="getCheckBoxValuesUpload();"  title="确定要删除吗?"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="148" rel="uploadList">
		<thead>
			<tr>
			    <th width="22"><input onclick="setCheckBoxValuesUpload();" id="checkBoxUpload" type="checkbox" group="ids333" class="checkboxCtrl"></th>
				<th width="40">编号</th>
				<th width="130">上传帐号</th>
				<th width="100">上传人</th>
				<th width="60">是否处理</th>
				<th width="60">Excel链接</th>	
				<th width="60">上传日期</th>												
				<th width="120">上传时间</th>								
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list2.result}" var="c" varStatus="xh">
			<tr target="sid_book" rel="${c.id}">
			    <td><input id="${c.id}" name="ids_Upload2" value="${c.id}" type="checkbox"></td>
				<td>${xh.count}</td>
				<td>${c.userAccount}</td>
				<td>${c.userName}</td>
				<td>
					<c:if test='${c.uploadState == "0" }'>
					<b style="color:red">未处理</b>
					</c:if>
					<c:if test='${c.uploadState == "1" }'>
					已处理
					</c:if>
				</td>
				<td>
				<a href="<%=basePath %>${c.excelUrl}" target="_blank" style="color:blue" title="点击查看">Excel链接</a>
				</td>
				<td>${c.uploadDate}</td>
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

function setCheckBoxValuesUpload(){
	
	 if($("#checkBoxUpload").attr("checked")){
		 $("[name = ids_Upload2]:checkbox").attr("checked", true);
	 }else{
    	$("[name= ids_Upload]:checkbox").attr("checked", false);
	 }
}

function getCheckBoxValuesUpload(){
	
    var result = "";
    $("[name = ids_Upload2]:checkbox").each(function () {
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

        	 // $.post(url, {accountId: accountId}, DWZ.ajaxDone, "json");  ensure/uploadListDelete
        	 var url="ensure/uploadListDelete.htm?uploadIds="+result;
             ajaxTodo(url,navTabAjaxDoneUploadDelete);
        }
   });
}

</script>