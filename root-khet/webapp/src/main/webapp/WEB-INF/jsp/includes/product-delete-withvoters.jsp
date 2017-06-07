                    <div class="col-md-7">
         				<div class="row product-item product-item-height vertical-align highlighted">
                             <div class="col-md-3 product-logo">
                                 <img src="<c:url value="/product/${product.id}/logo"/>">
                             </div>
                             <div class="col-md-9 product-info-box">
 							 <sec:authorize access="isAuthenticated()">
                               <c:if test="${loggedUser.userId == creator.userId}">
                                   <span id="delete${product.id}" data-product-id="${product.id}" class="glyphicon glyphicon-trash delete-product-button"></span>
 	                               <!-- The Modal -->
                                   <%@include file="deleteModal.jsp"%>
                               </c:if>
                             </sec:authorize>
                                 <div class="row col-md-12">
                                     <div class="row product-name">
                                         <div class="col-md-12 info-box-field capitalize-firstLetter">
                                             <p><c:out value="${product.name}"/></p>
                                         </div>
                                     </div>
                                     <div class="row product-short-description">
                                         <div class="col-md-12 info-box-field capitalize-firstLetter" >
                                             <p><c:out value="${product.shortDescription}"/></p>
                                         </div>
                                     </div>
                                     <div class="row">
                                        <div class="col-md-3">
		                                         	<a href="<c:url value="/category/${product.category.lowerName}"/>" class="product-category">
		 												<div class="categoryTag">
 													<p><spring:message code="category.${product.category.lowerName}"/></p>
 												</div>
                                         	</a>
                                        </div>
                                   	   <%@include file="votersModal.jsp"%>
                                        
                                        <div class="col-md-4 col-md-offset-3 text-right voters-holder">
 	                                       	<span class="voter-span" data-toggle="modal" data-target="#votersModal">
 	                                       		<span class="voter-span" data-toggle="tooltip" data-placement="bottom" title="<spring:message code="ProductPage.votersTooltip"/>">
 			                                       <c:forEach items="${votersresume}" var="voter">
 		                                       			<img class="profile-img-circle voters-img" src="<c:url value="/profile/${voter.userId}/profilePicture"/>">
 												   </c:forEach>
 										  		</span>
 										  	</span>
 									   </div>
                                        <c:url value="/vote/product/${product.id}" var="vote" />
 										<div class="col-md-2">
 											<sec:authorize access="isAuthenticated()">
												<form:form class="pull-right" action="${vote}" method="post">
														<button class="btn btn-default upvote-btn upvote-auth-btn" type="submit" data-vote-id="vote${product.id}">
															<p><span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
															<c:out value="${product.votesCount}"/></p>
														</button>
												</form:form>
											</sec:authorize>
											<sec:authorize access="isAnonymous()">
												<button class="btn btn-default upvote-btn popover-btn" data-toggle="popover" data-trigger="focus" 
												data-content="<p class='popover-msg'>
												 <span><spring:message code="upVote.toPost"/></span>
												 <a href='<c:url value="/login"/>'> 
												 <span> <spring:message code="upVote.signIn"/></span>
												 </a>
												 <span><spring:message code="upVote.toPostBis"/></span>
												 <a href='<c:url value="/register"/>'> 
												 <span> <spring:message code="upVote.signUp"/></span>
												 </a>
												 </p>">
													<p><span class="glyphicon glyphicon-arrow-up upvote-icon"></span>
													<c:out value="${product.votesCount}"/></p>
												</button>
											</sec:authorize>
 										</div>
 					                   <sec:authorize access="isAuthenticated()">
 					                   		<c:if test="${ product.votingUsers.contains(loggedUser) }">
 					                    		<script>
 					                    		upVotedProductByLoggedUser(${product.id});
 					                    		</script>
 					                    	</c:if>							                    
 					                   </sec:authorize>
                                     </div>
                                 </div>
                             </div>
                         </div>
                        <div class="row">
                            <div class="col-md-4 website-btn-col">
                                <c:if test="${not empty product.website}">
                                    <div class="website-btn-row">
                                        <a href="<c:out value="${product.website}"/>" class="ps-btn btn website-btn" target="_blank">
                                            <span class="glyphicon glyphicon-globe"></span>
                                            <spring:message code="productPage.visitWebsite"/>
                                        </a>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>