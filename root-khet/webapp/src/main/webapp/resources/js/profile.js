$(document).ready(function(){
	
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