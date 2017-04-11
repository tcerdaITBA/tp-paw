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
<link href="<c:url value="/resources/css/index.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
						<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
</head>

<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="row">
					<div class="col-md-12 product-list">

						<c:forEach items="${products}" var="product">
							<a href="<c:url value="/product/${product.id}"/>">
								<div class="row product-list-item vertical-align">
									<div class="col-md-3 product-logo">
										<img src="<c:url value="/product/${product.id}/logo"/>">
									</div>
									<div class="col-md-9 product-info-box">
										<div class="row col-md-12">
											<div class="row product-name">
												<div class="col-md-12">
													<p><c:out value="${product.name}"/></p>
												</div>
											</div>
											<div class="row product-short-description">
												<div class="col-md-12">
													<p><c:out value="${product.shortDescription}"/></p>
												</div>
											</div>
										</div>
									</div>
								</div>	
							</a>				
						</c:forEach>
						

					</div>
				</div>
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