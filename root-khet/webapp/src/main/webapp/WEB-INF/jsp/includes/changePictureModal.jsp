<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="changePictureModal" class="modal fade">
	<div class="modal-dialog">
	    <div class="modal-content">
	        <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	             <h4 class="modal-title"><spring:message code="Profile.settings.changePicture"/></h4>
	
	        </div>
	        <div class="modal-body">

				<div class="row">
				<div class="col-md-12">
					<c:url value="/profile/customize/profilePicture" var="postPath" />
					<form:form modelAttribute="changeProfilePictureForm" action="${postPath}" method="post" enctype="multipart/form-data"
								class="form-horizontal">
						<div class="row row-centered">
							<div class="col-md-12">
								<div class="form-group">
							
								<div class="row">
									<div class="col-md-12">
										<h2 class="change-title"><spring:message code="Profile.settings.changePicture"/></h2>								
									</div>
								</div>
								<div class="row">
								<div class="col-md-6 col-md-offset-3">
									<div class="profile-img-input">
										<form:input class="image-input" type="file" path="profilePicture" accept="image/*"/>
										<form:label id="profilePicture-label" path="profilePicture">
											<div class="preview-container">
												<img src="#" class="preview-image">
												<span class="add-img-text">
													<span class="glyphicon glyphicon-plus"></span>
													<spring:message code="userFormLabel.profilePicture"/>
												</span>
												<div class="remove-btn glyphicon glyphicon-remove"></div>
											</div>
										</form:label>
									</div>
								</div>
								</div>
									<form:errors path="profilePicture" element="p" cssClass="error-centered form-error"/>
								</div>
							</div>
							
						</div>
						<div class="row row-centered">
								<div class="col-md-12">
									<div class="col-sm-12">
										<input class="ps-btn-red btn submit-btn modal-btn" type="submit" value="<spring:message code="Profile.settings.changePicture"></spring:message>" />
										<button type="button" class="btn btn-default modal-btn" data-dismiss="modal"><spring:message code="passwordForm.cancel"/></button>
									</div>
								</div>
							</div>
						
						</form:form>
						</div>
						</div>
	        </div>
	    </div>
	</div>
</div>