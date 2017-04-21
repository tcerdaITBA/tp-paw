$(document).ready(function(){	
	$('.multiple-items').slick({
		  dots: true,
		  infinite: false,
		  slidesToScroll : 1,
		  slidesToShow: 1,
	});
	
	$('.reply-btn').on('click', function() {
		// Hide all other open comment forms.
		$('.reply-comment').css('display', 'none');
		
		var replyform = $(this).closest('.comment-and-replies').find('.reply-comment');
		replyform.css('display', 'inline');
	});
});