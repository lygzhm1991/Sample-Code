<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<jsp:include page="customer-top.jsp" />

<div class="col-md-2 column"></div>
<div class="col-md-8 column"></div>
<!-- <div class="panel-heading"> -->
<!-- 	<h3 class="panel-title">Your Account Information</h3> -->
<!-- </div> -->


<div class="panel-body">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Your Profile</h3>
		</div>
		<div class="panel-body">
			<jsp:include page="template-result.jsp" />
			<jsp:include page="customer-profile.jsp" />
		</div>
	</div>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Your Fund</h3>
		</div>
		<div class="panel-body">
			<c:choose>
				<c:when test="${shareList ==null}">
					<h5>You don't have any funds currently.</h5>
				</c:when>
				<c:otherwise>
					<h5>Last trading day: ${lastTransactionDay}</h5>
					<table class="table">
						<thead>
							<tr>
								<th>Fund name</th>
								<th>Ticker</th>
								<th style="text-align:right">Shares</th>
								<th style="text-align:right">Estimated value</th>
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="share" items="${shareList}">
								<tr>
									<td><a href="customer_fund_detail.do?fundId=${share.getFundId()}">${share.getFundName() }</a></td>
									<td>${share.getFundTicker() }</td>
									<td align="right">${share.getShare()}</td>
									<td align="right">${share.getAmount()}</td>
									<td><a
										href="customer-sell-fund.do?fundId=${share.getFundId()}">Sell
									</a>&nbsp;<a
										href="customer-buy-fund.do?fundId=${share.getFundId()}">Buy
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>

<div class="col-md-2 column"></div> 

<!-- <div class="col-md-2 column"></div> -->
<jsp:include page="template-bottom.jsp" />

