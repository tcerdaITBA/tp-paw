$(document).ready(function(){
	
	// si no hay elementos indicarle que no subio ninguna foto o video
	var carouselSize = Number(document.getElementById("carouselSize").innerHTML);
	
	if (carouselSize == 0) {
		document.getElementById("carouselHolder").style.display = "none";
	}
	
	$('.multiple-items').slick({
		  dots: true,
		  infinite: false,
		  slidesToScroll : 1,
		  slidesToShow: 1,
		});        
});