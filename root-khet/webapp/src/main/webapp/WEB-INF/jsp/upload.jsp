<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>

<body>
	<div class="container">
	<div>
		<div>
			<h2>Upload Product</h2>
		</div>
		<div>
		<c:url value="/upload" var="postPath" />
		<form:form modelAttribute="uploadForm" action="${postPath}" method="post">
		<div>
			<form:label path="name"><spring:message code="formLabel.productName" /></form:label>
			<form:input type="text" path="name" />
			<form:errors path="name" cssClass="" element="p"/>
		</div>
		<div>
			<form:label path="creatorName"><spring:message code="formLabel.creatorName" /></form:label>
			<form:input type="text" path="name" />
			<form:errors path="creatorName" cssClass="" element="p"/>
		</div>
		<div>
			<form:label path="description"><spring:message code="formLabel.description" /></form:label>
			<form:input type="text" path="description" />
			<form:errors path="description" cssClass="" element="p"/>
			
		</div>
		<div>
			<form:label path="shortDescription"><spring:message code="formLabel.shortDescription" /></form:label>
			<form:input type="text" path="description" />
			<form:errors path="shortDescription" cssClass="" element="p"/>
		</div>
		<div>
			<form:label path="logo"><spring:message code="formLabel.logo" /></form:label>
			<form:input type="file" path="logo" />
			<form:errors path="logo" cssClass="" element="p"/>
		</div>
		<div>
			<form:label path="image"><spring:message code="formLabel.image" /></form:label>
			<form:input type="file" path="image" />
			<form:errors path="image" cssClass="" element="p"/>
		</div>
		<div>
			<form:label path="video"><spring:message code="formLabel.video" /></form:label>
			<form:input type="url" path="video" />
			<form:errors path="video" cssClass="" element="p"/>
		</div>
		
		<div>
			<input type="submit" value="Upload Product!" />
		</div>
	</form:form>
		</div>
		</div>
	</div>
	
</body>
</html>