$(document).ready(function(){
//	var currentHash = window.location.hash.substr(1);
//	
//	if (!currentHash)
//		location.hash = "goBack";
//	else if (currentHash === "goBack")
//		history.back();
	
	$.fn.goTo = function() {
		var offset = 100;
        $('html, body').animate({
            scrollTop: $(this).offset().top + - offset + 'px'
        }, 'fast');
        return this; // for chaining...
    };
	
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
	
	$('[data-toggle="popover"]').popover({
		trigger: 'focus',
		placement: 'bottom',
		html: true,
	});
	
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
	})
	
	$('.upvote-btn').on('click', function() {
		return false;
	});
});