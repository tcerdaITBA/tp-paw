<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!--foreach-->
<div class="row search-product-list-item vertical-align">
	<div class="col-md-3 search-product-logo">
		<img src="<c:url value="/product/${product.id}/logo"/>">
	</div>
	<div class="col-md-9 search-product-info-box">
		<div class="row col-md-12">
			<div class="row search-product-name">
				<div class="col-md-12">
					<p><c:out value="${product.name}"/></p>
				</div>
			</div>
			<div class="row search-product-short-description">
				<div class="col-md-12">
					<p><c:out value="${product.shortDescription}"/></p>
				</div>
			</div>
			<div class="row search-product-category">
				<div class="col-md-4">
					<div class="search-categoryTag">
						<p><spring:message code="category.${product.category.lowerName}"/></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>	
<div class="divide-line" />
<!--foreach-->
<div class="row user-list-item vertical-align">
	<div class="col-md-3 search-user-img">
<!--		img src user profile picture-->
<!--		<img src="<c:url value="/product/${product.id}/logo"/>">-->
	</div>
	<div class="col-md-9 search-user-info-box">
		<div class="row search-user-name">
			<div class="col-md-12">
				<p><c:out value="${username}"/></p>
			</div>
		</div>
		<div class="row search-user-mail">
			<div class="col-md-12">
				<p><c:out value="${product.shortDescription}"/></p>
			</div>
		</div>
	</div>
</div>	