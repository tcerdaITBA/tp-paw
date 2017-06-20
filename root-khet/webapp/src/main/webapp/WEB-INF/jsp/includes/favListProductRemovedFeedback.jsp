<c:if test="${not empty productRemovedFromFavList}">
	<div id="remove-product-from-favlist-snackbar">
		<div class="alert alert-success row-centered">
			<strong>
				<span class="glyphicon glyphicon-remove"></span>
				<span class="snackbar-product-name"><c:out value="${productRemovedFromFavList}" /></span>
			</strong>
			<spring:message code="feedback.productRemovedFromFavList.text" />
            <strong>
				<span class="snackbar-product-name"><c:out value="${favListRemoved}" /></span>
			</strong>
		</div>
	</div>
	
	<script>
		var x = document.getElementById('remove-product-from-favlist-snackbar');
		x.className = 'show';
		setTimeout(function(){ x.className = x.className.replace('show', ''); }, 3000);
	</script>
</c:if>