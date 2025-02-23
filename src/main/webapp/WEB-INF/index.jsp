<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- <%@ page isELIgnored="false" %> --%>



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



<%-- <!DOCTYPE html>
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
		response.sendRedirect("LoginServlet");
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
		<h1> Hello, <%=userName%> </h1>
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
				<a href="IncomeServlet" style="align-items: center">List Income</a>
				<table>
					<thead>
						<tr>
							<th>Name</th>
							<th class="amount"> Current Month</th>
							<th>Monthly Goal</th>
							<th>Remaining</th>
							<th>Progress</th>
						</tr>
					</thead>
					<tbody id="income-body">
					
					<c:forEach var="record" items="${budgetTrackerIncome}">
                		<c:set var="name" value="${record[0]}" />
                		<c:set var="currentMonth" value="${record[1]}" />
                		<c:set var="monthlyGoal" value="${record[2]}" />
                
                		<c:set var="remaining" value="${monthlyGoal - currentMonth}" />
                		<c:set var="progress" value="${(currentMonth / monthlyGoal) * 100}" />
					
					 	<tr>
                    		<td>${name}</td>
                    		<td>${currentMonth}</td>
                    		<td>${monthlyGoal}</td>
                    		<td>${remaining}</td>
                    		<td>${progress}%</td>
                		</tr>
           	 		</c:forEach>
					
					</tbody>
				</table>
			</div>
			<div class="table-card">
				<h2>Expenses</h2>
				<table>
					<thead>
						<tr>
							<th>Name</th>
							<th class="amount">Current Month</th>
							<th>Monthly Goal</th>
							<th>Remaining</th>
							<th>Progress</th>
							<th>Budget Tracker</th>
						</tr>
					</thead>
					<tbody id="expenses-body">
						
						<c:forEach var="record" items="${budgetTrackerExpense}">
                			<c:set var="name" value="${record[0]}" />
                			<c:set var="currentMonth" value="${record[1]}" />
                			<c:set var="monthlyBudget" value="${record[2]}" />
                			<c:set var="budgetName" value="${record[3]}" />
                			
                			<c:set var="absoluteCurrentMonth" value="${currentMonth * -1}" />
                
                			<c:set var="remaining" value="${monthlyBudget + absolutecurrentMonth}" />
                			<c:set var="progress" value="${((absoluteCurrentMonth)/ monthlyBudget) * 100}" />

                			<tr>
                    			<td>${name}</td>
                    			<td>${absoluteCurrentMonth}</td>
                    			<td>${monthlyBudget}</td>
                    			<td>${remaining}</td>
                    			<td>${progress}%</td>
                    			<td>${budgetName}</td>
               				</tr>
           			 	</c:forEach>
					
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
							<th>Name</th>
							<th>Budget</th>
							<th>Monthly Budget</th>
							<th>Current Month</th>
							<th>Remaining</th>
						</tr>
					</thead>
					<tbody>
					
						<c:forEach var="budget" items="${budgetData}">
							<tr>
								
								<td>${budget.name}</td>
                				<td>${budget.budgetPercentage} %</td>
                				<td>${budget.allocatedAmount}</td>
                				<td>${budget.currentExpense}</td>
                				<td>${budget.remaining}</td>
								
							</tr>
						</c:forEach>

					</tbody>
				</table>

			</div>
		</div>
	</div>

	<script>
         /* const income = [
            { date: "2024-01-27", source: "Salary", amount: 3000 },
            { date: "2024-01-20", source: "Freelance", amount: 500 },
            { date: "2024-01-15", source: "Investments", amount: 200 },
        ]; */

        /* const expenses = [
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
 */
        // Populate tables
        /* function populateTables() {
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
        } */

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
</html> --%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Finance Tracker</title>
    <link rel="stylesheet" href="styles.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    
