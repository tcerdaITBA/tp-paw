$(document).ready(function(){
	$.fn.goTo = function() {
		var offset = 100;
        $('html, body').animate({
            scrollTop: $(this).offset().top + - offset + 'px'
        }, 'fast');
        return this; // for chaining...
    };
    
    if (gotoProduct)
		$('#product' + gotoProduct).goTo();
});