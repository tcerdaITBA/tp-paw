<div class="panel panel-default">
	<div class="panel-heading" role="tab" id="heading-${favList.id}">
		<h4 class="panel-title">
			<div class="row">
				<div class="col-md-12">
					<div class="col-md-10">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse-${favList.id}" aria-expanded="false" aria-controls="collapse-${favList.id}">
							<c:out value="${favList.name}"></c:out>
						</a>
					</div>
					<div class="col-md-2">
						<sec:authorize access="isAuthenticated()">
							<c:if test="${loggedUser.userId == profileUser.userId}">
								<c:url value="/favlist/delete/${favList.id}" var="delete" />
								<form:form class="" action="${delete}" method="post">
									<button class="btn btn-default" type="submit">
										<p><span class="glyphicon glyphicon-trash"></span></p>
									</button>
								</form:form>	
							</c:if>
						</sec:authorize>
					</div>
				</div>
			</div>
		</h4>
	</div>
	<div id="collapse-${favList.id}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-${favList.id}">
		<div class="panel-body">
			<c:forEach items="${favList.productList}" var="product">
				<br><c:out value="${product.name}"></c:out>
			</c:forEach>	
		</div>
	</div>
</div>
