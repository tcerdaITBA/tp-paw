$(document).ready(function() {

	function removeOnlyText(node) {
		node.contents().filter(function () {
					return this.nodeType === 3; 
		}).remove();
	}
	
	function showImage (image, data) {
		image.attr("src", data);
		image.css("display", "block");
	}
	
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function (e) {
				var previewImg = $(input).parent().find("img");
				var previewLabel = $(input).parent().find("label");

				removeOnlyText(previewLabel);
				showImage(previewImg, e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}

	$(".image-input").change(function(){
		readURL(this);
	});

});