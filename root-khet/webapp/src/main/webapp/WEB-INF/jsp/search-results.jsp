<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
						<h3 class="search-title result-text"><spring:message code="searchResults.resultsFor" arguments="${fn:escapeXml(queryText)}"/></h3>
					</div>
				</div>
				<div class="row tabs-row">
					<div class="col-md-6 col-md-offset-3">
						<ul class="nav nav-pills nav-justified search-tabs">
							<c:set var="activeTab" value="${products.size() == 0 && users.size() != 0 }"></c:set>

			
							<li role="presentation" class="${!activeTab ? 'active' : 'none' }"><a href="#products-pane" data-toggle="tab"><spring:message code="searchResults.products"/><span class="badge"><c:out value="${products.size()}"/></span></a></li>
							<li role="presentation" class="${activeTab ? 'active' : 'none' }"><a href="#users-pane" data-toggle="tab"><spring:message code="searchResults.users"/><span class="badge tab-badge"><c:out value="${users.size()}"/></span></a></li>
						</ul>
					</div>
				</div>
				<div class="tab-content">
					<div id="products-pane" class="tab-pane fade row result-for-products ${!activeTab ? 'active in' : 'none' }">
						<c:choose>
							<c:when test="${products.isEmpty()}">
								<div class="col-md-6 col-md-offset-3">
									<div id="product-search-zrp" class="zrp">
										<h2><spring:message code="searchZRP.notFound"/></h2>
										<h3 class="result-text"><spring:message code="searchZRP.noProducts" arguments="${fn:escapeXml(queryText)}"/></h3>
										<p><spring:message code="searchZRP.tryDifferentSearch"/></p>	
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-md-3">
									<div class="panel filter-panel">
										<div class="panel-body">
											<h4 class="filter-title"><spring:message code="searchResults.filterByCategory"/></h4>
											<c:forEach items="${categories}" var="category">
												<div class="filter-item">
													<label class="filter-checkbox" id="filter-${category.lowerName}" >
														<input type="checkbox" name="category" value="${category.lowerName}">
														<spring:message code="category.${category.lowerName}"/>
													</label>
												</div>
											</c:forEach>
											<a href="#" id="reset-filters-btn"><spring:message code="searchResults.resetFilters"/></a>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<c:forEach items="${products}" var="product">
										<a class="product-item" href="<c:url value="/product/${product.id}"/>" data-category="${product.category.lowerName}">
											<div class="row product-list-item vertical-align">
												<div class="col-md-3 product-logo">
													<img src="<c:url value="/product/${product.id}/logo"/>">
												</div>
												<div class="col-md-9 product-info-box">
													<div class="row col-md-12">
														<div class="row">
															<div class="col-md-12 capitalize-firstLetter">
																<p class="product-name result-text"><c:out value="${product.name}"/></p>
															</div>
														</div>
														<div class="row product-short-description">
															<div class="col-md-12 capitalize-firstLetter">
																<p class="result-text"><c:out value="${product.shortDescription}"/></p>
															</div>
														</div>
														<div class="row product-category">
															<div class="col-md-12">
																<div data-href="<c:url value="/category/${product.category.lowerName}"/>" class="categoryTag product-category-btn">
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
							</c:otherwise>
						</c:choose>
					</div>
					<div id="users-pane" class="tab-pane fade row result-for-users ${activeTab ? 'active in' : 'none' }">
						<c:choose>
							<c:when test="${users.isEmpty()}">
								<div class="col-md-6 col-md-offset-3">
									<div id="user-search-zrp" class="zrp">
										<h2><spring:message code="searchZRP.notFound"/></h2>
										<h3 class="result-text"><spring:message code="searchZRP.noUsers" arguments="${fn:escapeXml(queryText)}"/></h3>
										<p><spring:message code="searchZRP.tryDifferentSearch"/></p>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-md-6 col-md-offset-3">
									<c:forEach items="${users}" var="user">
										<a class="user-ref" href="<c:url value="/profile/${user.userId}"/>">
											<div class="row user-info-box">
												<div class="col-md-3 img-col">
													<img class="profile-img-circle" src="<c:url value="/profile/${user.userId}/profilePicture"/>">
												</div>
												<div class="col-md-9">
													<div class="row">
														<div class="col-md-12">
															<div class="profile-name-holder capitalize-firstLetter">
																<span class="profile-name result-text"><c:out value="${user.name}"/></span>
															</div>
														</div>
													</div>
													<div class="row">
														<div class="col-md-12">
															<div class="creator-mail" data-href="mailto:<c:out value="${user.email}"/>">
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
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<%@include file="includes/footer.jsp"%>
			</div>
		</div>
	</div>

	<%@include file="includes/scripts.jsp"%>
	<script src="<c:url value="/resources/js/search-results.js"/>"></script>
</body>
</html>