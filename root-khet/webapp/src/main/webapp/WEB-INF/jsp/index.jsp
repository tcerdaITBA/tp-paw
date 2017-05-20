<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>    

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<c:choose>
    <c:when test="${empty currentCategory}"><c:set var="pageTitle" value="default.title"/></c:when>
    <c:otherwise><c:set var="pageTitle" value="default.title.${currentCategory.lowerName}"/></c:otherwise>
</c:choose>
    
<title><spring:message code="${pageTitle}" /></title>
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
	<div class="row title-row">
		<div class="col-md-8 col-md-offset-3 title-col">
			<div class="content-title">
				<c:choose>
				 <c:when test="${empty currentCategory}"><h2><spring:message code="index.mostrecent"/></h2></c:when>
				 <c:otherwise>
				 <h2><spring:message code="category.${currentCategory.lowerName}"/></h2>
				 <div class="categoryDescription"><spring:message code="category.description.${currentCategory.lowerName}"/></div>
				 </c:otherwise>
				 </c:choose>					
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3">
				<div class="row">
					<div class="col-md-9">
						<div class="row">
							<ul class="nav nav-pills nav-stacked categoryBox">
								<c:set var="active" value="${empty currentCategory}"/>
								<li role="presentation" class="${active ? 'active' : 'none'}">
										<a href="<c:url value="/"/>">
										<div class="col-md-5"></div>
										<spring:message code="category.all"/></a>
								</li>
							<c:forEach items="${categories}" var="category">
						     <c:set var="active" value="${category eq currentCategory}"/>
								<li role="presentation" class="${active ? 'active' : 'none'}">					
									<a href="<c:url value="/category/${category.lowerName}"/>">
									<div class="col-md-5">
										<img class="icon" src="<c:url value="/resources/img/${category.lowerName}.svg"/>"/>
									</div>
										<spring:message code="category.${category.lowerName}"/>
									</a>
								</li>
							</c:forEach>
							</ul>
						</div>
					</div>
			</div>
		</div>	
	
		<div class="col-md-8">
					<c:choose>
						<c:when test="${products.isEmpty()}">
							<div class="col-md-10 col-md-offset-1">
								<div class="zrp" id="category-zrp">
									<h2><spring:message code="categoryZRP.sorry"/></h2>
									<h3><spring:message code="categoryZRP.noProducts"/></h3>
									<p>
										<span><spring:message code="categoryZRP.checkOther"/></span>
										<span>
											<a href="<c:url value="/upload"/>"><spring:message code="categoryZRP.postYourOwn"/></a>
										</span>
									</p>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="col-md-10 col-md-offset-1 product-list">
							<c:forEach items="${products}" var="product">
								
								<a href="<c:url value="/product/${product.id}"/>">
									<div class="row product-list-item">
										<div class="col-md-3 product-logo">
											<img src="<c:url value="/product/${product.id}/logo"/>">
										</div>
										<div class="col-md-9 product-info-box">
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
											<div class="row">
												<a href="<c:url value="/category/${product.category.lowerName}"/>" class="product-category">
													<div class="col-md-3">
														<div class="categoryTag">
															<p><spring:message code="category.${product.category.lowerName}"/></p>
														</div>
													</div>
												</a>
											</div>
										</div>
									</div>	
								</a>
								
								
							</c:forEach>
							</div>
						</c:otherwise>
				</c:choose>									
				<%@include file="includes/pagination.jsp"%></%@include>
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
	</div>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
	<script src="<c:url value="/resources/js/search.js" />"></script>	
</body>
</html>