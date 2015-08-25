<jsp:include page="customer-top.jsp" />

<%-- <jsp:include page="template-top.jsp" />
<jsp:include page="customersidebar.jsp" /> --%>
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Buy Fund</h3>
	</div>
	<jsp:include page="template-result.jsp" />
	<div class="panel-body">
		<p>
			Your available Balance: <strong>$ ${validAmount}</strong>.
		</p>
		<p>
			Your Cash Balance: <strong>$ ${currentAmount}</strong>.
		</p>
		<br>
		<form class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-2 control-label"> Fund Name: </label>
				<div class="col-sm-10">
					<a href="customer_fund_detail.do?fundId=${fund.getId()}">${fund.getName() }</a>
				</div>
			</div>
			<div class="form-group">
				<input type="hidden" style="width: 30%" name="fundId"
					value="${fund.getId()}"> <label
					class="col-sm-2 control-label"> Amount($): </label>
					
				<div class="col-sm-8">
					<input type="text" class="form-control" name="amount">
				</div>
			</div>
			<div class="col-sm-offset-2">
			      <p style="color:grey"> *Please enter a number with no more than 2 digits after the decimal </p>
			</div>
			<br>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-success" name="action"
						value="buy">Buy Now!</button>
				</div>
			</div>
		</form>
		<br></br>
		<br>
	</div>


<jsp:include page="bottom.jsp" />

