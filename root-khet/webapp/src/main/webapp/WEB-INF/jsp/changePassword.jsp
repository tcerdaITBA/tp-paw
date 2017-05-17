<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="changePassModal" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
		       <div class="modal-header">
		           <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		            <h4 class="modal-title"><spring:message code="Profile.settings.changePassword"/></h4>
		
		       </div>
		       <div class="modal-body">
		       		
				<div class="row pass-modal-row">
		
					<div class="row">
						<div class="col-md-12">
							<c:url value="/profile/customize/password" var="postPath" />
						<form:form modelAttribute="changePasswordForm" action="${postPath}" method="post" enctype="multipart/form-data"
									class="form-horizontal">
						
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="previousPassword" class="col-sm-3 control-label"><spring:message code="changePassword.previousPassword"></spring:message></form:label>
								<div class="col-sm-9">
									<form:input type="password" path="previousPassword" class="form-control" maxlength="64"/>
									<form:errors path="previousPassword" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
					
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="passwordForm.password" class="col-sm-3 control-label"><spring:message code="userFormLabel.password"></spring:message></form:label>
								<div class="col-sm-9">
									<form:input type="password" path="passwordForm.password" class="form-control" maxlength="64"/>
									<form:errors path="passwordForm.password" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="passwordForm.passwordConf" class="col-sm-3 control-label"><spring:message code="userFormLabel.passwordConfirm"></spring:message></form:label>
								<div class="col-sm-9">
									<form:input type="password" path="passwordForm.passwordConf" class="form-control" maxlength="64"/>
									<form:errors path="passwordForm.passwordConf" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
							
							<div class="row row-centered">
								<div class="col-md-12">
									<div class="col-sm-12">
										<input class="ps-btn-red btn submit-btn" type="submit" value="<spring:message code="changePassword"></spring:message>" />
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
</div>
