<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title><spring:message code="login.head.title" /></title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
		<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
		
		<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/upload-form.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/dropzone.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/login.css"/>" rel="stylesheet">
		
	</head>

    <body>
    	<%@include file="includes/navbar.jsp"%>
    	<div class="form-container container">
    		<div class="row">
    			<div class="row">
    				<c:url value="/login" var="loginPath"/>
    				<div class="col-md-12">
    					<div class="row">
							<div class="col-md-12 title">
								<h2><spring:message code="loginLabel.title"/></h2>
							</div>
						</div>
						<form action="${loginPath}" method="post" enctype="application/x-www-form-urlencoded" 
								class="form-horizontal">
						<div class="row">
							<div class="col-md-12">
							<div class="col-md-offset-3 col-md-6">		
								<div class="row">
									<div class="col-md-12 form-group">
										<div class="col-md-3">
		        							<label for="j_username"><spring:message code="loginLabel.email"/></label>
										</div>
										<div class="col-md-9">
											<input type="text" name="j_username" id="j_username" class="form-control"/>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 form-group">
										<div class="col-md-3">
	        								<label for="password"><spring:message code="loginLabel.password"/></label>
	        							</div>
	        							<div class="col-md-9">
											<input type="password" name="j_password" id="j_password" class="form-control"/>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 form-group">
										<div class="col-md-3">
	        								<label for="j_rememberme"><spring:message code="loginLabel.rememberMe"/></label>
	        							</div>
	        							<div class="col-md-1">
											<input type="checkbox" name="j_rememberme" id="j_rememberme" class="form-control" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="centered">
											<input class="ps-btn-red btn submit-btn" type="submit" value="Sign In" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 signUpLink">
										<div class="centered">
											<a href="<c:url value="/register"/>">
												<spring:message code="login.goToRegister"/>
											</a>
										</div>
									</div>
								</div>
							</div>
							</div>
						</div>
						</form>										 
    				</div>  
    			</div>
    		</div>
    	</div>

    </body>
    
    	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
					src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
					src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
					integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
					crossorigin="anonymous"></script>
	<script src="<c:url value="/resources/js/upload-form.js"/>"></script>
    
</html>
