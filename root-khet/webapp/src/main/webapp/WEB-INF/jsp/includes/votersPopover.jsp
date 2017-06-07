<div id="votersPopover" class="row hide popover-body">
	<div class="col-md-12">
		<c:forEach items="${voters}" var="voter">
        	<a href="<c:url value="/profile/${voter.userId}"/>">
        		<div class="row voters-info-box">        		
	        		<div class="col-md-2 usr-img-col">
						<img class="votersPopover-img-circle" src="<c:url value="/profile/${voter.userId}/profilePicture"/>">
					</div>
		        	<div class="col-md-10 voter-box capitalize-firstLetter">
		        		<span class="result-text"><c:out value="${voter.name}"/></span>
		        	</div>
        		</div>
        	</a>
		 </c:forEach>
	</div>
</div>