'use strict';
define(['productSeek', 'services/sessionService'], function(productSeek) {

	var logInError = function(error) {
		session.destroy();
	} 
	
	return productSeek.factory('authService', ['$http', 'url', 'sessionService', function($http, url, session) {
		var AuthService = {};

		AuthService.logIn = function(username, password) {
			var credentials = { j_username: username, j_password: password };
			return $http.post(url + '/login', credentials)
				.then(function(response) {
					session.setAccessToken(response.headers('X-AUTH-TOKEN'));
					return $http.post(url + '/user', {headers: {'X-AUTH-TOKEN': session.getAccessToken()}});
				})
				.then(function(response) {
					this.loggedUser = response.data;
					return this.loggedUser;
				})
				.catch(logInError);
		};

		AuthService.isLoggedIn = function() {
			return this.loggedUser !== null;
		};

		AuthService.logOut = function() {
			session.destroy();
			this.loggedUser = null;
		};
		
		AuthService.getLoggedUser = function() {
			return this.loggedUser;
		}
		
		return AuthService;
	}]);

});