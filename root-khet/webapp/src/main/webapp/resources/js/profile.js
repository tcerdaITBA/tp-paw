$(document).ready(function() {
//	var currentHash = window.location.hash.substr(1);
//	
//	if (!currentHash)
//		location.hash = "goBack";
//	else if (currentHash === "goBack")
//		history.back();
	
	if(passError)
		$("#changePassModal").modal("show");
		
	if(imgError)
		$("#changePictureModal").modal("show");
	
    
	// to avoid propagation return false
	$('.glyphicon-trash').on('click', function() {
        $("#deleteModal").modal("show");
		return false;
	});	
});