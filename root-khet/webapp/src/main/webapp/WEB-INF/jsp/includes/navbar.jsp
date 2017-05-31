<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
	<sec:authentication var="user" property="principal" />
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
			
			<a class="navbar-brand" href="<c:url value="/"/>">
				<img src="<c:url value="/resources/img/logo-wide.svg"/>"/>
			</a>
		</div>

		<form action="<c:url value="/search"/>" method="get" class="navbar-form navbar-left">
			<div class="form-group">
				<input id="search-box" pattern=".{3,}" maxlength="64" required oninvalid="this.setCustomValidity('<spring:message code="navBar.search.minimum3"/>')" onchange="try{setCustomValidity('')}catch(e){}" value="${fn:escapeXml(queryText)}" name="query" type="text" class="form-control" placeholder="<spring:message code="navBar.search.placeholder"/>">
				<button type="submit" class="search-button">
					<span class="glyphicon glyphicon-search search-icn"></span>
				</button>
			</div>
		</form>
			
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="isAnonymous()">
					<li>
						<a href="<c:url value="/register" />" class="navbar-link"><spring:message code="navBar.signupButton" /></a>
					</li>	
					<li>
						<a href="<c:url value="/login" />" class="navbar-link"><spring:message code="navBar.loginButton" /></a>	
					</li>
				</sec:authorize>	
				<sec:authorize access="isAuthenticated()">
					<li>
						<a class="user-nav-img" href="<c:url value="/profile/${loggedUser.userId}"/>">
							<img class="img-contain" src="<c:url value="/profile/${loggedUser.userId}/profilePicture"/>">
						</a>
					</li>
					<li>
						<a class="user-nav-name" href="<c:url value="/profile/${loggedUser.userId}"/>">
							<span><c:out value="${loggedUser.name}"/></span>
						</a>
					</li>
					<li>
						<a href="<c:url value="/logout"/>" class="navbar-link"><spring:message code="navBar.logoutButton" /></a>
					</li>
				</sec:authorize>
				<li>
					<p class="navbar-btn">
						<sec:authorize access="isAuthenticated()">
							<a href="<c:url value="/upload"/>" class="ps-btn btn upload-btn"><spring:message code="navBar.postButton" /></a>
						</sec:authorize>
						<sec:authorize access="isAnonymous()">
							<a tabindex="0" class="ps-btn btn upload-btn" role="button" data-toggle="popover" data-trigger="focus" 
								 data-content="<p class='popover-msg'>
								 <span><spring:message code="navBar.toPost"/></span>
								 <a href='<c:url value="/login"/>'> 
								 <span> <spring:message code="navBar.signIn"/></span>
								 </a></p>" id="upload-popover"><spring:message code="navBar.postButton" /></a>
						</sec:authorize>
					</p>
				</li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>
