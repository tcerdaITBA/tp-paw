<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
	<sec:authentication var="user" property="principal" />
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">

			<!--	Nav colapsado	-->
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only"><spring:message code="navBar.toggle" /></span>
				<!--	Tres rayitas del burguer button -->
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<a class="navbar-brand" href="<c:url value="/"/>"><img
				src="<c:url value="/resources/img/logo-wide.svg"/>" /></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li>
					<p class="navbar-btn">
					<sec:authorize access="isAnonymous()">
						<a href="<c:url value="/login" />" class="ps-btn btn upload-btn"><spring:message code="navBar.loginButton" /></a>
						<a href="<c:url value="/register" />" class="ps-btn btn upload-btn"><spring:message code="navBar.registerButton" /></a>								
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						<a href="<c:url value="/profile/${loggedUser.userId}" />" class="user-btn">
						<img class="user-nav-img" src="<c:url value="/profile/${loggedUser.userId}/profilePicture"/>">
						<span><c:out value="${loggedUser.name}"/></span>
						</a>
						<a href="<c:url value="/logout" />" class="ps-btn btn upload-btn"><spring:message code="navBar.logoutButton" /></a>
					</sec:authorize>
						<a href="<c:url value="/upload" />" class="ps-btn btn upload-btn"><spring:message code="navBar.postButton" /></a>
					</p>
				
				</li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>
