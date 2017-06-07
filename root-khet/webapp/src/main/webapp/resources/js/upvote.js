function upVotedProductByLoggedUser(id) {
	var els = document.querySelectorAll('[data-vote-id=vote' + id + ']');
	var className = 'voted'
	for (var i = 0; i < els.length; i++) {
		if (els[i].classList)
			els[i].classList.add(className);
		else
			els[i].className += ' ' + className;
	}
};