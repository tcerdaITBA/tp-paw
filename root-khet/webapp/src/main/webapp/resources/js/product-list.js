$(document).ready(function() {
	$.fn.goTo = function() {
		var offset = 100;
        $('html, body').animate({
            scrollTop: $(this).offset().top + - offset + 'px'
        }, 'fast');
        return this; // for chaining...
    };
    
	$('.product-category-btn').on('click', function(event) {
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

	$('.collection-delete-btn').on('click', function() {
        $('#favListDeleteModal' + $(this).data('favlist-id')).modal('show');
		return false;
	});
    
	// TODO: Se puede mejorar porque ahora se le aplica a TODOS los del html
	$('.add-to-collection-btn').on('click', function() {
		$($(this).data('target')).modal('show');
		$('.new-collection-form').hide();
		$('.new-collection-form input').val(null);
		$('.add-to-new-list-btn').show();
		return false;
	});
	
	$('.add-to-new-list-btn').on('click', function() {
		$(this).hide();
		$('.new-collection-form').show();
		$('.new-collection-form input').focus();
	})
});