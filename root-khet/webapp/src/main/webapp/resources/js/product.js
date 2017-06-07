$(document).ready(function(){
	
	$('.multiple-items').slick({
		  dots: true,
		  infinite: false,
		  slidesToScroll : 1,
		  slidesToShow: 1,
	});
		
	if (gotoComment)
		$('#comment' + gotoComment).goTo();
	
	if (showForm) {
		var form = $('#form' + showForm);
		form.show();
		form.goTo();
	}
	
	if (window.location.hash) {
		var replyId = window.location.hash.substring(1);
		$('#' + replyId).show();
	}
	
	$('.reply-btn-fn').on('click', function() {
		// Hide all other open comment forms.
		$('.reply-comment').hide();
		
		var replyform = $(this).closest('.comment-and-replies').find('.reply-comment');
		replyform.css('display', 'inline');
		
		replyform.goTo();
	});	
	
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
	})
		
	$(function(){
	    $('[rel="popover"]').popover({
	        container: 'body',
	        html: true,
	        content: function () {
	            var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
	            return clone;
	        }
	    }).click(function(e) {
	        e.preventDefault();
	    });
	});
	
	
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
	})
	
	$('.popover-btn').on('click', function() {
		return false;
	});

});