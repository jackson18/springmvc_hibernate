<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  
  <body>
	<h1>新增/修改学生</h1>
	<form action="/springmvc_hibernate/student/save" name="userForm" method="post">
		<input type="hidden" name="sid" value="${entity.sid }"/>
		<input type="hidden" name="cid" value="${cid }"/>
		姓名：<input type="text" name="sname" value="${entity.sname }">
		年龄：<input type="text" name="age" value="${entity.age }">
		<input type="submit" value="添加">
	</form>
  </body>
</html>
