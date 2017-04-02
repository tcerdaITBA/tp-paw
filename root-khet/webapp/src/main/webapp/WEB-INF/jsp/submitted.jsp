<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>

</head>

<body>
	<h1><c:out value="${product.name}"></c:out></h1>
	<div>
		<span>
				<iframe frameborder="0" allowfullscreen="1" title="YouTube video player" width="320" height="160" 
				src="https://www.youtube.com/embed/${video}?autohide=1&amp;modestbranding=1&amp;iv_load_policy=3&amp;rel=0&amp;enablejsapi=1;"></iframe>
		</span>
	</div>
<!-- 	<div style="height: 400px" > -->
<!-- 		<ol> -->
<%-- 			<c:forEach items="${product.videos}" var="video"> --%>
<!-- 		  	<li> -->
<!-- 					<div> -->
<!-- 						<span> -->
<!-- 								<iframe frameborder="0" allowfullscreen="1" title="YouTube video player" width="320" height="160"  -->
<!-- 								src="https://www.youtube.com/embed/{STRING_VARIABLE}?autohide=1&amp;modestbranding=1&amp;iv_load_policy=3&amp;rel=0&amp;enablejsapi=1;"></iframe> -->
<!-- 						</span> -->
<!-- 					</div> -->
<!-- 			</li> -->
<%-- 			</c:forEach> --%>
			
<%-- 			<c:forEach items="${product.images}" var="image"> --%>
<!-- 		  	<li> -->
<!-- 			</li> -->
<%-- 			</c:forEach> --%>
			
<!-- 		</ol> -->
<!-- 	</div> -->
</body>
</html>