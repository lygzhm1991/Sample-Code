<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Blossom Mutual Funds</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">


<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<style>
body {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
}
h1,h2,h3,h4 {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
}

</style>

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="img/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="img/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="img/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="img/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="img/logo.png">

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<script type="text/javascript" src="js/highcharts.js"></script>
<script type="text/javascript" src="js/modules/exporting.js"></script>
</head>

<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="row clearfix">
					<div class="col-md-2 column"></div>
					<div class="col-md-8 column">
						<div class="row clearfix">
							<div class="col-md-6 column"
								style="margin-top: 50px; font-weight: 900; font-size: 20px">
								<img alt="Brand" src="img/logo.png"
									style="width: 35px; height: 35px">Blossom Mutual Funds
							</div>
							<div class="col-md-6 column"
								style="text-align: right; margin-top: 60px">
								<c:if test="${customer !=null}">
								Hello, ${customer.getUserName()}! &nbsp;
								<a type="button" href="logout.do" class="btn btn-primary btn-xs"
										role="button">Logout</a>
								</c:if>
							</div>
						</div>
						<br>
						<nav class="navbar navbar-default">
							<div class="container-fluid">
								<!-- Brand and toggle get grouped for better mobile display -->
								<div class="navbar-header">
									<button type="button" class="navbar-toggle collapsed"
										data-toggle="collapse"
										data-target="#bs-example-navbar-collapse-1">
										<span class="sr-only">Toggle navigation</span> <span
											class="icon-bar"></span> <span class="icon-bar"></span> <span
											class="icon-bar"></span>
									</button>
									<a class="navbar-brand" href="customer_viewaccount.do">My Account</a>
								</div>

								<!-- Collect the nav links, forms, and other content for toggling -->
								<div class="collapse navbar-collapse"
									id="bs-example-navbar-collapse-1">
									<ul class="nav navbar-nav">
										<li class="btn-group navbar-btn">
											<button type="button" class="btn btn-default dropdown-toggle"
												data-toggle="dropdown" aria-expanded="false">
												Account Management <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="customer_viewaccount.do">View Account Info</a></li>
												<li><a href="customer-change-pwd.do">Change Password</a></li>
												
											</ul>
										</li>
										<li>&nbsp;</li>
										<li class="btn-group navbar-btn">
											<button type="button" class="btn btn-default dropdown-toggle"
												data-toggle="dropdown" aria-expanded="false">
												Transaction Management <span class="caret"></span>
											</button>
											<ul class="dropdown-menu" role="menu">
												<li><a href="customer-request-check.do">Request Check</a></li>										
												<li><a href="customer-view-transactions.do">Transaction History</a></li>
												<li><a href="customer_search_fund.do">Search Fund</a></li>
											</ul>
										</li>
										<li>&nbsp;</li>
										
									</ul>

								</div>
								<!-- /.navbar-collapse -->
							</div>
							<!-- /.container-fluid -->
						</nav>
					
						<!--  <div class="panel panel-danger" style="margin-top: 10px">-->