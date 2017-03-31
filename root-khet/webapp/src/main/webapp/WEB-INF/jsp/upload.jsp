<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>

</head>

<body>
	<h2>Upload Product</h2>
	<c:url value="/upload" var="postPath" />
	<form:form modelAttribute="uploadForm" action="${postPath}"method="post">
		<div>
			<form:label path="name">Name of the product: </form:label>
				<form:input type="text" path="name" />
		</div>
		<div>
			<form:label path="description">Description: </form:label>
			<form:input type="text" path="description" />
		</div>
		<div>
			<form:label path="shortDescription">Short description: </form:label>
			<form:input type="text" path="description" />
		</div>
		<div>
			<input type="submit" value="Upload!" />
		</div>
	</form:form>
</body>
</html>