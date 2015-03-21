/**************************************
 * 用途：处理业务逻辑的js方法
 * 时间：2014-12-08
 *       尹云飞
 **************************************/

/**
 * 方法说明：添加用户主方法
 * 页    面：addUser.html
 * 创建时间：2014-12-08 yinyunfei
 */
function validateAddUserCallback(form, callback){
	
    var $form = $(form);  
    //验证表单如果有非法字段 返回  
	if (!$form.valid()) {  
	    return false;  
	}  
	
	var password=$("#userPsw").val();
	var password1=$("#userPsw1").val();
	if(password!=password1){
		//alertMsg.correct('两次密码不一致！');
		alertMsg.warn('登录密码和确认密码，两次密码不一致!!!');
		$("#userPsw").focus();
		return false;  
	}
	    //Ajax向后台提交数据  
	$.ajax({  
	    type: form.method || 'POST',  
	    url:$form.attr("action"),  
	    
	    //获取表单的内容数据  
	            data:$form.serializeArray(),  
	    dataType:"json",  
	    cache: false,  
	    //执行回调函数  
	    success: callbackMeth2 || DWZ.ajaxDone,  
	    error: DWZ.ajaxError  
	});  
    //保证不会通过普通表单提交  
    return false;  
}

function callbackMeth2(){
	
	alertMsg.correct("添加成功！");
	$.pdialog.close("addUserForm");
	setMenth3();
}

function setMenth4(){
	
	alertMsg.correct("获取列表方法启动！");
}
function setMenth3(){
	
	//alertMsg.correct("获取列表方法启动！");
	var str="";
	var postUrl="user/userList.htm";
	$.post(postUrl,{"pageSize":"10","pageNow":"1"},function(data){
		//alert("d");
		//$('#tbUserList').datagrid('loadData', { total: 0, rows: [] }); 
		if(data!=null){
            //alert(data);
            //lastPage=(data.results[0].totalPages)==0?1:data.results[0].totalPages;
            //totalPages=(data.results[0].totalPages)==0?1:data.results[0].totalPages;
            //pageNow=(data.results[0].pageNow)==0?1:data.results[0].pageNow;		
            //$('#tbUserList').remove();
            
			$.each(data.results[0].result,function(i,item){
				
				str+="<tr target=\"sid_user\" rel=\""+i+"\">";
				str+="<td  width=\"22\"><input name=\"ids\" value=\"xxx\" type=\"checkbox\"></td>";
				str+="<td width=\"100\">"+item.userAccount+"</td>";
				str+="<td width=\"70\">"+item.userName+"</td>";
				str+="<td width=\"140\">"+item.userMail+"</td>";
				str+="<td width=\"80\">"+item.userPhone+"</td>";
				str+="<td width=\"100\">"+item.userDept+"</td>";
				str+="<td width=\"60\">"+item.userType+"</td>";
				str+="<td width=\"80\">"+item.userState+"</td>";
				str+="<td width=\"120\">"+item.insertTime+"</td>";
				str+="<td width=\"80\"><a style=\"color:blue\" onclick=\"deleteUser('"+item.id+"');\" target=\"navTab\" rel=\"userList\">删除</a> <a href=\"###\" onclick=\"editUser('"+item.id+"');\">修改</a></td>";
				str+="</tr>";
			});
			//$('#tbUserList').datagrid('appendRow',{itemid:"",productName:"<input style=\"width:100px;height:30px\" type=\"button\" onclick=\"getCheckValue();\" value=\"添加到精品库\" />",imgUrl:"",productPrice:"",productId:"",recDate:""});
			//alert(str);
			$("#tbUserList tr:gt(0)").remove();
			$("#tbUserList").empty();
			$("#tbUserList").html();
			$("#tbUserList").append(str);
        }

	},"json");
}

