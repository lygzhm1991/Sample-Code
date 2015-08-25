<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<dl>
	<dt>Profile</dt>
	<dd>${customer.getFirstName()}&nbsp;${customer.getLastName()}</dd>
	<dd>
		<c:if test="${customer.getAddrLine1() !=null}">${customer.getAddrLine1()}
		</c:if>
		<c:if test="${customer.getAddrLine2() !=null && customer.getAddrLine2().length() != 0}">
			<br></br>${customer.getAddrLine2()}
		</c:if>
	</dd>
	<dd>${customer.getCity()}&nbsp;${customer.getState()}&nbsp;${customer.getZip()}</dd>
	<dt>Cash Balance</dt>
	<dd>${currentAmount}</dd>
	<dt>Available Balance</dt>
	<dd>${validAmount}</dd>
</dl>