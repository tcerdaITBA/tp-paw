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
				<c:if test="${loggedUser.userId == creator.userId}">
					<div class="col-md-2">
						<span id="delete${product.id}" data-product-id="${product.id}" class="glyphicon glyphicon-trash delete-product-button pull-right"></span>
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
				<div data-href="<c:url value="/?category=${product.category.name}"/>" class="categoryTag product-category-btn">
					<p><spring:message code="category.${product.category.lowerName}"/></p>
				</div>
			</div>
			<c:url value="/vote/product/${product.id}" var="vote" />
			<div class="col-md-5 col-md-offset-2 text-right voters-holder">
				<a tabindex="0" class="voters-popover-btn" rel="popover" data-popover-content="#votersPopover" data-placement="bottom" title="<spring:message code="ProductPage.votersTooltip"/>" data-trigger="focus" >
					<span class="voter-span" data-toggle="tooltip" data-placement="left" title="<spring:message code="ProductPage.votersTooltip"/>">
						<c:forEach items="${votersresume}" var="voter">
							<img class="profile-img-circle voters-img" src="<c:url value="/profile/${voter.userId}/profilePicture"/>">
						</c:forEach>
					</span>
				</a>
			</div>
			<%@include file="votersPopover.jsp"%>
			<div class="col-md-2">
				<sec:authorize access="isAuthenticated()">
					<form:form class="pull-right" action="${vote}" method="post">
							<button class="btn btn-default upvote-btn" type="submit" data-vote-id="vote${product.id}">
								<p><span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
								<c:out value="${product.votesCount}"/></p>
							</button>
					</form:form>
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
		
		
		
		
		