//导出发放单
function exportExcelFfd(className,path,types,upload){
	
	//alertMsg.correct("获取列表方法启动！");
	var str="";
	var postUrl="ensure/exportExcelFfd.htm";
	$.post(postUrl,{"className":className,"uploadId":upload,"who":types},function(data){
		//alert("go"); 
		//$('#tbUserList').datagrid('loadData', { total: 0, rows: [] }); 
		if(data!=null){

			var url=path+data.results[0];
			//alert(url); 
			if(types=="hi"){
				
				$('#aExprotHi').attr('href',url); 
			}else if(types=="now"){
				
				$('#aExprotNow').attr('href',url); 
			}
        }

	},"json");
}

//导出保障计划 
function exportExcelBzjh(bigType,path){
	
	//alertMsg.correct("获取列表方法启动！");
	var str="";
	var postUrl="ensure/exportExcelBzjh.htm";
	$.post(postUrl,{"bigType":bigType},function(data){
		
		//$('#tbUserList').datagrid('loadData', { total: 0, rows: [] }); 
		if(data!=null){

			var url=path+data.results[0];
			//alert(url); 
			$('#aExprotBzjh').attr('href',url); 
        }

	},"json");
}

//导出教材信息 boolist
function exportExcelBooList(bookName,autor,bigType,path){
	
	//alertMsg.correct("获取列表方法启动！");
	var str="";
	var postUrl="book/exportExcelBooList.htm";
	$.post(postUrl,{"bookName":bookName,"bookAuthor":autor,"bigType":bigType},function(data){
		
		//$('#tbUserList').datagrid('loadData', { total: 0, rows: [] }); 
		if(data!=null){

			var url=path+data.results[0];
			//alert(url); 
			$('#aExprot2').attr('href',url); 
        }

	},"json");
}

//导出教材信息 boolist
function exportExcelUseList(path){
	//alertMsg.correct("获取列表方法启动！");
	var str="";
	var postUrl="ensure/exportExcelUseList.htm";
	$.post(postUrl,{"bookName":""},function(data){
		
		//$('#tbUserList').datagrid('loadData', { total: 0, rows: [] }); 
		if(data!=null){

			var url=path+data.results[0];
			//alert(url); 
			$('#aExprotUse').attr('href',url); 
        }

	},"json");
}

//删除用户
function deleteUser(userId){
	
	alertMsg.confirm("确定要删除吗？", {

        okCall: function(){

    	    //Ajax向后台提交数据  
        	$.ajax({  
        	    type:'POST',  
        	    url:"user/deleteUser.htm?userId="+userId,  
        	    //获取表单的内容数据  
        	    data:{},  
        	    dataType:"json",  
        	    cache: true,  
        	    //执行回调函数  
        	    success: deleteCallback(userId) || DWZ.ajaxDone,  
        	    error: DWZ.ajaxError  
        	});  
        }
	});
}

function deleteCallback(userId){
	
	alertMsg.correct("删除成功！");
	setMenth3();
}

function editUser(userId){
	
	$("#editA").click();
	$("#hidUserId").val(userId);
}

function editSave(){
	
	var userId=$("#hidUserId").val();
	alertMsg.correct("userId:"+userId);
}

/**
 * 方法说明：添加用户主方法
 * 页    面：addUser.html
 * 创建时间：2014-12-08 yinyunfei
 */
function validateEditserCallback(form, callback){
	$.ajaxSettings.global=false;
    var $form = $(form);  
    //验证表单如果有非法字段 返回  
	if (!$form.valid()) {  
	    return false;  
	}  
	
	var password=$("#userPsw").val();
	var password1=$("#userPsw1").val();
	if(password!=password1){
		//alertMsg.correct('两次密码不一致！');
		alertMsg.warn('登录密码和确认密码，两次密码不一致!!!');
		$("#userPsw").focus();
		return false;  
	}
	
	var userId=$("#hidUserId").val();
	//alert(userId);
	//return false;
	    //Ajax向后台提交数据  
	$.ajax({  
	    type: form.method || 'POST',  
	    url:$form.attr("action")+"?id="+userId,  
	    
	    //获取表单的内容数据  
	            data:$form.serializeArray(),  
	    dataType:"json",  
	    cache: false,  
	    //执行回调函数  
	    success: callbackMethEdit || DWZ.ajaxDone,  
	    error: DWZ.ajaxError  
	});  
    //保证不会通过普通表单提交  
    return false;  
}

