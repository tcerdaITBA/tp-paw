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
	
	if (window.location.hash) {
		var errorReplyId = window.location.hash.substring(1);
		$('#' + errorReplyId).show();
	}
	
	$('.reply-btn').on('click', function() {
		// Hide all other open comment forms.
		$('.reply-comment').hide();
		
		var replyform = $(this).closest('.comment-and-replies').find('.reply-comment');
		replyform.css('display', 'inline');
		
		replyform.goTo();
	});
	
	var modal = document.getElementById('myModal');
	
	var leftButton = document.getElementById('leftModalButton');
	
	// When the user clicks on left button, close the modal
	leftButton.onclick = function() {
	    modal.style.display = "none";
	}

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
	    modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
	
	$('.ps-btn-red').on('click', function() {
	    modal.style.display = "block";
	})
});