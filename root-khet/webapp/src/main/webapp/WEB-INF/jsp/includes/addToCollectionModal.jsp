<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/favlist/create" var="postPath" />
<div id="add-to-collection-modal-${product.id}" class="modal fade">
    <div class="modal-dialog">
		<div class="modal-content">
            <div class="modal-header">
       	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-plus"></span>
                    <spring:message code="collections.addToCollection"/>
                </h4>
            </div>
            <div class="modal-body">
               <c:choose>
									<c:when test="${loggedUser.favLists.isEmpty()}">
										<div class="row">
											<div class="col-md-12">
												<p><spring:message code="collections.noCollectionsYet"></spring:message></p>
											</div>
										</div>
									</c:when>
									<c:otherwise>
										<c:forEach items="${loggedUser.favLists}" var="collection">
											<div class="row">
												<div class="col-md-12">
													<c:url value="/favlist/add/${collection.id}/${product.id}" var="addToList"/>
													<form:form action="${addToList}" method="post">
															<button class="add-to-list-item" type="submit" data-list-id="favlist${collection.name}">
																<span class="collection-name"><c:out value="${collection.name}"></c:out></span>
																<span class="collection-info">
																	<p>- <c:out value="${fn:length(collection.productList)}"/> productos</p>
																</span>
															</button>
													</form:form>
												</div>
											</div>
										</c:forEach> 
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
												<form:form modelAttribute="createFavListForm" class="favlist-form" action="${postPath}" method="post">
													<div class="form-group">
															<form:input type="text" class="form-control" rows="1" path="name" placeholder="Collection name" maxlength="64"/>
															<form:errors path="name" element="p" cssClass="form-error"/>
													</div>
													<button class="btn btn-default create-and-add-btn" type="submit">
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
	
