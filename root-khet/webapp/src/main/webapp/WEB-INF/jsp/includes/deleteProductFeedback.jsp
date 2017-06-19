<c:if test="${not empty productDeleted}">
	<div id="delete-snackbar">
		<div class="alert alert-success row-centered">
			<strong>
				<span class="glyphicon glyphicon-trash"></span>
				<span class="snackbar-product-name"><c:out value="${productDeleted}" /></span>
			</strong>
			<spring:message code="Profile.modal.body.feedbackDeleteModal.textEnd" />
		</div>
	</div>
	
	<script>
		var x = document.getElementById('delete-snackbar');
		x.className = 'show';
		setTimeout(function(){ x.className = x.className.replace('show', ''); }, 3000);
	</script>
</c:if>