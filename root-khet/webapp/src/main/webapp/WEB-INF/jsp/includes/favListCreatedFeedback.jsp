<c:if test="${not empty favListCreated}">
	<div id="create-favlist-snackbar">
		<div class="alert alert-success row-centered">
			<strong>
				<span class="glyphicon glyphicon-plus"></span>
				<span class="snackbar-product-name"><c:out value="${favListCreated}" /></span>
			</strong>
			<spring:message code="feedback.favListCreated.textEnd" />
		</div>
	</div>
	
	<script>
		var x = document.getElementById('create-favlist-snackbar');
		x.className = 'show';
		setTimeout(function(){ x.className = x.className.replace('show', ''); }, 3000);
	</script>
</c:if>