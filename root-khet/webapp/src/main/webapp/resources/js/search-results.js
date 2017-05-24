var noChecks = true;

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function forEachProduct(action) {
	$('.product-item').each(action);
}

function forEachCheckbox(action) {
	$('.filter-checkbox input').each(action);
}

function resetCheckboxes() {
	forEachCheckbox(function() {
		$(this).prop('checked', false);
	});
	noChecks = true;
	showAllProducts();
}

function showAllProducts() {
	forEachProduct(function() {
		$(this).show();
	});
}

function hideProductsWithCategory(category) {
	forEachProduct(function() {
		console.log("HIDING WITH CATEGORY");
		console.log($(this).data('category'));
		console.log(category);
		
		if($(this).data('category') === category)
			$(this).hide();
	});	
}

function showProductsWithCategory(category) {
	forEachProduct(function() {
		if($(this).data('category') === category)
			$(this).show();
	});	
}

function hideAllExcept(category) {
	forEachProduct(function() {
		if($(this).data('category') !== category)
			$(this).hide();
	});	
}

function hasNoChecks() {
	anyCheck = true;
	forEachCheckbox(function() {
		if($(this).is(':checked')) {
			anyCheck = false;
			return;
		}
	});
	return anyCheck;
}

$(document).ready(function(){
	var productItems = $('.product-item');
	
	productItems.each(function() {
		var category = $(this).data('category');
		$('#filter-' + category).show();
	});
	
	$('.filters:hidden').each(function() {
		console.log($(this));
		$(this).remove();
	}); 	
	
	$('#reset-filters-btn').click(resetCheckboxes);
	
	$('.filter-checkbox input').change(function() {
		var category = $(this).val();
		if ($(this).is(':checked')) {
			if (noChecks)
				hideAllExcept(category);
			else
				showProductsWithCategory(category);
			noChecks = false;
		}
		else {
			noChecks = hasNoChecks();
			if (noChecks)
				showAllProducts();
			else
				hideProductsWithCategory(category);
		}
	});
	
//	var regex = RegExp(getParameterByName('query'), 'gi');
//	var replacement = '<strong class="no-cap-strong">$&</strong>';
//	$('.result-text').html(function() {
//		return $(this).text().replace(regex, replacement);
//	});
	
	$('.creator-mail').on('click', function() {
		location.href = $(this).data('href');
		return false;
	});
});