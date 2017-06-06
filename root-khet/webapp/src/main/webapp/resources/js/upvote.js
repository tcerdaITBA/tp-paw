function upVotedProductByLoggedUser(id) {
	var el = document.getElementById('vote' + id);
	var className = 'voted'
	if (el.classList)
  	el.classList.add(className);
	else
  	el.className += ' ' + className;
};