<div id="favListDeleteModal${favList.id}" class="modal fade deleteModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">
                    <span class="glyphicon glyphicon-trash"></span>
                    <spring:message code="Profile.modal.deleteFavList"/>
                </h4>	
            </div>
            
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <p class="modal-text"><spring:message code="Profile.modal.textBeginning" />
                        <span class="modal-product-name"><c:out value="${favList.name}" /></span>
                        <spring:message code="Profile.modal.textEnd" /></p>
                    </div>
                </div>
                <div class="row row-centered">
                    <div class="col-md-12">
                        <c:url value="/favlist/delete/${favList.id}" var="delete"/>
                        <form:form class="delete-collection-form pull-right" action="${delete}" method="post">
                            <input class="ps-btn-red btn submit-btn modal-btn" type="submit" value="<spring:message code="Profile.modal.leftButton"></spring:message>" />
                            <button type="button" class="btn btn-default modal-btn" data-dismiss="modal"><spring:message code="Profile.modal.rightButton"/></button>
                        </form:form>
                    </div>
                </div>
            </div>	
        </div>
    </div>	
</div>