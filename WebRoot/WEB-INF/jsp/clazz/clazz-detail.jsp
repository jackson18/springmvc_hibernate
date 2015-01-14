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
  	<h1>班级信息</h1>
  	班级名称：${entity.cname }
  	<br/>
    <h6>相关学生列表</h6>
	<table border="1">
		<tbody>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>年龄</th>
			</tr>
			<c:forEach items="${entity.studentList }" var="o" varStatus="item">
				<tr>
					<td>${item.count }</td>
					<td>${o.sname }</td>
					<td>${o.age }</td>
				</tr>				
			</c:forEach>
		</tbody>
	</table>
  </body>
</html>
