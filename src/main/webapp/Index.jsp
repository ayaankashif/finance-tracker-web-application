<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
System.out.println("HI from JAVA");
%>

<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Dashboard</h1>
    <form action="DashboardServlet" method="get">
        <label>Add BankAccount:</label>
        <button type="submit">Add</button> <br>
        <label>Add Income:</label>
        <button type="submit">Add</button> <br>
        <label>Add Expense:</label>
        <button type="submit">Add</button> <br>
        <label>Add Budget:</label>
        <button type="submit">Add</button> <br>
    </form>
   
    <a href = "BankAccount.jsp" > Add Bank Account</a> <br>
    <a href = "Income.jsp" > Add Income </a> <br>
    <a href = "IncomeServlet">List Incomes</a> <br>

    
</body>
</html> -->



<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Finance DashBoard</title>
<link href="Style.css" rel="stylesheet">

</head>
<body>
	<%
	String userName = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("user"))
		userName = cookie.getValue();
		}
	}
	if (userName == null)
		response.sendRedirect("login.html");
	%>

	<a href="Income.jsp"> Add Income </a>
	<br>

	<div class="header">
		<div class="search-container">
			<span class="search-icon">🔍</span> <input type="text"
				class="search-input" placeholder="Search">
		</div>
		<div class="user-info">
			<span>San Francisco, CA — 8:00 PM</span> <img
				src="https://hebbkx1anhila5yf.public.blob.vercel-storage.com/ss-0ZDfLqbo2qMDgbGSeNyAFQOYeWaWLL.png"
				alt="User avatar" class="user-avatar">
		</div>
	</div>

	<div class="welcome">
		<h1>Hello, <%=userName %></h1>
		<p>Here's a summary of your account activity for this week.</p>
	</div>

	<div class="tabs">
		<div class="tab-header">
			<div class="tab-buttons">
				<button class="tab-btn active" data-tab="checking">Checking
					Account</button>
				<button class="tab-btn" data-tab="savings">Savings Account</button>
			</div>
			<a href="BankAccount.jsp">
				<button class="add-account-btn">Add Account</button>
			</a>
		</div>

		<div class="tab-content" id="checking">
			<div class="metrics">
				<div class="metric-card">
					<h3>Earned</h3>
					<div class="metric-value">$1,250</div>
				</div>
				<div class="metric-card">
					<h3>Hours logged</h3>
					<div class="metric-value">35.5 hrs</div>
				</div>
				<div class="metric-card">
					<h3>Avg. time</h3>
					<div class="metric-value">2:55 hrs</div>
				</div>
				<div class="metric-card">
					<h3>Weekly growth</h3>
					<div class="metric-value">14.5%</div>
				</div>
			</div>
		</div>

		<div class="tab-content" id="savings" style="display: none;">
			<div class="metrics">
				<div class="metric-card">
					<h3>Balance</h3>
					<div class="metric-value">$5,000</div>
				</div>
				<div class="metric-card">
					<h3>Interest Rate</h3>
					<div class="metric-value">2.5%</div>
				</div>
				<div class="metric-card">
					<h3>YTD Interest</h3>
					<div class="metric-value">$125</div>
				</div>
				<div class="metric-card">
					<h3>Growth</h3>
					<div class="metric-value">3.2%</div>
				</div>
			</div>
		</div>
	</div>

	<div class="tables-container">
		<div class="left-tables">
			<div class="table-card">
				<h2>Income</h2>
				<a href="IncomeServlet" style="align-items: center;">List
					Income</a>
				<table>
					<thead>
						<tr>
							<th>Name</th>
							<th>Income Source</th>
							<th class="amount">Income</th>
						</tr>
					</thead>
					<tbody id="income-body">Populated by JavaScript
					</tbody>
				</table>
			</div>
			<div class="table-card">
				<h2>Expenses</h2>
				<table>
					<thead>
						<tr>
							<th>Date</th>
							<th>Description</th>
							<th class="amount">Amount</th>
						</tr>
					</thead>
					<tbody id="expenses-body">Populated by JavaScript
					</tbody>
				</table>
			</div>
		</div>
		<div class="right-table">
			<div class="table-card">
				<h2>Budget Tracker</h2>
				<table>
					<thead>
						<tr>
							<th>Category</th>
							<th>Budgeted</th>
							<th>Spent</th>
							<th>Remaining</th>
						</tr>
					</thead>
					<tbody id="budget-body">Populated by JavaScript
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<script>
         const income = [
            { date: "2024-01-27", source: "Salary", amount: 3000 },
            { date: "2024-01-20", source: "Freelance", amount: 500 },
            { date: "2024-01-15", source: "Investments", amount: 200 },
        ];

        const expenses = [
            { date: "2024-01-26", description: "Rent", amount: 1500 },
            { date: "2024-01-25", description: "Groceries", amount: 200 },
            { date: "2024-01-24", description: "Utilities", amount: 150 },
        ];

        const budget = [
            { category: "Housing", budgeted: 1500, spent: 1500, remaining: 0 },
            { category: "Food", budgeted: 500, spent: 350, remaining: 150 },
            { category: "Transportation", budgeted: 300, spent: 200, remaining: 100 },
            { category: "Entertainment", budgeted: 200, spent: 150, remaining: 50 },
        ];

        // Populate tables
        function populateTables() {
            const incomeBody = document.getElementById('income-body');
            const expensesBody = document.getElementById('expenses-body');
            const budgetBody = document.getElementById('budget-body');

            incomeBody.innerHTML = income.map(i => `
                <tr>
                    <td>${i.date}</td>
                    <td>${i.source}</td>
                    <td class="amount">$${i.amount.toFixed(2)}</td>
                </tr>
            `).join('');

            expensesBody.innerHTML = expenses.map(e => `
                <tr>
                    <td>${e.date}</td>
                    <td>${e.description}</td>
                    <td class="amount">$${e.amount.toFixed(2)}</td>
                </tr>
            `).join('');

            budgetBody.innerHTML = budget.map(b => `
                <tr>
                    <td>${b.category}</td>
                    <td>$${b.budgeted.toFixed(2)}</td>
                    <td>$${b.spent.toFixed(2)}</td>
                    <td>$${b.remaining.toFixed(2)}</td>
                </tr>
            `).join('');
        }

        // Tab switching functionality
        function setupTabs() {
            const tabBtns = document.querySelectorAll('.tab-btn');
            const tabContents = document.querySelectorAll('.tab-content');

            tabBtns.forEach(btn => {
                btn.addEventListener('click', () => {
                    // Remove active class from all buttons
                    tabBtns.forEach(b => b.classList.remove('active'));
                    // Add active class to clicked button
                    btn.classList.add('active');

                    // Hide all tab contents
                    tabContents.forEach(content => content.style.display = 'none');
                    // Show selected tab content
                    document.getElementById(btn.dataset.tab).style.display = 'block';
                });
            });
        }

        // Initialize
        document.addEventListener('DOMContentLoaded', () => {
            populateTables();
            setupTabs();
        });
    </script>
