<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="employee-top.jsp" />

		<div class="panel-heading">
			<h3 class="panel-title">Create Fund</h3>
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
					<div class="form-group">
						<label for="exampleInputEmail1">Fund Name</label><input type="text"
							class="form-control" name="name">
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Ticker</label><input
							type="text" class="form-control" name="ticker">
					</div>
					<button type="submit" class="btn btn-primary" >Create New Fund</button>
				</form>
			</div>
		</div>
		
<jsp:include page="template-bottom.jsp" />

