<%@include file="favListProductAddedFeedback.jsp"%>
<%@include file="favListCreatedFeedback.jsp"%>
<script src="<c:url value="/resources/js/profile.js"/>"></script>
<script src="<c:url value="/resources/js/product-list.js"/>"></script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
	integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
	crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/navbar.js"/>"></script>
<script>
    var passError = "${passError}";
    var passFeedback = "${passFeedback}";
    var imgError = "${imgError}";
    var imgFeedback = "${imgFeedback}";
    var productDeleted = "<c:out value="${productDeleted}" />";
    var gotoProduct = "${productVoted}";
    var favListErrorProductId = "${favListErrorProductId}";
</script>