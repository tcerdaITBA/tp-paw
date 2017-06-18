<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:message code="collection.collectionNamePlaceholder" var="namePlaceholder"></spring:message>
<div id="add-to-collection-modal-${product.id}" class="modal fade">
    <div class="modal-dialog">
		<div class="modal-content">
            <div class="modal-header">
       	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title collection-modal-title">
                    <span class="glyphicon glyphicon-plus"></span>
                    <spring:message code="collections.add"/>
                    <span class="product-to-add"><c:out value="${product.name}"></c:out></span>
                    <spring:message code="collections.toCollection"/>
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
																				<c:choose>
																					<c:when test="${fn:contains(collection.productList, product)}">
																						<span class="tool-tip" data-toggle="tooltip" data-placement="left" title="<spring:message code="collections.alreadyInCollection"/>">
																							<button class="add-to-list-item" type="submit" data-list-id="favlist${collection.name}" disabled>
																									<span class="collection-name"><c:out value="${collection.name}"></c:out></span>
																									<span class="collection-info">
																									<p>
																										<c:set var="collectionLen" value="${fn:length(collection.productList)}"/>
																										<c:out value="${collectionLen}"></c:out>
																										<c:choose>
																												<c:when test="${collectionLen eq 1}"><spring:message code="product.singular" /></c:when>
																												<c:otherwise><spring:message code="product.plural" /></c:otherwise>
																										</c:choose>
																									</p>
																									</span>
																							</button>
																						</span>
																					</c:when>
																					<c:otherwise>
																						<button class="add-to-list-item" type="submit" data-list-id="favlist${collection.name}">
																							<span class="collection-name"><c:out value="${collection.name}"></c:out></span>
																							<span class="collection-info">
																							<p> 
																								<c:set var="collectionLen" value="${fn:length(collection.productList)}" />
																								<c:out value="${collectionLen}"></c:out>
																								<c:choose>
																										<c:when test="${collectionLen eq 1}"><spring:message code="product.singular" /></c:when>
																										<c:otherwise><spring:message code="product.plural" /></c:otherwise>
																								</c:choose>
																							</p>
																							</span>
																						</button>
																					</c:otherwise>
																				</c:choose>		
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
                            <c:url value="/favlist/create-and-add/${product.id}" var="postPath" />
                                <form:form modelAttribute="createFavListForm" class="favlist-form" action="${postPath}" method="post">
                                    <h3 class="favlist-form-title"><spring:message code="collections.newCollection"></spring:message></h3>
                                    <div class="form-group">
                                        <form:input type="text" class="form-control" rows="1" path="name" placeholder="${namePlaceholder}" maxlength="64"/>
                                        <form:errors path="name" element="p" cssClass="form-error"/>
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
	
