<div class="col-md-12 page-numbers">
	<!--	first page-->
	<c:if test="${currentPage > 3}">
		<a href="<c:url value="/?page=1"/>" class="page-num"><c:out value="1..."/></a>
	</c:if>
	<!--	chevron left-->
	<c:if test="${currentPage > 1}">
		<a href="<c:url value="/?page=${currentPage-1}"/>" class="glyphicon glyphicon-chevron-left"></a>
	</c:if>
	
	<c:set var="pageBegin" value="${currentPage > 3 ? currentPage - 2 : 1}"/>
	<c:set var="pageEnd" value="${currentPage + 2 < totalPages ? currentPage + 2 : totalPages}"/>
	
	<c:forEach var="i" begin="${pageBegin}" end="${pageEnd}">
		<c:choose>
			<c:when test="${i == currentPage}">
				<span class="selected page-num"><c:out value="${i}"/></span>
			</c:when>
			<c:otherwise>
				<a href="<c:url value="/?page=${i}"/>" class="page-num"><c:out value="${i}"/></a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	
	<!--	chevron right-->
	<c:if test="${currentPage < totalPages}">
		<a href="<c:url value="/?page=${currentPage+1}"/>" class="glyphicon glyphicon-chevron-right"></a>
	</c:if>
	<!--	last page-->
	<c:if test="${currentPage < totalPages - 2}">
		<a href="<c:url value="/?page=${totalPages}"/>" class="page-num"><c:out value="..."/><c:out value="${totalPages}"/></a>
	</c:if>
</div>