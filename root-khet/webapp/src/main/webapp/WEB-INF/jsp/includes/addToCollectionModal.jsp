<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:message code="collection.collectionNamePlaceholder" var="namePlaceholder"></spring:message>

<div id="add-to-collection-modal-${product.id}" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<div class="product-preview">
					<div class="row center-flex">
						<div class="col-md-2 preview-product-logo center-flex">
							<img src="<c:url value="/product/${product.id}/logo"/>">
						</div>
						<div class="col-md-7">
							<span class="preview-product-name capitalize-firstLetter">
								<c:out value="${product.name}"/>
							</span>
						</div>
						<div class="col-md-3">
							<span class="preview-upvote-info pull-right">
								<span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
								<c:out value="${product.votesCount}"/>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-body">
				<c:choose>
					<c:when test="${empty loggedUser.favLists}">
						<div class="row">
							<div class="col-md-12">
								<p><spring:message code="collections.noCollectionsYet"></spring:message></p>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="collection-modal-body">
							<c:forEach items="${loggedUser.favLists}" var="collection">
								<div class="row">
									<div class="col-md-12">
										<c:url value="/favlist/add/${collection.id}/${product.id}" var="addToList"/>
										<form:form action="${addToList}" method="post">
											<c:choose>
												<c:when test="${fn:contains(collection.productList, product)}">
													<span class="tool-tip" data-toggle="tooltip" data-placement="right" title="<spring:message code="collections.alreadyInCollection"/>">
														<button class="add-to-list-item" type="submit" data-list-id="favlist${collection.name}" disabled>
															<div class="collection-name"><c:out value="${collection.name}"></c:out></div>
															<div class="collection-info">
																<p>
																	<c:set var="collectionLen" value="${fn:length(collection.productList)}"/>
																	<c:out value="${collectionLen}"></c:out>
																	<c:choose>
																		<c:when test="${collectionLen eq 1}"><spring:message code="product.singular" /></c:when>
																		<c:otherwise><spring:message code="product.plural" /></c:otherwise>
																	</c:choose>
																</p>
															</div>
														</button>
													</span>
												</c:when>
												<c:otherwise>
													<button class="add-to-list-item" type="submit" data-list-id="favlist${collection.name}">
														<div class="row">
															<div class="col-md-8">
																<div class="collection-name"><c:out value="${collection.name}"></c:out></div>
																<div class="collection-info">
																	<p> 
																		<c:set var="collectionLen" value="${fn:length(collection.productList)}" />
																		<c:out value="${collectionLen}"></c:out>
																		<c:choose>
																			<c:when test="${collectionLen eq 1}"><spring:message code="product.singular" /></c:when>
																			<c:otherwise><spring:message code="product.plural" /></c:otherwise>
																		</c:choose>
																	</p>
																</div>
															</div>
															<div class="col-md-4 hint-col">
																<div class="adding-hint pull-right">
																	<span class="glyphicon glyphicon-plus"></span>
																	<span>Agregar</span>
																</div>
															</div>
														</div>
													</button>
												</c:otherwise>
											</c:choose>		
										</form:form>
									</div>
								</div>
							</c:forEach> 
						</div>
					</c:otherwise>
				</c:choose>
				<div class="new-collection-holder">
					<div class="row">
						<div class="col-md-12">
							<div class="new-collection-button-row">
								<button class="btn btn-default add-to-new-list-btn" data-collection-popover="${product.id}">
									<span class="glyphicon glyphicon-plus"></span>
									<spring:message code="collections.addInNewCollection"/>
								</button>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="new-collection-form well">
								<c:url value="/favlist/create?productId=${product.id}" var="postPath" />
								<form:form modelAttribute="createFavListForm" class="favlist-form" action="${postPath}" method="post">
									<h3 class="favlist-form-title"><spring:message code="collections.newCollection"></spring:message></h3>
									<div class="form-group">
										<form:input type="text" class="form-control" rows="1" path="name" placeholder="${namePlaceholder}" maxlength="64"/>
										<form:errors path="name" element="p" cssClass="form-error collection-error"/>
									</div>
									<button class="btn btn-default create-and-add-btn" type="submit">
										<span class="glyphicon glyphicon-plus"></span>
										<spring:message code="collections.createAndAdd"/>
									</button>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>	

