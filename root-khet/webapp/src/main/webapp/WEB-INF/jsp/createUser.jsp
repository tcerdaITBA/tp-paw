<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title><spring:message code="default.title" /></title>
		<link
					href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
					rel="stylesheet"
					integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
					crossorigin="anonymous">
		<link href="<c:url value="/resources/css/login.css"/>" rel="stylesheet">
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
									</div>
									<form:errors path="profilePicture" cssClass="" element="p" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="name" class="col-sm-3 control-label"><spring:message code="userFormLabel.name" /></form:label>
								<div class="col-sm-9">
									<form:input type="text" path="name" class="form-control" placeholder="${NamePlaceholder}" maxlength="64"/>
									<form:errors path="name" cssClass="" element="p"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="email" class="col-sm-3 control-label"><spring:message code="userFormLabel.email" /></form:label>
								<div class="col-sm-9">
									<form:input type="text" path="email" class="form-control" placeholder="${EmailPlaceholder}" maxlength="64"/>
									<form:errors path="email" cssClass="" element="p"/>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="name" class="col-sm-3 control-label"><spring:message code="userFormLabel.password" /></form:label>
								<div class="col-sm-9">
									<form:input type="password" path="password" class="form-control" placeholder="${PasswordPlaceholder}" maxlength="60"/>
									<form:errors path="password" cssClass="" element="p"/>
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

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
					src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
					src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
					integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
					crossorigin="anonymous"></script>
	<script src="<c:url value="/resources/js/upload-form.js"/>"></script>
	</body>
	
