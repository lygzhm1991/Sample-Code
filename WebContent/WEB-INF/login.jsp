<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Blossom Mutual Funds</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!--link rel="stylesheet/less" href="less/bootstrap.less" type="text/css" /-->
<!--link rel="stylesheet/less" href="less/responsive.less" type="text/css" /-->
<!--script src="js/less-1.3.3.min.js"></script-->
<!--append ‘#!watch’ to the browser URL, then refresh the page. -->

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">

<style>
body {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
}

h1, h2, h3, h4 {
	font-family: Tahoma, Geneva, sans-serif;
	font-weight: normal;
}
</style>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

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
</head>

<body>
	<div class="container">
		<br /> <br /> <br />
	</div>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column" style="margin-left:90px">
					<div class="header"
						style="margin-top: 50px; font-weight: 900; font-size: 20px">
						<img alt="Brand" src="img/logo.png"
							style="width: 35px; height: 35px">Blossom Mutual Funds
					</div>
			</div>
		</div><hr>
	</div>
	<div><br><br></div>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="row clearfix">
				<div class="col-md-1 column"></div>
				<div class="col-md-10 column">
				<c:if test="${errors !=null && fn:length(errors) > 0}">
						<div class="alert alert-warning">
							<h4><img src="img/Warning.png" style="width: 20px; height: 20px">
							There was a problem with your request</h4>
	
							<c:forEach var="error" items="${errors}">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error}<br>
							</c:forEach>
						</div>
				</c:if>
			</div>
			<div class="col-md-1 column"></div>
					<div class="col-md-6 column">
						<div class="row clearfix">
							<div class="col-md-2 column"></div>
							<div class="col-md-8 column">
								<div class="form">
									<form role="form" action="login.do">
										<div class="form-group">
											<label for="exampleInputEmail1">User Name/Email</label> <input
												type="text" class="form-control" name="userName">
										</div>
										<div class="form-group">
											<label for="exampleInputPassword1">Password</label><input
												type="password" class="form-control" name="password">
										</div>
										<button type="submit" class="btn btn-primary" name="action"
											value="customer_login">Customer Login</button>
									</form>
								</div>
							</div>
							<div class="col-md-2 column"></div>
						</div>
					</div>
					<div class="col-md-6 column">
						<div class="row clearfix">
							<div class="col-md-2 column"></div>
							<div class="col-md-8 column">
								<form role="form" action="login.do">
									<div class="form-group">
										<label for="exampleInputEmail1">User Name/Email</label> <input
											type="text" class="form-control" name="userName">
									</div>
									<div class="form-group">
										<label for="exampleInputPassword1">Password</label><input
											type="password" class="form-control" name="password">
									</div>
									<button type="submit" class="btn btn-info" name="action"
										value="employee_login">Employee Login</button>
								</form>
							</div>
							<div class="col-md-2 column"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<br /> <br /> <br />
		<hr />
	</div>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<p style="text-align: center">
					<small>Copyright &copy; 2015 The Blossom Group, Inc </small>
				</p>
			</div>
		</div>
	</div>
</body>
</html>