</body>
</html>



<!-- <!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <title>Finance Tracker</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      background-color: #f4f4f9;
    }

    .header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 20px;
      background-color: #6200ea;
      color: white;
    }

    .tabs {
      display: flex;
      gap: 20px;
    }

    .tab {
      background-color: white;
      color: #6200ea;
      padding: 10px 20px;
      border-radius: 8px;
      cursor: pointer;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .tab:hover {
      background-color: #bb86fc;
      color: white;
    }

    .add-account {
      background-color: #03dac6;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      font-size: 1rem;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .add-account:hover {
      background-color: #018786;
    }

    .tables-container {
      display: flex;
      justify-content: space-between;
      margin: 20px;
      gap: 20px;
    }

    .left-tables {
      display: flex;
      flex-direction: column;
      gap: 20px;
      width: 65%;
    }

    .table {
      background-color: white;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      padding: 10px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
    }

    th, td {
      padding: 10px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }

    th {
      background-color: #6200ea;
      color: white;
    }

    .budget-table {
      	width: 40%;
    	height: 220px; 	
    }
	
	
  </style>
</head>
<body>
  Header Section
  <div class="header">
    <div class="tabs">
      <div class="tab">Bank Account 1: $1000</div>
      <div class="tab">Bank Account 2: $2000</div>
      <div class="tab">Bank Account 3: $3000</div>
    </div>
    <button class="add-account">Add Account</button>
  </div>

  Tables Section
  <div class="tables-container">
    Left Tables
    <div class="left-tables">
      Income Table
      <div class="table">
        <h5>Income</h5>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Bank Account</th>
              <th>Income</th>
              <th>Income Source</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Salary</td>
              <td>$5000</td>
              <td>Company</td>
              <td>01/28/2025</td>
            </tr>
            <tr>
              <td>Freelancing</td>
              <td>$1200</td>
              <td>Client</td>
              <td>01/25/2025</td>
            </tr>
          </tbody>
        </table>
      </div>

      Expense Table
      <div class="table">
        <h5>Expenses</h5>
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Bank Account</th>
              <th>Expense</th>
              <th>Expense Source</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Rent</td>
              <td>$1500</td>
              <td>Landlord</td>
              <td>01/10/2025</td>
            </tr>
            <tr>
              <td>Groceries</td>
              <td>$300</td>
              <td>Supermarket</td>
              <td>01/20/2025</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    Budget Table
    <div class="table budget-table">
      <h5>Budget Tracker</h5>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Budget %</th>
            <th>Monthly Budget</th>
            <th>Current Month</th>
            <th>Remaining</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Travel</td>
            <td>20%</td>
            <td>$1000</td>
            <td>$200</td>
            <td>$800</td>
          </tr>
          <tr>
            <td>Entertainment</td>
            <td>10%</td>
            <td>$500</td>
            <td>$100</td>
            <td>$400</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  
  Script
  <script>
    document.querySelector('.add-account').addEventListener('click', () => {
      alert('Add Account functionality will go here.');
    });
  </script>
</body>
</html> -->