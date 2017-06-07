<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <c:set var="pageTitle" value="${fn:toUpperCase(fn:substring(product.name, 0, 1))}${fn:substring(product.name, 1,fn:length(product.name))}"/>
    <title><spring:message code="productPage.title" arguments="${fn:escapeXml(pageTitle)}" /></title>
    <link
                href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
                rel="stylesheet"
                integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
                crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.css"/>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick-theme.css"/>
    <link href="<c:url value="/resources/css/index.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/product.css" />" rel="stylesheet" />
		<link href="<c:url value="/resources/css/product-item.css" />" rel="stylesheet" />
    <link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
    <link href="<c:url value="/resources/css/modal.css"/>" rel="stylesheet">

</head>
<body>
<spring:message code="formLabel.emailPlaceholder" var="EmailPlaceholder"/>
<spring:message code="commentLabel.userNamePlaceholder" var="UserNamePlaceholder"/>
<spring:message code="commentLabel.contentPlaceholder" var="ContentPlaceholder"/>	

<script src="<c:url value="/resources/js/upvote.js" />"></script>
<%@include file="includes/navbar.jsp" %>
    <div class="container">
        <div class="row" id="carouselHolder">
            <div class="col-md-6 col-md-offset-3">
                <div class="multiple-items carousel highlighted">
                    <c:forEach items="${videos}" var="video">
                        <div class="embed-responsive embed-responsive-16by9 video-holder">
                            <iframe class="embed-responsive-item video-item" src="//www.youtube.com/embed/${video.videoId}?rel=0" allowfullscreen></iframe>
                        </div>
                    </c:forEach>
                    <c:forEach items="${images}" var="image">
                        <div class="image-holder">
                            <img class="image-item" src="<c:url value="/product/${product.id}/image/${image}"/>" >
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <div class="row">
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
                                   <%@include file="includes/deleteModal.jsp"%>
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
                                         	<a href="<c:url value="/?category=${product.category.name}"/>" class="product-category">
 												<div class="categoryTag">
 													<p><spring:message code="category.${product.category.lowerName}"/></p>
 												</div>
                                         	</a>
                                        </div>
                                   	   <%@include file="includes/votersModal.jsp"%>
                                        
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
														<button class="btn btn-default upvote-btn" type="submit" data-vote-id="vote${product.id}">
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

                    <div class="col-md-4 col-md-offset-1 creator-item highlighted">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="row creator-first-row-holder">
                                    <div class="col-md-5 product-name creator-name">
                                        <p><spring:message code="productPage.creator" /></p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-2">
                                        <a href="<c:url value="/profile/${creator.userId}"/>">
                                            <img class="profile-img-circle" src="<c:url value="/profile/${creator.userId}/profilePicture"/>">
                                        </a>
                                    </div>
                                    <div class="col-md-10">
                                        <div class="row col-md-12 profile-name-holder">
                                            <a class="profile-name" href="<c:url value="/profile/${creator.userId}"/>"> 
                                                <c:out value="${creator.name}" />
                                            </a>
                                        </div>
											<div class="row col-md-12">
												<a class="creator-mail" href="mailto:<c:out value="${creator.email}"/>">
														<span class="glyphicon glyphicon-envelope"></span>
														<p><c:out value="${creator.email}"/></p>
												</a>
											</div>										
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row long-description">
                    <div class="col-md-12">
                        <p><c:out value="${product.description}" /></p>
                    </div>
                </div>

                <p class="join-discussion" >
                    <spring:message code="productPage.joinDiscussion"/>
                </p>
                <sec:authorize access="isAuthenticated()">
                <c:url value="/product/${product.id}/comment" var="postPath" />
                <div class="row" id="formparent">
                    <div class="col-md-7 highlighted parent-form-comment">	
                        <div class="row">
                            <div class="col-md-1">
                                <a href="<c:url value="/profile/${loggedUser.userId}"/>">
                                    <img class="profile-img-circle" src="<c:url value="/profile/${loggedUser.userId}/profilePicture"/>">
                                </a>
                            </div>
                            <div class="col-md-10 parent-name-mail-holder">
                                <div class="row col-md-12 profile-name-holder">
                                    <a class="profile-name" href="<c:url value="/profile/${loggedUser.userId}"/>"> 
                                        <c:out value="${loggedUser.name}" />
                                    </a>
                                </div>
                                <div class="row col-md-12">
                                    <a class="creator-mail" href="mailto:<c:out value="${loggedUser.email}"/>">
                                        <span class="glyphicon glyphicon-envelope"></span>
                                        <p><c:out value="${loggedUser.email}"/></p>
                                    </a>
                                </div>										
                            </div>
                        </div>											
                        <form:form modelAttribute="commentsForm" class="comment-form" action="${postPath}" method="post">
                            <div class="form-group">
                                <form:textarea type="text" class="form-control" rows="3" path="parentForm.content" placeholder="${ContentPlaceholder}" maxlength="512"/>
                                <form:errors path="parentForm.content" element="p" cssClass="form-error"/>
                            </div>
                            <div class="btn-place">
                                <input type="submit" class="btn btn-default post-comment-btn" value="<spring:message code="productPage.comment.post" />" />
                            </div>
                        </form:form>
                    </div>
                </div>
                </sec:authorize>
                <sec:authorize access="isAnonymous()">
                <div class="row">
                    <div class="col-md-7 highlighted anonymous-holder">
                        <p> <spring:message code="productPage.anonymousComment1"/> 
                        <a href="<c:url value="/login"/>"><spring:message code="productPage.anonymousComment2"/> </a>
                        <spring:message code="productPage.anonymousComment3"/>
                        <a href="<c:url value="/register"/>"><spring:message code="productPage.anonymousComment4"/></a>
                        </p>
                    </div>
                </div>
                </sec:authorize>

                <div class="row">
                    <div class="col-md-7 comments-holder highlighted">
                        <c:forEach items="${parentcomments}" var="commentFamily" varStatus="status">

                            <div class="comment-and-replies">
                            <div class="parent-comment" id="comment${commentFamily.parentComment.id}">
                                <div class="row">
                                    <div class="col-md-1">
                                        <a href="<c:url value="/profile/${commentFamily.parentComment.author.userId}"/>">
                                            <img class="profile-img-circle" src="<c:url value="/profile/${commentFamily.parentComment.author.userId}/profilePicture"/>">
                                        </a>
                                    </div>
                                    <div class="col-md-10 parent-name-mail-holder">
                                        <div class="row col-md-12 profile-name-holder">
                                            <a class="profile-name" href="<c:url value="/profile/${commentFamily.parentComment.author.userId}"/>"> 
                                                <c:out value="${commentFamily.parentComment.author.name}" />
                                            </a>															</div>
                                        <div class="row col-md-12">
                                            <a class="creator-mail" href="mailto:<c:out value="${commentFamily.parentComment.author.email}"/>">
                                                <span class="glyphicon glyphicon-envelope"></span>
                                                <p><c:out value="${commentFamily.parentComment.author.email}"/></p>
                                            </a>
                                        </div>										
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 comment-content">
                                        <p>
                                            <c:out value="${commentFamily.parentComment.content}" />
                                        </p>
                                    </div>
                                </div>
                                <div class="row reply-button-holder">
                                    <div class="col-md-4">
                                        	<sec:authorize access="isAnonymous()">
                                        		<a tabindex="0" class="reply-btn" role="button" data-toggle="popover" data-trigger="focus" 
												 data-content="<p class='popover-msg'>
												 <span><spring:message code="productPage.anonymousComment1"/></span>
												 <a href='<c:url value="/login"/>'>
												 <span> <spring:message code="productPage.anonymousComment2"/></span>
								 				</a>
								 				<span><spring:message code="productPage.anonymousComment3"/></span>
												 <a href='<c:url value="/register"/>'>
												 <span> <spring:message code="productPage.anonymousComment4"/></span>
								 				</a>
								 				</p>" id="reply-popover">
												 <span class="glyphicon glyphicon-share-alt"></span>
						                         <spring:message code="productPage.reply"/>
								 				</a>
                                        	</sec:authorize>
                                        	<sec:authorize access="isAuthenticated()">
		                                        <p class="reply-btn reply-btn-fn">
	                                        		<span class="glyphicon glyphicon-share-alt"></span>
		                                            <spring:message code="productPage.reply"/>
		                                        </p>
                                        	</sec:authorize>    	
                                    </div>
                                </div>       
                                <div class="row comment-divider">
                                    <div class="col-md-12"></div>
                                </div>
                            </div>

                            <c:forEach items="${commentFamily.childComments}" var="child">
                                <div class="row child-comment" id="comment${child.id}">
                                    <div class="col-md-10 col-md-offset-2">
                                        <div class="row">
                                            <div class="col-md-1">
                                                <a href="<c:url value="/profile/${child.author.userId}"/>">
                                                    <img class="profile-img-circle" src="<c:url value="/profile/${child.author.userId}/profilePicture"/>">
                                                </a>
                                            </div>
                                            <div class="col-md-10 child-name-mail-holder">
                                                <div class="row col-md-12 profile-name-holder">
                                                    <a class="profile-name" href="<c:url value="/profile/${child.author.userId}"/>"> 
                                                        <c:out value="${child.author.name}" />
                                                    </a>
                                                </div>
                                                <div class="row col-md-12">
                                                    <a class="creator-mail" href="mailto:<c:out value="${child.author.email}"/>">
                                                        <span class="glyphicon glyphicon-envelope"></span>
                                                        <p><c:out value="${child.author.email}"/></p>
                                                    </a>
                                                </div>										
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12 comment-content">
                                                <p>
                                                    <c:out value="${child.content}" />
                                                </p>
                                            </div>
                                        </div>
                                        <div class="row comment-divider">
                                            <div class="col-md-12"></div>
                                        </div>
                                    </div>
                                </div>	
                            </c:forEach>												
                            <sec:authorize access="isAuthenticated()">
                            <div class="row child-comment-row">
                                <div class="col-md-10 col-md-offset-2">
                                    <form:form modelAttribute="commentsForm" id="form${status.index}" class="comment-form reply-comment" action="${postPath}?parentid=${commentFamily.parentComment.id}&index=${status.index}" method="post">
                                        <div class="row">
                                            <div class="col-md-1">
                                                <a href="<c:url value="/profile/${loggedUser.userId}"/>">
                                                    <img class="profile-img-circle" src="<c:url value="/profile/${loggedUser.userId}/profilePicture"/>">
                                                </a>
                                            </div>
                                            <div class="col-md-10 child-name-mail-holder">
                                                <div class="row col-md-12 profile-name-holder">
                                                    <a class="profile-name" href="<c:url value="/profile/${loggedUser.userId}"/>"> 
                                                        <c:out value="${loggedUser.name}" />
                                                    </a>
                                                </div>
                                                <div class="row col-md-12">
                                                    <a class="creator-mail" href="mailto:<c:out value="${loggedUser.email}"/>">
                                                        <span class="glyphicon glyphicon-envelope"></span>
                                                        <p><c:out value="${loggedUser.email}"/></p>
                                                    </a>
                                                </div>										
                                            </div>
                                        </div>	
                                        <div class="form-group comment-form-fields">
                                            <spring:message code="productPage.replyTo" arguments="${commentFamily.parentComment.author.name}" var="replyPlaceholder"/>
                                            <form:textarea type="text" class="form-control" rows="3" path="childForms[${status.index}].content" placeholder="${replyPlaceholder}"  maxlength="512"/>
                                            <form:errors path="childForms[${status.index}].content" element="p" cssClass="form-error"/>
                                        </div>
                                        <div class="btn-place">
                                            <input type="submit" class="btn btn-default post-comment-btn" value="<spring:message code="productPage.reply"/>" />
                                        </div>
                                        <div class="row comment-divider">
                                            <div class="col-md-12"></div>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                            </sec:authorize>
                            <sec:authorize access="isAnonymous()">
                            <div class="row reply-comment">
                                <div class="col-md-12 anonymous-holder">
                                    <p class="child-text-anonymous"> <spring:message code="productPage.anonymousComment1"/> 
                                    <a href="<c:url value="/login"/>"><spring:message code="productPage.anonymousComment2"/> </a>
                                    <spring:message code="productPage.anonymousComment3"/>
                                    <a href="<c:url value="/register"/>"><spring:message code="productPage.anonymousComment4"/></a>
                                    </p>

                                    <div class="row comment-divider">
                                        <div class="col-md-12"></div>
                                    </div>
                                </div>
                            </div>
                            </sec:authorize>
                            </div>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>
    <%@include file="includes/footer.jsp"%>
    </div>
	<%@include file="includes/scripts.jsp"%>
	<script>
			var gotoComment = "${comment}";
			var showForm = "${form}";
	</script>
	<script type="text/javascript" src="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.min.js"></script>
	<script src="<c:url value="/resources/js/product.js" />"></script>
</body>
</html>
