<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Expense</title>
</head>
<body>
	
	<h1>Add Expense</h1>
    <form action="ExpenseServlet" method="post">
        <input type="hidden" name="action" value="addExpense" />
        <label>Name:</label>
        <input type="text" name="name" /><br />
        <label>Bank Account:</label>
        <input type="text" name="bankAccount" /><br />
        <label>Expense Source:</label>
        <input type="text" name="expenseSource" /><br />
        <label>Expense:</label>
        <input type="text" name="expense" /><br />
        <button type="submit">Add Expense</button>
    </form>	
	
</body>
</html>