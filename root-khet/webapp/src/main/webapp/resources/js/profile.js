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

//http://stackoverflow.com/questions/1634748/how-can-i-delete-a-query-string-parameter-in-javascript
function removeURLParameter(parameter) {
	var url = window.location.href;
    var urlparts= url.split('?');
    
    if (urlparts.length>=2) {

        var prefix= encodeURIComponent(parameter)+'=';
        var pars= urlparts[1].split(/[&;]/g);

        for (var i= pars.length; i-- > 0;) {    
            if (pars[i].lastIndexOf(prefix, 0) !== -1) {  
                pars.splice(i, 1);
            }
        }

        url= urlparts[0] + (pars.length > 0 ? '?' + pars.join('&') : "");
        return url;
    } else {
        return url;
    }
}

$(document).ready(function(){
	
	var query = getParameterByName("passwordError");
	
	if(query != null) {
		$("#changePassModal").modal("show");
		window.history.replaceState("", "", removeURLParameter("passwordError"));
	}
	
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