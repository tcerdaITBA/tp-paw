<c:if test="${not empty productAddedToFavList}">
	<div id="add-product-to-favlist-snackbar">
		<div class="alert alert-success row-centered">
			<strong>
				<span class="glyphicon glyphicon-plus"></span>
				<span class="snackbar-product-name"><c:out value="${productAddedToFavList}" /></span>
			</strong>
			<spring:message code="feedback.productAddedToFavList.text" />
            <strong>
				<span class="snackbar-product-name"><c:out value="${favListAdded}" /></span>
			</strong>
		</div>
	</div>
	
	<script>
		var y = document.getElementById('add-product-to-favlist-snackbar');
        <c:choose>
            <c:when test="${empty favListCreated}">
                x.className = 'show';
                setTimeout(function(){ y.className = y.className.replace('show', ''); }, 3000);
            </c:when>
            <c:otherwise>
                setTimeout(function(){ y.className = 'show'; }, 3500);                
                setTimeout(function(){ y.className = y.className.replace('show', ''); }, 6500);                
            </c:otherwise>
        </c:choose>
	</script>
</c:if>