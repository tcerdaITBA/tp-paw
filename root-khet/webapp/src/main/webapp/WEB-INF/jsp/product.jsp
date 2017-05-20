<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><spring:message code="default.title" /></title>
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
    <link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">

</head>
<body>
<spring:message code="formLabel.emailPlaceholder" var="EmailPlaceholder"/>
<spring:message code="commentLabel.userNamePlaceholder" var="UserNamePlaceholder"/>
<spring:message code="commentLabel.contentPlaceholder" var="ContentPlaceholder"/>	

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
                        <div class="row product-item vertical-align highlighted">
                            <div class="col-md-3 product-logo">
                                <img src="<c:url value="/product/${product.id}/logo"/>">
                            </div>
                            <div class="col-md-9 product-info-box">
                                <div class="row col-md-12">
                                    <div class="row product-name">
                                        <div class="col-md-12 info-box-field">
                                            <p><c:out value="${product.name}"/></p>
                                        </div>
                                    </div>
                                    <div class="row product-short-description">
                                        <div class="col-md-12 info-box-field">
                                            <p><c:out value="${product.shortDescription}"/></p>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <a href="<c:url value="/category/${product.category.lowerName}"/>" class="product-category">
                                            <div class="col-md-4">
                                                <div class="categoryTag">
                                                    <p><spring:message code="category.${product.category.lowerName}"/></p>
                                                </div>
                                            </div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 website-btn-col">
                                <c:if test="${not empty product.website}">
                                    <div class="website-btn-row">
                                        <a href="${product.website}" class="ps-btn btn website-btn" target="_blank">
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
<!--
                        <div class="back-to-products">
                            <a href="<c:url value="/"/>"><spring:message code="productPage.backToProducts" /></a>
                        </div>
-->
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
                                        <p class="reply-btn">
                                            <span class="glyphicon glyphicon-share-alt"></span>
                                            <spring:message code="productPage.reply"/>
                                        </p>
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
    </div>

    <%@include file="includes/footer.jsp"%>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script
                    src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- 	Include all compiled plugins (below), or include individual files as needed -->
    <script
                    src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
                    integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
                    crossorigin="anonymous"></script>
    <script type="text/javascript" src="//cdn.jsdelivr.net/jquery.slick/1.6.0/slick.min.js"></script>
    <script>
        var gotoComment = "${comment}";
        var showForm = "${form}";
    </script>
    <script src="<c:url value="/resources/js/product.js" />"></script>	
</body>
</html>