<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Add Bank Account</h1>
		
	<form action="BankAccountServlet" method="post">
		<input type="hidden" name="action" value="addBank" /> 
		<label>BankName:</label> 
		<input type="text" name="name" required /><br />
		<button type="submit">Add</button>
	</form>
		
	<hr />
</body>
</html>