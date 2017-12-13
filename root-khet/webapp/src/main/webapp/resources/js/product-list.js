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
    
    if (favListErrorProductId) {
        $('#add-to-collection-modal-' + favListErrorProductId).modal('show');
        $('.add-to-new-list-btn').hide();
        $('.new-collection-form').fadeIn();
		$('.new-collection-form input').focus();
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
        
	$('.add-to-collection-btn').on('click', function() {
        var addModal = $($(this).data('target'));
		addModal.modal('show');  
		$('.new-collection-form').hide();
		$('.new-collection-form input').val(null);
		$('.add-to-new-list-btn').show();
        $('.collection-error').hide();
        
        if ($(this).data('published-tab')) {
            addModal.find('form').each(function() {
                var action = $(this).attr('action');
                if (action.indexOf('publishedTab') == -1) {
                    var prefix = action.indexOf('?') == -1 ? '?' : '&'
                    $(this).attr('action', action + prefix + 'publishedTab=true');
                }
            });
        }
        
		return false;
	});
	
	$('.collection-column').hover(function() {
		$(this).find('.tool-tip').tooltip('show');
	})
	
	$('.collection-column').mouseleave(function() {
		$(this).find('.tool-tip').tooltip('hide');
	})
	
	$('.add-to-new-list-btn').on('click', function() {
		$(this).hide();
		$('.new-collection-form').fadeIn();
		$('.new-collection-form input').focus();
	})
	
	$('.close-well-icn').on('click', function() {
		$('.new-collection-form').hide();
		$('.new-collection-form input').val(null);
		$('.add-to-new-list-btn').fadeIn();
	})
});
