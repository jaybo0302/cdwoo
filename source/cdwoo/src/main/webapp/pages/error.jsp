<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%String errorMessage = (String)request.getAttribute("errorMessage"); %>
<title>ERROR PAGE</title>
</head>
<body>
  <span><%=errorMessage %></span>
</body>
</html>