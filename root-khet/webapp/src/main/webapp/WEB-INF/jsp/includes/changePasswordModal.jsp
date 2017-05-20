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
                <c:url value="/profile/customize/password" var="postPath" />
                <form:form modelAttribute="changePasswordForm" action="${postPath}" method="post" class="form-horizontal">
                
                <div class="form-group">
                    <form:label path="currentPasswordConf" class="col-sm-4 control-label"><spring:message code="changePassword.currentPasswordConf"></spring:message></form:label>
                    <div class="col-sm-7">
                        <form:input type="password" path="currentPasswordConf" class="form-control" maxlength="64"/>
                        <form:errors path="currentPasswordConf" cssClass="form-error" element="p"/>
                    </div>
                </div>
                    
                <div class="form-group">
                    <form:label path="passwordForm.password" class="col-sm-4 control-label"><spring:message code="changePassword.newPassword"></spring:message></form:label>
                    <div class="col-sm-7">
                        <form:input type="password" path="passwordForm.password" class="form-control" maxlength="64"/>
                        <form:errors path="passwordForm.password" cssClass="form-error" element="p"/>
                    </div>
                </div>

                <div class="form-group">
                    <form:label path="passwordForm.passwordConf" class="col-sm-4 control-label"><spring:message code="userFormLabel.passwordConfirm"></spring:message></form:label>
                    <div class="col-sm-7">
                        <form:input type="password" path="passwordForm.passwordConf" class="form-control" maxlength="64"/>
                        <form:errors path="passwordForm.passwordConf" cssClass="form-error" element="p"/>
                    </div>
                </div>
                    
                <div class="form-group row-centered">
                    <div class="form-input-buttons">
                        <input class="ps-btn-red btn submit-btn" type="submit" value="<spring:message code="changePassword"></spring:message>" />
                        <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="passwordForm.cancel"/></button>
                    </div>
                </div>
                    
                </form:form>
            </div>
        </div>
    </div>
</div>