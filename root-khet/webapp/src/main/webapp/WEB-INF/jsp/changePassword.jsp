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
		
		<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">

	</head>
	
	<body>
		<%@include file="includes/navbar.jsp" %>
		<div class="container">
			<div class="row">
				<div class="col-md-10">
					<c:url value="/profile/customize/password" var="postPath" />
					<form:form modelAttribute="changePasswordForm" action="${postPath}" method="post" enctype="multipart/form-data"
								class="form-horizontal">
					
					<div class="row">
							<div class="col-md-12 form-group">
								<form:label path="password" class="col-sm-3 control-label"><spring:message code="userFormLabel.password"></spring:message></form:label>
								<div class="col-sm-9">
									<form:input type="password" path="password" class="form-control" maxlength="64"/>
									<form:errors path="password" cssClass="form-error" element="p"/>
								</div>
							</div>
						</div>
						
						<div class="row row-centered">
							<div class="col-md-12">
								<div class="col-sm-9 col-sm-offset-3">
									<input class="ps-btn-red btn submit-btn" type="submit" value="<spring:message code="changePassword"></spring:message>" />
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
						
	</body>

</html>
	
	