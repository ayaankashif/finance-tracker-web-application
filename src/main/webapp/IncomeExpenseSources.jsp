<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Add Income/Expense Sources</h1>
    <form action="IncomeExpenseServlet" method="post">
        <input type="hidden" name="action" value="addIncomeExpense" />
        <h3>Income Section: </h3>
        <label>Income Source: </label>
        <input type="text" name="incomeSrc" />
        <button type="submit">Add Income</button> <br /> 
        
        <h3>Expense Section: </h3>
        <label>Expense Source: </label>
        <input type="text" name="expenseSrc" />
        <button type="submit">Add Expense</button> <br />
        <label>link to one of your Budgets: </label>
        <input type="text" name="budgetTracker" />
        <button type="submit">Add Budget</button>
    </form>

</body>
</html>