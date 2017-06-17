<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
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
													<form:form class="pull-right" action="${addToList}" method="post">
															<button class="add-to-list-item" type="submit" data-list-id="favlist${collection.name}">
																<p><c:out value="${collection.name}"></c:out></p>
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
											<button class="btn btn-default add-to-new-list-btn" data-collection-popover="${product.id}">
												<p><spring:message code="collections.addInNewCollection"/></p>
											</button>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="new-collection-form well">
												<form:form action="${addToList}" method="post">
													<input type="text" name="j_username" id="j_username" class="form-control" placeholder="<spring:message code="loginLabel.email"/>"/>
													<button class="btn btn-default create-and-add-btn" type="submit">
														<p><spring:message code="collections.createAndAdd"/></p>
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
	
