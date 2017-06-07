$(document).ready(function() {
	$('.product-category-btn').on('click', function(event) {
        console.log(event);
		location.href = $(this).data('href');
		event.stopPropagation();
		return false;
	});
	
	$('.popover-btn').on('click', function() {
		$(this).focus();
		return false;
	});
});