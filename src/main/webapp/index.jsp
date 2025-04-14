<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ayaan.FinanceTracker.dao.BudgetTrackerDAO" %>
<%@ page import="com.ayaan.FinanceTracker.daoImpl.BudgetTrackerDAOImpl" %>
<%@ page import="com.ayaan.FinanceTracker.exceptionHandling.DataAccessException" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Finance Tracker</title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="assets/vendors/feather/feather.css">
    <link rel="stylesheet" href="assets/vendors/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="assets/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="assets/vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/vendors/typicons/typicons.css">
    <link rel="stylesheet" href="assets/vendors/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet" href="assets/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script> -->
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <link rel="stylesheet" href="assets/vendors/datatables.net-bs4/dataTables.bootstrap4.css">
    <link rel="stylesheet" type="text/css" href="assets/js/select.dataTables.min.css">
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="assets/css/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="assets/images/favicon.png" />
  </head>
  <body class="with-welcome-text" onload="updateClock()">
  <%
	String userName = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("name"))
				userName = cookie.getValue();
			}
		}
	if (userName == null)
		response.sendRedirect("LoginServlet");
	%>
    <div class="container-scroller">
      <div class="row p-0 m-0 proBanner" id="proBanner">
        <div class="col-md-12 p-0 m-0">
          <div class="card-body card-body-padding px-3 d-flex align-items-center justify-content-between">
            <div class="d-flex align-items-center justify-content-between">
              <a href="https://www.bootstrapdash.com/product/star-admin-pro/"><i class="ti-home me-3 text-white"></i></a>
              <button id="bannerClose" class="btn border-0 p-0">
                <i class="ti-close text-white"></i>
              </button>
            </div>
          </div>
        </div>
      </div>
      <!-- partial:partials/_navbar.html -->
      <nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex align-items-top flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-start">
          <div class="me-3">
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-bs-toggle="minimize">
              <span class="icon-menu"></span>
            </button>
          </div>
          <div>
            <a class="navbar-brand brand-logo" style="display: flex; align-items: center; gap: 4px;font-family: 'Georgia', 'Garamond', 'Times New Roman', serif;" href="DashboardServlet">
             <!--  <img src="assets/images/logo.svg" alt="logo" /> -->
              <h4 style="margin: 0;">Finance</h4>
			  <h4 style="margin: 0; color: blue;">Tracker</h4>
            </a>
            <a class="navbar-brand brand-logo-mini" href="DashboardServlet">
              <img src="assets/images/logo-mini.svg" alt="logo" />
            </a>
          </div>
         	<!-- <div class="brand-logo" style="display: flex; align-items: center; gap: 4px;font-family: 'Georgia', 'Garamond', 'Times New Roman', serif;"> 
			   	<h4 style="margin: 0;">Finance</h4>
				<h4 style="margin: 0; color: blue;">Tracker</h4>
				<a class="navbar-brand brand-logo-mini" href="DashboardServlet">
		            <img src="assets/images/logo-mini.svg" alt="logo" />
		        </a>
        	</div> -->
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-top">
          <ul class="navbar-nav">
            <li class="nav-item fw-semibold d-none d-lg-block ms-0">
              <h1 class="welcome-text">Welcome Back, <span class="text-black fw-bold"><%=userName%></span></h1>
              <h3 class="welcome-sub-text">Your performance summary this week </h3>
            </li>
          </ul>
          <ul class="navbar-nav ms-auto">
            <li class="nav-item dropdown d-none d-lg-block">
              <a class="nav-link dropdown-bordered dropdown-toggle dropdown-toggle-split" id="messageDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false"> Select Category </a>
              <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list pb-0" aria-labelledby="messageDropdown">
                <a class="dropdown-item py-3">
                  <p class="mb-0 fw-medium float-start">Select category</p>
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item preview-item">
                  <div class="preview-item-content flex-grow py-2">
                    <p class="preview-subject ellipsis fw-medium text-dark">Bootstrap Bundle </p>
                    <p class="fw-light small-text mb-0">This is a Bundle featuring 16 unique dashboards</p>
                  </div>
                </a>
                <a class="dropdown-item preview-item">
                  <div class="preview-item-content flex-grow py-2">
                    <p class="preview-subject ellipsis fw-medium text-dark">Angular Bundle</p>
                    <p class="fw-light small-text mb-0">Everything you’ll ever need for your Angular projects</p>
                  </div>
                </a>
                <a class="dropdown-item preview-item">
                  <div class="preview-item-content flex-grow py-2">
                    <p class="preview-subject ellipsis fw-medium text-dark">VUE Bundle</p>
                    <p class="fw-light small-text mb-0">Bundle of 6 Premium Vue Admin Dashboard</p>
                  </div>
                </a>
                <a class="dropdown-item preview-item">
                  <div class="preview-item-content flex-grow py-2">
                    <p class="preview-subject ellipsis fw-medium text-dark">React Bundle</p>
                    <p class="fw-light small-text mb-0">Bundle of 8 Premium React Admin Dashboard</p>
                  </div>
                </a>
              </div>
            </li>
            <li class="nav-item d-none d-lg-block">
              <div id="datepicker-popup" class="input-group date datepicker navbar-date-picker">
                <span class="input-group-addon input-group-prepend border-right">
                  <span class="icon-calendar input-group-text calendar-icon"></span>
                </span>
                <input type="text" class="form-control">
              </div>
            </li>
            <li class="nav-item">
              <form class="search-form" action="#">
                <i class="icon-search"></i>
                <input type="search" class="form-control" placeholder="Search Here" title="Search here">
              </form>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link count-indicator" id="notificationDropdown" href="#" data-bs-toggle="dropdown">
                <i class="icon-bell"></i>
                <span class="count"></span>
              </a>
              <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list pb-0" aria-labelledby="notificationDropdown">
                <a class="dropdown-item py-3 border-bottom">
                  <p class="mb-0 fw-medium float-start">You have 4 new notifications </p>
                  <span class="badge badge-pill badge-primary float-end">View all</span>
                </a>
                <a class="dropdown-item preview-item py-3">
                  <div class="preview-thumbnail">
                    <i class="mdi mdi-alert m-auto text-primary"></i>
                  </div>
                  <div class="preview-item-content">
                    <h6 class="preview-subject fw-normal text-dark mb-1">Application Error</h6>
                    <p class="fw-light small-text mb-0"> Just now </p>
                  </div>
                </a>
                <a class="dropdown-item preview-item py-3">
                  <div class="preview-thumbnail">
                    <i class="mdi mdi-lock-outline m-auto text-primary"></i>
                  </div>
                  <div class="preview-item-content">
                    <h6 class="preview-subject fw-normal text-dark mb-1">Settings</h6>
                    <p class="fw-light small-text mb-0"> Private message </p>
                  </div>
                </a>
                <a class="dropdown-item preview-item py-3">
                  <div class="preview-thumbnail">
                    <i class="mdi mdi-airballoon m-auto text-primary"></i>
                  </div>
                  <div class="preview-item-content">
                    <h6 class="preview-subject fw-normal text-dark mb-1">New user registration</h6>
                    <p class="fw-light small-text mb-0"> 2 days ago </p>
                  </div>
                </a>
              </div>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link count-indicator" id="countDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="icon-mail icon-lg"></i>
              </a>
              <div class="dropdown-menu dropdown-menu-right navbar-dropdown preview-list pb-0" aria-labelledby="countDropdown">
                <a class="dropdown-item py-3">
                  <p class="mb-0 fw-medium float-start">You have 7 unread mails </p>
                  <span class="badge badge-pill badge-primary float-end">View all</span>
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item preview-item">
                  <div class="preview-thumbnail">
                    <img src="assets/images/faces/face10.jpg" alt="image" class="img-sm profile-pic">
                  </div>
                  <div class="preview-item-content flex-grow py-2">
                    <p class="preview-subject ellipsis fw-medium text-dark">Marian Garner </p>
                    <p class="fw-light small-text mb-0"> The meeting is cancelled </p>
                  </div>
                </a>
                <a class="dropdown-item preview-item">
                  <div class="preview-thumbnail">
                    <img src="assets/images/faces/face12.jpg" alt="image" class="img-sm profile-pic">
                  </div>
                  <div class="preview-item-content flex-grow py-2">
                    <p class="preview-subject ellipsis fw-medium text-dark">David Grey </p>
                    <p class="fw-light small-text mb-0"> The meeting is cancelled </p>
                  </div>
                </a>
                <a class="dropdown-item preview-item">
                  <div class="preview-thumbnail">
                    <img src="assets/images/faces/face1.jpg" alt="image" class="img-sm profile-pic">
                  </div>
                  <div class="preview-item-content flex-grow py-2">
                    <p class="preview-subject ellipsis fw-medium text-dark">Travis Jenkins </p>
                    <p class="fw-light small-text mb-0"> The meeting is cancelled </p>
                  </div>
                </a>
              </div>
            </li>
            <li class="nav-item dropdown d-none d-lg-block user-dropdown">
              <a class="nav-link" id="UserDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false">
                <img class="img-xs rounded-circle" src="assets/images/faces/face8.jpg" alt="Profile image"> </a>
              <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="UserDropdown">
                <div class="dropdown-header text-center">
                  <img class="img-md rounded-circle" src="assets/images/faces/face8.jpg" alt="Profile image">
                  <p class="mb-1 mt-3 fw-semibold">Allen Moreno</p>
                  <p class="fw-light text-muted mb-0">allenmoreno@gmail.com</p>
                </div>
                <a class="dropdown-item"><i class="dropdown-item-icon mdi mdi-account-outline text-primary me-2"></i> My Profile <span class="badge badge-pill badge-danger">1</span></a>
                <a class="dropdown-item"><i class="dropdown-item-icon mdi mdi-message-text-outline text-primary me-2"></i> Messages</a>
                <a class="dropdown-item"><i class="dropdown-item-icon mdi mdi-calendar-check-outline text-primary me-2"></i> Activity</a>
                <a class="dropdown-item"><i class="dropdown-item-icon mdi mdi-help-circle-outline text-primary me-2"></i> FAQ</a>
                <a class="dropdown-item"><i class="dropdown-item-icon mdi mdi-power text-primary me-2"></i>Sign Out</a>
              </div>
            </li>
          </ul>
          <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-bs-toggle="offcanvas">
            <span class="mdi mdi-menu"></span>
          </button>
        </div>
      </nav>
      <!-- partial -->
      <div class="container-fluid page-body-wrapper">
        <!-- partial:partials/_sidebar.html -->
        <nav class="sidebar sidebar-offcanvas" id="sidebar">
          <ul class="nav">
            <li class="nav-item">
              <a class="nav-link" href="DashboardServlet">
                <i class="mdi mdi-grid-large menu-icon"></i>
                <span class="menu-title">Dashboard</span>
              </a>
            </li>
            <li class="nav-item nav-category">Manager</li>
            <li class="nav-item">
              <a class="nav-link" data-bs-toggle="collapse" href="#form-elements" aria-expanded="false" aria-controls="form-elements">
                <i class="menu-icon mdi mdi-card-text-outline"></i>
                <span class="menu-title">Finance</span>
                <i class="menu-arrow"></i>
              </a>
              <div class="collapse" id="form-elements">
                <ul class="nav flex-column sub-menu">
                  <li class="nav-item"> <a class="nav-link" href="IncomeServlet">Income</a></li>
                  <li class="nav-item"> <a class="nav-link" href="ExpenseServlet">Expense</a></li>
                </ul>
              </div>
            </li>            
            <li class="nav-item">
              <a class="nav-link" data-bs-toggle="collapse" href="#tables" aria-expanded="false" aria-controls="tables">
                <i class="menu-icon mdi mdi-table"></i>
                <span class="menu-title">Bank Account</span>
                <i class="menu-arrow"></i>
              </a>
              <div class="collapse" id="tables" >
                <ul class="nav flex-column sub-menu">
                  <li class="nav-item"> <a class="nav-link" href="basic-table.html">Active Banks</a></li>
                </ul>
              </div>
            </li>
            <li class="nav-item">
              <a class="nav-link" data-bs-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
                <i class="menu-icon mdi mdi-account-circle-outline"></i>
                <span class="menu-title">User Pages</span>
                <i class="menu-arrow"></i>
              </a>
              <div class="collapse" id="auth">
                <ul class="nav flex-column sub-menu">
                  <li class="nav-item"> <a class="nav-link" href="LoginServlet"> Login </a></li>
                  <li class="nav-item"> <a class="nav-link" href="SignupServlet"> Register </a></li>
                </ul>
              </div>
            </li>
            <!-- <li class="nav-item">
              <a class="nav-link" data-bs-toggle="collapse" href="#tables" aria-expanded="false" aria-controls="tables">
                <i class="menu-icon mdi mdi-table"></i>
                <span class="menu-title">Bank</span>
                <i class="menu-arrow"></i>
              </a>
              <div class="collapse" id="tables">
                <ul class="nav flex-column sub-menu">
                  <li class="nav-item"> <a class="nav-link" href="basic-table.html">Link Banks</a></li>
                </ul>
              </div>
            </li> -->
            <!-- <li class="nav-item">
              <a class="nav-link" data-bs-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
                <i class="menu-icon mdi mdi-account-circle-outline"></i>
                <span class="menu-title">User Pages</span>
                <i class="menu-arrow"></i>
              </a>
              <div class="collapse" id="auth">
                <ul class="nav flex-column sub-menu">
                  <li class="nav-item"> <a class="nav-link" href="pages/samples/blank-page.html"> Blank Page </a></li>
                  <li class="nav-item"> <a class="nav-link" href="pages/samples/error-404.html"> 404 </a></li>
                  <li class="nav-item"> <a class="nav-link" href="pages/samples/error-500.html"> 500 </a></li>
                  <li class="nav-item"> <a class="nav-link" href="pages/samples/login.html"> Login </a></li>
                  <li class="nav-item"> <a class="nav-link" href="pages/samples/register.html"> Register </a></li>
                </ul>
              </div>
            </li> -->
            <li class="nav-item">
              <a class="nav-link" href="documentation.html">
                <i class="menu-icon mdi mdi-file-document"></i>
                <span class="menu-title">Documentation</span>
              </a>
            </li>
          </ul>
        </nav>
        <!-- partial -->
        <div class="main-panel">
          <div class="content-wrapper">
            <div class="row">
              <div class="col-sm-12">
                <div class="home-tab">
                  <div class="d-sm-flex align-items-center justify-content-between border-bottom">
                    <ul class="nav nav-tabs" role="tablist">
                      <li class="nav-item">
                        <a class="nav-link active ps-0" id="home-tab" data-bs-toggle="tab" href="#overview" role="tab" aria-controls="overview" aria-selected="true">Analytics</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" id="profile-tab" data-bs-toggle="tab" href="#audiences" role="tab" aria-selected="false">Income Overview</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link" id="contact-tab" data-bs-toggle="tab" href="#demographics" role="tab" aria-selected="false">Expense Overview</a>
                      </li>
                      <li class="nav-item">
                        <a class="nav-link border-0" id="more-tab" data-bs-toggle="tab" href="#more" role="tab" aria-selected="false">More</a>
                      </li>
                    </ul>
                    <div>
                      <div class="btn-wrapper">
                        <a href="#" class="btn btn-otline-dark align-items-center"><i class="icon-share"></i> Share</a>
                        <a href="#" class="btn btn-otline-dark"><i class="icon-printer"></i> Print</a>
                        <a href="#" class="btn btn-primary text-white me-0"><i class="icon-download"></i>Export</a>
                      </div>
                    </div>
                  </div>
                  <div class="tab-content tab-content-basic">
                    <div class="tab-pane fade show active" id="overview" role="tabpanel" aria-labelledby="overview">
                      <div class="row">
                        <div class="col-sm-12">
                          <div class="statistics-details d-flex align-items-center justify-content-between">
                            <div>
                              <p class="statistics-title">Total Balance</p>
                              <h3 class="rate-percentage">$<%= request.getAttribute("totalBalance") %></h3>
                              <p class="text-danger d-flex"><i class="mdi mdi-menu-down"></i><span>-0.5%</span></p>
                            </div>
                            <%
							    Object incomeObj = request.getAttribute("totalIncome");
							    double income = (incomeObj != null) ? Double.parseDouble(incomeObj.toString()) : 0.0;
							%>
                            <div>
                              <p class="statistics-title">Monthly Income</p>
                              <h3 class="rate-percentage" style="font-weight: bold; font-size: 24px;">$<%= income %></h3>
                              <p class="text-success d-flex"><i class="mdi mdi-menu-up"></i><span>+0.1%</span></p>
                            </div>
                            <div>
                              <p class="statistics-title">Total Expense</p>
                              <h3 class="rate-percentage" style="font-weight: bold; font-size: 24px;">$<%= request.getAttribute("totalExpense") %></h3>
                              <p class="text-danger d-flex"><i class="mdi mdi-menu-down"></i><span>68.8</span></p>
                            </div>
                            <div class="d-none d-md-block" style=" width: 120px; display: inline-block;">
                              <p class="statistics-title" style = "margin-top: 0.5px;">Asia Time Zone </p>
                              <h3 
                              		class="rate-percentage"> <h3 id="clock" style="font-weight: bold; font-size: 24px; margin-top: 10px;"></h3> 
                              </h3>
                              <p class="text-success d-flex"><i class="mdi mdi-menu-down"></i><span>+0.8%</span></p>
                            </div>
                            <%-- <div class="d-none d-md-block"  style=" width: 140px; display: inline-block;">
                              <p class="statistics-title" style = "margin-top: -1px;" >Date</p>
                              <h2 class="rate-percentage" style="font-weight: bold; font-size: 24px; margin-top: 1.5px;"><%= request.getAttribute("date") %></h2>
                              <p class="text-danger d-flex"><i class="mdi mdi-menu-down"></i><span>68.8</span></p>
                            </div> --%>
                            <div class="d-none d-md-block" style=" width: 150px; display: inline-block;">
                              <p class="statistics-title" style = "margin-top: -5px;">Avg. Time on Site</p>
                              <h3 class="rate-percentage" style="font-weight: bold; font-size: 24px; margin-top: -1px;" id= "timeOnSite" >0 min 0 sec</h3>
                              <p class="text-success d-flex"><i class="mdi mdi-menu-down"></i><span>+0.8%</span></p>
                            </div>
                          </div>
                        </div>
                      </div>
                      <%-- <div class="row">
                        <div class="col-lg-8 d-flex flex-column">
                          <div class="row flex-grow">
                            <div class="col-12 col-lg-4 col-lg-12 grid-margin stretch-card">
                              <div class="card card-rounded">
                                <div class="card-body">
                                  <div class="d-sm-flex justify-content-between align-items-start">
                                    <div>
                                      <h4 class="card-title card-title-dash">Performance Line Chart</h4>
                                      <h5 class="card-subtitle card-subtitle-dash">Lorem Ipsum is simply dummy text of the printing</h5>
                                    </div>
                                    <div id="performanceLine-legend"></div>
                                  </div>
                                  <div class="chartjs-wrapper mt-4">
                                    <canvas id="performanceLine" width=""></canvas>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div> --%>
                      
               <div class="col-lg-12 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                      <div class="d-flex justify-content-between align-items-center">
                    	<h4 class="card-title"><i class="fa-solid fa-chart-pie"></i> Budget Tracker</h4>
  						<a href="#" class="btn btn-primary text-white me-0" data-bs-toggle="modal" data-bs-target="#budgetModal">
    					<i class="fa-solid fa-wallet"></i> Set Budget
  						</a>
					  </div>
                     <div class="table-responsive">
                      <table class="table table-striped">
                        <thead>
                          <tr>
                            <th> Name </th>
                            <th> Budget %</th>
                            <th> Monthly Budget </th>
                            <th> Current Month </th>
                            <th> Remaining </th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <c:forEach var="budget" items="${budgetData}">
								<tr>
									<td>
									<strong>${budget.name}</strong>
									</td>
                					<td>
                					${budget.budgetPercentage}%
                                           <%--  <div>
                                              <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                <p class="text-success">${budget.budgetPercentage}%</p>
                                              </div>
                                              <div class="progress progress-md">
                                                <div class="progress-bar bg-success" role="progressbar" style="width: 85%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                                              </div>
                                            </div> --%>
                                   </td>
                                   
                					<td>${budget.allocatedAmount}</td>
                					<td>${budget.currentExpense}</td>
                					<td>${budget.remaining}</td>
								</tr>
							</c:forEach>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              
              <div class="modal fade" id="budgetModal" tabindex="-1" aria-labelledby="budgetModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="budgetModalLabel">Set Your Budget</h5>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				      </div>
				      <div class="modal-body">
				        <!-- Budget Form -->
				        <form class="forms-sample material-form" method="post" action= "BudgetTrackerServlet" >
				          <div class="mb-3">
				            <label for="budgetName" class="form-label">Budget Category</label>
				            <input type="text" class="form-control" id="budgetName" name="budget" placeholder="Category">
				          </div>
				          <div class="mb-3">
				            <label for="budgetAmount" class="form-label">Budget Percentage</label>
				            <input type="text" class="form-control" id="budgetAmount" name="budgetPer" placeholder="Budget Percentage">
				          </div>
				         <div class="button-container">
                        	<button type="submit" class="button btn btn-primary"><span>Submit</span></button>
                    	</div>
				        </form>
				      </div>
				    </div>
				  </div>
				</div>
                    
                      
          	<div class = "row">
                 
               <div class="col-lg-6 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">Bank Account</h4>
                    <form class="forms-sample material-form" method = "post" action= "BankAccountServlet">                  
                      <div class="input-group mb-2 mr-sm-2">
                        <div class="input-group-prepend">
						<div class="input-group-text">💳</div>                        
						</div>
                        <input type="text" class="form-control" placeholder="Bank Name" name= "bankName" required>
                      </div>
                     <div class="button-container">
                        <button type="submit" class="button btn btn-primary"><span>Submit</span></button>
                    </div>
                    </form>
                  </div>
                </div>
              </div>
              
              <div class="col-lg-6 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title"><i class="fa-solid fa-building-columns"></i> Active Accounts</h4>               
                    <div class="table-responsive">
                      <table class="table">
                        <thead>
                          <tr>
                           <!--  <th>Account Name</th> -->
                          <!--   <th>Open Date</th> -->
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                              <c:forEach var="bank" items="${bankName}">
        							<td><label class="badge badge-warning">${bank.name}</label></td>
        							<%-- <td>${bank.accountDate}</td> --%>
    					      </c:forEach>
                          <!--   <td><label class="badge badge-warning">In progress</label></td> -->
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
                 
          		<div class="col-md-6 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title"><i class="fa-solid fa-money-bill-wave"></i> Expense Source</h4>
                    <form class="forms-sample material-form" method= "post" action= "IncomeExpenseServlet">
                    <div class="form-group" >
                      <label >Your Source</label>
                      <input type="text" class="form-control" required= "required" placeholder="Source" aria-label="Source" name = "expenseSrc" style= "border: 1px solid #d3d3d3; padding: 5px; border-radius: 4px; height: 40px;  padding-left: 15px;">
                    </div>
                    <div class="form-group">
                      <label>Link your expense to one of your budgets</label>
                      <select class="js-example-basic-single w-100" name= "budgetTracker" required= "required" style="border: 1px solid #d3d3d3; padding: 5px; border-radius: 4px; height: 35px;">
                         <c:forEach var="budget" items="${budgetName}">
        						<option>${budget.name}</option>
    					</c:forEach>
                      </select>
                    </div>
                    <div class="button-container"  style="text-align: right;">
                        <button type="submit" class="button btn btn-primary"><span>Submit</span></button>
                    </div>
                    </form>
                  </div>
                </div>
              </div>
              
              <div class="col-md-6 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title"><i class="fa-solid fa-credit-card"></i> Income Source</h4>
                    <form class="forms-sample material-form" method= "post" action= "IncomeExpenseServlet">
                    <div class="form-group">
                      <label>Your Source</label>
                      <input type="text" class="form-control" required= "required" placeholder="Source" aria-label="Source" name= "incomeSrc" style= "border: 1px solid #d3d3d3; padding: 5px; border-radius: 4px; height: 40px; padding-left: 15px;">
                    </div>
                  	<div class="button-container" style="text-align: right;">
                        <button type="submit" class="button btn btn-primary"><span>Submit</span></button>
                    </div>
                    </form>
                  </div>
                </div>
              </div>               
                          <!-- <div class="row flex-grow">
                            <div class="col-12 grid-margin stretch-card">
                              <div class="card card-rounded">
                                <div class="card-body">
                                  <div class="d-sm-flex justify-content-between align-items-start">
                                    <div>
                                      <h4 class="card-title card-title-dash">Pending Requests</h4>
                                      <p class="card-subtitle card-subtitle-dash">You have 50+ new requests</p>
                                    </div>
                                    <div>
                                      <button class="btn btn-primary btn-lg text-white mb-0 me-0" type="button"><i class="mdi mdi-account-plus"></i>Add new member</button>
                                    </div>
                                  </div>
                                  <div class="table-responsive  mt-1">
                                    <table class="table select-table">
                                      <thead>
                                        <tr>
                                          <th>
                                            <div class="form-check form-check-flat mt-0">
                                              <label class="form-check-label">
                                                <input type="checkbox" class="form-check-input" aria-checked="false" id="check-all"><i class="input-helper"></i></label>
                                            </div>
                                          </th>
                                          <th>Customer</th>
                                          <th>Company</th>
                                          <th>Progress</th>
                                          <th>Status</th>
                                        </tr>
                                      </thead>
                                      <tbody>
                                        <tr>
                                          <td>
                                            <div class="form-check form-check-flat mt-0">
                                              <label class="form-check-label">
                                                <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="d-flex ">
                                              <img src="assets/images/faces/face1.jpg" alt="">
                                              <div>
                                                <h6>Brandon Washington</h6>
                                                <p>Head admin</p>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <h6>Company name 1</h6>
                                            <p>company type</p>
                                          </td>
                                          <td>
                                            <div>
                                              <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                <p class="text-success">79%</p>
                                                <p>85/162</p>
                                              </div>
                                              <div class="progress progress-md">
                                                <div class="progress-bar bg-success" role="progressbar" style="width: 85%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="badge badge-opacity-warning">In progress</div>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td>
                                            <div class="form-check form-check-flat mt-0">
                                              <label class="form-check-label">
                                                <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="d-flex">
                                              <img src="assets/images/faces/face2.jpg" alt="">
                                              <div>
                                                <h6>Laura Brooks</h6>
                                                <p>Head admin</p>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <h6>Company name 1</h6>
                                            <p>company type</p>
                                          </td>
                                          <td>
                                            <div>
                                              <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                <p class="text-success">65%</p>
                                                <p>85/162</p>
                                              </div>
                                              <div class="progress progress-md">
                                                <div class="progress-bar bg-success" role="progressbar" style="width: 65%" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"></div>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="badge badge-opacity-warning">In progress</div>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td>
                                            <div class="form-check form-check-flat mt-0">
                                              <label class="form-check-label">
                                                <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="d-flex">
                                              <img src="assets/images/faces/face3.jpg" alt="">
                                              <div>
                                                <h6>Wayne Murphy</h6>
                                                <p>Head admin</p>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <h6>Company name 1</h6>
                                            <p>company type</p>
                                          </td>
                                          <td>
                                            <div>
                                              <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                <p class="text-success">65%</p>
                                                <p>85/162</p>
                                              </div>
                                              <div class="progress progress-md">
                                                <div class="progress-bar bg-warning" role="progressbar" style="width: 38%" aria-valuenow="38" aria-valuemin="0" aria-valuemax="100"></div>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="badge badge-opacity-warning">In progress</div>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td>
                                            <div class="form-check form-check-flat mt-0">
                                              <label class="form-check-label">
                                                <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="d-flex">
                                              <img src="assets/images/faces/face4.jpg" alt="">
                                              <div>
                                                <h6>Matthew Bailey</h6>
                                                <p>Head admin</p>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <h6>Company name 1</h6>
                                            <p>company type</p>
                                          </td>
                                          <td>
                                            <div>
                                              <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                <p class="text-success">65%</p>
                                                <p>85/162</p>
                                              </div>
                                              <div class="progress progress-md">
                                                <div class="progress-bar bg-danger" role="progressbar" style="width: 15%" aria-valuenow="15" aria-valuemin="0" aria-valuemax="100"></div>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="badge badge-opacity-danger">Pending</div>
                                          </td>
                                        </tr>
                                        <tr>
                                          <td>
                                            <div class="form-check form-check-flat mt-0">
                                              <label class="form-check-label">
                                                <input type="checkbox" class="form-check-input" aria-checked="false"><i class="input-helper"></i></label>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="d-flex">
                                              <img src="assets/images/faces/face5.jpg" alt="">
                                              <div>
                                                <h6>Katherine Butler</h6>
                                                <p>Head admin</p>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <h6>Company name 1</h6>
                                            <p>company type</p>
                                          </td>
                                          <td>
                                            <div>
                                              <div class="d-flex justify-content-between align-items-center mb-1 max-width-progress-wrap">
                                                <p class="text-success">65%</p>
                                                <p>85/162</p>
                                              </div>
                                              <div class="progress progress-md">
                                                <div class="progress-bar bg-success" role="progressbar" style="width: 65%" aria-valuenow="65" aria-valuemin="0" aria-valuemax="100"></div>
                                              </div>
                                            </div>
                                          </td>
                                          <td>
                                            <div class="badge badge-opacity-success">Completed</div>
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div> -->
							<!-- <div class="row flex-grow">
                            <div class="col-md-6 col-lg-6 grid-margin stretch-card">
                              <div class="card card-rounded">
                                <div class="card-body card-rounded">
                                  <h4 class="card-title  card-title-dash">Recent Events</h4>
                                  <div class="list align-items-center border-bottom py-2">
                                    <div class="wrapper w-100">
                                      <p class="mb-2 fw-medium"> Change in Directors </p>
                                      <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center">
                                          <i class="mdi mdi-calendar text-muted me-1"></i>
                                          <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="list align-items-center border-bottom py-2">
                                    <div class="wrapper w-100">
                                      <p class="mb-2 fw-medium"> Other Events </p>
                                      <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center">
                                          <i class="mdi mdi-calendar text-muted me-1"></i>
                                          <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="list align-items-center border-bottom py-2">
                                    <div class="wrapper w-100">
                                      <p class="mb-2 fw-medium"> Quarterly Report </p>
                                      <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center">
                                          <i class="mdi mdi-calendar text-muted me-1"></i>
                                          <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="list align-items-center border-bottom py-2">
                                    <div class="wrapper w-100">
                                      <p class="mb-2 fw-medium"> Change in Directors </p>
                                      <div class="d-flex justify-content-between align-items-center">
                                        <div class="d-flex align-items-center">
                                          <i class="mdi mdi-calendar text-muted me-1"></i>
                                          <p class="mb-0 text-small text-muted">Mar 14, 2019</p>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                  <div class="list align-items-center pt-3">
                                    <div class="wrapper w-100">
                                      <p class="mb-0">
                                        <a href="#" class="fw-bold text-primary">Show all <i class="mdi mdi-arrow-right ms-2"></i></a>
                                      </p>
                                    </div>
                                  </div>
                                </div>
                              </div> -->
                          </div>
                        </div>

                        <div class="tab-pane fade" id="audiences" role="tabpanel" aria-labelledby="profile-tab">
                         <div class="col-lg-12 grid-margin stretch-card">
                			<div class="card">
                  				<div class="card-body">
									<div class="d-flex justify-content-between align-items-center">
                    					<h4 class="card-title"><i class="mdi mdi-arrow-up-bold"></i> Income Overview</h4>
					  						<a href="#" class="btn btn-primary text-white me-0" data-bs-toggle="modal" data-bs-target="#IncomeModal">
					    						<i class="mdi mdi-cash-check"></i> Set Monthly Goal
					  						</a>
										</div>
										<table class="table table-striped">
											<thead>
												<tr>
													<th>Name</th>
													<th>Current Month</th>
													<th>Monthly Goal</th>
													<th>Remaining</th>
													<th>Progress</th>
												</tr>
											</thead>
											<tbody>
												<tr>
		<%
			BudgetTrackerDAO budgetTrackerDAO  = new BudgetTrackerDAOImpl();
            try {
                List<Object[]> budgetTracker = budgetTrackerDAO.displayIncome();
                if (budgetTracker == null || budgetTracker.isEmpty()) {
                    throw new DataAccessException("No Income Found!");
                }
                for (Object[] record : budgetTracker) {
                    String name = (String) record[0];
                    Double currentMonth = (Double) record[1];
                    Double monthlyGoal = (Double) record[2];
                    Double remaining = 0.0;
                    Double progress = 0.0;
                    
                    if (monthlyGoal != null && currentMonth != null) {
                        remaining = monthlyGoal - currentMonth;
                        progress = (currentMonth / monthlyGoal) * 100;
                    }
                    if (monthlyGoal == null) {
                        monthlyGoal = 0.0;
                    }
        %>
        <tr>
            <td><%= name %></td>
            <td><%= currentMonth %></td>
            <td><%= monthlyGoal %></td>
            <td><%= remaining %></td>
            <td><%= String.format("%.2f", progress) %></td>
        </tr>
        <%
                }
            } catch (DataAccessException e) {
        %>
        <tr>
            <td colspan="5" style="color: red; text-align: center;">Error: <%= e.getMessage() %></td>
        </tr>
        <%
            } catch (Exception e) {
                e.printStackTrace();
        %>
        <tr>
            <td colspan="5" style="color: red; text-align: center;">An error occurred while fetching data.</td>
        </tr>
        <%
            }
        %>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		 <div class="modal fade" id="IncomeModal" tabindex="-1" aria-labelledby="IncomeModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="IncomeModalLabel">Set your Monthly Goal</h5>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				      </div>
				      <div class="modal-body">
				        <!-- Budget Form -->
				        <form class="forms-sample material-form" method="post" action= "BudgetTrackerServlet" >
				          <div class="mb-3">
				            <label for="budgetName" class="form-label">Income Source</label>
				            <input type="text" class="form-control" id="budgetName" name="incomeSource" placeholder="Source">
				          </div>
				          <div class="mb-3">
				            <label for="budgetAmount" class="form-label">Amount</label>
				            <input type="text" class="form-control" id="budgetAmount" name="monthlyGoal" placeholder="Enter Goal">
				          </div>
				         <div class="button-container">
                        	<button type="submit" class="button btn btn-primary"><span>Submit</span></button>
                    	</div>
				        </form>
				      </div>
				    </div>
				  </div>
				</div>
			</div>
								
				<div class="tab-pane fade" id="demographics" role="tabpanel" aria-labelledby="contact-tab">
                    <div class="col-lg-12 grid-margin stretch-card">
           			<div class="card">
             		<div class="card-body">
					<div class="d-flex justify-content-between align-items-center">
               			<h4 class="card-title"><i class="mdi mdi-arrow-down-bold"></i> Expense Overview</h4>
  						<a href="#" class="btn btn-primary text-white me-0" data-bs-toggle="modal" data-bs-target="#ExpenseModal">
    						<i class="mdi mdi-finance"></i>Set Monthly Budget
  						</a>
					</div>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Name</th>
								<th>Current Month</th>
								<th>Monthly Budget</th>
								<th>Remaining</th>
								<th>Progress</th>
								<th>Budget Tracker</th>
							</tr>
						</thead>
						<tbody>								
		<%
            try {
                List<Object[]> budgetTracker = budgetTrackerDAO.displayExpense();
                if (budgetTracker == null || budgetTracker.isEmpty()) {
                    throw new DataAccessException("NO Expense Found!");
                }
                for (Object[] record : budgetTracker) {
                    String name = (String) record[0];
                    Double currentMonth = (Double) record[1];
                    Double monthlyBudget = (Double) record[2];
                    String budgetName = (String) record[3];
                    Double remaining = 0.0;
                    Double progress = 0.0;
                    
                    if (monthlyBudget != null && currentMonth != null) {
                        remaining = monthlyBudget + currentMonth;
                        progress = (Math.abs(currentMonth) / monthlyBudget) * 100;
                    }
        %>
        <tr>
            <td><%= name %></td>
            <td><%= Math.abs(currentMonth) %></td>
            <td><%= monthlyBudget %></td>
            <td><%= remaining %></td>
            <td><%= String.format("%.2f", progress) %></td>
            <td><%= budgetName %></td>
        </tr>
        <%
                }
            } catch (DataAccessException e) {
        %>
        <tr>
            <td colspan="6" style="color: red; text-align: center;">Error: <%= e.getMessage() %></td>
        </tr>
        <%
            } catch (Exception e) {
                e.printStackTrace();
        %>
        <tr>
            <td colspan="6" style="color: red; text-align: center;">An error occurred while fetching data.</td>
        </tr>
        <%
            }
        %>
							</tbody>
					</table>
				</div>
			</div>
		</div>
			<div class="modal fade" id="ExpenseModal" tabindex="-1" aria-labelledby="ExpenseModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="ExpenseModalLabel">Set your Monthly Goal</h5>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				      </div>
				      <div class="modal-body">
				        <!-- Budget Form -->
				        <form class="forms-sample material-form" method="post" action= "BudgetTrackerServlet" >
				          <div class="mb-3">
				            <label for="budgetName" class="form-label">Expense Source</label>
				            <input type="text" class="form-control" id="budgetName" name="expenseSource" placeholder="Source">
				          </div>
				          <div class="mb-3">
				            <label for="budgetAmount" class="form-label">Amount</label>
				            <input type="text" class="form-control" id="budgetAmount" name="monthlyBudget" placeholder="Enter Goal">
				          </div>
				         <div class="button-container">
                        	<button type="submit" class="button btn btn-primary"><span>Submit</span></button>
                    	</div>
				        </form>
				      </div>
				    </div>
				  </div>
				</div>
				
				<div class="col-lg-6 grid-margin stretch-card">
                <div class="card">
                  <div class="card-body">
                    <h4 class="card-title">Linked Expense Sources</h4>
                    <div class="table-responsive">
                      <table class="table">
                        <thead>
                          <tr>
                            <th>Expense Name</th>
                            <th>Expense Source</th>
                          </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="source" items="${expenseSource}">
								<tr>
									<td>${source[0]}</td>
                					<td>${source[1]}</td>
                				</tr>
                			</c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
			
			</div>
                        
                 
               </div>
             </div>
           </div>
         </div>
       </div>
     </div>
   </div>
          <!-- content-wrapper ends -->
          <!-- partial:partials/_footer.html -->
          <footer class="footer">
            <div class="d-sm-flex justify-content-center justify-content-sm-between">
              <span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Premium <a href="https://www.bootstrapdash.com/" target="_blank">Bootstrap admin template</a> from BootstrapDash.</span>
              <span class="float-none float-sm-end d-block mt-1 mt-sm-0 text-center">Copyright © 2023. All rights reserved.</span>
            </div>
          </footer>
          <!-- partial -->
        </div>
        <!-- main-panel ends -->
      </div>
      <!-- page-body-wrapper ends -->
    </div>
    <!-- container-scroller -->
    <!-- plugins:js -->
    <script src="assets/vendors/js/vendor.bundle.base.js"></script>
    <script src="assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <!-- endinject -->
    <!-- Plugin js for this page -->
    <script src="assets/vendors/chart.js/chart.umd.js"></script>
    <script src="assets/vendors/progressbar.js/progressbar.min.js"></script>
    <!-- End plugin js for this page -->
    <!-- inject:js -->
    <script src="assets/js/off-canvas.js"></script>
    <script src="assets/js/template.js"></script>
    <script src="assets/js/settings.js"></script>
    <script src="assets/js/hoverable-collapse.js"></script>
    <script src="assets/js/todolist.js"></script>
    <!-- endinject -->
    <!-- Custom js for this page-->
    <script src="assets/js/jquery.cookie.js" type="text/javascript"></script>
    <script src="assets/js/dashboard.js"></script>
    
    <!-- Server time -->
    
    <%-- <script>
    let serverTime = "<%= serverTime %>";
    let parts = serverTime.split(":");
    let hours = parseInt(parts[0]);
    let minutes = parseInt(parts[1]);
    let seconds = parseInt(parts[2]);

    function updateClock() {
        seconds++;
        if (seconds == 60) { seconds = 0; minutes++; }
        if (minutes == 60) { minutes = 0; hours++; }
        if (hours == 24) { hours = 0; }

        let timeString = 
            (hours < 10 ? "0" : "") + hours + ":" +
            (minutes < 10 ? "0" : "") + minutes + ":" +
            (seconds < 10 ? "0" : "") + seconds;

        document.getElementById("clock").innerHTML = timeString;
        setTimeout(updateClock, 1000);
    }
