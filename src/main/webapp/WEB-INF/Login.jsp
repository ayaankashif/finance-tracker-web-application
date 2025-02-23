<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<h1>Login</h1>
	<form action= "LoginServlet" method = "post">
		<input type="hidden" name="action" value="login" />
        <label>User Name:</label>
        <input type="text" name="user" required /><br />
        <label>Password:</label>
        <input type="text" name="password" required /><br />
        <button type="submit">Login</button>
	</form>
	
</body>
</html>