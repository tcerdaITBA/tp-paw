$(document).ready(function() {

	function setRemoveButton(removeBtn, image, labelText, input) {
		removeBtn.click(function(e) {
			// Empty input
			input.value="";

			// Hide image
			image.attr("src", "#");
			image.css("display", "none");

			// Hide remove button
			removeBtn.css("display", "none");

			//Show label
			labelText.css("display", "inline-block");

			//So it doesn't open the file browser window
			e.preventDefault();
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
	
	$('[data-maxlength]').on('propertychange change keyup paste input',function() {
		var max = $(this).data('maxlength');
		var errorMessage = $(this).siblings('.input-long-error');
		if ($(this).val().length > max) 
			errorMessage.show();
		else
			errorMessage.fadeOut();
	});

});