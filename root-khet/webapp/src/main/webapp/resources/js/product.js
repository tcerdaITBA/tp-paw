$(document).ready(function(){	
	$('.multiple-items').slick({
		  dots: true,
		  infinite: false,
		  slidesToScroll : 1,
		  slidesToShow: 1,
	});
	
	if (window.location.hash) {
		var errorReplyId = window.location.hash.substring(1);
		$('#' + errorReplyId).show();
	}
	
	$('.reply-btn').on('click', function() {
		// Hide all other open comment forms.
		$('.reply-comment').hide();
		
		var replyform = $(this).closest('.comment-and-replies').find('.reply-comment');
		replyform.css('display', 'inline');
	});
});