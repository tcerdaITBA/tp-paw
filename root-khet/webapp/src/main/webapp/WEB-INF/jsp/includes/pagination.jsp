<div class="col-md-12 page-numbers">
	<c:if test="${currentPage > 1}">
		<a href="<c:url value="/?page=${currentPage-1}"/>" class="glyphicon glyphicon-chevron-left"></a>
	</c:if>
	<c:forEach begin="1" end="${totalPages}" varStatus="pageNum">
		<c:choose>
			<c:when test="${pageNum.count == currentPage}">
				<span class="selected page-num"><c:out value="${pageNum.count}"/></span>
			</c:when>
			<c:otherwise>
				<a href="<c:url value="/?page=${pageNum.count}"/>" class="page-num"><c:out value="${pageNum.count}"/></a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${currentPage < totalPages}">
		<a href="<c:url value="/?page=${currentPage+1}"/>" class="glyphicon glyphicon-chevron-right"></a>
	</c:if>
</div>