<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title><spring:message code="register.title" /></title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
		<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
		
		<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/img-upload.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/createUser.css"/>" rel="stylesheet">
	</head>
	
	<body>
		<%@include file="includes/navbar.jsp"%>
		<spring:message code="userFormLabel.namePlaceholder" var="NamePlaceholder"/>
		<spring:message code="userFormLabel.emailPlaceholder" var="EmailPlaceholder"/>
		<spring:message code="userFormLabel.passwordPlaceholder" var="PasswordPlaceholder"/>
		<spring:message code="userFormLabel.signUp" var="signUpMessage"/>
		<spring:message code="userFormLabel.passwordConfirmPlaceholder" var="PasswordConfirmPlaceholder"/>

		
		<div class="container">
			<div class="row">
				<div class="col-md-10">
					<c:url value="/register" var="postPath" />
					<form:form modelAttribute="createUserForm" action="${postPath}" method="post" enctype="multipart/form-data"
								class="form-horizontal">
						<div class="row row-centered">
							<div class="col-md-12 form-group">
								<div class="col-sm-9 col-sm-offset-3">
									<h2 class="create-title"><spring:message code="userFormLabel.signUp"/></h2>
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
									   <form:errors path="profilePicture" element="p" cssClass="error-centered form-error"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="name" class="col-sm-3 control-label"><spring:message code="userFormLabel.name" /></form:label>
								<div class="col-sm-9">
									<form:input type="text" path="name" class="form-control" placeholder="${NamePlaceholder}" maxlength="64"/>
									<form:errors path="name" element="p" cssClass="form-error" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="email" class="col-sm-3 control-label"><spring:message code="userFormLabel.email" /></form:label>
								<div class="col-sm-9">
									<form:input type="text" path="email" class="form-control" placeholder="${EmailPlaceholder}" maxlength="64"/>
									<form:errors path="email" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="passwordForm.password" class="col-sm-3 control-label"><spring:message code="userFormLabel.password" /></form:label>
								<div class="col-sm-9">
									<form:input type="password" path="passwordForm.password" class="form-control" placeholder="${PasswordPlaceholder}" maxlength="60"/>
									<form:errors path="passwordForm.password" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="passwordForm.passwordConf" class="col-sm-3 control-label"><spring:message code="userFormLabel.passwordConfirm" /></form:label>
								<div class="col-sm-9">
									<form:input type="password" path="passwordForm.passwordConf" class="form-control" placeholder="${PasswordConfirmPlaceholder}" maxlength="60"/>
									<form:errors path="passwordForm.passwordConf" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
								
						<div class="row row-centered">
							<div class="col-md-12 form-group">
								<div class="col-sm-9 col-sm-offset-3" style="text-align: center;">
									<input class="ps-btn-red btn submit-btn" type="submit" value="${signUpMessage}"/>

								</div>
							</div>
						</div>
					
					</form:form>
					
				</div>
			</div>
		</div>
		<%@include file="includes/footer.jsp"%>

	<%@include file="includes/scripts.jsp"%>
	<script src="<c:url value="/resources/js/upload-form.js"/>"></script>
	</body>
	
