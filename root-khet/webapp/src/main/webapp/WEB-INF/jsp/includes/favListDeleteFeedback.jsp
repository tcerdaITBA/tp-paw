<c:if test="${not empty favListDeleted}">
	<div id="delete-favlist-snackbar">
		<div class="alert alert-success row-centered">
			<strong>
				<span class="glyphicon glyphicon-trash"></span>
				<span class="snackbar-product-name"><c:out value="${favListDeleted}" /></span>
			</strong>
			<spring:message code="feedback.favListDeleted.textEnd" />
		</div>
	</div>
	
	<script>
		var x = document.getElementById('delete-favlist-snackbar');
		x.className = 'show';
		setTimeout(function(){ x.className = x.className.replace('show', ''); }, 3000);
	</script>
</c:if>