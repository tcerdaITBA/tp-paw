<div class="row">
	<div class="col-md-10 col-md-offset-1">
		<div class="order-selection">
			<c:set var="selected" value="${productOrder == 'RECENT'}"/>
			<a class="order-btn ${selected ? 'selected':''}" href="<c:url value="?orderBy=recent${categoryQuery}"/>">
				<spring:message code="index.order.recent"/>
			</a>
			<span class="order-divider"></span>
			<c:set var="selected" value="${productOrder == 'POPULARITY'}"/>
			<a class="order-btn ${selected ? 'selected':''}" href="<c:url value="?orderBy=popularity${categoryQuery}"/>">
				<spring:message code="index.order.popular"/>
			</a>
			<span class="order-divider"></span>
			<c:set var="selected" value="${productOrder == 'ALPHABETICALLY'}"/>
			<a class="order-btn ${selected ? 'selected':''}" href="<c:url value="?orderBy=alphabetically${categoryQuery}"/>">
				<spring:message code="index.order.atoz"/>
			</a>
		</div>
	</div>
</div>