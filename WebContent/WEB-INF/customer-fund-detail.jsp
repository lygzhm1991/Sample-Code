
<%@page import="databeans.Price"%>

<jsp:include page="customer-top.jsp" />

<div class="panel panel-primary">
	<div class="panel-heading">
		<h3 class="panel-title">Fund Detail</h3>
	</div>
	<jsp:include page="template-result.jsp" />

	<div class="panel-body">
			<div class="form-group">
				<div class="col-md-12 column">
				<div class="row clearfix">
				<div class="col-md-1 column"></div>
				<div class="col-md-8 column">
				<h4> Fund Name:   ${fund.getName()}</h4>                 
		       <h4>Fund Ticker:   ${fund.getTicker()}</h4>
		       </div>
				<div class=" col-md-2 column">
				<br>
				<form role="form" action="customer-buy-fund.do">
					<input type="hidden" name="fundId" value="${fund.getId() }">
					<button type="submit" class="btn btn-success">Buy Now!</button>
				</form>
				</div>
				<div class="col-md-1 column"></div>
				</div>
			</div>
	    <hr>
		<div class="col-sm-11">
			<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
			<c:choose>
				<c:when test="${prices ==null}">
					<h5>No price history.</h5>
				</c:when>
				<c:otherwise>
					<script type="text/javascript">
						$(function() {
							var fundName = "${fund.getName()}";
							var dates = new Array();
							var prices = new Array();
							<%Price[] archiveNameArr = (Price[]) request.getAttribute("prices");
								for (int i = 0; i < archiveNameArr.length; i++) {%>
									dates[<%=i%>] = "<%=archiveNameArr[i].getDateMMDDYYYY()%>";
									prices[<%=i%>] = <%=archiveNameArr[i].getPriceValue()%>;
							<%}%>
						$('#containerChart').highcharts(
									{
										title : {
											text : 'Price History of '
													+ fundName,
											x : -20
										},
										xAxis : {
											categories :dates,
			
											labels:{
												maxStaggerLines: 1,
												step: Math.floor((dates.length+4)/5)
											}
										},
										yAxis : {
											title : {
												text : 'Price (USD)'
											},
											plotLines : [ {
												value : 0,
												width : 1,
												color : '#808080'
											} ]
										},
										tooltip : {
											valuePrefix : '$'
										},
										legend : {
											enabled: false
										},
										series : [ {
											name : fundName,
											data : prices
										} ],
										credits: {
							                enabled: false
							            }
									});
						});
					</script>
					<div id="containerChart"
						style="min-width: 100px; height: 400px; margin: 0 auto"></div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-4 column"></div>
	</div>
</div>
<jsp:include page="bottom.jsp" />

