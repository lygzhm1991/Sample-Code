

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:include page="employee-top.jsp" />

		<div class="panel-heading">
			<h3 class="panel-title">Update Transition Information</h3>
		</div>
		<div class="panel-body">
		<jsp:include page="template-result.jsp" /><br>
		<h4> Last Trading Day:    ${lastTransactionDay }</h4>
		<br>
			<form role="form">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Fund Name</th>
							<th>Fund Ticker</th>
							<th style="text-align:right">Current Price</th>
							<th> </th>
							<th >New Price ($0.01 ~ $10,000.00)</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="fund" items="${funds}">
							<tr>
								<td>${fund.getFundName()}</td>
								<td>${fund.getFundSymbol()}</td>
								<td align = "right">${fund.getPrice()}</td>
								<td> </td>
								<td><input type="hidden" name="ids"
									value="${fund.getFundId()}" /><input type="text" name="prices" value="${fund.getPrice()}"
									class="form-control"  /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<p style="color: grey"> *Please enter numbers with no more than 2 digits after the decimal </p>
				<br> 
				<br>
				<div class="form-group">
					<label for="exampleInputEmail1">New transition day</label><input
						type="date" class="form-control" name="date" min="${minNewTransactionDay }" max="2050-01-01" value ="${minNewTransactionDay }">
				</div>
				<button type="submit" class="btn btn-primary">Update</button>
			</form>
		</div>
		
<jsp:include page="bottom.jsp" />

