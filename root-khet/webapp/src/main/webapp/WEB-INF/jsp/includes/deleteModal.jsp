<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="deleteModal${product.id}" class="modal fade deleteModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-trash"></span>
                    <spring:message code="Profile.modal.deleteProduct"/>
                </h4>	
            </div>
            
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <p class="modal-text"><spring:message code="Profile.modal.textBeginning" />
                        <span class="modal-product-name"><c:out value="${product.name}" /></span>
                        <spring:message code="Profile.modal.textEnd" /></p>
                    </div>
                </div>
                <div class="row row-centered">
                    <div class="col-md-12">
                        <c:url value="/delete/product/${product.id}" var="deletePath" />
                        <form:form action="${deletePath}" method="post">
                            <input class="ps-btn-red btn submit-btn" type="submit" value="<spring:message code="Profile.modal.leftButton"></spring:message>" />
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="Profile.modal.rightButton"/></button>
                        </form:form>
                    </div>
                </div>
            </div>	
        </div>
    </div>	
</div>

<c:if test="${not empty productDeleted}">    
    <div id="feedbackDeleteModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">
                        <span class="glyphicon glyphicon-trash"></span>
                        <spring:message code="Profile.modal.title.feedbackDeleteModal"/>
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-12">
                            <p>
                                <spring:message code="Profile.modal.body.feedbackDeleteModal.textBeginning" />
                                <span class="modal-product-name"><c:out value="${productDeleted}" /></span>
                                <spring:message code="Profile.modal.body.feedbackDeleteModal.textEnd" />
                            </p>
                        </div>
                    </div>
                    <div class="row row-centered">
                        <div class="col-md-12">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="Profile.settings.close"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>