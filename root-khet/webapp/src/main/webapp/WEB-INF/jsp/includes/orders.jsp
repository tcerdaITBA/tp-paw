<div class="row">
	<div class="col-md-10 col-md-offset-1">
		<!--TODO: hacer con la página actual, no con category-->
		<div class="order-selection">
			<c:set var="selected" value="${productOrder == 'recent'}"/>
			<a class="order-btn ${selected ? 'selected':''}" href="<c:url value="?orderBy=recent"/>">
				<spring:message code="index.order.recent"/>
			</a>
			<span class="order-divider"></span>
			<c:set var="selected" value="${productOrder == 'popularity'}"/>
			<a class="order-btn ${selected ? 'selected':''}" href="<c:url value="?orderBy=popularity"/>">
				<spring:message code="index.order.popular"/>
			</a>
			<span class="order-divider"></span>
			<c:set var="selected" value="${productOrder == 'alphabethically'}"/>
			<a class="order-btn ${selected ? 'selected':''}" href="<c:url value="?orderBy=alphabethically"/>">
				<spring:message code="index.order.atoz"/>
			</a>
		</div>
	</div>
</div>