function callbackMethEdit(){
	
	alertMsg.correct("修改成功！");
	//$.pdialog.close("editUserForm2");
	
	$("#btnList").click();
	$.ajaxSettings.global=true;
}

function navTabAjaxDone2(json){
	var pageNum=document.getElementById("pageNum").value;
	var forwardurl ="user/userList2.htm?pageNum="+pageNum;
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("addUserForm");
	}else {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("editUserForm");
	}
	
	
	navTab.reload(forwardurl, "", json.navTabId);
}

//修改密码 
function navTabAjaxChangePsw(json){
	
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("editPswForm");
	}
}

//添加成功后的提示并刷新列表
function navTabAjaxDone3(json){
	var pageNum=document.getElementById("pageNum").value;
	var forwardurl ="user/userList2.htm?pageNum="+pageNum;
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	navTab.reload(forwardurl, "", json.navTabId);
}

//用户删除提示
function dialogAjaxDoneThis(json){
	var pageNum=document.getElementById("pageNum").value;
	var forwardurl ="user/userList2.htm?pageNum="+pageNum;
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	$.pdialog.close("addUserForm");
	navTab.reload(forwardurl, "", json.navTabId);
} 

//课程添加成功回调  
function navTabAjaxDoneCourse(json){
	
	var pageNum=document.getElementById("pageNumCourse").value;
	var forwardurl ="course/courseList.htm?pageNum="+pageNum;
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	//alert("dddd:"+json.statusCode);
	//课程名称重复
	if (json.statusCode == 400) {
	       alertMsg.warn(json.message);
	       return null;
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("addCourseForm");
	}else{
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("editCourseForm");
	}
	
	
	navTab.reload(forwardurl, "", json.navTabId);
}
//删除课程提示
function navTabAjaxDoneCourseDelete(json){
	var forwardurl ="course/courseList.htm";
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	navTab.reload(forwardurl, "", json.navTabId);
}

//删除上传眆录
function navTabAjaxDoneUploadDelete(json){
	var forwardurl ="ensure/uploadList.htm";
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	navTab.reload(forwardurl, "", json.navTabId);
}

//教材添加成功回调  
function navTabAjaxDoneBook(json){
	
	var pageNum=document.getElementById("pageNum0").value;
	//alert("pageNum:"+pageNum);
	var forwardurl ="book/bookList.htm?pageNum="+pageNum;
	//forwardurl = forwardurl;
	
	//alert("json.cfrom:"+json.cfrom);
	if(json.cfrom=="2"){
		
		forwardurl ="book/quebookList.htm?pageNum="+pageNum;
	}
	
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	//教材名称重复
	if (json.statusCode == 400) {
	       alertMsg.warn(json.message);
	       return null;
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("addBookForm");
	}else  if (json.addOrEdit == "0"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("editBookForm");
	}else if (json.addOrEdit == "3"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("chuBookForm");
	}else if (json.addOrEdit == "4"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("ruBookForm");
	}else if (json.addOrEdit == "5"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("tiaoBookForm");
	}
	
	navTab.reload(forwardurl, "", json.navTabId);
}

//管理功能--库存明细
function navTabAjaxDoneBook2(json){
	var forwardurl ="book/bookList2.htm";
	//forwardurl = forwardurl;
	var pageNum=document.getElementById("pageNum2").value;
	//alert("pageNum2:"+pageNum);
	if(json.cfrom=="2"){
		
		forwardurl ="book/quebookList.htm?pageNum="+pageNum;
	}
	
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	//教材名称重复
	if (json.statusCode == 400) {
	       alertMsg.warn(json.message);
	       return null;
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("addBookForm");
	}else  if (json.addOrEdit == "0"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("editBookForm");
	}else if (json.addOrEdit == "3"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("chuBookForm");
	}else if (json.addOrEdit == "4"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("ruBookForm");
	}else if (json.addOrEdit == "5"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("tiaoBookForm");
	}
	
	navTab.reload(forwardurl, "", json.navTabId);
}

