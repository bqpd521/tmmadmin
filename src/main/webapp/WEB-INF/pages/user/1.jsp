<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@page import="java.util.List"%>
 <%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ page isELIgnored="false" %>
</head>
<body>

<form>
<table>
<c:forEach  items="${userList.result}" var="item">
<tr>
<td>${item.userAccount}</td>
</tr>
</c:forEach>
</table>
</form>
</body>
</html>