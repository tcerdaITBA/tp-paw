<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="login.head.title" /></title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="icon" href="<c:url value="/resources/img/icon.png"/>" sizes="16x16 32x32" type="image/png">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet">

    <link href="<c:url value="/resources/css/ps-buttons.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/general.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/login.css"/>" rel="stylesheet">
</head>

<body>
    <%@include file="includes/navbar.jsp"%>
    <c:url value="/login" var="loginPath" />
    <div class="container">
        <div class="row row-centered">
            <div class="col-md-12 title">
                <h2><spring:message code="signIn"/></h2>
            </div>
        </div>
        
        <form action="${loginPath}" method="post" enctype="application/x-www-form-urlencoded" class="form-horizontal">

            <div class="form-group row-centered title-align">
                <label for="j_username" class="col-md-3 control-label"><spring:message code="loginLabel.email"/></label>
                <div class="col-md-6">
                    <input type="text" name="j_username" id="j_username" class="form-control" placeholder="<spring:message code="loginLabel.email"/>"/>
                </div>
            </div>

            <div class="form-group row-centered title-align">
                <label for="j_password" class="col-md-3 control-label"><spring:message code="loginLabel.password"/></label>
                <div class="col-md-6">
                    <input type="password" name="j_password" id="j_password" class="form-control" placeholder="<spring:message code="loginLabel.password"/>"/>
                </div>
            </div>

            <div class="form-group row-centered title-align">
                <label for="j_rememberme" class="col-md-3 control-label"><spring:message code="loginLabel.rememberMe"/></label>
                <div class="col-md-1">
                    <input type="checkbox" name="j_rememberme" id="j_rememberme" class="form-control"/>
                </div>
            </div>

            <c:if test="${not empty param['error']}">
                <div class="row rwo-centered title-align">
                    <div class="col-md-12">
                        <div class="login-error">
                            <spring:message code="login.BadCredentials" />
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row row-centered">
                <div class="col-md-12 centered">
                    <input class="ps-btn-red btn submit-btn" type="submit" value="<spring:message code="signIn" />"/>
                </div>
            </div>

            <div class="row row-centered">
                <div class="col-md-12 signUpLink centered">
                    <a href="<c:url value="/register"/>">
                        <spring:message code="login.goToRegister"/>
                    </a>
                </div>
            </div>
            
        </form>  
    </div>
        
    <%@include file="includes/footer.jsp"%>
</body>

<%@include file="includes/scripts.jsp"%>
<script src="<c:url value="/resources/js/upload-form.js"/>"></script>
</html>
