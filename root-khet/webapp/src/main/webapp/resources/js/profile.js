function glowObject(object) {
	object.addClass('glow-active');
	setTimeout(function() {
		object.addClass('glow-remove');
	}, 600);
}

$(document).ready(function() {
	if(passError)
		$('#changePassModal').modal('show');

	if(passFeedback) {
		$('#password-change-snackbar').addClass('show');
		setTimeout(function(){ $('#password-change-snackbar').removeClass('show'); }, 3000);
	}

	if(imgError)
		$('#changePictureModal').modal('show');

	if(productDeleted)
		$('#feedbackDeleteModal').modal('show');

	if(imgFeedback)
		glowObject($('.profile-img'));	
	
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
	
	$('#add-new-collection-link').on('click', function() {
		$(this).hide();
		$('#new-collection-section').fadeIn();
		$('#new-collection-section input').focus();
	});
	
	$('.close-well-icn').on('click', function() {
		$('#new-collection-section').hide();
		$('#new-collection-section input').val(null);
		$('#add-new-collection-link').fadeIn();
	})
});
