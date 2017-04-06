$(document).ready(function() {

	function setRemoveButton(removeBtn, image, labelText, input) {
		removeBtn.on("click", function(e) {
			// Empty input
			input.value="";
			
			// Hide image
			image.attr("src", "#");
			image.css("display", "none");
			
			// Hide remove button
			removeBtn.css("display", "none");
			
			//Show label
			labelText.css("display", "inline-block");
			
			//TODO: esto no anda bien - vuelve a llamarse la ventana de subir archivo
			//e.stopImmediatePropagation();
		});
	}
	
	function showPreview (input, data) {
		var parent = $(input).parent();
		var image = parent.find("img");
		var removeBtn = parent.find(".remove-btn");
		var labelText = parent.find(".add-img-text");
				
		//Show Image
		image.attr("src", data);
		image.css("display", "inline");
		
		//Show remove button
		removeBtn.css("display", "block");
		
		//Hide label
		labelText.css("display", "none");
		
		setRemoveButton(removeBtn, image, labelText, input);
	}
	
	function loadImage(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function (e) {
				showPreview(input, e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}

	$(".image-input").change(function(){
		loadImage(this);
	});
	
});