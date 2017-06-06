function upVotedProductByLoggedUser(id) {
	document.getElementById("vote" + id).addClass('voted');
}

$(document).ready(function() {
	$('.product-category-btn').on('click', function(event) {
		console.log(event);
		location.href = $(this).data('href');
		event.stopPropagation();
		return false;
	});
});