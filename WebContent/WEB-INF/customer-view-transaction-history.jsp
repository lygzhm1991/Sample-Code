<jsp:include page="customer-top.jsp" />

<%-- <jsp:include page="template-top.jsp" /> --%>

<%-- <jsp:include page="customersidebar.jsp" /> --%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Transaction history</h3>
	</div>
			
			<jsp:include page="template-result.jsp" />
			
			<div class="panel-body">
		<div class="col-sm-12">
			<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
			<c:choose>
				<c:when test="${transactions ==null}">
					<h5>No transactions.</h5>
				</c:when>
				<c:otherwise>
					<h5>Transaction information</h5>
					<table class="table table-striped">
						<thead >
							<tr >
								<th >Date</th>
								<th >Fund name</th>
								<th style="text-align:right">Shares</th>
								<th style="text-align:right">Price</th>
								<th style="text-align:right">Amount</th>
								<th></th>
								<th >Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="transaction" items="${transactions}">
								<tr>
									<td >${transaction.getDate()}</td>
									<td >${transaction.getFundName()}</td>
									<td align="right">${transaction.getShare()}</td>
									<td align="right">${transaction.getPrice()}</td>
									<td align="right">${transaction.getAmount()}</td>
									<td></td>
									<td >${transaction.getType()}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-4 column"></div>
	</div>

<jsp:include page="bottom.jsp" />