//管理功能--缺库统计
function navTabAjaxDoneBook3(json){
	var forwardurl ="book/bookList2.htm";
	//forwardurl = forwardurl;
	var pageNum=document.getElementById("pageNum3").value;
	//alert("pageNum2:"+pageNum);
	if(json.cfrom=="2"){
		
		forwardurl ="book/quebookList.htm?pageNum="+pageNum;
	}
	
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	//教材名称重复
	if (json.statusCode == 400) {
	       alertMsg.warn(json.message);
	       return null;
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("addBookForm");
	}else  if (json.addOrEdit == "0"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("editBookForm");
	}else if (json.addOrEdit == "3"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("chuBookForm");
	}else if (json.addOrEdit == "4"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("ruBookForm");
	}else if (json.addOrEdit == "5"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("tiaoBookForm");
	}
	
	navTab.reload(forwardurl, "", json.navTabId);
}

//单个发放添加成功回调  
function navTabAjaxDoneSendOne(json){
	
	var forwardurl = json.forwardurl;
	//alert("forwardurl:"+forwardurl+"--"+json.addOrEdit);
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("sendOneForm");
	}else  if (json.addOrEdit == "2"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("sendAllForm");
	}else  if (json.addOrEdit == "3"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("sendAllForm2");
	}
	
	navTab.reload(forwardurl, "", json.navTabId);
}

function navTabAjaxDoneSendOne2(json){
	
	alert("addOrEdit2:"+json.addOrEdit+",json.navTabId2:"+json.navTabId);
	var forwardurl = json.forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	if (json.addOrEdit == "1") {
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("sendOneFormHi");
	}else  if (json.addOrEdit == "2"){
		
		//alert("addOrEdit："+json.addOrEdit);
		$.pdialog.close("sendAllFormHi");
	}
	
	navTab.reload(forwardurl, "", json.navTabId);
}
//删除教材提示
function navTabAjaxDoneBookDelete(json){
	var forwardurl ="book/bookList.htm";
	
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	navTab.reload(forwardurl, "", json.navTabId);
}

//上传列表
function navTabAjaxDoneBookUpload(json){
	var forwardurl ="ensure/uploadList.htm";
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	navTab.reload(forwardurl, "", json.navTabId);
}

//添加 使用班级
function navTabAjaxDoneBookAddUseClass(json){
	
	var pageNum=document.getElementById("pageNum").value;
	var forwardurl ="ensure/useList.htm?pageNum="+pageNum;
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
		    alertMsg.correct(json.message);
		   	$.pdialog.close("addUseClassForm");
		   	navTab.reload(forwardurl, "", json.navTabId);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
}

//教材销毁回调  
function navTabAjaxDoneBookXiaoHui(json){
	
	var pageNum=document.getElementById("pageNum").value;
	var forwardurl ="book/huishouList.htm?pageNum="+pageNum;
	forwardurl = forwardurl;
	if (json.statusCode == 200) {
	       alertMsg.correct(json.message);
	}
	if (json.statusCode == 300) {
	       alertMsg.error(json.message);
	}
	
	//alert("json.addOrEdit:"+json.addOrEdit);
	if (json.addOrEdit == "xiaohui"){
		
		$.pdialog.close("huishouBookForm2");
	}
	
	navTab.reload(forwardurl, "", json.navTabId);
}

//成功提示框，alertMsg.correct('您的数据提交成功！')deleteUser('"+item.id+"');
//错误提示框，alertMsg.error('您提交的数据有误，请检查后重新提交！', [options])
//警告提示框，alertMsg.warn('您提交的数据有误，请检查后重新提交！', [options])
//信息提示框，alertMsg.info('您提交的数据有误，请检查后重新提交！', [options])
