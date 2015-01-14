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
    <script type="text/javascript" src="js/jquery-1.7.1.js"></script>
  </head>
  
  <body>
    <h6><a href="/springmvc_hibernate/clazz/create">添加班级</a></h6>
	<table border="1">
		<tbody>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>操作</th>
			</tr>
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="o" varStatus="item">
					<tr>
						<td>${item.count }</td>
						<td>${o.cname }</td>
						<td>
							<a href="/springmvc_hibernate/clazz/update?cid=${o.cid }">编辑</a>
							<a href="/springmvc_hibernate/clazz/delete?cid=${o.cid }">删除</a>
							<a href="/springmvc_hibernate/student/create?cid=${o.cid }">增加学生</a>
							<a href="/springmvc_hibernate/clazz/detail?cid=${o.cid }">班级详情</a>
						</td>
					</tr>				
				</c:forEach>
			</c:if>
		</tbody>
	</table>
  </body>
</html>
