<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title><spring:message code="searchResults.title" /></title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<link href="<c:url value="/resources/css/index.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
<link href="<c:url value="/resources/css/search.css"/>" rel="stylesheet">
<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
<link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">
</head>

<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="row">
					<div class="col-md-10 col-md-offset-1">
						<h3 class="search-title"><spring:message code="searchResults.resultsFor" arguments="${queryText}"/></h3>
					</div>
				</div>
				<div class="row tabs-row">
					<div class="col-md-6 col-md-offset-3">
						<ul class="nav nav-pills nav-justified search-tabs">
							<li role="presentation" class="active"><a href="#products-pane" data-toggle="tab"><spring:message code="searchResults.products"/></a></li>
							<li role="presentation"><a href="#users-pane" data-toggle="tab"><spring:message code="searchResults.users"/></a></li>
						</ul>
					</div>
				</div>
				<div class="tab-content">
					<div id="products-pane" class="tab-pane fade in active row result-for-products">
						<div class="col-md-6 col-md-offset-3">
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
												<div class="row product-category">
													<div class="col-md-4">
														<div class="categoryTag">
															<p><spring:message code="category.${product.category.lowerName}"/></p>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>	
								</a>				
							</c:forEach>
						</div>
					</div>
					<div id="users-pane" class="tab-pane fade row result-for-users">
						<div class="col-md-6 col-md-offset-3">
							<c:forEach items="${users}" var="user">
								<a href="<c:url value="/profile/${user.userId}"/>">
									<div class="row user-info-box">
										<div class="col-md-3 img-col">
											<img class="profile-img-circle" src="<c:url value="/profile/${user.userId}/profilePicture"/>">
										</div>
										<div class="col-md-9">
											<div class="row">
												<div class="col-md-12">
													<div class="profile-name-holder">
														<span class="glyphicon glyphicon-user"></span>
														<span class="profile-name"><c:out value="${user.name}"/></span>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<div class="creator-mail" href="mailto:<c:out value="${user.email}"/>">
														<span class="glyphicon glyphicon-envelope"></span>
														<p><c:out value="${user.email}"/></p>
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
				<%@include file="includes/footer.jsp"%>
			</div>
		</div>
	</div>


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