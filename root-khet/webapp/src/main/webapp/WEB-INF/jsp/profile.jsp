<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<html>
		<head>
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
			<title><spring:message code="default.title" /></title>
			<link
						href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
						rel="stylesheet"
						integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
						crossorigin="anonymous">
			<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.css"/>
			<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick-theme.css"/>
			<link href="<c:url value="/resources/css/index.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/profile.css"/>" rel="stylesheet">
			
			<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">

		</head>
		<body>
			<%@include file="includes/navbar.jsp" %>
			<div class="container">
				<div class="row">
					<div class="col-md-3 profile-info-box">
					
								<div class="row img-row">
									<img class="profile-img" src="<c:url value="/profile/${us.userId}/profilePicture"/>">
								</div>
								<div class="row">
									<h3><c:out value="${us.name}"></c:out></h3>	
								</div>
								<div class="row">
									<h3><c:out value="${us.email}"></c:out></h3>	
								</div>
						
					</div>
					<div class="col-md-8 activity-box">
						<h3></h3>	
					
					</div>
				</div>
			</div>
		
		</body>
		
	</html>