</head>
<body class="dark">
    <div class="container">
        <header class="header">
            <h1>Budget Tracker</h1>
        </header>

        <div class="tabs">
            <div class="tabs-list">
                <button class="tab-trigger active" data-tab="overview">Overview</button>
                <button class="tab-trigger" data-tab="income">Income</button>
                <button class="tab-trigger" data-tab="expenses">Expenses</button>
            </div>

            <div class="tab-content active" id="overview">
                <div class="cards-grid">
                    <div class="card">
                        <div class="card-header">
                            <h3>Total Balance</h3>
                            <span class="material-icons">attach_money</span>
                        </div>
                        <div class="card-content">
                            <div class="amount">$1,234.56</div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3>Monthly Income</h3>
                            <span class="material-icons">pie_chart</span>
                        </div>
                        <div class="card-content">
                            <div class="amount">$4,500.00</div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3>Total Expenses</h3>
                            <span class="material-icons">account_balance_wallet</span>
                        </div>
                        <div class="card-content">
                            <div class="amount">$1,015.00</div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h3>Savings</h3>
                            <span class="material-icons">swap_vert</span>
                        </div>
                        <div class="card-content">
                            <div class="amount">$345.00</div>
                        </div>
                    </div>
                </div>

                <div class="card expenses-card">
                    <div class="card-header">
                        <h3>Expenses Overview</h3>
                        <p>Track your monthly expenses and budgets</p>
                    </div>
                    <div class="card-content">
                        <div id="expenses-list" class="expenses-list">
                            <!-- Expenses will be inserted here by JavaScript -->
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="card-header">
                        <h3>Recent Transactions</h3>
                    </div>
                    <div class="card-content">
                        <table>
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Description</th>
                                    <th>Amount</th>
                                    <th>Type</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>2025-02-23</td>
                                    <td>Salary</td>
                                    <td>$3,000.00</td>
                                    <td>Income</td>
                                </tr>
                                <tr>
                                    <td>2025-02-22</td>
                                    <td>Groceries</td>
                                    <td>$150.00</td>
                                    <td>Expense</td>
                                </tr>
                                <tr>
                                    <td>2025-02-21</td>
                                    <td>Freelance Work</td>
                                    <td>$500.00</td>
                                    <td>Income</td>
                                </tr>
                                <tr>
                                    <td>2025-02-20</td>
                                    <td>Utilities</td>
                                    <td>$200.00</td>
                                    <td>Expense</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="tab-content" id="income">
                <div class="tab-header">
                    <h2 class="tab-title">Income</h2>
                    <button class="btn btn-outline" id="add-income-btn">
                        <span class="material-icons">add</span>
                        Add Income
                    </button>
                </div>
                <div class="card">
                    <div class="card-content">
                        <table>
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Source</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>2025-02-23</td>
                                    <td>Salary</td>
                                    <td>$3,000.00</td>
                                </tr>
                                <tr>
                                    <td>2025-02-21</td>
                                    <td>Freelance Work</td>
                                    <td>$500.00</td>
                                </tr>
                                <tr>
                                    <td>2025-02-15</td>
                                    <td>Investment Dividends</td>
                                    <td>$200.00</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="tab-content" id="expenses">
                <div class="tab-header">
                    <h2 class="tab-title">Expenses</h2>
                    <button class="btn btn-outline" id="add-expense-btn">
                        <span class="material-icons">add</span>
                        Add Expense
                    </button>
                </div>
                <div class="card">
                    <div class="card-content">
                        <table>
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Category</th>
                                    <th>Amount</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>2025-02-22</td>
                                    <td>Groceries</td>
                                    <td>$150.00</td>
                                </tr>
                                <tr>
                                    <td>2025-02-20</td>
                                    <td>Utilities</td>
                                    <td>$200.00</td>
                                </tr>
                                <tr>
                                    <td>2025-02-18</td>
                                    <td>Transportation</td>
                                    <td>$50.00</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="card bank-accounts">
            <div class="card-header">
                <h3>Bank Accounts</h3>
                <p>Manage your connected bank accounts</p>
            </div>
            <div class="card-content">
                <div class="accounts-list">
                    <div class="account-item">
                        <div class="account-info">
                            <p class="account-name">Main Checking Account</p>
                            <p class="account-number">**** 1234</p>
                        </div>
                        <div class="account-balance">
                            <p class="balance-amount">$3,456.78</p>
                            <p class="balance-label">Available Balance</p>
                        </div>
                    </div>
                    <div class="account-item">
                        <div class="account-info">
                            <p class="account-name">Savings Account</p>
                            <p class="account-number">**** 5678</p>
                        </div>
                        <div class="account-balance">
                            <p class="balance-amount">$10,234.56</p>
                            <p class="balance-label">Available Balance</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <button class="btn btn-outline btn-full">
                    <span class="material-icons">add</span>
                    Add New Account
                </button>
            </div>
        </div>
    </div>

    <script>
        // Sample expense data
        const expenses = [
            { category: "Software", amount: 40, budget: 100, progress: 40, type: "business" },
            { category: "Crypto", amount: 0, budget: 500, progress: 0, type: "investment" },
            { category: "Food", amount: 125, budget: 125, progress: 100, type: "personal" },
            { category: "Living Expenses", amount: 850, budget: 2000, progress: 42.5, type: "personal" }
        ];

        // Initialize the application
        document.addEventListener('DOMContentLoaded', () => {
            initializeTabs();
            renderExpenses();
        });

        // Tab functionality
        function initializeTabs() {
            const tabTriggers = document.querySelectorAll('.tab-trigger');
            const tabContents = document.querySelectorAll('.tab-content');

            tabTriggers.forEach(trigger => {
                trigger.addEventListener('click', () => {
                    // Remove active class from all triggers and contents
                    tabTriggers.forEach(t => t.classList.remove('active'));
                    tabContents.forEach(c => c.classList.remove('active'));

                    // Add active class to clicked trigger and corresponding content
                    trigger.classList.add('active');
                    const tabId = trigger.dataset.tab;
                    document.getElementById(tabId).classList.add('active');
                });
            });
        }

        // Render expenses list
        function renderExpenses() {
            const expensesList = document.getElementById('expenses-list');
            expensesList.innerHTML = expenses.map(expense => `
                <div class="expense-item">
                    <div class="expense-header">
                        <div>
                            <span class="expense-category">${expense.category}</span>
                            <span class="expense-amount">$${expense.amount}</span>
                        </div>
                        <span class="expense-budget">$${expense.amount} / $${expense.budget}</span>
                    </div>
                    <div class="progress-bar">
                        <div class="progress-bar-fill" style="width: ${expense.progress}%"></div>
                    </div>
                </div>
            `).join('');
        }

        // Add new expense functionality
        function addExpense(expense) {
            expenses.push(expense);
            renderExpenses();
        }

        // Add new account functionality
        function addAccount(account) {
            const accountsList = document.querySelector('.accounts-list');
            const accountElement = document.createElement('div');
            accountElement.className = 'account-item';
            accountElement.innerHTML = `
                <div class="account-info">
                    <p class="account-name">${account.name}</p>
                    <p class="account-number">**** ${account.number.slice(-4)}</p>
                </div>
                <div class="account-balance">
                    <p class="balance-amount">$${account.balance.toFixed(2)}</p>
                    <p class="balance-label">Available Balance</p>
                </div>
            `;
            accountsList.appendChild(accountElement);
        }

        // Event listeners for buttons
        document.querySelectorAll('.btn').forEach(button => {
            button.addEventListener('click', () => {
                if (button.textContent.includes('Add New Account')) {
                    // Example of adding a new account
                    addAccount({
                        name: 'New Account',
                        number: '1234567890',
                        balance: 0.00
                    });
                }
            });
        });

        // Calculate totals
        function calculateTotals() {
            const totalExpenses = expenses.reduce((sum, expense) => sum + expense.amount, 0);
            const totalBudget = expenses.reduce((sum, expense) => sum + expense.budget, 0);
            return { totalExpenses, totalBudget };
        }

        // Update dashboard
        function updateDashboard() {
            const { totalExpenses, totalBudget } = calculateTotals();
            // Update the dashboard with new totals
            // This would be where you update the display of totals
        }
        
     // Add event listeners for new buttons
        document.getElementById('add-income-btn').addEventListener('click', () => {
            alert('Add Income functionality to be implemented');
        });

        document.getElementById('add-expense-btn').addEventListener('click', () => {
            alert('Add Expense functionality to be implemented');
        });
       
    </script>
</body>
</html>
