function updateSearch(event) {
	console.log('SEARCHING');
	console.log(event);
}

$(document).ready({
	var searchBox = $('#search-box')
	searchBox.onfocus({
		
	});

	searchBox.onsearch(function(event) {
		updateSearch(event);
	});
});