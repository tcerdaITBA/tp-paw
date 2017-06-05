<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Modal -->
<div id="votersModal" class="modal fade" role="dialog">
  <div class="modal-dialog votersModalDialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><spring:message code="Product.votersList"/></h4>
      </div>
      <div class="modal-body votersModal-body">
        <c:forEach items="${voters}" var="voter">
        	<a href="<c:url value="/profile/${voter.userId}"/>">
	        	<div class="col-md-12 voter-box capitalize-firstLetter">
	        		<p><c:out value="${voter.name}"/></p>
	        	</div>
        	</a>
        </c:forEach>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>