<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="js/jquery-1.7.1.js"></script>
  </head>
  
  <body>
    <h6><a href="/springmvc_hibernate/user/create">添加用户</a></h6>
	<table border="1">
		<tbody>
			<tr>
				<th>姓名</th>
				<th>密码</th>
				<th>操作</th>
			</tr>
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="user">
					<tr>
						<td>${user.uname }</td>
						<td>${user.upass }</td>
						<td>
							<shiro:hasRole name="superAdmin">
								<a href="/springmvc_hibernate/user/update?uid=${user.uid }">编辑</a>
								<a href="/springmvc_hibernate/user/delete?uid=${user.uid }">删除</a>
							</shiro:hasRole>
						</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
  </body>
</html>
