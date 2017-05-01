<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
    <body>
    	<%@include file="includes/navbar.jsp"%>
    	<h2><spring:message code="login.title" /></h2>
        <c:url value="/login" var="postPath" />
        <form action="${postPath}" method="post" enctype="application/x-www-form-urlencoded">
        	<div>
        		<label for="j_username">Username: </label>
        		<input type="text" name="j_username" id="j_username" />
        	</div>
        	<div>
        		<label for="password">Password: </label>
        		<input type="password" name="j_password" id="j_password"/>
        	</div>
        	<div>
        		<label for="j_rememberme">Remember me: </label>
        		<input type="checkbox" name="j_rememberme" id="j_rememberme" />
        	</div>
        	<div>
        		<input type="submit" value="Login!" />
        	</div>
        </form >
    </body>
</html>
