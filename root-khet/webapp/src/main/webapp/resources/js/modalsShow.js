$(document).ready(function() {
	if(passError)
		$("#changePassModal").modal("show");
    
    if(passFeedback)
        $("#feedbackChangePassModal").modal("show");
		
	if(imgError)
		$("#changePictureModal").modal("show");
	
    if(productDeleted)
        $("#feedbackDeleteModal").modal("show");
    
    // to avoid propagation return false
	$('.delete-product-button').on('click', function() {
        $('#deleteModal' + $(this).data('product-id')).modal('show');
		return false;
	});	
});
