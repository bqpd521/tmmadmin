<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>

<div class="pageContent" id="chuBookForm">
	<form id="chuBookForm2" method="post" action="book/addBookSend.htm" class="pageForm required-validate" onsubmit="return validateCallback(this, ${jsMenth})">
		<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="bookId" id="bookId" value="${bModel.id}" />
		<input type="hidden" name="sendFlag" id="sendFlag" value="chu" />
		<input type="hidden" name="cfrom" id="cfrom" value="${cfrom}" />
		
			<p>
				<label>教材名称：</label>
				<input id="bookName" maxlength="80"  readonly="readonly" name="bookName" type="text" size="30" value="${bModel.bookName}" />
			</p>
			<p>
				<label>剩余库存：</label>
				<input id="kcCount" maxlength="80"  readonly="readonly" name="kcCount" type="text" size="30" value="${bModel.kcCount}" />
			</p>			
			<p>
				<label>领取人：</label>
				<input id="sendUser" maxlength="10"  class="required" name="sendUser" type="text" size="30" value="" />
			</p>	
			<p>
				<label>领取数量：</label>
				<input id="sendCount" maxlength="3" onMouseOut="sumbitChu('${bModel.kcCount}');" class="required number" name="sendCount" type="text" size="30" value="" />注意库存
			</p>	
			<p>
				<label>发放类型：</label>
				<select id="sendType" name="sendType" class="required combox">
					<option value="1">教材参谋</option>
					<option value="2">其它人领取</option>
				</select>
			</p>																								
		</div>
		<div class="formBar">
			<table style=" width: 100%">
			  <tr style=" width: 100%">
			     <td style=" width: 30%"></td>
			     <td>
			     <div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div>
			     </td>
			     <td align="left" style=" width: 50%">
			     <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			     </td>
			  </tr>
			</table>
		</div>
	</form>
	<script>
	    
	     function sumbitChu(kcCountTemp){
	    	 
	 		
	 		var kcCount=kcCountTemp;//$("#kcCount").val();
	 		//alert("kc:"+kcCount);
			var sendCount=$("#sendCount").val();
			
			if(sendCount==""){
				
				return true;
			}
			
			if(Number(sendCount)>Number(kcCount)){
				
				alertMsg.warn('发放数量大于库存数,目前库存剩余:'+kcCount+"本");
				return false;
			}
	     }
	</script>
</div>