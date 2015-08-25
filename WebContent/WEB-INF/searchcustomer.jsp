<jsp:include page="employee-top.jsp" />


<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="panel-heading">
	<h3 class="panel-title">Search Customer</h3>
</div>
<div class="panel-body">
	<div class="search">
		<h3>Please type in customer name to find customer</h3>
		<jsp:include page="template-result.jsp" />
		<br>
		<form class="form-inline">
			<div class="col-sm-8">
				<input type="text" class="form-control" name="keyword"
					placeholder="Search for...">
			</div>
			<button class="btn btn-primary" type="submit">Search</button>
		</form>
		<br>
		<hr>
	</div>
	<div class="search_result">
		<c:choose>
			<c:when test="${customers !=null}">
				<h3>Search Result:</h3>
				<br>
				<c:if test="${fn:length(customers) == 0}">
					No results matched
				</c:if>
				<c:if test="${fn:length(customers) != 0}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>User Name</th>
								<th>First Name</th>
								<th>Last Name</th>
								<th>Details</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customer" items="${customers}">
								<tr>
									<td>${customer.getUserName() }</td>
									<td>${customer.getFirstName() }</td>
									<td>${customer.getLastName() }</td>
									<td><a
										href="employee_view_customer.do?username=${customer.getUserName()}">view</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</c:when>
			<%-- <c:when test=" ">
					<div class="alert alert-dismissable alert-warning">
				 		<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
						<h4><strong>No Results Found!</strong>Please Try Again...</h4>
					</div>
				</c:when>
				<c:otherwise>
				</c:otherwise> --%>
		</c:choose>
	</div>
</div>



<jsp:include page="bottom.jsp" />

