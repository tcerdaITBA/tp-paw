<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        
        <title><spring:message code="error.title.403"/></title>
        
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		
		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
		<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
		<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
		
		<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">

	</head>
	<body>
	<%@include file="includes/navbar.jsp" %>
	<div class="container">
		<h1>
			<spring:message code="error.403"/>
		</h1>
	
	</div>
	<%@include file="includes/footer.jsp"%>
	
	</body>

</html>