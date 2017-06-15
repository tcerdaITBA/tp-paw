<div id="favlistPopover" class="row hide popover-body">
	<div class="col-md-12">

	<!--Formulario para crear una lista (Solo se deberia ver si soy el usuario del perfil o estoy autenticado)-->
	<form:form modelAttribute="createFavListForm" class="favlist-form" action="${postPath}" method="post">
	    <div class="form-group">
	        <form:input type="text" class="form-control" rows="1" path="name" placeholder="Name" maxlength="64"/>
	        <form:errors path="name" element="p" cssClass="form-error"/>
	    </div>
	    <div class="btn-place">
	        <input type="submit" class="btn btn-default post-comment-btn" value="<spring:message code="Profile.createFavLists" />" />
	    </div>
	</form:form>
	<!-- END del formulario -->

	</div>
</div>
									