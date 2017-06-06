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
        		<div class="row voters-info-box">        		
	        		<div class="col-md-2 usr-img-col">
						<img class="profile-img-circle" src="<c:url value="/profile/${voter.userId}/profilePicture"/>">
					</div>
		        	<div class="col-md-10 voter-box capitalize-firstLetter">
		        		<span class="result-text"><c:out value="${voter.name}"/></span>
		        	</div>
        		</div>
        	</a>
        </c:forEach>
      </div>
    </div>

  </div>
</div>