<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
		<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

			<html>
				<head>
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
					<!--TODO: No se pone #fff el navbar-->
					<%@include file="includes/navbar.jsp" %>
						<div class="form-container container">
							<div class="row">
								<div class="row">
									<c:url value="/upload" var="postPath"/>
									<div class="col-md-10">
										<div class="col-sm-9 col-sm-offset-3">
											<div>
												<h2>Post Product</h2>
											</div>
											<div>
												<h4>Give us some information about your product</h4>
											</div>
										</div>
										<form:form modelAttribute="uploadForm" action="${postPath}" method="post" enctype="multipart/form-data"
															 class="form-horizontal">
											<div class="row">
												<div class="col-md-12 form-group">
													<form:label path="name" class="col-sm-3 control-label"><spring:message code="formLabel.productName" /></form:label>
													<div class="col-sm-9">
														<form:input type="text" path="name" class="form-control" placeholder="Name"/>
														<form:errors path="name" cssClass="" element="p"/>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 form-group">
													<form:label path="shortDescription" class="col-sm-3 control-label"><spring:message code="formLabel.shortDescription"/></form:label>
													<div class="col-sm-9">
														<form:textarea type="text" path="shortDescription" placeholder="Short Description" class="form-control" rows="1"/>
														<form:errors path="shortDescription" cssClass="" element="p"/>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12 form-group">
													<form:label path="description" class="col-sm-3 control-label"><spring:message code="formLabel.description"/></form:label>
													<div class="col-sm-9">
														<form:textarea type="text" path="description" class="form-control" rows="4" placeholder="Full Description"/>
														<form:errors path="description" cssClass="" element="p"/>
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-md-12">
													<form:label path="logo"><spring:message code="formLabel.logo" /></form:label>
													<form:input type="file" path="logo" accept="image/*"/>
													<form:errors path="logo" cssClass="" element="p"/>
												</div>
											</div>
											<c:forEach items="${uploadForm.images}" varStatus="status">
												<div>
													<form:label path="images[${status.index}].file"><spring:message code="formLabel.image" /></form:label>
													<form:input type="file" path="images[${status.index}].file" accept="image/*"/>
													<form:errors path="images[${status.index}].file" cssClass="" element="p"/>
												</div>
											</c:forEach>
											<div class="row">
												<div class="col-sm-9 col-sm-offset-3">
													<h5>Youtube Videos</h5>
													<c:forEach items="${uploadForm.videos}" varStatus="status">
														<div class="form-inline video-form">
															<form:label path="videos[${status.index}].url"><spring:message code="formLabel.video" /></form:label>
															<form:input type="url" path="videos[${status.index}].url" class="form-control" placeholder="Video URL"/>
															<form:errors path="videos[${status.index}].url" cssClass="" element="p"/>
														</div>
													</c:forEach>
												</div>
											</div>
											
											<div class="row creator-data-row">
												<div class="col-md-12 form-inline">
													<div class="col-sm-9 col-sm-offset-3">
														<div class="form-group">
															<form:label path="creatorName"><spring:message code="formLabel.creatorName" /></form:label>
															<form:input type="text" path="creatorName" class="form-control" placeholder="Juan Pérez"/>
															<form:errors path="creatorName" cssClass="" element="p"/>
														</div>
														<div class="form-group">
															<form:label path="creatorMail"><spring:message code="formLabel.creatorMail" /></form:label>
															<form:input type="email" path="creatorMail" class="form-control" placeholder="juan@productseek.com"/>
															<form:errors path="creatorMail" cssClass="" element="p"/>
														</div>
													</div>
												</div>
											</div>



											<div class="row row-centered">
												<div class="col-md-12">
													<div class="col-sm-9 col-sm-offset-3">
														<form:errors path="images" cssClass="" element="p"/>
														<input class="ps-btn-red btn submit-btn" type="submit" value="Post 🎉" />
													</div>
												</div>
											</div>

<!--
											<div id="dZUpload" class="dropzone">
												<div class="dz-default dz-message"></div>
											</div>
-->
											
										</form:form>
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
							<script src="<c:url value="/resources/js/dropzone.js"/>"></script>
							<script src="<c:url value="/resources/js/uploadapp.js"/>"></script>
							</body>
						</html>