$(document).ready(function() {
	$.fn.goTo = function() {
		var offset = 100;
        $('html, body').animate({
            scrollTop: $(this).offset().top + - offset + 'px'
        }, 'fast');
        return this; // for chaining...
    };
    
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
	
	if (gotoProduct) {
		var prod = $('#product' + gotoProduct);
		if (prod.length)
			prod.goTo();
	}    
        
    // to avoid propagation return false
	$('.delete-product-button').on('click', function() {
		$('#deleteModal' + $(this).data('product-id')).modal('show');
		return false;
	});
	
	$('[data-toggle="popover"]').on('shown.bs.popover', function() {
		$('.add-to-new-list-btn').on('click', function(e) {
			$(this).hide();
			var id = $(this).data('collection-popover');
			console.log($(this));
			console.log(id);
			$('.new-collection-form[data-collection-popover=' + id.toString() + ']').show();
			console.log($('.new-collection-form[data-collection-popover=' + id.toString() + ']'));//.show();

			e.stopPropagation();
			e.stopImmediatePropagation();
			return false;
		});
	});
});