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
<form id="pagerForm" method="post" action="ensure/nowSendList.htm">
	<input type="hidden" name="status" value="">
	<input type="hidden" name="keywords" value="" />
	<input type="hidden" id="pageNum" name="pageNum" value="${list2.pageNow}" />
	<input type="hidden" name="numPerPage" value="${list2.pageSize}" />
	<input type="hidden" name="orderField" value="" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="ensure/nowSendList.htm" method="post">
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
				<td align="left">
				<input type="hidden" name="uploadId" id="uploadId" value="${uploadId}" />
				<c:set var="className1" value="${className1}" />
				<input type="hidden" name="className" id="className" value="${className1}" />
				<label><input <c:if test='${className1==null }'>checked="checked"</c:if> onclick="getRadioValue('radioCa');" type="radio" name="className" id="radioCa}" value="" />全部班级</label>
					<c:forEach items="${classList}" var="cc" varStatus="xh">
					   <label <c:if test='${cc==className1 }'>style="color:red"</c:if>><input <c:if test='${cc==className1 }'>checked="checked"</c:if> onclick="getRadioValue('radioC${xh.count}');" type="radio" name="className" id="radioC${xh.count}" value="${cc}" />${cc}</label>
					</c:forEach>
				</td>		
			</tr>
		</table>
		<br/>
		<!-- <div class="subBar"> -->
		<div class="">
		<table style=" width: 100%">
			<Tr style=" width: 100%">
			   <td style=" width: 30%"></td>
			   <td>
			   <div class="buttonActive"><div class="buttonContent"><button type="submit">查 &nbsp; &nbsp;询</button></div></div>
			   </td>
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
			<li><a id="aAllUrl" onclick="getCheckBoxValues();" class="add" rel="sendAllForm" height="550"><span>批量发放</span></a></li>
			<li><a class="edit" href="ensure/sendOneView.htm?sType=1&id={sid_book}" target="dialog" rel="sendOneForm" height="260"><span>单个发放</span></a></li>
		    <li><a id="aExprotNow" class="icon" onclick="exportExcelFfd('${className1}','<%=basePath%>','now','${uploadId}');" href="###" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		    <!-- <li><a id="aJiankuchun" onclick="getCheckBoxValues2();" class="add" rel="sendAllForm" height="550"><span style="color:red">减库存</span></a></li>
	-->
	    </ul>
	</div>
	<table class="table" width="100%" layoutH="158" rel="nowSendList">
		<thead>
			<tr>
			    <th width="22"><input onclick="setCheckBoxValues();" id="checkBoxo" type="checkbox" group="ids3" class="checkboxCtrl"></th>
				<th width="40">使用班级</th>	
				<th width="150">教材名称</th>
				<th width="70">出版社</th>
				<th width="70">作者</th>
				<th width="70">出版时间</th>
				<th width="60">是否减库存</th>	
				<th width="120">上传时间</th>	
				<th width="40">领取人</th>	
				<th width="120">领取时间</th>											
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list2.result}" var="c">
			<tr target="sid_book" rel="${c.id}">
			    <td><input <c:if test='${c.sendState == "1" }'>disabled="disabled"</c:if> id="input${c.id}" name="checkBoxAll" value="<c:if test='${c.sendState == "1" }'></c:if><c:if test='${c.sendState != "1" }'>${c.id}</c:if>" type="checkbox"></td>
			    <td>${c.className}</td>
				<td>${c.bookName}</td>
				<td>${c.bookPress}</td>
				<td>${c.bookAuthor}</td>
				<td>${c.pressTime}</td>
				<td>
					<c:if test='${c.sendState == "1" }'>
					<b style="color:blue">已减库存</b>
					</c:if>
					<c:if test='${c.sendState == "0" }'>
					<b>未减库存</b>
					</c:if>
				</td>
				<td>${c.insertTime}</td>
				<td>${c.lqUser}</td>
				<td>${c.lqTime}</td>
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
	<script>
	    function getRadioValue(radId){
	    	
	    	var className2=$("input[name='className']:checked").val();
	    	$("#className").val(className2);
	    }
	    
	    function setCheckBoxValues(){
	    	
	    	 if($("#checkBoxo").attr("checked")){
	    		 $("[name = checkBoxAll]:checkbox").attr("checked", true);
	    	 }else{
	 	    	$("[name= checkBoxAll]:checkbox").attr("checked", false);
	    	 }
	    }
	    
	    function getCheckBoxValues(){
	    	
            var result = "";
            $("[name = checkBoxAll]:checkbox").each(function () {
                if ($(this).is(":checked")) {
                	
                	if($(this).attr("value")!=""){
                		
                		result+=$(this).attr("value")+",";
                	}
                }
            });
            
            if(result==""){
            	
            	 alertMsg.warn('请勾选要处理的班级！');
            	 return false;
            }
            //var options={width:410,height:550,max:true,mask:true,mixable:false,minable:true,resizable:true,drawable:true,fresh:true};
            var options={width:510,height:450};
            //$('#aAllUrl').attr('href','ensure/sendAllView.htm?ids='+result); 
            $.pdialog.open('ensure/sendAllView.htm?sType=1&ids='+result, "sendAllForm", "批量发放",options);
	    }
	    
	    function getCheckBoxValues2(){
	    	
            var result = "";
            $("[name = checkBoxAll]:checkbox").each(function () {
                if ($(this).is(":checked")) {
                	
                	if($(this).attr("value")!=""){
                		
                		result+=$(this).attr("value")+",";
                	}
                }
            });
            
            if(result==""){
            	
            	 alertMsg.warn('请勾选要处理的班级！');
            	 return false;
            }
            //var options={width:410,height:550,max:true,mask:true,mixable:false,minable:true,resizable:true,drawable:true,fresh:true};
            //var options={width:510,height:450};
            //$('#aAllUrl').attr('href','ensure/sendAllView.htm?ids='+result); 
            //$.pdialog.open('ensure/sendAllView.htm?sType=1&ids='+result, "sendAllForm", "批量发放",options);
            //alertMsg.confirm("确定要进行减库存吗？<b style=\"color:red\">请仔细确认，这一步很重要</b>"+result, {});
            //var options={width:410,height:550,max:true,mask:true,mixable:false,minable:true,resizable:true,drawable:true,fresh:true};
            var options={width:510,height:450};
            //$('#aAllUrl').attr('href','ensure/sendAllView.htm?ids='+result); 
            //alertMsg.confirm("确定要进行减库存吗？<b style=\"color:red\">请仔细确认，这一步很重要</b>", {
            	
            	$.pdialog.open('ensure/sendAllView2.htm?sType=1&ids='+result, "sendAllForm2", "减库存",options);
            //});
	    }	    
	</script>
</div>