</script> --%>

 <script>
        function updateClock() {
            let now = new Date();
            let hours = now.getHours();
            let minutes = now.getMinutes();
            let seconds = now.getSeconds();

            // Format time with leading zeros
            hours = hours < 10 ? "0" + hours : hours;
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            let timeString = hours + ":" + minutes + ":" + seconds;
            document.getElementById("clock").innerHTML = timeString;

            // Update every second
            setTimeout(updateClock, 1000);
        }
    </script>
    
    <script>
    let startTime = Date.now(); // Capture start time

    function getTimeSpent() {
        let timeSpent = Math.floor((Date.now() - startTime) / 1000); // Seconds
        let minutes = Math.floor(timeSpent / 60);
        let seconds = timeSpent % 60;
        document.getElementById("timeOnSite").innerText = minutes + " min " + seconds + " sec";
    }

    setInterval(getTimeSpent, 1000); // Update every second
</script>
    
    <script src="assets/js/Chart.roundedBarCharts.js"></script>
    <!-- End custom js for this page-->
       <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
  $(document).ready(function () {
    $('#expenseForm').on('submit', function (e) {
      e.preventDefault();

      $.ajax({
        url: 'ExpenseServlet',
        type: 'POST',
        data: $(this).serialize(),
        success: function (responseText) {
         
          alert("Success: " + responseText);
          $('#expenseTableContainer').load(window.location.href + ' #expenseTableContainer>*', '');
          $('#expenseForm')[0].reset(); m
        },
        error: function (xhr) {
        	alert("Error: " + xhr.responseText); 
        }
      });
    });
  });
</script>
  </body>
</html>