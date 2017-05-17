const SEARCH_WAIT_TIME_MILLIS = 400;
var timeoutSearch;

function search(searchBox) {
	console.log('searching!');
	console.log(searchBox.val());
}

$(document).ready(function() {
	var searchBox = $('#search-box');
	searchBox.on('focus', function() {
		console.log('focusing!');
		//TODO: cambiar css para enfocar en barra de búsqueda
	});

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