$(document).ready(function() {
//	var currentHash = window.location.hash.substr(1);
//	
//	if (!currentHash)
//		location.hash = "goBack";
//	else if (currentHash === "goBack")
//		history.back();
	    
	// to avoid propagation return false
	$('.glyphicon-trash').on('click', function() {
        $('#deleteModal' + $(this).data('product-id')).modal('show');
		return false;
	});	
});