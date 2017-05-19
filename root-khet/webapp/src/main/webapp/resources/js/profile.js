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
	
	var currentModal = null;

	// to avoid propagation return false
	$('.glyphicon-trash').on('click', function() {
	    
		var trashiconId = this.id;
	    
		var productId = trashiconId.replace("delete", "");
	    
		var modal = document.getElementById('modal' + productId);
		
		modal.style.display = "block";
		
		currentModal = modal;
		
		return false;
	});
	
	$('.modal-left-button').on('click', function() {
	    
		var leftModalButtonId = this.id;
				
		var productId = leftModalButtonId.replace("leftModalButton", "");
	    
		var modal = document.getElementById('modal' + productId);
		
		modal.style.display = "none";
		
		currentModal = null;
		
	});
	
	$('.close-modal').on('click', function() {
	    
		var closeModalId = this.id;
				
		var productId = closeModalId.replace("closeModal", "");
	    
		var modal = document.getElementById('modal' + productId);
		
		modal.style.display = "none";
		
		currentModal = null;
		
	});
	
	$(document).keyup(function(e) {
	     if (e.keyCode == 27) { // escape key maps to keycode `27`
	    	 if (currentModal != null) {
	    		 currentModal.style.display = "none";
	    		 currentModal = null;
	    	 }
	    }
	});
	

});