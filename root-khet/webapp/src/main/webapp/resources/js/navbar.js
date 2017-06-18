$(document).ready(function(){
	$('[data-toggle="popover"]').popover({
		trigger: 'focus',
		placement: 'bottom',
		html: true,
	});
	
  $('[data-toggle="tooltip"]').tooltip();
});

const SEARCH_WAIT_TIME_MILLIS = 400;

function search(searchBox) {
	console.log('searching!');
	console.log(searchBox.val());
}

function showSuggestions() {
	$('.suggestions-box').show();
}

function hideSuggestions() {
	$('.suggestions-box').hide();	
}

function arrowFocus(selected) {
	$('#suggestion-' + selected.toString()).focus();
}

$(document).ready(function() {
	var timeoutSearch;
	
	var searchBox = $('#search-box');
	var searchBtn = $('.search-button');
	
	searchBox.focus(showSuggestions);
	searchBtn.focus(showSuggestions);
	
	var selected = -1;
	var maxIndex = $('[data-list-index]').length - 1;
	
	// Dismiss search suggestions if clicked outside
	$(document).click(function(e) {
    var container = $('.search-form-container');
    if (!container.is(e.target) && container.has(e.target).length === 0) {
      $('.suggestions-box').hide();
			selected = -1;	
		}
	});
	
	
	$('.search-form-container').keydown(function(e) {
		if (e.keyCode == 38) { // arrow up
			selected--;
			if (selected < 0) { // Focus back to input
				selected = -1;
				searchBox.focus();
			}
			else
				arrowFocus(selected);
			return false;
		} 
		else if (e.keyCode == 40) { // arrow down
			selected = (selected < maxIndex ? selected + 1 : selected);
			arrowFocus(selected);
			return false;
		}
	});
	
// Timed autocomplete
//	searchBox.on('keyup', function(event) {
//		if (timeoutSearch) {
//			console.log('clearing');
//			clearTimeout(timeoutSearch);
//		}
//		
//		timeoutSearch = setTimeout(function() {
//			search(searchBox)	
//		}, SEARCH_WAIT_TIME_MILLIS);
//	});
});