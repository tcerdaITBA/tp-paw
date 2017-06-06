<div class="row product-list-item product-item-height">
	<div class="col-md-3 product-logo">
		<img src="<c:url value="/product/${product.id}/logo"/>">
	</div>
	<div class="col-md-9 product-info-box">
		<div class="row product-name">
			<div class="col-md-10 capitalize-firstLetter">
				<p><c:out value="${product.name}"/></p>
			</div>
			<sec:authorize access="isAuthenticated()">
				<c:if test="${loggedUser.userId == profileUser.userId}">
					<div class="col-md-2">
						<span id="delete${votedProduct.id}" data-product-id="${votedProduct.id}" class="glyphicon glyphicon-trash delete-product-button pull-right"></span>
					</div>
				</c:if>
			</sec:authorize>
		</div>
		<div class="row product-short-description">
			<div class="col-md-12 capitalize-firstLetter">
				<p><c:out value="${product.shortDescription}"/></p>
			</div>
		</div>
		<div class="row product-category">
			<div class="col-md-3">
				<div data-href="<c:url value="/category/${product.category.lowerName}"/>" class="categoryTag product-category-btn">
					<p><spring:message code="category.${product.category.lowerName}"/></p>
				</div>
			</div>
			<c:url value="/vote/product/${product.id}" var="vote" />
			<div class="col-md-3 col-md-offset-6">
				<form:form class="pull-right" action="${vote}" method="post">
						<button class="btn btn-default upvote-btn" type="submit" id="vote${product.id}">
							<p><span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
							<c:out value="${product.votesCount}"/></p>
						</button>
				</form:form>
			</div>
			<sec:authorize access="isAuthenticated()">
				<c:if test="${product.votingUsers.contains(loggedUser)}">
					<script>upVotedProductByLoggedUser(${product.id});</script>
				</c:if>							                    
			</sec:authorize>
		</div>
	</div>
</div>