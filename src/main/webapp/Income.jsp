<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Finance Tracker</title>
</head>

<body>
<h1>Add Income</h1>
    <form action="IncomeServlet" method="post">
        <input type="hidden" name="action" value="addIncome" />
        <label>Name:</label>
        <input type="text" name="name" /><br />
        <label>Bank Account:</label>
        <input type="text" name="bankAccount" /><br />
        <label>Income Source:</label>
        <input type="text" name="incomeSource" /><br />
        <label>Income:</label>
        <input type="text" name="income" /><br />
        <button type="submit">Add Income</button>
    </form>
    <hr />
	
<table class="table">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Income</th>
      <th scope="col">Bank Account</th>
      <th scope="col">Income Sources</th>
    </tr>
  </thead>
  <tbody>
  	<tr>
     <c:forEach var="income" items="${incomes}">
            <tr>
                <td>${income.incomeId}</td> 
                <td>${income.name}</td>
                <td>${income.income}</td>
                <td>${income.incomeSources.incomeExpenseSource}</td>
            </tr>
     </c:forEach>
  </tbody>
</table>

</body>
</html>