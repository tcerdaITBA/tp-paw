<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<div id="category-zrp">
	<h2><spring:message code="categoryZRP.sorry"/></h2>
	<h2><spring:message code="categoryZRP.noProducts"/></h2>
	<p>
		<span><spring:message code="categoryZRP.checkOther"/></span>
		<span><a href="<c:url value="/upload"/>"><spring:message code="categoryZRP.postYourOwn"/></a></span>
	</p>
</div>