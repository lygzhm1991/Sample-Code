

<jsp:include page="employee-top.jsp" />

<div class="panel-heading">
			<h3 class="panel-title">Deposit Check</h3>
		</div><jsp:include page="template-result.jsp" /><br>
<div class="panel-body">
<div class="col-md-8 column">
			<h3>
				Deposit check for <a
					href="employee_view_customer.do?username=${customer.getUserName()}">${customer.getUserName()}</a>
			</h3>
			<br>
			
			<form role="form">
				<div class="form-group">
					<input type="hidden" name="userName"
						value="${customer.getUserName()}"> <label
						for="exampleInputEmail1">Amount</label>
						<input type="text"
						class="form-control" name="amount">
						<p style="color: grey"> *Please enter a number with no more than 2 digits after the decimal </p>
						
				</div>
				<button type="submit" class="btn btn-primary">Submit</button>
				
			</form>
			<br>
			<br>
			
</div>
</div>
<jsp:include page="template-bottom.jsp" />

