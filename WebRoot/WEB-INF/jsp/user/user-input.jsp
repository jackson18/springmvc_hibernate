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
	<h1>添加/修改用户</h1>
	<form action="/springmvc_hibernate/user/save" name="userForm" method="post">
		<input type="hidden" name="uid" value="${entity.uid }"/>
		姓名：<input type="text" name="uname" value="${entity.uname }">
		密码：<input type="text" name="upass" value="${entity.upass }">
		<input type="submit" value="添加">
	</form>
  </body>
</html>
