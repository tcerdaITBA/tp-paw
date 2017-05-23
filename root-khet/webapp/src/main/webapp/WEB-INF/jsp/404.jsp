<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
        
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		
        <title><spring:message code="error.title.404"/></title>
        
        <!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
		<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
		
		<link href="<c:url value="/resources/css/errorcodes.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
 
	</head>
	<body>
	<%@include file="includes/navbar.jsp" %>
	<div class="container">
		<h1>
			<spring:message code="error.404"/>
		</h1>
		
		<div class="row col-md-8 col-md-offset-2">
			<div class="errorDescription">
				<h2><spring:message code="error.tear"/></h2>
				<h3><spring:message code="error.pageNotFound"/></h3>
				<p><spring:message code="error.pageNotFoundDesc"/></p>
			</div>
		</div>
	
	</div>
	<%@include file="includes/footer.jsp"%>
	<%@include file="includes/scripts.jsp"%>	
	</body>

</html>