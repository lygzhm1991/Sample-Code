

<jsp:include page="employee-top.jsp" />
		<div class="panel-heading">
			<h3 class="panel-title">Transaction History</h3>
		</div><br>
		<div class="panel-body">


			<h3>
				Transaction history for <a
					href="employee_view_customer.do?username=${customer.getUserName()}">${customer.getUserName()}</a>
			</h3>
			<jsp:include page="template-result.jsp" />
			<br></br>
			<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
			<c:choose>
				<c:when test="${transactions ==null}">
					<h5>No transactions.</h5>
				</c:when>
				<c:otherwise>
					<table class="table">
						<thead>
							<tr>
								<th>Date</th>
								<th>Fund name</th>
								<th style="text-align:right">Shares</th>
								<th style="text-align:right">Price</th>
								<th style="text-align:right">Amount</th>
								<th></th>
								<th>Type</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="transaction" items="${transactions}">
								<tr>
									<td>${transaction.getDate()}</td>
									<td>${transaction.getFundName()}</td>
									<td style="text-align:right">${transaction.getShare()}</td>
									<td style="text-align:right">${transaction.getPrice()}</td>
									<td style="text-align:right">${transaction.getAmount()}</td>
									<td></td>
									<td>${transaction.getType()}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
<jsp:include page="template-bottom.jsp" />

