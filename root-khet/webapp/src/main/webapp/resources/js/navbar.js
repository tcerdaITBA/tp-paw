$(document).ready(function(){
	$('[data-toggle="popover"]').popover({
		trigger: 'focus',
		placement: 'bottom',
		html: true,
	});
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

$(document).ready(function() {
	var timeoutSearch;
	
	var searchBox = $('#search-box');
	var searchBtn = $('.search-button');
	
	searchBox.focus(showSuggestions);
	searchBtn.focus(showSuggestions);
	
	searchBox.focusout(hideSuggestions);

	searchBox.on('keyup', function(event) {
		if (timeoutSearch) {
			console.log('clearing');
			clearTimeout(timeoutSearch);
		}
		
		timeoutSearch = setTimeout(function() {
			search(searchBox)	
		}, SEARCH_WAIT_TIME_MILLIS);
	});
});