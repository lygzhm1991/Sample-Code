<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="employee-top.jsp" />

		<div class="panel-heading">
			<h3 class="panel-title">Create Customer Account</h3>
		</div><br>
		<div class="panel-body">
			<div>
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
			<div class="col-sm-6" style="margin-left:180px">
				<form role="form">
					<p>Creating New Customer Account? Register Below.</p><br>
					<div class="form-group">
						<label for="exampleInputEmail1">User Name</label><input type="text"
							class="form-control" name="userName">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">First Name</label><input
							type="text" class="form-control" name="firstName">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Last Name</label><input type="text"
							class="form-control" name="lastName">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Password</label><input
							type="password" class="form-control" name="password">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Confirm Password</label><input
							type="password" class="form-control" name="confirm">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Address Line 1</label><input type="text"
							class="form-control" name="addressLine1">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Address Line 2</label><input type="text"
							class="form-control" name="addressLine2">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">City</label><input type="text"
							class="form-control" name="city">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">State</label><input type="text"
							class="form-control" name="state">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Zip code</label><input type="text"
							class="form-control" name="zip">
					</div>
					<button type="submit" class="btn btn-primary" name="action"
						value="create">Create Customer Account</button>
				</form>
			</div>
		</div>

<jsp:include page="bottom.jsp" />

