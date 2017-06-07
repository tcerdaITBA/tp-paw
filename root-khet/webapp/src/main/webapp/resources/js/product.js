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
	
	//Esconde el popover en caso de hacer click afuera
	//https://stackoverflow.com/questions/11703093/how-to-dismiss-a-twitter-bootstrap-popover-by-clicking-outside
	$(document).on('click', function (e) {
	    $('[data-toggle="popover"],[data-original-title]').each(function () {
	        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {                
	            (($(this).popover('hide').data('bs.popover')||{}).inState||{}).click = false
	        }

	    });
	});
	
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
	})
	
	$('.popover-btn').on('click', function() {
		return false;
	});
});