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
	</head>

    <body>
    	<%@include file="includes/navbar.jsp"%>
    	<h2><spring:message code="login.title" /></h2>
        <c:url value="/login" var="postPath" />
        <form action="${postPath}" method="post" enctype="application/x-www-form-urlencoded">
        	<div>
        		<label for="j_username">Username: </label>
        		<input type="text" name="j_username" id="j_username" />
        	</div>
        	<div>
        		<label for="password">Password: </label>
        		<input type="password" name="j_password" id="j_password"/>
        	</div>
        	<div>
        		<label for="j_rememberme">Remember me: </label>
        		<input type="checkbox" name="j_rememberme" id="j_rememberme" />
        	</div>
        	<div>
        		<input type="submit" value="Login!" />
        	</div>
        </form >
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
