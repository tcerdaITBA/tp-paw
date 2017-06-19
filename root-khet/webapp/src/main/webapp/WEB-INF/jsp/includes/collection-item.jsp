<div class="panel panel-default collection-panel">
	<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-${favList.id}" aria-expanded="false" aria-controls="collapse-${favList.id}">
		<div class="panel-heading" role="tab" id="heading-${favList.id}">
			<div class="row">
				<div class="col-md-10">
					<div class="collection-title">
						<c:out value="${favList.name}"></c:out>
					</div>
					<div class="collection-info">
						<c:set var="favListLen" value="${fn:length(favList.productList)}" />
						<c:out value="${favListLen}"></c:out>
						<c:choose>
								<c:when test="${favListLen eq 1}"><spring:message code="product.singular" /></c:when>
								<c:otherwise><spring:message code="product.plural" /></c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-md-2">
					<sec:authorize access="isAuthenticated()">
						<c:if test="${loggedUser.userId == profileUser.userId}">
							<button class="btn btn-default collection-delete-btn pull-right" type="submit" data-favlist-id="${favList.id}">
									<span class="glyphicon glyphicon-trash"></span>
							</button>
						</c:if>
					</sec:authorize>
				</div>
			</div>
		</div>
	</a>
	<div id="collapse-${favList.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-${favList.id}">
		<div class="panel-body">
			<c:choose>
				<c:when test="${empty favList.productList}">
					<!-- ZRP -->
					<h4><spring:message code="collection-zrp"/></h4>
				</c:when>
				<c:otherwise>
					<c:forEach items="${favList.productList}" var="product">
					<div class="row">
						<div class="col-md-11">
							<a class="collection-product-list-item" href="<c:url value="/product/${product.id}"/>" >
								<c:url value="/vote/product/${product.id}" var="vote"/>
								<div class="row center-flex">
									<div class="col-md-2 collection-product-logo center-flex">
										<img src="<c:url value="/product/${product.id}/logo"/>">
									</div>
									<div class="col-md-7">
										<span class="collection-product-name capitalize-firstLetter">
											<c:out value="${product.name}"/>
										</span>
									</div>
									<div class="col-md-3">
										<span class="collection-upvote-info pull-right">
											<span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
											<c:out value="${product.votesCount}"/>
										</span>
									</div>
								</div>
							</a>
						</div>
						<div class="col-md-1">
							<c:url value="/favlist/delete/${favList.id}/${product.id}" var="removeFromList"/>
							<sec:authorize access="isAuthenticated()">
							<c:if test="${loggedUser.userId == profileUser.userId}">
								<form:form class="pull-right" action="${removeFromList}" method="post">
										<button class="btn collection-delete-product" type="submit"">
											<p><span class="glyphicon glyphicon-remove collection-remove-product-icon"></span>
										</button>
								</form:form>
							</c:if>
						</sec:authorize>
						</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
