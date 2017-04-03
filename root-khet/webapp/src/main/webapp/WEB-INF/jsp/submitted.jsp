<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" --%>
<%--     pageEncoding="UTF-8"%> --%>
<html>
<head>
				<link href="<c:url value="/resources/css/submitted.css" />" rel="stylesheet" />

</head>
<body>
	<h1> Probando </h1>

	<div style="height:400px, width:640px'" id="div_top_hypers">
		<ol id="ul_top_hypers">
			<c:forEach items="${videos}" var="video">
			  	<li style="height:360px; width:640px">
						<div style="height: 360px; width: 640px;">
							<span>
									<iframe frameborder="0" allowfullscreen="1" title="YouTube video player" width="640" height="360" 
									src="https://www.youtube.com/embed/${video.videoId}?autohide=1&amp;modestbranding=1&amp;iv_load_policy=3&amp;rel=0&amp;enablejsapi=1;"></iframe>
							</span>
						</div>
				</li>
			</c:forEach>
	 	</ol>
 	</div>

</body>
</html>