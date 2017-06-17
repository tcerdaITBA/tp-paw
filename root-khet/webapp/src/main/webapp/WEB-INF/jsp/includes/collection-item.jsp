<div class="panel panel-default collection-panel">
	<div class="panel-heading" role="tab" id="heading-${favList.id}">
		<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-${favList.id}" aria-expanded="false" aria-controls="collapse-${favList.id}">
			<div class="row">
				<div class="col-md-10">
					<span class="collection-title">
						<c:out value="${favList.name}"></c:out>
					</span>
				</div>
				<div class="col-md-2">
					<sec:authorize access="isAuthenticated()">
						<c:if test="${loggedUser.userId == profileUser.userId}">
							<c:url value="/favlist/delete/${favList.id}" var="delete"/>
							<form:form class="delete-collection-form" action="${delete}" method="post">
								<button class="btn btn-default" type="submit">
									<p><span class="glyphicon glyphicon-trash"></span></p>
								</button>
							</form:form>	
						</c:if>
					</sec:authorize>
				</div>
			</div>
		</a>
	</div>
	<div id="collapse-${favList.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-${favList.id}">
		<div class="panel-body">
			<c:forEach items="${favList.productList}" var="product">
				<br><c:out value="${product.name}"></c:out>
			</c:forEach>	
		</div>
	</div>
</div>
