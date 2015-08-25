
<jsp:include page="customer-top.jsp" />

<!-- <div class="row clearfix">
		<div class="col-md-8 column"> -->
<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Request Check</h3>
	</div>
	<!-- <h3>Request Check</h3> -->
	<jsp:include page="template-result.jsp" />
	<br></br>
	<div class="panel-body">
		<div class="col-sm-10">
			<p>
				Your Valid Balance: <strong>$ ${validAmount}</strong>.
			</p>
			<p>
				Your Current Balance: <strong>$ ${currentAmount}</strong>.
			</p>
			<br>
			<form role="form">
			<div class="form-group">
				 <label
					class="col-sm-2 control-label"> Amount($): 
					</label>					
				<div class="col-sm-10">
					<input type="text" class="form-control" name="amount">
				</div>
			</div>
			<div class="col-sm-offset-2">
			      <p style="color:grey"> *Please enter a number with no more than 2 digits after the decimal </p>
			      <p style="color: grey"> *Range between 0.01 and ${MAX_VALUE_OF_AMOUNT_INPUT_STRING}<p>
			</div>
				<button type="submit" class="btn btn-success" name="action"
					value="request">Submit</button>
			</form>
			<br></br>
			<br>
		</div>
	</div>
</div>
</div>




<jsp:include page="template-bottom.jsp" />