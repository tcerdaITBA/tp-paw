<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <c:set var="capitalizedUserName" value="${fn:toUpperCase(fn:substring(profileUser.name, 0, 1))}${fn:substring(profileUser.name, 1,fn:length(profileUser.name))}" />
    
	<html>
		<head>
			<meta charset="utf-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
			<title><spring:message code="Profile.title" arguments="${capitalizedUserName}"/></title>
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
			<link href="<c:url value="/resources/css/customizeUser.css"/>" rel="stylesheet">
			<link href="<c:url value="/resources/css/img-upload.css"/>" rel="stylesheet">
			
			
			<link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">

		</head>
		<body>
			<%@include file="includes/navbar.jsp" %>
			<div class="container">
				<div class="row">
					<div class="col-md-3 profile-info-box">
							<sec:authorize access="isAuthenticated()">
								<c:if test="${loggedUser.userId == profileUser.userId}">
									<div class="row settings-row">
										<div class="col-md-offset-10">
											<div class="dropdown">
											  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
											    <span class="glyphicon glyphicon-cog"></span>
											    <span class="caret"></span>
											  </button>
											  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
											    <li class="pointer-cursor"><a
											    	data-toggle="modal" data-target="#changePictureModal">
														<spring:message code="Profile.settings.changePicture"/>
													</a></li>
											    <li class="pointer-cursor"><a data-toggle="modal" data-target="#changePassModal">
											    	<spring:message code="Profile.settings.changePassword"/>
											    	</a></li>
											  </ul>
											</div>
										</div>
										</div>
									</c:if>						
							</sec:authorize>
							
								<div class="row img-row">
									<div class="col-md-12">
										<img class="profile-img" src="<c:url value="/profile/${profileUser.userId}/profilePicture"/>">
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="profile-username">
											<span class="glyphicon glyphicon-user"></span>
											<p><c:out value="${capitalizedUserName}"></c:out></p>
										</div>	
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<a class="profile-mail" href="mailto:<c:out value="${profileUser.email}"/>">
											<span class="glyphicon glyphicon-envelope"></span>
											<p><c:out value="${profileUser.email}"></c:out></p>
										</a>	
									</div>
								</div>
						
					</div>
					<div class="col-md-7 col-md-offset-1">
						<c:choose>
							<c:when test="${products.isEmpty()}">
								<div class="zrp" id="user-products-zrp">
									<h2><spring:message code="userZRP.empty"/></h2>
									<h3><spring:message code="userZRP.noProducts" arguments="${capitalizedUserName}"/></h3>
								</div>
							</c:when>
							<c:otherwise>
							<h2 class="uploaded-products-title"><spring:message code="uploadedProductsTitle" arguments="${capitalizedUserName}"/></h2>
							<div class="col-md-12 product-list">
								<c:forEach items="${products}" var="product">									
									<a href="<c:url value="/product/${product.id}"/>">
										<div class="row product-list-item">
                                            <sec:authorize access="isAuthenticated()">
                                                <c:if test="${loggedUser.userId == profileUser.userId}">
                                                    <span id="delete${product.id}" class="glyphicon glyphicon-trash delete-product-button"></span>
                                                </c:if>
                                            </sec:authorize>
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
									
									<!-- The Modal -->
									<div id="deleteModal" class="modal fade">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
										            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										            <h4 class="modal-title">
																	<span class="glyphicon glyphicon-trash"></span>
																	<spring:message code="Profile.modal.deleteProduct"/>
																</h4>	
										        </div>
											    <div class="modal-body">
												
											    <div class="row">
											    	<div class="col-md-12">
											    		<p class="modal-text"><spring:message code="Profile.modal.textBeginning" />
											    		<span class="modal-product-name"><c:out value="${product.name}" /></span>
											    		<spring:message code="Profile.modal.textEnd" /></p>
											  		</div>
											  	</div>
											  	<div class="row row-centered">
													<div class="col-md-12">
														<c:url value="/delete/product/${product.id}" var="deletePath" />
														<form:form action="${deletePath}" method="post">
															<input class="ps-btn-red btn submit-btn" type="submit" value="<spring:message code="Profile.modal.leftButton"></spring:message>" />
															<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="Profile.modal.rightButton"/></button>
														</form:form>
													</div>
												</div>
																
											  </div>	
													
												</div>
											</div>	
										</div>
								</c:forEach>
							</div>
						</c:otherwise>
					</c:choose>									
				</div>
			</div>				
		</div>
			<%@include file="includes/changePictureModal.jsp"%>
			<%@include file="includes/changePasswordModal.jsp"%>
			<%@include file="includes/footer.jsp"%>
            <%@include file="includes/scripts.jsp"%>
			<script>
				var passError = "${passError}";
                var passFeedback = "${passFeedback}";
				var imgError = "${imgError}";
			</script>
			<script src="<c:url value="/resources/js/profile.js" />"></script>		
			<script src="<c:url value="/resources/js/upload-form.js"/>"></script>
		</body>
		
	</html>