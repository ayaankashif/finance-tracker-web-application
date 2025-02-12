<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Budget Tracker</title>
</head>
<body>

	<h1>Add Budget</h1>
	<form action= "BudgetTrackerServlet" method= "post">
	 <input type="hidden" name="action" value="addBudget" />
        <label>Budget:</label>
        <input type="text" name="budget"/> <br />
        <label>Budget Percentage:</label>
        <input type= "text" name="budgetPer"/> <br />
       	<button type="submit">Add Budget</button> 
	</form>
	
</body>
</html>