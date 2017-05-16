// Fuente: stackoverflow.com/questions/901115/how-can-i-get-query-string-values-in-javascript
function getParameterByName(name) {
	var url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
		results = regex.exec(url);
	if (!results) return null;
	if (!results[2]) return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}

$(document).ready(function(){	
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
	
	var gotoComment = getParameterByName('comment');
	if (gotoComment != null)
		$('#comment' + gotoComment).goTo();
	
	var showForm = getParameterByName('form');
	if (showForm != null) {
		var form = $('#form' + showForm);
		form.show();
		form.goTo();
	}
	
	if (window.location.hash) {
		var replyId = window.location.hash.substring(1);
		$('#' + replyId).show();
	}
	
	$('.reply-btn').on('click', function() {
		// Hide all other open comment forms.
		$('.reply-comment').hide();
		
		var replyform = $(this).closest('.comment-and-replies').find('.reply-comment');
		replyform.css('display', 'inline');
		
		replyform.goTo();
	});	
});