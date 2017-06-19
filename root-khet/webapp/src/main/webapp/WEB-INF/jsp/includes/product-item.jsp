<div class="row product-list-item product-item-height" id="product${product.id}">
	<div class="col-md-3 product-logo">
		<img src="<c:url value="/product/${product.id}/logo"/>">
	</div>
	<div class="col-md-9 product-info-box">
		<div class="row product-name">
			<div class="col-md-10 capitalize-firstLetter">
				<p><c:out value="${product.name}"/></p>
			</div>
		</div>
		<div class="row product-short-description">
			<div class="col-md-12 capitalize-firstLetter">
				<p><c:out value="${product.shortDescription}"/></p>
			</div>
		</div>
		<div class="row product-category">
		
		<c:set var="offset" value="6" />
		
		<!-- Render category only if no category filter is present -->
		
		<c:if test="${empty currentCategory}">   
			<c:set var="offset" value="3" />
			<div class="col-md-3">
				<div data-href="<c:url value="/?category=${product.category.name}${orderQuery}"/>" class="categoryTag product-category-btn">
					<p><spring:message code="category.${product.category.lowerName}"/></p>
				</div>
			</div>
		</c:if>
		
		<!-- End render category  -->
		
			<c:url value="/vote/product/${product.id}" var="vote" />
			<div class="col-md-6 col-md-offset-${offset}">
				<sec:authorize access="isAuthenticated()">
					<form:form class="pull-right" action="${vote}" method="post">
						<button class="btn btn-default upvote-btn" type="submit" data-vote-id="vote${product.id}">
							<p><span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
							<c:out value="${product.votesCount}"/></p>
						</button>
					</form:form>
					<button class="btn btn-default add-to-collection-btn pull-right categoryTag" data-target="#add-to-collection-modal-${product.id}" data-toggle="modal">
						<p>
							<span class="glyphicon glyphicon-plus"></span>
						</p>
					</button>	
				</sec:authorize>
				<sec:authorize access="isAnonymous()">
					<button class="btn btn-default upvote-btn popover-btn pull-right" data-toggle="popover" data-trigger="focus" 
					data-content="<p class='popover-msg'>
					 <span><spring:message code="upVote.toPost"/></span>
					 <a href='<c:url value="/login"/>'> 
					 <span> <spring:message code="upVote.signIn"/></span>
					 </a>
					 <span><spring:message code="upVote.toPostBis"/></span>
					 <a href='<c:url value="/register"/>'> 
					 <span> <spring:message code="upVote.signUp"/></span>
					 </a>
					 </p>">
						<p><span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
						<c:out value="${product.votesCount}"/></p>
					</button>
				</sec:authorize>
			</div>
			<sec:authorize access="isAuthenticated()">
				<c:if test="${fn:contains(product.votingUsers, loggedUser)}">
					<script>upVotedProductByLoggedUser(${product.id});</script>
				</c:if>							                    
			</sec:authorize>
		</div>
	</div>
</div>
